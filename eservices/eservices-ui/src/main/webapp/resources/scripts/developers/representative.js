var Representative =
{
    correspondenceAddressIndex:0,
    numberOfAddedCorrespondenceAddresses:0,
    getMaxCorrespondenceAddresses:function ()
    {
        return parseInt($("#representativeSection #maxCorrespondenceAddresses").val());
    },
    getRepresentativeCount: function()
    {
        return Person.numberOfTableRowsNoHeader("#userDataRepresentatives")
            + Person.numberOfTableRowsNoHeader("#addedRepresentatives");
    },
    buildListFromTable: function(tableSelector)
    {
        var list = new Array();
        $(tableSelector + " tr[id^='representative_id_']").each(function()
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
            var row = "<tr id='representative_id_" + list[i].id + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";

            /*row += "<td data-val='organization'>";
            row += list[i].organization;
            row += "</td>";*/

            /*row += "<td data-val='type'>";
            row += list[i].type;
            row += "</td>";*/

            row += "<td data-val='name'>";
            row += list[i].name;
            row += "</td>";

            /*row += "<td data-val='country'>";
            row += list[i].country;
            row += "</td>";*/

            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>"
            $(tableSelector + " tbody:last").append(row);
        }
    },
    sort: function(tableSelector, property, descending)
    {
        var list = Representative.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Representative.buildTableFromList(tableSelector, list);
    }
};

Representative.nav =
{
    addLegalEntity:"addRepresentativeLegalEntity.htm",
    addNaturalPerson:"addRepresentativeNaturalPerson.htm",
    addEmployeeRepresentative:"addRepresentativeEmployeeRepresentative.htm",
    addLegalPractitioner:"addRepresentativeLegalPractitioner.htm",
    addProfessionalPractitioner:"addRepresentativeProfessionalPractitioner.htm",
    addAssociation:"addRepresentativeAssociation.htm",
    chooseType:"chooseRepresentativeType.htm",
    getForEdit:"getRepresentativeForEdit.htm",
    remove:"removeRepresentative.htm",
    importRep:"importRepresentative.htm",
    addUserRepresentativeNaturalPerson: "addUserRepresentativeNaturalPersonDetails.htm",
    addUserRepresentativeAssociation: "addUserRepresentativeAssociationDetails.htm",
    addUserRepresentative: "addUserRepresentative.htm",
    addLawyerCompany:"addRepresentativeLawyerCompany.htm",
    addLawyerAssociation:"addRepresentativeLawyerAssociation.htm",
    addTemporaryRepresentative:"addRepresentativeTemporary.htm",
    addRepresentativeIntlPRepresentative:"addRepresentativeIntlPRepresentative.htm",
    listIntlPRepresentatives: "listIntlPRepresentatives.htm",
    importIntlPRepresentative: "importIntlPRepresentative.htm",
};

