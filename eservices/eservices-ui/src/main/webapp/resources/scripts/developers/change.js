//EVENTS FOR RADIOBUTTONS
$('input[type=radio][name=changeKind]').live("change", function() {
    var changeType = getRequestChangeType();
    $.ajax({
        url: "addPersonChange.htm",
        type: 'GET',
        data: 'changeType='+changeType,
        success: function(html) {
            $('#changeRequestContent').html(html);
            initChangeRequestContent(null, true, changeType);
        },
        error:function (error) {
            genericHandleError("An error occured while processing your request. Please try again later.",
                "#section_personChangeUser", true);
        }
    });
})

/**
 * Reset the id of the imported representative if any modification is done
 */
$('#personChangeTopSection form input').live('change', function() {
    if ($('#importPersonFromTmDs').length>0 && $('#importPersonFromTmDs').val()!='') {
        $('#personChangeTopSection #hiddenFormPreviousPersonId').val('');
        $('#personChangeTopSection #importPersonFromTmDs').val('');
        $('#countOfRemainPersons').val('');
    }
})

$('#importPersonFromTmDs').live("change", function() {
    if ($(this).find(":selected").val() == '') {
        $('#personChangeSection form input[type=text]').val('');
        $('#personChangeSection form select').val('');
        $('#personChangeSection #hiddenFormId').val('');
        $('#personChangeTopSection form input[type=text]').val('');
        $('#personChangeTopSection form select').val('');
        $('#personChangeTopSection #hiddenFormPreviousPersonId').val('');
        $('#countOfRemainPersons').val('');
    } else {
        var personChangeType = '';
        if ($('#personChangeSection #reptype').length > 0) {
            personChangeType = $('#personChangeSection #reptype').val();
        }
        var parameters = 'id='+$(this).find(":selected").val();
        var changeType = getRequestChangeType();
        if (changeType)
        {
            parameters+='&changeType='+changeType;
        }
        removePersonChangeErrors();
        var updateBottomLayer = !$(this).hasClass("notUpdateBottom");
        $.ajax({
            url:PersonChange.nav.getPersonFromTmDs,
            type:"GET",
            data:parameters,
            success:function (html)
            {
                updatePersonChangeSection(html, false, true, updateBottomLayer);

                postUpdatePersonChangeSection();

                initChangeRequestContent(null, false, changeType);

                // Set value of reptype select box
                if (personChangeType) {
                    $("#personChangeSection #reptype").val(personChangeType);
                }

                // Check if warning for the last representative to remove has to be shown
                var requestChangeType = getRequestChangeType();
                if (requestChangeType == 'REMOVE_REPRESENTATIVE' || requestChangeType == 'REMOVE_CORRESPONDENT') {
                    var numberOfPersons = 0;
                    var dossierId = $('#importPersonFromTmDs').find(":selected").attr('data-dossierid');
                    if (dossierId != null) {
                        $('#importPersonFromTmDs option').each(function() {
                            if ($(this).attr('data-dossierid') == dossierId) {
                                numberOfPersons++;
                            }
                        })
                    }

                    if (numberOfPersons == 1) {
                        $('showLastPersonWarning').val(requestChangeType);
                    }
                }
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#personChangeSection", true);
            }
        });

    }
})

function initChangeRequestContent(forEdit, checkVisibilityOptions, changeType) {
    if($("#id_personChange_manual").val()== "false"){
        //$("#importedPersonChange").val("true");
        $("#personChangeSection").hide();
        $("#personChangeTopSection").hide();
    }

    if($("#id_personChange_manual").val()== "true"){

        if(($("#personChangeCreateNew").size() == 0 || $("#personChangeCreateNew").is(":hidden")) && forEdit != "edit")
        {
            PersonChange.ajax.displayChoosePersonChange();
        }
    }

    // Hide create new change options if it is opened for edition
    if (checkVisibilityOptions) {
        if (forEdit != "edit") {
            $('#requestNewChangeOptionsLayer').show();
        } else {
            $('#requestNewChangeOptionsLayer').hide();
        }
    }

    if (changeType == 'CHANGE_REPRESENTATIVE_ADDRESS' || changeType == 'CHANGE_CORRESPONDENT_ADDRESS') {
        Person.fillStatesSelectbox(
                $("#personChangeSection select[name='address.country']").val(),
                "#personChangeSection select[name='address.stateprovince']",
                $("#personChangeSection input[id='address.selectedstate']").val());
    }

    // Show empty correspondence address if person has none
    if (changeType == 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS' &&
        $('#personChangeSection #representativeHasCorrespondenceAddress').length > 0 &&
        !$('#personChangeSection #representativeHasCorrespondenceAddress').is(":checked")) {

        initializePersonCorrespondenceAddress();

        $('#personChangeSection #representativeHasCorrespondenceAddress').click();
    }
}

