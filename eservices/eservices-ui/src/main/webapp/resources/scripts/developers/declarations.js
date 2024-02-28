$(document).on("change", "#declaration_smallCompany", function () {

    $.ajax(
        {
            url: "changeSmallCompanyDeclaration.htm",
            method: "POST",
            success: function(){
                getFeesInformation();
                if($('#smallCompany_div').css('display') == 'none'){
                    $('#smallCompany_div').css('display', 'block');
                } else {
                    $('#smallCompany_div').css('display', 'none');
                }
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#declaration_smallCompany", true);
            }
        }
    )
});

$(document).on("change", "#licenceAvailability", function () {
    $.ajax(
        {
            url: "changeLicenceAvailability.htm",
            method: "POST",
            success: function(){
                getFeesInformation();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#licenceAvailability", true);
            }
        }
    )
});