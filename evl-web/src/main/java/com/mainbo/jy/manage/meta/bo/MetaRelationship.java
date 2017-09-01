/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;

/**
 * 元数据关系表 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = MetaRelationship.TABLE_NAME)
public class MetaRelationship extends QueryObject {
	
	
	
	public static final String TABLE_NAME = "sys_meta_relationship";
	
	public static final String SPLIT_CHAR = ",";

	/**
	 * 学段-年级
	 */
	public static final int T_XD_NJ = 0;
	
	public static final int T_ORG_TYPE = 1;

	/**
	 * 学段 -- 学科
	 */
	public static final int T_XD_XK = 2;

	/**
	 * 学段类型
	 */
	public static final int T_XD = 3;
	
	
	public static final String ORG = "org";
	
	public static final String AREA = "area";
	
	public static final String SYS = "sys";
	
	@Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 学制名称
	 **/
	@Column(name = "name",length=255)
	private String name;

	/**
	 * 关联表字段id列表，使用‘，’ 分割
	 **/
	@Column(name = "ids",length=10000)
	private String ids;

	/**
	 * 类型， 0 ：学段(eid)-->年级(ids) 1：学校类型(id)-->学段(ids) 2：学段(eid)-->学科(ids)
	 * 3：学段类型(id)-->学段(eid) 4：学制类型(id) 5：学段、学制-年级关联(eid、sort)-->年级(ids)
	 * 
	 **/
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "descs",length=10000)
	private String descs;
	
	@Column(name = "sort")
	private Integer sort;

	//平台定制新增字段
	@Column(name="org_id",length=11)
	private Integer orgId;
	
	@Column(name="area_id",length=11)
	private Integer areaId;
	/**
	 * 范围  sys,area,org
	 */
	@Column(name="scope",length=11)
	private String scope;
	
	@Column(name="enable",length=11)
	private Integer enable;
	
	@Column(name="exclude_ids")
	private String excludeIds;
	@Column(name="exclude_names")
	private String excludeNames;
	
	public String getExcludeNames() {
		return excludeNames;
	}

	public void setExcludeNames(String excludeNames) {
		if(excludeNames == null){
			excludeNames = "";
		}
		this.excludeNames = excludeNames;
	}

	public String getExcludeIds() {
		if(excludeIds==null){
			excludeIds = "";
		}
		return excludeIds;
	}

	public void setExcludeIds(String excludeIds) {
		this.excludeIds = excludeIds;
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 基础元素id
	 **/
	@Column(name = "eid")
	private Integer eid;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public <T> void setIds(List<T> idSet) {
		this.ids = StringUtils.join(idSet, SPLIT_CHAR);
	}

	public String getIds() {
		return this.ids;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getEid() {
		return this.eid;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof MetaRelationship))
			return false;
		MetaRelationship castOther = (MetaRelationship) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
