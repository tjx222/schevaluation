<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="提示"></ui:htmlHeader>
	<!--自定义css文件引用  -->
	<link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
</head>
<body>
	<div class="nav"></div>
	<div class="prompt_wrap">
		<div class="prompt1">
			<div class="prompt1_top"></div>
			<div class="prompt1_bottom">${msg}</div>
		</div>
	</div>
	<ui:htmlFooter></ui:htmlFooter>
</body>
</html>