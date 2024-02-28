var Inventor = {

    correspondenceAddressIndex: 0,
    numberOfAddedCorrespondenceAddresses: 0,

    getMaxCorrespondenceAddresses: function() {
        return parseInt($("#inventorSection #maxCorrespondenceAddresses").val());
    },

    getInventorCount: function() {
        return Person.numberOfTableRowsNoHeader("#userDataInventors") + Person.numberOfTableRowsNoHeader("#addedInventors");
    },

    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='inventor_id_']").each(function(){
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
            var row = "<tr id='inventor_id_" + (i + 1) + "'>";

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
        var list = Inventor.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Inventor.buildTableFromList(tableSelector, list);
    }
};

Inventor.nav = {
    addInventor: "addInventor.htm",
    displayWaiver: "displayInventorWaiver.htm",
    displayGroup: "displayInventorGroup.htm",
    displayNotAGroup: "displayInventorNotAGroup.htm",
    getForEdit:"getInventorForEdit.htm",
    remove:"removeInventor.htm",
    import: "importInventor.htm"
};

Inventor.ajax = {

    displayAddWaiver: function() {
        removeInventorErrors();
        $.ajax({
            url: Inventor.nav.displayWaiver,
            type: "GET",
            data: "",
            success: function(html) {
                updateInventorSection(html, false);
                // Set checkbox inventor waives.
                $("#checkWaiver").prop("checked", true);
                $("#topButtons").remove();
                postUpdateInventorSection();
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorSection", true);
            }
        });
    },

    displayAddGroup: function() {
        removeInventorErrors();
        $.ajax({
            url: Inventor.nav.displayGroup,
            type: "GET",
            data: "",
            success: function(html) {
                updateInventorSection(html, false);
                // Set checkbox inventor belongs to a group of inventors.
                $("#checkBelongs").prop("checked", true);
                postUpdateInventorSection();
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorSection", true);
            }
        });
    },

    displayAddNotAGroup: function() {
        removeInventorErrors();
        $.ajax({
            url: Inventor.nav.displayNotAGroup,
            type: "GET",
            data: "",
            success: function(html) {
                updateInventorSection(html, false);
                postUpdateInventorSection();
            }, error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorSection", true);
            }
        });
    },

    /**
     * Gets filled inventor form to edit.
     * @param inventorId
     */
    getInventorForm: function(inventorId, rowNumber) {
        removeInventorErrors();
        $.ajax({
            url: Inventor.nav.getForEdit,
            type: "GET",
            data: "id=" + inventorId,
            success: function(html) {
                updateInventorSection(html, true);
                $("#inventorCurrentNumber").html(rowNumber);

                showInventorTab("edit");
                postUpdateInventorSection();
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorCardList", true);
            }
        });
    },

    /**
     * Removes inventor with id embedded in sender's data-val attribute then refreshes inventor cards.
     * @param inventorId
     */
    removeInventor: function(inventorId) {
        removeInventorErrors();
        $.ajax({
            url: Inventor.nav.remove,
            data: "id=" + inventorId,
            type: "GET",
            success: function(html) {
                refreshInventorCards(html);
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorCardList", true);
            }
        });
    },

    /**
     * Imports an inventor.
     * @param inventorId inventor's id
     */
    importInventor: function(inventorId) {
        if (inventorId == null || inventorId.trim() == "") {
            showWarningModal($("#inventor_missingInputText").html());
            return;
        }
        removeInventorErrors();
        // Disable the page to not allow the user to click anything else while the process ends.
        showLoadingScreen();
        $.ajax({
            url: Inventor.nav.import,
            data: "id=" + escape(inventorId),
            type: "GET",
            success: function(html) {
                updateInventorSection(html, true);

                if (Person.containsImportError(html)) {
                    hideLoadingScreen();
                    return;
                }

                // set this so that adding doesn't check for matches anymore
                $("#importedInventor").attr("value", "true");

                postUpdateInventorSection();
                hideLoadingScreen();
            },
            error: function(error) {
                hideLoadingScreen();
                genericHandleError($("#inventor_cannotImport").html(), "#inventorSection", true);
            }
        });
    },

    /**
     * Posts inventor information to server.
     * @param sender
     * @param address
     */
    addInventorPost: function(address, ignoreMatches) {
        var dataToSend = $("#inventorSection form").serialize();
        if (ignoreMatches) {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeInventorErrors();
        $.ajax({
            url: address,
            type: "POST",
            async: false,
            data: dataToSend,
            success: function(html) {
                // Check if returned form is a result of inventors matching
                if ($("input#inventorMatchesExist", $(html)).val() == "true") {
                    // show popup with matches
                    $("#inventorMatches").html(html);
                    $("#doublesInventor").modal("show");
                    return;
                }

                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                    // then display the errors
                    displayInventorValidationErrors(html);
                    return;
                }

                // otherwise, just refresh inventor cards
                refreshInventorCards(html);
                $(".cancelButton.inventor").click();


            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#inventorSection", true);
            }
        });
    }
};


