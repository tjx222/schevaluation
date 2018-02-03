/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;

/**
 * <pre>
 *	班级升级数据模型
 * </pre>
 *
 * @author yangchao
 * @version $Id: UpdateClassVo.java, v 1.0 2016年8月1日 上午11:44:27 yangchao Exp $
 */
@SuppressWarnings("serial")
public class UpdateClassVo implements Serializable{
	private Integer oldClassId;
	private String oldClassName;
	private Integer newClassId;
	private String 	newClassName;
	
	public Integer getOldClassId() {
		return oldClassId;
	}
	public void setOldClassId(Integer oldClassId) {
		this.oldClassId = oldClassId;
	}
	public String getOldClassName() {
		return oldClassName;
	}
	public void setOldClassName(String oldClassName) {
		this.oldClassName = oldClassName;
	}
	public Integer getNewClassId() {
		return newClassId;
	}
	public void setNewClassId(Integer newClassId) {
		this.newClassId = newClassId;
	}
	public String getNewClassName() {
		return newClassName;
	}
	public void setNewClassName(String newClassName) {
		this.newClassName = newClassName;
	}
}
