/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.manage.resources.bo.Resources;

/**
 * <pre>
 * 资源存储表 服务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: Resources.java, v 1.0 2015-02-07 wangdawei Exp $
 */

public interface ResourcesService extends BaseService<Resources, String>,WebResourcesService{
	/**
	 * @param stream
	 *            上传文件流
	 * @param fileName
	 *            上传文件名称
	 * @param fileSize
	 *            上传文件大小
	 * @param relativePath
	 *            上传文件保存相对路径，包含文件名称
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	Resources saveResources(InputStream stream, String fileName,Long fileSize,String relativePath);
	
	
	/**
	 * 本地上传，作为web 资源上传到web 目录下
	 * @param stream
	 *            上传文件流
	 * @param fileName
	 *            上传文件名称
	 * @param fileSize
	 *            上传文件大小
	 * @param relativePath
	 *            上传文件保存相对路径，包含文件名称
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	Resources saveTmptResources(MultipartFile myfile,String relativePath);
	
	/**
	 * 更新临时文件为普通文件
	 * @param oldResId 原资源文件id
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	Resources updateTmptResources(String oldResId);
	
	/**
	 * 
	 * 更新资源
	 * @param stream
	 *            上传文件流
	 * @param fileName
	 *            上传文件名称
	 * @param fileSize
	 *            上传文件大小
	 * @param relativePath
	 *            上传文件保存相对路径，包含文件名称
	 * @param oldResId 原资源文件id
	 * @return
	 * @see com.tmser.schevaluation.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	Resources updateResources(InputStream stream, String fileName,Long fileSize,String relativePath,String oldResId);
	
	/**
	 * 查看资源
	 * @param resId
	 * @return
	 */
	String viewResources(String resId);
	
	/**
	 *  删除资源
	 */
	boolean deleteResources(String resid);
	
	/**
	 * 下载文件
	 * @param resid 资源id
	 * @return 资源绝对路径
	 */
	String download(String resid);
	
	/**
	 * 复制资源文件
	 * @param resid  资源id
	 * @return 新资源文件resId
	 */
	String copyRes(String resid) ;
	
	/**
	 * 缩放图片文件。
	 * @param resources
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	String resizeImage(Resources resources,int newWidth,int newHeight);
	
}
