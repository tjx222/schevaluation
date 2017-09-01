package com.mainbo.jy.evl.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.evl.bo.EvlAnalyzeIndicator;

/**
 * 评价分析指标dao接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicatorDao.java, v 1.0 2016年5月10日 下午5:31:37
 *          gengqianfeng Exp $
 */
public interface EvlAnalyzeIndicatorDao extends BaseDAO<EvlAnalyzeIndicator, Integer> {

	/**
	 * 计算问卷各指标得分率
	 * @param questionId
	 */
	void calScoreAverage(Integer questionId);
}
