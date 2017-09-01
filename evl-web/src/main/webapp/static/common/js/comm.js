function isPositiveNum(s){//是否为正整数  
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s)  
} 

function gotoPage(obj,url,totalPages) {
		var form = $(obj).parents("form")[0];
		var gopage = new Number($(form).find("#go_page").val());// 要跳往的页数
		if (!isNaN(gopage) && gopage != "" &&isPositiveNum(gopage)) {
			if (gopage > 0) {
				if (gopage > totalPages) {
					alert("您输入的页数过大.");
					return;
				} else {
					$(form).find(".currentPage").val(gopage);
					var form = $(obj).parents("form");
					form.attr("action",url);
					form.submit();
				}
			} else {
				alert("输入的跳转页面错误不存在");
				return;
			}
		} else {
			alert("请输入正确的页数！");
			return;
		}
}

function gotoPageAjax(obj,url,totalPages,pcallback,jsonp) {
		var form = $(obj).parents("form")[0];
		var dtype = "html";
		if(jsonp){
			dtype = "jsonp";
		}
		var gopage = new Number($(form).find("#go_page").val());// 要跳往的页数
		if (!isNaN(gopage) && gopage != "" && isPositiveNum(gopage)) {
			if (gopage > 0) {
				if (gopage > totalPages) {
					alert("您输入的页数过大.");
					return;
				} else {
					$(form).find(".currentPage").val(gopage);
					$.ajax({url : url,
						data : $(form).serialize(),
						dataType : dtype,
						type : 'post',
						cache : false,
						success : function(data, textStatus,
								XMLHttpRequest) {
								try {
									pcallback(data);
								} catch (e) {
									alert(e.message);
								}
						},
						error: function(XMLHttpRequest, textStatus,
								errorThrown) {
							if (XMLHttpRequest.status == 500) {
								alert(XMLHttpRequest.responseText);
							}
						}
					 });
				}
			} else {
				alert("输入的跳转页面错误不存在");
				return;
			}
		} else {
			alert("请输入正确的页数！");
			return;
		}
}

function turnPage(obj,url,index){
	var form = $(obj).parents("form");
	$(form).find(".currentPage").val(index);
	form.attr("action",url);
	form.submit();
}

function turnPageAjax(obj,url,index,pcallback,jsonp){
	var form = $(obj).parents("form")[0];
	$(form).find(".currentPage").val(index);
	var dtype = "html";
	if(jsonp){
		dtype = "jsonp";
	}
	$.ajax({url : url,
		data : $(form).serialize(),
		dataType : dtype,
		type : 'post',
		cache : false,
		success : function(data, textStatus,
				XMLHttpRequest) {
				try {
					pcallback(data);
				} catch (e) {
					alert(e.message);
				}
		},
		error: function(XMLHttpRequest, textStatus,
				errorThrown) {
			if (XMLHttpRequest.status == 500) {
				alert(XMLHttpRequest.responseText);
			}
		}
	 });
}

/**
 * 移动端瀑布流
 * @param obj
 * @param url
 * @param index
 * @param pcallback
 * @param jsonp
 */
function turnPageAjax_m(obj,url,pcallback,dt){
	var totalPage = $(obj).attr("totalPage");
	var nextPage = $(obj).attr("nextPage");
	var form = $(obj).parents("form")[0];
	if(!dt){
		dt="json";
	}
	$(form).find(".currentPage").val(nextPage);
	$.ajax({url : url,
		data : $(form).serialize(),
		dataType : dt,
		type : 'post',
		cache : false,
		success : function(data, textStatus,
				XMLHttpRequest) {
				try {
					$(obj).attr("nextPage", parseInt(nextPage)+1);
					pcallback(data);
				} catch (e) {
					alert(e.message);
				}
		},
		error: function(XMLHttpRequest, textStatus,
				errorThrown) {
			if (XMLHttpRequest.status == 500) {
				alert(XMLHttpRequest.responseText);
			}
		}
	 });
	if(parseInt(nextPage)+1>totalPage){
		$(obj).removeAttr("onclick");
		$(obj).html("没有更多");
	}
}

/**
 * 自动增长函数
 * @param obj iframe id 或iframe 本身
 * @param force 是否允许父级页面增长，如允许父级页面也必须引入comm.js
 * @param defaultHeight 默认高度
 */
