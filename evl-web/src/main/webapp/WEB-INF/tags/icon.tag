<%@tag pageEncoding="UTF-8"%>
<%--  
icon 、iconId 或 iconIdentity 属性至少有一个 
宽和高只对图片类型有效
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="width" type="java.lang.Integer" required="false" description="图标宽id" %>
<%@ attribute name="height" type="java.lang.Integer" required="false" description="图标高" %>
<%@ attribute name="ext" type="java.lang.String" required="false" description="文件后缀" %>
<%@ attribute name="title" type="java.lang.String" required="false" description="title" %>
<c:if test="${empty icon }">
	<c:if test="${not empty ext}">
        <img src="${ctx }static/common/icon/base/${fn:toLowerCase(ext)}.png" ${not empty width ?'width="':''}${width }${not empty width ?'"':''} ${not empty height ?'height="':''}${height }${not empty height ?'"':''} title="${title }"/>
    </c:if>
</c:if>
