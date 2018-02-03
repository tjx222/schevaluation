/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.evl.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlquestionBizService;
import com.tmser.schevaluation.evl.bo.ClassStudent;
import com.tmser.schevaluation.evl.bo.EvlLevelWeight;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlStudentAccount;
import com.tmser.schevaluation.evl.bo.EvlSuggestion;
import com.tmser.schevaluation.evl.bo.EvlSuggestionType;
import com.tmser.schevaluation.evl.bo.EvlTimeline;
import com.tmser.schevaluation.evl.service.ClassStudentService;
import com.tmser.schevaluation.evl.service.EvlLevelWeightService;
import com.tmser.schevaluation.evl.service.EvlOperateResultService;
import com.tmser.schevaluation.evl.service.EvlQuestionMemberService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.service.EvlStudentAccountService;
import com.tmser.schevaluation.evl.service.EvlSuggestionService;
import com.tmser.schevaluation.evl.service.EvlSuggestionTypeService;
import com.tmser.schevaluation.evl.service.EvlTimelineService;
import com.tmser.schevaluation.evl.statics.EvlMemberStatus;
import com.tmser.schevaluation.evl.statics.EvlQuestionType;
import com.tmser.schevaluation.evl.vo.EvlIndicatorVo;
import com.tmser.schevaluation.evl.vo.EvlLoginTag;
import com.tmser.schevaluation.evl.vo.SubjectVo;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.bo.SchClassUser;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.schconfig.clss.service.SchClassUserService;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author yc
 * @author ljh 2017年7月25
 * @version $Id: EvlOperateController1.java, v 1.0 2016年5月20日 上午11:25:39 dell
 *          Exp $
 */
@Controller
@RequestMapping("/")
public class EvlOperateController extends AbstractController {
  @Autowired
  EvlStudentAccountService studentAccountService;
  @Autowired
  EvlQuestionMemberService questionMemberService;
  @Autowired
  private EvlLevelWeightService evlLevelWeightService;
  @Autowired
  private EvlSuggestionService evlSuggestionService;
  @Autowired
  private EvlSuggestionTypeService evlSuggestionTypeService;
  @Autowired
  private SchClassUserService schClassUserService;
  @Autowired
  private EvlOperateResultService evlOperateResultService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private EvlquestionBizService evlquestionBizService;
  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private EvlTimelineService evlTimelineService;
  @Autowired
  private ClassStudentService classStudentService;

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
   * 家长登陆入口
   * 
   * @param m
   * @param params
   * @return
   */
  @RequestMapping("/o/{params}")
  public String login(Model m, @PathVariable(value = "params") String params) {
    String buffer;
    if (StringUtils.isNotBlank(params) && params.length() > 6) {
      buffer = params.substring(0, 6);
    } else {
      buffer = params;
    }
    String checkurl = checkParentLogin(m, buffer);
    if (checkurl != null) {
      return checkurl;// 校验未通过跳转登陆也和提示
    }
    EvlQuestionMember model = new EvlQuestionMember();
    model.setUrl(params);
    model = questionMemberService.findOne(model);
    EvlLoginTag loginTag = fillLoginTag(model);
    return operate(m, loginTag);
  }

