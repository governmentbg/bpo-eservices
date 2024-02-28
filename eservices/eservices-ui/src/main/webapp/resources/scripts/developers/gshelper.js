var Gshelper = {

    getMaxGshelpers: function() {
        return parseInt($("#gshelperSection #maxGshelpers").val());
    },

    getGshelpersCount: function() {
        return Person.numberOfTableRowsNoHeader("#addedGshelpers");
    },

    buildListFromTable: function(tableSelector) {
        var list = new Array();
        $(tableSelector + " tr[id^='gshelper_id_']").each(function(){
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
            var row = "<tr id='gshelper_id_" + list[i].id + "'>";

            row += "<td data-val='tmApplicationNumber'>";
            row += list[i].tmApplicationNumber;
            row += "</td>";

            row += "<td data-val='extent'>";
            row += list[i].extent;
            row += "</td>";

            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },

    sort: function(tableSelector, property, descending) {
        var list = Gshelper.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Gshelper.buildTableFromList(tableSelector, list);
    }
};

Gshelper.nav = {
	addGshelper: "addGSHelper.htm",
	remove:"removeGSHelper.htm",
	refreshGS: "refreshGSHelperGS.htm",
	refreshGshelper: "refreshGSHelper.htm"

};

Gshelper.ajax = {

	refreshGshelperForm: function(gshelperId, rowNumber) {

	    	removeGshelperErrors();

	    	var tmApplicationNumber = $("#tmApplicationNumberGshelper").val();

	        $.ajax({
	            url: Gshelper.nav.refreshGshelper,
	            type: "GET",
	            data: { id: gshelperId, tm: tmApplicationNumber} ,
	            success: function(html) {
	            	var edit = gshelperId != "";
	            	updateGshelperSection(html, true);
	                showGshelperTab("edit");
	                toggleGshelperExtent(edit);
	            },
	            error: function(error) {
	                genericHandleError("An error occured while processing your request. Please try again later.", "#gshelperCardList", true);
	            }
	        });
	    },

	getGshelperForm: function(gshelperId, rowNumber) {

    	removeGshelperErrors();

        $.ajax({
            url: Gshelper.nav.addGshelper,
            type: "GET",
            data: "id=" + gshelperId,
            success: function(html) {
            	var edit = gshelperId != "";

            	updateGshelperSection(html, true);
                showGshelperTab("edit");
                toggleGshelperExtent(edit);
            },
            error: function(error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#gshelperCardList", true);
            }
        });
    },

    refreshGS: function(edit){
    	if($("#tmApplicationNumberGshelper option:selected")){
    		var tmNum = $("#tmApplicationNumberGshelper option:selected").val();
    		$.ajax({
    			url: Gshelper.nav.refreshGS,
    			type: "GET",
    			async: false,
    			data: "tmNum=" + tmNum+"&edit="+edit,
    			success: function(html) {

    			},
    			error: function(error) {
    				genericHandleError("An error occured while processing your request. Please try again later.", "#gshelperCardList", true);
    			}
    		});
    	}
    },

    removeGshelper: function(gshelperId) {

    	removeGshelperErrors();
        $.ajax({
        	url: Gshelper.nav.remove,
            data: "id=" + gshelperId,
            type: "GET",
            success: function(html) {
            	refreshGshelperCards(html);
            	updateFeesInformation();
            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#gshelperCardList", true);
            }
        });
    },

    addGshelperPost: function(address) {
    	$("#gsHelperGSCommentDiv").appendTo("#gshelperSection form");
        var dataToSend = $("#gshelperSection form").serialize();
        if(dataToSend.indexOf("tmApplicationNumber") == -1) {
            dataToSend+="&tmApplicationNumber="+$("#tmApplicationNumberGshelper").val()
        }
        removeGshelperErrors();

        $.ajax({
        	url: address,
        	type: "POST",
            data: dataToSend,
            success: function(html) {

                // check if returned form is a form containing errors
                if ($("input#formReturned", $(html)).val() == "true") {
                	// then display the errors
                    displayGshelperValidationErrors(html);
                    toggleGshelperExtent();
                    return;
                }

                // otherwise, just refresh application cards
                refreshGshelperCards(html);
                closeGshelperSection();
                updateFeesInformation();

            },
            error: function(error) {
            	genericHandleError("An error occured while processing your request. Please try again later.", "#gshelperSection", true);
            }
        });
    }
};


$("#gshelperTrigger").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
    toggleGshelperAdd();
});

function toggleGshelperAdd() {
	if($("#tabGshelper").is(":hidden")){
		$("#gshelperTrigger").addClass("active");
		Gshelper.ajax.getGshelperForm("", "");
        showGshelperTab();
    } else {
        $("#gshelperTrigger").removeClass("active");
        hideGshelperTab();
    }
}


