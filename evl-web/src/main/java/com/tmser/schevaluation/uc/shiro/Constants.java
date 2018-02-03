/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.shiro;

/**
 * <pre>
 * 	平台公用开发常量
 * </pre>
 *
 * @author tmser
 * @version $Id: Constants.java, v 1.0 2017年2月23日 上午10:23:55 tmser Exp $
 */
public abstract class Constants {

	/**
	 * 默认用户id
	 */
	public static final String DEFAULT_USER_ID = "0";
	
	/**
	 * 默认机构id
	 */
	public static final String DEFAULT_ORG_ID = "0";
	
	/**
	 * 当前用户MDC key
	 */
	public static final String MDC_CURRENT_USER_ID = "_mdc_current_user_id";
	
	/**
	 * 首页地址
	 */
	public  static final String  EVL_INDEX="/jy/evl/index";
	
	
	/**
	 * 用户空间不存在异常
	 * USERSPACE_ERROR
	 */
	public  static final String  USERSPACE_ERROR ="USERSPACE_ERROR";
}
