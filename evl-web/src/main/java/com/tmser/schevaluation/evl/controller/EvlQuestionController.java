package com.tmser.schevaluation.evl.controller;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tmser.schevaluation.common.orm.SqlMapping;
import com.tmser.schevaluation.common.page.PageList;
import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlquestionBizService;
import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlClass;
import com.tmser.schevaluation.evl.bo.EvlIndicator;
import com.tmser.schevaluation.evl.bo.EvlLevelWeight;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;
import com.tmser.schevaluation.evl.bo.EvlSuggestionType;
import com.tmser.schevaluation.evl.bo.EvlTimeline;
import com.tmser.schevaluation.evl.service.EvlAnalyzeIndicatorService;
import com.tmser.schevaluation.evl.service.EvlAnalyzeTeacherService;
import com.tmser.schevaluation.evl.service.EvlClassService;
import com.tmser.schevaluation.evl.service.EvlIndicatorService;
import com.tmser.schevaluation.evl.service.EvlLevelWeightService;
import com.tmser.schevaluation.evl.service.EvlMetaService;
import com.tmser.schevaluation.evl.service.EvlOperateResultService;
import com.tmser.schevaluation.evl.service.EvlQuestionMemberService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.service.EvlSmsService;
import com.tmser.schevaluation.evl.service.EvlStudentAccountService;
import com.tmser.schevaluation.evl.service.EvlSuggestionTypeService;
import com.tmser.schevaluation.evl.service.EvlTimelineService;
import com.tmser.schevaluation.evl.sms.SmsService;
import com.tmser.schevaluation.evl.sms.StatusCode;
import com.tmser.schevaluation.evl.statics.EvlMemberStatus;
import com.tmser.schevaluation.evl.statics.EvlQuestionType;
import com.tmser.schevaluation.evl.utils.DateUtils;
import com.tmser.schevaluation.evl.utils.EvlHelper;
import com.tmser.schevaluation.evl.vo.EvlIndicatorVo;
import com.tmser.schevaluation.evl.vo.EvlQuestionnairesVo;
import com.tmser.schevaluation.evl.vo.GradeAndClassVo;
import com.tmser.schevaluation.evl.vo.SubjectVo;
import com.tmser.schevaluation.manage.config.ConfigUtils;
import com.tmser.schevaluation.manage.meta.Meta;
import com.tmser.schevaluation.manage.meta.MetaUtils;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 问卷基本设置controller
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author ljh
 * @version $Id: EvlQuestionController.java, v 1.0 2016年5月9日 下午3:53:08 ljh Exp $
 */
