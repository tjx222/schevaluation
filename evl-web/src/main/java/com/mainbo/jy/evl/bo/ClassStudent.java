/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.bo;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 学生表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ClassStudent.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ClassStudent.TABLE_NAME)
public class ClassStudent extends BaseObject {
	public static final String TABLE_NAME="evl_class_student";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="student_id")
	private String studentId;

	@Column(name="org")
	private Integer orgId;

	/**
	 *年级id
	 **/
	@Column(name="grade_id")
	private Integer gradeId;
	

	/**
	 *班级id
	 **
	 */
	@Column(name="class_id")
	private Integer classId;
	
	//复合查询条件
	private List<Integer> classIds;
	private List<Integer> gradeIds;

	/**
	 *班级名称
	 **/
	@Column(name="class_name")
	private String className;

	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;
	@Transient
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setClassId(Integer classId){
		this.classId = classId;
	}

	public Integer getClassId(){
		return this.classId;
	}

	public void setClassName(String className){
		this.className = className;
	}

	public String getClassName(){
		return this.className;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public List<Integer> getClassIds() {
		return classIds;
	}

	public void setClassIds(List<Integer> classIds) {
		this.classIds = classIds;
	}

	public List<Integer> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Integer> gradeIds) {
		this.gradeIds = gradeIds;
	}
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof ClassStudent))
				return false;
			ClassStudent castOther = (ClassStudent) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


