/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.utils;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.tmser.schevaluation.utils.SpringContextHolder;

/**
 * <pre>
 * web 工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: WebUtils.java, v 1.0 2015年5月14日 下午1:28:21 tmser Exp $
 */
public abstract class WebUtils {
	/**
	 * 获取用户ip 地址
	 * @param request
	 * @return
	 */
	public final static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	private final static String getWebAppRoot(){
		Properties config = SpringContextHolder.getBean("config");
		String webRootKey = config.getProperty("front_web_root");
		return StringUtils.isEmpty(webRootKey) ? System.getProperty("jypt.back.root") : webRootKey;
	}
	
	public static final String getRootPath(){
		String basePath = null;
		if(basePath ==  null  || "".equals(basePath.trim()) 
				|| !(new File(basePath).isAbsolute())){
			String root = getWebAppRoot();
			if(root != null  &&  !"".equals(root))
				basePath = new File(root).getAbsolutePath();
		}
		
		if(basePath ==  null  || "".equals(basePath.trim())){
			String root = new File(ClassLoader.getSystemResource("").getFile())
								.getParentFile().getParent();
			if(root != null  &&  !"".equals(root))
				basePath = new File(root).getAbsolutePath();
		}
		
		return basePath;
	}
}