function resetChangeRequestContent() {
    $('input[type=radio][name=changeKind]').prop('checked', false);
    $('#changeRequestContent').html('<div id="personChangeTopSection"></div><div id="personChangeSection"></div>');
}

var PersonChange =
{
    correspondenceAddressIndex:0,
    numberOfAddedCorrespondenceAddresses:0,
    getMaxCorrespondenceAddresses:function ()
    {
        return parseInt($("#personChangeSection #maxCorrespondenceAddresses").val());
    },
    getPersonChangeCount: function()
    {
        return Person.numberOfTableRowsNoHeader("#addedPersonChanges");
    },
    buildListFromTable: function(tableSelector)
    {
        var list = new Array();
        $(tableSelector + " tr[id^='personChange_id_']").each(function()
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
            var row = "<tr id='personChange_id_" + list[i].id + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";

            row += "<td data-val='organization'>";
            row += list[i].organization;
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
        var list = PersonChange.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        PersonChange.buildTableFromList(tableSelector, list);
    }
};

PersonChange.nav =
{
    addLegalEntity:"addChangeRepresentativeLegalEntity.htm",
    addNaturalPerson:"addChangeRepresentativeNaturalPerson.htm",
    addEmployeePersonChange:"addChangeEmployeeRepresentative.htm",
    addLegalPractitioner:"addChangeLegalPractitioner.htm",
    addProfessionalPractitioner:"addChangeProfessionalPractitioner.htm",
    addAssociation:"addChangeRepresentativeAssociation.htm",
    chooseType:"chooseChangePersonType.htm",
    getForEdit:"getChangePersonForEdit.htm",
    remove:"removePersonChange.htm",
    importRep:"importChangePerson.htm",
    getPersonFromTmDs:"getChangePersonFromTmDs.htm",
    addChangeAddress:"addChangeAddress.htm"
};

PersonChange.ajax =
{
    changePersonChangeType: function(sender)
    {
        var personChangeType = sender.options[sender.selectedIndex].value;
        var selectedUrl = "none";

        switch (personChangeType)
        {
            case "LEGAL_ENTITY":
                selectedUrl = PersonChange.nav.addLegalEntity;
                break;
            case "NATURAL_PERSON":
                selectedUrl = PersonChange.nav.addNaturalPerson;
                break;
            case "EMPLOYEE_REPRESENTATIVE":
                selectedUrl = PersonChange.nav.addEmployeePersonChange;
                break;
            case "LEGAL_PRACTITIONER":
                selectedUrl = PersonChange.nav.addLegalPractitioner;
                break;
            case "PROFESSIONAL_PRACTITIONER":
                selectedUrl = PersonChange.nav.addProfessionalPractitioner;
                break;
            case "ASSOCIATION":
                selectedUrl = PersonChange.nav.addAssociation;
                break;
            default:
                selectedUrl = PersonChange.nav.chooseType;
                spLog("Wrong personChange type selected: " + personChangeType);
                break;
        }
        if (selectedUrl == "none")
        {
            return;
        }
        var parameters = "";
        if (getRequestChangeType())
        {
            parameters+='changeType='+getRequestChangeType();
        }
        removePersonChangeErrors();
        $.ajax({
            url:selectedUrl,
            type:"GET",
            data:parameters,
            success:function (html)
            {
                updatePersonChangeSection(html, false, false, true);

                // Set value of reptype select box
                $("#personChangeSection #reptype").val(personChangeType);

                postUpdatePersonChangeSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#personChangeSection", true);
            }
        });
    },
    displayChoosePersonChange: function()
    {
        removePersonChangeErrors();
        $.ajax({
            url:PersonChange.nav.chooseType,
            data:"",
            success:function (html)
            {
                updatePersonChangeSection(html, false, false, true);

                postUpdatePersonChangeSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#personChangeSection", true);
            }
        });
    },
    /**
     * Posts personChange information to server
     * @param sender
     * @param address
     */
    addPersonChangePost: function(sender, address, ignoreMatches)
    {
        //This has been changed to work just with the autocomplete, must certainly would fail in the manual
    	var dataToSend = "";
    	if ($("#changeRequestContent #id").length>0) {
    		dataToSend = $("#personChangeForm").serialize();
    	}
    	else
    		{
    		dataToSend = $("#changeRequestContent form").serialize();
    		}

    	$("#personChangeImportTextfield").val("");
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removePersonChangeErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                data:dataToSend,
                success:function (html)
                {
                    // Check if returned form is a result of personChanges matching
                    if ($("input#personChangeMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#personChangeMatches").html(html);
                        $("#doublesPersonChange").modal("show");
                        return;
                    }

                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayPersonChangeValidationErrors(html);
                        return;
                    }

                    //checkPersonChangeNewAssociation();

                    // otherwise, just refresh personChange cards
                    refreshPersonChangeCards(html);
                    $("#personChangeSection .cancelButton.representative").click();

                    //clean personChange autocomplete data
                    $("#personChangeSection #id").val("");
                	$("#personChangeSection #firstName").val("");
                	$("#personChangeSection #afimi").val("");

                    //refreshPersonChangesPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#personChangeSection", true);
                }
            });
    },
    /**
     * Gets filled personChange form to edit
     * @param personChangeId
     */
    getPersonChangeForm: function(personChangeId, rowNumber)
    {
        removePersonChangeErrors();
        $.ajax({
            url:PersonChange.nav.getForEdit,
            type:"GET",
            data:"id=" + personChangeId,
            success:function (html)
            {
                resetChangeRequestContent();

            	updatePersonChangeSection(html, true, true, true);

                $("#personChangeCurrentNumber").html(rowNumber);

                showPersonChangeTab("edit");

                // set "save" text for editing
                $("#tabPersonChange .addRepresentative").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                $("#personChangeSection #addRepresentativeTopButton").each(function()
                {
                    $(this).html($("#personEditText").html());
                })


                postUpdatePersonChangeSection();

                //in edit mode Type is disabled
                $('#repSelec').hide();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#personChangeCardList", true);
            }
        });
    },
    /**
     * Removes personChange with id embedded in sender's data-val attribute then refreshes personChange cards.
     * @param personChangeId
     */
    removePersonChange: function(personChangeId)
    {
        removePersonChangeErrors();
        $.ajax({
                url:PersonChange.nav.remove,
                data:"id=" + personChangeId,
                type:"GET",
                success:function (html)
                {
                    refreshPersonChangeCards(html);
                    //refreshPersonChangesPayerSection();
                    $("#personChangeTrigger").removeClass("active");
                    hidePersonChangeTab();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#personChangeCardList", true);
                }
            }
        );
    },
    /**
     * Imports an personChange
     * @param personChangeId personChange's id
     */
    importPersonChange: function(personChangeId)
    {
        if (personChangeId == null || personChangeId.trim() == "")
        {
            showWarningModal($("#personChange_missingInputText").html());
            return;
        }
        var parameters = "id=" + personChangeId;
        if (getRequestChangeType())
        {
            parameters+='&changeType='+getRequestChangeType();
        }
        removePersonChangeErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        $.ajax({
            url:PersonChange.nav.importRep,
            data:parameters,
            type:"GET",
            success:function (html)
            {
                updatePersonChangeSection(html, true, false, true);

                if(Person.containsImportError(html))
                {
                    hideLoadingScreen();
                    return;
                }
                // set imported form field
                $("#personChangeSection #importedPersonChange").attr("value", "true");

                // disable the personChange type select box
                $("#personChangeSection #reptype").attr("disabled", "disabled");

                postUpdatePersonChangeSection();
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#personChange_cannotImport").html(),
                    "#personChangeSection", true);
            }
        });
    }
}

