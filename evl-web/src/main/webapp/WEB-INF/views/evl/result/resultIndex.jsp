<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="统计结果"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop modelName="统计结果"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="evl_result"></jy:nav>
	</div>	
	<div class="flow_chart_wrap">
		<div class="flow_chart_wrap_left">
			<ul>
				<li><a href="${ctx}jy/evl/index">流程图</a></li>
				<li><a href="${ctx}jy/evl/question/indexQuestions">设计问卷</a></li>
				<li><a href="${ctx}jy/evl/result/resultIndex"
					class="f_c_w_l_act">结果统计</a></li>
				<li><a href="${ctx}jy/evl/analyze/analyzeIndex">分析报告</a></li>
				<li><a href="${ctx}jy/evl/manage/students">学生管理</a></li>
			</ul>
		</div>
		<div class="design_questionnaire_right">
			<div class="height2"></div>
			<c:forEach items="${currentList }" var="item">
				<div class="design_quest_div" style="cursor: pointer;" data-id="${item.evlQuestionnaires.questionnairesId }">
					<c:choose>
						<c:when test="${item.evlQuestionnaires.type == 1 }">
							<div class="srte">学生评教</div>
						</c:when>
						<c:when test="${item.evlQuestionnaires.type == 2 }">
							<div class="parent_education">家长评教</div>
						</c:when>
						<c:when test="${item.evlQuestionnaires.type == 3 }">
							<div class="parent_education">教师自评</div>
						</c:when>
						<c:otherwise>
							<div class="parent_education">民主互评</div>
						</c:otherwise>
					</c:choose>
					<c:set var="schoolYearHead"
						value="${ empty item.evlQuestionnaires.title ? '无标题' :item.evlQuestionnaires.title }" />
					<div class="design_title"
						data-id="${item.evlQuestionnaires.questionnairesId }">
						<span title="${schoolYearHead }">					
							<ui:sout value="${schoolYearHead }" length="70" needEllipsis="true" />	
						</span>	
					</div>
					<div class="not"></div>
					<div class="time_b">
						<span></span><strong>${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
						</strong>
					</div>
				</div>
			</c:forEach>
			<c:forEach items="${schoolYearList }" var="schoolYear">
				<div class="clear"></div>
				<div class="design_ques_cont" id="schoolYear${schoolYear }">
					<div class="design_ques_wrap_1">
						<h3>${schoolYear }~${schoolYear+1 }学年设计问卷</h3>
						<div class="show_hide" data-schoolYear="${schoolYear }" >
							<span></span> <strong>展开</strong>
						</div>
					</div>
					<div class="design_ques_cont1" style="display:none;"></div>
				</div>
			</c:forEach>
			<c:if test="${empty currentList && empty schoolYearList }">
				<div class="cont_empty">
					<div class="cont_empty_img"></div>
					<div class="cont_empty_words">您还没有已结束的问卷哟！</div>
				</div>
			</c:if> 
		</div>
		<div class="clear"></div>
	</div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript">
		$(function() {
			//展开事件
			$(".design_ques_wrap_1").click(
					function() {						
						var schoolYear = $(this).find(".show_hide").attr("data-schoolYear");
						var showDiv = $("#schoolYear" + schoolYear).find(
								".design_ques_cont1");
						if (showDiv.css("display") != "none") {
							$(this).find(".show_hide").find('strong').html("展开");
							$(this).find(".show_hide").find('span').css({
								"background-position" : "-296px -4px"
							});
							showDiv.hide();							
						} else {
							$(this).find(".show_hide").find('strong').html("收起");
							$(this).find(".show_hide").find('span').css({
								"background-position" : "-296px -11px"
							});
							showDiv.html("").show();
							bindSchoolYearListInfo(showDiv, schoolYear);
						}
						return true;
					});
			
			//加载对应学年下的问卷列表
			function bindSchoolYearListInfo(showDiv, schoolYear) {
				$.post("${ctx}jy/evl/result/findResultQuestionnairesList", {
					"schoolYear" : schoolYear
				}, function(data) {
					if (data) {
						bindQuestionnairesListInfo(showDiv, data);
						bindTitleClick();//点击标题进入问卷详情页面
					}
				}, "json");
			}

			//拼接问卷
			function bindQuestionnairesListInfo(showDiv, data) {
				for (var i = 0; i < data.length; i++) {
					var questionnaires = data[i].evlQuestionnaires;
					var timeline = data[i].evlTimeline;
					var content = '<div class="design_quest_div" style="cursor: pointer;" data-id="'+questionnaires.questionnairesId+'">';
					switch (questionnaires.type) {
					case 1:
						content += '<div class="srte">学生评教 </div>';
						break;
					case 2:
						content += '<div class="parent_education">家长评教</div>';
						break;
					case 3:
						content += '<div class="parent_education">教师自评</div>';
						break;
					default:
						content += '<div class="parent_education">民主互评</div>';
						break;
					}
					if (questionnaires.title == null
							|| questionnaires.title == "") {
						content += '<div class="design_title" data-id="'+questionnaires.questionnairesId+'"><span title="无标题">无标题</span></div>';
					} else {
						content += '<div class="design_title" data-id="'+questionnaires.questionnairesId+'"><span title="'
								+ questionnaires.title+'">'
								+ questionnaires.flags + '</span></div>';
					}
					var timeStr = "";
					if (timeline != null) {					
						if(typeof timeline.beginTimeStr != "undefined" && typeof timeline.endTimeStr != "undefined"){
							timeStr = timeline.beginTimeStr + '至' + timeline.endTimeStr;
						}else if(typeof timeline.beginTimeStr != "undefined"){
							timeStr = "开始时间：" + timeline.beginTimeStr;
						}else if(typeof timeline.endTimeStr != "undefined"){
							timeStr = "结束时间：" + timeline.endTimeStr;
						}else{
							timeStr = "";
						}
					}
					content += '<div class="not"></div><div class="time_b"><span></span><strong>'+timeStr+'</strong></div></div>';
					showDiv.append(content);
				}
			}

			bindTitleClick();

			//点击标题进入问卷详情页面
			function bindTitleClick() {
				$(".design_quest_div")
						.click(
								function() {
									var questionnairesId = $(this).attr(
											"data-id");
									location.href = "${ctx}jy/evl/result/resultQuestionnairesInfo?questionnairesId="
											+ questionnairesId;
								});
			}
		});
	</script>
</body>
</html>