Representative.ajax =
{

    addUserRepresentativeDetails: function()
    {
        removeRepresentativeErrors();
        var representativeSelected = new Array();
        $('#section_representativeUser input:checkbox:checked').each(function()
        {
            representativeSelected.push($(this).val());
        }) ;

        var dataToSend = "selectedRepresentativeUser="+ representativeSelected;

        $.ajax({
            url: Representative.nav.addUserRepresentative,
            data: dataToSend,
            type: 'POST',
            async: false,
            success: function(html) {
                refreshRepresentativeCards(html);
                hideSectionRepresentativeUser();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#section_representativeUser", true);
            }
        });

    },

    addUserRepresentativeNaturalPerson: function(powOption)
    {

        removeRepresentativeErrors();
        $.ajax(
            {
                url:Representative.nav.addUserRepresentativeNaturalPerson,
                type:"POST",
                async: false,
                data: "defaultPOWOption="+powOption,
                success:function (html)
                {
                    // Check if returned form is a result of represent matching
                    if ($("input#representativeMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#representativeMatches").html(html);
                        $("#doublesRepresentative").modal("show");
                        return;
                    }

                    if ($("input#importRepresentativeUser", $(html)).length>0)
                    {
                        showModalRepresentativeSelection(html);
                    }
                    else
                    {
                        refreshRepresentativeCards(html);
                    }



                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#representativeSection", true);
                }
            });
    },

    addUserRepresentativeAssociation: function(powOption)
    {

        removeRepresentativeErrors();
        $.ajax(
            {
                url:Representative.nav.addUserRepresentativeAssociation,
                type:"POST",
                async: false,
                data: "defaultPOWOption="+powOption,
                success:function (html)
                {
                    // Check if returned form is a result of represent matching
                    if ($("input#representativeMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#representativeMatches").html(html);
                        $("#doublesRepresentative").modal("show");
                        return;
                    }

                    if ($("input#importRepresentativeUser", $(html)).length>0)
                    {
                        showModalRepresentativeSelection(html);
                    }
                    else
                    {
                        refreshRepresentativeCards(html);
                    }

                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#representativeSection", true);
                }
            });
    },

    changeRepresentativeType: function(representativeType)
    {
        var selectedUrl = "none";

        switch (representativeType)
        {
            case "LEGAL_ENTITY":
                selectedUrl = Representative.nav.addLegalEntity;
                break;
            case "NATURAL_PERSON":
                selectedUrl = Representative.nav.addNaturalPerson;
                break;
            case "EMPLOYEE_REPRESENTATIVE":
                selectedUrl = Representative.nav.addEmployeeRepresentative;
                break;
            case "LEGAL_PRACTITIONER":
                selectedUrl = Representative.nav.addLegalPractitioner;
                break;
            case "PROFESSIONAL_PRACTITIONER":
                selectedUrl = Representative.nav.addProfessionalPractitioner;
                break;
            case "ASSOCIATION":
                selectedUrl = Representative.nav.addAssociation;
                break;
            case "LAWYER_ASSOCIATION":
                selectedUrl = Representative.nav.addLawyerAssociation;
                break;
            case "LAWYER_COMPANY":
                selectedUrl = Representative.nav.addLawyerCompany;
                break;
            case "TEMPORARY_REPRESENTATIVE":
                selectedUrl = Representative.nav.addTemporaryRepresentative;
                break;
            default:
                selectedUrl = Representative.nav.chooseType;
                spLog("Wrong representative type selected: " + representativeType);
                break;
        }
        if (selectedUrl == "none")
        {
            return;
        }
        removeRepresentativeErrors();
        $.ajax({
            url:selectedUrl,
            type:"GET",
            data:"",
            success:function (html)
            {
                updateRepresentativeSection(html, false);

                // Set value of reptype select box
                $("#representativeSection #reptype").val(representativeType);

                postUpdateRepresentativeSection();

            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#representativeSection", true);
            }
        });
    },
    displayChooseRepresentative: function()
    {
        removeRepresentativeErrors();
        $.ajax({
            url:Representative.nav.chooseType,
            data:"",
            success:function (html)
            {
                updateRepresentativeSection(html, false);

                postUpdateRepresentativeSection();

                $("#representativeSection #reptype").removeAttr("disabled");
                $('#representativeSection #reptype option[value="ASSOCIATION"]').remove();
                $('#representativeSection #reptype option[value="NATURAL_PERSON"]').remove();
                $('#representativeSection #reptype option[value="LEGAL_ENTITY"]').remove();

            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#representativeSection", true);
            }
        });
    },
    /**
     * Posts representative information to server
     * @param sender
     * @param address
     */
    addRepresentativePost: function(sender, address, ignoreMatches)
    {
        //This has been changed to work just with the autocomplete, must certainly would fail in the manual
        showLoadingScreen();
    	var dataToSend = "";
    	if ($("#id").length>0) {
    		dataToSend = $("#representativeForm").serialize();
    	}
    	else
    		{
    		dataToSend = $("#representativeSection form").serialize();
    		}

    	$("#representativeImportTextfield").val("");
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeRepresentativeErrors();
        $.ajax(
            {
                url:address,
                type:"POST",
                async: false,
                data:dataToSend,
                success:function (html)
                {
                    // Check if returned form is a result of representatives matching
                    if ($("input#representativeMatchesExist", $(html)).val() == "true")
                    {
                        // show popup with matches
                        $("#representativeMatches").html(html);
                        $("#doublesRepresentative").modal("show");
                        hideLoadingScreen();
                        return;
                    }

                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        displayRepresentativeValidationErrors(html);
                        hideLoadingScreen();
                        return;
                    }

                    checkNewAssociation();

                    // otherwise, just refresh representative cards
                    refreshRepresentativeCards(html);
                    $("#representativeSection .cancelButton.representative").click();

                    //clean representative autocomplete data
                    $("#representativeSection #id").val("");
                	$("#representativeSection #firstName").val("");
                	$("#representativeSection #afimi").val("");

                    //refreshRepresentativesPayerSection();
                    hideLoadingScreen();
                },
                error:function (error)
                {
                    hideLoadingScreen();
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#representativeSection", true);
                }
            });
    },
    /**
     * Gets filled representative form to edit
     * @param representativeId
     */
    getRepresentativeForm: function(representativeId, rowNumber)
    {
        removeRepresentativeErrors();
        $.ajax({
            url:Representative.nav.getForEdit,
            type:"GET",
            data:"id=" + representativeId,
            success:function (html)
            {
            	$("#importAndCreateSection").hide();
            	updateRepresentativeSection(html, true);
                $("#representativeCurrentNumber").html(rowNumber);
                showRepresentativeTab("edit");

                // set "save" text for editing
                $("#tabRepresentative .addRepresentative").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                $("#representativeSection #addRepresentativeTopButton").each(function()
                {
                    $(this).html($("#personEditText").html());
                })




                postUpdateRepresentativeSection();

                //in edit mode Type is disabled
                // $('#repSelec').hide();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#representativeCardList", true);
            }
        });
    },
    /**
     * Removes representative with id embedded in sender's data-val attribute then refreshes representative cards.
     * @param representativeId
     */
    removeRepresentative: function(representativeId)
    {
        removeRepresentativeErrors();
        $.ajax({
                url:Representative.nav.remove,
                data:"id=" + representativeId,
                type:"GET",
                success:function (html)
                {
                    refreshRepresentativeCards(html);
                    //refreshRepresentativesPayerSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#representativeCardList", true);
                }
            }
        );
    },
    /**
     * Imports an representative
     * @param representativeId representative's id
     */
    importRepresentative: function(representativeId, maintainAttachments)
    {
        if (representativeId == null || representativeId.trim() == "")
        {
            showWarningModal($("#representative_missingInputText").html());
            return;
        }
        removeRepresentativeErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        var attachmentsHtml = "";
        if(maintainAttachments && $("#tabRepresentative").is(":visible")){
            attachmentsHtml = $("#tabRepresentative #fileDocumentAttachment").html();
        }
        $.ajax({
            url:Representative.nav.importRep,
            data:"id=" + representativeId,
            type:"GET",
            success:function (html)
            {

                if(Person.containsImportError(html))
                {
                    showWarningModal($(".importError", $(html)).html());
                    hideLoadingScreen();
                    return;
                }

            	$("#importAndCreateSection").hide();
                updateRepresentativeSection(html, true);

                // set imported form field
                $("#representativeSection #importedRepresentative").attr("value", "true");

                postUpdateRepresentativeSection();
                if(maintainAttachments && attachmentsHtml != ""){
                    $("#tabRepresentative #fileDocumentAttachment").html(attachmentsHtml);
                }

                refreshProvidedPOW();
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#representative_cannotImport").html(),
                    "#representativeSection", true);
            }
        });
    },
    loadIntlPRepresentatives: function(){
        showLoadingScreen();
        $.ajax({
            url:Representative.nav.listIntlPRepresentatives,
            type:"GET",
            success:function (reps)
            {
                if((reps+"").indexOf("flMessageError") >0 ){
                    genericHandleError($("#representative_intlp_load_error").html(),
                        "#representativeCardList", true);
                } else if(reps.length > 0) {
                    var targetSelect = $("#representativeIntellectualSelect");
                    for (var i=0;i<reps.length;i++) {
                        var cloned = $("#representativeIntellectualSelectEmptyOpt").clone();
                        $(cloned).val(reps[i].rid);
                        $(cloned).text(reps[i].rnm);
                        $(cloned).removeAttr("id");
                        targetSelect.append(cloned);
                    }
                }
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#representative_intlp_load_error").html(),
                    "#representativeCardList", true);
            }
        });
    },
    importIntlPRepresentative: function(representativeId, maintainAttachments)
    {
        if (representativeId == null || representativeId.trim() == "")
        {
            showWarningModal($("#representative_intlp_missingInputText").html());
            return;
        }
        removeRepresentativeErrors();
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        var attachmentsHtml = "";
        if(maintainAttachments && $("#tabRepresentative").is(":visible")){
            attachmentsHtml = $("#tabRepresentative #fileDocumentAttachment").html();
        }
        $.ajax({
            url:Representative.nav.importIntlPRepresentative,
            data:"id=" + representativeId,
            type:"GET",
            success:function (html)
            {
                updateRepresentativeSection(html, true);

                if(Person.containsImportError(html))
                {
                    hideLoadingScreen();
                    return;
                }
                // set imported form field
                $("#importAndCreateSection").hide();
                $("#representativeSection #importedRepresentative").attr("value", "true");
                $("#representativeIntellectualSelect").val("");

                postUpdateRepresentativeSection();
                if(maintainAttachments && attachmentsHtml != ""){
                    $("#tabRepresentative #fileDocumentAttachment").html(attachmentsHtml);
                }
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#representative_cannotImport").html(),
                    "#representativeSection", true);
            }
        });
    },
}