$("#personChangeTrigger").live("click", function()
{
    togglePersonChangeAdd();
    $("#personChangeCurrentNumber").html(PersonChange.getPersonChangeCount() + 1);
})

function checkMaxPersonChanges(){
	var maxApplicants=$('#maxPersonChanges').val();

	var rowCount = $('#personChangeCardList tr').length-1;

	if(rowCount==maxApplicants){
		$("#personChangeTrigger").hide();
	}else{
		$("#personChangeTrigger").show();
	}
}

function togglePersonChangeAdd()
{
    if($("#tabPersonChange").is(":hidden"))
    {
        $("#personChangeTrigger").addClass("active");
        showPersonChangeTab();
    }
    else
    {
        $("#personChangeTrigger").removeClass("active");
        hidePersonChangeTab();
    }
}

function showPersonChangeTab(forEdit)
{
	$("#tabPersonChange").show();
	initChangeRequestContent(forEdit, true);
    $("#personChangeTrigger").addClass("active");
}

function hidePersonChangeTab()
{
    $("#tabPersonChange").hide();
    $("#personChangeTrigger").removeClass("active");
    resetChangeRequestContent();
}

function scrollToPersonChangesTop()
{
    scrollToContainer("#personChangeCardList");
}

$("#personChangeSection #reptype").live("change", function ()
{
    PersonChange.ajax.changePersonChangeType(this);
});

