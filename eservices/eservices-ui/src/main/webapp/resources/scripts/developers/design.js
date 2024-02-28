var DS = {};

// zoom the thumbnail image on the design card list (tm_card_list)
$(".imagezoom").live("mouseover",
function () {
	$(".imagezoom").css("cursor", "pointer");
    $(".imagezoom").tooltip({
    				html:true,
    				delay:{show: 500},
    				placement: 'right'
    			});                
});

/* avoid carousel from cycling */
$.fn.carousel.defaults = {
	    interval: false
	  , pause: 'hover'
};

/* carousel for design images */
$(document).ready(function () {
	$('.carousel').carousel();
});

//Main button to add a new design, open the form to be fill
$("#dsTrigger").live("click", function()
{
    toggleImportDS();
});

//Main button to add a new design, open the form to be fill
$("#createManualDetails").live("click", function()
{
    toggleDSAdd();
});

//Button to add a new design to the list of designs
$(".addDSDetails").live("click", function (event, ignoreMatches)
{
    ignoreMatches = "true";
 
    DS.ajax.addDSPost(this, DS.nav.addDS, ignoreMatches);
    DS.global.isEdit = false;
});

$(".cancelButton.design").live("click", function ()
{
    $("#dsSection").html("");
    toggleDSAdd();
    scrollToDSsTop();
    DS.global.isEdit = false;
});

$("#editDSButton").live("click", function ()
{	
	DS.global.isEdit = true;
    DS.ajax.getDSForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteDSButton").live("click", function ()
{
    var dsId = $(this).attr("data-val");
    var onConfirmYes = partial(DS.ajax.removeDS, dsId);
    showConfirmModal($("#delete_design_confirmation").html(), onConfirmYes);
    DS.global.isEdit = false;
});
$("#dsRemoveAll").live("click", function ()
{
    var onConfirmYes = partial(DS.ajax.removeAllDS);
    showConfirmModal($("#delete_all_design_confirmation").html(), onConfirmYes);
    DS.global.isEdit = false;
});

$("#deleteEarlierDSButton").live("click", function ()
{
    var dsId = $(this).attr("data-val");
    var onConfirmYes = partial(DS.ajax.removeDS, dsId, 1);
    showConfirmModal($("#delete_design_confirmation").html(), onConfirmYes);
    DS.global.isEdit = false;
});

$('.importDS').live("click", function() {
	var importId = getIdDSToImport();
	if (importId) {
		if(importId.indexOf('-') != -1){
			var onConfirmYes = partial(DS.ajax.showDSDetails, importId, false);
			showConfirmModal($("#design_import_only_application").html(), onConfirmYes);
		} else {
			DS.ajax.showDSDetails(importId, false);
		}
	}
});

$(".unpublishedImportBtn.DS").live("click", function (){
    var importId = $(this).attr("data-id");
    if (importId) {
        $("#unpublishedAppsModal").modal("hide");
        DS.ajax.showDSDetails(importId, true);
    }
});

$(".addHolderDS").live('click', function(e) {
	var name = $(this).closest(".holderDSAdded").find("input[name=applicant]").val();
	var domicile = $(this).closest(".holderDSAdded").find("input[name=domicile]").val();
	DS.ajax.displayHoldersDetails(name, domicile);
});

$(".removeHolderDS").live('click', function(e) {

	var name = $(this).closest(".holderDSAdded").find("input[name=applicant]").val();
	var domicile =  $(this).closest(".holderDSAdded").find("input[name=domicile]").val();
	DS.ajax.removeHoldersDetails(name, domicile);
	$(this).closest(".holderDSAdded").remove();
});

$(".dessign_choose").live('change', function (){
	var checked = $(this).attr('checked') == 'checked';
	var id= $(this).attr('data-target');
	DS.ajax.changeDesignChecked(checked, id);
});

$(".importDesignByAppIdBtn").live('click', function(e){
    var appId = $(this).attr("data-app");
    $("#multipleDesignAppsModal").modal("hide");
    DS.ajax.showDSDetailsForAppId(appId);
});

function getIdDSToImport() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $("#auto_designId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCDS text box
        importId = $("#designId").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#emptyImport_warning_ds").html());
        return;
    }
    return importId;
}

$("#designId").live("focus", function ()
{
    initAutocomplete(this);
    designAutocomplete(this, true, "#previousDesign_viewMessage", "#previousDesign_viewUrl");
});

$("#designId").live("blur", function ()
{
    closeDesignAutocomplete(this);
});

