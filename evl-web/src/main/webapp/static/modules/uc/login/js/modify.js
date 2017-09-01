require(['jquery'],function(){
	var $ = require("jquery");
	$(function(){
		init();
	});
	function init(){
		$(".t_r_l_c_select ol li").click(function (){
	     	$(this).addClass("t_r_l_c_li_act").siblings().removeClass("t_r_l_c_li_act");
	     	$(".info_tab_wrap .info_tab").hide().eq($(".t_r_l_c_select ol li").index(this)).show();  
	     	 
	     });  
		
		$('.close').click(function() {
			parent.closeFrame("user_div");
		});
		$('.btn_bottom_2').click(function() {
			parent.closeFrame("user_div");
		});
		var strs = ",";
		$.each($(".formatName"),function(index,obj){
			if(strs.indexOf(","+obj.id+",") < 0 ){
				strs += obj.id+",";
			}
		});
		if(strs!=","){
			var bbspanhtml = "";
			$.each(strs.split(","),function(index,obj){
				if(obj!=null && obj!=""){
					bbspanhtml += "<span>"+obj+"</span>";
				}
			});
			$("#bbspan").html(bbspanhtml);
			$("#bbspan").css("display","");
		}
		$("#modifyuserinformation").validationEngine({
			scroll : false,
		});
		$("#modifyuserinformation").validationEngine('attach', {
			scroll : false
		});
	}
})
var InterValObj; // timer变量，控制时间
var count = 60; // 间隔函数，1秒执行
var curCount;// 当前剩余秒数
var code = ""; // 验证码
var codeLength = 6;// 验证码长度
function sendMessage(){
	//if($("#validationMailCode").validationEngine("validate","#btnSendCode")){
	curCount = count;
	var mail=$("#mails").val();
	var oldmail = $("#mails").attr("data-old");
	if(oldmail != "" && oldmail == mail){
		alert("该邮箱账号已经通过认证，无需再次认证！");
		return false;
	}
	var m=mail.split("@")[1];
	if(mail==null || mail==""){
		alert("邮箱账号不能为空，请输入");
	}else if((mail!=null || mail!="") && mail.indexOf("@")==-1 ||  m==""||m==null){
		alert("邮箱账号不合法，请重新输入");
	}else{
		if(m=="163.com"){
			$("#inmail").attr("href", "http://mail.163.com/");
		}else if(m=="126.com"){
			$("#inmail").attr("href", "http://www.126.com/");
		}else if(m=="qq.com"||(m=="vip.qq.com")){
			$("#inmail").attr("href", "http://mail.qq.com/");
		}else if(m=="yeah.net"){
			$("#inmail").attr("href", "http://www.yeah.net/");
		}else if(m=="sina.com"||(m=="sina.cn")){
			$("#inmail").attr("href", "http://mail.sina.com.cn/");
		}else if(m=="139.com"){
			$("#inmail").attr("href", "http://mail.10086.cn/");
		}else if(m=="yahoo.com.cn"||(m=="yahoo.cn")){
			$("#inmail").attr("href", "http://mail.cn.yahoo.com/");
		}else if(m=="foxmail.com"){
			$("#inmail").attr("href", "http://www.foxmail.com/");
		}else if(m=="sohu.com"){
			$("#inmail").attr("href", "http://mail.sohu.com/");
		}else if(m=="gmail.com"){
			$("#inmail").attr("href", "http://gmail.google.com");
		}else if(m=="eyou.com"){
			$("#inmail").attr("href", "http://www.eyou.com/");
		}else if(m=="wo.com.cn"){
			$("#inmail").attr("href", "http://mail.wo.com.cn/");
		}else if(m=="tom.com"||(m=="vip.tom.com")){
			$("#inmail").attr("href", "http://mail.tom.com/");
		}else if(m=="hotmail.com"||(m=="live.cn")||(m=="live.com")){
			$("#inmail").attr("href", "http://mail.sohu.com/");
		}else if(m=="21cn.com"){
			$("#inmail").attr("href", "http://mail.21cn.com/");
		}else if(m=="263.net"||(m=="263.net.cn")||(m=="x263.net")){
			$("#inmail").attr("href", "http://www.263.net/");
		}else if(m=="sogou.com"){
			$("#inmail").attr("href", "http://mail.sogou.com/");
		}else if(m=="189.cn"){
			$("#inmail").attr("href", "http://webmail15.189.cn/");
		}else if(m=="188.com"||(m=="vip.188.com")){
			$("#inmail").attr("href", "http://www.188.com/");
		}else{
			$("#inmail").hide();
		}
		// 向后台发送处理数据
		$.ajax({
			type : "POST", // 用POST方式传输
			dataType : "json", // 数据格式:JSON
			url : _WEB_CONTEXT_ +'/jy/uc/verificationCode', // 目标地址
			data :"mail=" +mail,
			success : function(data) {
				if(data.code==1){
					$("#sendsuccess").show().html("发送成功");
					updatemail();
					$("#inmail").show();
					$("#btnSendCode").attr("disabled", "true");
					$("#btnSendCode").val("重新发送"+"("+curCount+")");
					InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
				}else{
					$("#sendsuccess").show().html("发送失败,请稍后重试");
					$("#inmail").hide();
				}
				
			}
		});
	}
}