$("#personChangeSection .cancelButton.representative").live("click", function ()
{
    $("#personChangeSection").html("");
    $("#personChangeTopSection").html("");
    togglePersonChangeAdd();
    scrollToPersonChangesTop();
    personChangeResetCACounter();
});

function refreshPersonChangeCards(html)
{
    $("#personChangeCardList").html(html);
    scrollToPersonChangesTop();
    checkMaxPersonChanges();
    updateFeesInformation();
}

function displayPersonChangeValidationErrors(html)
{
    updatePersonChangeSection(html, true, true, true);
    postUpdatePersonChangeSection();
}

$("#personChangeSection .addChangeAddress").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }
    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addChangeAddress, ignoreMatches);
});

$("#personChangeSection .addRepresentativeProfessionalPractitioner").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }
    // check if association is new and if so display message to user
    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addProfessionalPractitioner, ignoreMatches);
});

$("#personChangeSection .addRepresentativeAssociation").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }
    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addAssociation, ignoreMatches);
});

$("#personChangeSection .addRepresentativeLegalPractitioner").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }
    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addLegalPractitioner, ignoreMatches);
});

$("#personChangeSection .addRepresentativeLegalEntity").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }

    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addLegalEntity, ignoreMatches);
});

$("#personChangeSection .addRepresentativeEmployeeRepresentative").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }
    PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addEmployeePersonChange, ignoreMatches);
});

$("#personChangeSection .addRepresentativeNaturalPerson").live("click", function (event, ignoreMatches)
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        ignoreMatches = "true";
    }

    if(($("#personChangeSection #id").length>0) && $("#personChangeSection #id").val().trim() == ""){
    	showWarningModal($("#personChange_missingInputText").html());
    	return;
    } else {
        var countOfRemainPersons = $('#countOfRemainPersons').val();
        var requestChangeType = getRequestChangeType();
    	if (countOfRemainPersons != null && countOfRemainPersons != '' && countOfRemainPersons < 2
                && $('#formEdit').val() != 'true'
                && (requestChangeType == 'REMOVE_REPRESENTATIVE' || requestChangeType == 'REMOVE_CORRESPONDENT')) {
            var onConfirmYes = partial(PersonChange.ajax.addPersonChangePost, this, PersonChange.nav.addNaturalPerson, ignoreMatches);
            var onConfirmNo = function(){};
            showConfirmModal($("#personChange_warning_last_person").html(), onConfirmYes, onConfirmNo);
        } else {
            PersonChange.ajax.addPersonChangePost(this, PersonChange.nav.addNaturalPerson, ignoreMatches);
        }
    }
});

$("#personChangeSection #addRepresentativeTopButton").live("click", function (event, ignoreMatches)
{
    $("#personChangeSection .addRepresentative").trigger("click", [ignoreMatches]);
});

function removePersonChangeErrors()
{
    removePreviousErrors("#personChangeCardList");
    removePreviousErrors("#personChangeSection");
    removePreviousErrors("#personChangeTopSection");
}

