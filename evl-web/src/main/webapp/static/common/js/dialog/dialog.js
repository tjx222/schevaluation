function drag(a) {
	function n() {
		var a = k ? k : document.body,
			b = a.scrollHeight > a.clientHeight ? a.scrollHeight : a.clientHeight,
			c = a.scrollWidth > a.clientWidth ? a.scrollWidth : a.clientWidth;
	}
	var d, e, b = 9e3,
		c = !1,
		f = $("#" + a),
		g = f.width(),
		h = f.height(),
		i = f.find(".dialog_head"),
		k = document.documentElement,
		l = ($(document).width() - f.width()) / 2,
		m = (k.clientHeight - f.height()) / 2;
	f.css({
		left: l,
		top: m
	}), i.mousedown(function(a) {
		c = !0, d = a.pageX - parseInt(f.css("left")), e = a.pageY - parseInt(f.css("top"))
	}), $(document).mouseup(function() {
		c&&f.fadeTo("fast", 1),c = !1
	}), $(document).mousemove(function(a) {
		if (c) {
			var b = a.pageX - d;
			0 >= b && (b = 0), b = Math.min(k.clientWidth - g, b) - 5;
			var i = a.pageY - e;
			0 >= i && (i = 0), i = Math.min(k.clientHeight - h, i) - 5, f.css({
				top: i,
				left: b
			})
		}
	});
}

;(function ( window, $) {
	$.fn.dialog = function(args){
			 var options={
				show:true,
				title:"",
				modal:true,
				resize:false,
				tag:0
			 };
			 var dialogSelf=this;
			 //初始化参数
			 if(typeof args == "object"){
				 for(var key in args){
					 //控制args侵入
					 options[key]=args[key];
					 if(key=="width"||key=="height"){
						 options.resize=true;
					 }
				 }
			 }
			 //初始化弹出框
			 if(options.modal&&$('.mask').length==0){
				 var mask = document.createElement('div');
				 mask.innerHTML = '<div class="mask"></div>'; 
				 window.document.body.appendChild(mask);
				 
				 //两层弹出窗体
				 var mask1 = document.createElement('div');
				 mask1.innerHTML = '<div class="mask1"></div>'; 
				 window.document.body.appendChild(mask1);
			 }
			 //初始化窗体
			 if($('.dialog_head',dialogSelf).length==0){
				 var dialogHead='<div class="dialog_head">'
						+'<span class="dialog_title">'+options.title+'</span>'
						+'<span class="dialog_close"></span>'
						+'</div>';
				 $(dialogSelf).prepend(dialogHead);
			 }else if(options.title){
				 var dialogHead='<span class="dialog_title">'+options.title+'</span>'
						+'<span class="dialog_close"></span>';
				 $('.dialog_head',dialogSelf).html(dialogHead);
			 }  
			 if(options.content){
					 $('.dialog_content',dialogSelf).html(options.content);
			  }else if(options.url){
				  if($("#dialogFrame").length > 0){  
					  $("#dialogFrame",dialogSelf).attr('src',options.url);
				  }else{
					  $('.dialog_content',dialogSelf).html('<iframe id="dialogFrame" src="'+options.url
							  +'" style="width:98%;margin:0 auto;display:block;border:none;" onload="setCwinHeight(this,false,100)" frameborder="0" scrolling="no"></iframe>');
				  }
			  }
			 //方法区
			 var close=function(tag){
				 options.tag--;
				 if(options.tag==0||tag==0){
					 $(dialogSelf).hide();
					 if(options.modal){
				 		if(!$('.mask1').is(":hidden")){
							 $('.mask1').hide();
							 $(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
							 
						 }else if(!$('.mask').is(":hidden")){
							 $('.mask').hide();
							 $(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
						 } 
					 }
				 }
				 
			 }
				
			 //初始化执行
			 if(options.resize){
				 if(options.height>0){
					 $(dialogSelf).css("height",options.height); 
					 $('.dialog_content',dialogSelf).height($(dialogSelf).height()-40)
					/* $(dialogSelf).css("top","50%");
					 $(dialogSelf).css("margin-top",-options.height/2); */
				 }
				 if(options.width>0){
					 $(dialogSelf).css("width",options.width);
					/* $(dialogSelf).css("margin-left",-options.width/2);*/
					 $(".dialog_head",dialogSelf).css("width",options.width-12);
				 }
				 if(typeof options.top  == "string"){
					 $(dialogSelf).css("top",options.top);
				 }
			 }
			 //监听事件
			 $('.dialog_close',dialogSelf).bind("click",close);
			//载入命名执行
			 if(typeof args =="string"){
				 if(args=="show"){
					 options.tag++;
					 $(dialogSelf).show();
					 if(options.modal){
				 		if($('.mask').is(":hidden")){
							 $('.mask').show();
							 $('.mask1').hide();
							 $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
						 }else{
							 $('.mask1').show();
							 $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
						 } 
					 }
				 }else if(args=="close"){
					 close(0);
					 return;
				 }
			 }else if(options.show){
				 options.tag++;
				 $(dialogSelf).show();
				 if(options.modal){
			 		if($('.mask').is(":hidden")){
						 $('.mask').show(); 
						 $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
						 $('.mask1').hide();
					 }else{
						 $('.mask1').show();
						 $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
						 $(dialogSelf).css("z-index", "104");
					 } 
				 }
			 } 
			 /*弹框拖拽调用*/
			 if($('.dialog:visible').length <= 1){
				 drag($('.dialog:visible').eq(0).attr('id'));
			 }else{
				 drag($('.dialog:visible').eq(1).attr('id'));
			 } 
		 };
})(window,jQuery); 