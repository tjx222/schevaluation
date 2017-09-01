package com.mainbo.jy.evl.utils.freemarker;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;

/**
 * freemarker配置
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: FreemarkerConfiguration.java, v 1.0 2016年6月3日 上午10:25:05
 *          gengqianfeng Exp $
 */
public class FreemarkerConfiguration {

	private final static Logger logger = LoggerFactory
			.getLogger(FreemarkerConfiguration.class);

	private static Configuration config = null;

	/**
	 * FreemarkerConfiguration
	 * 
	 * @return
	 */
	public static synchronized Configuration getConfiguation() {
		if (config == null) {
			setConfiguation();
		}
		return config;
	}

	/**
	 * 设置freemarker配置
	 */
	private static void setConfiguation() {
		config = new Configuration();
		String path = ResourceLoader.getPath("");
		logger.info("path=" + path);
		try {
			config.setDirectoryForTemplateLoading(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}