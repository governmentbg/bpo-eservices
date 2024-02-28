$('#partialCheck').live("change", function() {
	toggleFields($(this).is(":checked"));
});

function clearInputs() {
    $("#divisionalApplication .fields input").each(function()
    {
        $(this).val("");
    })
}

function toggleFields(show)
{
    if(show)
    {
        $('#divisionalApplication .fields').removeClass('hidden');
    }
    else
    {
    	$('#divisionalApplication .fields').addClass('hidden');
        clearInputs();
    }
}

