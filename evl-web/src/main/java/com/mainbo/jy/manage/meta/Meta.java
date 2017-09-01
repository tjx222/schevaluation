/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta;

/**
 * <pre>
 *  原数据属性接口
 * </pre>
 *
 * @author tmser
 * @version $Id: Meta.java, v 1.0 2016年9月30日 上午10:59:40 tmser Exp $
 */
public interface Meta {

	/**
	 * 元数据id
	 * @return
	 */
	Integer getId();

    /**
     * 原数据名称
     * @return
     */
	String getName();

	/**
	 * 原数据父级id
	 * @return
	 */
	Integer getParentId();

	/**
	 * @return
	 */
	Integer getChildCount();

	/**
	 * @return
	 */
	Integer getDicOrderby();
}
