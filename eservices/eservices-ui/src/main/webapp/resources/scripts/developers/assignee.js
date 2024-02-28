var Assignee =
{
    correspondenceAddressIndex:0,
    numberOfAddedCorrespondenceAddresses:0,
    getMaxCorrespondenceAddresses:function ()
    {
        return parseInt($("#assigneeSection #maxCorrespondenceAddresses").val());
    },
    getAssigneeCount: function()
    {
        return Person.numberOfTableRowsNoHeader("#userDataAssignees")
            + Person.numberOfTableRowsNoHeader("#addedAssignees");
    },
    buildListFromTable: function(tableSelector)
    {    	
        var list = new Array();
        $(tableSelector + " tr[id^='assignee_id_']").each(function()
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
            var row = "<tr id='assignee_id_" + list[i].id + "'>";

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
        var list = Assignee.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Assignee.buildTableFromList(tableSelector, list);
    }
};

Assignee.nav =
{
    addLegalEntity:"addAssigneeLegalEntity.htm",
    addNaturalPerson:"addAssigneeNaturalPerson.htm",
    addNaturalPersonSpecial:"addAssigneeNaturalPersonSpecial.htm",
    chooseType:"chooseAssigneeType.htm",
    getForEdit:"getAssigneeForEdit.htm",
    remove:"removeAssignee.htm",
    importApp: "importAssignee.htm",
    addUserNaturalPerson: "addUserNaturalPersonDetailsAssignee.htm",
    addUserLegalEntity: "addUserLegalEntityDetailsAssignee.htm",
    addUserAssignee: "addUserAssignee.htm"
};

Assignee.globals = {
    changedRemoveId: "",
};

Assignee.ajax =
{
		addUserAssigneeDetails: function()
		{
			removeAssigneeErrors();
			var assigneeSelected = new Array();
			$('#section_assigneeUser input:checkbox:checked').each(function()
            {
				assigneeSelected.push($(this).val());
            }) ;

			var dataToSend = "selectedAssigneeUser="+ assigneeSelected;
			
			$.ajax({
				url: Assignee.nav.addUserAssignee,
				data: dataToSend,
				type: 'POST',
                async: false,
				success: function(html) {
					refreshAssigneeCards(html);
					hideSectionAssigneeUser();
				},
				 error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#section_assigneeUser", true);
	                }
			});
			
		},
	
		addUserNaturalPerson: function()
	    {
	        
	        removeAssigneeErrors();
	        $.ajax(
	            {
	                url:Assignee.nav.addUserNaturalPerson,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#assigneeMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#assigneeMatches").html(html);
	                        $("#doublesAssignee").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayAssigneeValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importAssigneeUser", $(html)).length>0)
	                    {
	                    	showModalAssigneeSelection(html);
	                    }
	                    else
	                    {
	                    	refreshAssigneeCards(html);
                            getFeesInformation();
	                    }


	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#assigneeSection", true);
	                }
	            });
	    },
	    
	    
	    addUserLegalEntity: function()
	    {
	        
	        removeAssigneeErrors();
	        $.ajax(
	            {
	                url:Assignee.nav.addUserLegalEntity,
	                type:"POST",
                    async: false,
	                success:function (html)
	                {
	                    // Check if returned form is a result of applicants matching
	                    if ($("input#assigneeMatchesExist", $(html)).val() == "true")
	                    {
	                        // show popup with matches
	                        $("#assigneeMatches").html(html);
	                        $("#doublesAssignee").modal("show");
	                        return;
	                    }

	                    // check if returned form is a form containing errors
	                    if ($("input#formReturned", $(html)).val() == "true")
	                    {
	                        // then display the errors
	                        displayAssigneeValidationErrors(html);
	                        return;
	                    }
	                    
	                    if ($("input#importAssigneeUser", $(html)).length>0)
	                    {
	                    	showModalAssigneeSelection(html);
	                    }
	                    else
	                    {
	                    	refreshAssigneeCards(html);
                            getFeesInformation();
	                    }

	                },
	                error:function (error)
	                {
	                    genericHandleError("An error occured while processing your request. Please try again later.",
	                        "#assigneeSection", true);
	                }
	            });
	    },
		
	changeAssigneeType: function(sender)
    {
        var assigneeType = sender.options[sender.selectedIndex].value;
        var selectedUrl = "none";
      
        switch (assigneeType)
        {
            case "LEGAL_ENTITY":
                selectedUrl = Assignee.nav.addLegalEntity;
                break;
            case "NATURAL_PERSON":
                selectedUrl = Assignee.nav.addNaturalPerson;
                break;
            case "NATURAL_PERSON_SPECIAL":
                selectedUrl = Assignee.nav.addNaturalPersonSpecial;
                break;
            default:
                selectedUrl = Assignee.nav.chooseType;
                spLog("Wrong assignee type selected: " + assigneeType);
                break;
        }
        if (selectedUrl == "none")
        {
            return;
        }
        removeAssigneeErrors();
        $.ajax({
            url:selectedUrl,
            type:"GET",
            data:"",
            success:function (html)
            {
                updateAssigneeSection(html, false);

                // Set value of astype select box
                $("#astype").val(assigneeType);

                postUpdateAssigneeSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#assigneeSection", true);
            }
        });
       
    },
    displayChooseAssignee: function()
    {
        removeAssigneeErrors();
        $.ajax({
            url:Assignee.nav.chooseType,
            data:"",
            success:function (html)
            {
                updateAssigneeSection(html, false);

                postUpdateAssigneeSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#assigneeSection", true);
            }
        });
        
    },
    /**
     * Posts assignee information to server
     * @param sender
     * @param address
     */
    addAssigneePost: function(sender, address, ignoreMatches)
    {
        var dataToSend = $("#assigneeSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeAssigneeErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                data:dataToSend,
                async: false,
                success:function (html)
                {
                    // Check if returned form is a result of assignees matching
                    if ($("input#assigneeMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#assigneeMatches").html(html);
                        $("#doublesAssignee").modal("show");
                        return;
                    }

                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayAssigneeValidationErrors(html);
                        return;
                    }

                    // otherwise, just refresh assignee cards
                    refreshAssigneeCards(html);
                    if(Assignee.globals.changedRemoveId != ""){
                        Assignee.ajax.removeAssignee(Assignee.globals.changedRemoveId);
                        Assignee.globals.changedRemoveId = "";
                    }
                    $(".cancelButton.assignee").click();
                    updateFeesInformation();

                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#assigneeSection", true);
                }
            });
    },
    /**
     * Gets filled assignee form to edit
     * @param assigneeId
     */
    getAssigneeForm: function(assigneeId, rowNumber)
    {
        removeAssigneeErrors();
        $.ajax({
            url:Assignee.nav.getForEdit,
            type:"GET",
            data:"id=" + assigneeId,
            success:function (html)
            {
                updateAssigneeSection(html, true);

                $("#assigneeCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#assigneeSection .addAssignee").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                $("#assigneeSection .addAssigneeTopButton").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                showAssigneeTab("edit");

                postUpdateAssigneeSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#assigneeCardList", true);
            }
        });
    },
    /**
     * Removes assignee with id embedded in sender's data-val attribute then refreshes assignee cards.
     * @param assigneeId
     */
    removeAssignee: function(assigneeId)
    {
        removeAssigneeErrors();
        $.ajax({
                url:Assignee.nav.remove,
                data:"id=" + assigneeId,
                type:"GET",
                success:function (html)
                {
                    refreshAssigneeCards(html);
                    updateFeesInformation();
                    //refreshAssigneesPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#assigneeCardList", true);
                }
            }
        );
    },
    /**
     * Imports an assignee
     * @param assigneeId assignee's id
     */
    importAssignee: function(assigneeId)
    {
        if(assigneeId == null || assigneeId.trim() == "")
        {
            showWarningModal($("#assignee_missingInputText").html());
            return;
        }
        removeAssigneeErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        $.ajax({
            url:Assignee.nav.importApp,
            data:"id=" + assigneeId,
            type:"GET",
            success:function (html)
            {
                updateAssigneeSection(html, true);

                if(Person.containsImportError(html))
                {
                    hideLoadingScreen();
                    return;
                }

                // set this so that adding doesn't check for matches anymore
                $("#importedAssignee").attr("value", "true");

                postUpdateAssigneeSection();

    //            // set isImported field
    //            $("#assigneeSection #importedAssignee").val("true");
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#assignee_cannotImport").html(),
                    "#assigneeSection", true);
            }
        });
    }
}

