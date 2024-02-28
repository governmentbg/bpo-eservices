var Sign = {};
$("#signatureTrigger").live("click", function(){
    toggleSignatureAdd();
});

$(".addSignature").live("click", function (event, ignoreMatches)
{
    $(".addSignature").attr("disabled",true);
    ignoreMatches = "true";
    Sign.ajax.addSignaturePost(this, Sign.nav.addSign, ignoreMatches);
});

$(".cancelButton.signature").live("click", function (){
    $("#signatureSection").html("");
    toggleSignatureAdd();
    scrollToSignatureTop();
});

$("#deleteSignatureButton").live("click", function (){
	$(".cancelButton.signature").click();
	var signId = $(this).attr("data-val");
    var onConfirmYes = partial(Sign.ajax.removeSignature, signId);
    showConfirmModal($("#delete_confirmation_signature").html(), onConfirmYes);
});

$("#importSignature").live("click", function (event, ignoreMatches) {
    Sign.ajax.importSignatureDetails(this, Sign.nav.importSign, ignoreMatches);
});


$("#editSignatureButton").live("click", function (event, ignoreMatches){
	Sign.ajax.getSignatureForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

Sign.nav =
{
		addSign:"addSignature.htm",
	    getSign:"getSignatureForEdit.htm",
	    removeSign:"removeSignature.htm",
	    importSign:"importSignature.htm"
};

Sign.ajax =
{
	/**
	 * Display the form with the details of the selected signature to be edited
	 * */
    displayChooseSignature: function()
    {
        removeSignatureErrors();
        $.ajax({
            url:Sign.nav.addSign,
            data:"",
            async:false,
            success:function (html)
            {
                updateSignatureSection(html);
                               
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#signatureSection", true);
            }
        });
        
    },
    
    /**
     * Posts signature details to the server
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    addSignaturePost: function(sender,address,ignoreMatches)
    {
        var dataToSend = $("#signatureSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        removeSignatureErrors();
        $.ajax(
        {
        	url:address,
            type:"POST",
            data:dataToSend,
            success:function (html)
            {            	
            	if ($("input#formReturned", $(html)).val() == "true")
                {
                	// then display the errors
                    displaySignatureValidationErrors(html);
                    capacityEval($("#personCapacity").val());
                    return;
               	} else {
            	refreshSignatureCards(html);
               	}
                $(".cancelButton.signature").click();

            },
            error:function (error)
            {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#signatureSection", true);
            },
            complete: function() {
                $(".addSignature").attr("disabled",false);
            }
        });
    },

    getSignatureForm: function(signId, rowNumber)
    {
    	removeSignatureErrors();
        $.ajax({
            url:Sign.nav.getSign,
            type:"GET",
            async:false,
            data:"id=" + signId,
            success:function (html)
            {
            	updateSignatureSection(html, true);

                //$("#applicantCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#signatureSection .addSign").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                showSignatureTab("edit");

                capacityEval($("#personCapacity").val());

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantCardList", true);
            }
        });
    },

    removeSignature: function(signatureId)
    {
        removeSignatureErrors();
        $.ajax({
                url:Sign.nav.removeSign,
                data:"id=" + signatureId,
                type:"GET",
                success:function (html)
                {
                    refreshSignatureCards(html);
                    
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#signatureCardList", true);
                }
            }
        );
    },
    
    importSignatureDetails: function() {
    
		removeSignatureErrors();
		showLoadingScreen();
		$.ajax({
			url : Sign.nav.importSign,
			type : "POST",
			async:false,
			cache : false,
			success : function(html)
			{
                hideLoadingScreen();
                var $html = $(html).html();
                if ($(html).hasClass('importSignatureWarning')) {
                    showWarningModal($html);
                } else if($("input#formReturned", $(html)).val() == "true"){
                    // then display the errors
                    showSignatureTab("edit");
                    displaySignatureValidationErrors(html);
                    capacityEval($("#personCapacity").val());
                    return;
                } else {
                    refreshSignatureCards(html);
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





function updateSignatureSection(html)
{
    $("#signatureSection").html(html);
}

function displaySignatureValidationErrors(html)
{
	$("#signatureSection").html(html);
}


function scrollToSignatureTop()
{
    scrollToContainer("#signatureCardList");
}

function toggleSignatureAdd()
{
    if($("#tabSignature").is(":hidden"))
    {
        $("#signatureTrigger").addClass("active");
        showSignatureTab();
        //	
    }
    else
    {
        $("#signatureTrigger").removeClass("active");
        hideSignatureTab();
    }
    
}

function showSignatureTab(forEdit)
{
    $("#tabSignature").show();
    
    if(forEdit != "edit"){
    	Sign.ajax.displayChooseSignature();
    }

    $("#signatureTrigger").addClass("active");
    
}


function hideSignatureTab()
{
   
    $("#tabSignature").hide();
    $("#signatureTrigger").removeClass("active");
}




function refreshSignatureCards(html)
{
	$("#signatureCardList").html(html);
	
	scrollToSignatureTop();
    
    checkMaxSignatures();
}



function checkMaxSignatures(){
	var maxSignatures=$('#maxSignatures').val();
	
	var rowCount = $('#signatureCardList tr').length-1;
	
	if(rowCount==maxSignatures){
		$("#signatureTrigger").hide();
	}else{
		$("#signatureTrigger").show();
	}
	
		
}

function removeSignatureErrors()
{
	removePreviousErrors("#signatureCardList");
    removePreviousErrors("#signatureSection");
}


$("#personCapacity").live("change", function() {
	capacityEval($(this).val());
});

function capacityEval(val) {
    if (val == "OTHER") {
        $("#associatedTextDiv").show();
    } else {
        $("#associatedTextDiv").hide();
        $("#signatureAssociatedText").attr("value", "");
    }
}
