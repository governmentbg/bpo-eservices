var Designer =
    {
        correspondenceAddressIndex:0,
        numberOfAddedCorrespondenceAddresses:0,
        getMaxCorrespondenceAddresses:function ()
        {
            return parseInt($("#designerSection #maxCorrespondenceAddresses").val());
        },
        getDesignerCount: function()
        {
            return Person.numberOfTableRowsNoHeader("#userDataDesigners")
                + Person.numberOfTableRowsNoHeader("#addedDesigners");
        },
        buildListFromTable: function(tableSelector)
        {
            var list = new Array();
            $(tableSelector + " tr[id^='designer_id_']").each(function()
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
                var row = "<tr id='designer_id_" + list[i].id + "'>";

                row += "<td data-val='number'>";
                row += list[i].number;
                row += "</td>";

                /* row += "<td data-val='id'>";
                 row += list[i].id;
                 row += "</td>";
                 */
                // row += "<td data-val='type'>";
                // row += list[i].type;
                // row += "</td>";

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
            var list = Designer.buildListFromTable(tableSelector);
            list.sort(Common.Sort.predicate(property, descending));
            Designer.buildTableFromList(tableSelector, list);
        }
    };

Designer.nav =
    {
        addNaturalPerson:"addDesignerNaturalPerson.htm",
        getForEdit:"getESDesignerForEdit.htm",
        remove:"removeESDesigner.htm",
        importApp: "importESDesigner.htm",
        addUserNaturalPerson: "addUserNaturalPersonDetails.htm",
        addUserDesigner: "addUserDesigner.htm",
    };

Designer.globals = {
    changedRemoveId: "",
};

Designer.ajax =
    {

        addUserDesignerDetails: function()
        {
            removeDesignerErrors();
            var designerSelected = new Array();
            $('#section_designerUser input:checkbox:checked').each(function()
            {
                designerSelected.push($(this).val());
            }) ;

            var dataToSend = "selectedDesignerUser="+ designerSelected;

            $.ajax({
                url: Designer.nav.addUserDesigner,
                data: dataToSend,
                type: 'POST',
                async: false,
                success: function(html) {
                    refreshDesignerCards(html);
                    hideSectionDesignerUser();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#section_designerUser", true);
                }
            });

        },

        addUserNaturalPerson: function()
        {

            removeDesignerErrors();
            $.ajax(
                {
                    url:Designer.nav.addUserNaturalPerson,
                    type:"POST",
                    async: false,
                    success:function (html)
                    {
                        // Check if returned form is a result of designers matching
                        if ($("input#designerMatchesExist", $(html)).val() == "true")
                        {
                            // show popup with matches
                            $("#designerMatches").html(html);
                            $("#doublesDesigner").modal("show");
                            return;
                        }

                        // check if returned form is a form containing errors
                        if ($("input#formReturned", $(html)).val() == "true")
                        {
                            // then display the errors
                            displayDesignerValidationErrors(html);
                            return;
                        }

                        if ($("input#importDesignerUser", $(html)).length>0)
                        {
                            showModalDesignerSelection(html);
                        }
                        else
                        {
                            refreshDesignerCards(html);
                        }



                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.",
                            "#designerSection", true);
                    }
                });
        },
        /**
         * Posts designer information to server
         * @param sender
         * @param address
         */
        addDesignerPost: function(sender, address, ignoreMatches)
        {
            var dataToSend = $("#designerSection form").serialize();
            if (ignoreMatches)
            {
                dataToSend = dataToSend + "&ignoreMatches=true";
            }

            if( $("#showModalOwner").val() && !($("#designerIsOwner").is(":checked")) ){
                showWarningModal($("#ownerErrorText").val());
            }

            removeDesignerErrors();
            $.ajax(
                {
                    url:address,
                    type:"POST",
                    async: false,
                    data:dataToSend,
                    success:function (html)
                    {
                        // Check if returned form is a result of designers matching
                        if ($("input#designerMatchesExist", $(html)).val() == "true")
                        {
                            // show popup with matches
                            $("#designerMatches").html(html);
                            $("#doublesDesigner").modal("show");
                            return;
                        }

                        // check if returned form is a form containing errors
                        if ($("input#formReturned", $(html)).val() == "true")
                        {
                            // then display the errors
                            displayDesignerValidationErrors(html);
                            return;
                        }

                        // otherwise, just refresh designer cards
                        refreshDesignerCards(html);
                        if(Designer.globals.changedRemoveId != ""){
                            Designer.ajax.removeDesigner(Designer.globals.changedRemoveId);
                            Designer.globals.changedRemoveId = "";
                        }
                        $(".cancelButton.designer").click();

                        //refreshDesignersPayerSection();
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.",
                            "#designerSection", true);
                    }
                });
        },
        /**
         * Gets filled designer form to edit
         * @param designerId
         */
        getDesignerForm: function(designerId, rowNumber)
        {
            removeDesignerErrors();
            $.ajax({
                url:Designer.nav.getForEdit,
                type:"GET",
                data:"id=" + designerId,
                success:function (html)
                {
                    updateDesignerSection(html, true);

                    $("#designerCurrentNumber").html(rowNumber);
                    // set "save" text for editing
                    $("#designerSection .addDesigner").each(function()
                    {
                        $(this).html($("#personEditText").html());
                    })

                    $("#designerSection .addDesignerTopButton").each(function()
                    {
                        $(this).html($("#personEditText").html());
                    })

                    showDesignerTab("edit");

                    postUpdateDesignerSection();
                    //in edit mode Type is disabled
                    // $('#appSelec').hide();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#designerCardList", true);
                }
            });
        },
        /**
         * Removes designer with id embedded in sender's data-val attribute then refreshes designer cards.
         * @param designerId
         */
        removeDesigner: function(designerId)
        {
            removeDesignerErrors();
            $.ajax({
                    url:Designer.nav.remove,
                    data:"id=" + designerId,
                    type:"GET",
                    success:function (html)
                    {
                        refreshDesignerCards(html);
                        //refreshDesignersPayerSection();
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.",
                            "#designerCardList", true);
                    }
                }
            );
        },
        /**
         * Imports an designer
         * @param designerId designer's id
         */
        importDesigner: function(designerId)
        {
            if(designerId == null || designerId.trim() == "")
            {
                showWarningModal($("#designer_missingInputText").html());
                return;
            }
            removeDesignerErrors();
            //Disable the page to not allow the user to click anything else while the process ends
            showLoadingScreen();
            $.ajax({
                url:Designer.nav.importApp,
                data:"id=" + designerId,
                type:"GET",
                success:function (html)
                {
                    updateDesignerSection(html, true);

                    if(Person.containsImportError(html))
                    {
                        hideLoadingScreen();
                        return;
                    }

                    // set this so that adding doesn't check for matches anymore
                    $("#importedDesigner").attr("value", "true");

                    postUpdateDesignerSection();

                    //            // set isImported field
                    //            $("#designerSection #importedDesigner").val("true");
                    hideLoadingScreen();
                },
                error:function (error)
                {
                    hideLoadingScreen();
                    genericHandleError($("#designer_cannotImport").html(),
                        "#designerSection", true);
                }
            });
        }
    }

