/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlSuggestionType;
import com.tmser.schevaluation.evl.dao.EvlSuggestionTypeDao;
import com.tmser.schevaluation.evl.service.EvlSuggestionTypeService;

/**
 * 建议类型表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlSuggestionType.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlSuggestionTypeServiceImpl extends
		AbstractService<EvlSuggestionType, Integer> implements
		EvlSuggestionTypeService {

	@Autowired
	private EvlSuggestionTypeDao Dao;

	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlSuggestionType, Integer> getDAO() {
		return Dao;
	}

	@Override
	public List<EvlSuggestionType> findSuggestionTypeListByQuestionnaires(
			EvlQuestionnaires eq) {
		EvlSuggestionType est = new EvlSuggestionType();
		est.setOrgId(eq.getOrgId());
		est.setQuestionnairesId(eq.getQuestionnairesId());
		est.addOrder(" crtDttm asc ");		
		return this.findAll(est);
	}

}
