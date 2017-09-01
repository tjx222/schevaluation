/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.listenner;


/**
 * <pre>
 * 登录成功监听器，根据登录成功信息做登录初始化操作
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginSuccessListenner.java, v 1.0 2015年2月3日 下午2:34:44 tmser Exp $
 */
public interface LoginSuccessListenner {
	/**
	 * 监听登录成功
	 * @param userid
	 */
	void doOnSuccess(Integer userid);
}
