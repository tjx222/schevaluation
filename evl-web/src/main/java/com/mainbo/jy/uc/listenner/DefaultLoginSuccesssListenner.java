/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.listenner;


/**
 * <pre>
 *	默认登录成功监听器，完成如下操作：
 *  <1>  记录用户当前学年到session。
 *  <2>  记录用户信息到session。
 *  <3>  记录用户身份信息列表到session。
 * </pre>
 *
 * @author tmser
 * @version $Id: DefaultLoginSuccesssListenner.java, v 1.0 2015年2月3日 下午2:41:54 tmser Exp $
 */
public class DefaultLoginSuccesssListenner implements LoginSuccessListenner{
	/**
	 * 登录监听器
	 * @param login
	 * @see com.mainbo.jy.uc.listenner.LoginSuccessListenner#doOnSuccess(com.mainbo.jy.uc.bo.Login)
	 */
	@Override
	public void doOnSuccess(Integer userid) {
		//do nothing
	}
	
}
