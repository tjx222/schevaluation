<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all">
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
var _WEB_CONTEXT_ = '${pageContext.request.contextPath }';
if(self != top)
   top.location.replace(self.location);
</script>
<title>教研平台</title>
</head>
<body>
  <ui:recorder name="test"></ui:recorder>
  <br>
  <input type="button" value="播放音频1" onclick="playRecorder('${ctx}/jy/manage/res/download/a2de3c0d23f04a97a00563e2f2964e15.mp3')"></input>

</body>
</html>