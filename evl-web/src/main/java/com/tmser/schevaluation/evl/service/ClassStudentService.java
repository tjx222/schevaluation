/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.evl.bo.ClassStudent;

/**
 * 学生表 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ClassStudent.java, v 1.0 2016-05-12 Generate Tools Exp $
 */

public interface ClassStudentService extends BaseService<ClassStudent, Integer>{

	/**
	 * @param classStudent
	 */
	void delete(ClassStudent classStudent);
}
