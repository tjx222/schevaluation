$(function() {
	$('.t_b_right div dl dd').mouseover(function (){
		$(this).parent().parent().find('div').fadeIn('slow');
	});
	$('.t_b_right div dl dd').mouseout(function (){
		$(this).parent().parent().find('div').fadeOut();
	});
	$('.sch_b div dl dd').mouseover(function (){
		$(this).parent().parent().find('div').fadeIn('slow');
	});
	$('.sch_b div dl dd').mouseout(function (){
		$(this).parent().parent().find('div').fadeOut();
	});
	$('.product').click(function (){
		$(window).scrollTop(540);
	});
	$('.teacher_studio').click(function (){
		$(window).scrollTop(935);
	});
	$('.school_management').click(function (){
		$(window).scrollTop(1605);
	});
	
})