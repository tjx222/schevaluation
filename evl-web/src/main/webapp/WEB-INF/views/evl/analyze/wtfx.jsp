<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div class="clear"></div>
	<c:if test="${ problemsMap.isNull eq false }">
		<div class="analysis_report_div">
			<div class="analysis_report_div1">
				<h4 class="h44">本次问卷发现的主要问题</h4>
				<div class="show_hide">
					<span></span> <strong>收起</strong>
				</div>
			</div>
			<div class="analysis_report_cont" id="analysisProblems">
				<p style="margin-left: 0;">通过对本次问卷的分析，存在的问题如下（请学校对问题给予关注，并采取相应的对策予以解决）：</p>
				<c:if test="${ not empty problemsMap.problemGrade }">
					<h5>年级组方面：</h5>
					<c:forEach items="${problemsMap.problemGrade }" var="item">
						<p>${item.gradeName }教师平均分（${item.gradeScore }）比全体教师平均分低${item.teacherDiff }分，比满分低${item.allDiff }分。</p>
					</c:forEach>
				</c:if>
				<c:if test="${ not empty problemsMap.problemSubject }">
					<h5>学科组方面：</h5>
					<c:forEach items="${problemsMap.problemSubject }" var="item">
						<p>${item.subjectName }学科教师平均分（${item.subjectScore }）比全体教师平均分低${item.teacherDiff }分，比满分低${item.allDiff }分。</p>
					</c:forEach>
				</c:if>
				<c:if test="${ not empty problemsMap.problemDirector }">
					<h5>年级班主任方面：</h5>
					<c:forEach items="${problemsMap.problemDirector }" var="item">
						<p>${item.gradeName }班主任平均分（${item.gradeScore }）比全体班主任平均分低${item.directorDiff }分，比满分低${item.allDiff }分。</p>
					</c:forEach>
				</c:if>
				<c:if test="${ not empty problemsMap.problemSchoolAge }">
					<h5>教龄段方面：</h5>
					<c:forEach items="${problemsMap.problemSchoolAge }" var="item">
						<p>${item.schoolTitle }教师平均分（${item.schoolScore }）比全体教师平均分低${item.teacherDiff }分，比满分低${item.allDiff }分。</p>
					</c:forEach>
				</c:if>
				<c:if test="${ not empty problemsMap.problemDirectorMember }">
					<h5>低于平均分的班主任（后10%）名单如下：</h5>
					<p id="directorMember" style="max-height: 100px; overflow-y: auto;">
						<c:forEach items="${problemsMap.problemDirectorMember }"
							var="item">
							<span data-id="${item.directorId }"
								data-gradeName="${item.gradeName}"
								data-className="${item.className}">${item.directorName }（${item.directorScore }）</span>
						</c:forEach>
					</p>
				</c:if>
				<c:if test="${ not empty problemsMap.problemTeacherMember }">
					<h5>低于平均分的教师（后10%）名单如下：</h5>
					<p id="teacherMember" style="max-height: 100px; overflow-y: auto;">
						<c:forEach items="${problemsMap.problemTeacherMember }" var="item">
							<span data-id="${item.teacherId }"
								data-gradeName="${item.gradeId}"
								data-className="${item.classId}"
								data-subjectName="${item.subjectId}">${item.teacherName }（${item.resultScore }）</span>
						</c:forEach>
					</p>
				</c:if>
			</div>
		</div>
		<div class="clear"></div>
	</c:if>
</body>
</html>