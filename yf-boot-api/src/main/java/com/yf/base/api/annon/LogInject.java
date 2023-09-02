package com.yf.base.api.annon;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注入类
 * @author bool
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LogInject {

    /**
     * 日志类型
     * @return
     */
    String logType() default "日志类型";

    /**
     * 日志标题
     * @return
     */
    String title() default "系统日志";
}
