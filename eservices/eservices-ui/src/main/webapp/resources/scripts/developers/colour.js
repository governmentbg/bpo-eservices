
$(document).ready(function($) {
	if($("#colourCardList .format-table").html() != null) {
		$("#addColourCheck").attr("checked",true);
		$('#colourSection_internal').show();
	} 
});

var Colour =
{
};

Colour.nav =
{
    addColour:"addColour.htm",
    saveColour:"saveColour.htm",
    removeColour:"removeColour.htm",
    removeAllColours:"removeAllColours.htm"
};


$("#addColourButton").live("click", function ()
{
	var format = $("#format option:selected").attr("id");
	var value = $("#value").val();
	var parameters = "format=" + format + "&value=" + value;
    manageColourPost(this, Colour.nav.addColour, parameters, true);
});


$("#cancelColourButton").live("click", function ()
{
	$("#format").val("");
	$("#value").val("");
	$(".editformat").val("");
	$(".editvalue").val("");
});

$("#saveColourButton").live("click", function ()
{
	var format = $("#format option:selected").attr("id");
	var value = $("#value").val();
	var formatOld = $(".editformat").val().toUpperCase();
	var valueOld = $(".editvalue").val();
	var parameters = "format=" + format + "&value=" + value + "&formatOld=" + formatOld + "&valueOld=" + valueOld;
    manageColourPost(this, Colour.nav.saveColour, parameters, true);
});


$("#removeColourButton").live("click", function ()
{
	var format = $(this).closest('tr').find('.format').html();
	var value = $(this).closest('tr').find('.value').html();
	var parameters = "format=" + format.toUpperCase() + "&value=" + value;
    manageColourPost(this, Colour.nav.removeColour, parameters, true);
});


$("#editColourButton").live("click", function ()
{
	var format = $(this).closest('tr').find('.format').html();
	var value = $(this).closest('tr').find('.value').html();
    $("#colourSection_internal .flMessageError").remove();
	
	$("select#format option").each(function() { 
		this.selected = (this.text == format); 
	});
	$("#value").val(value);
	$(".editformat").val(format);
	$(".editvalue").val(value);
	
	$("#addColourButton").hide();
	$("#saveColourButton").show();
});


$("#addColourCheck").live("click", function ()
{
    if(!$('#addColourCheck').is(':checked')){
		$("#addColourCheck").attr("checked",true);
    	var removeFunc = partial(removeAllColours, this, Colour.nav.removeAllColours, "", true);
		showConfirmModal($("#colour_removeAllColours").html(), removeFunc);
    } else {
		$('#colourSection_internal').show();
	}
    
});


function removeAllColours(sender, address, parameters, ignoreMatches) {
    $('#colourSection_internal').hide();
	$("#addColourCheck").attr("checked",false);
	manageColourPost(sender, address, parameters, ignoreMatches);
}

function manageColourPost(sender, address, parameters, ignoreMatches)
{
    var dataToSend = parameters;
    if (ignoreMatches)
    {
        dataToSend =  dataToSend + "&sectionId=" + $("#sectionId").val() + "&ignoreMatches=true";
    }
    $.ajax(
        {
            url:address,
            type:"POST",
            data:dataToSend,
            success:function (html)
            {
                $("#colourSection_internal").html(html);
                $("#format").val("");
                $("#value").val("");
                $(".editvalue").val("");
				$(".editformat").val("");
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#colourSection", true);
            }
        });
}