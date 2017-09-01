/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.dao.impl;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.manage.org.bo.OrganizationRelationship;
import com.mainbo.jy.manage.org.dao.OrganizationRelationshipDao;

/**
 * 学校关系表 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OrganizationRelationship.java, v 1.0 2016-09-29 Generate Tools
 *          Exp $
 */
@Repository
public class OrganizationRelationshipDaoImpl extends AbstractDAO<OrganizationRelationship, Integer> implements OrganizationRelationshipDao {

	/**
	 * 
	 * @see com.mainbo.jy.manage.org.dao.OrganizationRelationshipDao#deleteByOrgId()
	 */
	@Override
	public void deleteByOrgId(Integer orgId) {
		String sql = "delete from OrganizationRelationship where orgId=?";
		Object[] args = new Object[]{orgId};
	    update(sql,args);
	}

}
