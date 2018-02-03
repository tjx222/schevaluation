<%@page import="com.tmser.schevaluation.uc.bo.UserSpace"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="消息中心"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/notice/css/notice.css" media="screen" />
	<script type="text/javascript" src="${ctxStatic }/modules/notice/js/notice_index.js"></script>
</head>
<body>
<div class="wrapper"> 
	<div id="message_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head" style="width:388px;">
				<span class="dialog_title">消息标题</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="dialog_dl1"> 
					您确定要删除此消息吗？					
				</div>
				<input type="button" value="确　定" class="dialog_btn" />
			</div>
		</div>
	</div>	
	<div class='jyyl_top'>
		<ui:tchTop modelName="消息列表"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="notice"></jy:nav>
	</div>
	<div class="collective_cont">
		<h3>
			<ul id="UL">			
			</ul>
		</h3>
		<div class="collective_cont_big">
			<div class="collective_cont_tab">
				<input type="hidden" id="currentUserId" name="currentUserId" value="${_CURRENT_SPACE_.userId}"/>
				<c:if test="${data.totalCount > 0  }"> 
					<table>
						<tr>
							<th style="width: 250px;">标题</th>
							<th style="width: 100px;">发布时间</th>
							<th style="width: 50px;">删除</th>
						</tr>						
						<c:forEach items="${data.datalist}" var="item">
							<input class="userId" value="${item.senderId }" type="hidden" data-id="${item.id }" data-title="${item.title }">
							<tr>
								<td style="text-align: left;">
									&nbsp;								
									<input type="checkbox"
									class="messageCheck" 
									data-id="${item.id }"
									data-title="${item.title }"> 									
									<span>
									</span>
									<strong class="title"> 
										<a href="./jy/notice/notices/${item.id }" class="title" title="${empty item.title?item.detail:item.title }">
											<ui:sout value="${empty item.title?item.detail:item.title }" needEllipsis="true" length="75"></ui:sout>										
										</a>										
									</strong>
								</td>
								<td>
									<fmt:formatDate value="${item.sendDate }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<div class="deleteMessage" data-id="${item.id }" data-title="${item.title }"></div>
								</td>
							</tr>
						</c:forEach>						 
					</table>
					<div style="float:left;width:300px;height:30px;line-height:30px;">
					&nbsp;&nbsp;<input type="checkbox" id="selectAll" >&nbsp;<label for="selectAll">全选</label>&nbsp;&nbsp; 
					<button id="delSelectMessage">删除</button>&nbsp;&nbsp;
					</div>
				</c:if>
				<c:if test="${data.totalCount==0  }">
					<div class="empty_wrap">
						<div class="empty_info">您还没有${receiverState==0?"未读":"已读"}消息哟，稍后再来查看吧！</div>
					</div>
				</c:if>								
			</div>
			<div class="clear"></div>
			<div class="pages" style="margin-top:0px;">
				<form id="pageForm" name="pageForm" method="get">
					<ui:page url="./jy/notice/notices" data="${data}" />
					<input type="hidden" name="page.currentPage" class="currentPage">
					<input type="hidden" name="receiverState"
						value="${receiverState}">
				</form>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div>	
</body>
</html>