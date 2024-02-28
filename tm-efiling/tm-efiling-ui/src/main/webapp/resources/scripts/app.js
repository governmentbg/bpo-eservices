$(function() {
	$('html').addClass('js');
	renderOffice();
	setPlugins();
	setEvents();
	sectLoad();
	sectRw();
//	addCalendar();
//	addPr();
	changeForm();
	inputHelpVal();
	tbIndex();
	cardMoreInf();
	reviewBoxHght();
//	createUploaders();
//	$.i18n.init({
//			ns: { namespaces: ['ns.labels'], defaultNs: 'ns.labels'},
//			//lng: langSel,
//	        fallbackLng: 'en',
//			useLocalStorage: false,
//			resGetPath: 'fakedata/__lng__/__ns__.json'
//		}, function() {
//			$('#container').i18n();
//			$('#gands').i18n();
//			$('#mylist').i18n();
//			$('#delWarnTxt').i18n();
//			$('#valWarnTxt').i18n();
//			$('#similarTrademark').i18n();
//			$('input[type=reset]').attr('value', $.t('labels.cleartxt'));
//			$('input[type=submit]').attr('value', $.t('labels.sendtxt'));
//			$('#lngSelTxt').text($.t('labels.langselection'));
//			$('div[data-rel=priority] input[type=button]').attr('value', $.t('labels.prioritytxt'));
//			$('div[data-rel=seniority] input[type=button]').attr('value', $.t('labels.senioritytxt'));
//			$('div[data-rel=exhibition] input[type=button]').attr('value', $.t('labels.exhibitiontxt'));
//			$('div[data-rel=irtransformation] input[type=button]').attr('value', $.t('labels.irtransformationtxt'));
//			extendMarkUp();
//		});
		initScroll();
});

function renderOffice(){
	var idOffice = getURLParameter('office');
	if(idOffice === 'ohim'){
		$('[data-office=super]').hide();
		$('[data-office=finland]').hide();
		$('#wizard a[href="#tabApplicant"]').click();
		$('#wizard a[href="#tabApplicant"]').hide();
		$('#wizard a[rel="prev"], #wizard a[rel="next"]').each(function(index) {
			var url = $(this).attr('href') + '?office=ohim';
			if($(this).attr('data-toggle') !== 'modal') $(this).attr('href', url);
		});
	}
}

function changeLng() {
	var langSel = $('#i18nSelect').val();
	var idOffice = getURLParameter('office');
	if(idOffice) var officeParam = '&office=' + idOffice;
	var setLng = '?setLng=';
	var oHref = this.location.href;
	var soHref = oHref.substring(0,oHref.indexOf('?'));
	window.location = soHref + setLng + langSel + officeParam;
}

function extendMarkUp(){
	$('.mandatory').closest('div').find('label').append("<em class='requiredTag'>(*)</em>");
}

function createUploaders(){
	$('.uploader').each(function(event) {
		var idItem = "#" + $(this).attr('id');
		$(idItem).fileupload();
		$(idItem).fileupload(
		    'option',
		    'redirect',
		    window.location.href.replace(
		        /\/[^\/]*$/,
		        '/cors/result.html?%s'
		    )
		);
	    $(idItem).each(function () {
	        var that = this;
	        $.getJSON(this.action, function (result) {
	            if (result && result.length) {
	                $(that).fileupload('option', 'done')
	                    .call(that, null, {result: result});
	            }
	        });
	    });
	});
}


function setPlugins(){
	jQuery.validator.addClassRules("mandatory", {
  		required: true
	});
//	$('.autocompleted').each(function(event) {
//		var urlJson = $(this).attr('data-url');
//		var minChars = $(this).attr('data-minchars');
//		if(!minChars) minChars = 3;
//		$(this).autocomplete({
//			minLength: minChars,
//			source: function(request, response) {
//			$.getJSON( urlJson, {
//				args: extractLast(request.term)
//			}, response );
//		}});
//	});	
}

function validatePage(){
	var validItems = $('.mandatory:visible').length;
	$('.mandatory:visible').each(function(event) {
		if($(this).closest(".mainForm").length > 0) {
	        $(this).closest(".mainForm").first().validate().element(this);
			//$('.mainForm').validate().element(this);
			placeErrorMessage(this);
			if($(this).valid()) validItems --;
		}
	});
	if(validItems > 0){ 
		showGeneralValidation();
		return false;
	}
}

