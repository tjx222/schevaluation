<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.tmser.schevaluation.evl.statics.EvlQuestionType"%>
	<c:set var="question_init" value="<%=EvlQuestionType.init.getValue()%>" />
<c:set var="question_sheji1"
	value="<%=EvlQuestionType.shejiwenjuan.getValue()%>" />
<c:set var="question_sheji2"
	value="<%=EvlQuestionType.xiangguanshezhi.getValue()%>" />
<c:set var="question_fabu" value="<%=EvlQuestionType.fabu.getValue()%>" />
<c:set var="question_jieshu"
	value="<%=EvlQuestionType.jieshu.getValue()%>" />
<html>
<head>
	<ui:htmlHeader title="查看"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	} 
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
</head>

<body>
<!--头部-->
<div class="jyyl_top">
	<ui:tchTop modelName="查阅"></ui:tchTop>
</div>
<div class="jyyl_nav">
	<jy:nav id="stuindex"></jy:nav>
</div>
<div class="see_teaching_evaluation"  style="padding-bottom:0;">
	<div class="see_teaching_evaluation_one_title"> 
		${question.title}
	</div>
	<div class="see_teaching_evaluation_two_title">
		评教账号
	</div>
	
	<c:if test="${pageContext.request.serverPort == 80}">
		<c:set var="ctx2" value="${pageContext.request.scheme }://${pageContext.request.serverName }${pageContext.request.contextPath }/"/>
	</c:if>
	<c:if test="${pageContext.request.serverPort !=80}">
		<c:set var="ctx2" value="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/"/>
	</c:if>
	<div class="see_teaching_evaluation_table">
		<table width="100%">
			<tr>
				<c:set var="user" value="${_CURRENT_USER_}"></c:set>
				<th style="width:150px;">活动名称</th>
				<c:if test="${user.userType==5}">
					<th style="width:150px;">学生姓名</th>
				</c:if>
				<th >评价填报时间</th>
				<th style="width:100px;">活动发布时间</th>
				<th style="width:100px;">操作</th>
			</tr>
			<c:forEach items="${questions}" var="question">
				<tr data-status="${question.status}" class="questiontr">
				<td><font color="#00CC99">${question.title}</font></td>
				<c:if test="${user.userType==5}">
					<td>${question.name}</td>
				</c:if>
				<td>${question.beginTime}至${question.endTime}</td>
				<td>
					<fmt:formatDate value="${question.fabuTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td><a target="_blank" href="${ctx2}o/${question.url}">
				
				<c:choose>
					<c:when test="${question.status <=30}">
						<font color="#00BC8E">开始评教</font>
					</c:when>
					<c:when test="${question.status ==40}">
						<font  color="#00CCFF">已评教</font>
					</c:when>
					<c:when test="${question.status ==50}">
						<font color="#A8A8A8">已弃权</font>
					</c:when>
				</c:choose>
				</a></td>
			</tr>
			</c:forEach>
		</table>
	</div>
</div>
<div class="clear"></div>
<!--页脚-->
<ui:htmlFooter></ui:htmlFooter>
</body> 
<script>
$(".chosen-select-deselect").chosen({disable_search : true});
$(".see_teaching_evaluation_table table tr:odd").css({"background":"#f9f9f9"});
$(".see_teaching_evaluation_table tr").each(function (){
	$(this).find("th").last().css({"border-right":"none"});  
	$(this).find("td").last().css({"border-right":"none"}); 
});
$.ready(function(){
	$(".questionstr").each(function(i,el){
		var status = $(el).attr("data-status");
		if(status==1)
	})
})
</script>
</html>
