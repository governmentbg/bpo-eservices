var TM = {};

// zoom the thumbnail image on the trademark card list (tm_card_list)
$(".imagezoom").live("mouseover",
function () {
	$(".imagezoom").css("cursor", "pointer");
    $(".imagezoom").tooltip({
    				html:true,
    				delay:{show: 500},
    				placement: 'right'
    			});                
});

$(".icon-arrow-down").live("click", function (){
	$("#expandGSReview"+$(this).attr("data-val")).hide();
	$("#collapseGSReview"+$(this).attr("data-val")).show();
	$("#tableremain"+$(this).attr("data-val")).show();
	if ($("#tableremoved"+$(this).attr("data-val")).length > 0){
		$("#tableremoved"+$(this).attr("data-val")).show();
	}
});

$(".icon-arrow-up").live("click", function (){
	$("#expandGSReview"+$(this).attr("data-val")).show();
	$("#collapseGSReview"+$(this).attr("data-val")).hide();
	$("#tableremain"+$(this).attr("data-val")).hide();
	if ($("#tableremoved"+$(this).attr("data-val")).length > 0){
		$("#tableremoved"+$(this).attr("data-val")).hide();
	}
});

$("#trademarkId").live("focus", function ()
{
	//change the autocomplete url (depends on the selected country)
    var office;
    if($("#categoryTradeMark-select").length == 0){
        office = $("#previousCtmConfig_officeCodesp").val();
    } else {
        office = $("#previousCtmConfig_officeCode").val();
        if(office == "") {
            return false;
        }
    }

    initAutocomplete(this, "autocompleteTrademark.htm?previousCTM=false&office=" + office);

	tradeMarkAutocomplete(this, "#previousCtmConfig_searchUrlEnabled", "#previousCtmConfig_viewMessage",
		"#previousCtmConfig_searchUrl", "#priorityForm #country", "#priorityTradeMark_importId", true,
        "#priorityForm #country");
		});

$("#trademarkId").live("blur", function ()
{
	closeTradeMarkAutocomplete(this);	
});

