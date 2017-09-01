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
</head>
<body>
	<div class="box" style="display: none;"></div>
	<div class="wrap">
		<div class="jyyl_top"> 
			<div class="jyyl_top_logo"> 
				<a href="jy/index">
					<div class="jyyl_logo"></div>
				</a>
			</div>
		</div> 
		<div class="jyyl_nav">  </div> 
		<div class="cont" >
			<div class="psd_next">
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/11.png" alt=""><span>验证身份</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/21.png" alt=""><span>重置密码</span>
				</h3>
				<h4></h4>
				<h3>
					<img src="${ctxStatic }/modules/uc/retrievepassword/images/3.png" alt=""><span>设置成功</span>
				</h3>
			</div>
			<form action="jy/uc/login" class="psd_form" style="margin-top:80px;">
				
				<p style="width:350px;text-align:center;font-size:22px;font-weight:bold;line-height:40px;">
					密码设置成功！
				</p>
				<input type="submit" class="psd_btn1" value="现在去登录">
			</form>
		</div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>

</body>
</html>