$("#representativeTrigger").live("click", function()
{
	$("#importAndCreateSection").show();
    toggleRepresentativeAdd();
    $("#representativeCurrentNumber").html(Representative.getRepresentativeCount() + 1);
});

$("#importRepresentativeNaturalPerson").live("click", function()
{
    Representative.ajax.addUserRepresentativeNaturalPerson($(this).attr("data-pow"));

});

$("#importRepresentativeAssociation").live("click", function()
{
    Representative.ajax.addUserRepresentativeAssociation($(this).attr("data-pow"));

});

$("#buttonSaveAddRepresentativeUser").live("click", function()
{
    Representative.ajax.addUserRepresentativeDetails();

});

$("#legalForm").live("change", function ()
{
	showLegalFormFields(this);
});


function showLegalFormFields(sender) {

	var legalFormType = sender.options[sender.selectedIndex].value;
	var legalPersonDiv = document.getElementById('legalEntity');
	var naturalPersonDiv = document.getElementById('naturalPerson');
    switch (legalFormType)
    {
        case "Legal Person":
	        legalPersonDiv.style.display = 'block';
	        $('#firstName').val("");
	        $('#surname').val("");
	        naturalPersonDiv.style.display= 'none';
            break;
        case "Natural Person":
        	legalPersonDiv.style.display = 'none';
        	$('#nameOfLegalEntity').val("");
	        naturalPersonDiv.style.display= 'block';
            break;
        default:
        	legalPersonDiv.style.display = 'none';
        	naturalPersonDiv.style.display= 'block';
            break;
    }

}

