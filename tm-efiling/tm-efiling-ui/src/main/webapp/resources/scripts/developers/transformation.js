$("#lastTransformation").live("focus", function ()
{
    initAutocomplete(this);
    tradeMarkAutocomplete(this, "#transformationTradeMarkConfig_searchUrlEnabled", "#transformationTradeMarkConfig_viewMessage",
        "#transformationTradeMarkConfig_searchUrl", "#transformationTradeMark_officeCode", "#transformationTradeMark_importId");
});

$('#normal #openTransformation').live("click", function() {
    if ($("#ctmTransformationForm").length > 0) {
        return;
    }
    removeAllHighlightRow
    newTransformationForm(this, 'claim/claim_transformation_details');
});

$('#wizard #openTransformation').live("click", function() {
    if ($("#ctmTransformationForm").length > 0) {
        showWarningModal($("#cannotEditTransformationConversionSameTime").html());
        $("#openTransformationLi").removeClass('active');
        return;
    }
    removeHighlightRow($('.transformation.active'));
    newTransformationForm(this, 'claim/claim_transformation_details_wizard');
});

$('.importTransformation').live("click", function() {
    // Checks if the user has selected a value using the autocomplete feature
    var importId = $("#transformationTradeMark_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#lastTransformation").val();
        if(!importId.startsWith($("#transformationTradeMark_officeCode").val())){
            //create ST13 code from what has been entered
            importId = generateST13ID(importId, $("#transformationTradeMark_officeCode").val());
        }
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }
    importPreviousCTM('ImportCTMTransformation', importId, $("#transformationTradeMark_officeCode").val(), $(this).attr('data-ask'));
});

$('.addTransformation').live("click", function() {
    addTransformationPost(true, this, 'claim/claim_transformation_details',
        'claim/claim_table_common');
});

$('.addAnotherTransformation').live("click", function() {
    addTransformationPost(false, this, 'claim/claim_transformation_details',
        'claim/claim_table_common');
});

$('.addTransformationWizard').live("click", function() {
    addTransformationPost(true, this, 'claim/claim_transformation_details_wizard',
        'claim/claim_table_common');
});

$('.addAnotherTransformationWizard').live("click", function() {
    addTransformationPost(false, this, 'claim/claim_transformation_details_wizard',
        'claim/claim_table_common');
});

$('.editTransformationCommon').live("click", function() {
    if($('body').attr('id') == 'normal') {
        getTransformationDetails($(this).attr("data-id"), 'claim/claim_transformation_details', $(this).closest('tr'));
    } else {
        getTransformationDetails($(this).attr("data-id"), 'claim/claim_transformation_details_wizard', $(this).closest('tr'));
    }
});

$('.removeTransformationCommon').live("click", function() {
    var removeFunc = partial(removeTransformation, $(this).attr("data-id"), 'claim/claim_table_common');
    showConfirmModal($("#transformation_remove").html(), removeFunc);
});

function newTransformationForm(tabPaneObject, claimDetails) {
    $('.claimSections .cancelButton').trigger('click');
    $(".tabClaim .flMessageError").closest(".tabClaim").hide()
    $.ajax({
        url: "addTransformation.htm",
        data: "detailsView=" + claimDetails,
        cache: false,
        success: function(html){
            $("#tabTransformation").html(html);
            $('#transformationCountryCode').val($("#transformationTradeMark_officeCode").val());
            $("#tabTransformation").show();
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            $("#tabTransformation").html(errorThrown);
            $("#tabTransformation").show();
        }
    });
}

function getTransformationDetails(id, claimDetails, row) {
    $.ajax({
        url: "addTransformation.htm",
        data: "id=" + id + "&detailsView=" + claimDetails,
        cache: false,
        success: function(html){
            $("#tabTransformation").html(html);
            $("#tabTransformation").addClass('active');
            $("#tabTransformation").show(); //for wizard
            $("#tabSeniority").removeClass('active');
            $("#tabExhibition").removeClass('active');
            $("#tabPriority").removeClass('active');
            //highlight the selected row
            highlightRow(row);
            resetClaimsNav(row);
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            $("#tabTransformation").html(errorThrown);
            $("#tabTransformation").show();
        }
    });
}

function removeTransformation(id, claimTable) {
    $.ajax({
        url: "removeTransformation.htm",
        data: "id=" + id + "&claimTable=" + claimTable,
        cache: false,
        success: function(html){
            //If post succeded we have to make two more actions:
            //Reload the common table
            $("#claimTableContainer").html(html);
            //If the removed one is currently being viewed in the card, hide it
            if($("#tabTransformation").attr('class').indexOf("active") !=-1 && $("#tabTransformation #id").val() == id) {
                $("#tabTransformation").removeClass('active');
                if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Transformation tab cannot be displayed anymore in oneform
                    $("#tabTransformation").hide();
                }
            }

            // change text of buttons to reflect change in number of transformations
            if(parseInt($("#transformationsSizeValue").val() < parseInt($("#maxTransformations").val()))){
                changeClaimText("#wizard #openTransformation", parseInt($("#transformationsSizeValue").val()));

                if ($("#wizard #openCtmTransformation").length >0 ) {
                    changeClaimText("#wizard #openCtmTransformation", parseInt($("#transformationsSizeValue").val()));
                }
            }
            markClaimNoButtonActive(".transformation", "#noTransformation");
            callGetFastTrackFails();
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            $("#claimTableContainer").html(errorThrown);
        }
    });
}

function addTransformationPost(closeDiv, tabPaneObject, claimDetails, claimTable) {
    $("#transformationForm").find(":checkbox").val("true");

    $.ajax({
        url: "addTransformation.htm",
        data: $("#transformationForm").serialize() +
        "&detailsView=" + claimDetails + "&claimTable=" + claimTable,
        type: "POST",
        cache: false,
        success: function(html){
            if(html.indexOf("error") != -1) {
                //If post succeded with validation errors:
                $("#tabTransformation").html(html);
                $(".claimSections #transformationForm #manualDetails").show();
            } else {
                //Reload the common table
                $("#claimTableContainer").html(html);
                if(closeDiv) {
                    clearTabsDevelopers(tabPaneObject);
                    $('.cancelTransformationButton').trigger('click');
                    $('.cancelButton').trigger('click');
                } else {
                    $('#openTransformation').trigger('click');
                    $('#openTransformation').trigger('mouseup');
                }
                callGetFastTrackFails();
            }
            // change text of buttons to reflect change in number of transformations
            if(parseInt($("#transformationsSizeValue").val() < parseInt($("#maxTransformations").val()))){
                changeClaimText("#wizard #openTransformation", parseInt($("#transformationsSizeValue").val()));

                if ($("#wizard #openCtmTransformation").length >0 ) {
                    changeClaimText("#wizard #openCtmTransformation", parseInt($("#transformationsSizeValue").val()));
                }
            }
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            $("#tabTransformation").html(errorThrown);
            $("#tabTransformation").show();
        }
    });
}

$('.claimSections #transformationForm #createManualDetails').live("click", function ()
{
    $(".claimSections #transformationForm #manualDetails").show();
});

$('#noTransformation').live("click", function ()
{
    $(".claimSections #transformationForm .cancelTransformationButton").trigger('click');
});