$("#assigneeTrigger").live("click", function()
{
    toggleAssigneeAdd();
    $("#assigneeCurrentNumber").html(Assignee.getAssigneeCount() + 1);
    
})

$("#importAssigneeNaturalPerson").live("click", function()
{
	Assignee.ajax.addUserNaturalPerson();
    
})

$("#importAssigneeLegalEntity").live("click", function()
{
	Assignee.ajax.addUserLegalEntity();
    
})

$("#buttonSaveAddAssigneeUser").live("click", function()
{
	Assignee.ajax.addUserAssigneeDetails();
    
})

function showModalAssigneeSelection(html)
{
	$("#section_assigneeUser").html(html);
	$("#section_assigneeUser").modal("show");
}

function hideSectionAssigneeUser()
{
	$('#section_assigneeUser').modal("hide");
	$('.modal-backdrop').hide();
}

function toggleAssigneeAdd()
{
    if($("#tabAssignee").is(":hidden"))
    {
        $("#assigneeTrigger").addClass("active");
        showAssigneeTab();
    }
    else
    {
        $("#assigneeTrigger").removeClass("active");
        hideAssigneeTab();
    }
    
}

function showAssigneeTab(forEdit)
{	
    $("#tabAssignee").show();
    if(($("#assigneeCreateNew").size() == 0 || $("#assigneeCreateNew").is(":hidden"))
        && forEdit != "edit")
    {
        Assignee.ajax.displayChooseAssignee();
    }

    $("#assigneeTrigger").addClass("active");
}

