/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * 防止重复提交注解，用于方法上<br/>
 * 在跳转到表单页面的方法和表单处理的方法上，注解UseToken()，
 * 其中表单处理方法value 值设置为true. @UseToken(true)
 * 同时需要在表单的页面的form 中增加
 * <ui:token/> 标签
 * </p>
 *
 * @author tmser
 * @version $Id: UseToken.java, v 1.0 2015年2月1日 下午11:02:42 tmser Exp $
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseToken {
	/**
	 * Whether check the token.
	 */
	boolean value() default false;
}
