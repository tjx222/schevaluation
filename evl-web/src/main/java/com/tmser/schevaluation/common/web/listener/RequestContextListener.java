/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;

/**
 * <pre>
 *  设置及清除全局request 线程变量
 * </pre>
 *
 * @author tmser
 * @version $Id: DefaultSessionListener.java, v 1.0 2015年2月12日 上午10:41:25 tmser Exp $
 */
public class RequestContextListener implements ServletRequestListener {
	private final static Logger logger = LoggerFactory.getLogger(RequestContextListener.class);
	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
			throw new IllegalArgumentException(
					"Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
		}
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		logger.info(request.getRequestURI());
		WebThreadLocalUtils.setRequest(request);
		
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		if (request != null) {
			WebThreadLocalUtils.clear();
		}
	}

}
