<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="学生管理"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<!--自定义css文件引用  -->
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css"	media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<style> 
		.chosen-container-single .chosen-single{
			border:none;
		}
		.chosen-container.chosen-with-drop .chosen-drop{
			width: 98%;
		}
		.chosen-container-single .chosen-single div {
			line-height:0;
		}
	</style>
</head>
<body>
	<!--头部-->
	<div class="jyyl_top">
	<ui:tchTop modelName="查阅"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="evl_manage">
		</jy:nav>
	</div>
	<!-- 提示信息 -->
	<div id="err_dialog"  class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close" onclick="location.reload();"></span>
			</div>
			<div   class="dialog_content" style="height:400px; overflow-y: auto;overflow-x: hidden;">
				<div class="release_info" >
					<span class=""></span> 
					<strong style="line-height: 42px;width:480px; margin: 9px 0 1px 0px;" class="err_tip"></strong>
				</div>
			</div>
		</div>
	</div>
	<!-- 批量升班 -->
	<div id="batch_dialog"  class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">批量升班</span> <span class="dialog_close" ></span>
			</div>
			<div class="dialog_content" style="overflow:hidden;">
				<div class="batch_info_wrap">
					<div class="batch_info_top" >
						<table width="100%" style="margin-left:20px;">
							<tr>
							<c:forEach items="${gradeVos}" var="nj" varStatus="status">
								<td>${nj.name}</td>
							</c:forEach>
							</tr>
						</table>	
					</div>
					<div class="batch_info_bottom"> 
						<table style="margin-left:20px;overflow:hidden;">
							<tr>
								<c:forEach items="${gradeVos}" var="gradeVo" varStatus="gradeStatus">
									<td style="vertical-align: top;">
										<table>
											<c:forEach items="${gradeVo.classes}" var="classVo" varStatus="classStatus">
												<tr style="width:116px;display:inline-block;">
													<td style="height:48px" class="class">${classVo.name}</td>
													<c:if test="${! empty gradeVos[gradeStatus.index+1]}">
														<c:if test="${fn:length(gradeVos[gradeStatus.index+1].classes)==fn:length(gradeVos[gradeStatus.index].classes)}">
															<td class="promoted"></td>
														</c:if>
														<c:if test="${fn:length(gradeVos[gradeStatus.index+1].classes)!=fn:length(gradeVos[gradeStatus.index].classes)}">
															<td style="width:50px;"></td>
														</c:if>
													</c:if>
												</tr>
											</c:forEach>
										</table>
									</td> 
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>
				<p class="notes2">班级信息对应的年级可升班，不对应的，可调整后重新导入学生。</p>
				<div class="batch_info_button" style="height:43px;">
					<div class="batch_promoted_button">
						<input type="button" value="确定升班"  class="confirm_promoted" onclick="batchUpdateStudents()"/>
						<input type="button" value="不升班" class="not_promoted" onclick="$('#batch_dialog').dialog('close')"/> 
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 升级失败的年级 -->
	<div id="failGrades_dialog"  class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">升级失败的年级</span> <span class="dialog_close" ></span>
			</div>
			<div class="dialog_content" >
				<div class="batch_info_wrap">
					<div class="batch_info_top" >
					</div>
					<div class="batch_info_bottom" style="width:400px;"> 
						<c:forEach items="${failGradeVos}" var="failGradeVo">
							<span  class="batch_info_bottom_span">${failGradeVo.name}<a href="${ctx}jy/evl/manage/exportStudentAccounts?orgId=${orgId}&schoolYear=${schoolYear.schoolYear-1}&gradeId=${failGradeVo.id}">导出学生</a></span>
						</c:forEach>
					</div>
				</div>
				<p class="notes2">注：请重新分班后，点击"批量导入"按钮重新导入</p>
				<div class="batch_info_button" style="width:400px;">
					<div class="batch_promoted_button" style="margin:13px auto;width:80px;" >
						<input type="button" value="关闭" class="not_promoted" onclick="$('#failGrades_dialog').dialog('close')"/> 
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="noFailGrades_dialog"  class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示</span> <span class="dialog_close" ></span>
			</div>
			<div class="dialog_content" >
				<div class="batch_info_wrap">
					<div style="width:400px;height:80px;margin-top:20px;"> 
						<center>您已在<fmt:formatDate value="${evlSchoolYear.crtDttm}" pattern="yyyy-MM-dd HH:mm"/> 成功升班啦！</center>
					</div>
				</div>
				<div style="width:400px;">
					<div class="batch_promoted_button" style="width:80px;" >
						<input type="button" value="关闭" class="not_promoted" onclick="$('#noFailGrades_dialog').dialog('close')"/> 
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--内容-->
	<div class="flow_chart_wrap" style="padding-bottom:0;">
	<div class="flow_chart_wrap_left">
		<ul>
			<li>
				<a href="${ctx}jy/evl/index" >流程图</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/question/indexQuestions">设计问卷</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/result/resultIndex">结果统计</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/analyze/analyzeIndex">分析报告</a>
			</li>
			<li>
				<a href="${ctx}jy/evl/manage/students" class="f_c_w_l_act">学生管理</a>
			</li>
		</ul>
	</div>
	<div class="student_management_right"> 
		<ul class="cont_ul_tab">
			<c:forEach items="${gradeVos}" var="nj" varStatus="status">
				<li class="an_re_act <c:if test="${status.index==0}" >an_re_act1</c:if>" onclick="loadGrade('${nj.id}','${nj.name}')">${nj.name} </li>
			</c:forEach>
		</ul>
		<div class="batch_btn" style="width:auto;">
      <input type="button" onclick="showImportGradeStudent()" value="批量导入" style="width:100px;" />
      <input type="button" onclick="showBatchUpdateStudents()" value="批量升班"  style="margin-left:5px;"/>
    </div>
		<div class="student_management_tab_wrap">
			<c:forEach items="${gradeVos}" var="gradeVo" varStatus="gradeStatus">
				<div class="student_management_tab"  <c:if test="${gradeStatus.index>0}">style="display:none;"</c:if>>
					<div class="each_class_wrap">
							<c:forEach items="${gradeVo.classes}" var="classVo">
								<div class="each_class">
									<input type="radio" name="classId" id="classId_${classVo.id}" value="${classVo.id}" class="each_class_input" onchange="loadStudentsList(this)" />
									<label for="classId_${classVo.id}" class="each_class_label">${classVo.name}</label>
								</div>
							</c:forEach>
					</div>
					<div class="student_man_table_wrap">
						<iframe id="iframe_${gradeVo.id}" name="iframe_${gradeVo.id}"  src="" height="100%" width="100%"  scrolling="no"  frameborder="0">
						</iframe>
					</div>
				</div>
			</c:forEach>
		</div> 
	</div>
	</div>
 		<!-- 删除 -->
	<div id="delete_dialog" class="dialog">
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">删除学生账号</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span>
					<strong style="line-height:33px;">您确定要删除已选中的学生吗？</strong>
				</div>
				<input type="button" onclick="delStudentSure();"   class="confirm" value="确 定">
			</div>
		</div> 
	</div>
	<!-- 批量导入 -->
	<div id="batch_import_student_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">批量导入*年级*班学生</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="batch_import_student_info">
					<span></span>
					<strong>请下载<a href="javascript:void(0);" onclick="download_templete()"><b>批量导入学生模板.xlsx</b></a>填写后再上传！</strong>
				</div>  
				<div class="dialog_content_bottom" id="class_upload_dialog_content_bottom">
					<span>*</span>
					<label for="">导入学生：</label>
					<ui:upload containerID="class_upload_dialog_content_bottom" 
									relativePath="evltFile/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" fileType="xls,xlsx"
									startElementId="class_upload_btn" fileSize="1" originFileName="classStudents"
									name="class_upload" callback="backUpload"></ui:upload>
				</div> 
				<input type="button" id="class_upload_btn" class="confirm" value="确 定">
			</div>
		</div> 
	</div>
	<!-- 批量导入学生 -->
	<div id="batch_import_grade_student_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">批量导入*年级学生</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="batch_import_student_info">
					<span></span>
					<strong>请下载<a href="javascript:void(0);" onclick="download_grade_templete()"><b>批量导入学生模板.xlsx</b></a>填写后再上传！</strong>
				</div>  
				<div class="dialog_content_bottom" id="upload_dialog_content_bottom">
					<span>*</span>
					<label for="">导入学生：</label>
					<ui:upload containerID="upload_dialog_content_bottom" 
									relativePath="evltFile/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" fileType="xls,xlsx"
									startElementId="upload_btn" fileSize="1" originFileName="gradeStudents"
									name="upload" callback="backGradeUpload"></ui:upload>
				</div> 
				<input type="button" id="upload_btn" class="confirm" value="确 定">
			</div>
		</div> 
	</div>
	
	<!-- 添加学生 -->
	<div id="add_student_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">添加*年级*班学生</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<form id="addStudentForm" action="">  
					<div class="dialog_content_bottom2"> 
						<label for=""><span>*</span>学号：</label>
						<input name="code" type="text" maxlength="20" class="student_txt validate[required]" />
					</div> 
					<div class="dialog_content_bottom1"> 
						<label for=""><span>*</span>姓名：</label>
						<input name="name" type="text" maxlength="10" class="student_txt validate[required]" />
					</div> 
					<div class="dialog_content_bottom1"> 
						<label>性别：</label>
						<input type="radio" name="sex" value="1" class="student_radio" checked />男 
						<input type="radio" name="sex" value="0" class="student_radio"  />女
					</div> 
					<div class="dialog_content_bottom1"> 
						<label for=""><span>*</span>家长电话：</label>
						<input name="cellphone" type="text" class="student_txt validate[required,custom[phone]] " />
					</div> 
					<input type="button" class="confirm" onclick="submitStudentInfo('addStudentForm')" value="保 存">
				</form>
			</div>
		</div> 
	</div>
	<!-- 编辑学生 -->
	<div id="edit_student_dialog" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">编辑学生信息</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<form id="editStudentForm" action="">  
					<input name="editId" type="hidden" />
					<div class="dialog_content_bottom2"> 
						<label for=""><span>*</span>学号：</label>
						<input name="code" type="text" maxlength="20" class="student_txt validate[required]" />
					</div> 
					<div class="dialog_content_bottom1"> 
						<label for=""><span>*</span>姓名：</label>
						<input name="name" type="text" maxlength="10" class="student_txt validate[required]" />
					</div> 
					<div class="dialog_content_bottom1"> 
						<label for="">性别：</label>
						<input type="radio" name="sex" value="1" class="student_radio"  />男 
						<input type="radio" name="sex" value="0" class="student_radio"  />女
					</div> 
					<div class="dialog_content_bottom1"> 
						<label for=""><span>*</span>家长电话：</label>
						<input name="cellphone" type="text" class="student_txt validate[required,custom[phone]]"  />
					</div> 
					<input type="button" class="confirm" onclick="submitStudentInfo('editStudentForm')" value="保 存">
				</form>
			</div>
		</div> 
	</div>
	<ui:htmlFooter></ui:htmlFooter>
	<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<script type="text/javascript">
		//全局变量定义
		var gradeId; //年级ID
		var gradeName;//年级名称
		var classId;//班级ID
		var className;//班级名称
		
		var currentId;//数据列对应ID
		$(document).ready(function(){
			 //加载第一个年级和班级的学生数据
			 <c:if test="${not empty gradeVos}">
				gradeId="${gradeVos[0].id}";
				gradeName="${gradeVos[0].name}";
				 <c:if test="${not empty gradeVos[0].classes}">
					classId="${gradeVos[0].classes[0].id}";
					className="${gradeVos[0].classes[0].name}";
					$("#classId_"+classId).attr("checked","checked");
				</c:if>
			 </c:if>
			 if(gradeId&&classId){
				 loadStudentsFrame(gradeId,classId);
			 }else{
				//没有班级:只加载第一个iframe
				 var  blankHtml='<div class="cont_empty">'+
					'<div class="cont_empty_img1"></div>'+
					'<div class="cont_empty_words">没有数据！</div>'+
				'</div>';
				$(".student_management_tab").first().html(blankHtml);
			 }
		});
		$(".student_management_right ul li").click(function (){ 
	    	$(this).addClass("an_re_act1").siblings().removeClass("an_re_act1");
	    	$('.student_management_tab_wrap .student_management_tab').hide().eq($(".student_management_right ul li").index(this)).show();
	    	try{
	    		var targetObj=$('.student_management_tab_wrap .student_management_tab').eq($(".student_management_right ul li").index(this));
		    	$(targetObj.find(".each_class_input")[0]).trigger("click");
	    		if(targetObj.find(".each_class_input").length==0&&targetObj.find("select").length==0){
	    			 var  blankHtml='<div class="cont_empty">'+
						'<div class="cont_empty_img1"></div>'+
						'<div class="cont_empty_words">还没有创建班级</div>'+
					'</div>';
	    			targetObj.html(blankHtml);
	    		}
	    	}catch(e){
				//异常处理
				alert(e);
	    	}
	    	
			 //chosen样式
			 if($(this).attr("data-key") == "history"){
			   	 $(".particular_year").chosen({disable_search : true});
				 $(".classes").chosen({disable_search : true}); 
			 }
	    });
		
		function loadStudentsFrame(gradeId,classId){
			 var url="${ctx}/jy/evl/manage/studentsPageList?gradeId="+gradeId+"&classId="+classId+"&schoolYear=${schoolYear==null?sessionScope._CURRENT_SCHOOLYEAR_:schoolYear.schoolYear}";
			 var iframeObj = document.getElementById("iframe_"+gradeId); 
			 iframeObj.src=url;
		}
		//加载班级数据
		function loadStudentsList(dom){
			classId=$(dom).val();
			className=$(dom).siblings(":first").html();
			 if(gradeId&&classId){
				 loadStudentsFrame(gradeId,classId);
			 }else{
				 console.error("年级或班级为空！");
			 }
		}
		//加载年级数据
		function loadGrade(id,name){
			gradeId=id;
			gradeName=name;
		}
		<!--自定义脚本函数 -->
		function addStudent(){
			$("#add_student_dialog").find("input[text]").each(
				function(){
					$(this).val("");
				}
			);
			$("#add_student_dialog").dialog({
				title:"添加"+gradeName+className+"学生",
				width: 423,
				height: 280
			});
		}
		function editStudent(id){
			$.ajax({
				type : 'post',
				cache : false,
				data: {"id":id},
				dataType : 'json',
				url : "${ctx}jy/evl/manage/getStudentInfo",
				success : function(result) {
					var data=result.evlStudentAccount;
					$('#edit_student_dialog').find("input[name='editId']").val(data.id);
					$('#edit_student_dialog').find("input[name='name']").val(data.name);
					$('#edit_student_dialog').find("input[name='code']").val(data.code);
					//$('#edit_student_dialog').find("input[name='sex']:eq(0)").removeAttr("checked");
					//$('#edit_student_dialog').find("input[name='sex']:eq(1)").removeAttr("checked");
					var tag=1-data.sex;
					$('#edit_student_dialog').find("input[name='sex']:eq("+tag+")").trigger("click");
					$('#edit_student_dialog').find("input[name='cellphone']").val(data.cellphone);
					$('#edit_student_dialog').dialog({
						width: 423,
						height: 280
					});
				},
				error : function() {
					alert("请求异常:" + this.url);
				}
			});
				
		}
		
		//导入
		function showImportGradeStudent(){
			//隐藏文件上传状态
			$(".mes_file_process").hide();
			$('#batch_import_grade_student_dialog').dialog({
				title:"批量导入"+gradeName+"学生",
				width:523,
				height:260
			});
		}
		//导入
		function showImportStudent(){
			//隐藏文件上传状态
			$(".mes_file_process").hide();
			$('#batch_import_student_dialog').dialog({
				title:"批量导入"+gradeName+className+"学生",
				width:523,
				height:260
			});
		}
		//导出
		function showExportStudent(){
 			if(gradeId&&classId){
 				window.location.href="${ctx}jy/evl/manage/exportStudentAccounts?schoolYear=${schoolYear==null?sessionScope._CURRENT_SCHOOLYEAR_:schoolYear.schoolYear}&gradeId="+gradeId+"&classId="+classId;
			 }else{
				 //导出当前学年全部数据
				 window.location.href="${ctx}jy/evl/manage/exportStudentAccounts?schoolYear=${schoolYear==null?sessionScope._CURRENT_SCHOOLYEAR_:schoolYear.schoolYear}";
			 }
			
		}
		//下载模板
		function download_templete(){
			window.open("${ctx}jy/evl/manage/downLoadRegisterTemplate?fileName=batch_import_student_template.xls");
		}
		//下载模板
		function download_grade_templete(){
			window.open("${ctx}jy/evl/manage/downLoadRegisterTemplate?fileName=batch_import_grade_student.xls");
		}
		//保存单个数据
		function submitStudentInfo(domId){
			 if(gradeId&&classId){
				 
			 }else{
				 alert("年级或班级信息缺失。");
				 return;
			 }
			if($("#"+domId).validationEngine('validate')){
				var url ="${ctx}jy/evl/manage/saveOrUpdate?orgId=${orgId}"+"&gradeId="+gradeId+"&classId="+classId+"&schoolYear=${schoolYear==null?sessionScope._CURRENT_SCHOOLYEAR_:schoolYear.schoolYear}";
				$.ajax({
					type : 'post',
					cache : false,
					data: $('#'+domId).serialize(),
					dataType : 'json',
					url : url,
					success : function(data) {
						if (data.result.code==200) {
							switch(domId){
								case "addStudentForm":
									$("#add_student_dialog").dialog("close");
									break;
								case "editStudentForm":
									$("#edit_student_dialog").dialog("close");
									break;
							}
							document.getElementById("iframe_"+gradeId).contentWindow.location.reload(true); 
						}else{
							alert(data.result.msg);
						}
					},
					error : function() {
						alert("请求异常:" + this.url);
					}
				});
			}
		}
		//导出数据解析保存
		function backUpload(data) {
			var resid = data.data;
			var url = "${ctx}/jy/evl/manage/analyzeAndSaveFile";
			$.ajax({
				url : url,
				async:false,
				type : 'post',
				data : {
					"resid" : resid,
					"gradeId":gradeId,
					"classId":classId
				},
				async : false,
				dataType : "json",
				success : function(result) {
					$("#batch_import_student_dialog").dialog("close");
					loadStudentsFrame(gradeId,classId);
					if(result.data.length!=0){
						var tip = "";
							for(var i in result.data){
								tip +=result.data[i]+"<br>";
							}
// 							alert(tip);
							$(".err_tip").html(tip);
							$("#err_dialog").dialog({
								width: 600,
								height: 500
							});
						}
				}
			});
		}
		//导出数据解析保存
		function backGradeUpload(data) {
			var resid = data.data;
			var url = "${ctx}/jy/evl/manage/analyzeAndSaveFile";
			$.ajax({
				url : url,
				async:false,
				type : 'post',
				data : {
					"resid" : resid,
					"gradeId":gradeId
				},
				async : false,
				dataType : "json",
				success : function(result) {
					$("#batch_import_grade_student_dialog").dialog("close");
					loadStudentsFrame(gradeId,classId);
					if(result.data.length!=0){
						var tip = "";
							for(var i in result.data){
								tip +=result.data[i]+"<br>";
							}
// 							alert(tip);
							$(".err_tip").html(tip);
							$("#err_dialog").dialog({
								width: 700,
								height: 500
							});
						}
				}
			});
		}
		//删除单条数据
		function delStudent(id){
			currentId = id;
			$("#delete_dialog").dialog({
				width: 423,
				height: 220
			});
		}
		
	 function showBatchUpdateStudents(){ 
	        if('${isStudentUpdate}'=="true"){
	          <c:if test="${empty failGradeVos}">
	          $("#noFailGrades_dialog").dialog({
	            width: 400,
	            height: 200
	          });
	          </c:if>
	          <c:if test="${! empty failGradeVos}">
	            $("#failGrades_dialog").dialog({
	              width: 400,
	              height: 400
	            }); 
	          </c:if>
	        }else{
	          $("#batch_dialog").dialog({
	            width: 700,
	            height: 400
	          }); 
	        }
	 }
	      
		function batchdeldialog(ids){
			currentId = ids;
			$("#delete_dialog").dialog({
				width: 423,
				height: 220
			});
		}
		function delStudentSure(){
			var url = "${ctx}/jy/evl/manage/delStudent";
			$.ajax({
				url : url,
				async:false,
				type : 'post',
				data : {
					"id":currentId
				},
				dataType : "json",
				success : function(data) {
					if(data.code==200){
						$("#delete_dialog").dialog("close");
						loadStudentsFrame(gradeId,classId);
					}else{
						alert("删除失败，请联系管理员！");
					}
				}
			});
		}
		//切换学年
		function querySchoolYear(dom){
			var schoolYear = $("#subject_sel1").val();
			var url = "${ctx}/jy/evl/manage/loadStudentsBySchoolYear";
			//根据学年动态加载班级
			$.ajax({
				url : url,
				type : 'post',
				data : {
					"schoolYear":schoolYear
				},
				dataType : "json",
				success : function(result) {
					loadClassData(result);
					queryClassList();
				}
			});
		}
		//根据学年加载班级option
		function loadClassData(result){
			var callbackStr='<option value="">请选择</option>';
			for(var i in result.data){
			callbackStr += 
			'<option value="'+result.data[i].classId+'">'+result.data[i].flago+'</option>';
			}
			$("#class_id").html("").html(callbackStr);
			$("select").trigger('chosen:updated');
		}
		//根据班级加载数据列表
		function queryClassList(){
			var dom = $("#class_id").val();
			var url = "${ctx}/jy/evl/manage/loadStudentsBySchoolYearAndClassId";
			var data ={
					"schoolYear":$("#subject_sel1").val(),
					"classId": $("#class_id").val()
					};
			$.ajax({
				url : url,
				type : 'post',
				data : data,
				dataType : "json",
				success : function(result) {
					loadHistoryStudents(result.data);
				}
			});
		}
		function loadHistoryStudents(data){
			if(data.length>0){
				var dom = $("#history_table");
				var htmlStr;
				var j=1;
				for(var i in data){
					var sex = data[i].sex==0?'女':'男';
					htmlStr+=
					'<tr>' 
					+'<td class="student_m_th2">'+(j++)+'</td>'
					+'<td class="student_m_th3">'+data[i].code+'</td>'
					+'<td class="student_m_th4">'+data[i].name+'</td>'
					+'<td class="student_m_th5">'+sex+'</td>'
					+'<td class="student_m_th6">'+data[i].cellphone+'</td>'
					+'</tr>';
					
				}
				dom.html("").html(htmlStr);
				$("#history_table tr:even").css({"background":"#f9f9f9"});
			}else{
				 var  blankHtml='<div class="cont_empty">'+
					'<div class="cont_empty_img1"></div>'+
					'<div class="cont_empty_words">没有数据！</div>'+
				'</div>';
				$("#history_table").html(blankHtml);
			}
		}
		//批量升级班级对应的学生
		function batchUpdateStudents(){
			var updateGrades=[];
			//不能升级的班级
			var notUpdateGrades=[];
			<c:forEach items="${gradeVos}" var="gradeVo" varStatus="gradeStatus">
				<c:if test="${! empty gradeVos[gradeStatus.index+1]}">
					<c:if test="${fn:length(gradeVos[gradeStatus.index+1].classes)==fn:length(gradeVos[gradeStatus.index].classes)}">
						updateGrades.push("${gradeVo.id}-${gradeVos[gradeStatus.index+1].id}");
					</c:if>
					<c:if test="${fn:length(gradeVos[gradeStatus.index+1].classes)!=fn:length(gradeVos[gradeStatus.index].classes)}">
						notUpdateGrades.push("${gradeVo.id}");
					</c:if>
				</c:if>
			</c:forEach>
			$.ajax({
				type : 'post',
				cache : false,
				data: {"updateGrades":updateGrades.join(','),"notUpdateGrades":notUpdateGrades.join(',')},
				dataType : "json",
				url : "${ctx}jy/evl/manage/batchUpdateStudents",
				success : function(result) {
					location.reload();
				},
				error : function(e) {
					alert("请求异常:" + e);
				}
			});
		}
		//导出学生历史数据
		function exportStudentAccount(){
			var schoolYear=$("#subject_sel1").val();
			var classId= $("#class_id").val();
			if(schoolYear&&classId){
				window.location.href="${ctx}jy/evl/manage/exportStudentAccounts?schoolYear="+schoolYear+"&classId="+classId;
			}else if(schoolYear){
				window.location.href="${ctx}jy/evl/manage/exportStudentAccounts?schoolYear="+schoolYear;
			}else{
				window.location.href="${ctx}jy/evl/manage/exportStudentAccounts";
			}
		}
	</script>

</body>
</html>