function designAutocomplete(object, searchUrlEnabled, viewMessage, searchUrl)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).autocomplete( "option", "delay", 500 );
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        var wrappedUrl = "";
        if (searchUrlEnabled == true)
        {
            var viewMessageVar = $(viewMessage).html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getDesignNavigationUrl([
                    ["designId", item.ST13]
                ], searchUrl) + "'>" +
                "<span class='fourth-col'>" + viewMessageVar + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {
            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.Name + "</span>" +
                    "<span class='second-col'>" + item.ApplicationNumber + "</span><span class='third-col'><span style='padding-left: 10px'>" + item.RegistrationNumber+"</span></span></span></span></a>"+wrappedUrl)
                .appendTo(ul);
        }
    }
    $(object).data("autocomplete")._resizeMenu = function() {
        this.menu.element.outerWidth( 660 );
    }
    $(object).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
            var registrationNum = ui.item.RegistrationNumber.substr(0, ui.item.RegistrationNumber.indexOf("-"));
            $(object).val(registrationNum);
            return false;
        }, 100);
    });
}

function closeDesignAutocomplete(object)
{
    if (typeof $(object).data("autocomplete") !== 'undefined') {
        $(object).data("autocomplete")._renderItem = null;
    }
}

function getDesignNavigationUrl(keyValuePairArray, searchUrl)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $(searchUrl).val());
}

DS.nav =
{
		addDS:"addDSDetails.htm",
	    getDS:"getDSDetailsForEdit.htm",
	    removeDS:"removeDSDetails.htm",
	    removeAllDS:"removeAllDSDetails.htm",
        removeEarlierDS:"removeEarlierDSDetails.htm",
	    importDS:"importDesign.htm",
	    importDSByAppId:"importDesignByApplicationId.htm",
	    renewableDS:"renewableTrademark.htm",
	    addHolderDS:"addHolderDetails.htm",
	    removeHolderDS:"removeHolderDSDetails.htm",
	    cleanHoldersDS:"cleanHoldersDSList.htm",
	    copyOwners: "refreshCopiedDSOwnersToOther.htm",
        importRepresentativesDSImport: "importRepresentativeDSImport.htm",
	    changeChecked: "changeDesignChecked.htm"
};

DS.global = {
	isEdit : false	
};