@Controller
@RequestMapping("/jy/evl/question/")
public class EvlQuestionController extends AbstractController {
  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private EvlquestionBizService evlquestionBizService;
  @Autowired
  private EvlMetaService evlMetaService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private EvlLevelWeightService evlLevelWeightService;
  @Autowired
  private EvlClassService evlClassService;
  @Autowired
  private EvlSuggestionTypeService evlSuggestionTypeService;
  @Autowired
  private EvlIndicatorService evlIndicatorService;
  @Autowired
  private EvlQuestionMemberService evlQuestionMemberService;
  @Autowired
  private EvlTimelineService evlTimelineService;
  @Autowired
  private SmsService smsService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private EvlAnalyzeIndicatorService evlAnalyzeIndicatorService;
  @Autowired
  private EvlAnalyzeTeacherService evlAnalyzeTeacherService;
  @Autowired
  private EvlOperateResultService evlOperateResultService;
  @Autowired
  private EvlStudentAccountService evlStudentAccountService;
  @Autowired
  private EvlSmsService evlSmsService;

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
   * 问卷首页
   * 
   * @return
   */
  @RequestMapping(value = "/indexQuestions")
  public String indexQuestions(Model model) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Map<String, Object> returnMap = evlquestionBizService.findResultIndex(null);
    model.addAttribute("currentList", returnMap.get("currentList"));
    model.addAttribute("schoolYearList", returnMap.get("schoolYearList"));
    model.addAttribute("user", user);
    return viewName("indexQuestions");
  }

  /**
   * 问卷列表
   * 
   * @param schoolYear
   * @return
   */
  @RequestMapping("/getQuestionList")
  @ResponseBody
  public List<EvlQuestionnairesVo> findResultQuestionnairesList(
      @RequestParam("schoolYear") Integer schoolYear) {
    return evlquestionBizService.findResultQuestionnairesList(schoolYear, null,
        null, null);
  }

  /*-----------------------------------------相关设置----------------------------------*/
  /**
   * 设计问卷初始化默认值
   * 
   * @return
   */
  @RequestMapping(value = "toSettingQuestionRelation")
  public String settingQuestionRelation() {
    // 重定向，防止刷新新建
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .initQuestion(null);// 初始化数据
    return "redirect:/jy/evl/question/settingQuestionRelation?tag=1&questionnairesId="
        + evlQuestionnaires.getQuestionnairesId();
  }

  /**
   * 设计问卷页面
   * 
   * @return
   */
  @RequestMapping(value = "settingQuestionRelation")
  public String settingQuestionRelation(Model model, Integer questionnairesId,
      Integer tag) {
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnairesId);
    if (evlQuestionnaires == null) {
      evlQuestionnaires = evlQuestionnairesService
          .initQuestion(questionnairesId);// 初始化数据
    }
    // 判断问卷是否已经发布，如果已经发布 1：状态改为未发布 2：若开始、结束时间已过，清空开始时间
    EvlTimeline time = new EvlTimeline();
    time.setType(EvlTimeline.pjsx);
    time.setOrgId(evlQuestionnaires.getOrgId());
    time.setQuestionnairesId(questionnairesId);
    time = evlTimelineService.findOne(time);
    Date nowDate = new Date();
    if (time != null
        && evlQuestionnaires.getStatus() >= EvlQuestionType.fabu.getValue()) {
      if (time.getBeginTime() != null) {
        if (nowDate.after(time.getBeginTime())) {// 当前时间>开始时间 清空时间
          time.setBeginTime(null);
          evlTimelineService.updateValue(time);
        }
      }
      if (time.getEndTime() != null) {
        if (nowDate.after(time.getEndTime())) {// 当前时间>结束时间 清空时间
          time.setBeginTime(null);
          evlTimelineService.updateValue(time);
        }
      }
    }
    String localDate = DateUtils.formatDate(nowDate, "yyyy-MM-dd HH:mm:ss");
    if (StringUtils.isNotEmpty(localDate)) {
      if (time != null) {
        time.setFlago(localDate);
      }
    }
    model.addAttribute("time", time);
    if (evlQuestionnaires.getStatus() >= EvlQuestionType.fabu.getValue()) {
      evlQuestionnaires.setStatus(EvlQuestionType.init.getValue());
      evlQuestionnairesService.update(evlQuestionnaires);
    }
    model.addAttribute("tag", tag);
    model.addAttribute("question", evlQuestionnaires);
    // 全部年级列表
    Integer phaseId = evlQuestionnaires.getPhaseId();// 学段ID
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = organizationService.findOne(user.getOrgId());
    List<GradeAndClassVo> njList = new ArrayList<>();
    if (org == null) {
      njList = evlMetaService.findGradeByPhaseIdAndSystemId(phaseId);
    } else {
      njList = evlMetaService.findGradeByPhaseIdAndSystemId(phaseId,
          org.getSchoolings());
    }
    model.addAttribute("njList", njList);
    // 已选年级
    List<EvlClass> classList = evlClassService
        .findClassListByQuestionnairesId(evlQuestionnaires);
    String selectedNjIds = "";
    for (EvlClass evlClass : classList) {
      selectedNjIds += evlClass.getGradeId() + ",";
    }
    model.addAttribute("selectedNjIds", selectedNjIds);
    // 学科列表
    List<SubjectVo> xkList = evlMetaService.findSubjectByPhaseId(
        evlQuestionnaires.getPhaseId(), evlQuestionnaires.getOrgId());
    model.addAttribute("xkList", xkList);
    // 等级权重
    EvlLevelWeight weight = new EvlLevelWeight();
    weight.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    List<EvlLevelWeight> weightList = evlLevelWeightService.findAll(weight);
    int levelIndex = 1;
    for (EvlLevelWeight evlLevelWeight : weightList) {
      evlLevelWeight.setIndex(levelIndex++);
    }
    model.addAttribute("weightList", weightList);
    model.addAttribute("weightListSize", weightList.size());
    model.addAttribute("tag", tag);
    model.addAttribute("user", user);

    String opt = evlquestionBizService.getQuestionOperateType();

    model.addAttribute(SessionKey.EVL_OPERATETYPE, opt);

    return viewName("settingQuestionRelation");
  }

  /**
   * 预览问卷《第一步》
   * 
   * @param model
   * @param questionnairesId
   * @return
   */
  @RequestMapping(value = "viewQuestionRelation")
  public String viewQuestionRelation(Model model, Integer questionnairesId) {
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnairesId);
    model.addAttribute("question", evlQuestionnaires);
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    // 哪些学生评？
    String studentTypeNames = "";
    if (evlQuestionnaires.getStudentType() == EvlQuestionnaires.student_type_nianji) {
      List<EvlClass> classList = evlClassService
          .findClassListByQuestionnairesId(evlQuestionnaires);
      for (EvlClass evlClass : classList) {
        Map<String, String> gradeNamesMap = evlMetaService
            .getGradeMap(evlQuestionnaires.getPhaseId());
        studentTypeNames += gradeNamesMap.get(evlClass.getGradeId().toString())
            + "、";
      }
    } else if (evlQuestionnaires.getStudentType() == EvlQuestionnaires.student_type_banji) {
      List<EvlClass> classList = evlClassService
          .findClassListByQuestionnairesId(evlQuestionnaires);
      Map<String, String> gradeMap = evlMetaService
          .getGradeMap(evlQuestionnaires.getPhaseId());
      for (EvlClass evlClass : classList) {
        String gradeName = gradeMap.get(evlClass.getGradeId().toString());
        String className = "";
        if (evlClass.getClassId() != null) {
          SchClass classmodel = schClassService.findOne(evlClass.getClassId());
          if (classmodel != null) {
            className = classmodel.getName();
          }
        }
        studentTypeNames += (gradeName + className) + "、";
      }
    }
    if (StringUtils.isNotEmpty(studentTypeNames)) {
      studentTypeNames = studentTypeNames.substring(0,
          studentTypeNames.length() - 1);
    }
    model.addAttribute("studentTypeNames", studentTypeNames);
    // 评价哪些学科？
    String subjectNames = "";
    List<SubjectVo> xkList = this.evlquestionBizService
        .findSubjectByQuestionId(questionnairesId);
    for (SubjectVo subjectVo : xkList) {
      subjectNames += subjectVo.getName() + "、";
    }
    if (StringUtils.isNotEmpty(subjectNames)) {
      subjectNames = subjectNames.substring(0, subjectNames.length() - 1);
    }
    model.addAttribute("subjectNames", subjectNames);
    // 等级权重
    EvlLevelWeight weight = new EvlLevelWeight();
    weight.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    List<EvlLevelWeight> weightList = evlLevelWeightService.findAll(weight);
    int levelIndex = 1;
    for (EvlLevelWeight evlLevelWeight : weightList) {
      evlLevelWeight.setIndex(levelIndex++);
    }
    model.addAttribute("weightList", weightList);
    model.addAttribute("weightListSize", weightList.size());
    // 时间
    EvlTimeline time = new EvlTimeline();
    time.setType(EvlTimeline.pjsx);
    time.setOrgId(user.getOrgId());
    time.setQuestionnairesId(questionnairesId);
    time = evlTimelineService.findOne(time);
    model.addAttribute("time", time);
    model.addAttribute("user", user);
    return viewName("viewQuestionRelation");
  }

  /**
   * 设置评教类型
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionType")
  public void setQuestionType(EvlQuestionnaires model) {
    this.evlQuestionnairesService.update(model);
  }

  /**
   * 设置评教范围（全体学生，按年级，按班级）
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionRange")
  public void setQuestionRange(EvlQuestionnaires model) {
    this.evlQuestionnairesService.update(model);
  }

  /**
   * 设置评教范围（年级详细）
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionGradeIds")
  public void setQuestionRange(EvlClass model, Boolean ischecked) {
    if (ischecked) {// 选中
      EvlQuestionnaires eq = evlQuestionnairesService.findOne(model
          .getQuestionnairesId());
      model.setOrgId(eq.getOrgId());
      evlClassService.save(model);
    } else if (!ischecked) {// 取消
      List<EvlClass> evlClassList = evlClassService.findAll(model);
      for (EvlClass evlClass : evlClassList) {
        evlClassService.delete(evlClass.getId());
      }
    }
  }

  /**
   * 设置评教范围（班级详细）
   * 
   * @return
   */
  @RequestMapping(value = "loadClassContent")
  public String loadClassContent(EvlClass model, Model m) {
    EvlQuestionnaires evlquestionnaires = evlQuestionnairesService
        .findOne(model.getQuestionnairesId());
    // 获取所有班级
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Integer schoolYear = (Integer) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    List<GradeAndClassVo> gradeVos = new ArrayList<>();
    Organization org = organizationService.findOne(user.getOrgId());
    List<GradeAndClassVo> njList = new ArrayList<>();
    if (org == null) {
      njList = evlMetaService.findGradeByPhaseIdAndSystemId(evlquestionnaires
          .getPhaseId());
    } else {
      njList = evlMetaService.findGradeByPhaseIdAndSystemId(
          evlquestionnaires.getPhaseId(), org.getSchoolings());
    }
    List<SchClass> allGradeIdList = new ArrayList<SchClass>();
    for (GradeAndClassVo gradeVo : njList) {
      List<SchClass> classList = evlStudentAccountService
          .getSchByGradeIdAndOrgId(gradeVo.getId(), user.getOrgId(), schoolYear);
      for (SchClass schClass : classList) {
        schClass.setName(gradeVo.getName() + schClass.getName());
      }
      gradeVo.setClasses(classList);
      gradeVos.add(gradeVo);
      allGradeIdList.addAll(classList);
    }
    m.addAttribute("gradeVos", gradeVos);// 嵌套年级/班级
    m.addAttribute("allGradeIdList", allGradeIdList);// 所有班级List
    // 查询已经选择的班级
    List<EvlClass> allreadyClassList = evlClassService
        .findClassListByQuestionnairesId(evlquestionnaires);
    String allreadyClassIds = "";
    for (EvlClass temp : allreadyClassList) {
      allreadyClassIds += temp.getClassId() + ",";
    }
    m.addAttribute("allreadyClassIds", allreadyClassIds);// 已选择的年级
    m.addAttribute("questionnairesId", evlquestionnaires.getQuestionnairesId());
    return viewName("loadClassContent");
  }

  /**
   * 设置评教学科
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionSubject")
  public void setQuestionSubject(EvlQuestionnaires model, String subjectId,
      Boolean ischecked) {
    model = evlQuestionnairesService.findOne(model.getQuestionnairesId());
    if (ischecked) {// 选中
      model.setSubjectIds((model.getSubjectIds() == null ? "" : model
          .getSubjectIds()) + subjectId + ",");
      this.evlQuestionnairesService.update(model);
    } else if (!ischecked) {// 取消
      String subjectIds = model.getSubjectIds();
      subjectIds = subjectIds.replace(subjectId + ",", "");
      model.setSubjectIds(subjectIds);
      this.evlQuestionnairesService.update(model);
    }
  }

  /**
   * 设置评教学期
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionOperator")
  public void QuestionOperator(EvlQuestionnaires model) {
    this.evlQuestionnairesService.update(model);
  }

  /**
   * 设置指标层级
   * 
   * @return
   */
  @RequestMapping(value = "setIndicatorType")
  public void setIndicatorType(EvlQuestionnaires model) {
    this.evlQuestionnairesService.update(model);
  }

  /**
   * 设置评教时限
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionTimeLine")
  public void setQuestionTimeLine(EvlTimeline time) {
    EvlTimeline model = new EvlTimeline();
    if (time != null) {
      Integer type = time.getType();
      Integer questionId = time.getQuestionnairesId();
      EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
      model.setOrgId(eq.getOrgId());
      model.setQuestionnairesId(questionId);
      model.setType(type);
      List<EvlTimeline> modelList = evlTimelineService.findAll(model);
      if (!CollectionUtils.isEmpty(modelList)) {
        for (EvlTimeline evlTimeline : modelList) {
          evlTimelineService.delete(evlTimeline.getId());
        }
      }
      time.setOrgId(eq.getOrgId());
      evlTimelineService.save(time);
    }
  }

  /**
   * 设置评教等级权重
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionLevelWeight/addOrUpdate")
  @ResponseBody
  public Result setQuestionLevelWeight(Integer id, Integer questionnairesId,
      Double levelWeight, String levelName) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Result result = new Result();
    EvlLevelWeight model = new EvlLevelWeight();
    try {
      if (null == id) {// 新增
        model.setQuestionnairesId(questionnairesId);
        model.setLevelWeight(levelWeight);
        model.setLevelName(levelName);
        model.setOrgId(user.getOrgId());
        model = evlLevelWeightService.save(model);
      } else {// 更新
        model.setId(id);
        model.setLevelWeight(levelWeight);
        model.setLevelName(levelName);
        evlLevelWeightService.update(model);
      }
    } catch (Exception e) {
      result.setCode(-1);
      e.printStackTrace();
    }
    result.setCode(model.getId());
    return result;
  }

  /**
   * 设置评教等级权重(删除)
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionLevelWeight/delete")
  @ResponseBody
  public Result setQuestionLevelWeight(Integer id) {
    Result result = new Result();
    try {
      evlLevelWeightService.delete(id);
      result.setCode(200);
    } catch (Exception e) {
      result.setCode(-1);
      result.setMsg("删除失败");
      e.printStackTrace();
    }
    return result;
  }

  /*-----------------------------------------设计问卷----------------------------------*/
  /**
   * 设计问卷
   * 
   * @return
   */
  @RequestMapping(value = "settingQuestionIndicator")
  public String setQuestionTwoStep(EvlQuestionnaires evlQuestionnaires,
      Model model) {
    if (getQuestionCache() != null) {
      getQuestionCache().clear();
    }
    // 加载已设置设计问卷
    EvlQuestionnaires currentEvlQuestionnaires = evlQuestionnairesService
        .findOne(evlQuestionnaires.getQuestionnairesId());
    String viewName = "";// 返回的url
    if (null == currentEvlQuestionnaires) {
      viewName = "err";
      return viewName(viewName);
    }
    model.addAttribute("question", currentEvlQuestionnaires);
    // 学科
    List<SubjectVo> subjectList = evlquestionBizService
        .findSubjectByQuestionId(evlQuestionnaires.getQuestionnairesId());// 学科
    model.addAttribute("subjectList", subjectList);
    // 权重基础数据
    EvlLevelWeight evlLevelWeight = new EvlLevelWeight();
    evlLevelWeight.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    List<EvlLevelWeight> levelList = evlLevelWeightService.find(evlLevelWeight,
        100);// 权重
    model.addAttribute("levelList", levelList);
    // 建议类型
    EvlSuggestionType suggestType = new EvlSuggestionType();
    suggestType.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    suggestType.setOrder("id asc");
    List<EvlSuggestionType> suggestTypeList = evlSuggestionTypeService
        .findAll(suggestType);
    model.addAttribute("suggestTypeList", suggestTypeList);
    Integer questionType = currentEvlQuestionnaires.getIndicatorType();
    switch (questionType) {
    case 0:// 整体评价
      viewName = "settingQuestionIndicatorAll";
      break;
    case 1:// 对一级指标评教
      viewName = "settingQuestionIndicatorOne";
      break;
    case 2:// 对二级指标评教
      viewName = "settingQuestionIndicatorTwo";
      break;
    }
    return viewName(viewName);
  }

  /**
   * 预览问卷《第二步》
   * 
   * @param evlQuestionnaires
   * @param model
   * @return
   */
  @RequestMapping(value = "viewQuestionIndicator")
  public String viewQuestionIndicator(EvlQuestionnaires evlQuestionnaires,
      Model model) {
    // 加载已设置设计问卷
    EvlQuestionnaires currentEvlQuestionnaires = evlQuestionnairesService
        .findOne(evlQuestionnaires.getQuestionnairesId());
    String viewName = "";
    model.addAttribute("question", currentEvlQuestionnaires);
    // 学科
    List<SubjectVo> subjectList = evlquestionBizService
        .findSubjectByQuestionId(evlQuestionnaires.getQuestionnairesId());// 学科
    model.addAttribute("subjectList", subjectList);
    // 权重基础数据
    EvlLevelWeight evlLevelWeight = new EvlLevelWeight();
    evlLevelWeight.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    List<EvlLevelWeight> levelList = evlLevelWeightService.find(evlLevelWeight,
        100);// 权重
    model.addAttribute("levelList", levelList);
    // 建议类型
    EvlSuggestionType suggestType = new EvlSuggestionType();
    suggestType.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
    suggestType.setOrder("id asc");
    List<EvlSuggestionType> suggestTypeList = evlSuggestionTypeService
        .findAll(suggestType);
    String suggestTypeNames = "";
    for (EvlSuggestionType evlSuggestionType : suggestTypeList) {
      suggestTypeNames += evlSuggestionType.getName() + "、";
    }
    if (StringUtils.isNotEmpty(suggestTypeNames)) {
      suggestTypeNames = suggestTypeNames.substring(0,
          suggestTypeNames.length() - 1);
    }
    model.addAttribute("suggestTypeNames", suggestTypeNames);
    Integer questionType = currentEvlQuestionnaires.getIndicatorType();
    switch (questionType) {
    case 0:// 整体评价
      viewName = "viewQuestionIndicatorAll";
      break;
    case 1:// 对一级指标评教
      viewName = "viewQuestionIndicatorOne";
      break;
    case 2:// 对二级指标评教
      viewName = "viewQuestionIndicatorTwo";
      break;
    }
    return viewName(viewName);
  }

  /**
   * 设置评教系统标题等相关
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionTitle")
  public String setQuestionTitle() {
    return null;
  }

  /**
   * 设置评教对学校意见或建议
   * 
   * @return
   */
  @RequestMapping(value = "setQuestionSuggestion")
  @ResponseBody
  public Result setQuestionSuggestion(EvlSuggestionType model) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Result result = new Result();
    if (model.getId() != null) {// 更新
      evlSuggestionTypeService.update(model);
      result.setData(model);
    } else {
      model.setOrgId(user.getOrgId());
      model = evlSuggestionTypeService.save(model);
      result.setData(model);
    }
    result.setCode(200);
    return result;
  }

  /**
   * 设置评教对学校意见或建议
   * 
   * @return
   */
  @RequestMapping(value = "delSuggestType")
  @ResponseBody
  public Result delSuggestType(@RequestParam(required = true) Integer id) {
    Result result = new Result();
    try {
      evlSuggestionTypeService.delete(id);
      result.setCode(200);
    } catch (Exception e) {
      e.printStackTrace();
      result.setMsg("操作异常");
    }
    return result;
  }

  /**
   * 删除问卷
   * 
   * @param questionnairesId
   * @param userId
   * @return
   */
  @RequestMapping(value = "delQuestion")
  @ResponseBody
  public Result delQuestion(Integer questionnairesId, Integer userId) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Result result = new Result();
    try {
      this.evlQuestionnairesService.delete(questionnairesId);
      // 删除年级班级
      EvlClass class_ = new EvlClass();
      class_.setQuestionnairesId(questionnairesId);
      class_.setOrgId(user.getOrgId());
      List<EvlClass> classList = evlClassService.findAll(class_);
      for (EvlClass evlClass : classList) {
        evlClassService.delete(evlClass.getId());
      }
      // 删除权重
      EvlLevelWeight level = new EvlLevelWeight();
      level.setQuestionnairesId(questionnairesId);
      level.setOrgId(user.getOrgId());
      List<EvlLevelWeight> levelList = evlLevelWeightService.findAll(level);
      for (EvlLevelWeight evlLevelWeight : levelList) {
        evlLevelWeightService.delete(evlLevelWeight.getId());
      }
      // 删除评审时限
      EvlTimeline time = new EvlTimeline();
      time.setOrgId(user.getOrgId());
      time.setQuestionnairesId(questionnairesId);
      List<EvlTimeline> timeList = evlTimelineService.findAll(time);
      for (EvlTimeline evlTimeline : timeList) {
        evlTimelineService.delete(evlTimeline.getId());
      }
    } catch (Exception e) {
      result.setCode(-1);
      e.printStackTrace();
    }
    result.setCode(200);
    return result;
  }

  /**
   * 删除问卷{初始化方案时，不点击保存，删除临时问卷}
   * 
   * @param questionnairesId
   * @param userId
   * @return
   */
  @RequestMapping(value = "delTagQuestion")
  @ResponseBody
  public Result delTagQuestion(Integer questionnairesId, Integer userId) {
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Result result = new Result();
    // 问卷
    EvlQuestionnaires currentQuestion = evlQuestionnairesService
        .findOne(questionnairesId);
    if (currentQuestion != null) {
      if (currentQuestion.getStatus() >= EvlQuestionType.xiangguanshezhi
          .getValue()) {
        return new Result();
      } else {
        try {
          this.evlQuestionnairesService.delete(questionnairesId);
          // 删除年级班级
          EvlClass class_ = new EvlClass();
          class_.setQuestionnairesId(questionnairesId);
          class_.setOrgId(user.getOrgId());
          List<EvlClass> classList = evlClassService.findAll(class_);
          for (EvlClass evlClass : classList) {
            evlClassService.delete(evlClass.getId());
          }
          // 删除权重
          EvlLevelWeight level = new EvlLevelWeight();
          level.setQuestionnairesId(questionnairesId);
          level.setOrgId(user.getOrgId());
          List<EvlLevelWeight> levelList = evlLevelWeightService.findAll(level);
          for (EvlLevelWeight evlLevelWeight : levelList) {
            evlLevelWeightService.delete(evlLevelWeight.getId());
          }
          // 删除评审时限
          EvlTimeline time = new EvlTimeline();
          time.setOrgId(user.getOrgId());
          time.setQuestionnairesId(questionnairesId);
          List<EvlTimeline> timeList = evlTimelineService.findAll(time);
          for (EvlTimeline evlTimeline : timeList) {
            evlTimelineService.delete(evlTimeline.getId());
          }
        } catch (Exception e) {
          result.setCode(-1);
          e.printStackTrace();
        }
      }
    }
    result.setCode(200);
    return result;
  }

  /**
   * 校验发布问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @RequestMapping(value = "checkQuestionStatus_fabu")
  @ResponseBody
  public Result checkQuestionStatus_fabu(Integer questionnairesId,
      Integer status) {
    Result result = new Result();
    result.setCode(200);
    String msg1 = "";
    try {
      /* 1:第一步设置判断是否合理 */
      EvlQuestionnaires model = this.evlQuestionnairesService
          .findOne(questionnairesId);
      // 哪些学科
      if (StringUtils.isEmpty(model.getSubjectIds())
          || model.getSubjectIds().split(",").length == 0) {
        msg1 += "\"评价哪些学科\"、";
        result.setCode(-1);
      }
      // 哪些学生评教
      if (null == model.getStudentType()) {
        msg1 += "\"哪些学生评\"、";
        result.setCode(-1);
      }
      // 等级和权重
      EvlLevelWeight level = new EvlLevelWeight();
      level.setQuestionnairesId(questionnairesId);
      EvlLevelWeight levelmodel = evlLevelWeightService.findOne(level);
      if (null == levelmodel) {
        msg1 += "\"评价等级及权重\"、";
        result.setCode(-1);
      }
      /* 2:第二步设置判断 */
      String msg2 = "";
      // 问卷内容
      if (StringUtils.isEmpty(model.getDirections())) {
        msg2 += "\"第二步:设计问卷\"中的\"问卷内容\"";
        result.setCode(-1);
      }
      String msg = "您的:";
      if (!StringUtils.isEmpty(msg1)) {
        msg1 = msg1.substring(0, msg1.length() - 1);
        msg += "\"第一步:相关设置\"中的" + msg1;
      }
      if (!StringUtils.isEmpty(msg2)) {
        if (StringUtils.isNotBlank(msg1)) {
          msg += "和";
        }
        msg += msg2;
      }
      msg += "还未设置,请检查修改后，再来发布吧！";
      /* 2:第二步设置判断是否合理 */
      if (result.getCode() != -1) {
        Result soreResult = this.evlIndicatorService.checkIndicatorScore(model);
        if (soreResult.getCode() != 200) {
          msg = "您\"第二步：设计问卷\"中的\"问卷内容\"中的分值未达到设定分数，请检查修改后，再来发布吧！";
          result.setCode(-1);
        }
      }
      /* 2:第三步问卷中指标数量是否合理 */
      if (result.getCode() != -1) {
        if (model.getIndicatorType() == EvlQuestionnaires.indicator_type_twoLevel) {
          EvlIndicator indicator = new EvlIndicator();
          indicator.setQuestionnairesId(questionnairesId);
          indicator.setLevel(1);
          EvlIndicator oneIndicator = evlIndicatorService.findOne(indicator);
          EvlIndicator indicator2 = new EvlIndicator();
          indicator2.setQuestionnairesId(questionnairesId);
          indicator2.setLevel(2);
          EvlIndicator twoIndicator = evlIndicatorService.findOne(indicator2);
          if (oneIndicator == null || twoIndicator == null) {
            msg = "请至少添加一个一级指标，一个二级指标！";
            result.setCode(-1);
          }
        } else if (model.getIndicatorType() == EvlQuestionnaires.indicator_type_oneLevel) {
          EvlIndicator indicator = new EvlIndicator();
          indicator.setQuestionnairesId(questionnairesId);
          indicator.setLevel(1);
          EvlIndicator oneIndicator = evlIndicatorService.findOne(indicator);
          if (oneIndicator == null) {
            msg = "请至少添加一个一级指标！";
            result.setCode(-1);
          }
        }
      }
      /* 4:是否发布时间已过 */
      EvlTimeline time = new EvlTimeline();
      time.setType(EvlTimeline.pjsx);
      time.setQuestionnairesId(questionnairesId);
      EvlTimeline timeModel = evlTimelineService.findOne(time);
      if (timeModel != null && timeModel.getEndTime() != null) {
        // 校验发布时间是否已经过去
        Date nowDate = new Date();
        if (nowDate.after(timeModel.getEndTime())) {// 当前时间>结束时间 不允许
          msg = "结束时间已经过去，请重新设置结束时间后再发布！";
          result.setCode(-1);
        }
      }
      result.setMsg(msg);
      if (result.getCode() == 200) {
        try {
          // 校验成功，改变方案状态
          this.evlQuestionnairesService.changeQuestionStatus(questionnairesId,
              status);// 3：修改状态
        } catch (Exception e) {
          e.printStackTrace();
          result.setMsg("修改状态异常");
          result.setCode(-1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 发布问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @RequestMapping(value = "sendMsgByStudentsCode")
  @ResponseBody
  public Result sendMsgByStudentsCode(Integer questionId, Integer status) {
    Result result = new Result();
    result.setCode(200);
    try {
      EvlQuestionMember model = new EvlQuestionMember();
      Integer schoolYear = EvlHelper.getCurrentSchoolYear();
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
      List<EvlQuestionMember> memberDataList = this.evlQuestionMemberService
          .findAll(model);
      List<EvlStudentAccount> accounts = new ArrayList<>();
      for (EvlQuestionMember member : memberDataList) {
        EvlStudentAccount at = new EvlStudentAccount();
        at.setCode(member.getCode());
        at.setOrgId(member.getOrgId());
        at = evlStudentAccountService.findOne(at);
        accounts.add(at);
      }
      EvlQuestionnaires questionModel = this.evlQuestionnairesService
          .findOne(questionId);
      sendMessage(questionModel, accounts);
    } catch (Exception e) {

    }
    return result;
  }

  /**
   * 发布问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @RequestMapping(value = "changeQuestionStatus_fabu")
  @ResponseBody
  public Result changeQuestionStatus_fabu(Integer questionnairesId,
      Integer status) {
    Result result = new Result();
    result.setCode(200);
    try {
      EvlQuestionnaires model = this.evlQuestionnairesService
          .findOne(questionnairesId);
      model.setLastDttm(new Date());
      this.evlQuestionnairesService.update(model);
      // 校验通过，允许发布
      result.setMsg("问卷发布后，将自动生成评教账号，您可以点击“评教账号”按钮进行查看。");
      // 时间是否设置：若未设置，则开始时间设置为当前时间
      EvlTimeline time = new EvlTimeline();
      time.setQuestionnairesId(questionnairesId);
      time.setType(EvlTimeline.pjsx);
      time.setOrgId(model.getOrgId());
      EvlTimeline timemodel = evlTimelineService.findOne(time);
      if (null == timemodel) {
        time.setBeginTime(new Date());
        evlTimelineService.save(timemodel);
      } else if (timemodel.getBeginTime() == null) {
        timemodel.setBeginTime(new Date());
        evlTimelineService.update(timemodel);
      }
      try {
        this.evlquestionBizService.gerenateAccountUrl(model);// 生成账号 ||
                                                             // 增加jynotice
        try {
          if (model.getType() == EvlQuestionnaires.type_jiazhangpingjiao) {// 2:发送短信
            sendMessage(model);
          }
        } catch (Exception e) {
          e.printStackTrace();
          result.setMsg("发送短信异常");
        }
      } catch (Exception e) {
        e.printStackTrace();
        result.setMsg("生成账号异常");
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.setMsg("发布失败，请联系管理员！");
    }
    return result;
  }

  private Result sendMessage(EvlQuestionnaires model) {
    Result result = new Result();
    result.setCode(200);
    List<EvlStudentAccount> studentsList = this.evlQuestionnairesService
        .getStudentsList(model.getQuestionnairesId());
    // 发送短信
    // 查询发布时间
    EvlTimeline time = new EvlTimeline();
    time.setQuestionnairesId(model.getQuestionnairesId());
    time.setType(EvlTimeline.pjsx);
    EvlTimeline timeModel = evlTimelineService.findOne(time);
    try {
      HttpServletRequest request = WebThreadLocalUtils.getRequest();
      String serverUrl = "";
      if (80 == request.getServerPort()) {
        serverUrl = request.getServerName() + request.getContextPath() + "/o/";
      } else {
        serverUrl = request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/o/";
      }
      String msgTem = ConfigUtils
          .readConfig(
              "temp",
              "尊敬的保师附校家长：现对您孩子的所有任课老师暑期有无参与有偿家教情况进行问卷调查。请您{0}登录网址 {1} 进行评价，若手机上无法打开，请将网址输入到电脑上操作。谢谢！");
      String dateStr = "";
      if (timeModel != null && timeModel.getEndTime() != null) {
        dateStr = DateUtils.formatDate(timeModel.getEndTime(), "于MM月dd")
            + "日之前，";
      }
      int singleMax = Integer.parseInt(ConfigUtils
          .readConfig("single_max", "0"));
      logger.info("SendMsgTask start! ...");
      ForkJoinPool pool = new ForkJoinPool();
      SendMsgTask smsSendMsgTask = new SendMsgTask(0, studentsList.size(),
          studentsList, model, singleMax, msgTem, dateStr, serverUrl);
      pool.submit(smsSendMsgTask).get();
      logger.info("SendMsgTask end! ...");
      pool.shutdown();
    } catch (Exception e) {
      result.setCode(500);
      logger.error("消息发送异常！", e);
    }
    return result;
  }

  private Result sendMessage(EvlQuestionnaires model,
      List<EvlStudentAccount> studentsList) {
    Result result = new Result();
    result.setCode(200);
    // 发送短信
    // 查询发布时间
    EvlTimeline time = new EvlTimeline();
    time.setQuestionnairesId(model.getQuestionnairesId());
    time.setType(EvlTimeline.pjsx);
    EvlTimeline timeModel = evlTimelineService.findOne(time);
    try {
      HttpServletRequest request = WebThreadLocalUtils.getRequest();
      String serverUrl = "";
      if (80 == request.getServerPort()) {
        serverUrl = request.getServerName() + request.getContextPath() + "/o/";
      } else {
        serverUrl = request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/o/";
      }
      // 文件记录短信发送数量
      String msgTem = ConfigUtils
          .readConfig(
              "temp",
              "尊敬的保师附校家长：现对您孩子的所有任课老师暑期有无参与有偿家教情况进行问卷调查。请您{0}登录网址 {1} 进行评价，若手机上无法打开，请将网址输入到电脑上操作。谢谢！");
      String dateStr = "";
      if (timeModel != null && timeModel.getEndTime() != null) {
        dateStr = DateUtils.formatDate(timeModel.getEndTime(), "于MM月dd")
            + "日之前，";
      }
      int singleMax = Integer.parseInt(ConfigUtils
          .readConfig("single_max", "0"));
      logger.info("SendMsgTask start! ...");
      ForkJoinPool pool = new ForkJoinPool();
      SendMsgTask smsSendMsgTask = new SendMsgTask(0, studentsList.size(),
          studentsList, model, singleMax, msgTem, dateStr, serverUrl);
      pool.submit(smsSendMsgTask).get();
      logger.info("SendMsgTask end! ...");
      pool.shutdown();
    } catch (Exception e) {
      result.setCode(500);
      logger.error("消息发送异常！", e);
    }
    return result;
  }

  /**
   * forkjoin处理发送短信
   * 
   * <pre>
   * 
   * </pre>
   * 
   * @author ljh
   * @version $Id: EvlQuestionController.java, v 1.0 2016年8月31日 下午1:41:12 ljh
   *          Exp $
   */
  class SendMsgTask extends RecursiveTask<Integer> {
    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = 1L;

    private static final int THRESHOLD = 200;

    private int start;

    private int end;

    private List<EvlStudentAccount> accountList;

    private EvlQuestionnaires model;
    private int singleMax;
    private String msgTem;
    private String serverUrl;
    private String dateStr;

    public SendMsgTask(int start, int end, List<EvlStudentAccount> accountList,
        EvlQuestionnaires model, int singleMax, String msgTem, String dateStr,
        String serverUrl) {
      this.start = start;
      this.end = end;
      this.accountList = accountList;
      this.model = model;
      this.singleMax = singleMax;
      this.msgTem = msgTem;
      this.serverUrl = serverUrl;
      this.dateStr = dateStr;
    }

    @Override
    protected Integer compute() {
      boolean canCompute = (end - start) <= THRESHOLD;
      if (canCompute) {
        sendMsg(start, end, accountList, model, singleMax, msgTem, dateStr,
            serverUrl);
      } else {
        // 如果任务大于阀值，就分裂成两个子任务计算
        int mid = (start + end) / 2;
        SendMsgTask leftTask = new SendMsgTask(start, mid, accountList, model,
            singleMax, msgTem, dateStr, serverUrl);
        SendMsgTask rightTask = new SendMsgTask(mid + 1, end, accountList,
            model, singleMax, msgTem, dateStr, serverUrl);
        // 执行子任务
        leftTask.fork();
        rightTask.fork();
        leftTask.join();
        rightTask.join();
      }
      return 1;
    }

    public void sendMsg(int start, int end,
        List<EvlStudentAccount> studentsList, EvlQuestionnaires model,
        int singleMax, String msgTem, String dateStr, String serverUrl) {
      for (int i = start; i < end; i++) {
        EvlStudentAccount evlStudentAccount = studentsList.get(i);
        if (evlStudentAccount.getCellphone() == null) {
          continue;// 手机号为空
        }
        EvlQuestionMember member = new EvlQuestionMember();
        member.setCode(evlStudentAccount.getCode());
        member.setQuestionId(model.getQuestionnairesId());
        EvlQuestionMember exitmodel = evlQuestionMemberService.findOne(member);
        if (exitmodel != null) {
          // 发送短信
          String url = "";
          url = serverUrl + exitmodel.getUrl();
          String msg = MessageFormat.format(msgTem, dateStr, url);
          String cellPhone = evlStudentAccount.getCellphone();
          if (StringUtils.isBlank(cellPhone)) {
            logger.info("{}手机号码为空。", exitmodel.getCode());
            continue;
          }
          int count = evlSmsService.getTodayCount(cellPhone);
          if (count >= singleMax) {
            // 被通知用户超出上限
            exitmodel.setStatus(EvlMemberStatus.tongzhichaoxian.getValue());
            evlQuestionMemberService.update(exitmodel);
            continue;
          } else {
            Map<String, String> smsResult = smsService.sendSms(msg, cellPhone);
            // 记录短信发送状态
            if ((StatusCode.code_2.getCode() + "")
                .equals(smsResult.get("code"))) {
              exitmodel.setStatus(EvlMemberStatus.tongzhichenggong.getValue());
              evlQuestionMemberService.update(exitmodel);
              logger.info("{}消息发送成功。", cellPhone);
            } else {
              exitmodel.setStatus(EvlMemberStatus.tongzhishibai.getValue());
              evlQuestionMemberService.update(exitmodel);
              logger.error("{}消息发送失败：{}。", cellPhone, smsResult.get("msg"));
            }
            evlSmsService.saveTodayCount(cellPhone);
          }
        }
      }
    }
  }

  /**
   * 检查反馈是否可以结束问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @RequestMapping(value = "checkEndQuestionStatus")
  @ResponseBody
  public Result checkEndQuestionStatus(Integer questionnairesId, Integer status) {
    EvlQuestionnaires model = this.evlQuestionnairesService
        .findOne(questionnairesId);
    String personType = "";
    if (model.getType() == EvlQuestionnaires.type_xueshengpingjiao) {// 学生评教
      personType = "学生";
    } else if (model.getType() == EvlQuestionnaires.type_jiazhangpingjiao) {
      personType = "家长";
    }
    Result result = new Result();
    try {
      // 未参与评教的用户
      List<EvlQuestionMember> membermodelList = evlQuestionMemberService
          .getNotInputStudent(questionnairesId);
      if (CollectionUtils.isEmpty(membermodelList)) {
        result.setCode(200);
        result.setMsg("您确定要结束此问卷吗？");
      } else {
        int count = membermodelList.size();
        result.setCode(-1);
        result.setMsg("您确定要结束此问卷吗？目前还有" + count + "位" + personType
            + "未进行评教，若“结束评教”后，系统将视该" + count + "位" + personType + "已弃权！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 结束问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @RequestMapping(value = "changeQuestionStatus_jieshu")
  @ResponseBody
  public Result changeQuestionStatus_jieshu(
      Integer questionnairesId,
      Integer status,
      @RequestParam(value = "forceUpdateTch", defaultValue = "true", required = false) Boolean forceUpdateTch) {
    Result result = new Result();
    // 结束时间是否设置：若未设置，则结束时间设置为当前时间
    EvlTimeline time = new EvlTimeline();
    time.setQuestionnairesId(questionnairesId);
    time.setType(EvlTimeline.pjsx);
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionnairesId);
    time.setOrgId(eq.getOrgId());
    EvlTimeline timemodel = evlTimelineService.findOne(time);
    if (null == timemodel) {
      time.setEndTime(new Date());
      evlTimelineService.save(time);
    } else if (timemodel.getEndTime() == null) {
      timemodel.setEndTime(new Date());
      evlTimelineService.update(timemodel);
    }

    this.evlQuestionnairesService
        .changeQuestionStatus(questionnairesId, status);// 更新问卷状态
    evlQuestionMemberService.updateMemberStatus(questionnairesId);// 更新未提交的用户状态未弃权
    evlOperateResultService.batchDelete(questionnairesId);// 删除脏数据{‘请选择’}
    if (status.equals(EvlQuestionType.jieshu.getValue())) {
      eq.setStatus(EvlQuestionType.jieshu.getValue());
      // 计算统计分析结果
      evlAnalyzeIndicatorService.saveOrUpdateAnalyzeIndicator(eq);
      if (forceUpdateTch) {
        evlAnalyzeTeacherService.saveOrUpdateAnalyzeTeacher(eq);
      }
    }
    result.setCode(200);
    return result;
  }

  /**
   * 设置第一步：保存修改状态
   * 
   * @return
   */
  @RequestMapping(value = "changeQuestionStatus_settingOne")
  @ResponseBody
  public Result changeQuestionStatus_settingOne(Integer questionnairesId,
      Integer status) {
    EvlQuestionnaires question = this.evlQuestionnairesService
        .findOne(questionnairesId);
    Result result = new Result();
    try {
      if (status > question.getStatus()) {
        this.evlQuestionnairesService.changeQuestionStatus(questionnairesId,
            status);
        result.setCode(200);
      }
    } catch (Exception e) {
      result.setCode(-1);
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 设置第二步：保存修改状态
   * 
   * @return
   */
  @RequestMapping(value = "saveQuestionIndicator")
  @ResponseBody
  public Result changeQuestionStatus_settingTwo(EvlQuestionnaires model) {
    // 刷新缓存
    if (getQuestionCache() != null) {
      getQuestionCache().clear();
    }
    EvlQuestionnaires question = this.evlQuestionnairesService.findOne(model
        .getQuestionnairesId());
    Result result = new Result();
    try {
      if (model.getStatus() > question.getStatus()) {
        model.setStatus(null);
      }
      // 修改问卷内容
      evlQuestionnairesService.update(model);
      // 处理冗余数据{建议}
      if (question.getIscollect() == EvlQuestionnaires.iscollect_bushouji) {
        EvlSuggestionType type = new EvlSuggestionType();
        type.setQuestionnairesId(question.getQuestionnairesId());
        type.setOrgId(question.getOrgId());
        List<EvlSuggestionType> typeList = evlSuggestionTypeService
            .findAll(type);
        if (typeList.size() > 0) {
          for (EvlSuggestionType evlSuggestionType : typeList) {
            evlSuggestionTypeService.delete(evlSuggestionType.getId());
          }
        }
      }
      // 核算分数{用于回调}
      Result soreResult = this.evlIndicatorService
          .checkIndicatorScore(question);
      if (soreResult.getCode() != 200) {
        question.setFlago("err");
      }
      result.setCode(200);
      result.setData(question);
    } catch (Exception e) {
      result.setCode(-1);
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 修改问卷状态统一入口
   * 
   * @return
   */
  @RequestMapping(value = "changeQuestionStatus")
  @ResponseBody
  public Result changeQuestionStatus(Integer questionnairesId, Integer status) {
    Result result = new Result();
    try {
      this.evlQuestionnairesService.changeQuestionStatus(questionnairesId,
          status);
      result.setCode(200);
    } catch (Exception e) {
      result.setCode(-1);
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取所有指标：校验分数
   * 
   * @param model
   * @return
   */

  @RequestMapping("toCheckScoreIndicator")
  public String getCheckScoreIndicator(Model model, Integer questionnairesId) {
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnairesId);
    EvlIndicatorVo indicators = evlIndicatorService
        .getAllIndicatorList(evlQuestionnaires);// 指标
    model.addAttribute("indicators", indicators);
    EvlQuestionnaires question = this.evlQuestionnairesService
        .findOne(questionnairesId);
    model.addAttribute("question", question);
    return viewName("checkScoreIndicator");
  }

  /**
   * 保存校验分数
   * 
   * @param model
   * @return
   */

  @RequestMapping("save_checkScoreIndicator")
  @ResponseBody
  public Result getCheckScoreIndicator(@RequestBody List<JSONObject> param) {
    Result result = new Result();

    try {
      result.setCode(200);
      result.setMsg("保存分数成功");
      for (JSONObject jsonObject : param) {
        String id = (String) jsonObject.get("id");
        String scoreTotal = (String) jsonObject.get("scoreTotal");
        EvlIndicator temp = evlIndicatorService.findOne(id);
        temp.setScoreTotal(StringUtils.isEmpty(scoreTotal) ? 0.0 : Double
            .parseDouble(scoreTotal));
        evlIndicatorService.update(temp);
      }
    } catch (Exception e) {
      result.setCode(-1);
      result.setMsg("保存分数失败");
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 复用问卷:获取问卷列表(和当前问卷指标类型一致)
   * 
   * @return
   */
  @RequestMapping(value = "getOldQuestionList")
  @ResponseBody
  public Result getOldQuestionList(Integer questionnairesId) {
    Result result = new Result();
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    EvlQuestionnaires m = this.evlQuestionnairesService
        .findOne(questionnairesId);
    EvlQuestionnaires model = new EvlQuestionnaires();
    model.setOrgId(user.getOrgId());
    model.setIndicatorType(m.getIndicatorType());
    model.addOrder("crtDttm desc");
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("questionnairesId", questionnairesId);
    model.addCustomCondition(" and questionnairesId <> :questionnairesId",
        param);
    List<EvlQuestionnaires> questions = this.evlQuestionnairesService
        .findAll(model);
    result.setData(questions);
    return result;
  }

  /**
   * 跳转预览指标
   * 
   * @param model
   * @param questionnairesId
   * @return
   */
  @RequestMapping("toViewAllIndicator")
  public String getViewAllIndicator(Model model, Integer questionnairesId) {
    EvlQuestionnaires question = evlQuestionnairesService
        .findOne(questionnairesId);
    model.addAttribute("question", question);
    List<SubjectVo> subjectList = evlquestionBizService
        .findSubjectByQuestionId(questionnairesId);// 学科
    model.addAttribute("subjectList", subjectList);
    EvlLevelWeight evlLevelWeight = new EvlLevelWeight();
    evlLevelWeight.setQuestionnairesId(questionnairesId);
    List<EvlLevelWeight> levelList = evlLevelWeightService.find(evlLevelWeight,
        100);// 权重
    model.addAttribute("levelList", levelList);
    EvlIndicatorVo indicators = evlIndicatorService
        .getAllIndicatorList(question);// 指标
    model.addAttribute("indicators", indicators);
    String returnStr = "";
    if (question.getIndicatorType() == EvlQuestionnaires.indicator_type_zhengti) {
      returnStr = "seeQuestionIndicatorAll";
    } else if (question.getIndicatorType() == EvlQuestionnaires.indicator_type_oneLevel) {
      returnStr = "seeQuestionIndicatorOne";
    } else if (question.getIndicatorType() == EvlQuestionnaires.indicator_type_twoLevel) {
      returnStr = "seeQuestionIndicatorTwo";
    }
    model.addAttribute("question", question);
    return viewName(returnStr);
  }

  /**
   * 班级保存
   * 
   * @param questionnairesId
   * @param map
   * @return
   */
  @RequestMapping("class_save")
  public Result class_save(@RequestBody List<JSONObject> paramMap) {
    JSONObject question = paramMap.get(paramMap.size() - 1);
    Integer id = question.getInteger("questionnairesId");
    EvlClass delmodel = new EvlClass();// 先删除记录
    delmodel.setQuestionnairesId(id);
    List<EvlClass> delmodelList = evlClassService.findAll(delmodel);
    for (EvlClass temp : delmodelList) {
      evlClassService.delete(temp.getId());
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(id);
    for (int i = 0; i < paramMap.size() - 1; i++) {
      Integer questionnairesId = paramMap.get(i).getInteger("questionnairesId");
      Integer gradeId = paramMap.get(i).getInteger("gradeId");
      Integer classId = paramMap.get(i).getInteger("id");
      EvlClass model = new EvlClass();
      model.setQuestionnairesId(questionnairesId);
      model.setGradeId(gradeId);
      model.setClassId(classId);
      model.setOrgId(eq.getOrgId());
      EvlClass temp = evlClassService.findOne(model);
      if (temp == null) {
        evlClassService.save(model);
      }
    }
    return new Result("success");
  }

  /**
   * 下载评教账号
   * 
   * @param response
   * @param questionId
   * @param status
   */
  @RequestMapping("downloadAccount")
  public void downloadAccount(HttpServletResponse response, Integer questionId,
      Integer status) {
    HttpServletRequest request = WebThreadLocalUtils.getRequest();
    String url = request.getScheme() + "://" + request.getServerName() + ":"
        + request.getServerPort() + request.getContextPath();
    this.evlQuestionnairesService.downloadAccount(response, questionId, status,
        url);
  }

  /**
   * 问卷中应评审、已评审 用户详情
   * 
   * @param response
   * @param questionId
   * @param status
   */
  @RequestMapping("downloadQuestionMemberDetail")
  public void downloadQuestionMemberDetail(HttpServletResponse response,
      Integer questionId, EvlQuestionMember member) {
    this.evlquestionBizService.downloadQuestionMemberDetail(response,
        questionId, member);
  }

  /**
   * 查看评教账号
   * 
   * @param model
   * @param status
   * @param questionId
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping("accountDetail")
  public String seeAccountHtml(Model model, EvlQuestionMember member)
      throws UnsupportedEncodingException {
    EvlQuestionnaires question = evlQuestionnairesService.findOne(member
        .getQuestionId());
    model.addAttribute("question", question);
    model.addAttribute("status", member.getStatus());
    model.addAttribute("name", member.getName());
    member.setOrgId(question.getOrgId());
    member.setName(SqlMapping.LIKE_PRFIX + member.getName()
        + SqlMapping.LIKE_PRFIX);
    if (member.getStatus() != null) {
      Map<String, Object> paramsMap = new HashMap<String, Object>();
      if (member.getStatus() == 2) {// 成功
        member.setStatus(null);
        paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
        member.addCustomCondition("and status>=:status", paramsMap);

      } else if (member.getStatus() == 1) {
        member.setStatus(null);
        paramsMap.put("status", EvlMemberStatus.tongzhichenggong.getValue());
        member.addCustomCondition("and status<:status", paramsMap);
      }
    }
    PageList<EvlQuestionMember> memberList = evlQuestionMemberService
        .findByPage(member);
    List<EvlQuestionMember> datalist = memberList.getDatalist();
    // 获取用户性别和手机号码
    for (EvlQuestionMember evlQuestionMember : datalist) {
      Integer orgId = evlQuestionMember.getOrgId();
      String code = evlQuestionMember.getCode();
      EvlStudentAccount account = new EvlStudentAccount();
      account.setCode(code);
      account.setOrgId(orgId);
      EvlStudentAccount accountModel = evlStudentAccountService
          .findOne(account);
      if (accountModel != null) {
        evlQuestionMember.setFlags(accountModel.getCellphone());
        evlQuestionMember.setFlago(accountModel.getSex() == 0 ? "女" : "男");
      }
    }
    model.addAttribute("memberList", memberList);
    return viewName("accountDetail");
  }

  // private List<SubjectVo> getAllSubject(Integer phaseId) {
  // return evlMetaService.findSubjectByPhaseId(phaseId);
  // }

  // 通过班级d获取name
  private String getClassNameById(Integer id) {
    SchClass class_ = schClassService.findOne(id);
    String name = "";
    if (class_ != null) {
      name = class_.getName();
    }
    return name;
  }

  @RequestMapping("getCurrentQuestion")
  @ResponseBody
  public Result getCurrentQuestion(Integer questionid) {
    EvlQuestionnaires currentQuestion = evlQuestionnairesService
        .findOne(questionid);
    Result result = new Result();
    result.setData(currentQuestion);
    return result;
  }

  @RequestMapping("getQuestionAccountDetail")
  public String getQuestionAccountDetail(Model model, Integer questionId,
      EvlStudentAccount accountModel, ClassStudent classModel) {
    if (classModel.getGradeId() == null) {
      classModel.setClassId(null);
    }
    EvlQuestionnaires currentQuestion = evlQuestionnairesService
        .findOne(questionId);
    model.addAttribute("question", currentQuestion);
    // 已提交
    Integer alreadyReviewCount = this.evlQuestionMemberService
        .getAlreadySubmitStudentsList(questionId);
    // 应评审总人数：非分页
    accountModel.setOrgId(currentQuestion.getOrgId());
    Map<String, String> gradeNamesMap = evlMetaService
        .getGradeMap(currentQuestion.getPhaseId());
    // 分页数据
    Integer[] pageSize = null;
    PageList<EvlStudentAccount> pageList = this.evlquestionBizService
        .getQuestionStudentsListByPage(questionId, accountModel, classModel,
            pageSize);
    for (EvlStudentAccount evlStudentAccount : pageList.getDatalist()) {
      String gradeName = gradeNamesMap.get(evlStudentAccount.getGradeId()
          .toString());// 年级
      String className = this.getClassNameById(evlStudentAccount.getClassId());// 班级
      String sexName = evlStudentAccount.getSex() == 0 ? "女" : "男";// 性别
      evlStudentAccount.setGradeName(gradeName);
      evlStudentAccount.setClassName(className);
      evlStudentAccount.setSexName(sexName);
      evlStudentAccount.setStatus(0);// 未提交
    }
    model.addAttribute("pageList", pageList);
    // 年级{显示下拉框}
    Map<Integer, String> gradeMap = new TreeMap<Integer, String>();

    Organization org = organizationService.findOne(currentQuestion.getOrgId());
    List<Meta> grades = MetaUtils.getOrgTypeMetaProvider().listAllXzGrade(
        org.getSchoolings(), currentQuestion.getPhaseId());
    for (Meta meta : grades) {
      gradeMap.put(meta.getId(), meta.getName());
    }
    // 班级{显示下拉框}
    List<EvlStudentAccount> classList = new ArrayList<EvlStudentAccount>();
    if (classModel.getGradeId() != null) {
      classList = evlQuestionnairesService.findClassListByGradeId(questionId,
          classModel.getGradeId());
    }
    Map<Integer, String> classMap = new TreeMap<Integer, String>();
    for (EvlStudentAccount cl : classList) {
      classMap.put(cl.getClassId(), this.getClassNameById(cl.getClassId()));// 班级);
    }
    model.addAttribute("gradeMap", gradeMap);
    model.addAttribute("classMap", classMap);
    Integer initCount = evlquestionBizService
        .getQuestionStudentsList(questionId);
    model.addAttribute("initCount", initCount);
    model.addAttribute("submitCount", alreadyReviewCount);
    model.addAttribute("m", classModel);
    return viewName("userSubmitAccountDetail");
  }

  /**
   * 已发布学生详情列表
   * 
   * @param model
   * @param questionId
   * @param m
   * @return
   */
  @RequestMapping("getQuestionMemberDetail")
  public String getQuestionMemberDetail(Model model, Integer questionId,
      EvlQuestionMember m) {
    EvlQuestionnaires currentQuestion = evlQuestionnairesService
        .findOne(questionId);
    model.addAttribute("question", currentQuestion);
    Integer status = m.getStatus();
    PageList<EvlQuestionMember> memberList = this.evlQuestionMemberService
        .memberFindByPage(m, questionId);
    Map<String, String> gradeNamesMap = evlMetaService
        .getGradeMap(currentQuestion.getPhaseId());
    for (EvlQuestionMember evlQuestionMember : memberList.getDatalist()) {
      String gradeName = gradeNamesMap.get(evlQuestionMember.getGradeId()
          .toString());// 年级
      String className = this.getClassNameById(evlQuestionMember.getClassId());// 班级
      EvlStudentAccount accountDetail = this.getAccountDetail(evlQuestionMember
          .getCode());
      evlQuestionMember.setGradeName(gradeName);
      evlQuestionMember.setClassName(className);
      if (accountDetail == null) {// 防止发布完成后，删除学生管理中学生导致异常
        continue;
      }
      evlQuestionMember.setSexName(accountDetail.getSex() == 0 ? "女" : "男");
      evlQuestionMember.setCellphone(accountDetail.getCellphone());
    }
    model.addAttribute("pageList", memberList);
    // 已提交
    Integer alreadyReviewCount = this.evlQuestionMemberService
        .getAlreadySubmitStudentsList(questionId);
    // 应评审总人数：非分页

    // 年级{显示下拉框}
    Map<Integer, String> gradeMap = new TreeMap<Integer, String>();
    Organization org = organizationService.findOne(currentQuestion.getOrgId());
    List<Meta> grades = MetaUtils.getOrgTypeMetaProvider().listAllXzGrade(
        org.getSchoolings(), currentQuestion.getPhaseId());
    for (Meta meta : grades) {
      gradeMap.put(meta.getId(), meta.getName());
    }
    // 班级{显示下拉框}
    List<EvlStudentAccount> classList = new ArrayList<EvlStudentAccount>();
    if (m.getGradeId() != null) {
      classList = evlQuestionnairesService.findClassListByGradeId(questionId,
          m.getGradeId());
    }
    Map<Integer, String> classMap = new TreeMap<Integer, String>();
    for (EvlStudentAccount cl : classList) {
      classMap.put(cl.getClassId(), this.getClassNameById(cl.getClassId()));// 班级);
    }
    model.addAttribute("gradeMap", gradeMap);
    model.addAttribute("classMap", classMap);
    Integer initCount = evlquestionBizService
        .getQuestionStudentsList(questionId);
    model.addAttribute("initCount", initCount);
    model.addAttribute("submitCount", alreadyReviewCount);
    m.setStatus(status);
    model.addAttribute("m", m);
    return viewName("userSubmitMemberDetail");
  }

  private EvlStudentAccount getAccountDetail(String code) {
    EvlStudentAccount account = new EvlStudentAccount();
    account.setCode(code);
    return evlStudentAccountService.findOne(account);
  }

  @ResponseBody
  @RequestMapping("getIndicatorByQuestionIdAndLevel")
  public List<EvlIndicator> getIndicatorByQuestionIdAndLevel(
      Integer questionId, Integer level) {
    return this.evlIndicatorService.getAllIndicatorListByLevel(questionId,
        level);
  }
}