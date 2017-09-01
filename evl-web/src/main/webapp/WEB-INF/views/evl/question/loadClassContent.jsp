<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script>
	<div style="border: none;" class="search_wrap"></div>
	<span class="search_wrap1">已选班级 (<span id="search_wrap1_span"></span>)：</span>
	<div class="clear"></div>
	<div class="centent_left">
		<ul id="ul1_1" >
			<div>
				<input type="checkbox" id="quanxuan1_1" />&nbsp;&nbsp;全选
				<div class="clear"></div>
			</div>
			<c:forEach items="${gradeVos}" var="gradeVo">
				<li data-id="${gradeVo.id}"><input  type="checkbox" name="checkbox1" > <a>${gradeVo.name}</a>
				<ul style="margin-top: auto;">
				<c:forEach items="${gradeVo.classes}" var="classVo">
				<c:set var="allIds" value=",${allreadyClassIds},"></c:set>   
				<c:set var="id" value=",${classVo.id},"></c:set>
					<li <c:if test="${fn:contains(allIds,id)}" >style="display: none;"</c:if> data-id="${classVo.id}"><input  type="checkbox" name="checkbox2"> <a>${classVo.name}</a></li>
				</c:forEach>
				</ul>
				</li>
				</c:forEach>
				</ul>
	</div>
	<div class="centent_center"><!--→ -->
		<input type="button" value="添加" class="add_1" id="add_1_1"> <input
			type="button" value="删除" class="add_2" id="add_2_1">
	</div>
	<div class="centent_right">
		<ul id="ul2_1"  class="right_content_id">
			<div id="quanxuan2_div">
				<input type="checkbox" id="quanxuan2_1" />&nbsp;全选
				<div class="clear"></div>
			</div>
		</ul>
	</div>
	<div class="clear"></div>
	<input type="button" class="btn_sub" id="class_save" value="确定" />
</body>
<script type="text/javascript">
loadRightStudent();
$(document).ready(function(){
	var allGradeIds = "${allreadyClassIds}";//所有已经选择年级班级
	var arryIds ="";
   	var count =0;
	if(allGradeIds!=""){
   		arryIds=allGradeIds.split(",");
    	$.each(arryIds, function(i, n){
    		if(n==""){
    			return;
    		}
    		count++;
    		var parent = $('#ul1_1 li[id='+n+'] input[name="checkbox2"]').parent();
    		parent.addClass("centent_left_act");
    		parent.hide();
    		var parent2 = $('#ul2_1 li[id='+n+'] input[name="checkbox2"]');
    		parent2.prop("checked",false);
    		parent2.eq(0).parent().show();
    	})
   	}
   	$("#search_wrap1_span").text(count);
   	if($("#ul2_1 input[name='checkbox2']:visible").length<=0){
 		$("#quanxuan2_div").hide();
    }
})
$('.centent_left li a').click(function(){ 
		$(this).next().slideToggle();
	});

$('#ul1_1 ').on('click','li input[name="checkbox1"]',function(){
	$(this).parent().find("input[name='checkbox2']").prop("checked",$(this).prop('checked'));
	}) 
//当每第一个li下的checkbox2全部选中时让父checkbox1也选中
function contorllerBox1(){
	$('#ul1_1 input[name="checkbox2"]').each(function(){
		var countLi = $(this).parents("ul:first").find("li:visible").size();
		var countCkeced = $(this).parents("ul:first").find("input:visible:checked").size();
		if(countLi != 0 && countCkeced != 0  && countLi == countCkeced){
			$(this).parents("ul:first").parent().children("input").prop("checked",true);
		}else{
			$(this).parents("ul:first").parent().children("input").prop("checked",false);
		}
	})
}
function quxuan2(){
		var boxUl2 = $('#ul2 input[name="checkbox2"]:checked').size();
		var unboxUl2 = $('#ul2 input[name="checkbox2"]').size();
		if(boxUl2==unboxUl2){
			$("#quanxuan2_checkbox").prop("checked",true);
		}else{
			$("#quanxuan2_checkbox").prop("checked",false);
		}
	}
