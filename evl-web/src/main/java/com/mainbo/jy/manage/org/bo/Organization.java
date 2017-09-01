/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.manage.org.bo;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mainbo.jy.common.bo.BaseObject;

 /**
 * zuzu Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Organization.java, v 1.0 2015-05-07 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Organization.TABLE_NAME)
public class Organization extends BaseObject {
	public static final String TABLE_NAME="sys_organization";
	
	public static final int ENABLE = 1;
	
	public static final int DISABLE = 0;
	public static final int SCHOOL = 0;//学校
	public static final int DEPT = 2;//部门
	public static final int UNIT = 1;//区域
	
	/**
	 *主键
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *机构编码
	 **/
	@Column(name="code",length=25)
	private String code;

	/**
	 *父节点
	 **/
	@Column(name="parent_id")
	private Integer parentId;

	/**
	 *机构简称
	 **/
	@Column(name="short_name",length=32)
	private String shortName;

	/**
	 *机构全称
	 **/
	@Column(name="name",length=64)
	private String name;

	
	/**
	 *机构所属区域层级关系表
	 **/
	@Column(name="area_ids",length=1024)
	private String areaIds;
	
	
	/**
	 *所属区域id
	 **/
	@Column(name="area_id")
	private Integer areaId;

	/**
	 *所属区域名称
	 **/
	@Column(name="area_name",length=256)
	private String areaName;
	
	/**
	 *所属学段类型列表
	 **/
	@Column(name="phase_types",length=32)
	private String phaseTypes;

	/**
	 *机构地址
	 **/
	@Column(name="address",length=64)
	private String address;

	/**
	 *联系人
	 **/
	@Column(name="contact_person",length=64)
	private String contactPerson;

	/**
	 *联系电话
	 **/
	@Column(name="phone_number",length=64)
	private String phoneNumber;

	/**
	 *说明
	 **/
	@Column(name="note",length=512)
	private String note;

	/**
	 *机构级别
	 **/
	@Column(name="level")
	private Integer level;

	/**
	 *机构级别名称
	 **/
	@Column(name="org_level_name",length=128)
	private String orgLevelName;

	/**
	 *机构类型
	 **/
	@Column(name="type",nullable=false)
	private Integer type;

	/**
	 *机构类型名称
	 **/
	@Column(name="type_name",length=64)
	private String typeName;
	/**
	 * 学制
	 **/
	@Column(name="system_id")
	private Integer systemId;

	/**
	 * 机构类别 0->会员校；1->体验校；2->演示校
	 */
	@Column(name="org_type")
	private Integer orgType;
	
	/**
	 * 英文名称
	 */
	@Column(name="english_name",length=64)
	private String englishName;
	
	/**
	 * 电子邮箱
	 */
	@Column(name="email",length=64)
	private String email;
	
	/**
	 * 学校网址
	 */
	@Column(name="sch_website",length=1024)
	private String schWebsite;
	/**
	 * 直属区域id    
	 */
	@Column(name="direct_area_id")
	private Integer directAreaId;
	/**
	 * 直属级别id
	 */
	@Column(name="dirlevel_id")
	private Integer dirLevelId;
	
	/**
	 * 平台对接过来的orgid
	 */
	@Column(name="trdparty_org_id")
	private String trdpartyOrgId;
	
	public String getTrdpartyOrgId() {
		return trdpartyOrgId;
	}

	public void setTrdpartyOrgId(String trdpartyOrgId) {
		this.trdpartyOrgId = trdpartyOrgId;
	}
	
	@Transient
	private List<OrganizationRelationship> orsList;
	
	/** 
	 * Getter method for property <tt>orsList</tt>. 
	 * @return property value of orsList 
	 */
	public List<OrganizationRelationship> getOrsList() {
		return orsList;
	}

	/**
	 * Setter method for property <tt>orsList</tt>.
	 * @param orsList value to be assigned to property orsList
	 */
	public void setOrsList(List<OrganizationRelationship> orsList) {
		this.orsList = orsList;
	}

	public String getEnglishName() {
		return englishName;
	}

	public Integer getDirectAreaId() {
		return directAreaId;
	}

	public void setDirectAreaId(Integer directAreaId) {
		this.directAreaId = directAreaId;
	}

	public Integer getDirLevelId() {
		return dirLevelId;
	}

	public void setDirLevelId(Integer dirLevelId) {
		this.dirLevelId = dirLevelId;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchWebsite() {
		return schWebsite;
	}

	public void setSchWebsite(String schWebsite) {
		this.schWebsite = schWebsite;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	/**
	 *会员级别： 0 是非会员，其他为不同级别会员
	 **/
	@Column(name="viplevel")
	private Integer vipLevel;
	public Integer getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}

	/**
	 *排序
	 **/
	@Column(name="sort",nullable=false)
	private Integer sort;

	/**
	 *机构树的层级（从1开始）
	 **/
	@Column(name="tree_level",nullable=false)
	private Integer treeLevel;

	/**
	 *所在区域id,如直属校所在区域
	 **/
	@Column(name="location_area_id")
	private Integer locationAreaId;

	/**
	 *学制
	 **/
	@Column(name="schoolings")
	private Integer schoolings;

	/**
	 *机构logo
	 **/
	@Column(name="logo",length=256)
	private String logo;

	/**
	 *机构类别：如学校：分直属校
	 **/
	@Column(name="category")
	private Integer category;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public String getPhaseTypes() {
		return phaseTypes;
	}

	public void setPhaseTypes(String phaseTypes) {
		this.phaseTypes = phaseTypes;
	}

	public Integer getParentId(){
		return this.parentId;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return this.shortName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public void setAreaId(Integer areaId){
		this.areaId = areaId;
	}

	public Integer getAreaId(){
		return this.areaId;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return this.areaName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setContactPerson(String contactPerson){
		this.contactPerson = contactPerson;
	}

	public String getContactPerson(){
		return this.contactPerson;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return this.phoneNumber;
	}

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return this.note;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

	public void setOrgLevelName(String orgLevelName){
		this.orgLevelName = orgLevelName;
	}

	public String getOrgLevelName(){
		return this.orgLevelName;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setTreeLevel(Integer treeLevel){
		this.treeLevel = treeLevel;
	}

	public Integer getTreeLevel(){
		return this.treeLevel;
	}

	public void setLocationAreaId(Integer locationAreaId){
		this.locationAreaId = locationAreaId;
	}

	public Integer getLocationAreaId(){
		return this.locationAreaId;
	}

	public void setSchoolings(Integer schoolings){
		this.schoolings = schoolings;
	}

	public Integer getSchoolings(){
		return this.schoolings;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return this.logo;
	}

	public void setCategory(Integer category){
		this.category = category;
	}

	public Integer getCategory(){
		return this.category;
	}

	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Organization))
				return false;
			Organization castOther = (Organization) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


