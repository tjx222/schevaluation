/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mainbo.jy.common.utils.RequestUtils;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginLogUtils.java, v 1.0 2015年2月1日 下午9:20:03 tmser Exp $
 */
public abstract class LoginLogUtils {
	 private static final Logger SYS_USER_LOGGER = LoggerFactory.getLogger(LoginLogUtils.class);

	    /**
	     * 记录格式 [ip][用户名][操作][错误消息]
	     * <p/>
	     * 注意操作如下：
	     * loginError 登录失败
	     * loginSuccess 登录成功
	     * passwordError 密码错误
	     * changePassword 修改密码
	     * changeStatus 修改状态
	     *
	     * @param username
	     * @param op
	     * @param msg
	     * @param args
	     */
	    public static void log(String username, String op, String msg, Object... args) {
	        StringBuilder s = new StringBuilder();
	        s.append(getBlock(RequestUtils.getIpAddr(WebThreadLocalUtils.getRequest())));
	        s.append(getBlock(username));
	        s.append(getBlock(op));
	        s.append(getBlock(msg));
	        SYS_USER_LOGGER.info(s.toString(), args);
	    }
	    
	    public static String getBlock(Object msg) {
	        if (msg == null) {
	            msg = "";
	        }
	        return "[" + msg.toString() + "]";
	    }
	    
}
