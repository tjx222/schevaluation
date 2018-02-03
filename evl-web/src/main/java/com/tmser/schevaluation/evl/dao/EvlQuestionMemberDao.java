/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.dao;

import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;

 /**
 * 问卷用户表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionMember.java, v 1.0 2016-05-24 Generate Tools Exp $
 */
public interface EvlQuestionMemberDao extends BaseDAO<EvlQuestionMember, Integer>{

	/**
	 * 根据orgID和questionId删除member表数据
	 * @param orgId
	 * @param questionnairesId
	 */
	void deleteMember(Integer orgId, Integer questionnairesId);

	/**
	 * 批量更新未提交的用户状态为弃权
	 * @param questionnairesId
	 * @param value
	 */
	void updateMemberStatus(Integer questionnairesId);
}