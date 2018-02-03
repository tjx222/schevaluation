<%@ page language="java" import="com.tmser.schevaluation.task.Task"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<script src="${ctx }${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<title>定时任务管理</title>
<style>
body{font-family: "Microsoft Yahei";margin:0;padding:0;}
a{text-decoration: none;color:#000;}
a:hover{text-decoration:underline}
.panel a{height:30px;line-height:30px;font-size:14px;color:#000;}
table{border-collapse:collapse;border-spacing:0;background-color: #fff;width:100%}
table tr th{text-align:left;border:1px #CACACA solid;background:#fafafa;color:#000;height:20px;font-weight: normal;font-size: 12px;}
table tr th:hover{background:#d9e8fb;}
table tr td{text-align:left;border:1px #CACACA solid;height:21px;color:#000;font-size:12px;}
table tr td a{font-size: 12px;height: 21px;line-height: 21px;}
</style>
</head>
<body>
<div data-table="table" class="panel">
    <table class="table table-bordered">
        <thead>
        <tr class="bold info">
                <th width="100">任务代码</th>
				<th width="160">任务周期</th>
				<th >任务描述</th>
				<th width="60">操作</th>
        </tr>
        </thead>
        <c:forEach items="${tasks}" var="task">
            <tr>
                <td ><%=((Task)pageContext.getAttribute("task")).code() %></td>
                <td><%=((Task)pageContext.getAttribute("task")).cron() %></td>
                <td><%=((Task)pageContext.getAttribute("task")).desc() %></td>
                <td><a data-key="<%=((Task)pageContext.getAttribute("task")).code() %>" href="javascript:;" class="btn btn-link no-padding btn-delete">强制执行</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/><br/>
</div>
<script type="text/javascript">
$(function(){
    $(".btn-delete").click(function() {
            var td = $(this).closest("td");
            var key = $(this).attr("data-key");
            var url = "${ctx}/jy/ws/monitor/task/excute/" + key+"/";
            $.get(url, function(data) {
               alert("执行成功！");
            });

    });

});
</script>
</body>
</html>
