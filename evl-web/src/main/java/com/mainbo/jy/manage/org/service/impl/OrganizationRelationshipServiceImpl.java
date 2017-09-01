/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.manage.org.bo.OrganizationRelationship;
import com.mainbo.jy.manage.org.dao.OrganizationRelationshipDao;
import com.mainbo.jy.manage.org.service.OrganizationRelationshipService;

/**
 * 学校关系表 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OrganizationRelationshipServiceImpl.java, v 1.0 2016-09-29
 *          Generate Tools Exp $
 */
@Service
@Transactional
public class OrganizationRelationshipServiceImpl extends AbstractService<OrganizationRelationship, Integer> implements OrganizationRelationshipService {

	@Autowired
	private OrganizationRelationshipDao organizationRelationshipDao;

	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<OrganizationRelationship, Integer> getDAO() {
		return organizationRelationshipDao;
	}

	/**
	 * @param orgId
	 * @see com.mainbo.jy.manage.org.service.OrganizationRelationshipService#deleteByOrgId(java.lang.String)
	 */
	@Override
	public void deleteByOrgId(Integer orgId) {
		organizationRelationshipDao.deleteByOrgId(orgId);
	}

	/**
	 * 
	 * @see com.mainbo.jy.manage.org.service.OrganizationRelationshipService#batchInsert()
	 */
	@Override
	public void batchInsert(List<OrganizationRelationship> list) {
		organizationRelationshipDao.batchInsert(list);
	}

}
