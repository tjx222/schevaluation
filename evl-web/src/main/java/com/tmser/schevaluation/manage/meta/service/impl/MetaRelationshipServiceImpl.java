/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.manage.meta.bo.MetaRelationship;
import com.tmser.schevaluation.manage.meta.dao.MetaRelationshipDao;
import com.tmser.schevaluation.manage.meta.service.MetaRelationshipService;

/**
 * 元数据关系表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
@Service
@Transactional
public class MetaRelationshipServiceImpl extends AbstractService<MetaRelationship, Integer> implements MetaRelationshipService {

	@Autowired
	private MetaRelationshipDao metaRelationshipDao;
	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<MetaRelationship, Integer> getDAO() {
		return metaRelationshipDao;
	}

}
