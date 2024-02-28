var ApplicationCA = {
    
	correspondenceAddressIndex: 0,
    numberOfAddedCorrespondenceAddresses: 0,
    
    getMaxCorrespondenceAddresses: function() {
        return parseInt($("#applicationCASection #maxCorrespondenceAddresses").val());
    },
    
    getApplicationCACount: function() {
        return Person.numberOfTableRowsNoHeader("#addedApplicationCAs");
    },
    
    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='applicationCA_id_']").each(function(){
        	var obj = new Object();
        	$(this).find("td").each(function(){
                obj[$(this).attr("data-val")] = $(this).html().trim();
            });
            list.push(obj);
        });
        return list;
    },
    
    buildTableFromList: function(tableSelector, list) {
        // clear table by removing all rows except first, which is the header
        $(tableSelector).find("tr:gt(0)").remove();

        for(var i = 0; i < list.length; i++){
            var row = "<tr id='applicationCA_id_" + list[i].id + "'>";

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            /*row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";*/
            	
            /*
            row += "<td data-val='belongs'>";
            row += list[i].belongs;
            row += "</td>";
            */
            
            row += "<td data-val='name'>";
            row += list[i].name;
            row += "</td>";

            
            row += "<td data-val='country'>";
            row += list[i].country;
            row += "</td>";
			
            
            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },
    
    sort: function(tableSelector, property, descending) {
        var list = ApplicationCA.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        ApplicationCA.buildTableFromList(tableSelector, list);
    }
};

ApplicationCA.nav = {
	addApplicationCA: "addApplicationCA.htm",
	remove:"removeApplicationCA.htm",	
	loadCAFromPerson: "loadCAFromPerson.htm"
};

function fillFormWithLoadedPerson(applicationCAForm){

    $("#correspondenceAddressForm\\.correspondenceName").val(applicationCAForm.correspondenceAddressForm.correspondenceName);
    $("#correspondenceAddressForm\\.addressForm\\.street").val(applicationCAForm.correspondenceAddressForm.addressForm.street);
    $("#correspondenceAddressForm\\.addressForm\\.city").val(applicationCAForm.correspondenceAddressForm.addressForm.city);
    $("#correspondenceAddressForm\\.addressForm\\.postalCode").val(applicationCAForm.correspondenceAddressForm.addressForm.postalCode);
    $("#correspondenceAddressForm\\.addressForm\\.country").val(applicationCAForm.correspondenceAddressForm.addressForm.country);
    $("#correspondenceAddressForm\\.correspondenceEmail").val(applicationCAForm.correspondenceAddressForm.correspondenceEmail);
	$("#correspondenceAddressForm\\.correspondencePhone").val(applicationCAForm.correspondenceAddressForm.correspondencePhone);
    $("#correspondenceAddressForm\\.correspondenceFax").val(applicationCAForm.correspondenceAddressForm.correspondenceFax);

}

