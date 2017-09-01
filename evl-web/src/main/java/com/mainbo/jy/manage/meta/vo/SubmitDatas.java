/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.meta.vo;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *	提交教案、课件、反思的封装对象
 * </pre>
 *
 * @author zpp
 * @version $Id: SubmitDatas.java, v 1.0 2015年3月17日 下午2:18:32 zpp Exp $
 */
public class SubmitDatas {
	/**
	 * BookLessonVo对象
	 */
	private BookLessonVo bookLessonVo;
	/**
	 * 是否为目录
	 * false(0):不是，true(1)：是
	 */
	private  Boolean isDirectory;
	/**
	 * map对象，存放最后一级的数据，包括资源类型数量和资源的集合
	 */
	private Map<String,Object> map;
	/**
	 * 存放子节点的数据，是目录的情况下才会有值
	 */
	private List<SubmitDatas> submitDatasList;
	public BookLessonVo getBookLessonVo() {
		return bookLessonVo;
	}
	public void setBookLessonVo(BookLessonVo bookLessonVo) {
		this.bookLessonVo = bookLessonVo;
	}
	public Boolean getIsDirectory() {
		return isDirectory;
	}
	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public Map<String,Object> getMap() {
		return map;
	}
	public void setMap(Map<String,Object> map) {
		this.map = map;
	}
	public List<SubmitDatas> getSubmitDatasList() {
		return submitDatasList;
	}
	public void setSubmitDatasList(List<SubmitDatas> submitDatasList) {
		this.submitDatasList = submitDatasList;
	}
	
}
