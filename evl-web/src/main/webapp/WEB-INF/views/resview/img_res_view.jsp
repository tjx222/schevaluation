<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${ctx}" />
<title>查看资源</title>
<style>
html,body{margin:0px; 
height:100%;
}
</style>
</head>
<body>
    	 <div style="height:100%;width:100%;overflow-y:hidden">
    		<img src="jy/manage/res/download/${resid }" />
    	</div>
</body>
</html>