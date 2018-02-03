/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao;

import java.util.List;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;

 /**
 * 学生账号表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlStudentAccount.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
public interface EvlStudentAccountDao extends BaseDAO<EvlStudentAccount, String>{
	public List<EvlStudentAccount> listAll(EvlStudentAccount model,ClassStudent classModel);

	/**
	 * @param model
	 * @param classModel
	 * @return
	 */
	public PageList<EvlStudentAccount> listPage(EvlStudentAccount model,ClassStudent classModel);
}