function hideSectionRepresentativeUser()
{
    $('#section_representativeUser').modal("hide");
    $('.modal-backdrop fade in').each(function()
    {
        $(this).modal("hide");
    });

}

function showModalRepresentativeSelection(html)
{
    $("#section_representativeUser").html(html);
    $("#section_representativeUser").modal("show");
}


function checkMaxRepresentatives(){
	var maxApplicants=$('#maxRepresentatives').val();

	var rowCount = $('#representativeCardList tr').length-1;

	if(rowCount==maxApplicants){
        if ($("#importRepresentativeNaturalPerson").length>0){
            $("#importRepresentativeNaturalPerson").hide();
        }
		$("#representativeTrigger").hide();
	}else{
        if ($("#importRepresentativeNaturalPerson").length>0){
            $("#importRepresentativeNaturalPerson").show();
        }
		$("#representativeTrigger").show();
	}
}

function toggleRepresentativeAdd()
{
    if($("#tabRepresentative").is(":hidden"))
    {
        $("#representativeTrigger").addClass("active");
        $('#representativeSection').html('');
        showRepresentativeTab();
    }
    else
    {
        $("#representativeTrigger").removeClass("active");
        hideRepresentativeTab();
    }
}

function showRepresentativeTab(forEdit)
{

	$("#tabRepresentative").show();
    // if($("#id_representative_manual").val()== "false"){
		// //$("#importedRepresentative").val("true");
		// $("#representativeSection").hide();
    // }
    //
    if($("#id_representative_manual").val()== "true"){

	    if(($("#representativeCreateNew").size() == 0 || $("#representativeCreateNew").is(":hidden")) && forEdit != "edit")
	    {
	        Representative.ajax.displayChooseRepresentative();
	    }
    }
    $("#representativeTrigger").addClass("active");
}