function clearErrors(){
	$('label.error').remove();
	$('.error').removeClass('error');
}


function fillTableData(tableId,data){
	var itemId = ($('>tbody>tr',tableId).filter(function(){return $(this).children('td:not(.editRow)').length;}).length) + 1;
	if(itemId < 10) itemId = '0' + itemId;
	var tableData = '<tr><td>' + itemId + '</td>';

	jQuery.each(data, function(i) {
		tableData += '<td>' + data[i] + '</td>';
	});
	tableData += '<td class = "options"><ul><li class="edit"><a href="#">edit</a></li><li class="remove"><a href="#">remove</a></li></ul></td>';
	tableData += '</tr>';
	$(tableId).append(tableData);
}

//temporary functions to fill in dummy data on the wizard tables
function setTableDataApplicant(){
	var containerInfo = $(this).closest('.tab-pane');
	var tempData = new Array('2000','Natural Person', 'Company Name', 'Finland');
	var tableData = $(this).closest('#formEF').find('.data-table.applicants');
	fillTableData(tableData, tempData);
}
function setTableDataClaim(){
	var containerInfo = $(this).closest('.tab-pane');
	var tempData = new Array('Priority', 'Finland','18/14/12', '2000','To follow');
	var tableData = $(this).closest('#formEF').find('.data-table.claims');
	fillTableData(tableData, tempData);
}


