/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.resources.service;

import com.mainbo.jy.manage.resources.bo.Resources;

/**
 * <pre>
 * 基础查看服务
 * </pre>
 *
 * @author tmser
 * @version $Id: ResViewService.java, v 1.0 2015年12月7日 下午2:27:29 tmser Exp $
 */
public interface ResViewService {

	/**
	 * 根据文件后缀，返回查看方式
	 * @param ext
	 * @return
	 */
	String choseView(Resources res);
	
	/**
	 * 判定是否支持改后缀文件预览
	 * @param ext
	 * @return
	 */
	boolean supports(String ext);
}
