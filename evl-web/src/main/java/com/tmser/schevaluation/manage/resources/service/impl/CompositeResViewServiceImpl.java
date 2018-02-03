/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.service.impl;

import java.util.List;

import com.tmser.schevaluation.manage.resources.bo.Resources;
import com.tmser.schevaluation.manage.resources.service.ResViewService;

/**
 * <pre>
 * 复合文件查看服务类
 * 转发用户到用户配置的查看服务 @ResViewService
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class CompositeResViewServiceImpl implements ResViewService{
	
	/**
	 * 
	 */
	private List<ResViewService> viewServices;
	

	private ResViewService defaultViewServices = new DownLoadResViewServiceImpl();
	/**
	 * @param res
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResViewService#choseView(com.tmser.schevaluation.manage.resources.bo.Resources)
	 */
	@Override
	public String choseView(Resources res) {
		for(ResViewService rs : viewServices){
			if(rs.supports(res.getExt())){
				return rs.choseView(res);
			}
		}
		return defaultViewServices.choseView(res);
	}


	/**
	 * 支持所有文件
	 * @param ext
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResViewService#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String ext) {
		return true;
	}


	public List<ResViewService> getViewServices() {
		return viewServices;
	}


	public void setViewServices(List<ResViewService> viewServices) {
		this.viewServices = viewServices;
	}


	public ResViewService getDefaultViewServices() {
		return defaultViewServices;
	}


	public void setDefaultViewServices(ResViewService defaultViewServices) {
		this.defaultViewServices = defaultViewServices;
	}
	
	

}
