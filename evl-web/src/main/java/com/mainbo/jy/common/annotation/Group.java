/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Group 分组注解。
 * 注解Controller 类时，作为类所有方法的分组。
 * 注解controller方法时，指定方法分组，不继承类所属分组
 * </pre>
 *
 * @author tmser
 * @version $Id: Action.java, v 1.0 2015年2月1日 下午11:02:42 tmser Exp $
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Group {
	/**
	 * 分组标识代码
	 * @return
	 */
	String value();
	
	/**
	 * 分组名称
	 * @return
	 */
	String name();
	
	
}
