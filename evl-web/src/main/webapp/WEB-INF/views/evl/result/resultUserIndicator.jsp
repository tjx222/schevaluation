<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教师指标统计"></ui:htmlHeader><link rel="stylesheet" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
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
	<ui:tchTop modelName="教师指标统计"></ui:tchTop>
	</div>
	<jy:di key="${teacher.teacherId }"
		className="com.tmser.schevaluation.uc.service.UserService" var="user" />
	<c:set var="schoolYearHead" value="${empty questionnaires.title ? '无标题': questionnaires.title }" />
	<div class="nav">
		当前位置：
		<c:if test="${empty teacher.flago }">
		<jy:nav id="evl_result"></jy:nav>
		&gt;&nbsp;<a href="${ctx}jy/evl/result/resultQuestionnairesInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;${user.name }
		</c:if>
		<c:if test="${teacher.flago eq 'analyze' }">
			<jy:nav id="evl_analyze"></jy:nav>
			&gt;&nbsp;<a href="${ctx}jy/evl/analyze/analyzeInfo?questionnairesId=${questionnaires.questionnairesId}"><ui:sout value="${schoolYearHead }" length="60" needEllipsis="false"/></a>&nbsp;&gt;&nbsp;${user.name }
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
				<c:if test="${ not empty teacher.resultScore && teacher.resultScore > 0 }">
					<input type="button" class="teaching_details" value="评教详情">
				</c:if>
			</div>
			<div class="view_name_top2">
				<c:if test="${questionnaires.indicatorType eq 1 }">
					<table class="view_name_top2_table1 view_name_top2_table">
						<tr>
							<th colspan="2" style="width: 643px;">评价内容</th>
							<th style="width: 114px;">得分</th>
							<th style="width: 111px;">得分率</th>
						</tr>
						<c:forEach items="${indicatorList }" var="indicatorVo1">
							<tr>
								<td ${empty indicatorVo1.childIndicators ? 'colspan="2" style="width: 643;"':'style="width:163px;"' } ><span title="${indicatorVo1.evlIndicator.title }（${indicatorVo1.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.evlIndicator.title }" escapeXml="false" length="12" needEllipsis="true" />（${indicatorVo1.evlIndicator.scoreTotalInt }分）</span></td>
								<c:if test="${not empty indicatorVo1.childIndicators }">
								<td style="width: 503px;">
									<table class="view_name_top2_table2">
										<c:forEach items="${indicatorVo1.childIndicators }"
											var="indicatorVo2">
											<tr>
												<td style="text-align: left;"><span title="${indicatorVo2.evlIndicator.title }"><ui:sout value="${indicatorVo2.evlIndicator.title }" escapeXml="false" length="66" needEllipsis="true" /></span></td>
											</tr>
										</c:forEach>
									</table>
								</td>
								</c:if>
								<c:set var="evlAnalyzeIndicator"
									value="${indicatorVo1.evlAnalyzeIndicator }"></c:set>
								<td class="td_111">${ empty evlAnalyzeIndicator.resultScore ? '' : evlAnalyzeIndicator.resultScore }</td>
								<td class="td_111">${ empty evlAnalyzeIndicator.scoreAverage ? '' : evlAnalyzeIndicator.scoreAverage }%</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${questionnaires.indicatorType eq 2 }">
					<table class="view_name_top2_table1 view_name_top2_table">
						<tr>
							<th colspan="2" style="width: 643px;">评价内容</th>
							<th style="width: 129px;">得分</th>
							<th style="width: 125px;">得分率</th>
						</tr>
					</table>
					<table class="view_name_top2_table">
						<c:forEach items="${indicatorList }" var="indicatorVo1">
							<tr>
								<td style="width: 163px;"><span title="${indicatorVo1.evlIndicator.title }（${indicatorVo1.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo1.evlIndicator.title }" escapeXml="false" length="12" needEllipsis="true" />（${indicatorVo1.evlIndicator.scoreTotalInt }分）</span></td>
								<td class="view_name_top2_td" style="border-right-width: 0px;">
									<table class="view_name_top2_table2">
										<c:forEach items="${indicatorVo1.childIndicators }"
											var="indicatorVo2">
											<tr>
												<td style="text-align: left;"><span title="${indicatorVo2.evlIndicator.title }（${indicatorVo2.evlIndicator.scoreTotalInt }分）"><ui:sout value="${indicatorVo2.evlIndicator.title }" escapeXml="false" length="54" needEllipsis="true" />（${indicatorVo2.evlIndicator.scoreTotalInt }分）</span></td>
												<c:set var="evlAnalyzeIndicator"
													value="${indicatorVo2.evlAnalyzeIndicator }"></c:set>
												<td class="td_111">${ empty evlAnalyzeIndicator.resultScore ? '' : evlAnalyzeIndicator.resultScore}</td>
												<td class="td_110">${ empty evlAnalyzeIndicator.scoreAverage ? '' : evlAnalyzeIndicator.scoreAverage }%</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	<script type="text/javascript">
		jQuery(function($) {
			$(".teaching_details").click(function() {
				location.href = "${ctx}jy/evl/result/resultUserIndicatorInfo?questionnairesId=${questionnaires.questionnairesId}&teacherId=${teacher.teacherId}&resultScore=${teacher.resultScore}&sort=${teacher.sort}&flago=${teacher.flago}";
			});
			$(".view_name_top2_table2").each(function (){
				$(this).find("tr").last().css({"border-bottom":"none"});
			});
			$(".view_name_top2_table2 tr").each(function (){
				$(this).find("td").last().css({"border-right":"none"});
			});
			$(".view_name_top2_table tr").each(function (){
				$(this).find("td").last().css({"border-right":"none"});
				$(this).find("th").last().css({"border-right":"none"});
			}); 
			
		});
	</script>
</body>
</html>