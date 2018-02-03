/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.bo;

import java.util.Date;


import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 推送资源表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ResRecommend.java, v 1.0 2015-03-27 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ResRecommend.TABLE_NAME)
public class ResRecommend extends BaseObject {
	public static final String TABLE_NAME="jy_recommend_res";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="res_id",nullable=false)
	private String resId;

	/**
	 *资源显示标题
	 **/
	@Column(name="title")
	private String title;

	/**
	 *0教案
  1课件 
 2习题  3 素材
	 **/
	@Column(name="res_type")
	private Integer resType;
	
	/**
	 * 字典表对应资源类型
	 *
	 *182教案
  	 181课件 
 	 180习题 
 	 183 素材
	 **/
	@Column(name="dic_type")
	private Integer dicType;

	/**
	 *素材下的子分类 ：0文档  1图片  2 音频  3 视频
	 **/
	@Column(name="res_second_type")
	private Integer resSecondType;

	/**
	 *格式id   元素据id
            word excel  ppt  txt pdf  flash  mp3  avi  jpg  其他  
	 **/
	@Column(name="ext")
	private String ext;

	/**
	 *创建者ID
	 **/
	@Column(name="upload_user_id")
	private Integer uploadUserId;

	/**
	 *创建者名称
	 **/
	@Column(name="upload_user_name")
	private String uploadUserName;

	/**
	 *创建时间
	 **/
	@Column(name="upload_time")
	private Date uploadTime;

	/**
	 *书籍ID
	 **/
	@Column(name="book_id")
	private String bookId;

	/**
	 *章节ID
	 **/
	@Column(name="lesson_id")
	private String LessonId;

	/**
	 *下载量
	 **/
	@Column(name="down_numb")
	private Integer downNumb;

	@Column(name="upload_org_id")
	private Integer uploadOrgId;

	@Column(name="upload_org_name")
	private String uploadOrgName;

	/**
	 *修改时间  资源内容修改时间
	 **/
	@Column(name="modified_time")
	private Date modifiedTime;

	/**
	 * 资源质量，0：非优质资源  >0:优质资源
	 */
	@Column(name="qualify")
	private Integer qualify;
	
	/**
	 * 显示顺序
	 */
	@Column(name="sort")
	private Integer sort;
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setResSecondType(Integer resSecondType){
		this.resSecondType = resSecondType;
	}

	public Integer getResSecondType(){
		return this.resSecondType;
	}

	public void setExt(String ext){
		this.ext = ext;
	}

	public String getExt(){
		return this.ext;
	}

	public void setUploadUserId(Integer uploadUserId){
		this.uploadUserId = uploadUserId;
	}

	public Integer getUploadUserId(){
		return this.uploadUserId;
	}

	public void setUploadUserName(String uploadUserName){
		this.uploadUserName = uploadUserName;
	}

	public String getUploadUserName(){
		return this.uploadUserName;
	}

	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}

	public Date getUploadTime(){
		return this.uploadTime;
	}

	public void setBookId(String bookId){
		this.bookId = bookId;
	}

	public String getBookId(){
		return this.bookId;
	}

	public void setDownNumb(Integer downNumb){
		this.downNumb = downNumb;
	}

	public Integer getDownNumb(){
		return this.downNumb;
	}

	public void setUploadOrgId(Integer uploadOrgId){
		this.uploadOrgId = uploadOrgId;
	}

	public Integer getUploadOrgId(){
		return this.uploadOrgId;
	}

	public void setUploadOrgName(String uploadOrgName){
		this.uploadOrgName = uploadOrgName;
	}

	public String getUploadOrgName(){
		return this.uploadOrgName;
	}

	public void setModifiedTime(Date modifiedTime){
		this.modifiedTime = modifiedTime;
	}

	public Date getModifiedTime(){
		return this.modifiedTime;
	}

	public String getLessonId() {
		return LessonId;
	}

	public void setLessonId(String lessonId) {
		LessonId = lessonId;
	}
	

	public Integer getDicType() {
		return dicType;
	}

	public void setDicType(Integer dicType) {
		this.dicType = dicType;
	}

	public Integer getQualify() {
		return qualify;
	}

	public void setQualify(Integer qualify) {
		this.qualify = qualify;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof ResRecommend))
				return false;
			ResRecommend castOther = (ResRecommend) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


