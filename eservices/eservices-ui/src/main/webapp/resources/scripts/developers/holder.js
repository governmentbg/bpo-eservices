var Holder =
{
    correspondenceAddressIndex:0,
    numberOfAddedCorrespondenceAddresses:0,
    getMaxCorrespondenceAddresses:function ()
    {
        return parseInt($("#holderSection #maxCorrespondenceAddresses").val());
    },
    getHolderCount: function()
    {
        return Person.numberOfTableRowsNoHeader("#userDataHolders")
            + Person.numberOfTableRowsNoHeader("#addedHolders");
    },
    buildListFromTable: function(tableSelector)
    {
        var list = new Array();
        $(tableSelector + " tr[id^='holder_id_']").each(function()
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
            var row = "<tr id='holder_id_" + list[i].id + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";

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
        var list = Holder.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Holder.buildTableFromList(tableSelector, list);
    }
};

Holder.nav =
{
    addLegalEntity:"addHolderLegalEntity.htm",
    addNaturalPerson:"addHolderNaturalPerson.htm",
    chooseType:"chooseHolderType.htm",
    getForEdit:"getHolderForEdit.htm",
    remove:"removeHolder.htm",
    importHol: "importHolder.htm",
    addApplicantAsHolder: "addApplicantAsHolder.htm",
    addUserNaturalPerson: "addUserNaturalPersonDetailsHolder.htm",
    addUserLegalEntity: "addUserLegalEntityDetailsHolder.htm",
    addUserHolder: "addUserHolder.htm"
};

Holder.globals = {
    changedRemoveId: "",
};

Holder.ajax =
{
		
		
		addUserHolderDetails: function()
		{
			removeHolderErrors();
			var holderSelected = new Array();
			$('#section_holderUser input:checkbox:checked').each(function()
            {
				holderSelected.push($(this).val());
            }) ;

			var dataToSend = "selectedHolderUser="+ holderSelected;
			
			$.ajax({
				url: Holder.nav.addUserHolder,
				data: dataToSend,
				type: 'POST',
                async: false,
				success: function(html) {
					refreshHolderCards(html);
					hideSectionHolderUser();
				},
				 error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#section_holderUser", true);
	                }
			});
			
		},
	
		addUserNaturalPerson: function()
	    {
	        
	        removeHolderErrors();
	        $.ajax(
	            {
	                url:Holder.nav.addUserNaturalPerson,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#holderMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#holderMatches").html(html);
	                        $("#doublesHolder").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayHolderValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importHolderUser", $(html)).length>0)
	                    {
	                    	showModalHolderSelection(html);
	                    }
	                    else
	                    {
	                    	refreshHolderCards(html);
	                    }
	                    


	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#holderSection", true);
	                }
	            });
	    },
	    
	    
	    addUserLegalEntity: function()
	    {
	        
	        removeHolderErrors();
	        $.ajax(
	            {
	                url:Holder.nav.addUserLegalEntity,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#holderMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#holderMatches").html(html);
	                        $("#doublesHolder").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayHolderValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importHolderUser", $(html)).length>0)
	                    {
	                    	showModalHolderSelection(html);
	                    }
	                    else
	                    {
	                    	refreshHolderCards(html);
	                    }
	                    


	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#holderSection", true);
	                }
	            });
	    },
	    
    changeHolderType: function(sender)
    {
    	$("#loadApplicant").hide();
        var holderType = sender.options[sender.selectedIndex].value;
        var selectedUrl = "none";
        	
        switch (holderType)
        {
            case "LEGAL_ENTITY":
                selectedUrl = Holder.nav.addLegalEntity;
                break;
            case "NATURAL_PERSON":
                selectedUrl = Holder.nav.addNaturalPerson;
                break;
            default:
                selectedUrl = Holder.nav.chooseType;
                spLog("Wrong holder type selected: " + holderType);
                break;
        }
        if (selectedUrl == "none")
        {
            return;
        }
        removeHolderErrors();
        $.ajax({
            url:selectedUrl,
            type:"GET",
            data:"",
            success:function (html)
            {
            	if($(html).filter(".flMessageError").length){
            		genericHandleError($(html).filter(".flMessageError").text(), "#errorMessageHolder", false);
            		return;
            	}
            	
                updateHolderSection(html, false);

                // Set value of htype select box
                $("#htype").val(holderType);

                postUpdateHolderSection();
                $("#loadApplicant").hide();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#errorMessageHolder", true);
            }
        });
    },
    displayChooseHolder: function()
    {
        removeHolderErrors();
        $.ajax({
            url:Holder.nav.chooseType,
            data:"",
            success:function (html)
            {
            	
                updateHolderSection(html, false);

                postUpdateHolderSection();
                // if($("#addedApplicants tr").length >0){
            	// 	$("#loadApplicant").show();
            	// 	$("#newHolder").hide();
            	// }else{
            	// 	$("#loadApplicant").hide();
            	// 	$("#newHolder").show();
            	// }
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#errorMessageHolder", true);
            }
        });
    },
    /**
     * Posts holder information to server
     * @param sender
     * @param address
     */
    addHolderPost: function(sender, address, ignoreMatches)
    {
        var dataToSend = $("#holderSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeHolderErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                data:dataToSend,
                async: false,
                success:function (html)
                {           
                	
                    // Check if returned form is a result of holders matching
                    if ($("input#holderMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#holderMatches").html(html);
                        $("#doublesHolder").modal("show");
                        return;
                    }

                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayHolderValidationErrors(html);
                        return;
                    }

                    // otherwise, just refresh holder cards
                    refreshHolderCards(html);
                    if(Holder.globals.changedRemoveId != ""){
                        Holder.ajax.removeHolder(Holder.globals.changedRemoveId);
                    }
                    $(".cancelButton.holder").click();

                    //refreshHoldersPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#errorMessageHolder", true);
                }
            });
    },
    addApplicantAsHolder: function(address)
    {
        var applicantId = $("#selectedApplicantCombo").val();
        removeHolderErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                data:{id: applicantId},
                async: false,
                success:function (html)
                {
                	if($(html).filter(".flMessageError").length){
                		genericHandleError($(html).filter(".flMessageError").text(), "#errorMessageHolder", false);
                		return;
                	}
                	
                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayHolderValidationErrors(html);
                        return;
                    }

                    // otherwise, just refresh holder cards
                    refreshHolderCards(html);
                    $(".cancelButton.holder").click();

                    //refreshHoldersPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#errorMessageHolder", true);
                }
            });
    },
    /**
     * Gets filled holder form to edit
     * @param holderId
     */
    getHolderForm: function(holderId, rowNumber)
    {
        removeHolderErrors();
        $.ajax({
            url:Holder.nav.getForEdit,
            type:"GET",
            data:"id=" + holderId,
            success:function (html)
            {
                updateHolderSection(html, true);

                $("#holderCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#holderSection .addHolder").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                $("#holderSection .addHolderTopButton").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                showHolderTab("edit");

                postUpdateHolderSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#holderCardList", true);
            }
        });
    },
    /**
     * Removes holder with id embedded in sender's data-val attribute then refreshes holder cards.
     * @param holderId
     */
    removeHolder: function(holderId)
    {
        removeHolderErrors();
        if(holderId == Holder.globals.changedRemoveId){
            Holder.globals.changedRemoveId ="";
        }
        $.ajax({
                url:Holder.nav.remove,
                data:"id=" + holderId,
                type:"GET",
                success:function (html)
                {
                    refreshHolderCards(html);
                    updateFeesInformation();
                    //refreshHoldersPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#holderCardList", true);
                }
            }
        );
    },
    /**
     * Imports an holder
     * @param holderId holder's id
     */
    importHolder: function(holderId)
    {
        if(holderId == null || holderId.trim() == "")
        {
            showWarningModal($("#holder_missingInputText").html());
            return;
        }
        removeHolderErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        $.ajax({
            url:Holder.nav.importHol,
            data:"id=" + holderId,
            type:"GET",
            success:function (html)
            {
                updateHolderSection(html, true);

                if(Person.containsImportError(html))
                {
                    hideLoadingScreen();
                    return;
                }

                // set this so that adding doesn't check for matches anymore
                $("#importedHolder").attr("value", "true");

                postUpdateHolderSection();

    //            // set isImported field
    //            $("#holderSection #importedHolder").val("true");
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#holder_cannotImport").html(),
                    "#selectHolderType", true);
            }
        });
    }
}

