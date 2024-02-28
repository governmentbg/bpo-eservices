
Patent={};
Patent.nav = {
    patentClaimsCount: "changeClaimsCount.htm",
    patentIndependentClaimsCount: "changeIndependentClaimsCount.htm",
    patentPagesCount: "changePagesCount.htm"
}

$(document).on("change", ".ajaxSubmitPatentField", function () {
   var id = $(this).attr("id");
   var val = $(this).val();
    if(val != "") {
        $.ajax(
            {
                url: Patent.nav[id],
                method: "POST",
                data: "count=" + val,
                success: function () {
                    getFeesInformation();
                },
                error: function (error) {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#" + id, true);
                }
            }
        )
    }
});

//FOR IMPORT PT/EP IN SPC-EFILING

var PT = {};

$("#ptType").live("change", function() {
   if($(this).val() == "national"){
       $("#patentNationalImport").show();
       $("#patentEPOImport").hide();
   } else if($(this).val() == "epo"){
       $("#patentNationalImport").hide();
       $("#patentEPOImport").show();
   } else {
       $("#patentNationalImport").hide();
       $("#patentEPOImport").hide();
   }
});

$("#ptTrigger").live("click", function(){
    toggleImportPT();
    $("#ptType").val("");
    $("#ptType").trigger("change");
});

$(".addPT").live("click", function (event, ignoreMatches) {
    ignoreMatches = "true";
    PT.ajax.addPTPost(this, PT.nav.addPT, ignoreMatches);
    PT.global.isEdit = false;
});

$(".cancelButton.patent").live("click", function () {
    $("#ptSection").html("");
    togglePTAdd();
    scrollToPTsTop();
    PT.global.isEdit = false;
});

