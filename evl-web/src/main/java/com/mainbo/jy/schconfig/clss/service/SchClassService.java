/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.schconfig.clss.service;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;

/**
 * 学校班级 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */

public interface SchClassService extends BaseService<SchClass, Integer>{

	/**
	 * 获得班级指定学科的教师id
	 * @param sheetName
	 * @param banji
	 * @param xueke
	 * @return
	 */
	public SchClassUser getUserIdByGradeAndBanJi(Integer gradeId,Integer subjectId,Integer banjiId);
}
