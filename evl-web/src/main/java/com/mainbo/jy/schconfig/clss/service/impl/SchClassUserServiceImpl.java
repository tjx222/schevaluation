/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.schconfig.clss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;
import com.mainbo.jy.schconfig.clss.dao.SchClassUserDao;
import com.mainbo.jy.schconfig.clss.service.SchClassUserService;
/**
 * 学校班级用户关联 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */
@Service
@Transactional
public class SchClassUserServiceImpl extends AbstractService<SchClassUser, Integer> implements SchClassUserService {

	@Autowired
	private SchClassUserDao schClassUserDao;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchClassUser, Integer> getDAO() {
		return schClassUserDao;
	}
}
