/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.meta.vo;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: MetaData.java, v 1.0 2015年2月3日 下午2:24:54 zpp Exp $
 */
public class MetaData implements Serializable{
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 8249831685211925737L;
	/**
	 * 元数据ID
	 */
	private Integer metaDataId;
	/**
	 * 元数据名称
	 */
	private String metaName;
	/**
	 * 元数据名称的简称
	 */
	private String shortMetaName;
	/**
	 * 元数据父级节点Id
	 */
	private Integer parentId;
	/**
	 * 元数据子节点数量
	 */
	private Integer childCount;
	/**
	 * 元数据排序顺序
	 */
	private Integer orderby;
	
	public Integer getMetaDataId() {
		return metaDataId;
	}
	public void setMetaDataId(Integer metaDataId) {
		this.metaDataId = metaDataId;
	}
	public String getMetaName() {
		return metaName;
	}
	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
	public String getShortMetaName() {
		return shortMetaName;
	}
	public void setShortMetaName(String shortMetaName) {
		this.shortMetaName = shortMetaName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	
}