$(".editPTButton").live("click", function () {
    PT.global.isEdit = true;
    PT.ajax.getPTForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$(".deletePTButton").live("click", function () {
    var ptId = $(this).attr("data-val");
    var onConfirmYes = partial(PT.ajax.removePT, ptId);
    showConfirmModal($("#delete_patent_confirmation").html(), onConfirmYes);
    PT.global.isEdit = false;
});

$('#importPTNationalButton').live("click", function() {
    var importId = getPTIdToImport("#auto_patentId_national", "#patentIdNational");
    if (importId) {
        PT.ajax.showPTDetails(importId, false, PT.nav.importNationalPatent);
    }
});

$('#importPTEPOButton').live("click", function() {
    var importId = getPTIdToImport("#auto_patentId_epo", "#patentIdEPO");
    if (importId) {
        PT.ajax.showPTDetails(importId, false, PT.nav.importEPOPatent);
    }
});

$(".unpublishedImportBtn.PT").live("click", function (){
    var importId = $(this).attr("data-id");
    if (importId) {
        $("#unpublishedAppsModal").modal("hide");
        PT.ajax.showPTDetails(importId, true);
    }
});

$("#searchForPatentBtn").live("click", function() {
    window.open($(this).attr("data-ask"));
});

function getPTIdToImport(hiddenSelector, inputSelector) {
    // Checks if the user has selected a value using the autocomplete feature
    var importId = $(hiddenSelector).val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $(inputSelector).val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#emptyImport_patent_warning").html());
        return;
    }
    return importId;
}

PT.global = {
    isEdit : false
};

PT.nav =
    {
        addPT:"addPTDetails.htm",
        getPT:"getPTDetailsForEdit.htm",
        removePT:"removePTDetails.htm",
        importNationalPatent:"importNationalPatent.htm",
        importEPOPatent:"importEPOPatent.htm",
    };

PT.ajax =
    {
        /**
         * Display the form with the details of the selected patent to be edited
         * */
        displayChoosePT: function()
        {
            removePTErrors();
            $.ajax({
                url:PT.nav.addPT,
                data:"",
                success:function (html)
                {
                    $("#ptSection").html(html);
                    $("#tabPT .addPT").each(function(){
                        $(this).html($("#addButtonText").html());
                    });
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#ptSection", true);
                }
            });
        },

        /**
         * Posts trademark details to the server
         * @param sender
         * @param address
         * @param ignoreMatches
         */
        addPTPost: function(sender, address, ignoreMatches)
        {
            var isEdit = PT.global.isEdit;
            var dataToSend = $("#ptSection form").serialize();
            if (ignoreMatches)
            {
                dataToSend = dataToSend + "&ignoreMatches=true";
            }
            removePTErrors();
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
                            displayPTValidationErrors(html);
                            return;
                        } else {
                            refreshPTCards(html);
                        }

                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.", "#ptSection", true);
                    }
                });
        },

        /**
         * Gets filled patent details form to edit
         * @param applicantId
         */
        getPTForm: function(ptId, rowNumber)
        {
            removePTErrors();
            $.ajax({
                url:PT.nav.getPT,
                type:"GET",
                data:"id=" + ptId,
                success:function (html)
                {
                    $("#ptSection").html(html);

                    //$("#applicantCurrentNumber").html(rowNumber);
                    // set "save" text for editing
                    $("#tabPT .addPT").each(function()
                    {
                        $(this).html($("#personEditText").html());
                    });
                    showPTTab("edit");

                    //postUpdateApplicantSection();
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#patentsCardList", true);
                }
            });
        },


        /**
         * Removes patent with id embedded in data-val attribute then refreshes applicant cards.
         * @param applicantId
         */
        removePT: function(ptId)
        {
            removePTErrors();
            $.ajax({
                    url:PT.nav.removePT,
                    data:"id=" + ptId,
                    type:"GET",
                    success:function (html)
                    {
                        refreshPTCards(html);
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.",
                            "#patentsCardList", true);
                    }
                }
            );
        },


        /**
         * Recovers the information relate to a patent
         * @param importId
         */
        showPTDetails: function(importId, isUnpublished, nav) {
            if (importId) {
                data = {
                    importId: importId,
                };
            }
            showLoadingScreen();
            $.ajax({
                url : nav,
                data : data,
                type : "GET",
                cache : false,
                success : function(html)
                {
                    hideLoadingScreen();
                    var $html = $(html).html();

                    if ($(html).hasClass('importPatentWarning')) {
                        showWarningModal($html);
                    } else {
                        if ($html) {
                            $("#ptSection").html(html);
                        }
                        $("#tabPT .addPT").each(function(){
                            $(this).html($("#addButtonText").html());
                        });
                        showPTTab("edit");
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

function scrollToPTsTop() {
    scrollToContainer("#patentsCardList");
}

function togglePTAdd() {
    if($("#tabPT").is(":hidden")) {
        showPTTab();
    } else {
        $("#ptTrigger").removeClass("active");
        hidePTTab();
    }
}

function showPTTab(forEdit) {
    $("#importPT").hide();
    $("#tabPT").show();
    if(forEdit != "edit"){
        PT.ajax.displayChoosePT();
    }
}

function hidePTTab() {
    $("#tabPT").hide();
}

function toggleImportPT() {
    if ($("#importPT").is(":visible") || $("#tabPT").is(":visible")) {
        $("#ptTrigger").removeClass("active");
        hideImportPT();
    } else if($("#importPT").is(":hidden")) {
        $("#ptTrigger").addClass("active");
        showImportPT();
    }
}

function showImportPT() {
    $("#importPT").show();
    $("#ptTrigger").addClass("active");
}

function hideImportPT() {
    $("#tabPT").hide();
    $("#importPT").hide();
    $("#ptTrigger").removeClass("active");
}

function refreshPTCards(html) {
    $("#patentsCardList").html(html);
    $("#ptSection").html("");
    scrollToPTsTop();
    hideImportPT();
    updateFeesInformation();
    checkMaxPTs();
}

function displayPTValidationErrors(html) {
    $("#ptSection").html(html);
}

function checkMaxPTs(){
    var max =$('#maxPTs').val();
    var rowCount = $('#patentsCardList tr').length-1;

    if(rowCount==max){
        $("#ptTrigger").hide();
        $(".unpublishedAppSelectButton").hide();
    }else{
        $("#ptTrigger").show();
        $(".unpublishedAppSelectButton").show();
    }
}

function removePTErrors() {
    removePreviousErrors("#patentsCardList");
    removePreviousErrors("#ptSection");
}