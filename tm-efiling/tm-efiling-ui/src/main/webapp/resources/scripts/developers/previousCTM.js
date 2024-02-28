$('.importPreviousCTM').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importId = $("#previousCTM_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#IDpreviousCTM").val();
        if(!importId.startsWith($("#previousCtmConfig_officeCode").val())){
            //create ST13 code from what has been entered
            importId = generateST13ID(importId, $("#previousCtmConfig_officeCode").val());
        }
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }

    var onConfirmYes = partial(importPreviousCTM, 'ImportCTM', importId, $("#previousCtmConfig_officeCode").val(), $("#partialCheck").is(':checked'));
    showConfirmModal($("#previousCTM_importConfirmation").html(), onConfirmYes);
});

function generateST13ID(idappli, officeCode) {
    idappli = idappli.trim();
    var st13id= officeCode+"50";
    if(officeCode == "BG"){
        var rest = "";
        var year = idappli.substring(0, 4);
        rest = idappli.substr(4);

        st13id += year;

        var rest_new = "";
        for (var i=0; i<rest.length; i++) {
            var ch = rest.substring(i, i+1);
            if (ch == "/"||ch == "*") rest_new+="0";
            else rest_new += ch;
        }
        rest=rest_new;

        var zeronum = 9 - rest.length;
        for (var i=0; i<zeronum; i++) {
            st13id += "0";
        }

        while (rest.length > 9) rest = rest.substr(1);
        console.log("THE ID:"+st13id+rest);
        return st13id+rest;
    } else {
        st13id += "0000";
        var zeronum = 9 - idappli.length;
        for (var i=0; i<zeronum; i++) {
            st13id += "0";
        }
        st13id += idappli;
        return st13id;
    }
}

function importPreviousCTM(method, value, officeCode, askExtraImport)
{
    //method can be one of the valid options for _eventId in the definition flow file
//	ImportCTM
//	ImportCTMPriority
//	ImportCTMSeniority
//	ImportCTMTransformation
    var askExtraImportB = askExtraImport;
    $("#hiddenElement").val(method);

    //Hidden input to store the id to search
    var hiddenfieldMethod = document.createElement("input");
    $(hiddenfieldMethod).attr("type", "hidden");
    $(hiddenfieldMethod).attr("name", "importCTMId");
    $(hiddenfieldMethod).attr("id", "importCTMId");
    $(hiddenfieldMethod).attr("value", value);

    //Hidden input to store the office-country code to search
    var hiddenfieldOffice = document.createElement("input");
    $(hiddenfieldOffice).attr("type", "hidden");
    $(hiddenfieldOffice).attr("name", "officeCode");
    $(hiddenfieldOffice).attr("id", "officeCode");
    $(hiddenfieldOffice).attr("value", officeCode);

    //Hidden input to store whether the import has to import extra information
    var hiddenfieldExtraImport = document.createElement("input");
    $(hiddenfieldExtraImport).attr("type", "hidden");
    $(hiddenfieldExtraImport).attr("name", "extraImport");
    $(hiddenfieldExtraImport).attr("id", "extraImport");

    $("#hiddenForm").append(hiddenfieldMethod);
    $("#hiddenForm").append(hiddenfieldOffice);

    if (method != 'ImportCTM')
    {
        if (askExtraImport == 'true')
        {
            var confirmMessage = "";
            if(method == "ImportCTMPriority") {
                confirmMessage = $("#importPriority_extraDetails").html();
            } else if(method == "ImportCTMSeniority") {
                confirmMessage = $("#importSeniority_extraDetails").html();
            } else if(method == "ImportCTMTransformation") {
                confirmMessage = $("#importTransformation_extraDetails").html();
            }

            showConfirmModalYesNo(confirmMessage,
                function ()
                {
                    $(hiddenfieldExtraImport).attr("value", true);
                    $("#hiddenForm").append(hiddenfieldExtraImport);
                    //Disable the page to not allow the user to click anything else while the process ends
                    showLoadingScreen();
                    $("#hiddenForm").submit();
                },
                function ()
                {
                    $(hiddenfieldExtraImport).attr("value", false);
                    $("#hiddenForm").append(hiddenfieldExtraImport);
                    //Disable the page to not allow the user to click anything else while the process ends
                    showLoadingScreen();
                    $("#hiddenForm").submit();
                });
        } else
        {
            $(hiddenfieldExtraImport).attr("value", false);
            $("#hiddenForm").append(hiddenfieldExtraImport);
            //Disable the page to not allow the user to click anything else while the process ends
            showLoadingScreen();
            $("#hiddenForm").submit();
        }
    } else
    {
        $(hiddenfieldExtraImport).attr("value", askExtraImport);
        $("#hiddenForm").append(hiddenfieldExtraImport);
        //Disable the page to not allow the user to click anything else while the process ends
        showLoadingScreen();
        $("#hiddenForm").submit();
    }
}

$("#IDpreviousCTM").live("focus", function ()
{
    initAutocomplete(this);
    tradeMarkAutocomplete(this, "#previousCtmConfig_searchUrlEnabled", "#previousCtmConfig_viewMessage",
        "#previousCtmConfig_searchUrl", "#previousCtmConfig_officeCode", "#previousCTM_importId");
});

function tradeMarkAutocomplete(object, searchUrlEnabled, viewMessage, searchUrl, officeCode, importId,
                               requiresCountrySelected, countrySelector)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        var searchUrlEnabledVar = $(searchUrlEnabled).val();
        var officeCodeVar = $(officeCode).val();
        if(requiresCountrySelected)
        {
            if($(countrySelector).val() == "")
            {
                searchUrlEnabledVar = false;
            }
            else
            {
                officeCodeVar = $(countrySelector).val();
            }
        }

        var wrappedUrl = "";
        if (searchUrlEnabledVar.toUpperCase() == "TRUE")
        {
            var viewMessageVar = $(viewMessage).html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getTradeMarkAutocompleteNavigationUrl([
                    ["trademarkId", item.ST13],
                    ["officeCode", officeCodeVar]
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
                .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col-smaller'>" + item.TradeMarkName + "</span>" +
                    "<span class='second-col'>" + item.TradeMarkIdentifier + "</span><span class='third-col-bigger'>" + item.RegistrationNumber + "</span></span></span></a>" + wrappedUrl)
                .appendTo(ul);
        }
    }
    $(object).data("autocomplete")._resizeMenu = function() {
        this.menu.element.outerWidth( 620 );
    }
    $(object).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
            $(object).val(ui.item.TradeMarkIdentifier);
            $(importId).val(ui.item.ST13);
            return false;
        }, 100);
    });
}

function getTradeMarkAutocompleteNavigationUrl(keyValuePairArray, searchUrl)
{
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $(searchUrl).val());
}

$("#searchForTrademarkBtn").live("click", function()
{
    window.open($(this).attr("data-ask"));
})