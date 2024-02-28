$("#lastSeniority").live("focus", function ()
{
	//change the autocomplete url (depends on the selected country)
	initAutocomplete(this, "autocompleteTrademark.htm?previousCTM=false&office=" + $("#seniorityForm #country").val());
	tradeMarkAutocomplete(this, "#seniorityTradeMarkConfig_searchUrlEnabled", "#seniorityTradeMarkConfig_viewMessage",
			"#seniorityTradeMarkConfig_searchUrl", "#seniorityForm #country", "#seniorityTradeMark_importId",
            true, "#seniorityForm #country");
});

$('#normal #openSeniority').live("click", function() {
	removeAllHighlightRow();
	newSeniorityForm(this, 'claim/claim_seniority_details');
});

$('#wizard #openSeniority').live("click", function() {
	removeHighlightRow($('.seniority.active'));
	newSeniorityForm(this, 'claim/claim_seniority_details_wizard');
});

$('.importSeniority').live("click", function() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $("#seniorityTradeMark_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#lastSeniority").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }
	importPreviousCTM('ImportCTMSeniority', importId, $("#seniorityForm #country").val(), $(this).attr('data-ask'));
});

$('.addSeniority').live("click", function() {
	addSeniorityPost(true, this, 'claim/claim_seniority_details', 'claim/claim_table_common');
});

$('.addAnotherSeniority').live("click", function() {
	addSeniorityPost(false, this, 'claim/claim_seniority_details', 'claim/claim_table_common');
});

$('.addSeniorityWizard').live("click", function() {
	addSeniorityPost(true, this, 'claim/claim_seniority_details_wizard', 'claim/claim_table_common');
});

$('.addAnotherSeniorityWizard').live("click", function() {
	addSeniorityPost(false, this, 'claim/claim_seniority_details_wizard', 'claim/claim_table_common');
});

$('.editSeniorityCommon').live("click", function() {
	if($('body').attr('id') == 'normal') {
		getSeniorityDetails($(this).attr("data-id"), 'claim/claim_seniority_details', $(this).closest('tr'));
	} else {
		getSeniorityDetails($(this).attr("data-id"), 'claim/claim_seniority_details_wizard', $(this).closest('tr'));
	}		
});

$('.removeSeniorityCommon').live("click", function() {
    var removeFunc = partial(removeSeniority, $(this).attr("data-id"), 'claim/claim_table_common');
    showConfirmModal($("#seniority_remove").html(), removeFunc);
});

function newSeniorityForm(tabPaneObject, claimDetails) {
	$('.claimSections .cancelButton').trigger('click');
	$(".tabClaim .flMessageError").closest(".tabClaim").hide()
	$.ajax({
			url: "addSeniority.htm",
			data: "detailsView=" + claimDetails,
			cache: false,
			success: function(html){
				$("#tabSeniority").html(html);
				$("#tabSeniority").show();
				enableDisableSeniority();
			},
			error: function (xmlHttpRequest, textStatus, errorThrown) {
				$("#tabSeniority").html(errorThrown);
				$("#tabSeniority").show();	
			}
	});				
}

function getSeniorityDetails(id, claimDetails, row) {
	$.ajax({
		url: "addSeniority.htm",
		data: "id=" + id + "&detailsView=" + claimDetails,
		cache: false,
		success: function(html){
			$("#tabSeniority").html(html);
			$("#tabSeniority").addClass('active');
			$("#tabSeniority").show(); //for wizard
			$("#tabPriority").removeClass('active');
			$("#tabExhibition").removeClass('active');
			$("#tabTransformation").removeClass('active');
			enableDisableSeniority();
			//highlight the selected row
			highlightRow(row);		
			resetClaimsNav(row);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabSeniority").html(errorThrown);
			$("#tabSeniority").show();	
		}
});
}

function removeSeniority(id, claimTable) {  
	$.ajax({
		url: "removeSeniority.htm",
		data: "id=" + id + "&claimTable=" + claimTable,
		cache: false,
		success: function(html){
			//If post succeded we have to make two more actions: 
			//Reload the common table
			$("#claimTableContainer").html(html);
			//If the removed one is currently being viewed in the card, hide it
			if($("#tabSeniority").attr('class').indexOf("active") !=-1 && $("#tabSeniority #id").val() == id) {
				$("#tabSeniority").removeClass('active');
				enableDisableSeniority();
				if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Seniority tab cannot be displayed anymore in oneform
					$("#tabSeniority").hide();
				}
			}
            $("#openSeniority").parent(".active").removeClass("active");

            // change text of buttons to reflect change in number of seniorities
            changeClaimText("#wizard #openSeniority", parseInt($("#senioritiesSizeValue").val()));
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#claimTableContainer").html(errorThrown);
		}
	});
}

function addSeniorityPost(closeDiv, tabPaneObject, claimDetails, claimTable) {  
	$("#seniorityForm").find(":checkbox").val("true");
	
	$.ajax({
		url: "addSeniority.htm",
		data: $("#seniorityForm").serialize() + 
			"&detailsView=" + claimDetails + "&claimTable=" + claimTable,
		type: "POST",
		cache: false,
		success: function(html){
			if(html.indexOf("error") != -1) {
				//If post succeded with validation errors:
				$("#tabSeniority").html(html);
				$(".claimSections #seniorityForm #manualDetails").show();
			} else {
				//Reload the common table
				$("#claimTableContainer").html(html);
				if(closeDiv) {
					clearTabsDevelopers(tabPaneObject);
					$('.cancelSeniorityButton').trigger('click');
					$('.cancelButton').trigger('click');
				} else {
					$('#openSeniority').trigger('click');
					$('#openSeniority').trigger('mouseup');
				}
			}
            // change text of buttons to reflect change in number of seniorities
            changeClaimText("#wizard #openSeniority", parseInt($("#senioritiesSizeValue").val()));
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabSeniority").html(errorThrown);
			$("#tabSeniority").show();	
		}
	});
}

$('.claimSections #seniorityForm #createManualDetails').live("click", function ()
{
	$(".claimSections #seniorityForm #manualDetails").show();	
});

$("#seniorityForm #country").live("change", enableDisableSeniority);

$("#seniorityForm #country").load(enableDisableSeniority);

function enableDisableSeniority() {
	if($("#seniorityForm #country").val() == "") {
		$('.claimSections #seniorityForm #createManualDetails').bind('click', false);
		$('.claimSections #seniorityForm #createManualDetails').addClass('disabled');
		$('.claimSections #seniorityForm .importSeniority').bind('click', false);
		$('.claimSections #seniorityForm .importSeniority').addClass('disabled');
		$('.claimSections #seniorityForm #lastSeniority').attr('disabled','true');
		$(".claimSections #seniorityForm #manualDetails").hide();
	} else {
		$('.claimSections #seniorityForm #createManualDetails').unbind('click', false);
		$('.claimSections #seniorityForm #createManualDetails').removeClass('disabled');
		$('.claimSections #seniorityForm .importSeniority').unbind('click', false);
		$('.claimSections #seniorityForm .importSeniority').removeClass('disabled');
		$('.claimSections #seniorityForm #lastSeniority').removeAttr('disabled');
	}
}

$('#noSeniority').live("click", function ()
{
    $(".claimSections #seniorityForm .cancelSeniorityButton").trigger('click');
});