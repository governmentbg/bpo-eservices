$('.importDivisional').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importCall = $("#divisionalApplication_importCall").val();
    var importId = $("#divisionalApplication_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#importDivisional").val();
    }
    callImportPreviousPatent(importCall, importId);
});

$('.importTransformation').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importCall = $("#transformation_importCall").val();
    var importId = $("#transformation_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#importTransformation").val();
    }
    callImportPreviousPatent(importCall, importId);
});

$('.importParallelApplication').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importCall = $("#parallelApplication_importCall").val();
    var importId = $("#parallelApplication_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#importParallelApplication").val();
    }
    callImportPreviousPatent(importCall, importId);
});

$('.importTemplatePatent').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importIdType = $('#patentImportForm input[name=identifierKind]:checked').val();
    var importId = $("#templatePatent_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $("#importTemplatePatent").val();
    }

    callImportPreviousTemplatePatent("importTemplatePatent", importId, importIdType, "true");
});


function callImportPreviousPatent(importCall, importId){
    if (importId.trim() == "")
    {
        showWarningModal($("#importPatent_missingInputText").html());
        return;
    }

    var onConfirmYes = partial(importPreviousPatent, importCall, importId);
    showConfirmModal($("#importPatent_importConfirmation").html(), onConfirmYes);
}

function callImportPreviousTemplatePatent(importCall, importId, importIdType, forceReimport){
    if (importId.trim() == "")
    {
        showWarningModal($("#importPatent_missingInputText").html());
        return;
    }

    var onConfirmYes = partial(importPreviousPatent, importCall, importId, importIdType, forceReimport);
    showConfirmModal($("#importTemplatePatent_importConfirmation").html(), onConfirmYes);
}

function importPreviousPatent(method, value, valueType, forceReimport)
{
    //method can be one of the valid options for _eventId in the definition flow file
    $("#hiddenElement").val(method);

    //Hidden input to store the id to search

    createHidden("importId", value);

    if(valueType != undefined && valueType != null){
        createHidden("importIdType", valueType);
    }

    if(forceReimport != undefined && forceReimport != null){
        createHidden("forceReimport", forceReimport);
    }

    showLoadingScreen();
    $("#hiddenForm").submit();
}


$("#importDivisional").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatentDivisional_viewMessage", "#previousPatentDivisional_viewUrl", false);
});

$("#importParallelApplication").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatentParallel_viewMessage", "#previousPatentParallel_viewUrl", false);
});

$("#importTransformation").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatentTransformation_viewMessage", "#previousPatentTransformation_viewUrl", false);
});

$("#importTemplatePatent").live("focus", function ()
{
    initAutocomplete(this);
    var importIdType = $('#patentImportForm input[name=identifierKind]:checked').val();
    patentAutocomplete(this, true, "#previousPatent_viewMessage", "#previousPatent_viewUrl", importIdType === "registration");
});

$("#patentIdNational").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatent_viewMessage", "#previousPatentNational_viewUrl", false);
});

$("#patentIdEPO").live("focus", function ()
{
    initAutocomplete(this);
    patentAutocomplete(this, true, "#previousPatent_viewMessage", "#previousPatentEPO_viewUrl", false);
});

$(".importPatentInput").live("blur", function ()
{
    closePatentAutocomplete(this);
});

function patentAutocomplete(object, searchUrlEnabled, viewMessage, searchUrl, fillInRegNumber)
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
        if (searchUrlEnabled == true && (item.EBD == undefined) )
        {
            var viewMessageVar = $(viewMessage).html();
            wrappedUrl = "<span class='navigation-col' data-url='" +
                getPatentNavigationUrl([
                    ["patentId", item.ApplicationNumber]
                ], searchUrl) + "'>" +
                "<span class='fourth-col'>" + viewMessageVar + "</span></span>";
        }

        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.Name + "</span>" +
                "<span class='second-col'>" + item.ApplicationNumber + "</span><span class='third-col'><span style='padding-left: 10px'>" + item.RegistrationNumber+"</span></span></span></span></a>"+wrappedUrl)
            .appendTo(ul);

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
            if(!fillInRegNumber) {
                $(object).val(ui.item.ApplicationNumber);
            } else {
                $(object).val(ui.item.RegistrationNumber);
            }
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

function createHidden(id, value){
    var hiddenfieldMethod = document.createElement("input");
    $(hiddenfieldMethod).attr("type", "hidden");
    $(hiddenfieldMethod).attr("name", id);
    $(hiddenfieldMethod).attr("id", id);
    $(hiddenfieldMethod).attr("value", value);

    $("#hiddenForm").append(hiddenfieldMethod);
}