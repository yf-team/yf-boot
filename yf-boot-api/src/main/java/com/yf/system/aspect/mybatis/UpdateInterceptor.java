package com.yf.system.aspect.mybatis;

import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.yf.system.modules.user.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

/**
 * 自动给创建时间个更新时间加值
 * @author bool
 */
@Log4j2
@Intercepts(value = {@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateInterceptor extends AbstractSqlParserHandler implements Interceptor {

    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createTime";
    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 创建人
     */
    private static final String CREATE_BY = "createBy";

    /**
     * 修改人
     */
    private static final String UPDATE_BY = "updateBy";

    /**
     * 归属部门
     */
    private static final String DEPT_CODE = "deptCode";

    /**
     * 需要进行数据权限过滤的查询
     */
    private static final String [] EXCLUDE_MAPPERS = new String[]{
            ".SysDepartMapper.updateById",
            ".SysUserMapper.update",
            ".SysUserMapper.updateById"
    };



    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获得Mapper名称
        String id = mappedStatement.getId();

        // 排除的不处理
        if(this.isExclude(id)){
            return invocation.proceed();
        }

        // SQL操作命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取新增或修改的对象参数
        Object parameter = invocation.getArgs()[1];

        // 处理数据
        if(parameter!=null){

            // 新增数据
            if(SqlCommandType.INSERT.equals(sqlCommandType)){
                this.processInsert(parameter);
            }

            if(SqlCommandType.UPDATE.equals(sqlCommandType)){
                this.processUpdate(parameter);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }


    /**
     * 处理插入数据
     * @param parameter
     * @throws IllegalAccessException
     */
    private void processInsert(Object parameter) throws IllegalAccessException {

        Field[] fields = getAllFields(parameter);

        for (Field field : fields) {
            // 创建时间
            if (Objects.equals(CREATE_TIME, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }

            // 注入创建人
            if (Objects.equals(CREATE_BY, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, UserUtils.getUserId(false));
            }

            // 更新时间
            if (Objects.equals(UPDATE_TIME, field.getName())) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }

            // 注入更新人
            if (Objects.equals(UPDATE_BY,  field.getName())) {
                field.setAccessible(true);
                field.set(parameter, UserUtils.getUserId(false));
            }

            // 注入部门ID
            if (Objects.equals(DEPT_CODE, field.getName())) {
                field.setAccessible(true);
                Object value = field.get(parameter);
                if(value == null){
                    field.set(parameter, UserUtils.departCode());
                }
            }
        }
    }

    /**
     * 处理更新
     * @param parameter
     * @throws IllegalAccessException
     */
    private void processUpdate(Object parameter) throws IllegalAccessException {

        if (parameter instanceof ParamMap) {
            ParamMap<?> p = (ParamMap<?>) parameter;
            if (p.containsKey("et")) {
                parameter = p.get("et");
            } else {
                parameter = p.get("param1");
            }
            if (parameter == null) {
                return;
            }
        }

        Field [] fields = getAllFields(parameter);

        for (Field field : fields) {
            String fieldName  = field.getName();
            // 更新时间
            if (Objects.equals(UPDATE_TIME, fieldName)) {
                field.setAccessible(true);
                field.set(parameter, new Timestamp(System.currentTimeMillis()));
            }
            // 注入更新人
            if (Objects.equals(UPDATE_BY, fieldName)) {
                field.setAccessible(true);
                field.set(parameter, UserUtils.getUserId(false));
            }
            // 注入部门ID
            if (Objects.equals(DEPT_CODE, field.getName())) {
                field.setAccessible(true);
                Object value = field.get(parameter);
                if(value == null){
                    field.set(parameter, UserUtils.departCode());
                }
            }
        }
    }


    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }



    /**
     * 是否需要排除的
     * @param id
     * @return
     */
    private boolean isExclude(String id) {
        for (String item : EXCLUDE_MAPPERS) {
            if (id.contains(item)) {
                return true;
            }
        }

        return false;
    }


}
