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
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlSuggestionType;
import com.mainbo.jy.evl.dao.EvlSuggestionTypeDao;
import com.mainbo.jy.evl.service.EvlSuggestionTypeService;

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
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
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
