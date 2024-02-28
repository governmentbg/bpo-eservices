var Appeal = {
    
    getMaxAppeals: function() {
        return parseInt($("#appealSection #maxAppeals").val());
    },
    
    getAppealCount: function() {
        return Person.numberOfTableRowsNoHeader("#addedAppeals");
    },
    
    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='appeal_id_']").each(function(){
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
            var row = "<tr id='appeal_id_" + list[i].id + "'>";

            row += "<td data-val='kind'>";
            row += list[i].kind;
            row += "</td>";

            row += "<td data-val='id'>";
            row += list[i].id;
            row += "</td>";
            
            row += "<td data-val='appeal_num'>";
            row += list[i].appeal_num;
            row += "</td>";

            
            row += "<td data-val='appeal_date'>";
            row += list[i].appeal_date;
            row += "</td>";
            			
            
            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },
    
    sort: function(tableSelector, property, descending) {
        var list = Appeal.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Appeal.buildTableFromList(tableSelector, list);
    }
};

Appeal.nav = {
	addAppeal: "addAppeal.htm",
	remove:"removeAppeal.htm"
};

Appeal.ajax = {
		
	getAppealForm: function(appealId, rowNumber) {
    	
    	removeAppealErrors();

        $.ajax({
            url: Appeal.nav.addAppeal,
            type: "GET",
            data: "id=" + appealId,
            success: function(html) {
                var edit = appealId != "";
            	updateAppealSection(html, true);
            	
                showAppealTab("edit");    
                toggleAppealKind();
                refreshExplanations();
                updateFeesInformation();
                toggleGshelperExtent(edit);
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#appealCardList", true);
            }
        });
    },
    
    removeAppeal: function(appealId) {

    	removeAppealErrors();
        $.ajax({
        	url: Appeal.nav.remove,
            data: "id=" + appealId,
            type: "GET",
            success: function(html) {
            	refreshAppealCards(html);
            	updateFeesInformation();
            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#appealCardList", true);
            }
        });
    },
    
    addAppealPost: function(address) {
        var dataToSend = $("#appealSection form").serialize();        
        removeAppealErrors();
        
        $.ajax({
        	url: address,
        	type: "POST",
            data: dataToSend,
            success: function(html) {
            	            	
                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                	// then display the errors
                    displayAppealValidationErrors(html);
                    toggleAppealKind();
                    refreshExplanations();
                    updateFeesInformation();
                    return;
                }

                // otherwise, just refresh application cards
                refreshAppealCards(html);
                $(".cancelButton.appeal").click();
                updateFeesInformation();
                refreshExplanations();

            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#appealSection", true);
            }
        });
    }
};


$("#appealTrigger").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
    toggleAppealAdd();
});

function toggleAppealAdd() {
	if($("#tabAppeal").is(":hidden")){
		$("#appealTrigger").addClass("active");
		Appeal.ajax.getAppealForm("", "");
        showAppealTab();
    } else {
        $("#appealTrigger").removeClass("active");
        hideAppealTab();
    }
}


function showAppealTab(forEdit) {
	
    $("#appealTrigger").addClass("active");
    
    $("#tabAppeal").show();

}

function hideAppealTab() {
    $("#tabAppeal").hide();
    $("#appealSection").html("");
    $("#appealTrigger").removeClass("active");
    
    $("#appealCardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToAppealsTop() {
    scrollToContainer("#appealCardList");
}

$(".cancelButton.appeal").live("click", function(){
	$("#appealSection").html("");
	toggleAppealAdd();
	scrollToAppealsTop();

});

	
function refreshAppealCards(html) {
    $("#appealCardList").html(html);
    scrollToAppealsTop();
    checkMaxAppeals();
}

//Use JM
function displayAppealValidationErrors(html) {
	updateAppealSection(html, true);
    var radio = $("#gshelper_extent input[type='radio']:checked");
    if(radio.size() == 1){
        var checked = radio[0];
        var checkedVal = $(checked).attr('value');
        if(checkedVal == "true") {
            $("#gshelperGS").hide();
        } else {
            $("#gshelperGS").show();
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
$(".addAppeal").live("click", function(e) {
	 	
	Appeal.ajax.addAppealPost(Appeal.nav.addAppeal);

});

function removeAppealErrors() {
    removePreviousErrors("#appealCardList");
    removePreviousErrors("#appealSection");
}

$("#editAppealButton").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
	Appeal.ajax.getAppealForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteAppealButton").live("click", function() {

    var appealId = $(this).attr("data-val");

    var onConfirmYes = partial(Appeal.ajax.removeAppeal, appealId);

    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

//Use JM
function updateAppealSection(html, forEditing) {
    
    $("#appealSection").html(html);    
}


// sorting functionality trigger
$("#appealCardList table th a.sorter").live("click", function() {
    Person.sortTable("Appeal", $(this));
});


function checkMaxAppeals(){
	var maxTMs=$('#maxAppeals').val();
	var rowCount = $('#appealCardList tr').length-1;
	
	if(rowCount==maxTMs){
		$("#appealTrigger").hide();
	}else{
		$("#appealTrigger").show();
	}
}

$("#appeal_kind_choice input[type='radio']").live("click", function () {
	toggleAppealKind();
})

function toggleAppealKind() {
	var radio = $("#appeal_kind_choice input[type='radio']:checked");
	if(radio.size() == 1){
		var checked = radio[0]; 
		var checkedVal = $(checked).attr('value');
		if(checkedVal == "APPEAL_AGAINST_OPPOSITION_DECISION"){
			$("#appealOppositionDiv").show();
			$("#evidWarningOppo").show();
			$("#evidWarningGeneral").hide();
		} else {
			$("#appealOppositionDiv").hide();
			$("#appealDecisionNumber").val('');
			$("#oppositionFilingDate").val('');
			$("#evidWarningOppo").hide();
			$("#evidWarningGeneral").show();
		}
	}
}

