/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.uc.bo.UserRole;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.uc.service.UserRoleService;
import com.mainbo.jy.uc.dao.UserRoleDao;
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
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserRole, Integer> getDAO() {
		return userRoleDao;
	}

}
