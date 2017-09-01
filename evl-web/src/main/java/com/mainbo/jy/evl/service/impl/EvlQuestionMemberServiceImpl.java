/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.dao.EvlQuestionMemberDao;
import com.mainbo.jy.evl.dao.EvlQuestionnairesDao;
import com.mainbo.jy.evl.service.EvlQuestionMemberService;
import com.mainbo.jy.evl.statics.EvlMemberStatus;

/**
 * 问卷用户表 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: EvlQuestionMember.java, v 1.0 2016-05-24 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlQuestionMemberServiceImpl extends
    AbstractService<EvlQuestionMember, Integer> implements
    EvlQuestionMemberService {

  @Autowired
  private EvlQuestionMemberDao evlQuestionMemberDao;

  @Autowired
  private EvlQuestionnairesDao evlQuestionnairesDao;

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache questionCache;

  private Cache getQuestionCache() {
    if (this.cacheManager != null && questionCache == null) {
      questionCache = cacheManager.getCache("evl_question_cache");
    }
    return questionCache;
  }

  /**
   * @return
   * @see com.mainbo.jy.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<EvlQuestionMember, Integer> getDAO() {
    return evlQuestionMemberDao;
  }

  @Override
  public List<EvlQuestionMember> getNotInputStudent(Integer questionId) {
    EvlQuestionnaires currentQuestion = evlQuestionnairesDao.get(questionId);
    EvlQuestionMember model = new EvlQuestionMember();
    model.setOrgId(currentQuestion.getOrgId());
    model.setSchoolYear(currentQuestion.getSchoolYear());
    model.setQuestionId(questionId);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("status", EvlMemberStatus.tijiaowenjuan.getValue());
    model.addCustomCondition("and status <:status", paramMap);
    return this.findAll(model);
  }

  @Override
  public PageList<EvlQuestionMember> memberFindByPage(EvlQuestionMember m,
      Integer questionId) {
    m.setQuestionId(questionId);
    if (m.getGradeId() == null) {
      m.setClassId(null);
    }
    if (m.getStatus() != null) {
      if (EvlMemberStatus.tijiaowenjuan.getValue().equals(m.getStatus())) {
        m.setStatus(m.getStatus());
        m.addCustomCondition(" order by status,code");
      } else {
        m.setStatus(null);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("status", EvlMemberStatus.tijiaowenjuan.getValue());
        m.addCustomCondition("and status <> :status order by status,code ",
            paramsMap);
      }
    } else {
      m.addCustomCondition(" order by status,code");
    }
    PageList<EvlQuestionMember> memberList = this.findByPage(m);
    return memberList;
  }

  @Override
  public void deleteMember(Integer orgId, Integer questionnairesId) {
    this.evlQuestionMemberDao.deleteMember(orgId, questionnairesId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<EvlQuestionMember> getStudentList(Integer questionId, int status) {
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_stu_q" + questionId + "_s" + status);
      if (cacheElement != null) {
        return (List<EvlQuestionMember>) cacheElement.get();
      }
    }
    EvlQuestionnaires currentQuestion = evlQuestionnairesDao.get(questionId);
    EvlQuestionMember model = new EvlQuestionMember();
    model.setOrgId(currentQuestion.getOrgId());
    model.setSchoolYear(currentQuestion.getSchoolYear());
    model.setQuestionId(questionId);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("status", status);
    model.addCustomCondition("and status =:status", paramMap);
    List<EvlQuestionMember> students = this.findAll(model);
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_stu_q" + questionId + "_s" + status,
          students);
    }
    return students;
  }

  @Override
  public void updateMemberStatus(Integer questionnairesId) {
    evlQuestionMemberDao.updateMemberStatus(questionnairesId);
  }

  @Override
  public Integer getAlreadySubmitStudentsList(Integer questionnairesId) {
    EvlQuestionMember member = new EvlQuestionMember();
    member.setQuestionId(questionnairesId);
    member.setStatus(EvlMemberStatus.tijiaowenjuan.getValue());
    return this.count(member);
  }
}
