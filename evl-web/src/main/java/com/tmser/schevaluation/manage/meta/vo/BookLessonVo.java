/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.meta.vo;



import java.io.Serializable;
import java.util.List;

 /**
 * 书籍的章节目录缓存bean
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: BookChapterVo.java, v 1.0 2015-02-04 zpp Exp $
 */
public class BookLessonVo implements Serializable{
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -8181612020919887986L;

	
	/**
	 *书籍章节目录主键
	 **/
	private String lessonId;

	/**
	 *书籍章节目录名称
	 **/
	private String lessonName;

	/**
	 *所属的书籍ID
	 **/
	private String comId;

	/**
	 *父节点Id
	 **/
	private String parentId;

	/**
	 *章节顺序，排序用
	 **/
	private Integer orderNum;
	
	/**
	 *是否为叶子节点数据
	 **/
	private Boolean isLeaf;
	
	/**
	 *自身集合，数据特殊处理所用，展示课题的层次结构
	 **/
	private List<BookLessonVo> bookLessons;


	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<BookLessonVo> getBookLessons() {
		return bookLessons;
	}

	public void setBookLessons(List<BookLessonVo> bookLessons) {
		this.bookLessons = bookLessons;
	}
	

}