function hideRepresentativeTab()
{
    $("#tabRepresentative").hide();
    $("#representativeTrigger").removeClass("active");
}

function scrollToRepresentativesTop()
{
    scrollToContainer("#representativeCardList");
}

$("#representativeSection #reptype").live("change", function ()
{
    var representativeType = this.options[this.selectedIndex].value;
    Representative.ajax.changeRepresentativeType(representativeType);
});

function closeRepresentativeSection() {
    $("#representativeSection").html("");
    toggleRepresentativeAdd();
    scrollToRepresentativesTop();
    representativeResetCACounter();
}
$("#representativeSection .closeButton").live("click", function(){
	closeRepresentativeSection();
});

$("#representativeSection .cancelButton.representative").live("click", function ()
{
	closeRepresentativeSection();
});

function refreshRepresentativeCards(html)
{
    $("#representativeCardList").html(html);
    scrollToRepresentativesTop();
    checkMaxRepresentatives();
}

function displayRepresentativeValidationErrors(html)
{
    updateRepresentativeSection(html, true);
    postUpdateRepresentativeSection();
}

$("#representativeSection .addRepresentativeProfessionalPractitioner").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    // check if association is new and if so display message to user
    Representative.ajax.addRepresentativePost(this, Representative.nav.addProfessionalPractitioner, ignoreMatches);
});

$("#representativeSection .addRepresentativeAssociation").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    Representative.ajax.addRepresentativePost(this, Representative.nav.addAssociation, ignoreMatches);
});

$("#representativeSection .addRepresentativeLegalPractitioner").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    Representative.ajax.addRepresentativePost(this, Representative.nav.addLegalPractitioner, ignoreMatches);
});

$("#representativeSection .addRepresentativeLegalEntity").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }

    Representative.ajax.addRepresentativePost(this, Representative.nav.addLegalEntity, ignoreMatches);
});

$("#representativeSection .addRepresentativeEmployeeRepresentative").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    Representative.ajax.addRepresentativePost(this, Representative.nav.addEmployeeRepresentative, ignoreMatches);
});

$("#representativeSection .addRepresentativeNaturalPerson").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    
    if(($("#representativeSection #id").length>0) && $("#representativeSection #id").val().trim() == ""){
    	showWarningModal($("#representative_missingInputText").html());
    	return;
    }
    else{
    	Representative.ajax.addRepresentativePost(this, Representative.nav.addNaturalPerson, ignoreMatches);
    }
});


$("#representativeSection .addRepresentativeIntlPRepresentative").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }
    Representative.ajax.addRepresentativePost(this, Representative.nav.addRepresentativeIntlPRepresentative, ignoreMatches);
});

$("#representativeSection .addRepresentativeLawyerAssociation").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }

    Representative.ajax.addRepresentativePost(this, Representative.nav.addLawyerAssociation, ignoreMatches);
});

$("#representativeSection .addRepresentativeLawyerCompany").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }

    Representative.ajax.addRepresentativePost(this, Representative.nav.addLawyerCompany, ignoreMatches);
});

$("#representativeSection .addRepresentativeTemporary").live("click", function (event, ignoreMatches)
{
    if ($("#representativeSection #importedRepresentative").val() == "true")
    {
        ignoreMatches = "true";
    }

    Representative.ajax.addRepresentativePost(this, Representative.nav.addTemporaryRepresentative, ignoreMatches);
});

$("#representativeSection #addRepresentativeTopButton").live("click", function (event, ignoreMatches)
{
    $("#representativeSection .addRepresentative").trigger("click", [ignoreMatches]);
});

