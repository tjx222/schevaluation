/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import java.util.List;

import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlSuggestionType;
import com.tmser.schevaluation.common.service.BaseService;

/**
 * 建议类型表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlSuggestionType.java, v 1.0 2016-05-09 Generate Tools Exp $
 */

public interface EvlSuggestionTypeService extends
		BaseService<EvlSuggestionType, Integer> {

	/**
	 * 通过问卷获取问卷反馈类型
	 * @param eq
	 * @return
	 */
	public List<EvlSuggestionType> findSuggestionTypeListByQuestionnaires(EvlQuestionnaires eq);
	
}
