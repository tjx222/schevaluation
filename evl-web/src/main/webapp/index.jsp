<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all">
<title>教研平台</title>
</head>
<body>
sfasdfdasf
</body>
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.cookie.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script>
$.get("http://172.16.0.162/lemon/cors.jsp",function(data){
	alert(data)
});
</script>
</html>