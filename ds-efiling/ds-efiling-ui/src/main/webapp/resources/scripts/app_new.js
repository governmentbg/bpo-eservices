$(function() {
	$('input[class*="-date"]').datepicker();
	initScroll();
	initPopovers();
	initTermsTooltips();
	initWizarMarkButtons();
});

function initScroll(){
    var scrlDwnMenu = $('aside');
    var topPos = 165;
    var lowPos = 250;
    var topCorrection = 150;
    $(window).scroll(function(){
        evalScroll();
    });
    $(window).resize(function(){
        evalScroll();
    });
    function evalScroll(){
        var sideBarHeight = scrlDwnMenu.height();
        var containerHeight = $('.main').height();
        var documentHeight = $(document).height();
        var scrollPos = $(window).scrollTop();
        if(scrollPos > topPos) {
            if(documentHeight - scrollPos - lowPos < sideBarHeight) { // lower visible main container less than side bar height
                scrlDwnMenu.css('top', containerHeight-sideBarHeight+topCorrection);
                scrlDwnMenu.css('position', 'absolute');
            } else {
                scrlDwnMenu.css('top', '0' );
                scrlDwnMenu.css('position', 'fixed');
            }
        } else {
            scrlDwnMenu.css('top', '');
            scrlDwnMenu.css('position', 'absolute');
        }

    }
}

function initPopovers() {
    $('[rel*="-popover"]').each(function(index, element) {
    	$element = $(element);
    	$element.popover({
         placement: 'top',
         trigger: 'hover',
         title: $element.data('title'),
         content: $($element.data('target')).html()
    	});
    });
}

function initTermsTooltips() {
	$('[rel="term-tooltip"]').tooltip({
		placement: 'top',
		trigger: 'hover'
	});
}

function initWizarMarkButtons() {
	$('.mark-types-wizard>li').on('click', function() {
		$('.mark-types-wizard>li').removeClass('active');
		$(this).addClass('active');
	});
	$('.see-more-button').on('click', function() {
		$('#hidden-wizard-marks').collapse('show');
		$(this).remove();
	});
}