$("#designerTrigger").live("click", function()
{
    toggleDesignerAdd();
    $("#designerCurrentNumber").html(Designer.getDesignerCount() + 1);

})


$("#importDesignerNaturalPerson").live("click", function()
{
    Designer.ajax.addUserNaturalPerson();

})

$("#buttonSaveAddDesignerUser").live("click", function()
{
    Designer.ajax.addUserDesignerDetails();

})

$(".addDesignerBtn").live("click", function (event, ignoreMatches)
{
    if(mustIgnoreDesignerMatches())
    {
        ignoreMatches = "true";
    }
    Designer.ajax.addDesignerPost(this, Designer.nav.addNaturalPerson, ignoreMatches);
});

function showModalDesignerSelection(html)
{
    $("#section_designerUser").html(html);
    $("#section_designerUser").modal("show");
}

function hideSectionDesignerUser()
{
    $('#section_designerUser').modal("hide");
    $('.modal-backdrop fade in').each(function()
    {
        $(this).modal("hide");
    });

}

function toggleDesignerAdd()
{
    if($("#tabDesigner").is(":hidden"))
    {
        $("#designerTrigger").addClass("active");
        $('#designerSection').html('');
        showDesignerTab();
    }
    else
    {
        $("#designerTrigger").removeClass("active");
        hideDesignerTab();
    }

}

function showDesignerTab(forEdit)
{
    $("#tabDesigner").show();
    $("#designerTrigger").addClass("active");
}

function hideDesignerTab()
{
    $("#tabDesigner").hide();
    $("#designerTrigger").removeClass("active");
}


function scrollToDesignersTop()
{

    scrollToContainer("#designerCardList");
}

$(".cancelButton.designer").live("click", function ()
{
    $("#designerSection").html("");
    toggleDesignerAdd();
    scrollToDesignersTop();
    designerResetCACounter();
});

function checkMaxDesigners(){
    var maxDesigners=$('#maxDesigners').val();

    var rowCount = $('#designerCardList tr').length-1;

    if(rowCount>=maxDesigners){
        $("#designerTrigger").hide();
        if ($("#importDesignerNaturalPerson").length>0){
            $("#importDesignerNaturalPerson").hide();
        }
        if ($("#importDesignerLegalEntity").length>0){
            $("#importDesignerLegalEntity").hide();
        }
    }else{
        $("#designerTrigger").show();
        if ($("#importDesignerNaturalPerson").length>0){
            $("#importDesignerNaturalPerson").show();
        }
        if ($("#importDesignerLegalEntity").length>0){
            $("#importDesignerLegalEntity").show();
        }

    }
}

