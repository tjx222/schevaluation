<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="评教系统"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop modelName="查阅"></ui:tchTop>
</div>
<div class="nav">
	当前位置：<jy:nav id="evl_index" hidden="${_CURRENT_SPACE_.flags == 'true' ? '' : 0}"></jy:nav>
</div>
<div class="flow_chart_wrap">
	<div class="flow_chart_wrap_left">
		<ul>
			<li>
				<a href="${ctx}jy/evl/index" class="f_c_w_l_act">流程图</a>
			</li>
			<c:if test="${hasPower==true}">
			<li>
				<a href="${ctx}jy/evl/question/indexQuestions">设计问卷</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/result/resultIndex">结果统计</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/analyze/analyzeIndex">分析报告</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/manage/students">学生管理</a>
			</li>
			</c:if>
		</ul>
	</div>
	<div class="flow_chart_wrap_right">
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
<!-- $(".design_quest_btn").click(function(){ -->
<!-- 	window.location.href=_WEB_CONTEXT_+"/jy/evl/question/setting/; -->
<!-- }) -->
</body>
</html>