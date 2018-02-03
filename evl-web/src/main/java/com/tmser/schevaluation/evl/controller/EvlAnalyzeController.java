package com.tmser.schevaluation.evl.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.schevaluation.common.vo.Result;
import com.tmser.schevaluation.common.web.controller.AbstractController;
import com.tmser.schevaluation.evl.bizservice.EvlquestionBizService;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeTeacher;
import com.tmser.schevaluation.evl.bo.EvlQuestionMember;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.bo.EvlSuggestion;
import com.tmser.schevaluation.evl.service.EvlAnalyzeIndicatorService;
import com.tmser.schevaluation.evl.service.EvlAnalyzeTeacherService;
import com.tmser.schevaluation.evl.service.EvlIndicatorService;
import com.tmser.schevaluation.evl.service.EvlQuestionMemberService;
import com.tmser.schevaluation.evl.service.EvlQuestionnairesService;
import com.tmser.schevaluation.evl.service.EvlSuggestionService;
import com.tmser.schevaluation.evl.service.EvlSuggestionTypeService;
import com.tmser.schevaluation.evl.statics.EvlQuestionType;
import com.tmser.schevaluation.evl.vo.EvlIndicatorVo;
import com.tmser.schevaluation.evl.vo.EvlQuestionnairesVo;
import com.tmser.schevaluation.schconfig.clss.bo.SchClass;
import com.tmser.schevaluation.schconfig.clss.service.SchClassService;
import com.tmser.schevaluation.utils.StringUtils;

/**
 * 评价分析控制器
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeController.java, v 1.0 2016年5月10日 下午6:20:43
 *          gengqianfeng Exp $
 */
@Controller
@RequestMapping("/jy/evl/analyze")
public class EvlAnalyzeController extends AbstractController {

	@Autowired
	private EvlAnalyzeTeacherService evlAnalyzeTeacherService;
	@Autowired
	private EvlAnalyzeIndicatorService evlAnalyzeIndicatorService;
	@Autowired
	private EvlQuestionnairesService evlQuestionnairesService;
	@Autowired
	private EvlIndicatorService evlIndicatorService;
	@Autowired
	private EvlSuggestionService evlSuggestionService;
	@Autowired
	private EvlSuggestionTypeService evlSuggestionTypeService;
	@Autowired
	private SchClassService schClassService;
	@Autowired
	private EvlQuestionMemberService evlQuestionMemberService;
	@Autowired
	private EvlquestionBizService evlquestionBizService;
	
	/**
	 * 分析报告首页
	 * 
	 * @return
	 */
	@RequestMapping("/analyzeIndex")
	public String showAnalyzeIndex(Model model) {
		Map<String, Object> returnMap = evlquestionBizService.findResultIndex(EvlQuestionType.jieshu.getValue());
		model.addAttribute("currentList", returnMap.get("currentList"));
		model.addAttribute("schoolYearList", returnMap.get("schoolYearList"));
		return viewName("/analyzeIndex");
	}

