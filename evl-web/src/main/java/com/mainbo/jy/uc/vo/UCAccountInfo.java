/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.vo;

import java.util.List;

import com.mainbo.jy.uc.bo.UserSpace;

/**
 * <pre>
 * 用户同步VO
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: UserSpaceAdd.java, v 1.0 2015年8月19日 下午3:00:28 daweiwbs Exp $
 */
public class UCAccountInfo {

	//账号
	private String account;
	//姓名
	private String name;
	//密码
	private String password;
	//性别
	private Integer sex;
	//邮箱
	private String email;
	
	/**
	 * 机构id
	 */
	private Integer orgId;
	/**
	 * 用户空间数组
	 */
	private List<UserSpace> userSpaceList;
	
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public List<UserSpace> getUserSpaceList() {
		return userSpaceList;
	}
	public void setUserSpaceList(List<UserSpace> userSpaceList) {
		this.userSpaceList = userSpaceList;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
