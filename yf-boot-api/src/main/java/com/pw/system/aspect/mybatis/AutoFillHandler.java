package com.pw.system.aspect.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pw.system.modules.user.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;

@Log4j2
public class AutoFillHandler implements MetaObjectHandler {

    /**
     * 需要进行数据权限过滤的查询
     */
    private static final String [] EXCLUDE_MAPPERS = new String[]{
            ".SysDepartMapper.updateById",
            ".SysUserMapper.update",
            ".SysUserMapper.updateById"
    };

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");


        // 注入数据
        this.setIfNull(metaObject, "createTime", Timestamp.class, new Timestamp(System.currentTimeMillis()));
        this.setIfNull(metaObject, "createBy", String.class, UserUtils.getUserId());
        this.setIfNull(metaObject, "deptCode", String.class, UserUtils.departCode());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("nothing to fill ....");
        this.setIfNull(metaObject, "updateTime", Timestamp.class, new Timestamp(System.currentTimeMillis()));
        this.setIfNull(metaObject, "createBy", String.class, UserUtils.getUserId());
    }


    /**
     * 如果为空，才进行赋值
     * @param metaObject
     * @param fieldName
     * @param fieldType
     * @param fieldVal
     */
    private void setIfNull(MetaObject metaObject, String fieldName, Class fieldType, Object fieldVal){
        Object obj = metaObject.getValue(fieldName);
        if(obj == null){
            this.strictInsertFill(metaObject, fieldName, fieldType, fieldVal);
        }
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
