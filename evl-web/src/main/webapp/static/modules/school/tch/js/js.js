define(["require","jquery",'jp/jquery-ui.min','jp/jquery.dragsort.min','jp/jquery.blockui.min'], function (require) {
	var jq=require("jquery");
	require("jp/jquery.dragsort.min");
	var oldsort = "";
	var oldsortmenus = [];
	function hideScroll(){
		jq(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	}

	function sortMenu(){
		var msort = [];
		jq("ul#tuozhuai dl").each(function(){
			msort.push(jq(this).attr("data-mid"));
		});
		var newsort = msort.join();
		if(oldsort != newsort){
			jq.getJSON(_WEB_CONTEXT_+"/jy/uc/sortMenu?mids="+newsort+"&"+Math.random(),function(data){
				  if(data.code == 0){oldsort = newsort;}
			});
		}
	}
	
	jq(function(){
		init();
		isHaveHistoryYear();//是否有历史学年按钮
	});
	var _CHILDMAP = [];
	window.opencal = function(){
		jq("#schedule_pop").dialog({width:800,height:600}); 
		jq("#schedule").attr("src","jy/schedule/index");
	}
	window.curriculum = function(){
		jq("#curriculum_pop").dialog({width:702,height:530}); 
		jq("#curriculum").attr("src","jy/curriculum");
	}
	
	function init() {
		jq('.table_curriculum1 table tbody tr').each(function (){
			jq(this).find('td').eq(1).css("border-left","1px #C8C8C8 solid");
			jq(this).find('th').eq(1).css("border-left","1px #C8C8C8 solid")
		});
		jq("ul#tuozhuai dl").each(function(){
			oldsortmenus.push(jq(this).attr("data-mid"));
		});
		oldsort = oldsortmenus.join();
		jq("#tuozhuai").dragsort({dragSelector: "img", dragBetween: true, dragEnd: sortMenu});
		
		jq('.curriculum').click(function (){
			window.curriculum();
		});
		jq('#curriculum_table').on("click", "span.isok", function(){
			window.curriculum();
		});
		jq('.schedule').click(function (){
			window.opencal();
		});
		jq('.set').click(function (){
			jq('#user_pop .dlog-top ul li').removeClass("white").eq(0).addClass("white");
			jq("#modify").attr("src","jy/uc/modify?"+ Math.random());
			jq.blockUI({ message: jq('#user_pop'),css:{width: '702px',height: '530px',top:'10%',left:'50%',marginLeft:'-351px'}});
			hideScroll();
		});
		jq('.pro_1').click(function () {
			//jq.blockUI({ message: jq('#user_pop'),css:{width: '423px',height: '468px',top:'20%',left:'50%',marginLeft:'-212px'}});
			jq('#user_pop .dlog-top ul li').removeClass("white").eq(0).addClass("white");
			jq("#modify").attr("src","jy/uc/modify?"+ Math.random());
			jq.blockUI({ message: jq('#user_pop'),css:{width: '702px',height: '530px',top:'10%',left:'50%',marginLeft:'-351px'}});
			hideScroll();
		});
		jq('.realname').click(function () {
			//jq.blockUI({ message: jq('#user_pop'),css:{width: '423px',height: '468px',top:'20%',left:'50%',marginLeft:'-212px'}});
			jq('#user_pop .dlog-top ul li').removeClass("white").eq(0).addClass("white");
			jq("#modify").attr("src","jy/uc/modify?"+ Math.random());
			jq.blockUI({ message: jq('#user_pop'),css:{width: '702px',height: '530px',top:'10%',left:'50%',marginLeft:'-351px'}});
			hideScroll();
		});  
		function len(){
			var ml = jq('#menu_list_box dl').length;
			if(ml%3==0){
				jq('.add').css('width','578px');
			}else{
				jq('.add').css('width','190px');
			}
		}
		jq('.add span').click(function () {
			jq.getJSON(_WEB_CONTEXT_+"/jy/uc/listMenu?"+Math.random(),function(data){
				var containner = jq("div.add_work_item ul");
				containner.html('');
				for(var i=0;i<data.length;i++){
					var d = data[i];
					var name = d.name ? d.name : d.menu.name;
					var title = d.menu.desc;
					var imgSrc = d.ico ? d.ico :d.menu.icon.imgSrc;
					var target = d.menu.target ? "target=\""+target+"\"":"";
					var dl = jq("<li/>").append(jq("<dl/>").attr("data-mid",d.id).attr("data-id",d.menu.id).append(jq("<dd/>").append(jq("<a "+target+" />").attr("href",d.menu.url)
							.append(jq("<img/>").attr({ src: _WEB_CONTEXT_+"/"+imgSrc,width:70,height:69, alt: name,title:title}))))
							.append(jq("<dt/>").append(jq("<a "+target+" />").attr("href",d.menu.url).html(name)))
							.append(jq("<span/>").addClass("add1").attr("mid",d.id)));
					containner.append(dl);
					
				}
				if(data.length == 0){
					containner.html('<div class="empty_wrap" style="margin:50px auto;"><div class="empty_info">工作项已经全部添加哟！</div></div>');
				}
			});
			jq("#app_pop").dialog({width:673,height:457}); 
//			jq.blockUI({ message: jq('#add_menu_pop'),css:{width: '673px',height: '457px',top:'20%',left:'50%',marginLeft:'-336px'}});
		});
		jq("div.menu_box").on("click","dl span",function(){
			var jqthis = jq(this);
			var jqparent = jqthis.parent().parent();
			var cls =jqthis.attr("class"); 
			var mid = jqthis.attr("mid");
			if(cls == "del"){
				jq.getJSON(_WEB_CONTEXT_+"/jy/uc/delMenu?mid="+mid+"&"+Math.random(),function(data){
					if(data.code == 0){
						var p = jqparent.html();
						jqparent.remove();
						len();
					}
				});
			}else if(cls == "add1"){
			  jq.getJSON(_WEB_CONTEXT_+"/jy/uc/addMenu?mid="+mid+"&"+Math.random(),function(data){
				  if(data.code == 0){
					    jqthis.removeClass("add1").addClass("del");
						jq("#tuozhuai").append(jqparent);
						len();
					}	
				});
			}
			
		});
		jq('#user_pop .dlog-top ul li').click(function(){ 
		    jq(this).addClass("white").siblings().removeClass("white");
		    var framedom = window.frames["modify"].document;
		    jq(".dlog-bottom_tab .dlog-bottom",framedom).hide().eq(jq('#user_pop .dlog-top ul li').index(this)).show(); 
	    });  
	   
		function showChild(children,container){
			if(children.length > 0){
				var dl = jq("<div class='Choice'/>").append(jq("<ul/>"));
				for(var i=0;i<children.length;i++){
						var m = children[i];
						jq("ul",dl).append(jq('<li/>').append(jq("<a>").attr("href",m.url).html(m.name)));
					}
				jq(container).append(dl);
			}
		}
		jq("div.menu_box").on('mouseenter mouseout',"dl",function(e){
			if(e.type === 'mouseenter'){
				var jqthis = jq(this), mid = jqthis.attr("data-id"), m = _CHILDMAP[mid];
				if(!m){
					jq.getJSON(_WEB_CONTEXT_+"/jy/uc/listChild?mid="+mid+"&"+Math.random(),function(data){
						  if(data.code == 0){
							  _CHILDMAP[mid]=data.data;
							  showChild(data.data,jqthis);
							}
						});
				}
			}
		});
		
		jq.ajax('./jy/planSummary/punishs/unViews/3?'+Math.random(),{
				'dataType':'json',
				'type':'GET',
				'success':function(data){
					var list=data.result.data;
					jq.getJSON(_WEB_CONTEXT_+"/jy/annunciate/getNotReadAnnunciate",function(data){
						var jylist=data.result.data;
						if(list.length+jylist.length>0){
							var html='';
							for(var j=0;j<jylist.length;j++){
								var title="";
								if (jylist[j].title.length>50){
									title=jylist[j].title.substring(0,50)+'...';
								}else{
									title=jylist[j].title;
								}
								if(jylist[j].type==1){
									html+='<li><a  href="./jy/annunciate/readedPunishAnnunciates?id='+jylist[j].id+'&&status='+jylist[j].status+'&&type=1" target="_blank">'+'<span class="redHead"></span><strong>'+"[学校通知]"+title+'</strong></a></li>';
								}else{
									if(jylist[j].orgId==-1){
										html+='<li><a  href="./jy/annunciate/readedPunishAnnunciates?id='+jylist[j].id+'&&status='+jylist[j].status+'&&type=1" target="_blank"><span></span><strong>'+"[系统通知]"+title+'</strong></a></li>';
									}else{
										html+='<li><a  href="./jy/annunciate/readedPunishAnnunciates?id='+jylist[j].id+'&&status='+jylist[j].status+'&&type=1" target="_blank"><span></span><strong>'+"[学校通知]"+title+'</strong></a></li>';
									}
								}
							}
							for(var i=0;i<list.length;i++){
								html+='<li><a  id="length'+i+'" href="./jy/planSummary/'+list[i].id+'/viewFile" target="_blank" ><span></span><strong>'+getPrefix(list[i])+list[i].title+'</strong></a></li>';
							}
							//jq('#scrollInfo').attr("style","width:"+jq('#scrollInfo').width()+"px;");
							
							jq('#ul').html(html);
							var width=40;
							jq('#scrollInfo  a').each(function(){
								width+=jq(this).width()+40;
							});
							jq("#scrollInfo").width(width);
						}else{
							jq('#scrollInfoH').hide();
							jq('#ul').html('<div class="empty_wrap" style="margin:0 auto;"><div class="empty_info">没有未查看的动态！</div></div>');
						}
					});
					}
				
			});
		
	}
	
	function getPrefix(ps){
		var roleName='';
		var categoryName='';
		if(ps.roleId==1373){
			roleName='备课';
		}else if(ps.roleId==1374){
			roleName='年级';
		}else if(ps.roleId==1375){
			roleName='学科';
		}else if(ps.roleId==1376||ps.roleId==1377){
			roleName='学校';
		}
		if(ps.category==3){
			categoryName='计划';
		}else{
			categoryName='总结';
		}
		return '['+roleName+categoryName+']';
	}
	
	window.closeFrame = function (objid,needUpdate){
		jq(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
		jq.unblockUI();
		if(needUpdate){
			loadCurriculum();
		}
	};
	
	function loadCurriculum() {
		jq.getJSON("jy/curriculum.json?"+Math.random(),	function(data) {
				var cus = data.map;
				var hasNight = true;
				var hasDay=true;
				for (var i = 1; i < 8; i++) {
					var cu = cus[i];
					if (cu) {
						for (var j = 1; j < 11; j++) {
						   var c = typeof cu[j] == 'undefined' ? '': cu[j].content;
						   if (c && jq.trim(c) != "") {
							   if(j>8){
								   hasNight = false; 
							   }
							   if(i>5){
								   hasDay = false; 
							   }
								var span = jq("<span class='isok'></span>")
								jq("#c" + i + "-" + j).attr("title",c).html(span);
								}else{
									jq("#c" + i + "-" + j).html("");
								}
							}
						}
					}
				if(hasNight){
					jq("tr.night").hide();
					jq("div.ev").hide();
					jq("div.mo").css({"height":"83px","line-height":"39px"});
					jq("div.aft").css({"height":"95px","line-height":"39px"});
				}else{
					jq("tr.night").show();
					jq("div.ev").show();
					jq("div.mo").css({"height":"58px","line-height":"29px"});
					jq("div.aft").css({"height":"54px","line-height":"28px"});
				}
				if(hasDay){
					jq("div.table_curriculum1 th").css("width","34px");
					jq(".cont_left_2_1 .mo").css("width","34px");
					jq(".cont_left_2_1 .aft").css("width","34px");
					jq(".cont_left_2_1 .ev").css("width","34px");
					jq("th.six").hide();
					jq("th.seven").hide();
					jq("td.day").hide();
					 
				}else{
					jq("div.table_curriculum1 th").css("width","23px");
					jq(".cont_left_2_1 .mo").css("width","26px");
					jq(".cont_left_2_1 .aft").css("width","26px");
					jq(".cont_left_2_1 .ev").css("width","26px");
					jq("th.six").show();
					jq("th.seven").show();
					jq("td.day").show(); 
				}
			});
	}
	loadCurriculum();
	window.fleshSchedule = function(){
		var frame = window.parent.document.getElementById("scheduleframe"); 
		frame.setAttribute("src", "jy/schedule/small");  
	}
	$(".home_cont_l_bottom1_title").click(function (){
		var show = $(this).parent().find("ul").is(":visible");
		if(!show){
			$(this).parent().find("ul").show();
		}else{
			$(this).parent().find("ul").hide();
		}
	});
	function isHaveHistoryYear(){
		$.getJSON(_WEB_CONTEXT_+"/jy/history/getYear?"+Math.random(),function(data){
			var yearStr = "";
			if(data){
				for(var i in data){
					var school = data[i];
					if(school){
						yearStr += '<a href='+_WEB_CONTEXT_+'/jy/history/'+school+'/index><li>'+school+"~"+(school+1)+'学年</li></a>';
					}
				}
				$(".cont_left_3").show();
			}
			$(".show_history_li").html(yearStr);
		});
	}
});
