package com.mainbo.jy.evl.dao.impl;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.evl.bo.EvlAnalyzeIndicator;
import com.mainbo.jy.evl.dao.EvlAnalyzeIndicatorDao;

/**
 * 评价分析指标dao实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicatorDaoImpl.java, v 1.0 2016年5月10日 下午5:33:28
 *          gengqianfeng Exp $
 */
@Repository
public class EvlAnalyzeIndicatorDaoImpl extends AbstractDAO<EvlAnalyzeIndicator, Integer> implements EvlAnalyzeIndicatorDao {

	/**
	 * 计算指标得分率
	 * 
	 * @param questionId
	 * @author tmser
	 * @return
	 */
	@Override
	public void calScoreAverage(Integer questionId) {
		String sql = " update EvlAnalyzeIndicator a INNER JOIN  EvlIndicator i on i.id = a.indicatorId "
						+ "set a.scoreAverage = ROUND(a.resultScore * 100/ i.scoreTotal,2) where a.questionnairesId=?";
				
		update(sql, new Object[]{questionId});			
	}
}
