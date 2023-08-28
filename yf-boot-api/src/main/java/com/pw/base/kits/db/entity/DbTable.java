package com.pw.base.kits.db.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据表信息
 *
 * @author Van
 * @date 2018/12/20 17:11
 */
@Data
public class DbTable {

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 数据库表的建表语句
     */
    private String comment;

    /**
     * 表包含的字段
     */
    private List<DbColumn> dbColumns;

    public DbTable(String tableName) {
        this.tableName = tableName;
    }

}