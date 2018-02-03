/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.schconfig.clss.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 学校班级 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchClass.TABLE_NAME)
public class SchClass extends BaseObject {
	public static final String TABLE_NAME="jy_class";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *名称
	 **/
	@Column(name="name",length=32)
	private String name;

	/**
	 *年级id
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *学校id
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *班级编号
	 **/
	@Column(name="code",length=32)
	private String code;

	/**
	 *排序
	 **/
	@Column(name="sort")
	private Integer sort;
	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchClass))
				return false;
			SchClass castOther = (SchClass) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


