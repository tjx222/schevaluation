<%--
	上传文件组件
--%>
<%@ tag pageEncoding="UTF-8" description="文件上传组件" trimDirectiveWhitespaces="true"  %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="文件上传完成后存储隐藏域名称" %>
<%@ attribute name="relativePath" type="java.lang.String" required="false" description="文件存储相对路径" %>
<%@ attribute name="startElementId" type="java.lang.String" required="false" description="启动文件html元素id,不设置将使用默认上传按钮" %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="文件上传成功后回调" %>
<%@ attribute name="beforeupload" type="java.lang.String" required="false" description="文件上传前回调" %>
<c:if test="${not empty name }" >
<!-- production -->
<c:if test="${empty requestScope._RECORDER_ }">
<%@ include file="/WEB-INF/include/recorder.jspf"%>
 <c:set var="_RECORDER_" value="1" scope="request"></c:set>
 <form id="uploadForm" name="uploadForm" action="${ctx }jy/manage/res/upload"> 
      <input name="relativePath" value="${relativePath}/recorder" type="hidden">
 </form>
</c:if>
<script type="text/javascript">
	$(function () {
		  var CLASS_CONTROLS = "control_panel";
		  var CLASS_RECORDING = "recording";
		  var CLASS_PLAYBACK_READY = "playback_ready";
		  var CLASS_PLAYING = "playing";
		  var CLASS_PLAYBACK_PAUSED = "playback_paused";

		//  Embedding flash object ---------------------------------------------------------------------------------------------
		  swfobject.embedSWF("${ctxStatic }/lib/recorder/mp3recorder.swf", "flashcontent", 24, 24, "11.0.0", "", {'upload_image': '${ctxStatic }/lib/recorder/upload.png'}, {}, {'id': "${name}_recorder_id", 'name': "${name}_recorder"});
		  swfobject.embedSWF("${ctx}${ctxStatic}/lib/recorder/mp3player.swf", "recordercontent", 100, 100, "11.0.0", "", {}, {}, {'id': "${name}_player_id", 'name': "${name}_player"}); 
		//  Handling FWR events ------------------------------------------------------------------------------------------------
		  window.fwr_event_handler = function fwr_event_handler() {
		    $('#status').prepend("<div class=\"recorder-event\">" + arguments[0] + "</div>");
		    var name, $controls;
		    switch (arguments[0]) {
		      case "准备就绪":
		        var width = parseInt(arguments[1]);
		        var height = parseInt(arguments[2]);
		        FWRecorder.uploadFormId = "#uploadForm";
		        FWRecorder.uploadFieldName = "file";
		        FWRecorder.connect("recorderApp", 0);
		        FWRecorder.recorderOriginalWidth = width;
		        FWRecorder.recorderOriginalHeight = height;
		        $('.save_button').css({'width': width, 'height': height});
		        break;

		      case "没有找到麦克风":
		        break;

		      case "请求使用麦克风":
		        recorderEl().addClass("floating");
		        FWRecorder.showPermissionWindow();
		        break;

		      case "麦克风已连接":
		        FWRecorder.isReady = true;
		        $uploadStatus.css({'color': '#000'});
		        break;

		      case "权限窗口关闭":
		        FWRecorder.defaultSize();
		        recorderEl().removeClass("floating");
		        break;

		      case "麦克风已激活":
		       // $('#activity_level').text(arguments[1]);
		        break;

		      case "正在录音":
		        name = arguments[1];
		        $controls = controlsEl(name);
		        FWRecorder.hide();
		        setControlsClass($controls, CLASS_RECORDING);
		        break;

		      case "录音已停止":
		        name = arguments[1];
		        $controls = controlsEl(name);
		        var duration = arguments[2];
		        FWRecorder.show();
		        setControlsClass($controls, CLASS_PLAYBACK_READY);
		        $('#duration').text(duration.toFixed(4) + " 秒");
		        break;  
		      case "正在播放":
		        name = arguments[1];
		        $controls = controlsEl(name);
		        setControlsClass($controls, CLASS_PLAYING);
		        break;

		      case "开始回放":
		        name = arguments[1];
		        var latency = arguments[2];
		        break;

		      case "播放已停止":
		        name = arguments[1];
		        $controls = controlsEl(name);
		        setControlsClass($controls, CLASS_PLAYBACK_READY);
		        break;

		      case "播放已暂停":
		        name = arguments[1];
		        $controls = controlsEl(name);
		        setControlsClass($controls, CLASS_PLAYBACK_PAUSED);
		        break;

		      case "点击上传":
		        FWRecorder.updateForm();
		        break;

		      case "正在上传":
		        name = arguments[1];
		        break;

		      case "上传成功":
		        name = arguments[1];
		        console.log(arguments[2]);
		        var data = $.parseJSON(arguments[2]);
		        if (data) {
		        	  //上传状态
		        	  debugger;
		        }
		        break;
		      case "上传失败":
		        name = arguments[1];
		        var errorMessage = arguments[2];
		        $uploadStatus.css({'color': '#F00'}).text( " 上传失败: " + errorMessage);
		        break;

		      case "上传进度":
		        name = arguments[1];
		        var bytesLoaded = arguments[2];
		        var bytesTotal = arguments[3];
		        $uploadStatus.css({'color': '#000'}).text( " 进度: " + (parseFloat(bytesLoaded) * 100 / parseFloat(bytesTotal))+"%");
		        break;
		    }
		  };

		//  Helper functions ---------------------------------------------------------------------------------------------------
		 

		  function setControlsClass($controls, className) {
		    $controls.attr('class', CLASS_CONTROLS + ' ' + className);
		  }

		  function controlsEl(name) {
		    return $('#recorder-' + name);
		  }

		  function recorderEl() {
		    return $('#recorderApp');
		  }

		  window.microphonePermission = function () {
		    recorderEl().addClass("floating");
		    FWRecorder.showPermissionWindow({permanent: true});
		  };

		  window.configureMicrophone = function () {
		    if (!FWRecorder.isReady) {
		      return;
		    }
		    FWRecorder.configure($('#rate').val(), $('#gain').val(), $('#silenceLevel').val(), $('#silenceTimeout').val());
		    FWRecorder.setUseEchoSuppression($('#useEchoSuppression').is(":checked"));
		    FWRecorder.setLoopBack($('#loopBack').is(":checked"));
		  };
		  
		  window.playRecorder = function (url)
		  {
		        flash = document.getElementById("${name}_player_id");
		        flash.playMP3(url);
		     }

		});


