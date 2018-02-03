package com.tmser.schevaluation.evl.bizservice;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.uc.bo.User;

public interface EvlStudentsBizService {
	/**
	 * 导出该校所有学生账号
	 * @param response
	 * @param user
	 */
	public void exportStudentAccounts(HttpServletResponse response, User user);
	/**
	 * 导出用户
	 * @param response
	 * @param model
	 */
	public void exportStudentAccounts(HttpServletResponse response,EvlStudentAccount model,ClassStudent classModel);
	/**
	 * 按年级批量升级学生账号
	 * @param gradeId
	 * @param toGradeId 要升级到的年级ID
	 */
	public boolean batchUpdateStudents(Integer gradeId, Integer toGradeId,Integer orgId);
	
	public List<SchClass> getSchByGradeIdAndOrgId(Integer gradeId, Integer orgId, Integer status);
}