function refreshDesignerCards(html)
{
    $("#designerCardList").html(html);
    scrollToDesignersTop();

    checkMaxDesigners();
}


function displayDesignerValidationErrors(html)
{
    updateDesignerSection(html, true);
    postUpdateDesignerSection();
}

function mustIgnoreDesignerMatches()
{
    if ($("#importedDesigner").val() == "true")
    {
        return "true";
    }
}

$("#addDesignerTopButton").live("click", function (event, ignoreMatches)
{
    $(".addDesigner").trigger("click", [ignoreMatches]);
});

function removeDesignerErrors()
{
    removePreviousErrors("#designerCardList");
    removePreviousErrors("#designerSection");
}

$("#editDesignerButton").live("click", function ()
{
    Designer.ajax.getDesignerForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteDesignerButton").live("click", function ()
{
    var designerId = $(this).attr("data-val");
    var onConfirmYes = partial(Designer.ajax.removeDesigner, designerId);
    showConfirmModal($("#designer_deleteConfirmation").html(), onConfirmYes);
});

$('#designerImportButton').live("click", function ()
{
    var id = $("#designerImportTextfield").val();
    if(id.trim() == "")
    {
        showConfirmModal($("#designer_missingInputText").html());
        return;
    }
    Designer.ajax.importDesigner(id);
});

$(".importDesignerFromMatches").live("click", function ()
{
    Designer.ajax.importDesigner($(this).attr("data-val"));
    $("#doublesDesigner").modal("hide");
});

$("#addDesignerAnyway").live("click", function ()
{
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
function updateDesignerSection(html, forEditing)
{
    if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#designerSection .importErrorPlaceholder").html(html);
        return;
    }

    $("#designerSection").html(html);

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Designer.numberOfAddedCorrespondenceAddresses = parseInt($("#designerSection #numberOfCA").val());
    Designer.correspondenceAddressIndex = Designer.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Designer.numberOfAddedCorrespondenceAddresses > 0)
    {
        designerCorrespAddrTemplate_Create("#designerSection");
        $("#designerSection #designerHasCorrespondenceAddress").attr("checked", "checked");
    }

    // set type of designer in select box
    // var designerType = $("#designerSection form .apptypehidden").val();
    // $("#aptype").val(designerType);


    designerCorrespAddrTemplate_Neutralize("#designerSection #correspondenceAddressPath");
}

function designerCorrespAddrTemplate_Create(containerQuery)
{
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#designerSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function designerCorrespAddrTemplate_Neutralize(pathContainerQuery)
{
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined)
    {
        return;
    }
    designerCorrespAddrTemplate_Modify("#designerSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function designerCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement)
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
$("#designerHasCorrespondenceAddress").live("click", function ()
{
    if ($(this).is(":checked"))
    {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#designerSection #correspondenceAddressPath").val();
        designerAppendCA(path);
        $("#designerSection [id^='correspondenceAddressCountry']").each(function ()
        {
            Person.handleCorrespondenceAddressStates(this, "#designerSection select[id='corrStateProvince']");
        });
    }
    else
    {
        // remove all added correspondence addresses:
        $("#designerSection #correspondenceAddressList").html("");
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
function designerAppendCA(path)
{
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#designerSection #correspondenceAddressTemplate").html();

    Designer.correspondenceAddressIndex += 1;
    Designer.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#designerSection #correspondenceAddressList").append(template);

    designerCorrespAddrTemplate_Modify("#designerSection #correspondenceAddressList", "PLACEHOLDER", path);

    $('#correspondenceAddressCountry').val($('#inputcorrespondenceAddressCountry').val());
    //$('#correspondenceAddressCountry').selectmenu("refresh");
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#designerSection .addCA").live("click", function ()
{
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Designer.correspondenceAddressIndex + "]";
    designerAppendCA(path);
});

$("#designerSection .addAnotherCA").live("click", function ()
{
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
$("#designerSection .removeCA").live("click", function ()
{
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Designer.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Designer.numberOfAddedCorrespondenceAddresses == 0)
    {
        $("#designerSection #designerHasCorrespondenceAddress").removeAttr("checked");
    }
});

function designerResetCACounter()
{
    Designer.correspondenceAddressIndex = 0;
    Designer.numberOfAddedCorrespondenceAddresses = 0;
}

$("a[href='#tabDesigner']").live("click", function ()
{
    if (($("#designerConfig_searchEnabled").val() == "true") && ($("#service_designerMatches_enabled") == "true"))
    {
        showWarningModal($("#designerCreationInfo").html());
    }
})

$("#designerImportTextfield").live("focus", function()
{
    initAutocomplete(this);
    if($(this).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(this).data("autocomplete")._renderItem = function(ul, item)
    {
        var searchUrlEnabled = $("#designerConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true")
        {
            var viewMessage = $("#designerConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getDesignerAutocompleteNavigationUrl([["designerId", item.auri]]) + "'>" +
                "<span class='fourth-col'>" + viewMessage + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {

            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a class='wider'><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.dnm + "</span>" +
                    "<span class='second-col'>" + item.did + "</span>" +
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

            $("#designerImportTextfield").val(ui.item.did);
            return false;
        }, 100);
    })
})

function getDesignerAutocompleteNavigationUrl(keyValuePairArray)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#designerConfig_searchUrl").val());
}

function postUpdateDesignerSection()
{
    // if($("#designerSection #importedDesigner").val() == "true")
    // {
    //     // disable the designer type select box
    //     // $("#aptype").attr("disabled", "disabled");
    // }


    // if($("#aptype").val() == "") {
    //     $("#addDesignerTopButton").attr("disabled", "disabled");
    // } else {
    //     $("#addDesignerTopButton").removeAttr("disabled");
    // }


    if($("#designerSection select[name='nationality']").val() == "US")
    {
        designerDisplayStateOfIncorporation(true);
    }
    else
    {
        designerDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".designerNaturalPersonTip", $("#designerSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#designerSection select[name='address.country']").val(),
        "#designerSection select[name='address.stateprovince']",
        $("#designerSection input[id='address.selectedstate']").val());

    $("#designerSection select[id^='correspondenceAddressCountry']").each(function()
    {
        Person.handleCorrespondenceAddressStates(this, "#designerSection select[id='corrStateProvince']");
    });
    addCreateNewDesignerOnChangeFunctionality();
}

$("#designerSection select[name='nationality']").live("change", function()
{
    if($(this).val() == "US")
    {
        designerDisplayStateOfIncorporation(true);
    }
    else
    {
        designerDisplayStateOfIncorporation(false);
    }
})

function designerDisplayStateOfIncorporation(display)
{
    if(display)
    {
        Person.fillStatesSelectbox("US",
            "#designerSection select[id='stateOfIncorporation']",
            $("#designerSection input[id='selectedStateOfIncorporation']").val());
        $("#designerSection select[id='stateOfIncorporation']").parent().show();
    }
    else
    {
        $("#designerSection select[id='stateOfIncorporation']").val("");
        $("#designerSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#designerSection select[name='address.country']").live("change", function()
{
    Person.fillStatesSelectbox(
        $(this).val(),
        "#designerSection select[name='address.stateprovince']");
});
$("#designerSection select[name='nationality']").live("change", function()
{
    Person.showNaturalPersonTipForBG(".designerNaturalPersonTip", $(this).val());
});

$("#designerSection select[id^='correspondenceAddressCountry']").live("change", function()
{
    Person.handleCorrespondenceAddressStates(this, "#designerSection select[id='corrStateProvince']");
});

$("#designerCreateNew").live("click", function()
{
    $.ajax({
        url:Designer.nav.addNaturalPerson,
        type:"GET",
        data:"",
        success:function (html)
        {
            updateDesignerSection(html, false);
            postUpdateDesignerSection();
        },
        error:function (error)
        {
            genericHandleError("An error occured while processing your request. Please try again later.",
                "#designerSection", true);
        }
    });
});

// sorting functionality trigger
$("#designerCardList table th a.sorter").live("click", function()
{
    Person.sortTable("Designer", $(this));
});

function addCreateNewDesignerOnChangeFunctionality() {
    //TODO:Create a flag somewhere if the "importedDesigner" is should be deleted on designer change, and check for it before adding this functionality...
    //if the designer is imported, and some of the imported values are changed, then the  hiddenFormId and importedDesigner fields are deleted, which means that new designer will be created with the imported data...
    $("#designerDetailsForm input:visible,#designerDetailsForm textarea:visible,#designerDetailsForm select:visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedDesigner').val() == 'true') {
                showConfirmModal($('#designerCreateNewOnImportedChange').html(),
                    function() {
                        $('#designerDetailsForm #importedDesigner').val('false');
                        var designerId = $('#designerDetailsForm #hiddenFormId').val();
                        var delBtnArr = $("#deleteDesignerButton[data-val='"+designerId+"']");
                        if(delBtnArr.size() >0){
                            Designer.globals.changedRemoveId = designerId;
                        }
                        $('#designerDetailsForm #hiddenFormId').val('');
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