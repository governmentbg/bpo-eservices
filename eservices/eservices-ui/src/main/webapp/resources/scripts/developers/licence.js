var Licence = {
    
    getMaxLicences: function() {
        return parseInt($("#licenceSection #maxLicences").val());
    },
    
    getLicenceCount: function() {
        return Person.numberOfTableRowsNoHeader("#addedLicences");
    },
    
    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='licence_id_']").each(function(){
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
            var row = "<tr id='licence_id_" + list[i].id + "'>";

            row += "<td data-val='kind'>";
            row += list[i].kind;
            row += "</td>";

            row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";
            
            row += "<td data-val='sublicence'>";
            row += list[i].sublicence;
            row += "</td>";

            
            row += "<td data-val='territory'>";
            row += list[i].territory;
            row += "</td>";
            
            row += "<td data-val='endDate'>";
            row += list[i].endDate;
            row += "</td>";
			
            
            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },
    
    sort: function(tableSelector, property, descending) {
        var list = Licence.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Licence.buildTableFromList(tableSelector, list);
    }
};

Licence.nav = {
	addLicence: "addLicence.htm",
	remove:"removeLicence.htm"
};

Licence.ajax = {
		
	getLicenceForm: function(licenceId, rowNumber) {
    	
    	removeLicenceErrors();

        $.ajax({
            url: Licence.nav.addLicence,
            type: "GET",
            data: "id=" + licenceId,
            success: function(html) {
            	var edit = licenceId != "";
            	updateLicenceSection(html, true);
            	toggleTerritoryText();
            	toggleSublicenceCheckbox();
            	managePeriodLimitationDateField();
            	//$("#licenceCurrentNumber").html(rowNumber);

                showLicenceTab("edit");
                toggleGshelperExtent(edit);
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#licenceCardList", true);
            }
        });
    },
    
    removeLicence: function(licenceId) {

    	removeLicenceErrors();
        $.ajax({
        	url: Licence.nav.remove,
            data: "id=" + licenceId,
            type: "GET",
            success: function(html) {
            	refreshLicenceCards(html);

                // updateFees
                getFeesInformation();

            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#licenceCardList", true);
            }
        });
    },
    
    addLicencePost: function(address) {
    	toggleSublicenceCheckbox(true);
        var dataToSend = $("#licenceSection form").serialize();        
        removeLicenceErrors();
        
        $.ajax({
        	url: address,
        	type: "POST",
            data: dataToSend,
            success: function(html) {
            	            	
                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                	// then display the errors
                    displayLicenceValidationErrors(html);
                    toggleTerritoryText();
                    toggleSublicenceCheckbox();
                    return;
                }

                // otherwise, just refresh application cards
                refreshLicenceCards(html);
                $(".cancelButton.licence").click();

                // updateFees
                getFeesInformation();

            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#licenceSection", true);
            }
        });
    }
};


$("#licenceTrigger").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
    toggleLicenceAdd();
});

function toggleLicenceAdd() {
	if($("#tabLicence").is(":hidden")){
		$("#licenceTrigger").addClass("active");
		Licence.ajax.getLicenceForm("", "");
        showLicenceTab();
    } else {
        $("#licenceTrigger").removeClass("active");
        hideLicenceTab();
    }
}


function showLicenceTab(forEdit) {
	
    $("#licenceTrigger").addClass("active");
    
    $("#tabLicence").show();

}

function hideLicenceTab() {
    $("#tabLicence").hide();
    $("#licenceTrigger").removeClass("active");
    
    $("#licenceCardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToLicencesTop() {
    scrollToContainer("#licenceCardList");
}

$(".cancelButton.licence").live("click", function(){
	$("#licenceSection").html("");
	toggleLicenceAdd();
	scrollToLicencesTop();

});

	
function refreshLicenceCards(html) {
    $("#licenceCardList").html(html);
    scrollToLicencesTop();
    checkMaxLicences();
}

//Use JM
function displayLicenceValidationErrors(html) {
	updateLicenceSection(html, true);
	managePeriodLimitationDateField();
	var radio = $("#gshelper_extent input[type='radio']:checked");
	if(radio.size() == 1){
		var checked = radio[0]; 
		var checkedVal = $(checked).attr('value');
		if(checkedVal == "true") {
			$("#gshelperGS").hide();

			$("#surrenderDeclarationPartial").hide();
		} else {
			$("#gshelperGS").show();
			$("#surrenderDeclarationPartial").show();
			$("#gshelperGS #tableGoodsAndServices").refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
			$("#gshelperGS .term-list").removeClass("notRemovableClass");
			$("#gshelperGS .term-options").removeClass("notRemovableClass");
			$("#gshelperGS .terms-header").removeClass("notRemovableClass");
			$(".term-list a").addClass("term-close");
			$(".term-options a").addClass("term-close");
			$(".terms-header a").addClass("terms-refresh");
		}
	}

}

//Use JM
$(".addLicence").live("click", function(e) {
	 	
	Licence.ajax.addLicencePost(Licence.nav.addLicence);

});

function removeLicenceErrors() {
    removePreviousErrors("#licenceCardList");
    removePreviousErrors("#licenceSection");
}

$("#editLicenceButton").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
	Licence.ajax.getLicenceForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteLicenceButton").live("click", function() {

    var licenceId = $(this).attr("data-val");

    var onConfirmYes = partial(Licence.ajax.removeLicence, licenceId);

    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

//Use JM
function updateLicenceSection(html, forEditing) {
    
    $("#licenceSection").html(html);    
}


// sorting functionality trigger
$("#licenceCardList table th a.sorter").live("click", function() {
    Person.sortTable("Licence", $(this));
});


function checkMaxLicences(){
	var maxTMs=$('#maxLicences').val();
	var rowCount = $('#licenceCardList tr').length-1;
	
	if(rowCount==maxTMs){
		$("#licenceTrigger").hide();
	}else{
		$("#licenceTrigger").show();
	}
}

$("#territoryRadios input[type='radio']").live("click", function () {
	toggleTerritoryText();
});


function toggleTerritoryText() {
	var radio = $("#territoryRadios input[type='radio']:checked");
	if(radio.size() == 1){
		var checked = radio[0]; 
		var checkedVal = $(checked).attr('value');
		if(checkedVal == "true"){
			$("#licenceTerritoryTextDiv").show();
		} else {
			$("#licenceTerritoryTextDiv").hide();
			$("#licenceTerritoryTextDiv textarea").each(function(){
				$(this).val("");
			});
		}
	}
}

$("#licenceKindRadios input[type='radio']").live("click", function () {
	toggleSublicenceCheckbox();
});

function toggleSublicenceCheckbox(beforeSave) {
	var radio = $("#licenceKindRadios input[type='radio']:checked");
	if(radio.size() == 1){
		var checked = radio[0]; 
		var checkedVal = $(checked).attr('value');
		if(checkedVal == "EXCLUSIVE"){
			$("#sublicenceGrant").show();
		} else {
			$("#sublicenceGrant").hide();
			if(beforeSave){
				$("#sublicenceGrant input[type='radio']").each(function(){
					if($(this).val() == "false"){
						$(this).attr("checked",true);
					}
				});
			}
		}
	}
}

$("#licencePeriodLimitationIndicator").live("change", function(){
	managePeriodLimitationDateField();
});

function managePeriodLimitationDateField(){
	var indicator = $("#licencePeriodLimitationIndicator");
	var date = $("#licencePeriodLimitationEndDate");
	if($(indicator).is(":checked")) {
		$("#licencePeriodLimitationEndDate input").each(function(){
			$(this).val("");
		});
		date.hide();
	} else {
		date.show();
	}
}