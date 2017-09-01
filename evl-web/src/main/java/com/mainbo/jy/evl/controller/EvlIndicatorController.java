package com.mainbo.jy.evl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainbo.jy.common.utils.WebThreadLocalUtils;
import com.mainbo.jy.common.vo.Result;
import com.mainbo.jy.common.web.controller.AbstractController;
import com.mainbo.jy.evl.bizservice.EvlquestionBizService;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.bo.EvlSuggestionType;
import com.mainbo.jy.evl.service.EvlIndicatorService;
import com.mainbo.jy.evl.service.EvlLevelWeightService;
import com.mainbo.jy.evl.service.EvlOperateResultService;
import com.mainbo.jy.evl.service.EvlQuestionnairesService;
import com.mainbo.jy.evl.service.EvlSuggestionTypeService;
import com.mainbo.jy.evl.utils.ExcleUtils;
import com.mainbo.jy.evl.utils.WordUtil;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.uc.bo.User;
import com.mainbo.jy.uc.utils.SessionKey;
import com.mainbo.jy.utils.Identities;
import com.mainbo.jy.utils.StringUtils;

/**
 * 问卷基本设置controller
 * 
 * <pre>
 * 	enable：如果已经发布,但未评教，可继续操作。enable设为1
 * 	如果已有评教操作，enable设为0.作为指标CRUD操作判断条件。
 * </pre>
 * 
 * @author ljh
 * @version $Id: EvlIndicatorController.java, v 1.0 2016年5月9日 下午3:53:08 ljh Exp
 *          $
 */
@Controller
@RequestMapping("/jy/evl/indicator")
public class EvlIndicatorController extends AbstractController {
	@Autowired
	private EvlIndicatorService evlIndicatorService;
	@Autowired
	private EvlQuestionnairesService evlQuestionnairesService;
	@Autowired
	private EvlquestionBizService evlquestionBizService;
	@Autowired
	private EvlLevelWeightService evlLevelWeightService;
	@Autowired
	private EvlSuggestionTypeService evlSuggestionTypeService;
	@Autowired
	private EvlOperateResultService evlOperateResultService;

	/**
	 * 获取所有指标：管理人员
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/getAllIndicator")
	@ResponseBody
	public EvlIndicatorVo getAllIndicator(Integer questionnairesId) {
		EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		return evlIndicatorService.getAllIndicatorList(evlQuestionnaires);// 指标
	}

	/**
	 * 获取所有指标：评教人员
	 * 
	 * @param questionId
	 * @param userId
	 *            :参数列表【1】：评教人userId
	 * @return
	 */
	@RequestMapping("/input/getAllIndicator")
	@ResponseBody
	public EvlIndicatorVo getAllIndicator(Integer questionnairesId, String userCode) {
		EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		return evlOperateResultService.getAllIndicatorList(evlQuestionnaires, userCode);// 指标
	}

