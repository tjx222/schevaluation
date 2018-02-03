/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.task.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.schevaluation.manage.config.ConfigUtils;
import com.tmser.schevaluation.task.Task;
import com.tmser.schevaluation.uc.service.SchoolYearService;

/**
 * <pre>
 *  学年切换任务管理器
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearTask.java, v 1.0 2016年8月18日 上午10:46:46 tmser Exp $
 */
public class SchoolYearTaskHolder implements Task{
	
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
	@Override
	public void execute(){
		logger.info("# start execute schoolyear update task!");
		for(SchoolYearTask task : tasks){
			logger.debug("# start execute task {} !",task.name());
			task.execute(schoolYearService.getCurrentSchoolYear());
			logger.debug("# end execute task {} !",task.name());
		}
		logger.info("# end execute schoolyear update task!");
	}



	/**
	 * @return
	 * @see com.tmser.schevaluation.back.task.Task#desc()
	 */
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		for(SchoolYearTask task : tasks){
			sb.append(task.name())
			.append(":<br/>   ")
			.append(task.desc()).append("<br/>")
			;
		}
		return sb.toString();
	}



	/**
	 * @return
	 * @see com.tmser.schevaluation.back.task.Task#code()
	 */
	@Override
	public String code() {
		return this.getClass().getName();
	}



	/**
	 * @return
	 * @see com.tmser.schevaluation.back.task.Task#cron()
	 */
	@Override
	
	public String cron() {
		return ConfigUtils.readConfig("history_split_cron","0 0 0 15 7 ?");
	}

}
