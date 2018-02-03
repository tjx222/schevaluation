<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.tmser.schevaluation.evl.statics.EvlQuestionType"%>
	<c:set var="status_sjwj_1" value="<%=EvlQuestionType.xiangguanshezhi.getValue()%>"/>
	<c:set var="status_sjwj_2" value="<%=EvlQuestionType.shejiwenjuan.getValue()%>"/>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="设计内容"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
</head>
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<body>
	<!--头部-->
	<div class="jyyl_top">
	<ui:tchTop modelName="设计内容"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="sjwj_2"></jy:nav>
	</div>
	<!--内容-->
	<div class="content">
	<div id="multiplexing_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">复用已有问卷<b>（只复用问卷内容）</b></span> <span
					class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="questionnaire_wrap">
					<!-- 问卷列表 -->
				</div>
				<input type="button" class="confirm" id="copy_question_sure" value="确 定" />
			</div>
		</div>
	</div>
	<!-- 提示信息 -->
	<div id="release_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close form_close"></span>
			</div>
			<div class="dialog_content">
				<div class="release_info" style="width: 180px;">
					<span class="save_img"></span> 
					<strong style="line-height: 40px; width: 130px;">恭喜您，保存成功！</strong>
				</div>
				<input type="button" class="category_btn form_close" value="关 闭"/>
			</div>
		</div>
	</div>
	<!-- 提示信息 -->
	<div id="not_release_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="release_info">
					<span class="not_save_img"></span> 
					<strong class="save_tip">您还没有设置问卷内容哟！</strong>
				</div>
				<input type="button" class="category_btn not_form_close" value="关 闭" />
			</div>
		</div>
	</div>
	<!-- 提示信息 -->
	<div id="empty_question_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="release_info">
					<span class="not_save_img"></span> 
					<strong  style="line-height: 38px;" class="save_tip">请选择一个问卷哟！</strong>
				</div>
			</div>
		</div>
	</div>
	<!-- 添加类别 -->
	<div id="add_category_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">添加类别</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="category_name">
					<label for="">类别名称：</label> 
					<form id="suggest_form">
					<input id="add_suggestType_title" type="text" class="category_txt validate[required]">
					</form>
				</div>
				<input type="button" class="category_btn" id="add_suggestType_save" value="保 存" />
			</div>
		</div>
	</div>
	<!-- 预览弹窗 -->
	<div id="view_indicator_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">问卷内容</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content" id="view_indicator_dialog_content">
			</div>
		</div>
	</div>
	<!-- 删除类别 一级-->
	<div id="one_indicators_del_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">删除</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info" style="width: 240px;">
					<span></span>
					<strong style="width: 190px;line-height:34px;">您确定要删除此一级指标吗？</strong>
				</div>
				<input type="button" class="confirm" value="确 定" onclick="Handler.delOneIndicator()"/>
			</div>
		</div>
	</div>
	<!-- 删除类别 二级-->
	<div id="two_indicators_del_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">删除</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info" style="width: 240px;">
					<span></span> <strong style="width: 190px;line-height:34px;">您确定要删除此二级指标吗？</strong>
				</div>
				<input type="button" class="confirm" value="确 定" onclick="Handler.delTwoIndicator()"/>
			</div>
		</div>
	</div>
		<!-- 删除建议类型-->
	<div id="del_suggest_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">删除</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info" style="width: 240px;">
					<span></span>
					 <strong style="width: 190px;line-height:34px;">您确定要删除此类别吗？</strong>
				</div>
				<input type="button" id="del_suggest_sure" class="confirm" value="确 定"/>
			</div>
		</div>
	</div>
	<!-- 添加一级指标 -->
	<div id="one_indicators_add_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">添加一级指标</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="add_one_level" style="margin-bottom:0;">
					<div class="one_name">
						<label for=""><span>*</span>一级指标：</label>
						<textarea maxlength="500" id="add_one_indicators_title" name="" id="" cols="30" rows="10"></textarea>
						<div class="annotation">注：最多可输入500字。</div>
					</div>
				</div>
				<div class="clear"></div>
				<input type="button" class="category_btn" onclick="submitData(this,'add_one_indicators')" value="保 存" />
			</div>
		</div>
	</div>
	<!-- 编辑一级指标 -->
	<div id="one_indicators_edit_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">编辑一级指标</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="add_one_level" style="margin-bottom:0;">
					<div class="one_name">
						<label for=""><span>*</span>一级指标：</label>
						<textarea maxlength="500" id="one_indicators_edit_title" name="" id="" cols="30" rows="10"></textarea>
						<div class="annotation">注：最多可输入500字。</div>
					</div>
				</div>
				<div class="clear"></div>
				<input type="button"  class="category_btn" onclick="submitData(this,'one_indicators_edit')" value="保 存" />
			</div>
		</div>
	</div>
	<!-- 添加二级指标 -->
	<div id="two_indicators_add_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">添加二级指标</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="add_two_level">
					<div class="one_name">
						<label for=""><span>*</span>二级指标：</label>
						<textarea maxlength="500" id="add_two_indicators_title" name="" id="" cols="30" rows="10"></textarea>
						<div class="annotation">注：最多可输入500字。</div>
					</div>
				</div>
				<div class="clear"></div>
				<input type="button"  class="category_btn" onclick="submitData(this,'add_two_indicators')" value="保 存" />
			</div>
		</div>
	</div>
	
	<!--编辑二级指标 -->
	<div id="two_indicators_edit_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">编辑二级指标</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="edit_one_level add_two_level" >
					<div class="one_name">
						<label for=""><span>*</span>二级指标：</label>
						<textarea maxlength="500" id="two_indicators_edit_title" name="" id="" cols="30" rows="10"></textarea>
						<div class="annotation">注：最多可输入500字。</div>
					</div>
				</div>
				<div class="clear"></div>
				<input type="button"  class="category_btn" onclick="submitData(this,'two_indicators_edit')" value="保 存" />
			</div>
		</div>
	</div>
	
	<div class="clear"></div>
	<div class="flow_chart_wrap flow_chart_wrap2">
		<div class="related_settings">
			<ul class="ul">
				<li class="ul_li ul_one "><a data-url="${ctx}jy/evl/question/settingQuestionRelation?questionnairesId=${question.questionnairesId}" onclick="toSettingQuestion(this)" href="javascript:void(0);"><span>1</span><strong>相关设置</strong></a></li>
			<li class="ul_li ul_two ul_act2"><a data-url="${ctx}jy/evl/question/settingQuestionIndicator?questionnairesId=${question.questionnairesId}" onclick="toSettingQuestion(this)" href="javascript:void(0);"><span>2</span><strong>设计内容</strong></a></li>
			</ul>
			<div class="download_btn" onclick="downloadhtml();">
				<div class="download_btn1"></div>
				<div class="download_title">
					<span></span> <strong>下载</strong>
				</div>
			</div>
			<div class="design_questionnaire_list">
				<div class="d_q_title">
					<span></span> <label>标题：</label> <input maxlength="80" type="text" value="${question.title}" id="question_title" class="d_q_title_txt notitle"/>
				</div>
				<div class="d_q_explain">
					<span></span> <label>问卷说明：</label>
					<textarea maxlength="2000" id="question_content" class="d_q_explain_txt">${question.directions}</textarea>
					<div class="clear"></div>
				</div>
				<div class="d_q_content">
					<span class="d_q_content_span"></span> <label
						class="d_q_content_label">问卷内容：</label><strong
						class="color multiplexing">复用已有问卷</strong>
					<div class="d_q_content_txt">
					<div class="d_q_content_one">
							<strong></strong> 
						</div>
						<div class="d_q_content_table">
							<!-- 指标begin -->
							<div class="table1_wrap" style="border-bottom: none;">
								<table class="table1" id="indicatorContent_one">
								</table>
								<div class="hide_table"></div>
							</div>
							<!-- 指标end -->
						</div>
						<div class="table2_wrap" style="border:none;">
								<div class="table2_wrap_title" style="height: 79px;line-height:79px;">评价结果</div>
								<div class="table2_wrap_bottom">
									<table class="table2 table2_height one_over" id="indicator_result_content">
									</table>
								</div>
							</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="d_q_opinion">
					<span></span> <label>对学校的意见或建议：</label>
					<div class="d_q_opinion_txt">
						<div class="d_q_opinion_txt1">
							<div class="pjlx_list pjlx_list0" style="width:70px;">
								<input data-id="1" name="suggestType" class="select_suggest" type="radio" <c:if test="${question.iscollect==1}" >checked="checked"</c:if> /><q>收集</q>
							</div>
							<div class="d_q_opinion_txt_wrap">
								<div class="add_lb_cont">
									<ol id="suggestType_li">
										<c:forEach items="${suggestTypeList}" var="suggestType">
											<li id="suggest_type_${suggestType.id}">${suggestType.name}<a
												class="delete_suggestType"
												onclick="Handler.delete_suggestType(this)"
												data-id="${suggestType.id}" href="javascript:void(0);"></a></li>
										</c:forEach>
									</ol>
								</div>
								<div class="add_type add_lb" onclick="add_type(this);">
									<b>+</b> <strong>添加收集类别</strong>
								</div>
							</div>
						</div>
						<div class="d_q_opinion_txt2">
							<div class="pjlx_list pjlx_list0">
								<input data-id="0" class="select_suggest" name="suggestType" type="radio" <c:if test="${question.iscollect==0}" >checked="checked"</c:if> /><q>不收集</q>
							</div>
						</div>
					</div>
				</div>
				<input type="button" class="save_form" value="保  存" />
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>
	</div>
