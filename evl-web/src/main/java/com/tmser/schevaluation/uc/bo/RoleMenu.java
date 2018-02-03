/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 角色menu关联 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RoleMenu.java, v 1.0 2015-09-07 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = RoleMenu.TABLE_NAME)
public class RoleMenu extends BaseObject {
	public static final String TABLE_NAME="sys_role_menu";
	
	public static final int NORMAL = 0;
	
	public static final int DELETE = 1;
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="menu_id")
	private Integer menuId;

	@Column(name="role_id")
	private Integer roleId;

	/**
	 *功能权限别名
	 **/
	@Column(name="perm_bname",length=65)
	private String permBname;

	/**
	 *状态：0正常，1禁用
	 **/
	@Column(name="is_del")
	private Integer isDel;

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setMenuId(Integer menuId){
		this.menuId = menuId;
	}

	public Integer getMenuId(){
		return this.menuId;
	}

	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}

	public Integer getRoleId(){
		return this.roleId;
	}

	public void setPermBname(String permBname){
		this.permBname = permBname;
	}

	public String getPermBname(){
		return this.permBname;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof RoleMenu))
				return false;
			RoleMenu castOther = (RoleMenu) other;
			return new EqualsBuilder().append(menuId, castOther.menuId)
					.append(roleId, castOther.roleId).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(menuId).append(roleId).toHashCode();
	}
}


