<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="提示"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<!--自定义css文件引用  -->
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/evl/css/index.css"	media="screen">
</head>
<body>
	<div class="nav"></div>
	<div class="prompt_wrap">
		<c:if test="${fillStatus=='weikaishi' }">
			<div class="prompt3">
				<div class="prompt3_top"></div>
				<div class="prompt1_bottom">此次调查问卷，尚未开始，请您核实一下哟！</div>
			</div>
		</c:if>
		<c:if test="${fillStatus=='yijieshu' }">
			<div class="prompt1">
				<div class="prompt1_top"></div>
				<div class="prompt1_bottom">此次调查问卷已结束啦！</div>
			</div>
		</c:if>
		<c:if test="${fillStatus=='yitijiao' }">
			<div class="prompt2">
				<div class="prompt2_top"></div>
				<div class="prompt1_bottom">您已对此次问卷作出了评价，感谢您的参与！</div>
			</div>
		</c:if>
	</div>
	<ui:htmlFooter></ui:htmlFooter>
</body>
 <script> 
 function CloseWebPage() {  
     if (navigator.userAgent.indexOf("MSIE") > 0) {  
         if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {  
             window.opener = null; window.close();  
         }  
         else {  
             window.open('', '_top'); window.top.close();  
         }  
     }  
     else if (navigator.userAgent.indexOf("Firefox") > 0) {  
         window.location.href = 'about:blank ';     
     }  
     else {  
         window.opener = null;   
         window.open('', '_self', '');  
         window.close();  
     }  
 }  
  </script>      
</html>