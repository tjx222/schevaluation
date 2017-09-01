/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;
import java.util.Map;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlOperateResult;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;
import com.mainbo.jy.evl.vo.GradeAndClassVo;

/**
 * 评教结果表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlOperateResult.java, v 1.0 2016-05-12 Generate Tools Exp $
 */

public interface EvlOperateResultService extends BaseService<EvlOperateResult, String> {
	/**
	 * 根据指标userCode和指标获取评教结果
	 * 
	 * @param indicator
	 * @param integer
	 * @return
	 */
	List<EvlOperateResult> findResultListByIndicatorId(EvlIndicator indicator, String userCode);
	
	/**
	 * 保存问卷单项填报结果
	 * @param rs
	 * @return 成功 true
	 */
	boolean saveOrUpdateResult(EvlOperateResult rs);

	/**
	 * 学生评教等级
	 * 
	 * @param eq
	 * @param result
	 * @return
	 */
	public List<EvlOperateResult> findOperateResultListByIndicatorLevel(EvlQuestionnaires eq, EvlOperateResult result);

	/**
	 * 获取整体评教学生列表
	 * 
	 * @param resultList
	 * @param levelList
	 * @param gradeMeta
	 * @return
	 */
	public Map<String, Object> findStudentResultListByZhengti(Integer questionId,List<EvlOperateResult> resultList, List<EvlLevelWeight> levelList, List<GradeAndClassVo> gradeMeta);

	/**
	 * 同步结果教师信息
	 * 
	 * @param orgId
	 * @param questionnairesId
	 * @return
	 */
	public int supplementResultTeacherId(EvlQuestionnaires questionnaires);

	/**
	 * 按按班级学科教师指标获取分析结果
	 * 
	 * @return
	 */
	public List<EvlOperateResult> findAnalyzeTeacherList(EvlQuestionnaires eq);
	
	/**
	 * 统计问卷各指标得分结果
	 * 
	 * @return EvlOperateResult 属性<指标id，得分>
	 */
	public List<EvlOperateResult> findAnalyzeIndicatorList(EvlQuestionnaires eq);
	
	/**
	 * 分数段内人数统计
	 * 
	 * @param peopleSection
	 * @param score
	 */
	public void statisticsSection(Integer[] peopleSection, double score);

	/**
	 * 批量删除脏数据
	 * @param questionnairesId
	 */
	void batchDelete(Integer questionnairesId);

	/**
	 * 校验数据是否完整
	 * @param questionId
	 * @param userCode
	 * @param count
	 * @return
	 */
	boolean checkIndicatorCount(Integer questionId, String userCode, int count);
	
	/**
	 * 提交问卷
	 * @param submitStatus  true 提交   false 未提交【填写中】
	 * @param questionId
	 * @param userCode
	 */
	public boolean submit(Boolean submitStatus,Integer questionId,String userCode);

	/**
	 * 获取指标和指标对应填报数据
	 * @param evlQuestionnaires
	 * @param userId
	 * @return
	 */
	EvlIndicatorVo getAllIndicatorList(EvlQuestionnaires evlQuestionnaires,
			String userCode);

	/**
	 * 获取二级指标列表(对应结果)
	 * @param type
	 *            问卷指标类型
	 * @param EvlIndicator
	 * @param firstEvlIndicatorVo
	 * @return
	 */
	EvlIndicatorVo secondEvlIndicatorVoList(Integer indicatorType,
			EvlIndicator EvlIndicator, EvlIndicatorVo firstEvlIndicatorVo,
			String userCode);

	/**
	 * 按学科，年级，班级，教师统计得分。
	 * @param paramRes
	 * @return
	 */
	List<EvlOperateResult> findAnalyzeResultScore(EvlOperateResult model);
	
}
