<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<%@page import="com.mainbo.jy.evl.statics.EvlMemberStatus"%>
<c:set var="member_tijiao" value="<%=EvlMemberStatus.tijiaowenjuan.getValue()%>" />
<html>
<head>
	<ui:htmlHeader title="查看评教情况"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
		height:29px;
		line-height:25px;
	} 
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98%;
	}
	</style>
</head>
<body>
<!--头部-->
<div class="jyyl_top">
	<ui:tchTop modelName="查看评教情况"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
	<jy:nav id="ckpjqk"></jy:nav>
</div>
<div class="see_teaching_evaluation"  style="padding-bottom:0;min-height:668px;">
	<div class="see_teaching_evaluation_one_title" title="${question.title}"> 
		${question.title}
	</div>
	<div class="see_teaching_evaluation_two_title">
		评教情况
	</div> 
	
	<form id="userForm" action="${ctx}jy/evl/question/getQuestionAccountDetail"  method="post">
	<input type="hidden" name="questionId" value="${question.questionnairesId}"></input>
	<div class="see_teaching_evaluation_option">
		<span class="submit_count_class">已评/应评：${submitCount}/${initCount}</span>
		  
		<input type="button" class="down_btn download_res" onclick="downloadQuestionMemberDetail();"/>
		<div class="a1" style="width: 150px;line-height:37px;" >
			<label>评教状态：</label>
			<select class="chosen-select-deselect status" name="status" style="width: 75px; height: 25px;" onchange="formSubmit();">
				<option value="" ${empty m.status?'selected':''}>全部</option>
				<option value="0" ${m.status==0?'selected':''}>未评</option>
				<option value="${member_tijiao}" ${m.status==member_tijiao?'selected':''}>已评</option>
			</select>
		</div>
		<div class="a1 a1_width">
			<label>班：</label>
			<select class="chosen-select-deselect classId" name="classId" style="width: 80px; height: 25px;" onchange="formSubmit();">
				<option value="" ${empty m.classId?'selected':''}>全部</option>
				<c:forEach items="${classMap}" var="class_">
				<option value="${class_.key}" ${class_.key eq m.classId?'selected':''}>${class_.value}</option>
				</c:forEach>
			</select>
		</div>  
		<div class="a1 a1_width" >
			<label>年级：</label>
			<select class="chosen-select-deselect gradeId" name="gradeId" style="width: 80px; height: 25px;" onchange="formSubmit('nj');">
				<option value="" ${empty m.gradeId?'selected':''}>全部</option>
				<c:forEach items="${gradeMap}" var="grade">
				<option value="${grade.key}" ${grade.key eq m.gradeId?'selected':''}>${grade.value}</option>
				</c:forEach>
			</select>
		</div>  
		
	</div>
	</form>
	<div class="see_teaching_evaluation_table">
		<table>
			<tr>
				<th style="width:160px;">学号</th>
				<th style="width:160px;">姓名</th>
				<th style="width:140px;">年级</th>
				<th style="width:130px;">班</th>
				<th style="width:90px;">性别</th> 
				<th style="width:140px;">家长电话</th>
				<th style="width:110px;">评教状态</th>
			</tr>
			<c:forEach items="${pageList.datalist}" var="user">
				<tr>
				<td>${user.code}</td>
				<td>${user.name}</td>
				<td>${user.gradeName}</td>
				<td>${user.className}</td>
				<td>${user.sexName}</td>
				<td>${user.cellphone}</td>
				<td>未评</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pages">
		<form id="pageForm" name="pageForm" method="post">
			<ui:page url="./jy/evl/question/getQuestionAccountDetail" data="${pageList}" views="12"/>
			<input type="hidden" id="curPage" class="currentPage" name="page.currentPage">
			<input type="hidden" name="questionId" value="${question.questionnairesId}"/>
			<input type="hidden" name="gradeId" value="${m.gradeId}"/>
			<input type="hidden" name="classId" value="${m.classId}"/>
			<input type="hidden" name="status" value="${m.status}"/>
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
var status = ${question.status};
var questionId = ${question.questionnairesId};
function downloadQuestionMemberDetail(){
		var gradeId = $(".gradeId").val();
		var classId = $(".classId").val();
		var status = $(".status").val();
		var url = "${ctx}/jy/evl/question/downloadQuestionMemberDetail?questionId="+questionId+"&gradeId="+gradeId+"&classId="+classId+"&status="+status;
		window.location.href=url
}
function formSubmit(type){
	if(type=='nj'){
		$(".classId").val(null);
	}
	$("#userForm").submit();
}
</script>
</html>
