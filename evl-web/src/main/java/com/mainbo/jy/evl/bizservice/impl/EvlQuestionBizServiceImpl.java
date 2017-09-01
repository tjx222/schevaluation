package com.mainbo.jy.evl.bizservice.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.mainbo.jy.common.page.Page;
import com.mainbo.jy.common.page.PageList;
import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.evl.bizservice.EvlquestionBizService;
import com.mainbo.jy.evl.bo.ClassStudent;
import com.mainbo.jy.evl.bo.EvlClass;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlStudentAccount;
import com.mainbo.jy.evl.bo.EvlTimeline;
import com.mainbo.jy.evl.service.ClassStudentService;
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
import com.mainbo.jy.evl.utils.UrlCode;
import com.mainbo.jy.evl.vo.EvlLoginTag;
import com.mainbo.jy.evl.vo.EvlQuestionnairesVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.manage.config.ConfigUtils;
import com.mainbo.jy.manage.meta.Meta;
import com.mainbo.jy.manage.meta.MetaUtils;
import com.mainbo.jy.manage.org.bo.Organization;
import com.mainbo.jy.manage.org.service.OrganizationService;
import com.mainbo.jy.notice.constants.NoticeType;
import com.mainbo.jy.notice.service.impl.NoticeUtils;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.uc.SysRole;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.service.UserService;
import com.mainbo.jy.uc.service.UserSpaceService;
import com.mainbo.jy.uc.utils.CurrentUserContext;
import com.mainbo.jy.uc.utils.SessionKey;
import com.mainbo.jy.utils.StringUtils;

@Service
@Transactional
public class EvlQuestionBizServiceImpl implements EvlquestionBizService {

  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private EvlStudentAccountService evlStudentAccountService;
  @Autowired
  private EvlClassService evlClassService;
  @Autowired
  private EvlQuestionMemberService evlQuestionMemberService;
  @Autowired
  private EvlMetaService evlMetaService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private EvlTimelineService evlTimelineService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private UserService userService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private ClassStudentService classStudentService;

  private static final Logger logger = LoggerFactory
      .getLogger(EvlQuestionBizServiceImpl.class);

  @Override
  public Result gerenateAccountUrl(EvlQuestionnaires model) {
    Result result = new Result();
    List<EvlStudentAccount> accountList = evlQuestionnairesService
        .getStudentsList(model.getQuestionnairesId());
    // 删除member表信息
    evlQuestionMemberService.deleteMember(model.getOrgId(),
        model.getQuestionnairesId());
    for (EvlStudentAccount temp : accountList) {
      EvlQuestionMember member = new EvlQuestionMember();
      member.setOrgId(model.getOrgId());
      member.setQuestionId(model.getQuestionnairesId());
      member.setCode(temp.getCode());
      member.setQuestionId(model.getQuestionnairesId());
      member.setOrgId(temp.getOrgId());
      member.setName(temp.getName());
      member.setGradeId(temp.getGradeId());
      member.setClassId(temp.getClassId());
      member.setSchoolYear(temp.getSchoolYear());
      // 生成url
      EvlLoginTag tag = new EvlLoginTag();
      tag.setQuestionnairesId(model.getQuestionnairesId());
      tag.setStudentCode(member.getCode());
      tag.setOrgId(model.getOrgId());
      String params = JSON.toJSONString(tag);
      String aResult = UrlCode.shortUrl(params);
      member.setUrl(aResult);
      member.setStatus(EvlMemberStatus.init.getValue());
      member = evlQuestionMemberService.save(member);
      if (member != null) {
        sendMessage(model, temp, member.getUrl());
      }

    }
    return result;
  }

