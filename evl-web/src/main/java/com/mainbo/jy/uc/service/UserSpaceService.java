/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.uc.bo.UserSpace;

/**
 * 用户空间信息 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */

public interface UserSpaceService extends BaseService<UserSpace, Integer>{
	
	/**
	 * 获取用户某学年下的所有身份
	 * @param orgId
	 * @return
	 */
	public List<UserSpace> listUserSpaceBySchoolYear(Integer userid,Integer schoolYear);

	/**
	 * 批量新增
	 * @param ups
	 */
	public void batchsaveUsers(List<UserSpace> ups);

}
