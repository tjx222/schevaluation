/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;

import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.EvlQuestionMember;

/**
 * 问卷用户表 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionMember.java, v 1.0 2016-05-24 Generate Tools Exp $
 */

public interface EvlQuestionMemberService extends BaseService<EvlQuestionMember, Integer>{
	/**
	 * 查询未提交用户
	 * @param questionId
	 * @param orgId
	 * @param schoolYear
	 * @return
	 */
	public List<EvlQuestionMember> getNotInputStudent(Integer questionId);
	/**
	 * 根据用户状态查询用户列表
	 * @param questionId
	 * @param orgId
	 * @param schoolYear
	 * @return
	 */
	public List<EvlQuestionMember> getStudentList(Integer questionId,int status);

	/**
	 * 用户评教{应评、已评}详情，筛选分页
	 * @param m
	 * @param questionId
	 * @return
	 */
	public PageList<EvlQuestionMember> memberFindByPage(EvlQuestionMember m,Integer questionId);
	/**
	 * 删除member表信息
	 * @param orgId
	 * @param questionnairesId
	 */
	public void deleteMember(Integer orgId, Integer questionnairesId);
	/**
	 * 更新未提交的用户状态为弃权
	 * @param questionnairesId
	 * @param value
	 */
	public void updateMemberStatus(Integer questionnairesId);
	/**
	 * 查询已提交
	 * @param questionnairesId
	 * @return
	 */
	public Integer getAlreadySubmitStudentsList(Integer questionnairesId);
}
