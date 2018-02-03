package com.tmser.schevaluation.storage.service.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.schevaluation.storage.service.StorageService;
import com.tmser.schevaluation.utils.FileUtils;
/**
 * 
 *	撰写教案 存储远程调用服务
 * @author csj
 * @author tmser 2015年3月3日 下午14:38
 * @version $Id: StorageServiceImpl.java, v 1.0 2015年2月6日 下午1:57:24 csj Exp $
 */
public class LocalStorageServiceImpl implements StorageService {

	private static final Logger logger = LoggerFactory.getLogger(LocalStorageServiceImpl.class);
	//存储服务根目录
	public String basePath = "";

	/**
	 *	上传服务方法(通用上传)
	 * @param tempUrl  临时文件路径，本地文件路径
	 * @param fileName  文件全名
	 * @param aimUrl  此文件存储服务器文件存储相对路径，如没有则为空 
	 * @return 
	 */
	@Override
	public String upload(InputStream stream,String fileName,String aimUrl) {
        try {
        	if(StringUtils.isEmpty(aimUrl)) {
				throw new IllegalStateException("aimUrl cann't be null!");
			}
			File aimFile = new File(basePath,aimUrl);
			File parentFolder = aimFile.getParentFile();
			if(parentFolder != null  && !parentFolder.exists())
				parentFolder.mkdirs();
			FileUtils.copyInputStreamToFile(stream,aimFile);
	        logger.info(">> [save success ] " + aimUrl);
		} catch (Exception e) {
			logger.error("upload file failed ,filename = [{}] ", fileName);
			logger.error("",e);
			throw new RuntimeException("upload file failed",e);
		}
		return aimUrl;
	}
	/**
	 * 下载服务方法
	 * @param tempUrl  临时文件路径，本地文件路径
	 * @param aimUrl  此文件存储服务器文件存储相对路径，如没有则为空 
	 * @return 临时文件绝对路径
	 */
	@Override
	public String download(String aimUrl) {
        File f = new File(basePath, aimUrl);
		return f.getAbsolutePath();
	}
	
	/**
	 * 删除存储服务器文件
	 * @param aimUrl  存储服务器文件存储相对路径
	 * @return
	 */
	@Override
	public boolean delete(String aimUrl) {
		boolean flag = false;
		File file = new File(basePath,aimUrl);
		if(!file.exists()) {
			logger.info(">> [file doesn't exist] " + aimUrl);
			return true;
		}
		
		if(file.isFile()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @param filepath
	 * @return
	 * @see com.tmser.schevaluation.storage.service.StorageService#parseDevice(java.lang.String)
	 */
	@Override
	public String parseDevice(String filepath) {
		return "local";
	}
	
	public String getBasePath() {
		return basePath;
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	/**
	 * @param aimUrl
	 * @return
	 * @see com.tmser.schevaluation.storage.service.StorageService#localUrl(java.lang.String)
	 */
	@Override
	public String localUrl(String aimUrl) {
		return aimUrl;
	}
	
}
