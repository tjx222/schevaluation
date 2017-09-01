/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;

 /**
 * 学生账号表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlStudentAccount.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlStudentAccount.TABLE_NAME)
public class EvlStudentAccount extends QueryObject {
	public static final String TABLE_NAME="evl_student_account";
	
	@Id
	@Column(name="id")
	private String id;

	@Column(name="code",length=32)
	private String code;

	@Column(name="name",length=32)
	private String name;

	@Column(name="sex")
	private Integer sex;

	@Column(name="cellphone",length=32)
	private String cellphone;

	@Column(name="isdelete")
	private Integer isdelete;

	@Column(name="org_id")
	private Integer orgId;

	@Transient
	private String gradeName;//年级名
	@Transient
	private String className;//班级名
	@Transient
	private String sexName;//性别
	@Transient
	private Integer status;//状态
	/**
	 * 学生的班级信息
	 */
	private ClassStudent classStudent;
	
	public ClassStudent getClassStudent() {
		return classStudent;
	}

	public void setClassStudent(ClassStudent classStudent) {
		this.classStudent = classStudent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setCellphone(String cellphone){
		this.cellphone = cellphone;
	}

	public String getCellphone(){
		return this.cellphone;
	}

	public void setIsdelete(Integer isdelete){
		this.isdelete = isdelete;
	}

	public Integer getIsdelete(){
		return this.isdelete;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlStudentAccount))
				return false;
			EvlStudentAccount castOther = (EvlStudentAccount) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}

	/**
	 * @return
	 */
	public Integer getGradeId() {
		// TODO Auto-generated method stub
		if(classStudent!=null){
			return classStudent.getGradeId();
		}
		return null;
	}

	/**
	 * @return
	 */
	public Integer getClassId() {
		// TODO Auto-generated method stub
		if(classStudent!=null){
			return classStudent.getClassId();
		}
		return null;
	}

	/**
	 * @return
	 */
	public Integer getSchoolYear() {
		// TODO Auto-generated method stub
		if(classStudent!=null){
			return classStudent.getSchoolYear();
		}
		return null;
	}
}


