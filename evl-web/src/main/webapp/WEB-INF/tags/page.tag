<%--
 分页格式
   首页 <<   1   2   3   4   5   6   7   8   9   10  11>  >> 尾页
   首页 <<   1   2   3   4   5   6   7   8   9   ... 11  12 >  >> 尾页
   首页 <<   1   2  ...  4   5   6   7   8   9   10 ... 12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ... 13  14 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ...   21  22 >  >> 尾页
--%>
<%@tag pageEncoding="UTF-8" description="分页" %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="data" type="com.tmser.schevaluation.common.page.PageList" required="true" description="分页数据" %>
<%@ attribute name="url" type="java.lang.String" required="false" description="分页数据源地址" %>
<%@ attribute name="style" type="java.lang.String" required="false" description="风格" %>
<%@ attribute name="isAjax" type="java.lang.Boolean" required="false" description="是否ajax 分页" %>
<%@ attribute name="dataType" type="java.lang.String" required="false" description="ajax 数据格式" %>
<%@ attribute name="isJsonp" type="java.lang.Boolean" required="false" description="是否jsonp" %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="回调地址，ajax 分页时有效" %>
<%@ attribute name="views" type="java.lang.Integer" required="false" description="显示页数，默认是10" %>
	<c:if test="${empty style}">
	    <c:set var="style" value="default"/>
	</c:if>
	<c:if test="${not empty data }">
	<c:choose>
		<c:when test="${style == 'default'}">
	<%
	if(data.getTotalPages() > 1){
			int sv = views == null ? 10:views.intValue();
			isAjax = isAjax == null ? false:isAjax;
			isJsonp = isJsonp == null ? false:isJsonp;
		    int  pages = data.getCurrentPage();//获取当前页码
		    int pageCount = data.getTotalPages(); //获取总页数
		    if (pageCount < pages) {
		        pages = pageCount;//如果分页变量大总页数，则将分页变量计为总页数
		    }
		    
		    if (pages < 1) {
		        pages = 1;//如果分页变量小于１,则将分页变量设为１
		    }
		    
		    
		    int listbegin = pages - sv/2;//从第几页开始显示分页信息
	        int listend = pages +(sv - sv/2 - 1);//分页信息显示到第几页
		    
		    if (listend > pageCount) {
		    	listbegin = listbegin -(listend-pageCount);
		        listend = pageCount;
		    }
		    if (listbegin < 1) {
		    	if(listend < pageCount){
		    		int cx = pageCount- listend;
		    		int lx = 0 - listbegin;
		    		listend = listend + ((cx > lx) ? lx : cx);
		    	}
		    	listbegin = 1;
		    }
	%>
		<div class="pages">
		<ol>
		<%if (pages > 1) { %>
			<li id="on_one_page">
			<strong onclick="javascript:<%=isAjax?"turnPageAjax":"turnPage"%>(this,'<%=url%>',<%=data.previousPage()%><%=isAjax?","+callback+(isJsonp?",true":",false"):""%>)">
			上一页</strong></li>
		<%}
		for (int i = listbegin; i <= listend; i++) {
			if(i == pages){
		%>
		    <li><strong class="pages_act"><%=i %></strong></li>
		<%}else{%>
			<li><strong onclick="javascript:<%=isAjax?"turnPageAjax":"turnPage"%>(this,'<%=url%>',<%=i%><%=isAjax?","+callback+(isJsonp?",true":",false"):""%>)"><%=i %></strong></li>
		<%}
		}
		if(pages < pageCount) {
		%>
		  <li id="the_next_page"><strong onclick="javascript:<%=isAjax?"turnPageAjax":"turnPage"%>(this,'<%=url%>',<%=data.nextPage()%><%=isAjax?","+callback+(isJsonp?",true":",false"):""%>)">下一页</strong></li>
		<%}%>
		<li style="background:none;">
			   <span>转到:<input type="text" class="wid" id="go_page">页</span>
		</li>
		<li id="go"><strong onclick="javascript:<%=isAjax?"gotoPageAjax":"gotoPage"%>(this,'<%=url%>',<%=data.getTotalPages()%><%=isAjax?","+callback+(isJsonp?",true":",false"):""%>)">GO</strong></li>
		</ol>
	</div>
	<%}%>
	</c:when>
	</c:choose>
	</c:if>

<script type="text/javascript">
$("#go_page").keypress(function(e){
	if (e && e.keyCode == 13) {
    	$("#go").find("strong").trigger("click");
    	return false;
    }
});
</script>
