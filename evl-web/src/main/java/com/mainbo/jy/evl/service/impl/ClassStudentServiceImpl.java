/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.dao.ClassStudentDao;
import com.mainbo.jy.evl.service.ClassStudentService;
/**
 * 学生表 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ClassStudent.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Service
@Transactional
public class ClassStudentServiceImpl extends AbstractService<ClassStudent, Integer> implements ClassStudentService {

	@Autowired
	private ClassStudentDao Dao;
	
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<ClassStudent, Integer> getDAO() {
		return Dao;
	}

	/**
	 * @param classStudent
	 * @see com.mainbo.jy.evl.service.ClassStudentService#delete(com.mainbo.jy.evl.bo.ClassStudent)
	 */
	@Override
	public void delete(ClassStudent classStudent) {
		Dao.delete(classStudent);
	}

}
