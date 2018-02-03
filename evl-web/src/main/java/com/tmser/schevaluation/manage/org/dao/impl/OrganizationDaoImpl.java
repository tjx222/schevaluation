/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.org.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.common.dao.annotation.UseCache;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.dao.OrganizationDao;

/**
 * 机构 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */
@Repository
@UseCache
public class OrganizationDaoImpl extends AbstractDAO<Organization, Integer> implements OrganizationDao {

	/**
	 * 通过组织id批量查询组织
	 * 
	 * @param schoolIds
	 * @return
	 * @see com.tmser.schevaluation.manage.org.dao.OrganizationDao#findByIds(java.util.List)
	 */
	@Override
	public List<Organization> findByIds(List<Integer> ids) {
		String sql = "select * from Organization where id in (:ids)";
		Map<String, Object> args = new HashMap<>();
		args.put("ids", ids);
		return this.queryByNamedSql(sql, args, this.mapper);
	}

	/**
	 * 通过名称模糊查询
	 * 
	 * @param name
	 * @return
	 * @see com.tmser.schevaluation.manage.org.dao.OrganizationDao#findByName(java.lang.String)
	 */
	@Override
	public List<Organization> findByName(String name) {
		String sql = "select * from Organization where name like '%?%'";
		return this.query(sql, new Object[] { name }, this.mapper);
	}

}
