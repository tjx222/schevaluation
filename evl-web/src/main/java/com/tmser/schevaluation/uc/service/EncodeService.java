/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.service;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: EncodeService.java, v 1.0 2015年12月2日 上午10:13:46 tmser Exp $
 */
public interface EncodeService {
	
	/**
	 * 生成密码
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
    String encryptPassword(String username, String password, String salt);
}