</script>

<div class="container">
    <div id="recorder-audio" class="control_panel idle" style="background-color:white;">
      <button class="record_button" onclick="FWRecorder.record('audio', new Date().getTime()+'.mp3');" title="录音">
        <img src="${ctxStatic }/lib/recorder//record.png" alt="录音"/>
      </button>
      <button class="stop_recording_button" onclick="FWRecorder.stopRecording('audio');" title="停止录音">
        <img src="${ctxStatic }/lib/recorder//stop.png" alt="停止录音"/>
      </button>
      <button class="play_button" onclick="FWRecorder.playBack('audio');" title="播放">
          <img src="${ctxStatic }/lib/recorder//play.png" alt="播放"/>
      </button>
      <button class="pause_playing_button" onclick="FWRecorder.pausePlayBack('audio');" title="暂停播放">
          <img src="${ctxStatic }/lib/recorder//pause.png" alt="暂停播放"/>
      </button>
      <button class="stop_playing_button" onclick="FWRecorder.stopPlayBack();" title="停止播放">
        <img src="${ctxStatic }/lib/recorder//stop.png" alt="停止播放"/>
      </button> 
    <button class="show_settings" onclick="microphonePermission()">
    <img src="${ctxStatic }/lib/recorder//setting.png" alt="麦克风设置"/></button>
    <span id="save_button">
        <span id="flashcontent">
         JavaScript已禁用或Flash Player未安装！
        </span>
        <span id="recordercontent" style="display:none">
			    JavaScript已禁用或Flash Player未安装！
			  </span> 
      </span> 
    </div> 
  </div>

</c:if>

