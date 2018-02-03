package com.tmser.schevaluation.evl.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.schevaluation.common.utils.WebThreadLocalUtils;
import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlquestionBizService;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeTeacher;
import com.tmser.schevaluation.evl.bo.EvlClass;
import com.tmser.schevaluation.evl.bo.EvlIndicator;
import com.tmser.schevaluation.evl.bo.EvlLevelWeight;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.service.EvlAnalyzeTeacherService;
import com.tmser.schevaluation.evl.service.EvlClassService;
import com.tmser.schevaluation.evl.service.EvlIndicatorService;
import com.tmser.schevaluation.evl.service.EvlLevelWeightService;
import com.tmser.schevaluation.evl.service.EvlMetaService;
import com.tmser.schevaluation.evl.service.EvlOperateResultService;
import com.tmser.schevaluation.evl.service.EvlQuestionMemberService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.statics.EvlMemberStatus;
import com.tmser.schevaluation.evl.statics.EvlQuestionType;
import com.tmser.schevaluation.evl.utils.FreemarkerUtils;
import com.tmser.schevaluation.evl.vo.EvlAnalyzeIndicatorVo;
import com.tmser.schevaluation.evl.vo.EvlQuestionnairesVo;
import com.tmser.schevaluation.evl.vo.GradeAndClassVo;
import com.tmser.schevaluation.evl.vo.SubjectVo;
import com.tmser.schevaluation.manage.org.bo.Organization;
import com.tmser.schevaluation.manage.org.service.OrganizationService;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.bo.SchClassUser;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.schconfig.clss.service.SchClassUserService;
import com.tmser.schevaluation.uc.bo.User;
import com.tmser.schevaluation.uc.bo.UserSpace;
import com.tmser.schevaluation.uc.service.UserSpaceService;
import com.tmser.schevaluation.uc.utils.SessionKey;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 评教结果控制器
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlResultController.java, v 1.0 2016年5月10日 下午3:44:31
 *          gengqianfeng Exp $
 */
@Controller
@RequestMapping("/jy/evl/result")
public class EvlResultController extends AbstractController {

	@Autowired
	private EvlOperateResultService evlOperateResultService;
	@Autowired
	private EvlQuestionnairesService evlQuestionnairesService;
	@Autowired
	private EvlQuestionMemberService evlQuestionMemberService;
	@Autowired
	private EvlMetaService evlMetaService;
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private EvlClassService evlClassService;
	@Autowired
	private SchClassUserService schClassUserService;
	@Autowired
	private SchClassService schClassService;
	@Autowired
	private EvlIndicatorService evlIndicatorService;
	@Autowired
	private EvlLevelWeightService evlLevelWeightService;
	@Autowired
	private EvlAnalyzeTeacherService evlAnalyzeTeacherService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private EvlquestionBizService evlquestionBizService;

	/**
	 * 结果统计首页
	 * 
	 * @return
	 */
	@RequestMapping("/resultIndex")
	public String showResultIndex(Model model) {
		Map<String, Object> returnMap = evlquestionBizService.findResultIndex(EvlQuestionType.jieshu.getValue());
		model.addAttribute("currentList", returnMap.get("currentList"));
		model.addAttribute("schoolYearList", returnMap.get("schoolYearList"));
		return viewName("/resultIndex");
	}

	/**
	 * 根据学年获取结果统计问卷列表
	 * 
	 * @param schoolYear
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findResultQuestionnairesList")
	public List<EvlQuestionnairesVo> findResultQuestionnairesList(@RequestParam("schoolYear") Integer schoolYear) {
		return evlquestionBizService.findResultQuestionnairesList(schoolYear, EvlQuestionType.jieshu.getValue(),null,null);
	}

	/**
	 * 结果统计问卷详情页
	 * 
	 * @param model
	 * @param eat
	 * @return
	 */
	@RequestMapping("/resultQuestionnairesInfo")
	public String showResultQuestionnairesInfo(Model model, EvlAnalyzeTeacher eat) {
		if (eat.getQuestionnairesId() == null) {
			return viewName("/resultIndex");
		}
		EvlQuestionnaires questionnaires = evlQuestionnairesService.findOne(eat.getQuestionnairesId());
		model.addAttribute("questionnaires", questionnaires);
		model.addAttribute("realGradeList", evlAnalyzeTeacherService.getGradeVoList(questionnaires));
		model.addAttribute("realSubjectList", evlAnalyzeTeacherService.getSubjectVoList(questionnaires));
		return viewName("/resultQuestionnairesInfo");
	}

