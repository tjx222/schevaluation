/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.vo;

import java.io.Serializable;
import java.util.List;

import com.tmser.schevaluation.schconfig.clss.bo.SchClass;

/**
 * <pre>
 *
 * </pre>
 *
 * @author dell
 * @version $Id: GradeAndClassVo.java, v 1.0 2016年5月24日 下午4:12:29 dell Exp $
 */
@SuppressWarnings("serial")
public class GradeAndClassVo implements Serializable{
	//年级信息
	private Integer id;
	private String name;
	//班级信息集合
	private List<SchClass> classes;
	
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
	public List<SchClass> getClasses() {
		return classes;
	}
	public void setClasses(List<SchClass> classes) {
		this.classes = classes;
	}
}