$("#editPersonChangeButton").live("click", function ()
{
    PersonChange.ajax.getPersonChangeForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deletePersonChangeButton").live("click", function ()
{
    var personChangeId = $(this).attr("data-val");
    var onConfirmYes = partial(PersonChange.ajax.removePersonChange, personChangeId);
    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

$('#personChangeImportButton').live("click", function ()
{
    var id = $("#personChangeImportTextfield").val();
    if (id.trim() == "")
    {
        showConfirmModal($("#personChange_missingInputText").html());
        return;
    }
    PersonChange.ajax.importPersonChange(id);
});

$("#importPersonChangeFromMatches").live("click", function ()
{
    PersonChange.ajax.importPersonChange($(this).attr("data-val"));
    $("#doublesPersonChange").modal("hide");
});

$("#addPersonChangeAnyway").live("click", function ()
{
    $("#tabPersonChange .addRepresentative").trigger("click", ["true"]);
    $("#doublesPersonChange").modal("hide");
});


/**
 * <p>
 * Function called each time the fields for the personChange need to be shown.
 * </p>
 * <p>
 * It toggles the economic connections inputs.
 *
 * It fills the #personChangeSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the personChange is being added or edited.
 * If the personChange is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updatePersonChangeSection(html, forEditing, updateTop, updateBottom)
{
    if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#personChangeSection .importErrorPlaceholder").html(html);
        return;
    }

    if ($(html).find("#personChangeSection").size() > 0 && $("#personChangeSection").size() > 0) {
        if (updateBottom) {
            $("#personChangeSection").html($(html).find("#personChangeSection").html());
        }
    } else {
        $("#personChangeSection").html(html);
    }

    if ($(html).find("#personChangeTopSection").size() > 0 && $("#personChangeTopSection").size() > 0) {
        if (updateTop) {
            updatePersonChangeTopSection(html);
        }
    }

    togglePersonChangeEconomicConnections();

    if($(html).hasClass('formEF')){
    	$("#personChangeImportTextfield").val($("#firstName").val());
    }

    initializePersonCorrespondenceAddress(forEditing);

    // set type of personChange in select box
    var personChangeType = $("#personChangeSection form .reptypehidden").val();
    $("#personChangeSection #reptype").val(personChangeType);
    // set label of personChange in select box
    if ($("#personChangeSection #repSelec label").size() > 0) {
        var oldRepSelecTxt = $("#personChangeSection #repSelec label").clone().children().remove().end().text();
        var newRepSelecHtml = $("#personChangeSection #repSelec label").html().replace(oldRepSelecTxt, $("#personChangeFormTypeText").text());
        $("#personChangeSection #repSelec label").html(newRepSelecHtml);
    }

}

function initializePersonCorrespondenceAddress(forEditing) {
    $("#personChangeSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    PersonChange.numberOfAddedCorrespondenceAddresses = parseInt($("#personChangeSection #numberOfCA").val());
    PersonChange.correspondenceAddressIndex = PersonChange.numberOfAddedCorrespondenceAddresses;
    if (forEditing && PersonChange.numberOfAddedCorrespondenceAddresses > 0)
    {
        personChangeCorrespAddrTemplate_Create("#personChangeSection");
        $("#personChangeSection #representativeHasCorrespondenceAddress").attr("checked", "checked");
        if (PersonChange.numberOfAddedCorrespondenceAddresses < PersonChange.getMaxCorrespondenceAddresses())
        {
            $("#personChangeSection .addAnotherCA").show();
        }
        if(PersonChange.getMaxCorrespondenceAddresses() <= 1)
        {
            $("#personChangeSection .removeCA").hide();
        }
    }

    personChangeCorrespAddrTemplate_Neutralize("#personChangeSection #correspondenceAddressPath");
}

/**
 * Updates only the top part of the form for the previous person
 */
function updatePersonChangeTopSection(html)
{
    if ($(html).find("#personChangeTopSection").size() > 0 && $("#personChangeTopSection").size() > 0) {
        $("#personChangeTopSection").html($(html).find("#personChangeTopSection").html());
    }
    // Remove repeated filing id. IMPORTANT! -> it has to be done BEFORE updatePersonChangeSection
/*    if ($("input[id=hiddenFormId]").size() > 1) {
        for (var i=0; i < $("input[id=hiddenFormId]").size(); i++) {
            $("input[id=hiddenFormId]")[i].remove();
        }
    }*/
}

function getRequestChangeType() {
    var changeType = $('input[type=radio][name=changeKind]:checked').val();
    if (changeType == undefined) {
        changeType = $('#personChangeSection #changeType').val();
    }
    return changeType;
}

function personChangeCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#personChangeSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function personChangeCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    personChangeCorrespAddrTemplate_Modify("#personChangeSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function personChangeCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
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
$("#personChangeSection #representativeHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#personChangeSection #correspondenceAddressPath").val();
        personChangeAppendCA(path);
        $("#personChangeSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#personChangeSection select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#personChangeSection #correspondenceAddressList").html("");
        $("#personChangeSection .addAnotherCA").hide();
        PersonChange.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the PersonChange object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function personChangeAppendCA(path)
{
    $("#personChangeSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#personChangeSection #correspondenceAddressTemplate").html();

    PersonChange.correspondenceAddressIndex += 1;
    PersonChange.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#personChangeSection #correspondenceAddressList").append(template);

    if (PersonChange.getMaxCorrespondenceAddresses() <= PersonChange.numberOfAddedCorrespondenceAddresses)
    {
        //template = template.replace($(template).find(".addCA").parent().html(), "");
        $("#personChangeSection .addAnotherCA").hide();
    }

    if(PersonChange.getMaxCorrespondenceAddresses() <= 1)
    {
        $("#personChangeSection .removeCA").hide();
    }

    personChangeCorrespAddrTemplate_Modify("#personChangeSection #correspondenceAddressList", "PLACEHOLDER", path);

    $('#correspondenceAddressCountry').val($('#inputcorrespondenceAddressCountry').val());
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#personChangeSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + PersonChange.correspondenceAddressIndex + "]";
    personChangeAppendCA(path);
});

$("#personChangeSection .addAnotherCA").live("click", function ()
{
    var path = $("#personChangeSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + PersonChange.correspondenceAddressIndex + "]";
    personChangeAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The PersonChange number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#personChangeSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    PersonChange.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (PersonChange.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#personChangeSection #representativeHasCorrespondenceAddress").removeAttr("checked");
        $("#personChangeSection .addAnotherCA").hide();
    }

    if (PersonChange.numberOfAddedCorrespondenceAddresses > 0 &&
        PersonChange.numberOfAddedCorrespondenceAddresses < PersonChange.getMaxCorrespondenceAddresses())
    {
        $("#personChangeSection .addAnotherCA").show();
    }
});

function personChangeResetCACounter()
{
    PersonChange.correspondenceAddressIndex = 0;
    PersonChange.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabPersonChange']").live("click", function ()
{
    if (($("#personChangeConfig_searchEnabled").val() == "true") && ($("#service_personChangeMatches_enabled") == "true"))
    {
        showWarningModal($("#personChangeCreationInfo").html());
    }
});


$("#personChangeImportTextfield").live("focus", function ()
{
    initAutocomplete(this);
    handleDisplayOfPersonChangeAutocompleteTable(this);

//		    $(this).bind("autocompleteselect", function(event, ui)
//		    {
//		        $("#personChangeImportTextfield").val(ui.item.rid);
//		        return false;
//		    }),
    $(this).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
            $("#personChangeImportTextfield").val(ui.item.ruri);
            return false;
        }, 100);
    })
})

