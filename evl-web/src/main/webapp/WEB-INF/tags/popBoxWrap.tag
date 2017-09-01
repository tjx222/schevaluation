<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@attribute name="id" type="java.lang.String" required="false" description="弹出层id"%>
<%@attribute name="iframeId" type="java.lang.String" required="false" description="弹出层内容为iframe时的iframeid"%>
<%@attribute name="style" type="java.lang.String" required="false" %>
<%@attribute name="current" type="java.lang.Integer" required="false" description="当前层" %>
<%@attribute name="items" type="java.lang.String" required="true" %>
<%@attribute name="closeCallBack" type="java.lang.String" required="false" description="关闭窗口回调方法"  %>
<c:if test="${empty items}">
 <c:set var="items" value=""/>
</c:if>
<c:set var="itemArr" value="${fn:split(items, ',')}"/>
<div class="dlog" style="${style}" id="${id }">
	<div class="dlog_wrap" >
		<div class="dlog-top">
			<ul>
			<c:forEach items="${pageScope.itemArr }" var="item" varStatus="st">
				<li class="${fn:length(itemArr) > 1 && st.index == 0 ? 'white':'' }" style="${fn:length(itemArr) > 1 ? 'margin-top: 6px;margin-left: 10px;' : ''}">${item }</li>
			</c:forEach>
			</ul>
			<img src="${ctxStatic }/modules/school/tch/images/close.png" alt="" class="close">
		</div>
		<div class="dlog-body">
			<jsp:doBody/>
	    </div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){$('#${id} img.close').click(function (){
		<c:if test="${not empty iframeId}">
		$("#${iframeId}").attr("src",/^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank');
		</c:if>
		$.unblockUI();
		$(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
		<c:if test="${not empty closeCallBack}">
			if (typeof(${closeCallBack }) == 'function'){
			 	${closeCallBack }();		 
		 	}
		</c:if>
	});
	});
	</script>
</div>