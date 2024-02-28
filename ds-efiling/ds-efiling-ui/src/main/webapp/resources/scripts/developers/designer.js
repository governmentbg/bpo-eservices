var Designer = {
    
	correspondenceAddressIndex: 0,
    numberOfAddedCorrespondenceAddresses: 0,
    
    getMaxCorrespondenceAddresses: function() {
        return parseInt($("#designerSection #maxCorrespondenceAddresses").val());
    },
    
    getDesignerCount: function() {
        return Person.numberOfTableRowsNoHeader("#userDataDesigners") + Person.numberOfTableRowsNoHeader("#addedDesigners");
    },
    
    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='designer_id_']").each(function(){
        	var obj = new Object();
        	$(this).find("td").each(function(){
                obj[$(this).attr("data-val")] = $(this).html().trim();
            });
            list.push(obj);
        });
        return list;
    },
    
    buildTableFromList: function(tableSelector, list) {
        // clear table by removing all rows except first, which is the header
        $(tableSelector).find("tr:gt(0)").remove();

        for(var i = 0; i < list.length; i++){
            var row = "<tr id='designer_id_" + (i + 1) + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            if (!!list[i].id) {
            	row += "<td data-val='id'>";
            	row += list[i].id;
            	row += "</td>";
            }
            
            row += "<td data-val='name'>";
            row += list[i].name;
            row += "</td>";

            if (!!list[i].designNumbers) { // multiple design application
            	row += "<td data-val='designNumbers'>";
            	row += list[i].designNumbers;
            	row += "</td>";
            }
            
            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },
    
    sort: function(tableSelector, property, descending) {
        var list = Designer.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Designer.buildTableFromList(tableSelector, list);
    }
};

Designer.nav = {
	addDesigner: "addDesigner.htm",
	displayWaiver: "displayDesignerWaiver.htm", 
	displayGroup: "displayDesignerGroup.htm",
	displayNotAGroup: "displayDesignerNotAGroup.htm",
    getForEdit:"getDesignerForEdit.htm",
    remove:"removeDesigner.htm",
    import: "importDesigner.htm"
};

