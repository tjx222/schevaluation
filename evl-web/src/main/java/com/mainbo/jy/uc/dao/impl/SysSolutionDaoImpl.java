/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao.impl;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.uc.bo.SysSolution;
import com.mainbo.jy.uc.dao.SysSolutionDao;

/**
 * 方案表-实体 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SysSolution.java, v 1.0 2015-09-21 Generate Tools Exp $
 */
@Repository
public class SysSolutionDaoImpl extends AbstractDAO<SysSolution,Integer> implements SysSolutionDao {

	@Override
	public void clearSolution(Integer solutionId) {
		
		Object[] args = new Object[]{solutionId};
		update("delete RoleMenu where roleId in (select id from Role where solutionId = ? ) ", args); //删除角色
		
		update("delete Role where solutionId = ? ", args);

		update("delete SysOrgSolution where solutionId = ?", args);
	}

}