function showGshelperTab(forEdit) {

    $("#gshelperTrigger").addClass("active");

    $("#tabGshelper").show();

}

function hideGshelperTab() {
    $("#tabGshelper").hide();
    $("#gshelperTrigger").removeClass("active");

    $("#gshelperCardList").find("tr").each(function(){
        $(this).removeClass("active");
    });
}

function scrollToGshelperTop() {
    scrollToContainer("#gshelperCardList");
}

$(".cancelButton.gshelper").live("click", function(){
    closeGshelperSection();
});

function closeGshelperSection() {
    $("#gshelperSection").html("");
    toggleGshelperAdd();
    scrollToGshelperTop();
}

function refreshGshelperCards(html) {
    $("#gshelperCardList").html(html);
    scrollToGshelperTop();
    checkMaxGshelpers();
}

//Use JM
function displayGshelperValidationErrors(html) {
    updateGshelperSection(html, true);

}

//Use JM
$(".addGshelper").live("click", function(e) {

	Gshelper.ajax.addGshelperPost(Gshelper.nav.addGshelper);

});

function removeGshelperErrors() {
    removePreviousErrors("#gshelperCardList");
    removePreviousErrors("#gshelperSection");
}

$("#editGshelperButton").live("click", function() {
    if(isFormOpen("#tabTM"))
    {
        showMessageModal(createFormsOpenText());
        return false;
    }
	Gshelper.ajax.getGshelperForm($(this).attr("data-val"), $(this).attr("data-rownum"));
});

$("#deleteGshelperButton").live("click", function() {

    var gshelperId = $(this).attr("data-val");

    var onConfirmYes = partial(Gshelper.ajax.removeGshelper, gshelperId);

    showConfirmModal($("#person_deleteConfirmation").html(), onConfirmYes);
});

//Use JM
function updateGshelperSection(html, forEditing) {

    $("#gshelperSection").html(html);
    $("#gsHelperGSCommentDiv").insertAfter("#gshelperGS");
}


// sorting functionality trigger
$("#gshelperCardList table th a.sorter").live("click", function() {
    Person.sortTable("Gshelper", $(this));
});


function checkMaxGshelpers(){
	var maxTMs=$('#maxGshelpers').val();
	var rowCount = $('#gshelperCardList tr').length-1;

	if(rowCount==maxTMs){
		$("#gshelperTrigger").hide();
	}else{
		$("#gshelperTrigger").show();
	}
}


$("#gshelper_extent input[type='radio']").live("change", function(){
	toggleGshelperExtent(false);
});

function toggleGshelperExtent(edit){
	var radio = $("#gshelper_extent input[type='radio']:checked");
	if(radio.size() == 1){
		var checked = radio[0];
		var checkedVal = $(checked).attr('value');
		if(checkedVal == "true"){
            $("#surrenderDeclarationFull").show();
            $("#gshelperGS").hide();

            $("#surrenderDeclarationPartial").hide();
		} else {
            $("#surrenderDeclarationFull").hide();
            $("#gshelperGS").show();

            $("#surrenderDeclarationPartial").show();

			Gshelper.ajax.refreshGS(edit);
			$(radio).closest("section").find("#tableGoodsAndServices").refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
        	$("#gshelperGS .term-list").removeClass("notRemovableClass");
    		$("#gshelperGS .term-options").removeClass("notRemovableClass");
    		$("#gshelperGS .terms-header").removeClass("notRemovableClass");
    		// $(".term-list a").addClass("term-close");
    		// $(".term-options a").addClass("term-close");
    		// $(".terms-header a").addClass("terms-refresh");
		}
	}

}

$("#tmApplicationNumberGshelper").live("change", function(){
	Gshelper.ajax.refreshGshelperForm("","");
	toggleGshelperExtent(false);
});


$(".icon-arrow-down-gshelper").live("click", function (){
	$("#expandGshelperReview"+$(this).attr("data-val")).hide();
	$("#collapseGshelperReview"+$(this).attr("data-val")).show();
	if ($("#tablegshelper"+$(this).attr("data-val")).length > 0){
		$("#tablegshelper"+$(this).attr("data-val")).show();
	}
	if ($("#tableremain_gshelper"+$(this).attr("data-val")).length > 0){
		$("#tableremain_gshelper"+$(this).attr("data-val")).show();
	}
});

$(".icon-arrow-up-gshelper").live("click", function (){
	$("#expandGshelperReview"+$(this).attr("data-val")).show();
	$("#collapseGshelperReview"+$(this).attr("data-val")).hide();
	if ($("#tablegshelper"+$(this).attr("data-val")).length > 0){
		$("#tablegshelper"+$(this).attr("data-val")).hide();
	}
	if ($("#tableremain_gshelper"+$(this).attr("data-val")).length > 0){
		$("#tableremain_gshelper"+$(this).attr("data-val")).hide();
	}
});