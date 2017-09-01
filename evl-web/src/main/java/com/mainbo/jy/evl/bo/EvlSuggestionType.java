/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.bo;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 建议类型表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlSuggestionType.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlSuggestionType.TABLE_NAME)
public class EvlSuggestionType extends BaseObject {
	public static final String TABLE_NAME="evl_suggestion_type";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name",length=256)
	private String name;

	@Column(name="questionnaires_id")
	private Integer questionnairesId;

	@Column(name="org_id")
	private Integer orgId;

	@Column(name="last_id")
	private Integer lastId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="last_dttm")
	private Date lastDttm;


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

	public void setQuestionnairesId(Integer questionnairesId){
		this.questionnairesId = questionnairesId;
	}

	public Integer getQuestionnairesId(){
		return this.questionnairesId;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setLastId(Integer lastId){
		this.lastId = lastId;
	}

	public Integer getLastId(){
		return this.lastId;
	}

	public void setLastDttm(Date lastDttm){
		this.lastDttm = lastDttm;
	}

	public Date getLastDttm(){
		return this.lastDttm;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlSuggestionType))
				return false;
			EvlSuggestionType castOther = (EvlSuggestionType) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


