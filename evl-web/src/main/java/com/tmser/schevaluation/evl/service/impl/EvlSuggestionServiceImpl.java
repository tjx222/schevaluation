/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.echarts.Option;
import com.tmser.schevaluation.common.dao.BaseDAO;
import com.tmser.schevaluation.common.service.AbstractService;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlSuggestion;
import com.tmser.schevaluation.evl.bo.EvlSuggestionType;
import com.tmser.schevaluation.evl.dao.EvlSuggestionDao;
import com.tmser.schevaluation.evl.service.EvlAnalyzeTeacherService;
import com.tmser.schevaluation.evl.service.EvlQuestionMemberService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.service.EvlSuggestionService;
import com.tmser.schevaluation.evl.service.EvlSuggestionTypeService;
import com.tmser.schevaluation.evl.utils.EchartUtil;
import com.tmser.schevaluation.evl.vo.GradeAndClassVo;

/**
 * 建议表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlSuggestion.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlSuggestionServiceImpl extends
    AbstractService<EvlSuggestion, Integer> implements EvlSuggestionService {

  @Autowired
  private EvlSuggestionDao Dao;
  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private EvlSuggestionTypeService evlSuggestionTypeService;
  @Autowired
  private EvlQuestionMemberService evlQuestionMemberService;
  @Autowired
  private EvlAnalyzeTeacherService evlAnalyzeTeacherService;
  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache questionCache;

  private Cache getQuestionCache() {
    if (this.cacheManager != null && questionCache == null) {
      questionCache = cacheManager.getCache("evl_result_cache");
    }
    return questionCache;
  }

  /**
   * @return
   * @see com.tmser.schevaluation.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<EvlSuggestion, Integer> getDAO() {
    return Dao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findAnalyzeSuggestionList(Integer questionId,
      boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_yj_q" + questionId);
      if (cacheElement != null) {
        returnMap = (Map<String, Object>) cacheElement.get();
        return returnMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    if (eq != null) {
      EvlSuggestion es = new EvlSuggestion();
      es.setOrgId(eq.getOrgId());
      es.setQuestionnairesId(eq.getQuestionnairesId());
      es.addCustomCulomn("suggestId,gradeId,code");
      es.addCustomCondition(" and (content is not null and content<>'') ");
      // 反馈类型
      Map<String, Object> suggestionMap = this
          .getAnalyzeSuggestionListOfSuggestion(es, isDownload);
      returnMap.put("questionnairesCount",
          suggestionMap.get("questionnairesCount"));
      returnMap.put("suggestionList", suggestionMap.get("suggestionList"));
      returnMap.put("titleSize", suggestionMap.get("titleSize"));
      // 年级
      Map<String, Object> suggestionGradeMap = this
          .getAnalyzeSuggestionListOfGrade(eq, es, isDownload);
      returnMap.put("suggestionGradeList",
          suggestionGradeMap.get("suggestionGradeList"));
      returnMap.put("suggestionCount",
          suggestionGradeMap.get("suggestionCount"));
      if (!isDownload) {
        returnMap.put("option1", suggestionMap.get("option"));
        returnMap.put("option2", suggestionGradeMap.get("option"));
      }
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_yj_q" + questionId, returnMap);
    }
    return returnMap;
  }

  /**
   * 反馈类别分组统计
   * 
   * @param es
   * @return
   */
  private Map<String, Object> getAnalyzeSuggestionListOfSuggestion(
      EvlSuggestion es, boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(es
        .getQuestionnairesId());
    if (eq != null) {
      EvlSuggestionType est = new EvlSuggestionType();
      est.setOrgId(eq.getOrgId());
      est.setQuestionnairesId(eq.getQuestionnairesId());
      est.addOrder(" crtDttm asc ");
      List<EvlSuggestionType> estList = evlSuggestionTypeService.findAll(est);
      if (CollectionUtils.isEmpty(estList)) {
        returnMap.put("suggestionList", null);
        returnMap.put("questionnairesCount", null);
        returnMap.put("option", null);
        return returnMap;
      }
      List<EvlSuggestion> esList = this.findAll(es);
      Map<String, EvlSuggestion> suggMap = new HashMap<String, EvlSuggestion>();
      Map<String, Integer> suggCountMap = new HashMap<String, Integer>();
      for (EvlSuggestion sug : esList) {
        String suggestId = sug.getSuggestId();
        EvlSuggestion suggest = suggMap.get(suggestId);
        if (suggest == null) {
          suggMap.put(suggestId, sug);
          suggCountMap.put(suggestId, 1);
        } else {
          suggCountMap.put(suggestId, suggCountMap.get(suggestId) + 1);
        }
      }
      esList = new ArrayList<EvlSuggestion>(suggMap.values());
      EvlQuestionMember eqm = new EvlQuestionMember();
      eqm.setOrgId(es.getOrgId());
      eqm.setQuestionId(es.getQuestionnairesId());
      int questionnairesCount = evlQuestionMemberService.count(eqm);// 调用问卷人数接口
      String[] suggestionTitle = new String[estList.size()];
      Double[] suggestionData = new Double[estList.size()];
      List<Map<String, String>> suggestionList = new ArrayList<Map<String, String>>();
      for (int i = 0; i < estList.size(); i++) {
        Map<String, String> suggestionMap = new HashMap<String, String>();
        String title = estList.get(i).getName();
        suggestionMap.put("suggestionName", title);
        suggestionMap.put("suggestionCount", "0");
        suggestionMap.put("suggestionPercent", "0.0");
        Integer count = suggCountMap.get(estList.get(i).getId().toString()) == null ? 0
            : suggCountMap.get(estList.get(i).getId().toString());
        suggestionMap.put("suggestionCount", count + "");
        if (questionnairesCount > 0) {
          suggestionMap.put(
              "suggestionPercent",
              new DecimalFormat("0.00").format(count * 100.0
                  / questionnairesCount));
        }
        suggestionList.add(suggestionMap);
        suggestionTitle[i] = title;
        suggestionData[i] = Double.valueOf(suggestionMap
            .get("suggestionPercent"));
      }
      returnMap.put("suggestionList", suggestionList);
      returnMap.put("questionnairesCount", questionnairesCount);
      if (!isDownload) {
        Option option = EchartUtil.packageBarOption("意见建议反馈比例图",
            suggestionTitle, "所占比例", suggestionData);
        option.getSeries().get(0).getItemStyle().getNormal().getLabel()
            .formatter("{c}%");
        option.grid().y2(110);
        returnMap.put("titleSize", suggestionTitle.length);
        returnMap.put("option", option);
      }
    }
    return returnMap;
  }

  /**
   * 反馈年级分组统计
   * 
   * @param es
   * @return
   */
  private Map<String, Object> getAnalyzeSuggestionListOfGrade(
      EvlQuestionnaires eq, EvlSuggestion es, boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    List<EvlSuggestion> esList = this.findAll(es);
    if (CollectionUtils.isEmpty(esList)) {
      returnMap.put("suggestionGradeList", null);
      returnMap.put("suggestionCount", null);
      returnMap.put("option", null);
      return returnMap;
    }
    // 去重
    Map<Integer, EvlSuggestion> suggMap = new HashMap<Integer, EvlSuggestion>();
    Map<Integer, Integer> suggCountMap = new HashMap<Integer, Integer>();
    for (EvlSuggestion sug : esList) {
      Integer gradeId = sug.getGradeId();
      EvlSuggestion suggest = suggMap.get(gradeId);
      if (suggest == null) {
        suggMap.put(gradeId, sug);
        suggCountMap.put(gradeId, 1);
      } else {
        suggCountMap.put(gradeId, suggCountMap.get(gradeId) + 1);
      }
    }
    List<GradeAndClassVo> gradeList = evlAnalyzeTeacherService
        .getGradeVoList(eq);
    int[] count = new int[gradeList.size()];
    int allCount = 0;
    for (int i = 0; i < gradeList.size(); i++) {
      GradeAndClassVo mv = gradeList.get(i);
      count[i] = suggCountMap.get(mv.getId()) == null ? 0 : suggCountMap.get(mv
          .getId());
      allCount += count[i];
    }
    String[] gradeTitle = new String[gradeList.size()];
    Double[] gradeData = new Double[gradeList.size()];
    List<Map<String, String>> suggestionGradeList = new ArrayList<Map<String, String>>();
    for (int i = 0; i < gradeList.size(); i++) {
      Map<String, String> gradeMap = new HashMap<String, String>();
      gradeMap.put("gradeName", gradeList.get(i).getName());
      gradeMap.put("gradeId", gradeList.get(i).getId() + "");
      gradeMap.put("gradeCount", count[i] + "");
      double gradePercent = 0.0;
      if (allCount > 0) {
        gradePercent = Double.valueOf(new DecimalFormat("0.00")
            .format((count[i]) * 100.0 / allCount));
      }
      gradeMap.put("gradePercent", gradePercent + "");
      suggestionGradeList.add(gradeMap);
      gradeTitle[i] = gradeList.get(i).getName();
      gradeData[i] = gradePercent;
    }
    returnMap.put("suggestionGradeList", suggestionGradeList);
    returnMap.put("suggestionCount", allCount);
    if (!isDownload) {
      Option option = EchartUtil.packageBarOption("年级意见反馈比例图", gradeTitle,
          "所占比例", gradeData);
      option.getSeries().get(0).getItemStyle().getNormal().getLabel()
          .formatter("{c}%");
      option.grid().y(120);
      returnMap.put("option", option);
    }
    return returnMap;
  }

  /**
   * @param suggest
   * @return
   * @see com.tmser.schevaluation.evl.service.EvlSuggestionService#saveOrUpdate(com.tmser.schevaluation.evl.bo.EvlSuggestion)
   */
  @Override
  public boolean saveOrUpdate(EvlSuggestion model) {
    if (model != null && model.getQuestionnairesId() != null
        && model.getCode() != null && model.getSuggestId() != null) {

      EvlSuggestion smodel = new EvlSuggestion();
      smodel.setQuestionnairesId(model.getQuestionnairesId());
      smodel.setSuggestId(model.getSuggestId());
      smodel.setCode(model.getCode());
      smodel.setOrgId(model.getOrgId());

      EvlSuggestion temp = findOne(smodel);
      if (temp == null) {
        save(model);
      } else {
        model.setId(temp.getId());
        update(model);
      }
      return true;
    }
    return false;
  }
}
