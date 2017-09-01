<%--
	是否支持在线预览
--%>
<%@ tag import="com.mainbo.jy.utils.StringUtils" %>
<%@ tag pageEncoding="UTF-8" description="在线预览" trimDirectiveWhitespaces="true"  %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="ext" type="java.lang.String" required="true" description="后缀" %>
<c:if test="${jfn:canView(ext)}">
    <jsp:doBody/>
</c:if>

