<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.tmser.schevaluation.evl.bo.EvlQuestionnaires"%>
<%@page import="com.tmser.schevaluation.evl.statics.EvlQuestionType"%>

<c:set var="pjlx_type_1" value="<%=EvlQuestionnaires.type_xueshengpingjiao%>"/>
<c:set var="pjlx_type_2" value="<%=EvlQuestionnaires.type_jiazhangpingjiao%>"/>

<c:set var="student_quanti" value="<%=EvlQuestionnaires.student_type_quantixusheng%>"/>
<c:set var="student_nianji" value="<%=EvlQuestionnaires.student_type_nianji%>"/>
<c:set var="student_banji" value="<%=EvlQuestionnaires.student_type_banji%>"/>

<c:set var="status_sjwj_1" value="<%=EvlQuestionType.xiangguanshezhi.getValue()%>"/>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="相关设置"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
</head>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<body>
	<!--头部-->
	<div class="jyyl_top">
	<ui:tchTop modelName="相关设置"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="sjwj_1"></jy:nav>
	</div>
	<!--内容 -->
	<div class="content" style="margin-bottom:0;">
	<!-- 提示信息 -->
	<div id="release_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="release_info" style="width: 170px;">
					<span class="save_img"></span> 
					<strong class="save_tip" style="width:120px;line-height:40px;">您确定要删除吗？</strong>
				</div>
				<input type="button" class="category_btn"  id="form_close" value="确定"  onclick="deleteWeightSure();"/>
			</div>
		</div>
	</div>
	<div class="flow_chart_wrap">
	<div class="related_settings">
		<ul class="ul">
			<li class="ul_li ul_one ul_act1"><a data-url="${ctx}jy/evl/question/settingQuestionRelation?questionnairesId=${question.questionnairesId}" href="javascript:void(0);"><span>1</span><strong>相关设置</strong></a></li>
			<li class="ul_li ul_two"><a data-url="${ctx}jy/evl/question/settingQuestionIndicator?questionnairesId=${question.questionnairesId}" onclick="toSettingQuestion(this)" href="javascript:void(0);"><span>2</span><strong>设计内容</strong></a></li>
		</ul>
		<div class="related_settings_list">
			<h3>评教类型</h3>
			<div class="pjlx">
				<c:if test="${empty operate_type || operate_type == '1'}">
					<div class="pjlx_list">
						<input type="radio" class="pingjiaoleixing" data-id=1 id="pingjiaoleixing1" name="pingjiaoleixing" <c:if test="${question.type==pjlx_type_1||operate_type == '1'}" >checked="checked"</c:if>><label for="pingjiaoleixing1" >学生评教</label>
					</div>
				</c:if>
				<c:if test="${empty operate_type || operate_type=='2'}">
					<div class="pjlx_list">
						<input type="radio" class="pingjiaoleixing" data-id=2 id="pingjiaoleixing2" name="pingjiaoleixing" <c:if test="${question.type==pjlx_type_2||operate_type == '2'}" >checked="checked"</c:if>><label for="pingjiaoleixing2">家长评教</label>
					</div>
				</c:if>
				
				<%--org
				<div class="pjlx_list">
					<input type="radio" name="pingjiaoleixing" <c:if test="${question.questionnairesId==pjlx_type_3}" >checked="checked"</c:if>><label>教师自评</label>
				</div>
				<div class="pjlx_list">
					<input type="radio" name="pingjiaoleixing" <c:if test="${question.questionnairesId==pjlx_type_4}" >checked="checked"</c:if>><label>民主互评</label>
				</div> --%>
			</div>
			<h3 id="evlperson_type"></h3>
			<div class="pjlx">
				<div class="pjlx_list">
					<input class="studentType" data-id=1 name="studentType" id="studentType_all" type="radio" <c:if test="${question.studentType==student_quanti}" >checked="checked"</c:if>><label for="studentType_all" id="evlperson_type1"></label>
				</div>
				<div class="clear"></div>
				<div class="pjlx_list0">
					<div class="pjlx_list1_wrap">
						<div class="pjlx_list1">
							<input class="studentType" data-id=2 name="studentType" id="studentType_nj" type="radio" <c:if test="${question.studentType==student_nianji}" >checked="checked"</c:if>><label for="studentType_nj">按年级选择</label>
						</div> 
					</div>
					<div class="pjlx_list2_wrap  div_student_nianji" style="display:none;">
						<c:forEach items="${njList }" var="nj">
							<div class="pjlx_list2">
								<c:set var="allIds" value=",${selectedNjIds},"></c:set>   
								<c:set var="id" value=",${nj.id},"></c:set>
								<input class="gradeType" id="gradeType_${nj.id}" data-id='${nj.id}' type="checkbox"<c:if test="${question.studentType==student_nianji&&fn:contains(allIds, id)}" >checked="checked"</c:if>><label for="gradeType_${nj.id}">${nj.name}</label>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="clear"></div>
				<div class="pjlx_list0">
					<div class="pjlx_list1_wrap">
						<div class="pjlx_list1">
							<input class="studentType" id="studentType_bj" data-id=3 name="studentType" type="radio" <c:if test="${question.studentType==student_banji}" >checked="checked"</c:if>><label for="studentType_bj">按班级选择</label>
						</div> 
					</div>
					<div class="pjlx_list2_wrap div_student_banji" style="display:none;">
						<span>查看/修改</span>
					</div>
				</div> 
				<div class="height clear"></div> 
			</div>
			<h3>评价哪些学科？<span>（确定学科即可确定所教班级的教师）</span></h3>
			<div class="pjlx">
				<div class="height clear"></div>
				<c:forEach items="${xkList}" var="xk">
				<c:if test="${fn:length(xk.name) gt 5 }">
					<div class="pjlx_list3">
						<c:set var="pjlx_allIds" value=",${question.subjectIds},"></c:set>   
						<c:set var="pjlx_id" value=",${xk.id},"></c:set>
						<input class="pingjiaxueke" id="pingjiaxueke_${xk.id}" <c:if test="${fn:contains(pjlx_allIds, pjlx_id)}" >checked="checked"</c:if> data-id='${xk.id}' type="checkbox">
						<label for="pingjiaxueke_${xk.id}">${fn:substring(xk.name, 0, 5)}...</label>
						<div class="hover_title" style="display: none;">
							<span></span>
							<strong>${xk.name}</strong>
						</div>
						</div>
				</c:if>
				<c:if test="${fn:length(xk.name) le 5 }">
				<div class="pjlx_list3">
					<c:set var="pjlx_allIds" value=",${question.subjectIds},"></c:set>
					<c:set var="pjlx_id" value=",${xk.id},"></c:set>
					<input class="pingjiaxueke" id="pingjiaxueke_${xk.id}" <c:if test="${fn:contains(pjlx_allIds, pjlx_id)}" >checked="checked"</c:if> data-id='${xk.id}' type="checkbox">
					<label for="pingjiaxueke_${xk.id}">${xk.name }</label>
				</div>
				</c:if>
				</c:forEach>
			</div>
			<h3>评教时段</h3>
			<div class="pjlx">
			
				<div class="pjlx_list">
					<input  data-year=2 id="operationType_all" class="operationType" name="operation_type" <c:if test="${question.operationType==2}" >checked="checked"</c:if> type="radio"><label for="operationType_all">全学年</label>
				</div>
				<div class="pjlx_list">
					<input  data-year=1 id="operationType_s" data-xueqi=1 class="operationType" name="operation_type" <c:if test="${question.operationType==1 && question.xueqi==1}" >checked="checked"</c:if> type="radio"><label for="operationType_s">上学期</label>
				</div>
				<div class="pjlx_list">
					<input data-year=1 id="operationType_x" data-xueqi=2 class="operationType" name="operation_type" <c:if test="${question.operationType==1 && question.xueqi==2}" >checked="checked"</c:if> type="radio"><label for="operationType_x">下学期</label>
				</div>
			</div>
			<h3>评教方式</h3>
			<div class="pjlx">
				<div class="pjlx_list4">
					<input class="indicatorType" id="indicatorType2" data-id=2 name="indicatorType" <c:if test="${question.indicatorType==2}" >checked="checked"</c:if> type="radio"><label for="indicatorType2" class="second_level">对各二级指标进行评价<span></span></label>
				</div>
				<div class="pjlx_list4">
					<input class="indicatorType" id="indicatorType1" data-id=1  name="indicatorType" <c:if test="${question.indicatorType==1}" >checked="checked"</c:if> type="radio"><label for="indicatorType1" class="one_level">对各一级指标进行评价<span></span></label>
				</div>
				<div class="pjlx_list4">
					<input class="indicatorType" id="indicatorType0" data-id=0  name="indicatorType" <c:if test="${question.indicatorType==0}" >checked="checked"</c:if> type="radio"><label for="indicatorType0" class="img_whole">整体评价<span></span></label>
				</div>
			</div>
			<h3>评价等级及权重<span>（为使区分度更加合理，建议评价等级不少于五级）</span></h3>
			<div class="pjlx">
			<div class="clear"></div>
				<div class="add_dj">
					<span>+</span>
					<strong>添加</strong>
				</div>
				<div class="notes">
					<span></span>
					<strong>注：请逐一添加各等级</strong>
				</div>
				<div class="clear"></div>
				<form id="weight_form_id">
				<div class="gradg_wrap">
					<table cellpadding="0" cellspacing="0" class="gradg_table">
						<tr>
							<th style="width:10%">序号</th>
							<th style="width:40%">评价等级</th>
							<th style="width:30%">权重</th>
							<th style="width:20%">操作</th>
						</tr>
						<c:forEach items="${weightList }" var="weight" varStatus="c">
						<tr <c:if test="${c.count%2==0}"> style="background: rgb(235, 243, 249);" </c:if>>
							<td>${weight.index}</td>
							<td>${weight.levelName }</td>
							<td>${weight.levelWeightInt}%</td>
							<td  data-id="${weight.id}">
							<span class="save_btn1" onclick="saveweight1(this);"></span>
							<span class="close_btn1" onclick="deleteweight(this);"></span>
							</td>
						</tr>
						</c:forEach>
					</table>
					<div class="add_dj1">
						<span>+</span>
						<strong>添加</strong>
					</div>
				</div>
				</form>
			</div>
			<h3>评价时限<span>（仅为起止时间的提醒，不会自动终止评价）</span></h3>
			<div class="pjlx">
				<div class="height clear"></div>
				<input  id="beginTime"  autocomplete="off" type="text" name="beginTime" class="validate[custom[dateFormat]] dateTimeInput start_time" value="${time.beginTimeStr }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'now'})" />
				<label for="" class="zhi">至</label>
				<input id="endTime"  autocomplete="off" type="text" name="endTime" class="validate[custom[dateFormat]] dateTimeInput end_time" value="${time.endTimeStr }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'now'})" />
			</div>
			<input onclick="save();" type="button" class="save" value="保 存"></input>
		</div>
		<div class="clear"></div>
	</div>
