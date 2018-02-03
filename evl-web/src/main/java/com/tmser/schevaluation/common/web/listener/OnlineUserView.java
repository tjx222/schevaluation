/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.listener;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  用户登录信息
 * </pre>
 *
 * @author tmser
 * @version $Id: OnlineUserView.java, v 1.0 2016年5月31日 下午3:53:52 tmser Exp $
 */
public class OnlineUserView implements java.io.Serializable {
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 2071664709509692807L;
	private Integer userId;
	private String userName;
	private String loginIP;
	private Set<String> sessionIds = new HashSet<String>();
	/** 
	 * Getter method for property <tt>userId</tt>. 
	 * @return property value of userId 
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * Setter method for property <tt>userId</tt>.
	 * @param userId value to be assigned to property userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/** 
	 * Getter method for property <tt>userName</tt>. 
	 * @return property value of userName 
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Setter method for property <tt>userName</tt>.
	 * @param userName value to be assigned to property userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/** 
	 * Getter method for property <tt>loginIP</tt>. 
	 * @return property value of loginIP 
	 */
	public String getLoginIP() {
		return loginIP;
	}
	/**
	 * Setter method for property <tt>loginIP</tt>.
	 * @param loginIP value to be assigned to property loginIP
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	/** 
	 * Getter method for property <tt>sessionId</tt>. 
	 * @return property value of sessionId 
	 */
	public Set<String> getSessionIds() {
		return sessionIds;
	}
	/**
	 * Setter method for property <tt>sessionId</tt>.
	 * @param sessionId value to be assigned to property sessionId
	 */
	public void addSessionId(String sessionId) {
		this.sessionIds.add(sessionId);
	}
	
	
}
