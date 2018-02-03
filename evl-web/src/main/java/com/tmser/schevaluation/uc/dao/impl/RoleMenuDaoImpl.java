/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.uc.bo.Role;
import com.tmser.schevaluation.uc.bo.RoleMenu;
import com.tmser.schevaluation.uc.dao.RoleMenuDao;

/**
 * 角色menu关联 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RoleMenu.java, v 1.0 2015-09-07 Generate Tools Exp $
 */
@Repository
public class RoleMenuDaoImpl extends AbstractDAO<RoleMenu,Integer> implements RoleMenuDao {

	@Override
	public void copyMenuToSolution(List<Role> roles) {
		//复制权限
		String sql1 = " insert into RoleMenu (menuId,roleId,permBname) "
				+ " select menuId, ?,permBname from RoleMenu where roleId = ? and isDel = ?";
				
		for (Role role : roles) {
			Object[] args = new Object[]{role.getId(),role.getRelId(),0};
			update(sql1,args);
		}
	}
}
