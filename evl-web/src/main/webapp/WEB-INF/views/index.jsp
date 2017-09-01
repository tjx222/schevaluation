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
<title>教研平台</title>
</head>
<body>
	<shiro:user >
		<c:redirect url="/jy/uc/workspace"></c:redirect>
	</shiro:user>
	<shiro:guest>
	<div class="logo_wrap">
		<div class='logo'></div>
	</div>
	<div class="login">
		<div class="banner_login">
			<div class="banner_login_r">
				<div class="banner_login_r_c">
					<h3>登录</h3>
					<form id="login" action="jy/uc/login" method="post">
						<div style="color:#f00;position: absolute;width: 312px;">
							&nbsp;<span id="errmsg">${error }</span>
						</div>
						<div class="login_news">
						<span>账号：</span><input type="text"  name="username" class="validate[required,minSize[4]] txt"  title="用户名"/>
						</div>
						<div class="login_news">
						<span>密码：</span><input type="password"  class="validate[required,minSize[6]] pwd" name="password"/>
						</div>
						<input type="submit" class="btn" value="">
						<h4><a href="jy/uc/findps/retrievepassword" class="a_retrieve">忘记密码</a></h4>
						<h5>  
							<span style="float:left;">
								<input type="checkbox" class="check" style="margin-top: -0.5px;margin-top:-2px\9\0;" id="rememberPassword" title="请勿在公用电脑上启用。">记住密码  
							</span>
							<span style="float:right;">
								<input type="checkbox" style="margin-top: -0.5px;margin-top:-2px\9\0;" title="请勿在公用电脑上启用。" name="rememberMe" value="true">自动登录
							</span>
						</h5>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="cont">
		<div class="cont_info">
			<div class="cont_info_l">
				<img src="${ctxStatic }/modules/uc/login/images/1.png" alt="" width=40 height=40 />
				<h3>让操作更简单</h3>
				<p>流程简约、操作简单、工作量明显降低。</p>
			</div>
			<div class="cont_info_l">
				<img src="${ctxStatic }/modules/uc/login/images/2.png" alt="" width=40 height=40 />
				<h3>个性化设定</h3>
				<p>个性化设定评价指标和分值标准。</p>
			</div>
			<div class="cont_info_l">
				<img src="${ctxStatic }/modules/uc/login/images/3.png" alt="" width=40 height=40 />
				<h3>评价操作人性化</h3>
				<p>评价操作人性化，不限制时间、场所。</p>
			</div>
			<div class="cont_info_l" style="margin-right:0;">
				<img src="${ctxStatic }/modules/uc/login/images/4.png" alt="" width=40 height=40 />
				<h3>统计分析功能强大</h3>
				<p>统计分析功能强大，可生成综合分析报告。</p>
			</div>
		</div>
	</div> 
	<div class="footer">
		Copyright 2009-2014 版权所有 明博公司 京ICP备09067472号 京公网安备11010802013475号
	</div>
	</shiro:guest>
</body>
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.cookie.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script src="${ctxStatic }/modules/uc/login/js/login.js"></script>
<script>
	$(function() {
		$(".login_wrap_m_r  li").last().css("margin-right","0px");
		$(".login_type_content li").each(function(){
			var index=$(this).index();
			var pos = $(".cons").eq(index).offset().top-164;
			var self=this;
			$(this).click(function(){
				$(self).addClass("yes").siblings().removeClass("yes");
				$("body,html").animate({scrollTop:pos},50);
			})
		})
		if($.cookie("_checked_")){
			$('input[name="username"]').val($.cookie("_username_"));
			$('input[name="password"]').val($.cookie("_password_"));
			$("#rememberPassword").prop("checked",true);
		}
		$('input[name="username"]').blur(function(){
			var newname = $(this).val();
			if($.cookie("_checked_")){
				var oldname = $.cookie("_username_");
				if(newname != oldname){
					$('input[name="password"]').val("");
					$("#rememberPassword").prop("checked",false);
				}
			}
		});
		$("#login").validationEngine('attach',{
			onValidationComplete: function(form, status){
				if(status){
					if($("#rememberPassword").prop("checked")){
						var nm = $('input[name="username"]').val();
						var ps = $('input[name="password"]').val();
						$.cookie("_username_",nm,{expires:7});
						$.cookie("_password_",ps,{expires:7});
						$.cookie("_checked_",true,{expires:7});
					}else{
						$.removeCookie("_username_");
						$.removeCookie("_password_");
						$.removeCookie("_checked_");
					}
					return true;
				}
			},scroll:false,onFieldFailure:function(){$("#errmsg").hide();}});
	 
	});
	window.onload=function(){
		 window.onscroll=function(){
		  	var top=document.documentElement.scrollTop||document.body.scrollTop;
		  	var h=$(".banner").height();
		  	var h2=$(".login_wrap").height();
		  	if(top>=h+h2){
		  		$(".login_type_content_box").addClass("_fixed")
		  	}else{
		  		$(".login_type_content_box").removeClass("_fixed")
		  	}
		 }	
	}
	</script>
</html>