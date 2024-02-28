// eFiling app 
function dropDwn(oSelect, target) {
	$(oSelect).change(function(){
			var dwnVal = $(this).val();
			if(dwnVal != '-'){
				$(target).removeAttr('disabled');
			}
			if(dwnVal == '-'){			
				$(target).attr('disabled','disabled');			
			}
		})
}

// adding cards
function addPr(){
	var addBut = $('a.addBut');
	$(addBut).click(
		function() {
			if($(this).parents('div[data-rel]').length> 0){
				var oPar = $(this).parents('div[data-rel]');
				var parCla = $(oPar).attr('data-rel');
				var typeO = parCla.substring(5);
				var cardType = 1;
				$('p[data-rel='+parCla+']').removeClass('hidden');
			}
			if($(this).parents('div[data-ap]').length > 0){
				var oPar = $(this).parents('div[data-ap]');
				var typeO = $(oPar).attr('data-ap');
				var parCla = typeO;
				var cardType = 0;
			}
				var inpFlds = $(oPar).find('.mandatory');
		
				$.each(inpFlds, function(id,value){
				});
				var thisLgnd = $(oPar).find('legend').text();			
				var num = $(oPar).find('span.numC').text()
				var newId = 'id'+ num + typeO;
				if($('.errorVal').length === 0 ){
					addRow(num,typeO,newId,cardType);
					addCard(num,typeO,newId,cardType);
					$.each(inpFlds, function(key, value){
						var inpId  = $(value).attr('id');
						var inpVal = $(value).val(); 
						var inpLab = $('label[for=' + inpId + ']').text();
						addField(inpVal,inpLab,newId);
					});
					hideForm(parCla,cardType);
				}
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
	$(newCardFlds).find('span.flLabel').text(label);
	$(newCardFlds).find('span.flField').text(val);
} 
function hideForm(parCla, cardType){
	if(cardType == 1){
			var thForm = $('div[data-rel='+parCla+']')
	}
	if(cardType == 0){
			var thForm = $('div[data-ap='+parCla+']')
	}
	var numC = $(thForm).find('span.numC').text();
	var newNum = $(thForm).find('span.numC').text((parseInt(numC))+1);
	var frmInp = $(thForm).find('input');
	$.each(frmInp, function(key,inpt){
		inpt.value = '';
		if($(inpt).attr('checked')){
			$(inpt).removeAttr('checked');
		} 
	})	
	$(thForm).find('select option[value='-']').attr('selected', 'selected');
	$(thForm).hide();
	$('p[data-rel='+parCla+']').click(function(){
		$(thForm).show();
		return false;
	});
}
function trType(type){
	var oldtr = $('tr[data-reldiv*='+type+']');
	if($(oldtr).length>0){
		var radButv = $('div[data-rel='+type+']').find('input[type=radio]');
		$(radButv).attr('disabled','disabled');
		$('div[data-rel='+type+'] input[type=radio].ok').removeAttr('disabled');
	}
}
function closeNw(){
	$('.closeNw').click(function(){
		$.colorbox.close();
	})
	
}
function tbIndex(){
	var recDivs = $('.mc_reclcard');
	var oTbtrTd = $('#formEF table.index tr td a');
	var oTbtr = $('#formEF table.index tr');
	$(recDivs).hide();
	$(oTbtrTd).click(function(){
		var attRel = $(this).parents('tr').attr('data-reldiv');
		var target = $('.mc_reclcard[data-rel='+attRel+']');
		if($(this).hasClass('delete')){
			$(this).colorbox({inline:true,innerWidth:'20%',innerHeight:'20%'});
			$(this).parents('tr').addClass('toDelete');
			$(target).addClass('toDelete');
			$('#delWarnTxt a.confirm').click(function(){$('.toDelete').remove();$.colorbox.close()});
			$('#delWarnTxt a.cancel').click(function(){$('.toDelete').removeClass('toDelete');$.colorbox.close()});
		} else {
			if($(target).css('display') == 'none'){
				$('.mc_reclcard').hide();
				$(target).show('fast');
				return false;
			} if($(target).css('display') == 'block'){
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
function delButton(){
	var delBot = $('.deleteB');
	$(delBot).click(function(){
		$(this).colorbox({inline:true,innerWidth:'20%',innerHeight:'20%'});
		$(this).closest('div.mc_repcard').addClass('toDelete');
		$('#delWarnTxt a.confirm').click(function(){$('.toDelete').remove();$.colorbox.close()});
		$('#delWarnTxt a.cancel').click(function(){$('.toDelete').removeClass('toDelete');$.colorbox.close()});
	})
}
function addCalendar(){
	var numCal = $('.dateInput');
	$.each(numCal, function(key,value){
		var tId = $(value).attr('id');
		$('#'+tId).DatePicker({
				date: $('#'+tId).val(),
				format: 'd/m/y',
				onChange: function(formated, dates) {
						$('#'+tId).val(formated);
						$('#'+tId).DatePickerHide();
					}
		});
	});
}

function init(){
	addPr();
	addCalendar();
	$('div.priorityY').hide();
	$('div.exhibitionY').hide();
	$('div.seniorityY').hide();
	$('div.irtransformationY').hide();
	dropDwn('#memberState', '#importseniority');
	dropDwn('#lastCountry', '#importpriority');
	swapVisib('.irtransformation li a', 'div.irtransformationY');
	swapVisib('.exhibition li a', 'div.exhibitionY');
	swapVisib('.priority li a', 'div.priorityY');
	swapVisib('.seniority li a', 'div.seniorityY');
}
$(window).load(function(){
	init();
});