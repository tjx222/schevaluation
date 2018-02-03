/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import java.util.List;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.evl.bo.EvlClass;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;

/**
 * 班级表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlClass.java, v 1.0 2016-05-09 Generate Tools Exp $
 */

public interface EvlClassService extends BaseService<EvlClass, Integer> {

	/**
	 * 通过问卷id获取选择的年级班级列表
	 * 
	 * @param orgId
	 * @param questionnairesId
	 * @return
	 */
	public List<EvlClass> findClassListByQuestionnairesId(EvlQuestionnaires evlQuestionnaires);
}
