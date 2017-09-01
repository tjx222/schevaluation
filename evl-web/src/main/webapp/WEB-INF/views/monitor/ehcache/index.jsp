<%@ page import="org.springframework.cache.CacheManager" %>
<%@ page import="net.sf.ehcache.Statistics" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>缓存管理</title>
<style>
body{font-family: "Microsoft Yahei";margin:0;padding:0;}
table{border-collapse:collapse;border-spacing:0;background-color: #fff; width:100%}
table tr th{text-align:left;border:1px #CACACA solid;background:#fafafa;height:20px;font-weight: normal;font-size: 12px;}
table tr th:hover{background:#d9e8fb;}
a{text-decoration: none;color:#000;}
a:hover{text-decoration:underline}
table tr td{text-align:left;border:1px #CACACA solid;height:21px;color:#353535;font-size:12px;}
table tr td a{height: 21px;line-height:21px;}
</style>
</head>
<body>
<div data-table="table" class="panel">
    <table class="table table-bordered">
   		 <tr>
			<th ><a href="${ctx}/jy/ws/monitor/ehcache">缓存名称</a></th>
			<th style="width:7%;"><a href="${ctx}/jy/ws/monitor/ehcache?sort=hitPercent">总命中率</a></th>
			<th style="width:7%;"><a href="${ctx}/jy/ws/monitor/ehcache?sort=hitCount">命中次数</a></th>
			<th style="width:7%;"><a href="${ctx}/jy/ws/monitor/ehcache?sort=misses">失效次数</a></th>
			<th style="width:10%;"><a href="${ctx}/jy/ws/monitor/ehcache?sort=objectCount">缓存总对象数</a></th>
			<th style="width:20%;">最后一秒查询完成的执行数</th>
			<th style="width:20%;">最后一次采样的平均执行时间</th>
			<th style="width:15%;">平均获取时间</th>
		</tr>
        <tbody>
        <%
            CacheManager cacheManager = (CacheManager) request.getAttribute("cacheManager");
        	Collection<String> cacheNames = cacheManager.getCacheNames();
            String[] names = sort(cacheNames, cacheManager, request.getParameter("sort"));

            for (String cacheName : names) {
                pageContext.setAttribute("cacheName", cacheName);
                pageContext.setAttribute("cache", cacheManager.getCache(cacheName).getNativeCache());
        %>
		
        <c:set var="totalCount"
               value="${cache.statistics.cacheHits + cache.statistics.cacheMisses}"/>
        <c:set var="totalCount" value="${totalCount > 0 ? totalCount : 1}"/>
        <c:set var="cacheHitPercent" value="${cache.statistics.cacheHits * 1.0 / (totalCount)}"/>

		
        <tr class="bold info">
            <td style="text-align:left;">
            <a href="${ctx}/jy/ws/monitor/ehcache/${cacheName}/details">${cacheName}</a>
            </td>
            <td><fmt:formatNumber type="percent" value="${cacheHitPercent}" /> </td>
            <td>${cache.statistics.cacheHits}</td>
            <td>${cache.statistics.cacheMisses}</td>
            <td>${cache.statistics.objectCount}</td>
            <td>${cache.statistics.searchesPerSecond}</td>
            <td><fmt:formatNumber type="number" value="${cache.statistics.averageSearchTime}" />毫秒</td>
            <td><fmt:formatNumber type="number" value="${cache.statistics.averageGetTime}" />毫秒</td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
    <br/><br/>
</div>
</body>
</html>
<%!
        private String[] sort(final Collection<String> cacheNames, final CacheManager cacheManager, final String sort) {
        String[] a = new String[cacheNames.size()];    
		Arrays.sort(cacheNames.toArray(a), new Comparator<String>() {
                public int compare(String n1, String n2) {
                    Statistics s1 = ((Cache)cacheManager.getCache(n1).getNativeCache()).getStatistics();
                    Statistics s2 = ((Cache)cacheManager.getCache(n2).getNativeCache()).getStatistics();
                    if("hitPercent".equals(sort)) {
                        double n1HitPercent = 1.0 * s1.getCacheHits() / Math.max(s1.getCacheHits() + s1.getCacheMisses(), 1);
                        double n2HitPercent = 1.0 * s2.getCacheHits() / Math.max(s2.getCacheHits() + s2.getCacheMisses(), 1);
                        return -Double.compare(n1HitPercent, n2HitPercent);
                    }else if("hitCount".equals(sort)) {
                        return -Long.valueOf(s1.getCacheHits()).compareTo(Long.valueOf(s2.getCacheHits()));
                    }else if("misses".equals(sort)) {
                        return -Long.valueOf(s1.getCacheMisses()).compareTo(Long.valueOf(s2.getCacheMisses()));
                    } else if("objectCount".equals(sort)) {
                        return -Long.valueOf(s1.getObjectCount()).compareTo(Long.valueOf(s2.getObjectCount()));
                    }
                    return -n1.compareTo(n2);
                }

            });
		return a;
        }
%>