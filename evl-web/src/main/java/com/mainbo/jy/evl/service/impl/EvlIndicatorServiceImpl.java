/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.dao.EvlIndicatorDao;
import com.mainbo.jy.evl.service.EvlIndicatorService;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;
import com.mainbo.jy.utils.Identities;

/**
 * 指标实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author ljh
 * @version $Id: EvlIndicatorServiceImpl.java, v 1.0 2016年5月9日 下午4:51:24 ljh Exp
 *          $
 */
@Service
@Transactional
public class EvlIndicatorServiceImpl extends AbstractService<EvlIndicator, String> implements EvlIndicatorService {

	@Autowired
	private EvlIndicatorDao evlIndicatorDao;
	private static String orderCondition = "crtDttm asc";
	
	@Resource(name="cacheManger")
    private CacheManager cacheManager;

	private Cache questionCache;
	
	private Cache getQuestionCache(){
		if(this.cacheManager != null && questionCache == null){
			questionCache = cacheManager.getCache("evl_question_cache");
		}
		return questionCache;
	}

	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlIndicator, String> getDAO() {
		return evlIndicatorDao;
	}

	@Override
	public EvlIndicatorVo getAllIndicatorList(EvlQuestionnaires evlQuestionnaires) {
		EvlIndicatorVo allIndicatorVo = new EvlIndicatorVo();
		if (evlQuestionnaires == null) {
			return allIndicatorVo;
		}
		Integer type = evlQuestionnaires.getIndicatorType();
		// 获取所有指标数据
		List<EvlIndicatorVo> evlIndicatorVoVoList = new ArrayList<EvlIndicatorVo>();// 返回集合
		// 获取一级指标集合
		List<EvlIndicator> firstEvlIndicatorVoList = getOneLevelIndicator(evlQuestionnaires);
		for (EvlIndicator indicator : firstEvlIndicatorVoList) {
			EvlIndicatorVo firstEvlIndicatorVo = new EvlIndicatorVo();// 返回对象
			firstEvlIndicatorVo.setIndicator(indicator);
			secondEvlIndicatorVoList(type, indicator, firstEvlIndicatorVo);
			evlIndicatorVoVoList.add(firstEvlIndicatorVo);
		}
		allIndicatorVo.setChildIndicators(evlIndicatorVoVoList);
		return allIndicatorVo;
	}

	
	@Override
	public List<EvlIndicator> getOneLevelIndicator(EvlQuestionnaires evlQuestionnaires) {
		List<EvlIndicator> twoLevelIndicatorList = getLevelIndicator(evlQuestionnaires.getQuestionnairesId(),EvlIndicator.level1);
		List<EvlIndicator> rs = new ArrayList<EvlIndicator>();
		if(twoLevelIndicatorList !=  null){
			for(EvlIndicator ei : twoLevelIndicatorList){
				EvlIndicator e = new EvlIndicator();
				BeanUtils.copyProperties(ei,e);
				rs.add(e);
			}
			
		}
		return rs;
	}

	/**
	 * 获取二级指标列表
	 * 
	 * @param type
	 *            问卷指标类型
	 * @param EvlIndicator
	 * @param firstEvlIndicatorVo
	 * @return
	 */
	@Override
	public EvlIndicatorVo secondEvlIndicatorVoList(Integer indicatorType, EvlIndicator EvlIndicator, EvlIndicatorVo firstEvlIndicatorVo) {
		List<EvlIndicator> secondEvlIndicatorList = getTwoLevelIndicator(EvlIndicator);
		List<EvlIndicatorVo> childKpiIndicatorVoList = new ArrayList<>();
		for (EvlIndicator evlIndicator : secondEvlIndicatorList) {
			EvlIndicatorVo vo2 = new EvlIndicatorVo();// 二级指标vo
			vo2.setIndicator(evlIndicator);
			childKpiIndicatorVoList.add(vo2);
		}
		firstEvlIndicatorVo.setChildIndicators(childKpiIndicatorVoList);
		return firstEvlIndicatorVo;
	}


