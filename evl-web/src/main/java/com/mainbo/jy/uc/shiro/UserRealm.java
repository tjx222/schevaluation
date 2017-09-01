/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.shiro;


import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.uc.Constants;
import com.mainbo.jy.uc.bo.Login;
import com.mainbo.jy.uc.exception.UcException;
import com.mainbo.jy.uc.exception.UserBlockedException;
import com.mainbo.jy.uc.exception.UserNotExistsException;
import com.mainbo.jy.uc.exception.UserPasswordNotMatchException;
import com.mainbo.jy.uc.exception.UserPasswordRetryLimitExceedException;
import com.mainbo.jy.uc.service.LoginService;

/**
 * <pre>
 *	自定义认证及权限收集器
 * </pre>
 *
 * @author tmser
 * @version $Id: UserRealm.java, v 1.0 2015年1月23日 下午6:23:54 tmser Exp $
 */
public class UserRealm extends AbstractUserRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    
    @Resource
    private LoginService loginService;
    

	/**
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
							throws AuthenticationException {
		 	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
	        String username = upToken.getUsername().trim();
	        String password = "";
	        if (upToken.getPassword() != null) {
	            password = new String(upToken.getPassword());
	        }

	        Login user = null;
	        try {
	            user = loginService.login(username, password);
	            WebThreadLocalUtils.setSessionAttrbitue(Constants.LOGIN_FROM_WHERE,Constants.LOGIN_FROM_LOCAL);
	        } catch (UserNotExistsException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new UnknownAccountException(e.getMessage(), e);
	        } catch (UserPasswordNotMatchException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new AuthenticationException(e.getMessage(), e);
	        } catch (UserPasswordRetryLimitExceedException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new ExcessiveAttemptsException(e.getMessage(), e);
	        } catch (UserBlockedException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new LockedAccountException(e.getMessage(), e);
	        } catch (Exception e) {
	            WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            log.warn("login error", e);
	            throw new AuthenticationException(new UcException("user.unknown.error", null));
	        }

	        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), password.toCharArray(), getName());
	        return info;
	}

}
