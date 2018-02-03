/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.notice.service;

import java.util.List;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.notice.bo.JyNotice;

/**
 * 通知 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-10 Generate Tools Exp $
 */

public interface JyNoticeService extends BaseService<JyNotice, Long>{
	
	/**
	 * 新增通知
	 * @param notice
	 */
	public void addNotice(JyNotice notice);
	
	/**
	 * 阅读通知
	 * @param notice
	 */
	public void readNotice(Long noticeId);
	
	/**
	 * 获取下一篇通知
	 * @param noticeId
	 * @return
	 */
	public JyNotice getNextNotice(Long noticeId,Integer receiverState);
	
	/**
	 * 获取上一篇通知
	 * @param noticeId
	 * @return
	 */
	public JyNotice getPreNotice(Long noticeId,Integer receiverState);
	
	/**
	 * 批量删除通知
	 * @param ids
	 */
	public void delteNotices(List<Long> ids);
	
	/**
	 * 批量标记已阅读
	 * @param noticeIds
	 */
	public void reaNotices(List<Long> noticeIds);
	
}
