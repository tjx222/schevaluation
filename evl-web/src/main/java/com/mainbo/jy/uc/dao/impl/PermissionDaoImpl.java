/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.uc.bo.Permission;
import com.mainbo.jy.uc.dao.PermissionDao;

/**
 * 权限表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Permission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */
@Repository
public class PermissionDaoImpl extends AbstractDAO<Permission,Integer> implements PermissionDao {

	/**
	 * @param roleid
	 * @return
	 * @see com.mainbo.jy.uc.dao.PermissionDao#listPermissionByRoleid(java.lang.Integer)
	 */
	@Override
	public List<Permission> listPermissionByRoleid(Integer roleid) {
		String sql = " select  * from Permission p where p.id in (select rp.permissionId from "
				+ " RolePermission rp where rp.roleId = ?)";
		
		return this.query(sql, new Object[]{roleid}, getMapper());
	}

}
