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
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.QueryObject;

 /**
 * 用户登录信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Login.java, v 1.0 2015-01-30 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Login.TABLE_NAME)
public class Login extends QueryObject {
	public static final String TABLE_NAME="sys_login";
	
    public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|17[0-9]|18[0-9])[0-9]{8}$";
    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 50;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="loginname",length=32)
	private String loginname;

	@Column(name="password")
	private String password;

	@Column(name="cellphone")
	private String cellphone;

	@Column(name="mail")
	private String mail;

	/**
	 * 有效性
	 */
	@Column(name="enable")
	private Integer enable;

	/**
	 *是否删除
	 **/
	@Column(name="deleted")
	private Boolean deleted;
	
	/**
	 *是否删除
	 **/
	@Column(name="is_admin")
	private Boolean isAdmin;
	
	
	@Column(name="logincode",length=64)
	private String logincode;
	/**
	 *安全盐值，根据 注册时用户名生成，只生成一次
	 **/
	@Column(name="salt")
	private String salt;
	
	
	/**
	 * 用户信息
	 */
	@Transient
	private User user;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setLoginname(String loginname){
		this.loginname = loginname;
	}

	public String getLoginname(){
		return this.loginname;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setCellphone(String cellphone){
		this.cellphone = cellphone;
	}

	public String getCellphone(){
		return this.cellphone;
	}

	public void setMail(String mail){
		this.mail = mail;
	}

	public String getMail(){
		return this.mail;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setSalt(String salt){
		this.salt = salt;
	}

	public String getSalt(){
		return this.salt;
	}

	public Boolean getIsAdmin(){
		return isAdmin;
	}
	
	public void setIsAdmin(Boolean isAdmin){
		this.isAdmin = isAdmin;
	}

	/**
	 * 有效性
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * 有效性
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	public String getLogincode() {
		return logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}
	
	/** 
	 * Getter method for property <tt>user</tt>. 
	 * @return property value of user 
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter method for property <tt>user</tt>.
	 * @param user value to be assigned to property user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Login))
				return false;
			Login castOther = (Login) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


