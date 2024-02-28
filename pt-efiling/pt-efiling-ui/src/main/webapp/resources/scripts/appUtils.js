

function initScroll(){
	var scrlDwnMenu = $('#rightBar');
	var topPos = 114;	
	$(window).scroll(function(){
		evalScroll();
	});
	$(window).resize(function(){
		evalScroll();
	});
	function evalScroll(){
		var rightBarHeight = scrlDwnMenu.height();
		var wHeight = $('#centerCtn').height();
		var ctnHght = $('#basicPage').height();
		var contHght = $('#container').height()
		var footHgt = $('.flApplicationFooter').height();
		var scrollPos = $(window).scrollTop();
		$('#rightBar').css('position', 'relative');
		if($(window).scrollTop() > topPos){
			scrlDwnMenu.css('top',($(window).scrollTop() - 90) )
		} 
		else {
			scrlDwnMenu.css('top', '0');
			//scrlDwnMenu.animate({top: '0'}, 300);
		}
		if(scrollPos > (contHght - rightBarHeight)){
			scrlDwnMenu.css('top', '0');
		//scrlDwnMenu.animate({top: '0'}, 300);
		}				

	}	
}