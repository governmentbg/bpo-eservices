$("#lastCtmTransformation").live("focus", function ()
{
	initAutocomplete(this);
	tradeMarkAutocomplete(this, "#ctmTransformationTradeMarkConfig_searchUrlEnabled", "#ctmTransformationTradeMarkConfig_viewMessage",
			"#ctmTransformationTradeMarkConfig_searchUrl", "#ctmTransformationTradeMark_officeCode", "#ctmTransformationTradeMark_importId");
});

$('#normal #openCtmTransformation').live("click", function() {
	if ($("#transformationForm").length > 0) {
		return;
	}
	removeAllHighlightRow
	newCtmTransformationForm(this, 'claim/claim_ctmtransformation_details');
});

$('#wizard #openCtmTransformation').live("click", function() {
	if ($("#transformationForm").length > 0) {
		showWarningModal($("#cannotEditTransformationConversionSameTime").html());
		$("#openCtmTransformationLi").removeClass('active');
		return;
	}
	removeHighlightRow($('.ctmtransformation.active'));
	newCtmTransformationForm(this, 'claim/claim_ctmtransformation_details_wizard');
});

$('.importCtmTransformation').live("click", function() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $("#ctmTransformationTradeMark_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#lastCtmTransformation").val();
        if(!importId.startsWith($("#ctmTransformationTradeMark_officeCode").val())){
        	//create ST13 code from what has been entered
        	importId = generateST13ID(importId, $("#ctmTransformationTradeMark_officeCode").val());
        }
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }
	importPreviousCTM('ImportCTMTransformation', importId, $('#ctmTransformationTradeMark_officeCode').val(), $(this).attr('data-ask'));
});

$('.addCtmTransformation').live("click", function() {
	addCtmTransformationPost(true, this, 'claim/claim_ctmtransformation_details',
			'claim/claim_table_common');
});

$('.addAnotherCtmTransformation').live("click", function() {
	addCtmTransformationPost(false, this, 'claim/claim_ctmtransformation_details',
			'claim/claim_table_common');
});

$('.addCtmTransformationWizard').live("click", function() {
	addCtmTransformationPost(true, this, 'claim/claim_ctmtransformation_details_wizard',
			'claim/claim_table_common');
});

$('.addAnotherCtmTransformationWizard').live("click", function() {
	addCtmTransformationPost(false, this, 'claim/claim_ctmtransformation_details_wizard',
			'claim/claim_table_common');
});

$('.editCtmTransformationCommon').live("click", function() {
	if($('body').attr('id') == 'normal') {
		getCtmTransformationDetails($(this).attr("data-id"), 'claim/claim_ctmtransformation_details', $(this).closest('tr'));
	} else {
		getCtmTransformationDetails($(this).attr("data-id"), 'claim/claim_ctmtransformation_details_wizard', $(this).closest('tr'));
	}		
});

$('.removeCtmTransformationCommon').live("click", function() {
    var removeFunc = partial(removeCtmTransformation, $(this).attr("data-id"), 'claim/claim_table_common');
    showConfirmModal($("#conversion_remove").html(), removeFunc);
});

function newCtmTransformationForm(tabPaneObject, claimDetails) {
	$('.claimSections .cancelButton').trigger('click');
	$(".tabClaim .flMessageError").closest(".tabClaim").hide()
	$.ajax({
			url: "addTransformation.htm",
			data: "detailsView=" + claimDetails,
			cache: false,
			success: function(html){
				$("#tabCtmTransformation").html(html);
				$('#ctmTransformationCountryCode').val($('#ctmTransformationTradeMark_officeCode').val());
				$("#tabCtmTransformation").show();				
			},
			error: function (xmlHttpRequest, textStatus, errorThrown) {
				$("#tabCtmTransformation").html(errorThrown);
				$("#tabCtmTransformation").show();	
			}
	});				
}

