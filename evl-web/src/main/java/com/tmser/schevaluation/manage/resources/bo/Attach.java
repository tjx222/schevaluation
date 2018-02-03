/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.schevaluation.common.bo.BaseObject;

 /**
 * 集备主题研讨附件表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityAttach.java, v 1.0 2015-03-30 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Attach.TABLE_NAME)
public class Attach extends BaseObject {
	public static final String TABLE_NAME="sys_attach";
	public static final Integer JTBK = 0;//集体备课
	public static final Integer QYJY = 1;//区域教研
	public static final Integer XJJY = 2;//校际教研
	public static final Integer ZJZD = 3;//专家指导
	public static final Integer ZXGK = 4;//在线观课
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *集备id
	 **/
	@Column(name="activity_id")
	private Integer activityId;

	/**
	 * 集备类型（0：集体备课，1：区域教研  2：校际教研）
	 */
	@Column(name="activity_type")
	private Integer activityType;
	/**
	 *资源存储id
	 **/
	@Column(name="res_id")
	private String resId;

	/**
	 *附件名称
	 **/
	@Column(name="attach_name")
	private String attachName;

	/**
	 * 文件后缀名
	 */
	@Column(name="ext")
	private String ext;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public Integer getActivityId(){
		return this.activityId;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setAttachName(String attachName){
		this.attachName = attachName;
	}

	public String getAttachName(){
		return this.attachName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Attach))
				return false;
			Attach castOther = (Attach) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


