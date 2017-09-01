/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.exception;

import com.mainbo.jy.utils.exception.BaseException;

/**
 * <pre>
 * uc 模块基础异常
 * </pre>
 *
 * @author tmser
 * @version $Id: UcException.java, v 1.0 2015年1月23日 下午8:09:18 tmser Exp $
 */
public class UcException extends BaseException{

	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -5944368378936037362L;

	/**
	 * @param module
	 * @param code
	 * @param args
	 * @param defaultMessage
	 */
	public UcException(String code, Object[] args,String defaultMessage) {
		super("uc", code, args, defaultMessage);
	}
	
	/**
	 * @param module
	 * @param code
	 * @param args
	 * @param defaultMessage
	 */
	public UcException(String code, Object[] args) {
		this(code, args,"");
	}

}
