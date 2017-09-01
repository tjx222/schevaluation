/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.dao;

import java.util.List;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.manage.org.bo.Organization;

 /**
 * 机构 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */
public interface OrganizationDao extends BaseDAO<Organization, Integer>{
	
	/**
	 * 通过组织id批量查询组织
	 * @param schoolIds
	 * @return
	 */
	List<Organization> findByIds(List<Integer> schoolIds);
	
	/**
	 * 通过名称模糊查询
	 * @param name
	 */
	List<Organization> findByName(String name);
	
}