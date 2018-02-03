/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.manage.resources.bo.Attach;
import com.tmser.schevaluation.manage.resources.dao.AttachDao;
import com.tmser.schevaluation.manage.resources.service.AttachService;
/**
 * 集体备课活动资源 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ActivityRes.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
@Service
@Transactional
public class AttachServiceImpl extends AbstractService<Attach, Integer> implements AttachService {

	@Autowired
	private AttachDao activityAttachDao;
	
	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Attach, Integer> getDAO() {
		return activityAttachDao;
	}

}
