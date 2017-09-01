<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="schoolYearHead" value="${empty questionnaires.title ? '无标题' : questionnaires.title }" />
<ui:htmlHeader title="${schoolYearHead}"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	} 
	.chosen-container .chosen-drop{
	width:98%;}
	</style>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop modelName="${schoolYearHead}"></ui:tchTop>
	</div>
	<div id="resultPersent_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">提示</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span>
					<strong>您各项等级所占比例之和≠100%，请调整！</strong>
				</div>
			</div>
		</div> 
	</div>
	<div id="suggestionGradeTable_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">意见建议反馈</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="suggestionGradeTable_info">
					<div class="suggestion_info">
						<div class="suggestion_info_left" data-id="">
							 年级：一年
						 </div>
						 <div class="suggestion_info_right"> 	
						 	<label>班级：</label>
						 	<div class="a">								
								<select class="chosen-select-deselect" style="width: 120px; height: 25px;" id="sugestionClassSel">
									<option value="" selected="selected">全部班级</option>
								</select>  
							</div>
						 </div>
					</div>
					<div class="suggestion_info_bottom">										
					</div>
				</div>
			</div>
		</div> 
	</div>
	<div id="memberShowDialog" style="position: absolute;width: 115px;height: auto; background: #fff;border: 1px #B7B7B7 solid;box-shadow: 1px 1px 1px #9F9F9F;padding: 5px;display: none;z-index: 100;">
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${not empty mark && mark=='pt'}">
			<jy:nav id="evl_analyze_pt">
				<jy:param name="questionnairesId" value="${questionnaires.questionnairesId}"></jy:param>
				<jy:param name="mark" value="${mark}"></jy:param>
			</jy:nav>
			&gt;&nbsp;<ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/>
		</c:if>
		<c:if test="${empty mark}">
			<jy:nav id="evl_analyze"></jy:nav>
			&gt;&nbsp;<ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/>
		</c:if>
	</div>
	<div class="flow_chart_wrap">
		<div class="analysis_report">
			<div class="analysis_report_top">
				<h3 class="analysis_report_h3">
					<span>${schoolYearHead }</span> 					
				</h3>
				<div class="clear"></div>
				<h3 style="height:20px;line-height:20px;padding-top:20px;text-align:center;font-size:18px;">
					<span>分析报告</span>
				</h3>
				<div style="float:left;height:38px;width:100%;">
					<input type="button" class="download_res" />
				</div>
			</div>
			<div class="analysis_report_bottom">
				<div class="analysis_report_div">
					<div class="analysis_report_div1">
						<h4 class="h4">问卷说明</h4>
						<div class="show_hide">
							<span></span> <strong>收起</strong>
						</div>
					</div>
					<div class="analysis_report_cont">${ empty questionnaires.directions ? '' : questionnaires.directions }</div>
				</div>
				<div class="clear"></div>
				<div class="analysis_report_div">
					<div class="analysis_report_div1">
						<h4 class="h41">问卷内容</h4>
						<div class="show_hide">
							<span></span> <strong>展开</strong>
						</div>
					</div>
					<div class="analysis_report_cont" style="display: none;">
						<table class="a_r_t1">
							<c:if test="${questionnaires.indicatorType == 0 }">
								<c:forEach items="${indicatorVo.childIndicators }" var="indicatorVo1">
									<tr>
										<td class="center"><span title="${indicatorVo1.indicator.title }"><ui:sout value="${indicatorVo1.indicator.title }" length="18" needEllipsis="true" /></span></td>
										<c:if test="${not empty indicatorVo1.childIndicators }">
										<td>
											<table class="a_r_t2">
												<c:forEach items="${indicatorVo1.childIndicators }"
													var="indicatorVo2">
													<tr>
														<td style="border-right-width: 0px;"><span title="${indicatorVo2.indicator.title }"><ui:sout value="${indicatorVo2.indicator.title }" length="78" needEllipsis="true" /></span></td>
													</tr>
												</c:forEach>
											</table>
										</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${questionnaires.indicatorType == 1 }">
								<c:forEach items="${indicatorVo.childIndicators }" var="indicatorVo1">
									<tr>
										<td class="center"><span title="${indicatorVo1.indicator.title }（${indicatorVo1.indicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.indicator.title }" length="18" needEllipsis="true" />（${indicatorVo1.indicator.scoreTotalInt }分）</span></td>
										<c:if test="${not empty indicatorVo1.childIndicators }">
										<td>
											<table class="a_r_t2">
												<c:forEach items="${indicatorVo1.childIndicators }"
													var="indicatorVo2">
													<tr>
														<td style="border-right-width: 0px;"><span title="${indicatorVo2.indicator.title }"><ui:sout value="${indicatorVo2.indicator.title }" length="78" needEllipsis="true" /></span></td>
													</tr>
												</c:forEach>
											</table>
										</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>		
							<c:if test="${questionnaires.indicatorType == 2 }">
								<c:forEach items="${indicatorVo.childIndicators }" var="indicatorVo1">
									<tr>
										<td class="center"><span title="${indicatorVo1.indicator.title }（${indicatorVo1.indicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.indicator.title }" length="18" needEllipsis="true" />（${indicatorVo1.indicator.scoreTotalInt }分）</span></td>
										<td>
											<table class="a_r_t2">
												<c:forEach items="${indicatorVo1.childIndicators }"
													var="indicatorVo2">
													<tr>
														<td style="border-right-width: 0px;"><span title="${indicatorVo2.indicator.title }（${indicatorVo2.indicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo2.indicator.title }" length="78" needEllipsis="true" />（${indicatorVo2.indicator.scoreTotalInt }分）</span></td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>							
						</table>
					</div>
				</div>
				<div class="clear"></div>
				<div class="analysis_report_div">
					<div class="analysis_report_div1">
						<h4 class="h42">问卷结果</h4>
						<div class="show_hide">
							<span></span> <strong>收起</strong>
						</div>
					</div>
					<div class="analysis_report_cont">
						<div class="a_report_cont">
							<div class="a_report_info">
								<form id="percentNum">
								<span>【系统预置】</span>
								<span>优秀：</span><input type="text" class="num_txt validate[required,custom[number],min[0],max[100]]"  title="${empty questionnairesPercent ? '50' : questionnairesPercent[0] }" value="${empty questionnairesPercent ? '50' : questionnairesPercent[0] }" readonly/><span>%，</span>
								<span>良好：</span><input type="text" class="num_txt validate[required,custom[number],min[0],max[100]]" title="${empty questionnairesPercent ? '35' : questionnairesPercent[1] }" value="${empty questionnairesPercent ? '35' : questionnairesPercent[1] }" readonly /><span>%，</span>
								<span>合格：</span><input type="text" class="num_txt validate[required,custom[number],min[0],max[100]]"  title="${empty questionnairesPercent ? '15' : questionnairesPercent[2] }" value="${empty questionnairesPercent ? '15' : questionnairesPercent[2] }" readonly/><span>%，</span>
								<span>不合格：</span><input type="text" class="num_txt validate[required,custom[number],min[0],max[100]]"  title="${empty questionnairesPercent ? '0' : questionnairesPercent[3] }" value="${empty questionnairesPercent ? '0' : questionnairesPercent[3] }" readonly/><span>%。</span>
								</form>
							</div>
							<div class="a_report_info_edit">调整比例</div>
							<div class="a_report_info_icon" style="display:none;">
								<div class="a_report_info_save">保存</div>
								<div class="a_report_info_c">取消</div>
							</div>
						</div>
						<h5>◆ 优秀（<span></span>人）</h5>
						<p></p>
						<h5>◆ 良好（<span></span>人）</h5>
						<p></p>
						<h5>◆ 合格（<span></span>人）</h5>
						<p></p>
						<h5>◆ 不合格（<span></span>人）</h5>
						<p></p>
					</div>
				</div>
				<div class="clear"></div>
				<div class="analysis_report_div">
					<div class="analysis_report_div1">
						<h4 class="h43">综合分析图表</h4>
						<div class="show_hide">
							<span></span> <strong>收起</strong>
						</div>
					</div>
					<div class="analysis_report_cont" style="width: 900px;">
						<ul class="cont_ul_tab">
							<li class="an_re_act an_re_act1">1.全体教师汇总</li>
							<li class="an_re_act">2.年级组分析对比</li>
							<li class="an_re_act">3.学科组分析对比</li>
							<li class="an_re_act">4.班主任分析对比</li>
							<li class="an_re_act">5.教龄段分析对比</li>							
							<li class="an_re_act">6.意见建议反馈</li>
						</ul>
						<div class="analysis_report_cont_wrap">														
							<div class="analysis_report_cont_tab">						
								<div class="view_name_top3" style="margin-top: 30px; margin-bottom: 30px; float: right;">									
									<div class="view_results_top2">
										<div id="barChart_all_0" class="echart"
											style="height: 376px; width: 100%;">问卷结果分布率图</div>
									</div>
									<div class="view_results_top3" style="margin-bottom:0px;">				
										<table id="sectionTable" class="view_results_top3_table">
										</table>
									</div>
									<div class="border_bottom"></div>
									<div class="view_results_top4" style="margin-bottom:20px;">
										<h3 style="font-weight:normal;">问卷结果一览表</h3>
										<span class="view_results_top4_span">注：评价等级已按权重转换为分数</span>
										<table class="view_results_top4_table1">
											<tr>
												<th style="width: 15%;">姓名</th>
												<th style="width: 15%;">担任职务</th>
												<th style="width: 25%;">年级</th>
												<th style="width: 25%;">学科</th>
												<th style="width: 10%;">总分</th>
												<th style="width: 10%;">排名</th>
											</tr>
										</table>
										<div class="view_results_top4_table_wrap">
											<table class="view_results_top4_table">
											</table>
										</div>
									</div>
									<div class="clear"></div>
								</div>								
								<c:if test="${questionnaires.indicatorType == 2 }">
									<div class="view_name_top3" style="border-top: 1px #27bb8b dashed;padding-top: 30px; margin-bottom: 30px; float: right;">
										<div style="width: 100%; height: 400px; text-align: center;">
											<div class="echart" id="barChart_all_2" style="width:100%;height:376px;">各评价指标得分率对比图</div>
										</div>
										<div class="view_name_table_b">
											<h3 style="height: 47px;font-size: 16px;color: #4a4a4a;text-align: center;">各评价指标得分率对比一览表</h3>
											<table class="view_name_table_b1" id="twoIndicatorVoTable">
												<tr>
													<th style="width: 20%;">一级指标</th>
													<th style="width: 49.7%;">二级指标</th>
													<th style="width: 15%;">平均分</th>
													<th style="width: 15%;">得分率</th>
												</tr>														
											</table>
										</div>
									</div>
									<div class="clear"></div>
								</c:if>
								<c:if test="${questionnaires.indicatorType == 1 }">
									<div class="view_name_top3" style="border-top: 1px #27bb8b dashed;padding-top: 30px;float: right;">
										<div class="view_name_top3_left">
											<div class="echart" id="barChart_all_1" style="height:376px;">各评价指标得分率对比图</div>
										</div>
										<div class="view_name_top3_right">
											<div class="view_name_whole_table" style="float: right;">
												<h3 style="height: 47px;font-size: 16px;color: #4a4a4a;text-align: center;">各评价指标得分率对比一览表</h3>
												<table class="v_n_w_table0" style="border-bottom: none;">
													<tr>
														<th style="width: 33.3%">指标名称</th>
														<th style="width: 33.3%">平均分</th>
														<th style="width: 33.3%">得分率</th>
													</tr>
												</table>
												<div class="view_name_whole_table0_wrap" style="height:286px;">
													<table class="v_n_w_table0" style="border-top: none;" id="oneIndicatorVoTable">																										
													</table>
												</div>
											</div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</c:if>
							</div>
							<div class="analysis_report_cont_tab" style="display: none;">
								<div class="view_name_top3"
									style="margin-top: 30px; margin-bottom: 30px; float: right;">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_grade" style="height:376px;">年级组平均分对比图</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 47px;">年级组平均分对比一览表</h3>
										<div class="view_name_whole_table" style="float: right;">
											<table class="v_n_w_table0" style="border-bottom: none;">
												<tr>
													<th style="width: 33.3%">年级</th>
													<th style="width: 33.3%">人数</th>
													<th style="width: 33.3%">平均分</th>
												</tr>
											</table>
											<div class="view_name_whole_table0_wrap" style="height:286px;">
												<table class="v_n_w_table0" style="border-top: none;" id="analysisGrade">																							
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="analysis_report_cont_tab" style="display: none;">
								<div class="view_name_top3"
									style="margin-top: 30px; margin-bottom: 30px; float: right;width:920px">
									<div class="view_name_top3_left" style="width:920px;border-right:0">
										<div class="echart" id="barChart_subject" style="height:376px;width:920px;">学科平均分对比图</div>
									</div>
									<div class="view_name_top3_right" style="width:920px; padding-top:20px;">
										<h3 style="height: 47px;">学科组平均分对比一览表</h3>
										<table class="v_n_w_table0" style="width:100px;float:left;height: 182px;border-bottom: none;border-right: 1px solid #ccc;">
											<tr>
												<th style="border-right: none;">学科</th>
											</tr>	
											<tr style="background:#f9f9f9;"> 
												<th style="border-right: none;">人数</th>
											</tr>	
											<tr> 
												<th style="border-right: none;">平均分</th>
											</tr>		
										</table>
										<div style="width:820px;overflow-x:auto;">
											<table class="v_n_w_table0 v_min_width" style="width:820px;height: 182px;border-left:none;" id="analysisSubject">		
											</table>
										</div>
									</div>
								</div>
								<div class="clear"></div>																	
							</div>
							<div class="analysis_report_cont_tab" style="display: none;">
								<input type="hidden" id="director_gradeId" value="" /> 
								<div class="view_name_top3" style="margin-top: 30px; margin-bottom: 30px; float: right;" id="director_grade_div">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_director_grade" style="height:376px;">年级平均分对比图</div>
										<div class="view_name_whole_table" style="margin-top: 22px;">
											<table class="view_name_whole_table1" id="directorSectionTable">
												<tr>
													<th style="width: 33.3%">分数段</th>
													<th style="width: 33.3%">人数</th>
													<th style="width: 33.3%">分布率</th>
												</tr>												
											</table>
										</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 35px; line-height: 25px;">班主任总分一览表</h3>
										<div class="select_right">											
											<select class="chosen-select-deselect" style="width: 110px; height: 25px;"
												onchange="directorgrade.getDataByGrade(this)" id="director_grade_sel">
												<option value="" selected="selected">全部年级</option>
												<c:forEach items="${realGradeList }" var="g" varStatus="st">
													<c:if test="${not empty g.id }">
														<option value="${g.id }">${g.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="view_name_whole_tabl2">
											<table class="view_name_whole_tabl21"
												style="border-bottom: none;">
												<tr>
													<th style="width: 20%;">姓名</th>
													<th style="width: 40%;">班级</th>
													<th style="width: 20%;">总分</th>
													<th style="width: 20%;">排名</th>
												</tr>
											</table>

											<div class="view_name_whole_tabl22_wrap">
												<table class="view_name_whole_tabl22"
													style="border-top: none;" id="directorGradeTable">																										
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
								<div class="view_name_top3"
									style="margin-top: 30px;margin-bottom: 30px;  float: right; display:none; " id="director_class_div">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_director_class" style="height:376px;">一年级班主任平均分对比图表</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 16px; line-height: 18px;" id="classTitle">一年级班主任总分一览表</h3>
										<div class="select_right">											
											<select class="chosen-select-deselect" style="width: 110px; height: 25px;"
												onchange="directorgrade.getDataByGrade(this)" id="director_class_sel">
												<option value="" selected="selected">全部年级</option>
												<c:forEach items="${realGradeList }" var="g" varStatus="st">
													<c:if test="${not empty g.id }">
														<option value="${g.id }">${g.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="view_name_whole_table" style="float: right;">
											<table class="v_n_w_table0" style="border-bottom: none;">
												<tr>
													<th style="width: 20%;">姓名</th>
													<th style="width: 40%;">班级</th>
													<th style="width: 20%;">总分</th>
													<th style="width: 20%;">排名</th>
												</tr>
											</table>
											<div class="view_name_whole_table0_wrap"
												style="height: 280px;">
												<table class="v_n_w_table0" style="border-top: none;" id="directorClassTable">																									
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="analysis_report_cont_tab" style="display: none;">
								<div class="view_name_top3"
									style="margin-top: 30px; margin-bottom: 30px; float: right;">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_schoolAgeSex" style="height:376px;">教齡段平均分对比图</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 47px;">教龄段平均分对比一览表</h3>
										<div class="view_name_whole_table" style="float: right;">
											<table class="v_n_w_table0" style="border-bottom: none;">
												<tr>
													<th style="width: 33.3%">教龄段</th>
													<th style="width: 33.3%">人数</th>
													<th style="width: 33.3%">平均分</th>
												</tr>
											</table>
											<div class="view_name_whole_table0_wrap"
												style="height: 282px;">
												<table class="v_n_w_table0" style="border-top: none;" id="schoolAgeSex">																									
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div class="analysis_report_cont_tab" style="display: none;" id="suggestionDiv">
								<c:if test="${isShowSuggestion }">
								<div class="view_name_top3"
									style="margin-top: 30px;padding-bottom:30px; float: right;border-bottom: 1px #27bb8b dashed;">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_suggestion" style="height:376px;">意见建议反馈比例图</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 18px; line-height: 18px;">意见建议反馈一览表</h3>
										<span class="view_results_top4_span">问卷总人数：<span id="questionnairesCount"></span>人</span>
										<div class="view_name_whole_table" style="float: right;">
											<table class="v_n_w_table0" style="border-bottom: none;">
												<tr>
													<th style="width: 33.3%">类别</th>
													<th style="width: 33.3%">人数</th>
													<th style="width: 33.3%">所占比例</th>
												</tr>
											</table>
											<div class="view_name_whole_table0_wrap"
												style="height: 282px;">
												<table class="v_n_w_table0" style="border-top: none;" id="suggestionTable">												
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								</c:if>
								<div class="view_name_top3"
									style="margin-top: 30px; margin-bottom: 30px; float: right;">
									<div class="view_name_top3_left">
										<div class="echart" id="barChart_suggestion_grade" style="height:376px;">年级意见反馈比例图</div>
									</div>
									<div class="view_name_top3_right">
										<h3 style="height: 18px; line-height: 18px;">年级意见建议反馈一览表</h3>
										<span class="view_results_top4_span">意见反馈总人数：<span id="suggestionCount"></span>人</span>
										<div class="view_name_whole_table" style="float: right;">
											<table class="v_n_w_table0" style="border-bottom: none;">
												<tr>
													<th style="width: 33.3%">年级</th>
													<th style="width: 33.3%">人数</th>
													<th style="width: 33.3%">所占比例</th>
												</tr>
											</table>
											<div class="view_name_whole_table0_wrap"
												style="height: 282px;">
												<table class="v_n_w_table0" style="border-top: none;" id="suggestionGradeTable">												
												</table>
											</div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="problemDiv">
				<!-- 问题分析 -->
				</div>
			</div>
		</div>
	</div>	
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript">
	require(["require","echarts/echarts"],function(require){		
		var echarts = require("echarts/echarts");
		$(document).ready(function() {
			var indicatorType = "${questionnaires.indicatorType}";//评价类型
			var questionnairesId = "${questionnaires.questionnairesId}";//问卷id
			$("#problemDiv").load(_WEB_CONTEXT_+"/jy/evl/analyze/loadAnalysisProblem",{questionnairesId:questionnairesId});
			window.directorgrade = {
					questionnairesId : "${questionnaires.questionnairesId}",//问卷id				
					percents : "",
					picBase64Infos : [],
					imagePaths : [],
					getDataByGrade : function(obj){
						var gradeId = $(obj).val();
						$("#director_gradeId").val(gradeId);
						if(gradeId == null || gradeId == ""){					
							$("#director_class_div").hide();
							$("#director_grade_div").show();						
							$("#director_grade_sel").val(gradeId).trigger("chosen:updated"); 						
						}else{
							$("#director_grade_div").hide();
							$("#director_class_div").show();
							$("#director_class_sel").chosen({disable_search : true});
							$("#director_class_sel").val(gradeId).trigger("chosen:updated"); 						
						}	
						directorgrade.loadDirectorTeacherList();
					},
					
					//4.班主任分析对比
					loadDirectorTeacherList : function(){
						var gradeId = $("#director_gradeId").val();
						var barChart = null;
						var widthSize = 0;
						if(gradeId == null || gradeId == ""){
							barChart = echarts.init(document.getElementById('barChart_director_grade'));
							widthSize = $("#barChart_director_grade").width();
						}else{
							barChart = echarts.init(document.getElementById('barChart_director_class'));
							widthSize = $("#barChart_director_class").width();
						} 
						barChart.clear();
						barChart.showLoading({
							text : '正在努力的读取数据中...'
						});
						var url = "${ctx}jy/evl/analyze/findDirectorTeacherList";
						$.post(url,{"questionId":directorgrade.questionnairesId,"gradeId":gradeId},function(data){
							if(data){
								var option = data.option;
								if(gradeId == null || gradeId == ""){
									directorgrade.loadDirectorGradeTable(data.teacherList,data.sectionList);
								}else{
									directorgrade.loadDirectorClassTable(data.teacherList);
									$("#classTitle").html(data.gradeName+"班主任总分一览表");
									directorgrade.autoSetBarWidth(option,widthSize,data.titleSize);	
									option.xAxis[0].axisLabel.clickable = true;
								} 																					
								directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,option,4);
							}
						},"json");
					},
					
					//班主任年级分析对比表
					loadDirectorGradeTable : function(gradeList,sectionList){
						$("#directorGradeTable").empty();
						for(var i in gradeList){
							var director = gradeList[i];
							var innerHtml = '<tr><td style="width: 20%;"><span title="'+director.teacherName+'">'+director.flags+'</span></td><td style="width: 40%;">'+director.flago+'</td><td style="width: 20%;">'+director.resultScore+'</td><td style="width: 20%;">'+director.sort+'</td></tr>';
							$("#directorGradeTable").append(innerHtml);
						}	
						$("#directorGradeTable").find("tr:even").css({"background":"#f9f9f9"});
						
						$("#directorSectionTable").find("tr:gt(0)").remove();
						for(var j in sectionList){
							var director = sectionList[j];
							var innerHtml = '<tr><td style="width: 33.3%">'+director.sectionName+'</td><td style="width: 33.3%">'+director.sectionCount+'</td><td style="width: 33.3%">'+director.sectionPercent+'%</td></tr>';
							$("#directorSectionTable").append(innerHtml);
						}
						$("#directorSectionTable").find("tr:odd").css({"background":"#f9f9f9"});
					},
					
					//班主任班级分析对比表
					loadDirectorClassTable : function(classList){
						$("#directorClassTable").empty();
						for(var i in classList){
							var director = classList[i];
							var innerHtml = '<tr><td style="width: 20%;"><span title="'+director.teacherName+'">'+director.flags+'</span></td><td style="width: 40%;">'+director.flago+'</td><td style="width: 20%;">'+director.resultScore+'</td><td style="width: 20%;">'+director.sort+'</td></tr>';
							$("#directorClassTable").append(innerHtml);
						}		
						$("#directorClassTable").find("tr:even").css({"background":"#f9f9f9"});
					},								
					
					//评价分析柱状图
					loadAnalyzeIndicatorVoBarChart : function(barChart,option,picIndex){		
						if(option.xAxis[0].axisLabel.clickable){						
							option.xAxis[0].axisLabel.formatter = function(val){
								return directorgrade.getVerticalTitle(val);
							}
							option.xAxis[0].axisLabel.clickable = false;
						}
						var labelFormatter = option.series[0].itemStyle.normal.label.formatter;
						if(typeof labelFormatter != "undefined"){
							labelFormatter = "%";
						}else{
							labelFormatter = "";
						}					
						option.tooltip.formatter = function(params){  						
				           var relVal = directorgrade.getTooltipTitle(params[0].name,25);  
				           for (var i = 0, l = params.length; i < l; i++) {  
				                relVal += '<br/>' + params[i].seriesName + ' : ' + params[i].value + labelFormatter;  
				            }  
				           return relVal;  
				        }  
						option.series[0].itemStyle.normal["color"] = function(params) {								
							var colorList = [ '#4CC9E7', '#FF895A', '#7ADD70',
									'#FFD700', '#FF1493', '#8B008B', '#808000',
									'#0000FF', '#DAA520', '#EE0000' ];
							var index = (params.dataIndex+1) % colorList.length-1;
							var count = (index == -1 ? params.dataIndex : index);						
							return colorList[count];
						};						
						option.animation = false;//获取完整图片信息
						barChart.setOption(option, true);
						barChart.hideLoading();
						directorgrade.picBase64Infos[picIndex] = barChart.getDataURL('png');//获取图表图片信息
						if(picIndex == 0 || picIndex == 1 || picIndex == 4){
							//清除重新加载以实现动画效果
							barChart.clear();
							option.animation = true;
							barChart.setOption(option, true);
							barChart.hideLoading();
						}					
					},
					
					//班级柱状图自动设置bar宽度
					autoSetBarWidth : function(option,widthSize,titleSize){
						var numSize = parseInt(titleSize);
						if(numSize > 0){
							var barWidth = (widthSize - (option.grid.x+option.grid.x2))/numSize;
							if(barWidth < option.series[0].barWidth / 0.6){
								if(barWidth < 1 / 0.6){
									option.series[0].barWidth = 1;
								}else{
									option.series[0].barWidth = barWidth * 0.6;
								}
							} 						
						}	
					},
					
					//分析报告下载
					downloadRes : function (){
						for(var i = 0; i<directorgrade.picBase64Infos.length; i++){
							var param = {
									"picBase64Info" : directorgrade.picBase64Infos[i],
									"type" : "word"
								};
							$.ajax({
								   type: "POST",
								   url: "${ctx}jy/evl/result/getPicFilePath",
								   data: param,
								   cache: false,
								   async: false,
								   success: function(result){
								     if(result){
								    	 directorgrade.imagePaths[i] = result.msg;
								     }
								   }
								});
						}
						var url = "${ctx}jy/evl/analyze/downLoadAnalyzeInfo?questionnairesId="+directorgrade.questionnairesId;
						url += "&flags="+directorgrade.percents;
// 						url += "&gradeId="+$("#director_grade_sel").val();
						url += "&flago=" + directorgrade.imagePaths.join(",");
						location.href = url;//请求文件	
					},
					
					//柱状图x轴标签垂直显示数字汉字区分
					getVerticalTitle : function(title){
						if(title == null || title == ""){
							return "";
						}else{
							var Regx = /^[0-9]*$/;
							var newTitle = "";
							var numberMes = "";
							var count = 0;
							for(var i in title){		
					            if (Regx.test(title[i])) {
					            	numberMes += title[i];
					            }else{
					            	if(numberMes == ""){
					            		newTitle += title[i] + '\n';
					            		count++;
					            	}else{
					            		if(count == 5){
					            			newTitle += numberMes + '\n';
					            			count++;
					            		}else{
					            			newTitle += numberMes + '\n' + title[i] + '\n';
					            			count += 2;
					            		}
					            		numberMes = "";				            		
					            	}	
					            	if(count >= 6){
					            		break;//6个汉字长度以后舍去
					            	}
					            }				           
							}
							newTitle += numberMes;
							return newTitle;
						}
					},
					
					getTooltipTitle : function(title,number){
						var newTitle = "";
						var count = 0;
						for(var i=number; i<title.length;i++){						
							if(i%number == 0){
								newTitle += title.substring(number*count++,i);
								if(title.length > count*number){
									newTitle += "<br/>";
									if(parseInt(title.length/number) == count){
										newTitle += title.substring(count*number);
									}
								}														
							}
						}
						return newTitle == "" ? title : newTitle;
					}				
			}
			if($(".show_hide").find("strong").html()=="收起"){
				$(".show_hide").find('span').css({"background-position":"-296px -11px"});
			}
			//展开事件
			$('.show_hide').click(function (){
				$(this).parent().siblings().toggle(); 
				if($(this).find('strong').html()=="展开"){
					$(this).find('strong').html("收起");
					$(this).find('span').css({"background-position":"-296px -11px"});					 
				}else{ 
					$(this).find('strong').html("展开");
					$(this).find('span').css({"background-position":"-296px -4px"}); 
				} 				
			});			
			
			/** 问卷内容 **/
			$(".a_r_t2").each(function(){
				$(this).find("tr").last().css({"border-bottom-width":"0px"});	
			});		
			
			/** 问卷结果 **/
			var percent = [];//问卷结果百分比数组
			setPercentArray();
			loadAnalysisReport();
			
			//编辑
			$(".a_report_info_edit").click(function(){
				$(this).hide();
				$(".a_report_info_icon").show();
				$(".num_txt").attr("readonly",false).css({border:'1px #b2b2b2 solid'});				
				setPercentArray();
			});
			
			//装载问卷结果百分比集合
			function setPercentArray(){
				if(!$("#percentNum").validationEngine("validate")){
					return false;	
				}
				var allPercent = 0;
				var newPercent = [];
				$(".num_txt").each(function(index){
					newPercent[index]=parseFloat($(this).val());
					allPercent += newPercent[index];
					$(this).val(newPercent[index]);
				});				
				if(allPercent != 100){
					$('#resultPersent_dialog').dialog({
						width: 423,
						height: 200
					});					
					return false;
				}
				percent = newPercent;
				directorgrade.percents = percent.join(",");
				return true;
			}
			
			//保存
			$(".a_report_info_save").click(function(){					
				if(!setPercentArray()){
					return false;
				}				
				$(".a_report_info_icon").hide();
				$(".a_report_info_edit").show();
				$(".num_txt").attr("readonly",true).css({border:'1px white solid'});
				var url = "${ctx}jy/evl/analyze/updateQuestionnairesPercentById";				
				$.post(url,{"questionnairesId":questionnairesId,"back1":directorgrade.percents},function(data){
					if(data){
						if(data.code == 200){							
							//加载数据
							loadAnalysisReport();
							$(".num_txt").each(function(i,el){
								$(el).attr("title",$(el).val());
							})
						}else{
							$('#resultPersent_dialog').dialog({
								width: 423,
								height: 200
							});		
							$('#resultPersent_dialog').find("strong").html(data.msg);
						}						
					}
				},"json");
				
			});
			
			//取消
			$(".a_report_info_c").click(function(){
				$(".a_report_info_icon").hide();
				$(".a_report_info_edit").show();
				$(".num_txt").attr("readonly",true).css({border:'1px white solid'});				
				for(var i in percent){
					$(".num_txt").eq(i).val(percent[i]);
				}				
			});
			
			//加载问卷结果列表
			function loadAnalysisReport(){
				var url = "${ctx}jy/evl/analyze/loadAnalysisReport";
				$.post(url,{"questionnairesId":questionnairesId},function(teacherList){
					if(teacherList){
						var allCount = teacherList.length;
						var peopleCount = 0;
						var index = 0;
						for(var i in percent){
							peopleCount = Math.round((percent[i] / 100.00) * allCount);
							if(peopleCount > allCount - index){
								peopleCount = allCount - index;
							}
							$(".analysis_report_cont h5").eq(i).find("span").html(peopleCount);							
							var names = "";
							for(var j = index;j< index + peopleCount;j++){
								names += "、"+teacherList[j].teacherName;								
							}
							$(".analysis_report_cont p").eq(i).empty().append(names.length>0 ? names.substring(1) : "");
							index += peopleCount;
						}							
						if(index < allCount){
							for(var i = percent.length-1 ;i >= 0 ;i--){
								if(percent[i] == 0){
									continue;
								}else{
									var oldCount = parseInt($(".analysis_report_cont h5").eq(i).find("span").html());
									$(".analysis_report_cont h5").eq(i).find("span").html(oldCount+(allCount-index));							
									var names = "";
									for(var j = index;j< allCount;j++){
										names += "、"+teacherList[j].teacherName;								
									}
									var oldPeople = $(".analysis_report_cont p").eq(i).html();
									if(oldPeople == null || oldPeople == ""){
										$(".analysis_report_cont p").eq(i).append(names.length>0 ? names.substring(1) : names);
									}else{
										$(".analysis_report_cont p").eq(i).append(names);
									}
									break;
								}
							}
						}						
					}
				},"json");
			}
			
			/** 综合分析图表   **/
			
			loadAnalysisAllTeacher();//1.全体教师汇总
			loadTeacherListOfGrade();//2.年级组分析对比
			loadTeacherListOfSubject();//3.学科组分析对比
			//班主任分析对比刷新初始化选择全部年级
			$("#director_gradeId").val("");
			directorgrade.loadDirectorTeacherList();//4.班主任分析对比
			loadTeacherListBySchoolAgeSex();//5.教龄段分析对比			
			loadAnalyzeSuggestionList();//6.意见反馈分析对比
			
			$(".analysis_report_cont ul li").click(function (){ 
		    	$(this).addClass("an_re_act1").siblings().removeClass("an_re_act1");
		    	var index = $(".analysis_report_cont ul li").index(this);
		    	$('.analysis_report_cont_wrap .analysis_report_cont_tab').hide().eq(index).show();
		    	if(index == 3){
		    		$("#director_grade_sel").chosen({disable_search : true});					
		    	}
			}); 

			//1全体教师汇总-整体评价分析
			function loadAnalysisAllTeacher(){
				var param = {"questionnairesId" : questionnairesId,"flago":"analyze"};
				loadAllAnalyzeTeacherList(param);
				if(indicatorType == 1 || indicatorType == 2){//一级指标评价,二级指标评价
					loadAllAnalyzeIndicatorVoList();
				}
			}
			
			//加载数据
			function loadAllAnalyzeTeacherList(param) {
				var barChart = echarts
						.init(document.getElementById('barChart_all_0'));
				barChart.clear();
				barChart.showLoading({
					text : '正在努力的读取数据中...'
				});
				$.ajax({
					type : 'post',
					cache : false,
					data : param,
					dataType : 'json',
					url : "${ctx}jy/evl/analyze/findAllAnalyzeTeacherList",
					success : function(data) {
						if (data) {
							allAnalyzeTeacher_drawTeacherTable(data.teacherList);
							allAnalyzeTeacher_drawSectionTable(data.sectionList);
							var option = data.option;
							option.series[0].barWidth = "40";						
							directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,option,0);
						}
					},
					error : function() {
						alert("请求异常:" + this.url);
					}
				});
			}

			//加载教师列表
			function allAnalyzeTeacher_drawTeacherTable(teacherList) {
				$(".view_results_top4_table").empty();
				for ( var index in teacherList) {
					var teacher = teacherList[index];
					var mesArr = teacher.teacherRole.split("=,=");
					var teacherHtml = '<tr><td style="width:15%;" data-id="' + teacher.teacherId + '" ><a style="cursor: pointer;">'
							+ '<span title="'+(teacher.teacherName == null ? "": teacher.teacherName)+'">'+mesArr[1]+'</span>'
							+ '</a></td><td style="width:15%;"><span title="'+(mesArr[0] == null ? "": mesArr[0])+'">'+mesArr[2]+'</span>' 
							+ '</td><td style="width:25%;"><span title="'+teacher.flago+'">'+mesArr[3]+'</span>' 
							+ '</td><td style="width:25%;"><span title="'+teacher.flags+'">'+mesArr[4]+'</span>' 
							+ '</td><td style="width:10%;">' + teacher.resultScore + '</td><td style="width:10%;">'+ teacher.sort + '</td></tr>';
					$(".view_results_top4_table").append(teacherHtml);
				}
				$(".view_results_top4_table tr:even").css({"background":"#f9f9f9"});
				requestTeacherResult();//教师统计详情
			}						

			//教师结果统计
			function requestTeacherResult () {
				$(".view_results_top4_table").find("a").click(
								function() {
									var tds = $(this).closest("tr").children(
											"td");
									var teacherId = $(tds[0]).attr("data-id");
									var teacherScore = $(tds[4]).text();
									var teacherSort = $(tds[5]).text();
									location.href = "${ctx}jy/evl/result/resultUserIndicator?questionnairesId="
											+ questionnairesId
											+ "&teacherId="
											+ teacherId
											+ "&resultScore="
											+ teacherScore
											+ "&sort=" + teacherSort
											+ "&flago=analyze";
								});
			}
			
			//分数段分组统计
			function allAnalyzeTeacher_drawSectionTable(sectionList) {
				var columOne = "<tr><td>分数段</td>";
				var columTwo = "<tr><td>人数</td>";
				var columTree = "<tr><td>分布率</td>";
				for ( var i in sectionList) {
					columOne += "<td>" + sectionList[i].scoreSection + "</td>";
					columTwo += "<td>" + sectionList[i].peopleSection + "</td>";
					columTree += "<td>" + sectionList[i].percentSection
							+ "%</td>";
				}
				$("#sectionTable").html(
						columOne + "</tr>" + columTwo + "</tr>" + columTree
								+ "</tr>");
				$("#sectionTable").find("tr:odd").css({"background":"#f9f9f9"});
			}
			
			//1全体教师汇总-一级指标评价分析或二级指标评价分析						
			
			function loadAllAnalyzeIndicatorVoList(){
				var barChart = echarts.init(document.getElementById('barChart_all_'+indicatorType));
				barChart.clear();
				barChart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var url = "${ctx}jy/evl/analyze/findAllAnalyzeIndicatorVoList";
				$.post(url,{"questionId":questionnairesId},function(data){
					if(data){
						var option = data.option;
						option.xAxis[0].axisLabel.clickable = true;
						var widthSize = $('#barChart_all_'+indicatorType).width();										
						directorgrade.autoSetBarWidth(option,widthSize,data.titleSize);	
						if(indicatorType == 1){
							loadOneAnalyzeIndicatorVoList(data.indicatorList);
							directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,option,1);
						}else if(indicatorType == 2){
							loadTwoAnalyzeIndicatorVoList(data.indicatorList);														
							directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,option,1);
						}else{
							//评价类型异常
						}
					}
				},"json");
			}
			
			//一级指标评价分析
			function loadOneAnalyzeIndicatorVoList(indicatorList){				
				$("#oneIndicatorVoTable").empty();
				for(var i in indicatorList){
					var oneIndicator = indicatorList[i].evlIndicator;
					var indicatorHtml = '<tr><td style="width: 33.3%"><span title="'+oneIndicator.title+'（'+oneIndicator.scoreTotal+'分）">'+oneIndicator.flago+'（'+oneIndicator.scoreTotal+'分）</span></td>';
					var oneAnalyzeIndicator = indicatorList[i].evlAnalyzeIndicator;
					if(oneAnalyzeIndicator != null){
						indicatorHtml += '<td style="width: 33.3%">'+((oneAnalyzeIndicator.resultScore == 0)?'0.0':oneAnalyzeIndicator.resultScore)+'</td><td style="width: 33.3%">'+((oneAnalyzeIndicator.scoreAverage == 0)?'0.0':oneAnalyzeIndicator.scoreAverage)+'%</td></tr>';
					}else{
						indicatorHtml += '<td style="width: 33.3%">0</td><td style="width: 33.3%">0.0%</td></tr>';
					}	
					$("#oneIndicatorVoTable").append(indicatorHtml);					
				}
				$("#oneIndicatorVoTable").find("tr:even").css({"background":"#f9f9f9"});
			}						
			
			//二级指标评价分析
			function loadTwoAnalyzeIndicatorVoList(indicatorList){
				$("#twoIndicatorVoTable").find("tr:gt(0)").remove();				
				for(var i in indicatorList){
					var innerHtml = '<tr><td style="width: 20%;"><span title="'+indicatorList[i].evlIndicator.title+'（'+indicatorList[i].evlIndicator.scoreTotalInt+'分）">'+indicatorList[i].evlIndicator.flago+'（'+indicatorList[i].evlIndicator.scoreTotalInt+'分）</span></td><td colspan="3" style="width: 80%;"><table class="view_name_table_b2" style="border: none;">';
					var twoIndicator = indicatorList[i].childIndicators;
					for(var j in twoIndicator){
						innerHtml += '<tr><td style="text-align: left;width: 50%; padding-left:10px;"><span title="'+twoIndicator[j].evlIndicator.title+'（'+twoIndicator[j].evlIndicator.scoreTotalInt+'分）">'+twoIndicator[j].evlIndicator.flago+'（'+twoIndicator[j].evlIndicator.scoreTotalInt+'分）</span></td>';
						var twoAnalyzeIndicator = twoIndicator[j].evlAnalyzeIndicator;
						if(twoAnalyzeIndicator != null){
							innerHtml += '<td style="width: 15%;">'+((twoAnalyzeIndicator.resultScore == 0)?'0.0':twoAnalyzeIndicator.resultScore) + '</td><td style="width: 15%;border-right-width: 0px;">'+((twoAnalyzeIndicator.scoreAverage == 0)?'0.0':twoAnalyzeIndicator.scoreAverage)+'%</td></tr>';
						}else{
							innerHtml += '<td style="width: 15%;">0</td><td style="width: 15%;">0.0%</td></tr>';
						}
					}
					innerHtml += '</table></td></tr>';
					$("#twoIndicatorVoTable").append(innerHtml);
				}	
				$(".view_name_table_b2").find("tr:last").css("border-bottom-width","0px");
			}
			
			//2.年级组分析对比
			function loadTeacherListOfGrade(){
				var barChart = echarts.init(document.getElementById('barChart_grade'));
				barChart.clear();
				barChart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var url = "${ctx}jy/evl/analyze/findTeacherListOfGrade";
				$.post(url,{"questionId":questionnairesId},function(data){
					if(data){
						loadAnalysisGrade(data.gradeList);
						directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,data.option,2);
					}
				},"json");
			}
			
			//加载分析年级统计表
			function loadAnalysisGrade(gradeList){
				$("#analysisGrade").empty();
				for(var i in gradeList){
					var gradeMap = gradeList[i];
					$("#analysisGrade").append('<tr><td style="width: 33.3%">'+gradeMap.gradeName+'</td><td style="width: 33.3%">'+gradeMap.studentCount+'</td><td style="width: 33.3%">'+gradeMap.resultScore+'</td></tr>');
				}
				$("#analysisGrade").find("tr:even").css({"background":"#f9f9f9"});
			}
			
			//3.学科组分析对比
			function loadTeacherListOfSubject(){
				var barChart = echarts.init(document.getElementById('barChart_subject'));
				barChart.clear();
				barChart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var url = "${ctx}jy/evl/analyze/findTeacherListOfSubject";
				$.post(url,{"questionId":questionnairesId},function(data){
					if(data){
						loadAnalysisSubject(data.subjectList);
						var option = data.option;
						var widthSize = $('#barChart_subject').width();	
						option.xAxis[0].axisLabel.clickable = true;
						directorgrade.autoSetBarWidth(option,widthSize,data.titleSize);
						directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,option,3);
					}
				},"json");
			}
			
			//加载分析年级统计表
			function loadAnalysisSubject(subjectList){
				$("#analysisSubject").empty();
				var innerHtml1 = '<tr>';
				var innerHtml2 = '<tr>';
				var innerHtml3 = '<tr>';
				for(var i in subjectList){
					var subjectMap = subjectList[i];
					innerHtml1 += '<td>'+subjectMap.subjectName+'</td>';
					innerHtml2 += '<td>'+subjectMap.studentCount+'</td>';
					innerHtml3 += '<td>'+subjectMap.resultScore+'</td>';
				}
				$("#analysisSubject").empty().append(innerHtml1+'</tr>'+innerHtml2+'</tr>'+innerHtml3+'</tr>');
				$("#analysisSubject").find("tr:odd").css({"background":"#f9f9f9"});
			}									
			
			//5.教龄段分析对比
			function loadTeacherListBySchoolAgeSex(){
				var barChart = echarts.init(document.getElementById('barChart_schoolAgeSex'));
				barChart.clear();
				barChart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var url = "${ctx}jy/evl/analyze/findTeacherListBySchoolAgeSex";
				$.post(url,{"questionId":questionnairesId},function(data){
					if(data){
						loadSchoolAgeSexTable(data.returnList);
						directorgrade.loadAnalyzeIndicatorVoBarChart(barChart,data.option,5);
					}
				},"json");
			}
			
			//加载教龄分析对比表
			function loadSchoolAgeSexTable(list){
				$("#schoolAgeSex").empty();
				for(var i in list){
					var schoolAgeSex = list[i];
					$("#schoolAgeSex").append('<tr><td style="width: 33.3%">'+schoolAgeSex.schoolAgeSex+'</td><td style="width: 33.3%">'+schoolAgeSex.studentCount+'</td><td style="width: 33.3%">'+schoolAgeSex.resultScore+'</td></tr>');
				}
				$("#schoolAgeSex").find("tr:even").css({"background":"#f9f9f9"});
			}
			
			//6.意见建议反馈
			function loadAnalyzeSuggestionList(){
				var suggestionType = "${isShowSuggestion }";
				var barChart1 = null;
				if(suggestionType == 'true'){
					barChart1 = echarts.init(document.getElementById('barChart_suggestion'));
					barChart1.clear();
					barChart1.showLoading({
						text : '正在努力的读取数据中...'
					});
				}
				var barChart2 = echarts.init(document.getElementById('barChart_suggestion_grade'));
				barChart2.clear();
				barChart2.showLoading({
					text : '正在努力的读取数据中...'
				});
				var url = "${ctx}jy/evl/analyze/findAnalyzeSuggestionList";
				$.post(url,{"questionId":questionnairesId},function(data){
					if(data){
						var gradeBarIndex = 7;
						if(suggestionType == 'true'){
							loadSuggestionType(data.suggestionList,data.questionnairesCount);
							var option1 = data.option1;
							option1.xAxis[0].axisLabel.clickable = true;
							var widthSize = $('#barChart_suggestion').width();										
							directorgrade.autoSetBarWidth(option1,widthSize,data.titleSize);	
							directorgrade.loadAnalyzeIndicatorVoBarChart(barChart1,option1,6);
						}else{
							gradeBarIndex = 6;
						}
						if((typeof data.suggestionGradeList != "undefined" && typeof data.suggestionCount !="undefined") && typeof data.option2 != "undefined"){
							loadSuggestionGrade(data.suggestionGradeList,data.suggestionCount);
							directorgrade.loadAnalyzeIndicatorVoBarChart(barChart2,data.option2,gradeBarIndex);
						}else{
							$(".an_re_act:last").remove();
							$("#suggestionDiv").hide();
						}
					}
				},"json");
			}
			
			//加载反馈类型统计表
			function loadSuggestionType(suggestionList,questionnairesCount){
				$("#questionnairesCount").html(questionnairesCount);
				$("#suggestionTable").empty();
				for(var i in suggestionList){
					var suggestionMap = suggestionList[i];
					$("#suggestionTable").append('<tr><td style="width: 33.3%">'+suggestionMap.suggestionName+'</td><td style="width: 33.3%">'+suggestionMap.suggestionCount+'</td><td style="width: 33.3%">'+suggestionMap.suggestionPercent+'%</td></tr>');
				}
				$("#suggestionTable").find("tr:even").css({"background":"#f9f9f9"});
			}
			
			//加载反馈年级统计表
			function loadSuggestionGrade(suggestionGradeList,suggestionCount){
				$("#suggestionCount").html(suggestionCount);
				$("#suggestionGradeTable").empty();
				for(var i in suggestionGradeList){
					var suggestionMap = suggestionGradeList[i];
					$("#suggestionGradeTable").append('<tr><td style="width: 33.3%" data-id="'+suggestionMap.gradeId+'"><a class="click_name">'+suggestionMap.gradeName+'</a></td><td style="width: 33.3%">'+suggestionMap.gradeCount+'</td><td style="width: 33.3%">'+suggestionMap.gradePercent+'%</td></tr>');
				}
				$("#suggestionGradeTable").find("tr:even").css({"background":"#f9f9f9"});
				$(".click_name").click(function (){ 
					$("#suggestionGradeTable_dialog").dialog({
						width:770,
						height:460
					});
					$(".suggestion_info_left").html("年级："+$(this).html());
					$(".suggestion_info_left").attr("data-id",$(this).parent("td").attr("data-id"));
					$(".chosen-select-deselect").chosen({disable_search : true});
					loadSuggestionInfoByGrade();
				});				
			}	
			
			//加载意见年级意见反馈详情
			function loadSuggestionInfoByGrade(){
				loadClassListByGradeId();
				$("#sugestionClassSel").change(function(){
					var classId = $(this).val();
					if(classId == ""){
						classId = null;
					}
					loadSuggestionListInfo($(".suggestion_info_left").attr("data-id"),classId);
				});
			}
			
			//加载对应年级下班级列表
			function loadClassListByGradeId(){
				var gradeId = $(".suggestion_info_left").attr("data-id");
				var url = "${ctx}jy/evl/analyze/getSchClassListByGradeId";			
				$.post(url,{"questionnairesId":questionnairesId,"gradeId":gradeId},function(data){
					if(data){
						$("#sugestionClassSel").find("option:gt(0)").remove();
						var innerHtml = "";
						for(var index in data){
							innerHtml += '<option value="'+data[index].id+'">'+data[index].name+'</option>';
						}
						$("#sugestionClassSel").append(innerHtml).trigger("chosen:updated");
						loadSuggestionListInfo(gradeId,null);
					}
				},"json");
			}
			
			//加载意见建议反馈列表详情
			function loadSuggestionListInfo(gradeId,classId){
				if(gradeId == ""){
					return true;
				}
				var url = "${ctx}jy/evl/analyze/loadSuggestionListInfo";
				$.post(url,{"questionnairesId":questionnairesId,"gradeId":gradeId,"classId":classId},function(data){
					if(data){
						$(".suggestion_info_bottom").html("");
						for(var index in data){
							var classNames = data[index].standby1.split(",");
							var innerHtml = '<div class="suggestion_info_bottom_wrap clearfix"><div class="suggestion_info_bottom_top">';
							innerHtml += '<span class="class_span" title="'+classNames[0]+'">'+classNames[1]+ '</span><span title="'+data[index].flago+'" class="name_span" >'+ data[index].flags + '：</span></div><div class="suggestion_info_bottom_bottom"><ul>';
							var contents = data[index].content.split("=,=");
							for(var i in contents){
								innerHtml += '<li>'+contents[i]+'</li>';
							}
							innerHtml += '</ul></div></div>';
							$(".suggestion_info_bottom").append(innerHtml);
						}
						if($(".suggestion_info_bottom").html() == ""){
							var emptyHtml = '<div class="cont_empty" style="width:100%;height:205px;margin-top:50px;"><div class="cont_empty_img1"></div><div class="cont_empty_words">没有数据！</div></div>	';
							$(".suggestion_info_bottom").html(emptyHtml);
						}
					}
				},"json");
			}			
			
			/** 本次问卷发现的主要问题 **/
			//问题项序号添加
			$("#analysisProblems").find("h5").each(function(index){
				$(this).html((index+1)+"、"+$(this).text());
			});
			
			//班主任成员显示详情事件
			$("#directorMember").children("span").each(function(){
				$(this).mouseover(function(e){				
					var innerHtml = '<p><b>年级：'+$(this).attr("data-gradeName")+'</b><b>班级：'+$(this).attr("data-className")+'</b></p>';				
					$("#memberShowDialog").html(innerHtml).css({ top: e.pageY+5, left: e.pageX+5 }).show();
				});	
				
				$(this).mouseout(function(){
					$("#memberShowDialog").hide();
				});	
			});					
			
			//教师成员显示详情事件
			$("#teacherMember").children("span").each(function(){
				$(this).mouseover(function(e){
					var innerHtml = '<p><b>年级：'+$(this).attr("data-gradeName")+'</b><b>班级：'+$(this).attr("data-className")+'</b><b>学科：'+$(this).attr("data-subjectName")+'</b></p>';				
					$("#memberShowDialog").html(innerHtml).css({ top: e.pageY+5, left: e.pageX+5 }).show();
				});		
				
				$(this).mouseout(function(){
					$("#memberShowDialog").hide();
				});				
			});					
			
			//下载
			$(".download_res").click(function(){
				directorgrade.downloadRes();	
			});
		});
	});  
	</script>
</body>
</html>