<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="找回密码"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/uc/retrievepassword/css/retrievepassword.css"
	media="screen">
<link rel="stylesheet"
	href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css"
	media="screen">
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#verifyUserMessage").validationEngine({
			scroll : false
		//onFieldFailure:function(){$("#errmsg").hide();}
		});
	});
	function verifyUserMessage(){
	$("#verifyUserMessage").submit();
	}
</script>

</head>
<body>
	<div class="box" style="display: none;"></div>
	<div class="wrapper">
		<div class="jyyl_top"> 
			<div class="jyyl_top_logo"> 
				<a href="jy/index">
					<div class="jyyl_logo"></div>
				</a>
			</div>
		</div> 
		<div class="jyyl_nav">  </div> 
		<div class="cont">
			<div class="psd_next">
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/1.png"
						alt=""><span>验证身份</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/21.png"
						alt=""><span>重置密码</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/31.png"
						alt=""><span>设置成功</span>
				</h3>
			</div>
			<form action="jy/uc/findps/resetpassword" id="verifyUserMessage"
				class="psd_form" method="post">
				<p><span style="color: red" id="errmsg">${error}</span></p> 
				<p>
					<label for="">用户名:</label> <input type="text" name="loginname"
						class="validate[required,minSize[4]]">
				</p>
				<p>
					<label for="">教师姓名:</label> <input type="text" name="name"
						class="validate[required,maxSize[5]]">
				</p>
				<input type="button" class="psd_btn" onclick="verifyUserMessage()"  value="下一步">
			</form>
		</div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>

</body>
</html>