$("#inventorTrigger").live("click", function() {
    addInventor();
});

function addInventor() {
    toggleInventorAdd();
    $("#inventorCurrentNumber").html(Inventor.getInventorCount() + 1);
}

function toggleInventorAdd() {
    if($("#tabInventor").is(":hidden")){
        $("#inventorTrigger").addClass("active");
        showInventorTab();
    } else {
        $("#inventorTrigger").removeClass("active");
        hideInventorTab();
    }
}

function showInventorTab(forEdit) {
    $("#tabInventor").show();
    if (($("#inventorCreateNew").size() == 0 || $("#inventorCreateNew").is(":hidden")) && forEdit != "edit") {
        Inventor.ajax.displayAddNotAGroup();
    }
    $("#inventorTrigger").addClass("active");
}

function hideInventorTab() {
    $("#tabInventor").hide();
    $("#inventorSection").html("");
    $("#inventorTrigger").removeClass("active");

    $("#inventorCardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToInventorsTop() {
    scrollToContainer("#inventorCardList");
}

function inventorResetCACounter() {
    Inventor.correspondenceAddressIndex = 0;
    Inventor.numberOfAddedCorrespondenceAddresses = 0;
}

$("#checkWaiver").live("click", function(){
    if ($(this).is(":checked")){
        Inventor.ajax.displayAddWaiver();
    } else {
        Inventor.ajax.displayAddNotAGroup();
    }
});

$("#checkBelongs").live("click", function(){
    if ($(this).is(":checked")){
        Inventor.ajax.displayAddGroup();
    } else {
        Inventor.ajax.displayAddNotAGroup();
    }
});

$(".cancelButton.inventor").live("click", function(){
    $("#inventorSection").html("");
    toggleInventorAdd();
    scrollToInventorsTop();
    inventorResetCACounter();
    //$("#inventorCardList").find("tr").each(function(){
    //   $(this).removeClass("active");
    //})
});

function refreshInventorCards(html) {
    $("#inventorCardList").html(html);
    scrollToInventorsTop();
}

function displayInventorValidationErrors(html) {
    updateInventorSection(html, true);
    postUpdateInventorSection();
}

function mustIgnoreInventorMatches(){
    return $("#importedInventor").val() == "true";
}

$(".addInventor").live("click", function(e, ignoreMatches) {
    saveInventor(ignoreMatches);
});

function saveInventor(ignoreMatches) {

    if(mustIgnoreInventorMatches()) {
        ignoreMatches = "true";
    }
    Inventor.ajax.addInventorPost(Inventor.nav.addInventor, ignoreMatches);
}

$("#addInventorTopButton").live("click", function (e, ignoreMatches) {
    $(".addInventor").trigger("click", [ignoreMatches]);
});




function removeInventorErrors() {
    removePreviousErrors("#inventorCardList");
    removePreviousErrors("#inventorSection");
}

$(".editInventorButton").live("click", function() {
    Inventor.ajax.getInventorForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$(".deleteInventorButton").live("click", function() {
    var inventorId = $(this).attr("data-val");
    var onConfirmYes = partial(Inventor.ajax.removeInventor, inventorId);
    showConfirmModal($("#inventor_deleteConfirmation").html(), onConfirmYes);
});

$('#inventorImportButton').live("click", function() {
    var id = $("#inventorImportTextfield").val();
    if (id.trim() == "") {
        showConfirmModal($("#inventor_missingInputText").html());
        return;
    }

    Inventor.ajax.importInventor(id);
});

$(".importInventorFromMatches").live("click", function() {
    Inventor.ajax.importInventor($(this).attr("data-val"));
    $("#doublesInventor").modal("hide");
});

$("#addInventorAnyway").live("click", function() {
    $("#tabInventor .addInventor").trigger("click", ["true"]);
    $("#doublesInventor").modal("hide");
});



/**
 * <p>
 * Function called each time the fields for the inventor need to be shown.
 * It fills the #inventorSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the inventor is being added or edited.
 * If the inventor is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateInventorSection(html, forEditing) {
    if (Person.containsImportError(html)) {
        showWarningModal($(".importError", $(html)).html());
        return;
    }

    $("#inventorSection").html(html);
    $("#inventorSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    Inventor.numberOfAddedCorrespondenceAddresses = parseInt($("#inventorSection #numberOfCA").val());
    Inventor.correspondenceAddressIndex = Inventor.numberOfAddedCorrespondenceAddresses;

    if (forEditing && Inventor.numberOfAddedCorrespondenceAddresses > 0) {
        inventorCorrespAddrTemplate_Create("#inventorSection");
        $("#inventorSection #inventorHasCorrespondenceAddress").attr("checked", "checked");
        if (Inventor.numberOfAddedCorrespondenceAddresses < Inventor.getMaxCorrespondenceAddresses()) {
            $("#inventorSection .addAnotherCA").show();
        }
        if (Inventor.getMaxCorrespondenceAddresses() <= 1) {
            $("#inventorSection .removeCA").hide();
        }
    }

    // Set waiver y belongs in the checboxes.
    if ($("#inventorSection form #inventorWaiverHidden").val() == "true"){
        $("#checkWaiver").prop('checked', true);
    }

    if ($("#inventorSection form #inventorBelongsHidden").val() == "true"){
        $("#checkBelongs").prop('checked', true);
    }

    inventorCorrespAddrTemplate_Neutralize("#inventorSection #correspondenceAddressPath");
}

function inventorCorrespAddrTemplate_Create(containerQuery) {
    var preTemplateContainer = $(containerQuery + " [id^='CAwrapper_']").first();
    $("#inventorSection #correspondenceAddressTemplate").html($(preTemplateContainer).html());
}

function inventorCorrespAddrTemplate_Neutralize(pathContainerQuery) {
    var correspAddressPath = $(pathContainerQuery).val();
    if (correspAddressPath == undefined) {
        return;
    }
    inventorCorrespAddrTemplate_Modify("#inventorSection #correspondenceAddressTemplate", correspAddressPath, "PLACEHOLDER");
}

function inventorCorrespAddrTemplate_Modify(containerQuery, currentPath, replacement) {
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
$("#inventorHasCorrespondenceAddress").live("click", function() {
    if ($(this).is(":checked")) {
        // Add first correspondence address: correspondenceAddress[0]
        var path = $("#inventorSection #correspondenceAddressPath").val();
        inventorAppendCA(path);
        $("#inventorSection [id^='correspondenceAddresses'][id$='stateprovince']").each(function() {
            Person.handleCorrespondenceAddressStates(this, "#inventorSection");
        });
    } else {
        // remove all added correspondence addresses:
        $("#inventorSection #correspondenceAddressList").html("");
        $("#inventorSection .addAnotherCA").hide();
        Inventor.numberOfAddedCorrespondenceAddresses = 0;
    }
});

/**
 * Appends the template for Correspondence Address in the list of correspondence addresses.
 * A valid path is assigned, using the Inventor object information.
 * The added list of fields will have their names equal to the path
 * and will be correctly interpreted by the Spring bindings.
 * example of a modified template input:
 * <input type="text" name="correspondenceAddress[3].street">
 *
 * @param path the correspondence address path to be assigned to the template
 */
function inventorAppendCA(path) {
    $("#inventorSection .addAnotherCA").show();
    var wrapperDivId = "CAwrapper_" + path;

    var template = "<span id='" + wrapperDivId + "'>";
    template += $("#inventorSection #correspondenceAddressTemplate").html();

    Inventor.correspondenceAddressIndex += 1;
    Inventor.numberOfAddedCorrespondenceAddresses += 1;

    // close wrapper div
    template += "</span>";

    $("#inventorSection #correspondenceAddressList").append(template);

    if (Inventor.getMaxCorrespondenceAddresses() <= Inventor.numberOfAddedCorrespondenceAddresses) {
        $("#inventorSection .addAnotherCA").hide();
    }
    if (Inventor.getMaxCorrespondenceAddresses() <= 1) {
        $("#inventorSection .removeCA").hide();
    }

    inventorCorrespAddrTemplate_Modify("#inventorSection #correspondenceAddressList", "PLACEHOLDER", path);
}

/**
 * Defines the function to execute when clicking the Add button of a correspondence address.
 * The wrapper is selected in order to determine the path of the CA.
 * A new CA is then appended, using this path.
 */
$("#inventorSection .addCA").live("click", function() {
    var rawPath = $(this).parents("[id^=CAwrapper_]").attr("id");
    var path = rawPath.replace("CAwrapper_", "");
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Inventor.correspondenceAddressIndex + "]";
    inventorAppendCA(path);
});

$("#inventorSection .addAnotherCA").live("click", function() {
    var path = $("#inventorSection #correspondenceAddressPath").val();
    path = path.replace(path.substring(path.indexOf('[')), "");
    path += "[" + Inventor.correspondenceAddressIndex + "]";
    inventorAppendCA(path);
});

/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Inventor number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#inventorSection .removeCA").live("click", function() {
    $(this).parents("[id^=CAwrapper_]").first().remove();
    Inventor.numberOfAddedCorrespondenceAddresses -= 1;

    // reset checkbox
    if (Inventor.numberOfAddedCorrespondenceAddresses == 0) {
        $("#inventorSection #inventorHasCorrespondenceAddress").removeAttr("checked");
        $("#inventorSection .addAnotherCA").hide();
    }

    if (Inventor.numberOfAddedCorrespondenceAddresses > 0 &&
        Inventor.numberOfAddedCorrespondenceAddresses < Inventor.getMaxCorrespondenceAddresses()) {
        $("#inventorSection .addAnotherCA").show();
    }
});

$("a[href='#tabInventor']").live("click", function() {
    if (($("#inventorConfig_searchEnabled").val() == "true") && ($("#service_inventorMatches_enabled") == "true")) {
        showWarningModal($("#inventorCreationInfo").html());
    }
});


$("#inventorImportTextfield").live("focus", function() {
    initAutocomplete(this);
    $("#inventorImportTextfield").removeAttr("can-import");
    if($(this).data("autocomplete") == null) {
        // autocomplete not enabled
        return;
    }

    $(this).data("autocomplete")._renderItem = function(ul, item) {
        var searchUrlEnabled = $("#inventorConfig_searchUrlEnabled").val();
        var wrappedUrl = "";
        if(searchUrlEnabled == "true") {
            var viewMessage = $("#inventorConfig_viewMessage").html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getInventorAutocompleteNavigationUrl([["inventorId", item.did]]) +
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

            $("#inventorImportTextfield").attr("can-import", 1);
            $("#inventorImportTextfield").val(ui.item.did);
            return false;
        }, 100);
    });
});

