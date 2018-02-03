/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.dao;

import java.util.List;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.bo.RoleMenu;

 /**
 * 角色menu关联 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RoleMenu.java, v 1.0 2015-09-07 Generate Tools Exp $
 */
public interface RoleMenuDao extends BaseDAO<RoleMenu, Integer>{

	/**
	 * 批量拷贝系统角色权限到对应方案角色下,只拷贝非删除状态权限
	 * @param roles 要进行拷贝的方案角色列表
	 */
	void copyMenuToSolution(List<Role> roles);

}