  /**
   * 学生登陆入口
   * 
   * @param m
   * @param studentCode
   * @param questionnaireId
   * @param params
   * @return
   */
  @RequestMapping("q/{id}")
  public String login(Model m, @PathVariable("id") Integer questionnaireId,
      @RequestParam(value = "studentCode", required = false) String studentCode) {
    if (StringUtils.isEmpty(studentCode)) {
      return ("/evl/operate/operateLogin");
    }
    String checkurl = checkStudentLogin(m, studentCode, questionnaireId);
    if (checkurl != null) {
      return checkurl;// 校验未通过跳转登陆也和提示
    }
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnaireId);
    EvlQuestionMember model = fillMember(studentCode,
        evlQuestionnaires.getSchoolYear(), evlQuestionnaires.getOrgId(),
        questionnaireId);
    model = questionMemberService.findOne(model);
    EvlLoginTag loginTag = fillLoginTag(model);
    return operate(m, loginTag);
  }

  private EvlLoginTag fillLoginTag(EvlQuestionMember member) {
    EvlLoginTag tag = new EvlLoginTag();
    tag.setOrgId(member.getOrgId());
    tag.setStudentCode(member.getCode());
    tag.setQuestionnairesId(member.getQuestionId());
    tag.setGradeId(member.getGradeId());
    return tag;
  }

  /**
   * 检验学生登陆
   * 
   * @param m
   * @param studentCode
   * @param questionnaireId
   * @return
   */
  private String checkStudentLogin(Model m, String studentCode,
      Integer questionnaireId) {
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnaireId);
    if (evlQuestionnaires == null) {
      m.addAttribute("errorMsg", "问卷不存在，请联系管理员！");
      return ("/evl/operate/operateLogin");
    }
    m.addAttribute("evlQuestionnaires", evlQuestionnaires);
    if (evlQuestionnaires.getStatus() >= EvlQuestionType.fabu.getValue()) {
      String checkDateUrl = checkQuestionDate(m, questionnaireId,
          evlQuestionnaires.getOrgId());
      if (checkDateUrl != null) {
        return checkDateUrl;
      }
    } else {
      m.addAttribute("errorMsg", "此次调查问卷，尚未开始，请您核实一下哟！");
      return ("/evl/operate/operateLogin");
    }

    String checkMemberUnique = checkMemberUnique(m, studentCode,
        evlQuestionnaires.getOrgId(), evlQuestionnaires.getSchoolYear(),
        evlQuestionnaires.getQuestionnairesId());
    if (checkMemberUnique != null) {
      return checkMemberUnique;
    }
    return null;
  }

  private String checkParentLogin(Model m, String params) {
    EvlQuestionMember model = new EvlQuestionMember();
    model.setUrl(params);
    List<EvlQuestionMember> members = questionMemberService.find(model, 2);
    if (CollectionUtils.isEmpty(members)) {
      // 没有相应用户状态
      m.addAttribute("errorMsg", "链接信息错误或者过期，请联系管理员！");
    } else {
      if (members.size() == 1) {
        EvlQuestionMember member = members.get(0);
        // 判断问卷状态
        EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
            .findOne(member.getQuestionId());
        if (EvlQuestionType.fabu.getValue().equals(
            evlQuestionnaires.getStatus())) {
          String checkDateUrl = checkQuestionDate(m,
              evlQuestionnaires.getQuestionnairesId(),
              evlQuestionnaires.getOrgId());
          if (checkDateUrl != null) {
            return checkDateUrl;
          }
        }
      } else {
        m.addAttribute("errorMsg", "链接信息冗余，请联系管理员！");
      }
    }
    return null;
  }

  private String checkMemberUnique(Model m, String studentCode, Integer orgId,
      Integer schoolYear, Integer questionnaireId) {
    EvlQuestionMember model = new EvlQuestionMember();
    model.setCode(studentCode);
    model.setOrgId(orgId);
    model.setSchoolYear(schoolYear);
    model.setQuestionId(questionnaireId);
    List<EvlQuestionMember> buffer = questionMemberService.find(model, 2);
    if (buffer != null) {
      if (buffer.size() > 1) {
        m.addAttribute("errorMsg", "账号信息冗余（库表中不唯一），请联系管理员！");
        return ("/evl/operate/operateLogin");
      }
    } else {
      m.addAttribute("errorMsg", "该账号不存在于此项问卷调查范围内，请联系管理员！");
      return ("/evl/operate/operateLogin");
    }
    return null;
  }

  private EvlQuestionMember fillMember(String studentCode, Integer schoolYear,
      Integer orgId, Integer questionId) {
    EvlQuestionMember model = new EvlQuestionMember();
    model.setCode(studentCode);
    model.setSchoolYear(schoolYear);
    model.setOrgId(orgId);
    model.setQuestionId(questionId);
    return model;
  }

  private String checkQuestionDate(Model m, Integer questionnaireId,
      Integer orgId) {
    // 判断问卷是否在开始、结束时间范围内
    EvlTimeline time = new EvlTimeline();
    time.setType(EvlTimeline.pjsx);
    time.setOrgId(orgId);
    time.setQuestionnairesId(questionnaireId);
    time = evlTimelineService.findOne(time);
    if (time != null) {
      Date now = new Date();
      if (time.getBeginTime() != null && now.before(time.getBeginTime())) {
        m.addAttribute("errorMsg", "此次调查问卷，尚未开始，请您核实一下呦！");
        return ("/evl/operate/operateLogin");
      }
      if (time.getEndTime() != null && now.after(time.getEndTime())) {
        m.addAttribute("errorMsg", "此次调查问卷已结束啦！");
        return ("/evl/operate/operateLogin");
      }
    }
    return null;
  }

  /**
   * 跳转填写问卷
   * 
   * @param tag
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  private String operate(Model m,
      @RequestParam(required = false) EvlLoginTag tag) {
    EvlQuestionnaires evlQuestionnaires = new EvlQuestionnaires();
    if (tag != null) {
      String fillStatus = evlquestionBizService.getFillPrompt(
          tag.getQuestionnairesId(), tag.getStudentCode());// 判断是否已经提交填报或者未开始
      if (StringUtils.isNotEmpty(fillStatus)) {
        m.addAttribute("fillStatus", fillStatus);
        return "redirect:/jy/evl/operate/getFillPrompt?questionId="
            + tag.getQuestionnairesId() + "&userCode=" + tag.getStudentCode();
      }
      EvlQuestionMember member = new EvlQuestionMember();
      member.setCode(tag.getStudentCode());
      member.setQuestionId(tag.getQuestionnairesId());
      EvlQuestionMember exitmodel = questionMemberService.findOne(member);
      if (exitmodel != null) {
        if (exitmodel.getStatus() < EvlMemberStatus.tianxiewenjuan.getValue()) {
          exitmodel.setStatus(EvlMemberStatus.tianxiewenjuan.getValue());
          questionMemberService.update(exitmodel);
        }
      }
      evlQuestionnaires = evlQuestionnairesService.findOne(tag
          .getQuestionnairesId());
      List<SubjectVo> subjectList = null;
      List<EvlLevelWeight> levelList = null;
      if (getQuestionCache() != null) {
        ValueWrapper cacheElement = getQuestionCache().get(
            "evl_q_" + evlQuestionnaires.getQuestionnairesId() + "_sublist");
        if (cacheElement != null) {
          subjectList = (List<SubjectVo>) cacheElement.get();
        }

        cacheElement = getQuestionCache().get(
            "evl_q_" + evlQuestionnaires.getQuestionnairesId() + "_level");
        if (cacheElement != null) {
          levelList = (List<EvlLevelWeight>) cacheElement.get();
        }
      }
      if (subjectList == null) {
        subjectList = evlquestionBizService
            .findSubjectByQuestionId(evlQuestionnaires.getQuestionnairesId());// 学科
        if (getQuestionCache() != null && subjectList != null) {
          getQuestionCache().put(
              "evl_q_" + evlQuestionnaires.getQuestionnairesId() + "_sublist",
              subjectList);
        }
      }
      // 学科
      m.addAttribute("subjectList", subjectList);
      // 权重基础数据
      EvlLevelWeight evlLevelWeight = new EvlLevelWeight();
      evlLevelWeight.setQuestionnairesId(evlQuestionnaires
          .getQuestionnairesId());
      if (levelList == null) {
        levelList = evlLevelWeightService.find(evlLevelWeight, 100);// 权重
        if (getQuestionCache() != null && levelList != null) {
          getQuestionCache().put(
              "evl_q_" + evlQuestionnaires.getQuestionnairesId() + "_level",
              levelList);
        }
      }

      m.addAttribute("levelList", levelList);
      // 建议类型
      if (evlQuestionnaires.getIscollect() != null
          && evlQuestionnaires.getIscollect() == EvlQuestionnaires.iscollect_shouji) {// 收集
        List<EvlSuggestionType> suggestList = null;
        if (getQuestionCache() != null) {
          ValueWrapper cacheElement = getQuestionCache().get(
              "evl_q_" + evlQuestionnaires.getQuestionnairesId() + "_suggest");
          if (cacheElement != null) {
            suggestList = (List<EvlSuggestionType>) cacheElement.get();
          }
        }
        if (suggestList == null) {
          EvlSuggestionType suggest = new EvlSuggestionType();
          suggest.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
          suggestList = evlSuggestionTypeService.findAll(suggest);
          if (getQuestionCache() != null && suggestList != null) {
            getQuestionCache()
                .put(
                    "evl_q_" + evlQuestionnaires.getQuestionnairesId()
                        + "_suggest", suggestList);
          }
        }
        // 建议类型对应数据
        if (CollectionUtils.isEmpty(suggestList)) {
          EvlSuggestion suggestcontent = new EvlSuggestion();
          suggestcontent.setQuestionnairesId(evlQuestionnaires
              .getQuestionnairesId());
          suggestcontent.setCode(tag.getStudentCode());
          EvlSuggestion temp = evlSuggestionService.findOne(suggestcontent);
          m.addAttribute("suggestion", temp);
        } else {
          for (EvlSuggestionType evlSuggestionType : suggestList) {
            EvlSuggestion suggestcontent = new EvlSuggestion();
            suggestcontent.setQuestionnairesId(evlQuestionnaires
                .getQuestionnairesId());
            suggestcontent.setCode(tag.getStudentCode());
            suggestcontent.setSuggestId(evlSuggestionType.getId().toString());
            // 查询是否有值
            EvlSuggestion temp = evlSuggestionService.findOne(suggestcontent);
            if (temp != null) {
              evlSuggestionType.setFlago(temp.getContent());
            } else {
              evlSuggestionType.setFlago(null);
            }
          }
        }

        m.addAttribute("suggestList", suggestList);
      }

      m.addAttribute("userCode", tag.getStudentCode());
      m.addAttribute("tag", tag);

      m.addAttribute("question", evlQuestionnaires);
      switch (evlQuestionnaires.getIndicatorType()) {
      case EvlQuestionnaires.indicator_type_zhengti:
        return ("/evl/operate/operateByAllLevel");
      case EvlQuestionnaires.indicator_type_oneLevel:
        return ("/evl/operate/operateByOneLevel");
      case EvlQuestionnaires.indicator_type_twoLevel:
        return ("/evl/operate/operateByTwoLevel");
      default:
        m.addAttribute("errorMsg", "问卷类型错误，请联系管理员！");
        return ("/evl/operate/operateLogin");
      }

    } else {
      return ("/evl/operate/operateLogin");
    }
  }

  /**
   * 填写问卷操作
   * 
   * @return
   */
  @RequestMapping("/jy/evl/operate/saveOrUpdateLevelAndWeight")
  @ResponseBody
  public Result saveOrUpdateLevelAndWeight(EvlOperateResult model,
      EvlLoginTag tag) {
    Result result = new Result();
    result.setCode(200);
    result.setData(model);
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(model.getQuestionnairesId());
    Integer resultLevel = model.getResultLevel();
    // 分数对应权重
    if (resultLevel == -1) {
      model.setResultScore(0.0);// 分数
    } else {
      EvlLevelWeight weight = evlLevelWeightService.findOne(resultLevel);
      if (weight != null) {
        model.setResultScore(model.getResultScore() * weight.getLevelWeight()
            / 100);// 分数
      }
    }

    // 学生详情
    EvlStudentAccount student = this
        .getUserDetailByUserId(evlQuestionnaires.getSchoolYear(),
            tag.getOrgId(), tag.getStudentCode());
    if (student == null) {
      // 当前学生不存在
      result.setCode(-1);
      result.setMsg("当前问卷无此用户，请联系管理员！");
      return result;
    }
    model.setCode(student.getCode());
    model.setClassId(student.getClassStudent().getClassId());
    // 添加班级默认排序
    SchClass sc = schClassService.findOne(student.getClassId());
    if (sc != null) {
      model.setClassSort(sc.getSort());
    }
    model.setGradeId(student.getClassStudent().getGradeId());
    model.setOrgId(tag.getOrgId());
    model.setStudentName(student.getName());
    // 教师详情
    Integer schoolYear = evlQuestionnaires.getSchoolYear();
    SchClassUser teacherDetail = this.getTeacherDetail(model.getSubjeId(),
        model.getClassId(), schoolYear);
    if (teacherDetail != null) {
      model.setTeacherId(teacherDetail.getTchId());
    }
    boolean rs = evlOperateResultService.saveOrUpdateResult(model);
    if (!rs) {
      result.setCode(-1);
      result.setMsg("问卷填写失败,请刷新重试！");
      return result;
    }
    return result;
  }

  private SchClassUser getTeacherDetail(Integer subjectId, Integer classId,
      Integer schoolYear) {
    SchClassUser scu = null;
    String key = "evl_qscu_" + subjectId + "_cl_" + classId + "_sy_"
        + schoolYear;
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(key);
      if (cacheElement != null) {
        scu = (SchClassUser) cacheElement.get();
      }

    }
    if (scu == null) {
      SchClassUser model = new SchClassUser();
      model.setSchoolYear(schoolYear);
      model.setClassId(classId);
      model.setSubjectId(subjectId);
      model.addCustomCondition(" and type < 2");
      scu = schClassUserService.findOne(model);
      if (getQuestionCache() != null && scu != null) {
        getQuestionCache().put(key, scu);
      }
    }
    return scu;
  }

  /**
   * 通过学号和学校ID获取用户唯一信息
   * 
   * @param orgId
   * @param code
   * @return
   */
  private EvlStudentAccount getUserDetailByUserId(Integer schoolYear,
      Integer orgId, String code) {
    String key = "evl_q_us_org_" + orgId + "_cd_" + code + "_sy_" + schoolYear;
    EvlStudentAccount stu = null;
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(key);
      if (cacheElement != null) {
        stu = (EvlStudentAccount) cacheElement.get();
      }

    }
    if (stu == null) {
      EvlStudentAccount model = new EvlStudentAccount();
      model.setCode(code);
      model.setOrgId(orgId);
      stu = studentAccountService.findOne(model);
      if (stu == null) {
        return null;
      }
      String studentId = stu.getId();
      ClassStudent classStudent = new ClassStudent();
      classStudent.setStudentId(studentId);
      classStudent.setOrgId(orgId);
      classStudent.setSchoolYear(schoolYear);
      ClassStudent classStudentModel = classStudentService
          .findOne(classStudent);
      stu.setClassStudent(classStudentModel);
      if (getQuestionCache() != null && stu != null) {
        getQuestionCache().put(key, stu);
      }
    }
    return stu;
  }

  /**
   * 保存填报数据{建议保存}
   * 
   * @param model
   * @return
   */
  @RequestMapping("/jy/evl/operate/saveOrUpdateInputData")
  @ResponseBody
  public Result saveOrUpdateInputData(@RequestBody List<JSONObject> param) {
    EvlQuestionnaires evlQuestionnaires = new EvlQuestionnaires();
    EvlStudentAccount studentDetail = new EvlStudentAccount();
    Result result = new Result();
    result.setCode(200);
    try {
      for (JSONObject jsonObject : param) {
        Integer questionId = jsonObject.getInteger("questionid");
        Integer orgId = jsonObject.getInteger("orgId");
        String studentCode = jsonObject.getString("studentCode");

        if (evlQuestionnaires.getQuestionnairesId() == null) {
          evlQuestionnaires = evlQuestionnairesService.findOne(questionId);
          studentDetail = getUserDetailByUserId(
              evlQuestionnaires.getSchoolYear(), orgId, studentCode);
        }
        String content = jsonObject.getString("content");
        String suggestName = jsonObject.getString("name");
        String suggestId = jsonObject.getString("suggestId");
        EvlSuggestion model = new EvlSuggestion();
        model.setQuestionnairesId(questionId);
        model.setSuggestId(suggestId);
        model.setOrgId(evlQuestionnaires.getOrgId());
        model.setContent(content);
        model.setSuggestName(suggestName);
        model.setUserType(evlQuestionnaires.getType() == null ? 1
            : evlQuestionnaires.getType());
        if (studentDetail != null) {
          model.setCode(studentDetail.getCode());
          model.setGradeId(studentDetail.getClassStudent().getGradeId());
          model.setClassId(studentDetail.getClassStudent().getClassId());
        }

        boolean succss = evlSuggestionService.saveOrUpdate(model);
        if (!succss) {
          result.setCode(500);
          result.setMsg("保存失败");
        }
      }
    } catch (Exception e) {
      logger.error("", e);
      result.setCode(-1);
    }
    return result;
  }

  @RequestMapping("/jy/evl/operate/input/getAllIndicator")
  @ResponseBody
  public EvlIndicatorVo getAllIndicator(Integer questionnairesId,
      String userCode) {
    EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService
        .findOne(questionnairesId);
    EvlIndicatorVo indicatorList = evlOperateResultService.getAllIndicatorList(
        evlQuestionnaires, userCode);// 指标
    return indicatorList;
  }

  /**
   * 提交问卷
   * 
   * @param questionnairesId
   * @param status
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/jy/evl/operate/changeMemberStatus")
  public Boolean changeMemberStatus(Boolean submitStatus, Integer questionId,
      String userCode) {
    return evlOperateResultService.submit(submitStatus, questionId, userCode);
  }

  /**
   * 根据问卷id和用户code跳转填报提示信息
   * 
   * @param questionId
   * @param userCode
   * @return
   */
  @RequestMapping(value = "/jy/evl/operate/showMsg", method = RequestMethod.POST)
  public String showMsg(Model model, String msg) {
    model.addAttribute("msg", msg);
    return ("/evl/operate/showMsg");
  }

  /**
   * 根据问卷id和用户code跳转填报提示信息
   * 
   * @param questionId
   * @param userCode
   * @return
   */
  @RequestMapping("/jy/evl/operate/getFillPrompt")
  public String getFillPrompt(Model model, Integer questionId, String userCode) {
    String fillStatus = evlquestionBizService.getFillPrompt(questionId,
        userCode);
    model.addAttribute("fillStatus", fillStatus);
    if (StringUtils.isEmpty(fillStatus)) {
      return "redirect:/jy/evl/operate/getFillPrompt?questionId=" + questionId
          + "&userCode=" + userCode;
    }
    return ("/evl/operate/fillPrompt");
  }
}