function removeRepresentativeErrors()
{
    removePreviousErrors("#representativeCardList");
    removePreviousErrors("#representativeSection");
}

$("#editRepresentativeButton").live("click", function ()
{
    Representative.ajax.getRepresentativeForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteRepresentativeButton").live("click", function ()
{
    var representativeId = $(this).attr("data-val");
    var onConfirmYes = partial(Representative.ajax.removeRepresentative, representativeId);
    showConfirmModal($("#representative_deleteConfirmation").html(), onConfirmYes);
});

$('#representativeImportButton').live("click", function ()
{
    var id = $("#representativeImportTextfield").val();
    if (id.trim() == "")
    {
        showConfirmModal($("#representative_missingInputText").html());
        return;
    }
    Representative.ajax.importRepresentative(id);
});

$(".importRepresentativeFromMatches").live("click", function ()
{
    Representative.ajax.importRepresentative($(this).attr("data-val"), true);
    $("#doublesRepresentative").modal("hide");
});

$("#addRepresentativeAnyway").live("click", function ()
{
    $("#tabRepresentative .addRepresentative").trigger("click", ["true"]);
    $("#doublesRepresentative").modal("hide");
});


/**
 * <p>
 * Function called each time the fields for the representative need to be shown.
 * </p>
 * <p>
 * It toggles the economic connections inputs.
 *
 * It fills the #representativeSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the representative is being added or edited.
 * If the representative is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateRepresentativeSection(html, forEditing)
{
    $("#representativeSection").html(html);
    toggleRepresentativeEconomicConnections();
    
/*    if($(html).hasClass('formEF')){
    	$("#representativeImportTextfield").val($("#firstName").val());
    }
*/
    $("#representativeSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Representative.numberOfAddedCorrespondenceAddresses = parseInt($("#representativeSection #numberOfCA").val());
    Representative.correspondenceAddressIndex = Representative.numberOfAddedCorrespondenceAddresses;
    if (forEditing && Representative.numberOfAddedCorrespondenceAddresses > 0)
    {
        representativeCorrespAddrTemplate_Create("#representativeSection");
        $("#representativeSection #representativeHasCorrespondenceAddress").attr("checked", "checked");
        if (Representative.numberOfAddedCorrespondenceAddresses < Representative.getMaxCorrespondenceAddresses())
        {
            $("#representativeSection .addAnotherCA").show();
        }
        if(Representative.getMaxCorrespondenceAddresses() <= 1)
        {
            $("#representativeSection .removeCA").hide();
        }
    }

    // set type of representative in select box
    var representativeType = $("#representativeSection form .reptypehidden").val();
    $("#representativeSection #reptype").val(representativeType);

    representativeCorrespAddrTemplate_Neutralize("#representativeSection #correspondenceAddressPath");
}

function representativeCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#representativeSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function representativeCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    representativeCorrespAddrTemplate_Modify("#representativeSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function representativeCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
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
$("#representativeSection #representativeHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#representativeSection #correspondenceAddressPath").val();
        representativeAppendCA(path);
        $("#representativeSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#representativeSection select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#representativeSection #correspondenceAddressList").html("");
        $("#representativeSection .addAnotherCA").hide();
        Representative.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Representative object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function representativeAppendCA(path)
{
    $("#representativeSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#representativeSection #correspondenceAddressTemplate").html();

    Representative.correspondenceAddressIndex += 1;
    Representative.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#representativeSection #correspondenceAddressList").append(template);

    if (Representative.getMaxCorrespondenceAddresses() <= Representative.numberOfAddedCorrespondenceAddresses)
    {
        //template = template.replace($(template).find(".addCA").parent().html(), "");
        $("#representativeSection .addAnotherCA").hide();
    }

    if(Representative.getMaxCorrespondenceAddresses() <= 1)
    {
        $("#representativeSection .removeCA").hide();
    }

    representativeCorrespAddrTemplate_Modify("#representativeSection #correspondenceAddressList", "PLACEHOLDER", path);
    
    $('#correspondenceAddressCountry').val($('#inputcorrespondenceAddressCountry').val());
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#representativeSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Representative.correspondenceAddressIndex + "]";
    representativeAppendCA(path);
});

