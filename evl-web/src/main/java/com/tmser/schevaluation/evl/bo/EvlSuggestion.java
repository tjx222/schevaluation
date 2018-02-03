/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.QueryObject;

/**
 * 建议表 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlSuggestion.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlSuggestion.TABLE_NAME)
public class EvlSuggestion extends QueryObject {
	public static final String TABLE_NAME = "evl_suggestion";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_type")
	private Integer userType;

	@Column(name = "subje_id")
	private Integer subjeId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "code", length = 30)
	private String code;

	@Column(name = "result_level")
	private Integer resultLevel;

	@Column(name = "suggest_id", length = 30)
	private String suggestId;

	@Column(name = "suggest_name", length = 30)
	private String suggestName;

	@Column(name = "standby1", length = 30)
	private String standby1;

	@Column(name = "standby2")
	private Integer standby2;

	@Column(name = "org_id")
	private Integer orgId;

	@Column(name = "class_id")
	private Integer classId;

	@Column(name = "content", length = 21845)
	private String content;

	@Column(name = "questionnaires_id")
	private Integer questionnairesId;

	@Column(name = "grade_id")
	private Integer gradeId;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setSubjeId(Integer subjeId) {
		this.subjeId = subjeId;
	}

	public Integer getSubjeId() {
		return this.subjeId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setResultLevel(Integer resultLevel) {
		this.resultLevel = resultLevel;
	}

	public Integer getResultLevel() {
		return this.resultLevel;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}

	public String getSuggestId() {
		return this.suggestId;
	}

	public void setSuggestName(String suggestName) {
		this.suggestName = suggestName;
	}

	public String getSuggestName() {
		return this.suggestName;
	}

	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}

	public String getStandby1() {
		return this.standby1;
	}

	public void setStandby2(Integer standby2) {
		this.standby2 = standby2;
	}

	public Integer getStandby2() {
		return this.standby2;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}

	public Integer getQuestionnairesId() {
		return this.questionnairesId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof EvlSuggestion))
			return false;
		EvlSuggestion castOther = (EvlSuggestion) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
