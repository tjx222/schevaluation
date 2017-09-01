<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="module" type="java.lang.String" required="true" description="当前模块，作为基址" %>
<script type="text/javascript">
require.config({
    'baseUrl': '${ctx}${ctxStatic}/modules/${module}'
});
</script>