<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<ui:htmlHeader title="选择学段"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/uc/modify/css/modify.css" media="screen">
</head>
<body>
	<div class="wrapper">
		<div class="jyyl_top"><ui:tchTop modelName="选择学段"></ui:tchTop></div>
		<div class="jyyl_nav">
			当前位置：
				<jy:nav id="phase_select"></jy:nav>
		</div>
		<div class="select_phase">
			<ul>
				<c:forEach items="${phaseList }" var="phase">
					<li><a href="${ctx}${sessionScope._CURRENT_SPACE_.spaceHomeUrl }?phaseId=${phase.id }"><ui:icon ext="${phase.eid }" width="187" height='187'></ui:icon></a></li>
				</c:forEach>
			</ul>
		</div>  
	</div>
	<ui:htmlFooter style="1" needCompanionSide="false"></ui:htmlFooter>
</body>
</html>