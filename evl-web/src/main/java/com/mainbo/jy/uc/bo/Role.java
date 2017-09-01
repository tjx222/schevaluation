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

import com.mainbo.jy.common.bo.QueryObject;

 /**
 * 角色 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends QueryObject {
	public static final String TABLE_NAME="sys_role";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="sys_role_id")
	private Integer sysRoleId;

	/**
	 *角色名称
	 **/
	@Column(name="role_name",length=64)
	private String roleName;

	/**
	 *权限代码
	 **/
	@Column(name="role_code",length=10)
	private String roleCode;

	/**
	 *组织机构
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *描述
	 **/
	@Column(name="remark",length=128)
	private String remark;

	@Column(name="role_desc",length=155)
	private String roleDesc;

	/**
	 *方案id
	 **/
	@Column(name="solution_id")
	private Integer solutionId;

	/**
	 *状态 0正常，1删除
	 **/
	@Column(name="is_del")
	private Boolean isDel;

	/**
	 *关联id 系统--方案
	 **/
	@Column(name="rel_id")
	private Integer relId;

	/**
	 *应用方向-角色类型--冗余
	 **/
	@Column(name="use_position")
	private Integer usePosition;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setSysRoleId(Integer sysRoleId){
		this.sysRoleId = sysRoleId;
	}

	public Integer getSysRoleId(){
		return this.sysRoleId;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	public String getRoleCode(){
		return this.roleCode;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRoleDesc(String roleDesc){
		this.roleDesc = roleDesc;
	}

	public String getRoleDesc(){
		return this.roleDesc;
	}

	public void setSolutionId(Integer solutionId){
		this.solutionId = solutionId;
	}

	public Integer getSolutionId(){
		return this.solutionId;
	}

	public void setIsDel(Boolean isDel){
		this.isDel = isDel;
	}

	public Boolean getIsDel(){
		return this.isDel;
	}

	public void setRelId(Integer relId){
		this.relId = relId;
	}

	public Integer getRelId(){
		return this.relId;
	}

	public void setUsePosition(Integer usePosition){
		this.usePosition = usePosition;
	}

	public Integer getUsePosition(){
		return this.usePosition;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Role))
				return false;
			Role castOther = (Role) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


