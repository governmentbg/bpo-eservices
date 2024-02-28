//function setDisabledCheckExhClaimLater(disabled) {
//	$('#checkExhPriorityClaimLater').attr('disabled', disabled);
//}
//
//function isSelectedNoExhButton() {
//	return $('a#noExhibition').parent().hasClass('active');
//}

function disableNoButtons() {
	
    if ($("#claimTableContainer tr.priority").length > 0) {
        $('#noPriority').bind('click', false);
        $('a#noPriority').addClass('disabled');
        
        $('input#noPriority').attr('disabled','true');
        $('input#noPriority').removeAttr('checked');
    } else {
        $('#noPriority').unbind('click', false);
        $('a#noPriority').removeClass('disabled');
        $('input#noPriority').removeAttr('disabled');
    }
    
    if ($("#claimTableContainer tr.exhpriority").length > 0) {
        $('#noExhibition').bind('click', false);
        $('a#noExhibition').addClass('disabled');
        $('input#noExhibition').attr('disabled','true');
        $('input#noExhibition').removeAttr('checked');
    } else {
        $('#noExhibition').unbind('click', false);
        $('a#noExhibition').removeClass('disabled');
        $('input#noExhibition').removeAttr('disabled');
    }
    
//    if (isSelectedNoExhButton()) {
//    	setDisabledCheckExhClaimLater(false);
//    } else {
//    	setDisabledCheckExhClaimLater(true);
//    }
    
}

$('.claimSections .cancelButton').live("click", function() {
    removeAllHighlightRow();
    $(this).closest(".tabClaim").hide();
});

$('.claimSections .cancelManualButton').live("click", function() {
    $(this).closest('#manualDetails').hide();
});

$('.claimSections .toggleNav .btn').live("click", function() {
    if($(this).attr('id') == 'noPriority') {
        $('.priority').removeClass('active');
    } else if($(this).attr('id') == 'noExhibition') {
        $('.exhpriority').removeClass('active');
    }
    // else if($(this).attr('id') == 'noSeniority') {
    //    $('.seniority').removeClass('active');
    //} else if($(this).attr('id') == 'noTransformation'){
    //    $('.transformation').removeClass('active');
    //}
});







function changeClaimText(buttonSelector, numberOfClaims) {
    if($(buttonSelector).size() == 0) {
        return;
    }
    if(numberOfClaims == 0) {
        $(buttonSelector).html($(buttonSelector + "_yesMessage").html());
    } else {
        $(buttonSelector).html($(buttonSelector + "_addAnotherMessage").html());
    }
}

ClaimTypes = {
    Priority: "priority",
    Exhibition: "exhpriority"
    //, Seniority: "seniority",
    //Transformation: "transformation"
};



function createClaimListFromTable() {
    var list = new Array();
    $("#claimTableContainer").find("tr:gt(0)").each(function() {
        var obj = new Object();
        obj.className = $(this).attr("class");
        $(this).find("td").each(function() {
            obj[$(this).attr("data-val")] = $(this).html().trim();
        });

        list.push(obj);
    });
    return list;
}

function createTableFromClaimList(list) {
    // clear table by removing all rows except first, which is the header
    $("#claimTableContainer").find("tr:gt(0)").remove();

    for(var i = 0; i < list.length; i++) {
        var row = "<tr class='" + list[i].className + "'>";

        row += "<td class='number' data-val='number'>";
        row += list[i].number;
        row += "</td>";

        row += "<td data-val='type'>";
        row += list[i].type;
        row += "</td>";

        row += "<td data-val='country'>";
        row += list[i].country;
        row += "</td>";

        row += "<td data-val='date'>";
        row += list[i].date;
        row += "</td>";

        row += "<td data-val='id'>";
        row += list[i].id;
        row += "</td>";

        row += "<td data-val='files'>";
        row += list[i].files;
        row += "</td>";

        row += "<td data-val='options'>";
        row += list[i].options;
        row += "</td>";

        row += "</tr>";
        $("#claimTableContainer tbody:last").append(row);
    }
}

function sortClaimsWithSorter(trigger) {
    var sortDescending = $(trigger).hasClass("asc");
    // remove all sort classes from other selectors
    $(trigger).parents("table").find("a.sorter").each(function() {
        $(this).removeClass("desc");
        $(this).removeClass("asc");
    });

    sortClaims($(trigger).attr("data-val"), sortDescending);
    if(sortDescending) {
        $(trigger).addClass("desc");
    } else {
        $(trigger).addClass("asc");
    }
}

function sortClaims(property, descending) {
    var list = createClaimListFromTable();
    list.sort(Common.Sort.predicate(property, descending));
    createTableFromClaimList(list);
}

// sorting functionality trigger
$("#claimTableContainer table th a.sorter").live("click", function() {
    sortClaimsWithSorter($(this));
});
