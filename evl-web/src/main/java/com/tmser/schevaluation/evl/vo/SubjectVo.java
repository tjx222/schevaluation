/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author dell
 * @version $Id: GradeAndClassVo.java, v 1.0 2016年5月24日 下午4:12:29 dell Exp $
 */
public class SubjectVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//学科信息
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
