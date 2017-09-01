/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.bo;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 指标表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlIndicator.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlIndicator.TABLE_NAME)
public class EvlIndicator extends BaseObject implements Cloneable {
	public static final String TABLE_NAME="evl_indicator";
	/**
	 * 当前指标等级
	 */
	public static final int level0=0;
	public static final int level1=1;
	public static final int level2=2;
	@Id
	@Column(name="id",length=32)
	private String id;

	/**
	 *问卷表id
	 **/
	@Column(name="questionnaires_id")
	private Integer questionnairesId;

	/**
	 *指标名称
	 **/
	@Column(name="title",length=21845)
	private String title;

	/**
	 *指标等级
	 **/
	@Column(name="level")
	private Integer level;

	/**
	 *指标总分
	 **/
	@Column(name="score_total")
	private Double scoreTotal;

	/**
	 *从属指标ID
	 **/
	@Column(name="parent_id",length=32)
	private String parentId;

	@Column(name="back1",length=32)
	private String back1;

	@Column(name="back2",length=32)
	private String back2;
	/**
	 * 指标对应结果集合
	 */
	@Transient
	private List<EvlOperateResult> resultList;

	public List<EvlOperateResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<EvlOperateResult> resultList) {
		this.resultList = resultList;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setQuestionnairesId(Integer questionnairesId){
		this.questionnairesId = questionnairesId;
	}

	public Integer getQuestionnairesId(){
		return this.questionnairesId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

	public void setScoreTotal(Double scoreTotal){
		this.scoreTotal = scoreTotal;
	}

	public Double getScoreTotal(){
		if(this.scoreTotal==null){
			this.scoreTotal=0.0;
		}
		return this.scoreTotal;
	}
	public Integer getScoreTotalInt(){
		Integer score = 0;
		if(this.scoreTotal!=null){
			return this.scoreTotal.intValue();
		}
		return score;
	}
	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
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
			if (!(other instanceof EvlIndicator))
				return false;
			EvlIndicator castOther = (EvlIndicator) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
	@Override
	public EvlIndicator clone() {
		EvlIndicator evlIndicator = new EvlIndicator();
		try {
			evlIndicator = (EvlIndicator) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return evlIndicator;
	}
}


