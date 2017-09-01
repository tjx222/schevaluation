<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	当前位置：
	<jy:nav id="ckpjzh"></jy:nav>
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
	<form action="${ctx}jy/evl/question/accountDetail"  method="post">
	<div class="see_teaching_evaluation_option">
		<div class="a1">
			发送状态：
			<select class="chosen-select-deselect" name="status" style="width: 101px; height: 25px;" onchange="this.form.submit();">
				<option value="" ${empty status?'selected':''}>全部</option>
				<option value="2" ${status==2?'selected':''}>已成功</option>
				<option value="1" ${status==1?'selected':''}>未成功</option>
			</select>
		</div>  
		<div class="serach_input">
			<label>姓名：</label>
			<input type="text" name="name" class="ser_txt" value="${name}"/>
			<input type="hidden" name="questionId" class="ser_txt" value="${question.questionnairesId}"/>
			<input type="button" class="ser_btn" onclick="this.form.submit();"/>
		</div>
		<input type="button" class="button" value="重新发送短信" onclick="retrySendMsg();" style="display:none"/>
		<input type="button" class="down_btn download_res" onclick="downloadAccount();"/>
	</div>
	</form>
	<div class="see_teaching_evaluation_table">
		<table>
			<tr>
				<th style="width:100px;">学号</th>
				<th style="width:120px;">姓名</th>
				<th style="width:70px;">性别</th> 
				<th style="width:130px;">家长电话</th>
				<th style="width:500px;">评教地址</th>
				<th style="width:100px;">发送状态</th>
			</tr>
			<c:forEach items="${memberList.datalist}" var="userDetail">
				<tr>
				<td>${userDetail.code}</td>
				<td>${userDetail.name}</td>
				<td>${userDetail.flago}</td>
				<td>${userDetail.flags}</td>
				<td><a target="_blank" href="${ctx2}o/${userDetail.url}">${ctx2}o/${userDetail.url}</a></td>
				<td>${userDetail.statusStr}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pages">
		<form id="pageForm" name="pageForm" method="post">
			<ui:page url="./jy/evl/question/accountDetail" data="${memberList}" views="12"/>
			<input type="hidden" id="curPage" class="currentPage" name="page.currentPage">
			<input type="hidden" name="name" value="${name}"/>
			<input type="hidden" name="questionId" value="${question.questionnairesId}"/>
			<input type="hidden" name="status" value="${status}"/>
		</form>
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
function downloadAccount(){
	window.open("${ctx}/jy/evl/question/downloadAccount?status=${empty status?'':status}&questionId=${question.questionnairesId}");
}
function retrySendMsg(){
	window.open("${ctx}/jy/evl/question/sendMsgByStudentsCode?status=${empty status?'':status}&questionId=${question.questionnairesId}");
}
</script>
</html>
