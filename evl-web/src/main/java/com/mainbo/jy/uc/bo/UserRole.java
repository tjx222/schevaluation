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
 * 用户角色关系 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserRole.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = UserRole.TABLE_NAME)
public class UserRole extends QueryObject {
	public static final String TABLE_NAME="sys_user_role";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="role_id")
	private Integer roleId;

	@Column(name="user_id")
	private Integer userId;

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

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof UserRole))
				return false;
			UserRole castOther = (UserRole) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


