/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.QueryObject;

 /**
 * 权限表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RolePermission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = RolePermission.TABLE_NAME)
public class RolePermission extends QueryObject {
	public static final String TABLE_NAME="sys_role_permission";
	
	@Id
	@Column(name="id")
	private Integer id;

	@Column(name="role_id")
	private Integer roleId;

	@Column(name="permission_id")
	private Integer permissionId;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}

	public Integer getRoleId(){
		return this.roleId;
	}

	public void setPermissionId(Integer permissionId){
		this.permissionId = permissionId;
	}

	public Integer getPermissionId(){
		return this.permissionId;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof RolePermission))
				return false;
			RolePermission castOther = (RolePermission) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


