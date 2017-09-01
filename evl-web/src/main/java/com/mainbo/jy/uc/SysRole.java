/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.uc;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 系统角色
 * </pre>
 *
 * @author tmser
 * @version $Id: SysRole.java, v 1.0 2015年2月10日 下午3:51:57 tmser Exp $
 */
public enum SysRole {

	ADMIN(243,"超级管理员"),
	TEACHER(27,"教师"),
	STUFENT(26,"学生"),
	PATRIARCH(244,"家长"),
	BKZZ(1373,"备课组长"),
	NJZZ(1374,"年级组长"),
	XKZZ(1375,"学科组长"),
	ZR(1376,"主任"),
	FXZ(2000,"副校长"),
	XZ(1377,"校长"),
	
	JYY(1378,"教研员"),
	FJYZR(1379,"副教研主任"),
	JYZR(1380,"教研主任"),
	
	BU_HEAD(1381,"单位领导"),
	PROFESSOR(1382,"专家"),
	
	YWGLY(2001,"运维管理员"),	
	QYGLY(2002,"区域管理员"),	
	XXGLY(2003,"学校管理员"),	
	UNKNOWN(0,"未知");
	
	
	/**
	 * 中文名
	 */
	 private String cname;
	 /**
	  * id 关联
	  */
	 private Integer id;
	 
	 public String getCname() {
		return cname;
	}

	public Integer getId() {
		return id;
	}

	SysRole(Integer id,String cname){
		 this.cname = cname;
		 this.id = id;
	}
	 
	public static SysRole findByCname(String cname){
		 SysRole sr = UNKNOWN;
		 for(SysRole s : values()){
			 if(s.cname.equals(cname)){
				 sr = s;
				 break;
			 }
		 }
		 return sr;
	 }
	 
	 /**
	  * 通过角色id获取角色名称
	  * @param roleId
	  * @return
	  */
	 public static String getCname(Integer roleId){
		 SysRole[] roles = SysRole.values();
		 for(int i=0;i<roles.length;i++){
			 if(roles[i].id.equals(roleId)){
				 return roles[i].cname;
			 }
		 }
		 return null;
	 }
	 
	 
	 /**
	  * 通过角色id获取角色名称
	  * @param roleId
	  * @return
	  */
	 public static SysRole getRoleById(Integer roleId){
		 SysRole[] roles = SysRole.values();
		 for(int i=0;i<roles.length;i++){
			 if(roles[i].id.equals(roleId)){
				 return roles[i];
			 }
		 }
		 return null;
	 }
	 
	/**
	 * 检查角色
	 * @param roleid 
	 * @param roleName
	 * @return
	 */
	 public static final boolean checkSysRole(Integer roleid,String roleName){
		 if(roleName != null){
			SysRole role;
			try {
				role = SysRole.valueOf(roleName.toUpperCase());
			} catch (Exception e) {
				role =  SysRole.UNKNOWN;
			}
			return role != SysRole.UNKNOWN && role.getId().equals(roleid);
		 }	 
		 return false;
	 }
	 
	 /**
	 * 获得所有角色集合
	 * @return
	 */
     public static final Map<Integer,String> getAllSysRole() {
    	 Map<Integer,String> map = new HashMap<Integer,String>();
    	 for(SysRole s : values()){
    		 map.put(s.getId(), s.getCname());
		 }
         return map;
     }
}
