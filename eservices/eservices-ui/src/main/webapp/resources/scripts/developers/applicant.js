var Applicant =
{
    correspondenceAddressIndex:0,
    numberOfAddedCorrespondenceAddresses:0,
    getMaxCorrespondenceAddresses:function ()
    {
        return parseInt($("#applicantSection #maxCorrespondenceAddresses").val());
    },
    getApplicantCount: function()
    {
        return Person.numberOfTableRowsNoHeader("#userDataApplicants")
            + Person.numberOfTableRowsNoHeader("#addedApplicants");
    },
    buildListFromTable: function(tableSelector)
    {    	
        var list = new Array();
        $(tableSelector + " tr[id^='applicant_id_']").each(function()
        {
            var obj = new Object();
           $(this).find("td").each(function()
            {
                obj[$(this).attr("data-val")] = $(this).html().trim();
            });
            list.push(obj);
        })
        return list;
    },
    buildTableFromList: function(tableSelector, list)
    {    	
        // clear table by removing all rows except first, which is the header
        $(tableSelector).find("tr:gt(0)").remove();

        for(var i = 0; i < list.length; i++)
        {
            var row = "<tr id='applicant_id_" + list[i].id + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

           /* row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";
            */
            row += "<td data-val='type'>";
            row += list[i].type;
            row += "</td>";

            row += "<td data-val='name'>";
            row += list[i].name;
            row += "</td>";

            row += "<td data-val='country'>";
            row += list[i].country;
            row += "</td>";

            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>"
            $(tableSelector + " tbody:last").append(row);
        }        
        
    },
    sort: function(tableSelector, property, descending)
    {
        var list = Applicant.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Applicant.buildTableFromList(tableSelector, list);
    }
};

Applicant.nav =
{
    addLegalEntity:"addApplicantLegalEntity.htm",
    addNaturalPerson:"addApplicantNaturalPerson.htm",
    addNaturalPersonSpecial:"addApplicantNaturalPersonSpecial.htm",
    chooseType:"chooseApplicantType.htm",
    getForEdit:"getApplicantForEdit.htm",
    remove:"removeApplicant.htm",
    importApp: "importApplicant.htm",
    addUserNaturalPerson: "addUserNaturalPersonDetails.htm",
    addUserLegalEntity: "addUserLegalEntityDetails.htm",
    addUserApplicant: "addUserApplicant.htm",
    addApplicantsFromHolders: "addApplicantsFromHolders.htm",
    addApplicantsFromAssignees: "addApplicantsFromAssignees.htm",
    addApplicantsFromOwners: "addApplicantsFromOwners.htm"
};

Applicant.globals = {
    changedRemoveId: "",
};

