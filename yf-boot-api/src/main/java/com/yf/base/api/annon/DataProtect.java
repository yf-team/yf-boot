package com.yf.base.api.annon;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保护某些数据不被删除或更新
 * @author bool
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataProtect {

    /**
     * 更新保护
     * @return
     */
    boolean update() default false;

    /**
     * 删除保护
     * @return
     */
    boolean delete() default false;

    /**
     * 当前用户ID
     * @return
     */
    boolean currUsr() default false;

    /**
     * 实体类名，获取表名
     * @return
     */
    Class clazz();
}
