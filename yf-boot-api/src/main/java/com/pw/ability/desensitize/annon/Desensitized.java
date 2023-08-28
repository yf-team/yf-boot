package com.pw.ability.desensitize.annon;

import com.pw.ability.desensitize.enums.DesensitizeType;

import java.lang.annotation.*;


/**
 * 脱敏注解类
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Desensitized {

    // 加密类型
    DesensitizeType type() default DesensitizeType.STRING;

    // 类型
    String [] props() default {};
}