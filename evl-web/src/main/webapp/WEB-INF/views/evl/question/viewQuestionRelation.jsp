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
</head>
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
	<div class="flow_chart_wrap">
	<div class="related_settings">
		<ul class="ul">
			<li class="ul_li ul_one ul_act1"><a data-url="${ctx}jy/evl/question/viewQuestionRelation?questionnairesId=${question.questionnairesId}" onclick="toSettingQuestion(this)" href="javascript:void(0);"><span>1</span><strong>相关设置</strong></a></li>
			<li class="ul_li ul_two"><a data-url="${ctx}jy/evl/question/viewQuestionIndicator?questionnairesId=${question.questionnairesId}" onclick="toSettingQuestion(this)" href="javascript:void(0);"><span>2</span><strong>设计内容</strong></a></li>
		</ul>
		<div class="related_settings_list">
			<h3>评教类型</h3>
			<div class="pjlx">
				<div class="pjlx_list">
				<c:if test="${question.type==pjlx_type_1}" >学生评教</c:if>
				<c:if test="${question.type==pjlx_type_2}" >家长评教</c:if>
				</div>
			</div>
			
			
			<h3 id="evlperson_type"></h3>
			<div class="pjlx">
				
				<div class="pjlx_list" style="width: 900px;">
					<c:if test="${question.studentType==student_quanti}" >全体学生</c:if>
					<c:if test="${question.studentType==student_nianji||question.studentType==student_banji}" >
						${studentTypeNames}
					</c:if>
				</div>
			</div>
			<h3>评价哪些学科？<span>（确定学科即可确定所教班级的教师）</span></h3>
			<div class="pjlx">
				<div class="height clear"></div>
					<div class="pjlx_list3"  style="width: 900px;">
						${subjectNames}
					</div>
			</div>
			<h3>评教时段</h3>
			<div class="pjlx">
				<div class="pjlx_list">
				<c:if test="${question.operationType==2}" ><label>全学年</label></c:if>
				<c:if test="${question.operationType==1 && question.xueqi==1}" ><label>上学期</label></c:if>
				<c:if test="${question.operationType==1 && question.xueqi==2}" ><label>下学期</label></c:if>
				</div>
			</div>
			<h3>评教方式</h3>
			<div class="pjlx">
				<div class="pjlx_list4">
					
					<c:if test="${question.indicatorType==2}" ><label class="second_level">对各二级指标进行评价<span></span></label></c:if>
					<c:if test="${question.indicatorType==1}" ><label class="second_level">对各一级指标进行评价<span></span></label></c:if>
					<c:if test="${question.indicatorType==0}" ><label class="second_level">整体评价<span></span></label></c:if>
				</div>
			</div>
			<h3>评价等级及权重<span>（为使区分度更加合理，建议评价等级不少于五级）</span></h3>
			<div class="pjlx" style="margin-top:25px;">
				<div class="gradg_wrap" style="width:900px;">
					<table cellpadding="0" cellspacing="0" class="gradg_table">
						<tr>
							<th style="width:33%">序号</th>
							<th style="width:33%">评价等级</th>
							<th style="width:33%">权重</th> 
						</tr>
						<c:forEach items="${weightList }" var="weight" varStatus="c">
						<tr <c:if test="${c.count%2==0}">style="background: rgb(235, 243, 249);" </c:if>>
							<td>${weight.index}</td>
							<td>${weight.levelName }</td>
							<td>${weight.levelWeightInt}%</td> 
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<h3>评价时限<span>（仅为起止时间的提醒，不会自动终止评价）</span></h3>
			<div class="pjlx">
				<div class="height clear"></div>
					<c:if test="${time.beginTimeStr==null&&time.endTimeStr!=null}">
					结束时间：${time.endTimeStr }
					</c:if>
					<c:if test="${time.beginTimeStr!=null&&time.endTimeStr==null}">
					开始时间：${time.beginTimeStr }
					</c:if>
					<c:if test="${time.beginTimeStr!=null&&time.endTimeStr!=null}">
					${time.beginTimeStr} 至 ${time.endTimeStr }
					</c:if>
			</div>
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
$(function(){
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
	var evlperson_type =  '${question.type}';
	if(evlperson_type==1){//student
		$("#evlperson_type").html("哪些学生评？");
		$("#evlperson_type1").html("全体学生");
	}else if(evlperson_type==2){//parent
		$("#evlperson_type").html("哪些家长评？");
		$("#evlperson_type1").html("全体家长");
	}
	$("input").attr("disabled","disabled");
});
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
//时间保存
function checkTime(){
	if((beginTime!=null && beginTime!="") && (endTime!=null && endTime!="")){
		if(endTime<beginTime){
			alert("开始时间大于结束时间，请检查！");
			return false;
		}
	}
	return true;
}
function toSettingQuestion(dom){
	var status = ${question.status};
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
</script>
</html>