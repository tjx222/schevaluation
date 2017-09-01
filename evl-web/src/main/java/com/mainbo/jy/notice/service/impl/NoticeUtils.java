package com.mainbo.jy.notice.service.impl;

import java.util.Date;
import java.util.Map;

import com.mainbo.jy.notice.bo.JyNotice;
import com.mainbo.jy.notice.constants.JyNoticeConstants;
import com.mainbo.jy.notice.constants.NoticeType;
import com.mainbo.jy.notice.service.JyNoticeService;
import com.mainbo.jy.utils.SpringContextHolder;

/**
 * 
 * <pre>
 *	通知工具类
 * </pre>
 *
 * @author wanzheng,tmser
 * @version $Id: NoticeUtils.java, v 1.0 May 15, 2015 10:02:05 AM wanzheng Exp $
 */
public class NoticeUtils{
	
	/**
	 * 发送通知
	 * @param businessType
	 * @param info
	 */
	public static void sendNotice(NoticeType noticeType,String title,Integer senderId,String receiverId,Map<String,Object> info){
		JyNotice notice = initNotice(noticeType,senderId,receiverId);
		notice.setTitle(title);
		notice.setDetail(getUrl((String)info.get("params")));
		addNotice(notice);
	}
	private static String  getUrl(String params){
		return new StringBuilder()
		.append("/o/")
		.append(params)
		.toString();
	}
	
	/**
	 * 初始化通知对象
	 * @param businessType
	 * @param senderId
	 * @param receiverId
	 * @return
	 */
	private static JyNotice initNotice(NoticeType noticeType, Integer senderId,
			String receiverId) {
		JyNotice notice = new JyNotice();
		notice.setBusinessType(noticeType.getValue());
		notice.setSenderId(senderId);
		notice.setSenderState(JyNoticeConstants.SEND_STATE_UNDELETE);
		notice.setReceiverId(receiverId);
		notice.setReceiverState(JyNoticeConstants.RECIEVER_STATE_UNREAD);
		notice.setDetailType(1);
		notice.setSenderStateChangeDate(new Date());
		notice.setReceiverStateChangeDate(new Date());
		return notice;
	}

	/**
	 * 新增通知
	 * @param notice
	 */
	private static void addNotice(JyNotice notice) {
		JyNoticeService service=SpringContextHolder.getBean(JyNoticeService.class);
		service.addNotice(notice);
	}
}
