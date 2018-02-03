/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.nav;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: NavNamespaceHandler.java, v 1.0 2015年2月14日 下午3:06:50 tmser Exp $
 */
public class NavNamespaceHandler extends NamespaceHandlerSupport{

	/**
	 * 
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		registerBeanDefinitionParser("nav-context", new NavBeanDefinitionParser());
	}

}
