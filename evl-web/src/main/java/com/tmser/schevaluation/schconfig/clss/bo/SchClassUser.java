/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.schconfig.clss.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 学校班级用户关联 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchClassUser.TABLE_NAME)
public class SchClassUser extends BaseObject {
	public static final String TABLE_NAME="jy_class_user";
	
	public static final Integer T_TEACHER = 0;
	
	public static final Integer T_MASTER = 1;
	
	public static final Integer T_STUDENT = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *班级id
	 **/
	@Column(name="class_id")
	private Integer classId;

	/**
	 *用户姓名
	 **/
	@Column(name="username",length=32)
	private String username;

	/**
	 *用户id
	 **/
	@Column(name="tch_id")
	private Integer tchId;
	
	/**
	 *学科id
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 *0  普通教师， 1 班主任，2 学生
	 **/
	@Column(name="type")
	private Integer type;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setClassId(Integer classId){
		this.classId = classId;
	}

	public Integer getClassId(){
		return this.classId;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setTchId(Integer tchId){
		this.tchId = tchId;
	}

	public Integer getTchId(){
		return this.tchId;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchClassUser))
				return false;
			SchClassUser castOther = (SchClassUser) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


