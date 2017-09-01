/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.uc.bo.SysSolution;

 /**
 * 方案表-实体 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SysSolution.java, v 1.0 2015-09-21 Generate Tools Exp $
 */
public interface SysSolutionDao extends BaseDAO<SysSolution, Integer>{
	
	/**
	 * 删除销售方案相关的角色权限信息及其与校关联信息
	 * @param solutionId 方案id
	 */
	void clearSolution(Integer solutionId);
}