Applicant.ajax =
{
		
		addUserApplicantDetails: function()
		{
			removeApplicantErrors();
			var applicantSelected = new Array();
			$('#section_applicantUser input:checkbox:checked').each(function()
            {
				applicantSelected.push($(this).val());
            }) ;

			var dataToSend = "selectedApplicantUser="+ applicantSelected;
			
			$.ajax({
				url: Applicant.nav.addUserApplicant,
				data: dataToSend,
				type: 'POST',
                async: false,
				success: function(html) {
					refreshApplicantCards(html);
					hideSectionApplicantUser();
				},
				 error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#section_applicantUser", true);
	                }
			});
			
		},
	
		addUserNaturalPerson: function()
	    {
	        
	        removeApplicantErrors();
	        $.ajax(
	            {
	                url:Applicant.nav.addUserNaturalPerson,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#applicantMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#applicantMatches").html(html);
	                        $("#doublesApplicant").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayApplicantValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importApplicantUser", $(html)).length>0)
	                    {
	                    	showModalApplicantSelection(html);
	                    }
	                    else
	                    {
	                    	refreshApplicantCards(html);
	                    }
	                    


	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#applicantSection", true);
	                }
	            });
	    },

        addApplicantsFromHolders: function() {
            $.ajax({
                url: Applicant.nav.addApplicantsFromHolders,
                type: 'POST',
                async: false,
                success: function(html) {
                    if ($(html).find('.flMessageError').length > 0) {
                        showWarningModal($(html).text());
                    } else {
                        refreshApplicantCards(html);
                    }
                    hideApplicantPanel();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#applicantSection", true);
                }
            });
        },

        addApplicantsFromAssignees: function() {
            $.ajax({
                url: Applicant.nav.addApplicantsFromAssignees,
                type: 'POST',
                async: false,
                success: function(html) {
                    if ($(html).find('.flMessageError').length > 0) {
                        showWarningModal($(html).text());
                    } else {
                        refreshApplicantCards(html);
                    }
                    hideApplicantPanel();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#applicantSection", true);
                }
            });
        },

        addApplicantsFromOwners: function() {
            $.ajax({
                url: Applicant.nav.addApplicantsFromOwners,
                type: 'POST',
                async: false,
                success: function(html) {
                    if ($(html).find('.flMessageError').length > 0) {
                        showWarningModal($(html).text());
                    } else {
                        refreshApplicantCards(html);
                    }
                    hideApplicantPanel();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#applicantSection", true);
                    hideApplicantPanel();
                }
            });
        },
	    
	    addUserLegalEntity: function()
	    {
	        
	        removeApplicantErrors();
	        $.ajax(
	            {
	                url:Applicant.nav.addUserLegalEntity,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#applicantMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#applicantMatches").html(html);
	                        $("#doublesApplicant").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayApplicantValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importApplicantUser", $(html)).length>0)
	                    {
	                    	showModalApplicantSelection(html);
	                    }
	                    else
	                    {
	                    	refreshApplicantCards(html);
	                    }
	                    


	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#applicantSection", true);
	                }
	            });
	    },
		
    changeApplicantType: function(sender)
    {
        var applicantType = sender.options[sender.selectedIndex].value;
        var selectedUrl = "none";

        switch (applicantType)
        {
            case "LEGAL_ENTITY":
                selectedUrl = Applicant.nav.addLegalEntity;
                break;
            case "NATURAL_PERSON":
                selectedUrl = Applicant.nav.addNaturalPerson;
                break;
            case "NATURAL_PERSON_SPECIAL":
                selectedUrl = Applicant.nav.addNaturalPersonSpecial;
                break;
            default:
                selectedUrl = Applicant.nav.chooseType;
                spLog("Wrong applicant type selected: " + applicantType);
                break;
        }
        if (selectedUrl == "none")
        {
            return;
        }
        removeApplicantErrors();
        $.ajax({
            url:selectedUrl,
            type:"GET",
            data:"",
            success:function (html)
            {
                updateApplicantSection(html, false);

                // Set value of aptype select box
                $("#aptype").val(applicantType);

                postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantSection", true);
            }
        });
    },
    displayChooseApplicant: function()
    {
        removeApplicantErrors();
        $.ajax({
            url:Applicant.nav.chooseType,
            data:"",
            success:function (html)
            {
                updateApplicantSection(html, false);

                postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantSection", true);
            }
        });
        
    },
    /**
     * Posts applicant information to server
     * @param sender
     * @param address
     */
    addApplicantPost: function(sender, address, ignoreMatches)
    {
        var dataToSend = $("#applicantSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
                
        if( $("#showModalOwner").val() && !($("#applicantIsOwner").is(":checked")) ){
        	showWarningModal($("#ownerErrorText").val());
        }
        
        removeApplicantErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                async: false,
                data:dataToSend,                
                success:function (html)
                {
                    // Check if returned form is a result of applicants matching
                    if ($("input#applicantMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#applicantMatches").html(html);
                        $("#doublesApplicant").modal("show");
                        return;
                    }

                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayApplicantValidationErrors(html);
                        return;
                    }

                    // otherwise, just refresh applicant cards
                    refreshApplicantCards(html);
                    if(Applicant.globals.changedRemoveId != ""){
                        Applicant.ajax.removeApplicant(Applicant.globals.changedRemoveId);
                        Applicant.globals.changedRemoveId = "";
                    }
                    $(".cancelButton.applicant").click();

                    //refreshApplicantsPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#applicantSection", true);
                }
            });
    },
    /**
     * Gets filled applicant form to edit
     * @param applicantId
     */
    getApplicantForm: function(applicantId, rowNumber)
    {
        removeApplicantErrors();
        $.ajax({
            url:Applicant.nav.getForEdit,
            type:"GET",
            data:"id=" + applicantId,
            success:function (html)
            {
                updateApplicantSection(html, true);

                $("#applicantCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#applicantSection .addApplicant").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                $("#applicantSection .addApplicantTopButton").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                showApplicantTab("edit");

                postUpdateApplicantSection();
                //in edit mode Type is disabled
                // $('#appSelec').hide();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantCardList", true);
            }
        });
    },
    /**
     * Removes applicant with id embedded in sender's data-val attribute then refreshes applicant cards.
     * @param applicantId
     */
    removeApplicant: function(applicantId)
    {
        removeApplicantErrors();
        $.ajax({
                url:Applicant.nav.remove,
                data:"id=" + applicantId,
                type:"GET",
                success:function (html)
                {
                    refreshApplicantCards(html);
                    //refreshApplicantsPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#applicantCardList", true);
                }
            }
        );
    },
    /**
     * Imports an applicant
     * @param applicantId applicant's id
     */
    importApplicant: function(applicantId)
    {
        if(applicantId == null || applicantId.trim() == "")
        {
            showWarningModal($("#applicant_missingInputText").html());
            return;
        }
        removeApplicantErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        $.ajax({
            url:Applicant.nav.importApp,
            data:"id=" + applicantId,
            type:"GET",
            success:function (html)
            {
                updateApplicantSection(html, true);

                if(Person.containsImportError(html))
                {
                    hideLoadingScreen();
                    return;
                }

                // set this so that adding doesn't check for matches anymore
                $("#importedApplicant").attr("value", "true");

                postUpdateApplicantSection();

    //            // set isImported field
    //            $("#applicantSection #importedApplicant").val("true");
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#applicant_cannotImport").html(),
                    "#applicantSection", true);
            }
        });
    }
}