	/**
	 * 根据学年获取结果统计问卷列表
	 * 
	 * @param schoolYear
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAnalyzeQuestionnairesList")
	public List<EvlQuestionnairesVo> findAnalyzeQuestionnairesList(@RequestParam("schoolYear") Integer schoolYear) {
		return evlquestionBizService.findResultQuestionnairesList(schoolYear, EvlQuestionType.jieshu.getValue(),null,null);
	}

	/**
	 * 渲染分析详情页
	 * 
	 * @param model
	 * @param questionId
	 * @return
	 */
	@RequestMapping("/analyzeInfo")
	public String showAnalyzeInfo(@RequestParam("questionnairesId") Integer questionId, Model model,String mark) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		// 问卷
		EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionId);
		// 指标
		EvlIndicatorVo eiv = evlIndicatorService.getAllIndicatorList(eq);
		// 反馈类别
		boolean flag = true;
		if (eq.getIscollect().intValue() == EvlQuestionnaires.iscollect_bushouji || CollectionUtils.isEmpty(evlSuggestionTypeService.findSuggestionTypeListByQuestionnaires(eq))) {
			flag = false;
		}
		model.addAttribute("questionnaires", eq);// 问卷
		if (StringUtils.isNotEmpty(eq.getBack1())) {
			model.addAttribute("questionnairesPercent", eq.getBack1().split(","));
		}
		model.addAttribute("indicatorVo", eiv);// 指标
		model.addAttribute("isShowSuggestion", flag);// 反馈类别
		
		model.addAttribute("realGradeList", evlAnalyzeTeacherService.getGradeVoList(eq));
		model.addAttribute("mark", mark);
		return viewName("/analyzeInfo");
	}
	
	/**
	 * 问题分析
	 */
	@RequestMapping("/loadAnalysisProblem")
	public String findAnalyzeProblemsList(Model model,Integer questionnairesId){
		Map<String, Object> problemsMap = evlAnalyzeTeacherService.findAnalyzeProblemsList(questionnairesId);
		model.addAttribute("problemsMap", problemsMap);// 问题分析统计
		return viewName("/wtfx");
	}
	/**
	 * 问卷结果
	 * 
	 * @param questionnairesId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadAnalysisReport")
	public List<EvlAnalyzeTeacher> loadAnalysisReport(@RequestParam("questionnairesId") Integer questionnairesId) {
		List<EvlAnalyzeTeacher> result = evlAnalyzeTeacherService.findAnalyzeTeacherListByQuestionnaire(evlQuestionnairesService.findOne(questionnairesId),null);
		return result;
		
	}

	/**
	 * 全体教师汇总
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAllAnalyzeTeacherList")
	public Map<String, Object> findAllAnalyzeTeacherList(EvlAnalyzeTeacher eat) {
		if (eat.getQuestionnairesId() == null) {
			return null;
		}
		return this.evlAnalyzeTeacherService.findResultQuestionnairesInfo(eat, false);
	}

	/**
	 * 评价内容分析对比
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAllAnalyzeIndicatorVoList")
	public Map<String, Object> findAllAnalyzeIndicatorVoList(@RequestParam("questionId") Integer questionId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlAnalyzeIndicatorService.findAllAnalyzeIndicatorVoList(questionId, false);
	}

	/**
	 * 年级分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findTeacherListOfGrade")
	public Map<String, Object> findTeacherListOfGrade(@RequestParam("questionId") Integer questionId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlAnalyzeTeacherService.findTeacherListOfGrade(questionId, false);
	}

	/**
	 * 学科分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findTeacherListOfSubject")
	public Map<String, Object> findTeacherListOfSubject(@RequestParam("questionId") Integer questionId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlAnalyzeTeacherService.findTeacherListOfSubject(questionId, false);
	}

	/**
	 * 教龄分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findTeacherListBySchoolAgeSex")
	public Map<String, Object> findTeacherListBySchoolAgeSex(@RequestParam("questionId") Integer questionId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlAnalyzeTeacherService.findTeacherListBySchoolAgeSex(questionId, false);
	}

	/**
	 * 班主任分析对比列表
	 * 
	 * @param questionId
	 * @param gradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findDirectorTeacherList")
	public Map<String, Object> findDirectorTeacherList(@RequestParam("questionId") Integer questionId, @RequestParam(value = "gradeId", required = false) Integer gradeId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlAnalyzeTeacherService.findDirectorTeacherList(questionId, gradeId, false);
	}

	/**
	 * 意见建议反馈分析列表
	 * 
	 * @param questionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAnalyzeSuggestionList")
	public Map<String, Object> findAnalyzeSuggestionList(@RequestParam("questionId") Integer questionId) {
		if (questionId == null || questionId <= 0) {
			return null;
		}
		return evlSuggestionService.findAnalyzeSuggestionList(questionId, false);
	}

	/**
	 * 下载分析报告详情页
	 * 
	 * @return
	 */
	@RequestMapping("/downLoadAnalyzeInfo")
	public void downLoadAnalyzeInfo(HttpServletResponse response, EvlAnalyzeTeacher eat) {
		Map<String, Object> root = new HashMap<String, Object>();
		Integer questionnairesId = eat.getQuestionnairesId();// 问卷id
		// 问卷
		EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionnairesId);
		// 指标
		EvlIndicatorVo eiv = evlIndicatorService.getAllIndicatorList(eq);
		// 全体教师汇总
		Map<String, Object> analyzeTeacherMap = null;
		Map<String, Object> analyzeIndicatorMap = null;
		analyzeTeacherMap = evlAnalyzeTeacherService.findResultQuestionnairesInfo(eat, true);
		if (eq.getIndicatorType().intValue() != EvlQuestionnaires.indicator_type_zhengti) {
			// 1、一级指标评教,2、二级指标评教
			analyzeIndicatorMap = evlAnalyzeIndicatorService.findAllAnalyzeIndicatorVoList(questionnairesId, true);
		}
		// 年级组分析对比
		Map<String, Object> gradeMap = evlAnalyzeTeacherService.findTeacherListOfGrade(questionnairesId, true);
		// 学科组分析对比
		Map<String, Object> subjectMap = evlAnalyzeTeacherService.findTeacherListOfSubject(questionnairesId, true);
		// 班主任分析对比
		Integer gradeId = null;
		root.put("directorFlag", 1);
		if (StringUtils.isNotEmpty(eat.getGradeId())) {
			gradeId = Integer.valueOf(eat.getGradeId());
			root.put("directorFlag", 2);
		}
		Map<String, Object> directorMap = evlAnalyzeTeacherService.findDirectorTeacherList(questionnairesId, gradeId, true);
		// 各教龄教师分析对比
		Map<String, Object> schoolSexMap = evlAnalyzeTeacherService.findTeacherListBySchoolAgeSex(questionnairesId, true);
		// 反馈类别
		boolean flag = false;
		if (eq.getIscollect().intValue() == EvlQuestionnaires.iscollect_shouji && !CollectionUtils.isEmpty(evlSuggestionTypeService.findSuggestionTypeListByQuestionnaires(eq))) {
			flag = true;
		}
		// 意见建议反馈
		Map<String, Object> suggestionMap = evlSuggestionService.findAnalyzeSuggestionList(questionnairesId, true);
		boolean flag1 = false;
		if (suggestionMap.get("suggestionGradeList") != null && suggestionMap.get("suggestionCount") != null) {
			flag1 = true;
		}
		// 问题分析统计
		Map<String, Object> problemsMap = evlAnalyzeTeacherService.findAnalyzeProblemsList(questionnairesId);
		// 问卷结果
		List<EvlAnalyzeTeacher> percentList = evlAnalyzeTeacherService.findAnalyzeTeacherListByQuestionnaire(eq,null);
		this.filterPercentTeacherList(root, percentList, eat);// 百分比过滤结果
		root.put("title", eq.getTitle());
		if (StringUtils.isEmpty(eq.getTitle())) {
			root.put("title", "无标题");
		}
		root.put("questionnaires", eq);
		root.put("indicatorVo", eiv);
		root.put("analyzeTeacherMap", analyzeTeacherMap);
		root.put("analyzeIndicatorMap", analyzeIndicatorMap);
		root.put("gradeMap", gradeMap);
		root.put("subjectMap", subjectMap);
		root.put("directorMap", directorMap);
		root.put("schoolSexMap", schoolSexMap);
		root.put("flag", flag);
		root.put("flag1", flag1);
		root.put("suggestionMap", suggestionMap);
		root.put("problemsMap", problemsMap);
		evlAnalyzeTeacherService.downLoadAnalyzeInfo(response, eat, root);
	}

	/**
	 * 问卷结果百分比过滤
	 * 
	 * @param root
	 * @param percentList
	 * @param eat
	 */
	private void filterPercentTeacherList(Map<String, Object> root, List<EvlAnalyzeTeacher> percentList, EvlAnalyzeTeacher eat) {
		String percents = eat.getFlags();// 获取百分比
		int allCount = percentList.size();
		String[] arrPercent = percents.split(",");
		int[] peopleCount = new int[arrPercent.length];
		String[] teacherMes = new String[arrPercent.length];
		int startIndex = 0;
		for (int i = 0; i < arrPercent.length; i++) {
			double percent = Double.parseDouble(arrPercent[i]);
			peopleCount[i] = (int) Math.round(percent / 100.00 * allCount);
			if (peopleCount[i] > allCount - startIndex) {
				peopleCount[i] = allCount - startIndex;
			}
			String teacherStr = "";
			for (int j = startIndex; j < startIndex + peopleCount[i]; j++) {
				teacherStr += "、" + percentList.get(j).getTeacherName();
			}
			teacherMes[i] = "".equals(teacherStr) ? "" : teacherStr.substring(1);
			startIndex += peopleCount[i];
		}
		if (startIndex < allCount) {
			for (int i = arrPercent.length - 1; i >= 0; i--) {
				double percent = Double.parseDouble(arrPercent[i]);
				if (percent == 0) {
					continue;
				} else {
					peopleCount[i] += (allCount - startIndex);
					String teacherStr = "";
					for (int j = startIndex; j < allCount; j++) {
						teacherStr += "、" + percentList.get(j).getTeacherName();
					}
					if (StringUtils.isEmpty(teacherMes[i])) {
						teacherMes[i] = "".equals(teacherStr) ? "" : teacherStr.substring(1);
					} else {
						teacherMes[i] += teacherStr;
					}
					break;
				}
			}
		}
		root.put("percents", arrPercent);
		root.put("peopleCount", peopleCount);
		root.put("teacherMes", teacherMes);
	}

	/**
	 * 更改用户问卷自定义查询百分比
	 * 
	 * @param eq
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateQuestionnairesPercentById")
	public Result updateQuestionnairesPercentById(EvlQuestionnaires eq) {
		Result result = new Result();
		if (eq.getQuestionnairesId() == null || eq.getQuestionnairesId() <= 0) {
			result.setCode(400);
			result.setMsg("筛选百分比保存失败！");
		} else {
			EvlQuestionnaires params = new EvlQuestionnaires();
			params.setQuestionnairesId(eq.getQuestionnairesId());
			params.setBack1(eq.getBack1());
			if (evlQuestionnairesService.update(params) == 1) {
				result.setCode(200);
			}
		}
		return result;
	}

	/**
	 * 根据年级id获取年级下的所有班级
	 * 
	 * @param questionnairesId
	 * @param gradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchClassListByGradeId")
	public List<SchClass> getSchClassListByGradeId(@RequestParam("questionnairesId") Integer questionnairesId, @RequestParam("gradeId") Integer gradeId) {
		EvlQuestionnaires eq = evlQuestionnairesService.findOne(questionnairesId);
		SchClass sc = new SchClass();
		sc.setOrgId(eq.getOrgId());
		sc.setGradeId(gradeId);
		sc.setSchoolYear(eq.getSchoolYear());
		sc.addOrder(" sort asc ");
		return schClassService.findAll(sc);
	}

	/**
	 * 加载意见反馈详情列表
	 * 
	 * @param suggestion
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadSuggestionListInfo")
	public List<EvlSuggestion> loadSuggestionListInfo(EvlSuggestion suggestion) {
		EvlQuestionnaires eq = evlQuestionnairesService.findOne(suggestion.getQuestionnairesId());
		EvlSuggestion params = new EvlSuggestion();
		params.setOrgId(eq.getOrgId());
		params.setQuestionnairesId(suggestion.getQuestionnairesId());
		params.setUserType(eq.getType() == null ? 1 : eq.getType());
		params.setGradeId(suggestion.getGradeId());
		if (suggestion.getClassId() != null) {
			params.setClassId(suggestion.getClassId());
		}
		params.addCustomCulomn(" classId,code,suggestName,content ");
		params.addCustomCondition(" and (content is not null and content<>'') ");
		List<EvlSuggestion> suggesionList = evlSuggestionService.findAll(params);
		List<SchClass> classList = this.getSchClassListByGradeId(suggestion.getQuestionnairesId(), suggestion.getGradeId());
		EvlQuestionMember member = new EvlQuestionMember();
		member.setOrgId(eq.getOrgId());
		member.setQuestionId(eq.getQuestionnairesId());
		member.setSchoolYear(eq.getSchoolYear());
		for (int index = 0; index < suggesionList.size(); index++) {
			EvlSuggestion evlSuggestion = suggesionList.get(index);
			for (SchClass schClass : classList) {
				if (evlSuggestion.getClassId().equals(schClass.getId())) {
					evlSuggestion.setStandby1(schClass.getName() + "," + StringUtils.abbr(schClass.getName(), 12, true, false));
					evlSuggestion.setStandby2(schClass.getSort());
					break;
				}
			}
			if (StringUtils.isEmpty(evlSuggestion.getStandby1())) {
				evlSuggestion.setStandby1(",");
			}
			member.setGradeId(evlSuggestion.getGradeId());
			member.setClassId(evlSuggestion.getClassId());
			member.setCode(evlSuggestion.getCode());
			EvlQuestionMember user = evlQuestionMemberService.findOne(member);
			if (user != null) {
				evlSuggestion.setFlago(user.getName());
				evlSuggestion.setFlags(StringUtils.abbr(user.getName(), 24, true, false));
			} else {
				evlSuggestion.setFlago("当前学年下的评教账号不存在");
				evlSuggestion.setFlags("不存在");
			}
			String titleMes = "【" + evlSuggestion.getSuggestName() + "】";
			if (StringUtils.isEmpty(evlSuggestion.getSuggestName())) {
				titleMes = "";
			}
			evlSuggestion.setContent(titleMes + evlSuggestion.getContent());
			for (int i = index + 1; i < suggesionList.size(); i++) {
				EvlSuggestion es = suggesionList.get(i);
				if (evlSuggestion.getCode().equals(es.getCode())) {
					evlSuggestion.setContent(evlSuggestion.getContent() + "=,=【" + es.getSuggestName() + "】" + es.getContent());
					suggesionList.remove(i--);
				}
			}
		}
		// 排序
		Collections.sort(suggesionList, new Comparator<EvlSuggestion>() {
			@Override
			public int compare(EvlSuggestion arg0, EvlSuggestion arg1) {
				if (arg0.getStandby2().intValue() < arg1.getStandby2().intValue()) {
					return -1;
				} else if (arg0.getStandby2().equals(arg1.getStandby2())) {
					return arg0.getCode().compareTo(arg1.getCode());
				} else {
					return 1;
				}
			}
		});
		return suggesionList;
	}
}
