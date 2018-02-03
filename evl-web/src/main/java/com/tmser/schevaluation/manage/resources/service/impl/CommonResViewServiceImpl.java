/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.manage.resources.bo.Resources;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class CommonResViewServiceImpl extends AbstractResViewServiceImpl{
	
	/**
	 * @param res
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResViewService#choseView(com.tmser.schevaluation.manage.resources.bo.Resources)
	 */
	@Override
	public void doView(Resources res) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		request.setAttribute("res", res);
	}

}
