package com.pw.base.kits.db;

import com.pw.base.kits.db.entity.DbColumn;
import com.pw.base.kits.db.entity.DbTable;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 批量为数据库中的表增加公共字段。
 * 注意：
 * 方法比较暴力，会先删除原来数据库的字段，再增加字段，会丢失原来的数据。
 * 此功能不适合线上数据库操作，仅用于数据库设计阶段，注意！
 *
 * @author Van
 * @date 2018/12/20 17:13
 */
@Log4j2
public class TableUtils {

    /**
     * 数据库连接，不解释
     */
    private static final String SQL_URL = "jdbc:mysql://localhost:3306/yf_exam_20?characterEncoding=UTF-8" +
            "&useUnicode" +
            "=true&useSSL=false&tinyInt1isBit=false";
    private static final String SQL_USER = "root";
    private static final String SQL_PASSWORD = "root";

    /**
     * 先DROP已有的字段
     */
    private static final List<String> FIX_COLUMNS = Arrays.asList(
            "create_time",
            "update_time",
            "create_by",
            "update_by",
            "data_flag");

    /**
     * 增加公共列的方法
     */
    private static final String ALTER_SQL = "ALTER TABLE {table} " +
            "ADD COLUMN `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
            "ADD COLUMN `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间' AFTER `create_time`,\n" +
            "ADD COLUMN `create_by` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人' AFTER `update_time`,\n" +
            "ADD COLUMN `update_by` varchar(255) NOT NULL DEFAULT '' COMMENT '修改人' AFTER `create_by`,\n" +
            "ADD COLUMN `data_flag` int(11) NOT NULL DEFAULT 0 COMMENT '数据标识' AFTER `update_by`;";

    /**
     * 删除列的方法
     */
    private static final String DROP_SQL = "ALTER TABLE {table} ";


    /**
     * 数据库连接
     */
    private static Connection conn;


    /**
     * 操作入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 要处理的表前缀
        String prefix = "sys_role_menu";

        //获取数据库连接
        conn = getMySQLConnection();

        // 获取数据库下的所有表名称，包含了需要处理的字段列表
        List<DbTable> dbTables = getAllTableName(prefix);

        // 获得表中所有字段信息
        alterColumns(dbTables);

        if (conn != null) {
            conn.close();
        }
    }


    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    private static Connection getMySQLConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASSWORD);
        return conn;
    }

    /**
     * 获取数据库的所有表信息
     *
     * @return
     * @throws Exception
     */
    private static List<DbTable> getAllTableName(String prefix) throws Exception {
        List<DbTable> dbTables = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES");
        while (rs.next()) {
            String tableName = rs.getString(1);
            if(tableName.startsWith(prefix)) {
                DbTable dbTable = new DbTable(tableName);
                dbTables.add(dbTable);
            }
        }
        rs.close();
        stmt.close();

        //生成完成的数据表和字段
        buildColumns(dbTables);

        return dbTables;
    }


    /**
     * 获取完整的数据库表和字段（字段仅仅是包含的）
     *
     * @param dbTables
     * @throws Exception
     */
    private static void buildColumns(List<DbTable> dbTables) throws Exception {
        Statement stmt = conn.createStatement();
        for (DbTable dbTable : dbTables) {
            List<DbColumn> dbColumns = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("show full columns from " + dbTable.getTableName());
            if (rs != null) {
                while (rs.next()) {
                    String field = rs.getString("Field");

                    //包含在公共列中则删除
                    if (FIX_COLUMNS.contains(field)) {
                        DbColumn dbColumn = new DbColumn(field, field, "", "", "", "");
                        dbColumns.add(dbColumn);
                    }
                }
            }
            if (rs != null) {
                rs.close();
            }
            dbTable.setDbColumns(dbColumns);
        }
        stmt.close();
    }


    /**
     * 批量移除和添加公共字段
     *
     * @param dbTables
     * @throws Exception
     */
    private static void alterColumns(List<DbTable> dbTables) throws Exception {

        for (DbTable dbTable : dbTables) {
            List<DbColumn> dbColumns = dbTable.getDbColumns();
            if (!CollectionUtils.isEmpty(dbColumns)) {
                StringBuffer dropSql = new StringBuffer(DROP_SQL.replace("{table}", dbTable.getTableName()));
                for (DbColumn dbColumn : dbColumns) {

                    if (dropSql.indexOf("DROP") != -1) {
                        dropSql.append(",");
                    }

                    dropSql.append(" DROP COLUMN `" + dbColumn.getField() + "`");
                }
                dropSql.append(";");
                log.info(MessageFormat.format("移除表{0}的公共字段：\n{1}", dbTable.getTableName(), dropSql.toString()));
                executeSql(dropSql.toString());
            }
            String alterSql = ALTER_SQL.replace("{table}", dbTable.getTableName());
            log.info(MessageFormat.format("新增表{0}的公共字段：\n{1}", dbTable.getTableName(), alterSql));
            executeSql(alterSql);
        }

    }

    /**
     * 执行SQL
     *
     * @param sql
     * @throws Exception
     */
    private static void executeSql(String sql) throws Exception {
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

}
