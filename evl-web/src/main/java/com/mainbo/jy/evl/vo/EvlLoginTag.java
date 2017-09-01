/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.vo;

import java.io.Serializable;

/**
 * <pre>
 *	问卷登陆信息
 * </pre>
 *
 * @author yc
 * @version $Id: EvlLoginTag.java, v 1.0 2016年5月20日 下午2:39:27 dell Exp $
 */
@SuppressWarnings("serial")
public class EvlLoginTag implements Serializable{
	Integer questionnairesId;
	Integer studentId;
	String studentCode;
	Integer orgId;
	Integer gradeId;
	public Integer getQuestionnairesId() {
		return questionnairesId;
	}
	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
}
