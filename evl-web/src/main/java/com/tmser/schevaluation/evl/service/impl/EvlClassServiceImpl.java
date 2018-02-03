/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlClass;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.dao.EvlClassDao;
import com.tmser.schevaluation.evl.service.EvlClassService;

/**
 * 班级表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlClass.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlClassServiceImpl extends AbstractService<EvlClass, Integer> implements EvlClassService {

	@Autowired
	private EvlClassDao Dao;

	/**
	 * @return
	 * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlClass, Integer> getDAO() {
		return Dao;
	}

	@Override
	public List<EvlClass> findClassListByQuestionnairesId(EvlQuestionnaires evlQuestionnaires) {
		List<EvlClass> classList = new ArrayList<EvlClass>();
		if(evlQuestionnaires != null){
			EvlClass ec = new EvlClass();
			ec.setOrgId(evlQuestionnaires.getOrgId());
			ec.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
			if (evlQuestionnaires.getStudentType() == EvlQuestionnaires.student_type_nianji) {
				ec.addCustomCondition(" and classId  is  null");
			} else if (evlQuestionnaires.getStudentType() == EvlQuestionnaires.student_type_banji) {
				ec.addCustomCondition(" and classId  is not  null");
			}
			classList = this.findAll(ec);
		}
		return classList;
	}
}
