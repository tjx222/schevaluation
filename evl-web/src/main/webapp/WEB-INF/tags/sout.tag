<%--
	字符串长度截取，中文字符算2个字宽
--%>
<%@ tag import="com.tmser.schevaluation.utils.StringUtils" %>
<%@ tag pageEncoding="UTF-8" description="字符串格式化" trimDirectiveWhitespaces="true"  %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="要处理的字符串" %>
<%@ attribute name="length" type="java.lang.Integer" required="false" description="长度" %>
<%@ attribute name="needEllipsis" type="java.lang.Boolean" required="false" description="是否显示省略号" %>
<%@ attribute name="defaultValue" type="java.lang.String" required="false" description="默认字符串" %>
<%@ attribute name="escapeXml" type="java.lang.Boolean" required="false" description="是否显示html" %>
<%@ attribute name="encodingURL" type="java.lang.Boolean" required="false" description="是否强制URL编码" %>

<c:if test="${empty defaultValue}">
    <c:set var="defaultValue" value=""/>
</c:if>

<c:if test="${empty escapeXml}">
    <c:set var="escapeXml" value="false"/>
</c:if>
<c:out value="<%=StringUtils.abbr(value, length == null ? Integer.MAX_VALUE : length, needEllipsis == null ? false:needEllipsis,encodingURL==null?false:encodingURL) %>" escapeXml="${escapeXml}" default="${defaultValue }"/>
