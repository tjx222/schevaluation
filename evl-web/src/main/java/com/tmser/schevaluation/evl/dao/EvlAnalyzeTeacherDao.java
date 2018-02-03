package com.tmser.schevaluation.evl.dao;

import java.util.List;
import java.util.Map;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeTeacher;

/**
 * 评价分析教师dao接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeTeacherDao.java, v 1.0 2016年5月10日 下午5:20:43
 *          gengqianfeng Exp $
 */
public interface EvlAnalyzeTeacherDao extends BaseDAO<EvlAnalyzeTeacher, Integer> {

	/**
	 * 分班级学科过滤教师列表
	 * 
	 * @param eatList
	 */
	public List<EvlAnalyzeTeacher> filterTeacherListByClassSubject(List<EvlAnalyzeTeacher> eatList, Map<String, EvlAnalyzeTeacher> teacherMap);

	/**
	 * 学科过滤教师分析列表
	 * 
	 * @param eatList
	 */
	public  List<EvlAnalyzeTeacher> distinctTeacherListOfSubject(List<EvlAnalyzeTeacher> eatList);

	/**
	 * 班级过滤教师分析列表
	 * 
	 * @param eatList
	 */
	public List<EvlAnalyzeTeacher> distinctTeacherListOfClass(List<EvlAnalyzeTeacher> eatList);

	/**
	 * 年级过滤教师分析列表
	 * 
	 * @param eatList
	 * @param isDirector
	 * @return 
	 */
	public List<EvlAnalyzeTeacher> distinctTeacherListOfGrade(List<EvlAnalyzeTeacher> eatList, boolean isDirector);

	/**
	 * 教师去重过滤
	 * 
	 * @param eatList
	 */
	public List<EvlAnalyzeTeacher> distinctTeacherListOfTeacher(List<EvlAnalyzeTeacher> eatList);
}
