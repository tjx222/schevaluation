<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<ui:htmlHeader title="查看相关设置"></ui:htmlHeader>
<head>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/evl/css/index.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css"> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<body>
<!--头部-->
<div class="jyyl_top">
	<ui:tchTop modelName="查看相关设置"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：空间首页 &gt;评教系统&gt;查看相关设置
</div>
<div class="clear"></div> 
<div class="flow_chart_wrap">
	<div class="related_settings">
		<ul>
			<li class="ul_one ul_act1"><a herf="#"><span>1</span><strong>相关设置</strong></a></li>
			<li class="ul_two"><a herf="#"><span>2</span><strong>设计内容</strong></a></li>
		</ul>
		<div class="related_settings_list">
			<h3>评教类型</h3>
			<div class="check">
				学生评教
			</div>
			<h3>哪些学生评</h3>
			<div class="check"> 
				全体学生
			</div>
			<h3>评价哪些学科<span>（确定学科即可确定所教班级的教师）</span></h3>
			<div class="check">
				语文、数学、科学、英语、语文、数学、思品与社会
			</div>
			<h3>评教时段</h3>
			<div class="check">
				全学年 
			</div>
			<h3>评教方式</h3>
			<div class="check">
				对各二级指标进行评价
			</div>
			<h3>评价等级及权重<span>（为使区分度更加合理，建议评价等级不少于五级）</span></h3>
			<div class="check">  
				<table cellpadding="0" cellspacing="0" class="gradg_table">
					<tr>
						<th>序号</th><th>评价等级</th><th>权重</th>
					</tr>
					<tr>
						<td>1</td><td>A+</td><td>100%</td>
					</tr>
					<tr>
						<td>2</td><td>A</td><td>90%</td>
					</tr>
					<tr>
						<td>3</td><td>B+</td><td>75%</td>
					</tr>
					<tr>
						<td>4</td><td>B</td><td>60%</td>
					</tr>
					<tr>
						<td>5</td><td>C</td><td>50%</td>
					</tr>
					<tr>
						<td>4</td><td>D</td><td>0</td>
					</tr>
				</table>
			</div>
			<h3>评价时限<span>（仅为起止时间的提醒，不会自动终止评价）</span></h3>
			<div class="check">
				2016-06-14 13:23 至 2016-06-20 13:23
			</div> 
		</div>
		<div class="height1 clear"></div>
	</div>
</div>
<div class="clear"></div>
<!--页脚-->
<ui:htmlFooter></ui:htmlFooter>
</body>
<script type="text/javascript" src="${ctxStatic }/modules/evl/js/js.js"></script>
</html>