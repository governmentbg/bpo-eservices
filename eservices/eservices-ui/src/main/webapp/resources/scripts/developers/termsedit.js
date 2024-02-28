var gsTerms = (function() {
	var modalItem = "#termModal";
	var activeTerm = 0;
	var invalidData = [];
	
	$(function() {
		$('.term-list:not(.notRemovableClass) a.term-search').live('click', viewDetails);
		$('#prevTerm').live('click', function(){navigateModalData(-1);});
		$('#nextTerm, #modalKeepTerm').live('click', function(){navigateModalData(1);});
		$('#modalRemoveTerm').live('click', removeItemFromModal);
		$('#modalChangeTerm').live('click', function(event){changeTerm(event);});
	});
	
	function createInvalidArray(){
		var cantItems = termsData.length;
		var g = 0;
		invalidData = [];
		for(var i=0; i<cantItems; i++){
			if(termsData[i].validationState!=null 
				&& termsData[i].validationState !== 'valid'
				&& !termsData[i].disabledRemoval){
				invalidData.push(termsData[i]);	
				invalidData[g].posInArray= i;
				invalidData[g].visited=false;
				g++;
			}
		}
	}
	

	function changeTerm(event){
		
		if ($('.editableTerms li.active').size()==0) {
			showMessageModal($("#goodsServices_selectClass").html());
		} else {
			dataTerm = $('.editableTerms li.active a').attr("data-term");
			var n = $('.editableTerms li').index($('.active'));

			var infoTerm = dataTerm.split("|");
			var str = "term=" + infoTerm[1] + "&niceClass=" + infoTerm[0] + "&langId=" + infoTerm[2] ;
			str += "&oldNiceClass=" + $("#termModal #termClass").html() + "&oldTerm=" + $("#termModal #termReviewed").html();
			
			$.ajax({
				url : "modifyTerm.htm",
				type : "GET",
				data : str,
				cache : false,
				success : function(html) {
					var languageInfo = getLanguageInfo();
					$("#tableGoodsAndServices").refreshGS({langId: infoTerm[2]});
				}
			});

			removeFromData(activeTerm);
			if(invalidData.length === 0) $(modalItem).modal('hide');

			navigateModalData(1);
			event.preventDefault();
		}
	}
	
	function removeItem(){
		var liItem = $(this).parent().parent().closest('li');
		var pos = $('.term-list:not(.notRemovableClass)>li').index(liItem);
		var itemsAmount = $(this).closest('.term-list').find('>li').length;
		var classesAmount = $(this).closest('table').find('tr').length;
		if(itemsAmount === 1) $(liItem).closest('tr').remove();

		liItem.remove();
		removeFromData(pos);
	}

	function removeItemFromModal(){
		removeInvalidFromData(activeTerm);
		navigateModalData(0);
	}

	function fillModalData(itempos){
		if (invalidData!=null && invalidData[itempos]!=null) {
			if (!invalidData[itempos].visited) {
				invalidData[itempos].visited=true;
			} else if (invalidData[itempos].visited) {
				$("#termModal .close").click();
			}

			var info = invalidData[itempos];
			var termsAmount = invalidData.length;
			$("#termsAmount span").text((itempos+1) + "/" + termsAmount);
			$("#termClass").text(info.cat);
			var modalHeader = $("h1",modalItem);
			modalHeader.text(info.termStatus);
			$(modalHeader).attr('class',info.validationState);
			$("#termReviewed").parent().attr('class',info.validationState);
			$("#termReviewed").text(info.name);

			if(info.validationState === "editable"){
				displayEditing(info.relatedTerms);
				$("#termsEditing").show();
				$('#modalChangeTerm').show();
				$('#modalChangeTerm').addClass('disabled');
			} else{
				$('#modalChangeTerm').hide();
				$("#termsEditing").hide();
			}
		}
	}
	function displayEditing(info){
		var amountRelated = info.length;
		var labels = '';
		for(var i=0; i< amountRelated; i++) {
			labels += '<tr><td>';
			labels += info[i].cat;
			labels += '</td><td><ul class="editableTerms">';			
			labels += '<li><a data-show-second="';
			labels += info[i].showSecond;
			labels += '" data-term="';
			labels += info[i].cat + "|";
			labels += info[i].name + "|";
			labels += info[i].langId;
			labels += '" href="#">';
			labels += info[i].name;
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
		if(activeTerm > termsAmount -1) activeTerm = 0;
		if(activeTerm < 0) activeTerm = termsAmount -1;

		$(modalItem).fadeOut(0,function(){
			fillModalData(activeTerm);
		});
		$(modalItem).fadeIn(0);
	}
	
	function viewDetails(){
		var language = $(this).closest("section.classes").find("ul.language-tabs").find("li.active a").attr("href");
	
		var initiator = $(this);
		$.ajax({
			url:"terms.htm",
			data:"langId="+language, 
			type:"GET",
			cache:false,
			success:function (html) {
				termsData = eval(html);

				createInvalidArray();
				var liItem = $(initiator).parent().closest('li');
				var pos = $('#tableGoodsAndServices tr:not(.template) .term-list:not(.notRemovableClass)>li:not(.term-valid)').index(liItem);
				activeTerm = pos;

				fillModalData(pos);
				$(modalItem).modal('show');
			}
		});
	}
	
	function removeInvalidFromData(n){
		var generalPos = invalidData[n].posInArray;
		removedItem = invalidData.splice(n,1);
		var liItem = $('.term-list:not(.notRemovableClass)>li:eq(' + (generalPos) +')');		

		var parentRemoveElement = $(liItem).find("li.removeTerm").find("a");
		
		var str = "term=" + removedItem[0].name + "&classId=" + removedItem[0].cat + "&langId=" + removedItem[0].langId ;
		str += ($(parentRemoveElement).attr("data-show-second")=="true"?"&showSecond=true":"");
		$.ajax({
			url : "removeTerm.htm",
			type : "POST",
			data : encodeURI(str),
			cache : false,
			success : function(html) {
				$("#tableGoodsAndServices").refreshGS({langId: removedItem[0].langId});
			}
		});

		removeFromData(generalPos);
		if(invalidData.length === 0) $(modalItem).modal('hide');
	}
	function removeFromData(n){
		termsData.splice(n,1);
		createInvalidArray();
	}
})();