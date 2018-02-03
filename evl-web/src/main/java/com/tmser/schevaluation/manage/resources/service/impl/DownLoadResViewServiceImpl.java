/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.service.impl;

import com.tmser.schevaluation.manage.resources.bo.Resources;
import com.tmser.schevaluation.manage.resources.service.ResViewService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class DownLoadResViewServiceImpl implements ResViewService{
	
	/**
	 * @param res
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResViewService#choseView(com.tmser.schevaluation.manage.resources.bo.Resources)
	 */
	@Override
	public String choseView(Resources res) {
		return "redirect:/jy/manage/res/download/"+res.getId();
	}


	/**
	 * 支持所有文件下载
	 * @param ext
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResViewService#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String ext) {
		return true;
	}

}
