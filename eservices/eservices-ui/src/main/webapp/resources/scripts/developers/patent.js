var PT = {};

$("#ptTrigger").live("click", function(){
    toggleImportPT();
});

$(".addPT").live("click", function (event, ignoreMatches) {
    ignoreMatches = "true";
    //}
    PT.ajax.addPTPost(this, PT.nav.addPT, ignoreMatches);
    PT.global.isEdit = false;
    // updateFeesInformation();  // causes problem with saving the patent-like object in the application
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

$('#importPTButton').live("click", function() {
    var importId = getPTIdToImport();
    if (importId) {
        PT.ajax.showPTDetails(importId, false);
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

function getPTIdToImport() {
    // Checks if the user has selected a value using the autocomplete feature
    var importId = $("#auto_patentId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#patentId").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#emptyImport_patent_warning").html());
        return;
    }
    return importId;
}

$("#patentId").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatent_viewMessage", "#previousPatent_viewUrl");
});

$("#patentId").live("blur", function ()
{
    closePatentAutocomplete(this);
});

function patentAutocomplete(object, searchUrlEnabled, viewMessage, searchUrl)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).autocomplete( "option", "delay", 500 );
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        var wrappedUrl = "";
        if (searchUrlEnabled == true)
        {
            var viewMessageVar = $(viewMessage).html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getPatentNavigationUrl([
                    ["patentId", item.ApplicationNumber]
                ], searchUrl) + "'>" +
                "<span class='fourth-col'>" + viewMessageVar + "</span></span>";
        }
        if (item.NoResultsFound) {
            return $("<li><a onclick=\"$('.ui-autocomplete').hide();\"><span class='selectable-col'>" + $("#noResultsFoundInfo").text() + "</span></a></li>")
                .data("item.autocomplete", item)
                .appendTo(ul);
        } else {
            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.Name + "</span>" +
                    "<span class='second-col'>" + item.ApplicationNumber + "</span><span class='third-col'><span style='padding-left: 10px'>" + item.RegistrationNumber+"</span></span></span></span></a>"+wrappedUrl)
                .appendTo(ul);
        }
    }
    $(object).data("autocomplete")._resizeMenu = function() {
        this.menu.element.outerWidth( 660 );
    }
    $(object).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
            $(object).val(ui.item.ApplicationNumber);
            return false;
        }, 100);
    });
}

function closePatentAutocomplete(object)
{
    if (typeof $(object).data("autocomplete") !== 'undefined') {
        $(object).data("autocomplete")._renderItem = null;
    }
}

function getPatentNavigationUrl(keyValuePairArray, searchUrl)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $(searchUrl).val());
}

PT.global = {
    isEdit : false
};

PT.nav =
    {
        addPT:"addPTDetails.htm",
        getPT:"getPTDetailsForEdit.htm",
        removePT:"removePTDetails.htm",
        importPT:"importPatent.htm",
        copyOwners: "refreshCopiedPTOwnersToOther.htm",
        copyRepresentatives: "importRepresentativePTImport.htm"
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
                            // otherwise, just refresh applicant cards
                            refreshPTCards(html);
                            var path = window.location.pathname;
                            if((path.indexOf("pt-change") != -1 || path.indexOf("um-change") != -1 || path.indexOf("ep-change") != -1 || path.indexOf("sv-change") != -1 || path.indexOf("spc-change") != -1 ||
                                path.indexOf("pt-bankruptcy") != -1|| path.indexOf("um-bankruptcy") != -1||  path.indexOf("ep-bankruptcy") != -1 || path.indexOf("sv-bankruptcy") != -1 || path.indexOf("spc-bankruptcy") != -1 ||
                                path.indexOf("pt-surrender") != -1 || path.indexOf("um-surrender") != -1 || path.indexOf("ep-surrender") != -1 ||
                                path.indexOf("pt-appeal") != -1 || path.indexOf("spc-appeal") != -1 || path.indexOf("um-appeal") != -1 || path.indexOf("um-renewal") != -1 || path.indexOf("spc-extendterm") != -1 ||
                                path.indexOf("pt-docchanges") != -1|| path.indexOf("um-docchanges") != -1||
                                path.indexOf("pt-withdrawal") != -1 || path.indexOf("um-withdrawal") != -1 || path.indexOf("ep-withdrawal") != -1) && (isEdit == false)){
                                //TODO add other form cases
                                PT.ajax.refreshCopiedPTOwnersToOther(path);
                            }

                            if(path.indexOf("-changerep") != -1 && (isEdit == false)){
                                PT.ajax.refreshCopiedPTRepresentatives();
                            }

                        }

                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.", "#ptSection", true);
                    }
                });
        },

        refreshCopiedPTOwnersToOther: function(path) {
            $.ajax(
                {
                    url:PT.nav.copyOwners,
                    type:"GET",
                    success:function (html)
                    {
                        if( path.indexOf("pt-change")!= -1 || path.indexOf("um-change")!= -1 || path.indexOf("ep-change")!= -1 || path.indexOf("sv-change")!= -1 || path.indexOf("spc-change")!= -1){
                            //TODO add other cases for other forms
                            refreshHolderCards(html);
                        } else if(path.indexOf("pt-bankruptcy")!= -1 || path.indexOf("um-bankruptcy")!= -1 || path.indexOf("ep-bankruptcy")!= -1 || path.indexOf("sv-bankruptcy")!= -1 || path.indexOf("spc-bankruptcy")!= -1 ||
                            path.indexOf("pt-surrender")!= -1 || path.indexOf("um-surrender")!= -1 || path.indexOf("ep-surrender")!= -1 ||
                            path.indexOf("pt-appeal")!= -1 || path.indexOf("spc-appeal")!= -1 || path.indexOf("um-appeal")!= -1 || path.indexOf("um-renewal")!= -1 || path.indexOf("spc-extendterm")!= -1 ||
                            path.indexOf("pt-docchanges")!= -1 || path.indexOf("um-docchanges")!= -1 ||
                            path.indexOf("pt-withdrawal")!= -1 || path.indexOf("um-withdrawal")!= -1 || path.indexOf("ep-withdrawal")!= -1){
                            refreshApplicantCards(html);
                        }
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.", "#applicantSection", true);
                    }
                });
        },
        refreshCopiedPTRepresentatives: function() {
            $.ajax(
                {
                    url:PT.nav.copyRepresentatives,
                    type:"GET",
                    success:function (html)
                    {
                        refreshRepresentativeCards(html);
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.", "#representativeSection", true);
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
        showPTDetails: function(importId, isUnpublished) {
            if (importId) {
                data = {
                    importId: importId,
                    unpublished: isUnpublished
                };
            }
            showLoadingScreen();
            $.ajax({
                url : PT.nav.importPT,
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