$("#holderTrigger").live("click", function()
{	
    toggleHolderAdd();
    $("#holderCurrentNumber").html(Holder.getHolderCount() + 1);
   
})

$("#importHolderNaturalPerson").live("click", function()
{
	Holder.ajax.addUserNaturalPerson();
    
})

$("#importHolderLegalEntity").live("click", function()
{
	Holder.ajax.addUserLegalEntity();
    
})

$("#buttonSaveAddHolderUser").live("click", function()
{
	Holder.ajax.addUserHolderDetails();
    
})

function showModalHolderSelection(html)
{
	$("#section_holderUser").html(html);
	$("#section_holderUser").modal("show");
}

function hideSectionHolderUser()
{
	$('#section_holderUser').modal("hide");
	$('.modal-backdrop fade in').each(function()
            {
		$(this).modal("hide");
    }); 	
}


function toggleHolderAdd()
{

    if($("#tabHolder").is(":hidden"))
    {
        $("#holderTrigger").addClass("active");
        showHolderTab();
    }
    else
    {
        $("#holderTrigger").removeClass("active");
        hideHolderTab();
    }
}

function showHolderTab(forEdit)
{
    $("#tabHolder").show();
    if(($("#holderCreateNew").size() == 0 || $("#holderCreateNew").is(":hidden"))
        && forEdit != "edit")
    {
        Holder.ajax.displayChooseHolder();
    }
    
    
    if($("#reuse-applicant-data").length){
    	if(forEdit){
    		$("#reuse-applicant-data").hide();
    		$("#selectHolderType").show();
    	}else{
    		$("#reuse-applicant-data").show();
    		$("#reuseApplicantYES").click();
    	}
    }
    

    $("#holderTrigger").addClass("active");
}

