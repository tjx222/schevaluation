/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 用户管理范围表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: UserManagescope.java, v 1.0 2015-10-14 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = UserManagescope.TABLE_NAME)
public class UserManagescope extends BaseObject {
	public static final String TABLE_NAME="sys_user_managescope";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	/**
	 *角色id
	 **/
	@Column(name="role_id")
	private Integer roleId;

	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 * 机构名称
	 */
	@Column(name="org_name")
	private String orgName;
	
	/**
	 * 区域id
	 */
	@Column(name="area_id")
	private Integer areaId;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}

	public Integer getRoleId(){
		return this.roleId;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof UserManagescope))
				return false;
			UserManagescope castOther = (UserManagescope) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


