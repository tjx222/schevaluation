package com.tmser.schevaluation.storage.service;

import java.io.InputStream;

public interface StorageService {

	/**
	 *	上传服务方法(通用上传)
	 * @param stream  文件流
	 * @param fileName  文件全名
	 * @param aimUrl  此文件存储服务器文件存储相对路径，如没有则为空 
	 * @return 文件存储真实路径
	 */
	String upload(InputStream stream, String fileName,String aimUrl);
	
	/**
	 * 根据文件路径分析服务器标识
	 * @param filepath
	 * @return
	 */
	String parseDevice(String filepath);

	/**
	 * 下载服务方法   
	 * @param aimUrl  此文件存储服务器文件存储相对路径，如没有则为空 
	 * @return 文件存储路径（绝对路径）
	 */
	String download(String aimUrl);
	
	/**
	 * 本地ulr地址， 如文件存储在跨域服务器上时，需要先下载到本地临时目录。
	 * @param aimUrl  此文件存储服务器文件存储相对路径，如没有则为空 
	 * @return 临时文件存储路径（绝对路径）
	 */
	String localUrl(String aimUrl);
	
	/**
	 * 删除文件
	 * @param aimUrl  存储服务器文件存储相对路径
	 * @return
	 */
	boolean delete(String aimUrl);

}
