<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教师指标统计详情"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"><script src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<style>
.chosen-container-single .chosen-single {
	border: none;
}

.chosen-container.chosen-with-drop .chosen-drop {
	width: 99%;
}
</style>
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop modelName="教师指标统计详情"></ui:tchTop>
	</div>
	<jy:di key="${teacher.teacherId }"
		className="com.mainbo.jy.uc.service.UserService" var="user" />
	<c:set var="schoolYearHead" value="${empty questionnaires.title ? '无标题' : questionnaires.title }" />	
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${empty teacher.flago }">
		<jy:nav id="evl_result"></jy:nav>
		&gt;&nbsp;<a href="${ctx}jy/evl/result/resultQuestionnairesInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;<span
			style="cursor: pointer;" id="showUserIndicator">${user.name }</span>&nbsp;&gt;&nbsp;评教详情
		</c:if>
		<c:if test="${teacher.flago eq 'analyze' }">
			<jy:nav id="evl_analyze"></jy:nav>
			&gt;&nbsp;<a href="${ctx}jy/evl/analyze/analyzeInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;<span
				style="cursor: pointer;" id="showUserIndicator">${user.name }</span>&nbsp;&gt;&nbsp;评教详情
		</c:if>
	</div>
	<div class="flow_chart_wrap">
		<div class="view_name">
			<div class="view_name_top1">
				<div class="view_head">
					<div class="view_head_img">
						<ui:photo src="${user.photo }"></ui:photo>
					</div>
					<div class="view_head_img_mask"></div>
					<div class="view_head_title">
						<span></span>
						<div class="download_strong">
							<div class="download_strong1">教龄：${user.schoolAge }</div>
							<div class="download_strong1">性别：${user.sex == 0 ? '男' : '女' }</div>
							<div class="download_strong1">职称：${user.profession }</div>
							<div class="download_strong1">
								<div class="zw">职务：</div>
								<c:forEach items="${spaceList }" var="space">
									<div class="zw_cont">${space.spaceName }</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<ol>
					<li>${user.name }</li>
					<li>总分：${ empty teacher.resultScore ? '' : teacher.resultScore }</li>
					<li>排名：${ empty teacher.sort ? '' : teacher.sort }</li>
				</ol>
				<div class="float_right" style="width:300px;">
					<div class="left_w"></div>
					<div class="a" style="width: 140px;">
						<select class="chosen-select-deselect" style="width: 140px; height: 25px;" id="gradeClass_sel">
						</select>
					</div>
					<div class="left_border"></div>
					<div class="a" style="margin-left:4px;">
						<select class="chosen-select-deselect"	style="width: 110px; height: 25px;" id="subject_sel">
						</select>
					</div>
				</div>
			</div>
			<div class="view_name_top2">
				<div class="view_name_top2_left">
					<table class="v_n_table_l">
						<tr>
							<th>评价内容</th> 
							<th>得分</th>							
						</tr>
					</table>
					<table class="v_n_table_l">
						<c:if test="${questionnaires.indicatorType eq 1 }">
							<c:forEach items="${indicatorList }" var="indicatorVo1">
								<tr class="table_r_height indicatorVo1">
									<td class="td_left td_134 oneIndicator_td" ${empty indicatorVo1.childIndicators ?'style="width:506px;"':'' } data-id="${indicatorVo1.evlIndicator.id }"><span title="${indicatorVo1.evlIndicator.title }（${indicatorVo1.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.evlIndicator.title }" escapeXml="false" length="12" needEllipsis="true" />（${indicatorVo1.evlIndicator.scoreTotalInt }分）</span></td>
									<c:if test="${not empty indicatorVo1.childIndicators }">
									<td class="td_372" style="border-right-width: 0px;">
										<table class="v_n_table_l2">
											<c:forEach items="${indicatorVo1.childIndicators }"
												var="indicatorVo2">
												<tr>
													<td class="td_355 twoIndicator_td" style="text-align: left;"
														data-id="${indicatorVo2.evlIndicator.id }"><span title="${indicatorVo2.evlIndicator.title }"><ui:sout value="${indicatorVo2.evlIndicator.title }" escapeXml="false" length="48" needEllipsis="true" /></span></td>													
												</tr>
											</c:forEach>
										</table>
									</td>
									</c:if>
									<c:set var="evlAnalyzeIndicator"
														value="${indicatorVo1.evlAnalyzeIndicator }"></c:set>
									<td class="td_76 scoreClass">${ empty evlAnalyzeIndicator.resultScore ? '' : evlAnalyzeIndicator.resultScore}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${questionnaires.indicatorType eq 2 }">
							<c:forEach items="${indicatorList }" var="indicatorVo1">
								<tr>
									<td class="td_left oneIndicator_td" data-id="${indicatorVo1.evlIndicator.id }"><span title="${indicatorVo1.evlIndicator.title }（${indicatorVo1.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.evlIndicator.title }" escapeXml="false" length="12" needEllipsis="true" />（${indicatorVo1.evlIndicator.scoreTotalInt }分）</span></td>
									<td style="border-right-width: 0px;">
										<table class="v_n_table_l2">
											<c:forEach items="${indicatorVo1.childIndicators }"
												var="indicatorVo2">
												<tr class="table_r_height indicatorVo2">
													<td class="td_355 twoIndicator_td" style="text-align: left;width:335px;"
														data-id="${indicatorVo2.evlIndicator.id }"><span title="${indicatorVo2.evlIndicator.title }（${indicatorVo2.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo2.evlIndicator.title }" escapeXml="false" length="36" needEllipsis="true" />（${indicatorVo2.evlIndicator.scoreTotalInt }分）</span></td>
													<c:set var="evlAnalyzeIndicator"
														value="${indicatorVo2.evlAnalyzeIndicator }"></c:set>
													<td class="scoreClass">${ empty evlAnalyzeIndicator.resultScore ? '' : evlAnalyzeIndicator.resultScore}</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<div class="view_name_top2_right" style="min-height:57px;">
					<table class="v_n_table_r" id="studentLevel">
						<tr id="studentHead">
						</tr>
					</table>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript">
		$(function() {
			var questionnairesId = "${questionnaires.questionnairesId}";
			var teacherId = "${teacher.teacherId}";
			var indicatorType = "${questionnaires.indicatorType}";

			initLoad();
			
			function initLoad() {
				var url = "${ctx}jy/evl/result/findClassAndSubjectListByUserId";
				$.getJSON(url, {
					"questionnairesId" : questionnairesId,
					"teacherId" : teacherId
				}, function(data) {
					if (data) {				
						var innerHtml = "";
						for ( var index in data) {					
							innerHtml += "<option value='"+data[index].gradeId +";"+data[index].classId +";"+data[index].subjectId+"'>"
											+ data[index].gradeName + "（"
											+ data[index].className
											+ "）</option>";							
						}
						$("#gradeClass_sel").append(innerHtml);
						$("#gradeClass_sel").chosen({disable_search : true});
						$("#gradeClass_sel").trigger("chosen:updated");
						
						$(".v_n_table_l2").find("tr:last").css("border-bottom-width","0px");
						$("#studentHead").html("<th>&nbsp;</th>");
						
						//年级下拉变更
						loadSubjectByIds();
					}
				});
								
			}
			
			//通过学科id加载学科下拉框
			function loadSubjectByIds(){
				//班级下拉变更事件
				var idsStr = $("#gradeClass_sel").val();
				var questionId = '${questionnaires.questionnairesId}';
				if(idsStr == null || idsStr == "undefined"){
					return false;
				}
				var ids = $("#gradeClass_sel").val().split(";");
				$.getJSON("${ctx}jy/evl/result/getSubjectByIds", {
					"subjectIds" : ids[2],
					"questionId":questionId
				}, function(data) {
					$("#subject_sel").empty();
					if(data){
						for(var i in data){
							$("#subject_sel").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
						}	
					}
					$("#subject_sel").chosen({disable_search : true});
					$("#subject_sel").trigger("chosen:updated");
										
					//学科下拉变更
					loadStudentResultListByQuestionnairesId($("#subject_sel"));
				});			
				
				$(".indicatorVo1").children("td").each(function(){
					$(this).css("height",($(this).height()-1)+"px");
				});
			}

			//年级下拉变更事件
			$("#gradeClass_sel").change(function(){
				loadSubjectByIds($(this));
			});
			
			//学科下拉变更事件
			$("#subject_sel").change(function(){
				loadStudentResultListByQuestionnairesId($(this));
			});
			
			//学科下拉变更事件
			function loadStudentResultListByQuestionnairesId(obj){				
				var ids = $("#gradeClass_sel").val().split(";");
				var param = {"questionnairesId" : questionnairesId,
						"teacherId" : teacherId,
						"gradeId":ids[0],
						"classId":ids[1],
						"subjeId":obj.val()};
				$.post("${ctx}jy/evl/result/findStudentResultListByQuestionnairesId",param,function(data){
					if(data){
						var indicatorIds = [];
						if(indicatorType == 1){
							$(".oneIndicator_td").each(function(index){
								indicatorIds.push($(this).attr("data-id"));
							});
						}else if(indicatorType == 2){
							$(".twoIndicator_td").each(function(index){
								indicatorIds.push($(this).attr("data-id"));
							});
						}						
						loadStudentHead(data.headList);
						loadStudentLevel(indicatorIds,data);
					}
				},"json");
			}		
			
			//加载学生列头
			function loadStudentHead(headList){
				$("#studentHead").empty();
				for(var i in headList){
					$("#studentHead").append("<th data-code='"+headList[i].code+"'>"+headList[i].name+"</th>");
				}
				if(headList == null || headList.length == 0){
					$("#studentHead").html("<th>&nbsp;</th>");
				}
				$(".v_n_table_r tr").each(function (){ 
					$(this).find("th").last().css({"border-right":"none"});
				}); 
			}
			
			//加载评价等级
			function loadStudentLevel(indicatorIds,data){				
				$("#studentLevel").find("tr:gt(0)").remove();
				var headList = data.headList;
				var columnCount = new Array();
				for(var i in indicatorIds){
					var allScore = 0.0;
					var allCount = 0;					
					var levelScore = data[indicatorIds[i]];
					var studentCode = $("#studentHead").find("th");						
					var innerHtml = "<tr>";
					for(var j = 0; j < studentCode.length; j++){		
						if(i == 0){
							columnCount[j] = 0;
						}
						var flag = true;
						for(var k in levelScore){
							if($(studentCode[j]).attr("data-code") == levelScore[k].code){
								innerHtml += "<td>"+levelScore[k].flago+"</td>";
								allScore += levelScore[k].resultScore;
								allCount ++ ;
								flag = false;
								break;
							}
						}
						if(flag){
							innerHtml += "<td>&nbsp;</td>";
							columnCount[j]++;
						}
					}
					$(".scoreClass").eq(i).html(allCount==0?"0.00":(allScore/allCount).toFixed(2));
					if(studentCode == null || studentCode == ""){						
						$("#studentLevel").append("<td>&nbsp;</td></tr>");
					}else{
						$("#studentLevel").append(innerHtml+"</tr>");
					}
				}
				var hideCount = 0;
				for(var j = 0; j < studentCode.length; j++){	
					if(columnCount[j] == indicatorIds.length){
						$("#studentLevel").find("tr th").eq(j).hide();
						$("#studentLevel").find("tr:gt(0)").each(function(index){
							$(this).find("td").eq(j).hide();
						});
						hideCount++;
					}
				}
				if(hideCount == studentCode.length){
					$("#studentHead").append("<th>&nbsp;</th>");
					$("#studentLevel").find("tr:gt(0)").each(function(index){
						$(this).append("<td>&nbsp;</td>");
					});
				}
				var sUserAgent = navigator.userAgent.toLocaleLowerCase();
				var levelTr = null;
				levelTr = $("#studentLevel").children().children("tr:gt(0)");
				var length = 0;
				if(indicatorType == 1){
					$(".v_n_table_l").find("th:last").addClass("td_76");
					$(".indicatorVo1").each(function(index){
						var height = $(this).height();		
						if(sUserAgent.indexOf('firefox') == -1){
							if(length == index){
								length += $(this).parent().find("tr").length;
							}
							if($(".indicatorVo1").length != length ){
								height += 1;
							}
							if(index == 0){
								height += 1;
							}
						}else{
							if($(".indicatorVo1").length == index + 1 ){
								height = height-2;
								levelTr.eq(index).find("td").css({"height":height+"px","line-height":height+"px"});
							}
						}						
						levelTr.eq(index).css("height",height+"px");
					});
				}else if (indicatorType == 2){	
					var widthScore = $(".table_r_height").children("td:last").width();
					if(navigator.userAgent.toLocaleLowerCase().indexOf('firefox') >= 0){
						widthScore += 1;
					}					
					$(".v_n_table_l").find("th:last").css({"width":(widthScore+"px"),"padding":"0 10px"});

					$(".indicatorVo2").each(function(index){
						var height = $(this).height();		
						if(sUserAgent.indexOf('firefox') >= 0){
							if(length == index){
								length += $(this).parent().find("tr").length;
							}
							if($(".indicatorVo2").length != length && length == index+1){
								height += 1;
							}
						}
						levelTr.eq(index).css("height",height+"px");
					});
				}	

				$("#studentLevel").find("tr").last().css("border-bottom-width","0px");		
				$(".v_n_table_r tr").each(function (){ 
					$(this).find("td").last().css({"border-right":"none"});
				}); 
			}
			
			//教师名称导航链接
			$("#showUserIndicator").click(function(){
				location.href = "${ctx}jy/evl/result/resultUserIndicator?questionnairesId="
						+ questionnairesId
						+ "&teacherId="
						+ teacherId
						+ "&resultScore=${teacher.resultScore }"
						+ "&sort=${teacher.sort }"
						+ "&flago=${teacher.flago}";
			});
						
		});
	</script>
</body>
</html>