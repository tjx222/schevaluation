/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.meta;

/**
 * 元数据操作的常量
 * @author zpp
 * @version $Id: MetaDataConstants.java, v 1.0 2015年2月4日 上午11:25:58 zpp Exp $
 */
public class MetaDataConstants {
	
	 /**
     * 元数据缓存名称
     */
	public static final String METADATA_CACHE_NAME = "metaData";
	
	/**
	 * 书籍目录缓存名称
	 */
	public static final String BOOKCHAPTER_CACHE_NAME = "bookChapterData";
	
	/**
	 * 书籍对象缓存key前缀
	 */
	public static final String COMMIDITY_KEY_PRE = "com_key_";
	
	/**
	 * 元数据缓存名称
	 */
	public static final String METADATA_CACHE_KEY_ALL = "metaDataCacheKeyAll";
	
	/**
	 * 元数据可用状态
	 */
	public static final String METADATA_OK = "active";
	
	 /**
     * 学科元数据缓存的key
     */
	public static final String SUBJECT_METADATA_KEY = "subject_key";
	
	/**
	 * 学科元数据节点Id
	 */
	public static final Integer SUBJECT_METADATA_ID = 97;
	
	/**
	 * 不同类型元数据的前缀，通过类型的前缀和类型节点id的组拼作为缓存的key，如“metadata_type_97”就是学科
	 */
	public static final String METADATA_TYPE_PRE = "metadata_type_";
}
