package com.tmser.schevaluation.evl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tmser.schevaluation.common.service.BaseService;
import com.tmser.schevaluation.evl.bo.EvlAnalyzeTeacher;
import com.tmser.schevaluation.evl.bo.EvlOperateResult;
import com.tmser.schevaluation.evl.bo.EvlQuestionnaires;
import com.tmser.schevaluation.evl.vo.EvlAnalyzeIndicatorVo;
import com.tmser.schevaluation.evl.vo.GradeAndClassVo;
import com.tmser.schevaluation.evl.vo.SubjectVo;

/**
 * 评价分析教师服务层接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author gengqianfeng
 * @version $Id: EvlAnalyzeTeacherService.java, v 1.0 2016年5月10日 下午5:25:51
 *          gengqianfeng Exp $
 */
public interface EvlAnalyzeTeacherService extends BaseService<EvlAnalyzeTeacher, Integer> {

	/**
	 * 结束后计算指标分数，保存或修改分析教师数据
	 * 
	 * @param eq
	 */
	public void saveOrUpdateAnalyzeTeacher(EvlQuestionnaires eq);

	/**
	 * 根据问卷获取分析教师姓名列表
	 * 
	 * @param eq
	 * @return
	 */
	public List<EvlAnalyzeTeacher> findAnalyzeTeacherListByQuestionnaire(EvlQuestionnaires eq,EvlAnalyzeTeacher tea);

	/**
	 * 年级分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findTeacherListOfGrade(Integer questionId, boolean isDownload);

	/**
	 * 学科分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findTeacherListOfSubject(Integer questionId, boolean isDownload);

	/**
	 * 教龄性别分析对比列表
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findTeacherListBySchoolAgeSex(Integer questionId, boolean isDownload);

	/**
	 * 班主任分析对比列表
	 * 
	 * @param questionId
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> findDirectorTeacherList(Integer questionId, Integer gradeId, boolean isDownload);

	/**
	 * 获取问卷问题列表
	 * 
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> findAnalyzeProblemsList(Integer questionId);

	/**
	 * 下载分析报告详情页
	 * 
	 * @param response
	 * @param eat
	 * @param root
	 */
	public void downLoadAnalyzeInfo(HttpServletResponse response, EvlAnalyzeTeacher eat, Map<String, Object> root);

	/**
	 * 获取过滤后的年级列表
	 * 
	 * @param question
	 * @return
	 */
	public List<GradeAndClassVo> getGradeVoList(EvlQuestionnaires question);

	/**
	 * 获取过滤后的学科列表
	 * 
	 * @param question
	 * @return
	 */
	public List<SubjectVo> getSubjectVoList(EvlQuestionnaires question);

	/**
	 * 教师总平均分
	 * 
	 * @param eat
	 * @return 
	 */
	List<EvlOperateResult> getTeacherResultScoreByTeacherId(EvlAnalyzeTeacher eat,int key);
	/**
	 * 获取教师指标统计列表
	 * 
	 * @param eq
	 * @param teacherId
	 * @return
	 */
	List<EvlAnalyzeIndicatorVo> findResultUserIndicator(EvlQuestionnaires eq,Integer teacherId);
	/**
	 * 获取教师指标统计列表详情
	 * 
	 * @param eq
	 * @param teacherId
	 * @return
	 */
	List<EvlAnalyzeIndicatorVo> findResultUserIndicatorInfo(EvlQuestionnaires eq, EvlAnalyzeTeacher eat);

	/**
	 * 下载
	 * @param response
	 * @param eat
	 */
	void downLoadResultInfo(HttpServletResponse response, EvlAnalyzeTeacher eat);

	/**
	 * 获取分析统计问卷详情
	 * @param eat
	 * @param isDownload
	 * @return
	 */
	Map<String, Object> findResultQuestionnairesInfo(EvlAnalyzeTeacher eat,boolean isDownload);

}