ApplicationCA.ajax = {

	loadCAFromPerson: function(personId) {

		removeApplicationCAErrors();
		
		$.ajax({
			url: ApplicationCA.nav.loadCAFromPerson,
			type: "GET",
			data: "id=" + personId,
			success: function(form) {

                fillFormWithLoadedPerson(form);
                $("#correspondentButtons").hide();
                $("#newApplicationCA").show();
                postUpdateApplicationCASection();

			}, error: function(error) {
				genericHandleError("An error occured while processing your request. Please try again later.", "#loadCAFromPerson", true);
			}
		});
	},
	
	displayAddApplicationCA: function() {
		
		removeApplicationCAErrors();
		$.ajax({
			url: ApplicationCA.nav.addApplicationCA,
			type: "GET",
			data: "",
			success: function(html) {
				updateApplicationCASection(html, false);				
				postUpdateApplicationCASection();				
			}, error: function(error) {
				genericHandleError("An error occured while processing your request. Please try again later.", "#applicationCASection", true);
			}
		});
	},
	
  
	//Use JM
    /**
     * Gets filled applicationCA form to edit.
     * @param applicationCAId
     */
    getApplicationCAForm: function(applicationCAId, rowNumber) {
    	
    	removeApplicationCAErrors();

        $.ajax({
            url: ApplicationCA.nav.addApplicationCA,
            type: "GET",
            data: "id=" + applicationCAId,
            success: function(html) {
            	
            	updateApplicationCASection(html, true);

            	$("#applicationCACurrentNumber").html(rowNumber);

            	/*
            	// set "save" text for editing
                $("#applicationCASection #addApplicationCATopButton").each(function() {
                	$(this).html($("#personEditText").html());
                });

                $("#applicationCASection .addApplicationCA").each(function() {
                    $(this).html($("#personEditText").html());
                });
                */

                showApplicationCATab("edit");                
                postUpdateApplicationCASection();
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#applicationCACardList", true);
            }
        });
    },
    
    //Use JM
    /**
     * Removes ApplicationCA with id embedded in sender's data-val attribute then refreshes applicationCA cards.
     * @param applicationCAId
     */
    removeApplicationCA: function(applicationCAId) {

    	removeApplicationCAErrors();
        $.ajax({
        	url: ApplicationCA.nav.remove,
            data: "id=" + applicationCAId,
            type: "GET",
            success: function(html) {
            	refreshApplicationCACards(html);
            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#applicationCACardList", true);
            }
        });
    },
    
    
    //Use JM
    /**
     * Posts applicationCA information to server.
     * @param sender
     * @param address
     */
        		
    addApplicationCAPost: function(address, ignoreMatches) {

        var dataToSend = $("#applicationCASection form").serialize()

        if (ignoreMatches) {
            dataToSend = dataToSend + "&ignoreMatches=true";
        }
        
        removeApplicationCAErrors();
        
        $.ajax({
        	url: address,
        	type: "POST",
            data: dataToSend,
            success: function(html) {
            	            	
                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                	// then display the errors
                    displayApplicationCAValidationErrors(html);
                    $("#newApplicationCA").show();
                    return;
                }

                // otherwise, just refresh application cards
                refreshApplicationCACards(html);
                $(".cancelButton.applicationCA").click();

            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#applicationCASection", true);
            }
        });
    }
};


$("#applicationCATrigger").live("click", function() {
	
    toggleApplicationCAAdd();
    $("#applicationCACurrentNumber").html(ApplicationCA.getApplicationCACount() + 1);
});

$("#applicationCACreateButton").live("click", function() {

	$("#newApplicationCA").show();
	$("#correspondentButtons").hide();
});



function toggleApplicationCAAdd() {
	if($("#tabApplicationCA").is(":hidden")){
		$("#applicationCATrigger").addClass("active");
        showApplicationCATab();
    } else {
        $("#applicationCATrigger").removeClass("active");
        hideApplicationCATab();
    }
}


function showApplicationCATab(forEdit) {
	$("#tabApplicationCA").show();
    if (($("#applicationCACreateNew").size() == 0 || $("#applicationCACreateNew").is(":hidden")) && forEdit != "edit") {
    	ApplicationCA.ajax.displayAddApplicationCA();
    } else {
    	$("#newApplicationCA").show();
    }
    $("#applicationCATrigger").addClass("active");

}