$("#personChangeSection input[name='associationId'].autocompleted").live("focus", function ()
{
    initAutocomplete(this);
    handleDisplayOfPersonChangeAutocompleteTable(this);
    $(this).bind("autocompleteselect", function (event, ui)
    {
        $("#personChangeSection input[name='associationName']").val(ui.item.rnm);
        $("#personChangeSection input[name='associationId']").val(ui.item.ruri);
        return false;
    });
    $(this).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
            $("#personChangeSection input[name='associationName']").val(ui.item.rnm);
            $("#personChangeSection input[name='associationId']").val(ui.item.ruri);
            return false;
        }, 100);
    })
})

function handleDisplayOfPersonChangeAutocompleteTable(object)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        var searchUrlEnabled = $("#personChangeConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if (searchUrlEnabled == "true")
        {
            var viewMessage = $("#personChangeConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getPersonChangeAutocompleteNavigationUrl([
                    ["personChangeId", item.ruri]
                ]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.rnm + "</span>" +
            "<span class='second-col'>" + item.ruri + "</span>" +
           "</span></span></a>" + wrappedUrl)
            .appendTo(ul)
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
    }
}

function getPersonChangeAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#personChangeConfig_searchUrl").val());
}

function postUpdatePersonChangeSection()
{
    if ($("#personChangeSection #importedPersonChange").val() == "true")
    {
        // disable the personChange type select box
        $("#personChangeSection #reptype").attr("disabled", "disabled");
    }


	if($("#personChangeSection #reptype").val() == "" && !($("#personChangeSection #importedPersonChange").val() == "true")) {
		$("#personChangeSection #addRepresentativeTopButton").attr("disabled", "disabled");
	} else {
		$("#personChangeSection #addRepresentativeTopButton").removeAttr("disabled");
	}


    Person.fillStatesSelectbox(
        $("#personChangeSection select[name='address.country']").val(),
        "#personChangeSection select[name='address.stateprovince']",
        $("#personChangeSection input[id='address.selectedstate']").val());

    $("#personChangeSection select[id^='correspondenceAddressCountry']").each(function ()
    {
        Person.handleCorrespondenceAddressStates(this, "#personChangeSection select[id='corrStateProvince']");
    })
    if($("#personChangeSection #legalPractitionerType").size() > 0)
    {
        togglePersonChangeAssociation("#legalPractitionerType");
    }
    if($("#personChangeSection #professionalPractitionerType").size() > 0)
    {
        togglePersonChangeAssociation("#professionalPractitionerType");
    }

}