function hideAssigneeTab()
{
    $("#tabAssignee").hide();
    $("#assigneeTrigger").removeClass("active");
}


function scrollToAssigneesTop()
{
	
    scrollToContainer("#assigneeCardList");
}

$("#astype").live("change", function ()
{
    Assignee.ajax.changeAssigneeType(this);
});

$(".cancelButton.assignee").live("click", function ()
{
    $("#assigneeSection").html("");
    toggleAssigneeAdd();
    scrollToAssigneesTop();
    assigneeResetCACounter();
});

function checkMaxAssignees(){
	var maxAssignees=$('#maxAssignees').val();
	
	var rowCount = $('#assigneeCardList tr').length-1;
	
	if(rowCount>=maxAssignees){
		$("#assigneeTrigger").hide();
		if ($("#importAssigneeNaturalPerson").length>0){
			$("#importAssigneeNaturalPerson").hide();
		}
		if ($("#importAssigneeLegalEntity").length>0){
			$("#importAssigneeLegalEntity").hide();
		}
	}else{
		$("#assigneeTrigger").show();
		if ($("#importAssigneeNaturalPerson").length>0){
			$("#importAssigneeNaturalPerson").show();
		}
		if ($("#importAssigneeLegalEntity").length>0){
			$("#importAssigneeLegalEntity").show();
		}
	}
}

function refreshAssigneeCards(html)
{
	$("#assigneeCardList").html(html);
    scrollToAssigneesTop();    
    checkMaxAssignees();
}


function displayAssigneeValidationErrors(html)
{
    updateAssigneeSection(html, true);
    postUpdateAssigneeSection();
}

$(".addAssLegalEntity").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreAssigneeMatches())
    {
        ignoreMatches = "true";
    }
    Assignee.ajax.addAssigneePost(this, Assignee.nav.addLegalEntity, ignoreMatches);
});

$(".addAssNaturalPerson").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreAssigneeMatches())
    {
        ignoreMatches = "true";
    }
    Assignee.ajax.addAssigneePost(this, Assignee.nav.addNaturalPerson, ignoreMatches);
});

$(".addAssNaturalPersonSpecial").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreAssigneeMatches())
    {
        ignoreMatches = "true";
    }
    Assignee.ajax.addAssigneePost(this, Assignee.nav.addNaturalPersonSpecial, ignoreMatches);
  
});

function mustIgnoreAssigneeMatches()
{
    if ($("#importedAssignee").val() == "true")
    {
        return "true";
    }
}

$("#addAssigneeTopButton").live("click", function (event, ignoreMatches)
{
    $(".addAssignee").trigger("click", [ignoreMatches]);
});

function removeAssigneeErrors()
{
    removePreviousErrors("#assigneeCardList");
    removePreviousErrors("#assigneeSection");
}

