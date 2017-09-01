/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.uc.bo.Permission;

/**
 * 权限表 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Permission.java, v 1.0 2015-02-10 Generate Tools Exp $
 */

public interface PermissionService extends BaseService<Permission, Integer>{
	/**
	 * 
	 * 根据角色id 获取权限
	 * 
	 * @param roleid
	 * @return
	 */
	List<Permission> findPermissionByRoleid(Integer roleid);
}
