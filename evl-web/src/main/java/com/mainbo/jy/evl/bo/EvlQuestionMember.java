/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;
import com.mainbo.jy.evl.statics.EvlMemberStatus;

 /**
 * 问卷用户表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionMember.java, v 1.0 2016-05-24 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlQuestionMember.TABLE_NAME)
public class EvlQuestionMember extends QueryObject {
	public static final String TABLE_NAME="evl_question_member";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *学号
	 **/
	@Column(name="code",length=32)
	private String code;


	/**
	 *姓名
	 **/
	@Column(name="name",length=32)
	private String name;


	/**
	 *班级id
	 **/
	@Column(name="class_id")
	private Integer classId;

	/**
	 *年级id
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	@Column(name="url",length=256)
	private String url;

	/**
	 * 用户状态
	 **/
	@Column(name="status")
	private Integer status;

	/**
	 *问卷id
	 **/
	@Column(name="question_id")
	private Integer questionId;

	/**
	 *学科
	 **/
	@Column(name="subject_id")
	private Integer subjectId;
	/**
	 *机构
	 **/
	@Column(name="org_id")
	private Integer orgId;
	
	@Transient
	private String gradeName;
	@Transient
	private String className;
	@Transient
	private String sexName;
	@Transient
	private String statusStr;
	@Transient
	private String cellphone;;
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlQuestionMember))
				return false;
			EvlQuestionMember castOther = (EvlQuestionMember) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}

	public String getStatusStr() {
		if(status<EvlMemberStatus.tongzhichenggong.getValue()){
			return "未成功";
		}else{
			return "已成功";
		}
	}
}