	/**
	 * 教师指标统计列表
	 * 
	 * @param model
	 * @param eat
	 * @return
	 */
	@RequestMapping("/resultUserIndicator")
	public String showResultUserIndicator(Model model, EvlAnalyzeTeacher eat) {
		Integer questionnairesId = eat.getQuestionnairesId();
		Integer teacherId = eat.getTeacherId();
		if (questionnairesId == null || teacherId == null) {
			return viewName("/resultQuestionnairesInfo");
		}
		EvlQuestionnaires evlquestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		model.addAttribute("teacher", eat);
		model.addAttribute("spaceList", this.loadUserSpaceListByUserId(evlquestionnaires, teacherId));
		model.addAttribute("questionnaires", evlquestionnaires);
		if (evlquestionnaires.getIndicatorType().intValue() == EvlQuestionnaires.indicator_type_zhengti) {
			return viewName("/resultUserWhole");
		} else {
			List<EvlAnalyzeIndicatorVo> indicatorList = evlAnalyzeTeacherService.findResultUserIndicator(evlquestionnaires, teacherId);
			model.addAttribute("indicatorList", indicatorList);
			return viewName("/resultUserIndicator");
		}
	}

	/**
	 * 教师指标统计列表详情
	 * 
	 * @param model
	 * @param eat
	 * @return
	 */
	@RequestMapping("/resultUserIndicatorInfo")
	public String showResultUserIndicatorInfo(Model model, EvlAnalyzeTeacher eat) {
		Integer questionnairesId = eat.getQuestionnairesId();
		Integer teacherId = eat.getTeacherId();
		Double resultScore = eat.getResultScore();
		if (questionnairesId == null || teacherId == null || resultScore == null) {
			return viewName("/resultUserIndicator");
		}
		EvlQuestionnaires evlquestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		// 教师对象
		model.addAttribute("teacher", eat);
		// 用户空间
		model.addAttribute("spaceList", this.loadUserSpaceListByUserId(evlquestionnaires, teacherId));
		// 问卷
		model.addAttribute("questionnaires", evlquestionnaires);
		// 指标列表
		model.addAttribute("indicatorList", evlAnalyzeTeacherService.findResultUserIndicatorInfo(evlquestionnaires, eat));
		return viewName("/resultUserIndicatorInfo");
	}

