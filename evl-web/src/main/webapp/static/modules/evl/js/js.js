;(function ( window, $) {
	$(document).ready(function () {
		teaching_evaluation();
		
	});
	
	var teaching_evaluation = function () {
		
		$(".release").click(function(){
			$('#release_dialog').dialog({
				width: 423,
				height: 200
			});
		});
		$(".del").click(function(){
			$('#del_dialog').dialog({
				width: 423,
				height: 200
			});
		});
		$(".end_assessment").click(function(){
			$('#end_assessment_dialog').dialog({
				width: 423,
				height: 200
			});
		}); 
		$(".teach_account").click(function(){
			$('#teach_account_dialog').dialog({
				width: 590,
				height: 263
			});
		}); 
		$(".hover_title").hide();
		$(".pjlx").each(function (){ 
			$(".pjlx_list3").hover(function(){  
				$(this).find(".hover_title").stop().fadeIn(); 
				},function(){ 
				$(this).find(".hover_title").stop().fadeOut(); 
			}); 
		});
		$(".pjlx_list2_wrap").click(function(){
			$('#pjlx_list2_wrap_dialog').dialog({
				width: 563,
				height: 468
			});
		}); 
		 $('#ul1 li a').click(function(){ 
			$(this).next().slideToggle();
  		});
        //移到右边
        $('#add_1').on('click',function() {
        //获取选中的选项，删除并追加给对方
	       	$('#ul1 li.centent_left_act').appendTo('#ul2');
	       	$('#ul2 li').removeClass("centent_left_act"); 
        });
        $(".gradg_table tr:odd").css({"background":"#ebf3f9"});
        $('.gradg_wrap').hide();
        $('.add_dj').click(function (){
        	$('.gradg_wrap').show(); 
        	$(this).parent().css({"margin-top":"0","margin-left":"0"})
        	$(".add_dj").hide();
        	$(".notes").hide();
        });
        $('.d_q_title_txt').placeholder({
        	 word: '请输入问卷标题'
        });
        $('.d_q_explain_txt').placeholder({
        	 word: '请输入评价说明'
        });
        $('.d_q_content_table .table2_wrap').css({"border-right":"none"});
        $('.d_q_content_table table.table1 tr td').last().css({"border-right":"none"}); 
        $('.d_q_content_table table.table2 tr td').last().css({"border-right":"none"}); 
        $('.d_q_content_table table.table2 tr th').last().css({"border-right":"none"}); 
        $('.d_q_content_table table.table1 tr').last().css({"border-bottom":"none"})
        var t_w_q = $('.d_q_content_table table tr td a q').width(); 
        var t_w_s = $('.d_q_content_table table tr td a strong').width(); 
        $('.d_q_content_table table tr td a').width(t_w_q+t_w_s+10+'px');  
        $('.hide_table').click(function (){
        	if($('.table2_wrap').is(":hidden")){
        		$('.table2_wrap').show();
        		$('.table1_wrap').css({"width":"59.5%"}); 
        		$('.score_table2').css({"width":"398px"});
        		$('.hide_table').css({"background-position":"-298px -45px"});
        	}else{ 
        		$('.table2_wrap').hide();
        		$('.table1_wrap').css({"width":"100%"});
        		$('.score_table2').css({"width":"100%"});
        		$('.hide_table').css({"background-position":"-316px -45px"});
        	}
        	

        });
//        $(".add_lb").click(function(){
//			$('#add_category_dialog').dialog({
//				width: 421,
//				height: 195
//			});
//		}); 
//		$(".add_lb_cont ol li a").click(function(){
//			$('#del_dialog').dialog({
//				width: 421,
//				height: 205
//			});
//		}); 
		$(".add_one").click(function(){
			$('#add_one_level_dialog').dialog({
				width: 595,
				height: 365
			});
		}); 
		$(".save_form").click(function(){
			$('#release_dialog').dialog({
				width: 421,
				height: 199
			});
		});
		$(".multiplexing").click(function(){
			$('#multiplexing_dialog').dialog({
				width: 565,
				height: 400
			});
		}); 
		
		$(".score_calculation").click(function(){
			$('#score_calculation_dialog').dialog({
				width: 680,
				height: 550
			});
		}); 
		$('.score_table2').each(function (){
			$(this).find('tr').last().css({"border-bottom":"none"});
		});
		var over_hei = $('.overall_table2 tr td').height();
		$('.overall_table1 tr td').height(over_hei);
		$('.overall_table2 tr').last().css({"border-bottom":"none"}); 
        $('.table2_wrap table.table2 tr th').last().css({"border-right":"none"});   
		$('.table2_wrap_bottom tr').each(function (){
			$(this).find('td').last().css({"border-right":"none"});
		});
		 var score_table1_height = $(".score_table1").height(); 
		$(".score_table1 .tr").each(function(){
			var index=$(this).index();
			$(".one_over tr").eq(index).css("height",$(this).height()+"px")
		});
		$('.one_over tr').last().css({"border-bottom":"none"});
		$('.overall_rating').height(score_table1_height-72+'px');

		$(".chosen-select-deselect").chosen({disable_search : true}); 
		$('.view_results_top3_table tr:odd').css({"background":"#f9f9f9"});
		$('.view_results_top4_table tr:odd').css({"background":"#f9f9f9"});
		$('.student_id').placeholder({
        	 word: '请输入学号'
        });
        $('.view_name_top1 ul li').last().css("border-right","none"); 
        $('.view_name_top2_table tr th').last().css("border-right","none"); 
        $('.view_name_top2_table2').each(function (){
        	$(this).find('tr').last().css("border-bottom","none"); 
        });
         $('.view_name_top2_table tr').each(function (){
         	$(this).find('td').last().css("border-right","none");
         });
         $('.view_name_top2_td').css("border-right","none");
         $('.view_name_top2_table .view_name_top2_table2 tr').each(function (){
         	$(this).find('td').last().css("border-right","none");
         }); 
         $('.v_n_table_l2').each(function (){ 
         	$(this).find('tr').last().css("border-bottom","none");
         });
         $('.v_n_table_l2 tr').each(function (){
         	$(this).find('td').last().css("border-right","none");
         });
          $('.v_n_table_r tr').each(function (){
         	$(this).find('td').last().css("border-right","none");
         });
         $(".v_n_table_r tr th").last().css("border-right","none");
         $('.v_n_table_r tr').last().css("border-bottom","none"); 


        $(".v_n_table_l .table_r_height").each(function(){
			var index=$(this).index();
			$(".v_n_table_r2 tr").eq(index).css("height",$(this).height()+"px")
		}); 
	    if(navigator.userAgent.indexOf("Chrome")>0){  
			$(".v_n_table_l .table_r_height").each(function(){
				var index=$(this).index();
				$(".v_n_table_r2 tr").eq(index).css("height",$(this).height()+1+"px")
			});
	    }

	    $(".view_name_whole_table1 tr:odd").css({"background":"#f9f9f9"});
	    $(".view_name_whole_tabl2 tr:odd").css({"background":"#f9f9f9"});
	    $(".v_n_w_table0 tr:odd").css({"background":"#f9f9f9"}); 
	    

	    $(".a_r_t2 tr").each(function (){
	    	$(this).find("td").last().css({"border-right":"none"}); 
	    }); 
		$(".a_r_t1").each(function (){ 
	    	$(this).find("tr").last().css({"border-bottom":"none"});
	    }); 
		$(".a_r_t2").each(function (){ 
	    	$(this).find("tr").last().css({"border-bottom":"none"});
	    });  
	    $(".view_name_table_b1 tr").each(function (){
	    	$(this).find("th").last().css({"border-right":"none"}); 
	    	$(this).find("td").last().css({"border-right":"none"});   
	    }); 
	    $(".view_name_table_b2").each(function (){
	    	$(this).find("tr").last().css({"border-bottom":"none"});
	    });
	    $(".view_name_table_b2 tr").each(function (){
	    	$(this).find("td").last().css({"border-right":"none"});  
	    	$(this).find("td").last().css({"border-bottom":"none"}); 
	    });  
	    $(".view_name_whole_table0 tr:even").css({"background":"#f9f9f9"});

	    $(".analysis_report_cont ul li").click(function (){ 
	    	$(this).addClass("an_re_act1").siblings().removeClass("an_re_act1");
	    	$('.analysis_report_cont_wrap .analysis_report_cont_tab').hide().eq($(".analysis_report_cont ul li").index(this)).show(); 
	    	$(".chosen-select-deselect").chosen({disable_search : true});  
	    	$("#subject_sel").trigger("chosen:updated"); 
			
	    });
	    $('.analysis_report_div .analysis_report_div1').each(function (){ 
			/*var that = $(this); */
			$(this).find('.show_hide').click(function (){
				$(this).parent().siblings().toggle(); 
				if($(this).find('strong').html()=="展开"){
					$(this).find('strong').html("收起");
					$(this).find('span').css({"background-position":"-296px -11px"});
					 
				}else{ 
					$(this).find('strong').html("展开");
					$(this).find('span').css({"background-position":"-296px -4px"}); 
				} 

			});
			var wrap = $(this).parent().find('.analysis_report_cont');
			var show_strong = $(this).find('.show_hide strong');
			var show_span = $(this).find('.show_hide span');
			if(wrap.css("display")=="block"){
				show_strong.html("收起"); 
				show_span.css({"background-position":"-296px -11px"});
			}else{
				show_strong.html("展开");
				show_span.css({"background-position":"-296px -4px"}); 
			}

		});
	}

})(window,jQuery);