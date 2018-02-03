/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.exception.UserPasswordNotMatchException;
import com.tmser.schevaluation.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.schevaluation.uc.log.LoginLogUtils;
import com.tmser.schevaluation.uc.service.PasswordService;
import com.tmser.schevaluation.utils.Encodes;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: PasswordServiceImpl.java, v 1.0 2015年2月1日 下午10:32:56 tmser Exp $
 */
public class PasswordServiceImpl implements PasswordService {
	
	@Value("${user.password.maxRetryCount:3}")
	private Integer maxRetryCount;
	
	@Resource(name="cacheManger")
    private CacheManager cacheManager;

    private Cache loginRecordCache;
    
    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

	protected boolean matches(Login login, String newPassword) {
        return login.getPassword().equals(encryptPassword(login.getLoginname(), newPassword, login.getSalt()));
    }

	/**
	 * 生成密码
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
    @Override
	public String encryptPassword(String username, String password, String salt) {
        return Encodes.md5(new StringBuilder(username).append(password).append(salt).toString());
    }


	/**
	 * @param login
	 * @param password
	 * @throws UserPasswordRetryLimitExceedException
	 * @throws UserPasswordNotMatchException
	 * @see com.tmser.schevaluation.uc.service.PasswordService#validate(com.tmser.schevaluation.uc.bo.Login, java.lang.String)
	 */
	@Override
	public void validate(Login login, String password)
			throws UserPasswordRetryLimitExceedException,
			UserPasswordNotMatchException {
		String username = login.getLoginname();
        int retryCount = 0;
        ValueWrapper cacheElement = loginRecordCache.get(username);
        
        if (cacheElement != null) {
            retryCount = (Integer) cacheElement.get();
            if (retryCount >= maxRetryCount) {
            	LoginLogUtils.log(
                        username,
                        "passwordError",
                        "password error, retry limit exceed! password: {},max retry count {}",
                        password, maxRetryCount);
                throw new UserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }

        if (!matches(login, password)) {
        	loginRecordCache.put(username,Integer.valueOf(++retryCount));
            LoginLogUtils.log(
                    username,
                    "passwordError",
                    "password error! password: {} retry count: {}",
                    password, retryCount);
            throw new UserPasswordNotMatchException();
        }
        
        loginRecordCache.put(username,Integer.valueOf(0));
	}
}
