<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<body>
<div class="score_wrap">
	<div class="score_title">总分已设定为${question.totleScoreInt}分。</div>
	<form id="score_check_formId">
	<div class="score_table_wrap">
		<table class="score_table1">
			<c:forEach items="${indicators.childIndicators}" var="oneindicator">
					<tr data-id="${oneindicator.indicator.id}" class="oneindicator_score_tr">
						<td title="${oneindicator.indicator.title}" class="score_table1_td">
						${fn:length(oneindicator.indicator.title) gt 7?fn:substring(oneindicator.indicator.title,0,7):oneindicator.indicator.title}
						<input  data-id="${oneindicator.indicator.id}" type="text" value="${oneindicator.indicator.scoreTotalInt }" class="validate[required,custom[integer],min[0],max[100]] table_txt oneindicator_score" />分
						</td>
						<td>
							<table class="score_table2" style="min-height:30px;display:block;">
								<c:forEach items="${oneindicator.childIndicators}" var="secondindicator">
									<tr class="secondindicator_score_tr">
										<td title="${secondindicator.indicator.title}" style="width: 398px;">
											${fn:length(secondindicator.indicator.title) gt 20?fn:substring(secondindicator.indicator.title,0,20):secondindicator.indicator.title}
											<c:if test="${question.indicatorType==2}">
											<input data-id="${secondindicator.indicator.id}" type="text" data-id="${secondindicator.indicator.id}" value="${secondindicator.indicator.scoreTotalInt}" class="validate[required,custom[integer],min[0],max[100]] table_txt secondindicator_score" />分
											</c:if>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td style="display: none;"
										class="prompt second_tip_${oneindicator.indicator.id}"></td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
		</table>
		<table style="display:none;" class="score_table1 border_top_none second_tip_all_table">
			<tr>
				<td  class="second_tip_all"></td>
			</tr>
		</table>
	</div>
</form>
<input type="button" class="confirm" onclick="save_score();" value="确 定" />
</div>
</body>
<script type="text/javascript">
var twoIndicatorUrl = "${ctx}/jy/evl/question/getIndicatorByQuestionIdAndLevel";
var twoIndicatorCount=0;
var onetitleLength=0;
var data = {
		"questionId":${question.questionnairesId},
		"level":2
}
$.ajax({
	url : twoIndicatorUrl,
	type : 'post',
	data : data,
	async:false,
	dataType : "json",
	success : function(result) {
		if(result.length!=0){
			twoIndicatorCount = 1;
			onetitleLength =5;
		}else{
			onetitleLength =530;
		}
	}
});
var checkScore = function (){
	var existIndicator = '${indicators.childIndicators[0]}'
		if(existIndicator==''){
			 var  blankHtml='<div class="cont_empty" style="height: 100px">'+
				'<div class="cont_empty_img1"></div>'+
				'<div class="cont_empty_words">您还没有添加指标哟！</div>'+
			'</div>';
			$(".score_wrap").html(blankHtml);
			$(".confirm").hide();
		}else{
			$(".confirm").show();
			//初始化校验分数
			if(${question.indicatorType==1}){//一级
				checkOneIndicator();
			}else if(${question.indicatorType==2}){//二级
			checkOneIndicator();
			checkSecondIndicator();
			}
		}
}
$(function(){
	if(parseInt(twoIndicatorCount)==0){
	$(".score_table1 tr").each(function(i,el){
		$(this).find('td').first().css({"border-right":"none"});
	})
	}
})
function checkOneIndicator(){
	var init_oneTotalScore = ${question.totleScore};
	var oneTotalScore=0;//
	$(".oneindicator_score").each(function(i,dom){
		oneTotalScore+=Number($(dom).val());
	})
	if(oneTotalScore!=init_oneTotalScore){
		$(".second_tip_all_table").show();
		$(".second_tip_all").html('总分为'+init_oneTotalScore+'分，当前为'+oneTotalScore+'分，请调整。');
	}else{
		$(".second_tip_all_table").hide();
	}
}
function checkSecondIndicator(){
	var init_twoTotalScore=0;
	var secondTotalScore=0;
	$(".oneindicator_score_tr").each(function(i,dom){
		secondTotalScore=0;
		init_twoTotalScore = Number($(dom).find(".oneindicator_score").val());
		$(dom).find(".secondindicator_score_tr").each(function(i,dom2){
			secondTotalScore+=Number($(dom2).find(".secondindicator_score").val())
		})
		if(init_twoTotalScore!=secondTotalScore){
			$(dom).find(".second_tip_"+$(dom).attr("data-id")).show();
			$(dom).find(".second_tip_"+$(dom).attr("data-id")).html('总分为'+init_twoTotalScore+'分，当前二级指标为'+secondTotalScore+'分，请调整。');
		}else{
			$(dom).find(".second_tip_"+$(dom).attr("data-id")).hide();
		}
	}); 
}
$(".score_table2").each(function (){ 
	var tr_len =  $(this).find("tr").last(); 
	if(tr_len.find("td").css("display") == "none" ){
		tr_len.prev().css({"border-bottom":"none"});
		tr_len.css({"border-bottom":"none"});
	}else{
		tr_len.css({"border-bottom":"none"});
	}
});
$(".oneindicator_score").change(function(){
	if(!checkForm()){
		return false;
	}
	checkScore();
})
$(".secondindicator_score").change(function(){
	if(!checkForm()){
		return false;
	}
	checkScore();
})
function save_score(){
	if(!checkForm()){
		return false;
	}
	var url = "${ctx}jy/evl/question/save_checkScoreIndicator";
	var param = [];
	$(".table_txt").each(function(i,el){
		var scoreObj = new Object();
		var id = $(el).attr("data-id");
		var scoreTotal = $(el).val();
		scoreObj.id=id;
		scoreObj.scoreTotal = scoreTotal;
		param.push(scoreObj);
	})
	var back = check_success;
	jsonAjax(url,JSON.stringify(param),back);
}
function check_success(result){
	alert(result.msg)
}
function checkForm(){
	return $("#score_check_formId").validationEngine("validate");
}
function jsonAjax(url, data, callback) {
	$.ajax({
		url : url,
		type : 'post',
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
</script>
</html>
