var OpposedTM = {};

//Main button to add a new trademark, open the form to be fill
$("#opposedTradeMarkTrigger").live("click", function()
{
    toggleOpposedTradeMarkAdd();
    //$("#applicantCurrentNumber").html(Applicant.getApplicantCount() + 1);
});

//Button to add a new trademark to the list of trademarks
$(".addOpposedTM").live("click", function (event, ignoreMatches)
{
    /*if(mustIgnoreApplicantMatches())
    {*/
        ignoreMatches = "true";
    //}
    var applicationType = $("#applicationType").val();
    OpposedTM.ajax.addOpposedTMPost(applicationType , OpposedTM.nav.addOpposedTM, ignoreMatches);
});

$(".cancelButton.opposedTradeMark").live("click", function ()
{
    $("#opposedTradeMarkSection").html("");
    toggleOpposedTradeMarkAdd();
    scrollToOpposedTradeMarkTop();
});

$("#editOpposedTMButton").live("click", function ()
		{
		    OpposedTM.ajax.getOpposedTMForm($(this).attr("data-val"), $(this).attr("data-rownum"));
		});

$("#deleteOpposedTMButton").live("click", function ()
{
    var tmoId = $(this).attr("data-val");
    var onConfirmYes = partial(OpposedTM.ajax.removeOpposedTM, tmoId);
    showConfirmModal($("#delete_confirmation").html(), onConfirmYes);
});
		
OpposedTM.nav =
{
		addOpposedTM:"addOpposedTradeMark.htm",
	    getOpposedTM:"getOpposedTradeMarkForEdit.htm",
	    removeOpposedTM:"removeOpposedTradeMark.htm"
	    //importApp: "importApplicant.htm"		
	
};

OpposedTM.ajax =
{
	/**
	 * Display the form with the details of the selected trademark to be edited
	 * */
    displayChooseOpposedTM: function()
    {
        removeOpposedTMErrors();
        $.ajax({
            url:OpposedTM.nav.addOpposedTM,
            data:"",
            success:function (html)
            {
                updateOpposedTMSection(html, false);

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#opposedTradeMarkSection", true);
            }
        });
    },
    
    /**
     * Posts trademark details to the server
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    addOpposedTMPost: function(applicationType , address, ignoreMatches)
    {
        var dataToSend = $("#opposedTradeMarkSection form").serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
       
        dataToSend = dataToSend + "&applicationType=" + applicationType; 
        removeOpposedTMErrors();
        $.ajax(
        {
        	url:address,
            type:"POST",
            data:dataToSend,
            success:function (html)
            {
            	/*JD Find about matches, dont use it for now, dont have to
                // Check if returned form is a result of applicants matching
                if ($("input#applicantMatchesExist", $(html)).val() == "true")
                {
                	// show popup with matches
                 	$("#applicantMatches").html(html);
                    $("#doublesApplicant").modal("show");
                    return;
              	}
                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true")
                {
                	// then display the errors
                    displayApplicantValidationErrors(html);
                    return;
               	}*/

                // otherwise, just refresh applicant cards
            	
            	
            	refreshTMOCards(html);
            	
                $(".cancelButton.opposedTradeMark").click();
                    
                    
                /*JD: Espero no tener que hacerlo nunca*/	
                //refreshApplicantsPayerSection();
            },
            error:function (error)
            {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#opposedTradeMarkSection", true);
            }
        });
    },
    
    
   
    
    /**
     * Gets filled trade mark details form to edit
     * @param applicantId
     */
    getOpposedTMForm: function(tmoId, rowNumber)
    {
    	removeOpposedTMErrors();
        $.ajax({
            url:OpposedTM.nav.getOpposedTM,
            type:"GET",
            data:"id=" + tmoId,
            success:function (html)
            {
            	updateOpposedTMSection(html, true);

                //$("#applicantCurrentNumber").html(rowNumber);
                // set "save" text for editing
                $("#opposedTradeMarkSection .addOpposedTM").each(function()
                {
                    $(this).html($("#personEditText").html());
                })

                showOpposedTMTab("edit");

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError("An error occured while processing your request. Please try again later.",
                    "#applicantCardList", true);
            }
        });
    },
    /**
     * Removes trademark with id embedded in data-val attribute then refreshes applicant cards.
     * @param applicantId
     */
    removeOpposedTM: function(tmId)
    {
        removeOpposedTMErrors();
        $.ajax({
                url:OpposedTM.nav.removeOpposedTM,
                data:"id=" + tmId,
                type:"GET",
                success:function (html)
                {
                    refreshTMOCards(html);
                    
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#tmoCardList", true);
                }
            }
        );
    }
};





