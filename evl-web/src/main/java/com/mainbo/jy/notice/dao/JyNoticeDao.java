/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.notice.dao;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.notice.bo.JyNotice;

 /**
 * 通知 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-10 Generate Tools Exp $
 */
public interface JyNoticeDao extends BaseDAO<JyNotice, Long>{
	
	/**
	 * 获取下一篇通知
	 * @param noticeId
	 * @return
	 */
	JyNotice getNextNotice(Long noticeId,Integer receiverState,Integer receiverId);
	
	/**
	 * 获取上一篇通知
	 * @param noticeId
	 * @return
	 */
	JyNotice getPreNotice(Long noticeId,Integer receiverState,Integer receiverId);
	
}