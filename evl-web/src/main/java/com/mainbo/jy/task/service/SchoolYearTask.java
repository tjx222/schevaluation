/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.task.service;

/**
 * <pre>
 *  学年切换任务
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearTask.java, v 1.0 2016年8月18日 上午10:46:46 tmser Exp $
 */
public interface SchoolYearTask {
	

	/**
	 * 任务介绍
	 */
	String desc();
	
	
	/**
	 * 返回任务名称
	 * @return
	 */
	String name();

	/**
	 * 历年资源定时执行方法
	 * @param schoolYear 当前学年
	 */
	void execute(Integer schoolYear);
}
