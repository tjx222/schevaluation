<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<script src="${ctx }${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<title>缓存管理</title>
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
    <a href="${ctx }/jy/ws/monitor/ehcache" class="btn btn-link pull-right">返回</a>
    <a href="?" class="btn btn-link pull-right">刷新</a>
    <a href="javascript:void(0);" class="btn btn-link pull-right btn-clear">清空</a>
    <table class="table table-bordered">
        <thead>
        <tr class="bold info">
                <th>键值</th>
				<th width="100">命中次数</th>
				<th width="80">大小</th>
				<th width="100">更新时间</th>
				<th width="100">访问时间</th>
				<th width="100">过期时间</th>
				<th width="80">timeToIdle</th>
				<th width="80">timeToLive</th>
				<th width="80">version</th>
				<th width="60">操作</th>
        </tr>
        </thead>
        <c:forEach items="${keys}" var="key">
            <tr>
                <td >${cacheName == 'sqlMappingCache'?jfn:decodeBase64(key) : key}</td>
                <td>${keydetails[key].hitCount}</td>
                <td>${keydetails[key].size}</td>
                <td>${keydetails[key].latestOfCreationAndUpdateTime}</td>
                <td>${keydetails[key].lastAccessTime}</td>
                <td>${keydetails[key].expirationTime}</td>
                <td>${keydetails[key].timeToIdle}</td>
                <td>${keydetails[key].timeToLive}</td>
                <td>${keydetails[key].version}</td>
                <td><a data-key="${key}" href="javascript:;" class="btn btn-link no-padding btn-delete">删除</a></td>
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
            var url = "${ctx}/jy/ws/monitor/ehcache/${cacheName}/" + key + "/delete";
            $.get(url, function(data) {
                td.closest("tr").remove();
            });

    });

    $(".btn-clear").click(function() {
       if(confirm("确认清空整个缓存吗？")){
                var url = "${ctx}/jy/ws/monitor/ehcache/${cacheName}/clear";
                $.get(url, function(data) {
                    window.location.reload();
            });
        }
    });
});
</script>
</body>
</html>