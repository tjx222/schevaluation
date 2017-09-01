/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.notice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.notice.bo.JyNotice;
import com.mainbo.jy.notice.constants.JyNoticeConstants;
import com.mainbo.jy.notice.dao.JyNoticeDao;
import com.mainbo.jy.notice.service.JyNoticeService;
import com.mainbo.jy.notice.service.bo.MessageNumCacheEntity;
import com.mainbo.jy.uc.utils.CurrentUserContext;
import com.mainbo.jy.utils.JyAssert;

/**
 * 通知 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyNotice.java, v 1.0 2015-05-10 Generate Tools Exp $
 */
@Service
@Transactional
public class JyNoticeServiceImpl extends AbstractService<JyNotice, Long>
    implements JyNoticeService {

  @Autowired
  private JyNoticeDao jyNoticeDao;

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache messageNumCache;

  public static final String defaultLink = "jy_front_url";

  @PostConstruct
  public void init() {
    messageNumCache = cacheManager.getCache("messageNumCache");
  }

  /**
   * @return
   * @see com.mainbo.jy.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<JyNotice, Long> getDAO() {
    return jyNoticeDao;
  }

  /**
   * 新增通知
   * 
   * @param notice
   * @see com.mainbo.jy.notice.service.JyNoticeService#addNotice(com.mainbo.jy.notice.bo.JyNotice)
   */
  @Override
  public void addNotice(JyNotice notice) {
    // 校验通知
    checkNotice(notice);
    notice.setReceiverState(notice.getReceiverState() == null
        ? JyNoticeConstants.RECIEVER_STATE_UNREAD : notice.getReceiverState());
    notice.setSenderState(notice.getSendDate() == null
        ? JyNoticeConstants.SEND_STATE_UNDELETE : notice.getSenderState());
    notice.setSendDate(new Date());
    notice.setSenderStateChangeDate(new Date());
    jyNoticeDao.insert(notice);
    ValueWrapper valueWrapper = messageNumCache.get(notice.getReceiverId());
    // 未读通知新增1
    if (valueWrapper != null) {
      MessageNumCacheEntity entry = (MessageNumCacheEntity) valueWrapper.get();
      entry.increaseNotice(1);
    }
  }

  /**
   * 教研通知
   * 
   * @param notice
   */
  private void checkNotice(JyNotice notice) {
    JyAssert.notNull(notice, "通知不能为空！");
    JyAssert.notNull(notice.getReceiverId(), "接受者id不能为空！");
    JyAssert.notNull(notice.getSenderId(), "发送者id不能为空！");
    JyAssert.notBlank(notice.getTitle(), "通知不能为空！");
    JyAssert.notNull(notice.getDetailType(), "消息打开类型不能为空！");
    JyAssert.notBlank(notice.getDetail(), "消息详情不能为空！");
    JyAssert.notNull(notice.getBusinessType(), "业务类型不能为空！");
  }

  /**
   * 阅读通知
   * 
   * @param notice
   * @see com.mainbo.jy.notice.service.JyNoticeService#readNotice(com.mainbo.jy.notice.bo.JyNotice)
   */
  @Override
  public void readNotice(Long noticeId) {
    JyNotice notice = new JyNotice();
    notice.setId(noticeId);
    notice.setReceiverState(JyNoticeConstants.RECIEVER_STATE_READED);
    notice.addCustomCondition("and receiverState=0",
        new HashMap<String, Object>());
    int update = jyNoticeDao.update(notice);
    if (update == 1) {
      JyNotice j = jyNoticeDao.get(noticeId);
      ValueWrapper valueWrapper = messageNumCache.get(j.getReceiverId());
      // 未读通知减1
      if (valueWrapper != null) {
        MessageNumCacheEntity entry = (MessageNumCacheEntity) valueWrapper
            .get();
        if (entry != null)
          entry.increaseNotice(-1);
      }
    }
  }

  /**
   * 获取下一篇通知
   * 
   * @param noticeId
   * @return
   * @see com.mainbo.jy.notice.service.JyNoticeService#getNextNotice(java.lang.Long)
   */
  @Override
  public JyNotice getNextNotice(Long noticeId, Integer receiverState) {
    return jyNoticeDao.getNextNotice(noticeId, receiverState,
        CurrentUserContext.getCurrentUserId());
  }

  /**
   * 获取上一篇通知
   * 
   * @param noticeId
   * @return
   * @see com.mainbo.jy.notice.service.JyNoticeService#getPreNotice(java.lang.Long)
   */
  @Override
  public JyNotice getPreNotice(Long noticeId, Integer receiverState) {
    return jyNoticeDao.getPreNotice(noticeId, receiverState,
        CurrentUserContext.getCurrentUserId());
  }

  /**
   * 批量删除通知
   * 
   * @param ids
   * @see com.mainbo.jy.notice.service.JyNoticeService#delteNotices(java.util.List)
   */
  @Override
  public void delteNotices(List<Long> ids) {

    for (Long id : ids) {
      JyNotice notice = jyNoticeDao.get(id);
      if (notice != null) {
        jyNoticeDao.delete(id);
        ValueWrapper valueWrapper = messageNumCache.get(notice.getReceiverId());
        // 如果删除的是未读消息
        if (notice.getReceiverState() == 0) {
          // 未读通知减1
          if (valueWrapper != null) {
            MessageNumCacheEntity entry = (MessageNumCacheEntity) valueWrapper
                .get();
            entry.increaseNotice(-1);
          }
        }

      }
    }

  }

  /**
   * 批量标记已读
   * 
   * @param noticeIds
   */
  @Override
  public void reaNotices(List<Long> noticeIds) {
    for (Long id : noticeIds) {
      this.readNotice(id);
    }

  }
}
