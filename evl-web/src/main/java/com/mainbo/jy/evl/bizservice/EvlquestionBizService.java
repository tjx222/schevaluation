package com.mainbo.jy.evl.bizservice;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.evl.vo.EvlQuestionnairesVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.uc.bo.UserSpace;

public interface EvlquestionBizService {
  /**
   * 通过问卷ID获取"应评"学生集合
   * 
   * @param questionnairesId
   * @return
   */
  public Integer getQuestionStudentsList(Integer questionnairesId);

  /**
   * 生成账号
   * 
   * @param model
   * @return
   */
  public Result gerenateAccountUrl(EvlQuestionnaires model);

  /**
   * 通过方案获取学科列表
   * 
   * @param questionnairesId
   * @return
   */
  public List<SubjectVo> findSubjectByQuestionId(Integer questionnairesId);

  /**
   * 获取当前机构下有操作evl权限的用户
   * 
   * @return
   */
  public Map<Integer, Object> getEvlPowerUserList();

  /**
   * 根据问卷id和用户code跳转填报提示信息
   * 
   * @param questionId
   * @param userCode
   * @return
   */
  public String getFillPrompt(Integer questionId, String userCode);

  /**
   * 获得指定角色的用户信息{系统角色}
   * 
   * @param orgId
   * @param isDistink
   *          isDistink是否去重 true：去重 false：不去重
   * @param schoolYear
   *          学年
   * @param roleIds
   *          多个或一个角色id
   * @return
   */
  public List<UserSpace> getAllUserBySysRoleId(Integer orgId, boolean isDistink,
      Integer... roleIds);

  /**
   * 通过问卷ID获取学生集合{当前问卷初始化的学生[按班级选择、按年级选择、全体学生]}:---分页查询---
   * 
   * @param questionnairesId
   * @param account
   *          参数筛选接收model
   *          pageSize 用于其他非分页调用此接口，修改pageSize大小即可
   * @return
   */
  public PageList<EvlStudentAccount> getQuestionStudentsListByPage(
      Integer questionnairesId, EvlStudentAccount account,
      ClassStudent classModel, Integer... pageSize);

  /**
   * 获取问卷结果列表
   * 
   * @param schoolYear
   * @param status
   * @param currentPage
   * @param pageSize
   * @return
   */
  List<EvlQuestionnairesVo> findResultQuestionnairesList(Integer schoolYear,
      Integer status, Integer currentPage, Integer pageSize);

  /**
   * 获取问卷结果列表
   * 
   * @param schoolYear
   * @param status
   * @param currentPage
   * @param pageSize
   * @return
   */
  Map<String, Object> findResultIndex(Integer status);

  void downloadQuestionMemberDetail(HttpServletResponse response,
      Integer questionId, EvlQuestionMember member);

  String getQuestionOperateType();
}
