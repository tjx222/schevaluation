/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.io.File;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.schconfig.clss.bo.SchClass;

/**
 * 学生账号表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlStudentAccount.java, v 1.0 2016-05-12 Generate Tools Exp $
 */

public interface EvlStudentAccountService extends
		BaseService<EvlStudentAccount, String> {

	/**
	 * 导出学生账号
	 * @param model
	 * @param response
	 * @return 
	 */
	public ResponseEntity<byte[]> getImportTemplateFile(String fileName) throws Exception;
	/**
	 * 解析文件数据
	 * @param file
	 * @param gradeId
	 * @param classId
	 * @throws Exception
	 * return 错误信息的行
	 */
	public List<String> analyzeScoreFile(File file,Integer gradeId,Integer classId)  throws Exception;
	
	public List<String> analyzeScoreFile(File file, Integer gradeId) throws Exception;
	/**
	 * 获取当前机构下下学生管理中的所有学生
	 * @param orgId
	 * @return
	 */
	public List<EvlStudentAccount> getAllAccountUserByOrgId(Integer orgId);
	/**
	 * 获取当前机构班级列表
	 * @param id 年级id
	 * @param orgId 机构id
	 * @param questionnairesId
	 * @param schoolYear
	 * @return
	 */
	List<SchClass> getSchByGradeIdAndOrgId(Integer gradeId, Integer orgId,Integer schoolYear);
	/**
	 * 通过关联表（ClassStudent）查询学生信息
	 * @param model
	 * @param classModel
	 * @return
	 */
	public List<EvlStudentAccount> findAll(EvlStudentAccount model,ClassStudent classModel);
	/**
	 * 通过关联表（ClassStudent）分页查询学生信息
	 * @param model
	 * @param classModel
	 * @return
	 */
	public PageList<EvlStudentAccount> findByPage(EvlStudentAccount model,ClassStudent classModel);
	/**
	 * 添加编辑学生存在验证
	 * @param studentAccount
	 * @param orgId
	 * @return
	 */
	public EvlStudentAccount isExistStudent(EvlStudentAccount studentAccount, Integer orgId);
	/**
	 * 同一个学校的学生学号是否重复
	 * @param code
	 * @return
	 */
	EvlStudentAccount isExistStudent(String code, Integer orgId);
	
	
	/**
		批量插入学生账号
	 * @return
	 */
	void batchSaveStudent(List<EvlStudentAccount> stus);
}
