<%@page import="javax.swing.text.html.CSS"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="修改信息"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/uc/modify/css/modify.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css"media="screen">
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<ui:require module="uc/login/js"></ui:require>
<style>
.file{
	width:200px;
	height:27px;
}
</style>
</head>
<body>
	<div class="wrapper"> 
		<div class="jyyl_top"><ui:tchTop modelName="个人中心"></ui:tchTop></div>
		<div class="jyyl_nav">
				当前位置：<jy:nav id="personal_index"></jy:nav> 
		</div>
		<div class='home_bg'>
			<div class="t_r_l_c_select">
	            <ol>
	            	<li class="t_r_l_c_li ${type ==  0 ? 't_r_l_c_li_act' : ''}">修改个人信息</li>
					<li class="t_r_l_c_li ${type ==  1 ? 't_r_l_c_li_act' : ''}">修改密码</li>
					<li class="t_r_l_c_li ${type ==  2 ? 't_r_l_c_li_act' : ''}">修改头像</li>
					<li class="t_r_l_c_li ${type ==  3 ? 't_r_l_c_li_act' : ''}">账号关联</li>
	            </ol>  
	        </div> 
	        <div class="info_tab_wrap">
	        	<div class="info_tab" style="${type ==  0 ? '' : 'display:none;'}">
	        		<h3 class="info_tab_h3">
	        			<span>${_CURRENT_USER_.name}</span>
	        			<strong style="line-height: 60px;">${_CURRENT_USER_.orgName}</strong>
	        		</h3>
	        		<h3 class="info_tab_h4">
	        			<b>职务：
		        			<c:forEach items="${_USER_SPACE_LIST_}" var="date">
							 	${date.spaceName}
							</c:forEach>
						</b> 
	        		</h3>
	        		<div class="border"></div>
	        		<form id="modifyuserinformation"  method="post" onsubmit="saveUser();">
		        		<div class="record clearfix">
		        			<label for=""><a>*</a>昵称：</label>
		        			<input type="text" class="validate[required,maxSize[5]] text" name="nickname" value="${user.nickname}" />
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">教龄：</label>
		        			<input type="text" class="validate[custom[integer],min[0],max[99]] text" name="schoolAge" value="${user.schoolAge}" /> 
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">职称：</label>
		        			<input type="text" class="validate[maxSize[6]] text" name="profession" value="${user.profession}" />
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">荣誉称号：</label>
		        			<input type="text" class="validate[maxSize[15]] text" name="honorary" value="${user.honorary}" />
		        		</div>
		        		<div class="record record1 clearfix">
		        			<label for="">性别：</label>
		        			<input type="radio" class="radio" <c:if test="${user.sex==0 ||empty user.sex}">checked</c:if>  name="sex" value="0">男&nbsp;&nbsp;&nbsp;<input type="radio" class="radio" <c:if test="${user.sex==1}">checked</c:if>  name="sex" value="1">女
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">联系电话：</label>
		        			<input type="text" class="validate[custom[phone],ajax[ajaxUserPhone]] text" name="cellphone" value="${user.cellphone}" />
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">出生日期：</label>
		        			<input type="text" name="birthday" class="validate[custom[dateFormat]] text"
								value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'now'})" />
		        		</div>
		        		<div class="record clearfix">
		        			<label for="">个人简介：</label>
		        			<textarea name="explains" id="" cols="20" rows="100" class='validate[maxSize[500]] textarea'>${user.explains}</textarea>
		        			<div class="zhu">注：最多可输入500个字</div>
		        		</div>
		        		<div class="bottom_btn_wrap">
		        			<input type="submit" class="preservation_btn" value="保存">
		        		</div>
	        		</form>
	       	    </div>
	       	    <div class="info_tab" style="${type ==  1 ? '' : 'display:none;'}">
	       	    	<form id="modifypassword"  method="post" onsubmit="updataPassword()">
	       	    		<ui:token />
		       	    	<div class="record2 recordTop clearfix">
		        			<label for="">姓名：</label>
		        			<span>${_CURRENT_USER_.name}</span>
		        		</div>
		        		<div class="record2 clearfix">
		        			<label for="">原密码：</label>
		        			<input type="password" class="validate[required,minSize[6],ajax[ajaxUserPassword]] text" id="password" name="password" value="" placeholder="原密码">
		        		</div>
		        		<div class="record2 clearfix">
		        			<label for="">新密码：</label>
		        			<input type="password" class="validate[required,minSize[6]] text" id="newpassword" name="newpassword" value="" placeholder="新密码">
		        		</div>
		        		<div class="record2 clearfix">
		        			<label for="">确认新密码：</label>
		        			<input type="password" class="validate[required,minSize[6],equals[newpassword]] text" id="renewpassword" name="renewpassword" value="" placeholder="确认密码" equalTo="#newpassword">
		        		</div>
		        		<div class="bottom_btn_wrap" style="margin: 40px auto;">
		        			<input type="submit" class="preservation_btn" value="确认修改" >
		        			<!-- <input type="button" class="temporarily_btn" value="暂不修改"> -->
		        		</div>
	        		</form>
	       	    </div>
	       	    <div class="info_tab" style="${type ==  2 ? '' : 'display:none;'}">
	       	    	<form id="modifyPhoto"  method="post">
       	    			<div class="head">
		       	    		<div class="head_img">
		       	    			<ui:photo src="${user.photo }" height="128" width="134"></ui:photo>
		       	    		</div>
		       	    		<div class="record2 clearfix">
		       	    			<div id="fileuploadContainer" style="margin-top: 50px; margin-left: 89px;">
			        				<label for="">上传头像：</label>
			        				<ui:upload containerID="fileuploadContainer" name="photoPath" fileType="jpg,png,gif" fileSize="1" isWebRes="true"
									relativePath="photo/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" startElementId="savephotobtn"
									callback="upload" />
			        			</div>
			        			<!-- <input type="text" class="text"> -->
			        		</div>
			        		<div class="bottom_btn_wrap" style="margin: 40px auto;">
			        			<input type="button" class="preservation_btn" value="确认修改" id="savephotobtn">
			        			<!-- <input type="button" class="temporarily_btn" value="暂不修改"> -->
			        		</div>
		       	    	</div>
	       	    	</form>
	       	    </div>
	       	    <div class="info_tab" style="${type ==  3 ? '' : 'display:none;'}">
	       	    	<form id="validationMailCode"  method="post">
		       	    	<div class="info1">如果您使用邮箱账号进行登录,请完善以下邮箱信息,并进行验证</div>
		       	    	<div class="record clearfix">
		        			<label for="">邮件地址：</label>
		        			<input type="hidden" name="uid" value="${user.id}"/>
		        			<input type="text" id="mails" class="validate[required,custom[email],ajax[ajaxUserEmail]] text" data-old="${user.mail}" name="mails" value="${user.mail}">
		        			<input type="button" value="发送验证码" id="btnSendCode">
		        			<span id="sendsuccess" style="display: none;">&nbsp;&nbsp;&nbsp;发送成功</span><a id="inmail" href="" style="color:blue;display:none;" target="_blank">&nbsp;请进入邮箱</a>
		        		</div>
		        		<div class="record" style="${not empty user.mail?'display:none':''}" id="securityCode" >
							<label for="">邮件验证码:</label>
							<input type="text" class="validate[required] text" id="verificationcode"  name="code" value="" />
							<a style="color:#aeaeae;;line-height:25px;display:block;" >&nbsp;&nbsp;填写您邮箱中收到的验证码</a>
						</div>
						<div class="clear"></div>
						<div class="btn_sava bottom_btn_wrap" style="${not empty user.mail?'display:none;':''}" id="updatemail">
							<input type="button" class="preservation_btn sava_btn" id="saveverificationcode" value="保存"/>
						</div>
	        		</form>
	       	    </div>
	        </div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div> 
</body>
<script type="text/javascript">
	require(['jquery','modify'],function(){
		
	});
</script>
</html>