function hideApplicationCATab() {
    $("#tabApplicationCA").hide();
    $("#applicationCATrigger").removeClass("active");
    
    $("#applicationCACardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToApplicationCAsTop() {
    scrollToContainer("#applicationCACardList");
}

function applicationCAResetCACounter() {
	ApplicationCA.correspondenceAddressIndex = 0;
	ApplicationCA.numberOfAddedCorrespondenceAddresses = 0;
}


$(".cancelButton.applicationCA").live("click", function(){
	$("#applicationCASection").html("");
	toggleApplicationCAAdd();
	scrollToApplicationCAsTop();
	applicationCAResetCACounter();

});

$("#addApplicantionCATopButton").live("click", function (event, ignoreMatches)
		{

	genericHandleError('addApplicantionCATopButton', "#applicationCASection", true);

		
		    $(".addApplicationCA").trigger("click", [ignoreMatches]);
		});

$("#applicationCACreateExist").live("click", function (event, ignoreMatches)
		{

		ApplicationCA.ajax.loadCAFromPerson( $("#person").val());
		
		});

	
function refreshApplicationCACards(html) {
    $("#applicationCACardList").html(html);
    scrollToApplicationCAsTop();
    checkMaxApplicationCA();
}

//Use JM
function displayApplicationCAValidationErrors(html) {
    updateApplicationCASection(html, true);
    postUpdateApplicationCASection();
}

function mustIgnoreApplicationCAMatches(){
	return $("#importedApplicationCA").val() == "true";
}


//Use JM
$(".addApplicationCA").live("click", function(e, ignoreMatches) {
	
 		if(mustIgnoreApplicationCAMatches()) {
        ignoreMatches = "true";
    }
 	
	ApplicationCA.ajax.addApplicationCAPost(ApplicationCA.nav.addApplicationCA, ignoreMatches);

});


$("#addApplicationCATopButton").live("click", function (e, ignoreMatches) {
    $(".addApplicationCA").trigger("click", [ignoreMatches]);
});




function removeApplicationCAErrors() {
    removePreviousErrors("#applicationCACardList");
    removePreviousErrors("#applicationCASection");
}

$("#editApplicationCAButton").live("click", function() {
	ApplicationCA.ajax.getApplicationCAForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteApplicationCAButton").live("click", function() {

    var applicationCAId = $(this).attr("data-val");

    var onConfirmYes = partial(ApplicationCA.ajax.removeApplicationCA, applicationCAId);

    showConfirmModal($("#applicationca_deleteConfirmation").html(), onConfirmYes);
});




/**
 * <p>
 * Function called each time the fields for the applicationCA need to be shown.
 * It fills the #aplicationCASection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the aplicationCA is being added or edited.
 * If the aplicationCA is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */

//Use JM
function updateApplicationCASection(html, forEditing) {
	
    if (Person.containsImportError(html)) {
        showWarningModal($(".importError", $(html)).html());
        return;
    }
    
    $("#applicationCASection").html(html);
    if(forEditing) {
    	$("#correspondentButtons").hide();
    }
    
    // value needs to be parsed to support integer addition later on
    // otherwise it behaves as a string and, for example, appends 1 to 1 obtaining 11 instead of 2
    ApplicationCA.numberOfAddedCorrespondenceAddresses = parseInt($("#applicationCASection #numberOfCA").val());
    ApplicationCA.correspondenceAddressIndex = ApplicationCA.numberOfAddedCorrespondenceAddresses;
    
}



/**
 * Defines the function to execute when clicking the Remove button of a correspondence address.
 * The first parent which has a CAwrapper id is selected and removed.
 * The Designer number of added CAs decreases and the checkbox is reset if there are no CAs added.
 */
$("#applicationCASection .removeCA").live("click", function() {
    $(this).parents("[id^=CAwrapper_]").first().remove();
    AplicationCA.numberOfAddedCorrespondenceAddresses -= 1;

 
});

$("a[href='#tabAplicationCA']").live("click", function() {
    if (($("#aplicationCAConfig_searchEnabled").val() == "true") && ($("#service_aplicationCAMatches_enabled") == "true")) {
        showWarningModal($("#aplicationCACreationInfo").html());
    }
});



function getAplicationCAAutocompleteNavigationUrl(keyValuePairArray) {
    return parseLinkUsingKeyValuePairs(keyValuePairArray, $("#aplicationCAConfig_searchUrl").val());
}

function postUpdateApplicationCASection() {

	
    Person.fillStatesSelectbox(
    		$("#applicationCAForm select[name='correspondenceAddressForm.addressForm.country']").val(),
    		"#applicationCAForm select[name='correspondenceAddressForm.addressForm.stateprovince']",
    		$("#applicationCAForm input[id='correspondenceAddressForm.addressForm.selectedstate']").val());

	if($("#applicationCAForm input[name*='electronicCorrespondence']").length>0){
        if($("#applicationCAForm input[name*='electronicCorrespondence']:checked").length ==0){
            $("#applicationCAForm input[name*='electronicCorrespondence'][value='true']").attr("checked", "checked");
        }
    }

}


$("#applicationCASection select[name='correspondenceAddressForm.addressForm.country']").live("change", function() {


    Person.fillStatesSelectbox($(this).val(), "#applicationCASection select[id='correspondenceAddressForm.addressForm.stateprovince']");


});


// sorting functionality trigger
$("#applicationCACardList table th a.sorter").live("click", function() {
    Person.sortTable("ApplicationCA", $(this));
});


function checkMaxApplicationCA(){
    var maxCAs=$('#maxApplicationCA').val();

    var rowCount = $('#applicationCACardList tr').length-1;

    if(rowCount>=maxCAs){
        $("#applicationCATrigger").hide();
    } else {
        $("#applicationCATrigger").show();
    }
}