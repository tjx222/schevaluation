/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;
import com.mainbo.jy.manage.meta.Meta;

 /**
 * 基础元数据 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SysDic.java, v 1.0 2016-01-14 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SysDic.TABLE_NAME)
public class SysDic extends QueryObject implements Meta {
	public static final String TABLE_NAME="sys_dic";
	
	@Id
	@Column(name="dic_id")
	private Integer id;

	@Column(name="dic_name",length=64)
	private String name;

	@Column(name="parent_id")
	private Integer parentId;

	@Column(name="dic_level")
	private Integer dicLevel;

	@Column(name="dic_orderby")
	private Integer dicOrderby;

	@Column(name="dic_status",length=32)
	private String dicStatus;

	@Column(name="operator",length=32)
	private String operator;

	@Column(name="cascade_dic_ids",length=64)
	private String cascadeDicIds;

	@Column(name="child_count")
	private Integer childCount;

	@Column(name="standard_code",length=8)
	private String standardCode;
	
	//平台定制新增字段
	@Column(name="org_id",length=11)
	private Integer orgId;//机构id
	
	@Column(name="area_id",length=11)
	private Integer areaId;//区域id
	
	@Column(name="enable",length=11)
	private Integer enable;
	
	@Column(name="type",length=11)
	private String type;
	
	
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public void setId(Integer id){
		this.id = id;
	}

	@Override
	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	@Override
	public Integer getParentId(){
		return this.parentId;
	}

	public void setDicLevel(Integer dicLevel){
		this.dicLevel = dicLevel;
	}

	public Integer getDicLevel(){
		return this.dicLevel;
	}

	public void setDicOrderby(Integer dicOrderby){
		this.dicOrderby = dicOrderby;
	}

	@Override
	public Integer getDicOrderby(){
		return this.dicOrderby;
	}

	public void setDicStatus(String dicStatus){
		this.dicStatus = dicStatus;
	}

	public String getDicStatus(){
		return this.dicStatus;
	}

	public void setOperator(String operator){
		this.operator = operator;
	}

	public String getOperator(){
		return this.operator;
	}

	public void setCascadeDicIds(String cascadeDicIds){
		this.cascadeDicIds = cascadeDicIds;
	}

	public String getCascadeDicIds(){
		return this.cascadeDicIds;
	}

	public void setChildCount(Integer childCount){
		this.childCount = childCount;
	}

	@Override
	public Integer getChildCount(){
		return this.childCount;
	}

	public void setStandardCode(String standardCode){
		this.standardCode = standardCode;
	}

	public String getStandardCode(){
		return this.standardCode;
	}

	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SysDic))
				return false;
			SysDic castOther = (SysDic) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


