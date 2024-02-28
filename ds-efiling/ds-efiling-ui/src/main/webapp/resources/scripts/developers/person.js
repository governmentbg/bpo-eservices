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