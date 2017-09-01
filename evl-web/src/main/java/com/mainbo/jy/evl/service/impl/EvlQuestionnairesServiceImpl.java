/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlClass;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.evl.bo.EvlTimeline;
import com.mainbo.jy.evl.dao.EvlQuestionnairesDao;
import com.mainbo.jy.evl.service.EvlClassService;
import com.mainbo.jy.evl.service.EvlMetaService;
import com.mainbo.jy.evl.service.EvlQuestionMemberService;
import com.mainbo.jy.evl.service.EvlQuestionnairesService;
import com.mainbo.jy.evl.service.EvlStudentAccountService;
import com.mainbo.jy.evl.service.EvlTimelineService;
import com.mainbo.jy.evl.statics.EvlMemberStatus;
import com.mainbo.jy.evl.statics.EvlQuestionType;
import com.mainbo.jy.evl.utils.EvlHelper;
import com.mainbo.jy.evl.utils.ExcleUtils;
import com.mainbo.jy.manage.meta.Meta;
import com.mainbo.jy.manage.meta.MetaUtils;
import com.mainbo.jy.manage.org.bo.Organization;
import com.mainbo.jy.manage.org.service.OrganizationService;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.utils.CurrentUserContext;
import com.mainbo.jy.uc.utils.SessionKey;
import com.mainbo.jy.utils.StringUtils;

