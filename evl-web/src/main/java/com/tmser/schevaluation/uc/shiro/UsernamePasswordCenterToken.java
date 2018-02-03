/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: UsernamePasswordCenterToken.java, v 1.0 2015年12月18日 下午5:22:34 zpp Exp $
 */
@SuppressWarnings("serial")
public class UsernamePasswordCenterToken extends UsernamePasswordToken {

	private boolean isEcode;

	public UsernamePasswordCenterToken(){}
	
	public UsernamePasswordCenterToken(String username,String password){
		super(username,password);
	}
	/**
	 * Getter method for property <tt>isEcode</tt>.
	 * @return property value of isEcode
	 */
	public boolean isEcode() {
		return isEcode;
	}
	/**
	 * Setter method for property <tt>isEcode</tt>.
	 * @param isEcode value to be assigned to property isEcode
	 */
	public void setEcode(boolean isEcode) {
		this.isEcode = isEcode;
	}


}
