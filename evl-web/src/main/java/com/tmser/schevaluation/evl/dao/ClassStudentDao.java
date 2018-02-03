/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.evl.bo.ClassStudent;

 /**
 * 学生表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ClassStudent.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
public interface ClassStudentDao extends BaseDAO<ClassStudent, Integer>{

	/**重写父类delete方法，根据model删除
	 * @param classStudent
	 */
	void delete(ClassStudent classStudent);

}