function updatemail(){
	$("#securityCode").attr("style","margin-bottom: 30px;");
	$("#updatemail").attr("style","");
	$("#btnSendCode").removeAttr("disabled"); 
}
// timer处理函数
function SetRemainTime() {
	if (curCount == 0) {
		window.clearInterval(InterValObj);// 停止计时器
		$("#btnSendCode").removeAttr("disabled");// 启用按钮
		$("#sendsuccess").attr("style", "display: none;");
		$("#btnSendCode").val("重新发送");
		code = ""; // 清除验证码。
	} else {
		curCount--;
		$("#btnSendCode").val("重新发送"+"("+curCount+")");
	}
}
//验证邮件验证码
$(function(){
	  $('#saveverificationcode').click(function(){//点击按钮提交
		//if($("#validationMailCode").validationEngine("validate"))
		  var mail=$("#mails").val();
		  var code=$("#verificationcode").val();
		  if(mail==null||mail==""){
			  alert("邮箱不能为空，请输入");
		  }else if(code==null||code==""){
			  alert("验证码不能为空，请输入");
		  }else{
			  $.ajax({
	            	 type : "POST", // 用POST方式传输
	            	 dataType : "json", // 数据格式:JSON
	                 url: _WEB_CONTEXT_ +'/jy/uc/verificationMailCode.json', // 目标地址
	                 data: $("#validationMailCode").serialize(),
	                 success : function(data) {
	         			if(data.code==1){
	         				alert("验证通过，您可以使用该邮箱地址登录了。");
	         				location.href=_WEB_CONTEXT_+"/jy/uc/modify?type=3";
	         			}else{
	         				alert("验证码输入错误或验证码已过期");
	         			}
	         		}
	            });     
		   }
	       
	      });
	  $('#btnSendCode').click(function(){sendMessage();});
});
function saveUser() {
	var hasmit = $("#modifyuserinformation").attr("hascomit");
	if(!hasmit){
		$("#modifyuserinformation").attr("hascomit",true);
		$.ajax({
			type : "post",
			dataType : "json",
			url : _WEB_CONTEXT_ + "/jy/uc/saveuserinfomobile",
			data : $("#modifyuserinformation").serialize(),
			success : function(data) {
				//刷新页面
				if(data.code==1){
					location.href=_WEB_CONTEXT_+"/jy/uc/modify?type=0";
				}else{
					alert("保存失败");
				}
			}
		});
		}
		return false;
}
function updataPassword() {
	var hasmit = $("#modifypassword").attr("hascomit");
	if(!hasmit){
		$.ajax({
			type : "post",
			dataType : "json",
			url : _WEB_CONTEXT_ + "/jy/uc/savemodifymobile",
			data : $("#modifypassword").serialize(),
			success : function(data) {
				//刷新页面
				if(data.code==1){
					location.href=_WEB_CONTEXT_+"/jy/uc/modify?type=1";
				}else{
					alert("修改失败");
				}
			}
		});
	}
	return false;
}
function upload(data) {
	if (data.code == 0) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : _WEB_CONTEXT_ + "/jy/uc/modifyUserPhoto",
			data : {photoPath:$("input[name='photoPath']").val()},
			success : function(data) {
				//刷新页面
				location.href=_WEB_CONTEXT_+"/jy/uc/modify?type=2";
			}
		});
	}
}