<ui:htmlFooter></ui:htmlFooter>
</body>
<script type="text/javascript"> 
var questionnairesId = '${question.questionnairesId}';
var Handler = {
		currentId : "",
		currentTwoId : "",
		suggestId:"",
		copyId:"",
		addOneIndicatorDialog : function(parentId) {
			$("#add_one_indicators_title").val("");
			$('#one_indicators_add_dialog').dialog({
				width:562,
				height:342
			});
		},
		addOneIndicator : function(url, data, callback) {
			ajax(url, data, callback);
		},
		delOneIndicator : function() {
			var url = "${ctx}jy/evl/indicator/setting/del_one_indicators";
			var data = {
				'id' : this.currentId
			};
			$("#one_indicators_del_dialog").dialog("close");
			ajax(url, data, delOneIndicator);
		},
		delTwoIndicator : function() {
			var url = "${ctx}jy/evl/indicator/setting/del_two_indicators";
			var data = {
				'id' : this.currentId
			};
			$("#two_indicators_del_dialog").dialog("close");
			ajax(url, data, delOneIndicator);
		},
		editOneIndicatorDialog : function(id) {
			this.currentId = id;
			var url = "${ctx}jy/evl/indicator/setting/getIndicator";
			$.ajax({
				url:url,
				type : 'post',
				data : {
					"id" : id,
				},
				dataType : "json",
				success : function(result) {
					var indicator = result.data;
					$("#one_indicators_edit_title").val(indicator.title);
					$("#one_indicators_edit_dialog").dialog({
						width:562,
						height:342
					});
				}
			});
		},
		addTwoIndicatorDialog : function(parentId) {
			this.currentId = parentId;
			$("#add_two_indicators_title").val("");
			$("#two_indicators_add_dialog").dialog({
				width:562,
				height:342
			});
		},
		editTwoIndicatorDialog : function(id) {
			this.currentTwoId = id;
			var url = "${ctx}jy/evl/indicator/setting/getIndicator";
			$.ajax({
				url:url,
				type : 'post',
				data : {
					"id" : id,
				},
				dataType : "json",
				success : function(result) {
					var indicator = result.data;
					$("#two_indicators_edit_title").val(indicator.title);
					$("#two_indicators_edit_dialog").dialog({
						width:562,
						height:342
					});
				}
			});
		},
		addTwoIndicator : function(url, data, callback) {
			ajax(url, data, callback);
		},
		show_add_suggestType : function(data) {
	 		var htmlStr =
	 		'<li id="suggest_type_'
	 		+data.id
	 		+'">'
	 		+data.name+'<a data-id="'+data.id+'" class="delete_suggestType" onclick="Handler.delete_suggestType(this)" href="javascript:void(0);"></a></li>';
	 		$("#suggestType_li").append(htmlStr);
	 		$("#add_category_dialog").dialog("close");
	 		isshow_addType_btn();
		},
		delete_suggestType:function(dom){
        	Handler.suggestId = $(dom).attr("data-id");
        	$('#del_suggest_dialog').dialog({
				width: 421,
				height: 205
			});
		},
	}
