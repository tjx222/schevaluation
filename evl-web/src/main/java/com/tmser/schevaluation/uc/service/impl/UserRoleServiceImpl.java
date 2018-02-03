/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.uc.bo.UserRole;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.uc.service.UserRoleService;
import com.tmser.schevaluation.uc.dao.UserRoleDao;
/**
 * 用户角色关系 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserRole.java, v 1.0 2015-02-05 Generate Tools Exp $
 */
@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole, Integer> implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserRole, Integer> getDAO() {
		return userRoleDao;
	}

}