  private void sendMessage(EvlQuestionnaires question,
      EvlStudentAccount account, String url) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    try {
      if (user == null) {
        return;
      }
      String title = "开始对《" + question.getTitle() + "》进行打分操作";
      Integer senderId = user.getId();
      String receiveId = account.getId();
      Map<String, Object> noticeInfo = new HashMap<String, Object>();
      noticeInfo.put("params", url);
      NoticeType noticeType = NoticeType.EVL_JZPJ;

      if (EvlQuestionnaires.type_xueshengpingjiao == question.getType()) {
        noticeType = NoticeType.EVL_XSPJ;
      }
      NoticeUtils.sendNotice(noticeType, title, senderId, receiveId,
          noticeInfo);
    } catch (Exception e) {
      logger.info("新增站内消息时异常，userId为：{}发送给evl_student_account Id为：{}，问卷id{}",
          user.getId(), account.getId(), question.getQuestionnairesId());
      logger.info("", e);
    }
  }

  @Override
  public List<SubjectVo> findSubjectByQuestionId(Integer questionnairesId) {
    EvlQuestionnaires question = this.evlQuestionnairesService
        .findOne(questionnairesId);
    String subjectIds = question.getSubjectIds();
    List<SubjectVo> list = new ArrayList<SubjectVo>();
    //
    Map<String, String> subjectMap = evlMetaService
        .getSubjectMap(question.getPhaseId());
    if (StringUtils.isNotEmpty(subjectIds)) {
      for (String subjectId : subjectIds.split(",")) {
        SubjectVo vo = new SubjectVo();
        vo.setName(subjectMap.get(subjectId));
        vo.setId(Integer.valueOf(subjectId));
        list.add(vo);
      }
    }
    return list;
  }

  @Override
  public Map<Integer, Object> getEvlPowerUserList() {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Integer schoolYear = (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    List<UserSpace> roleList = this.getAllUserBySysRoleId(user.getOrgId(), true,
        schoolYear, SysRole.ZR.getId(), SysRole.XZ.getId(),
        SysRole.FXZ.getId());
    Map<Integer, Object> roleMap = new HashMap<Integer, Object>();
    for (UserSpace userSpace : roleList) {
      roleMap.put(userSpace.getUserId(), userSpace);
    }
    return roleMap;
  }

  @Override
  public String getFillPrompt(Integer questionId, String userCode) {
    EvlTimeline time = new EvlTimeline();
    time.setQuestionnairesId(questionId);
    time.setType(EvlTimeline.pjsx);
    EvlTimeline timeModel = evlTimelineService.findOne(time);
    Date now = new Date();
    String tipStatus = "";
    if (timeModel != null) {
      // 未开始
      if (timeModel.getBeginTime() != null
          && timeModel.getBeginTime().after(now)) {
        tipStatus = "weikaishi";
        return tipStatus;
      } else if (timeModel.getEndTime() != null
          && timeModel.getEndTime().before(now)) {
        tipStatus = "yijieshu";
        return tipStatus;
      }
    }
    EvlQuestionMember member = new EvlQuestionMember();
    member.setQuestionId(questionId);
    member.setCode(userCode);
    EvlQuestionMember memberModel = evlQuestionMemberService.findOne(member);
    if (memberModel != null) {
      // 已经提交、弃权
      if (memberModel.getStatus() >= EvlMemberStatus.tijiaowenjuan.getValue()) {
        tipStatus = "yitijiao";
      }
    }
    return tipStatus;
  }

  @Override
  public List<UserSpace> getAllUserBySysRoleId(Integer orgId, boolean isDistink,
      Integer... roleIds) {
    UserSpace userSpace = new UserSpace();
    if (isDistink) {
      userSpace.addCustomCulomn("DISTINCT userId,username");
    } else {
      userSpace
          .addCustomCulomn("id,userId,username,subjectId,sysRoleId,roleId");

    }
    userSpace.setOrgId(orgId);
    if (roleIds.length == 1) {
      userSpace.setSysRoleId(roleIds[0]);
    } else if (roleIds.length > 1) {
      userSpace.buildCondition("and sysRoleId in(:roleIds)").put("roleIds",
          Arrays.asList(roleIds));
    } else {
      userSpace.setRoleId(-1);
    }
    userSpace.setEnable(UserSpace.ENABLE);
    return userSpaceService.findAll(userSpace);
  }

  @Override
  public Integer getQuestionStudentsList(Integer questionnairesId) {
    // 查询当前问卷是否已发布，如果已发布，筛选的是account表，发布之后，筛选member表
    EvlQuestionnaires question = this.evlQuestionnairesService
        .findOne(questionnairesId);
    // 发布之后
    if (question.getStatus() >= EvlQuestionType.fabu.getValue()) {
      EvlQuestionMember member = new EvlQuestionMember();
      member.setQuestionId(questionnairesId);
      return evlQuestionMemberService.count(member);
    }
    List<EvlClass> classList = evlClassService
        .findClassListByQuestionnairesId(question);
    // 班级
    if (question.getStudentType() == EvlQuestionnaires.student_type_banji) {
      if (CollectionUtils.isEmpty(classList)) {
        return 0;
      }
      List<Integer> classListParam = new ArrayList<Integer>();
      for (EvlClass ec : classList) {
        if (ec.getClassId() != null) {
          classListParam.add(ec.getClassId());
        }
      }

      ClassStudent classModel = new ClassStudent();
      classModel.buildCondition(" and classId in (:classIds)").put("classIds",
          classListParam);
      classModel.setSchoolYear(question.getSchoolYear());
      classModel.setOrgId(question.getOrgId());
      // 关联班级映射表查询
      return classStudentService.count(classModel);
    }
    // 年级
    if (question.getStudentType() == EvlQuestionnaires.student_type_nianji) {
      if (CollectionUtils.isEmpty(classList)) {
        return 0;
      }
      List<Integer> gradeListParam = new ArrayList<Integer>();
      for (EvlClass grade : classList) {
        if (grade.getGradeId() != null) {
          gradeListParam.add(grade.getGradeId());
        }
      }
      ClassStudent classModel = new ClassStudent();
      classModel.buildCondition(" and gradeId in (:gradeIds)").put("gradeIds",
          gradeListParam);
      classModel.setSchoolYear(question.getSchoolYear());
      classModel.setOrgId(question.getOrgId());
      // 关联班级映射表查询
      return classStudentService.count(classModel);
      // 全体
    } else if (question
        .getStudentType() == EvlQuestionnaires.student_type_quantixusheng) {
      ClassStudent classModel = new ClassStudent();
      classModel.setSchoolYear(question.getSchoolYear());
      // 根据学段获取年级
      Organization org = organizationService.findOne(question.getOrgId());
      List<Meta> phaseList = MetaUtils.getOrgTypeMetaProvider()
          .listAllXzGrade(org.getSchoolings(), question.getPhaseId());
      List<Integer> gradeIds = new ArrayList<Integer>();
      for (Meta meta : phaseList) {
        gradeIds.add(meta.getId());
      }
      classModel.setOrgId(question.getOrgId());
      classModel.buildCondition(" and gradeId in(:gradeIds)").put("gradeIds",
          gradeIds);
      return classStudentService.count(classModel);
    }
    return 0;
  }

  @Override
  public PageList<EvlStudentAccount> getQuestionStudentsListByPage(
      Integer questionnairesId, EvlStudentAccount account,
      ClassStudent classModel, Integer... pageSize) {
    EvlQuestionnaires question = this.evlQuestionnairesService
        .findOne(questionnairesId);
    // 发布之后
    if (question.getStatus() >= EvlQuestionType.fabu.getValue()) {
      EvlQuestionMember member = new EvlQuestionMember();
      member.setQuestionId(questionnairesId);
      if (pageSize.length > 0) {
        // 全部查询
        member.currentPage(1);
        member.pageSize(pageSize[0]);
      } else {
        Page page = account.getPage();
        member.addPage(page);
      }
      member.addOrder("code");
      PageList<EvlQuestionMember> memPageList = evlQuestionMemberService
          .findByPage(member);
      List<EvlStudentAccount> studentList = new ArrayList<EvlStudentAccount>();
      for (EvlQuestionMember evlQuestionMember : memPageList.getDatalist()) {
        EvlStudentAccount esa = new EvlStudentAccount();
        ClassStudent classStudent = new ClassStudent();
        classStudent.setGradeId(evlQuestionMember.getGradeId());
        classStudent.setClassId(evlQuestionMember.getClassId());
        esa.setClassStudent(classStudent);
        esa.setCode(evlQuestionMember.getCode());
        EvlStudentAccount accountDetail = getAccountDetail(
            evlQuestionMember.getCode());
        // 防止发布完成后，删除学生管理中学生导致异常
        if (accountDetail == null) {
          continue;
        }
        esa.setSex(accountDetail.getSex());
        esa.setCellphone(accountDetail.getCellphone());
        studentList.add(esa);
      }
      PageList<EvlStudentAccount> accountPageList = new PageList<EvlStudentAccount>(
          studentList, memPageList.getPage());
      return accountPageList;
    }
    List<EvlClass> classList = evlClassService
        .findClassListByQuestionnairesId(question);
    account.setOrgId(question.getOrgId());
    classModel.setSchoolYear(question.getSchoolYear());
    account.addOrder("code");
    // 班级
    if (question.getStudentType() == EvlQuestionnaires.student_type_banji) {
      List<Integer> gradeListParam = new ArrayList<Integer>();
      for (EvlClass grade : classList) {
        if (grade.getGradeId() != null) {
          gradeListParam.add(grade.getGradeId());
        }
      }
      List<Integer> classListParam = new ArrayList<Integer>();
      for (EvlClass ec : classList) {
        if (ec.getClassId() != null) {
          classListParam.add(ec.getClassId());
        }
      }
      if (CollectionUtils.isEmpty(gradeListParam)) {
        gradeListParam.add(-1);
      }
      if (CollectionUtils.isEmpty(classListParam)) {
        classListParam.add(-1);
      }
      classModel.setClassIds(classListParam);
      classModel.setGradeIds(gradeListParam);
      // 年级
    } else if (question
        .getStudentType() == EvlQuestionnaires.student_type_nianji) {
      List<Integer> gradeListParam = new ArrayList<Integer>();
      for (EvlClass grade : classList) {
        if (grade.getGradeId() != null) {
          gradeListParam.add(grade.getGradeId());
        }
      }
      if (CollectionUtils.isEmpty(gradeListParam)) {
        gradeListParam.add(-1);
      }
      classModel.setGradeIds(gradeListParam);
    }
    if (account.getStatus() != null) {
      // 已提交：无
      if (EvlMemberStatus.tijiaowenjuan.getValue()
          .equals(account.getStatus())) {
        account.setId("-1");// 无匹配
      }
    }
    if (pageSize != null && pageSize.length > 0) {
      // 全部查询
      account.currentPage(1);
      account.pageSize(pageSize[0]);
    }
    Organization org = organizationService.findOne(question.getOrgId());
    List<Meta> phaseList = MetaUtils.getOrgTypeMetaProvider()
        .listAllXzGrade(org.getSchoolings(), question.getPhaseId());
    List<Integer> gradeIds = new ArrayList<Integer>();
    for (Meta meta : phaseList) {
      gradeIds.add(meta.getId());
    }
    classModel.setGradeIds(gradeIds);
    PageList<EvlStudentAccount> pageList = evlStudentAccountService
        .findByPage(account, classModel);
    return pageList;
  }

  private EvlStudentAccount getAccountDetail(String code) {
    EvlStudentAccount account = new EvlStudentAccount();
    account.setCode(code);
    return evlStudentAccountService.findOne(account);
  }

  @Override
  public List<EvlQuestionnairesVo> findResultQuestionnairesList(
      Integer schoolYear, Integer status, Integer currentPage,
      Integer pageSize) {
    // 问卷
    User currentUser = CurrentUserContext.getCurrentUser();
    EvlQuestionnaires question = new EvlQuestionnaires();
    question.setOrgId(currentUser.getOrgId());
    if (status != null) {
      question.setStatus(status);
    } else {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("thisCrtId", currentUser.getId());
      List<Integer> paramList = new ArrayList<Integer>();
      paramList.add(EvlQuestionType.shejiwenjuan.getValue());
      paramList.add(EvlQuestionType.xiangguanshezhi.getValue());
      paramList.add(EvlQuestionType.fabu.getValue());
      paramMap.put("status", paramList);
      question.addCustomCondition(
          "and (crtId=:thisCrtId or (crtId <> :thisCrtId and status in(:status)))",
          paramMap);
    }
    if (schoolYear == null) {
      question.setSchoolYear(CurrentUserContext.getCurrentSchoolYear());// 当前学年
    } else {
      question.setSchoolYear(schoolYear);
    }
    question.addOrder(" crtDttm desc ");// 问卷创建时间倒序
    String opt = getQuestionOperateType();
    if (!StringUtils.isEmpty(opt)) {
      question.setType(Integer.parseInt(opt));
    }
    List<EvlQuestionnaires> listBuffer = null;
    if (pageSize != null && currentPage != null) {
      question.pageSize(pageSize);
      question.currentPage(currentPage);
      PageList<EvlQuestionnaires> questionList = evlQuestionnairesService
          .findByPage(question);
      listBuffer = questionList.getDatalist();
    } else {
      // 不按分页查询
      listBuffer = evlQuestionnairesService.findAll(question);
    }
    // 问卷时限
    EvlTimeline etl = new EvlTimeline();
    etl.setOrgId(currentUser.getOrgId());
    List<EvlTimeline> etlList = evlTimelineService.findAllTimelineList(etl);
    Map<Integer, EvlTimeline> timeMap = new HashMap<Integer, EvlTimeline>();
    for (EvlTimeline et : etlList) {
      timeMap.put(et.getQuestionnairesId(), et);
    }
    // 问卷扩展实体装载
    List<EvlQuestionnairesVo> questionVoList = new ArrayList<EvlQuestionnairesVo>();
    if (listBuffer != null) {
      for (EvlQuestionnaires eq : listBuffer) {
        Integer crtId = eq.getCrtId();
        User user = userService.findOne(crtId);
        if (user != null) {
          eq.setFlago(user.getName()); // 用户名
        }
        eq.setFlags(StringUtils.abbr(eq.getTitle(), 70, true, false));
        EvlQuestionnairesVo questionnairesVo = new EvlQuestionnairesVo();
        // 查询当前问卷应该评审人数
        Integer shouldReviewCount = this
            .getQuestionStudentsList(eq.getQuestionnairesId());
        // 查询当前问卷已经评审人数
        Integer alreadyReviewCount = evlQuestionMemberService
            .getAlreadySubmitStudentsList(eq.getQuestionnairesId());
        questionnairesVo.setShouldReviewCount(shouldReviewCount);
        questionnairesVo.setAlreadyReviewCount(alreadyReviewCount);
        questionnairesVo.setEvlQuestionnaires(eq);
        EvlTimeline timeLine = timeMap.get(eq.getQuestionnairesId());
        questionnairesVo
            .setEvlTimeline(timeLine == null ? new EvlTimeline() : timeLine);
        questionVoList.add(questionnairesVo);
      }
    }
    return questionVoList;
  }

  @Override
  public Map<String, Object> findResultIndex(Integer status) {
    return findResultIndex(status, null, null);
  }

  private Map<String, Object> findResultIndex(Integer status,
      Integer currentPage, Integer pageSize) {
    EvlQuestionnaires question = new EvlQuestionnaires();
    question.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
    question.setStatus(status);
    question.addOrder(" schoolYear desc ");
    question.addGroup(" schoolYear ");
    String opt = getQuestionOperateType();
    if (!StringUtils.isEmpty(opt)) {
      question.setType(Integer.parseInt(opt));
    }
    List<EvlQuestionnaires> eqist = evlQuestionnairesService.findAll(question);
    List<Integer> schoolYearList = new ArrayList<Integer>();
    if (!CollectionUtils.isEmpty(eqist)) {
      if (eqist.get(0).getSchoolYear() >= CurrentUserContext
          .getCurrentSchoolYear()) {
        eqist.remove(0);
      }
      for (EvlQuestionnaires eq : eqist) {
        if (eq != null) {
          schoolYearList.add(eq.getSchoolYear());
        }
      }
    }
    Map<String, Object> returnMap = new HashMap<String, Object>();
    List<EvlQuestionnairesVo> currentList = this
        .findResultQuestionnairesList(null, status, currentPage, pageSize);
    returnMap.put("currentList", currentList);
    returnMap.put("schoolYearList", schoolYearList);
    return returnMap;
  }

  /**
   * 根据studentId获取student详情
   * 
   * @param orgId
   *          机构id
   * @param studentId
   *          学生id
   * @return ClassStudent
   */
  private ClassStudent getGradeByStudentId(Integer orgId, String studentId) {
    ClassStudent model = new ClassStudent();
    model.setStudentId(studentId);
    model.setSchoolYear(EvlHelper.getUserSchoolYear());
    model.setOrgId(orgId);
    return classStudentService.findOne(model);
  }

  @Override
  public void downloadQuestionMemberDetail(HttpServletResponse response,
      Integer questionId, EvlQuestionMember member) {
    Map<String, String> file = new HashMap<String, String>();
    file.put("fileName", "评教情况");
    file.put("type", "1");// 1表示单列头，2表示双列头
    // 导出
    Map<String, String> sheetBase = new HashMap<String, String>();
    sheetBase.put("sheetname", "评教情况");
    sheetBase.put("cellcount", "7");
    Map<Integer, String> titleMap = new HashMap<Integer, String>();
    titleMap.put(1, "学号");
    titleMap.put(2, "姓名");
    titleMap.put(3, "年级");
    titleMap.put(4, "班");
    titleMap.put(5, "性别");
    titleMap.put(6, "家长电话");
    titleMap.put(7, "评教状态");
    List<Map<Integer, String>> dataList = new ArrayList<Map<Integer, String>>();// 导出数据集合
    dataList.add(titleMap);
    EvlQuestionnaires question = evlQuestionnairesService.findOne(questionId);
    Map<String, String> gradeNamesMap = evlMetaService
        .getGradeMap(question.getPhaseId());
    if (question.getStatus() < EvlQuestionType.fabu.getValue()) {
      EvlStudentAccount account = new EvlStudentAccount();
      ClassStudent classModel = new ClassStudent();
      classModel.setGradeId(member.getGradeId());
      classModel.setClassId(member.getClassId());
      account.setStatus(member.getStatus());
      PageList<EvlStudentAccount> pageList = this.getQuestionStudentsListByPage(
          member.getQuestionId(), account, classModel, Integer.MAX_VALUE);
      for (EvlStudentAccount evlStudentAccount : pageList.getDatalist()) {
        ClassStudent classStudent = this.getGradeByStudentId(
            evlStudentAccount.getOrgId(), evlStudentAccount.getId());
        String gradeName = gradeNamesMap
            .get(classStudent.getGradeId().toString());// 年级
        String className = this.getClassNameById(classStudent.getClassId());// 班级
        String sexName = evlStudentAccount.getSex() == 0 ? "女" : "男";// 性别
        Map<Integer, String> dataMap = new HashMap<Integer, String>();
        dataMap.put(1, evlStudentAccount.getCode());
        dataMap.put(2, evlStudentAccount.getName());
        dataMap.put(3, gradeName);
        dataMap.put(4, className);
        dataMap.put(5, sexName);
        dataMap.put(6, evlStudentAccount.getCellphone());
        dataMap.put(7, "未评");
        dataList.add(dataMap);
      }
    } else {
      PageList<EvlQuestionMember> memberList = this.evlQuestionMemberService
          .memberFindByPage(member, member.getQuestionId());
      member.pageSize(Integer.MAX_VALUE);
      for (EvlQuestionMember evlQuestionMember : memberList.getDatalist()) {
        EvlStudentAccount accountDetail = this
            .getAccountDetail(evlQuestionMember.getCode());
        // 防止发布完成后，删除学生管理中学生导致异常
        if (accountDetail == null) {
          continue;
        }
        Map<Integer, String> dataMap = new HashMap<Integer, String>();
        dataMap.put(1, evlQuestionMember.getCode());
        dataMap.put(2, evlQuestionMember.getName());
        dataMap.put(3,
            gradeNamesMap.get(evlQuestionMember.getGradeId().toString()));
        dataMap.put(4, getClassNameById(evlQuestionMember.getClassId()));
        dataMap.put(5, accountDetail.getSex() == 0 ? "女" : "男");
        dataMap.put(6, accountDetail.getCellphone());
        Integer status = evlQuestionMember.getStatus();
        String buffername = "未评";
        if (status != null
            && status.equals(EvlMemberStatus.tijiaowenjuan.getValue())) {
          buffername = "已评";
        }
        dataMap.put(7, buffername);
        dataList.add(dataMap);
      }
    }
    Map<String, Object> sheetMap = new HashMap<String, Object>();
    sheetMap.put("sheetBase", sheetBase);
    sheetMap.put("dataList", dataList);
    // sheet对象集合
    List<Map<String, ?>> sheetList = new ArrayList<Map<String, ?>>();
    sheetList.add(sheetMap);
    ExcleUtils.exportSheetsExcel2(response, file, sheetList);
  }

  // 通过班级d获取name
  private String getClassNameById(Integer id) {
    SchClass schClass = schClassService.findOne(id);
    String name = "";
    if (schClass != null) {
      name = schClass.getName();
    }
    return name;
  }

  @Override
  public String getQuestionOperateType() {
    String opt = (String) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.EVL_OPERATETYPE);
    if (StringUtils.isEmpty(opt)) {
      opt = ConfigUtils.readConfig("evl.operate.type", "");
    }
    return opt;
  }
}
