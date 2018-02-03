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
 * 角色类型管理 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RoleType.java, v 1.0 2015-09-01 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = RoleType.TABLE_NAME)
public class RoleType extends BaseObject {
	public static final String TABLE_NAME="sys_role_type";
	
	public static final Integer APPLICATION_AREA=1;
	public static final Integer APPLICATION_SCHOOL=2;
	public static final Integer APPLICATION_SYSTEM=3;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="code")
	private Integer code;

	@Column(name="role_type_name",length=30)
	private String roleTypeName;

	@Column(name="role_type_desc",length=200)
	private String roleTypeDesc;

	/**
	 *排序
	 **/
	@Column(name="sort")
	private Integer sort;

	/**
	 *角色类型--功能权限
	 **/
	@Column(name="role_type_perm",length=250)
	private String roleTypePerm;

	/**
	 *应用方向。1区域、2学校、3系统
	 **/
	@Column(name="use_position")
	private Integer usePosition;

	/**
	 *1显示，0不显示
	 **/
	@Column(name="is_no_bm_manager")
	private Boolean isNoBmManager;

	/**
	 *学段显示，1显示，0不显示
	 **/
	@Column(name="is_no_xz")
	private Boolean isNoXz;

	/**
	 *是否需要设置学科属性。1显示，0不显示
	 **/
	@Column(name="is_no_xk")
	private Boolean isNoXk;

	/**
	 *是否需要设置年级属性。1显示，0不显示
	 **/
	@Column(name="is_no_nj")
	private Boolean isNoNj;
  
	
	/**
	 *角色类型--功能权限
	 **/
	@Column(name="home_url",length=255)
	private String homeUrl;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setRoleTypeName(String roleTypeName){
		this.roleTypeName = roleTypeName;
	}

	public String getRoleTypeName(){
		return this.roleTypeName;
	}

	public void setRoleTypeDesc(String roleTypeDesc){
		this.roleTypeDesc = roleTypeDesc;
	}

	public String getRoleTypeDesc(){
		return this.roleTypeDesc;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setRoleTypePerm(String roleTypePerm){
		this.roleTypePerm = roleTypePerm;
	}

	public String getRoleTypePerm(){
		return this.roleTypePerm;
	}

	public void setUsePosition(Integer usePosition){
		this.usePosition = usePosition;
	}

	public Integer getUsePosition(){
		return this.usePosition;
	}

	public void setIsNoBmManager(Boolean isNoBmManager){
		this.isNoBmManager = isNoBmManager;
	}

	public Boolean getIsNoBmManager(){
		return this.isNoBmManager;
	}

	public void setIsNoXz(Boolean isNoXz){
		this.isNoXz = isNoXz;
	}

	public Boolean getIsNoXz(){
		return this.isNoXz;
	}

	public void setIsNoXk(Boolean isNoXk){
		this.isNoXk = isNoXk;
	}

	public Boolean getIsNoXk(){
		return this.isNoXk;
	}

	public void setIsNoNj(Boolean isNoNj){
		this.isNoNj = isNoNj;
	}

	public Boolean getIsNoNj(){
		return this.isNoNj;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/** 
	 * Getter method for property <tt>homeUrl</tt>. 
	 * @return property value of homeUrl 
	 */
	public String getHomeUrl() {
		return homeUrl;
	}

	/**
	 * Setter method for property <tt>homeUrl</tt>.
	 * @param homeUrl value to be assigned to property homeUrl
	 */
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof RoleType))
			return false;
		RoleType castOther = (RoleType) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}