function getCtmTransformationDetails(id, claimDetails, row) {
	$.ajax({
		url: "addTransformation.htm",
		data: "id=" + id + "&detailsView=" + claimDetails,
		cache: false,
		success: function(html){
			$("#tabCtmTransformation").html(html);
			$("#tabCtmTransformation").addClass('active');
			$("#tabCtmTransformation").show(); //for wizard
			$("#tabSeniority").removeClass('active');
			$("#tabExhibition").removeClass('active');
			$("#tabPriority").removeClass('active');
			//highlight the selected row
			highlightRow(row);		
			resetClaimsNav(row);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabCtmTransformation").html(errorThrown);
			$("#tabCtmTransformation").show();	
		}
});
}

function removeCtmTransformation(id, claimTable) {  
	$.ajax({
		url: "removeTransformation.htm",
		data: "id=" + id + "&claimTable=" + claimTable,
		cache: false,
		success: function(html){
			//If post succeded we have to make two more actions: 
			//Reload the common table
			$("#claimTableContainer").html(html);
			//If the removed one is currently being viewed in the card, hide it
			if($("#tabCtmTransformation").attr('class').indexOf("active") !=-1 && $("#tabCtmTransformation #id").val() == id) {
				$("#tabCtmTransformation").removeClass('active');
				if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Transformation tab cannot be displayed anymore in oneform
					$("#tabCtmTransformation").hide();
				}
			}

            // change text of buttons to reflect change in number of transformations
			if(parseInt($("#transformationsSizeValue").val() < parseInt($("#maxTransformations").val()))){
				changeClaimText("#wizard #openCtmTransformation", parseInt($("#transformationsSizeValue").val()));

				if ($("#wizard #openTransformation").length >0 ) {
					changeClaimText("#wizard #openTransformation", parseInt($("#transformationsSizeValue").val()));
				}
			}
			markClaimNoButtonActive(".conversion", "#noCtmTransformation");
			callGetFastTrackFails();
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#claimTableContainer").html(errorThrown);
		}
	});
}

function addCtmTransformationPost(closeDiv, tabPaneObject, claimDetails, claimTable) {
	$("#ctmTtransformationForm").find(":checkbox").val("true");
	$.ajax({
		url: "addTransformation.htm",
		data: $("#ctmTransformationForm").serialize() + 
			"&detailsView=" + claimDetails + "&claimTable=" + claimTable,
		type: "POST",
		cache: false,
		success: function(html){
			if(html.indexOf("error") != -1) {
				//If post succeded with validation errors:
				$("#tabCtmTransformation").html(html);
				$(".claimSections #ctmTransformationForm #manualDetails").show();
			} else {
				//Reload the common table
				$("#claimTableContainer").html(html);				
				if(closeDiv) {
					clearTabsDevelopers(tabPaneObject);
					$('.cancelCtmTransformationButton').trigger('click');
					$('.cancelButton').trigger('click');
				} else {
					$('#openCtmTransformation').trigger('click');
					$('#openCtmTransformation').trigger('mouseup');
				}
				callGetFastTrackFails();
			}
            // change text of buttons to reflect change in number of transformations
			if(parseInt($("#transformationsSizeValue").val() < parseInt($("#maxTransformations").val()))){
				changeClaimText("#wizard #openCtmTransformation", parseInt($("#transformationsSizeValue").val()));

				if ($("#wizard #openTransformation").length >0 ) {
					changeClaimText("#wizard #openTransformation", parseInt($("#transformationsSizeValue").val()));
				}
			}
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabCtmTransformation").html(errorThrown);
			$("#tabCtmTransformation").show();	
		}
	});
}

$('.claimSections #ctmTransformationForm #ctmCreateManualDetails').live("click", function ()
{
	$(".claimSections #ctmTransformationForm #manualDetails").show();	
});
$('#noCtmTransformation').live("click", function ()
{
	$(".claimSections #ctmTransformationForm .cancelCtmTransformationButton").trigger('click');	
});