	/**
	 * 获取教师班级学科
	 * 
	 * @param questionnairesId
	 * @param teacherId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findClassAndSubjectListByUserId")
	public List<Map<String, Object>> findClassAndSubjectListByUserId(@RequestParam("questionnairesId") Integer questionnairesId, @RequestParam("teacherId") Integer teacherId) {
		EvlQuestionnaires evlquestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		List<EvlClass> ecClass = evlClassService.findClassListByQuestionnairesId(evlquestionnaires);
		SchClassUser scu = new SchClassUser();
		scu.setTchId(teacherId);
		scu.setType(SchClassUser.T_TEACHER);
		scu.setSchoolYear(evlquestionnaires.getSchoolYear());
		List<SchClassUser> ucList = schClassUserService.findAll(scu);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (evlquestionnaires.getStudentType().intValue() == EvlQuestionnaires.student_type_nianji) {
			for (EvlClass ec : ecClass) {
				for (SchClassUser uc : ucList) {
					SchClass sc = schClassService.findOne(uc.getClassId());
					if (sc != null && sc.getGradeId().equals(ec.getGradeId())) {
						this.loadClassUserList(returnList, sc, uc);
					}
				}
			}
		} else if (evlquestionnaires.getStudentType().intValue() == EvlQuestionnaires.student_type_banji) {
			for (EvlClass ec : ecClass) {
				for (SchClassUser uc : ucList) {
					if (uc.getClassId().equals(ec.getClassId())) {
						this.loadClassUserList(returnList, schClassService.findOne(uc.getClassId()), uc);
					}
				}
			}
		} else {
			for (SchClassUser uc : ucList) {
				this.loadClassUserList(returnList, schClassService.findOne(uc.getClassId()), uc);
			}
		}

		for (int i = 0; i < returnList.size(); i++) {
			Map<String, Object> map1 = returnList.get(i);
			for (int j = i + 1; j < returnList.size(); j++) {
				Map<String, Object> map2 = returnList.get(j);
				if (map1.get("gradeId").equals(map2.get("gradeId")) && map1.get("classId").equals(map2.get("classId"))) {
					map1.put("subjectId", map1.get("subjectId") + "," + map2.get("subjectId"));
					returnList.remove(j--);
				}
			}
			List<GradeAndClassVo> gradeMeta = this.getGradeVo(evlquestionnaires.getPhaseId());
			map1.put("gradeName", "");
			for (GradeAndClassVo gradeVo : gradeMeta) {
				if (map1.get("gradeId").equals(gradeVo.getId())) {
					map1.put("gradeName", gradeVo.getName());
					break;
				}
			}
		}
		// 排序分组
		Collections.sort(returnList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
				int gradeId0 = Integer.parseInt(arg0.get("gradeId").toString());
				int gradeId1 = Integer.parseInt(arg1.get("gradeId").toString());
				if (gradeId0 > gradeId1) {
					return 1;
				} else if (gradeId0 == gradeId1) {
					int classSort0 = Integer.parseInt(arg0.get("classSort").toString());
					int classSort1 = Integer.parseInt(arg1.get("classSort").toString());
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
		return returnList;
	}

	/**
	 * 通过学科id获取当前学段的学科
	 * 
	 * @param subjectIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSubjectByIds")
	public List<SubjectVo> getSubjectByIds(@RequestParam("subjectIds") String subjectIds,Integer questionId) {
		EvlQuestionnaires question = evlQuestionnairesService.findOne(questionId);
		List<SubjectVo> subjectVoList = new ArrayList<SubjectVo>();
		if (StringUtils.isEmpty(subjectIds)) {
			subjectVoList = evlMetaService.findSubjectByPhaseId(question.getPhaseId(),question.getOrgId());
		} else {
			subjectVoList = evlMetaService.findSubjectByPhaseId(question.getPhaseId(),subjectIds.split(","));
		}
		return subjectVoList;
	}

	/**
	 * 装载用户班级学科信息
	 * 
	 * @param returnList
	 * @param sc
	 * @param uc
	 */
	private void loadClassUserList(List<Map<String, Object>> returnList, SchClass sc, SchClassUser uc) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("gradeId", sc.getGradeId());
		returnMap.put("classId", sc.getId());
		returnMap.put("className", sc.getName());
		returnMap.put("classSort", sc.getSort());
		returnMap.put("subjectId", uc.getSubjectId());
		returnList.add(returnMap);
	}

	/**
	 * 根据用户id获取角色列表
	 * 
	 * @param userId
	 * @return
	 */
	private List<UserSpace> loadUserSpaceListByUserId(EvlQuestionnaires evlquestionnaires, Integer userId) {
		UserSpace us = new UserSpace();
		us.setOrgId(evlquestionnaires.getOrgId());
		us.setUserId(userId);
		us.setSchoolYear(evlquestionnaires.getSchoolYear());
		us.addCustomCulomn("id,username,spaceName,gradeId,subjectId");
		return userSpaceService.findAll(us);
	}

	/**
	 * 获取对应教师指标下的学生评审结果集
	 * 
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findStudentResultListByQuestionnairesId")
	public Map<String, Object> findStudentResultListByQuestionnairesId(EvlOperateResult result) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer questionnairesId = result.getQuestionnairesId();
		Integer gradeId = result.getGradeId();
		Integer classId = result.getClassId();
		EvlQuestionnaires evlquestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		List<EvlIndicator> indicatorList = new ArrayList<EvlIndicator>();
		if (evlquestionnaires.getIndicatorType().equals(EvlQuestionnaires.indicator_type_oneLevel)) {
			indicatorList = evlIndicatorService.getAllIndicatorListByLevel(questionnairesId, EvlIndicator.level1);
		} else if (evlquestionnaires.getIndicatorType().equals(EvlQuestionnaires.indicator_type_twoLevel)) {
			indicatorList = evlIndicatorService.getAllIndicatorListByLevel(questionnairesId, EvlIndicator.level2);
		} else {
			return null;
		}
		// 学生列头
		EvlQuestionMember member = new EvlQuestionMember();
		member.setOrgId(evlquestionnaires.getOrgId());
		member.setQuestionId(questionnairesId);
		member.setGradeId(Integer.valueOf(gradeId));
		member.setClassId(Integer.valueOf(classId));
		member.setSchoolYear(evlquestionnaires.getSchoolYear());
		member.setStatus(EvlMemberStatus.tijiaowenjuan.getValue());
		member.addGroup("code");
		List<EvlQuestionMember> headList = evlQuestionMemberService.findAll(member);
		returnMap.put("headList", headList);
		// 问卷权重等级列表
		List<EvlLevelWeight> levelList = evlLevelWeightService.findEvlLevelWeightListByQuestionnaires(evlquestionnaires);
		// 学生评教等级
		for (EvlIndicator indicator : indicatorList) {
			result.setIndicatorId(indicator.getId());
			List<EvlOperateResult> resultList = evlOperateResultService.findOperateResultListByIndicatorLevel(evlquestionnaires, result);
			for (EvlOperateResult eor : resultList) {
				for (EvlLevelWeight levelWeight : levelList) {
					if (eor.getResultLevel().equals(levelWeight.getId())) {
						eor.setFlago(levelWeight.getLevelName());
						break;
					}
				}
			}
			returnMap.put(indicator.getId(), resultList);
		}
		return returnMap;
	}

	/**
	 * 获取某个教师的整体评价记录列表
	 * 
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findStudentResultListByZhengti")
	public Map<String, Object> findStudentResultListByZhengti(EvlOperateResult result) {
		EvlQuestionnaires evlquestionnaires = evlQuestionnairesService.findOne(result.getQuestionnairesId());
		// 学生评教等级
		List<EvlOperateResult> resultList = evlOperateResultService.findOperateResultListByIndicatorLevel(evlquestionnaires, result);
		// 问卷权重等级列表
		List<EvlLevelWeight> levelList = evlLevelWeightService.findEvlLevelWeightListByQuestionnaires(evlquestionnaires);
		// 当前学段年级列表
		List<GradeAndClassVo> gradeMeta = getGradeVo(evlquestionnaires.getPhaseId());
		// 评教等级分组计数
		return evlOperateResultService.findStudentResultListByZhengti(result.getQuestionnairesId(),resultList, levelList, gradeMeta);
	}

	/**
	 * 获取当前学段年级列表
	 * 
	 * @return
	 */
	private List<GradeAndClassVo> getGradeVo(Integer phaseId) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Organization org = organizationService.findOne(user.getOrgId());
		List<GradeAndClassVo> gradeMeta = null;
		if(org==null){
			gradeMeta=evlMetaService.findGradeByPhaseIdAndSystemId(phaseId);
		}else{
			gradeMeta=evlMetaService.findGradeByPhaseIdAndSystemId(phaseId,org.getSchoolings());
		}
		return gradeMeta;
	}

	/**
	 * 下载结果统计详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/downLoadResultInfo")
	public void downLoadResultInfo(HttpServletResponse response, EvlAnalyzeTeacher eat) {
		evlAnalyzeTeacherService.downLoadResultInfo(response, eat);
	}

	/**
	 * 获取图表图片保存路径
	 * 
	 * @param picBase64Info
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPicFilePath")
	public Result getPicFilePath(@RequestParam("picBase64Info") String picBase64Info, @RequestParam("type") String type) {
		Result result = new Result();
		String picPath = FreemarkerUtils.getPicFilePath(picBase64Info, type);
		if ("".equals(picPath)) {
			result.setCode(400);
		} else {
			result.setCode(200);
			result.setMsg(picPath);
		}
		return result;
	}
}
