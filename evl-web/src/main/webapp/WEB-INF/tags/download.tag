<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="resid" type="java.lang.String" required="true" description="要下载资源id" %>
<%@ attribute name="type" type="java.lang.Boolean" required="false" description="要下载资源id" %>
<%@ attribute name="filename" type="java.lang.String" required="false" description="自定义下载文件名称" %>
<c:if test= "${jfn:cfgv('resSync.switch','off')=='on'}">
	<jy:ds var="resource" key="${resid}" className="com.tmser.schevaluation.manage.resources.service.impl.ResourcesServiceImpl"></jy:ds>
	${jfn:cfgv('storageServer.host','')}/resource/${resource.rmsResId}/${not empty type ? 'preview' :'download' }
</c:if>
<c:if test= "${jfn:cfgv('resSync.switch','off')=='off'}">
	jy/manage/res/download/${resid}?filename=<ui:sout value="${filename}" escapeXml="true" encodingURL="true"></ui:sout>
</c:if>