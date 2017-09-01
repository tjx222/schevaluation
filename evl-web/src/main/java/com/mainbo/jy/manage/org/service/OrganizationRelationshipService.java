/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.manage.org.bo.OrganizationRelationship;

/**
 * 学校关系表 服务类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OrganizationRelationship.java, v 1.0 2016-09-29 Generate Tools
 *          Exp $
 */

public interface OrganizationRelationshipService extends BaseService<OrganizationRelationship, Integer> {

	/**
	 * 根据orgId删除关系数据
	 * @param orgId
	 */
	void deleteByOrgId(Integer orgId);
	
	/**
	 * 批量插入关系数据
	 * @param list
	 */
	void batchInsert(List<OrganizationRelationship> list);

}
