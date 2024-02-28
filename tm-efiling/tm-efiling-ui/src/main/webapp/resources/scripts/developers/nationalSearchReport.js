$("#nationalSearchReport_Yes").live("click", function ()
{
	changeNationalSearch(true);
});

$("#nationalSearchReport_No").live("click", function ()
{
	changeNationalSearch(false);
});

function nationalSearchReportInitialization()
{
    if ($("[name='mainForm.nationalSearchReport']").size() > 0)
    {
        if($("[name='mainForm.nationalSearchReport']").val() == "true")
        {
            $("#nationalSearchReport_Yes").addClass("active");
        }
        if($("[name='mainForm.nationalSearchReport']").val() == "false")
        {
            $("#nationalSearchReport_No").addClass("active");
        }
    }
}

function changeNationalSearch(value) {
	//Disable the page to not allow the user to click anything else while the process ends
    $("[name='mainForm.nationalSearchReport']").val(value);
	$.ajax({
		url : "changeNationalSearch.htm",
		type : "GET",
			cache : false,
			data:"value=" + value,
			success : function(html) {
				if (value){
					showMessageModal(html);
				}
                updateFeesInformation();
			}
	});
}