$("#editAssigneeButton").live("click", function ()
{
    Assignee.ajax.getAssigneeForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteAssigneeButton").live("click", function ()
{
    var assigneeId = $(this).attr("data-val");
    var onConfirmYes = partial(Assignee.ajax.removeAssignee, assigneeId);
    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

$('#assigneeImportButton').live("click", function ()
{
    var id = $("#assigneeImportTextfield").val();
    if(id.trim() == "")
    {
        showConfirmModal($("#assignee_missingInputText").html());
        return;
    }
    Assignee.ajax.importAssignee(id);
});

$(".importAssigneeFromMatches").live("click", function ()
{
    Assignee.ajax.importAssignee($(this).attr("data-val"));
    $("#doublesAssignee").modal("hide");
});

$("#addAssigneeAnyway").live("click", function ()
{
    $("#tabAssignee .addAssignee").trigger("click", ["true"]);
    $("#doublesAssignee").modal("hide");
});

/**
 * <p>
 * Function called each time the fields for the assignee need to be shown.
 * It fills the #assigneeSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the assignee is being added or edited.
 * If the assignee is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateAssigneeSection(html, forEditing)
{
    if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#assigneeSection .importErrorPlaceholder").html(html);
        return;
    }

    $("#assigneeSection").html(html);
    
    $("#assigneeSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Assignee.numberOfAddedCorrespondenceAddresses = parseInt($("#assigneeSection #numberOfCA").val());
    Assignee.correspondenceAddressIndex = Assignee.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Assignee.numberOfAddedCorrespondenceAddresses > 0)
    {
        assigneeCorrespAddrTemplate_Create("#assigneeSection");
        $("#assigneeSection #assigneeHasCorrespondenceAddress").attr("checked", "checked");
        if(Assignee.numberOfAddedCorrespondenceAddresses < Assignee.getMaxCorrespondenceAddresses())
        {
            $("#assigneeSection .addAnotherCA").show();
        }
        if(Assignee.getMaxCorrespondenceAddresses() <= 1)
        {
            $("#assigneeSection .removeCA").hide();
        }
    }

    // set type of assignee in select box
    var assigneeType = $("#assigneeSection form .apptypehidden").val();
    $("#astype").val(assigneeType);


    assigneeCorrespAddrTemplate_Neutralize("#assigneeSection #correspondenceAddressPath");
}

function assigneeCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#assigneeSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function assigneeCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    assigneeCorrespAddrTemplate_Modify("#assigneeSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function assigneeCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
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
$("#assigneeHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#assigneeSection #correspondenceAddressPath").val();
        assigneeAppendCA(path);
        $("#assigneeSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#assigneeSection select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#assigneeSection #correspondenceAddressList").html("");
        $("#assigneeSection .addAnotherCA").hide();
        Assignee.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Assignee object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function assigneeAppendCA(path)
{
    $("#assigneeSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#assigneeSection #correspondenceAddressTemplate").html();

    Assignee.correspondenceAddressIndex += 1;
    Assignee.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#assigneeSection #correspondenceAddressList").append(template);

    if (Assignee.getMaxCorrespondenceAddresses() <= Assignee.numberOfAddedCorrespondenceAddresses)
    {
        $("#assigneeSection .addAnotherCA").hide();
    }
    if(Assignee.getMaxCorrespondenceAddresses() <= 1)
    {
        $("#assigneeSection .removeCA").hide();
    }

    assigneeCorrespAddrTemplate_Modify("#assigneeSection #correspondenceAddressList", "PLACEHOLDER", path);
    
    $('#correspondenceAddressCountry').val($('#inputcorrespondenceAddressCountry').val());
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#assigneeSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Assignee.correspondenceAddressIndex + "]";
    assigneeAppendCA(path);
});

$("#assigneeSection .addAnotherCA").live("click", function ()
{
    var path = $("#assigneeSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Assignee.correspondenceAddressIndex + "]";
    assigneeAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Assignee number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#assigneeSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Assignee.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Assignee.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#assigneeSection #assigneeHasCorrespondenceAddress").removeAttr("checked");
        $("#assigneeSection .addAnotherCA").hide();
    }

    if (Assignee.numberOfAddedCorrespondenceAddresses > 0 &&
        Assignee.numberOfAddedCorrespondenceAddresses < Assignee.getMaxCorrespondenceAddresses())
    {
        $("#assigneeSection .addAnotherCA").show();
    }
});

function assigneeResetCACounter()
{
    Assignee.correspondenceAddressIndex = 0;
    Assignee.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabAssignee']").live("click", function ()
{
    if (($("#assigneeConfig_searchEnabled").val() == "true") && ($("#service_assigneeMatches_enabled") == "true"))
    {
        showWarningModal($("#assigneeCreationInfo").html());
    }
})

$("#assigneeImportTextfield").live("focus", function()
{
    initAutocomplete(this);
    if($(this).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(this).data("autocomplete")._renderItem = function(ul, item)
    {
        var searchUrlEnabled = $("#assigneeConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true")
        {
            var viewMessage = $("#assigneeConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getAssigneeAutocompleteNavigationUrl([["assigneeId", item.auri]]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {
            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.anm + "</span>" +
                    "<span class='second-col'>" + item.aid + "</span></span></span></a>" + wrappedUrl)
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

            $("#assigneeImportTextfield").val(ui.item.aid);
            return false;
        }, 100);
    })
})

function getAssigneeAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#assigneeConfig_searchUrl").val());
}

function postUpdateAssigneeSection()
{
    if($("#assigneeSection #importedAssignee").val() == "true")
    {
        // disable the assignee type select box
        $("#astype").attr("disabled", "disabled");
    }

	
	if($("#astype").val() == "") {
		$("#addAssigneeTopButton").attr("disabled", "disabled");
	} else {
		$("#addAssigneeTopButton").removeAttr("disabled");
	}

	
    if($("#assigneeSection select[name='nationality']").val() == "US")
    {
        assigneeDisplayStateOfIncorporation(true);
    }
    else
    {
        assigneeDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".assigneeNaturalPersonTip", $("#assigneeSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#assigneeSection select[name='address.country']").val(),
        "#assigneeSection select[name='address.stateprovince']",
        $("#assigneeSection input[id='address.selectedstate']").val());

    $("#assigneeSection select[id^='correspondenceAddressCountry']").each(function()
    {
        Person.handleCorrespondenceAddressStates(this, "#assigneeSection select[id='corrStateProvince']");
    });
    addCreateNewAssigneeOnChangeFunctionality();
}

$("#assigneeSection select[name='nationality']").live("change", function()
{
    if($(this).val() == "US")
    {
        assigneeDisplayStateOfIncorporation(true);
    }
    else
    {
        assigneeDisplayStateOfIncorporation(false);
    }
})

function assigneeDisplayStateOfIncorporation(display)
{
    if(display)
    {
        Person.fillStatesSelectbox("US",
            "#assigneeSection select[id='stateOfIncorporation']",
            $("#assigneeSection input[id='selectedStateOfIncorporation']").val());
        $("#assigneeSection select[id='stateOfIncorporation']").parent().show();
    }
    else
    {
        $("#assigneeSection select[id='stateOfIncorporation']").val("");
        $("#assigneeSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#assigneeSection select[name='address.country']").live("change", function()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#assigneeSection select[name='address.stateprovince']");
});
$("#assigneeSection select[name='nationality']").live("change", function()
{
    Person.showNaturalPersonTipForBG(".assigneeNaturalPersonTip", $(this).val());
});

$("#assigneeSection select[id^='correspondenceAddressCountry']").live("change", function()
{
    Person.handleCorrespondenceAddressStates(this, "#assigneeSection select[id='corrStateProvince']");
});

function refreshAssigneesPayerSection() {
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

$("#assigneeCreateNew").live("click", function()
{
    Assignee.ajax.displayChooseAssignee();
})

// sorting functionality trigger
$("#assigneeCardList table th a.sorter").live("click", function()
{	
    Person.sortTable("Assignee", $(this));
})

function addCreateNewAssigneeOnChangeFunctionality() {
    //TODO:Create a flag somewhere if the "importedApplicant" is should be deleted on applicant change, and check for it before adding this functionality...
    //if the applicant is imported, and some of the imported values are changed, then the  hiddenFormId and importedApplicant fields are deleted, which means that new applicant will be created with the imported data...
    $("#assigneeDetailsForm input:visible,#assigneeDetailsForm textarea:visible,#assigneeDetailsForm select:visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedAssignee').val() == 'true') {
                showConfirmModal($('#assigneeCreateNewOnImportedChange').html(),
                    function() {
                        $('#assigneeDetailsForm #importedAssignee').val('false');
                        var assigneeId = $('#assigneeDetailsForm #hiddenFormId').val();
                        var delBtnArr = $("#deleteAssigneeButton[data-val='"+assigneeId+"']");
                        if(delBtnArr.size() >0){
                            Assignee.globals.changedRemoveId = assigneeId;
                        }
                        $('#assigneeDetailsForm #hiddenFormId').val('');
                        $('#assigneeImportTextfield').val('');
                    },
                    function() {
                        el.val(el.data("prev"));
                        Person.showNaturalPersonTipForBG(".assigneeNaturalPersonTip", $("#assigneeSection select[name='nationality']").val());
                    });
            }
        });
    });
}