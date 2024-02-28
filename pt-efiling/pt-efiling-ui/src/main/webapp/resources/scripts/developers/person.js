var Person =
{
    fillStatesSelectbox:function (countryCode, selectBox, selectedState)
    {
        if (countryCode == null || countryCode == "")
        {
            $(selectBox).parent().hide();
            return;
        }
        $.getJSON("getStates.htm?country=" + countryCode,
            function (json)
            {
                if (json.length == 0)
                {
                    $(selectBox).val("");
                    $(selectBox).parent().hide();
                    return;
                }
                var options = [];
                for (var i = 0; i < json.length; i++)
                {
                    if(json[i].code ==''){
                        options.push("<option value=''>" + json[i].value + "</option>");
                    } else {
                        options.push("<option value='" + json[i].value + "'>" + json[i].value + "</option>");
                    }
                }
                var optionsStr = options.join('');
                $(selectBox).html(optionsStr);

                // place correct "Select" text in option without value
                $(selectBox + " option[value='']").html($("#selectText").html());
                $(selectBox).parent().show();
                if(selectedState != null && selectedState != "")
                {
                    $(selectBox + " option[value='" + selectedState + "']").attr("selected", "selected");
                }
            });
    },
    handleCorrespondenceAddressStates:function (triggeringObject, section)
    {
        var stateSelect = section + " select[name='" + $(triggeringObject).attr("name").replace("country", "stateprovince") + "']";
        var savedState = $(section + " select[name='" + $(triggeringObject).attr("name").replace("country", "selectedstate") + "']").val();

        Person.fillStatesSelectbox(
            $(triggeringObject).val(), stateSelect, savedState);
    },
    containsImportError: function(html)
    {
        if($(".importError", $(html)).size() > 0)
        {
            return true;
        }
        return false;
    },
    numberOfTableRowsNoHeader: function(tableSelector)
    {
        var tableSize = $(tableSelector + " tr").size();
        if(tableSize != 0)
        {
            tableSize = tableSize - 1;
        }
        return tableSize;
    },
    sortTable: function(personType, trigger)
    {
        var sortDescending = $(trigger).hasClass("asc");
        // remove all sort classes from other selectors
        $(trigger).parents("table").find("a.sorter").each(function()
        {
            $(this).removeClass("desc");
            $(this).removeClass("asc");
        })

        window[personType].sort("#" + $(trigger).parents("table").attr("id"),
            $(trigger).attr("data-val"), sortDescending);
        if(sortDescending)
        {
            $(trigger).addClass("desc");
        }
        else
        {
            $(trigger).addClass("asc");
        }
    },
    showNaturalPersonTipForBG: function(selector, country){
        $(selector).each(function () {
            if(country == "BG") {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }
}

Person.nav = {
    getSelectList: "getSelectablePersonsList.htm",
    saveSelectionList: "addPersonListSelection.htm"
};

Person.ajax = {
    getSelectList: function(sectionId, caller){
        showLoadingScreen();
        $.ajax({
            url:Person.nav.getSelectList,
            data:"sectionId="+sectionId,
            success:function (html)
            {
                if($(".flMessageError", $(html)).length >0){
                    genericHandleError($("#generic_errors_service_fail").html(),
                        caller.siblings(".updatableParent").val(), true);
                } else {
                    $("#personSelectDiv").html(html);
                    $("#personSelectionList").modal("show");
                }
                hideLoadingScreen();
                return;
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#generic_errors_service_fail").html(),
                    caller.siblings(".updatableParent").val(), true);
            }
        });
    },
    saveSelectionList: function (sectionId, data){
        if(data.length < 1){
            $("#personSelectionList").modal("hide");
            return;
        }
        showLoadingScreen();
        $.ajax({
            url:Person.nav.saveSelectionList+"?sectionId="+sectionId,
            method: "post",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success:function (html)
            {
                if($(".flMessageError", $(html)).length >0){
                    genericHandleError($("#generic_errors_service_fail").html(),
                        "#personSelectionList", true);
                } else {
                    $("#personSelectionList").modal("hide");
                    var updatable = $("input.updatableParent", $(html)).val();
                    $(updatable).html(html);
                }
                hideLoadingScreen();
            },
            error:function (error)
            {
                hideLoadingScreen();
                genericHandleError($("#generic_errors_service_fail").html(),
                    "#personSelectionList", true);
            }
        });
    }
};

$(".personSelectButton").live("click", function(e){
    Person.ajax.getSelectList($(this).attr("data-section"), $(this));
});

$("#savePersonChoiceBtn").live("click", function(e){
    var jsonArr= [];
    $("#personSelectionList .personSelectItem:checked").each(function (i, element){
        jsonArr.push({id: $(element).attr('data-id'), field: $(element).attr('data-field')});
    });
    Person.ajax.saveSelectionList($(this).attr('data-section'), jsonArr);
});