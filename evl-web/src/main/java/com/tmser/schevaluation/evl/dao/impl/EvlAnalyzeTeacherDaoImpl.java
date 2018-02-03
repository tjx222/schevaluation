package com.tmser.schevaluation.evl.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.schevaluation.common.dao.AbstractDAO;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeTeacher;
import com.tmser.schevaluation.evl.dao.EvlAnalyzeTeacherDao;

/**
 * 评价分析教师dao实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeTeacherDaoImpl.java, v 1.0 2016年5月10日 下午5:23:30
 *          gengqianfeng Exp $
 */
@Repository
public class EvlAnalyzeTeacherDaoImpl extends AbstractDAO<EvlAnalyzeTeacher, Integer> implements EvlAnalyzeTeacherDao {

	/**
	 * 分班级学科过滤教师列表
	 * 
	 * @param eatList
	 */
	@Override
	public List<EvlAnalyzeTeacher> filterTeacherListByClassSubject(List<EvlAnalyzeTeacher> eatList, Map<String, EvlAnalyzeTeacher> teacherMap) {
		for (EvlAnalyzeTeacher evlAnalyzeTeacher : eatList) {
			String key = "t"+evlAnalyzeTeacher.getTeacherId()
					+"c"+evlAnalyzeTeacher.getClassId()+"s"+evlAnalyzeTeacher.getSubjectId();
			if(teacherMap.get(key) == null){
				teacherMap.put(key, evlAnalyzeTeacher);
			}
		}
		return new ArrayList<EvlAnalyzeTeacher>(teacherMap.values());
	}
	/**
	 * 教师去重
	 * 
	 * @param eatList
	 */
	@Override
	public List<EvlAnalyzeTeacher> distinctTeacherListOfTeacher(List<EvlAnalyzeTeacher> eatList) {
		Map<String,EvlAnalyzeTeacher> teacherMap = new HashMap<String,EvlAnalyzeTeacher>(eatList.size());
		for (EvlAnalyzeTeacher evlAnalyzeTeacher : eatList) {
			String key = "t"+evlAnalyzeTeacher.getTeacherId();
			if(teacherMap.get(key) == null){
				teacherMap.put(key, evlAnalyzeTeacher);
			}
		}
		return new ArrayList<EvlAnalyzeTeacher>(teacherMap.values());
	}
	/**
	 * 年级组方面问题
	 * 
	 * @param eatList
	 */
	@Override
	public List<EvlAnalyzeTeacher> distinctTeacherListOfGrade(List<EvlAnalyzeTeacher> eatList, boolean isDirector) {
			Map<String,EvlAnalyzeTeacher> gradeMap = new HashMap<String, EvlAnalyzeTeacher>();
			Map<String,Integer> countMap = new HashMap<String, Integer>();
			for (EvlAnalyzeTeacher eat : eatList) {
				if(gradeMap.containsKey(eat.getGradeId())){
					eat.setResultScore(eat.getResultScore() + gradeMap.get(eat.getGradeId()).getResultScore());
					gradeMap.put(eat.getGradeId(), eat);
					countMap.put(eat.getGradeId(),countMap.get(eat.getGradeId())+1);
				}else{
					gradeMap.put(eat.getGradeId(), eat);
					countMap.put(eat.getGradeId(),1);
				}
			}
			if (isDirector) {
				for (String g : gradeMap.keySet()) {
					EvlAnalyzeTeacher eat = gradeMap.get(g);
					eat.setResultScore(Double.parseDouble(new DecimalFormat("0.00").format(eat.getResultScore() / countMap.get(g))));
				}
			} 
			return new ArrayList<EvlAnalyzeTeacher>(gradeMap.values());
	}

	/**
	 * 班级去重
	 * 
	 * @param eatList
	 */
	@Override
	public List<EvlAnalyzeTeacher> distinctTeacherListOfClass(List<EvlAnalyzeTeacher> eatList) {
		Map<String,EvlAnalyzeTeacher> teacherClassMap = new HashMap<String,EvlAnalyzeTeacher>(eatList.size());
		// 同学科不同班级或年级求平均值
		for (EvlAnalyzeTeacher tea : eatList) {
			String classIdkey = "c"+tea.getClassId();
			EvlAnalyzeTeacher evlAnalyzeTeacher = teacherClassMap.get(classIdkey);
			if(evlAnalyzeTeacher==null){
				teacherClassMap.put(classIdkey, tea);
			}
		}
		return new ArrayList<EvlAnalyzeTeacher>(teacherClassMap.values());
	}
	/**
	 * 学科去重
	 * 
	 * @param eatList
	 */
	@Override
	public  List<EvlAnalyzeTeacher> distinctTeacherListOfSubject(List<EvlAnalyzeTeacher> eatList) {
		Map<String,EvlAnalyzeTeacher> subMap = new HashMap<String, EvlAnalyzeTeacher>();
		for (EvlAnalyzeTeacher eat : eatList) {
			subMap.put(eat.getSubjectId(), eat);
		}
		return new ArrayList<EvlAnalyzeTeacher>(subMap.values());
	}
}