</div>
<!-- 查看/修改 -->
<div id="pjlx_list2_wrap_dialog" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">查看/修改</span>
			<span class="dialog_close"></span>
		</div>
			<div class="dialog_content">
			<div class="Invitation_bottom">
				<div class="centent" id="class_content"> 
					<!-- 班级内容 -->
				</div>
			</div>
		</div> 
	</div>
</div>
<div class="clear"></div>
	</div>
	<div class="clear"></div> 
<!--页脚-->
<ui:htmlFooter></ui:htmlFooter>
</body>
<script type="text/javascript">
var questionnairesId = '${question.questionnairesId}';
var del_dom;
var tag = '${tag}';
	window.onbeforeunload =function(){
		if(tag==1){
			var url =  "${ctx}/jy/evl/question/delTagQuestion";
			var data = {
					"questionnairesId":questionnairesId,
					"userId":'${user.id}'
			};
			$.ajax({
				url : url,
				type : 'post',
				data : data,
				async:false,
				dataType : "json"
			});
			}
		}
$(function(){
	//加载等级权重
	if($(".gradg_table tr").length>1){//有等级数据
	 	$('.gradg_wrap').show(); 
       	$(this).parent().css({"margin-top":"0","margin-left":"0"})
       	$(".add_dj").hide();
       	$(".notes").hide();
	}else{
		$('.gradg_wrap').hide();
	}
	//评教人员筛选类型
	var type = $(".studentType:checked").attr("data-id");
	if(type==2){
		$(".div_student_nianji").show();
		$(".div_student_banji").hide();
	}else if(type==3){
		$(".div_student_nianji").hide();
		$(".div_student_banji").show();
	}else{
		$(".div_student_nianji").hide();
		$(".div_student_banji").hide();
	}
	//评教类型
	
	var evlperson_type =  $(".pingjiaoleixing:checked").attr("data-id");
	if(evlperson_type==1){//student
		$("#evlperson_type").html("哪些学生评？");
		$("#evlperson_type1").html("全体学生");
	}else if(evlperson_type==2){//parent
		$("#evlperson_type").html("哪些家长评？");
		$("#evlperson_type1").html("全体家长");
	}
	checkNowTime();
});
function save(){
	$(".pingjiaoleixing:checked").trigger("click");
	tag=2;
	//change状态
	var change_questionData = {
			"status":1,
			"questionnairesId":questionnairesId
	};
	var change_question_url = "${ctx}/jy/evl/question/changeQuestionStatus_settingOne";
	$.ajax({
		url : change_question_url,
		async:false,
		type : 'post',
		data : change_questionData,
		dataType : "json"
	});
	//保存时间
	if(checkTime()){
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		url="${ctx}/jy/evl/question/setQuestionTimeLine";
		var data = {
				"questionnairesId":questionnairesId,
				"beginTimeStr":beginTime,
				"endTimeStr":endTime,
				"type":1
		};
		
		var back = redirect_setting_two;
		ajax(url,data,back)
	}
}
//设置权重
  $('.add_dj').click(function (){
       	$('.gradg_wrap').show(); 
       	$(this).parent().css({"margin-top":"0","margin-left":"0"})
       	$(".add_dj").hide();
       	$(".notes").hide();
       	loadLevelWeight();
    });