$("#personChangeSection select[name='address.country']").live("change", function ()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#personChangeSection select[id='address.stateprovince']");
})

$("#personChangeSection select[id^='correspondenceAddressCountry']").live("change", function ()
{
    Person.handleCorrespondenceAddressStates(this, "#personChangeSection select[id='corrStateProvince']");
})

$("#personChangeCreateNew").live("click", function()
{
    PersonChange.ajax.displayChoosePersonChange();
})

// sorting functionality trigger
$("#personChangeCardList table th a.sorter").live("click", function()
{
    Person.sortTable("PersonChange", $(this));
})

/////////////////////////////
// REPRESENTATIVE SPECIFIC //
/////////////////////////////
$("#personChangeSection #economicConnections").live("change", function ()
{
    togglePersonChangeEconomicConnections();
});

function togglePersonChangeEconomicConnections()
{
    if ($("#personChangeSection #economicConnections").is(":checked")
        || $("#personChangeSection [name='economicConnections'][type='checkbox']").is(":disabled"))
    {
        $("#personChangeSection #withoutEconomicConnections").removeAttr("checked");
        $("#personChangeSection #economicConnectionsFields").show();
    }
    else
    {
        $("#personChangeSection #withoutEconomicConnections").attr("checked", "checked");
        $("#personChangeSection #economicConnectionsFields").hide();
        $("#personChangeSection #economicConnectionsFields input").val("");
    }
    if($("#personChangeSection [name='economicConnections'][type='checkbox']").is(":disabled"))
    {
        $("#personChangeSection #withoutEconomicConnections").attr("disabled", "disabled");
    }
}

$("#personChangeSection #withoutEconomicConnections").live("change", function()
{
    if($(this).is(":checked"))
    {
        $("#personChangeSection #economicConnections").removeAttr("checked");
    }
    else
    {
        $("#personChangeSection #economicConnections").attr("checked", "checked");
    }
    togglePersonChangeEconomicConnections();
})

function checkPersonChangeNewAssociation()
{
    if(($("#personChangeSection #associationId").is(":visible")
       && !$("#personChangeSection #associationId").is(":disabled")
       && $("#personChangeSection #associationId").val().trim() == "")
        ||
        ($('#representativeSection #reptype').val() == "ASSOCIATION"
        && $('#representativeSection #importedRepresentative').val() !== "true"))
    {
        showMessageModal($("#personChange_newAssociation").html());
    }
}

$("#personChangeSection #legalPractitionerType").live("change", function()
{
    togglePersonChangeAssociation("#personChangeSection #legalPractitionerType");
})

$("#personChangeSection #professionalPractitionerType").live("change", function()
{
    togglePersonChangeAssociation("#personChangeSection #professionalPractitionerType");
})

function togglePersonChangeAssociation(selector)
{
    if($(selector).val() == "ASSOCIATION")
    {
        $("#personChangeSection #associationId").val("");
        $("#personChangeSection #associationId").parent().hide();

        if($(selector).attr("name") == "legalPractitionerType")
        {
            $("#personChangeSection #legalPractitionerPersonFields input").each(function()
            {
                $(this).val("");
            });
            $("#personChangeSection #legalPractitionerPersonFields").hide();
        }
        return;
    }
    $("#personChangeSection #legalPractitionerPersonFields").show();
    $("#personChangeSection #associationId").parent().show();
}

function refreshPersonChangesPayerSection() {
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
