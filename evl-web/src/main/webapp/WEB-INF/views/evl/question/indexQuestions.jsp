<%@page import="com.tmser.schevaluation.evl.statics.EvlQuestionType"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="question_init" value="<%=EvlQuestionType.init.getValue()%>" />
<c:set var="question_sheji1"
	value="<%=EvlQuestionType.shejiwenjuan.getValue()%>" />
<c:set var="question_sheji2"
	value="<%=EvlQuestionType.xiangguanshezhi.getValue()%>" />
<c:set var="question_fabu" value="<%=EvlQuestionType.fabu.getValue()%>" />
<c:set var="question_jieshu"
	value="<%=EvlQuestionType.jieshu.getValue()%>" />

<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="设计问卷"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
</head>
<body>
	<!--头部-->
	<div class="jyyl_top">
	<ui:tchTop modelName="设计问卷"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="sjwj"></jy:nav>
	</div>
	<!-- 发布 -->
	<div id="release_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">发布</span> <span class="dialog_close"
					onclick="location.reload();"></span>
			</div>
			<div class="dialog_content">
				<div class="release_info">
					<span class="save_img"></span> <strong>问卷发布后，将自动生成评教账号，您可以点击“评教账号”按钮进行查看。</strong>
				</div>
			</div>
		</div>
	</div>
	<!-- 发布不成功弹窗-->
	<div id="not_release_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">发布</span> <span class="dialog_close"
					onclick="location.reload();"></span>
			</div>
			<div class="dialog_content">
				<div class="not_release_info" align="center">
					<span></span> <strong id="not_release_dialog_content"></strong>
				</div>
			</div>
		</div>
	</div>
	<!-- 删除 -->
	<div id="del_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">删除</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span> <strong>您确定要删除此问卷吗？删除后此问卷的全部信息将会被删除！</strong>
				</div>
				<div class="batch_promoted_button">
				<input type="button" class="confirm delQuestion_btn_sure"
					value="确 定">
				</div>
			</div>
		</div>
	</div>
	<!-- 发布后修改 -->
	<div id="edit_fa_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">修改</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span> <strong>此问卷已发布，修改后需重新发布后才能生效，您确定要修改吗？</strong>
				</div>
				<input type="button" data-id="" class="confirm edit_fa_dialog_sure" value="确 定">
			</div>
		</div>
	</div>
	<!-- 结束评教 -->
	<div id="end_assessment_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">结束评教</span> <span class="dialog_close" onclick="location.reload();"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					<span></span> <strong class="end_assessment_dialog_strong"></strong>
				</div>
				<input type="button" class="confirm end_assessment_dialog_sure"
					value="确 定" style="margin: 20px auto;">
			</div>
		</div>
	</div>
	<!-- 学生评教账号 -->
	<div id="student_account_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">评教账号</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="teach_account_info">
					<strong>学生在浏览器中输入评教地址和学号登陆成功后，就可以进行评教啦！</strong>
				</div>
				<div class="teach_account_info1">
					<div class="whole1">
						<span>评教地址：</span><span><a id="copyUrl" target="_blank"></a></span>
						<div class="download" onclick="CopyToClipboard();">
							<strong>复制</strong>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 家长评教账号 -->
	<div id="teach_account_dialog" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">评教账号</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="teach_account_info">
					<span></span> <strong>系统已将评教账号通过短信发送给家长，家长点击评教网址就可以进行评教啦！若家长未接收到短信，请您通过其他方式告知家长。</strong>
				</div>
				<div class="clear"></div>
				<div class="teach_account_info1">
					<div class="whole1">
						<span>全部评教账号：</span>
						<div class="see">
							<span></span> <strong onclick="seeAccount('')">查看</strong>
						</div>
						<div class="line">|</div>
						<div class="download">
							<span></span> <strong onclick="downloadAccount('')">下载</strong>
						</div>
					</div>
					<div class="clear"></div>
					<div class="not_sent">
						<span>未成功发送的评教账号：</span>
						<div class="see">
							<span></span> <strong onclick="seeAccount(1)">查看</strong>
						</div>
						<div class="line">|</div>
						<div class="download">
							<span></span> <strong onclick="downloadAccount(1)">下载</strong>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="flow_chart_wrap">
		<div class="flow_chart_wrap_left">
			<ul>
				<li><a href="${ctx}jy/evl/index">流程图</a></li>
				<li><a href="${ctx}jy/evl/question/indexQuestions"
					class="f_c_w_l_act">设计问卷</a></li>
				<li><a href="${ctx}jy/evl/result/resultIndex">结果统计</a></li>
				<li><a href="${ctx}jy/evl/analyze/analyzeIndex">分析报告</a></li>
				<li><a href="${ctx}jy/evl/manage/students">学生管理</a></li>
			</ul>
		</div>
		<div class="design_questionnaire_right">

			<div class="design_quest_btn">
				<a href="jy/evl/question/toSettingQuestionRelation"><span>+</span><strong>设计问卷</strong></a>
			</div>
			<div class="clear"></div>
			<c:forEach items="${currentList}" var="item">
				<div class="design_quest_div design_quest_div1"
					style="cursor: pointer;"
					data-id="${item.evlQuestionnaires.questionnairesId }"
					data-status="${item.evlQuestionnaires.status }"
					data-crtId="${item.evlQuestionnaires.crtId}"
					id="design_quest_div_${item.evlQuestionnaires.questionnairesId}">
					<c:choose>
						<c:when test="${item.evlQuestionnaires.type == 1 }">
							<div class="srte">学生评教</div>
						</c:when>
						<c:when test="${item.evlQuestionnaires.type == 2 }">
							<div class="parent_education">家长评教</div>
						</c:when>
						<c:when test="${item.evlQuestionnaires.type == 3 }">
							<div class="parent_education">教师自评</div>
						</c:when>
						<c:otherwise>
							<div class="parent_education">民主互评</div>
						</c:otherwise>
					</c:choose>
					<div class="record"
					data-enable="${item.evlQuestionnaires.status}"	data-id="${item.evlQuestionnaires.questionnairesId }">已评/应评：${item.alreadyReviewCount}/${item.shouldReviewCount}</div>
					<div class="design_title"
						title="${item.evlQuestionnaires.title==null?'无标题':item.evlQuestionnaires.title}"
						data-id="${item.evlQuestionnaires.questionnairesId }"
						data-status="${item.evlQuestionnaires.status }">
						<ui:sout
							value="${item.evlQuestionnaires.title==null?'无标题':item.evlQuestionnaires.title}"
							length="70" needEllipsis="true"></ui:sout>
					</div>
					<c:choose>
						<c:when
							test="${(item.evlQuestionnaires.crtId==user.id&&item.evlQuestionnaires.status == question_init)||(item.evlQuestionnaires.crtId==user.id&&item.evlQuestionnaires.status == question_sheji1)||(item.evlQuestionnaires.crtId==user.id&&item.evlQuestionnaires.status == question_sheji2)}">
							<div class="not_start"></div>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong>
										结束时间：${item.evlTimeline.endTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr==null}">
								<div class="time">
									<span></span> <strong>
										开始时间：${item.evlTimeline.beginTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong> ${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
									</strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr==null}">
								<div class="time1">
									<span></span> <strong> </strong>
								</div>
							</c:if>
							<div class="operation"
								data-id="${item.evlQuestionnaires.questionnairesId}">
								<div data-id="${item.evlQuestionnaires.questionnairesId}" data-status="${item.evlQuestionnaires.status }" class="edit" title="修改" style="margin-right: 40px;">
									<span></span> <strong
										class="orerator_edit">修改</strong>
								</div>
								<div data-id="${item.evlQuestionnaires.questionnairesId}" class="release" title="发布">
									<span></span> <strong
										data-id="${item.evlQuestionnaires.questionnairesId}"
										class="orerator_fabu">发布</strong>
								</div>
								<div data-id="${item.evlQuestionnaires.questionnairesId}"
									class="del delQuestion_btn" title="删除">
									<span></span> <strong>删除</strong>
								</div>
							</div>
						</c:when>
						<c:when
							test="${item.evlQuestionnaires.crtId==user.id&&item.evlQuestionnaires.status == question_fabu}">
							<div class="carried_out"></div>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong>
										结束时间：${item.evlTimeline.endTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr==null}">
								<div class="time">
									<span></span> <strong>
										开始时间：${item.evlTimeline.beginTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong> ${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
									</strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr==null}">
								<div class="time1">
									<span></span> <strong> </strong>
								</div>
							</c:if>
							<div class="operation"
								data-id="${item.evlQuestionnaires.questionnairesId}">
								<c:if test="${item.evlQuestionnaires.enable==1}">
									<div data-id="${item.evlQuestionnaires.questionnairesId}" data-status="${item.evlQuestionnaires.status }" class="edit" title="修改" style="margin-right: 40px;">
										<span></span> <strong
											class="orerator_edit">修改</strong>
									</div>
								</c:if>
								<div class="end_assessment" title="结束评教">
									<span></span> <strong>结束评教</strong>
								</div>
								<div data-membertype="${item.evlQuestionnaires.type}"
									data-id="${item.evlQuestionnaires.questionnairesId}"
									class="teach_account" title="评教账号">
									<span></span> <strong>评教账号</strong>
								</div>
							</div>
						</c:when>
						<c:when
							test="${item.evlQuestionnaires.crtId==user.id&&item.evlQuestionnaires.status == question_jieshu }">
							<div class="complete_evl"></div>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong>
										结束时间：${item.evlTimeline.endTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr==null}">
								<div class="time">
									<span></span> <strong>
										开始时间：${item.evlTimeline.beginTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong> ${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
									</strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr==null}">
								<div class="time1">
									<span></span> <strong> </strong>
								</div>
							</c:if>
							<div class="operation"
								data-id="${item.evlQuestionnaires.questionnairesId}">
								<div data-id="${item.evlQuestionnaires.questionnairesId}"
									class="del delQuestion_btn" title="删除">
									<span></span> <strong>删除</strong>
								</div>
							</div>
						</c:when>
						<c:when test="${item.evlQuestionnaires.crtId!=user.id}">
							<c:if test="${evlQuestionnaires.status == 0}">
								<div class="not_start"></div>
							</c:if>
							<c:if test="${evlQuestionnaires.status == question_jieshu}">
								<div class="complete"></div>
							</c:if>
							<c:if
								test="${evlQuestionnaires.status !=question_jieshu&&evlQuestionnaires.status != question_init}">
								<div class="carried_out"></div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong>
										结束时间：${item.evlTimeline.endTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr==null}">
								<div class="time">
									<span></span> <strong>
										开始时间：${item.evlTimeline.beginTimeStr } </strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr!=null}">
								<div class="time">
									<span></span> <strong> ${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
									</strong>
								</div>
							</c:if>
							<c:if
								test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr==null}">
								<div class="time1">
									<span></span> <strong> </strong>
								</div>
							</c:if>
							<div class="operation"
								data-id="${item.evlQuestionnaires.questionnairesId}">
								<div class="release_p">
									发布人：${item.evlQuestionnaires.flago}</div>
							</div>
						</c:when>
					</c:choose>
				</div>
			</c:forEach>
			<div class="clear"></div>
			<c:forEach items="${schoolYearList }" var="schoolYear">
				<div class="clear"></div>
				<div class="design_ques_cont" id="schoolYear${schoolYear }">
					<div class="design_ques_wrap_1">
						<h3>${schoolYear }~${schoolYear+1 }学年设计问卷</h3>
						<div class="show_hide showSchoolYear"
							data-schoolYear="${schoolYear }">
							<span></span> <strong>展开</strong>
						</div>
					</div>
					<div class="design_ques_cont1" style="display: none;"></div>
				</div>
			</c:forEach>
			<c:if test="${empty currentList && empty schoolYearList }">
				<div class="cont_empty">
					<div class="cont_empty_img"></div>
					<div class="cont_empty_words">您还未添加设计问卷，快去添加吧！</div>
				</div>
			</c:if>
		</div>

		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	<!--页脚-->
	<ui:htmlFooter></ui:htmlFooter>
</body>
<script type="text/javascript">
			$(function(){
				bindDomEventClick();
			})
			var currentId = "";
			//加载对应学年下的问卷列表
			function bindSchoolYearListInfo(showDiv, schoolYear) {
				$.post("${ctx}/jy/evl/question/getQuestionList", {
					"schoolYear" : schoolYear
				}, function(data) {
					if (data) {
						showQuestionList(showDiv, data);
						bindDomEventClick();
					}
				}, "json");
			}
			//拼接问卷
			function showQuestionList(showDiv, data) {
				for (var i = 0; i < data.length; i++) {
					var questionnaires = data[i].evlQuestionnaires;
					var shouldReviewCount = data[i].shouldReviewCount;
					var alreadyReviewCount = data[i].alreadyReviewCount;
					var title= "";
					if (questionnaires.title == null|| questionnaires.title == "") {
						title = "无标题";
					} else {
						title = questionnaires.title;
					}
					var timeline = data[i].evlTimeline;
					var content = '<div style="cursor: pointer;" title="'+title+'" data-id="'+questionnaires.questionnairesId+'" class="design_quest_div design_quest_div_history"  id="design_quest_div_'
					+questionnaires.questionnairesId
					+'"'
					+'>';
					switch (questionnaires.type) {
					case 1:
						content += '<div class="srte">学生评教 </div>';
						break;
					case 2:
						content += '<div class="parent_education">家长评教</div>';
						break;
					case 3:
						content += '<div class="parent_education">教师自评</div>';
						break;
					default:
						content += '<div class="parent_education">民主互评</div>';
						break;
					}
					content+='<div class="record" data-enable="'+questionnaires.status+'" data-id="'+questionnaires.questionnairesId+'">已评/应评：'+alreadyReviewCount+'/'+shouldReviewCount+'</div> ';
					content += '<div title="'+title+'" class="design_title" data-id="'+questionnaires.questionnairesId+'">'+title+'</div>';
					//问卷状态
					switch (questionnaires.status) {
					case ${question_init}://未开始
						content += '<div class="not_start"> </div>';
						break;
					case ${question_jieshu}://结束
						content += '<div class="complete"></div>';
						break;
					default://进行中
						content += '<div class="carried_out"></div>';
						break;
					}
					<c:if test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr==null}">
					</c:if>
					<c:if test="${item.evlTimeline.beginTimeStr==null&&item.evlTimeline.endTimeStr!=null}">
					结束时间：${item.evlTimeline.endTimeStr }
					</c:if>
					<c:if test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr==null}">
					开始时间：${item.evlTimeline.beginTimeStr }
					</c:if>
					<c:if test="${item.evlTimeline.beginTimeStr!=null&&item.evlTimeline.endTimeStr!=null}">
					${item.evlTimeline.beginTimeStr }至${item.evlTimeline.endTimeStr }
					</c:if>
					
					if(timeline.beginTimeStr==null&&timeline.endTimeStr!=null){
						content+='<div class="time">'
							+'<span></span>'
							+'<strong>结束时间：'+timeline.endTimeStr+'<strong></div>'
					}else if(timeline.beginTimeStr!=null&&timeline.endTimeStr==null){
						content+='<div class="time">'
							+'<span></span>'
							+'<strong>开始时间：'+timeline.beginTimeStr+'<strong></div>'
					}else if(timeline.beginTimeStr!=null&&timeline.endTimeStr!=null){
						content+='<div class="time">'
							+'<span></span>'
							+'<strong>'
							+timeline.beginTimeStr
							+'至'
							+timeline.endTimeStr
							+'<strong></div>'
					}else if(timeline.beginTimeStr==null&&timeline.endTimeStr==null){
						content+='<div class="time1"><span></span><strong><strong></div>'
					}
					//操作
					if(questionnaires.crtId==${user.id}){
						content+='<div class="operation">' 
						+'<div data-id="'
						+questionnaires.questionnairesId
						+'"'
						+'class="del delQuestion_btn" title="删除">'
							+'<span></span>'
							+'<strong>删除</strong>'
						+'</div>'
						+'</div>';
					}else{
						content+='<div class="operation">'
							+'<div class="release_p">'
							+'发布人：'
							+questionnaires.crtName;
							+'</div>'
							+'</div>';
					}
					showDiv.append(content);
				}
			}
			//点击标题进入问卷详情页面
			function bindDomEventClick() {
				$(".design_quest_div1").click(function() {
					var status = $(this).attr("data-status");
					var questionnairesId = $(this).attr("data-id");
					var crtId = $(this).attr("data-crtId");
					if(parseInt(status)<parseInt('${question_fabu}')){//发布之前
						if(crtId!=${user.id}){
						location.href = "${ctx}/jy/evl/question/viewQuestionRelation?questionnairesId="+ questionnairesId;
						}else{
						window.location.href=_WEB_CONTEXT_+"/jy/evl/question/settingQuestionRelation?questionnairesId="+questionnairesId+"";	
						}
					}else{
						location.href = "${ctx}/jy/evl/question/viewQuestionRelation?questionnairesId="+ questionnairesId;
					}
					
				});
				$(".design_quest_div_history").click(function() {
					var questionnairesId = $(this).attr("data-id");
					location.href = "${ctx}/jy/evl/question/viewQuestionRelation?questionnairesId="+ questionnairesId;
				});
				//删除问卷
				$(".delQuestion_btn").click(function(e){
					e.stopPropagation();
				    // ie
				    e.cancelBubble = true;
					currentId = $(this).attr("data-id");
					$('#del_dialog').dialog({
						width: 423,
						height: 200
					});
				})
				$(".delQuestion_btn_sure").click(function(){
					var url =  "${ctx}/jy/evl/question/delQuestion";
					var questionnairesId = currentId;
					$.post(url, { "questionnairesId":questionnairesId,"userId":${user.id}},
					   function(data){
						$('#del_dialog').dialog("close");
						$("#design_quest_div_"+currentId).remove();
					   });
				})
				//发布问卷
				$(".release").click(function(e){
					e.stopPropagation();
				    // ie
				    e.cancelBubble = true;
					currentId = $(this).attr("data-id");
					var checkstatus = checkQuestion_fabu(currentId);
					if(checkstatus){
					$("#release_dialog").dialog({
						width:420,
						height:220
					});
					var data = {"questionnairesId":currentId,"status":${question_fabu}}
					var url =  "${ctx}/jy/evl/question/changeQuestionStatus_fabu";
					var callback=success_fabu_callback;
					ajax(url,data,callback);
					}
				});
				//结束问卷
				$(".end_assessment").click(function(e){
					e.stopPropagation();
				    // ie
				    e.cancelBubble = true;
					currentId = $(this).parent().attr("data-id");
					var url =  "${ctx}/jy/evl/question/checkEndQuestionStatus";
					var data = { "questionnairesId":currentId,"status":${question_jieshu}};
					var callback= show_end_assessment_dialog;
					$.ajax({
						url : url,
						type : 'post',
						data : data,
						async:false,
						dataType : "json",
						success : function(result) {
							if (callback) {
								callback(result);
							}
						},
					});
				}); 
				//账号
				$(".teach_account").click(function(e){
					e.stopPropagation();
				    // ie
				    e.cancelBubble = true;
					var dom = $(this);
					currentId = $(dom).attr("data-id");
					var membertype = $(dom).attr("data-membertype");
					if(membertype==1){//学生评教
						var url = "${ctx}q/"+currentId;
						$("#copyUrl").prop("href",url);
						$("#copyUrl").html(url);
						$("#student_account_dialog").dialog({
							width:560,
							height:280
						});
					}else if(membertype==2){//家长评教
						$("#teach_account_dialog").dialog({
							width:560,
							height:240
						});
					}
					
				}); 
				$(".end_assessment_dialog_sure").click(function(){
					var url =  "${ctx}/jy/evl/question/changeQuestionStatus_jieshu";
					var data = { "questionnairesId":currentId,"status":${question_jieshu}};
					var callback= end_assessment_dialog_sure;
					$.ajax({
						url : url,
						type : 'post',
						data : data,
						async:true,
						dataType : "json",
						success : function(result) {
							if (callback) {
								callback(result);
							}
						},
					});
				});
				$(".record").click(function(e){
					e.stopPropagation();
				    // ie
				    e.cancelBubble = true;
					var questionId = $(this).attr("data-id");
					var status = $(this).attr("data-enable");
					if(parseInt(status)<parseInt('${question_fabu}')){//发布之前
						window.location.href=_WEB_CONTEXT_+"/jy/evl/question/getQuestionAccountDetail?questionId="+questionId+"";
					}else{
						window.location.href=_WEB_CONTEXT_+"/jy/evl/question/getQuestionMemberDetail?questionId="+questionId+"";	
					}
				})
			}
		$(".design_ques_wrap_1").click(function() {
			var schoolYear = $(this).find(".show_hide").attr("data-schoolYear");
			var showDiv = $("#schoolYear" + schoolYear).find(".design_ques_cont1");
			if (showDiv.css("display") != "none") {
				$(this).find(".show_hide").find('strong').html("展开");
				$(this).find(".show_hide").find('span').css({
					"background-position" : "-296px -4px"
				});
				showDiv.hide();
				return true;
			} else {
				$(this).find(".show_hide").find('strong').html("收起");
				$(this).find(".show_hide").find('span').css({
					"background-position" : "-296px -11px"
				});
				showDiv.html("").show();
				bindSchoolYearListInfo(showDiv, schoolYear);
			}
		});
		//修改问卷
		$(".edit").click(function(e){
			e.stopPropagation();
		    // ie
		    e.cancelBubble = true;
			var questionid = $(this).attr("data-id");
			var url =  "${ctx}/jy/evl/question/getCurrentQuestion";
			var data={
				"questionid":questionid
			}
			var editFlag = false;
			$.ajax({
				url : url,
				type : 'post',
				data : data,
				async:false,
				dataType : "json",
				success : function(result) {
				if(result.data){
					if(result.data.enable!=1){//不可用
						alert("当前问卷正在进行填报，不允许修改！");
					}else{
						editFlag = true;
					}
				}else{
					alert("数据异常，请联系管理员！");
				}
				},
			});
			if(editFlag){
				var status = parseInt($(this).attr("data-status"));
				var fabuStatus= parseInt('${question_fabu}');
			if(status>=fabuStatus){
				$(".edit_fa_dialog_sure").attr("data-id",questionid);
				$("#edit_fa_dialog").dialog({
					width: 423,
					height: 215
				});
			}else{
				window.location.href=_WEB_CONTEXT_+"/jy/evl/question/settingQuestionRelation?questionnairesId="+questionid+"";
			}
			}else{
				location.reload();
			}
		});
		$(".edit_fa_dialog_sure").click(function(){
			var questionid = $(this).attr("data-id");
			window.location.href=_WEB_CONTEXT_+"/jy/evl/question/settingQuestionRelation?questionnairesId="+questionid+"";
		})
		function show_end_assessment_dialog(result){
			$(".end_assessment_dialog_strong").html(result.msg);
				$('#end_assessment_dialog').dialog({
					width: 423,
					height: 230
				});
		}
		function end_assessment_dialog_sure(result){
				$('#end_assessment_dialog').dialog("close");
				location.reload();
		}
		function CopyToClipboard(){
			var text = $('#copyUrl').html();
		    var fb = function () {
		        $t.remove();
		        return false;
		    };
		    var $t = $('<textarea />');
		    $t.val(text).appendTo('body');
		    $t.select();
		    try {
		        if (document.execCommand('copy')||Document.execCommand('copy')) {
		        	alert("评教地址已经复制~");
		            $t.remove();
		            return true;
		        }
		        fb();
		    }
		    catch (e) {
		        fb();
		    }
		}
		function downloadAccount(status){
				window.location.href="${ctx}/jy/evl/question/downloadAccount?status="+status+"&questionId="+currentId;
		}
		function seeAccount(status){
			window.open("${ctx}/jy/evl/question/accountDetail?status="+status+"&name=&questionId="+currentId);
		}
		function checkQuestion_fabu(id){
			var flag = false;
			var data = {"questionnairesId":id,"status":${question_fabu}}
			var url =  "${ctx}/jy/evl/question/checkQuestionStatus_fabu";
			$.ajax({
				url : url,
				type : 'post',
				data : data,
				async:false,
				dataType : "json",
				success : function(result) {
					if(result.code==200){
						flag = true;
					}else{
						$("#not_release_dialog_content").html(result.msg);
						$("#not_release_dialog").dialog({
							width:420,
							height:230
						});
					}
				},
			});
			return flag;
		}
		function success_fabu_callback(result){
			if(result.code!=200){
			alert(result.msg);
			}
		}
		function ajax(url, data, callback) {
			$.ajax({
				url : url,
				type : 'post',
				data : data,
				async:true,
				dataType : "json",
				success : function(result) {
					if (callback) {
						callback(result);
					}
				},
			});
		}
</script>
</html>