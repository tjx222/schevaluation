/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mainbo.jy.uc.shiro;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mainbo.jy.uc.LoginSessionHelper;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.exception.RequestNotExistsException;
import com.mainbo.jy.uc.exception.UserSpaceNotExistsException;
import com.mainbo.jy.uc.utils.SessionKey;

/**
 * 验证用户过滤器
 * 1、用户是否删除
 * 2、用户是否锁定
 * <p>User: tmser
 * <p>Date: 14-3-19 下午3:09
 * <p>Version: 1.0
 */
public class SysUserFilter extends AccessControlFilter {

	private final static Logger logger = LoggerFactory.getLogger(SysUserFilter.class);
	
	private List<String> unusePhaseUrlPatterns;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null) {
			return true;
		}

		Integer uid = (Integer) subject.getPrincipal();
		try {
			LoginSessionHelper.setSession(uid);
		} catch (UserSpaceNotExistsException e) {
			WebUtils.issueRedirect(request, response, "/jy/uc/addrole");
			logger.warn("userspace not exit!",e);
			return false;
		}catch (RequestNotExistsException e){
			logger.warn("request not exist");
			return false;
		}catch (Exception e) {
			logger.warn("set session failed",e);
			return false;
		}

		return super.preHandle(request, response);
	}


	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		boolean rs = true;
		if (isLoginRequest(request, response)) {
			rs = true;
		} else {
			Subject subject = getSubject(request, response);
			rs = subject.getPrincipal() != null;
		}

		if(rs){
			HttpSession session = ((HttpServletRequest)request).getSession();
			UserSpace cus = (UserSpace)session.getAttribute(SessionKey.CURRENT_SPACE);
			if(cus != null && Integer.valueOf(0).equals(cus.getPhaseId())){
				String indexUrl = cus.getSpaceHomeUrl();
				if(notAjaxRequest((HttpServletRequest)request) && !pathsMatch("/",request)
						&& !pathsMatch(indexUrl,request)){
					boolean use = true;
					for(String  urlPattern: unusePhaseUrlPatterns){
						if(pathsMatch(urlPattern,request)){
							use = false;
							break;
						}
					}

					if(use){
						WebUtils.issueRedirect(request, response, indexUrl);
						return false;
					}

				}
			}
		}

		return rs;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		logger.debug("denied url: {}",((HttpServletRequest)request).getRequestURI());
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	@Override
	protected void saveRequest(ServletRequest request) {
		if(request instanceof HttpServletRequest){
			HttpServletRequest rq =  (HttpServletRequest)request;
			if (notAjaxRequest(rq) && notStaticRequest(rq)){
				WebUtils.saveRequest(request);
			}
		}else{
			super.saveRequest(request);
		}

	}

	protected boolean notAjaxRequest(HttpServletRequest request){
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith == null || !requestedWith.toLowerCase().contains("xmlhttprequest");
	}

	protected boolean notStaticRequest(HttpServletRequest request){
		return !pathsMatch("/static/**",request);
	}


	public List<String> getUnusePhaseUrlPatterns() {
		return unusePhaseUrlPatterns;
	}

	public void setUnusePhaseUrlPatterns(List<String> uselessPhaseUrlPatterns) {
		this.unusePhaseUrlPatterns = uselessPhaseUrlPatterns;
	}


}
