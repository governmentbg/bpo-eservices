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

function colorSelector(oElem){
	$(oElem).ColorPicker({
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onSubmit: function(hsb, hex, rgb, el) {
			$(oElem).val('#' + hex);
			$(el).ColorPickerHide();		
			return false;
		}	
	});		
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
		var colorHex = $(this).parent().find('#selCol').val();
		if(colorHex !== ''){
			validateHex(colorHex);
		};
	});
}	

function loadColorSel(){
	var launcher = $('#formEF input[name^=hasColor]');
	var mcColSel = document.createElement('div'); 
	$(launcher).click(function(){
		var bro = $(this).parent('span').find('.active');
		$(bro).removeClass('active');
		$(this).addClass('active');	
		if($(this).hasClass('ok')){
			var wInput = $(this).closest('div');
			$(wInput).attr('id','mcColors');
			$(mcColSel).appendTo($(wInput));
			$('#colorSelBx').appendTo($(mcColSel));
			$('#colorSelBx').i18n();
		} else {
			if($(mcColSel).length > 0){
				if($('#colorSelBx input').value != ''){
					$('#colorSelBx input').val('')
					$('#colorSelBx #choosenCols li').remove();
				}
				$('#colorSelBx').appendTo($('#mylist').closest('div'));
				$(mcColSel).remove();
			}
		}
	});
}


/*
from app.js
function loadColorSel(){
	var launcher = $('#formEF input[name^=hasColor]');
	var mcColSel = document.createElement('div'); 
	$(launcher).click(function(){
		if($(this).hasClass('ok')){
			var wInput = $(this).closest('div');
			$(wInput).attr('id','mcColors');
			$(mcColSel).appendTo($(wInput));
			$('#colorSelBx').appendTo($(mcColSel));
			$('#colorSelBx').i18n();			
		} else {
			if($(mcColSel).length > 0){
				if($('#colorSelBx input').value != ''){
					$('#colorSelBx input').val('')
					$('#colorSelBx #choosenCols li').remove();
				}
				$('#colorSelBx').appendTo($('#mylist').closest('div'));
				$(mcColSel).remove();
			}
		}
	});
}

*/