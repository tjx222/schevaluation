/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;

import com.tmser.schevaluation.common.utils.RequestUtils;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.SpringContextHolder;

/**
 * <pre>
 *  在线用户统计
 * </pre>
 *
 * @author tmser
 * @version $Id: OnlineUserListener.java, v 1.0 2016年5月31日 下午3:50:14 tmser Exp $
 */
public class OnlineUserListener implements HttpSessionAttributeListener, HttpSessionListener {
	 
    /**
     * 在线用户列表
     */
   private CacheManager cacheManager;

   private Cache onlineUserCache;
    
   private void initCache(){
	   if(onlineUserCache == null){
		   if(cacheManager == null){
			   cacheManager = SpringContextHolder.getBean("cacheManger");
		   }
		   onlineUserCache = cacheManager.getCache("onlineUserCache");
	   }
   }
	/* 
     * session 属性添加时（即首次执行setAttribute时）触发
     */
    @Override
	public void attributeAdded(HttpSessionBindingEvent event) {
        if(event.getSession()==null){
            return;
        }
        // 将当前用户添加到在线用户列表中
        if(SessionKey.CURRENT_USER.equals(event.getName())){
            // 获取当前登陆用户信息及session
            User user = (User)event.getValue();
            if(user == null) 
            	return;
            HttpSession session = event.getSession();
            ValueWrapper cacheElement = onlineUserCache.get(user.getId());
            OnlineUserView onlineUserView = null;
            if (cacheElement != null) {
            	onlineUserView = (OnlineUserView) cacheElement.get();
                if (onlineUserView != null) {
                	onlineUserView.addSessionId(session.getId());
                }
            }
            
            // 创建在线用户信息对象
            if(onlineUserView == null){
                onlineUserView = new OnlineUserView();
                onlineUserView.setLoginIP(RequestUtils.getIpAddr(WebThreadLocalUtils.getRequest()));
                onlineUserView.setUserId(user.getId());
                onlineUserView.setUserName(user.getName());
                onlineUserView.addSessionId(session.getId());
            }

            // 添加到在线用户列表
            onlineUserCache.put(user.getId(),onlineUserView);
        }
    }

    /* 
     * session 属性移除时（即执行removeAttribute时）触发
     */
    @Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    /* 
     * session 属性更新时（即再次执行setAttribute时）触发
     */
    @Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
        if(event.getSession()==null){
            return;
        }

        // 当执行再次执行setAttribute("logonUserView")时，发生用户替换
        if(SessionKey.CURRENT_USER.equals(event.getName())){
        	 // 获取当前登陆用户信息及session
            User user = (User)event.getValue();
            HttpSession session = event.getSession();
            ValueWrapper cacheElement = onlineUserCache.get(user.getId());
            OnlineUserView onlineUserView = null;
            if (cacheElement != null) {
            	onlineUserView = (OnlineUserView) cacheElement.get();
                if (onlineUserView != null) {
                	onlineUserView.addSessionId(session.getId());
                }
            }
            
            // 创建在线用户信息对象
            if(onlineUserView == null){
                onlineUserView = new OnlineUserView();
                onlineUserView.setLoginIP(RequestUtils.getIpAddr(WebThreadLocalUtils.getRequest()));
                onlineUserView.setUserId(user.getId());
                onlineUserView.setUserName(user.getName());
                onlineUserView.addSessionId(session.getId());
            }
            initCache();
            onlineUserCache.put(user.getId(),onlineUserView);
        }
    }

    /* 
     * session 创建时触发
     */
    @Override
	public void sessionCreated(HttpSessionEvent event) {
    	initCache();
    }

    /* 
     * session 销毁时（即用户注销、session超时时）触发
     */
    @Override
	public void sessionDestroyed(HttpSessionEvent event) {
        if(event.getSession() == null){
            return;
        }
        // 获取当前登陆用户信息及session
        HttpSession session = event.getSession();
        User user = (User)session.getAttribute(SessionKey.CURRENT_USER);
        
        if(user != null){
        	initCache();
        	ValueWrapper cacheElement = onlineUserCache.get(user.getId());
            OnlineUserView onlineUserView = null;
            if (cacheElement != null) {
            	onlineUserView = (OnlineUserView) cacheElement.get();
                if (onlineUserView != null) {
                	onlineUserView.getSessionIds().remove(session.getId());
                	if(onlineUserView.getSessionIds().size() == 0){
                		onlineUserCache.evict(user.getId());
                	}
                	return;
                }
            }
        }
    }
}