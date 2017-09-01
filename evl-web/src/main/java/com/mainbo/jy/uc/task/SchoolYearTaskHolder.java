/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mainbo.jy.uc.service.SchoolYearService;

/**
 * <pre>
 *  学年切换任务管理器
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearTask.java, v 1.0 2016年8月18日 上午10:46:46 tmser Exp $
 */
public class SchoolYearTaskHolder {
	
	private static final Logger logger = LoggerFactory.getLogger(SchoolYearTaskHolder.class);
	
	private List<SchoolYearTask> tasks;
	
	@Autowired
	private SchoolYearService schoolYearService;
	
	/** 
	 * Getter method for property <tt>schoolYearService</tt>. 
	 * @return property value of schoolYearService 
	 */
	public SchoolYearService getSchoolYearService() {
		return schoolYearService;
	}



	/**
	 * Setter method for property <tt>schoolYearService</tt>.
	 * @param schoolYearService value to be assigned to property schoolYearService
	 */
	public void setSchoolYearService(SchoolYearService schoolYearService) {
		this.schoolYearService = schoolYearService;
	}



	/** 
	 * Getter method for property <tt>tasks</tt>. 
	 * @return property value of tasks 
	 */
	public List<SchoolYearTask> getTasks() {
		return tasks;
	}



	/**
	 * Setter method for property <tt>tasks</tt>.
	 * @param tasks value to be assigned to property tasks
	 */
	public void setTasks(List<SchoolYearTask> tasks) {
		this.tasks = tasks;
	}

	/**
	 * 历年资源定时执行方法
	 */
	public void execute(){
		logger.info("# start execute schoolyear update task!");
		for(SchoolYearTask task : tasks){
			logger.info("# start execute task {} !",task.name());
			task.execute(schoolYearService.getCurrentSchoolYear());
			logger.info("# end execute task {} !",task.name());
		}
		logger.info("# end execute schoolyear update task!");
	}



}
