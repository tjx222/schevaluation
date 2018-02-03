/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 班级表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlClass.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlClass.TABLE_NAME)
public class EvlClass extends BaseObject {
	public static final String TABLE_NAME="evl_class";
	
		@Id
	@Column(name="id")
	private Integer id;

	/**
	 *问卷表id
	 **/
	@Column(name="questionnaires_id")
	private Integer questionnairesId;

	/**
	 *年级id
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *学科id
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *班级id
	 **/
	@Column(name="class_id")
	private Integer classId;

	@Column(name="org_id")
	private Integer orgId;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setQuestionnairesId(Integer questionnairesId){
		this.questionnairesId = questionnairesId;
	}

	public Integer getQuestionnairesId(){
		return this.questionnairesId;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setClassId(Integer classId){
		this.classId = classId;
	}

	public Integer getClassId(){
		return this.classId;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlClass))
				return false;
			EvlClass castOther = (EvlClass) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


