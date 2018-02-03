/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import java.util.Map;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.evl.bo.EvlSuggestion;

/**
 * 建议表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlSuggestion.java, v 1.0 2016-05-12 Generate Tools Exp $
 */

public interface EvlSuggestionService extends
		BaseService<EvlSuggestion, Integer> {

	/**
	 * 意见建议反馈分析列表
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findAnalyzeSuggestionList(Integer questionId,boolean isDownload);
	
	/**
	 * 保存建议
	 * @param suggest
	 * @return
	 */
	boolean saveOrUpdate(EvlSuggestion suggest);

	
}