function getInventorAutocompleteNavigationUrl(keyValuePairArray) {
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#inventorConfig_searchUrl").val());
}

function postUpdateInventorSection() {
    if($("#inventorSection select[name='nationality']").val() == "US") {
        inventorDisplayStateOfIncorporation(true);
    } else {
        inventorDisplayStateOfIncorporation(false);
    }
    Person.showNaturalPersonTipForBG(".inventorNaturalPersonTip", $("#inventorSection select[name='nationality']").val());
    Person.fillStatesSelectbox(
        $("#inventorSection select[name='address.country']").val(),
        "#inventorSection select[name='address.stateprovince']",
        $("#inventorSection input[id='address.selectedstate']").val());

    $("#inventorSection select[id^='correspondenceAddresses'][id$='country']").each(function() {
        Person.handleCorrespondenceAddressStates(this, "#inventorSection");
    });

    addCreateNewInventorOnChangeFunctionality();
}

$("#inventorSection select[name='nationality']").live("change", function() {
    if($(this).val() == "US") {
        inventorDisplayStateOfIncorporation(true);
    } else {
        inventorDisplayStateOfIncorporation(false);
    }
});

function inventorDisplayStateOfIncorporation(display) {
    if(display) {
        Person.fillStatesSelectbox("US",
            "#inventorSection select[id='stateOfIncorporation']",
            $("#inventorSection input[id='selectedStateOfIncorporation']").val());
        $("#inventorSection select[id='stateOfIncorporation']").parent().show();
    } else {
        $("#inventorSection select[id='stateOfIncorporation']").val("");
        $("#inventorSection select[id='stateOfIncorporation']").parent().hide();
    }
}

