/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;

/**
 * 指标表 服务类
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: EvlIndicator.java, v 1.0 2016-05-09 ljh Exp $
 */
 
public interface EvlIndicatorService extends BaseService<EvlIndicator, String>{
	/**
	 * 获取所有指标及附属考核项：管理人员
	 * @param model
	 * @return
	 */
	public EvlIndicatorVo getAllIndicatorList(EvlQuestionnaires evlQuestionnaires);
	/**
	 * 根据depth删除子指标数据
	 * @param id
	 * @param depth
	 */
	public Result deleteIndicatorLevel(String id,Integer depth);
	/**
	 * 拷贝指标
	 * @param oldQuestionId
	 * @param newQuestionId
	 */
	public void copyIndicator(Integer oldQuestionId,Integer newQuestionId);
	
	/**
	 * 获取指定等级的指标集
	 * @param questionnairesId 问卷id
	 * @param level 指标等级
	 * @return
	 */
	public List<EvlIndicator> getAllIndicatorListByLevel(Integer questionnairesId,Integer level);
	/**
	 * 校验分数是否合理
	 * @param questionnairesId
	 * @return
	 */
	public Result checkIndicatorScore(EvlQuestionnaires currentQuestion);
	
	/**
	 * 根据level获取指标
	 * @param qid
	 * @param level
	 * @return
	 */
	List<EvlIndicator> getLevelIndicator(Integer qid, Integer level);
	
	List<EvlIndicator> getTwoLevelIndicator(EvlIndicator evlIndicator);
	/**
	 * 获取二级指标列表
	 * 
	 * @param type
	 *            问卷指标类型
	 * @param EvlIndicator
	 * @param firstEvlIndicatorVo
	 * @return
	 */
	EvlIndicatorVo secondEvlIndicatorVoList(Integer indicatorType,
			EvlIndicator EvlIndicator, EvlIndicatorVo firstEvlIndicatorVo);
	/**
	 * 获得一级指标
	 * @param evlQuestionnaires
	 * @return
	 */
	List<EvlIndicator> getOneLevelIndicator(EvlQuestionnaires evlQuestionnaires);
}
