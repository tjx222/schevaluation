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

import com.tmser.schevaluation.common.bo.QueryObject;

 /**
 * 用户空间信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = UserSpace.TABLE_NAME)
public class UserSpace extends QueryObject {
	public static final String TABLE_NAME="sys_user_space";
	
	public static final int ENABLE = 1;
	
	public static final int DISABLE = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="user_name")
	private String username;
	
	/**
	 * 有效性  1正常 0 禁用
	 */
	@Column(name="enable")
	private Integer enable;

	/**
	 *所教书籍
	 **/
	@Column(name="book_id")
	private String bookId;

	@Column(name="space_home_url",length=64)
	private String spaceHomeUrl;

	/**
	 *所属机构
	 **/
	@Column(name="org_id")
	private Integer orgId;

	@Column(name="phase_id")
	private Integer phaseId;

	@Column(name="phase_type")
	private Integer phaseType;
	
	@Column(name="grade_id")
	private Integer gradeId;

	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 * 系统角色ID
	 */
	@Column(name="sys_role_id")
	private Integer sysRoleId;
	
	/**
	 * 角色ID
	 */
	@Column(name="role_id")
	private Integer roleId;

	@Column(name="space_name",length=32)
	private String spaceName;
	
	/**
	 * 学科排序
	 */
	@Column(name="subject_order")
	private Integer subjectOrder;

	/**
	 *数字小优先
	 **/
	@Column(name="sort")
	private Integer sort;

	/**
	 *0否   1 是
	 **/
	@Column(name="is_default")
	private Boolean isDefault;
	
	/**
	 * 学年
	 */
	@Column(name="school_year")
	private Integer schoolYear;
	
	/**
	 * 所属部门ID
	 */
	@Column(name="department_id")
	private Integer departmentId;
	
	/**
	 * 所管辖的部门IDs
	 */
	@Column(name="con_dep_ids")
	private String conDepIds;
	

	/** 
	 * Getter method for property <tt>departmentId</tt>. 
	 * @return property value of departmentId 
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}

	/**
	 * Setter method for property <tt>departmentId</tt>.
	 * @param departmentId value to be assigned to property departmentId
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/** 
	 * Getter method for property <tt>conDepIds</tt>. 
	 * @return property value of conDepIds 
	 */
	public String getConDepIds() {
		return conDepIds;
	}

	/**
	 * Setter method for property <tt>conDepIds</tt>.
	 * @param conDepIds value to be assigned to property conDepIds
	 */
	public void setConDepIds(String conDepIds) {
		this.conDepIds = conDepIds;
	}

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

	public void setBookId(String bookId){
		this.bookId = bookId;
	}

	public String getBookId(){
		return this.bookId;
	}

	public void setSpaceHomeUrl(String spaceHomeUrl){
		this.spaceHomeUrl = spaceHomeUrl;
	}

	public String getSpaceHomeUrl(){
		return this.spaceHomeUrl;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setPhaseId(Integer phaseId){
		this.phaseId = phaseId;
	}

	public Integer getPhaseId(){
		return this.phaseId;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public Integer getPhaseType() {
		return phaseType;
	}

	public void setPhaseType(Integer phaseType) {
		this.phaseType = phaseType;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public Integer getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public void setSpaceName(String spaceName){
		this.spaceName = spaceName;
	}

	public String getSpaceName(){
		return this.spaceName;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setIsDefault(Boolean isDefault){
		this.isDefault = isDefault;
	}

	public Boolean getIsDefault(){
		return this.isDefault;
	}
	
	/**
	 * 有效性
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * 有效性
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * 年级，学科，学年，用户id 相等则相同
	 * @param other
	 * @return
	 * @see com.tmser.schevaluation.common.bo.QueryObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof UserSpace))
				return false;
			UserSpace castOther = (UserSpace) other;
			return new EqualsBuilder().append(userId, castOther.userId)
					.append(gradeId, castOther.gradeId)
					.append(subjectId, castOther.subjectId).append(schoolYear, castOther.schoolYear)
					.isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(userId)
					.append(gradeId).append(subjectId).append(schoolYear).toHashCode();
	}

	public Integer getSubjectOrder() {
		return subjectOrder;
	}

	public void setSubjectOrder(Integer subjectOrder) {
		this.subjectOrder = subjectOrder;
	}

	/** 
	 * Getter method for property <tt>roleId</tt>. 
	 * @return property value of roleId 
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Setter method for property <tt>roleId</tt>.
	 * @param roleId value to be assigned to property roleId
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}


