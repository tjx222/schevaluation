/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.resources.service;

import org.springframework.web.multipart.MultipartFile;

import com.mainbo.jy.manage.resources.bo.Resources;

/**
 * <pre>
 * web 资源服务接口
 * </pre>
 *
 * @author tmser
 * @version $Id: WebResourcesService.java, v 1.0 2015年11月27日 上午10:04:27 tmser Exp $
 */
public interface WebResourcesService {

	/**
	 * web 资源服务设备ID
	 * 资源将存储在 部署后web 目录中
	 */
	Integer WEB_DEVICE_ID = 0;
	
	String WEB_DEVICE_NAME = "web";
	
	
	/**
	 * 本地上传，上传成功资源将存储在web 目录中，其根目录可在配置文件中配置，<br/>
	 * 变量名为uploadBasePath，默认值为static/upload
	 * （如在application.properties 中 #uploadBasePath= static/upload），
	 *  资源记录会状态默认标识为临时文件， 在业务处理成功后需要调相关接口更新资源状态
	 * @param myfile
	 *            上传文件
	 * @param relativePath 
	 *            相对uploadBasePath制定的路径，上传文件保存相对路径，包含文件名称
	 * @return
	 * @see com.mainbo.jy.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	Resources saveWebResources(MultipartFile myfile,String relativePath );
	
	/**
	 *  删除web资源
	 */
	
	boolean deleteWebResources(String path);
}
