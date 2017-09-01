package com.mainbo.jy.uc.vo;

public class LogSearchVo {

	// 根据日志登录名查询
	private String searchUserName;

	// 根据日志姓名查询
	private String searchName;

	// 根据身份sysRoleId查询
	private Integer sysRoleIdVo;

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getSysRoleIdVo() {
		return sysRoleIdVo;
	}

	public void setSysRoleIdVo(Integer sysRoleIdVo) {
		this.sysRoleIdVo = sysRoleIdVo;
	}

}