Designer.ajax = {
		
	displayAddWaiver: function() {
		removeDesignerErrors();
		$.ajax({
			url: Designer.nav.displayWaiver,
			type: "GET",
			data: "",
			success: function(html) {
				updateDesignerSection(html, false);
				// Set checkbox designer waives.
                $("#checkWaiver").prop("checked", true);
                $("#topButtons").remove();
				postUpdateDesignerSection();
			},
			error: function(error) {
				genericHandleError("An error occured while processing your request. Please try again later.", "#designerSection", true);
			}
		});
	},
	
	displayAddGroup: function() {
		removeDesignerErrors();
		$.ajax({
			url: Designer.nav.displayGroup,
			type: "GET",
			data: "",
			success: function(html) {
				updateDesignerSection(html, false);
				// Set checkbox designer belongs to a group of designers.
                $("#checkBelongs").prop("checked", true);
				postUpdateDesignerSection();
			},
			error: function(error) {
				genericHandleError("An error occured while processing your request. Please try again later.", "#designerSection", true);
			}
		});
	},
	
	displayAddNotAGroup: function() {
		removeDesignerErrors();
		$.ajax({
			url: Designer.nav.displayNotAGroup,
			type: "GET",
			data: "",
			success: function(html) {
				updateDesignerSection(html, false);
				postUpdateDesignerSection();
			}, error: function(error) {
				genericHandleError("An error occured while processing your request. Please try again later.", "#designerSection", true);
			}
		});
	},
  
    /**
     * Gets filled designer form to edit.
     * @param designerId
     */
    getDesignerForm: function(designerId, rowNumber) {
    	removeDesignerErrors();
        $.ajax({
            url: Designer.nav.getForEdit,
            type: "GET",
            data: "id=" + designerId,
            success: function(html) {
            	updateDesignerSection(html, true);
            	$("#designerCurrentNumber").html(rowNumber);
               
                showDesignerTab("edit");
                postUpdateDesignerSection();
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#designerCardList", true);
            }
        });
    },
    
    /**
     * Removes designer with id embedded in sender's data-val attribute then refreshes designer cards.
     * @param designerId
     */
    removeDesigner: function(designerId) {
    	removeDesignerErrors();
        $.ajax({
        	url: Designer.nav.remove,
            data: "id=" + designerId,
            type: "GET",
            success: function(html) {
            	refreshDesignerCards(html);
            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#designerCardList", true);
            }
        });
    },
    
    /**
     * Imports an designer.
     * @param designerId designer's id
     */
    importDesigner: function(designerId, keepDesignsLinked) {
        if (designerId == null || designerId.trim() == "") {
            showWarningModal($("#designer_missingInputText").html());
            return;
        }
        removeDesignerErrors();
        // Disable the page to not allow the user to click anything else while the process ends.
        showLoadingScreen();
        var designerDesignsHtml = "";
        if(keepDesignsLinked && $("#tabDesigner").is(":visible") && $("#tabDesigner #designerDesignsLinkedSection").length >0){
            designerDesignsHtml = $("#tabDesigner #designerDesignsLinkedSection").html();
        }
        $.ajax({
            url: Designer.nav.import,
            data: "id=" + escape(designerId),
            type: "GET",
            success: function(html) {
                updateDesignerSection(html, true);

                if (Person.containsImportError(html)) {
                    hideLoadingScreen();
                    return;
                }

                // set this so that adding doesn't check for matches anymore
                $("#importedDesigner").attr("value", "true");

                postUpdateDesignerSection();
                if(keepDesignsLinked && designerDesignsHtml != ""){
                    $("#tabDesigner #designerDesignsLinkedSection").html(designerDesignsHtml);
                }
                hideLoadingScreen();
            },
            error: function(error) {
                hideLoadingScreen();
                genericHandleError($("#designer_cannotImport").html(), "#designerSection", true);
            }
        });
    },
    
    /**
     * Posts designer information to server.
     * @param sender
     * @param address
     */
    addDesignerPost: function(address, ignoreMatches) {
        var dataToSend = $("#designerSection form").serialize();
        if (ignoreMatches) {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeDesignerErrors();
        $.ajax({
        	url: address,
        	type: "POST",
            async: false,
            data: dataToSend,
            success: function(html) {
            	// Check if returned form is a result of designers matching
                if ($("input#designerMatchesExist", $(html)).val() == "true") {
                	// show popup with matches
                    $("#designerMatches").html(html);
                    $("#doublesDesigner").modal("show");
                    return;
                }

                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                	// then display the errors
                    displayDesignerValidationErrors(html);
                    return;
                }

                // otherwise, just refresh designer cards
                refreshDesignerCards(html);
                $(".cancelButton.designer").click();
                
                ds.efiling.application.oneForm.refreshApplicantsTableWithAssociatedDesigner();
            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#designerSection", true);
            }
        });
    }
};


$("#designerTrigger").live("click", function() {
	if (ds.efiling.application.isOneForm()) {
		ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, addDesigner);
	} else {
		addDesigner();
	}
});

function addDesigner() {
	toggleDesignerAdd();
    $("#designerCurrentNumber").html(Designer.getDesignerCount() + 1);
}

function toggleDesignerAdd() {
	if($("#tabDesigner").is(":hidden")){
		$("#designerTrigger").addClass("active");
        showDesignerTab();
    } else {
        $("#designerTrigger").removeClass("active");
        hideDesignerTab();
    }
}

function showDesignerTab(forEdit) {
	$("#tabDesigner").show();
    if (($("#designerCreateNew").size() == 0 || $("#designerCreateNew").is(":hidden")) && forEdit != "edit") {
        Designer.ajax.displayAddNotAGroup();
    }
    $("#designerTrigger").addClass("active");
}

