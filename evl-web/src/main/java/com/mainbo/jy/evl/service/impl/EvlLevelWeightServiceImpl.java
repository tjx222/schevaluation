/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.dao.annotation.UseCache;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.dao.EvlLevelWeightDao;
import com.mainbo.jy.evl.service.EvlLevelWeightService;

/**
 * 等级权重表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlLevelWeight.java, v 1.0 2016-05-11 Generate Tools Exp $
 */
@Service
@Transactional
@UseCache
public class EvlLevelWeightServiceImpl extends
		AbstractService<EvlLevelWeight, Integer> implements
		EvlLevelWeightService {

	@Autowired
	private EvlLevelWeightDao Dao;

	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlLevelWeight, Integer> getDAO() {
		return Dao;
	}

	@Override
	public List<EvlLevelWeight> findEvlLevelWeightListByQuestionnaires(
			EvlQuestionnaires questionnaires) {
		EvlLevelWeight elw = new EvlLevelWeight();
		elw.setOrgId(questionnaires.getOrgId());
		elw.setQuestionnairesId(questionnaires.getQuestionnairesId());
		elw.addOrder(" crtDttm asc ");
		return this.findAll(elw);
	}

}
