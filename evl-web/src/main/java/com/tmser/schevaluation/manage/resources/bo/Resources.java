/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.manage.resources.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.schevaluation.common.bo.QueryObject;

 /**
 * 资源存储表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: Resources.java, v 1.0 2015-02-07 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Resources.TABLE_NAME)
public class Resources extends QueryObject {
	public static final String TABLE_NAME="sys_resources";
	
	/**
	 * 普通文件
	 */
	public static final int S_NORMAL = 1;
	
	/**
	 * 临时文件
	 */
	public static final int S_TEMP = 0;
	
		/**
	 *主键资源id
	 **/
	@Id
	@Column(name="id")
	private String id;

	/**
	 *资源存储的相对路径（包含文件名和后缀）
	 **/
	@Column(name="path")
	private String path;

	/**
	 *资源的真实名字（不包含后缀）
	 **/
	@Column(name="name")
	private String name;

	/**
	 *资源大小（字节）
	 **/
	@Column(name="size")
	private Long size;

	/**
	 *后缀（如：doc，不含点）
	 **/
	@Column(name="ext")
	private String ext;

	/**
	 *服务器id
	 **/
	@Column(name="device_id")
	private Integer deviceId;

	/**
	 *服务器名称
	 **/
	@Column(name="device_name")
	private String deviceName;

	/**
	 *资源状态
	 **/
	@Column(name="state")
	private Integer state;
	
	/**
	 * 创建时间
	 */
	@Column(name="crt_dttm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crtDttm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Resources))
				return false;
			Resources castOther = (Resources) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