$(".add_dj1").click(function(){
	loadLevelWeight('add');
});
function loadLevelWeight(add){
	//查询最大的序号
	var weightIndex = getWeightIndex();
	var isBackground = $(".gradg_table").find("tr").last().attr("style");
	var trStyle = 'style="background: rgb(235, 243, 249);"';
	var tr = "";
	if(isBackground){
		tr  ='<tr>';
	}else{
		tr = '<tr style="background: rgb(235, 243, 249);">'
	}
	var htmlStr =
	tr
	+'<td>'+weightIndex+'</td>'
	+'<td><input maxlength="6" type="text" class="evaluation_level"></td>'
	+'<td><input type="text" id="validate'+weightIndex+'" class="weight validate[required,custom[integer],min[0],max[100]]">%</td>'
	+'<td>'
	+'<span class="save_btn" onclick="saveweight(this);"></span>';
	if(add){
		tr+='<span class="close_btn" onclick="deleteweight(this);"></span>'
	}
	tr+='</td>'
	+'</tr>';
	$(".gradg_table").find("tbody").append(htmlStr);
}
function getWeightIndex(){
	return $(".gradg_table tr").length;
}
function updateIndex(){
	var index = $(".gradg_table tr").length;
	for(var i=1;i<index;i++){
		$(".gradg_table tr").eq(i).find("td").first().text(i);
	}
}
/*新增权重*/
function saveweight(dom){
	if(!checkForm(dom)){
		return false;
	}
	var levelName = $(dom).parent().parent().find("input:first").val();
	var levelWeight = $(dom).parent().parent().find("input:last").val();
	//保存操作
	var url = "${ctx}jy/evl/question/setQuestionLevelWeight/addOrUpdate";
	var id="";
	if($(dom).parent().attr("data-id")){
		//修改
		id=$(dom).parent().attr("data-id");
	}
	var data={
			"id":id,
			"questionnairesId":questionnairesId,
			"levelWeight":levelWeight,
			"levelName":levelName
	};
	$.ajax({
		url : url,
		type : 'post',
		data : data,
		dataType : "json",
		success : function(result) {
			if(result.code==-1){
				alert("保存失败！");
				return;
			}
			$(dom).parent().parent().find("input").hide();
			$(dom).parent().parent().find("td").eq(1).html(data.levelName);
			$(dom).parent().parent().find("td").eq(2).html(data.levelWeight+"%");
			$(dom).parent().parent().find("td").last().attr("data-id",result.code);
			$(dom).parent().parent().attr("data-id",result.code);
			var tempDom=
				'<span  class="save_btn1" onclick="saveweight1(this);"></span>'
				+'<span class="close_btn1" onclick="deleteweight(this);"></span>';
			$(dom).parent().html(tempDom);
		}
	});
}
/*修改权重*/
function saveweight1(dom){
	var index = $(dom).parent().parent().find("td").eq(0).text();
	var levelName = $(dom).parent().parent().find("td").eq(1).text();
	var levelWeight = $(dom).parent().parent().find("td").eq(2).text();
	if(levelWeight.indexOf("%")!=-1){
		levelWeight = levelWeight.substring(0,levelWeight.length-1);
	}	
	var nameinput = '<input maxlength="6" type="text" value="'+levelName+'" class="evaluation_level">';
	var weightinput = '<input id="validate'+index+'" type="text" value="'+levelWeight+'" class="weight validate[required,custom[integer],min[0],max[100]]">%';
	$(dom).parent().parent().find("td").eq(1).html(nameinput);
	$(dom).parent().parent().find("td").eq(2).html(weightinput);
	if(!checkForm(dom)){
		return false;
	}		
	var htmlStr = 
	'<span class="save_btn" onclick="saveweight(this);"></span>';
	$(dom).parent().html(htmlStr);
}
function save_back(dom){
	$(dom).removeClass("close_btn").addClass("close_btn1");
	$(dom).prev().removeClass("save_btn").addClass("save_btn1");
	var tempName = $(dom).parent().parent().find("input").first().val();
	var tempWeight = $(dom).parent().parent().find("input").last().val();
	$(dom).parent().parent().find("input").remove();
	$(dom).parent().parent().find("td").eq(1).html(tempName);
	$(dom).parent().parent().find("td").eq(2).html(tempWeight+"%");
	$(dom).attr("onclick","deleteweight(this)");
	$(dom).prev().attr("onclick","saveweight1(this)");
// 	location.reload();
}
/*删除权重*/
function deleteweight(dom){
	del_dom = dom;
	$("#release_dialog").dialog({
		width: 421,
		height: 220
	});
}
function deleteWeightSure(){
	var dom = del_dom;
	var url = "${ctx}jy/evl/question/setQuestionLevelWeight/delete";
	var id = $(dom).parent().attr("data-id");
	var data = {"id":id};
	if(id){
		ajax(url,data,null);
	}
	$(dom).parent().parent().remove();
	updateIndex();
	$("#release_dialog").dialog("close");
}
/*评教类型*/
 $(".pingjiaoleixing").click(function(){
	 var dom = $(this);
	 var type = dom.attr("data-id");
	 if(type==1){//student
			$("#evlperson_type").html("哪些学生评？");
			$("#evlperson_type1").html("全体学生");
		}else if(type==2){//parent
			$("#evlperson_type").html("哪些家长评？");
			$("#evlperson_type1").html("全体家长");
		}
	 var data = {"type":type,"questionnairesId":questionnairesId}
	 var url = "${ctx}jy/evl/question/setQuestionType";
	 $.ajax({
			url : url,
			async:false,
			type : 'post',
			data : data,
			dataType : "json"
		});
 })
