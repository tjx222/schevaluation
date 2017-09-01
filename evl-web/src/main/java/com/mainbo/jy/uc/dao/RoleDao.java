/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao;

import java.util.List;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.uc.bo.Role;

 /**
 * 用户角色 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
public interface RoleDao extends BaseDAO<Role, Integer>{

	/**
	 * 查找用户所拥有角色
	 * 
	 * @param userid
	 * @param sysRoleId
	 * @return
	 */
	List<Role> listRoleByUserid(Integer userid,Integer sysRoleId);
	
	/**
	 * 拷贝系统角色到方案
	 * @param solutionid 要拷贝到方案id
	 */
	void copyRoleForSolution(Integer solutionid);
}