function hideHolderTab()
{
    $("#tabHolder").hide();
    $("#holderTrigger").removeClass("active");
}


function scrollToHoldersTop()
{
    scrollToContainer("#holderCardList");
}

$("#htype").live("change", function ()
{
    Holder.ajax.changeHolderType(this);    
});

$(".cancelButton.holder").live("click", function ()
{
    $("#selectHolderType").html("");
    toggleHolderAdd();
    scrollToHoldersTop();
    holderResetCACounter();
    Holder.globals.changedRemoveId = "";
});

function checkMaxHolders(){
	var maxHolders=$('#maxHolders').val();
	
	var rowCount = $('#holderCardList tr').length-1;
	
	if(rowCount>=maxHolders){
		$("#holderTrigger").hide();
		if ($("#importHolderNaturalPerson").length>0){
			$("#importHolderNaturalPerson").hide();
		}
		if ($("#importHolderLegalEntity").length>0){
			$("#importHolderLegalEntity").hide();
		}
	}else{
		$("#holderTrigger").show();
		if ($("#importHolderNaturalPerson").length>0){
			$("#importHolderNaturalPerson").show();
		}
		if ($("#importHolderLegalEntity").length>0){
			$("#importHolderLegalEntity").show();
		}
	}
}

function refreshHolderCards(html)
{
    $("#holderCardList").html(html);
    scrollToHoldersTop();
    checkMaxHolders();
}

function displayHolderValidationErrors(html)
{
    updateHolderSection(html, true);
	$("#loadApplicant").hide();
    postUpdateHolderSection();
}

$(".addHolLegalEntity").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreHolderMatches())
    {
        ignoreMatches = "true";
    }
    Holder.ajax.addHolderPost(this, Holder.nav.addLegalEntity, ignoreMatches);
});

$(".addHolNaturalPerson").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreHolderMatches())
    {
        ignoreMatches = "true";
    }
    Holder.ajax.addHolderPost(this, Holder.nav.addNaturalPerson, ignoreMatches);
});

$(".addHolNaturalPersonSpecial").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreHolderMatches())
    {
        ignoreMatches = "true";
    }
    Holder.ajax.addHolderPost(this, Holder.nav.addNaturalPersonSpecial, ignoreMatches);
});

function mustIgnoreHolderMatches()
{
    if ($("#importedHolder").val() == "true")
    {
        return "true";
    }
}

$("#addHolderTopButton").live("click", function (event, ignoreMatches)
{	
	if($("#selectApplicant").is(":hidden") || $("#reuse-applicant-data").children().length == 0){	
		$(".addHolder").trigger("click", [ignoreMatches]);
	}else{
		Holder.ajax.addApplicantAsHolder(Holder.nav.addApplicantAsHolder);
	}
});

function removeHolderErrors()
{
    removePreviousErrors("#holderCardList");
    removePreviousErrors("#selectHolderType");
}

