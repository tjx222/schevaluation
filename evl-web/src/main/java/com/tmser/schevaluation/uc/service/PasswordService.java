/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service;


import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.exception.UserPasswordNotMatchException;
import com.tmser.schevaluation.uc.exception.UserPasswordRetryLimitExceedException;

/**
 * <pre>
 * 密码加密及验证服务
 * </pre>
 *
 * @author tmser
 * @version $Id: PasswordService.java, v 1.0 2015年2月1日 下午10:28:05 tmser Exp $
 */
public interface PasswordService extends EncodeService{
	
	/**
	 * 验证密码是否正确
	 * @param login
	 * @param password
	 * @throws UserPasswordNotMatchException,UserPasswordRetryLimitExceedException
	 */
	void validate(Login login,String password) throws UserPasswordRetryLimitExceedException,UserPasswordNotMatchException;
	
}
