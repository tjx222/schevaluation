<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="学生账号列表"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
	<!--自定义css文件引用  -->
	<style>
		.chosen-container-single .chosen-single{
			border:none;
		}
	</style> 
</head>
<body style="background:#fff">
	<!--内容-->
	<div class="content1">
		<div class="student_man_btn_wrap">
			<div class="add_student" onclick="addStudent()">
				<b>+</b>
				<strong>添加学生</strong>
			</div>
			<input type="button"  class="all_export" onclick="showExportStudent()" value="导出" />
			<!-- <input type="button"  class="batch_import_student" onclick="showImportStudent()" value="导入学生" /> -->
		</div>
		<c:if  test="${not empty page.datalist }">
			<div class="student_man_table1">
				<table>
					<tr> 
						<th class="student_m_th2">序号</th>	
						<th class="student_m_th3">学号</th>	
						<th class="student_m_th4">姓名</th>	
						<th class="student_m_th5">性别</th>	
						<th class="student_m_th6">家长电话</th>	
						<th class="student_m_th7">操作</th>	
					</tr>
				</table>
			</div>
			<div class="student_man_table2">
				<table class="s_man_table2">
					<c:forEach items="${page.datalist}" var="student" varStatus="status">
						<tr> 
							<td class="student_m_th2">
								<input type="checkbox" data-id="${student.id}" class="check_box student_check_box" />${status.index+1}
							</td>
							<td class="student_m_th3">${student.code}</td>
							<td class="student_m_th4">${student.name}</td>
							<td class="student_m_th5">${student.sex==1?"男":"女"}</td>
							<td class="student_m_th6">${student.cellphone}</td>
							<td class="student_m_th7">
								<span class="edit_btn" onclick="editStudent('${student.id}')"></span>
								<span class="del_btn" onclick="delStudent('${student.id}')"></span>
							</td>
						</tr>
					</c:forEach>
				</table> 
			</div>
		</c:if>
		<c:if  test="${empty page.datalist }">
			<div class="cont_empty">
				<div class="cont_empty_img1"></div>
				<div class="cont_empty_words">您还没有添加学生！</div>
			</div>
		</c:if>
		<div class="all_wrap">
			<div class="all_box">
				<input type="checkbox" class="all_check_box" id="selectAll"/>
				<label for="selectAll">全选</label>
			</div>
			
			<div class="delete" onclick="batchdelete()">
				<span></span>
				批量删除
			</div>
		</div>
		<div class="clear"></div>
		<div class="pages" style="margin:0 auto;">
			<form id="pageForm" name="pageForm" method="get">
				
				<ui:page url="${ctx}/jy/evl/manage/studentsPageList" data="${page}" />
				<input type="hidden" name="page.currentPage" class="currentPage">
				<input type="hidden" name="classId"	value="${classModel.classId}">
				<input type="hidden" name="schoolYear"	value="${classModel.schoolYear}">
			</form>
		</div>
	</div>
	<!--自定义js文件引用  -->
	<script type="text/javascript">
	<!--自定义脚本函数 -->
	function addStudent(){
		window.parent.addStudent();
	}
	function editStudent(id){
		window.parent.editStudent(id);
	}
	//批量导入学生
	function showImportStudent(){
		window.parent.showImportStudent();
	}
	//批量导出学生
	function showExportStudent(){
		window.parent.showExportStudent();
	}
	//删除学生(单条)
	function delStudent(id){
		window.parent.delStudent(id);
	}
	//全选
	$('.all_check_box').click(function(){
		if($(this).is(':checked')){
			$('.student_check_box').each(function(){
				this.checked=true;
			});
		}else{
			$('.student_check_box').removeAttr('checked');
		}
	});
	//隔行换色
	$(".s_man_table2 tr:even").css({"background":"#f9f9f9"});
	//批量删除
	function batchdelete(){
		if($('.student_check_box:checked').length<1){
			alert("请选择要删除的条目！")
		}else{
			var ids = "";
			$(".student_check_box:checked").each(function(){
				ids += $(this).attr("data-id")+",";
			});
			window.parent.batchdeldialog(ids);
		}
	}
	</script>
</body>
</html>