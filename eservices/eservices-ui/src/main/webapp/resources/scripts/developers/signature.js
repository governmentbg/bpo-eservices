$(document).ready(function()
{
    $(".signature #date").each(function()
    {
        $(this).val(new Date().format($("#dateFormat").html()))
    });

    if($("#hasSecondSignature").val() == "true")
    {
        showSecondSignature();
    }
    else
    {
        hideSecondSignature();
    }
})

function showSecondSignature()
{
    $("#addSignatureButton").addClass("active");
    $("#addSecondSign").val("on");
    $("#secondSignatureDetails").show();
}

function hideSecondSignature()
{
    $("#addSignatureButton").removeClass("active");
    $("#addSecondSign").val("off");
    $("#secondSignatureDetails").hide();
}

$("#addSignatureButton").live("click", function()
{
    if($(this).hasClass("active"))
    {
        showSecondSignature();
    }
    else
    {
        hideSecondSignature();
    }
})