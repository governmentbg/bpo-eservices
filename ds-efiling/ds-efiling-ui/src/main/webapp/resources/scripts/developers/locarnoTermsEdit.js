var gsTerms = (function() {
	var modalItem = '#termModal';
	var activeTerm = 0;
	var invalidData = [];
	
	$(function() {
		$('#productIndicationTable:not(.notRemovableClass) a.term-search').live('click', viewDetails);
		$('#prevTerm').live('click', function(){navigateModalData(-1);});
		$('#nextTerm, #modalKeepTerm').live('click', function(){navigateModalData(1);});
		$('#modalRemoveTerm').live('click', removeItemFromModal);
		$('#modalChangeTerm').live('click', function(event){changeTerm(event);});
		$('#replaceTerm').live('click', searchTermClass) ;
		$('#searchTerm').live('click', searchTermClass);
		$(".switchType").live("click", function(event){switchTermType(event, $(this));})

	});
	
	function createInvalidArray(){
		var cantItems = termsData.length;
		var g = 0;
		invalidData = [];
		for(var i=0; i<cantItems; i++){
			if(termsData[i].validationState!=null 
				&& termsData[i].validationState !== 'valid'){
				invalidData.push(termsData[i]);	
				invalidData[g].posInArray= i;
				invalidData[g].visited=false;
				g++;
			}
		}
	}
	
	function searchTermClass(e, searchName){
		var data = $( e.target ).data('classReplace');
		$(modalItem).modal('hide');

		if(!data){
			data = $( e.target ).data('classSearch');
			$('#locarnoModalBrowser').data('flow',data);
			ModalLocarnoBrowser.locarnoToAdd = $('#locarnoModalBrowser').data('flow');
			$('#locarnoModalBrowser').modal('show');
			$('#input-search').val(data.indication);
			$('#locarnoModalBrowser select#locarnoClasses').val('-1');
			$('#locarnoModalBrowser select#locarnoSubclasses').val('-1');
			$('#search-btn').click();
		}else{
			var isClicked = this.id;
			$('#locarnoModalBrowser').data('flow',data);	
			ModalLocarnoBrowser.locarnoToAdd = $('#locarnoModalBrowser').data('flow');
			ModalLocarnoBrowser.locarnoToAdd.clickedClass = isClicked;
			Locarno.ajax.loadLocarnoClassCodes('#locarnoModalBrowser select#locarnoClasses', ModalLocarnoBrowser.setClass);
		}		
	}

	function changeTerm(event){
		
		if ($('.editableTerms li.active').size()===0) {
			showMessageModal($('#locarno_selectClass').html());
		} else {
			dataTerm = $('.editableTerms li.active a').attr('data-term');
			var n = $('.editableTerms li').index($('.active'));

			var infoTerm = dataTerm.split('|');
			var str = 'id=' + infoTerm[0] + '&term=' + infoTerm[2] + '&locarnoClass=' + infoTerm[1];
			
			$.ajax({
				url : 'modifyTerm.htm',
				type : 'GET',
				data : str,
				success : function(html) {
					Locarno.cleanMessageErrors();
	            	Locarno.setResponseInTable(html);
					$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());				}
			});

			removeFromData(activeTerm);
			if(invalidData.length === 0){ $(modalItem).modal('hide');}

			navigateModalData(1);
			event.preventDefault();

            if(invalidData.length===0) {
                $('#termModal').hide();
            }
		}
	}

	function switchTermType(event, el){
		event.preventDefault();
		var id = $(el).attr("data-val");
		Locarno.ajax.switchProductType(id);
	}
	
	function removeItem(){
		var liItem = $(this).parent().parent().closest('li');
		var pos = $('.term-list:not(.notRemovableClass)>li').index(liItem);
		var itemsAmount = $(this).closest('.term-list').find('>li').length;
		var classesAmount = $(this).closest('table').find('tr').length;
		if(itemsAmount === 1) {$(liItem).closest('tr').remove();}

		liItem.remove();
		removeFromData(pos);
	}

	function removeItemFromModal(){
		removeInvalidFromData(activeTerm);
		navigateModalData(0);
        if(invalidData.length===0) {
            $('#termModal').hide();
        }
	}

	function fillModalData(itempos){
		if (invalidData!=null && invalidData[itempos]!=null) {
			if (!invalidData[itempos].visited) {
				invalidData[itempos].visited=true;
			} else if (invalidData[itempos].visited) {
				$('#termModal .close').click();
			}

			var info = invalidData[itempos];
			var termsAmount = invalidData.length;
			$('#termsAmount span').text((itempos+1) + '/' + termsAmount);
			$('#termClass').text(info.locarnoClassSubclass);
			$('#replaceTerm').find('.replace').html(info.locarnoClass);
			$('#replaceTerm').data('classReplace',info);
			var text = $('#searchTermText').text();
			text = text.replace('{0}', '"'+info.indication+'"');
			$('#searchTerm').html(text);
			$('#searchTerm').data('classSearch',info);

		
			var modalHeader = $('h1',modalItem);
			modalHeader.text(info.termStatus);
			$(modalHeader).attr('class',info.validationState);
			$('#termReviewed').parent().attr('class',info.validationState);
			$('#termReviewed').text(info.indication);

			if(info.validationState === 'editable'){
				displayEditing(info.relatedTerms, info.id);
				$('#termsEditing').show();
				$('#modalChangeTerm').show();
				$('#modalChangeTerm').addClass('disabled');
			} else{
				$('#modalChangeTerm').hide();
				$('#termsEditing').hide();
			}
		}
	}
	function displayEditing(info, id){
		var amountRelated = 0;
		if (info != null) {
			amountRelated = info.length;
		}
		var labels = '';
		for(var i=0; i< amountRelated; i++) {
			labels += '<tr><td>';
			labels += info[i].locarnoClassSubclass;
			labels += '</td><td><ul class="editableTerms">';			
			labels += '<li><a data-show-second="';
			labels += '" data-term="';
			labels += id + '|';
			labels += info[i].locarnoClassSubclass + '|';
			labels += info[i].indication + '|';
			labels += '" href="#">';
			labels += info[i].indication;
			labels += '</a></li></ul></td></tr>';
		};
		$('#otherTerms tbody').html(labels);
		$('.editableTerms li a').bind('click', function(event) {
			$('.editableTerms li').removeClass('active');
			$(this).parent().addClass('active');
			$('#modalChangeTerm').removeClass('disabled');
			event.preventDefault();
		});
	}
	function navigateModalData(amount){
		activeTerm += amount;
		
		var termsAmount = invalidData.length;
		if(activeTerm > termsAmount -1){ activeTerm = 0;}
		if(activeTerm < 0) {activeTerm = termsAmount -1;}

		$(modalItem).fadeOut(0,function(){
			fillModalData(activeTerm);
		});
		$(modalItem).fadeIn(0);
	}
	
	function viewDetails(){
		var language = $('#firstLang').val();
	
		var initiator = $(this);
		$.ajax({
			url:'getTerms.htm',
			data:'langId='+language, 
			type:'GET',
			cache:false,
			success:function (html) {
				termsData = eval(html);

				createInvalidArray();
				var liItem = $(initiator).parent().closest('td');
				var pos = $('#addedProductIndications tr:not(:empty) > td:not(.locarno-classification,.term-validharm,.productType)').index(liItem);
				activeTerm = pos;

				fillModalData(pos);
				$(modalItem).modal('show');
			}
		});
	}
	
	function removeInvalidFromData(n){
		var generalPos = invalidData[n].posInArray;
		removedItem = invalidData.splice(n,1);
		var liItem = $('#addedProductIndications tr:not(:empty) > td:eq(' + (generalPos) +')');		

		
		var str = 'id=' + removedItem[0].id;
		$.ajax({
			url : Locarno.nav.remove,
			type : 'GET',
			data : encodeURI(str),
			success : function(html) {
				Locarno.cleanMessageErrors();
            	Locarno.setResponseInTable(html);
				$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
			}
		});

		removeFromData(generalPos);
		if(invalidData.length === 0){ $(modalItem).modal('hide'); };
	}
	function removeFromData(n){
		termsData.splice(n,1);
		createInvalidArray();
	}
})();