$("#representativeSection .addAnotherCA").live("click", function ()
{
    var path = $("#representativeSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Representative.correspondenceAddressIndex + "]";
    representativeAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Representative number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#representativeSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Representative.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Representative.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#representativeSection #representativeHasCorrespondenceAddress").removeAttr("checked");
        $("#representativeSection .addAnotherCA").hide();
    }

    if (Representative.numberOfAddedCorrespondenceAddresses > 0 &&
        Representative.numberOfAddedCorrespondenceAddresses < Representative.getMaxCorrespondenceAddresses())
    {
        $("#representativeSection .addAnotherCA").show();
    }
});

function representativeResetCACounter()
{
    Representative.correspondenceAddressIndex = 0;
    Representative.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabRepresentative']").live("click", function ()
{
    if (($("#representativeConfig_searchEnabled").val() == "true") && ($("#service_representativeMatches_enabled") == "true"))
    {
        showWarningModal($("#representativeCreationInfo").html());
    }
});


$("#representativeImportTextfield").live("focus", function ()
{
    initAutocomplete(this);
    handleDisplayOfRepresentativeAutocompleteTable(this);

//		    $(this).bind("autocompleteselect", function(event, ui)
//		    {
//		        $("#representativeImportTextfield").val(ui.item.rid);
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
            $("#representativeImportTextfield").val(ui.item.rid);
            return false;
        }, 100);
    })
})

$("#representativeSection input[name='associationId'].autocompleted").live("focus", function ()
{
    initAutocomplete(this);
    handleDisplayOfRepresentativeAutocompleteTable(this);
    $(this).bind("autocompleteselect", function (event, ui)
    {
        $("#representativeSection input[name='associationName']").val(ui.item.rnm);
        $("#representativeSection input[name='associationId']").val(ui.item.rid);
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
            $("#representativeSection input[name='associationName']").val(ui.item.rnm);
            $("#representativeSection input[name='associationId']").val(ui.item.rid);
            return false;
        }, 100);
    })
})

function handleDisplayOfRepresentativeAutocompleteTable(object)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        var searchUrlEnabled = $("#representativeConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if (searchUrlEnabled == "true")
        {
            var viewMessage = $("#representativeConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getRepresentativeAutocompleteNavigationUrl([
                    ["representativeId", item.rid]
                ]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {

            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a class='wider'><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.rnm + "</span>" +
                    "<span class='second-col'>" + item.rid + "</span>" +
                    "<span class='third-col'>" + $("#"+item.rtp+"RepresentativeMsg").html() + "</span>" +
                    "</span></span></a>" + wrappedUrl)
                .appendTo(ul)
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
        }
    }
}

function getRepresentativeAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#representativeConfig_searchUrl").val());
}

function postUpdateRepresentativeSection()
{

    if($("#representativeSection #reptype").length > 0) {
        $("#representativeSection #reptype").attr("disabled", "disabled");

        if($("#representativeSection #reptype").val() == "" && !($("#representativeSection #importedRepresentative").val() == "true")) {
            $("#representativeSection #addRepresentativeTopButton").attr("disabled", "disabled");
        } else {
            $("#representativeSection #addRepresentativeTopButton").removeAttr("disabled");
        }
    }

    Person.showNaturalPersonTipForBG(".representativeNaturalPersonTip", $("#representativeSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#representativeSection select[name='address.country']").val(),
        "#representativeSection select[name='address.stateprovince']",
        $("#representativeSection input[id='address.selectedstate']").val());

    $("#representativeSection select[id^='correspondenceAddressCountry']").each(function ()
    {
        Person.handleCorrespondenceAddressStates(this, "#representativeSection select[id='corrStateProvince']");
    })
    if($("#representativeSection #legalPractitionerType").size() > 0)
    {
        toggleRepresentativeAssociation("#legalPractitionerType");
    }
    if($("#representativeSection #professionalPractitionerType").size() > 0)
    {
        toggleRepresentativeAssociation("#professionalPractitionerType");
    }
    
}


