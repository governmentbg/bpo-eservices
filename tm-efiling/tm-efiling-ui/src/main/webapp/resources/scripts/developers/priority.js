
$("#lastPriority").live("focus", function ()
{
	//change the autocomplete url (depends on the selected country)
	initAutocomplete(this, "autocompleteTrademark.htm?previousCTM=false&office=" + $("#priorityForm #country").val());
	tradeMarkAutocomplete(this, "#priorityTradeMarkConfig_searchUrlEnabled", "#priorityTradeMarkConfig_viewMessage",
			"#priorityTradeMarkConfig_searchUrl", "#priorityForm #country", "#priorityTradeMark_importId", true,
            "#priorityForm #country");
});

$('#normal #openPriority').live("click", function() {
    $('input#noPriority').removeAttr('checked');
	removeAllHighlightRow();
	newPriorityForm(this, 'claim/claim_priority_details');
});

$('#wizard #openPriority').live("click", function() {
	removeHighlightRow($('.priority.active'));
	newPriorityForm(this, 'claim/claim_priority_details_wizard');
});

$('.importPriority').live("click", function() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $("#priorityTradeMark_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#lastPriority").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }
    
    var showPopup = $(this).attr('data-ask');
    if (showPopup == 'true') {
    	var onConfirmYes = partial(importPreviousCTM, 'ImportCTMPriority', importId, $("#priorityForm #country").val(), showPopup);
    	showConfirmModal($("#previousCTM_importConfirmation").html(), onConfirmYes);
    } else {
    	importPreviousCTM('ImportCTMPriority', importId, $("#priorityForm #country").val(), showPopup);
    }
    
});

$('.addPriority').live("click", function() {
	addPriorityPost(true, this, 'claim/claim_priority_details',	'claim/claim_table_common');
});

$('.addAnotherPriority').live("click", function() {
	addPriorityPost(false, this, 'claim/claim_priority_details', 'claim/claim_table_common');
});

$('.addPriorityWizard').live("click", function() {
	addPriorityPost(true, this, 'claim/claim_priority_details_wizard', 'claim/claim_table_common');
});

$('.addAnotherPriorityWizard').live("click", function() {
	addPriorityPost(false, this, 'claim/claim_priority_details_wizard', 'claim/claim_table_common');
});

$('.editPriorityCommon').live("click", function() {
	if($('body').attr('id') == 'normal') {
		getPriorityDetails($(this).attr("data-id"), 'claim/claim_priority_details', $(this).closest('tr'));
	} else {
		getPriorityDetails($(this).attr("data-id"), 'claim/claim_priority_details_wizard', $(this).closest('tr'));
	}
});

$('.removePriorityCommon').live("click", function() {    
    var removeFunc = partial(removePriority, $(this).attr("data-id"), 'claim/claim_table_common');
    showConfirmModal($("#priority_remove").html(), removeFunc);
});

$('#partialInfo .addClass').live("click", function() {
	 if($(this).closest('div').find('.addClassDesc').attr('value') == "")
    {
        showWarningModal($("#partialPriority_missingInputText").html());
        return;
    }
	//First try to get the language from the select box. If it is null, then get it from the flowBean (through hidden field)
	firstLang = "";
	if($("#firstLang :selected").length > 0) {
		firstLang = $("#firstLang :selected").val();
	} else {
		firstLang = $("#storedFirstLang").val();
	}

    var classNumber = $("#partialInfo .classNumber select").val();
    var desc = $("#partialInfo .termsDescription input").val();
	
	addPartialGSClass(classNumber, desc, firstLang);
});

$('.partialGSClass .removeClass').live("click", function() {
    var removeClassFunc = partial(removePartialGSClass, $(this).closest('tr'));
    showConfirmModal($("#partialPriority_removeClass").html(), removeClassFunc);
});