function setEvents(){	
	$('#tabApplicant .addBut, #tabRepresentative .addBut').bind('click', setTableDataApplicant);
	$('.previousClaim .addBut, .claimSections .addBut').bind('click', setTableDataClaim);

	$('.toggleNav .btn').bind('click', toggleItems);

	$('a[data-toggle="tab"]').on('shown', function (e) {
  		clearErrors();
	});

	$('.country').bind('change', function(event) {
		var val = $('option:selected',this).val();
		if(val === 'US') $(this).next().show();
		else $(this).next().hide();
	});

	$('#i18nSelect').change(
		function(){
			changeLng();
		}
	);
	$(".toggleButton").bind('click', function(event) {
		var selector = $(this).attr('href');
		$(selector).toggle();
		$('span',this).toggle();
		event.preventDefault();
	});
	$("#gsResults .toggleButton").bind('click', function(event) {
		$('#resultsTable').toggleClass('fullSize');
	});
	$('#wizard #gsResults .toggleButton').click();
	$(".mandatory").live('blur', function(event) {
		var form = $(this).closest(".mainForm").first();
		if(form.length == 0) {
			form = $(this).closest(".formEF").first();
		}
		form.validate().element(this);
		//$('.mainForm').validate().element(this);
		placeErrorMessage(this);
	});
	$(".matchPattern").live('blur', function(event) {
		var form = $(this).closest(".mainForm").first();
		if(form.length == 0) {
			form = $(this).closest(".formEF").first();
		}
		form.validate().element(this);
		//$('.mainForm').validate().element(this);
		placeErrorMessage(this);
	});
	$('#errorBox .closeButton').live('click', function(event) {
		var posR = - ($('#errorBox').outerWidth() + 50)+ 'px';
		$('#errorBox').slideToggle(300);
		return false;
	});
	$('.formVal').bind('click', validatePage);
	$('.dependant input[type="checkbox"]').live('change', function(event){
		var itemDependant = $(this).closest('.dependant');
		itemDependant = $('>div', itemDependant);
		this.checked ? itemDependant.show() : itemDependant.hide();
	});
	$('#changeCss').change(function(){
		var cssVal = $('#changeCss').val();
		var cssLnk = $('link[href*=app]');
		$(cssLnk).attr('href', 'css/'+cssVal+'.css');
	});
	$(".cancelButton").live('click',clearSectionForm);
	$('#claimsNav li a.mainOption').bind('mouseup', function(event) {
		var tab = $(this).attr('href');
		$(tab).addClass('active');
		$('#claimsNav li a.mainOption').removeClass('active');
	});
	$('#claimsNav li a:last-child').bind('click', function(event) {
		var tab = $(this).siblings('a').attr('href');
		$(this).siblings('a').removeClass("active");
		$(tab).removeClass('active');
	});
    $('.checkerCollective, .checkerCertificate').live('change', function(event) {
        var selector = $("#wdCollective1");
        selector.insertAfter($(this).parent());
        this.checked ? selector.show() : selector.hide();
    });
    $('#markType input[type="checkbox"]').live('click', function() {
        if ($(this).is(':checked')) {
            var el = $(this);
            $('#markType input[type="checkbox"]').each(function() {
                if (!el.is($(this))) {
                    $(this).prop('checked', false);
                }
            });
        }
    });
    $('.documentSelectors select').live('change', function(event) {
        var selector = $(this).closest('.row').find('.fileUploader');
        var valor = $('option:selected',this).val();
        valor ? selector.show() : selector.hide();
    });
	$('.checkToggle input[type="checkbox"]').live('change', function(event) {
		var itemToShow = $(this).attr("rel");
		$(this).is(':checked') ? $("#" + itemToShow).show() : $("#" + itemToShow).hide()
	});
	$('#wizard a[rel="prev"], #wizard a[rel="next"]').bind('click',validatePage);
	$('.cart li:last-child').live('click', function(event) {
		$('.cart li:not(:last-child)').slideToggle(400);		
		$('.cart').toggleClass('collapsed');				
	});
	$('.deleteB').click(function(){
		$(this).colorbox({inline:true,innerWidth:'20%',innerHeight:'20%'});
		$(this).closest('div.mc_repcard').addClass('toDelete');
		$('#delWarnTxt a.confirm').click(function(){$('.toDelete').remove();$.colorbox.close()});
		$('#delWarnTxt a.cancel').click(function(){$('.toDelete').removeClass('toDelete');$.colorbox.close()});
	});
	$('.moreMarks a').live('click',function(){
		$('.minorType').slideToggle(300);
		$(this).parent().hide();
		return false;
	})

	
	$('#claimData').on('click','table .options .edit',function(e){
		var target = $(e.target);
		target.closest('tr').next().children('td').show();
		return false;
	});
	$('#claimData').on('click','table .cancelButton', function(e){
		var target = $(e.target);
		target.closest('td').hide();
		return false;
	});
	$('#claimData').on('click','table .options .remove',function(e){
		var target = $(e.target),
			tr = target.closest('tr');
		tr.next().remove();
		tr.remove();
		return false;
	});	
	
	
	$('#applicantData').on('click','table .options .edit',function(e){
		var target = $(e.target);
		target.closest('tr').next().children('td').show();
		return false;
	});
	$('#applicantData').on('click', 'table .cancelButton', function(e){
		var target = $(e.target);
		target.closest('td').hide();
		return false;
	});
	$('#applicantData').on('click', 'table .options .remove',function(e){
		var target = $(e.target),
			tr = target.closest('tr');
		tr.next().remove();
		tr.remove();
		return false;
	});	
}


function toggleItems(){
	var n = $(this).index();
	var itemToggable = $(this).closest('.toggleWrap').find('.toggleItem');
	if(n === 0) itemToggable.show();
	else  itemToggable.hide();


}

function sendData(){
	var tab = $(this).closest('.tab-pane');	
	var dataInfo = new Array();
	$('input,select,textarea',tab).each(function(event) {
		var itemInfo = $(this).val();
		dataInfo.push(itemInfo,this);
	});
	fillData(dataInfo);
	clearSectionForm(this);
}

function fillData(){

}


