<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="modelName" type="java.lang.String" required="false" description="移动端title"%>
<div class="jyyl_top_logo"> 
			<a href="">
				<div class="jyyl_logo ${sessionScope._sess_flag_ != 'hidebuttom'?'jy':'jx' }"></div>
			</a>
	<div class="jyyl_logo_right">
		<ul>
			<li class="jyyl_logo_right_li jyyl_avatar">
				<ui:photo src="${_CURRENT_USER_.photo }" width="30" height="30" />
				<span class="jyyl_head_mask"></span>
			</li>
			<li class="jyyl_logo_right_li" style="position:relative;">
				<b class="jyyl_name_news">${_CURRENT_USER_.name }</b>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li"><a href="${ctx}/jy/logout"><img src="${ctxStatic }/modules/evl/images/exit.png" alt="">退出</a></li>
		</ul>
	</div>
</div>