$('#ul1_1 ').on('click',' input[name="checkbox2"]',function(){
	var checkedtitle = $(this).parent().attr("data-id");
	controllerGl($(this),checkedtitle);
	var sizes = $('#ul1_1 li[data-id='+checkedtitle+']').find("input[name='checkbox2']").size();
	var countLi = $(this).parents("ul:first").find("li:visible").size();
	var countCkeced = $(this).parents("ul:first").find("input:visible:checked").size();
	if(countLi != 0 && countCkeced != 0  && countLi == countCkeced){
		$(this).parents("ul:first").parent().children("input").prop("checked",true);
	}else{
		$(this).parents("ul:first").parent().children("input").prop("checked",false);
	}
	var countLi1 = $(this).parents("ul").parents("ul").parents("li").find("li:visible").size();
	var countCkeced1 = $(this).parents("ul").parents("ul").parents("li").find("input:visible:checked").size();
	if(countLi1 != 0 && countCkeced1 != 0  && countLi1 == countCkeced1){
		$(this).parents("ul").parents("ul").parents("li:visible").children("input").prop("checked",true);
	}else{
		$(this).parents("ul").parents("ul").parents("li:visible").children("input").prop("checked",false);
	}
	quxuan1("ul1_1","quanxuan1_1");
	}) 
	
	$("#quanxuan1_1").on('click',function(){
     $("#ul1_1 input[name='checkbox1']").prop("checked",$(this).prop("checked"));
     $("#ul1_1 input[name='checkbox2']").prop("checked",$(this).prop("checked"));
});
	
$('#add_1_1').on('click',function() {
	$("#ul1_1 input[name='checkbox2']:checked").each(function(index,obj){
		$(this).parent().hide();
		$(this).parent("li").addClass("centent_left_act");
		var dataId = $(this).parent().attr("data-id");
		$('#ul2_1 li[data-id='+dataId+']').show();
		
	})
   	//如果右侧有内容，则显示全选复选框
   	if($("#ul2_1 li:visible").length>0){
    	$("#quanxuan2_div").show();
    }
   	$("#ul1_1 input").prop("checked",false);
   	quxuan2("ul2_1","quanxuan2_1");
   	$("#quanxuan2_1").prop("checked",false);
   	$("#search_wrap1_span").text($('#ul2_1 li:visible').length);
});
$('#add_2_1').on('click',function() {
	var equal1 = [];
	$("#ul2_1 input[name='checkbox2']:checked").each(function(){
		$(this).parent().hide();
		$(this).parent("li").removeClass("centent_left_act");
		$(this).prop("checked",false);
		var dataId = $(this).parent().attr("data-id");
		$('#ul1_1 li[data-id='+dataId+']').show();
		$('#ul1_1 li[data-id='+dataId+']').removeClass("centent_left_act");
 	});
 	if($("#ul2_1 input[name='checkbox2']:visible").length<=0){
 		$("#quanxuan2_div").hide();
    }
   	$("#search_wrap1_span").text($('#ul2_1 li:visible').length);
 });

$("#quanxuan2_1").on('click',function(){
     $("#ul2_1 input[name='checkbox2']").prop("checked",$(this).prop("checked"));
});

$('#ul2_1').on('click',' input[name="checkbox2"]',function(){
	var checkedId = $(this).parent().attr("data-id");
	if($(this).prop('checked')){
		$('#ul2_1 li[data-id='+checkedId+']').find("input[name='checkbox2']").prop("checked",true);
	}else{
		$('#ul2_1 li[data-id='+checkedId+']').find("input[name='checkbox2']").prop("checked",false);
	}
	quxuan2("ul2_1","quanxuan2_1");
});
//保存班级
$("#class_save").click(function(){
	var paramArray = [];
	var questionnairesId = ${questionnairesId};
	$("#ul2_1 li:visible").each(function(i,dom){
		var id = $(dom).attr("data-id");
		var gradeId =  $(dom).attr("data-gradeid");
		var paramObj =new Object();
		paramObj.id=id;
		paramObj.questionnairesId=questionnairesId;
		paramObj.gradeId=gradeId;
		paramArray.push(paramObj);
	});
	var paramObj =new Object();
	paramObj.questionnairesId=questionnairesId;
	paramArray.push(paramObj);
	var url = "${ctx}jy/evl/question/class_save";
	JsonAjax(url,JSON.stringify(paramArray),null);
	$("#pjlx_list2_wrap_dialog").dialog("close");
})
function loadRightStudent(){
	var ids = '${allreadyClassIds}';
	arryIds=ids.split(",");
	if(arryIds==null){
		return;
	}
	var bufferMap = {};
	$.each(arryIds, function(i, n){
		if(n!=null&&n!=""){
		bufferMap[n]="tag";
		}
	});
	var htmlStr='';
	<c:forEach items="${allGradeIdList}" var="allClass">
	if(bufferMap[${allClass.id}]){
	htmlStr+='<li data-id="${allClass.id}" data-gradeid="${allClass.gradeId}"><input  type="checkbox"   name="checkbox2"> <a>${allClass.name}</a></li>';
	}else{
	htmlStr+='<li data-id="${allClass.id}" data-gradeid="${allClass.gradeId}" style="display: none;"><input  type="checkbox"   name="checkbox2"> <a>${allClass.name}</a></li>';
	}
	</c:forEach>
	$(".right_content_id").append(htmlStr);
}
function JsonAjax(url, data, callback) {
			$.ajax({
				url : url,
				async:false,
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
