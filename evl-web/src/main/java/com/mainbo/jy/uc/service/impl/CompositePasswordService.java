/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mainbo.jy.uc.bo.Login;
import com.mainbo.jy.uc.exception.UcException;
import com.mainbo.jy.uc.exception.UserPasswordNotMatchException;
import com.mainbo.jy.uc.exception.UserPasswordRetryLimitExceedException;
import com.mainbo.jy.uc.service.LoginService;
import com.mainbo.jy.uc.service.PasswordService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositePasswordService.java, v 1.0 2015年12月2日 上午10:05:37 tmser Exp $
 */
public class CompositePasswordService implements PasswordService{
	
	private List<PasswordService> passwordServices;
	
	/**
	 * 加密使用的服务在passwordServices中的位置，默认使用 列表中的第一个passwordService 进行
	 */
	private Integer encodeServiceIndex = 0;
	
	private PasswordService encodeService;
	
	@Resource
	private LoginService loginService;

	/**
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 * @see com.mainbo.jy.uc.service.PasswordService#encryptPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String encryptPassword(String username, String password, String salt) {
		if(encodeService == null){
			getEncodeService();
		}
		return encodeService.encryptPassword(username, password, salt);
	}

	/**
	 * @param login
	 * @param password
	 * @throws UserPasswordRetryLimitExceedException
	 * @throws UserPasswordNotMatchException
	 * @see com.mainbo.jy.uc.service.PasswordService#validate(com.mainbo.jy.uc.bo.Login, java.lang.String)
	 */
	@Override
	public void validate(Login login, String password)
			throws UserPasswordRetryLimitExceedException,
			UserPasswordNotMatchException {
		UcException e = null;
		boolean success = false;
		
		try {//首先用设定的默认加密算法登录
			getEncodeService().validate(login,password);
			success = true;
		} catch (UcException e1) {
			e = e1;
		}
		if(!success){
			for(PasswordService ps : passwordServices){
				if(!ps.equals(encodeService)){
					 try {
						ps.validate(login,password);
						success = true;
						Login l = new Login();
						l.setId(login.getId());
						l.setPassword(getEncodeService()
							.encryptPassword(login.getLoginname(), password, login.getSalt()));
						loginService.update(l);
						break;
					} catch (UcException e1) {
						e = e1;
					}
			   }
			}
		}
		if(!success && e != null){
			throw e;
		}
	}

	public PasswordService getEncodeService() {
		if(encodeService == null){
			if(encodeServiceIndex != null 
				&& encodeServiceIndex < passwordServices.size()
				&& encodeServiceIndex >= 0){
				encodeService = passwordServices.get(encodeServiceIndex);
			}else{
				encodeService = passwordServices.get(0);
			}
		}
		return encodeService;
	}

	public List<PasswordService> getPasswordServices() {
		return passwordServices;
	}

	public void setPasswordServices(List<PasswordService> passwordServices) {
		this.passwordServices = passwordServices;
	}

	public Integer getEncodeServiceIndex() {
		return encodeServiceIndex;
	}

	public void setEncodeServiceIndex(Integer encodeServiceIndex) {
		this.encodeServiceIndex = encodeServiceIndex;
	}

}
