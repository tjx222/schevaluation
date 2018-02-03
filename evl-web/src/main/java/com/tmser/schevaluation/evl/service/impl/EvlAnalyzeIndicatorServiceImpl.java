package com.tmser.schevaluation.evl.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.echarts.Option;
import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeIndicator;
import com.tmser.schevaluation.evl.bo.EvlIndicator;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.dao.EvlAnalyzeIndicatorDao;
import com.tmser.schevaluation.evl.service.EvlAnalyzeIndicatorService;
import com.tmser.schevaluation.evl.service.EvlIndicatorService;
import com.tmser.schevaluation.evl.service.EvlOperateResultService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.utils.EchartUtil;
import com.tmser.schevaluation.evl.vo.EvlAnalyzeIndicatorVo;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 评价分析指标服务层实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeIndicatorServiceImpl.java, v 1.0 2016年5月11日 上午9:04:28
 *          gengqianfeng Exp $
 */
@Service
@Transactional
public class EvlAnalyzeIndicatorServiceImpl extends AbstractService<EvlAnalyzeIndicator, Integer> implements EvlAnalyzeIndicatorService {

	@Autowired
	private EvlAnalyzeIndicatorDao evlAnalyzeIndicatorDao;
	@Autowired
	private EvlOperateResultService evlOperateResultService;
	@Autowired
	private EvlIndicatorService evlIndicatorService;
	@Autowired
	private EvlQuestionnairesService evlQuestionnairesService;
	
	private static final DecimalFormat NUM_FORMATER = new DecimalFormat("0.00");
	
	@Resource(name="cacheManger")
    private CacheManager cacheManager;

	private Cache questionCache;
	
	private Cache getQuestionCache(){
		if(this.cacheManager != null && questionCache == null){
			questionCache = cacheManager.getCache("evl_result_cache");
		}
		return questionCache;
	}
	@Override
	public BaseDAO<EvlAnalyzeIndicator, Integer> getDAO() {
		return evlAnalyzeIndicatorDao;
	}

