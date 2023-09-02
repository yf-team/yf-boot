package com.yf.ability.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出注解定义
 * @author bool
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * 导出的excel标题
	 * @return
	 */
	String title();

	/**
	 * 数据过滤，格式如：0=正常,1=禁用
	 * @return
	 */
	String filter() default "";

	/**
	 * 导出字段字段排序（升序）
	 */
	int sort() default 0;

	/**
	 * 日期格式化
	 * @return
	 */
	String pattern() default "";

	/**
	 * 数据字典类型
	 */
	String dictCode() default "";

	/**
	 * 查找字段
	 * @return
	 */
	String dicText() default "";

	/**
	 * 查找表
	 * @return
	 */
	String dictTable() default "";
}
