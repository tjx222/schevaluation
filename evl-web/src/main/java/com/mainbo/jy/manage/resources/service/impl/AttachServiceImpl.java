/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.resources.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.manage.resources.bo.Attach;
import com.mainbo.jy.manage.resources.dao.AttachDao;
import com.mainbo.jy.manage.resources.service.AttachService;
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
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Attach, Integer> getDAO() {
		return activityAttachDao;
	}

}
