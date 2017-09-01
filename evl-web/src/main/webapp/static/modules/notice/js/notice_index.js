$(function(){
	//切换标签事件
	$(".collective_cont_act").click(function(){
		$(".collective_cont_act").removeClass("collective_cont_act1");
		$(this).addClass("collective_cont_act1");
		var receiverState=$(this).attr("data-status");
		location.href=_WEB_CONTEXT_+"/jy/notice/notices?receiverState="+receiverState;
	});		
	
	//单个删除
	$('.deleteMessage').click(function(){
		var id=$(this).attr('data-id');
		if(showDialog("删除消息","您确定要删除该条消息吗？删除后将无法恢复！")){
			$(".dialog_btn").click(function(){
				$("#message_dialog").dialog("close");
				$.ajax('./jy/notice/notices/'+id,{
					'type':'delete',
					'dataType':'json',
					'success':function(result){
						if(result.result.code==1){
							location.href=location.href;
						}else{
							alert("删除失败："+result.result.msg);
						}
					}
				});				
			});		
		}
	});
	
	//批量删除
	$('#delSelectMessage').click(function(){
		var j=$('.messageCheck:checked');
		if(j.length==0){
			if(showDialog("消息提示","您还没有选择要删除的消息！")){
				$(".dialog_btn").click(function(){
					$("#message_dialog").dialog("close");
				});
			}
			return false;
		}else{
			var messageIdAttr=[];
			j.each(function(){
				messageIdAttr.push($(this).attr('data-id'));
			});
			if(showDialog("批量删除消息","您确定要删除已选中的消息吗？删除后将无法恢复！")){
				$(".dialog_btn").click(function(){
					$("#message_dialog").dialog("close");					
					$.ajax('./jy/notice/deleteNotices?noticeIds='+messageIdAttr.join(','),{
						'type':'delete',
						'dataType':'json',
						'success':function(result){
							if(result.result.code==1){
								location.href=location.href;
							}else{
								alert("删除失败："+result.result.msg);
							}
						}
					});
				});
			}
		}
	});
	
	//批量标记已读
	$('#readSelectMessage').click(function(){
		var j=$('.messageCheck:checked');
		if(j.length==0){
			if(showDialog("消息提示","您还没有选择要标记已读的消息！")){
				$(".dialog_btn").click(function(){
					$("#message_dialog").dialog("close");
				});
			}
			return false;
		}else{
			var messageIdAttr=[];
			j.each(function(){
				messageIdAttr.push($(this).attr('data-id'));
			});
			if(showDialog("批量标记已读","您确定要将已选中的消息标记为已读吗？")){
				$(".dialog_btn").click(function(){
					$("#message_dialog").dialog("close");					
					$.ajax('./jy/notice/readedNotices?noticeIds='+messageIdAttr.join(','),{
						'type':'post',
						'dataType':'json',
						'success':function(result){
							if(result.result.code==1){
								location.href=location.href;
							}else{
								alert("标记已读失败："+result.result.msg);
							}
						}
					});
				});
			}
		}
	});
	
	//全选
	$('#selectAll').click(function(){
		if($(this).is(':checked')){
			$('.messageCheck').each(function(){
				this.checked=true;
			});
		}else{
			$('.messageCheck').removeAttr('checked');
		}
	});
	
	//弹出框
	function showDialog(title,content){
		$(".dialog_dd1").html(content);
		$("#message_dialog").dialog({
			width:400,
			height:220,
			"title":title
			});
		$(".dialog_close").click(function(){
			$("#message_dialog").dialog("close");
		});
		return true;		
	}
	
});