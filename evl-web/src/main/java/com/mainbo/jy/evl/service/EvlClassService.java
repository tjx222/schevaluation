/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.EvlClass;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;

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
