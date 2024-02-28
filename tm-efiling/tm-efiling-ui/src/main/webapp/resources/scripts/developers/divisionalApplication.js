$('#partialCheck').live("change", function() {
	toggleFields($(this).is(":checked"));
});

function toggleFields(show)
{
    if(show)
    {
        $('#divisionalApplicationDivSection .fields').show();
    }
    else
    {
        showConfirmModal($("#divisional_remove").html(), clearDivisional, function(){
            $('#partialCheck').attr("checked", "checked");
            $('#divisionalApplicationDivSection .fields').show();
        });
    }
}

// Import
$('.importDivisionalCTMFake').live("click", function ()
{
    // Checks if the user has selected a value using the autocomplete feature
    var importId = $("#divisionalCTM_importId").val();
    if (importId == null || importId.trim() == "")
    {
        // if not, make the call to the import service using the value in the IDdivisionalCTM text box
        importId = $("#IDdivisionalCTM").val();
    }
    if (importId.trim() == "")
    {
        showWarningModal($("#previousCtm_missingInputText").html());
        return;
    }

    importPreviousCTM('ImportCTMDivisional', importId, $("#divisionalCtmConfig_officeCode").val(),
        $("#partialCheck").is(':checked'));

});

$("#IDdivisionalCTM").live("focus", function ()
{
    initAutocomplete(this);
    tradeMarkAutocomplete(this, "#divisionalCtmConfig_searchUrlEnabled", "#divisionalCtmConfig_viewMessage",
        "#divisionalCtmConfig_searchUrl", "#divisionalCtmConfig_officeCode");
});


$('.importDivisionalCTM').live("click", function() {
    importDivisionalCTM($('#IDdivisionalCTM').val());
});


function importDivisionalCTM(id) {
    if (id) {
        $.ajax({
            url: "importDivisionalApplication.htm",
            data: "id=" + id,
            type: "POST",
            cache: false,
            success: function(html){
                if(html.indexOf("error") != -1) {
                    //If post succeded with validation errors
                    showWarningModal($(html).find('body').html());
                } else if(html.indexOf("importError") != -1) {
                    //If post succeded without results
                    showWarningModal($(html).find(".importError").html());
                } else {
                    //Show result
                    $('#divisionalApplicationDivSection').html($('<div>'+html+'</div>')
                        .find('#divisionalApplicationDivSection').html());
                }
            },
            error: function (xmlHttpRequest, textStatus, errorThrown) {
                showWarningModal($('#divisionalCTM_importError').html());
            },
            beforeSend: showLoadingScreen,
            complete: hideLoadingScreen
        });
    } else {
        showWarningModal($('#divisionalCTM_importBoxEmpty').html());
    }
}

function clearDivisional() {
    $.ajax({
        url: "clearDivisionalApplication.htm",
        type: "POST",
        cache: false,
        success: function(html){

            //Show result
            $('#divisionalApplicationDivSection').html($('<div>'+html+'</div>')
                .find('#divisionalApplicationDivSection').html());
        },
        error: function (xmlHttpRequest, textStatus, errorThrown) {
            genericHandleError("An error occured while processing your request. Please try again later.",
                "#divisionalApplicationDivSection", true);
        },
    });
}