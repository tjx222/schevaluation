<%--
	上传文件组件
--%>
<%@ tag pageEncoding="UTF-8" description="文件上传组件" trimDirectiveWhitespaces="true"  %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="containerID" type="java.lang.String" required="true" description="包含上传组件容器id" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="文件上传完成后存储隐藏域名称" %>
<%@ attribute name="fileType" type="java.lang.String" required="true" description="文件类型" %>
<%@ attribute name="relativePath" type="java.lang.String" required="false" description="文件存储相对路径" %>
<%@ attribute name="startElementId" type="java.lang.String" required="false" description="启动文件html元素id,不设置将使用默认上传按钮" %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="文件上传成功后回调" %>
<%@ attribute name="beforeupload" type="java.lang.String" required="false" description="文件上传前回调" %>
<%@ attribute name="fileSize" type="java.lang.Integer" required="false" description="单个文件大小，默认10M" %>
<%@ attribute name="isSingle" type="java.lang.Boolean" required="false" description="是否单文件上传" %>
<%@ attribute name="isWebRes" type="java.lang.Boolean" required="false" description="是否web资源文件上传" %>
<%@ attribute name="originFileName" type="java.lang.String" required="false" description="原文件名" %>

<c:if test="${empty isSingle}">
    <c:set var="isSingle" value="true"/>
</c:if>
<c:if test="${empty isWebRes}">
    <c:set var="isWebRes" value="false"/>
</c:if>
<c:if test="${empty originFileName}">
    <c:set var="originFileName" value="originFileName"/>
</c:if>

<c:if test="${empty fileSize}">
    <c:set var="fileSize" value="10"/>
</c:if>

<c:if test= "${isSingle }" >
	<c:set var="url" value="${ctx }jy/manage/res/upload" />
</c:if>
<c:if test= "${!isSingle }" >
	<c:set var="url" value="${ctx }jy/manage/res/upload" />
</c:if>
<c:if test="${not empty name }" >
<!-- production -->
<input type="text" placeholder="请选择文件" value="请选择文件" class="txt_txt" readonly="readonly" name="${originFileName }" id="${originFileName }"/>
<span id="${originFileName }_file_process" class="mes_file_process"></span>
<input id="hiddenFileId" type="hidden" name="${name }" />
<c:if test="${empty requestScope._UPLOADER_ }">
<%@ include file="/WEB-INF/include/upload.jspf"%>
<c:set var="_UPLOADER_" value="1" scope="request"></c:set>
</c:if>
<script type="text/javascript">
if(typeof uploader_${name} == 'undefined'){
var uploader_${name} = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : '${originFileName }',
	container:document.getElementById('${containerID}'),
	multi_selection: ${!isSingle},
	multipart_params:{isWebRes:${isWebRes},relativePath :'${relativePath}'},
	url : '${url}',
	flash_swf_url : _WEB_CONTEXT_+'/${ctxStatic }/lib/plupload/Moxie.swf',
	silverlight_xap_url : _WEB_CONTEXT_+'/${ctxStatic }/lib/plupload/Moxie.xap',
	filters : {
		max_file_size : '${fileSize}mb',
		mime_types: [
			{title : "上传类型", extensions : "${fileType}"},
		]
	},
	init: {
		PostInit: function() {
			document.getElementById('${originFileName }').value = '请选择文件';
			document.getElementById('${originFileName }_file_process').innerHTML = '';
			 <c:if test="${not empty startElementId }">
			document.getElementById('${startElementId}').onclick = function() {
				 <c:if test="${not empty beforeupload }">
				 if (typeof(${beforeupload }) == 'function'){
					 	if(!${beforeupload }(uploader_${name}.files.length)) return false;		 
				 	}
				 </c:if>
				 if(document.getElementById('${containerID}').getAttribute("isedit")=='1'){
					 <c:if test="${not empty callback }">
					 if (typeof(${callback }) == 'function' && uploader_${name}.files.length < 1){
						 	${callback }(false);	 
					 	}
					 </c:if>
				 }
				 uploader_${name}.start();
				return false;
			};
			</c:if>
		},
		BeforeUpload: function(up, file) {
			try{document.getElementById('${originFileName }_file_process').innerHTML='<img src="${ctxStatic }/common/images/loading.gif"/><b></b>';}catch(e){}
		},
		FilesAdded: function(up, files) {
			<c:if test= "${isSingle }" >
			var oldfiles = up.files;
			if(oldfiles.length > 1){
				up.removeFile(oldfiles[0]);
			}
			plupload.each(files, function(file) {
				document.getElementById('${originFileName }').value =  file.name;
			});
			</c:if>
			document.getElementById('${originFileName }_file_process').innerHTML = '';
			<c:if test= "${!isSingle }" >
			uploader_${name}.start();
			</c:if>
		
		},
		UploadProgress: function(up, file) {
			try
			{
				var fp = document.getElementById('${originFileName }_file_process').getElementsByTagName('b');
				if(fp.length > 0){
					fp[0].innerHTML = '<span>' + file.percent + "%</span>";
				}else{
					document.getElementById('${originFileName }_file_process').innerHTML='<img src="${ctxStatic }/common/images/loading.gif"/><b><span>' + file.percent + "%</span></b>";
				}
			}catch(e){}
		},
		FileUploaded:function(up, file, info) {
			var data = eval('(' + info.response + ')');
			if(data.code == 0){
				 document.getElementsByName('${name }')[0].value = data.data;
				 document.getElementById('${originFileName }_file_process').innerHTML = '<span style="color:#26ca28">上传成功</span>';
				 <c:if test="${not empty callback }">
				 if (typeof(${callback }) == 'function'){
					 	${callback }(data);		 
				 	}
				 </c:if>
				 document.getElementById('${originFileName }').value = '请选择文件';
			}else{
				document.getElementById('${originFileName }_file_process').innerHTML = '<span style="color:red">上传失败</span>';
			}
	   },
	   Error: function(up, err) {
		   try{var emsg = err.message;
		       if(err.code == -600){
		    	   emsg = "文件大小不能大于${fileSize}M,请重新上传。";
		       }else if(err.code == -601){
		    	   emsg = "您只能上传${fileType}类型的文件,请重新上传。";
		       }
			   document.getElementById('${originFileName }_file_process').innerHTML = '<span style="color:red">' + emsg+'</span>';}catch(e){}
		}
	}
});
uploader_${name}.init();
}
</script>
</c:if>

