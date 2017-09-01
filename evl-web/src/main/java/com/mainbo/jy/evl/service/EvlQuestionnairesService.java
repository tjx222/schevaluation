/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.mainbo.jy.common.service.BaseService;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlStudentAccount;

/**
 * 考核评教相关设置表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlQuestionnaires.java, v 1.0 2016-05-09 Generate Tools Exp $
 */

public interface EvlQuestionnairesService extends
    BaseService<EvlQuestionnaires, Integer> {

  /**
   * 初始化问卷
   * 
   * @return
   */
  EvlQuestionnaires initQuestion(Integer id);

  /**
   * 修改问卷统一入口
   * 
   * @param questionnairesId
   * @param status
   */
  void changeQuestionStatus(Integer questionnairesId, Integer status);

  /**
   * 导出当前问卷下的所有用户
   * 
   * @param response
   * @param solutionId
   * @param status
   */
  void downloadAccount(HttpServletResponse response, Integer solutionId,
      Integer status, String weburl);

  /**
   * 获取当前问卷学生
   * 
   * @param questionnairesId
   * @return
   */
  List<EvlStudentAccount> getStudentsList(Integer questionnairesId);

  /**
   * 根据年级id获取班级列数据表
   * 
   * @param questionnairesId
   * @param gradeId
   * @return
   */
  List<EvlStudentAccount> findClassListByGradeId(Integer questionnairesId,
      Integer gradeId);

  /**
   * 根据用户id查询问卷列表
   * 
   * @return List
   */
  List<Map<String, Object>> getQuestions();

}
