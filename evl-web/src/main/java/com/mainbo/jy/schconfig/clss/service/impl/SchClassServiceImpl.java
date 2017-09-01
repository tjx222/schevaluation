/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.schconfig.clss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;
import com.mainbo.jy.schconfig.clss.dao.SchClassDao;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.schconfig.clss.service.SchClassUserService;

/**
 * 学校班级 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */
@Service
@Transactional
public class SchClassServiceImpl extends AbstractService<SchClass, Integer> implements SchClassService {

	@Autowired
	private SchClassDao schClassDao;
	@Autowired
	private SchClassUserService schClassUserService;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchClass, Integer> getDAO() {
		return schClassDao;
	}

	@Override
	public SchClassUser getUserIdByGradeAndBanJi(Integer gradeId,Integer subjectId,Integer banjiId) {
		SchClassUser classUser = new SchClassUser();
		classUser.setClassId(banjiId);
		classUser.setSubjectId(subjectId);
		return  schClassUserService.findOne(classUser);				
	}
}
