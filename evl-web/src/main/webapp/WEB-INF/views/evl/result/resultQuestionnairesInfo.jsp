<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="schoolYearHead" value="${empty questionnaires.title ? '无标题' :questionnaires.title }" />
<ui:htmlHeader title="${schoolYearHead }"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
</style>
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop modelName="${schoolYearHead }"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="evl_result"></jy:nav>
		&gt;&nbsp;<ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/>
	</div>
	<div class="flow_chart_wrap">
		<div class="view_results">
			<h3>${schoolYearHead }</h3>
			<h3 style="height:20px;line-height:20px;text-align:center;font-size:18px;">结果统计</h3>
			<div class="view_results_top1">
				<div class="left_w"></div>
				<div class="a">
					<input type="hidden" id="subject_select" value="" /> <select
						class="chosen-select-deselect" style="width: 110px; height: 25px;"
						onchange="resultQuestionnairesInfo.getDataBySubject(this)"
						id="subject_select_sel">
						<option value="" selected="selected">全部学科</option>
						<c:forEach items="${realSubjectList}" var="s" varStatus="st">
							<c:if test="${not empty s.id }">
								<option value="${s.id}">${s.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="left_border"></div>
				<div class="a">
					<input type="hidden" id="grade_select" value="" /> <select
						class="chosen-select-deselect" style="width: 110px; height: 25px;"
						onchange="resultQuestionnairesInfo.getDataByGrade(this)"
						id="grade_select_sel">
						<option value="" selected="selected">全部年级</option>
						<c:forEach items="${realGradeList }" var="g" varStatus="st">
							<c:if test="${not empty g.id }">
								<option value="${g.id }">${g.name }</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<input type="button" class="download_res" />
			</div>
			<div class="view_results_top2">
				<div id="barChart" class="echart"
					style="height: 376px; width: 100%; padding-top: 10px;">问卷结果分布率图</div>
			</div>
			<div class="view_results_top3">				
				<table id="sectionTable" class="view_results_top3_table">
				</table>
			</div>
			<div class="border_bottom"></div>
			<div class="view_results_top4">
				<h3>问卷结果一览表</h3>
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
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript"> 	
	require(["require","echarts/echarts"],function(require){		
		var echarts = require("echarts/echarts");
		$(function() {
			window.resultQuestionnairesInfo = {

					//问卷id
					questionnairesId : '${questionnaires.questionnairesId }',

					picBase64Info : '',

					//加载数据
					ajaxGetData : function(param) {
						var barChart = echarts
								.init(document.getElementById('barChart'));
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
									resultQuestionnairesInfo
											.drawTeacherTable(data.teacherList);
									resultQuestionnairesInfo
											.drawSectionTable(data.sectionList);
									resultQuestionnairesInfo.drawChart(barChart,
											data.option);
								}
							},
							error : function() {
								alert("请求异常:" + this.url);
							}
						});
					},

					//加载教师列表
					drawTeacherTable : function(teacherList) {
						$(".view_results_top4_table").empty();
						for ( var index in teacherList) {
							var teacher = teacherList[index];
							var mesArr = teacher.teacherRole.split("=,=");
							var teacherHtml = '<tr><td style="width:15%;" data-id="' + teacher.teacherId + '" ><a style="cursor: pointer;">'
									+ '<span title="'+(teacher.teacherName == null ? "": teacher.teacherName)+'">'+mesArr[1]+'</span>'
									+ '</a></td><td style="width:15%;"><span title="'+(mesArr[0] == null ? "": mesArr[0])+'">'+mesArr[2]+'</span>' 
									+ '</td><td style="width:25%;" ><span title="'+teacher.flago+'">'+mesArr[3]+'</span>' 
									+ '</td><td style="width:25%;" ><span title="'+teacher.flags+'">'+mesArr[4]+'</span>' 
									+ '</td><td style="width:10%;" >' + teacher.resultScore + '</td><td style="width:10%;" >'+ teacher.sort + '</td></tr>';
							$(".view_results_top4_table").append(teacherHtml);
						}
						$(".view_results_top4_table tr:even").css({"background":"#f9f9f9"});
						resultQuestionnairesInfo.requestTeacherResult();
					},

					//教师结果统计
					requestTeacherResult : function() {
						$(".view_results_top4_table")
								.find("a")
								.click(
										function() {
											var tds = $(this).closest("tr").children(
													"td");
											var teacherId = $(tds[0]).attr("data-id");
											var teacherScore = $(tds[4]).text();
											var teacherSort = $(tds[5]).text();
											location.href = "${ctx}jy/evl/result/resultUserIndicator?questionnairesId="
													+ resultQuestionnairesInfo.questionnairesId
													+ "&teacherId="
													+ teacherId
													+ "&resultScore="
													+ teacherScore
													+ "&sort=" + teacherSort;
										});
					},

					//柱状图
					drawChart : function(barChart, option) {
						option.tooltip.formatter = function(params){  						
				           var relVal = params[0].name;  
				           for (var i = 0, l = params.length; i < l; i++) {  
				                relVal += '<br/>' + params[i].seriesName + ' : ' + params[i].value + "%";  
				            }  
				           return relVal;  
				        }  
						option.series[0].barWidth = "40";
						option.series[0].itemStyle.normal["color"] = function(params) {
							var colorList = [ '#4CC9E7', '#FF895A', '#7ADD70',
									'#FFD700', '#FF1493', '#8B008B', '#808000',
									'#0000FF', '#DAA520' ];
							return colorList[params.dataIndex];
						};
						option.animation = false;//获取完整图片信息
						barChart.setOption(option, true);
						barChart.hideLoading();
						resultQuestionnairesInfo.picBase64Info = barChart
								.getDataURL('png');//获取图表图片信息
						//清除重新加载以实现动画效果
						barChart.clear();
						option.animation = true;
						barChart.setOption(option, true);
						barChart.hideLoading();
					},

					//分数段分组统计
					drawSectionTable : function(sectionList) {
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
					},

					reloadAjaxData : function(gradeId, subjectId) {
						var param = {
							"questionnairesId" : resultQuestionnairesInfo.questionnairesId,
							"gradeId" : gradeId,
							"subjectId" : subjectId
						};
						resultQuestionnairesInfo.ajaxGetData(param);//加载数据
					},

					//根据年级筛选
					getDataByGrade : function(dom) {
						var gradeId = $(dom).val();
						$("#grade_select").val(gradeId);
						resultQuestionnairesInfo.reloadAjaxData(gradeId, $(
								"#subject_select").val());
					},

					//根据学科筛选
					getDataBySubject : function(dom) {
						var subjectId = $(dom).val();
						$("#subject_select").val(subjectId);
						resultQuestionnairesInfo.reloadAjaxData($("#grade_select")
								.val(), subjectId);
					},

					//下载
					downLoadResult : function() {
						var param = {
							"picBase64Info" : resultQuestionnairesInfo.picBase64Info,
							"type" : "word"
						};
						$.post("${ctx}jy/evl/result/getPicFilePath",param,function(result) {
							if (result) {
								if (result.code == 200) {
									var url = "${ctx}jy/evl/result/downLoadResultInfo?questionnairesId="
											+ resultQuestionnairesInfo.questionnairesId;
									url += "&gradeId="
											+ $("#grade_select").val();
									url += "&subjectId="
											+ $("#subject_select")
													.val();
									url += "&flago=" + result.msg;
									location.href = url;//请求文件	
								}
							}
						}, "json");
					}
				}
			$("#subject_select_sel").chosen({
				disable_search : true
			});
			$("#grade_select_sel").chosen({
				disable_search : true
			});

			resultQuestionnairesInfo.ajaxGetData({
				"questionnairesId" : resultQuestionnairesInfo.questionnairesId
			});//加载数据

			//结果统计下载
			$(".download_res").click(function() {
				resultQuestionnairesInfo.downLoadResult();
			});
		});
	});
	</script>
</body>
</html>