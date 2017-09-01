/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.notice.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mainbo.jy.common.dao.AbstractDAO;
import com.mainbo.jy.notice.bo.JyNotice;
import com.mainbo.jy.notice.dao.JyNoticeDao;

/**
 * 通知 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-10 Generate Tools Exp $
 */
@Repository
public class JyNoticeDaoImpl extends AbstractDAO<JyNotice,Long> implements JyNoticeDao {
	
	/**
	 * 获取下一篇通知
	 * @param noticeId
	 * @return
	 * @see com.mainbo.jy.notice.dao.JyNoticeDao#getNextNotice(java.lang.Long)
	 */
	@Override
	public JyNotice getNextNotice(Long noticeId,Integer receiverState,Integer receiverId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("id", noticeId);
		args.put("receiverState", receiverState);
		args.put("receiverId", receiverId);
		return this.queryByNamedSqlForSingle("select * from JyNotice jy where jy.receiverId=:receiverId and jy.sendDate<(select sendDate from JyNotice where id=:id) and receiverState=:receiverState order by sendDate desc limit 1", args,this.mapper);
	}

	@Override
	public JyNotice getPreNotice(Long noticeId,Integer receiverState,Integer receiverId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("id", noticeId);
		args.put("receiverState", receiverState);
		args.put("receiverId", receiverId);
		return this.queryByNamedSqlForSingle("select * from JyNotice jy where jy.receiverId=:receiverId and jy.sendDate>(select sendDate from JyNotice where id=:id) and receiverState=:receiverState order by sendDate limit 1", args,this.mapper);
	}
	
}
