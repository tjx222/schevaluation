/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service;

import java.util.Map;
import java.util.Set;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.exception.UcException;

/**
 * <pre>
 *	用户登录服务。
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginService.java, v 1.0 2015年1月31日 下午1:52:23 tmser Exp $
 */
public interface LoginService extends BaseService<Login, Integer>{

	/**
	 * 登录
	 * @param principal 登录身份标识，可以是用户名、邮箱、手机
	 * @param password 密码
	 * @return 成功返回登录信息
	 */
	Login login(String principal,String password) throws UcException;


	/**
	 * 按用户名查找用户
	 * @param username
	 * @return
	 */
	Login findByUsername(String username);

	/**
	 * 按邮箱查找用户
	 * @param email
	 * @return
	 */
	Login findByEmail(String email);

	/**
	 * 按手机号查找用户
	 * @param cellPhone
	 * @return
	 */
	Login findByCellPhone(String cellPhone);

	/**
	 * 根据用户id 判定用户登录成功进入的空间地址
	 * @param id
	 * @return
	 */
	String toWorkSpace(Integer uid);

	/**
	 * 根据用户id 获取角色组
	 * @param uid
	 * @return
	 */
	Set<String> findStringRoles(Integer uid);

	/**
	 * 根据用户id 获取权限组
	 * @param uid
	 * @return
	 */
	Set<String> findStringPermissions(Integer uid);

	/**
	 * 根据登录名，查找登录登录对象
	 * @param loginname
	 * @return
	 */
	Login findLogin(String loginname);

	/**
	 * 用户互通校验
	 * @param username
	 * @param password
	 * @return
	 */
	Map<String, Object> checkAccountLoginAccessProtocol(String username,
			String password);

	/**
	 * 通过账户名称获得账户对象
	 * @param account
	 * @return
	 */
	Login getLoginByLoginname(String account);

	/**
	 * 按登录名查找
	 * @param loginName
	 * @return
	 */
	Login findOneByLoginName(String loginName);
	
	/**
	 * 根据登录名和密码创建新用户对象，该对象状态： deteted ： false, enable : 1 , isAdmin : false
	 * <code>
	 * 	l.setLoginname(loginname);
		l.setPassword(psd);
		l.setDeleted(false);
		l.setEnable(1);
		l.setIsAdmin(false);
		</code>
	 * @param loginname
	 * @param psd
	 * @return
	 */
	Login newLogin(String loginname,String psd);

}