function clearSectionForm(){
    $('.ui-autocomplete').hide();

    var section = $(this).closest('ul').parent().find('fieldset');
	$('label.error',section).remove();
	var clearingItems = $(':input', section).each(function() {
		$(this).removeClass('valid error');
		  switch(this.type) {
			case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'textarea':
                $(this).val('');
                break;
            case 'hidden':
            	if(this.name == "id") {
            		$(this).val('');
            	}
            	break;
            case 'checkbox':
            case 'radio':
    		this.checked = false;
		  }
	});
	var tab = $(this).closest('.tab-content').find('>div:visible');
	var nav = $(this).closest('.tabsWrap').find('.tabNav li.active');
	nav.removeClass('active');
	tab.removeClass('active');
}
//START: legacy: pending refactor
function addPr(){
	$('a.addBut').bind('click', function(event) {
		var formItem = $(this).closest('form');
		var fieldsContainer = $(this).closest('ul').parent();
		var itemsToValidate = $('.mandatory:visible',fieldsContainer).length;
		var validItems = 0;
		
		$('.mandatory:visible',fieldsContainer).each(function(event) {
			$(formItem).validate().element(this);
			placeErrorMessage(this);
			validItems += $(this).valid();
		});

		if(itemsToValidate !== validItems) {
			event.stopImmediatePropagation();
			return false;
		}
		
//		if($(this).parents('div[data-rel]').length> 0){
//			var oPar = $(this).parents('div[data-rel]');
//			var parCla = $(oPar).attr('data-rel');
//			var typeO = parCla.substring(5);
//			var cardType = 1;
//		}
//		else if($(this).parents('div[data-ap]').length > 0){
//			var oPar = $(this).parents('div[data-ap]');
//			var typeO = $(oPar).attr('data-ap');
//			var parCla = typeO;
//			var cardType = 0;
//		}
//		else {
//			return;
//		}
//		var inpFlds = $(oPar).find('.mandatory');
//		var num = $(oPar).find('span.numC').text()
//		var newId = 'id'+ num + typeO;		
//		addRow(num,typeO,newId,cardType);
//		addCard(num,typeO,newId,cardType);
//		$.each(inpFlds, function(key, value){
//			var inpId  = $(value).attr('id');
//			var inpVal = $(value).val(); 
//			var inpLab = $('label[for=' + inpId + ']').text();
//			addField(inpVal,inpLab,newId);
//		});
		var tab = $(this).closest('.tab-pane');
		tab.removeClass('active');
		$('a[href*="#' + $(tab).attr("id") + '"]').removeClass('active');
		
	});
}

function addRow(num,type,newId,cardType,fillNum){
	if(cardType == 1){
		var tb = $('table.genericTable.index');
		var firstTr = $(tb).find('tbody tr:first-child');
		var newTr = $(firstTr).clone();
		$(newTr).attr('data-reldiv',newId);
		$(newTr).find('td:first-child').text(num+' '+type);	
		$(firstTr).after($(newTr));
		$(newTr).removeClass('hidden');
		$(tb).removeClass('hidden');
		tbIndex();
		trType(type);
	}
}

function addCard(num,type,newId,cardType){
	if(cardType == 1){
		var newCard = $('.mc_reclcard[data-rel=model]').clone();
		$('table.index').after($(newCard));
	}
	if(cardType == 0){
		var newCard = $('.mc_repcard[data-rel=model]').clone();
		$('p.add'+type).before($(newCard));
		$(newCard).removeAttr('style');
		$(newCard).addClass(type);		
	}
	//$('table.index').after($(newCard));
	$(newCard).i18n();
	$(newCard).find('span.numType').text(num);
	$(newCard).find('span.type').text(type);
	$(newCard).attr('data-rel',newId);
	if(cardType == 1){$(newCard).hide();}
	if(cardType == 0){$(newCard).show();cardMoreInf();delButton();$('p.add'+type).removeClass('hidden');
			if(type == 'representative'){$('p.repIntroTxt').removeClass('hidden')}
	}
}

function addField(val,label,newId){
	var cardFlds = $('div[data-rel='+newId+']').find('div.hidden').first();
	var cardCon = $('div[data-rel='+newId+']').find('div.fl3lcols div').last();
	var newCardFlds = $(cardFlds).clone();
	$(cardCon).after($(newCardFlds));
	$(newCardFlds).removeClass('hidden');
} 



function trType(type){
	var oldtr = $('tr[data-reldiv*='+type+']');
	if($(oldtr).length>0){
		var radButv = $('div[data-rel='+type+']').find('input[type=radio]');
		$(radButv).attr('disabled','disabled');
		$('div[data-rel='+type+'] input[type=radio].ok').removeAttr('disabled');
	}
}