DS.ajax =
{
	changeDesignChecked: function(selected, id){
		$.ajax({
			url:DS.nav.changeChecked,
			async:false,
			type:"POST",
			data:"selected="+selected+"&id="+id,
			success:function (html)
            {
				if($(html).hasClass('dsSelectWarning')){
					var $html = $(html).html();
	    			showWarningModal($html);
				}
            	updateFeesInformation();
            },
			error:function (error)
			{
				genericHandleError("An error occured while processing your request. Please try again later.",
						"#dsSection", true);
			}
		});
	},
	/**
	 * Display the form with the details of the selected design to be edited
	 * */
    displayChooseDS: function()
    {
        removeDSErrors();
        $.ajax({
            url:DS.nav.addDS,
            data:"",
            success:function (html)
            {
            	updateDSSection(html, false);
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#dsSection", true);
            }
        });
    },
    
    refreshCopiedDSOwnersToOther: function(path) {
    	$.ajax(
        {
        	url:DS.nav.copyOwners,
            type:"GET",
            success:function (html)
            {
            	if(path.indexOf("ds-renewal") != -1 || path.indexOf("ds-bankruptcy")!= -1 || path.indexOf("ds-surrender")!= -1 || path.indexOf("ds-withdrawal")!= -1 || path.indexOf("ds-appeal")!= -1){
            		refreshApplicantCards(html);    
            	} else if( path.indexOf("ds-change")!= -1){
            		refreshHolderCards(html);
            	}
            },
            error:function (error)
            {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#applicantSection", true);
            }
        });
    },

    refreshCopiedDSRepresentatives: function() {
        $.ajax(
            {
                url:DS.nav.importRepresentativesDSImport,
                type:"GET",
                success:function (html)
                {
                    refreshRepresentativeCards(html);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.", "#representativeSection", true);
                }
            });
    },
    
    /**
	 * Display the form with the basic details of an applicant to be added
	 * */
    displayHoldersDetails: function(name, domicile)
    {
        $.ajax({
            url:DS.nav.addHolderDS,
            data:"name="+name+"&domicile="+domicile,
            success:function (html)
            {
            	var $html = $(html).html();
    			if ($(html).hasClass('addHolderskWarning')) {
    				showWarningModal($html);
    			} else {
    				updateHoldersDS(html, false);
    			}
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#dsSection", true);
            }
        });
    },
    
    /**
	 * Display the form with the basic details of an applicant to be added
	 * */
    removeHoldersDetails: function(name, domicile)
    {
        $.ajax({
            url:DS.nav.removeHolderDS,
            data:"name="+name+"&domicile="+domicile,
            success:function (html)
            {
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#dsSection", true);
            }
        });
    },
    
    /**
	 * Display the form with the basic details of an applicant to be added
	 * */
    cleanHoldersDSList: function()
    {
        $.ajax({
            url:DS.nav.cleanHoldersDS,
            data:"",
            success:function (html)
            {
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#dsSection", true);
            }
        });
    },
    
    /**
     * Posts design details to the server
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    addDSPost: function(sender, address, ignoreMatches)
    {
        var dataToSend = $("#dsSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        var isEdit = DS.global.isEdit;
        removeDSErrors();
        $.ajax(
        {
        	url:address,
            type:"POST",
            data:dataToSend,
            success:function (html)
            {
            	            	
                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true")
                {
                	// then display the errors
                    displayDSValidationErrors(html);
                    return;
               	} else {
               		// otherwise, just refresh applicant cards
               		refreshDSCards(html);
                    var path = window.location.pathname;
                    if((path.indexOf("ds-renewal") != -1 || path.indexOf("ds-change") != -1 ||
                        path.indexOf("ds-bankruptcy") != -1|| path.indexOf("ds-withdrawal") != -1|| path.indexOf("ds-appeal") != -1||
                        path.indexOf("ds-surrender") != -1) && (isEdit == false)){
                        DS.ajax.refreshCopiedDSOwnersToOther(path);
                    }

                    if(path.indexOf("ds-changerep") != -1 && (isEdit == false)){
                        DS.ajax.refreshCopiedDSRepresentatives();
                    }
               	}
                    
            },
            error:function (error)
            {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#dsSection", true);
            }
        });
    },
    /**
     * Gets filled trade mark details form to edit
     * @param applicantId
     */
    getDSForm: function(dsId, rowNumber)
    {
    	removeDSErrors();
        $.ajax({
            url:DS.nav.getDS,
            type:"GET",
            data:"id=" + dsId,
            success:function (html)
            {
            	var imported = $(html).find("#importedId").val();
            	updateDSSection(html, imported);

                $("#dsSection .addDS").each(function()
                {
                    $(this).html($("#personEditText").html());
                });
                showDSTab("edit");

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#dsCardList", true);
            }
        });
    },
    
    
    /**
     * Removes design with id embedded in data-val attribute then refreshes applicant cards.
     * @param applicantId
     */
    removeDS: function(tmId, earlier)
    {
        removeDSErrors();
        $.ajax({
                url: (!earlier?DS.nav.removeDS:DS.nav.removeEarlierDS),
                data:"id=" + tmId,
                type:"GET",
                success:function (html)
                {
                    refreshDSCards(html);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#dsCardList", true);
                }
            }
        );
    },
    removeAllDS: function()
    {
        removeDSErrors();
        $.ajax({
                url: DS.nav.removeAllDS,
                type:"GET",
                success:function (html)
                {
                    refreshDSCards(html);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#dsCardList", true);
                }
            }
        );
    },
    
    /**
     * Check if a DS is renewable after importing it.
     */
    renewableDS: function() {
        ignoreMatches = "true";
        DS.ajax.addDSPost(this, DS.nav.renewableDS, ignoreMatches);
    },    
    
    /**
     * Recovers the information relate to a Trademark imported from DSView.
     * @param importId
     */
    showDSDetails: function(importId, isUnpublished) {
    	var data = {};
    	if (importId) {
    		data = {
    				importId: importId,
                    unpublished: isUnpublished
    			};
    	}
    	showLoadingScreen();
    	$.ajax({
    		url : DS.nav.importDS,
    		data : data,
    		type : "GET",
    		cache : false,
    		success : function(html)
    		{
    			hideLoadingScreen();
    			var $html = $(html).html();
    			var imported = $(html).find("#importedId").val();
    			if ($(html).hasClass('importDesignWarning')) {
    				showWarningModal($html);
    			} else if($(html).hasClass('designChooseApplication')) {
                    showMultipleDesignAppsModal($html);
                } else {
    			    postImportDesigns(html);

    				//showDSTab("edit");
    				
    				//show custom warning message
    				/*var $warning = $("#customWarningMessage").html();
    				if($warning) {
    					showWarningModal($warning);
    				}*/
    			}
    		},
    		error:function (error)
            {
            	hideLoadingScreen();
            	showWarningModal($("#generic_errors_service_fail").html());
            }

    	});
    },
    showDSDetailsForAppId: function(appId) {
        var data = {};
        if (appId) {
            data = {
                applicationId: appId,
            };
        }
        showLoadingScreen();
        $.ajax({
            url : DS.nav.importDSByAppId,
            data : data,
            type : "GET",
            cache : false,
            success : function(html)
            {
                hideLoadingScreen();
                var $html = $(html).html();
                if ($(html).hasClass('importDesignWarning')) {
                    showWarningModal($html);
                } else {
                    postImportDesigns(html);
                }
            },
            error:function (error)
            {
                hideLoadingScreen();
                showWarningModal($("#generic_errors_service_fail").html());
            }

        });
    }
};