$("#applicantTrigger").live("click", function()
{
    toggleApplicantAdd();
    $("#applicantCurrentNumber").html(Applicant.getApplicantCount() + 1);
    
})


$("#importApplicantNaturalPerson").live("click", function()
{
	Applicant.ajax.addUserNaturalPerson();
    
})

$("#importApplicantLegalEntity").live("click", function()
{
	Applicant.ajax.addUserLegalEntity();
    
})

$("#buttonSaveAddApplicantUser").live("click", function()
{
	Applicant.ajax.addUserApplicantDetails();
    
})

function showModalApplicantSelection(html)
{
	$("#section_applicantUser").html(html);
	$("#section_applicantUser").modal("show");
}

function hideSectionApplicantUser()
{
	$('#section_applicantUser').modal("hide");
	$('.modal-backdrop fade in').each(function()
            {
		$(this).modal("hide");
    }); 
	
}

function toggleApplicantAdd()
{
    if($("#tabApplicant").is(":hidden"))
    {
        $("#applicantTrigger").addClass("active");
        $('#applicantSection').html('');
        showApplicantTab();
    }
    else
    {
        $("#applicantTrigger").removeClass("active");
        hideApplicantTab();
    }
    
}

function showApplicantTab(forEdit)
{	
    $("#tabApplicant").show();
    if(($("#applicantCreateNew").size() == 0 || $("#applicantCreateNew").is(":hidden"))
        && forEdit != "edit")
    {
        Applicant.ajax.displayChooseApplicant();
    }

    $("#applicantTrigger").addClass("active");
}

function hideApplicantTab()
{
    $("#tabApplicant").hide();
    $("#applicantTrigger").removeClass("active");
}


function scrollToApplicantsTop()
{
	
    scrollToContainer("#applicantCardList");
}

$("#aptype").live("change", function ()
{
    Applicant.ajax.changeApplicantType(this);
});

$(".cancelButton.applicant").live("click", function ()
{
    $("#applicantSection").html("");
    toggleApplicantAdd();
    scrollToApplicantsTop();
    applicantResetCACounter();
});