function getURLParameter(name) {
    return decodeURIComponent(
        (location.search.match(RegExp("[?|&]"+name+'=(.+?)(&|$)'))||[,null])[1]
    );  
};
function updateLang(langSel){
	$.i18n.init({
		ns: { namespaces: ['ns.labels'], defaultNs: 'ns.labels'},
		//lng: langSel,
        fallbackLng: 'en',
		useLocalStorage: false,
		resGetPath: 'fakedata/__lng__/__ns__.json'
	}, function() {
		$('#container').i18n();
		$('input[type=reset]').attr('value', $.t('labels.cleartxt'));
		$('input[type=submit]').attr('value', $.t('labels.sendtxt'));
		$('#lngSelTxt').text($.t('labels.langselection'));
	});		
}
function split(val) {
	return val.split( /;\s*/ );
}
function extractLast(term) {
	return split(term).pop();
}

function tbIndex(){
	var recDivs = $('.mc_reclcard');
	var oTbtrTd = $('.mainForm table.index tr td a');
	var oTbtr = $('.mainForm table.index tr');
	$(recDivs).hide();
	$(oTbtrTd).live("click", function(){

		var attRel = $(this).parents('tr').attr('data-reldiv');
		var target = $('.mc_reclcard[data-rel='+attRel+']');
		if($(this).hasClass('delete')){
			$(this).colorbox({inline:true,innerWidth:'20%',innerHeight:'20%'});
			$(this).parents('tr').addClass('toDelete');
			$(target).addClass('toDelete');
			$('#delWarnTxt a.confirm').click(function(){$('.toDelete').remove();$.colorbox.close()});
			$('#delWarnTxt a.cancel').click(function(){$('.toDelete').removeClass('toDelete');$.colorbox.close()});
		} if($(this).hasClass('edit')){
			if($(target).css('display') == 'none'){
				$('.mc_reclcard').hide();
				$(target).show('fast');
				return false;
			} else {
				$(target).hide();
				return false;				
			}
		}
	});	
	$(oTbtr).click(function(){
		var thisRel = $(this).attr('data-reldiv');
		var targt = $('.mc_reclcard[data-rel='+thisRel+']');
			if($(targt).css('display') == 'none'){
				$('.mc_reclcard').hide();
				$(targt).show('fast');
			} else {
				$(targt).hide();
			}
	});
}

function cardMoreInf(){
	var cardInfRow = $('.mainForm .card .fl3lcols');
	var fstCardInfRow = $('.mainForm .card .fl3lcols:first-child');
	var swBtn = ('.mainForm .mc_repcard a.flButton[data-i18n*=moredetails]');
	var hdBtn = ('.mainForm .mc_repcard a.flButton[data-i18n*=lessdetails]');
	$(cardInfRow).hide();
	$(fstCardInfRow).show();	
	$(hdBtn).hide();
	$(swBtn).toggle(
		function(){
			$(this).parents('.mc_repcard').find('.fl3lcols').show();
			$(this).attr('data-i18n','labels.lessdetails');
			$(this).text($.t('labels.lessdetails'));
		},
		function(){
			$(this).parents('.mc_repcard').find('.fl3lcols').hide();
			$(this).attr('data-i18n','labels.moredetails');
			$(this).text($.t('labels.moredetails'));
		}
	);
}

function reviewBoxHght(){
	if($('.reviewBox').length > 0){
		var fBx = $('.reviewBox').first();
		var lBBx = $('.reviewBox').first().next();
 		if($(fBx).height() > $(lBBx).height() && $(fBx).offset().top === $(lBBx).offset().top){
			$(lBBx).css('height', $(fBx).height());
		}
	}
}



// aside menu validation
function sectLoad(){
	var sdMen = $('#sideNav ul li a');
	$.each(sdMen,function(k,v){
		var oHref = $(v).attr('href');
		var oSect = oHref.substring((oHref.lastIndexOf('#'))+1);
		var sectType = $('*[data-section='+oSect+'].mandatory');
		if(sectType.length > 0){
			$(v).addClass('ctrtodo');	
		} else {$(v).addClass('ctroptional');}
	}); 
}

function sectRw(){
	var cartOb = $('*[data-section].mandatory');
	$(cartOb).blur(function(){
		var sect = $(this).attr('data-section');
		var tVal = $(this).closest('.flFormRow').find('label');
		if($(tVal).hasClass('validateOK')){
			$(this).addClass('fldComplete');
		} 
		sectValidation(sect);
	});
}

