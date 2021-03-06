<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="评教"></ui:htmlHeader>
	<style>
	html,body{color:#4a4a4a;background: #f2f2f2;
	font-size: 14px;font-family: "Microsoft YaHei"! important; }
	.flow_chart_wrap{width: 985px;height: auto;min-height: 650px;background: #fff;margin:0 auto;}
	.st_related_settings{width: 985px;height: auto;margin:0 auto;padding-bottom:40px;position: relative;}
	.st_rd_s_check_two_level_index{width: 875px;height: auto;margin:0 auto 50px auto;}
	.st_rd_s_check_two_level_index .st_r_s_h3{width: 100%;height: 80px;line-height: 95px;color: #4a4a4a;font-size: 18px;
	text-align: center;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;border-bottom: 1px #5bc79d solid;
	margin-bottom: 20px;}
	.st_rd_s_check_two_level_index .ev_inst_p{ width: 833px;text-indent: 0;line-height: 20px;font-size: 14px;margin-left: 0;
	float:left;margin-bottom: 18px;clear:none;word-wrap: break-word; }
	.st_rd_s_check_two_level_index .evaluation_instructions {width: 888px;height: 65px;margin-left: -25px;}
	.st_rd_s_check_two_level_index .evaluation_instructions .ev_inst_span{margin-left: 25px;}
	.table1_wrap,.table2_wrap{float: left;}
	.table1_wrap{width: 59.5%;border-bottom: 1px #b2b2b2 solid;border-right: 1px #b2b2b2 solid;border-top: 1px #b2b2b2 solid;
	position: relative;}
	.table2_wrap{width: 40%;border: 1px #b2b2b2 solid;border-left:none;}
	.table1_wrap_border_l{border-left: 1px #b2b2b2 solid;}
	.score_table1 {width: 100%;border: 1px #b2b2b2 solid;}
	.score_table1 tr {border-bottom: 1px #b2b2b2 solid;word-break:break-all;}
	.score_table1 tr th {height: 70px;line-height: 70px;}
	.score_table1_td {width: 165px;border-right: 1px #b2b2b2 solid;border-bottom: 1px #b2b2b2 solid;}
	.table2_wrap_title {height: 38px;line-height: 38px;font-size: 14px;font-weight: bold;text-align: center;
	border-bottom: 1px #b2b2b2 solid;border-top: 1px #b2b2b2 solid;}
	.table2_wrap_bottom {overflow-x: auto;}
	.table2_wrap table.table2 {width: 100%;float: left;overflow-x: auto;}
	.table2_wrap table.table2 tr {border-bottom: 1px #b2b2b2 solid;}
	.table2_wrap table.table2 tr th{height: 40px;line-height: 40px;font-size: 14px; text-align: center;
	border-right: 1px #b2b2b2 solid;min-width: 60px;}
	.table2_wrap table.table2 tr td{height: 47px;line-height: 47px;font-size: 14px; text-align: center;
	border-right: 1px #b2b2b2 solid;min-width: 60px;padding:0 5px; } 
	.table2_wrap table.table2_height tr td {height: 44px;line-height: 44px;}
	.name_b {float: left;display: inline-block;}
	.score_table2 {  width: 398px;}
	.score_table2 tr {border-bottom: 1px #b2b2b2 solid;} 
	.score_table2 tr td {height: 30px;padding-left: 10px;}
	.score_table2_td_height tr td {height: 45px;line-height: 45px;}
	.hide_table{position: absolute;	top: 50%;margin-top:-15px;right: 0;width:14px;cursor: pointer;height: 29px;
	background: url(${ctxStatic }/modules/evl/images/icon.png) no-repeat;background-position:-298px -45px;} 
	.select_over {min-width: 63px;height: 25px;}
	.evaluation_instructions1 {width: 917px;min-height: 50px;height: auto;margin-left: -25px;margin-top: 20px;}
	.ev_inst_span {display: inline-block;float: left;font-size: 50px;color: #ff971d;width: 30px;height: 23px;line-height: 15px;text-align: center;}
	.ev_inst_strong {font-size: 14px;display: inline-block;float: left;font-weight: bold;height: 23px;line-height: 23px;}
	.school_textarea_table {border: 1px #b3b3b3 solid;}
	.school_textarea_table tr {border-bottom: 1px #b3b3b3 solid;}
	.school_textarea_table tr th{width: 113px;height: 86px;text-align: center;line-height: 86px;border-right: 1px #b3b3b3 solid;}
	.school_textarea_table tr td {width: 759px;height: 86px;}
	.school_textarea_table tr td textarea {border: none;width: 739px;padding: 5px 10px;height: 76px;resize: none; outline: none;
	font-size: 14px;color: #4a4a4a;}
	.submit {width: 123px;height: 32px;background: #ff971d;line-height: 30px;color: #fff;font-size: 14px;
    text-align: center;display: block;margin: 0 auto;border: none;}
    .confirm{ display:block; margin:20px auto;}
	</style>
</head>
<body>
	<!--提交弹窗 -->
	<div id="submit_dialog" class="dialog">
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">提交问卷</span>
				<span class="dialog_close"  onclick="location.reload();"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info" >
					<span></span>
					<strong style="height:33px;line-height:22px;">您确定要提交此问卷吗？点击“确定”按钮即可成功提交。</strong>
				</div>
				<input type="button" onclick="saveinputquestionsure();"   class="confirm" value="确 定">
			</div>
		</div> 
	</div>
	<!--提交弹窗{未全部提交} -->
	<div id="not_submit_dialog" class="dialog">
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">提交问卷</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span>
					<strong style="height:33px;line-height:22px;" id="dialog_tip_content">您还没有全部评价完哟，请再仔细检查一下！</strong>
				</div>
				<input type="button" onclick='$("#not_submit_dialog").dialog("close")'    class="confirm" value="确 定">
			</div>
		</div> 
	</div>
<div class="flow_chart_wrap">
	<div class="st_related_settings">
		<div class="st_rd_s_check_two_level_index">
			<h3 class="st_r_s_h3">${question.title}</h3>
			<div class="evaluation_instructions">
				<span class="ev_inst_span">·</span> 
				<p class="ev_inst_p">
					<% request.setAttribute("vEnter", "\n"); %>
					${fn:replace(question.directions,vEnter,'<br/>')}
				</p>
			</div>
			<div class="table1_wrap table1_wrap_border_l" style="border-bottom:none;">
				<table class="score_table1" style="border:none;" id="indicatorContent_one">
					
				</table> 
				<div class="hide_table hide_table1"></div>
			</div>
			<div class="table2_wrap">
				<div class="table2_wrap_title" style="border-top:none;">
					评价结果
				</div>
				<div class="table2_wrap_bottom">
					<table class="table2 table2_height" id="indicator_result_content"> 
						
					</table>
				</div> 
			</div> 
			<div class="clear"></div>
			<div id="suggest_content" <c:if test="${question.iscollect!=1}" >style="display:none;"</c:if>>
			<c:if test="${not empty suggestList }">
			<div class="evaluation_instructions1">
				<span class="ev_inst_span">·</span>
				<strong class="ev_inst_strong">对学校意见或建议： </strong>
			</div>
			<div class="school_textarea">
			<form id="textarea_form">
				<table class="school_textarea_table" border="1">
					<c:forEach items="${suggestList}" var="suggest">
					<tr>
						<th>${suggest.name}</th>
						<td>
							<textarea class="school_textarea_table_textarea validate[required,min[0]]" maxlength="500"  data-suggestname="${suggest.name}" data-suggestId="${suggest.id}"  name="suggest_textarea_name" id="suggest_textarea_id" cols="30" rows="10">${suggest.flago}</textarea>
						</td>
					</tr>
					</c:forEach>
				</table>
				</form>
			</div>
			</c:if>
			<c:if test="${empty suggestList }">
			<div style="min-height: 40px;" class="evaluation_instructions1">
				<span class="ev_inst_span">·</span>
				<strong class="ev_inst_strong">对学校意见或建议： </strong>
			</div>
			<div class="school_textarea">
			<form id="textarea_form">
				<table class="school_textarea_table" border="1">
					<tr>
						<td>
							<textarea style="width: 853px;" class="school_textarea_table_textarea validate[required,min[0]]" maxlength="500"  data-suggestname="" data-suggestId=""  name="suggest_textarea_name" id="suggest_textarea_id" cols="30" rows="10">${suggestion.content}</textarea>
						</td>
					</tr>
				</table>
				</form>
			</div>
			</c:if>
			</div>
			<div class="clear"></div>
		</div>
		<input type="button" class="submit" onclick="saveinputquestiondialog();" value="提 交" /> 
	</div>
	<div class="clear"></div>
</div>
<script type="text/javascript">
window.history.forward(1);
var questionnairesId = '${question.questionnairesId}';
var submitStatus = false;
var resultBuffer =  {};//结果数据缓存
$(document).ready(function() {
	initindicatorDom(questionnairesId);
	//调整table高度
	$(".score_table2").find("tr").each(function(i,obj){
		$(".table2_height").find("tr").eq(i+1).css("height",$(this).height()+1+"px");
	});
});
function initindicatorDom(questionnairesId){
	//清理dom
	var _tr =
		'<tr>'
		+'<th colspan="2">评价内容</th>'
		+'</tr>';
	$("#indicatorContent_one").html("").append(_tr);
	var result_content_init_tr=
		'<tr>';
		<c:forEach items="${subjectList}" var="sub">
		result_content_init_tr+='<th style="height:31px;line-height:31px;" data-id="${sub.id}">${sub.name}</th>'
		</c:forEach>
		result_content_init_tr+='</tr>';
	$("#indicator_result_content").html("").html(result_content_init_tr);
	//加载指标数据并初始化
	var url = "${ctx}jy/evl/operate/input/getAllIndicator";
	var data = {
		"questionnairesId" : questionnairesId,
		"userCode":'${userCode}'
	};
	var callback=initIndicator;
	requestAjax(url,data,callback);
}
function initIndicator(data) {
	var oneIndicator = data.childIndicators;
		for ( var i in oneIndicator) {
			showOneIndicator(oneIndicator[i]);
			var twoIndicator = oneIndicator[i].childIndicators;
			for ( var j in twoIndicator) {
				showIndicatorResultBuffer(twoIndicator[j].resultList);
				showSecondIndicator(twoIndicator[j].indicator);
			}
			$(".score_table2 tr").last().css({"border-bottom-width":"0px"});
		}
		/*去除边框*/
        $('.table1_wrap_table2 tr').each(function (){
        	$(this).find("td").last().css({"border-right":"none"});
        }); 
       $('.table1_wrap .table1 tr').each(function (){
		$(this).find('td').last().css({"border-right":"none"});
	});
    $(".table2_wrap table.table2 tr").last().css("border-bottom","0px #b2b2b2 solid");
}
function showOneIndicator(indicator_i){
	var indicator = indicator_i.indicator;
	var tempTitle = indicator.title.substring(0,28)+"...";
	var title = indicator.title;
	var htmlStr = 
	'<tr>'
	+'<td title='+indicator.title+' class="score_table1_td" style="text-align:center;"><b id="'+"indicator_title_"+indicator.id+'" class="name_b">'
	+ '<span id="indicator_title_'+indicator.id+'">'
	+ title
	+ '</span>'
	+ '（<span id="indicator_score_'+indicator.id+'">'
	+ indicator.scoreTotal
	+ '</span>分）'
	+'</b>'
	+'<b class=""  onclick="Handler.editOneIndicatorDialog('
	+ "'" + indicator.id + "'" 
	+ ')"></b><b'
	+ '" class="" id="'+indicator.id+'" onclick="submitData(this,'
	+ "'"
	+ "del_one_indicators"
	+ "'"
	+ ')">'
	+'</b></td>'
	+'<td>'
	+'<table class="score_table2 score_table2_td_height" id="indicatorContent_'+indicator.id+'">'
	+'</table>'
	+'</td>'
	+'</tr>'
	$("#indicatorContent_one").append(htmlStr);
}
function showSecondIndicator(indicator){
	//指标
	var tempTitle = indicator.title.substring(0,28)+"...";
	var title =indicator.title;
	var htmlStr = 
		'<tr>'
		+'<td title='+indicator.title+'><b class="name_b">'
		+ '<span id="indicator_title_'+indicator.id+'">'
		+ title
		+ '</span>'
		+ '（<span id="indicator_score_'+indicator.id+'">'
		+ indicator.scoreTotal
		+ '</span>分）'
		+'</b>'
		+'<b class=""  onclick="Handler.editTwoIndicatorDialog('
		+ "'" + indicator.id + "'" 
		+ ')"></b><b' 
		+ '" class="" id="'+indicator.id+'" onclick="submitData(this,'
		+ "'"
		+ "del_two_indicators"
		+ "'"
		+ ')">'
		$("#indicatorContent_"+indicator.parentId).append(htmlStr);
	//评教结果
	showIndicatorResult(indicator);//显示评教结果
}
	function showIndicatorResultBuffer(result){
		if(result&&result.length>0){
			for(var k in result){
				var indicatorId = result[k].indicatorId;
				var subjectId = result[k].subjeId;
				var resultLevel = result[k].resultLevel;
				resultBuffer["indicator_"+indicatorId+"_subjectId"+subjectId] = resultLevel;
			}
		}
	}
	function showIndicatorResult(indicator) {
		var _tds = "";
		<c:forEach items="${subjectList}" var="sub">
			var optionvalue = resultBuffer["indicator_"+indicator.id+"_subjectId${sub.id}"];
			if(!optionvalue){
				optionvalue=0;
			}
			_tds += 
				'<td data-subjectId='
				+${sub.id}
				+' data-indicatorId='
				+indicator.id
				+' data-indicatorType='
				+${question.indicatorType}
				+' data-indicatorScore='
				+indicator.scoreTotal
				+' >';
					_tds+='<select onchange="evlOperatorWeight(this);" class="select_over"><option value="-1">请选择</option>' 
						<c:forEach items="${levelList}" var="level">
							_tds += '<option'
								if(optionvalue==${level.id}){
									_tds += ' selected=true';
								}
							_tds+=' value="${level.id}">${level.levelName}</option>';
						</c:forEach>
						_tds +='</select>';
				_tds+= '</td>';
		</c:forEach>
		var htmlStr = '<tr>' + _tds + '</tr>';
		$("#indicator_result_content").append(htmlStr);
		$('.table2_wrap_bottom tr').each(function (){
			$(this).find('td').last().css({"border-right":"none"});
		});
		$('.table2_wrap_bottom tr th').last().css({"border-right":"none"}); 
	}
    $('.hide_table').click(function (){
    	if($('.table2_wrap').is(":hidden")){
    		$('.table2_wrap').show();
    		$('.table1_wrap').css({"width":"59.5%"}); 
    		$('.score_table2').css({"width":"398px"});
    		$('.hide_table').css({"background-position":"-298px -45px"});
    	}else{ 
    		$('.table2_wrap').hide();
    		$('.table1_wrap').css({"width":"100%"});
    		$('.score_table2').css({"width":"100%"});
    		$('.hide_table').css({"background-position":"-316px -45px"});
    	}
    });
    //保存等级打分
    function evlOperatorWeight(dom){
    	var indicatorId = $(dom).parent().attr("data-indicatorId");//指标id
    	var subjectId = $(dom).parent().attr("data-subjectid");//学科
    	var indicatorLevel = $(dom).parent().attr("data-indicatorType");//指标等级
    	var indicatorScore = $(dom).parent().attr("data-indicatorScore");//等级对应分数
    	var resultLevel = $(dom).val();//结果等级类型
    	var resultScore = "";
    	var url = "${ctx}jy/evl/operate/saveOrUpdateLevelAndWeight";
    	var data = {
    			"userType":'${question.type}',
    			"questionnairesId":questionnairesId,
    			"indicatorId":indicatorId,
    			"indicatorLevel":indicatorLevel,
    			"resultScore":indicatorScore,
    			"subjeId":subjectId,
    			"resultLevel":resultLevel,
    			"studentId":'${tag.studentId}',
    			"studentCode":'${tag.studentCode}',
    			"orgId":'${tag.orgId}',
    			"gradeId":'${tag.gradeId}'
    	}
    	$.ajax({
       		url : url,
       		type : 'post',
       		data : data,
       		dataType : "json",
       		success : function(result) {
       			if(result.code!=200){
	       			$(dom).val(-1);
	       			alert(result.msg);
       			}
       		},
       		error:function(){
       			$(dom).val(-1);
       			alert("服务器错误，请联系管理员！");
       		}
       	});
    };
    //保存问卷填报弹窗
    function saveinputquestiondialog(){
		   	if(!checkForm()){
		   		return false;
		   	}
		  	var tip = "";	
		  	var flag = true;
    		if(!flag){
    			submitStatus = false;
    			$("#not_submit_dialog").dialog({width: 403,height: 220});
    		}else{
    		}
    			submitStatus = true;
    			$("#submit_dialog").dialog({width: 403,height: 220})
    }
  //保存问卷
    function saveinputquestionsure(){
    	if(submitStatus){
    		var url = "${ctx}jy/evl/operate/changeMemberStatus";
    		var data = {
    				"questionId":questionnairesId,
    				"userCode":'${userCode}',
    				"submitStatus":submitStatus
    		}
    		var callback = saveinputcallback;
    		$.ajax({
    	   		url : url,
    	   		type : 'post',
    	   		data : data,
    	   		dataType : "json",
    	   		success : function(result) {
    	   			if(callback){
    	   				callback(result);
    	   			}
    	   		}
    	   	});
    	}
    }
  $(".school_textarea_table_textarea").change(function(){
	  var iscollect = ${question.iscollect==null?0:1};
	  	var param = [];
	  	if(iscollect&&iscollect==1){//收集
	  		$(".school_textarea_table_textarea").each(function(i,el){
	  			var suggest = new Object();
	  			suggest.questionid = questionnairesId,
	  			suggest.name = $(el).attr("data-suggestname");
	  			suggest.content = $(el).val().trim();
	  			suggest.suggestId = $(el).attr("data-suggestId");
	  			suggest.studentCode ='${tag.studentCode}';
	  			suggest.orgId = '${tag.orgId}';
	  			if(suggest.content!=null){
	  				param.push(suggest);
	  			}
	  		})
	  	}
	  	var url = "${ctx}jy/evl/operate/saveOrUpdateInputData";
	  	jsonAjax(url,JSON.stringify(param),null);
  })
  function saveinputcallback(flag){
	  if(!flag){
		  alert("数据保存不完整，请刷新页面后重试！");
		}else{
		  $("#submit_dialog").dialog("close");
	 	  window.location.href="${ctx}jy/evl/operate/getFillPrompt?questionId="+questionnairesId+"&userCode=${userCode}";
		}
  }
   function requestAjax(url,data,callback){
   	$.ajax({
   		url : url,
   		type : 'post',
   		data : data,
   		async:false,
   		dataType : "json",
   		success : function(result) {
   			if(callback){
   				callback(result);
   			}
   		}
   	});
    }
    function jsonAjax(url, data, callback) {
    	$.ajax({
    		url : url,
    		type : 'post',
    		async:false,
    		data : data,
    		dataType : "json",
    		contentType : "application/json",
    		success : function(result) {
    			if (callback) {
    				callback(result);
    			}
    		}
    	});
    }
    function checkForm(){
		//校验某个学科是否全部提交
		var subjects={};
    	var resultMap=[];
    	$("#indicator_result_content th").each(
    		function(i,element){
    			var subjectId=$(this).attr("data-id");
    			subjects[subjectId]=$(this).html();
	    		//校验该学科下的所有指标结果
	    		var valueBuffer;
	    		$('td[data-subjectid="'+subjectId+'"] select').each(function(j,selectDom){
	    			if(valueBuffer){
	    				if(valueBuffer==-1){
			    			if($(selectDom).val()!=valueBuffer){
			    				resultMap.push(subjects[subjectId]);
			    				return false;
			    			}
	    				}else{
	    					if($(selectDom).val()==-1){
			    				tag=false;
			    				resultMap.push(subjects[subjectId]);
			    				return false;
			    			}
	    				}
	    			}else{
	    				//第一次赋值
	    				valueBuffer=$(selectDom).val();
	    			}
	    		});
    		}
    	);
    	if(resultMap.length==0){
			//校验通过    		
	    	return true;
    	}else{
    		$("#dialog_tip_content").html("您对"+resultMap.toString()+"学科老师的评价还未全部完成，请检查后再提交！");
    		$("#not_submit_dialog").dialog({width: 403,height: 220});
    		return false;
    	}
	}
</script>
</body>
</html>