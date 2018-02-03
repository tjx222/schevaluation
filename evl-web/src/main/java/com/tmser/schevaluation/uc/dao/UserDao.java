/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.page.Page;
import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.uc.bo.User;

/**
 * 用户登息 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: User.java, v 1.0 2015-01-30 Generate Tools Exp $
 */
public interface UserDao extends BaseDAO<User, Integer>{

	/**
	 * 通过用户id排列查询用户
	 * @param userIds
	 * @return
	 */
	List<User> findByIds(List<Integer> userIds);

	/**
	 * 通过机构ID，查询出用户登录等信息
	 * @param orgId
	 * @return
	 */
	List<Map<String,Object>> findUserInfosByOrgId(Integer orgId,Integer userType,Integer appId);
	List<Map<String,Object>> findUserInfosByOrgId(Integer orgId,Integer userType);

	/**
	 * 通过用户类型查询用户信息
	 * @param userType
	 * @return
	 */
	List<Map<String, Object>> findUserInfosByUserType(Integer userType);
	
	
	PageList<Map<String,Object>> queryPageByNamedSql(String sql,Map<String,Object> args, Page pageInfo) throws DataAccessException;
}