$("#editHolderButton").live("click", function ()
{
    Holder.ajax.getHolderForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteHolderButton").live("click", function ()
{
    var holderId = $(this).attr("data-val");
    var onConfirmYes = partial(Holder.ajax.removeHolder, holderId);
    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

$('#holderImportButton').live("click", function ()
{
    var id = $("#holderImportTextfield").val();
    if(id.trim() == "")
    {
        showConfirmModal($("#holder_missingInputText").html());
        return;
    }
    Holder.ajax.importHolder(id);
});

$("#importHolderFromMatches").live("click", function ()
{
    Holder.ajax.importHolder($(this).attr("data-val"));
    $("#doublesHolder").modal("hide");
});

$("#addHolderAnyway").live("click", function ()
{
    $("#tabHolder .addHolder").trigger("click", ["true"]);
    $("#doublesHolder").modal("hide");
});

/**
 * <p>
 * Function called each time the fields for the holder need to be shown.
 * It fills the #holderSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the holder is being added or edited.
 * If the holder is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateHolderSection(html, forEditing)
{
	
    if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#holderSection .importErrorPlaceholder").html(html);
        return;
    }
    
    $("#selectHolderType").html(html);
    $("#selectHolderType").show();
    $("#holderSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Holder.numberOfAddedCorrespondenceAddresses = parseInt($("#holderSection #numberOfCA").val());
    Holder.correspondenceAddressIndex = Holder.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Holder.numberOfAddedCorrespondenceAddresses > 0)
    {
        holderCorrespAddrTemplate_Create("#selectHolderType");
        $("#holderSection #holderHasCorrespondenceAddress").attr("checked", "checked");
        if(Holder.numberOfAddedCorrespondenceAddresses < Holder.getMaxCorrespondenceAddresses())
        {
            $("#holderSection .addAnotherCA").show();
        }
        if(Holder.getMaxCorrespondenceAddresses() <= 1)
        {
            $("#holderSection .removeCA").hide();
        }
    }

    // set type of holder in select box
    var holderType = $("#holderSection form .htypehidden").val();
    $("#htype").val(holderType);
    
    // if (forEditing){
    // 	$("#newHolder").hide();
    // }

    holderCorrespAddrTemplate_Neutralize("#holderSection #correspondenceAddressPath");
}

function holderCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#holderSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function holderCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    holderCorrespAddrTemplate_Modify("#holderSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function holderCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
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
$("#holderHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#holderSection #correspondenceAddressPath").val();
        holderAppendCA(path);
        $("#holderSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#selectHolderType select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#holderSection #correspondenceAddressList").html("");
        $("#holderSection .addAnotherCA").hide();
        Holder.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Holder object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function holderAppendCA(path)
{
    $("#holderSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#holderSection #correspondenceAddressTemplate").html();

    Holder.correspondenceAddressIndex += 1;
    Holder.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#holderSection #correspondenceAddressList").append(template);

    if (Holder.getMaxCorrespondenceAddresses() <= Holder.numberOfAddedCorrespondenceAddresses)
    {
        $("#holderSection .addAnotherCA").hide();
    }
    if(Holder.getMaxCorrespondenceAddresses() <= 1)
    {
        $("#holderSection .removeCA").hide();
    }

    holderCorrespAddrTemplate_Modify("#holderSection #correspondenceAddressList", "PLACEHOLDER", path);
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#holderSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Holder.correspondenceAddressIndex + "]";
    holderAppendCA(path);
});

$("#holderSection .addAnotherCA").live("click", function ()
{
    var path = $("#holderSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Holder.correspondenceAddressIndex + "]";
    holderAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Holder number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#holderSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Holder.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Holder.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#holderSection #holderHasCorrespondenceAddress").removeAttr("checked");
        $("#holderSection .addAnotherCA").hide();
    }

    if (Holder.numberOfAddedCorrespondenceAddresses > 0 &&
        Holder.numberOfAddedCorrespondenceAddresses < Holder.getMaxCorrespondenceAddresses())
    {
        $("#holderSection .addAnotherCA").show();
    }
});

function holderResetCACounter()
{
    Holder.correspondenceAddressIndex = 0;
    Holder.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabHolder']").live("click", function ()
{
    if (($("#holderConfig_searchEnabled").val() == "true") && ($("#service_holderMatches_enabled") == "true"))
    {
        showWarningModal($("#holderCreationInfo").html());
    }
})

$("#holderImportTextfield").live("focus", function()
{
    initAutocomplete(this);
    if($(this).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(this).data("autocomplete")._renderItem = function(ul, item)
    {
        var searchUrlEnabled = $("#holderConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true")
        {
            var viewMessage = $("#holderConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getHolderAutocompleteNavigationUrl([["holderId", item.aid]]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.anm + "</span>" +
                    "<span class='second-col'>" + item.aid + "</span>" +
                    "<span class='third-col'>" + item.anc + "</span></span></span></a>" + wrappedUrl)
            .appendTo(ul)
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
    }
    $(this).bind("autocompleteselect", function(event, ui)
    {
        setTimeout(function()
        {
            if(ui.item == null)
            {
                return;
            }

            $("#holderImportTextfield").val(ui.item.aid);
            return false;
        }, 100);
    })
})

function getHolderAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#holderConfig_searchUrl").val());
}

function postUpdateHolderSection()
{
    if($("#holderSection #importedHolder").val() == "true")
    {
        // disable the holder type select box
        $("#htype").attr("disabled", "disabled");
    }

	
	if($("#htype").val() == "" && $("#selectApplicant").is(":hidden")) {
		$("#addHolderTopButton").attr("disabled", "disabled");
	} else {
		$("#addHolderTopButton").removeAttr("disabled");
	}

	
    if($("#holderSection select[name='nationality']").val() == "US")
    {
        holderDisplayStateOfIncorporation(true);
    }
    else
    {
        holderDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".holderNaturalPersonTip", $("#holderSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#holderSection select[name='address.country']").val(),
        "#holderSection select[name='address.stateprovince']",
        $("#holderSection input[id='address.selectedstate']").val());

    $("#holderSection select[id^='correspondenceAddressCountry']").each(function()
    {
        Person.handleCorrespondenceAddressStates(this, "#selectHolderType select[id='corrStateProvince']");
    });
    addCreateNewHolderOnChangeFunctionality();
}

$("#holderSection select[name='nationality']").live("change", function()
{
    if($(this).val() == "US")
    {
        holderDisplayStateOfIncorporation(true);
    }
    else
    {
        holderDisplayStateOfIncorporation(false);
    }
})

function holderDisplayStateOfIncorporation(display)
{
    if(display)
    {
        Person.fillStatesSelectbox("US",
            "#holderSection select[id='stateOfIncorporation']",
            $("#holderSection input[id='selectedStateOfIncorporation']").val());
        $("#holderSection select[id='stateOfIncorporation']").parent().show();
    }
    else
    {
        $("#holderSection select[id='stateOfIncorporation']").val("");
        $("#holderSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#holderSection select[name='address.country']").live("change", function()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#holderSection select[name='address.stateprovince']");
});
$("#holderSection select[name='nationality']").live("change", function()
{
    Person.showNaturalPersonTipForBG(".holderNaturalPersonTip", $(this).val());
});

$("#holderSection select[id^='correspondenceAddressCountry']").live("change", function()
{
    Person.handleCorrespondenceAddressStates(this, "#selectHolderType select[id='corrStateProvince']");
});

function refreshHoldersPayerSection() {
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

$("#holderCreateNew").live("click", function()
{
    Holder.ajax.displayChooseHolder();
})

// sorting functionality trigger
$("#holderCardList table th a.sorter").live("click", function()
{
    Person.sortTable("Holder", $(this));
})


$(".reuse-applicant-data input").live("click", function () {
	removePreviousErrors("#errorMessageHolder");
	
    var holderType = $("#selectHolderType");
    var selectApplicant = $("#selectApplicant");

    if ($(this).val()=="false") {
    	$("#newHolder").show();
    	$("#loadApplicant").hide();
    }
    
    /*if ($(this).val()=="true") {
    	$("#addHolderTopButton").removeAttr("disabled");
    	holderType.hide();
    	selectApplicant.show();
    } else {
    	$("#addHolderTopButton").attr("disabled", "disabled");
    	holderType.show();
    	selectApplicant.hide();
    }*/
});

function addCreateNewHolderOnChangeFunctionality() {
    //TODO:Create a flag somewhere if the "importedApplicant" is should be deleted on applicant change, and check for it before adding this functionality...
    //if the applicant is imported, and some of the imported values are changed, then the  hiddenFormId and importedApplicant fields are deleted, which means that new applicant will be created with the imported data...
    $("#holderDetailsForm input:visible,#holderDetailsForm textarea:visible,#holderDetailsForm select:visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedHolder').val() == 'true') {

                $('#holderDetailsForm #importedHolder').val('false');
                var hldId = $('#holderDetailsForm #hiddenFormId').val();
                var delBtnArr = $("#deleteHolderButton[data-val='"+hldId+"']");
                if(delBtnArr.size() >0){
                    Holder.globals.changedRemoveId = hldId;
                    //Holder.ajax.removeHolder(hldId);
                }
                $('#holderDetailsForm #hiddenFormId').val('');


            }
        });
    });
}