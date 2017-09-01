<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page isErrorPage="true" import="java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/include/header.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误</title>
</head>
<body>
	<div style="width:100%;height:500px;margin:0 auto;">
	<div style="padding-top:150px;">
		<img src="${ctxStatic }/common/images/dte.png" style="margin:15px auto;display:block;" >
	</div>
	<div style="text-align:center;color:#ccc;font-size:15px;font-size:bold;">该资源已被作者删除！</div>
	</div>
	<div style="display: none;">
	   <%=exception == null ? "" : exception.getMessage() %><br>
	　　<%=exception == null ? "" : exception.getLocalizedMessage() %>
	　　<%
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		if(exception != null){
			exception.printStackTrace(pw);
			out.print(sw);
		}
		%>
	</div>
</body>
</html>