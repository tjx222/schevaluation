<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<ui:htmlHeader title="评教"></ui:htmlHeader>
<head>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css"> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<body>
<div class="clear"></div>
	<div class="flow_chart_wrap flow_chart_wrap1">
	<div align="center" style="padding: 20px;height: 5px;">${question.title}</div>
		<div class="related_settings">
			<div class="design_questionnaire_list">
				<div class="d_q_content">
					<div class="d_q_content_txt">
						<div class="d_q_content_table">
							<!-- 指标begin -->
							<div class="table1_wrap" style="border-bottom: none;">
								<table class="table1" id="indicatorContent_one">
								</table>
								<div class="hide_table"></div>
							</div>
							<!-- 指标end -->
							<div class="table2_wrap" style="border:none;">
								<div class="table2_wrap_title" style="height: 79px;line-height:79px;">评价结果</div>
								<div class="table2_wrap_bottom">
									<table class="table2 table2_height one_over table2_height1" id="indicator_result_content">
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>

</body>
<script type="text/javascript"> 
var questionnairesId_view = '${question.questionnairesId}';
var onetitleLength = 0;
var twoIndicatorCount = 0;
$(document).ready(function() {
	var twoIndicatorUrl = "${ctx}/jy/evl/question/getIndicatorByQuestionIdAndLevel";
	var data = {
			"questionId":questionnairesId_view,
			"level":2
	}
	$.ajax({
		url : twoIndicatorUrl,
		type : 'post',
		data : data,
		async:false,
		dataType : "json",
		success : function(result) {
			debugger;
			if(result.length!=0){
				twoIndicatorCount = 1;
				onetitleLength =28;
			}else{
				onetitleLength =530;
			}
		}
	});
	initindicatorDom(questionnairesId_view);
})
function initindicatorDom(questionnairesId_view){
	//清理dom
	var _tr =
		'<tr>'
		+'<th colspan="2">评价内容</th>'
		+'</tr>';
	$("#indicatorContent_one").html("").append(_tr);
	//加载指标数据并初始化
	var url = "${ctx}jy/evl/indicator/setting/getAllIndicator";
	var data = {
		"questionnairesId" : questionnairesId_view
	};
	var callback=initIndicator;
	$.ajax({
		url : url,
		type : 'post',
		data : data,
		dataType : "json",
		success : function(result) {
			if (callback) {
				callback(result);
			}
		}
	});
	bindClickEvent();
}
function initIndicator(data) {
	var oneIndicator = data.childIndicators;
		for ( var i in oneIndicator) {
			showOneIndicator(oneIndicator[i].indicator);
				var twoIndicator = oneIndicator[i].childIndicators;
					for ( var j in twoIndicator) {
						showSecondIndicator(twoIndicator[j].indicator);
					}
		}
        $('.table1_wrap_table2 tr').each(function (){
        	$(this).find("td").last().css({"border-right":"none"});
        	
        });
        $('.table1_wrap_table2').each(function (){
    		$(this).find('tr').last().css({"border-bottom":"none"});
    	}); 
        showIndicatorResult();
        $('.table1_wrap table.table1 tr').each(function (){
      		 if(parseInt(twoIndicatorCount)==0){
      	          $(this).find('td').first().css({"border-right":"none"});
      	       	  }
      	}); 
}
function showOneIndicator(indicator){
	var tempTitle = indicator.title.substring(0,onetitleLength)+"...";
	var title = indicator.title.length>=onetitleLength?tempTitle:indicator.title;
	var htmlStr = 
	'<tr data-id="'+indicator.id+'" class="tr">'
	+'<td style="text-align:center;"><b id="'+"indicator_title_"+indicator.id+'" class="name_b">'
	+ '<span title="'+indicator.title+'" id="indicator_title_'+indicator.id+'">'
	+ title
	+ '</span>'
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
	+'<table class="table1_wrap_table2" id="indicatorContent_'+indicator.id+'">'
	+'</table>'
	+'</td>'
	+'</tr>'
	$("#indicatorContent_one").append(htmlStr);
	$('.table1_wrap .table1 tr').each(function (){
		$(this).find('td').last().css({"border-right":"none"});
	});
}
function showSecondIndicator(indicator){
	//指标
	var tempTitle = indicator.title.substring(0,16)+"...";
	var title = indicator.title.length>=16?tempTitle:indicator.title;
	var htmlStr = 
		'<tr>'
		+'<td title='+indicator.title+'><b class="name_b">'
		+ '<span title="'+indicator.title+'" id="indicator_title_'+indicator.id+'">'
		+ title
		+ '</span>'
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
}
function showIndicatorResult(indicator,leftheight) {
	var htmlStr = ''
		+'<tr style="border-bottom:0;"><td style="border-right:0;">'
		<c:forEach items="${subjectList}" var="sub">
			htmlStr+='<label for="" class="label_s">${sub.name}</label><select disabled="disabled" class="select_over"><option>请选择</option>';
// 			<c:forEach items="${levelList}" var="level">
// 					htmlStr += '<option value="${level.levelWeight}">${level.levelName}</option>';
// 			</c:forEach>
			htmlStr+='</select><div class="clear"></div>'
		</c:forEach>
		htmlStr+='</td></tr>';
		$("#indicator_result_content").append(htmlStr);
		changedom_height();
	}
	
	function changedom_height(){
		var height_=$("#indicatorContent_one").height();
		 $(".table2_height1").parent().css({"height":height_-81+"px","border-bottom":"1px #b2b2b2 solid"});
	}
	function ajax(url, data, callback) {
		$.ajax({
			url : url,
			type : 'post',
			data : data,
			dataType : "json",
			success : function(result) {
				if (callback) {
					callback(result.data);
				}
			},
			error : function(result) {
				alert("请求异常！" + url);
			}
		});
	}
	$('.flow_chart_wrap1 .hide_table').click(function (){
	   	 if($('.flow_chart_wrap1 .table2_wrap').is(":hidden")){
	   		$('.flow_chart_wrap1 .table2_wrap').show();
	   		$('.flow_chart_wrap1 .table1_wrap').css({"width":"59.5%"}); 
	   		$('.flow_chart_wrap1 .score_table2').css({"width":"398px"});
	   		$('.flow_chart_wrap1 .hide_table').css({"background-position":"-298px -45px"});
	   	}else{ 
	   		$('.flow_chart_wrap1 .table2_wrap').hide();
	   		$('.flow_chart_wrap1 .table1_wrap').css({"width":"100%"});
	   		$('.flow_chart_wrap1 .score_table2').css({"width":"100%"});
	   		$('.flow_chart_wrap1 .hide_table').css({"background-position":"-316px -45px"});
	   	} 
	});
</script>
</html>