/**
 * <p>
 * Function called each time the fields for the applicant need to be shown.
 * It fills the #applicantSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the applicant is being added or edited.
 * If the applicant is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateOpposedTMSection(html, forEditing)
{
   /* if(Person.containsImportError(html))
    {
        showWarningModal($(".importError", $(html)).html());
        //$("#applicantSection .importErrorPlaceholder").html(html);
        return;
    }*/

    $("#opposedTradeMarkSection").html(html);
    
    //************JD: Something about adresses that I dont think I have to use.
    //$("#tmSection .addAnotherCA").hide();

    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    //Applicant.numberOfAddedCorrespondenceAddresses = parseInt($("#applicantSection #numberOfCA").val());
    //Applicant.correspondenceAddressIndex = Applicant.numberOfAddedCorrespondenceAddresses;

    /*if (forEditing && Applicant.numberOfAddedCorrespondenceAddresses > 0)
    {
        applicantCorrespAddrTemplate_Create("#applicantSection");
        $("#applicantSection #applicantHasCorrespondenceAddress").attr("checked", "checked");
        if(Applicant.numberOfAddedCorrespondenceAddresses < Applicant.getMaxCorrespondenceAddresses())
        {
            $("#applicantSection .addAnotherCA").show();
        }
        if(Applicant.getMaxCorrespondenceAddresses() <= 1)
        {
            $("#applicantSection .removeCA").hide();
        }
    }*/

    // set type of applicant in select box
    //var applicantType = $("#tmSection form .apptypehidden").val();
    //$("#aptype").val(applicantType);


    //applicantCorrespAddrTemplate_Neutralize("#applicantSection #correspondenceAddressPath");
}

function scrollToOpposedTradeMarkTop()
{
    scrollToContainer("#tmoCardList");
}

function toggleOpposedTradeMarkAdd()
{
    if($("#tabOpposedTradeMark").is(":hidden"))
    {
        $("#opposedTradeMarkTrigger").addClass("active");
        showOpposedTMTab();
    }
    else
    {
        $("#opposedTradeMarkTrigger").removeClass("active");
        hideOpposedTMTab();
    }
}

function showOpposedTMTab(forEdit)
{
    $("#tabOpposedTradeMark").show();
    /*if(($("#applicantCreateNew").size() == 0 || $("#applicantCreateNew").is(":hidden"))
        && forEdit != "edit")
    {*/
    if(forEdit != "edit"){
    	
    	OpposedTM.ajax.displayChooseOpposedTM();
    
    }

    $("#opposedTradeMarkTrigger").addClass("active");
}

function hideOpposedTMTab()
{
    $("#tabOpposedTradeMark").hide();
    $("#opposedTradeMarkTrigger").removeClass("active");
}


function refreshTMOCards(html)
{
	$("#tmoCardList").html(html);
	
	scrollToOpposedTradeMarkTop();
    
    checkMaxOpposedTMs();
}

function refreshOGS(html)
{
	$("#tmoExtent").html(html);
	
}


function checkMaxOpposedTMs(){
	var maxOTMs=$('#maxOTMs').val();
	
	var rowCount = $('#tmoCardList tr').length-1;
	
	if(rowCount==maxOTMs){
		$("#opposedTradeMarkTrigger").hide();
	}else{
		$("#opposedTradeMarkTrigger").show();
	}
	
		
}


function removeOpposedTMErrors()
{
	removePreviousErrors("#tmoCardList");
    removePreviousErrors("#opposedTradeMarkSection");
}
