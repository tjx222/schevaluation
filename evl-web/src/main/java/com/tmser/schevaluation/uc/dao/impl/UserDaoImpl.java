/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.common.dao.annotation.UseCache;
import com.tmser.schevaluation.common.page.Page;
import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.dao.UserDao;

/**
 * 用户登息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: User.java, v 1.0 2015-01-30 Generate Tools Exp $
 */
@Repository
@UseCache
public class UserDaoImpl extends AbstractDAO<User,Integer> implements UserDao {

	/**
	 * 通过用户id排列查询用户
	 * @param userIds
	 * @return
	 * @see com.tmser.schevaluation.uc.dao.UserDao#findByIds(java.util.List)
	 */
	@Override
	public List<User> findByIds(List<Integer> userIds) {
		String sql = "select * from User where id in (:userIds)";
		Map<String,Object> map = new HashMap<>();
		map.put("userIds", userIds);
		return this.queryByNamedSql(sql, map, this.getMapper());
	}

	/**
	 * 通过机构ID查询出用户登录信息
	 * @param orgId
	 * @see com.tmser.schevaluation.uc.dao.UserDao#findUserInfosByOrgId(java.lang.Integer)
	 */
	@Override
	public List<Map<String,Object>> findUserInfosByOrgId(Integer orgId,Integer userType,Integer appId) {
		String sql = "select distinct u.name,u.sex,l.loginname,l.enable from User u,Login l where u.id=l.id and u.orgId=:orgId and u.userType=:userType and u.appId=:appId";
		Map<String,Object> map = new HashMap<>();
		map.put("orgId", orgId);
		map.put("userType", userType);
		map.put("appId", appId);
		return this.queryByNamedSql(sql, map);

	}
	@Override
	public List<Map<String,Object>> findUserInfosByOrgId(Integer orgId,Integer userType) {
		String sql = "select distinct u.name,u.sex,l.loginname,l.enable from User u,Login l where u.id=l.id and u.orgId=:orgId and u.userType=:userType";
		Map<String,Object> map = new HashMap<>();
		map.put("orgId", orgId);
		map.put("userType", userType);
		return this.queryByNamedSql(sql, map);

	}

	/**
	 * 通过用户类型查询用户信息
	 * @param userType
	 * @return
	 * @see com.tmser.schevaluation.uc.dao.UserDao#findUserInfosByUserType(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> findUserInfosByUserType(Integer userType) {
		String sql = "select distinct u.name,u.sex,l.loginname,l.enable from User u,Login l where u.id=l.id and u.userType=:userType";
		Map<String,Object> map = new HashMap<>();
		map.put("userType", userType);
		return this.queryByNamedSql(sql, map);
	}
	
	/**
	 * 通过sql 查询用户
	 * @param userType
	 * @return
	 * @see com.tmser.schevaluation.uc.dao.UserDao#findUserInfosByUserType(java.lang.Integer)
	 */
	@Override
	public PageList<Map<String,Object>> queryPageByNamedSql(String sql,Map<String,Object> args, Page pageInfo) throws DataAccessException{
		return super.queryPageByNamedSql(sql, args, pageInfo);
	}

}