function setCwinHeight(obj,force,defaultHeight) {
	var cwin = obj;
	if(!defaultHeight){
		defaultHeight = 0;
	}
	if (document.getElementById) {
		if (typeof obj == "string")
			cwin = document.getElementById(obj);
		if (cwin && !window.opera) {
			if (cwin.Document && cwin.Document.body
					&& cwin.Document.body.scrollHeight)
				if(cwin.Document.body.scrollHeight  < defaultHeight)
				   cwin.height = defaultHeight;// IE
				else{
					  cwin.height = cwin.Document.body.scrollHeight + 70;
				}
			else if (cwin.contentDocument && cwin.contentDocument.body // FF
					&& cwin.contentDocument.body.offsetHeight)
				if(cwin.contentDocument.body.offsetHeight  < defaultHeight)
					cwin.height = defaultHeight;
				else{
					cwin.height = cwin.contentDocument.body.offsetHeight + 70;
				}
		} else if (cwin && cwin.contentWindow
				&& cwin.contentWindow.document.body.scrollHeight)
			if(cwin.contentWindow.document.body.scrollHeight+defaultHeight < defaultHeight)
				cwin.height = defaultHeight;// Opera
			else{
				cwin.height = cwin.contentWindow.document.body.scrollHeight;
			}
	}
	if (force && window.parent && window.parent.document != window.document) {
		if(typeof(window.parent.setCwinHeight)=="function"){
			window.parent.setCwinHeight(window.parent,true);
		}
	}
}

// 获取项目根路径+虚拟路径// 如Http://localhost:8080/jy/
function getRootPath(context) {
	var rootpath = "";
	try{
		// 获取当前网址
		var strFullPath = window.document.location.href;
		var strPath = window.document.location.pathname;
		// 获取主机地址，
		var prePath = strFullPath.substring(0, strFullPath.indexOf(strPath));
		if(!context){context = _WEB_CONTEXT_;}
		rootpath = (prePath + context+"/");
	}catch(e){
	};
	return rootpath;
}

/**
 * swfupload 上传路径加上session id
 * @param url 相对路径
 * @returns {String}
 */
function uploadUrl(url,sessionId){
	return getRootPath()+url+";jsessionid="+sessionId;
}


/**
 * 获取url参数
 * @return {}
 */
 function getRequest() {
	   var url = location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
	}
 /*
  *限制字符串长度
  * str:原字符串，strLength:允许的长度(如果有‘...’则是包含其之后) needEllipsis:是否如果原字节长度超过了规定的则显示‘...’
  */
 function cutStr(str,strLength,needEllipsis){
 	var re=/^[\u4e00-\u9fa5]+$/;
 	var strNew = "";
 	var thelength = 0;
 	for(var i=0;i<str.length;i++){
 		var thechar = str.charAt(i);
 		if (re.test(thechar)){
 			thelength = thelength+2;
 		}else{
 			thelength = thelength+1;
 		}
 	}
 	if(thelength>strLength){
 		thelength = 0;
 		if(needEllipsis){//需要...
 			for(var i=0;i<str.length;i++){
 				var thechar = str.charAt(i);
 				if (re.test(thechar)){
 					thelength = thelength+2;
 				}else{
 					thelength = thelength+1;
 				}
 				if(thelength<=strLength-2){
 					strNew = strNew+thechar;
 				}else{
 					return strNew+"...";
 				}
 			}
 		}else{
 			for(var i=0;i<str.length;i++){
 				var thechar = str.charAt(i);
 				if (re.test(thechar)){
 					thelength = thelength+2;
 				}else{
 					thelength = thelength+1;
 				}
 				if(thelength<=strLength){
 					strNew = strNew+thechar;
 				}else{
 					return strNew;
 				}
 			}
 		}
 	}else{
 		return str;
 	}
 }

 //资源文档浏览
 function scanResFile(resId,ops){
 	var url = _WEB_CONTEXT_+"/jy/scanResFile?resId="+resId;
 	if(!ops){
 		window.open(url,"_blank");
 	}else{
 		window.open(url,"_blank",ops);
 	}
 	
 }
//资源文档浏览
 function scanResFile_m(resId){
 	var url = _WEB_CONTEXT_+"/jy/scanResFile?site_preference=mobile&resId="+resId;
	window.open(url,"_self");
 	
 }
 if(!Date.prototype.toISOString){
	    Date.prototype.toISOString = function() {
	    function pad(n) { return n < 10 ? '0' + n : n }
	    return this.getUTCFullYear() + '-'
	    + pad(this.getUTCMonth() + 1) + '-'
	    + pad(this.getUTCDate()) + 'T'
	    + pad(this.getUTCHours()) + ':'
	    + pad(this.getUTCMinutes()) + ':'
	    + pad(this.getUTCSeconds()) + '.'
	    + pad(this.getUTCMilliseconds()) + 'Z';
}
}
