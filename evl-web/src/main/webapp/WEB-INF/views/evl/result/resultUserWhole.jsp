<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="schoolYearHead" value="${questionnaires.title? '无标题':questionnaires.title }" />
<ui:htmlHeader title="${schoolYearHead }"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
<style type="text/css">
.chosen-container-single .chosen-single {
	border: none;
}

.chosen-container.chosen-with-drop .chosen-drop {
	width: 99%;
}
</style>
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop modelName="结果"></ui:tchTop>
	</div>
	<jy:di key="${teacher.teacherId }"
		className="com.tmser.schevaluation.uc.service.UserService" var="user" />
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${empty teacher.flago }">
			<jy:nav id="evl_result"></jy:nav>
			&gt;&nbsp;<a href="${ctx}jy/evl/result/resultQuestionnairesInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;${user.name }
		</c:if>
		<c:if test="${teacher.flago eq 'analyze' }">
			<jy:nav id="evl_analyze"></jy:nav>
			&gt;&nbsp;<a href="${ctx}jy/evl/analyze/analyzeInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;${user.name }
		</c:if>
	</div>	
	<div class="flow_chart_wrap">
		<div class="view_name">
			<div class="view_name_top1">
				<div class="view_head">
					<div class="view_head_img">
						<ui:photo src="${user.photo }"></ui:photo>
					</div>
					<div class="view_head_img_mask"></div>
					<div class="view_head_title">
						<span></span>
						<div class="download_strong">
							<div class="download_strong1">教龄：${user.schoolAge }</div>
							<div class="download_strong1">性别：${user.sex == 0 ? '男' : '女' }</div>
							<div class="download_strong1">职称：${user.profession }</div>
							<div class="download_strong1">
								<div class="zw">职务：</div>
								<c:forEach items="${spaceList }" var="space">
									<div class="zw_cont">${space.spaceName }</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<ol>
					<li>${user.name }</li>
					<li>总分：${ empty teacher.resultScore ? '' : teacher.resultScore }</li>
					<li>排名：${ empty teacher.sort ? '' : teacher.sort }</li>
				</ol>
				<div class="float_right" style="width: 300px;">
					<div class="left_w"></div>
					<div class="a" style="width: 140px;">
						<select class="chosen-select-deselect" style="width: 140px; height: 25px;" id="gradeClass_sel">
							<option value="">全部年级</option>
						</select>
					</div>
					<div class="left_border"></div>
					<div class="a" style="margin-left:4px;">
						<select class="chosen-select-deselect" style="width: 110px; height: 25px;" id="subject_sel">
							<option value="">全部学科</option>
						</select>
					</div>
				</div>
			</div>
			<div class="view_name_top3">
				<div class="border_line"></div>
				<div class="view_name_top3_left">
					<div class="echart" id="barChart" style="height:356px;">评价结果分布率图表</div>
					<div class="view_name_whole_table">
						<table class="view_name_whole_table1" id="levelList">
							<tr>
								<th style="width: 33.3%">评价等级</th>
								<th style="width: 33.3%">人数</th>
								<th style="width: 33.3%">分布率</th>
							</tr>							
						</table>
					</div>
				</div>
				<div class="view_name_top3_right">
					<h3>评价结果一览表</h3>
					<div class="view_name_whole_tabl2">
						<table class="" style="width:100%;" id="studentList">
							<tr>
								<th style="width: 25%">姓名</th>
								<th style="width: 25%%">班级</th>
								<th style="width: 25%">学科</th>
								<th style="width: 25%">评价等级</th>
							</tr>							
						</table>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript">
	require(["require","echarts/echarts"],function(require){		
	var echarts = require("echarts/echarts");
	$(function() {
		var questionnairesId = "${questionnaires.questionnairesId}";
		var teacherId = "${teacher.teacherId}";

		initLoad();
		
		function initLoad() {
			var url = "${ctx}jy/evl/result/findClassAndSubjectListByUserId";
			$.getJSON(url, {
				"questionnairesId" : questionnairesId,
				"teacherId" : teacherId
			}, function(data) {
				if (data) {										
					for ( var index in data) {					
						$("#gradeClass_sel").append(
								"<option value='"+data[index].gradeId +";"+data[index].classId +";"+data[index].subjectId+"'>"
										+ data[index].gradeName + "（"
										+ data[index].className
										+ "）</option>");							
					}

					$("#gradeClass_sel").trigger("change");		
					$("#gradeClass_sel").chosen({disable_search : true});
					$("#gradeClass_sel").trigger("chosen:updated"); 							
				}
			});
		}
		
		//班级下拉变更事件
		$("#gradeClass_sel").change(function(){
			if($(this).val() == null || $(this).val() == ""){
				$("#subject_sel").html('<option value="">全部学科</option>').trigger("chosen:updated");
				var param = {"questionnairesId" : questionnairesId,
						"teacherId" : teacherId};
				loadStudentResultListByQuestionnairesId(param); 
				$("#subject_sel").chosen({disable_search : true});
				return true;
			}else{
				var ids = $(this).val().split(";");					
				$.getJSON("${ctx}jy/evl/result/getSubjectByIds", {//通过学科id加载学科下拉框
					"subjectIds" : ids[2],questionId:questionnairesId
				}, function(data) {					
					if(data){
						if(data.length > 1){
							$("#subject_sel").html('<option value="">全部学科</option>');
						}else{
							$("#subject_sel").html('');
						}
						for(var i in data){
							$("#subject_sel").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						} 
						$("#subject_sel").trigger("change");
						$("#subject_sel").chosen({disable_search : true});
						$("#subject_sel").trigger("chosen:updated"); 
					}		
																	
				});	
			}
		});		
		
		//学科下拉事件
		$("#subject_sel").change(function(){	
			var gradeClass = $("#gradeClass_sel").val();
			if(gradeClass != null && gradeClass != ""){
				var ids = gradeClass.split(";");		
				var param = {"questionnairesId" : questionnairesId,
						"teacherId" : teacherId,
						"gradeId":ids[0],
						"classId":ids[1],
						"subjectId":$(this).val()};
				loadStudentResultListByQuestionnairesId(param);
			}			
		});
		
		//学科下拉变更事件
		function loadStudentResultListByQuestionnairesId(param){
			var barChart = echarts.init(document.getElementById('barChart'));
			barChart.clear();
			barChart.showLoading({
				text : '正在努力的读取数据中...'
			});				
			$.post("${ctx}jy/evl/result/findStudentResultListByZhengti",param,function(data){
				if(data){
					initStudentResult(data.studentList);
					initStudentLevel(data.levelList);
					initStudentBarChart(barChart,data.option);
				}
			},"json");									
		}
		
		//初始化学生评价记录列表
		function initStudentResult(studentList){
			$("#studentList").find("tr:gt(0)").remove();
			for(var i in studentList){
				var studentName = studentList[i].studentName.split("=,=");
				var gradeClass = studentList[i].flago.split("=,=");
				var subjectName = studentList[i].flags.split("=,=");
				$("#studentList").append('<tr><td><span title="'+studentName[0]+'">'+studentName[1]+'</span></td><td><span title="'+gradeClass[0]+'">'+gradeClass[1]+'</span></td><td><span title="'+subjectName[0]+'">'+subjectName[1]+'</span></td><td>'+studentList[i].standby1+'</td></tr>');				
				$("#studentList tr:odd").css({"background":"#f9f9f9"});
			}
			if($("#subject_sel").find("option").length > 1 || $("#subject_sel").val() == ""){
				$("#studentList").find("tr").each(function(index){
					if(index == 0){						
						$(this).find("th").css("width","25%");
						$(this).find("th:eq(2)").show();
					}else{						
						$(this).find("td").css("width","25%");
						$(this).find("td:eq(2)").show();
					}
				});
			}else{
				$("#studentList").find("tr").each(function(index){
					if(index == 0){
						$(this).find("th:eq(2)").hide();
						$(this).find("th").css("width","33.3%");
					}else{
						$(this).find("td:eq(2)").hide();
						$(this).find("td").css("width","33.3%");
					}
				});
			}
		}
		
		//初始化学生评价等级分组列表
		function initStudentLevel(levelList){
			$("#levelList").find("tr:gt(0)").remove();
			for(var i in levelList){
				$("#levelList").append('<tr><td>'+levelList[i].levelName+'</td><td>'+levelList[i].flago+'</td><td>'+levelList[i].flags+'%</td></tr>');
			}	
			$(".view_name_whole_table1 tr:odd").css({"background":"#f9f9f9"});
		}
		
		//初始化等级分组图表
		function initStudentBarChart(barChart,option){			
			option.series[0].barWidth = "35";
			option.series[0].itemStyle.normal["color"] = function(params) {				
				var colorList = [ '#4CC9E7', '#FF895A', '#7ADD70',
						'#FFD700', '#FF1493', '#8B008B', '#808000',
						'#0000FF', '#DAA520' ];
				var index = params.dataIndex;
				if(index+1 > colorList.length){
					index = params.dataIndex - colorList.length + 1;
				}
				return colorList[params.dataIndex];
			};
			barChart.setOption(option, true);
			barChart.hideLoading();
		}
	});
	}); 
	</script>
</body>
</html>