//Main button to add a new trademark, open the form to be fill
$("#tmTrigger").live("click", function()
{
    if(isFormOpen("#tabGshelper") || isFormOpen("#tabLicence")|| isFormOpen("#tabAppeal"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
    toggleImportTM();
    //$("#applicantCurrentNumber").html(Applicant.getApplicantCount() + 1);
});

//Main button to add a new trademark, open the form to be fill
$("#createManualDetails").live("click", function()
{
    toggleTMAdd();
    //$("#applicantCurrentNumber").html(Applicant.getApplicantCount() + 1);
});

//Button to add a new trademark to the list of trademarks
$(".addTMDetails").live("click", function (event, ignoreMatches)
{
    /*if(mustIgnoreApplicantMatches())
    {*/
        ignoreMatches = "true";
    //}
    TM.ajax.addTMPost(this, TM.nav.addTM, ignoreMatches);
    TM.global.isEdit = false;
});

//Button to add a new trademark for opposition
$(".addOppoTMDetails").live("click", function (event, ignoreMatches)
{
    TM.ajax.getNotAppGroundsPost();
});

$(".cancelButton.trademark").live("click", function ()
{
    $("#tmSection").html("");
    toggleTMAdd();
    scrollToTMsTop();
    TM.global.isEdit = false;
});

$("#editTMButton").live("click", function ()
{
    if(isFormOpen("#tabGshelper") || isFormOpen("#tabLicence")|| isFormOpen("#tabAppeal"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
    TM.global.isEdit = true;
    TM.ajax.getTMForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteTMButton").live("click", function ()
{
    var tmId = $(this).attr("data-val");
    var onConfirmYes = partial(TM.ajax.removeTM, tmId);
    showConfirmModal($("#delete_confirmation").html(), onConfirmYes);
    TM.global.isEdit = false;
});

$('.importTM').live("click", function() {
	var importId = getIdToImport();
	if (importId) {
		TM.ajax.showTMDetails(importId, false);
	}
});

$(".unpublishedImportBtn.TM").live("click", function (){
    var importId = $(this).attr("data-id");
    if (importId) {
        $("#unpublishedAppsModal").modal("hide");
        TM.ajax.showTMDetails(importId, true);
    }
});

$(".allGS").live('click', function(e) {
	//OJO: CAMBIAR ESTO PARA QUE RECONOZCA CUANDO ES MANUAL Y CUANDO ES IMPORTED
	if($("#manuallyGS").is(":hidden")){
		if($(this).is(':checked')){
		    var resetTermsButton = $('#goodsandservices .terms-header:not(.notRemovableClass) .terms-refresh');
			$("#gsSection .term-list").addClass("notRemovableClass");
			$("#gsSection .term-options").addClass("notRemovableClass");
			$("#gsSection .terms-header").addClass("notRemovableClass");
			$("a").removeClass("term-close");
			$("a").removeClass("terms-refresh");
			//Reset the original Goods and Services
            var appLang = $("#appLang").val();
            var langId = $("#gs_Languages").val();
            if(appLang != null && appLang != ""){
                langId = langId + ","+appLang;
            }

			resetTerms(langId, resetTermsButton);
		}
	}
});

$(".someGS").live('click', function(e) {
	if($("#manuallyGS").is(":hidden")){
		if($(this).is(':checked')){
			$("#gsSection .term-list").removeClass("notRemovableClass");
			$("#gsSection .term-options").removeClass("notRemovableClass");
			$("#gsSection .terms-header").removeClass("notRemovableClass");
			$(".term-list a").addClass("term-close");
			$(".term-options a").addClass("term-close");
			$(".terms-header a").addClass("terms-refresh");

            if($("#markExtentPartialWarning").length >0){
                $("#markExtentPartialWarning").show();
            }
		}
	}
});

$(".addOwner").live('click', function(e) {
	var name = $(this).closest(".ownerAdded").find("input[name=applicant]").val();
	var domicile = $(this).closest(".ownerAdded").find("input[name=domicile]").val();
	TM.ajax.displayOwnersDetails(name, domicile);
});

$(".removeApplicant").live('click', function(e) {

	var name = $(this).closest(".ownerAdded").find("input[name=applicant]").val();
	var domicile =  $(this).closest(".ownerAdded").find("input[name=domicile]").val();
	TM.ajax.removeOwnersDetails(name, domicile);
	$(this).closest(".ownerAdded").remove();
});

function getIdToImport() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $("#auto_trademarkId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#trademarkId").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#emptyImport_warning").html());
        return;
    }
    return importId;
}

TM.global = {
    isEdit : false
};

TM.nav =
{
		addTM:"addTMDetails.htm",
	    getTM:"getTMDetailsForEdit.htm",
	    removeTM:"removeTMDetails.htm",
	    importTM:"importTrademark.htm",
	    renewableTM:"renewableTrademark.htm",
	    addApp:"addOwnerDetails.htm",
	    removeApp:"removeOwnerDetails.htm",
	    cleanApps:"cleanOwnersList.htm",
        getNotAppGrounds:"getNotApplicableGrounds.htm",
        copyOwners: "refreshCopiedTMOwnersToOther.htm",
        copyRepresentatives: "refreshCopiedTMRepresentatives.htm"
};

TM.ajax =
{
	/**
	 * Display the form with the details of the selected trademark to be edited
	 * */
    displayChooseTM: function()
    {
        removeTMErrors();
        $.ajax({
            url:TM.nav.addTM,
            data:"",
            success:function (html)
            {
            	updateTMSection(html, false);
            	$("#tabTM .addTM").each(function(){
            		$(this).html($("#addButtonText").html());
            	});
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#tmSection", true);
            }
        });
    },

    refreshCopiedTMRepresentatives: function() {
        $.ajax(
            {
                url:TM.nav.copyRepresentatives,
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
    displayOwnersDetails: function(name, domicile)
    {
        $.ajax({
            url:TM.nav.addApp,
            data:"name="+name+"&domicile="+domicile,
            success:function (html)
            {
            	var $html = $(html).html();
    			if ($(html).hasClass('addOwnerskWarning')) {
    				showWarningModal($html);
    			} else {
    				updateApplicants(html, false);
    			}
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#tmSection", true);
            }
        });
    },
    
    /**
	 * Display the form with the basic details of an applicant to be added
	 * */
    removeOwnersDetails: function(name, domicile)
    {
        $.ajax({
            url:TM.nav.removeApp,
            data:"name="+name+"&domicile="+domicile,
            success:function (html)
            {
                //$("#manuallyGS").show();
            	
            	//updateApplicants(html, false);

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#tmSection", true);
            }
        });
    },
    
    /**
	 * Display the form with the basic details of an applicant to be added
	 * */
    cleanOwnersList: function()
    {
        $.ajax({
            url:TM.nav.cleanApps,
            data:"",
            success:function (html)
            {
            	//$(".applicantsContainer").html("");
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#tmSection", true);
            }
        });
    },
    /**
     * Get the list of not applicable grounds of opposition for the current legal acts
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    getNotAppGroundsPost: function()
    {
        var dataToSend = $("#tmSection form").serialize();
        $.ajax(
            {
                url:TM.nav.getNotAppGrounds,
                type:"POST",
                data:dataToSend,
                success:function (html)
                {
                    /*if(mustIgnoreApplicantMatches())
                    {*/
                        ignoreMatches = "true";
                    //}
                    if (html === 0) {
                        TM.ajax.addTMPost(this, TM.nav.addTM, ignoreMatches);
                    } else {
                        var onConfirmYes = partial(TM.ajax.addTMPost, this, TM.nav.addTM, ignoreMatches);
                        showConfirmModal($("#delete_confirmation_grounds").html().replace("{0}", html), onConfirmYes);
                    }
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.", "#tmSection", true);
                }
            });
    },
    /**
     * Posts trademark details to the server
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    addTMPost: function(sender, address, ignoreMatches)
    {
        if($("#tmGSCommentDiv").length != 0) {
            $("#tmGSCommentDiv").appendTo("#tmSection form");
        }
        var dataToSend = $("#tmSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        var isEdit = TM.global.isEdit;
        removeTMErrors();
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
                    displayTMValidationErrors(html);
                    return;
               	} else {
               		// otherwise, just refresh applicant cards
               		refreshTMCards(html);
                    var path = window.location.pathname;
                    if((path.indexOf("tm-renewal") != -1 || path.indexOf("tm-change") != -1 ||
                        path.indexOf("tm-bankruptcy") != -1 || path.indexOf("tm-surrender") != -1|| path.indexOf("tm-withdrawal") != -1) && (isEdit == false)){
                        TM.ajax.refreshCopiedTMOwnersToOther(path);
                    }
                    if(path.indexOf("tm-changerep") != -1 && (isEdit == false)){
                        TM.ajax.refreshCopiedTMRepresentatives();
                    }

                    if(path.indexOf("tm-generic") != -1){
                        getFeesInformation();
                    }
               		//$(".cancelButton.trademark").click();
               	}
                    
            },
            error:function (error)
            {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#tmSection", true);
            }
        });
    },

    refreshCopiedTMOwnersToOther: function(path) {
        $.ajax(
            {
                url:TM.nav.copyOwners,
                type:"GET",
                success:function (html)
                {
                    if(path.indexOf("tm-renewal") != -1 || path.indexOf("tm-bankruptcy") != -1
                        || path.indexOf("tm-surrender") != -1 || path.indexOf("tm-withdrawal") != -1){
                        refreshApplicantCards(html);
                    } else if( path.indexOf("tm-change")!= -1){
                        refreshHolderCards(html);
                    }
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.", "#applicantSection", true);
                }
            });
    },
    /**
     * Gets filled trade mark details form to edit
     * @param applicantId
     */
    getTMForm: function(tmId, rowNumber)
    {
    	removeTMErrors();
        $.ajax({
            url:TM.nav.getTM,
            type:"GET",
            data:"id=" + tmId,
            success:function (html)
            {
            	var imported = $(html).find("#importedId").val();
            	updateTMSection(html, imported);

                //$("#applicantCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#tabTM .addTM").each(function()
                {
                    $(this).html($("#personEditText").html());
                });
                $("#tableGoodsAndServices").refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
                refreshGSExtentSettings(html, imported);
                showTMTab("edit");

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantCardList", true);
            }
        });
    },
    
    
    /**
     * Removes trademark with id embedded in data-val attribute then refreshes applicant cards.
     * @param applicantId
     */
    removeTM: function(tmId)
    {
        removeTMErrors();
        $.ajax({
                url:TM.nav.removeTM,
                data:"id=" + tmId,
                type:"GET",
                success:function (html)
                {
                    refreshTMCards(html);
                    //refreshApplicantsPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#tmCardList", true);
                }
            }
        );
    },
    
    /**
     * Check if a TM is renewable after importing it.
     */
    renewableTM: function() {
        ignoreMatches = "true";
        TM.ajax.addTMPost(this, TM.nav.renewableTM, ignoreMatches);
    },    
    
    /**
     * Recovers the information relate to a Trademark imported from TMView.
     * @param importId
     */
    showTMDetails: function(importId, isUnpublished) {
    	var languagesFilter = $("#gs_Languages").val();
        var officeCode;
        if($("#categoryTradeMark-select").length == 0 || isUnpublished){
            officeCode = $("#previousCtmConfig_officeCodesp").val();
        } else {
            officeCode = $("#previousCtmConfig_officeCode").val();
        }

        var data = {languagesFilter: languagesFilter};
    	if (importId) {
            data = {
                importId: importId,
                languagesFilter: languagesFilter,
                officeImport: officeCode,
                unpublished: isUnpublished
            };

        }
    	showLoadingScreen();
    	$.ajax({
    		url : TM.nav.importTM,
    		data : data,
    		type : "GET",
    		cache : false,
    		success : function(html)
    		{
    			//$("#manuallyGS").hide();
    			hideLoadingScreen();
    			var $html = $(html).html();
    			var imported = $(html).find("#importedId").val();
    			var invalidState = $(html).find("#tradeMarkStatus").hasClass("error");
    			if ($(html).hasClass('importTrademarkWarning')) {
    				showWarningModal($html);
    			} else if (invalidState) {
    				showWarningModal($("#errors_invalidstate").html());
    			} else {
    				if ($html) {
                        if ($("#appLang") != null) {
                            languagesFilter = languagesFilter + "," + $("#appLang").val();
                        }
                        updateTMSection(html, imported);
                        if ($("#extentChooseMessage").length > 0) {
                            $("#extentChooseMessage").show();
                        }
                        $("#tableGoodsAndServices").refreshGS({langId: languagesFilter, languageInfo : getLanguageInfo()});
                        refreshGSExtentSettings(html, imported);

                    }
    				$("#tabTM .addTM").each(function(){
                		$(this).html($("#addButtonText").html());
                	});
    				showTMTab("edit");

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
function updateTMSection(html, imported)
{

    if($("#tmGSCommentDiv").length != 0) {
        $("#tmGSCommentDiv").remove();
    }
    refreshGSExtentSettings(html, imported);
	$("#tmSection").html(html);
    if($("#tmGSCommentDiv").length != 0) {
        $("#tmGSCommentDiv").insertAfter("#gsSection");
    }
}

function refreshGSExtentSettings(html, imported){
    if(imported == "true"){
        $("#manuallyGS").hide();

        $("a").removeClass("term-close");
        $("a").removeClass("terms-refresh");

        var allGS = $(html).find("#extent").find(".allGS");
        var someGS = $(html).find("#extent").find(".someGS");

        /*if(allGS.is(':checked')){
            $("a").removeClass("term-close");
            $("a").removeClass("terms-refresh");
        }*/
        if (someGS.is(':checked')) {
            $("#gsSection .term-list").removeClass("notRemovableClass");
            $("#gsSection .term-options").removeClass("notRemovableClass");
            $("#gsSection .terms-header").removeClass("notRemovableClass");
            $(".term-list a").addClass("term-close");
            $(".term-options a").addClass("term-close");
            $(".terms-header a").addClass("terms-refresh");

            if($("#markExtentPartialWarning").length >0){
                $("#markExtentPartialWarning").show();
            }
        }

    }
    else{
        $("#manuallyGS").show();
        $("#gsSection .term-list").removeClass("notRemovableClass");
        $("#gsSection .term-options").removeClass("notRemovableClass");
        $(".term-list a").addClass("term-close");
        $(".term-options a").addClass("term-close");
        $(".terms-header a").removeClass("terms-refresh");
        //$("#gsSection .terms-header").removeClass("notRemovableClass");
    }
}

function updateApplicants(html, imported){
	
	$(".applicantsContainer").find("input").attr('disabled', 'disabled');
	$(".applicantsContainer .addOwner").each(function(){
		$(this).html($("#remove_owner_icon").html());
		$(this).removeClass("addOwner");
		$(this).addClass("removeApplicant");
	});
	$(".applicantsContainer").append(html);
	
}

function scrollToTMsTop()
{
    scrollToContainer("#tmCardList");
}

function toggleTMAdd()
{
	TM.ajax.cleanOwnersList();
	if($("#tabTM").is(":hidden"))
    {
        $("#createManualDetails").addClass("active");
        showTMTab();
        //showTabGandS();
    }
    else
    {
    	$("#tmTrigger").removeClass("active");
        hideTMTab();
        var langId = $("#goodsandservices").find("ul.language-tabs").find("li.active").find("a").attr("href");
        cleanGS(langId);
        //hideTabGandS();
    }
}

function showTMTab(forEdit)
{
	$("#importTM").hide();
	$("#tabTM").show();
    /*if(($("#applicantCreateNew").size() == 0 || $("#applicantCreateNew").is(":hidden"))
        && forEdit != "edit")
    {*/
    if(forEdit != "edit"){
    	
    	TM.ajax.displayChooseTM();
    
    }
    $("#createManualDetails").addClass("active");
}

function hideTMTab()
{
    $("#tabTM").hide();
    $("#createManualDetails").removeClass("active");
}

function toggleImportTM()
{
	var langId = $("#goodsandservices").find("ul.language-tabs").find("li.active").find("a").attr("href");
	
	if ($("#importTM").is(":visible") || $("#tabTM").is(":visible"))
    {
        $("#tmTrigger").removeClass("active");
        hideImportTM();
        cleanGS(langId);
    }
    else if($("#importTM").is(":hidden"))
    {
        $("#tmTrigger").addClass("active");
        showImportTM();
        cleanGS(langId);
    } 
}

function showImportTM()
{
    $("#importTM").show();

    $("#tmTrigger").addClass("active");
}

function showTabGandS()
{
    $("#tabG&S").show();

    $("#createManualDetails").addClass("active");
}

function hideTabGandS()
{
	$("#tabG&S").hide();
	$("#createManualDetails").removeClass("active");
}

function hideImportTM()
{
	$("#tabTM").hide();
	$("#importTM").hide();
	//$("#tabG&S").hide();
    $("#tmTrigger").removeClass("active");
}

function mustIgnoreApplicantMatches()
{
    if ($("#importedApplicant").val() == "true")
    {
        return "true";
    }
}

function refreshTMCards(html)
{
	$("#tmCardList").html(html);
	$("#tmSection").html("");
	scrollToTMsTop();
	hideImportTM();
	updateFeesInformation();
    checkMaxTMs();
}

function displayTMValidationErrors(html)
{
	$("#tmSection").html(html);
    if($("#tmGSCommentDiv").length != 0) {
        $("#tmGSCommentDiv").insertAfter("#gsSection");
    }
}

function checkMaxTMs(){
	var maxTMs=$('#maxTMs').val();
	var rowCount = $('#tmCardList tr').length-1;
	
	if(rowCount==maxTMs){
		$("#tmTrigger").hide();
		$(".unpublishedAppSelectButton").hide();
	}else{
		$("#tmTrigger").show();
        $(".unpublishedAppSelectButton").show();
	}
}

function removeTMErrors()
{
    removePreviousErrors("#tmCardList");
    removePreviousErrors("#tmSection");
}