	/**
	 * 保存或修改分析指标数据
	 * 
	 * @param eq
	 * @param paramMap
	 */
	@Override
	public void saveOrUpdateAnalyzeIndicator(EvlQuestionnaires eq) {
		if (!eq.getIndicatorType().equals(EvlQuestionnaires.indicator_type_zhengti)) {
			EvlAnalyzeIndicator model = new EvlAnalyzeIndicator();
			model.setOrgId(eq.getOrgId());
			model.setQuestionnairesId(eq.getQuestionnairesId());
			model.addCustomCulomn("id,indicatorId,resultScore");
			List<EvlAnalyzeIndicator> resultEais = evlAnalyzeIndicatorDao.listAll(model);
			Map<String,EvlAnalyzeIndicator> existAIs = new HashMap<String,EvlAnalyzeIndicator>();
			for(EvlAnalyzeIndicator ai : resultEais){
				existAIs.put(ai.getIndicatorId(), ai);
			}
			
			List<EvlOperateResult> invs = evlOperateResultService.findAnalyzeIndicatorList(eq);
			// 去重
			for (EvlOperateResult eoa : invs) {
				EvlAnalyzeIndicator eai = new EvlAnalyzeIndicator();
				if(eoa.getResultScore() != null && eoa.getResultScore() > 0){
					eai.setResultScore(Double.parseDouble(NUM_FORMATER.format(eoa.getResultScore())));
				}else{
					eai.setResultScore(0.0);
				}
				
				if (existAIs.get(eoa.getIndicatorId()) != null ) {
					if(!eai.getResultScore().equals(existAIs.get(eoa.getIndicatorId()).getResultScore())){
						eai.setId(existAIs.get(eoa.getIndicatorId()).getId());
						evlAnalyzeIndicatorDao.update(eai);
					}
				} else {
					eai.setOrgId(eq.getOrgId());
					eai.setQuestionnairesId(eq.getQuestionnairesId());
					eai.setIndicatorId(eoa.getIndicatorId());
					evlAnalyzeIndicatorDao.insert(eai);
				}
			}
			
			if(invs.size() > 0){//整体计算指标得分率
				evlAnalyzeIndicatorDao.calScoreAverage(eq.getQuestionnairesId());
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findAllAnalyzeIndicatorVoList(Integer questionId, boolean isDownload) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(getQuestionCache()!=null){
			ValueWrapper cacheElement = getQuestionCache().get("ev_rs_i_q"+questionId+"_d"+isDownload);
			if (cacheElement != null) {
				returnMap = (Map<String, Object>) cacheElement.get();
				return returnMap;
			}
		}
		
		List<EvlAnalyzeIndicatorVo> eaiVoList = new ArrayList<EvlAnalyzeIndicatorVo>();
		// 问卷对象
		EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
		// 一级指标
		EvlIndicator ei1 = new EvlIndicator();
		ei1.setQuestionnairesId(eq.getQuestionnairesId());
		ei1.setLevel(EvlIndicator.level1);
		ei1.addOrder(" crtDttm asc ");
		List<EvlIndicator> indicatorList1 = evlIndicatorService.findAll(ei1);
		int questionType = eq.getIndicatorType().intValue();
		for (EvlIndicator evlIndicator : indicatorList1) {
			EvlAnalyzeIndicatorVo eaiVo = new EvlAnalyzeIndicatorVo();
			if (EvlQuestionnaires.indicator_type_oneLevel == questionType) {
				// 分析指标
				EvlAnalyzeIndicator eai = new EvlAnalyzeIndicator();
				eai.setIndicatorId(evlIndicator.getId());
				eai.setQuestionnairesId(evlIndicator.getQuestionnairesId());
				EvlAnalyzeIndicator oneAnalyzeIndicator = new EvlAnalyzeIndicator();
				oneAnalyzeIndicator = this.findOne(eai);
				eaiVo.setEvlAnalyzeIndicator(oneAnalyzeIndicator);
			} else if (EvlQuestionnaires.indicator_type_twoLevel == questionType) {
				List<EvlAnalyzeIndicatorVo> eaiVoList2 = new ArrayList<EvlAnalyzeIndicatorVo>();
				// 获取二级指标
				EvlIndicator ei2 = new EvlIndicator();
				ei2.setQuestionnairesId(eq.getQuestionnairesId());
				ei2.setLevel(EvlIndicator.level2);
				ei2.setParentId(evlIndicator.getId());
				ei2.addOrder(" crtDttm asc ");
				List<EvlIndicator> indicatorList12 = evlIndicatorService.findAll(ei2);
				for (EvlIndicator evlIndicator2 : indicatorList12) {
					EvlAnalyzeIndicatorVo eaiVo2 = new EvlAnalyzeIndicatorVo();
					// 分析指标
					EvlAnalyzeIndicator eai = new EvlAnalyzeIndicator();
					eai.setIndicatorId(evlIndicator2.getId());
					eai.setQuestionnairesId(evlIndicator2.getQuestionnairesId());

					EvlAnalyzeIndicator twoAnalyzeIndicator = new EvlAnalyzeIndicator();
					twoAnalyzeIndicator = this.findOne(eai);
					eaiVo2.setEvlAnalyzeIndicator(twoAnalyzeIndicator);
					evlIndicator2.setFlago(StringUtils.abbr(evlIndicator2.getTitle(), 54, true, false));
					eaiVo2.setEvlIndicator(evlIndicator2);
					eaiVoList2.add(eaiVo2);
				}
				// 加载二级指标
				eaiVo.setChildIndicators(eaiVoList2);
			}
			// 加载一级指标
			evlIndicator.setFlago(StringUtils.abbr(evlIndicator.getTitle(), 12, true, false));
			eaiVo.setEvlIndicator(evlIndicator);
			// 装在指标数据
			eaiVoList.add(eaiVo);
		}
		this.getAnalyzeIndicatorMap(returnMap, eaiVoList, eq, isDownload);
		if(getQuestionCache()!=null){
			getQuestionCache().put("ev_rs_i_q"+questionId+"_d"+isDownload, returnMap);
		}
		return returnMap;
	}

	/**
	 * 装载指标分析对象
	 * 
	 * @param returnMap
	 * @param indicatorList
	 * @param eq
	 */
	private void getAnalyzeIndicatorMap(Map<String, Object> returnMap, List<EvlAnalyzeIndicatorVo> indicatorList, EvlQuestionnaires eq, boolean isDownload) {
		Option option = new Option();
		if (eq.getIndicatorType().intValue() == EvlQuestionnaires.indicator_type_oneLevel) {
			String[] titleArray = new String[indicatorList.size()];
			Double[] dataArray = new Double[indicatorList.size()];
			for (int i = 0; i < indicatorList.size(); i++) {
				EvlAnalyzeIndicatorVo evlAnalyzeIndicatorVo = indicatorList.get(i);
				titleArray[i] = evlAnalyzeIndicatorVo.getEvlIndicator().getTitle();
				EvlAnalyzeIndicator evlAnalyzeIndicator = evlAnalyzeIndicatorVo.getEvlAnalyzeIndicator();
				if (evlAnalyzeIndicator == null) {
					dataArray[i] = 0.0;
					evlAnalyzeIndicator = new EvlAnalyzeIndicator();
					evlAnalyzeIndicator.setResultScore(0.0);
					evlAnalyzeIndicator.setScoreAverage(0.0);
					evlAnalyzeIndicatorVo.setEvlAnalyzeIndicator(evlAnalyzeIndicator);
				} else {
					evlAnalyzeIndicator.setResultScore(Double.valueOf(NUM_FORMATER.format(evlAnalyzeIndicator.getResultScore())));
					evlAnalyzeIndicator.setScoreAverage(Double.valueOf(NUM_FORMATER.format(evlAnalyzeIndicator.getScoreAverage())));
					dataArray[i] = evlAnalyzeIndicator.getScoreAverage();
				}
			}
			if (!isDownload) {
				option = EchartUtil.packageBarOption("各评价指标得分率对比图", titleArray, "得分率", dataArray);
				option.getGrid().setY2(110);
				returnMap.put("titleSize", titleArray.length);
			}
		} else if (eq.getIndicatorType().intValue() == EvlQuestionnaires.indicator_type_twoLevel) {
			List<String> titleList = new ArrayList<String>();
			List<Double> scoreList = new ArrayList<Double>();
			for (int i = 0; i < indicatorList.size(); i++) {
				List<EvlAnalyzeIndicatorVo> twoIndicatorList = indicatorList.get(i).getChildIndicators();
				for (int j = 0; j < twoIndicatorList.size(); j++) {
					EvlAnalyzeIndicatorVo twoIndicator = twoIndicatorList.get(j);
					titleList.add(twoIndicator.getEvlIndicator().getTitle());
					EvlAnalyzeIndicator evlAnalyzeIndicator = twoIndicator.getEvlAnalyzeIndicator();
					if (evlAnalyzeIndicator == null) {
						scoreList.add(0.0);
						evlAnalyzeIndicator = new EvlAnalyzeIndicator();
						evlAnalyzeIndicator.setResultScore(0.0);
						evlAnalyzeIndicator.setScoreAverage(0.0);
						twoIndicator.setEvlAnalyzeIndicator(evlAnalyzeIndicator);
					} else {
						evlAnalyzeIndicator.setResultScore(Double.valueOf(NUM_FORMATER.format(evlAnalyzeIndicator.getResultScore())));
						evlAnalyzeIndicator.setScoreAverage(Double.valueOf(NUM_FORMATER.format(evlAnalyzeIndicator.getScoreAverage())));
						scoreList.add(evlAnalyzeIndicator.getScoreAverage());
					}
				}
			}
			if (!isDownload) {
				option = EchartUtil.packageBarOption("各评价指标得分率对比图", titleList.toArray(), "得分率", scoreList.toArray());
				option.getGrid().setY2(110);
				returnMap.put("titleSize", titleList.size());
			}
		} else {
			// 评价类型异常
			logger.error("获取指标列表中问卷的评价类型不存在！");
		}
		if (!isDownload) {
			option.getSeries().get(0).getItemStyle().getNormal().getLabel().formatter("{c}%");
			returnMap.put("option", option);
		}
		returnMap.put("indicatorList", indicatorList);
	}
}
