/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.mainbo.jy.evl.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.Trigger;
import com.mainbo.jy.common.dao.BaseDAO;
import com.mainbo.jy.common.service.AbstractService;
import com.mainbo.jy.evl.bo.EvlIndicator;
import com.mainbo.jy.evl.bo.EvlLevelWeight;
import com.mainbo.jy.evl.bo.EvlOperateResult;
import com.mainbo.jy.evl.bo.EvlQuestionMember;
import com.mainbo.jy.evl.bo.EvlQuestionnaires;
import com.mainbo.jy.evl.dao.EvlOperateResultDao;
import com.mainbo.jy.evl.service.EvlIndicatorService;
import com.mainbo.jy.evl.service.EvlMetaService;
import com.mainbo.jy.evl.service.EvlOperateResultService;
import com.mainbo.jy.evl.service.EvlQuestionMemberService;
import com.mainbo.jy.evl.service.EvlQuestionnairesService;
import com.mainbo.jy.evl.statics.EvlMemberStatus;
import com.mainbo.jy.evl.utils.EchartUtil;
import com.mainbo.jy.evl.vo.EvlIndicatorVo;
import com.mainbo.jy.evl.vo.GradeAndClassVo;
import com.mainbo.jy.evl.vo.SubjectVo;
import com.mainbo.jy.schconfig.clss.bo.SchClassUser;
import com.mainbo.jy.schconfig.clss.service.SchClassService;
import com.mainbo.jy.schconfig.clss.service.SchClassUserService;
import com.mainbo.jy.utils.StringUtils;

/**
 * 评教结果表 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: EvlOperateResult.java, v 1.0 2016-05-12 Generate Tools Exp $
 */
@Service
@Transactional
public class EvlOperateResultServiceImpl extends AbstractService<EvlOperateResult, String> implements EvlOperateResultService {

	@Autowired
	private EvlOperateResultDao evlResultDao;
	@Autowired
	private EvlQuestionnairesService evlQuestionnairesService;
	@Autowired
	private SchClassService schClassService;
	@Autowired
	private EvlMetaService evlMetaService;
	
	@Autowired
	private SchClassUserService schClassUserService;
	
	@Autowired
	private EvlQuestionMemberService evlQuestionMemberService;
	