$("#inventorSection select[name='address.country']").live("change", function() {
    Person.fillStatesSelectbox($(this).val(), "#inventorSection select[name='address.stateprovince']");
});
$("#inventorSection select[name='nationality']").live("change", function() {
    Person.showNaturalPersonTipForBG(".inventorNaturalPersonTip", $(this).val());
});

$("#inventorSection select[id^='correspondenceAddresses'][id$='country']").live("change", function() {
    Person.handleCorrespondenceAddressStates(this, "#inventorSection");
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

$("#inventorCreateNew").live("click", function() {
    Inventor.ajax.displayAddNotAGroup();
});

// sorting functionality trigger
$("#inventorCardList table th a.sorter").live("click", function() {
    Person.sortTable("Inventor", $(this));
});


function addCreateNewInventorOnChangeFunctionality() {
    //if the applicant is imported, and some of the imported values are changed, then the  hiddenFormId and importedApplicant fields are deleted, which means that new applicant will be created with the imported data...
    $("#inventorNotAGroup input:visible,#inventorNotAGroup textarea:visible,#inventorNotAGroup select:not([name*='Linked']):visible").each(function() {
        var el = $(this);
        el.data("prev",el.val());
        el.change(function() {
            if ($('#importedInventor').val() == 'true') {
                showConfirmModal($('#inventorCreateNewOnImportedChange').html(),
                    function() {
                        $('#importedInventor').val('false');
                        $('#inventorNotAGroup #hiddenFormId').val('');
                        $('#inventorImportTextfield').val('');
                    },
                    function() {
                        el.val(el.data("prev"));
                        Person.showNaturalPersonTipForBG(".inventorNaturalPersonTip", $("#inventorSection select[name='nationality']").val());
                    });
            }
        });
    });
}