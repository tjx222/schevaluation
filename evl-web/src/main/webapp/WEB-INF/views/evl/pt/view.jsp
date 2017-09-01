<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教师工作评价"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/evl/css/teaching.css" media="screen">
</head>
<body>
	<div class="jyyl_top"><ui:tchTop modelName="教师工作评价"></ui:tchTop></div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="tch_index">
		</jy:nav>
	</div>
	<div class="teaching_cont">
		<div class="teaching_cont_bottom">
			<h3>各校评教情况一览</h3>
			<ul class="tab">
				<c:forEach items="${phaseList}" var="phase">
					<li class="${phase.id==phaseId?'li_act':''}" data-id="${phase.id}">${phase.name}</li>
				</c:forEach>
			</ul>
			<table class="teaching_cont_tab">
				<thead>
				<tr>
					<th style="width:25%">评价名称</th>
					<th style="width:25%">学校名称</th>
					<th style="width:10%">评教类型</th>
					<th style="width:15%">发布时间</th>
					<th style="width:15%">状态</th>
					<th style="width:10%">查看详情</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${dataPage.datalist}" var="data">
						<tr>
							<td><a title="${data.title}">${data.title}</a></td>
							<td title="${data.flago}">${data.flago}</td>
							<td>
								<c:if test="${data.type==1}">学生评教</c:if>
								<c:if test="${data.type==2}">家长评教</c:if>
								<c:if test="${data.type==3}">教师自评</c:if>
								<c:if test="${data.type==4}">民主互评</c:if>
							</td>
							<td><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd"/></td>
							<td>${data.status==4?'已结束':data.status<3?'未开始':'进行中'}</td>
							<c:if test="${data.status==4}">
								<td><a href="${ctx}jy/evl/analyze/analyzeInfo?questionnairesId=${data.questionnairesId}&mark=pt">查看</a></td>
							</c:if>
							<c:if test="${data.status!=4}">
								<td>查看</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="pages">
				<form id="pageForm" name="pageForm" method="post">
					<ui:page url="${ctx}jy/evl/pt/view" views="10"  data="${dataPage}"/>
					<input type="hidden" name="page.currentPage" class="currentPage">
					<input type="hidden" name="phaseId"  value="${phaseId}">
				</form>
			</div>
			<c:if test="${empty dataPage.datalist}">
				<!-- 内容为空时显示 -->
				<div class="empty_wrap">
					<div class="empty_img"></div>
					<div class="empty_info">暂时还没有信息，稍后再来吧！</div>
				</div>
			</c:if>
		</div>
	</div>
	
	<ui:htmlFooter></ui:htmlFooter>
</body>
<script>
	$('body').on('click','.tab li', function (){
		$(this).addClass('li_act').siblings().removeClass('li_act');
		var phaseId = $(this).attr("data-id");
		window.location.href=_WEB_CONTEXT_+"/jy/evl/pt/view?phaseId="+phaseId;
	});
</script>
</html>
