(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define(["jquery",'plupload'], factory);
	} else {
		factory( jQuery );
	}
})(function($, plupload) {
	var _LOADER_LIST_ = {};
	/**
	 * 对象定义
	 */
	function MyUploader(fileDoms, option) {
		option['browse_button'] = fileDoms[0];
		option['jq'] = fileDoms;
		var init = {};
		$.extend(init,MyUploader._defaultOption.init, option.init);
		option['init']=init;
		
		//文件过滤
		option['filters']={};
		var filters=option['filters'];
		//文件类型
		if(option.type){
			filters['mime_types']=[{title : "上传类型", extensions : option.type}];
		}
		//文件大小
		if(option.maxSize){
			filters['max_file_size']=option.maxSize+'mb';
		}
		
		var multipart_params ={};
		option['multipart_params']=multipart_params;
		if(option.isWebRes){
			multipart_params['isWebRes']=option.isWebRes;
		}else{
			multipart_params['isWebRes']=false;
		}
		if(option.relativePath){
			multipart_params['relativePath']=option.relativePath;
		}else{
			multipart_params['relativePath']='';
		}
		
		var o = {};
		$.extend(o,MyUploader._defaultOption, option);
		var up = new plupload.Uploader(o);
		up.init();
//		var upload = {};
//		$.extend(upload,MyUploader,up);
//		upload.init();
//		upload.option = option;
		return up;
	}

	/**
	 * 开始上传 successFunc 成功回调方法，会覆盖默认的成功回调方法 errorFunc 失败回调方法，会覆盖默认的失败回调方法
	 */
	MyUploader.upload = function(successFunc, errorFunc) {
		var option = this.option;
		if(!option.upload){
			option.upload={};
		}
		option.upload.success=successFunc;
		option.upload.error=errorFunc;
		this.start();
	},
	/**
	 * 重置，清除上传消息
	 */
	MyUploader.reset = function() {
		var num = this.files.length;
		if (this.option.jq.is('input')
				&& this.option.jq.attr('type') != "button") {
			this.option.jq.val('');
		}
		if(this.option.messageShow){
			this.option.messageShow.html('');
		}
	}

	/**
	 * 默认配置
	 */
	MyUploader._defaultOption = {
		runtimes : 'html5,flash,silverlight,html4',
		flash_swf_url : _WEB_CONTEXT_ + '/static/lib/plupload/Moxie.swf',
		silverlight_xap_url : _WEB_CONTEXT_ + '/static/lib/plupload/Moxie.xap',
		url:_WEB_CONTEXT_+'/jy/manage/res/upload',
		multi_selection : false,// 默认单选
		uploadingStyle : 'circle',// 默认上传时,转圈提示
		messageShow:null,// jquery选择元素，显示上传消息，包括：上传中、错误、成功等
		error:null,// 上传错误回调方法，参数：emsg,错误描述;err,plupload组件内部错误描述
		success:null,// 上传成功回调方法,参数：object。参数对象结构为{fileName：'',resId:''},文件名称，上传资源id
		process:null,// 文件上传过程回调方法，参数：percent，文件上传百分比
		maxSize:null,//文件最大限制
		type:null,//文件类型
		init: {
			FilesAdded: function (up, files) {
				document.getElementById('uploadFile').value =  files[0].name;
				/*if (this.option.jq.is('input')
					&& this.option.jq.attr('type') != "button") {
					this.option.jq.val(files[0].name);
				}*/
			},
			Error: function (up, err) {
				var option = up.option;
					var emsg = err.message;
					if(err.code == -600){
				    	   emsg = "文件大小不能大于"+up.option.maxSize+"MB,请重新上传。";
				       }else if(err.code == -601){
				    	   emsg = "您只能上传"+up.option.type+"类型的文件,请重新上传。";
				       }
					if(option.error){
						option.error(emsg,err);
					}
					if(option.messageShow){
						option.messageShow.html(emsg);
					}
					
			},
			FileUploaded: function(up, file, response) {
				response=response.response;
				var option = up.option;
				var data = eval('(' + response + ')');
				if(data.code==0){
					if(option.upload&&option.upload.success){
						option.upload.success({'fileName':file.name,'resId':data.data});
					}
					if(option.success){
						option.success({'fileName':file.name,'resId':data.data});
					}
					// 显示上传成功信息
					if(option.messageShow){
						option.messageShow.html('<span style="color:#26ca28">上传成功</span>');
					}
					
				}else{
					if(option.upload&&option.upload.error){
						option.upload.error(data.msg);
					}
					if(option.error){
						option.error(data.msg);
					}
					// 显示上传失败信息
					if(option.messageShow){
						option.messageShow.html('<span style="color:red">上传失败</span>');
					}
				}
				
			},
			// 上传文件过程
			UploadProgress: function(up, file) {
				var option = up.option;
				try
				{	
					// 显示上传过程
					if(option.messageShow){
						var messageShow = option.messageShow;
						var bJq = messageShow('b');
						if(bJq.length > 0){
							bJq.html('<span>' + file.percent + "%</span>");
						}else{
							bJq.html('<img src="'+_STATIC_BASEPATH_+'/common/images/loading.gif"/><b><span>' + file.percent + "%</span></b>");
						}
					}
					// 过程回调
					if(option.process){
						option.process(file.percent);
					}
				}catch(e){}
			}
		}
	}

	/**
	 * 注册插件
	 */
	$.fn.uploader = function(methodOrOption) {
		    var loaderName= "UPLOADER_"+$(this).attr('id');
			// 如果是字符则表示方法调用
			if (typeof methodOrOption == 'string') {
				var methodName = methodOrOption;
				var loader = _LOADER_LIST_[loaderName];
				if (loader == null) {
					return;
				}
				// 如果是函数名调用函数
				if(typeof loader[methodName]==='function'){
					// 调用方法的参数
					var args = new Array();
					if (arguments.length > 1) {
						for (var i = 1; i < arguments.length; i++) {
							args.push(arguments[i]);
						}
					}
					// 调用方法
					return loader[methodName].apply(loader, args);
				}else{// 否则返回对应属性
					return loader[methodName];
				}
			} else if (typeof methodOrOption == 'object') {// 如果时对象则初始化
				// 如果已经初始化，则无需再次初始化
				if(_LOADER_LIST_[loaderName]){
					return _LOADER_LIST_[loaderName];
				}
				var option = methodOrOption;
				var loader = new MyUploader($(this), option);
				_LOADER_LIST_[loaderName] = loader;
				return loader;
			}else{
				return $(this).data('uploder');
			}
	}
});

