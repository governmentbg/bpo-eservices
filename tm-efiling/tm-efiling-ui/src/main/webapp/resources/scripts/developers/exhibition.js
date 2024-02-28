$('#normal #openExhibition').live("click", function() {
    $('input#noExhibition').removeAttr('checked');
	removeAllHighlightRow();
	newExhPriorityForm(this, 'claim/claim_exhibition_details');
});

$('#wizard #openExhibition').live("click", function() {
	removeHighlightRow($('.exhpriority.active'));
	newExhPriorityForm(this, 'claim/claim_exhibition_details_wizard');
});

$('.addExhibition').live("click", function() {
	addExhibitionPost(true, this, 'claim/claim_exhibition_details',
			'claim/claim_table_common');
});

$('.addExhibitionWizard').live("click", function() {
	addExhibitionPost(true, this, 'claim/claim_exhibition_details_wizard',
			'claim/claim_table_common');
});

$('.addAnotherExhibition').live("click", function() {
	addExhibitionPost(false, this, 'claim/claim_exhibition_details', 'claim/claim_table_common');
});

$('.addAnotherExhibitionWizard').live("click", function() {
	addExhibitionPost(false, this, 'claim/claim_exhibition_details_wizard',
			'claim/claim_table_common');
});

$('.editExhibitionCommon').live("click", function() {
	if($('body').attr('id') == 'normal') {
		getExhibitionDetails($(this).attr("data-id"), 'claim/claim_exhibition_details', $(this).closest('tr'));
	} else {
		getExhibitionDetails($(this).attr("data-id"), 'claim/claim_exhibition_details_wizard', $(this).closest('tr'));
	}		
});

$('.removeExhibitionCommon').live("click", function() {
    var removeFunc = partial(removeExhibition, $(this).attr("data-id"), 'claim/claim_table_common');
    showConfirmModal($("#exhibition_remove").html(), removeFunc);
});

function newExhPriorityForm(tabPaneObject, claimDetails) {
	$('.claimSections .cancelButton').trigger('click');
	$(".tabClaim .flMessageError").closest(".tabClaim").hide()
	$.ajax({
			url: "addExhPriority.htm",
			data: "detailsView=" + claimDetails,
			cache: false,
			success: function(html){
				$("#tabExhibition").html(html);
				$("#tabExhibition").show();								
			},
			error: function (xmlHttpRequest, textStatus, errorThrown) {
				$("#tabExhibition").html(errorThrown);
				$("#tabExhibition").show();	
			}
	});				
}

function getExhibitionDetails(id, claimDetails, row) {
	$.ajax({
		url: "addExhPriority.htm",
		data: "id=" + id + "&detailsView=" + claimDetails,
		cache: false,
		success: function(html){
			$("#tabExhibition").html(html);
			$("#tabExhibition").addClass('active');
			$("#tabExhibition").show(); //for wizard
			$("#tabSeniority").removeClass('active');
			$("#tabPriority").removeClass('active');
			$("#tabTransformation").removeClass('active');

			//highlight the selected row
			highlightRow(row);
			resetClaimsNav(row);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabExhibition").html(errorThrown);
			$("#tabExhibition").show();	
		}
});
}

function removeExhibition(id, claimTable) {  
	$.ajax({
		url: "removeExhPriority.htm",
		data: "id=" + id + "&claimTable=" + claimTable,
		cache: false,
		success: function(html){
			//If post succeded we have to make two more actions: 
			//Reload the common table
			$("#claimTableContainer").html(html);
			//If the removed one is currently being viewed in the card, hide it
			if($("#tabExhibition").attr('class').indexOf("active") !=-1 && $("#tabExhibition #id").val() == id) {
				$("#tabExhibition").removeClass('active');
				if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Priority tab cannot be displayed anymore in oneform
					$("#tabExhibition").hide();
				}
			}
            // change text of buttons to reflect change in number of exhibitions
            changeClaimText("#wizard #openExhibition", parseInt($("#exhibitionsSizeValue").val()));
            updateFeesInformation();
			callGetFastTrackFails();
			markClaimNoButtonActive(".exhpriority", "#noExhibition");
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#claimTableContainer").html(errorThrown);
		}
	});
}

function addExhibitionPost(closeDiv, tabPaneObject, claimDetails, claimTable) {
	$("#exhibitionForm").find(":checkbox").val("true");
	
	$.ajax({
		url: "addExhPriority.htm",
		data: $("#exhibitionForm").serialize() + 
		"&detailsView=" + claimDetails + "&claimTable=" + claimTable,
		type: "POST",
		cache: false,
		success: function(html){
			if(html.indexOf("error") != -1) {
				//If post succeded with validation errors:
				$("#tabExhibition").html(html);
			} else {
				//Reload the common table
				$("#claimTableContainer").html(html);
				if(closeDiv) {
					clearTabsDevelopers(tabPaneObject);
					$('.cancelExhibitionButton').trigger('click');
					$('.cancelButton').trigger('click');
				} else {
					$('#openExhibition').trigger('click');
					$('#openExhibition').trigger('mouseup');
				}
                updateFeesInformation();
				callGetFastTrackFails();
			}
            // change text of buttons to reflect change in number of exhibitions
            changeClaimText("#wizard #openExhibition", parseInt($("#exhibitionsSizeValue").val()));
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabExhibition").html(errorThrown);
			$("#tabExhibition").show();	
		}
	});
}

$('#noExhibition').live("click", function ()
{
	$(".claimSections #exhibitionForm .cancelExhibitionButton").trigger('click');	
});