function checkMaxApplicants(){
	var maxApplicants=$('#maxApplicants').val();
	
	var rowCount = $('#applicantCardList tr').length-1;
	
	if(rowCount>=maxApplicants){
        if( $("#addApplicantsFromOwners").length )
        {
            $("#addApplicantsFromOwners").hide();
        }
        if( $("#addApplicantsFromAssignees").length )
        {
            $("#addApplicantsFromAssignees").hide();
        }
        if( $("#addApplicantsFromHolders").length )
        {
            $("#addApplicantsFromHolders").hide();
        }
		$("#applicantTrigger").hide();
		if ($("#importApplicantNaturalPerson").length>0){
			$("#importApplicantNaturalPerson").hide();
		}
		if ($("#importApplicantLegalEntity").length>0){
			$("#importApplicantLegalEntity").hide();
		}	        
	}else{
        if( $("#addApplicantsFromOwners").length )
        {
            $("#addApplicantsFromOwners").show();
        }
        if( $("#addApplicantsFromAssignees").length )
        {
            $("#addApplicantsFromAssignees").show();
        }
        if( $("#addApplicantsFromHolders").length )
        {
            $("#addApplicantsFromHolders").show();
        }
		$("#applicantTrigger").show();
		if ($("#importApplicantNaturalPerson").length>0){
			$("#importApplicantNaturalPerson").show();
		}
		if ($("#importApplicantLegalEntity").length>0){
			$("#importApplicantLegalEntity").show();
		}
			
	}
}

function refreshApplicantCards(html)
{
	$("#applicantCardList").html(html);
    scrollToApplicantsTop();
    
    checkMaxApplicants();
}


function displayApplicantValidationErrors(html)
{
    updateApplicantSection(html, true);
    postUpdateApplicantSection();
}

$(".addAppLegalEntity").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreApplicantMatches())
    {
        ignoreMatches = "true";
    }
    Applicant.ajax.addApplicantPost(this, Applicant.nav.addLegalEntity, ignoreMatches);
});

$(".addAppNaturalPerson").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreApplicantMatches())
    {
        ignoreMatches = "true";
    }
    Applicant.ajax.addApplicantPost(this, Applicant.nav.addNaturalPerson, ignoreMatches);
});

$(".addAppNaturalPersonSpecial").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreApplicantMatches())
    {
        ignoreMatches = "true";
    }
    Applicant.ajax.addApplicantPost(this, Applicant.nav.addNaturalPersonSpecial, ignoreMatches);
  
});

function mustIgnoreApplicantMatches()
{
    if ($("#importedApplicant").val() == "true")
    {
        return "true";
    }
}

$("#addApplicantTopButton").live("click", function (event, ignoreMatches)
{
    $(".addApplicant").trigger("click", [ignoreMatches]);
});

function removeApplicantErrors()
{
    removePreviousErrors("#applicantCardList");
    removePreviousErrors("#applicantSection");
}

$("#editApplicantButton").live("click", function ()
{
    Applicant.ajax.getApplicantForm($(this).attr("data-val"), $(this).attr("data-rownum")); 
});

$("#deleteApplicantButton").live("click", function ()
{
    var applicantId = $(this).attr("data-val");
    var onConfirmYes = partial(Applicant.ajax.removeApplicant, applicantId);
    showConfirmModal($("#applicant_deleteConfirmation").html(), onConfirmYes);
});

$('#applicantImportButton').live("click", function ()
{
    var id = $("#applicantImportTextfield").val();
    if(id.trim() == "")
    {
        showConfirmModal($("#applicant_missingInputText").html());
        return;
    }
    Applicant.ajax.importApplicant(id);
});

$(".importApplicantFromMatches").live("click", function ()
{
    Applicant.ajax.importApplicant($(this).attr("data-val"));
    $("#doublesApplicant").modal("hide");
});

$("#addApplicantAnyway").live("click", function ()
{
    $("#tabApplicant .addApplicant").trigger("click", ["true"]);
    $("#doublesApplicant").modal("hide");
});

/**
 * <p>
 * Function called each time the fields for the applicant need to be shown.
 * It fills the #applicantSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the applicant is being added or edited.
 * If the applicant is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateApplicantSection(html, forEditing)
{
    if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#applicantSection .importErrorPlaceholder").html(html);
        return;
    }

    $("#applicantSection").html(html);

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Applicant.numberOfAddedCorrespondenceAddresses = parseInt($("#applicantSection #numberOfCA").val());
    Applicant.correspondenceAddressIndex = Applicant.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Applicant.numberOfAddedCorrespondenceAddresses > 0)
    {
        applicantCorrespAddrTemplate_Create("#applicantSection");
        $("#applicantSection #applicantHasCorrespondenceAddress").attr("checked", "checked");
    }

    // set type of applicant in select box
    var applicantType = $("#applicantSection form .apptypehidden").val();
    $("#aptype").val(applicantType);


    applicantCorrespAddrTemplate_Neutralize("#applicantSection #correspondenceAddressPath");
}

function applicantCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#applicantSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function applicantCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    applicantCorrespAddrTemplate_Modify("#applicantSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function applicantCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
{
    $(containerQuery + " [name^='" + currentPath + "']").each(function ()
    {
        var name = $(this).attr("name");
        var id = $(this).attr("id");

        $(this).attr("name", name.replace(currentPath, replacement));
        $(this).attr("id", id.replace(currentPath, replacement));

        $(this).removeAttr("value");
    });
    $(containerQuery + " [for^='" + currentPath + "']").each(function ()
    {
        var forAttr = $(this).attr("for");

        $(this).attr("for", forAttr.replace(currentPath, replacement));
    });
}

/**
 * This function specifies what should happen when the user clicks the
 * checkbox indicating whether they desire to add different correspondence addresses.
 */
