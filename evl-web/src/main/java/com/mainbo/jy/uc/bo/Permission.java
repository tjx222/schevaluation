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
 * 权限表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Permission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Permission.TABLE_NAME)
public class Permission extends QueryObject {
	public static final String TABLE_NAME="sys_permission";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name",length=32)
	private String name;

	/**
	 *操作码，以' : ’ 分割的权限码
	 **/
	@Column(name="code",length=32)
	private String code;

	@Column(name="remark",length=128)
	private String remark;

	@Column(name="sys_role_id")
	private Integer sysRoleId;


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

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setSysRoleId(Integer sysRoleId){
		this.sysRoleId = sysRoleId;
	}

	public Integer getSysRoleId(){
		return this.sysRoleId;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Permission))
				return false;
			Permission castOther = (Permission) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


