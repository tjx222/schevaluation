<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
window.history.forward(1);
</script>
<ui:htmlHeader title="教研平台"></ui:htmlHeader>
<c:if test="${not empty sessionScope._CURRENT_SPACE_ }">
<c:redirect url="${ctx }"></c:redirect>
</c:if>
<link rel="stylesheet" href="${ctxStatic }/modules/uc/login/css/login1.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
</head>
<body>
<div class="wrapper">
	<div class="jyyl_top">
		<div class="jyyl_top_logo"> 
			<c:choose>
				<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
					<a href="jy/index">
						<div class="jyyl_logo1"></div>
					</a>
				</c:when>
			    <c:otherwise>
					<a href="jy/index">
						<div class="jyyl_logo ${sessionScope._sess_flag_ != 'hidebuttom'?'jy':'jx' }"></div>
					</a>
				</c:otherwise> 
			</c:choose>
			<div class="logo_1"></div>
			<div class="jyyl_logo_right">
				<c:if test="${sessionScope._sess_flag_ != 'hidebuttom' }">
					<ul>
						<li class="jyyl_logo_right_li"><a href="${ctx}/jy/logout"><img src="${ctxStatic }/common/images/exit.png" alt="">退出</a></li>
					</ul>
				</c:if>
			</div>
		</div>
	</div>
	<div class="info_tab_wrap">
		<h3>为了平台能够更快的给您推送相关资源及同伴，请您完善以下信息吧：</h3>
		<div class="content_form">
			<form method="POST" action="jy/ws/uc/complementUserInfo" id="form1" name="form1">
				<input type="hidden" name="totype" value="${sessionScope._login_params_['totype'] }"/>
				<c:if test="${not empty sessionScope._CURRENT_USER_.orgId}">
				<p>
					<strong>*</strong>
					<label>区域：</label><span>${area.name }</span>
					</p>
					<p>
						<strong>*</strong>
						<label><c:if test="${org.type == 0 }">学校：</c:if>
								<c:if test="${org.type != 0 }">机构：</c:if>
						</label>
						<span>${org.name }</span><input type="hidden" name="orgId" value="${org.id }"/>
					</p>
					
					<div class="clear"></div>
					<div class="content_form_1" id="content_form">
						<p style="width:285px;">
							<strong>*</strong>
							<label>职务：</label>
							<select id="sel_post" class="chosen-select-deselect" style="width:130px;" data-errormessage-value-missing="请选择">
								<option value="">请选择</option>
								<c:forEach var="role" items="${roleList }">
									<c:if test="${27 == role.sysRoleId ||1378 == role.sysRoleId }">
										<option value="${role.sysRoleId }">${role.roleName }</option>
									</c:if>
								</c:forEach>
							</select>
						</p>
						<p>
								<strong>*</strong>
								<label>学段：</label>
								<select id="sel_xueduan" class="chosen-select-deselect" style="width:130px;" data-errormessage-value-missing="请选择" onchange="selxueduan()">
									<option value="">请选择</option>
									<c:forEach var="phase" items="${phaseList }">
										<option value="${phase.eid }" data="${phase.id }">${phase.name }</option>
									</c:forEach>
								</select>
							</p>
						<!--是学校 -->
						<c:if test="${org.type == 0 }">
							
							<p id="p_subject">
							<strong>*</strong>
							<label>学科：</label>
							<span id="span_subject">
								<select id="sel_subject" class="chosen-select-deselect" style="width:130px;" data-errormessage-value-missing="请选择">
							      <option value="">请选择</option>
								</select>
							</span>
							</p>
							<p id="p_grade">
								<strong>*</strong>
								<label>年级：</label>
								<span id="span_grade">
									<select id="sel_grade" class="chosen-select-deselect" style="width:130px;" data-errormessage-value-missing="请选择">
										<option value="">请选择</option>
									</select>
								</span>

							</p>
						</c:if>
						<!--不是学校 -->
						<c:if test="${org.type!=0 }">
							<p id="p_subject">
								<strong>*</strong>
								<label>学科：</label>
								<select id="sel_subject" class="chosen-select-deselect" style="width:130px;" data-errormessage-value-missing="请选择">
								      <option value="">请选择</option>
								      	<c:forEach var="subject" items="${subjectList}">
								      		<option sort="${subject.dicOrderby }" value="${subject.id }" class="subject_${subject.name }">${subject.name }</option>
								      	</c:forEach>
								</select>
							</p>
						</c:if>
						
						<div class="clear"></div>
						<input type="button" class="confirm" onclick="addToTable();" value="添加">
						<div class="border_x"></div>
						<div class="form_table">
							<h4>已添加：</h4>
							<table border="1" id="spaceTable">
							</table>
						</div>
					</div>
				</c:if>
			</form>
			<input type="button" class="come_into" onclick="submitForm();" value="进入平台">
		</div>
		<c:if test="${empty sessionScope._CURRENT_USER_ || sessionScope._CURRENT_USER_.appId == 0}">
		<div class="content_div">
			<div class="cont_k">
			    <h1>您没有可用的空间，或空间已被禁用，<br />请联系相关负责人处理。</h1>
			</div>
		</div>
		</c:if>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
