/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.manage.org.bo.OrganizationRelationship;

/**
 * 学校关系表 DAO接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OrganizationRelationship.java, v 1.0 2016-09-29 Generate Tools
 *          Exp $
 */
public interface OrganizationRelationshipDao extends BaseDAO<OrganizationRelationship, Integer> {
	
	/**
	 * 根据学校id删除关联数据
	 */
	void deleteByOrgId(Integer orgId);
}