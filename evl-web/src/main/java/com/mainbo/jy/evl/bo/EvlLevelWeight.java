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
 * 等级权重表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlLevelWeight.java, v 1.0 2016-05-11 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlLevelWeight.TABLE_NAME)
public class EvlLevelWeight extends BaseObject {
	public static final String TABLE_NAME="evl_level_weight";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *等级名称（A,B,C,D）
	 **/
	@Column(name="level_name",length=12)
	private String levelName;

	/**
	 *等级权重
	 **/
	@Column(name="level_weight")
	private Double levelWeight;

	/**
	 *问卷表id
	 **/
	@Column(name="questionnaires_id")
	private Integer questionnairesId;

	@Column(name="org_id")
	private Integer orgId;

	@Column(name="last_id")
	private Integer lastId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="last_dttm")
	private Date lastDttm;

	/**
	 *备用1
	 **/
	@Column(name="back1",length=11)
	private String back1;

	/**
	 *备用2
	 **/
	@Column(name="back2",length=11)
	private String back2;
	@Transient
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public String getLevelName(){
		return this.levelName;
	}

	public void setLevelWeight(Double levelWeight){
		this.levelWeight = levelWeight;
	}

	public Double getLevelWeight(){
		return this.levelWeight;
	}
	public Integer getLevelWeightInt(){
		Integer weight = 0;
		if(this.levelWeight!=null){
			weight = this.levelWeight.intValue();
		}
		return weight;
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

	public void setBack1(String back1){
		this.back1 = back1;
	}

	public String getBack1(){
		return this.back1;
	}

	public void setBack2(String back2){
		this.back2 = back2;
	}

	public String getBack2(){
		return this.back2;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlLevelWeight))
				return false;
			EvlLevelWeight castOther = (EvlLevelWeight) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


