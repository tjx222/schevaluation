define(["require","jquery",'jp/jquery.cookie.min'], function (require) {
	var jq=require("jquery");
	require('jp/jquery.cookie.min');
	jq(document).ready(function(){ 
          if(!jq.cookie('cookie_1')){
        	jq('.cont_right_0').append('<div class="cont_right_0_h"></div>');
      	    jq('.cont_right_0').css({'z-index':'51','border':'3px #00fff6 dashed'});
      		jq('.cont_right_1').addClass('cont_right_1_bg');
      		jq('.guide2').show();
    		jq('.guide1').show();
    		jq('.guide').show();
			jq('.guide2 span').click(function (){ 
				jq('.guide2').hide();
				jq('.guide1').hide();
				jq('.cont_right_0').removeAttr("style");
				jq('.cont_right_1').css({'border':'none','background':'none'});
				jq('.add').css({'border':'3px #00fff6 dashed','z-index':'51'});
				jq('.guide3').show();
				jq('.guide4').show();
				jq('.cont_right_0_h').css('display','none');
				jq('.add').append('<div class="add_hie"></div>');
				jq('.add span').addClass('add_bg');
				jq(window).scrollTop(200);
			});
			jq('.guide4 b').click(function (){
				jq('.guide3').hide();
				jq('.guide4').hide();
				/* jq('.add').removeAttr("style"); */
				jq('.add').css({'border':'none','z-index':'0'});
				jq('.add span').css({'border':'none','background':'none'});
				jq('.guide5').show();
				jq('.guide6').show(); 
				jq('.add_hie').css('display','none');
				var span = jq("#tuozhuai dl span");
				var dom = false;
				if(span.length > 0){
					dom = jq(span[0]).parent();
					dom.css({'width':'130px','height':'110px','border':'3px #00fff6 solid','background':'#fff','margin-left':'10px','margin-top':'11px','position':'relative'});
					dom.parent().css({'width':'150px','height':'139px','border':'3px #00fff6 dashed','margin-top':'-20px','z-index':'51','position':'relative'});
					dom.append('<b class="guide5"></b>');
					dom.append('<b class="guide6">您可以点击每个工作项右上角的<img src="./static/common/images/del.png" style="width:20px;height:20px;">隐藏不常用的工作项！<b></b></b>');
					dom.append('<b id="del_b" style="position: absolute; top: 0;right: 10px;"><img src="./static/common/images/del.png" style="width:30px;height:30px;"></b>');
					dom.parent().append('<div class="hie_d" style="position: relative;width: 600px; height: 291px; left: -400px; top: -155px;"></div>');
					dom.parent().append('<div class="close_d"></div>');
				}else{
					jq('.guide').hide();
					jq('.guide3').hide();
					jq('.guide4').hide();
				}
				jq(window).scrollTop(0);
			});
			jq("body").on("click", ".close_d", function(){
				jq('.guide').hide();
				jq('.guide5').hide();
				jq('.guide6').hide(); 
				jq("#tuozhuai dl span").parent().removeAttr("style");
				jq("#tuozhuai dl span").parent().parent().removeAttr("style");
				jq('#del_b').css('display','none');
				jq('.hie_d').css('display','none');
				jq('.close_d').css('display','none');
			});
        	 jq.cookie('cookie_1',true); 
          };
	 });
});