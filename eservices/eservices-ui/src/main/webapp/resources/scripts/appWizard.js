document.getElementsByTagName('html')[0].className = 'js';

//var tw = (function() {
	$(function() {
		setEventsWizard();
		setPositioning();
		initScroll();
		initLang();
		validatePassStrength();
		swapTabResults();
		addCatGoodsAndServices();
		shwHd();
		inputHelpVal();
		dropDwn('#lastCountry', '#lastPriority', 'show');
	});

	function inputHelpVal(){
		var numInpt = $('.helpValue');
		if($(numInpt).length >0){
			$(numInpt).addClass('helpTxt');
			$(numInpt).focus(
				function(){
					$(this).attr('value', '').removeClass('helpTxt');
			});
		}
	}

	function setPositioning(){
		$('.fl2lcols .reviewBox').css('min-height','500px');		
	}
	function shwHd(){
		var gntLst = $('.giantButtonsList li a');
		$('div[data-rel=busi]').hide();
		$('div[data-rel=legal]').hide();
		$(gntLst).click(function(){
			var tgt = $(this).attr('data-rel');
			var tgType = $(this).attr('data-type');
			$('div.[data-rel='+tgType+']').hide();
			$('div.'+tgt).show();
		})
	}	
	function setEventsWizard(){
		$('#i18nSelect').change(function(){
			changeLng();
		});
		$('.giantButtonsList li a').click(function(){
			var thUl = $(this).closest('.giantButtonsList');
			$(thUl).find('li a').removeClass('active');
			$(this).addClass('active');
			return false;
		});
		$('.cardS .readMore a').click(function(){
			$(this).colorbox({inline: true, width: '50%'})	
		});
		$('.moreTypesBot').click(function(){
			var lcA = $(this).attr('rel');
			$('#'+lcA).show(700);
			$(this).parent().hide();
		});
		$('.moreMarks a').bind('click',function(){
			$('.minorType').slideToggle(300);
			$(this).parent().hide();
			return false;
		})

	}	
	function swapTabResults(){
		var objA = $('.medButtonsList a');
		var objDiv = $('div[data-reldiv]');
		$(objDiv).hide();
		$(objDiv).first().show();
		$(objA).click(function(){
			var tTarget = $(this).attr('data-reldiv');
			$(objDiv).hide();
			$('div[data-reldiv='+tTarget+']').show();
			$(objA).removeClass('active');
			$(this).addClass('active');
			return false;
		});
	}
	function addCatGoodsAndServices(){
		var tbIndex = $('.genericTable[data-tbtype=index]');
		var tbSpn = $('.genericTable[data-tbtype=index] span.tknClass');
		var tbSumm = $('.genericTable[data-tbtype=summary]');
		$(tbSumm).hide();
		$('.genericTable[data-tbtype=summary] tbody tr').first().hide();
		$(tbSpn).click(function(){
			var cThs = $(this).clone();
			var newTR = $(tbSumm).find('tbody tr:first-child').clone();
			$(newTR).show();
			$(this).addClass('dark');
			$(tbSumm).show();
			$(tbSumm).append($(newTR));

			$(newTR).find('th.short').text($(this).parent().prev().html());
			$(newTR).find('td').html($(cThs));
		})
	}
	function validatePassStrength(){
		var passMsgs = $('p[data-rel=pass]');
		var inptPs = $('#passwrd');
		passMsgs.hide();
		inptPs.keydown(function(){
			var fldVal = $(inptPs).val().length;
			if(fldVal >= 12){
				$(passMsgs).hide();
				$('.passStrong').show();
			}
			if(fldVal < 8){
				$(passMsgs).hide();
				$('.passNormal').show();
			}
			if( fldVal < 4){
				$(passMsgs).hide();
				$('.passWeak').show();
			}
		});
	}

	function initLang(){
		$.i18n.init({
			ns: { namespaces: ['ns.labels'], defaultNs: 'ns.labels'},
			useLocalStorage: false,
			fallbackLng: 'en',
			resGetPath: 'fakedata/__lng__/__ns__.json'
		}, function() {
			$('#container').i18n();
			$('input[type=reset]').attr('value', $.t('labels.cleartxt'));
			$('input[type=submit]').attr('value', $.t('labels.sendtxt'));
			$('#lngSelTxt').text($.t('labels.langselection'));
			$('input[data-i18n^=yestxt]').attr('value', $.t('labels.yestxt'));
			$('input[data-i18n^=notxt]').attr('value', $.t('labels.notxt'));
			$('#delWarnTxt').i18n();
			$('#valWarnTxt').i18n();
		});	
	}


	function updateLang(langSel){
		$.i18n.init({
			ns: { namespaces: ['ns.labels'], defaultNs: 'ns.labels'},
			lng: langSel,
			useLocalStorage: false,
			resGetPath: 'fakedata/__lng__/__ns__.json'
		}, function() {
			$('#container').i18n();
			$('input[type=reset]').attr('value', $.t('labels.cleartxt'));
			$('input[type=submit]').attr('value', $.t('labels.sendtxt'));
			$('#lngSelTxt').text($.t('labels.langselection'));
		});		
	}

	function swapVisib(source, target) {
		$(source).click(function(){
			var aRel = $(this).attr('rel');
			if(aRel.indexOf('Y')!== -1){
				$(target).show(200);
			} else {
				$(target).hide(200)		
			}
			$(source).removeClass('active');
			$(this).addClass('active')
		})
	}

	function errorTxt(destino) {
		var errM = document.createElement('p');
		$(errM).addClass('flMessageError');
		$(errM).attr('data-i18n', 'colorErrTxt')
		$(destino).before($(errM));
		$(errM).text($.t('labels.colorErrTxt'));
	}

	function split(val) {
		return val.split( /,\s*/ );
	}

	function extractLast(term) {
		return split(term).pop();
	}	
	function swapVisib(source, target) {
		$(source).click(function(){
			var aRel = $(this).attr('rel');
			if(aRel.indexOf('Y')!== -1){
				$(target).show(200);
			} else {
				$(target).hide(200)		
			}
			$(source).removeClass('active');
			$(this).addClass('active')
		})
	}	
//})();	