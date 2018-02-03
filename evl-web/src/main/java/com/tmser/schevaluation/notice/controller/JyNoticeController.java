/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.notice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.notice.bo.JyNotice;
import com.tmser.schevaluation.notice.constants.JyNoticeConstants;
import com.tmser.schevaluation.notice.service.JyNoticeService;
import com.tmser.schevaluation.notice.service.bo.MessageNumCacheEntity;
import com.tmser.schevaluation.uc.utils.CurrentUserContext;

/**
 * 通知控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/notice")
public class JyNoticeController extends AbstractController {

	@Autowired
	private JyNoticeService jyNoticeService;

	// 超时时间5分钟
	private static final Long TIME_OUT = 20000L;

	@Resource(name = "cacheManger")
	private CacheManager cacheManager;

	private Cache messageNumCache;

	@PostConstruct
	public void init() {
		messageNumCache = cacheManager.getCache("messageNumCache");
	}

	/**
	 * 新增通知
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping(value = "/notices", method = RequestMethod.POST)
	public Result addNotice(JyNotice notice) {
		notice.setSendDate(new Date());
		notice.setSenderStateChangeDate(new Date());
		notice.setReceiverId(CurrentUserContext.getCurrentUserId().toString());
		notice.setSenderId(CurrentUserContext.getCurrentUserId());
		notice.setReceiverStateChangeDate(new Date());
		try {
			jyNoticeService.addNotice(notice);
		} catch (Exception e) {
			logger.warn("add notice failed",e);
		}
		return new Result("success", false);
	}

	/**
	 * 查询通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public ModelAndView getNotices(JyNotice model) {
	  ModelAndView result = new ModelAndView("notice/notice_index");
    model.pageSize(10);
    Integer receiverState = model.getReceiverState();
    model.addCustomCondition("order by sendDate desc", new HashMap<String, Object>());
    model.setReceiverId(CurrentUserContext.getCurrentUserId().toString());
    model.addCustomCulomn("id,senderId,title,businessType,sendDate");
    PageList<JyNotice> page = jyNoticeService.findByPage(model);
    if (page.getTotalCount() == 0 && receiverState == null) {
      model.setReceiverState(2);
      page = jyNoticeService.findByPage(model);
    }
    result.addObject("data", page);
    result.addObject("receiverState", model.getReceiverState());
    return result;
	}

	/**
	 * 查询通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/notices_mobile", method = RequestMethod.POST)
	public ModelAndView getNotices_mobile(JyNotice model) {
		ModelAndView result = new ModelAndView("notice/notice_index");
		model.pageSize(15);
		Integer receiverState = model.getReceiverState();
		if(receiverState == null)
			model.setReceiverState(0);
		model.addCustomCondition("order by sendDate desc",
				new HashMap<String, Object>());
		model.setReceiverId(CurrentUserContext.getCurrentUserId().toString());
		model.addCustomCulomn("id,senderId,title,businessType,sendDate");
		PageList<JyNotice> page = jyNoticeService.findByPage(model);
		if (page.getTotalCount() == 0 && receiverState == null) {
			model.setReceiverState(2);
			page = jyNoticeService.findByPage(model);
		}
		result.addObject("data", page);
		result.addObject("receiverState", model.getReceiverState());
		return result;
	}
	
	/**
	 * 删除通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteNotices", method = RequestMethod.DELETE)
	public Result delteNotices(
			@RequestParam(value = "noticeIds") List<Long> noticeIds) {
		jyNoticeService.delteNotices(noticeIds);
		return new Result();
	}

	/**
	 * 批量标记已阅读
	 * 
	 * @param noticeIds
	 * @return
	 */
	@RequestMapping(value = "/readedNotices", method = RequestMethod.POST)
	public Result reaNotices(@RequestParam("noticeIds") List<Long> noticeIds) {
		jyNoticeService.reaNotices(noticeIds);
		return new Result();
	}

	/**
	 * 删除通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/notices/{id}", method = RequestMethod.DELETE)
	public Result delteNotice(@PathVariable(value = "id") Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		jyNoticeService.delteNotices(ids);
		return new Result();
	}


	/**
	 * 进入同伴聊天页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/senderUsers/{userId}", method = RequestMethod.GET)
	public String viewMessages(@PathVariable("userId") Integer userId) {
		return "forward:/jy/companion/companions/" + userId;
	}

	/**
	 * 下一篇通知
	 * 
	 * @param noticeId
	 * @return
	 */
	@RequestMapping("/notices/nextNotices/{noticeId}")
	public ModelAndView getNextNotice(@PathVariable("noticeId") Long noticeId,
			@RequestParam("receiverState") Integer receiverState) {
		ModelAndView result = new ModelAndView("notice/notice_detail");
		// 获取下一篇通知
		JyNotice notice = jyNoticeService
				.getNextNotice(noticeId, receiverState);
		if (notice == null) {
			result.addObject("hasNext", false);
			result.addObject("notice", jyNoticeService.findOne(noticeId));
		} else {
			result.addObject("notice", notice);
			if (receiverState == 0) {
				// 更新通知为已读
				jyNoticeService.readNotice(notice.getId());
			}
		}
		result.addObject("receiverState", receiverState);
		return result;
	}

	/**
	 * 上一篇通知
	 * 
	 * @param noticeId
	 * @return
	 */
	@RequestMapping("/notices/preNotices/{noticeId}")
	public ModelAndView getPretNotice(@PathVariable("noticeId") Long noticeId,
			@RequestParam("receiverState") Integer receiverState) {
		ModelAndView result = new ModelAndView("notice/notice_detail");
		// 获取上一篇通知
		JyNotice notice = jyNoticeService.getPreNotice(noticeId, receiverState);
		if (notice == null) {
			result.addObject("hasPre", false);
			result.addObject("notice", jyNoticeService.findOne(noticeId));
		} else {
			result.addObject("notice", notice);
			if (receiverState == 0) {
				// 更新通知为已读
				jyNoticeService.readNotice(notice.getId());
			}
		}
		result.addObject("receiverState", receiverState);
		return result;
	}

	@RequestMapping(value = "/notices/{noticeId}")
	public ModelAndView getNoticeDetail(@PathVariable("noticeId") Long noticeId) {
		// 获取通知详情
		JyNotice jyNotice = jyNoticeService.findOne(noticeId);
		// 更新通知为已读
		jyNoticeService.readNotice(noticeId);
		return new ModelAndView("notice/notice_detail", "notice", jyNotice);
	}

	@RequestMapping(value = "/{noticeId}/readed", method = RequestMethod.GET)
	public Result readNotice(@RequestParam("noticeId") Long noticeId) {
		// 更新通知为已读
		jyNoticeService.readNotice(noticeId);
		return new Result("success", false);
	}

	/**
	 * 获取未阅读消息数目
	 * 
	 * @param timestamp
	 * @return
	 */
	@RequestMapping(value = "/unreadNum", method = RequestMethod.GET)
	public @ResponseBody DeferredResult<Result> listener(@RequestParam(value="timestamp",required=false) Long timestamp) {
		DeferredResult<Result> result = new DeferredResult<Result>(TIME_OUT,new Result("timeOut"));
		ValueWrapper valueWrapper = messageNumCache.get(CurrentUserContext.getCurrentUserId());
		// 设置缓存
		if (valueWrapper == null) {
			JyNotice notice = new JyNotice();
			notice.setReceiverId(CurrentUserContext.getCurrentUserId().toString());
			notice.setReceiverState(JyNoticeConstants.RECIEVER_STATE_UNREAD);
			// 查阅未阅读的通知数目
			int unreadNoticeCount = jyNoticeService.count(notice);

			MessageNumCacheEntity entity = new MessageNumCacheEntity();
			entity.increaseNotice(unreadNoticeCount);
			entity.addResult(timestamp, result);
			
			// 放入缓存
			messageNumCache.put(CurrentUserContext.getCurrentUserId(), entity);
			
		} else {
			MessageNumCacheEntity entity = (MessageNumCacheEntity) valueWrapper.get();
			entity.addResult(timestamp, result);
		}
		return result;
	}
	
	public void setJyNoticeService(JyNoticeService jyNoticeService) {
		this.jyNoticeService = jyNoticeService;
	}

}