/*评教学生*/
 $(".studentType").click(function(){
	 var dom = $(this);
	 var studentType = dom.attr("data-id");
	 isShowStudentType(studentType);
	 var data = {"studentType":studentType,"questionnairesId":questionnairesId}
	 var url = "${ctx}jy/evl/question/setQuestionType";
	 ajax(url,data,null);
 })
 
/*评教学生：选择年级*/
 $(".gradeType").click(function(){
	 var dom = $(this);
	 var ischecked = dom.prop("checked");
	 var gradeId = dom.attr("data-id");
	 var data = {"gradeId":gradeId,"questionnairesId":questionnairesId,"ischecked":ischecked}
	 var url = "${ctx}jy/evl/question/setQuestionGradeIds";
	 ajax(url,data,null);
 })
/*评教学科*/
 $(".pingjiaxueke").click(function(){
	 var dom = $(this);
	 var ischecked = dom.prop("checked");
	 var url = "${ctx}jy/evl/question/setQuestionSubject";
	 var subjectId = dom.attr("data-id");
	 var data = {"subjectId":subjectId,"questionnairesId":questionnairesId,"ischecked":ischecked}
	 ajax(url,data,null);
 })
/*评教学科*/
 $(".operationType").click(function(){
	 var dom = $(this);
	 var operationType = dom.attr("data-year");
	 var xueqi = dom.attr("data-xueqi");
	 var data = {"xueqi":xueqi,"operationType":operationType,"questionnairesId":questionnairesId}
	 var url = "${ctx}jy/evl/question/setQuestionOperator";
	 ajax(url,data,null);
 })