$("#representativeSection select[name='address.country']").live("change", function ()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#representativeSection select[id='address.stateprovince']");
})

$("#representativeSection select[name='nationality']").live("change", function ()
{
    Person.showNaturalPersonTipForBG(".representativeNaturalPersonTip", $(this).val());
})

$("#representativeSection select[id^='correspondenceAddressCountry']").live("change", function ()
{
    Person.handleCorrespondenceAddressStates(this, "#representativeSection select[id='corrStateProvince']");
})

$("#representativeCreateNew").live("click", function()
{
    Representative.ajax.displayChooseRepresentative();
})

// sorting functionality trigger
$("#representativeCardList table th a.sorter").live("click", function()
{
    Person.sortTable("Representative", $(this));
})

/////////////////////////////
// REPRESENTATIVE SPECIFIC //
/////////////////////////////
$("#representativeSection #economicConnections").live("change", function ()
{
    toggleRepresentativeEconomicConnections();
});

function toggleRepresentativeEconomicConnections()
{
    if ($("#representativeSection #economicConnections").is(":checked")
        || $("#representativeSection [name='economicConnections'][type='checkbox']").is(":disabled"))
    {
        $("#representativeSection #withoutEconomicConnections").removeAttr("checked");
        $("#representativeSection #economicConnectionsFields").show();
    }
    else
    {
        $("#representativeSection #withoutEconomicConnections").attr("checked", "checked");
        $("#representativeSection #economicConnectionsFields").hide();
        $("#representativeSection #economicConnectionsFields input").val("");
    }
    if($("#representativeSection [name='economicConnections'][type='checkbox']").is(":disabled"))
    {
        $("#representativeSection #withoutEconomicConnections").attr("disabled", "disabled");
    }
}

$("#representativeSection #withoutEconomicConnections").live("change", function()
{
    if($(this).is(":checked"))
    {
        $("#representativeSection #economicConnections").removeAttr("checked");
    }
    else
    {
        $("#representativeSection #economicConnections").attr("checked", "checked");
    }
    toggleRepresentativeEconomicConnections();
})

function checkNewAssociation()
{
    if(($("#representativeSection #associationId").is(":visible")
        && !$("#representativeSection #associationId").is(":disabled")
        && $("#representativeSection #associationId").val().trim() == "")
        ||
        ($('#representativeSection #reptype').val() == "ASSOCIATION"
        && $('#representativeSection #importedRepresentative').val() !== "true"))
    {
        showMessageModal($("#representative_newAssociation").html());
    }
}

$("#representativeSection #legalPractitionerType").live("change", function()
{
    toggleRepresentativeAssociation("#representativeSection #legalPractitionerType");
})

$("#representativeSection #professionalPractitionerType").live("change", function()
{
    toggleRepresentativeAssociation("#representativeSection #professionalPractitionerType");
})

function toggleRepresentativeAssociation(selector)
{
    if($(selector).val() == "ASSOCIATION")
    {
        $("#representativeSection #associationId").val("");
        $("#representativeSection #associationId").parent().hide();

        if($(selector).attr("name") == "legalPractitionerType")
        {
            $("#representativeSection #legalPractitionerPersonFields input").each(function()
            {
                $(this).val("");
            });
            $("#representativeSection #legalPractitionerPersonFields").hide();
        }
        return;
    }
    $("#representativeSection #legalPractitionerPersonFields").show();
    $("#representativeSection #associationId").parent().show();
}

function refreshRepresentativesPayerSection() {
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

function refreshProvidedPOW(){
    var opts = $(".file-upload-radio input[type='radio']").length;
    var selectedOpts = $(".file-upload-radio input[type='radio']:checked").length;
    if(opts > 0 && selectedOpts == 0) {
        $(".file-upload-radio input[type='radio']").each(function () {
            if ($(this).attr("id").indexOf("provided") != -1) {
                $(this).click();
            }
        });
    }
}

function loadIntlPRepresentatives() {
    Representative.ajax.loadIntlPRepresentatives();
}

$("#representativeIntellectualImportBtn").live("click", function (){
    Representative.ajax.importIntlPRepresentative($("#representativeIntellectualSelect").val());
});