function sectValidation(section){
	var totalFlds = $('*[data-section='+section+'].mandatory').length;
	var  fldsVal =  $('*[data-section='+section+'].fldComplete').length;
	var secLnk = $('a[href=#'+section+']');
	var nwSpn = document.createElement('span');
	if(fldsVal > 0) {
		$(secLnk).attr('class','');
		$(secLnk).addClass('crtprogress');		
	}
	if(fldsVal == totalFlds) {
		$(secLnk).attr('class','');
		$(secLnk).addClass('crtdone');
	}
}


function changeForm(){
	var selEl = $('select.hideFlds');
		$('fieldset[data-val*=""]').hide(); 
		$(selEl).change(function(){
			clearErrors();
			var hideFlds = $(this).attr('data-val');
			var datV = $(this).val();
			$('fieldset.'+hideFlds).hide().removeClass('toCart'); 
			$('fieldset[data-val='+datV+']').show().addClass('toCart');
			$('fieldset[data-val="contactData"]').show().addClass('toCart');
		});		
} 

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

function formatDate(value){
	var day = value.getDate();
	if(day < 10){
		day = "0" + day;
	} 
	var month = (value.getMonth() + 1);
	if(month < 10){
		month = "0" + month;
	} 
	return day + "/" + month + "/" + value.getFullYear();
}

function resHeight(){
	var rBarH = $('#rightBar').height();
	var wHeight = $(window).height();
	if(wHeight < rBarH){
		$("#sideNav").css('marginTop', '0');				
	}
}


function placeErrorMessage (inputItem){
		var labelItem = $(inputItem).parent().find('label.error[for="' + $(inputItem).attr("id") + '"]');
		labelItem.hiddenPosition({
				of: inputItem,
				my: 'left top',	
				at: 'left bottom'
		});
};	
function showGeneralValidation(){
	$('#errorBox').delay(100).slideToggle(300);
}
function clearImg() {
	$(".btn-danger").live('click' , function(){
		$.ajax({
			type: 'POST',
			url: $(this).attr('data-url'),
			success: function(data) {					
			},
			error: function() {
				alert("Error");
			},            
			dataType: 'json'
		});
		return false
	});
}

$(function() {
//	$('input[class*="-date"]').datepicker();
	initScroll();
	initTaxonomyPopover();
	initTermsPopover();
	initHelpTooltipPopover();
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
    function evalScroll() {
		if ($("#frameExternal").length==0) {
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
}

function initTaxonomyPopover() {
    $('[rel="taxonomy-popover"]').each(function(index, element) {
    	$element = $(element);
    	$element.popover({
         placement: 'top',
         trigger: 'hover',
         title: $element.data('title'),
         content: $($element.data('target')).html()
    	});
    });
}

function initTermsPopover() {
	$('[rel="term-tooltip"]').tooltip({
		placement: 'top',
		trigger: 'hover'
	});
}

function initHelpTooltipPopover() {
    $(document).on('mouseenter', '[rel*="-help-tooltip"]', function(ev) {
		$this=$(this);
		if (!$this.next('.popover').hasClass('in')) {
	    	$this.popover({
	            placement: 'right',
	            trigger: 'manual',
	            html: true,
	            content: $this.next('[data-tooltip="help"]').html()
	       	});
			$this.popover('show');
			$this.data('bs.popover')['$tip'].data('bs.popover', $this.data('bs.popover'));
			$this.data('bs.popover')['$tip'].find('a').attr('target','new');
			$this.data('bs.popover')['$tip'].on('click', '.close-popover', function() {
		    	$popover = $(this).closest('.popover');
		    	if ($popover.data('bs.popover')) {
		    		$popover.data('bs.popover').hide();
		    	}
		    });
			$this.data('bs.popover')['$tip'].on('mouseleave', function () {
				$this.popover('hide');
			});
		}
    });
	$(document).on('mouseleave', '[rel*="-help-tooltip"]', function(ev) {
		$this=$(this);
		setTimeout(function () {
			if (!$this.next('.popover:hover').length) {
				$this.popover('hide');
			}
		}, 300);
		ev.stopPropagation();
	});
}
