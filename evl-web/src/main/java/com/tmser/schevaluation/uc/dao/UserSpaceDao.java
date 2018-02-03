/**
1 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.uc.dao;

import java.util.List;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.uc.bo.UserSpace;

 /**
 * 用户空间信息 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */
public interface UserSpaceDao extends BaseDAO<UserSpace, Integer>{
	
	/**
	 * 通过用户id批量查询工作空间
	 * @param userIds
	 * @return
	 */
	List<UserSpace> findByUserIds(List<Integer> userIds);
	
	/**
	 * 更换机构的销售方案
	 * @param solutionId
	 * @param orgId
	 * @param schoolYear
	 */
	void updateUserSolution(Integer solutionId,Integer orgId,Integer schoolYear);

	/**
	 * 通过机构、学科、年级获取用户空间集合
	 * @param orgIdList
	 * @param subjectIdList
	 * @param gradeIdList
	 * @return
	 */
	List<UserSpace> getUserSpaceByOrg_Subject_Grade(List<Integer> orgIdList,
			List<Integer> subjectIdList, List<Integer> gradeIdList);

}