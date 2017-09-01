/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;

import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.common.service.BaseService;

/**
 * 等级权重表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlLevelWeight.java, v 1.0 2016-05-11 Generate Tools Exp $
 */

public interface EvlLevelWeightService extends
		BaseService<EvlLevelWeight, Integer> {

	/**
	 * 获取问卷权重等级列表
	 * 
	 * @param questionnaires
	 * @return
	 */
	public List<EvlLevelWeight> findEvlLevelWeightListByQuestionnaires(
			EvlQuestionnaires questionnaires);
}