	/**
	 * 
	 * @param evlIndicator
	 * @return
	 */
	@Override
	public List<EvlIndicator> getTwoLevelIndicator(EvlIndicator evlIndicator) {
		List<EvlIndicator> twoLevelIndicatorList = getLevelIndicator(evlIndicator.getQuestionnairesId(),EvlIndicator.level2);
		List<EvlIndicator> rs = new ArrayList<EvlIndicator>();
		if(twoLevelIndicatorList !=  null){
			for(EvlIndicator ei : twoLevelIndicatorList){
				if(ei.getParentId().equals(evlIndicator.getId())){
					try {
						EvlIndicator e = ei.clone();
						rs.add(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						logger.warn("copy evlindicator failed , id={}",ei.getId());
					}
					
				}
			}
			
		}
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EvlIndicator> getLevelIndicator(Integer qid,Integer level) {
		List<EvlIndicator> twoLevelIndicatorList = null;
		if(getQuestionCache() != null){
			 ValueWrapper cacheElement = getQuestionCache().get("evl_q_"+qid+"_l_"+level);
			 if (cacheElement != null) {
				 twoLevelIndicatorList = (List<EvlIndicator>) cacheElement.get();
			 }
		}
		
		if(twoLevelIndicatorList ==  null){
			// 默认排序
			EvlIndicator parentkpiIndicator = new EvlIndicator();
			parentkpiIndicator.setOrder(orderCondition);
			parentkpiIndicator.setLevel(level);
			parentkpiIndicator.setQuestionnairesId(qid);
			twoLevelIndicatorList = evlIndicatorDao.listAll(parentkpiIndicator);
			
			if(getQuestionCache() != null && twoLevelIndicatorList != null){
				getQuestionCache().put("evl_q_"+qid+"_l_"+level,twoLevelIndicatorList);
			}
		}
		
		return twoLevelIndicatorList;
	}
	
		
	@Override
	public Result deleteIndicatorLevel(String id, Integer depth) {
		// TODO 级联删除
		deleteTreeData(id, depth);
		return null;
	}

	private void deleteTreeData(String parentId, Integer depth) {
		if (depth > 0) {
			EvlIndicator model = new EvlIndicator();
			model.setParentId(parentId);
			for (EvlIndicator indicator : evlIndicatorDao.listAll(model)) {
				evlIndicatorDao.delete(indicator.getId());
				deleteTreeData(indicator.getId(), --depth);
			}
		}
	}

	@Override
	public void copyIndicator(Integer oldId, Integer newId) {
		// 删除旧指标
		EvlIndicator oldIndicator = new EvlIndicator();
		oldIndicator.setQuestionnairesId(oldId);
		List<EvlIndicator> oldList = this.findAll(oldIndicator);
		for (EvlIndicator old : oldList) {
			this.delete(old.getId());
		}
		// 新指标复制
		EvlIndicator newIndicator = new EvlIndicator();
		newIndicator.setQuestionnairesId(newId);
		newIndicator.setLevel(EvlIndicator.level1);
		List<EvlIndicator> newList = this.findAll(newIndicator);
		for (EvlIndicator parentModel : newList) {
			String newParentId = Identities.uuid2();
			// 查询对应二级指标
			EvlIndicator second = new EvlIndicator();
			second.setParentId(parentModel.getId());
			List<EvlIndicator> secondList = this.findAll(second);
			if (!CollectionUtils.isEmpty(secondList)) {
				for (EvlIndicator child : secondList) {
					child.setParentId(newParentId);
					child.setId(Identities.uuid2());
					child.setQuestionnairesId(oldId);
					this.save(child);// 保存二级指标
				}
			}
			// 保存一级指标
			parentModel.setQuestionnairesId(oldId);
			parentModel.setId(newParentId);
			this.save(parentModel);// 保存一级指标
		}
	}

	/**
	 * 枚举公用类
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private <T> T getEnum(Class<T> clazz, int value) {
		T[] c = clazz.getEnumConstants();
		return c[value];
	}

	@Override
	public List<EvlIndicator> getAllIndicatorListByLevel(Integer questionnairesId, Integer level) {
		EvlIndicator ei = new EvlIndicator();
		ei.setQuestionnairesId(questionnairesId);
		ei.setLevel(level);
		ei.addOrder(orderCondition);
		return this.findAll(ei);
	}

	@Override
	public Result checkIndicatorScore(EvlQuestionnaires currentQuestion) {

		Result result = new Result();
		result.setCode(200);
		if(currentQuestion != null){
			// 如果时整体指标类型，不校验分数
			if (currentQuestion.getIndicatorType() == EvlQuestionnaires.indicator_type_zhengti) {
				return result;
			}
			Double initScore = currentQuestion.getTotleScore();
			// 获取所有指标
			EvlIndicatorVo allIndicatorList = getAllIndicatorList(currentQuestion);// 全部指标
			if (allIndicatorList != null) {
				List<EvlIndicatorVo> childIndicators = allIndicatorList.getChildIndicators();
				if (!CollectionUtils.isEmpty(childIndicators)) {
					Double oneTotalScore = 0.0;
					// 校验一级指标
					for (EvlIndicatorVo oneIndicator : childIndicators) {
						oneTotalScore += oneIndicator.getIndicator().getScoreTotal();
						// 如果是一级指标类型，不校验二级分数
						if (currentQuestion.getIndicatorType() == EvlQuestionnaires.indicator_type_twoLevel) {
							Result second_result = checkSecondScore(oneIndicator);
							if (second_result.getCode() == -1) {
								result.setCode(-1);
								return result;
							}
						}
					}
					if (!oneTotalScore.equals(initScore)) {
						result.setCode(-1);
						return result;
					}
				}
			}
		}
		return result;
	}

	private Result checkSecondScore(EvlIndicatorVo oneIndicator) {
		// 校验二级指标
		Result result = new Result();
		result.setCode(200);
		List<EvlIndicatorVo> secondchildIndicators = oneIndicator.getChildIndicators();
		Double initSecondTotalScore = oneIndicator.getIndicator().getScoreTotal();
		Double secondTotalScore = 0.0;
		if (!CollectionUtils.isEmpty(secondchildIndicators)) {
			for (EvlIndicatorVo secondIndicator : secondchildIndicators) {
				secondTotalScore += secondIndicator.getIndicator().getScoreTotal();
			}
			if (!secondTotalScore.equals(initSecondTotalScore)) {
				result.setCode(-1);
				return result;
			}
		}
		return result;
	}

	
}