</div>
</body>
<script type="text/javascript">
window.history.forward(1);
$(document).ready(function(){
	 //下拉框
	var config = {
	   '.chosen-select'           : {},
	   '.chosen-select-deselect'  : {allow_single_deselect: true},
	   '.chosen-select-deselect' : {disable_search:true}
	 };
	 for (var selector in config) {
	   $(selector).chosen(config[selector]);
	 }
	 $("#sel_post").change(function(){
		 $("#sel_grade").val("");
		 $("#sel_subject").val("");
		 $("#sel_xueduan").val("");
		 $("#sel_grade").trigger("chosen:updated");
		 $("#sel_xueduan").trigger("chosen:updated");
		 $("#sel_subject").trigger("chosen:updated");
		 $("#p_grade").show();
		 $("#p_subject").show();
			 var value=$(this).val();
			 if ( value == 1377 || value == 2000 || value == 1376 || value== 1380) {
				 $("#p_grade").hide();
				 $("#p_subject").hide();
			 }
			 if (value == 1375) {
				 $("#p_grade").hide();
			 }
			 if (value == 1374) {
				 $("#p_subject").hide();
			 }
	 });
})

 function selxueduan(obj){
		var phase = $("#sel_xueduan").find("option:selected").attr("data");
		var subjectMap = ${empty phaseSubjectMap?"{}" : phaseSubjectMap};
		if(!subjectMap[phase]){
			return;
		}
		$("#sel_subject").html("<option value=''>请选择</option>");
		$("#sel_subject").trigger("chosen:updated");
		$("#sel_grade").html("<option value=''>请选择</option>");
		$("#sel_grade").trigger("chosen:updated");
		//显示学科
		var subjectOptionHtml = '';
		var subjectList = subjectMap[phase];
		for(var i=0;i<subjectList.length;i++){
			var subject = subjectList[i];
			subjectOptionHtml = subjectOptionHtml+'<option sort="'+subject.dicOrderby+'" value="'+subject.id+'">'+subject.name+'</option>';
		}
		
		$("#sel_subject").append(subjectOptionHtml);
		$("#sel_subject").trigger("chosen:updated");
		//显示年级
		var gradeOptionHtml = '';
														
		var gradeMap = ${empty phaseGradeMap?"{}" : phaseGradeMap };
		var gradeList = gradeMap[phase];
		for(var i=0;i<gradeList.length;i++){
			var grade = gradeList[i];
			gradeOptionHtml = gradeOptionHtml+'<option value="'+grade.id+'">'+grade.name+'</option>';
		}
		$("#sel_grade").append(gradeOptionHtml);
		$("#sel_grade").trigger("chosen:updated");
}
var arrayIndex = 0;
//将已选择的数据添加到表格中
function addToTable(){
	var zhiwu = $("#sel_post").val();
	//校验职务
	if(zhiwu == ""){
		alert("请选择职务");
		return;
	}
	
	//校验学段
	if ($("#sel_xueduan").val()==""){
		alert("请选择学段");
		return;
	}
	
	if (zhiwu == 27 || zhiwu == 1375 || zhiwu == 1373) {
		//校验学科
		if ($("#sel_subject").val()==""){
			alert("请选择学科");
			return;
		}
	}
	
	if (zhiwu == 27 || zhiwu == 1374 || zhiwu == 1373) {
		if(${empty sessionScope._CURRENT_USER_.orgId} || ${org.type == 0 }){
			//校验年级
			if ($("#sel_grade").val()==""){
				alert("请选择年级");
				return;
			}
		}
	}
	
	var spaceName = $("#sel_post").find("option:selected").text();
	var spaceVal = "r_" + $("#sel_post").find("option:selected").val();
	spaceVal += $("#sel_xueduan").find("option:selected").val()
	if($("#sel_subject").val()!=0){
		spaceName = $("#sel_subject").find("option:selected").text()+spaceName;
		spaceVal += "_s_" + $("#sel_subject").find("option:selected").val();
	}
	if($("#sel_grade").val()!=0){
		spaceName = $("#sel_grade").find("option:selected").text()+spaceName;
		spaceVal += "_g_" + $("#sel_grade").find("option:selected").val();
	}
	
	var trArr = $("#spaceTable").find("tr");
	for (var i =0; i< trArr.length; i++) {
		var data = $(trArr[i]).attr("data-id");
		if (spaceVal == data) {
			alert("已经添加该身份，请勿重复添加！");
			return false;
		}
	}
	var htmlStr = "<tr data-id='"+spaceVal+"'>"+
	               "<td>"+$("#sel_post").find("option:selected").text()+"<input type='hidden' name='userSpaceList["+arrayIndex+"].sysRoleId' value='"+$("#sel_post").val()+"'/></td>"+
	               "<td>"+$("#sel_xueduan").find("option:selected").text()+"<input type='hidden' name='userSpaceList["+arrayIndex+"].phaseId' value='"+$("#sel_xueduan").val()+"'/></td>";
	if ($("#sel_subject").length >0 && !$("#p_subject").is(":hidden")) {
		
        htmlStr += "<td>"+$("#sel_subject").find("option:selected").text()+"<input type='hidden' name='userSpaceList["+arrayIndex+"].subjectId' value='"+$("#sel_subject").val()+"'/></td>";
	}
	if($("#sel_grade").length>0 && !$("#p_grade").is(":hidden")){
	
	htmlStr += "<td>"+$("#sel_grade").find("option:selected").text()+"<input type='hidden' name='userSpaceList["+arrayIndex+"].gradeId' value='"+$("#sel_grade").val()+"'/></td>";
	}
	htmlStr += "<td><span class='addrole_del' title='删除' onclick='deleteIt(this,"+$("#sel_post").val()+");'</span>";
	htmlStr += "<input type='hidden' name='userSpaceList["+arrayIndex+"].spaceName' value='"+spaceName+"'/></td>";
	
	if ($("#sel_subject").length >0 && !$("#p_subject").is(":hidden")) {
		htmlStr += "<input type='hidden' name='userSpaceList["+arrayIndex+"].subjectOrder' value='"+$("#sel_subject").find("option:selected").attr("sort")+"'/></td>";
	}
	
	htmlStr += "</tr>";
	$("#spaceTable").append(htmlStr);
	arrayIndex = arrayIndex+1;
	$("#sel_post").trigger("chosen:updated");
	
}

//删除行
function deleteIt(obj,sysRoleId){
	$(obj).parent().parent().remove();
	$("#sel_post").find("option:hidden").each(function(){
		if($(this).val()==sysRoleId){
			$(this)[0].style.display = "block";
		}
	});
	$("#sel_post").trigger("chosen:updated");
}
//提交表单
function submitForm(){
	if($("#sel_school").val()==""){
		alert("请选择学校");
		return;
	}
	if($("#spaceTable").find("tr").length<=0){
		alert("请添加详细信息");
		return;
	}
	if(window.confirm("您确定提交当前所添加的信息吗？")){
		$("#form1").submit();
	}
}

</script>
</html>