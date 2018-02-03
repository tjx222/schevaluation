/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.bo;



import java.util.Date;

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
 * 短息发送状态记录 Entity
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: EvlSms.java, v 1.0 2017-05-26 ljh Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = EvlSms.TABLE_NAME)
public class EvlSms extends BaseObject {
	public static final String TABLE_NAME="evl_sms";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="phone",length=32)
	private String phone;

	@Column(name="count")
	private Integer count;
	
	@Column(name="time")
	private Date time;


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setCount(Integer count){
		this.count = count;
	}

	public Integer getCount(){
		return this.count;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof EvlSms))
				return false;
			EvlSms castOther = (EvlSms) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


