<%@page import="com.tmser.schevaluation.uc.bo.UserSpace"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="消息中心"></ui:htmlHeader> 
	<link rel="stylesheet" href="${ctxStatic }/modules/notice/css/notice.css" media="screen" />
	<script type="text/javascript" src="${ctxStatic }/modules/notice/js/notice_index.js"></script>
	<style>
		.chosen-container-single .chosen-single{border:none;}
	</style>
</head>
<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop modelName="消息列表"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			当前位置：
			<jy:nav id="notice_detail">
				<jy:param name="noticeId" value="${notice.id }"></jy:param>
			</jy:nav>
		</div>
		<div class="content1">
			<div class="messageTitle">${notice.title }</div>	
			<div class="messagePrompt">
				发布人：<span>${notice.senderUserName }</span>&nbsp;&nbsp;&nbsp;&nbsp;
				发布时间：<span><fmt:formatDate value="${notice.sendDate }" pattern="yyyy-MM-dd HH:mm" /></span>
			</div>		
			<c:choose>
				<c:when test="${notice.businessType==701}">						
					<div class="messageContent">
						${notice.detail }
					</div>	
				</c:when>
				<c:when test="${notice.businessType==702}">
					<div class="messageContent">
						${notice.detail }
					</div>
				</c:when>
				<c:when test="${notice.businessType==703}">
					<div class="messageContent">
						${notice.detail }
					</div>
				</c:when>
				<c:when test="${notice.businessType==704}">
					<div class="messageContent">
						${notice.detail }
					</div>
				</c:when>
				<c:when test="${notice.businessType==705}">
					<div class="messageContent">
						${notice.detail }
					</div>
				</c:when>
				<c:otherwise>
					<div class="messageContent">
						${notice.detail }
					</div>
				</c:otherwise>
			</c:choose>				
		</div>
	</div> 
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>