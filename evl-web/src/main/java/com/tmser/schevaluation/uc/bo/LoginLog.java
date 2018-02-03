/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.bo;

import java.util.Date;

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
 * 登录日志 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LoginLog.java, v 1.0 2015-05-13 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LoginLog.TABLE_NAME)
public class LoginLog extends QueryObject {
	public static final String TABLE_NAME="sys_login_log";
	
	/**
	 *  登录
	 */
	public static final int T_LOGIN = 0;
	
	/**
	 * 切换
	 */
	public static final int T_CHANGE = 1;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="space_id")
	private Integer spaceId;

	@Column(name="ip",length=32)
	private String ip;

	@Column(name="grade_id")
	private Integer gradeId;

	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *系统角色id
	 **/
	@Column(name="sys_role_id")
	private Integer sysRoleId;

	/**
	 *登录时间或切换角色时间
	 **/
	@Column(name="login_time")
	private Date loginTime;

	/**
	 *登录类型。 0 ：正常登录 ， 1  切换身份
	 **/
	@Column(name="type")
	private Integer type;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setSysRoleId(Integer sysRoleId){
		this.sysRoleId = sysRoleId;
	}

	public Integer getSysRoleId(){
		return this.sysRoleId;
	}

	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}

	public Date getLoginTime(){
		return this.loginTime;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof LoginLog))
				return false;
			LoginLog castOther = (LoginLog) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