function hideDesignerTab() {
    $("#tabDesigner").hide();
    $("#designerSection").html("");
    $("#designerTrigger").removeClass("active");
    
    $("#designerCardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToDesignersTop() {
    scrollToContainer("#designerCardList");
}

function designerResetCACounter() {
    Designer.correspondenceAddressIndex = 0;
    Designer.numberOfAddedCorrespondenceAddresses = 0;
}

$("#checkWaiver").live("click", function(){
	if ($(this).is(":checked")){
		Designer.ajax.displayAddWaiver();
	} else {
		Designer.ajax.displayAddNotAGroup();
	}
});

$("#checkBelongs").live("click", function(){
	if ($(this).is(":checked")){
		Designer.ajax.displayAddGroup();
	} else {
		Designer.ajax.displayAddNotAGroup();
	}
});

$(".cancelButton.designer").live("click", function(){
	$("#designerSection").html("");
	toggleDesignerAdd();
	scrollToDesignersTop();
	designerResetCACounter();
	//$("#designerCardList").find("tr").each(function(){
     //   $(this).removeClass("active");
    //})
});

function refreshDesignerCards(html) {
    $("#designerCardList").html(html);
    scrollToDesignersTop();
}

function displayDesignerValidationErrors(html) {
    updateDesignerSection(html, true);
    postUpdateDesignerSection();
}

function mustIgnoreDesignerMatches(){
	return $("#importedDesigner").val() == "true";
}

$(".addDesigner").live("click", function(e, ignoreMatches) {
	if (ds.efiling.application.isOneForm()) {
		ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, partial(saveDesigner, ignoreMatches));
	} else {
		saveDesigner(ignoreMatches);
	}
});

function saveDesigner(ignoreMatches) {
 	// We have to select the designs before sending them to the server.
	DesignsLink.selectOptionsDesignerLists();
	
	if(mustIgnoreDesignerMatches()) {
        ignoreMatches = "true";
    }
	Designer.ajax.addDesignerPost(Designer.nav.addDesigner, ignoreMatches);
}

$("#addDesignerTopButton").live("click", function (e, ignoreMatches) {
    $(".addDesigner").trigger("click", [ignoreMatches]);
});




function removeDesignerErrors() {
    removePreviousErrors("#designerCardList");
    removePreviousErrors("#designerSection");
}

$("#editDesignerButton").live("click", function() {
    Designer.ajax.getDesignerForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteDesignerButton").live("click", function() {
    var designerId = $(this).attr("data-val");
    var onConfirmYes = partial(Designer.ajax.removeDesigner, designerId);
    showConfirmModal($("#designer_deleteConfirmation").html(), onConfirmYes);
});

$('#designerImportButton').live("click", function() {
    var id = $("#designerImportTextfield").val();
    if (id.trim() == "") {
        showConfirmModal($("#designer_missingInputText").html());
        return;
    }
    if($("#designerImportTextfield").attr("can-import")) {
        Designer.ajax.importDesigner(id);
    } else {
        showModal($("#designer_select_first").html(), function() {
            return;
        });
    }
});

$(".importDesignerFromMatches").live("click", function() {
    Designer.ajax.importDesigner($(this).attr("data-val"), true);
    $("#doublesDesigner").modal("hide");
});

$("#addDesignerAnyway").live("click", function() {
    $("#tabDesigner .addDesigner").trigger("click", ["true"]);
    $("#doublesDesigner").modal("hide");
});



