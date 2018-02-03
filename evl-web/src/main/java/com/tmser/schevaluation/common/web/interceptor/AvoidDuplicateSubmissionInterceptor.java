/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmser.schevaluation.common.annotation.UseToken;
import com.tmser.schevaluation.utils.Identities;

/**
 * <pre>
 *  防止重复提交过滤器
 * </pre>
 *
 * @author tmser
 * @version $Id: AvoidDuplicateSubmissionInterceptor.java, v 1.0 2015年4月6日 下午9:25:50 tmser Exp $
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);
	
	public static final String TOKEN_KEY = "_TOKEN_";
	
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            UseToken annotation = method.getAnnotation(UseToken.class);
            if (annotation != null) {
                if (isRepeatSubmit(request,annotation.value())) {
                        LOG.warn("please don't repeat submit,url:[{}]", request.getServletPath());
                        response.sendRedirect(request.getContextPath()+"/repeat.jsp");
                        return false;
                 }
           }
            return true;
    	} else {
    		return super.preHandle(request, response, handler);
    	}
    }
 
    private boolean isRepeatSubmit(HttpServletRequest request,boolean check) {
    	 String clinetTokenName = request.getParameter(TOKEN_KEY);
         if (clinetTokenName == null && check == false) {
        	 String tokenName =  Identities.uuid();
        	 request.setAttribute(TOKEN_KEY, tokenName);
        	 request.getSession(false).setAttribute(tokenName,Identities.uuid());
        	 LOG.debug("set token url:[{}]", request.getServletPath());
             return false;
         }
         
      
        if (StringUtils.isEmpty(clinetTokenName) ) {
            return true;
        }
        
        String clinetToken = request.getParameter(clinetTokenName);
        
        String serverToken = (String) request.getSession(false).getAttribute(clinetTokenName);
       
        if (serverToken == null || !serverToken.equals(clinetToken)) {
            return true;
        }
        
        request.getSession(false).removeAttribute(clinetTokenName);
        return false;
    }
 
}
