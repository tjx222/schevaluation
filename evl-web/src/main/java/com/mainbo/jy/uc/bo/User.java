/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * 用户 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: User.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = User.TABLE_NAME)
public class User extends BaseObject {
	public static final String TABLE_NAME="sys_user";
	public static final Integer SCHOOL_USER = 0;
	public static final Integer AREA_USER = 1;
	public static final Integer SYS_USER = 2;
	public static final Integer EXPERT_USER = 3;
	
	@Id
	@Column(name="id")
	private Integer id;

	@Column(name="nickname",length=32)
	private String nickname;

	/**
	 *真实姓名
	 **/
	@Column(name="name",length=32)
	private String name;

	/**
	 *用户头像缩略图
	 **/
	@Column(name="photo",length=128)
	private String photo;

	/**
	 *头像原图
	 **/
	@Column(name="original_photo",length=128)
	private String originalPhoto;

	/**
	 *所属机构ID
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *手机
	 **/
	@Column(name="cellphone",length=32)
	private String cellphone;

	/**
	 *手机是否可见
	 **/
	@Column(name="cellphone_view")
	private Boolean cellphoneView;

	/**
	 *手机是否验证
	 **/
	@Column(name="cellphone_valid")
	private Boolean cellphoneValid;

	/**
	 *邮箱
	 **/
	@Column(name="mail",length=64)
	private String mail;

	/**
	 *邮箱是否验证
	 **/
	@Column(name="mail_valid")
	private Boolean mailValid;

	/**
	 *邮箱是否可见
	 **/
	@Column(name="mail_view")
	private Boolean mailView;

	/**
	 *用户类别
	 **/
	@Column(name="user_type")
	private Integer userType;

	/**
	 *所属机构名称
	 **/
	@Column(name="org_name",length=128)
	private String orgName;

	/**
	 *0非  1 是优秀教师
	 **/
	@Column(name="is_famous_teacher")
	private Integer isFamousTeacher;

	@Column(name="idcard",length=18)
	private String idcard;

	@Column(name="qq",length=15)
	private String qq;

	/**
	 *性别   0男 1女
	 **/
	@Column(name="sex")
	private Integer sex;

	/**
	 *出生日期
	 **/
	@Column(name="birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	/**
	 *教龄
	 **/
	@Column(name="school_age")
	private Integer schoolAge;

	/**
	 *职称
	 **/
	@Column(name="profession",length=32)
	private String profession;

	/**
	 *荣誉称号
	 **/
	@Column(name="honorary",length=32)
	private String honorary;

	/**
	 *个人简介
	 **/
	@Column(name="explains",length=1000)
	private String explains;

	/**
	 *备注
	 **/
	@Column(name="remark",length=200)
	private String remark;

	/**
	 *所属应用
	 **/
	@Column(name="app_id")
	private Integer appId;

	/**
	 *骨干教师
	 **/
	@Column(name="teacher_level",length=20)
	private String teacherLevel;

	/**
	 *地址
	 **/
	@Column(name="address",length=128)
	private String address;

	/**
	 *邮编
	 **/
	@Column(name="postcode",length=8)
	private String postcode;

	@Column(name="cercode",length=32)
	private String cercode;

	/**
	 *最后登录时间
	 **/
	@Column(name="last_login")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLogin;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return this.photo;
	}

	public void setOriginalPhoto(String originalPhoto){
		this.originalPhoto = originalPhoto;
	}

	public String getOriginalPhoto(){
		return this.originalPhoto;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setCellphone(String cellphone){
		this.cellphone = cellphone;
	}

	public String getCellphone(){
		return this.cellphone;
	}

	public void setCellphoneView(Boolean cellphoneView){
		this.cellphoneView = cellphoneView;
	}

	public Boolean getCellphoneView(){
		return this.cellphoneView;
	}

	public void setCellphoneValid(Boolean cellphoneValid){
		this.cellphoneValid = cellphoneValid;
	}

	public Boolean getCellphoneValid(){
		return this.cellphoneValid;
	}

	public void setMail(String mail){
		this.mail = mail;
	}

	public String getMail(){
		return this.mail;
	}

	public void setMailValid(Boolean mailValid){
		this.mailValid = mailValid;
	}

	public Boolean getMailValid(){
		return this.mailValid;
	}

	public void setMailView(Boolean mailView){
		this.mailView = mailView;
	}

	public Boolean getMailView(){
		return this.mailView;
	}

	public void setUserType(Integer userType){
		this.userType = userType;
	}

	public Integer getUserType(){
		return this.userType;
	}

	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	public String getOrgName(){
		return this.orgName;
	}

	public void setIsFamousTeacher(Integer isFamousTeacher){
		this.isFamousTeacher = isFamousTeacher;
	}

	public Integer getIsFamousTeacher(){
		return this.isFamousTeacher;
	}

	public void setIdcard(String idcard){
		this.idcard = idcard;
	}

	public String getIdcard(){
		return this.idcard;
	}

	public void setQq(String qq){
		this.qq = qq;
	}

	public String getQq(){
		return this.qq;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}

	public Date getBirthday(){
		return this.birthday;
	}

	public void setSchoolAge(Integer schoolAge){
		this.schoolAge = schoolAge;
	}

	public Integer getSchoolAge(){
		return this.schoolAge;
	}

	public void setProfession(String profession){
		this.profession = profession;
	}

	public String getProfession(){
		return this.profession;
	}

	public void setHonorary(String honorary){
		this.honorary = honorary;
	}

	public String getHonorary(){
		return this.honorary;
	}

	public void setExplains(String explains){
		this.explains = explains;
	}

	public String getExplains(){
		return this.explains;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setAppId(Integer appId){
		this.appId = appId;
	}

	public Integer getAppId(){
		return this.appId;
	}

	public void setTeacherLevel(String teacherLevel){
		this.teacherLevel = teacherLevel;
	}

	public String getTeacherLevel(){
		return this.teacherLevel;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return this.postcode;
	}

	public void setCercode(String cercode){
		this.cercode = cercode;
	}

	public String getCercode(){
		return this.cercode;
	}

	public void setLastLogin(Date lastLogin){
		this.lastLogin = lastLogin;
	}

	public Date getLastLogin(){
		return this.lastLogin;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof User))
				return false;
			User castOther = (User) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


