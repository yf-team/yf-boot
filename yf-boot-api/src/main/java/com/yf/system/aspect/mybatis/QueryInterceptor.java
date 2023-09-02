package com.yf.system.aspect.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.yf.ability.shiro.dto.SysUserLoginDTO;
import com.yf.base.enums.DataScope;
import lombok.extern.log4j.Log4j2;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.shiro.SecurityUtils;

import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;

/**
 * 查询拦截器，用于拦截处理通用的信息、如用户ID、多租户信息等；
 * 特别注意：此处继承了PaginationInterceptor分页，分页必须在拦截数据后执行，否则容易出现分页不准确，分页计数大于实际数量等问题
 * @author bool
 */
@Log4j2
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),})
public class QueryInterceptor extends PaginationInterceptor implements Interceptor {

    /**
     * 客户ID
     */
    private static final String USER_FILTER = "{{userId}}";


    /**
     * 本人数据
     */
    private static final String FILTER_SELF = "{alias}.`create_by`='{userId}'";

    /**
     * 本部门数据
     */
    private static final String FILTER_DEPT = "{alias}.`dept_code`='{deptCode}'";

    /**
     * 本部门及以下
     */
    private static final String FILTER_DEPT_DOWN = "{alias}.`dept_code` LIKE '{deptCode}%'";


    /**
     * 需要进行数据权限过滤的查询
     */
    private static final String [] FILTER_MAPPERS = new String[]{
            ".CourseMapper.selectPage",
            ".CourseFileMapper.selectPage",
            ".CourseQaMapper.paging",
            ".CertMapper.selectPage",
            ".CertGrantMapper.paging",
            ".RepoMapper.paging",
            ".QuMapper.paging",
            ".LiveMapper.selectPage",
            ".ExamMapper.paging",
            ".ExamRecordMapper.paging",
            ".UserRepoMapper.paging",
            ".TmplMapper.selectPage",
            // 用户列表
            ".SysUserMapper.paging",
            // 用户导出
            ".SysUserMapper.listForExport",
            ".BattleMapper.selectPage",
            // 阅卷
            ".PaperMapper.paging",
            // 首页考试列表
            ".StatExamMapper.dashExamList",
            // 首页课程列表
            ".StatCourseMapper.dashCourseList",
            // 报名相关
            ".ActivityMapper.paging",
            ".ActivityJoinMapper.paging"
    };


    /**
     * 是否需要处理数据权限
     * @param id
     * @return
     */
    private boolean needFilter(String id) {
        for (String item : FILTER_MAPPERS) {
            if (id.contains(item)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //sql语句类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 获得Mapper名称
        String id = mappedStatement.getId();

        // 只过滤查询的
        if (SqlCommandType.SELECT == sqlCommandType) {
            // 获得原始SQL
            String sql = statementHandler.getBoundSql().getSql();

            // 替换用户ID
            if(sql.contains(USER_FILTER)){
                sql = sql.replace(USER_FILTER, currentId());
            }

            // 数据权限
            if(this.needFilter(id)){
                sql = this.parseSql(sql);
            }

            // 设置SQL
            metaObject.setValue("delegate.boundSql.sql", sql);
            // 再分页
            return super.intercept(invocation);
        }

        // 执行分页
        return super.intercept(invocation);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    /**
     * 获取当前登录
     * @return
     */
    private SysUserLoginDTO currentUser() {
        try {
            return SecurityUtils.getSubject().getPrincipal() != null ? (SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户ID
     * @return
     */
    private String currentId() {

        // 当前用户
        SysUserLoginDTO user = this.currentUser();

        if(user!=null){
            return user.getId();
        }

        // 返回一个完全不存在的用户ID
        return "no-this-user";
    }

    /**
     * 处理SQL语句
     *
     * @param src
     * @return
     */
    private String parseSql(String src) {

        log.info("++++++++++原始SQL：" + src);
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        try {

            Select select = (Select) parserManager.parse(new StringReader(src));
            PlainSelect selectBody = (PlainSelect) select.getSelectBody();

            // 处理表的别名，防止出现多表一样的字段
            Table table = (Table) selectBody.getFromItem();
            String alias;
            if (table.getAlias() == null) {
                alias = "mtb";
                table.setAlias(new Alias(alias));
            } else {
                alias = table.getAlias().getName();
            }

            // 本人数据
            String appendSql = this.generateAppend(alias);

            if(!StringUtils.isBlank(appendSql)){

                // 无条件的加上WHERE
                if (selectBody.getWhere() == null) {
                    selectBody.setWhere(CCJSqlParserUtil.parseCondExpression(appendSql));
                } else {
                    Expression whereExpression = CCJSqlParserUtil.parseCondExpression(appendSql);
                    selectBody.setWhere(new AndExpression(selectBody.getWhere(), new Parenthesis(whereExpression)));
                }
            }


            String out = selectBody.toString();
            log.info("++++++++++过滤SQL：" + out);
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return src;
    }


    /**
     * 处理数据权限作用域
     * @param alias
     * @return
     */
    private String generateAppend(String alias){

        SysUserLoginDTO user = this.currentUser();

        // 无需登录的内容，不做权限过滤
        if(user == null){
            return "";
        }

        // 自己的数据
        if(DataScope.SCOPE_SELF.equals(user.getDataScope())){
            return FILTER_SELF.replace("{alias}", alias).replace("{userId}", this.currentId());
        }

        // 自己部门的数据
        if(DataScope.SCOPE_DEPT.equals(user.getDataScope())){
            return FILTER_DEPT.replace("{alias}", alias).replace("{deptCode}", user.getDeptCode());
        }

        // 自己部门及以下的数据
        if(DataScope.SCOPE_DEPT_DOWN.equals(user.getDataScope())){
            return FILTER_DEPT_DOWN.replace("{alias}", alias).replace("{deptCode}", user.getDeptCode());
        }

        return "";
    }


}