	@Autowired
	private EvlIndicatorService evlIndicatorService;
	/**
	 * @return
	 * @see com.mainbo.jy.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<EvlOperateResult, String> getDAO() {
		return evlResultDao;
	}

	@Override
	public List<EvlOperateResult> findResultListByIndicatorId(EvlIndicator indicator, String userCode) {
		EvlOperateResult result = new EvlOperateResult();
		result.setIndicatorId(indicator.getId());
		result.setCode(userCode);
		result.setQuestionnairesId(indicator.getQuestionnairesId());
		return this.findAll(result);
	}

	@Override
	public List<EvlOperateResult> findOperateResultListByIndicatorLevel(EvlQuestionnaires eq, EvlOperateResult result) {
		// 学生评教等级
		EvlOperateResult operateResult = new EvlOperateResult();
		operateResult.setOrgId(eq.getOrgId());
		operateResult.setQuestionnairesId(eq.getQuestionnairesId());
		operateResult.setTeacherId(result.getTeacherId());
		if (eq.getIndicatorType().equals(EvlQuestionnaires.indicator_type_zhengti)) {
			if (result.getGradeId() != null) {
				operateResult.setGradeId(result.getGradeId());
				if (result.getClassId() != null) {
					operateResult.setClassId(result.getClassId());
					if (result.getSubjeId() != null) {
						operateResult.setSubjeId(result.getSubjeId());
					}
				}
			}
			operateResult.setIndicatorLevel(EvlQuestionnaires.indicator_type_zhengti);
			operateResult.addOrder(" gradeId asc,classSort asc ");
			operateResult.addCustomCulomn(" code,studentName,gradeId,classId,subjeId,resultLevel ");
		} else {
			operateResult.setGradeId(result.getGradeId());
			operateResult.setClassId(result.getClassId());
			operateResult.setSubjeId(result.getSubjeId());
			operateResult.setIndicatorId(result.getIndicatorId());
			operateResult.addOrder(" code asc ");
			operateResult.addCustomCulomn(" code,resultLevel,resultScore ");
		}
		return this.findAll(operateResult);
	}

	@Override
	public boolean submit(Boolean submitStatus,Integer questionId,String userCode) {
		//校验数据填写完整
		EvlQuestionnaires question = evlQuestionnairesService.findOne(questionId);
		List<EvlIndicator> levelIndicator = new ArrayList<EvlIndicator>();
		if(question!=null && question.getIndicatorType() == EvlQuestionnaires.indicator_type_oneLevel){
			levelIndicator = evlIndicatorService.getLevelIndicator(questionId, EvlQuestionnaires.indicator_type_oneLevel);
		}else if(question!=null&&question.getIndicatorType() ==EvlQuestionnaires.indicator_type_twoLevel){
			levelIndicator = evlIndicatorService.getLevelIndicator(questionId, EvlQuestionnaires.indicator_type_twoLevel);
		}
		int count = levelIndicator.size();
		
		EvlOperateResult rmodel = new EvlOperateResult();
		rmodel.setQuestionnairesId(questionId);
		rmodel.setCode(userCode);
		rmodel.addCustomCulomn("id");
		rmodel = findOne(rmodel);
		//验证没有进行任何评教或提交不完全
		if(rmodel == null || !this.checkIndicatorCount(questionId,userCode,count)){
			return false;
		}
		EvlQuestionMember model = new EvlQuestionMember();
		model.setQuestionId(questionId);
		model.setCode(userCode);
		EvlQuestionMember memberModel = evlQuestionMemberService.findOne(model);
		if(memberModel!=null){
			Integer status = EvlMemberStatus.tianxiewenjuan.getValue();	
			if(submitStatus){//用户提交
				status =  EvlMemberStatus.tijiaowenjuan.getValue();
			}
			model.setStatus(status);
			model.setId(memberModel.getId());
			evlQuestionMemberService.update(model);			
		}
		evlResultDao.deleteRepeatResult(questionId,userCode);
		return true;
	}
	
	@Override
	public Map<String, Object> findStudentResultListByZhengti(Integer questionId,List<EvlOperateResult> resultList, List<EvlLevelWeight> levelList, List<GradeAndClassVo> gradeMeta) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		EvlQuestionnaires question = evlQuestionnairesService.findOne(questionId);
		List<SubjectVo> subjectList = evlMetaService.findSubjectByPhaseId(question.getPhaseId(),question.getOrgId());
		for (EvlOperateResult eor : resultList) {
			eor.setStudentName(eor.getStudentName() + "=,=" + StringUtils.abbr(eor.getStudentName(), 12, true, false));
			for (GradeAndClassVo gradeVo : gradeMeta) {
				if (eor.getGradeId().equals(gradeVo.getId())) {
					String gradeName = gradeVo.getName() + "（" + schClassService.findOne(eor.getClassId()).getName() + "）";
					eor.setFlago(gradeName + "=,=" + StringUtils.abbr(gradeName, 16, true, false));
					break;
				}
			}
			for (SubjectVo subjectVo : subjectList) {
				if (eor.getSubjeId().equals(subjectVo.getId())) {
					eor.setFlags(subjectVo.getName() + "=,=" + StringUtils.abbr(subjectVo.getName(), 16, true, false));
					break;
				}
			}
			if (StringUtils.isEmpty(eor.getFlago())) {
				eor.setFlago("=,=");
			}
			if (StringUtils.isEmpty(eor.getFlags())) {
				eor.setFlags("=,=");
			}
			if (CollectionUtils.isEmpty(levelList)) {
				eor.setStandby1("无");
			} else {
				for (EvlLevelWeight level : levelList) {
					if (eor.getResultLevel().equals(level.getId())) {
						// 返回名称
						eor.setStandby1(level.getLevelName());
						// 统计人数
						if (StringUtils.isNotEmpty(level.getFlago())) {
							level.setFlago((Integer.valueOf(level.getFlago()).intValue() + 1) + "");
						} else {
							level.setFlago("1");
						}
						break;
					}
				}
			}
		}
		// 柱状图统计数据
		String[] levelSection = new String[levelList.size()];
		Double[] percentCount = new Double[levelList.size()];
		for (int i = 0; i < levelList.size(); i++) {
			EvlLevelWeight level = levelList.get(i);
			levelSection[i] = level.getLevelName();
			double percentNumber = 0.0;
			if (StringUtils.isNotEmpty(level.getFlago())) {
				percentNumber = Double.valueOf(new DecimalFormat("0.00").format(Integer.valueOf(level.getFlago()).intValue() * 100.00 / resultList.size()));
			} else {
				level.setFlago("0");
			}
			level.setFlags(percentNumber + "");
			percentCount[i] = percentNumber;
		}

		returnMap.put("studentList", resultList);
		returnMap.put("levelList", levelList);
		Option option = EchartUtil.packageBarOption("评价结果分布率图表", levelSection, "分布率", percentCount);
		option.getTooltip().trigger(Trigger.axis).formatter("{a}<br/>{b}：{c}%");
		option.getSeries().get(0).getItemStyle().getNormal().getLabel().formatter("{c}%");
		returnMap.put("option", option);
		return returnMap;
	}

	@Override
	public int supplementResultTeacherId(EvlQuestionnaires questionnaires) {
		int number = 0;
		EvlOperateResult eor = new EvlOperateResult();
		eor.setOrgId(questionnaires.getOrgId());
		eor.setQuestionnairesId(questionnaires.getQuestionnairesId());
		eor.addCustomCondition(" and teacherId is null ");
		eor.addCustomCulomn("id,classId,subjeId");
		List<EvlOperateResult> resultList = this.findAll(eor);//所有结果
		
		Map<String,Integer> teachers = new HashMap<String,Integer>();
		for (EvlOperateResult result : resultList) {
			String key = "c_"+result.getClassId()+"_s_"+result.getSubjeId()+"_y_"+questionnaires.getSchoolYear();
			Integer teacherId = teachers.get(key);
			if(teacherId == null){
				SchClassUser scu = new SchClassUser();
				scu.setClassId(result.getClassId());
				scu.setSubjectId(result.getSubjeId());
				scu.setSchoolYear(questionnaires.getSchoolYear());
				scu.addCustomCulomn("tchId");
				SchClassUser teacher = schClassUserService.findOne(scu);
				if(teacher != null && (teacherId = teacher.getTchId()) !=null){
					teachers.put(key,teacherId);
				}else{
					teacherId = -1;
					teachers.put(key,teacherId);
				}
			}
			
			if (teacherId != -1 ) {
				EvlOperateResult params = new EvlOperateResult();
				params.setId(result.getId());
				params.setTeacherId(teacherId);
				this.update(params);
				number++;
			}
		}
		return number;
	}

	@Override
	public List<EvlOperateResult> findAnalyzeTeacherList(EvlQuestionnaires eai) {
		EvlOperateResult paramRes = new EvlOperateResult();
		paramRes.setOrgId(eai.getOrgId());
		paramRes.setQuestionnairesId(eai.getQuestionnairesId());
		paramRes.addCustomCulomn("DISTINCT subjeId, classId, gradeId,indicatorId");//所有老师
		List<EvlOperateResult> resultList = this.findAll(paramRes);
		if(resultList != null){
			List<EvlOperateResult> valiadResult = new ArrayList<EvlOperateResult>(resultList.size());
			for(EvlOperateResult tch : resultList){
				tch.setOrgId(eai.getOrgId());
				tch.setQuestionnairesId(eai.getQuestionnairesId());
				List<EvlOperateResult> tchresultScores = evlResultDao.findAnalyzeTeacherResultScore(tch);
				if(tchresultScores != null){
					for(EvlOperateResult or : tchresultScores){
							valiadResult.add(or);
					}
				}
			}
			return valiadResult;
		}
				
		return null;
	}

	@Override
	public void statisticsSection(Integer[] peopleSection, double score) {
		double oldScore = score;
		score = Math.round(score);
		if (score <= 100 && score >= 95) {
			peopleSection[0] += 1;
		} else if (score <= 94 && score >= 90) {
			peopleSection[1] += 1;
		} else if (score <= 89 && score >= 85) {
			peopleSection[2] += 1;
		} else if (score <= 84 && score >= 80) {
			peopleSection[3] += 1;
		} else if (score <= 79 && score >= 75) {
			peopleSection[4] += 1;
		} else if (score <= 74 && score >= 70) {
			peopleSection[5] += 1;
		} else if (score <= 69 && score >= 65) {
			peopleSection[6] += 1;
		} else if (score <= 64 && oldScore >= 60) {
			peopleSection[7] += 1;
		} else {
			peopleSection[8] += 1;
		}
	}
	@Override
	public void batchDelete(Integer questionnairesId) {
		evlResultDao.batchDelete(questionnairesId);
		
	}
	@Override
	public boolean checkIndicatorCount(Integer questionId, String userCode,int count) {
		return this.evlResultDao.checkIndicatorCount(questionId, userCode, count);
	}
	/**
	 * @param eq
	 * @return
	 * @see com.mainbo.jy.evl.service.EvlOperateResultService#findAnalyzeIndicatorList(com.mainbo.jy.evl.bo.EvlQuestionnaires)
	 */
	@Override
	public List<EvlOperateResult> findAnalyzeIndicatorList(EvlQuestionnaires eq) {
		if(eq != null && eq.getQuestionnairesId() != null){
			EvlOperateResult model = new EvlOperateResult();
			model.setQuestionnairesId(eq.getQuestionnairesId());
			model.setOrgId(eq.getOrgId());
			return evlResultDao.findAnalyzeIndicatoResultScore(model);
		}
		return null;
		
	}
	/**
	 * @param rs
	 * @see com.mainbo.jy.evl.service.EvlOperateResultService#saveOrUpdateResult(com.mainbo.jy.evl.bo.EvlOperateResult)
	 */
	@Override
	public boolean saveOrUpdateResult(EvlOperateResult model) {
		if(model !=null && model.getQuestionnairesId() != null 
				&& model.getCode() != null && model.getSubjeId() != null){
			EvlOperateResult temp = new EvlOperateResult();
			temp.setQuestionnairesId(model.getQuestionnairesId());
			temp.setCode(model.getCode());
			temp.setIndicatorId(model.getIndicatorId());
			temp.setSubjeId(model.getSubjeId());
			temp.setTeacherId(model.getTeacherId());
			
			EvlOperateResult buffer = findOne(temp);
			if (buffer == null) {
				model.setCrtDttm(new Date());
				save(model);
				changeMemberUserStatus(model.getQuestionnairesId(), model.getOrgId(), model.getCode(), EvlMemberStatus.tianxiewenjuan.getValue());
				changeQuestionEnable(model.getQuestionnairesId());
			} else {
				model.setId(buffer.getId());
				update(model);
			}
			return true;
		}
		return false;
	}
	