/**
 * 考核评教相关设置表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlQuestionnaires.java, v 1.0 2016-05-09 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlQuestionnairesServiceImpl
    extends AbstractService<EvlQuestionnaires, Integer>
    implements EvlQuestionnairesService {

  @Autowired
  private EvlQuestionnairesDao evlQuestionnairesDao;
  @Autowired
  private EvlQuestionMemberService evlQuestionMemberService;
  @Autowired
  private EvlStudentAccountService evlStudentAccountService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private EvlMetaService evlMetaService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private EvlClassService evlClassService;
  @Autowired
  private EvlTimelineService evlTimelineService;

  /**
   * @see com.mainbo.jy.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<EvlQuestionnaires, Integer> getDAO() {
    return evlQuestionnairesDao;
  }

  @Override
  public EvlQuestionnaires initQuestion(Integer id) {
    EvlQuestionnaires question = new EvlQuestionnaires();
    question.setOrder("questionnairesId desc");
    question.setType(EvlQuestionnaires.type_xueshengpingjiao);// 评教类型
    question.setStudentType(EvlQuestionnaires.student_type_quantixusheng);// 哪些人评
    question.setOperationType(EvlQuestionnaires.operation_type_xuenian); // 评教时段
    question.setIndicatorType(EvlQuestionnaires.indicator_type_twoLevel);// 评教指标层级
    question.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
    question.setStatus(EvlQuestionType.init.getValue());// 状态
    question.setSchoolYear(CurrentUserContext.getCurrentSchoolYear());// 学年
    question.setCrtId(CurrentUserContext.getCurrentUser().getId());
    question.setCrtDttm(new Date());
    question.setEnable(EvlQuestionnaires.ENABLE);// 生效：当有教师填报的时候，改为失效
    question.setIscollect(EvlQuestionnaires.iscollect_shouji);
    question.setTotleScore(100d);// 默认分数100分
    question.setPhaseId(EvlHelper.getCurrentPhaseId());
    if (id != null) {
      question.setQuestionnairesId(id);
    }
    return evlQuestionnairesDao.insert(question);
  }

  @Override
  public void changeQuestionStatus(Integer questionnairesId, Integer status) {
    EvlQuestionnaires model = this.findOne(questionnairesId);
    if (model != null) {
      model.setStatus(status);
      this.update(model);
    }
  }

  @Override
  public void downloadAccount(HttpServletResponse response, Integer questionId,
      Integer status, String webUrl) {
    int colNum = 6;
    // 文件
    Map<String, String> file = new HashMap<String, String>();
    file.put("fileName", "导出的家长账号");
    file.put("type", "1");// 1表示单列头，2表示双列头
    // sheet对象集合
    List<Map<String, ?>> sheetList = new ArrayList<Map<String, ?>>();
    // 数据结合
    EvlQuestionMember model = new EvlQuestionMember();
    Integer schoolYear = (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    model.setSchoolYear(schoolYear);
    model.setQuestionId(questionId);
    if (status != null) {
      Map<String, Object> paramsMap = new HashMap<String, Object>();
      if (status == 2) {// 成功
        model.setStatus(null);
        paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
        model.addCustomCondition("and status>=:status", paramsMap);

      } else if (status == 1) {
        model.setStatus(null);
        paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
        model.addCustomCondition("and status<:status", paramsMap);
      }
    }
    model.addGroup("classId");
    List<EvlQuestionMember> bufferList = this.evlQuestionMemberService
        .findAll(model);

    EvlQuestionnaires question = this.findOne(questionId);
    Map<String, String> subjectsMap = evlMetaService
        .getGradeMap(question.getPhaseId());
    for (EvlQuestionMember njMv : bufferList) {
      String gradename = "";
      String classname = "";
      gradename = subjectsMap.get(njMv.getGradeId().toString()) == null ? ""
          : subjectsMap.get(njMv.getGradeId().toString());
      SchClass classmodel = schClassService.findOne(njMv.getClassId());
      if (classmodel != null) {
        classname = classmodel.getName();
      } else {
        classname = "未知班级" + njMv.getClassId();
      }
      Map<String, String> sheetBase = new HashMap<String, String>();
      sheetBase.put("sheetname", gradename + "(" + classname + ")");
      sheetBase.put("cellcount", colNum + "");

      Map<Integer, String> titleMap = new HashMap<Integer, String>();
      titleMap.put(1, "学号");
      titleMap.put(2, "姓名");
      titleMap.put(3, "班级");
      titleMap.put(4, "性别");
      titleMap.put(5, "家长电话");
      titleMap.put(6, "评教地址");
      // 数据集合
      List<Map<Integer, String>> dataList = new ArrayList<Map<Integer, String>>();
      dataList.add(titleMap);
      // 数据列表
      EvlQuestionMember dataMember = new EvlQuestionMember();
      dataMember.setSchoolYear(schoolYear);
      dataMember.setQuestionId(questionId);
      if (status != null) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        // 成功
        if (status == 2) {
          dataMember.setStatus(null);
          paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
          dataMember.addCustomCondition("and status>=:status", paramsMap);

        } else if (status == 1) {
          dataMember.setStatus(null);
          paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
          dataMember.addCustomCondition("and status<:status", paramsMap);
        }
      }
      dataMember.setGradeId(njMv.getGradeId());
      dataMember.setClassId(njMv.getClassId());
      List<EvlQuestionMember> memberDataList = this.evlQuestionMemberService
          .findAll(dataMember);
      for (EvlQuestionMember evlQuestionMember : memberDataList) {
        evlQuestionMember.setFlago(
            webUrl + "/jy/evl/operate/?params=" + evlQuestionMember.getUrl());
        EvlStudentAccount at = new EvlStudentAccount();
        at.setCode(evlQuestionMember.getCode());
        at.setOrgId(evlQuestionMember.getOrgId());
        at = evlStudentAccountService.findOne(at);
        if (at != null) {
          evlQuestionMember.setCellphone(at.getCellphone());
          evlQuestionMember.setSexName(at.getSex() == 0 ? "女" : "男");
        }
      }
      for (int i = 0; i < memberDataList.size(); i++) {
        Map<Integer, String> dataMap = new HashMap<Integer, String>();
        EvlQuestionMember users = memberDataList.get(i);
        dataMap.put(1, users.getCode());
        dataMap.put(2, users.getName());
        dataMap.put(3, gradename + classname);
        dataMap.put(4, users.getSexName());
        dataMap.put(5, users.getCellphone());
        dataMap.put(6, memberDataList.get(i).getFlago());
        dataList.add(dataMap);
      }
      Map<String, Object> sheetMap = new HashMap<String, Object>();
      sheetMap.put("sheetBase", sheetBase);
      sheetMap.put("dataList", dataList);
      sheetList.add(sheetMap);
    }
    ExcleUtils.exportSheetsExcel2(response, file, sheetList);
  }

  @Override
  public List<EvlStudentAccount> getStudentsList(
      @RequestParam(value = "questionnairesId", required = true) Integer questionnairesId) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    EvlQuestionnaires model = this.findOne(questionnairesId);// 参数集合
    List<EvlStudentAccount> accountList = new ArrayList<EvlStudentAccount>();// 学生列表返回
    switch (model.getStudentType()) {
    case EvlQuestionnaires.student_type_quantixusheng:// 全体学生
    {
      // 关联班级学生映射表查询
      EvlStudentAccount student = new EvlStudentAccount();
      student.setOrgId(user.getOrgId());
      ClassStudent classModel = new ClassStudent();
      classModel.setSchoolYear(model.getSchoolYear());
      Organization org = organizationService.findOne(model.getOrgId());
      // 根据学段获取年级
      List<Meta> phaseList = MetaUtils.getOrgTypeMetaProvider()
          .listAllXzGrade(org.getSchoolings(), model.getPhaseId());
      List<Integer> gradeIds = new ArrayList<Integer>();
      for (Meta meta : phaseList) {
        gradeIds.add(meta.getId());
      }
      classModel.buildCondition(" and c.gradeId in(:gradeIds)").put("gradeIds",
          gradeIds);
      accountList = evlStudentAccountService.findAll(student, classModel);
      break;
    }
    case EvlQuestionnaires.student_type_nianji:// 按年级
    {
      List<EvlClass> gradeList = evlClassService
          .findClassListByQuestionnairesId(model);
      String gradeStr = "";
      for (EvlClass gradeTemp : gradeList) {
        gradeStr += gradeTemp.getGradeId() + ",";
      }
      gradeStr = "".equals(gradeStr) ? ""
          : gradeStr.substring(0, gradeStr.length() - 1);
      // 根据年级查询用户
      List<String> paramList = new ArrayList<String>();
      if (StringUtils.isNotEmpty(gradeStr)) {
        paramList = Arrays.asList(gradeStr.split(","));
        for (String gradeId : paramList) {
          ClassStudent classModel = new ClassStudent();
          EvlStudentAccount student = new EvlStudentAccount();
          student.setOrgId(user.getOrgId());

          classModel.setSchoolYear(model.getSchoolYear());
          classModel.setGradeId(Integer.parseInt(gradeId));
          // 关联班级学生映射表查询
          accountList
              .addAll(evlStudentAccountService.findAll(student, classModel));
        }
      }
      break;
    }
    case EvlQuestionnaires.student_type_banji:// 按班级
    {
      List<EvlClass> classList = evlClassService
          .findClassListByQuestionnairesId(model);
      for (EvlClass evlClass : classList) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gradeId", evlClass.getGradeId());
        paramMap.put("classId", evlClass.getClassId());

        // 关联班级学生映射表查询
        EvlStudentAccount student = new EvlStudentAccount();
        student.setOrgId(user.getOrgId());
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(evlClass.getClassId());
        classStudent.setGradeId(evlClass.getGradeId());
        classStudent.setSchoolYear(model.getSchoolYear());

        List<EvlStudentAccount> temp = evlStudentAccountService.findAll(student,
            classStudent);
        accountList.addAll(temp);
      }
      break;
    }
    default:
      break;
    }
    return accountList;
  }

  @Override
  public List<EvlStudentAccount> findClassListByGradeId(
      Integer questionnairesId, Integer gradeId) {
    // 当前问卷下所有学生
    List<EvlStudentAccount> accountList = this
        .getStudentsList(questionnairesId);
    List<EvlStudentAccount> classList = new ArrayList<EvlStudentAccount>();
    for (EvlStudentAccount evlStudentAccount : accountList) {
      if (gradeId.equals(evlStudentAccount.getGradeId())) {
        classList.add(evlStudentAccount);
      }
    }

    for (EvlStudentAccount evlStudentAccount : classList) {
      evlStudentAccount.setGradeName(
          MetaUtils.getMeta(evlStudentAccount.getGradeId()).getName());
      evlStudentAccount
          .setClassName(evlStudentAccount.getClassStudent().getClassName());
    }
    return classList;
  }

  /**
   * 根据用户id查询问卷列表
   * 
   * @param userId
   *          用户id
   * @return List
   * @see com.mainbo.jy.evl.service.EvlQuestionnairesService#getQuestions(java.lang.String)
   */
  @Override
  public List<Map<String, Object>> getQuestions() {
    /**
     * 1:判断用户类型，学生用户，家长用户(找到学生用户列表)
     * 2：根据userId查询evl_student_account,家长的话遍历userId查询evl_student_account(无orgId)
     * 3:根据查询code集合和对应orgId，查询evl_question_member,找到问卷集合和对应访问地址
     */
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);

    List<Map<String, Object>> questions = new ArrayList<Map<String, Object>>();
    questions = getStudenQuestions(user.getIdcard());

    // 根据发布时间排序
    Collections.sort(questions, new Comparator<Map<String, Object>>() {
      @Override
      public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        boolean after = false;
        if (o1.get("fabuTime") != null && o2.get("fabuTime") != null) {
          Date d1 = (Date) o1.get("fabuTime");
          Date d2 = (Date) o2.get("fabuTime");
          after = d1.after(d2);
        }

        if (after) {
          return 1;
        } else {
          return -1;
        }
      }
    });

    return questions;
  }

  private EvlTimeline getQuestionTime(Integer orgId, Integer questionId) {
    EvlTimeline time = new EvlTimeline();
    time.setType(EvlTimeline.pjsx);
    time.setOrgId(orgId);
    time.setQuestionnairesId(questionId);
    time = evlTimelineService.findOne(time);
    return time;
  }

  private List<Map<String, Object>> getStudenQuestions(String studentId) {
    EvlStudentAccount ea = evlStudentAccountService.findOne(studentId);
    if (ea != null) {
      String code = ea.getCode();
      Integer orgId = ea.getOrgId();
      List<Map<String, Object>> questions = getQuesetionsByCodeOrgId(code,
          orgId);
      return questions;
    }
    return null;
  }

  /**
   * 根据code和orgId查询问卷列表
   * 
   * @param code
   *          学号
   * @param orgId
   *          机构id
   * @return List
   */
  private List<Map<String, Object>> getQuesetionsByCodeOrgId(String code,
      Integer orgId) {
    EvlQuestionMember member = new EvlQuestionMember();
    member.setOrgId(orgId);
    member.setCode(code);
    List<EvlQuestionMember> members = evlQuestionMemberService.findAll(member);
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    if (!CollectionUtils.isEmpty(members)) {
      for (EvlQuestionMember m : members) {
        EvlQuestionnaires question = evlQuestionnairesDao
            .get(m.getQuestionId());
        if (EvlQuestionType.init.getValue().equals(question.getStatus())) {
          continue;
        }
        // 开始结束时间
        EvlTimeline questionTime = getQuestionTime(question.getOrgId(),
            question.getQuestionnairesId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", question.getTitle());
        map.put("beginTime", StringUtils.isEmpty(questionTime.getBeginTimeStr())
            ? "无" : questionTime.getBeginTimeStr());
        map.put("endTime", StringUtils.isEmpty(questionTime.getBeginTimeStr())
            ? "无" : questionTime.getBeginTimeStr());
        map.put("fabuTime", question.getLastDttm());
        map.put("url", m.getUrl());
        map.put("status", m.getStatus());
        map.put("name", m.getName());

        result.add(map);
      }
    }

    return result;
  }

}