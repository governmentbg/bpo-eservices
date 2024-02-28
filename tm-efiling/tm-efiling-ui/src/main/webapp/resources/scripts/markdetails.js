// eFiling app 
function changeLng() {
	var langSel = $('#i18nSelect').val();
	var setLng = '?setLng=';
	var oHref = this.location.href;
	var soHref = oHref.substring(0,oHref.indexOf('?'));
	window.location = soHref + setLng + langSel;
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
function readMore(){
	$('.cardS .readMore a').click(function(){
/*		var rmHref = $(this).find('p').find('a').attr('href');
		$(this).colorbox({href: rmHref, inline: true, width: '50%'});
*/
		$(this).colorbox({inline: true, width: '50%'})	
	});
}
function showTypes(){
	$('.moreTypesBot').click(function(){
		var lcA = $(this).attr('rel');
		$('#'+lcA).show(700);
		$(this).parent().hide();
	})
}
function hideTypes(){
	if($('.moreTypes').length > 0){
		$('.moreTypes').closest('div').hide();
		showTypes();
	}
}
function swapVisibMarkDetails(source, target) {
	$(source).click(function(){
		alert('')
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
function validateHex(hexcode){
	var regExpHex = /^(#)?([0-9a-fA-F]{3})([0-9a-fA-F]{3})?$/;
	if(regExpHex.exec(hexcode)!= null){
		newColor(hexcode);
		if($('.colorY p.flMessageError')){$('.colorY p.flMessageError').remove()}
	} else {
		if($('.colorY p.flMessageError').length === 0){
			errorTxt('#choosenCols');
		}
	}
}
function getColors(){
	var selectedCols = $('#choosenCols li').text();
	$('#colsAdded').val('');
	for(i=0;i<selectedCols.length;i++){
		$('#colsAdded').val(selectedCols); 
	}
} 
function deleteColors() {
	var currentClrs = $('#choosenCols li');
	$(currentClrs).click(function(){
		$(this).remove();
		getColors();
	})
}
function newColor(colorHex){
	var colorNum = colorHex.substr(1);
	if($('#col_'+ colorNum).length === 0){
		$('#choosenCols').append('<li id="col_'+ colorNum +'"><span class="tknClass" style="border-top:5px solid '+colorHex+'"></span></li>');
		$('li[id=col_'+colorNum+'] span.tknClass').text(colorHex);
		deleteColors();
		getColors();
	}
}
function addColor(colorcode) {
	$('#addColor').click(function(){
		var colorHex = $('#selCol').val();
		if(colorHex !== ''){
			validateHex(colorHex);
		};
	});
}	
function initMarkDetails(){
//	swapVisibMarkDetails('.medButtonsList li a', 'div.colorY');
	$('.cardS').addClass('pointer');
	readMore();
	//hideTypes();
	addColor();
}
$(window).load(function(){
	initMarkDetails();
	$('#i18nSelect').change(function(){changeLng();})
//	$.i18n.init({
//			ns: { namespaces: ['ns.labels'], defaultNs: 'ns.labels'},
//			useLocalStorage: false,
//			fallbackLng: 'en',
//			resGetPath: 'fakedata/__lng__/__ns__.json'
//		}, function() {
//			$('#container').i18n();
//			$('input[type=reset]').attr('value', $.t('labels.cleartxt'));
//			$('input[type=submit]').attr('value', $.t('labels.sendtxt'));
//			$('#lngSelTxt').text($.t('labels.langselection'));
//	});	
	$('#selCol').ColorPicker({
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onSubmit: function(hsb, hex, rgb, el) {
			$('#selCol').val('#' + hex);
			$(el).ColorPickerHide();		
			return false;
		}	
	});
});