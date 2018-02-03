/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.task;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ClearTmptResource.java, v 1.0 2015年11月11日 下午2:58:37 tmser Exp $
 */
public interface Task {
	
	/**
	 * 任务编码
	 * @return
	 */
	String code();
	
	/**
	 * 任务介绍
	 */
	String desc();
	
	/**
	 * 执行计划
	 */
	String cron();
	
	/**
	 * 清除无效的资源
	 */
	void execute();
}
