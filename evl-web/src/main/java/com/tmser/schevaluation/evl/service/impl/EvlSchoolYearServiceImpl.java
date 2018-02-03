/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlSchoolYear;
import com.tmser.schevaluation.evl.dao.EvlSchoolYearDao;
import com.tmser.schevaluation.evl.service.EvlSchoolYearService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yangchao
 * @version $Id: EvlSchoolYearServiceImpl.java, v 1.0 2016年8月1日 下午4:16:06 yangchao Exp $
 */
@Service
@Transactional
public class EvlSchoolYearServiceImpl extends AbstractService<EvlSchoolYear, Integer> implements  EvlSchoolYearService{

	@Autowired
	private EvlSchoolYearDao Dao;
	
	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlSchoolYear, Integer> getDAO() {
		return Dao;
	}

	

}