$('.togglePartial').live("change", function() {
	if(!this.checked && $(".partialGoodServicesContainer table").length > 0) {
        showConfirmModal($("#partialPriority_removeGoodsServices").html(), removeAllPartialGSClass, setPartialCheckBox(true));
	} else if (!this.checked) {
		removeAllPartialGSClass();
	} else {		
		$.ajax({
			url : "hasLanguage.htm",
			type : "GET",
			cache : false,
			success : function(html) {
				if (!(html.indexOf("true")>-1)) {
					showMessageModal($("#goodsServices_selectLanguageFirst").html());
					setPartialCheckBox(false);
					$("#priorityForm #partialInfo").hide();
				}  else {
					$("#priorityForm #partialInfo").show();
				}
			}
		});
	}
});

function setPartialCheckBox(value) {
	 $("#priorityForm .togglePartial").attr('checked', value);
}

function addPartialGSClass(classId, desc, langId) {
    var addClass = partial(addPartialGSClass_actual, classId, desc, langId);
    // check for existing classes
    var foundSameClass = false;
    $(".partialGoodServicesContainer .partialGSClass .classIdText").each(function()
    {
        if(classId == $(this).val())
        {
            foundSameClass = true;
        }
    });

    if(foundSameClass)
    {
        showConfirmModal($("#partialPriority_addClassWithSameName").html(), addClass);
    }
    else
    {
        addClass();
    }
}

function addPartialGSClass_actual(classId, desc, langId)
{
    $.ajax({
        url: "addPartialGSClass.htm",
        data: $("#priorityForm").serialize() +
            "&classId=" + classId + "&langId=" + langId + "&desc=" + encodeURIComponent(desc),
        type: "POST",
        cache: false,
        success: function(html){
            $(".partialGoodServicesContainer").html(html);
            $('.addClassDesc').attr('value','');
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            $(".partialGoodServicesContainer").html(errorThrown);
        }
    });
}

function removePartialGSClass(row) {
	$.ajax({
		url: "removePartialGSClass.htm",
		data: $("#priorityForm").serialize() +
			"&classId=" + row.find('.classIdText').val(),
		type: "POST",
		cache: false,
		success: function(html){
			$(".partialGoodServicesContainer").html(html);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$(".partialGoodServicesContainer").html(errorThrown);
		}
	});
}

function removeAllPartialGSClass() {
	$.ajax({
		url: "removeAllPartialGSClass.htm",
		data: $("#priorityForm").serialize(),
		type: "POST",
		cache: false,
		success: function(html){
			$(".partialGoodServicesContainer").html(html);
			$("#priorityForm #partialInfo").hide();
			setPartialCheckBox(false);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$(".partialGoodServicesContainer").html(errorThrown);
		}
	});
}

function newPriorityForm(tabPaneObject, claimDetails) {
	$('.claimSections .cancelButton').trigger('click');
	$(".tabClaim .flMessageError").closest(".tabClaim").hide()
	$.ajax({
			url: "addPriority.htm",
			data: "detailsView=" + claimDetails,
			cache: false,
			success: function(html){
				$("#tabPriority").html(html);
				$("#tabPriority").show();
				enableDisablePriority();
			},
			error: function (xmlHttpRequest, textStatus, errorThrown) {
				$("#tabPriority").html(errorThrown);
				$("#tabPriority").show();	
			}
	});
}

function getPriorityDetails(id, claimDetails, row) {
	$.ajax({
		url: "addPriority.htm",
		data: "id=" + id + "&detailsView=" + claimDetails,
		cache: false,
		success: function(html){
			$("#tabPriority").html(html);
			$("#tabPriority").addClass('active'); //for oneform
			$("#tabPriority").show(); //for wizard
			$("#tabSeniority").removeClass('active'); //for oneform
			$("#tabExhibition").removeClass('active'); //for oneform
			$("#tabTransformation").removeClass('active'); //for oneform
			enableDisablePriority();
			//highlight the selected row
			highlightRow(row);
			resetClaimsNav(row);
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabPriority").html(errorThrown);
			$("#tabPriority").show();	
		}
	});
}