$("#applicantHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#applicantSection #correspondenceAddressPath").val();
        applicantAppendCA(path);
        $("#applicantSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#applicantSection select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#applicantSection #correspondenceAddressList").html("");
        Applicant.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Applicant object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function applicantAppendCA(path)
{
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#applicantSection #correspondenceAddressTemplate").html();

    Applicant.correspondenceAddressIndex += 1;
    Applicant.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#applicantSection #correspondenceAddressList").append(template);

    applicantCorrespAddrTemplate_Modify("#applicantSection #correspondenceAddressList", "PLACEHOLDER", path);
    
    $('#correspondenceAddressCountry').val($('#inputcorrespondenceAddressCountry').val());
    //$('#correspondenceAddressCountry').selectmenu("refresh");
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#applicantSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Applicant.correspondenceAddressIndex + "]";
    applicantAppendCA(path);
});

$("#applicantSection .addAnotherCA").live("click", function ()
{
    var path = $("#applicantSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Applicant.correspondenceAddressIndex + "]";
    applicantAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Applicant number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#applicantSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Applicant.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Applicant.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#applicantSection #applicantHasCorrespondenceAddress").removeAttr("checked");
    }
});

function applicantResetCACounter()
{
    Applicant.correspondenceAddressIndex = 0;
    Applicant.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabApplicant']").live("click", function ()
{
    if (($("#applicantConfig_searchEnabled").val() == "true") && ($("#service_applicantMatches_enabled") == "true"))
    {
        showWarningModal($("#applicantCreationInfo").html());
    }
})

$("#applicantImportTextfield").live("focus", function()
{
    initAutocomplete(this);
    if($(this).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(this).data("autocomplete")._renderItem = function(ul, item)
    {
        var searchUrlEnabled = $("#applicantConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true")
        {
            var viewMessage = $("#applicantConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getApplicantAutocompleteNavigationUrl([["applicantId", item.auri]]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {

            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a class='wider'><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.anm + "</span>" +
                    "<span class='second-col'>" + item.aid + "</span>" +
                    "</span></span></a>" + wrappedUrl)
                .appendTo(ul)
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
        }
    }
    $(this).bind("autocompleteselect", function(event, ui)
    {
        setTimeout(function()
        {
            if(ui.item == null)
            {
                return;
            }

            $("#applicantImportTextfield").val(ui.item.aid);
            return false;
        }, 100);
    })
})

function getApplicantAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#applicantConfig_searchUrl").val());
}

function postUpdateApplicantSection()
{
    if($("#applicantSection #importedApplicant").val() == "true")
    {
        // disable the applicant type select box
        $("#aptype").attr("disabled", "disabled");
    }

	
	if($("#aptype").val() == "") {
		$("#addApplicantTopButton").attr("disabled", "disabled");
	} else {
		$("#addApplicantTopButton").removeAttr("disabled");
	}

	
    if($("#applicantSection select[name='nationality']").val() == "US")
    {
        applicantDisplayStateOfIncorporation(true);
    }
    else
    {
        applicantDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".applicantNaturalPersonTip", $("#applicantSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#applicantSection select[name='address.country']").val(),
        "#applicantSection select[name='address.stateprovince']",
        $("#applicantSection input[id='address.selectedstate']").val());

    $("#applicantSection select[id^='correspondenceAddressCountry']").each(function()
    {
        Person.handleCorrespondenceAddressStates(this, "#applicantSection select[id='corrStateProvince']");
    });
    addCreateNewApplicantOnChangeFunctionality();
}