$(document).ready(function() {
	initindicatorDom(questionnairesId);
	isshow_addType_btn();
	isShowSuggest(parseInt('${question.iscollect}'));
})
$('.notitle').placeholder({
word: '无标题'
})
function initindicatorDom(questionnairesId){
	//清理dom
	var _tr =
		'<tr>'
		+'<th colspan="2">评价内容</th>'
		+'</tr>';
	$("#indicatorContent_one").html("").append(_tr);
	//加载指标数据并初始化
	var url = "${ctx}jy/evl/indicator/setting/getAllIndicator";
	var data = {
		"questionnairesId" : questionnairesId
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
		showSecondIndicatorBtn(oneIndicator[i].indicator);
		}
		showOneIndicatorBtn(twoIndicator);

        $('.table1_wrap_table2 tr').each(function (){
        	$(this).find("td").last().css({"border-right":"none"});
        	
        });
        showIndicatorResult();
//         changedom_height();
}
function showOneIndicator(indicator){
	var tempTitle = indicator.title.substring(0,8)+"...";
	var title = indicator.title.length>=8?tempTitle:indicator.title;
	var htmlStr = 
	'<tr data-id="'+indicator.id+'" class="tr">'
	+'<td style="text-align:center;"><b class="name_b">'
	+ '<span title="'+indicator.title+'" id="indicator_title_'+indicator.id+'">'
	+ title
	+ '</span>'
	+'</b>'
	+'<b class="edit_b"  onclick="Handler.editOneIndicatorDialog('
	+ "'" + indicator.id + "'" 
	+ ')"></b><b'
	+ '" class="del_b" id="'+indicator.id+'" onclick="submitData(this,'
	+ "'"
	+ "del_one_indicators"
	+ "'"
	+ ')">'
	+'</b></td>'
	+'<td style="border-right:none;">'
	+'<table class="table1_wrap_table2" id="indicatorContent_'+indicator.id+'">'
	+'</table>'
	+'</td>'
	+'</tr>'
	$("#indicatorContent_one").append(htmlStr);
	$('.table1_wrap table.table1 tr').each(function (){
		$(this).find('td').last().css({"border-right":"none"});
	});
}
function showOneIndicatorAdd(indicator){
	var tempTitle = indicator.title.substring(0,8)+"...";
	var title = indicator.title.length>=8?tempTitle:indicator.title;
	var htmlStr = 
	'<tr data-id="'+indicator.id+'" class="tr">'
	+'<td><b class="name_b">'
	+ '<span title="'+indicator.title+'" id="indicator_title_'+indicator.id+'">'
	+ title
	+ '</span>'
	+'</b>'
	+'<b class="edit_b"  onclick="Handler.editOneIndicatorDialog('
	+ "'" + indicator.id + "'" 
	+ ')"></b><b'
	+ '" class="del_b" id="'+indicator.id+'" onclick="submitData(this,'
	+ "'"
	+ "del_one_indicators"
	+ "'"
	+ ')">'
	+'</b></td>'
	+'<td style="border-right:none;">'
	+'<table class="table1_wrap_table2" id="indicatorContent_'+indicator.id+'">'
	+'</table>'
	+'</td>'
	+'</tr>'
	$(".add_one_tr").before(htmlStr);
	 var btn = 
		'<tr style="border-bottom:none;" class="add_two_td_'+indicator.id+'">'
		+'<td style="border-right:none;" class=""><a onclick="Handler.addTwoIndicatorDialog('
		+ "'" + indicator.id + "'" 
		+')" href="javascript:void(0);"  class="add_one"> <q>+</q> <strong>添加二级指标</strong></a></td>'
		+'</tr>';
	$("#indicatorContent_"+indicator.id).append(btn);
	changedom_height(); 
}
function showOneIndicatorBtn(indicator){
	var htmlStr = 
	'<tr class="add_one_tr">'
 	+'<td>'
	+'<a href="javascript:void(0);" class="add_one">'
	+'<q>+</q>'
	+'<strong onclick="Handler.addOneIndicatorDialog()">添加一级指标</strong>'
	+'</a>'
	+'</td>' 
	+'<td style="border-right:none;">'
	+'<a href="javascript:void(0);" class="add_two">'
	+'<q>+</q>'
	+'<strong>添加二级指标</strong>'
	+'</a>'
	+'</td>' 
	+'</tr>';
	$("#indicatorContent_one").append(htmlStr);
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
		+'<b class="edit_b"  onclick="Handler.editTwoIndicatorDialog('
		+ "'" + indicator.id + "'" 
		+ ')"></b><b' 
		+ '" class="del_b" id="'+indicator.id+'" onclick="submitData(this,'
		+ "'"
		+ "del_two_indicators"
		+ "'"
		+ ')">'
		$("#indicatorContent_"+indicator.parentId).append(htmlStr);
		$('.table2_wrap_bottom tr').each(function (){
			$(this).find('td').last().css({"border-right":"none"});
		}); 
		
}
function showSecondIndicatorAdd(indicator){
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
		+'<b class="edit_b"  onclick="Handler.editTwoIndicatorDialog('
		+ "'" + indicator.id + "'" 
		+ ')"></b><b' 
		+ '" class="del_b" id="'+indicator.id+'" onclick="submitData(this,'
		+ "'"
		+ "del_two_indicators"
		+ "'"
		+ ')">';
		$(".add_two_td_"+indicator.parentId).before(htmlStr);
}
function showSecondIndicatorBtn(indicator){
	//增加添加指标按钮
	var htmlStr = 
	'<tr style="border-bottom:none;"class="add_two_tr add_two_td_'+indicator.id+'">'
	+'<td style="border-right:none;" class=""><a onclick="Handler.addTwoIndicatorDialog('
	+ "'" +indicator.id + "'" 
	+')" href="javascript:void(0);"  class="add_one"> <q>+</q> <strong>添加二级指标</strong></a></td>'
	+'</tr>';
	$("#indicatorContent_"+indicator.id).append(htmlStr);
}
	function showIndicatorResult(indicator,leftheight) {
	var htmlStr = ''
		+'<tr style="border-bottom:none;"><td style="border-right:none;">'
		<c:forEach items="${subjectList}" var="sub">
			htmlStr+='<label for="" class="label_s">${sub.name}</label><select disabled="disabled" class="select_over"><option>请选择</option>'
// 			<c:forEach items="${levelList}" var="level">
// 					htmlStr += '<option value="${level.levelWeight}">${level.levelName}</option>'
// 			</c:forEach>
			htmlStr+='</select><div class="clear"></div>'
		</c:forEach>
		htmlStr+='</td></tr>';
		$("#indicator_result_content").append(htmlStr);
		changedom_height(); 
	}
	
	function changedom_height(){
		var height_=$("#indicatorContent_one").height();
		 $(".table2_height").css("height",height_-80+"px");
		 $(".table2_wrap_bottom").css({"height":height_-81+"px","border-bottom":"1px #b2b2b2 solid"});
	}
	//页面提交按钮事件
	function submitData(dom, key) {
		var url = "${ctx}/jy/evl/indicator/setting/";
		//页面提交的数据集合
		var data = {};
		//ajax提交成功后的callback事件
		var callback;
		switch (key) {
		case "del_one_indicators":
			var id = $(dom).attr("id");
			Handler.currentId = id;
			$("#one_indicators_del_dialog").dialog({
				width:420,
				height:210
			});
			break;
		case "del_two_indicators":
			var id = $(dom).attr("id");
			Handler.currentId = id;
			$("#two_indicators_del_dialog").dialog({
				width:420,
				height:210
			});
			break;
		case "add_one_indicators":
			var title = $("#" + key + "_title").val();
			data = {
				"title" : title,
				"level" : 1,
				"questionnairesId":questionnairesId
			};
			callback = showOneIndicatorAdd;
			url += key;
			Handler.addOneIndicator(url, data, callback);
			$("#one_indicators_add_dialog").dialog("close");
			break;
		case "one_indicators_edit":
			var title = $("#" + key + "_title").val();
			data = {
				"title" : title,
				"id" : Handler.currentId
			};
			url += key;
			ajax(url, data, callback);
			var tempTitle = title.substring(0,8)+"...";
			tempTitle = title.length>=4?tempTitle:title;
			$("#indicator_title_" + Handler.currentId).html(tempTitle);
			$("#indicator_title_" + Handler.currentId).attr("title",title);
			$("#one_indicators_edit_dialog").dialog("close");
			break;
		case "add_two_indicators":
			var title = $("#" + key + "_title").val();
			data = {
				"title" : title,
				"level" : 2,
				"parentId" : Handler.currentId,
				"questionnairesId":questionnairesId
			};
			callback = showSecondIndicatorAdd;
			url += key;
			Handler.addTwoIndicator(url, data, callback);
			$("#two_indicators_add_dialog").dialog("close");
			break;
		case "two_indicators_edit":
			var title = $("#" + key + "_title").val();
			data = {
				"title" : title,
				"id" : Handler.currentTwoId
			};
			url += "two_indicators_edit";
			ajax(url, data, callback);
			var tempTitle = title.substring(0,16)+"...";
			tempTitle = title.length>=16?tempTitle:title;
			$("#indicator_title_" + Handler.currentTwoId).html(tempTitle);
			$("#indicator_title_" + Handler.currentTwoId).attr("title",title);
			$("#two_indicators_edit_dialog").dialog("close");
			break;
		case "del_two_indicators":
			var id = $(dom).attr("id");
			Handler.currentId = id;
			$("#one_indicators_del_dialog").dialog({
				width:420,
				height:210
			});
			break;
	}
		}
	//清除指标节点
	function delOneIndicator(data) {
		location.reload();
	}
	function bindClickEvent(){
        $("#add_suggestType_save").click(function(){
        	if(!$("#suggest_form").validationEngine("validate")){
        		return false;
        	}
        	var url = "${ctx}/jy/evl/question/setQuestionSuggestion";
        	var data={
        			"name":$("#add_suggestType_title").val(),
        			"questionnairesId":questionnairesId
        	}
        	var callback = Handler.show_add_suggestType;
        	ajax(url, data, callback)
        });

        $("#del_suggest_sure").click(function(){
        	var id=Handler.suggestId;
    		var url = "${ctx}/jy/evl/question/delSuggestType";
    		var data ={
    				"id":id
    		};
    		$.ajax({
    			url : url,
    			type : 'post',
    			data : data,
    			dataType : "json",
    			success : function(result) {
    				if(result.code=200){
    					$("#suggest_type_"+id).remove();
    					isshow_addType_btn();
    				}else{
    					alert(result.msg);
    				}
    				$("#del_suggest_dialog").dialog("close");
    			}
    		});
        })
	 $(".select_suggest").click(function(){
		 var dom = $(this);
		 var iscollect = dom.attr("data-id");
		 isShowSuggest(iscollect);
		 var data = {"iscollect":iscollect,"questionnairesId":questionnairesId}
		 var url = "${ctx}jy/evl/question/setQuestionType";
		 ajax(url,data,null);
	 })
	 $(".form_close").click(function(){
		 $('#release_dialog').dialog("close");
		 window.location.href="${ctx}jy/evl/question/indexQuestions";
	 })
	  $(".save_form").click(function(){
		 var callback = savecallback;
		 saveFormData(callback);
	 })
	 $("#question_content").change(function(){
		 var callback;
		 saveFormData(callback);
	 })
	 $("#question_title").change(function(){
		 var callback;
		 saveFormData(callback);
	 })
	}
	 //复用问卷
	 $(".multiplexing").click(function(){
		//查询问卷列表
		var url = "${ctx}jy/evl/question/getOldQuestionList";
		var data = {"questionnairesId":questionnairesId};
		var back = showQuestions;
		ajax(url,data,back);
	 });
    $("#copy_question_sure").click(function(){
  	  var newId = Handler.copyId;
  		if(newId==""||newId==null){
// 		  alert("请选择一个问卷！");
		 $("#empty_question_dialog").dialog({
				 width:400,
				height:210
		 });
		  return false;
	  }
  	  copyIndicator(newId);
  	  $("#multiplexing_dialog").dialog("close");
    })
     function saveFormData(callback){
	 	var title = $("#question_title").val();
		var directions = $("#question_content").val();
		var data = {"title":title,"directions":directions,"questionnairesId":questionnairesId,status:${status_sjwj_2}}
		var url = "${ctx}jy/evl/question/saveQuestionIndicator";
	 	ajax(url,data,callback);
	 }
    function savecallback(data){
    	var content = $("#question_content").val();
    	if(!content){
    		$(".release_info strong").css("line-height","36px");
    		$('#not_release_dialog').dialog({
    			width: 421,
    			height: 220
    		});
    	}else if(data){
       	$('#release_dialog').dialog({
    			width: 421,
    			height: 220
    		});
    	}
       }
	//复制指标
	function copyIndicator(newId) {
		var oldId = questionnairesId;
		var url = "${ctx}jy/evl/indicator/setting/copyIndicator";
		var data = {
			"oldId" : oldId,
			"newId" : newId
		};
		$.ajax({
			url : url,
			type : 'post',
			data : data,
			dataType : "json",
			success : function(result) {
				initindicatorDom(questionnairesId);
				//预览元素和本页面冲突，刷新
				location.reload();
			},
			error : function(result) {
				alert("请求异常！" + url);
			}
		});
	}
	function click_copy_questionnaire(dom) {
		Handler.copyId = $(dom).attr("data-id");
	}
	function showQuestions(data) {
		if(data.length<1){
			 var  blankHtml='<div class="cont_empty" style="margin:1px auto;height: 100px">'+
				'<div class="cont_empty_img1"></div>'+
				'<div class="cont_empty_words">暂时没有可复用的问卷！</div>'+
			'</div>';
			$(".questionnaire_wrap").html(blankHtml);
			$("#copy_question_sure").hide();
		}else{
			$("#copy_question_sure").show();
		var htmlStr = "";
		for (var i = 0; i < data.length; i++) {
			htmlStr += '<div class="questionnaire">'
					+ '<input type="radio" data-id="'
					+ data[i].questionnairesId
					+ '"'
					+ 'class="copy_questionnaire" name="copy_questionnaire"  onclick="click_copy_questionnaire(this)"/> <span title="'+((data[i].title == null || data[i].title == "undefined") ? "无标题" : data[i].title)+'">'
					+ ((data[i].title == null || data[i].title == "undefined") ? "无标题" : data[i].title.length>16?(data[i].title.substring(0,15)):data[i].title) 
					+ '</span>' 
					+ '<a data-id="'
					+ data[i].questionnairesId
					+ '"'
					+' href="javascript:void(0);" onclick="view_indicator(this)">预览</a>'
					+ '</div>';
		}
		$(".questionnaire_wrap").html("").append(htmlStr);
		}
		$('#multiplexing_dialog').dialog({
			width : 565,
			height : 400
		});
	}
	//预览
	function view_indicator(dom){
		var data={"questionnairesId":$(dom).attr("data-id")}
		var url = "${ctx}/jy/evl/question/toViewAllIndicator";
		$.ajax({
			url : url,
			type : 'post',
			data : data,
			dataType : "html",
			success : function(htmlStr) {
				$("#view_indicator_dialog_content").html(htmlStr);
			},
		});
		$('#view_indicator_dialog').dialog({
			width: 985,
			height: 600
		});
	}
	function toSettingQuestion(dom){
		var status = ${question.status};
		var url = $(dom).attr("data-url");
		if(status>=${status_sjwj_1}){
			window.location.href=url;
		}
	}
	function isshow_addType_btn(){
		var _length = $("#suggestType_li li").length;
		if(_length>=6){
			$(".add_type").removeClass("add_lb").addClass("add_lb_disabled");
		}else{
			$(".add_type").removeClass("add_lb_disabled").addClass("add_lb");
		}
	}
	//添加类别btn
	function add_type(dom){
		if($("#suggestType_li li").length<6){
			$("#add_suggestType_title").val("");
			$('#add_category_dialog').dialog({
				width: 421,
				height: 210
			});
		}
	}
	//下载页面
	function downloadhtml(){
		try {
			window.location.href="${ctx}jy/evl/indicator/setting/downloadIndicatorExcel?questionnairesId="+questionnairesId;
  		} catch (e) {
		 alert(e.name + ":下载文件异常 " + e.message);
		}
	}
	function isShowSuggest(status){
		if(status==1){
		$(".d_q_opinion_txt_wrap").show();
		}else{
		$(".d_q_opinion_txt_wrap").hide();
		}
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
	$('.flow_chart_wrap2 .hide_table').click(function (){
    	if($('.flow_chart_wrap2 .table2_wrap').is(":hidden")){
    		$('.flow_chart_wrap2 .table2_wrap').show();
    		$('.flow_chart_wrap2 .table1_wrap').css({"width":"59.5%"}); 
    		$('.flow_chart_wrap2 .score_table2').css({"width":"398px"});
    		$('.flow_chart_wrap2 .hide_table').css({"background-position":"-298px -45px"});
    	}else{ 
    		$('.flow_chart_wrap2 .table2_wrap').hide();
    		$('.flow_chart_wrap2 .table1_wrap').css({"width":"100%"});
    		$('.flow_chart_wrap2 .score_table2').css({"width":"100%"});
    		$('.flow_chart_wrap2 .hide_table').css({"background-position":"-316px -45px"});
    	}
    });
	  $(".not_form_close").click(function(){
			 $('#not_release_dialog').dialog("close");
		 })
</script>
</html>