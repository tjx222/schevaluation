package com.mainbo.jy.evl.service;

import java.util.Map;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.EvlAnalyzeIndicator;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;

/**
 * 评价分析指标服务器接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicatorService.java, v 1.0 2016年5月10日 下午5:35:24
 *          gengqianfeng Exp $
 */
public interface EvlAnalyzeIndicatorService extends BaseService<EvlAnalyzeIndicator, Integer> {

	/**
	 * 保存或修改分析指标
	 * 
	 * @param eq
	 */
	public void saveOrUpdateAnalyzeIndicator(EvlQuestionnaires eq);

	/**
	 * 评价内容分析对比
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findAllAnalyzeIndicatorVoList(Integer questionId, boolean isDownload);

}