function removePriority(id, claimTable) {
	$.ajax({
		url: "removePriority.htm",
		data: "id=" + id + "&claimTable=" + claimTable,
		cache: false,
		success: function(html){
			//If post succeded we have to make two more actions:
			//Reload the common table
			$("#claimTableContainer").html(html);
			//If the removed one is currently being viewed in the edit form, hide it
			if($("#tabPriority").attr('class').indexOf("active") !=-1 && $("#tabPriority #id").val() == id) {
				$("#tabPriority").removeClass('active');
				enableDisablePriority();
				if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Priority tab cannot be displayed anymore in oneform
					$("#tabPriority").hide();
				}
			}
            $("#openPriority").parent(".active").removeClass("active");

            // change text of buttons to reflect change in number of priorities
            changeClaimText("#wizard #openPriority", parseInt($("#prioritiesSizeValue").val()));
            updateFeesInformation();
			callGetFastTrackFails();
			markClaimNoButtonActive(".priority", "#noPriority");
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#claimTableContainer").html(errorThrown);
		}
	});
}

function addPriorityPost(closeDiv, tabPaneObject, claimDetails, claimTable) {
	//Check first whether the user selected to claim partial priority but they didn't add any class
	if($("#priorityForm .togglePartial").attr('checked') == 'checked' && $(".partialGoodServicesContainer table").length < 1) {
		showWarningModal($("#partialPriority_emptyTable").html());
        return;
	}
	
	$("#priorityForm").find(":checkbox").val("true");

	$.ajax({
		url: "addPriority_new.htm",
		data: $("#priorityForm").serialize() +
			"&detailsView=" + claimDetails + "&claimTable=" + claimTable,
		type: "POST",
		cache: false,
		success: function(html){
			if(html.indexOf("error") != -1) {
				//If post succeded with validation errors:
				$("#tabPriority").html(html);
				$(".claimSections #priorityForm #manualDetails").show();
			} else {
				//Reload the common table
				$("#claimTableContainer").html(html);
				if(closeDiv) {
					clearTabsDevelopers(tabPaneObject);
					$('.cancelPriorityButton').trigger('click');
					$('.cancelButton').trigger('click');
				} else {
					$('#openPriority').trigger('click');
					$('#openPriority').trigger('mouseup');
				}
                updateFeesInformation();
				callGetFastTrackFails();
			}
            // change text of buttons to reflect change in number of priorities
            changeClaimText("#wizard #openPriority", parseInt($("#prioritiesSizeValue").val()));
		},
		error: function (xmlHttpRequest, textStatus, errorThrown) {
			$("#tabPriority").html(errorThrown);
			$("#tabPriority").show();	
		}
	});
}

$('#noPriority').live("click", function ()
{
	$(".claimSections #priorityForm .cancelPriorityButton").trigger('click');
});

$('#priorityForm #createManualDetails').live("click", function ()
{
	$("#priorityForm #manualDetails").show();	
});

$("#priorityForm #country").live("change", enableDisablePriority);

$("#priorityForm #country").load(enableDisablePriority);

function enableDisablePriority() {
	if($("#priorityForm #country").val() == "") {
		$('.claimSections #priorityForm #createManualDetails').bind('click', false);
		$('.claimSections #priorityForm #createManualDetails').addClass('disabled');
		$('.claimSections #priorityForm .importPriority').bind('click', false);
		$('.claimSections #priorityForm .importPriority').addClass('disabled');
		$('.claimSections #priorityForm #lastPriority').attr('disabled','true');
		//$(".claimSections #priorityForm #manualDetails").hide();
	} else {
		$('.claimSections #priorityForm #createManualDetails').unbind('click', false);
		$('.claimSections #priorityForm #createManualDetails').removeClass('disabled');
		$('.claimSections #priorityForm .importPriority').unbind('click', false);
		$('.claimSections #priorityForm .importPriority').removeClass('disabled');
		$('.claimSections #priorityForm #lastPriority').removeAttr('disabled');
	}
}