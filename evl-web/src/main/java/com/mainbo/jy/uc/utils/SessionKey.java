/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.utils;

/**
 * <pre>
 *	uc 模块session key 
 * </pre>
 *
 * @author tmser
 * @version $Id: SessionKey.java, v 1.0 2015年2月3日 下午2:58:46 tmser Exp $
 */
public abstract class SessionKey {
	/**
	 * 当前用户
	 */
	public static final String CURRENT_USER = "_CURRENT_USER_";
	
	/**
	* 当前用户所在机构
	*/
	public static final String CURRENT_ORGANIZATION = "_CURRENT_ORGANIZATION_";
	
	/**
	 * 当前用户空间列表@see UserSpace
	 */
	public static final String USER_SPACE_LIST = "_USER_SPACE_LIST_";
	
	/**
	 * 当前使用的用户空间
	 */
	public static final String CURRENT_SPACE = "_CURRENT_SPACE_";
	
	
	/**
	 * 当前使用的学年
	 */
	public static final String CURRENT_SCHOOLYEAR = "_CURRENT_SCHOOLYEAR_";
	
	/**
	 * 当前用户菜单列表
	 */
	public static final String CURRENT_MENU_LIST = "_CURRENT_MENU_LIST_";
	
	/**
	 * 当前学期
	 */
	public static final String CURRENT_TERM = "_CURRENT_TERM_";
	/**
	 * 当前学校学段列表
	 */
	public static final String CURRENT_PHASE_LIST = "_CURRENT_PHASE_LIST_";
	/**
	 * 当前学校学段信息
	 */
	public static final String CURRENT_PHASE = "_CURRENT_PHASE_";
	
	public static final String EVL_OPERATETYPE = "operate_type";
	
}
