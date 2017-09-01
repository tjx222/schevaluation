/**
 * 编辑器插件,封装kindeditor.
 * 使用方法
 * <pre>
 *  $("#web_editor").editor(webEditorOptions);
 * 可以使用kindeditor 所有api
 * 可设置的 webEditorOptions 有：
 *  height： 编辑器高度
 *  width: 编辑器宽
 *  style: 风格  1 默认模式, 0 空白模式，不包含工具栏, 2 简单模式，包含少量工具栏
 *  afterFocus： 获取焦点后回调
 *  afterBlur：失去焦点后回调
 * </pre>
 * @param $
 */
(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define(["jquery",'kindeditor','kindeditorLang'], factory);
	} else {
		factory( jQuery );
	}
}(function($) {
	$.Editor = function(elm, options) {
		this.$elm = $(elm);
		this.opts = options;
		return this.init();
	};
	$.extend($.fn,{
		editor:function(options) {
			var editors = [];
			this.each(function() {
				editors.push(new $.Editor(this, $.extend({}, $.Editor.defaults, options)));
			});
		return $(editors); 
	   }
	});
	
	$.extend($.Editor,{
		defaults : {
			style:1,
			resizeType: 0
	  },
	  prototype : {
				init: function() {
					if(this.$elm.length < 1){
							return null;
					}
					var tools = [];
					if (this.opts.style == 1) {
						tools = ['fontname', 'fontsize',
						         '|','forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'subscript','superscript','removeformat','|','plainpaste','wordpaste',
								 '|','emoticons','link','unlink','|','selectall','justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist','insertunorderedlist',
								 "|","fullscreen"];
					}else if(this.opts.style == 2){
						tools = ['fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold', 'italic','emoticons'];
					}
					var $this = this;
					this.opts.afterCreate = function(){
						if(typeof $this.opts.initContent === 'string'){
							this.html($this.opts.initContent);
						}
						if($this.opts.style == 0){
							$("div.ke-toolbar",this.container).css("display","none");
						}
						if($this.opts.resizeType == 0){
							this.statusbar.css("display","none");
						}
					};
				   if(this.opts.items){
					   this.opts.items=[];
				   }
				   var webOptions =$.extend({}, this.opts,{pasteType:1,newlineTag:'br',minWidth:150,minHeight:60,allowPreviewEmoticons : false,allowImageUpload: false,items : tools});
				   return KindEditor.create(this.$elm, webOptions);
				}
			},
	  setDefaults: function( settings ) {
			$.extend($.editor.defaults, settings);
	  }
	});
	
	return {
	fn:function(jq){
		jq.fn.editor = $.fn.editor;
	}
	};
	
}
));

/**
 * 解决有些机器初次加载不到“default”样式的问题
 */
var fileref=document.createElement("link") ;  
fileref.rel = "stylesheet";  
fileref.type = "text/css";  
fileref.href = "static/lib/kindeditor/themes/default/default.css"; 
fileref.media = "all";
var headobj = document.getElementsByTagName('head')[0];   
headobj.appendChild(fileref);  
