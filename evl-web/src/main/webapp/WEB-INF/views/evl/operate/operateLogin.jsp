<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<html>
<head>
	<base href="${ctx}" />
	<meta charset="UTF-8">
	<title>填写问卷登陆</title>
	<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all"> 
	<style> 
		.login_wrap{
			width: 100%;
			height: 100%;
			/* position:absolute; */
			z-index:-1;
		} 
		.login_top{
			width: 847px;
			height: auto;
			min-height: 37px;
			margin:0 auto;
			background: url(${ctxStatic }/modules/evl/images/login_title_bg.png) repeat-y;
			position: absolute;
			left: 50%;
			top: 100px;
			margin-left: -424px; 
			line-height: 46px;
			font-size: 36px;
			font-weight: bold;
			color: #fff;
			text-align: center;
			padding:21px 10px;
			letter-spacing: 3px;
		}
		.login_bottom{
			width: 358px;
			height: 379px;
			background: url(${ctxStatic }/modules/evl/images/login_form.png) no-repeat;
			position: absolute;
			left: 50%;
			top: 350px;
			margin-left: -179px;
		}
		.student_id_wrap{
			width: 231px; 
			height: 41px;
			border:1px #dedede solid; 
			margin: 172px auto 0 auto;
		    display: block;
		    font-size: 14px; 
		}
		.student_id{
			width: 221px;
			height: 21px; 
			border:none;
		    display: block;
		    margin-top:10px;
		    font-size: 14px; 
		    padding-left:5px;
		}
		.login_btn{
			width: 236px;
			height: 41px;
			color: #fff;
			font-size: 14px;
			line-height: 40px;
			border:none;
			text-align: center;
			background: #f58b36;
			display: block;
			margin: 42px auto 0 auto;
			border-radius: 2px;
		}
		.login_btn:active{
			background: #de7d30;
		}
	</style> 
</head>
<body> 
<div class="login_wrap" > 
	<img style="position:fixed;" src="${ctxStatic }/modules/evl/images/login_bg.jpg" height="100%" width="100%" /> 
	<div class="login_top">
		${evlQuestionnaires.title}
	</div>
	<div class="login_bottom"> 
		<form action="" method="post" onsubmit="javascript:return sub()" >
			<div class="student_id_wrap"> 
				<input type="text" class="student_id" id="loginname" placeholder="请输入学号" name="studentCode" value="请输入学号"
				 onclick="javascript:this.value=this.value=='请输入学号'?'':this.value"  onblur="javascript:this.value=this.value==''?'请输入学号':this.value" />
			</div> 
			<input type="submit" class="login_btn" value="登录"/>
		</form>
	</div>
</div>  
	<script type="text/javascript">
	function sub(){
		if(document.getElementById("loginname").value == "请输入学号" || document.getElementById("loginname").value ==""){
			return false;
		}
		return true;
	}
	
	<!--自定义脚本函数 -->
	<c:if test="${not empty errorMsg}">
		document.write("<form action='${ctx}/jy/evl/operate/showMsg' method='post' name='formx1' style='display:none'>");
		document.write("<input type='hidden' name='msg' value='${errorMsg}'/>");
		document.write("</form>");
		document.formx1.submit();
	</c:if> 
	</script>
</body>
</html>