$("#applicantSection select[name='nationality']").live("change", function()
{
    if($(this).val() == "US")
    {
        applicantDisplayStateOfIncorporation(true);
    }
    else
    {
        applicantDisplayStateOfIncorporation(false);
    }
})

function applicantDisplayStateOfIncorporation(display)
{
    if(display)
    {
        Person.fillStatesSelectbox("US",
            "#applicantSection select[id='stateOfIncorporation']",
            $("#applicantSection input[id='selectedStateOfIncorporation']").val());
        $("#applicantSection select[id='stateOfIncorporation']").parent().show();
    }
    else
    {
        $("#applicantSection select[id='stateOfIncorporation']").val("");
        $("#applicantSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#applicantSection select[name='address.country']").live("change", function()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#applicantSection select[name='address.stateprovince']");
});
$("#applicantSection select[name='nationality']").live("change", function()
{
    Person.showNaturalPersonTipForBG(".applicantNaturalPersonTip", $(this).val());
});

$("#applicantSection select[id^='correspondenceAddressCountry']").live("change", function()
{
    Person.handleCorrespondenceAddressStates(this, "#applicantSection select[id='corrStateProvince']");
});

function refreshApplicantsPayerSection() {
	//Only refresh the payer list box if the payment section is present in the page
	if($("#paymentOptions").length > 0) {
		var paymentData = $('#paymentForm').serialize();
		$.get('getPaymentOptions.htm', paymentData, function(data) {
			$('#paymentOptions').html(data);
			$('#paymentDetails').show();
			showHidePayerDetailsDiv();
		});
	}
}

$("#applicantCreateNew").live("click", function()
{
    Applicant.ajax.displayChooseApplicant();
});

// sorting functionality trigger
$("#applicantCardList table th a.sorter").live("click", function()
{	
    Person.sortTable("Applicant", $(this));
});

$("#addApplicantsFromOwners").live("click", function(){
    // the applicant list is forward in the wizard that ds/tm list
    if(!($("#tm_section").exists() || $("#ds_section").exists())) {
        Applicant.ajax.addApplicantsFromOwners();
    } else {
        // the applicant list is in the same screen that ds/tm list
        if (($('#tmsList tr').length) != 0 || ($('#dssList tr').length) != 0) {
            Applicant.ajax.addApplicantsFromOwners();
        }
    }
});

$("#addApplicantsFromHolders").live("click", function(){
    if(!$("#holder").exists()) {
        Applicant.ajax.addApplicantsFromHolders();
    } else {
        if ($('#holderCardList tr').length != 0){
            Applicant.ajax.addApplicantsFromHolders();
        }
    }

});

$("#addApplicantsFromAssignees").live("click", function(){
    if(!$("#assignee").exists()) {
        Applicant.ajax.addApplicantsFromAssignees();
    } else {
        if ($('#assigneeCardList tr').length != 0){
            Applicant.ajax.addApplicantsFromAssignees();
        }
    }
});

function hideApplicantPanel() {
    $("#tabApplicant").hide();
    $("#applicantTrigger").removeClass("active");
}

function addCreateNewApplicantOnChangeFunctionality() {
    //TODO:Create a flag somewhere if the "importedApplicant" is should be deleted on applicant change, and check for it before adding this functionality...
    //if the applicant is imported, and some of the imported values are changed, then the  hiddenFormId and importedApplicant fields are deleted, which means that new applicant will be created with the imported data...
    $("#applicantDetailsForm input:visible,#applicantDetailsForm textarea:visible,#applicantDetailsForm select:visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedApplicant').val() == 'true') {
                showConfirmModal($('#applicantCreateNewOnImportedChange').html(),
                    function() {
                        $('#applicantDetailsForm #importedApplicant').val('false');
                        var applicantId = $('#applicantDetailsForm #hiddenFormId').val();
                        var delBtnArr = $("#deleteApplicantButton[data-val='"+applicantId+"']");
                        if(delBtnArr.size() >0){
                            Applicant.globals.changedRemoveId = applicantId;
                        }
                        $('#applicantDetailsForm #hiddenFormId').val('');
                        $('#applicantImportTextfield').val('');
                    },
                    function() {
                        el.val(el.data("prev"));
                        Person.showNaturalPersonTipForBG(".applicantNaturalPersonTip", $("#applicantSection select[name='nationality']").val());
                    });
            }
        });
    });
}