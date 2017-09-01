/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.uc.bo.Role;
import com.mainbo.jy.uc.dao.RoleDao;

/**
 * 用户角色 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Role.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@Repository
public class RoleDaoImpl extends AbstractDAO<Role,Integer> implements RoleDao {

	/**
	 * @param userid
	 * @return
	 * @see com.mainbo.jy.uc.dao.RoleDao#findRoleByUserid(java.lang.Integer)
	 */
	@Override
	public List<Role> listRoleByUserid(Integer userid,Integer sysRoleId) {
		StringBuilder sql = new StringBuilder(" select  * from Role r where r.id in (select ur.roleId from "
				+ " UserRole ur where ur.userId = ?)");
		Object[] args = null;
		
		if(sysRoleId != null){
			sql.append(" and r.sysRoleId = ?");
			args = new Object[]{userid,sysRoleId};
		}
			
		return this.query(sql.toString(),args!=null?args:new Object[]{userid}, getMapper());
	}

	@Override
	public void copyRoleForSolution(Integer id) {
		//复制角色
		String sql = " insert into Role (sysRoleId,roleName,roleCode,orgId,remark,roleDesc,solutionId ,isDel,relId,usePosition)"
					+ " select sysRoleId,roleName,roleCode,orgId,remark,roleDesc,?,isDel,id,usePosition from Role where isDel = ? and solutionId = ? and usePosition != ?";
		update(sql, new Object[]{id,0,0,3});
	}

}
