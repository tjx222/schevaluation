/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;

 /**
 * 区域树 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Area.java, v 1.0 2015-05-07 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Area.TABLE_NAME)
public class Area extends QueryObject {
	public static final String TABLE_NAME="sys_area";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *名称
	 **/
	@Column(name="name",length=128)
	private String name;

	/**
	 *父区域
	 **/
	@Column(name="parent_id")
	private Integer parentId;

	/**
	 *邮政编码
	 **/
	@Column(name="postcode",length=32)
	private String postcode;

	/**
	 *区域编码
	 **/
	@Column(name="code",length=32)
	private String code;

	/**
	 *层级
	 **/
	@Column(name="level")
	private Integer level;

	/**
	 *排序
	 **/
	@Column(name="sort")
	private Integer sort;

	/**
	 *0  行政区域， 1 自定义区域
	 **/
	@Column(name="type")
	private Integer type;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return this.postcode;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Area))
				return false;
			Area castOther = (Area) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


