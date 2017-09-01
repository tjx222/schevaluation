package com.mainbo.jy.evl.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.echarts.Option;
import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlAnalyzeIndicator;
import com.mainbo.jy.evl.bo.EvlAnalyzeTeacher;
import com.mainbo.jy.evl.bo.EvlClass;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlOperateResult;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.dao.EvlAnalyzeTeacherDao;
import com.mainbo.jy.evl.service.EvlAnalyzeTeacherService;
import com.mainbo.jy.evl.service.EvlClassService;
import com.mainbo.jy.evl.service.EvlIndicatorService;
import com.mainbo.jy.evl.service.EvlMetaService;
import com.mainbo.jy.evl.service.EvlOperateResultService;
import com.mainbo.jy.evl.service.EvlQuestionMemberService;
import com.mainbo.jy.evl.service.EvlQuestionnairesService;
import com.mainbo.jy.evl.statics.EvlMemberStatus;
import com.mainbo.jy.evl.utils.EchartUtil;
import com.mainbo.jy.evl.utils.FreemarkerUtils;
import com.mainbo.jy.evl.vo.EvlAnalyzeIndicatorVo;
import com.mainbo.jy.evl.vo.GradeAndClassVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.manage.org.bo.Organization;
import com.mainbo.jy.manage.org.service.OrganizationService;
import com.mainbo.jy.schconfig.clss.bo.SchClass;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.schconfig.clss.service.SchClassUserService;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.bo.UserSpace;
import com.mainbo.jy.uc.service.UserService;
import com.mainbo.jy.uc.service.UserSpaceService;
import com.mainbo.jy.utils.StringUtils;

/**
 * 评价分析教师服务层实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeTeacherServiceImpl.java, v 1.0 2016年5月10日 下午5:28:31
 *          gengqianfeng Exp $
 */