/**
 * <p>
 * Function called each time the fields for the designer need to be shown.
 * It fills the #designerSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the designer is being added or edited.
 * If the designer is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateDesignerSection(html, forEditing) {
    if (Person.containsImportError(html)) {
        showWarningModal($(".importError", $(html)).html());
        return;
    }

    $("#designerSection").html(html);
    $("#designerSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Designer.numberOfAddedCorrespondenceAddresses = parseInt($("#designerSection #numberOfCA").val());
    Designer.correspondenceAddressIndex = Designer.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Designer.numberOfAddedCorrespondenceAddresses > 0) {
        designerCorrespAddrTemplate_Create("#designerSection");
        $("#designerSection #designerHasCorrespondenceAddress").attr("checked", "checked");
        if (Designer.numberOfAddedCorrespondenceAddresses < Designer.getMaxCorrespondenceAddresses()) {
            $("#designerSection .addAnotherCA").show();
        }
        if (Designer.getMaxCorrespondenceAddresses() <= 1) {
            $("#designerSection .removeCA").hide();
        }
    }

    // Set waiver y belongs in the checboxes.
    if ($("#designerSection form #designerWaiverHidden").val() == "true"){
    	$("#checkWaiver").prop('checked', true);
    }
    
    if ($("#designerSection form #designerBelongsHidden").val() == "true"){
    	$("#checkBelongs").prop('checked', true);
    }
    
    designerCorrespAddrTemplate_Neutralize("#designerSection #correspondenceAddressPath");
}

function designerCorrespAddrTemplate_Create(containerQuery) {
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#designerSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function designerCorrespAddrTemplate_Neutralize(pathContainerQuery) {
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined) {
        return;
    }
    designerCorrespAddrTemplate_Modify("#designerSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function designerCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement) {
    $(containerQuery + " [name^='" + currentPath + "']").each(function () {
        var name = $(this).attr("name");
        var id = $(this).attr("id");

        $(this).attr("name", name.replace(currentPath, replacement));
        $(this).attr("id", id.replace(currentPath, replacement));

        $(this).removeAttr("value");
    });
    $(containerQuery + " [for^='" + currentPath + "']").each(function () {
        var forAttr = $(this).attr("for");
        $(this).attr("for", forAttr.replace(currentPath, replacement));
    });
}

/**
 * This function specifies what should happen when the user clicks the
 * checkbox indicating whether they desire to add different correspondence addresses.
 */
