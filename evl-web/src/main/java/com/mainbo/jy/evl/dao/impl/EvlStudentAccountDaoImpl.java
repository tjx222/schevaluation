/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mainbo.jy.common.bo.QueryObject.NamedConditon;
import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.evl.dao.EvlStudentAccountDao;
import com.mainbo.jy.utils.StringUtils;

/**
 * 学生账号表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlStudentAccount.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Repository
public class EvlStudentAccountDaoImpl extends AbstractDAO<EvlStudentAccount,String> implements EvlStudentAccountDao {
	/**
	 * 重载方法 查询学生是关联ClassStudent中间表
	 * @param model
	 * @return
	 * @see com.mainbo.jy.common.dao.AbstractDAO#listAll(com.mainbo.jy.common.bo.QueryObject)
	 */
	@Override
	public	List<EvlStudentAccount> listAll(EvlStudentAccount model,ClassStudent classModel){
		Map<String,Object> argMap = new HashMap<String, Object>();
		StringBuilder sqlBuffer=new StringBuilder();
		sqlBuffer.append("select s.id id,s.name name,s.code code,s.sex sex,s.cellphone cellphone,c.classId class_id,c.gradeId grade_id, c.schoolYear school_year,c.className class_name,s.orgId org_id from ClassStudent c left join EvlStudentAccount s on c.studentId=s.id where 1=1");
		//班级条件拼凑
		if(classModel!=null){
			if(StringUtils.isNotEmpty(classModel.getClassName())){
				sqlBuffer.append("and c.className  =:className ");
				argMap.put("className", classModel.getClassName());
			}
			if(classModel.getClassId()!=null){
				sqlBuffer.append("and c.classId  =:classId ");
				argMap.put("classId", classModel.getClassId());
			}
			if(classModel.getGradeId()!=null){
				sqlBuffer.append("and c.gradeId  =:gradeId ");
				argMap.put("gradeId", classModel.getGradeId());
			}
			if(classModel.getSchoolYear()!=null){
				sqlBuffer.append("and c.schoolYear  =:schoolYear ");
				argMap.put("schoolYear", classModel.getSchoolYear());
			}
			if (!CollectionUtils.isEmpty(classModel.getClassIds())) {
				sqlBuffer.append("and c.classId in (:classIds) ");
				argMap.put("classIds", classModel.getClassIds());
			}
			if (!CollectionUtils.isEmpty(classModel.getGradeIds())) {
				sqlBuffer.append("and c.gradeId in (:gradeIds) ");
				argMap.put("gradeIds", classModel.getGradeIds());
			}
			//自定义条件
			NamedConditon customConditon = classModel.customCondition();
			if(customConditon != null){
				sqlBuffer.append(" ").append(customConditon.getConditon());
				argMap.putAll(customConditon.getParamMap());
			}
		}
		//学生账号条件拼凑
		if(model!=null){
			if(model.getOrgId()!=null){
				sqlBuffer.append("and s.orgId  =:orgId ");
				argMap.put("orgId", model.getOrgId());
			}
			if(StringUtils.isNotEmpty(model.getCellphone())){
				sqlBuffer.append("and s.cellphone  =:cellphone ");
				argMap.put("cellphone", model.getCellphone());
			}
			if(model.getId()!=null){
				sqlBuffer.append("and s.id  =:id ");
				argMap.put("id", model.getId());
			}
			if(StringUtils.isNotEmpty(model.getName())){
				sqlBuffer.append("and s.name  =:name ");
				argMap.put("name", model.getName());
			}
			if(StringUtils.isNotEmpty(model.getCode())){
				sqlBuffer.append("and s.code  =:code ");
				argMap.put("code", model.getCode());
			}
			if(model.getSex()!=null){
				sqlBuffer.append("and s.sex  =:sex ");
				argMap.put("sex", model.getSex());
			}
			if(model.getIsdelete()!=null){
				sqlBuffer.append("and s.isdelete  =:isdelete ");
				argMap.put("isdelete", model.getIsdelete());
			}
			//自定义条件
			NamedConditon customConditon = model.customCondition();
			if(customConditon != null){
				sqlBuffer.append(" ").append(customConditon.getConditon());
				argMap.putAll(customConditon.getParamMap());
			}
			
		}
		
		return this.queryByNamedSql(sqlBuffer.toString(),argMap,new RowMapper<EvlStudentAccount>() {
			@Override
			public EvlStudentAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
				EvlStudentAccount studentAccount = new EvlStudentAccount();
				ClassStudent classStudent=new ClassStudent();
				studentAccount.setId(rs.getString("id"));
				studentAccount.setName(rs.getString("name"));
				studentAccount.setCode(rs.getString("code"));
				studentAccount.setSex(rs.getInt("sex"));
				studentAccount.setCellphone(rs.getString("cellphone"));
				studentAccount.setOrgId(rs.getInt("org_id"));
				//填充班级信息
				classStudent.setClassId(rs.getInt("class_id"));
				classStudent.setGradeId(rs.getInt("grade_id"));
				classStudent.setSchoolYear(rs.getInt("school_year"));
				classStudent.setClassName(rs.getString("class_name"));
				studentAccount.setClassStudent(classStudent);
				return  studentAccount;
			}
		});
	}

	/**
	 * @param model
	 * @param classModel
	 * @return
	 * @see com.mainbo.jy.evl.dao.EvlStudentAccountDao#listPage(com.mainbo.jy.evl.bo.EvlStudentAccount, com.mainbo.jy.evl.bo.ClassStudent)
	 */
	@Override
	public PageList<EvlStudentAccount> listPage(EvlStudentAccount model,ClassStudent classModel) {
		assertNotNull(model, "model");
		Map<String,Object> argMap = new HashMap<String, Object>();
		StringBuilder sqlBuffer=new StringBuilder();
		sqlBuffer.append("select s.id id,s.name name,s.code code,s.sex sex,s.cellphone cellphone,c.classId class_id,c.gradeId grade_id, c.schoolYear school_year,c.className class_name from ClassStudent c join EvlStudentAccount s on c.studentId=s.id where 1=1");
		//班级条件拼凑
		if(classModel!=null){
			if(StringUtils.isNotEmpty(classModel.getClassName())){
				sqlBuffer.append("and c.className  =:className ");
				argMap.put("className", classModel.getClassName());
			}
			if(classModel.getClassId()!=null){
				sqlBuffer.append("and c.classId  =:classId ");
				argMap.put("classId", classModel.getClassId());
			}
			if(classModel.getGradeId()!=null){
				sqlBuffer.append("and c.gradeId  =:gradeId ");
				argMap.put("gradeId", classModel.getGradeId());
			}
			if(classModel.getSchoolYear()!=null){
				sqlBuffer.append("and c.schoolYear  =:schoolYear ");
				argMap.put("schoolYear", classModel.getSchoolYear());
			}
			if (!CollectionUtils.isEmpty(classModel.getClassIds())) {
				sqlBuffer.append("and c.classId in (:classIds) ");
				argMap.put("classIds", classModel.getClassIds());
			}
			if (!CollectionUtils.isEmpty(classModel.getGradeIds())) {
				sqlBuffer.append("and c.gradeId in (:gradeIds) ");
				argMap.put("gradeIds", classModel.getGradeIds());
			}
		}
		//学生账号条件拼凑
		if(model!=null){
			if(model.getOrgId()!=null){
				sqlBuffer.append("and s.orgId  =:orgId ");
				argMap.put("orgId", model.getOrgId());
			}
			if(StringUtils.isNotEmpty(model.getCellphone())){
				sqlBuffer.append("and s.cellphone  =:cellphone ");
				argMap.put("cellphone", model.getCellphone());
			}
			if(model.getId()!=null){
				sqlBuffer.append("and s.id  =:id ");
				argMap.put("id", model.getId());
			}
			if(StringUtils.isNotEmpty(model.getName())){
				sqlBuffer.append("and s.name  =:name ");
				argMap.put("name", model.getName());
			}
			if(StringUtils.isNotEmpty(model.getCode())){
				sqlBuffer.append("and s.code  =:code ");
				argMap.put("code", model.getCode());
			}
			if(model.getSex()!=null){
				sqlBuffer.append("and s.sex  =:sex ");
				argMap.put("sex", model.getSex());
			}
			if(model.getIsdelete()!=null){
				sqlBuffer.append("and s.isdelete  =:isdelete ");
				argMap.put("isdelete", model.getIsdelete());
			}
			
		}
		
		return this.queryPageByNamedSql(sqlBuffer.toString(),argMap,new RowMapper<EvlStudentAccount>() {
			@Override
			public EvlStudentAccount mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				EvlStudentAccount studentAccount = new EvlStudentAccount();
				ClassStudent classStudent=new ClassStudent();
				studentAccount.setId(rs.getString("id"));
				studentAccount.setName(rs.getString("name"));
				studentAccount.setCode(rs.getString("code"));
				studentAccount.setSex(rs.getInt("sex"));
				studentAccount.setCellphone(rs.getString("cellphone"));
				//填充班级信息
				classStudent.setClassId(rs.getInt("class_id"));
				classStudent.setGradeId(rs.getInt("grade_id"));
				classStudent.setSchoolYear(rs.getInt("school_year"));
				classStudent.setClassName(rs.getString("class_name"));
				studentAccount.setClassStudent(classStudent);
				return  studentAccount;
			}
		},model.getPage());
	}
}