@Service
@Transactional
public class EvlAnalyzeTeacherServiceImpl extends
    AbstractService<EvlAnalyzeTeacher, Integer> implements
    EvlAnalyzeTeacherService {

  @Autowired
  private EvlAnalyzeTeacherDao evlAnalyzeTeacherDao;
  @Autowired
  private EvlQuestionnairesService evlQuestionnairesService;
  @Autowired
  private EvlQuestionMemberService evlQuestionMemberService;
  @Autowired
  private EvlOperateResultService evlOperateResultService;
  @Autowired
  private UserService userService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private EvlMetaService evlMetaService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private SchClassUserService schClassUserService;
  @Autowired
  private EvlClassService evlClassService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private EvlIndicatorService evlIndicatorService;

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache questionCache;

  private Cache getQuestionCache() {
    if (this.cacheManager != null && questionCache == null) {
      questionCache = cacheManager.getCache("evl_result_cache");
    }
    return questionCache;
  }

  @Override
  public BaseDAO<EvlAnalyzeTeacher, Integer> getDAO() {
    return evlAnalyzeTeacherDao;
  }

  /**
   * 拼接去重年级，班级，学科ids
   * 
   * @param ids1
   *          旧ids
   * @param ids2
   *          新ids
   * @return
   */
  private String resetAnalyzeTeacherStringIds(String ids1, String ids2) {
    if (ids1 == null) {
      return ids2;
    }
    if (("," + ids1 + ",").indexOf("," + ids2 + ",") == -1) {
      return ids1 + "," + ids2;
    } else {
      return ids1;
    }
  }

  public List<EvlAnalyzeTeacher> filterAllTeacherList(EvlQuestionnaires eq,
      EvlAnalyzeTeacher tea) {
    EvlAnalyzeTeacher model = new EvlAnalyzeTeacher();
    model.setQuestionnairesId(eq.getQuestionnairesId());
    model.setOrgId(eq.getOrgId());
    List<EvlOperateResult> reList = this.getTeacherResultScoreByTeacherId(
        model, 0);
    List<EvlAnalyzeTeacher> teacherList = new ArrayList<>(reList.size());
    for (EvlOperateResult rs : reList) {
      model = new EvlAnalyzeTeacher();
      model.setQuestionnairesId(eq.getQuestionnairesId());
      model.setOrgId(eq.getOrgId());
      model.setTeacherId(rs.getStandby2());
      if (tea != null) {
        model.setGradeId(tea.getGradeId());
        model.setSubjectId(tea.getSubjectId());
      }
      model.setResultScore(rs.getResultScore());
      putTeacherInfo(model);
      if (model.getTeacherRole() == null) {
        continue;
      }
      teacherList.add(model);
    }

    return teacherList;
  }

  private void putTeacherInfo(EvlAnalyzeTeacher model) {
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(model.getOrgId());
    eat.setQuestionnairesId(model.getQuestionnairesId());
    eat.setGradeId(model.getGradeId());
    eat.setSubjectId(model.getSubjectId());
    eat.setTeacherId(model.getTeacherId());
    eat.addCustomCulomn("DISTINCT orgId,questionnairesId,subjectId,gradeId,classId,teacherName,teacherRole");
    List<EvlAnalyzeTeacher> teacherList = this.findAll(eat);
    for (EvlAnalyzeTeacher tch : teacherList) {
      if (tch != null) {
        model.setGradeId(this.resetAnalyzeTeacherStringIds(model.getGradeId(),
            tch.getGradeId()));
        model.setClassId(this.resetAnalyzeTeacherStringIds(model.getClassId(),
            tch.getClassId()));
        model.setSubjectId(this.resetAnalyzeTeacherStringIds(
            model.getSubjectId(), tch.getSubjectId()));
        model.setTeacherName(tch.getTeacherName());
        model.setTeacherRole(tch.getTeacherRole());
      }
    }

  }

  private List<EvlOperateResult> getAllResult(EvlAnalyzeTeacher question,
      String group) {
    EvlOperateResult paramRes = new EvlOperateResult();
    paramRes.setOrgId(question.getOrgId());
    paramRes.setQuestionnairesId(question.getQuestionnairesId());
    paramRes.addGroup(group);
    return evlOperateResultService.findAnalyzeResultScore(paramRes);// 所有打分
  }

  @SuppressWarnings("unchecked")
  private List<EvlOperateResult> getResultByType(EvlAnalyzeTeacher question,
      Integer type) {
    String cachekey = "evl_q_" + question.getQuestionnairesId() + "_fxbg"
        + type;
    if (getQuestionCache() != null) {
      ValueWrapper element = getQuestionCache().get(cachekey);
      if (element != null) {
        return (List<EvlOperateResult>) element.get();
      }
    }
    List<EvlOperateResult> allResult = null;
    switch (type) {
    case 0:// 教师
      allResult = getAllResult(question, "teacherId");// 所有学生操作数据
      break;
    case 1:// 年级
      allResult = getAllResult(question, "gradeId");// 所有学生操作数据
      break;
    case 2:// 班级
      allResult = getAllResult(question, "classId");// 所有学生操作数据
      break;
    case 3:// 学科
      allResult = getAllResult(question, "subjeId");// 所有学生操作数据
      break;
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put(cachekey, allResult);
    }
    return allResult;
  }

  /**
   * 教师总平均分
   * 
   * @param eat
   */
  @Override
  public List<EvlOperateResult> getTeacherResultScoreByTeacherId(
      EvlAnalyzeTeacher eat, int key) {
    List<EvlOperateResult> resultList = null;
    switch (key) {
    case 0:// 教师
    case 4:
      resultList = getResultByType(eat, key);
      break;
    case 1:// 年级
      resultList = getResultByType(eat, key);
      break;
    case 2:// 班级
      resultList = getResultByType(eat, key);
      break;
    case 3:// 学科
      resultList = getResultByType(eat, key);
      break;
    default:
      logger.error("统计分数类型不存在");
      break;
    }

    return resultList;
  }

  /**
   * 保存或修改分析教师数据
   * 
   * @param eq
   */
  @Override
  public void saveOrUpdateAnalyzeTeacher(EvlQuestionnaires eq) {
    List<EvlOperateResult> teacherList = evlOperateResultService
        .findAnalyzeTeacherList(eq);
    if (teacherList != null) {
      List<EvlAnalyzeTeacher> eats = new ArrayList<EvlAnalyzeTeacher>();
      Map<Integer, String> userRoleNameMap = new HashMap<Integer, String>();
      for (EvlOperateResult result : teacherList) {
        EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
        eat.setOrgId(eq.getOrgId());
        eat.setQuestionnairesId(eq.getQuestionnairesId());
        eat.setIndicatorId(result.getIndicatorId());

        Integer subjectId = result.getSubjeId();
        Integer gradeId = result.getGradeId();
        Integer classId = result.getClassId();
        Integer teacherId = result.getTeacherId();
        if (subjectId == null || gradeId == null || classId == null
            || teacherId == null) {
          continue;
        }

        eat.setSubjectId(subjectId.toString());
        eat.setGradeId(gradeId.toString());
        eat.setClassId(classId.toString());
        eat.setTeacherId(teacherId);
        EvlAnalyzeTeacher resultEat = this.findOne(eat);// 原有数据
        if (resultEat != null) {
          if (result.getResultScore() != null
              && !result.getResultScore().equals(resultEat.getResultScore())) {
            EvlAnalyzeTeacher newParam = new EvlAnalyzeTeacher();
            newParam.setResultScore(result.getResultScore());
            newParam.setId(resultEat.getId());
            this.update(newParam);// 清除脏数据
          }
        } else {
          eat.setResultScore(result.getResultScore());
          User user = userService.findOne(eat.getTeacherId());
          if (user == null) {
            continue;
          } else {
            eat.setSchoolAge(user.getSchoolAge());
            eat.setSex(user.getSex());
            eat.setTeacherName(user.getName());
            String spaces = userRoleNameMap.get(user.getId());
            if (spaces == null) {
              spaces = "";
              UserSpace us = new UserSpace();
              us.setUserId(user.getId());
              us.setSchoolYear(eq.getSchoolYear());

              List<UserSpace> spaceList = userSpaceService.findAll(us);
              for (UserSpace space : spaceList) {
                spaces += "、" + space.getSpaceName();
              }
              if (spaces.length() > 0) {
                spaces = spaces.substring(1);
              }
            }

            eat.setTeacherRole(spaces);
            eats.add(eat);
          }
        }
      }
      if (eats.size() > 0) {
        evlAnalyzeTeacherDao.batchInsert(eats);
      }
    } else {
      logger.error("评教完成用户状态未改变");
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<EvlAnalyzeTeacher> findAnalyzeTeacherListByQuestionnaire(
      EvlQuestionnaires eq, EvlAnalyzeTeacher tea) {
    String key = "ev_rs_wjjg_q" + eq.getQuestionnairesId();
    if (getQuestionCache() != null) {
      if (tea != null) {
        key += "_g" + tea.getGradeId() + "_s" + tea.getSubjectId();
      }
      ValueWrapper cacheElement = getQuestionCache().get(key);
      if (cacheElement != null) {
        List<EvlAnalyzeTeacher> teacherList = (List<EvlAnalyzeTeacher>) cacheElement
            .get();
        return copyTeacherList(teacherList);
      }
    }

    List<EvlAnalyzeTeacher> filterAllTeacherList = this.filterAllTeacherList(
        eq, tea);
    if (getQuestionCache() != null) {
      getQuestionCache().put(key, filterAllTeacherList);
    }
    return copyTeacherList(filterAllTeacherList);
  }

  /**
   * 
   * @return
   */
  private List<EvlAnalyzeTeacher> copyTeacherList(
      List<EvlAnalyzeTeacher> teachers) {
    List<EvlAnalyzeTeacher> newTeachers = new ArrayList<EvlAnalyzeTeacher>();
    for (EvlAnalyzeTeacher evlAnalyzeTeacher : teachers) {
      newTeachers.add(evlAnalyzeTeacher.clone());

    }
    return newTeachers;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findTeacherListOfGrade(Integer questionId,
      boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_njzfx_q" + questionId + "_d" + isDownload);
      if (cacheElement != null) {
        returnMap = (Map<String, Object>) cacheElement.get();
        return returnMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    if (eq != null) {
      EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
      eat.setOrgId(eq.getOrgId());
      eat.setQuestionnairesId(eq.getQuestionnairesId());
      eat.addCustomCulomn(" DISTINCT gradeId,teacherId,orgId,questionnairesId");
      eat.addOrder(" gradeId asc ");
      List<EvlAnalyzeTeacher> eatList = this.findAll(eat);

      Map<Integer, EvlOperateResult> teacherCountMap = new HashMap<Integer, EvlOperateResult>();
      Map<String, Integer> teacherSubjectCountMap = new HashMap<String, Integer>();
      // 同学科不同班级或年级求平均值
      for (EvlAnalyzeTeacher tea : eatList) {
        Integer subtechs = teacherSubjectCountMap.get(tea.getGradeId());
        if (subtechs == null) {
          teacherSubjectCountMap.put(tea.getGradeId(), 1);
        } else {
          teacherSubjectCountMap.put(tea.getGradeId(), ++subtechs);
        }

      }
      List<EvlOperateResult> teacherResult = this
          .getTeacherResultScoreByTeacherId(eat, 1);
      for (EvlOperateResult tea : teacherResult) {
        teacherCountMap.put(tea.getStandby2(), tea);
      }

      List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
      List<GradeAndClassVo> gradeList = this.getGradeVoList(eq);
      String[] gradeTitle = new String[gradeList.size()];
      Double[] gradeData = new Double[gradeList.size()];
      int index = 0;
      for (GradeAndClassVo metaVo : gradeList) {
        Map<String, String> gradeMap = new HashMap<String, String>();
        gradeMap.put("gradeId", metaVo.getId() + "");
        gradeMap.put("gradeName", metaVo.getName());
        double resultScore = 0.0;
        EvlOperateResult eat1 = teacherCountMap.get(metaVo.getId());
        if (eat1 != null) {
          Integer techs = teacherSubjectCountMap.get(String.valueOf(metaVo
              .getId()));
          gradeMap.put("studentCount", String.valueOf(techs));
          resultScore = eat1.getResultScore();
          if (techs != null && techs > 1) {
            resultScore = Double.valueOf(new DecimalFormat("0.00")
                .format(resultScore));
          }
        } else {
          gradeMap.put("studentCount", String.valueOf(0));
        }

        gradeMap.put("resultScore", resultScore + "");
        gradeTitle[index] = metaVo.getName();
        gradeData[index] = resultScore;
        returnList.add(gradeMap);
        index++;
      }
      returnMap.put("gradeList", returnList);
      if (!isDownload) {
        Option option = EchartUtil.packageBarOption("年级组平均分对比图", gradeTitle,
            "平均分", gradeData);
        returnMap.put("option", option);
      }
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_njzfx_q" + questionId + "_d" + isDownload,
          returnMap);
    }
    return returnMap;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findTeacherListOfSubject(Integer questionId,
      boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_xkzfx_q" + questionId + "_d" + isDownload);
      if (cacheElement != null) {
        returnMap = (Map<String, Object>) cacheElement.get();
        return returnMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    if (eq != null) {
      EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
      eat.setOrgId(eq.getOrgId());
      eat.setQuestionnairesId(eq.getQuestionnairesId());
      eat.addCustomCulomn("DISTINCT subjectId,teacherId,orgId,questionnairesId ");
      eat.addOrder(" subjectId asc ");
      List<EvlAnalyzeTeacher> eatList = this.findAll(eat);

      Map<Integer, EvlOperateResult> teacherCountMap = new HashMap<Integer, EvlOperateResult>();
      Map<String, Integer> teacherSubjectCountMap = new HashMap<String, Integer>();
      // 同学科不同班级或年级求平均值
      for (EvlAnalyzeTeacher tea : eatList) {
        Integer subtechs = teacherSubjectCountMap.get(tea.getSubjectId());
        if (subtechs == null) {
          teacherSubjectCountMap.put(tea.getSubjectId(), 1);
        } else {
          teacherSubjectCountMap.put(tea.getSubjectId(), ++subtechs);
        }
      }
      List<EvlOperateResult> teacherResult = this
          .getTeacherResultScoreByTeacherId(eat, 3);
      for (EvlOperateResult tea : teacherResult) {
        teacherCountMap.put(tea.getStandby2(), tea);
      }
      List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
      List<SubjectVo> subjectList = this.getSubjectVoList(eq);
      String[] subjectTitle = new String[subjectList.size()];
      Double[] subjectData = new Double[subjectList.size()];
      int index = 0;
      for (SubjectVo metaVo : subjectList) {
        Map<String, String> subjectMap = new HashMap<String, String>();
        subjectMap.put("subjectId", metaVo.getId() + "");
        subjectMap.put("subjectName", metaVo.getName());
        double resultScore = 0.0;
        EvlOperateResult eat1 = teacherCountMap.get(metaVo.getId());
        if (eat1 != null) {
          Integer techs = teacherSubjectCountMap.get(String.valueOf(metaVo
              .getId()));
          subjectMap.put("studentCount", String.valueOf(techs));
          resultScore = eat1.getResultScore();
          if (techs != null && techs > 1) {
            resultScore = Double.valueOf(new DecimalFormat("0.00")
                .format(resultScore));
          }
        } else {
          subjectMap.put("studentCount", String.valueOf(0));
        }

        subjectMap.put("resultScore", resultScore + "");
        subjectTitle[index] = metaVo.getName();
        subjectData[index] = resultScore;
        returnList.add(subjectMap);
        index++;
      }

      returnMap.put("subjectList", returnList);
      if (!isDownload) {
        Option option = EchartUtil.packageBarOption("学科组平均分对比图", subjectTitle,
            "平均分", subjectData);
        option.getGrid().y2(110);
        returnMap.put("titleSize", subjectTitle.length);
        returnMap.put("option", option);
      }
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_xkzfx_q" + questionId + "_d" + isDownload,
          returnMap);
    }
    return returnMap;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findTeacherListBySchoolAgeSex(Integer questionId,
      boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_jldfx_q" + questionId + "_d" + isDownload);
      if (cacheElement != null) {
        returnMap = (Map<String, Object>) cacheElement.get();
        return returnMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    if (eq != null) {
      EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
      eat.setOrgId(eq.getOrgId());
      eat.setQuestionnairesId(eq.getQuestionnairesId());
      eat.addOrder(" schoolAge asc ");
      eat.addCustomCulomn("distinct schoolAge,teacherId");
      List<EvlAnalyzeTeacher> findAll = this.findAll(eat);
      Map<Integer, EvlAnalyzeTeacher> teaMap = new HashMap<Integer, EvlAnalyzeTeacher>();
      for (EvlAnalyzeTeacher teacher : findAll) {
        teaMap.put(teacher.getTeacherId(), teacher);
      }
      List<EvlOperateResult> eatList = this.getTeacherResultScoreByTeacherId(
          eat, 0);

      String[] schoolTitle = EvlAnalyzeTeacher.SCHOOLAGE_SECTION;
      int[] studentCount = new int[schoolTitle.length];
      double[] schoolScore = new double[schoolTitle.length];
      for (EvlOperateResult or : eatList) {
        EvlAnalyzeTeacher eat1 = teaMap.get(or.getStandby2());
        if (eat1 == null) {
          continue;
        }
        eat1.setResultScore(or.getResultScore());
        if (eat1.getSchoolAge() == null || eat1.getSchoolAge() == 0) {
          continue;
        }
        if (eat1.getSchoolAge() > 0 && eat1.getSchoolAge() <= 5) {
          studentCount[0] += 1;
          schoolScore[0] += eat1.getResultScore();
        } else if (eat1.getSchoolAge() >= 6 && eat1.getSchoolAge() <= 10) {
          studentCount[1] += 1;
          schoolScore[1] += eat1.getResultScore();
        } else if (eat1.getSchoolAge() >= 11 && eat1.getSchoolAge() <= 15) {
          studentCount[2] += 1;
          schoolScore[2] += eat1.getResultScore();
        } else if (eat1.getSchoolAge() >= 16 && eat1.getSchoolAge() <= 20) {
          studentCount[3] += 1;
          schoolScore[3] += eat1.getResultScore();
        } else if (eat1.getSchoolAge() >= 21) {
          studentCount[4] += 1;
          schoolScore[4] += eat1.getResultScore();
        } else {
          // 教龄值异常
        }
      }
      List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
      Double[] schoolData = new Double[schoolTitle.length];
      for (int i = 0; i < schoolTitle.length; i++) {
        Map<String, String> schoolSexMap = new HashMap<String, String>();
        schoolSexMap.put("schoolAgeSex", schoolTitle[i]);
        schoolSexMap.put("studentCount", studentCount[i] + "");
        if (studentCount[i] == 0 || schoolScore[i] == 0) {
          schoolSexMap.put("resultScore", "0.0");
        } else {
          schoolSexMap.put("resultScore", new DecimalFormat("0.00")
              .format(schoolScore[i] / studentCount[i]));
        }
        returnList.add(schoolSexMap);
        schoolData[i] = Double.valueOf(schoolSexMap.get("resultScore"));
      }
      returnMap.put("returnList", returnList);
      if (!isDownload) {
        Option option = EchartUtil.packageBarOption("教龄段平均分对比图", schoolTitle,
            "平均分", schoolData);
        returnMap.put("option", option);
      }
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_jldfx_q" + questionId + "_d" + isDownload,
          returnMap);
    }
    return returnMap;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findDirectorTeacherList(Integer questionId,
      Integer gradeId, boolean isDownload) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_bzrfx_q" + questionId + "_g" + gradeId);
      if (cacheElement != null) {
        returnMap = (Map<String, Object>) cacheElement.get();
        return returnMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    boolean flag = false;
    if (gradeId != null && gradeId > 0) {
      flag = true;
    }

    List<EvlOperateResult> teacherResults = this
        .getTeacherResultScoreByTeacherId(eat, 2);
    List<GradeAndClassVo> gradeList = this.getGradeVoList(eq);
    Map<Integer, GradeAndClassVo> gradeMap = new HashMap<Integer, GradeAndClassVo>();
    for (GradeAndClassVo gradeAndClassVo : gradeList) {
      if (gradeAndClassVo.getId() != null) {
        gradeMap.put(gradeAndClassVo.getId(), gradeAndClassVo);
      }
    }
    List<EvlAnalyzeTeacher> teacherList = new ArrayList<EvlAnalyzeTeacher>();

    EvlOperateResult front = null;
    int sort = 1;
    if (!flag) {// 年级
      // 设置排序字段
      for (EvlOperateResult or : teacherResults) {
        EvlAnalyzeTeacher model = new EvlAnalyzeTeacher();
        model.setResultScore(or.getResultScore());
        // 设置年级班级和班主任名称
        SchClass sc = schClassService.findOne(or.getStandby2());
        GradeAndClassVo gradeAndClassVo = gradeMap.get(sc.getGradeId());
        model.setGradeId(String.valueOf(sc.getGradeId()));
        model.setFlago(gradeAndClassVo.getName() + "（" + sc.getName() + "）");
        SchClassUser scUser = new SchClassUser();
        scUser.setSchoolYear(eq.getSchoolYear());
        scUser.setClassId(or.getStandby2());
        scUser.setType(SchClassUser.T_MASTER);
        SchClassUser scu = schClassUserService.findOne(scUser);
        if (scu != null) {// 班主任名称
          model.setTeacherId(scu.getTchId());
          model.setTeacherName(scu.getUsername());
          model.setFlags(StringUtils.abbr(scu.getUsername(), 12, true, false));
        } else {
          model.setTeacherId(null);
          model.setTeacherName("");
          model.setFlags("");
        }
        if (front != null) {
          if (!front.getResultScore().equals(or.getResultScore())) {
            sort++;
          }
        }
        model.setSort(sort);
        front = or;
        teacherList.add(model);
      }
      returnMap.put("teacherList", teacherList);
      // 分数段统计
      String[] scoreSection = EvlAnalyzeTeacher.SCORESECTION;
      Integer[] peopleSection = new Integer[scoreSection.length];
      for (int i = 0; i < peopleSection.length; i++) {
        peopleSection[i] = 0;
      }
      for (int i = 0; i < teacherList.size(); i++) {
        EvlAnalyzeTeacher eat1 = teacherList.get(i);
        evlOperateResultService.statisticsSection(peopleSection,
            eat1.getResultScore());
      }
      List<Map<String, String>> sectionList = new ArrayList<Map<String, String>>();
      for (int i = 0; i < scoreSection.length; i++) {
        Map<String, String> sectionMap = new HashMap<String, String>();
        sectionMap.put("sectionName", scoreSection[i]);
        if (peopleSection[i] == null) {
          peopleSection[i] = 0;
        }
        sectionMap.put("sectionCount", peopleSection[i].toString());
        if (CollectionUtils.isEmpty(teacherList)) {
          sectionMap.put("sectionPercent", "0.0");
        } else {
          sectionMap.put(
              "sectionPercent",
              Double.valueOf(new DecimalFormat("0.00").format((peopleSection[i]
                  * 1.00 / teacherList.size() * 100)))
                  + "");
        }
        sectionList.add(sectionMap);
      }
      returnMap.put("sectionList", sectionList);
      if (!isDownload) {
        Map<String, Double> resultMap = new HashMap<String, Double>();
        Map<String, Integer> gradeCountMap = new HashMap<String, Integer>();
        for (EvlAnalyzeTeacher eat1 : teacherList) {
          Double evlAnalyzeTeacher = resultMap.get(eat1.getGradeId());
          if (evlAnalyzeTeacher == null) {
            gradeCountMap.put(eat1.getGradeId(), 1);
            resultMap.put(eat1.getGradeId(), eat1.getResultScore());
          } else {
            resultMap.put(eat1.getGradeId(),
                evlAnalyzeTeacher + eat1.getResultScore());
            gradeCountMap.put(eat1.getGradeId(),
                gradeCountMap.get(eat1.getGradeId()) + 1);
          }
        }
        // 年级分组统计柱状图
        String[] gradeTitle = new String[gradeList.size()];
        Double[] scoreData = new Double[gradeList.size()];
        for (int i = 0; i < gradeList.size(); i++) {
          GradeAndClassVo grade = gradeList.get(i);
          gradeTitle[i] = grade.getName();
          Double evlAnalyzeTeacher = resultMap.get(grade.getId().toString()) == null ? 0d
              : resultMap.get(grade.getId().toString());
          scoreData[i] = evlAnalyzeTeacher;
          Integer num = gradeCountMap.get(grade.getId().toString()) == null ? 0
              : gradeCountMap.get(grade.getId().toString());
          if (num != 0 && scoreData[i] != null) {
            scoreData[i] = Double.valueOf(new DecimalFormat("0.00")
                .format(scoreData[i] / num));
          } else {
            scoreData[i] = 0.0;
          }
        }
        returnMap.put("option", EchartUtil.packageBarOption("年级班主任平均分、分数段对比图表",
            gradeTitle, "平均分", scoreData));
      }
    } else {// 班级
      // 设置排序字段
      String gradeName = "";
      for (GradeAndClassVo gradeAndClassVo2 : gradeList) {
        if (gradeAndClassVo2.getId().equals(gradeId)) {
          gradeName = gradeAndClassVo2.getName();
          break;
        }
      }
      for (EvlOperateResult or : teacherResults) {
        EvlAnalyzeTeacher model = new EvlAnalyzeTeacher();
        model.setResultScore(or.getResultScore());
        model.setClassId(String.valueOf(or.getStandby2()));
        // 设置年级班级和班主任名称
        SchClass sc = schClassService.findOne(or.getStandby2());
        if (sc == null || sc.getGradeId() == null
            || !sc.getGradeId().equals(gradeId)) {
          continue;
        }
        model.setFlago(gradeName + "（" + sc.getName() + "）");
        model.setGradeId(String.valueOf(sc.getGradeId()));
        SchClassUser scUser = new SchClassUser();
        scUser.setSchoolYear(eq.getSchoolYear());
        scUser.setClassId(or.getStandby2());
        scUser.setType(SchClassUser.T_MASTER);
        SchClassUser scu = schClassUserService.findOne(scUser);
        if (scu == null) {// 班主任未找到
          model.setTeacherId(null);
          model.setTeacherName("");
          model.setFlags("");
        } else {
          model.setTeacherId(scu.getTchId());
          model.setTeacherName(scu.getUsername());
          model.setFlags(StringUtils.abbr(scu.getUsername(), 12, true, false));
        }
        if (front != null) {
          if (!front.getResultScore().equals(or.getResultScore())) {
            sort++;
          }
        }
        model.setSort(sort);
        front = or;
        teacherList.add(model);
      }
      returnMap.put("gradeName", gradeName);
      returnMap.put("teacherList", teacherList);
      if (!isDownload) {
        // 班级分组统计柱状图
        SchClass sc = new SchClass();
        sc.setGradeId(gradeId);
        sc.setOrgId(eq.getOrgId());
        sc.setSchoolYear(eq.getSchoolYear());
        List<SchClass> classList = schClassService.findAll(sc);
        String[] classTitle = new String[classList.size()];
        Double[] scoreData = new Double[classList.size()];
        Map<String, Double> resultMap = new HashMap<String, Double>();
        for (int j = 0; j < teacherList.size(); j++) {
          EvlAnalyzeTeacher eat1 = teacherList.get(j);
          if (gradeId.toString().equals(eat1.getGradeId())) {
            resultMap.put(eat1.getClassId(), eat1.getResultScore());
          }
        }
        for (int i = 0; i < classList.size(); i++) {
          SchClass sclass = classList.get(i);
          classTitle[i] = StringUtils.abbr(sclass.getName(), 10, false, false);
          scoreData[i] = resultMap.get(sclass.getId().toString()) == null ? 0d
              : resultMap.get(sclass.getId().toString());
        }
        Option option = EchartUtil.packageBarOption(gradeName + "班主任总分对比图",
            classTitle, "总分", scoreData);
        option.grid().y2(110);
        returnMap.put("titleSize", classTitle.length);
        returnMap.put("option", option);
      }
    }
    if (getQuestionCache() != null) {
      getQuestionCache().put("ev_rs_bzrfx_q" + questionId + "_g" + gradeId,
          returnMap);
    }
    return returnMap;
  }

  @Override
  public Map<String, Object> findAnalyzeProblemsList(Integer questionId) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
    double allScore = this.getAllTeacherScore(eq);// 获取教师总平均分
    // 查询年级
    List<GradeAndClassVo> gradeList = this.getGradeVoList(eq);
    List<SubjectVo> subjectList = this.getSubjectVoList(eq);
    List<Map<String, String>> problemGrade = this.getProblemGradeList(eq);
    List<Map<String, String>> problemSubject = this.getProblemSubjectList(eq);
    List<Map<String, String>> problemDirector = this.getProblemDirectorList(eq);
    List<Map<String, String>> problemSchoolAge = this.getProblemSchoolAgeList(
        eq, allScore);
    List<EvlAnalyzeTeacher> problemTeacherMember = this
        .getProblemTeacherMemberList(eq, allScore, gradeList, subjectList);
    List<Map<String, String>> problemDirectorMember = this
        .getProblemDirectorMemberList(eq, gradeList, subjectList);
    // 问卷问题总结
    returnMap.put("scores", eq.getTotleScoreInt() + "," + allScore + ","
        + allScore);
    returnMap.put("problemGrade", problemGrade);
    returnMap.put("problemSubject", problemSubject);
    returnMap.put("problemDirector", problemDirector);
    returnMap.put("problemSchoolAge", problemSchoolAge);
    returnMap.put("problemDirectorMember", problemDirectorMember);
    returnMap.put("problemTeacherMember", problemTeacherMember);
    returnMap.put("isNull", false);
    if (CollectionUtils.isEmpty(problemGrade)
        && CollectionUtils.isEmpty(problemSubject)
        && CollectionUtils.isEmpty(problemDirector)
        && CollectionUtils.isEmpty(problemSchoolAge)
        && CollectionUtils.isEmpty(problemDirectorMember)
        && CollectionUtils.isEmpty(problemTeacherMember)) {
      returnMap.put("isNull", true);
    }
    return returnMap;
  }

  /**
   * 获取年级问题列表
   * 
   * @param problemGrade
   * @param eq
   * @param allScore
   */
  private List<Map<String, String>> getProblemGradeList(EvlQuestionnaires eq) {
    Double allScore = 0.0;
    List<Map<String, String>> problemGrade = new ArrayList<Map<String, String>>();
    List<GradeAndClassVo> gradeList = this.getGradeVoList(eq);
    Map<String, String> metaMap = new HashMap<String, String>();
    for (GradeAndClassVo gradeAndClassVo : gradeList) {
      metaMap
          .put(gradeAndClassVo.getId().toString(), gradeAndClassVo.getName());
    }
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    List<EvlOperateResult> eatList = this.getTeacherResultScoreByTeacherId(eat,
        1);
    for (EvlOperateResult result : eatList) {
      allScore += result.getResultScore();
    }
    allScore = allScore / eatList.size();
    for (EvlOperateResult result : eatList) {
      Double gradeScore = result.getResultScore();
      if (gradeScore < allScore && eq.getTotleScore() != null) {
        Map<String, String> problemGradeMap = new HashMap<String, String>();
        problemGradeMap.put("gradeId", result.getStandby2().toString());
        problemGradeMap.put("gradeName", "");
        problemGradeMap.put("gradeScore", gradeScore.toString());
        problemGradeMap.put("teacherDiff",
            new DecimalFormat("0.00").format(allScore - gradeScore));
        problemGradeMap.put("allDiff",
            new DecimalFormat("0.00").format(eq.getTotleScore() - gradeScore));
        problemGradeMap.put("gradeName",
            metaMap.get(result.getStandby2().toString()));
        problemGrade.add(problemGradeMap);
      }
    }
    return problemGrade;
  }

  /**
   * 获取学科问题列表
   * 
   * @param problemSubject
   * @param eq
   * @param allScore
   */
  private List<Map<String, String>> getProblemSubjectList(EvlQuestionnaires eq) {
    Double allScore = 0.0;
    List<Map<String, String>> problemSubject = new ArrayList<Map<String, String>>();
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    List<SubjectVo> subjectList = this.getSubjectVoList(eq);
    List<EvlOperateResult> eatList = this.getTeacherResultScoreByTeacherId(eat,
        3);
    for (EvlOperateResult result : eatList) {
      allScore += result.getResultScore();
    }
    allScore = allScore / eatList.size();
    for (EvlOperateResult result : eatList) {
      Double subjectScore = result.getResultScore();
      if (subjectScore < allScore && eq.getTotleScore() != null) {
        Map<String, String> problemSubjectMap = new HashMap<String, String>();
        problemSubjectMap.put("subjectId", result.getStandby2().toString());
        problemSubjectMap.put("subjectName", "");
        problemSubjectMap.put("subjectScore", subjectScore.toString());
        problemSubjectMap.put("teacherDiff",
            new DecimalFormat("0.00").format(allScore - subjectScore));
        problemSubjectMap
            .put(
                "allDiff",
                new DecimalFormat("0.00").format(eq.getTotleScore()
                    - subjectScore));
        for (SubjectVo subjectVo : subjectList) {
          if (subjectVo.getId().equals(result.getStandby2())) {
            problemSubjectMap.put("subjectName", subjectVo.getName());
            break;
          }
        }
        problemSubject.add(problemSubjectMap);
      }
    }
    return problemSubject;
  }

  /**
   * 获取班主任问题列表
   * 
   * @param problemSubject
   * @param eq
   * @param allScore
   */
  private List<Map<String, String>> getProblemDirectorList(EvlQuestionnaires eq) {
    Double allScore = 0.0;
    List<Map<String, String>> problemDirector = new ArrayList<Map<String, String>>();
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    List<EvlOperateResult> teacherList = this.getTeacherResultScoreByTeacherId(
        eat, 2);
    Organization org = organizationService.findOne(eq.getOrgId());
    List<GradeAndClassVo> gradeList = null;
    if (org == null) {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId());
    } else {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId(),
          org.getSchoolings());
    }
    Map<String, String> gradeMap = new HashMap<String, String>();
    for (GradeAndClassVo gradeAndClassVo : gradeList) {
      gradeMap.put(gradeAndClassVo.getId().toString(),
          gradeAndClassVo.getName());
    }

    for (EvlOperateResult result : teacherList) {
      allScore += result.getResultScore();
    }
    allScore = allScore / teacherList.size();
    for (EvlOperateResult result : teacherList) {
      Double gradeScore = result.getResultScore();
      if (gradeScore < allScore && eq.getTotleScore() != null) {
        Map<String, String> problemDirectorMap = new HashMap<String, String>();
        problemDirectorMap.put("gradeId", result.getStandby2().toString());
        problemDirectorMap.put("gradeScore", gradeScore.toString());
        problemDirectorMap.put("directorDiff",
            new DecimalFormat("0.00").format(allScore - gradeScore));
        problemDirectorMap.put("allDiff",
            new DecimalFormat("0.00").format(eq.getTotleScore() - gradeScore));
        SchClass sc = schClassService.findOne(result.getStandby2());
        problemDirectorMap
            .put("gradeName", gradeMap.get(sc.getGradeId().toString()) + "("
                + sc.getName() + ")");
        problemDirector.add(problemDirectorMap);
      }
    }
    return problemDirector;
  }

  /**
   * 获取教龄问题列表
   * 
   * @param problemSubject
   * @param eq
   * @param allScore
   */
  private List<Map<String, String>> getProblemSchoolAgeList(
      EvlQuestionnaires eq, Double allScore) {
    List<Map<String, String>> problemSchoolAge = new ArrayList<Map<String, String>>();
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    eat.addOrder(" schoolAge asc ");
    eat.addCustomCulomn("distinct schoolAge,teacherId");
    List<EvlAnalyzeTeacher> findAll = this.findAll(eat);
    Map<Integer, EvlAnalyzeTeacher> teaMap = new HashMap<Integer, EvlAnalyzeTeacher>();
    for (EvlAnalyzeTeacher teacher : findAll) {
      teaMap.put(teacher.getTeacherId(), teacher);
    }
    List<EvlOperateResult> eatList = this.getTeacherResultScoreByTeacherId(eat,
        0);

    String[] schoolTitle = EvlAnalyzeTeacher.SCHOOLAGE_SECTION;
    int[] studentCount = new int[schoolTitle.length];
    double[] schoolScore = new double[schoolTitle.length];
    for (EvlOperateResult or : eatList) {
      EvlAnalyzeTeacher eat1 = teaMap.get(or.getStandby2());
      if (eat1 == null) {
        continue;
      }
      eat1.setResultScore(or.getResultScore());
      if (eat1.getSchoolAge() == null || eat1.getSchoolAge() == 0) {
        continue;
      }
      if (eat1.getSchoolAge() > 0 && eat1.getSchoolAge() <= 5) {
        studentCount[0] += 1;
        schoolScore[0] += eat1.getResultScore();
      } else if (eat1.getSchoolAge() >= 6 && eat1.getSchoolAge() <= 10) {
        studentCount[1] += 1;
        schoolScore[1] += eat1.getResultScore();
      } else if (eat1.getSchoolAge() >= 11 && eat1.getSchoolAge() <= 15) {
        studentCount[2] += 1;
        schoolScore[2] += eat1.getResultScore();
      } else if (eat1.getSchoolAge() >= 16 && eat1.getSchoolAge() <= 20) {
        studentCount[3] += 1;
        schoolScore[3] += eat1.getResultScore();
      } else if (eat1.getSchoolAge() >= 21) {
        studentCount[4] += 1;
        schoolScore[4] += eat1.getResultScore();
      } else {
        // 教龄值异常
      }
    }
    for (int i = 0; i < schoolTitle.length; i++) {
      if (schoolScore[i] > 0) {
        schoolScore[i] = Double.valueOf(new DecimalFormat("0.00")
            .format(schoolScore[i] / studentCount[i]));
        if (schoolScore[i] < allScore && eq.getTotleScore() != null) {
          Map<String, String> problemSchoolMap = new HashMap<String, String>();
          problemSchoolMap.put("schoolTitle", schoolTitle[i]);
          problemSchoolMap.put("schoolScore", schoolScore[i] + "");
          problemSchoolMap.put("teacherDiff",
              new DecimalFormat("0.00").format(allScore - schoolScore[i]));
          problemSchoolMap.put(
              "allDiff",
              new DecimalFormat("0.00").format(eq.getTotleScore()
                  - schoolScore[i]));
          problemSchoolAge.add(problemSchoolMap);
        }
      }
    }
    return problemSchoolAge;
  }

  /**
   * 获取班主任成员问题列表
   * 
   * @param problemDirectorMember
   * @param eq
   * @param allScore
   */
  private List<Map<String, String>> getProblemDirectorMemberList(
      EvlQuestionnaires eq, List<GradeAndClassVo> gradeList,
      List<SubjectVo> subjectList) {
    List<Map<String, String>> problemDirectorMember = new ArrayList<Map<String, String>>();
    EvlAnalyzeTeacher eat = new EvlAnalyzeTeacher();
    eat.setOrgId(eq.getOrgId());
    eat.setQuestionnairesId(eq.getQuestionnairesId());
    List<EvlOperateResult> teachers = this.getTeacherResultScoreByTeacherId(
        eat, 2);
    Double allScore = 0.0;
    for (EvlOperateResult result : teachers) {
      allScore += result.getResultScore();
    }
    allScore = allScore / teachers.size();
    for (EvlOperateResult teacher : teachers) {
      Double directorScore = teacher.getResultScore();
      if (directorScore < allScore && eq.getTotleScore() != null) {
        Map<String, String> problemDirectorMap = new HashMap<String, String>();
        SchClass sc = schClassService.findOne(teacher.getStandby2());
        Map<String, String> memberInfo = this.getAnalyzeMemberInfo(
            sc.getGradeId(), teacher.getStandby2(), null, gradeList,
            subjectList);
        problemDirectorMap.put("gradeId", sc.getGradeId().toString());
        problemDirectorMap.put("gradeName", memberInfo.get("gradeName"));
        problemDirectorMap.put("className", memberInfo.get("className"));
        problemDirectorMap.put("classSort", memberInfo.get("classSort"));
        SchClassUser scUser = new SchClassUser();
        scUser.setSchoolYear(eq.getSchoolYear());
        scUser.setClassId(teacher.getStandby2());
        scUser.setType(SchClassUser.T_MASTER);// 班主任
        SchClassUser scu = schClassUserService.findOne(scUser);
        if (scu != null) {
          problemDirectorMap.put("directorId", scu.getTchId() + "");
          problemDirectorMap.put("directorName", scu.getUsername());
          problemDirectorMap.put("directorScore", directorScore.toString());
          problemDirectorMember.add(problemDirectorMap);
        } else {
          logger.info("班级id：" + teacher.getStandby2() + "未设置对应的班主任");
        }
      }
    }

    int number = (int) Math.round(problemDirectorMember.size() / 10.0);// 默认显示后10%人员
    if (number > 0) {
      Collections.sort(problemDirectorMember,
          new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> arg0,
                Map<String, String> arg1) {
              double directorScore0 = Double.parseDouble(arg0
                  .get("directorScore"));
              double directorScore1 = Double.parseDouble(arg1
                  .get("directorScore"));
              if (directorScore0 > directorScore1) {
                return -1;
              } else if (directorScore0 == directorScore1) {
                return 0;
              } else {
                return 1;
              }
            }
          });
      problemDirectorMember.subList(0, problemDirectorMember.size() - number)
          .clear();
      // 排序分组
      Collections.sort(problemDirectorMember,
          new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> arg0,
                Map<String, String> arg1) {
              int gradeId0 = Integer.parseInt(arg0.get("gradeId"));
              int gradeId1 = Integer.parseInt(arg1.get("gradeId"));
              if (gradeId0 > gradeId1) {
                return 1;
              } else if (gradeId0 == gradeId1) {
                int classSort0 = Integer.parseInt(arg0.get("classSort"));
                int classSort1 = Integer.parseInt(arg1.get("classSort"));
                if (classSort0 > classSort1) {
                  return 1;
                } else if (classSort0 == classSort1) {
                  return 0;
                } else {
                  return -1;
                }
              } else {
                return -1;
              }
            }
          });
    } else {
      problemDirectorMember.clear();
    }
    return problemDirectorMember;
  }

  /**
   * 获取教师成员问题列表
   * 
   * @param problemTeacherMember
   * @param eq
   * @param allScore
   */
  private List<EvlAnalyzeTeacher> getProblemTeacherMemberList(
      EvlQuestionnaires eq, Double allScore, List<GradeAndClassVo> gradeList,
      List<SubjectVo> subjectList) {
    List<EvlAnalyzeTeacher> allTeach = new ArrayList<EvlAnalyzeTeacher>();
    List<EvlAnalyzeTeacher> eatList = this
        .findAnalyzeTeacherListByQuestionnaire(eq, null);
    int number = eatList.size() - (int) Math.round(eatList.size() / 10.0);// 默认显示后10%人员
    int index = 0;
    for (EvlAnalyzeTeacher evlAnalyzeTeacher : eatList) {
      Double teacherScore = evlAnalyzeTeacher.getResultScore();
      if (teacherScore < allScore && eq.getTotleScore() != null
          && index > number) {
        allTeach.add(evlAnalyzeTeacher);
      }
      index++;
    }
    return allTeach;
  }

  /**
   * 获取统计分析问题用户详情提示信息
   * 
   * @param gradeId
   * @param classId
   * @param subjectId
   * @param gradeList
   * @param subjectList
   * @return
   */
  private Map<String, String> getAnalyzeMemberInfo(Integer gradeId,
      Integer classId, Integer subjectId, List<GradeAndClassVo> gradeList,
      List<SubjectVo> subjectList) {
    Map<String, String> returnMap = new HashMap<String, String>();
    String gradeName = "";
    String className = "";
    Integer classSort = 0;
    String subjectName = "";
    for (GradeAndClassVo gradeAndClassVo : gradeList) {
      if (gradeAndClassVo.getId().equals(gradeId)) {
        gradeName = gradeAndClassVo.getName();
        break;
      }
    }
    // 查询班级
    SchClass sc = schClassService.findOne(classId);
    if (sc != null) {
      className = sc.getName();
      classSort = sc.getSort();
    }
    if (subjectId != null) {
      // 查询学科
      for (SubjectVo subjectVo : subjectList) {
        if (subjectVo.getId().equals(subjectId)) {
          subjectName = subjectVo.getName();
          break;
        }
      }
    }
    returnMap.put("gradeName", gradeName);
    returnMap.put("className", className);
    returnMap.put("classSort", classSort.toString());
    returnMap.put("subjectName", subjectName);
    return returnMap;
  }

  /**
   * 所有教师总平均分
   * 
   * @param allScore
   * @param eq
   */
  private double getAllTeacherScore(EvlQuestionnaires eq) {
    Double allScore = 0d;
    List<EvlAnalyzeTeacher> allAnalyTeach = this
        .findAnalyzeTeacherListByQuestionnaire(eq, null);
    for (EvlAnalyzeTeacher evlAnalyzeTeacher : allAnalyTeach) {
      if (evlAnalyzeTeacher.getResultScore() != null)
        allScore += evlAnalyzeTeacher.getResultScore();
    }
    return allAnalyTeach.size() > 0 ? allScore / allAnalyTeach.size() : 0.0;
  }

  @Override
  public void downLoadAnalyzeInfo(HttpServletResponse response,
      EvlAnalyzeTeacher eat, Map<String, Object> root) {
    // word文档专用显示图片
    root.put("imageCodeKeys", eat.getFlago());
    FreemarkerUtils.createWord(response, "statisticAnalyze.ftl", root);
  }

  /**
   * 获取年级列表
   * 
   * @return
   */
  @Override
  public List<GradeAndClassVo> getGradeVoList(EvlQuestionnaires question) {
    Organization org = organizationService.findOne(question.getOrgId());
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(question
        .getQuestionnairesId());
    List<GradeAndClassVo> allList = null;
    if (org == null) {
      allList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId());
    } else {
      allList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId(),
          org.getSchoolings());
    }
    if (question.getStudentType().intValue() == EvlQuestionnaires.student_type_quantixusheng) {
      return allList;
    } else {
      List<GradeAndClassVo> realList = new ArrayList<GradeAndClassVo>();
      EvlClass params = new EvlClass();
      params.setOrgId(question.getOrgId());
      params.setQuestionnairesId(question.getQuestionnairesId());
      params.addOrder(" gradeId asc ");
      params.addCustomCulomn(" distinct(gradeId) ");
      List<EvlClass> gradeList = evlClassService.findAll(params);
      Set<Integer> gradeIdSet = new HashSet<Integer>();
      for (EvlClass evlClass : gradeList) {
        if (evlClass.getGradeId() != null) {
          gradeIdSet.add(evlClass.getGradeId());
        }
      }
      for (GradeAndClassVo gradeAndClassVo : allList) {
        if (gradeIdSet.contains(gradeAndClassVo.getId())) {
          realList.add(gradeAndClassVo);
        }
      }
      return realList;
    }
  }

  /**
   * 获取学科列表
   * 
   * @return
   */
  @Override
  public List<SubjectVo> getSubjectVoList(EvlQuestionnaires question) {
    List<SubjectVo> allList = evlMetaService.findSubjectByPhaseId(
        question.getPhaseId(), question.getOrgId());
    String subjectMes = question.getSubjectIds();
    if (StringUtils.isEmpty(subjectMes)) {
      return allList;
    } else {
      List<SubjectVo> realList = new ArrayList<SubjectVo>();
      String[] subArr = subjectMes.split(",");
      for (int i = 0; i < subArr.length; i++) {
        for (SubjectVo subjectVo : allList) {
          if (Integer.parseInt(subArr[i]) == subjectVo.getId().intValue()) {
            realList.add(subjectVo);
            break;
          }
        }
      }
      return realList;
    }
  }

  @Override
  public List<EvlAnalyzeIndicatorVo> findResultUserIndicator(
      EvlQuestionnaires eq, Integer teacherId) {
    List<EvlAnalyzeIndicatorVo> eaiVoList = new ArrayList<EvlAnalyzeIndicatorVo>();
    this.loadAnalyzeIndicatorVo(eq, eaiVoList, "average", teacherId);
    return eaiVoList;
  }

  @Override
  public List<EvlAnalyzeIndicatorVo> findResultUserIndicatorInfo(
      EvlQuestionnaires eq, EvlAnalyzeTeacher eat) {
    List<EvlAnalyzeIndicatorVo> eaiVoList = new ArrayList<EvlAnalyzeIndicatorVo>();
    this.loadAnalyzeIndicatorVo(eq, eaiVoList, "student", eat.getTeacherId());
    return eaiVoList;
  }

  /**
   * 分析指标
   * 
   * @param eaiVo
   * @param teacherList
   * @param evlIndicator
   * @param eq
   * @param count
   */
  public void loadAnalyzeIndicator(EvlAnalyzeIndicatorVo eaiVo,
      EvlIndicator evlIndicator, EvlQuestionnaires eq, String type,
      Integer teacherId) {
    List<EvlQuestionMember> memberList = evlQuestionMemberService
        .getStudentList(eq.getQuestionnairesId(),
            EvlMemberStatus.tijiaowenjuan.getValue());
    // 获取分析指标
    EvlAnalyzeIndicator eai = new EvlAnalyzeIndicator();
    eai.setOrgId(eq.getOrgId());
    eai.setQuestionnairesId(eq.getQuestionnairesId());
    eai.setIndicatorId(evlIndicator.getId());
    if ("average".equals(type)) {
      EvlAnalyzeTeacher analyTea = new EvlAnalyzeTeacher();
      analyTea.setIndicatorId(evlIndicator.getId());
      analyTea.setQuestionnairesId(evlIndicator.getQuestionnairesId());
      analyTea.setTeacherId(teacherId);
      List<EvlAnalyzeTeacher> allAnaly = this.findAll(analyTea);
      Double totalScore = 0.0;
      for (EvlAnalyzeTeacher evlAnalyzeTeacher : allAnaly) {
        totalScore += (evlAnalyzeTeacher.getResultScore() == null ? 0.0
            : evlAnalyzeTeacher.getResultScore());
      }
      if (!CollectionUtils.isEmpty(allAnaly)) {
        Double score = totalScore / allAnaly.size();
        eai.setResultScore(Double.valueOf(new DecimalFormat("0.00")
            .format(score)));
      } else {
        eai.setResultScore(0.0);
      }
      eai.setScoreAverage(addScoreAverage(eq, evlIndicator.getScoreTotal(),
          eai.getResultScore()));
    } else if ("student".equals(type)) {
      Set<String> memberCodes = new HashSet<String>();
      for (EvlQuestionMember member : memberList) {
        memberCodes.add(member.getCode());
      }
      this.getIndicatorResultScoreByIndicatorId(eai, memberCodes, teacherId);
    }
    // 加载分析指标
    eaiVo.setEvlAnalyzeIndicator(eai);
  }

  private double addScoreAverage(EvlQuestionnaires eq, double indicatorScore,
      double resultScore) {
    Integer indicatorType = eq.getIndicatorType();
    if (EvlQuestionnaires.indicator_type_zhengti == indicatorType) {
      indicatorScore = eq.getTotleScore();
    }
    if (indicatorScore > 0) {
      return Double.valueOf(new DecimalFormat("0.00").format(resultScore
          / indicatorScore * 100));
    }
    return 0.0;
  }

  /**
   * 指标总平均分
   * 
   * @param eai
   */
  private void getIndicatorResultScoreByIndicatorId(EvlAnalyzeIndicator eai,
      Set<String> memberCodes, Integer teacherId) {
    EvlOperateResult paramRes = new EvlOperateResult();
    paramRes.setOrgId(eai.getOrgId());
    paramRes.setQuestionnairesId(eai.getQuestionnairesId());
    paramRes.setTeacherId(teacherId);
    paramRes.setIndicatorId(eai.getIndicatorId());
    paramRes.addCustomCulomn("code,subjeId,resultScore,teacherId,classId");

    List<EvlOperateResult> resultList = this.evlOperateResultService
        .findAll(paramRes);

    double newScore = 0.0;
    double resultScore = 0.0;
    if (!CollectionUtils.isEmpty(resultList)) {
      Set<String> codeList = new HashSet<String>();
      for (EvlOperateResult res : resultList) {
        if (res.getTeacherId() != null && memberCodes.contains(res.getCode())) {
          codeList.add(res.getCode() + "_c_" + res.getClassId() + "_s_"
              + res.getSubjeId());
          resultScore += res.getResultScore();
        }
      }
      if (!CollectionUtils.isEmpty(codeList)) {
        newScore = resultScore / codeList.size();
      }
    }
    eai.setResultScore(Double.valueOf(new DecimalFormat("0.00")
        .format(newScore)));
  }

  /**
   * 加载指标集
   * 
   * @param eq
   * @param eat
   * @param eaiVoList
   */
  private void loadAnalyzeIndicatorVo(EvlQuestionnaires eq,
      List<EvlAnalyzeIndicatorVo> eaiVoList, String type, Integer teacherId) {
    List<EvlIndicator> indicatorList1 = evlIndicatorService.getLevelIndicator(
        eq.getQuestionnairesId(), EvlIndicator.level1);
    // 一级指标
    Integer questionType = eq.getIndicatorType();
    for (EvlIndicator evlIndicator : indicatorList1) {
      EvlAnalyzeIndicatorVo eaiVo = new EvlAnalyzeIndicatorVo();
      List<EvlAnalyzeIndicatorVo> eaiVoList2 = new ArrayList<EvlAnalyzeIndicatorVo>();
      // 获取二级指标
      List<EvlIndicator> indicatorList12 = evlIndicatorService
          .getTwoLevelIndicator(evlIndicator);
      if (EvlQuestionnaires.indicator_type_oneLevel == questionType) {
        for (EvlIndicator evlIndicator2 : indicatorList12) {
          EvlAnalyzeIndicatorVo eaiVo2 = new EvlAnalyzeIndicatorVo();
          eaiVo2.setEvlIndicator(evlIndicator2);
          eaiVoList2.add(eaiVo2);
        }
        // 分析指标
        this.loadAnalyzeIndicator(eaiVo, evlIndicator, eq, type, teacherId);
      } else if (EvlQuestionnaires.indicator_type_twoLevel == questionType) {
        for (EvlIndicator evlIndicator2 : indicatorList12) {
          EvlAnalyzeIndicatorVo eaiVo2 = new EvlAnalyzeIndicatorVo();
          eaiVo2.setEvlIndicator(evlIndicator2);
          // 分析指标
          this.loadAnalyzeIndicator(eaiVo2, evlIndicator2, eq, type, teacherId);
          eaiVoList2.add(eaiVo2);
        }
      }
      // 加载一级指标
      eaiVo.setEvlIndicator(evlIndicator);
      // 加载二级指标
      eaiVo.setChildIndicators(eaiVoList2);
      // 装在指标数据
      eaiVoList.add(eaiVo);
    }
  }

  @Override
  public void downLoadResultInfo(HttpServletResponse response,
      EvlAnalyzeTeacher eat) {
    Map<String, Object> root = new HashMap<String, Object>();
    String gradeName = "全部年级";
    String subjectName = "全部学科";
    String title = "无标题";
    EvlAnalyzeTeacher param = new EvlAnalyzeTeacher();
    EvlQuestionnaires question = evlQuestionnairesService.findOne(eat
        .getQuestionnairesId());
    param.setOrgId(question.getOrgId());
    param.setQuestionnairesId(eat.getQuestionnairesId());
    Organization org = organizationService.findOne(question.getOrgId());
    List<GradeAndClassVo> gradeList = null;
    if (org == null) {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(question
          .getPhaseId());
    } else {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(
          question.getPhaseId(), org.getSchoolings());
    }
    List<SubjectVo> subjectList = evlMetaService.findSubjectByPhaseId(
        question.getPhaseId(), question.getOrgId());
    if (eat.getGradeId() != null) {
      param.setGradeId(eat.getGradeId());
      for (GradeAndClassVo gradeAndClassVo : gradeList) {
        if (gradeAndClassVo.getId().toString().equals(eat.getGradeId())) {
          gradeName = gradeAndClassVo.getName();
          break;
        }
      }
    }
    if (eat.getSubjectId() != null) {
      param.setSubjectId(eat.getSubjectId());
      for (SubjectVo subjectVo : subjectList) {
        if (subjectVo.getId().toString().equals(eat.getSubjectId())) {
          subjectName = subjectVo.getName();
          break;
        }
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(eat
        .getQuestionnairesId());

    title = StringUtils.abbr(eq.getTitle(), 48, true, false);

    List<EvlAnalyzeTeacher> teacherList = this
        .findAnalyzeTeacherListByQuestionnaire(eq, param);

    String[] scoreSection = EvlAnalyzeTeacher.SCORESECTION;
    Integer[] peopleSection = new Integer[scoreSection.length];
    for (int i = 0; i < peopleSection.length; i++) {
      peopleSection[i] = 0;
    }
    int sort = 1;
    EvlAnalyzeTeacher teacher = null;
    for (EvlAnalyzeTeacher eat1 : teacherList) {
      if (teacher == null
          || !eat1.getResultScore().equals(teacher.getResultScore())) {
        sort++;
      }
      eat1.setSort(sort);
      teacher = eat1;
      if (StringUtils.isNotEmpty(eat1.getGradeId())) {
        String[] arrIds = eat1.getGradeId().split(",");
        String arrNames = "";
        for (int j = 0; j < arrIds.length; j++) {
          for (GradeAndClassVo gradeAndClassVo : gradeList) {
            if (gradeAndClassVo.getId().toString().equals(arrIds[j])) {
              arrNames += "、" + gradeAndClassVo.getName();
              break;
            }
          }
        }
        arrNames = "".equals(arrNames) ? "" : arrNames.substring(1);
        eat1.setFlago(arrNames);
      }
      if (StringUtils.isNotEmpty(eat1.getSubjectId())) {
        String[] arrIds = eat1.getSubjectId().split(",");
        String arrNames = "";
        for (int j = 0; j < arrIds.length; j++) {
          for (SubjectVo subjectVo : subjectList) {
            if (subjectVo.getId().toString().equals(arrIds[j])) {
              arrNames += "、" + subjectVo.getName();
              break;
            }
          }
        }
        arrNames = "".equals(arrNames) ? "" : arrNames.substring(1);
        eat1.setFlags(arrNames);
      }
      this.evlOperateResultService.statisticsSection(peopleSection,
          eat1.getResultScore());
    }

    List<Map<String, String>> sectionList = new ArrayList<Map<String, String>>();
    // 分数段统计列表
    for (int k = 0; k < scoreSection.length; k++) {
      Map<String, String> sectionMap = new HashMap<String, String>();
      sectionMap.put("scoreSection", scoreSection[k]);
      sectionMap.put("peopleSection", peopleSection[k] + "");
      double percentNumber = 0.0;
      if (!CollectionUtils.isEmpty(teacherList)) {
        percentNumber = Double.valueOf(new DecimalFormat("0.00")
            .format((peopleSection[k] * 1.00) / teacherList.size() * 100));
      }
      sectionMap.put("percentSection", percentNumber + "");
      sectionList.add(sectionMap);
    }
    root.put("title", title);
    root.put("gradeName", gradeName);
    root.put("subjectName", subjectName);
    // word文档专用显示图片
    root.put("imageCodeKeys", eat.getFlago());
    root.put("teacherList", teacherList);
    root.put("sectionList", sectionList);
    FreemarkerUtils.createWord(response, "statisticResult.ftl", root);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> findResultQuestionnairesInfo(
      EvlAnalyzeTeacher eat, boolean isDownload) {
    Map<String, Object> resultMap = null;
    if (getQuestionCache() != null) {
      ValueWrapper cacheElement = getQuestionCache().get(
          "ev_rs_q" + eat.getQuestionnairesId() + "_g" + eat.getGradeId()
              + "_s" + eat.getSubjectId());
      if (cacheElement != null) {
        resultMap = (Map<String, Object>) cacheElement.get();
        return resultMap;
      }
    }
    EvlQuestionnaires eq = evlQuestionnairesService.findOne(eat
        .getQuestionnairesId());
    EvlAnalyzeTeacher param = new EvlAnalyzeTeacher();
    param.setOrgId(eq.getOrgId());
    param.setQuestionnairesId(eat.getQuestionnairesId());
    if (eat.getGradeId() != null) {
      param.setGradeId(eat.getGradeId());
    }
    if (eat.getSubjectId() != null) {
      param.setSubjectId(eat.getSubjectId());
    }

    List<EvlAnalyzeTeacher> teacherList = this
        .findAnalyzeTeacherListByQuestionnaire(eq, param);

    String[] scoreSection = EvlAnalyzeTeacher.SCORESECTION;
    Integer[] peopleSection = new Integer[scoreSection.length];
    for (int i = 0; i < peopleSection.length; i++) {
      peopleSection[i] = 0;
    }
    Organization org = organizationService.findOne(eq.getOrgId());
    List<GradeAndClassVo> gradeList = null;
    if (org == null) {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId());
    } else {
      gradeList = evlMetaService.findGradeByPhaseIdAndSystemId(eq.getPhaseId(),
          org.getSchoolings());
    }
    // 加载年级，学科名称
    List<SubjectVo> subjectList = evlMetaService.findSubjectByPhaseId(
        eq.getPhaseId(), eq.getOrgId());
    Map<String, String> gradeNameMap = new HashMap<String, String>();
    for (GradeAndClassVo gradeAndClassVo : gradeList) {
      gradeNameMap.put(gradeAndClassVo.getId().toString(),
          gradeAndClassVo.getName());
    }
    Map<String, String> subjectNameMap = new HashMap<String, String>();
    for (SubjectVo subjectVo : subjectList) {
      subjectNameMap.put(subjectVo.getId().toString(), subjectVo.getName());
    }
    for (int i = 0; i < teacherList.size(); i++) {
      EvlAnalyzeTeacher eat1 = teacherList.get(i);
      if (i == 0) {
        eat1.setSort(1);
      } else {
        EvlAnalyzeTeacher teacher = teacherList.get(i - 1);
        if (eat1.getResultScore().equals(teacher.getResultScore())) {
          eat1.setSort(teacher.getSort());
        } else {
          eat1.setSort(i + 1);
        }
      }
      this.evlOperateResultService.statisticsSection(peopleSection,
          eat1.getResultScore());

      if (StringUtils.isNotEmpty(eat1.getGradeId())) {
        String[] arrIds = eat1.getGradeId().split(",");
        String arrNames = "";
        for (int j = 0; j < arrIds.length; j++) {
          String gn = gradeNameMap.get(arrIds[j]);
          if (gn != null)
            arrNames += "、" + gn;
        }
        arrNames = "".equals(arrNames) ? "" : arrNames.substring(1);
        eat1.setFlago(arrNames);
      }
      if (StringUtils.isNotEmpty(eat1.getSubjectId())) {
        String[] arrIds = eat1.getSubjectId().split(",");
        String arrNames = "";
        for (int j = 0; j < arrIds.length; j++) {
          String sn = subjectNameMap.get(arrIds[j]);
          if (sn != null)
            arrNames += "、" + sn;
        }
        arrNames = "".equals(arrNames) ? "" : arrNames.substring(1);
        eat1.setFlags(arrNames);
      }
      if (StringUtils.isEmpty(eat1.getTeacherRoleName())) {
        eat1.setTeacherRoleName(eat1.getTeacherRole());
      }

      if (!isDownload) {
        String tr = eat1.getTeacherRole();
        if (tr != null && tr.indexOf("=,=") < 0) {
          String teacherName = StringUtils.abbr(eat1.getTeacherName(), 18,
              true, false);
          String teacherRole = StringUtils.abbr(tr, 18, true, false);
          String flago = StringUtils.abbr(eat1.getFlago(), 30, true, false);
          String flags = StringUtils.abbr(eat1.getFlags(), 30, true, false);
          eat1.setTeacherRole(tr + "=,=" + teacherName + "=,=" + teacherRole
              + "=,=" + flago + "=,=" + flags);
        }

      }
    }
    // 分数段统计列表
    List<Map<String, String>> sectionList = new ArrayList<Map<String, String>>();
    Double[] percentSection = new Double[scoreSection.length];
    for (int k = 0; k < scoreSection.length; k++) {
      Map<String, String> sectionMap = new HashMap<String, String>();
      sectionMap.put("scoreSection", scoreSection[k]);
      sectionMap.put("peopleSection", peopleSection[k] + "");
      double percentNumber = 0.0;
      if (!CollectionUtils.isEmpty(teacherList)) {
        percentNumber = Double.valueOf(new DecimalFormat("0.00")
            .format((peopleSection[k] * 1.00) / teacherList.size() * 100));
      }
      sectionMap.put("percentSection", percentNumber + "");
      sectionList.add(sectionMap);
      percentSection[k] = Double.valueOf(sectionMap.get("percentSection"));
    }
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (!isDownload) {
      Option option = EchartUtil.packageBarOption("问卷结果分布率图表", scoreSection,
          "分布率", percentSection);
      option.getSeries().get(0).getItemStyle().getNormal().getLabel()
          .formatter("{c}%");
      option.grid().x(75).x2(65);
      returnMap.put("option", option);
    }
    returnMap.put("teacherList", teacherList);
    returnMap.put("sectionList", sectionList);

    // 结果放入缓存
    if (getQuestionCache() != null) {
      getQuestionCache().put(
          "ev_rs_q" + eat.getQuestionnairesId() + "_g" + eat.getGradeId()
              + "_s" + eat.getSubjectId(), returnMap);
    }
    return returnMap;
  }
}
