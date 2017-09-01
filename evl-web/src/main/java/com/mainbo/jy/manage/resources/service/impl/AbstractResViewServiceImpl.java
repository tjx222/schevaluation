/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.resources.service.impl;

import java.util.Set;

import com.mainbo.jy.manage.resources.bo.Resources;
import com.mainbo.jy.manage.resources.service.ResViewService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public abstract class AbstractResViewServiceImpl implements ResViewService{
	
	/**
	 * 支持的后缀列表
	 */
	private Set<String> supportExts;
	
	/**
	 * 返回的页面
	 */
	private String viewName;
	
	/**
	 * @param res
	 * @return
	 * @see com.mainbo.jy.manage.resources.service.ResViewService#choseView(com.mainbo.jy.manage.resources.bo.Resources)
	 */
	@Override
	public String choseView(Resources res) {
		doView(res);
		return viewName;
	}

	/**
	 * 支持所有文件下载
	 * @param ext
	 * @return
	 * @see com.mainbo.jy.manage.resources.service.ResViewService#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String ext) {
		return supportExts != null && ext != null 
				&& supportExts.contains(ext.toLowerCase());
	}
	
	
	public abstract void doView(Resources res);

	public Set<String> getSupportExts() {
		return supportExts;
	}

	public void setSupportExts(Set<String> supportExts) {
		this.supportExts = supportExts;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	

}
