/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 方案表-实体 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SysSolution.java, v 1.0 2015-09-21 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SysSolution.TABLE_NAME)
public class SysSolution extends BaseObject {
	public static final String TABLE_NAME="sys_solution";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name",length=155)
	private String name;

	@Column(name="descs",length=250)
	private String descs;


	@Column(name="code",length=205)
	private String code;


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

	public void setDescs(String descs){
		this.descs = descs;
	}

	public String getDescs(){
		return this.descs;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SysSolution))
				return false;
			SysSolution castOther = (SysSolution) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


