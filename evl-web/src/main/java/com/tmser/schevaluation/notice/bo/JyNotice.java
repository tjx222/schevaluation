/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.notice.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.schevaluation.common.bo.BaseObject;
import com.tmser.schevaluation.notice.constants.NoticeType;

 /**
 * 通知模块 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-13 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyNotice.TABLE_NAME)
public class JyNotice extends BaseObject {
	public static final String TABLE_NAME="jy_notice";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	/**
	 *通知发送者id
	 **/
	@Column(name="sender_id")
	private Integer senderId;
	
	/**
	 *通知发送者用户名
	 **/
	@Column(name="sender_user_name",length=32)
	private String senderUserName;

	/**
	 *通知接受者id
	 **/
	@Column(name="receiver_id")
	private String receiverId;


	/**
	 *通知接受者用户名
	 **/
	@Column(name="receiver_user_name",length=32)
	private String receiverUserName;
	
	/**
	 *发送时间
	 **/
	@Column(name="send_date",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate;

	/**
	 *标题
	 **/
	@Column(name="title",length=200)
	private String title;

	/**
	 *通知详情
	 **/
	@Column(name="detail",length=21845)
	private String detail;

	/**
	 *发送者状态,1：已删除，0：未删除
	 **/
	@Column(name="sender_state")
	private Integer senderState;

	/**
	 *发送者更改状态时间
	 **/
	@Column(name="sender_state_change_date",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date senderStateChangeDate;

	/**
	 *接受者状态，2：已阅读，1：已删除，0：未阅读
	 **/
	@Column(name="receiver_state")
	private Integer receiverState;

	/**
	 *接受通知者更改状态时间
	 **/
	@Column(name="receiver_state_change_date",nullable=false)
	private Date receiverStateChangeDate;

	/**
	 *业务类型
	 **/
	@Column(name="business_type")
	private Integer businessType;

	/**
	 *详情类型：0、弹出框；1、新页面打开；2、url跳转
	 **/
	@Column(name="detail_type")
	private Integer detailType;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="parent_ids",length=200)
	private String parentIds;

	public String getTitlePrefix(){
		if(this.businessType!=null&&NoticeType.getByValue(businessType)!=null){
			return NoticeType.getByValue(businessType).getTitlePrefix();
		}
		return "";
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setSenderId(Integer senderId){
		this.senderId = senderId;
	}

	public Integer getSenderId(){
		return this.senderId;
	}

	public void setReceiverId(String receiverId){
		this.receiverId = receiverId;
	}

	public String getReceiverId(){
		return this.receiverId;
	}

	public void setSendDate(Date sendDate){
		this.sendDate = sendDate;
	}

	public Date getSendDate(){
		return this.sendDate;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return this.detail;
	}

	public void setSenderState(Integer senderState){
		this.senderState = senderState;
	}

	public Integer getSenderState(){
		return this.senderState;
	}

	public void setSenderStateChangeDate(Date senderStateChangeDate){
		this.senderStateChangeDate = senderStateChangeDate;
	}

	public Date getSenderStateChangeDate(){
		return this.senderStateChangeDate;
	}

	public void setReceiverState(Integer receiverState){
		this.receiverState = receiverState;
	}

	public Integer getReceiverState(){
		return this.receiverState;
	}

	public void setReceiverStateChangeDate(Date receiverStateChangeDate){
		this.receiverStateChangeDate = receiverStateChangeDate;
	}

	public Date getReceiverStateChangeDate(){
		return this.receiverStateChangeDate;
	}

	public void setBusinessType(Integer businessType){
		this.businessType = businessType;
	}

	public Integer getBusinessType(){
		return this.businessType;
	}

	public void setDetailType(Integer detailType){
		this.detailType = detailType;
	}

	public Integer getDetailType(){
		return this.detailType;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setParentIds(String parentIds){
		this.parentIds = parentIds;
	}

	public String getParentIds(){
		return this.parentIds;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof JyNotice))
				return false;
			JyNotice castOther = (JyNotice) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

}