	/**
	 * 增加一级指标
	 * 
	 * @param m
	 * @param model
	 * @param questionnairesId
	 * @return
	 */
	@RequestMapping("/setting/add_one_indicators")
	@ResponseBody
	public Result add_one_indicators(Model m, EvlIndicator model, @RequestParam(required = true) Integer questionnairesId) {
		EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		if (evlQuestionnaires.getEnable() != null && evlQuestionnaires.getEnable() == 0) {
			return new Result("问卷已评教，不允许操作！", true);
		}
		model.setEnable(1);
		model.setQuestionnairesId(questionnairesId);
		model.setCrtDttm(new Date());
		model.setCrtId(getCurrentUser().getId());
		model.setId(Identities.uuid2());
		evlIndicatorService.save(model);
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 删除一级指标
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/del_one_indicators")
	@ResponseBody
	public Result del_one_indicators(Model m, EvlIndicator model) {
		Result result = new Result();
		Integer depth = 2;
		if (model.getLevel() != null) {
			depth -= model.getLevel();
		}
		// 指标ID为空
		if (StringUtils.isEmpty(model.getId())) {
			result.setCode(400);
			result.setMsg("指标ID为空");
			result.setData(model);
			return result;
		}
		evlIndicatorService.deleteIndicatorLevel(model.getId(), depth);
		evlIndicatorService.delete(model.getId());
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 删除二级指标
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/del_two_indicators")
	@ResponseBody
	public Result del_two_indicators(Model m, EvlIndicator model) {
		Result result = new Result();
		// 指标ID为空
		if (StringUtils.isEmpty(model.getId())) {
			result.setCode(400);
			result.setMsg("指标ID为空");
			result.setData(model);
			return result;
		}
		evlIndicatorService.delete(model.getId());
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 编辑一级指标
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/one_indicators_edit")
	@ResponseBody
	public Result one_indicators_edit(Model m, EvlIndicator model) {
		EvlIndicator newIndicator = evlIndicatorService.findOne(model.getId());
		newIndicator.setScoreTotal(model.getScoreTotal());
		newIndicator.setTitle(model.getTitle());
		newIndicator.setLastupDttm(new Date());
		newIndicator.setLastupId(getCurrentUser().getId());
		evlIndicatorService.update(newIndicator);
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 新增二级指标
	 * 
	 * @param m
	 * @param model
	 * @param questionnairesId
	 * @return
	 */
	@RequestMapping("/setting/add_two_indicators")
	@ResponseBody
	public Result add_two_indicators(Model m, EvlIndicator model, Integer questionnairesId) {
		// EvlQuestionnaires evlQuestionnaires =
		// evlQuestionnairesService.findOne(questionnairesId);
		// if(evlQuestionnaires.getEnable()==0){
		// return new Result("问卷已评教，不允许操作！",true);
		// }
		model.setEnable(1);
		model.setQuestionnairesId(questionnairesId);
		model.setCrtDttm(new Date());
		model.setCrtId(getCurrentUser().getId());
		model.setId(Identities.uuid2());
		evlIndicatorService.save(model);
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 编辑二级指标
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/two_indicators_edit")
	public Result two_indicators_edit(Model m, EvlIndicator model) {
		EvlIndicator newIndicator = evlIndicatorService.findOne(model.getId());
		newIndicator.setScoreTotal(model.getScoreTotal());
		newIndicator.setTitle(model.getTitle());
		newIndicator.setLastupDttm(new Date());
		newIndicator.setLastupId(getCurrentUser().getId());
		evlIndicatorService.update(newIndicator);
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	/**
	 * 复制指标
	 * 
	 * @param oldId
	 * @param newId
	 * @return
	 */
	@RequestMapping("/setting/copyIndicator")
	public Result copyIndicator(Integer oldId, Integer newId) {
		evlIndicatorService.copyIndicator(oldId, newId);
		return null;
	}

	/**
	 * 校验更新指标分数
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateItemWeight")
	public void updateItemWeight(String itemId, Double weight) {
		EvlIndicator model = this.evlIndicatorService.findOne(itemId);
		if (model != null) {
			model.setScoreTotal(weight);
			this.evlIndicatorService.update(model);
		}
	}

	/**
	 * 获取指标详情
	 * 
	 * @param m
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting/getIndicator")
	@ResponseBody
	public Result getIndicator(Model m, EvlIndicator model) {
		model = evlIndicatorService.findOne(model.getId());
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setData(model);
		return result;
	}

	private User getCurrentUser() {
		// 获得当前用户Id
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		return user;
	}

	/**
	 * 生成word
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/setting/downloadHtmlToWord")
	public void downloadHtmlToWord(Integer questionnairesId, HttpServletResponse response) throws IOException {
		String filePath = ""; // 文件路径
		String fileOnlyName = ""; // 文件唯一名称
		/** 用于组装word页面需要的数据 */
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 问卷内容
		EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		if (StringUtils.isEmpty(evlQuestionnaires.getTitle())) {
			evlQuestionnaires.setTitle("");
		}
		if (StringUtils.isEmpty(evlQuestionnaires.getDirections())) {
			evlQuestionnaires.setDirections("");
		}
		// 指标{一级和二级指标数据结构}
		EvlIndicatorVo indicatorVo = evlIndicatorService.getAllIndicatorList(evlQuestionnaires);
		if (evlQuestionnaires.getIndicatorType() == EvlQuestionnaires.indicator_type_zhengti) {
			List<EvlIndicatorVo> oneList = indicatorVo.getChildIndicators();
			for (EvlIndicatorVo oneVo : oneList) {
				List<EvlIndicatorVo> twoIndicator = oneVo.getChildIndicators();
				if (!CollectionUtils.isEmpty(twoIndicator)) {
					oneVo.setTwoindicator(oneVo.getChildIndicators().get(0).getIndicator());
					twoIndicator.remove(0);
				} else {
					EvlIndicator temp = new EvlIndicator();
					oneVo.setTwoindicator(temp);
				}
				break;// 取出处理第一项
			}
			EvlIndicatorVo firstIndicatorVo = indicatorVo.getChildIndicators().get(0);
			dataMap.put("firstIndicatorVo", firstIndicatorVo);
			indicatorVo.getChildIndicators().remove(0);
		}
		// 二三级指标
		if (indicatorVo != null) {
			List<EvlIndicatorVo> oneList = indicatorVo.getChildIndicators();
			for (EvlIndicatorVo oneVo : oneList) {
				List<EvlIndicatorVo> twoIndicator = oneVo.getChildIndicators();
				if (!CollectionUtils.isEmpty(twoIndicator)) {
					oneVo.setTwoindicator(oneVo.getChildIndicators().get(0).getIndicator());
					twoIndicator.remove(0);
				} else {
					EvlIndicator temp = new EvlIndicator();
					oneVo.setTwoindicator(temp);
				}
			}
		}
		// 建议类型
		List<EvlSuggestionType> suggesttypeList = new ArrayList<EvlSuggestionType>();
		if (evlQuestionnaires.getIscollect() == EvlQuestionnaires.iscollect_shouji) {
			EvlSuggestionType suggesttype = new EvlSuggestionType();
			suggesttype.setQuestionnairesId(questionnairesId);
			suggesttypeList = evlSuggestionTypeService.findAll(suggesttype);
			evlQuestionnaires.setFlago("select");
		}
		dataMap.put("suggesttypeList", suggesttypeList);
		// 通过方案获取学科列表
		List<SubjectVo> subjectList = evlquestionBizService.findSubjectByQuestionId(questionnairesId);

		dataMap.put("subjectList", subjectList);
		dataMap.put("question", evlQuestionnaires);
		dataMap.put("indicatorVo", indicatorVo);
		// 表格动态学科长度
		Double length = 899 * (subjectList.size()) + 1668.0 + 4480.0;
		Double rightTitleLength = 899.0 * (subjectList.size());
		String resultLength = length.toString();
		String righttitleLength = rightTitleLength.toString();
		String rightCellcount = subjectList.size() + "";
		dataMap.put("resultLength", resultLength);// 表格总长度
		dataMap.put("righttitleLength", righttitleLength);// 右侧长度
		dataMap.put("rightCellcount", rightCellcount);// 右侧长度

		// 文件唯一名称
		fileOnlyName = evlQuestionnaires.getTitle() + ".doc";
		/** 生成word */
		Integer indicatorType = evlQuestionnaires.getIndicatorType();
		String returnStr = "";
		switch (indicatorType) {
		case 0:
			returnStr = "export_indicator_all.ftl";
			// 查询等级权重
			EvlLevelWeight level = new EvlLevelWeight();
			level.setQuestionnairesId(questionnairesId);
			List<EvlLevelWeight> levelList = evlLevelWeightService.findAll(level);
			dataMap.put("levelList", levelList);
			break;
		case 1:
			returnStr = "export_indicator_one.ftl";
			break;
		case 2:
			returnStr = "export_indicator_two.ftl";
			break;
		default:
			break;
		}
		WordUtil.createWord(dataMap, returnStr, filePath, fileOnlyName, response);
	}

	/**
	 * 生成excel
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/setting/downloadIndicatorExcel")
	public void downloadIndicatorExcel(Integer questionnairesId, HttpServletResponse response) throws IOException {

		Map<String, Object> root = new HashMap<String, Object>();
		// 问卷
		EvlQuestionnaires evlQuestionnaires = evlQuestionnairesService.findOne(questionnairesId);
		root.put("title", evlQuestionnaires.getTitle() == null ? "无标题" : evlQuestionnaires.getTitle());
		root.put("question", evlQuestionnaires);
		// 指标
		EvlIndicatorVo indicatorVo = evlIndicatorService.getAllIndicatorList(evlQuestionnaires);
		root.put("indicatorVo", indicatorVo.getChildIndicators());
		// 学科
		List<SubjectVo> subjectList = evlquestionBizService.findSubjectByQuestionId(questionnairesId);
		root.put("subjectList", subjectList);

		// 意见、建议
		List<EvlSuggestionType> suggesttypeList = new ArrayList<EvlSuggestionType>();
		if (evlQuestionnaires.getIscollect() == EvlQuestionnaires.iscollect_shouji) {
			EvlSuggestionType suggesttype = new EvlSuggestionType();
			suggesttype.setQuestionnairesId(questionnairesId);
			suggesttypeList = evlSuggestionTypeService.findAll(suggesttype);
			evlQuestionnaires.setFlago("select");
		}
		root.put("suggesttypeList", suggesttypeList);
		root.put("type", evlQuestionnaires.getIndicatorType());
		switch (evlQuestionnaires.getIndicatorType()) {
		case 0:
			root.put("template", "evl_indicator_all.xls");
			// 查询等级权重
			EvlLevelWeight level = new EvlLevelWeight();
			level.setQuestionnairesId(questionnairesId);
			List<EvlLevelWeight> levelList = evlLevelWeightService.findAll(level);
			root.put("levelList", levelList);
			break;
		case 1:
			root.put("template", "evl_indicator_one.xls");
			break;
		case 2:
			root.put("template", "evl_indicator_two.xls");
			break;
		default:
			break;
		}
		ExcleUtils.download_questionnaires(response, root);
	}
}