/*评教指标层级*/
 $(".indicatorType").click(function(){
	 var dom = $(this);
	 var indicatorType = dom.attr("data-id");
	 var data = {"indicatorType":indicatorType,"questionnairesId":questionnairesId}
	 var url = "${ctx}jy/evl/question/setIndicatorType";
	 ajax(url,data,null);
 })
/**
 * 班级选择
 */
$(".pjlx_list2_wrap span").click(function(){
	$('#class_content').html("");
	$.ajax({  
		url: _WEB_ROOT_+'jy/evl/question/loadClassContent',
		type:'post',
		data:{"questionnairesId":questionnairesId},
		cache:false,
		dataType:'html',
		success:function(data){
			$('#class_content').html(data);
		}, 
		error : function() {    
			alert("请求异常:"+this.url);   
		}   
	});
	$('#pjlx_list2_wrap_dialog').dialog({width:563,height: 495});
});
function checkTime(){
	    var startTime=$("#beginTime").val();
	    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
	    var endTime=$("#endTime").val();  
	    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
	    if(end<start){  
	    	alert("开始时间大于结束时间，请检查！")
	        return false;  
	    }  
	    return true;  
}
function checkNowTime(){
    var startTime='${time.flago}';
    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
    var endTime=$("#beginTime").val();  
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
    if(end<start){  
    	$("#beginTime").attr("disabled","disabled");
    }  
}
function redirect_setting_two(){
	var status = '${question.status}';
	window.location.href=_WEB_CONTEXT_+"/jy/evl/question/settingQuestionIndicator?questionnairesId="+questionnairesId+"";
}
function toSettingQuestion(dom){
	save();
	var url = $(dom).attr("data-url");
	window.location.href=url;
}
function isShowStudentType(type){
	if(type==2){
		$(".div_student_nianji").show();
		$(".div_student_banji").hide();
	}else if(type==3){
		$(".div_student_nianji").hide();
		$(".div_student_banji").show();
	}else{
		$(".div_student_nianji").hide();
		$(".div_student_banji").hide();
	}
}
function checkForm(dom){
	var index = $(dom).parent().parent().find("td").first().html();
	$(".weight").not($("#validate"+index)).each(function(){
		$(this).removeClass("validate[required,custom[integer],min[0],max[100]]");
	});
	var flag = $("#weight_form_id").validationEngine("validate")
	$(".weight").not($("#validate"+index)).each(function(){
		$(this).addClass("validate[required,custom[integer],min[0],max[100]]");
	});
	return flag;
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
		}
	});
}
$(".pjlx_list3").hover(
		function(){
			$(this).find(".hover_title").show();
		},
		function(){
			$(this).find(".hover_title").hide();
		}
	)
</script>
</html>