	// 保存成绩成功后，更新用户填报状态
		private void changeMemberUserStatus(Integer questionId, Integer orgId, String userCode, Integer status) {
			if (questionId != null && userCode != null) {// 结果表为空，修改用户填报状态为填写之前
				EvlQuestionMember member = new EvlQuestionMember();
				member.setOrgId(orgId);
				member.setQuestionId(questionId);
				member.setCode(userCode);
				EvlQuestionMember memberModel = evlQuestionMemberService.findOne(member);
				if (memberModel != null) {
					member.setId(memberModel.getId());
					member.setStatus(status);
					evlQuestionMemberService.update(member);
				}
			}
		}
		
		/**
		 * 修改问卷enable状态
		 * 
		 * @param questionnairesId
		 */
		private void changeQuestionEnable(Integer questionnairesId) {
			EvlQuestionnaires currentQuestion = evlQuestionnairesService.findOne(questionnairesId);
			if (currentQuestion != null) {
				if(currentQuestion.getEnable() != EvlQuestionnaires.DISABLE){
					currentQuestion.setEnable(EvlQuestionnaires.DISABLE);
					evlQuestionnairesService.update(currentQuestion);
				}
			}
		}
		@Override
		public EvlIndicatorVo getAllIndicatorList(EvlQuestionnaires evlQuestionnaires, String userCode) {
			EvlIndicatorVo allIndicatorVo = new EvlIndicatorVo();// 返回结果
			Integer indicatorType = evlQuestionnaires.getIndicatorType();
			// 获取所有指标数据
			List<EvlIndicatorVo> evlIndicatorVoVoList = new ArrayList<EvlIndicatorVo>();
			// 获取一级指标集合
			List<EvlIndicator> firstEvlIndicatorVoList = evlIndicatorService.getOneLevelIndicator(evlQuestionnaires);
			switch (evlQuestionnaires.getIndicatorType()) {
			case 0:// 整体评教
					// 查询结果评教
				EvlOperateResult evlOperateResult = new EvlOperateResult();
				evlOperateResult.setQuestionnairesId(evlQuestionnaires.getQuestionnairesId());
				evlOperateResult.setCode(userCode);
				evlOperateResult.setOrgId(evlQuestionnaires.getOrgId());
				List<EvlOperateResult> zhengtiResultList = this.findAll(evlOperateResult);
				if (zhengtiResultList == null) {
					allIndicatorVo.setResultList(new ArrayList<EvlOperateResult>());
				} else {
					allIndicatorVo.setResultList(zhengtiResultList);
				}
				for (EvlIndicator indicator : firstEvlIndicatorVoList) {
					EvlIndicatorVo firstEvlIndicatorVo = new EvlIndicatorVo();// 返回对象
					firstEvlIndicatorVo.setIndicator(indicator);
					evlIndicatorService.secondEvlIndicatorVoList(indicatorType, indicator, firstEvlIndicatorVo);
					evlIndicatorVoVoList.add(firstEvlIndicatorVo);
				}
				// 返回
				allIndicatorVo.setChildIndicators(evlIndicatorVoVoList);
				break;
			case 1:// 一级指标进行评教
				for (EvlIndicator indicator : firstEvlIndicatorVoList) {
					EvlIndicatorVo firstEvlIndicatorVo = new EvlIndicatorVo();// 返回对象
					firstEvlIndicatorVo.setIndicator(indicator);
					// 指标对应结果
					List<EvlOperateResult> firstIndicatorResultList = this.findResultListByIndicatorId(indicator, userCode);
					firstEvlIndicatorVo.setResultList(firstIndicatorResultList);
					evlIndicatorService.secondEvlIndicatorVoList(indicatorType, indicator, firstEvlIndicatorVo);
					evlIndicatorVoVoList.add(firstEvlIndicatorVo);
				}
				// 返回
				allIndicatorVo.setChildIndicators(evlIndicatorVoVoList);
				break;
			case 2:// 二级指标进行评教
				for (EvlIndicator indicator : firstEvlIndicatorVoList) {
					EvlIndicatorVo firstEvlIndicatorVo = new EvlIndicatorVo();// 返回对象
					firstEvlIndicatorVo.setIndicator(indicator);
					this.secondEvlIndicatorVoList(indicatorType, indicator, firstEvlIndicatorVo, userCode);
					evlIndicatorVoVoList.add(firstEvlIndicatorVo);
				}
				allIndicatorVo.setChildIndicators(evlIndicatorVoVoList);
				break;
			default:
				break;
			}
			return allIndicatorVo;
		}
		@Override
		public EvlIndicatorVo secondEvlIndicatorVoList(Integer indicatorType, EvlIndicator EvlIndicator, EvlIndicatorVo firstEvlIndicatorVo, String userCode) {
			List<EvlIndicator> secondEvlIndicatorList = evlIndicatorService.getTwoLevelIndicator(EvlIndicator);
			List<EvlIndicatorVo> childKpiIndicatorVoList = new ArrayList<>();
			for (EvlIndicator evlIndicator : secondEvlIndicatorList) {
				EvlIndicatorVo vo2 = new EvlIndicatorVo();// 二级指标vo
				vo2.setIndicator(evlIndicator);
				childKpiIndicatorVoList.add(vo2);
				// 指标对应结果
				List<EvlOperateResult> secondIndicatorResultList = this.findResultListByIndicatorId(evlIndicator, userCode);
				vo2.setResultList(secondIndicatorResultList);
			}
			firstEvlIndicatorVo.setChildIndicators(childKpiIndicatorVoList);
			return firstEvlIndicatorVo;
		}
		
		/**
		 * 
		 * @param paramRes
		 * @return
		 */
		@Override
		public List<EvlOperateResult> findAnalyzeResultScore(EvlOperateResult paramRes){
			return evlResultDao.findAnalyzeResultScore(paramRes);
		}
}