function postImportDesigns(html){
    if ($(html).html()) {
        //updateDSSection(html, imported);
        refreshDSCards(html);
        var path = window.location.pathname;
        if((path.indexOf("ds-renewal") != -1 || path.indexOf("ds-change") != -1 || path.indexOf("ds-withdrawal") != -1 || path.indexOf("ds-appeal") != -1
            || path.indexOf("ds-bankruptcy") != -1 || path.indexOf("ds-surrender") != -1) && (DS.global.isEdit == false)){
            DS.ajax.refreshCopiedDSOwnersToOther(path);
        }

        if(path.indexOf("ds-changerep") != -1 && (DS.global.isEdit == false)){
            DS.ajax.refreshCopiedDSRepresentatives();
        }
        updateFeesInformation();
    }
}
/**
 * <p>
 * Function called each time the fields for the applicant need to be shown.
 * It fills the #tmSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the applicant is being added or edited.
 * If the applicant is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateDSSection(html, imported)
{		
	$("#dsSection").html(html);
}

function updateHoldersDS(html, imported){
	
	$(".holdersDSContainer").find("input").attr('disabled', 'disabled');
	$(".holdersDSContainer .addHolderDS").each(function(){
		$(this).html($("#remove_owner_icon").html());
		$(this).removeClass("addHolderDS");
		$(this).addClass("removeHolderDS");
	});
	$(".holdersDSContainer").append(html);
	
}

function scrollToDSsTop()
{
    scrollToContainer("#dsCardList");
}

function toggleDSAdd()
{
	DS.ajax.cleanHoldersDSList();
	if($("#tabDS").is(":hidden"))
    {
        $("#createManualDetails").addClass("active");
        showDSTab();
    }
    else
    {
    	$("#dsTrigger").removeClass("active");
        hideDSTab();
    }
}

function showDSTab(forEdit)
{
	$("#importDS").hide();
	$("#tabDS").show();

    if(forEdit != "edit"){
    	
    	DS.ajax.displayChooseDS();
    
    }
    $("#createManualDetails").addClass("active");
}

function hideDSTab()
{
    $("#tabDS").hide();
    $("#createManualDetails").removeClass("active");
}

function toggleImportDS()
{
	
	if ($("#importDS").is(":visible") || $("#tabDS").is(":visible"))
    {
        $("#dsTrigger").removeClass("active");
        hideImportDS();
    }
    else if($("#importDS").is(":hidden"))
    {
        $("#dsTrigger").addClass("active");
        showImportDS();
    } 
}

function showImportDS()
{
	$("#designId").val('');
    $("#importDS").show();
    $("#dsTrigger").addClass("active");
}

function hideImportDS()
{
	$("#tabDS").hide();
	$("#importDS").hide();
	//$("#tabG&S").hide();
    $("#dsTrigger").removeClass("active");
}

function mustIgnoreApplicantMatchesDS()
{
    if ($("#importedApplicant").val() == "true")
    {
        return "true";
    }
}

function refreshDSCards(html)
{
	$("#dsCardList").html(html);
	scrollToDSsTop();
	hideImportDS();
	updateFeesInformation();
    checkMaxDSs();
}

function displayDSValidationErrors(html)
{
	$("#dsSection").html(html);
}

function checkMaxDSs(){
	var maxDSs=$('#maxDSs').val();
	var rowCount = $('#dsCardList tr').length-1;
	
	if(rowCount==maxDSs){
		$("#dsTrigger").hide();
	}else{
		$("#dsTrigger").show();
	}
}

function removeDSErrors()
{
    removePreviousErrors("#dsCardList");
    removePreviousErrors("#dsSection");
}

$("#searchForDesignBtn").live("click", function()
{
    window.open($(this).attr("data-ask"));
});


