$(function(){
//	getUnreadNum();
});

var timestamp="";
//错误时间戳
var errortimeStamp=0;
var getUnreadNum=function(){
	$.ajax('./jy/notice/unreadNum?r='+Math.random()+'&timestamp='+timestamp,{
		type:'get',
		dataType:'json',
		success:function(result){
			//result=result.result;
			if(typeof result=='undefined'){
				return;
			}else if(typeof result.code =='undefined'){
				window.top.location.reload();
			}
			if(result.code==1){
				if(result.data.noticeNum>0){
					$('#notice-new').show();
					$('#noticeNum_top').text('('+result.data.noticeNum+')');
				}else{
					$('#notice-new').hide();
					$('#noticeNum_top').text('');
				}
				timestamp=result.data.noticeTimeStamp;
			}
			//再次发起请求
			setTimeout("getUnreadNum()",1000);
		},
		error:function(){
			var now=new Date().getTime();
			//如果在10秒内连续出错，则等待两分钟后再执行
			if(now-errortimeStamp<10000){
				setTimeout("getUnreadNum()",120000);
			}else{
				setTimeout("getUnreadNum()",5000);
			}
			errortimeStamp=now;
		}
		
	});
}