$("#designerHasCorrespondenceAddress").live("click", function() {
    if ($(this).is(":checked")) {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#designerSection #correspondenceAddressPath").val();
        designerAppendCA(path);
        $("#designerSection [id^='correspondenceAddresses'][id$='stateprovince']").each(function() {
            Person.handleCorrespondenceAddressStates(this, "#designerSection");
        });
    } else {
        // remove all added correspondence addresses:
        $("#designerSection #correspondenceAddressList").html("");
        $("#designerSection .addAnotherCA").hide();
        Designer.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Designer object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function designerAppendCA(path) {
    $("#designerSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#designerSection #correspondenceAddressTemplate").html();

    Designer.correspondenceAddressIndex += 1;
    Designer.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#designerSection #correspondenceAddressList").append(template);

    if (Designer.getMaxCorrespondenceAddresses() <= Designer.numberOfAddedCorrespondenceAddresses) {
        $("#designerSection .addAnotherCA").hide();
    }
    if (Designer.getMaxCorrespondenceAddresses() <= 1) {
        $("#designerSection .removeCA").hide();
    }

    designerCorrespAddrTemplate_Modify("#designerSection #correspondenceAddressList", "PLACEHOLDER", path);
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#designerSection .addCA").live("click", function() {
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Designer.correspondenceAddressIndex + "]";
    designerAppendCA(path);
});

$("#designerSection .addAnotherCA").live("click", function() {
    var path = $("#designerSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Designer.correspondenceAddressIndex + "]";
    designerAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Designer number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#designerSection .removeCA").live("click", function() {
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Designer.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Designer.numberOfAddedCorrespondenceAddresses == 0) {
        $("#designerSection #designerHasCorrespondenceAddress").removeAttr("checked");
        $("#designerSection .addAnotherCA").hide();
    }

    if (Designer.numberOfAddedCorrespondenceAddresses > 0 &&
    		Designer.numberOfAddedCorrespondenceAddresses < Designer.getMaxCorrespondenceAddresses()) {
        $("#designerSection .addAnotherCA").show();
    }
});

$("a[href='#tabDesigner']").live("click", function() {
    if (($("#designerConfig_searchEnabled").val() == "true") && ($("#service_designerMatches_enabled") == "true")) {
        showWarningModal($("#designerCreationInfo").html());
    }
});


$("#designerImportTextfield").live("focus", function() {
    $("#designerImportTextfield").removeAttr("can-import");
    initAutocomplete(this);
    if($(this).data("autocomplete") == null) {
        // autocomplete not enabled
        return;
    }
 
    $(this).data("autocomplete")._renderItem = function(ul, item) {
    	var searchUrlEnabled = $("#designerConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true") {
            var viewMessage = $("#designerConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                			getDesignerAutocompleteNavigationUrl([["designerId", item.did]]) + 
                			"'>" +
                			"<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.dnm + "</span>" +
                "<span class='second-col'>" + item.did + "</span>" +
                "</span></span></a>" + wrappedUrl)
            .appendTo(ul);
    };
    $(this).bind("autocompleteselect", function(event, ui) {
        setTimeout(function() {
        	if(ui.item == null) {
                return;
            }

            $("#designerImportTextfield").attr("can-import", 1);
            $("#designerImportTextfield").val(ui.item.did);
            return false;
        }, 100);
    });
});

function getDesignerAutocompleteNavigationUrl(keyValuePairArray) {
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#designerConfig_searchUrl").val());
}

function postUpdateDesignerSection() {
    if($("#designerSection select[name='nationality']").val() == "US") {
        designerDisplayStateOfIncorporation(true);
    } else {
        designerDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".designerNaturalPersonTip", $("#designerSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
    		$("#designerSection select[name='address.country']").val(),
    		"#designerSection select[name='address.stateprovince']",
    		$("#designerSection input[id='address.selectedstate']").val());

    $("#designerSection select[id^='correspondenceAddresses'][id$='country']").each(function() {
        Person.handleCorrespondenceAddressStates(this, "#designerSection");
    });

    addCreateNewDesignerOnChangeFunctionality();
}

$("#designerSection select[name='nationality']").live("change", function() {
    if($(this).val() == "US") {
    	designerDisplayStateOfIncorporation(true);
    } else {
    	designerDisplayStateOfIncorporation(false);
    }
});

function designerDisplayStateOfIncorporation(display) {
	if(display) {
        Person.fillStatesSelectbox("US",
        		"#designerSection select[id='stateOfIncorporation']",
        		$("#designerSection input[id='selectedStateOfIncorporation']").val());
        $("#designerSection select[id='stateOfIncorporation']").parent().show();
    } else {
        $("#designerSection select[id='stateOfIncorporation']").val("");
        $("#designerSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#designerSection select[name='address.country']").live("change", function() {
    Person.fillStatesSelectbox($(this).val(), "#designerSection select[name='address.stateprovince']");
});
$("#designerSection select[name='nationality']").live("change", function() {
    Person.showNaturalPersonTipForBG(".designerNaturalPersonTip", $(this).val());
});

$("#designerSection select[id^='correspondenceAddresses'][id$='country']").live("change", function() {
    Person.handleCorrespondenceAddressStates(this, "#designerSection");
});

function refreshApplicantsPayerSection() {
	// Only refresh the payer list box if the payment section is present in the page.
	if ($("#paymentOptions").length > 0) {
		var paymentData = $('#paymentForm').serialize();
		$.get('getDSPaymentOptions.htm', paymentData, function(data) {
			$('#paymentOptions').html(data);
			$('#paymentDetails').show();
			showHidePayerDetailsDiv();
		});
	}
}

$("#designerCreateNew").live("click", function() {
    Designer.ajax.displayAddNotAGroup();
});

// sorting functionality trigger
$("#designerCardList table th a.sorter").live("click", function() {
    Person.sortTable("Designer", $(this));
});


function addCreateNewDesignerOnChangeFunctionality() {
    //if the applicant is imported, and some of the imported values are changed, then the  hiddenFormId and importedApplicant fields are deleted, which means that new applicant will be created with the imported data...
    $("#designerNotAGroup input:visible,#designerNotAGroup textarea:visible,#designerNotAGroup select:not([name*='Linked']):visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedDesigner').val() == 'true') {
                showConfirmModal($('#designerCreateNewOnImportedChange').html(),
                    function() {
                        $('#importedDesigner').val('false');
                        $('#designerNotAGroup #hiddenFormId').val('');
                        $('#designerImportTextfield').val('');
                    },
                    function() {
                        el.val(el.data("prev"));
                        Person.showNaturalPersonTipForBG(".designerNaturalPersonTip", $("#designerSection select[name='nationality']").val());
                    });
            }
        });
    });
}