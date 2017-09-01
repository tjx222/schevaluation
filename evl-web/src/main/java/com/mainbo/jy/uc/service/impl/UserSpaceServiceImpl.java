/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.dao.UserSpaceDao;
import com.mainbo.jy.uc.service.UserSpaceService;
/**
 * 用户空间信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
@Service
@Transactional
public class UserSpaceServiceImpl extends AbstractService<UserSpace, Integer> implements UserSpaceService {

	@Autowired
	private UserSpaceDao userSpaceDao;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<UserSpace, Integer> getDAO() {
		return userSpaceDao;
	}

	/**
	 * @param userid
	 * @param schoolYear
	 * @return
	 * @see com.mainbo.jy.uc.service.UserSpaceService#listUserSpaceBySchoolYear(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<UserSpace> listUserSpaceBySchoolYear(Integer userid,
			Integer schoolYear) {
		if(userid == null){
			return Collections.emptyList();
		}
		UserSpace userSpace = new UserSpace();
		userSpace.setUserId(userid);
		userSpace.setSchoolYear(schoolYear);
		userSpace.setEnable(UserSpace.ENABLE);
		userSpace.addOrder("sysRoleId");
		return userSpaceDao.listAll(userSpace);
	}

	@Override
	public void batchsaveUsers(List<UserSpace> ups) {
		userSpaceDao.batchInsert(ups);
		
	}
}
