<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>错误</title>
</head>
<body>
	<div style="width:100%;height:500px;margin:0 auto;">
		<img src="${ctxStatic }/common/images/dte.png" alt="" style="margin:0 auto;">
	</div>
	<div style="display:none;">
		${errorStack}
	</div>
</body>
</html>