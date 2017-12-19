<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all">
<script type="text/javascript">
var _WEB_CONTEXT_ = '${pageContext.request.contextPath }';
if(self != top)
   top.location.replace(self.location);
</script>
<link rel="stylesheet" href="${ctxStatic }/modules/uc/login/css/login1.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<title>教研平台</title>
</head>
<body>
<input type="button" value="submit" id="btn"/> 
<script>
  $(function() {
	  $("#btn").click(function(){
		  var data2={"msg":"ququ"}; 
	  $.ajax({
	　　　　　　　　url:"o/srep",
	　　　　　　　　type:"POST",
	　　　　　　　　data:JSON.stringify(data2),dataType:"json",
	　　　　　　　　contentType:"application/json",
	　　　　　　　　success:function(data){
	　　　　　　　　　　alert("request success ! ");
	　　　　　　　　}
	　　　　});
	  }
	  );
  });
  </script>
</body>


</html>