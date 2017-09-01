/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.dao.impl;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.dao.EvlQuestionMemberDao;
import com.mainbo.jy.evl.statics.EvlMemberStatus;

/**
 * 问卷用户表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionMember.java, v 1.0 2016-05-24 Generate Tools Exp $
 */
@Repository
public class EvlQuestionMemberDaoImpl extends AbstractDAO<EvlQuestionMember,Integer> implements EvlQuestionMemberDao {

	@Override
	public void deleteMember(Integer orgId, Integer questionnairesId) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from EvlQuestionMember where orgId = ? and questionId = ?");
		update(sql.toString(), new Object[]{orgId,questionnairesId});
	}

	@Override
	public void updateMemberStatus(Integer questionnairesId) {
		StringBuilder sql = new StringBuilder();
		sql.append("update EvlQuestionMember set status=? where questionId = ? and status < ?");
		update(sql.toString(), new Object[]{EvlMemberStatus.qiquan.getValue(),questionnairesId,EvlMemberStatus.tijiaowenjuan.getValue()});
		
	}
}
