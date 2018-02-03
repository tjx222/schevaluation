/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao;

import java.util.List;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;

/**
 * 评教结果表 DAO接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlOperateResult.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
public interface EvlOperateResultDao extends BaseDAO<EvlOperateResult, String> {

	/**
	 * 获取分析结果教师列表
	 * 按老师分组
	 * 
	 * @return 教师id <-> 指标得分
	 */
	List<EvlOperateResult> findAnalyzeTeacherResultScore(EvlOperateResult tch);

	/**
	 * 获取分析结果指标列表
	 * 
	 * @return
	 */
	public List<EvlOperateResult> findAnalyzeIndicatorList(EvlQuestionnaires eq, String code);

	/**
	 * 批量删除
	 * @param questionnairesId
	 */
	public void batchDelete(Integer questionnairesId);

	/**
	 * 校验填报数据是否完整
	 * @param questionId
	 * @param userCode
	 * @param count
	 * @return
	 */
	boolean checkIndicatorCount(Integer questionId, String userCode, int count);

	/**
	 * 统计指标有效得分
	 * @param eq
	 * @return
	 */
	List<EvlOperateResult> findAnalyzeIndicatoResultScore(EvlOperateResult eq);

	/**
	 * 分类统计得分
	 * 分：教师、年级、学科、班级
	 * 
	 * @param eq
	 * @return
	 */
	List<EvlOperateResult> findAnalyzeResultScore(EvlOperateResult eq);

	/**
	 * 删除重复数据
	 */
	void deleteRepeatResult(Integer questionId, String userCode);

}