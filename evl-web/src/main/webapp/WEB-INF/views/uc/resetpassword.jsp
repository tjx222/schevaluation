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
		$("#resetPassword").validationEngine({
			scroll : false
		});
	});
	function resetPassword() {
		$("#resetPassword").submit();
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
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/11.png"
						alt=""><span>验证身份</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/2.png"
						alt=""><span>重置密码</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/31.png"
						alt=""><span>设置成功</span>
				</h3>
			</div>
			<form action="jy/uc/findps/saveretrievepassword" id="resetPassword"
				class="psd_form" method="post">
				<p></p>
				<p>
					<label for="">新密码:</label> <input type="hidden" name="id" value="${id}">
					<input type="password" id="newpassword" name="newpassword" placeholder="请输入6-10位密码"
						class="validate[required,minSize[6],maxSize[10]]">  

				</p>
				<p>
					<label for="">确认新密码:</label> <input type="password" placeholder="请输入6-10位密码"
						name="renewpassword"
						class="validate[required,minSize[6],equals[newpassword]]">
				</p>
				<input type="button" class="psd_btn" onclick="resetPassword()" value="下一步">
			</form>
		</div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>

</body>
</html>