var Claim = {
    buildListFromTable: function(tableSelector)
    {
        var list = new Array();
        $(tableSelector + " tr.claimRow").each(function()
        {
            var obj = new Object();
            $(this).find("td").each(function()
            {
                obj[$(this).attr("data-val")] = $(this).html().trim();
            });
            obj["dataClass"] = $(this).attr('class');
            obj["dataCols"] = parseInt($(this).attr("data-cols"));
            list.push(obj);
        })
        return list;
    },
    getClaimCount: function(tableSelector)
    {
        return Person.numberOfTableRowsNoHeader(tableSelector);
    },
    buildTableFromList: function(tableSelector, list)
    {
        // clear table by removing all rows except first, which is the header
        $(tableSelector).find("tr:gt(0)").remove();

        for(var i = 0; i < list.length; i++)
        {
            var row = "<tr class='" + list[i].dataClass+"' data-cols='"+list[i].dataCols+"'>";

            if(list[i].dataCols == 4){
                row += "<td data-val='str'>";
                row += list[i].str;
                row += "</td>";
            }

            row += "<td data-val='number'>";
            row += list[i].number;
            row += "</td>";

            row += "<td data-val='date'>";
            row += list[i].date;
            row += "</td>";

            row += "<td data-val='options'>";
            row += list[i].options;
            row += "</td>";

            row += "</tr>";
            $(tableSelector + " tbody:last").append(row);
        }
    },
    sort: function(tableSelector, property, descending)
    {
        var list = Claim.buildListFromTable(tableSelector);
        list.sort(Common.Sort.predicate(property, descending));
        Claim.buildTableFromList(tableSelector, list);
    },
    sortClaimsWithSorter: function(trigger) {
        var sortDescending = $(trigger).hasClass("asc");
        var tableSelector = "#"+($(trigger).parents("table").attr("id"));
        // remove all sort classes from other selectors
        $(tableSelector).find("a.sorter").each(function()
        {
            $(this).removeClass("desc");
            $(this).removeClass("asc");
        })

        Claim.sort(tableSelector, $(trigger).attr("data-val"), sortDescending);
        if(sortDescending)
        {
            $(trigger).addClass("desc");
        }
        else
        {
            $(trigger).addClass("asc");
        }
    }
};

Claim.nav = {
};

Claim.nav.choose ={
    transformation: "transformationChoosetype.htm",
    parallelApplication: "parallelApplicationChoosetype.htm"
};

Claim.nav.add = {
    priority: "addPTPriority.htm",
    exhibition: "addPTExhibition.htm",
    divisionalApplication: "addPTDivisionalApplication.htm",
    pct: "addPct.htm",
    transformationNational: "addNationalTransformation.htm",
    transformationWO: "addWOTransformation.htm",
    transformationEM: "addConversion.htm",
    parallelApplicationNational: "addNationalParallelApplication.htm",
    parallelApplicationWO: "addWOParallelApplication.htm",
    parallelApplicationEM: "addEMParallelApplication.htm",

};
Claim.nav.remove = {
    priority:"removePTPriority.htm",
    exhibition:"removePTExhibition.htm",
    divisionalApplication:"removePTDivisionalApplication.htm",
    pct: "removePct.htm",
    transformation: "removeTransformation.htm",
    parallelApplication: "removeParallelApplication.htm",
}

Claim.prefix = {
    priority: "priority",
    divisionalApplication: "divisionalApplication",
    exhibition: "exhibition",
    pct: "pct",
    transformation: "transformation",
    parallelApplication: "parallelApplication",
};

Claim.ajax = {
    getClaimForm: function(id, nav, claimPrefix){
        $.ajax({
                url: nav,
                data:"id=" + id,
                type:"GET",
                success:function (html)
                {
                    showClaimTab(claimPrefix);
                    $("#"+claimPrefix+"Section").html(html);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#"+claimPrefix+"CardList", true);
                }
            }
        );
    },

    addClaimPost: function(nav, claimPrefix){
        var dataToSend = $("#"+claimPrefix+"Form").serialize();
        showLoadingScreen();
        $.ajax(
            {
                url:nav,
                type:"POST",
                data:dataToSend,
                success:function (html)
                {
                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true")
                    {
                        // then display the errors
                        $("#"+claimPrefix+"Section").html(html);
                        hideLoadingScreen();
                        return;
                    }

                    // otherwise, just refresh holder cards
                    refreshClaimCards(html, claimPrefix);

                    $(".cancelButton."+claimPrefix).click();

                    hideLoadingScreen();
                    getFeesInformation();
                },
                error:function (error)
                {
                    hideLoadingScreen();
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#"+claimPrefix+"Form", true);
                }
            });

    },

    removeClaimForm: function(id,nav, claimPrefix, imported){
        $.ajax(
            {
                url:nav,
                type:"POST",
                data:"id="+id,
                success:function (html)
                {
                    if(!imported) {
                        refreshClaimCards(html, claimPrefix);
                        $(".cancelButton." + claimPrefix).click();

                        hideLoadingScreen();
                        getFeesInformation();
                    } else {
                        submitMainForm("clearImportedFromEarlierApp", true);
                    }
                },
                error:function (error)
                {
                    hideLoadingScreen();
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#"+claimPrefix+"CardList", true);
                }
            });

    },
    chooseType: function(country, nav, claimPrefix){
        $.ajax({
                url: nav,
                data:"country=" + country,
                type:"GET",
                success:function (html)
                {
                    showClaimTab(claimPrefix);
                    $("#"+claimPrefix+"Section").html(html);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#"+claimPrefix+"CardList", true);
                }
            }
        );
    },
}


//PRIORITY
$('#priorityCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.priority);
});

$('#priorityTrigger').live("click", function(){
    var visible = toggleClaimAdd(Claim.prefix.priority);
    if(visible) {
        Claim.ajax.getClaimForm(null, Claim.nav.add.priority, Claim.prefix.priority);
    }
});

$('.editPriority').live("click", function(){
    $("#priorityTrigger").addClass("active");
    showClaimTab(Claim.prefix.priority);
    Claim.ajax.getClaimForm($(this).attr("data-id"), Claim.nav.add.priority, Claim.prefix.priority);
});

$(".removePriority").live("click", function () {
    var id = $(this).attr("data-id");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.priority, Claim.prefix.priority, false);
    showConfirmModal($("#priority_remove").html(), onConfirmYes);
});

$('.addPriorityButton').live("click", function(){
    Claim.ajax.addClaimPost(Claim.nav.add.priority, Claim.prefix.priority);
});

$(".cancelButton.priority").live("click", function ()
{
    $("#prioritySection").html("");
    toggleClaimAdd(Claim.prefix.priority);
    scrollToClaimsTop(Claim.prefix.priority);
});


//EXHIBITION
$('#exhibitionCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.exhibition);
});

$('#exhibitionTrigger').live("click", function(){
    var visible = toggleClaimAdd(Claim.prefix.exhibition);
    if(visible) {
        Claim.ajax.getClaimForm(null, Claim.nav.add.exhibition, Claim.prefix.exhibition);
    }

});

$('.editExhibition').live("click", function(){
    showClaimTab(Claim.prefix.exhibition);
    Claim.ajax.getClaimForm($(this).attr("data-id"), Claim.nav.add.exhibition, Claim.prefix.exhibition);
});

$(".removeExhibition").live("click", function () {
    var id = $(this).attr("data-id");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.exhibition, Claim.prefix.exhibition, false);
    showConfirmModal($("#exhibition_remove").html(), onConfirmYes);
});

$('.addExhibitionButton').live("click", function(){
    Claim.ajax.addClaimPost(Claim.nav.add.exhibition, Claim.prefix.exhibition);
});

$(".cancelButton.exhibition").live("click", function ()
{
    $("#exhibitionSection").html("");
    toggleClaimAdd(Claim.prefix.exhibition);
    scrollToClaimsTop(Claim.prefix.exhibition);
});

//DIVISIONAL APPLICATION
$('#divisionalApplicationCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.divisionalApplication);
});


$('#divisionalApplicationTrigger').live("click", function(){
    $("#divisionalCurrentNumber").html(Claim.getClaimCount("#divisionalApplicationTable") + 1);
    $("#createDivisionalOptionsDiv").show();
    toggleClaimAdd(Claim.prefix.divisionalApplication);
});

$("#createManualDivisional").live("click", function (){
    $("#createDivisionalOptionsDiv").hide();
    Claim.ajax.getClaimForm(null, Claim.nav.add.divisionalApplication, Claim.prefix.divisionalApplication);
});

function loadDivisionalApplicationManual(){
    $("#createDivisionalOptionsDiv").hide();
    Claim.ajax.getClaimForm(null, Claim.nav.add.divisionalApplication, Claim.prefix.divisionalApplication);
}

$('.editDivisionalApplication').live("click", function(){
    showClaimTab(Claim.prefix.divisionalApplication);
    Claim.ajax.getClaimForm($(this).attr("data-id"), Claim.nav.add.divisionalApplication, Claim.prefix.divisionalApplication);
});

$(".removeDivisionalApplication").live("click", function () {
    var id = $(this).attr("data-id");
    var imported = $(this).attr("data-imported");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.divisionalApplication, Claim.prefix.divisionalApplication, imported =='true');
    showConfirmModal($("#divisionalApplication_remove").html(), onConfirmYes);
});

$('.addDivisionalApplicationButton').live("click", function(){
    Claim.ajax.addClaimPost(Claim.nav.add.divisionalApplication, Claim.prefix.divisionalApplication);
});

$(".cancelButton.divisionalApplication").live("click", function ()
{
    $("#divisionalApplicationSection").html("");
    toggleClaimAdd(Claim.prefix.divisionalApplication);
    scrollToClaimsTop(Claim.prefix.divisionalApplication);
});

//PCT
$('#pctCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.pct);
});

$('#pctTrigger').live("click", function(){
    var visible = toggleClaimAdd(Claim.prefix.pct);
    if(visible){
        Claim.ajax.getClaimForm(null, Claim.nav.add.pct, Claim.prefix.pct);
    }

});

$('.editPct').live("click", function(){
    showClaimTab(Claim.prefix.pct);
    Claim.ajax.getClaimForm($(this).attr("data-id"), Claim.nav.add.pct, Claim.prefix.pct);
});

$(".removePct").live("click", function () {
    var id = $(this).attr("data-id");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.pct, Claim.prefix.pct, false);
    showConfirmModal($("#pct_remove").html(), onConfirmYes);
});

$('.addPctButton').live("click", function(){
    Claim.ajax.addClaimPost(Claim.nav.add.pct, Claim.prefix.pct);
});

$(".cancelButton.pct").live("click", function ()
{
    $("#pctSection").html("");
    toggleClaimAdd(Claim.prefix.pct);
    scrollToClaimsTop(Claim.prefix.pct);
});


//TRANSFORMATION
$('#transformationCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.transformation);
});


$('#transformationTrigger').live("click", function(){
    $("#transformationCurrentNumber").html(Claim.getClaimCount("#transformationTable") + 1);
    $("#transformationTypeDiv").show();
    $("#transformationType").val("");
    toggleClaimAdd(Claim.prefix.transformation);
});

$('#transformationType').live("change", function(){
    if($(this).val() == ""){
        return;
    }
    Claim.ajax.chooseType($(this).val(), Claim.nav.choose.transformation, Claim.prefix.transformation);
});

function resolveTransformationNav(country){
    if(country == $("#wipo_office").val()){
        return Claim.nav.add.transformationWO;
    } else if(country == $("#euipo_office").val()){
        return Claim.nav.add.transformationEM;
    } else {
        return Claim.nav.add.transformationNational;
    }
}

$("#createManualTransformation").live("click", function (){
    $("#transformationTypeDiv").hide();
    var country = $("#transformationType").val();
    var nav = resolveTransformationNav(country);

    Claim.ajax.getClaimForm(null, nav, Claim.prefix.transformation);
});

$('.editTransformation').live("click", function(){
    showClaimTab(Claim.prefix.transformation);
    Claim.ajax.getClaimForm($(this).attr("data-id"), resolveTransformationNav($(this).attr("data-country")), Claim.prefix.transformation);
});

$(".removeTransformation").live("click", function () {
    var id = $(this).attr("data-id");
    var imported = $(this).attr("data-imported");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.transformation, Claim.prefix.transformation, imported =='true');
    showConfirmModal($("#transformation_remove").html(), onConfirmYes);
});

$('.addTransformationButton').live("click", function(){
    var nav = resolveTransformationNav($("#transformationCountry").val());
    Claim.ajax.addClaimPost(nav, Claim.prefix.transformation);
});

$(".cancelButton.transformation").live("click", function ()
{
    $("#transformationSection").html("");
    toggleClaimAdd(Claim.prefix.transformation);
    scrollToClaimsTop(Claim.prefix.transformation);
});

function callTransformationFormImmediately(country){
    $("#transformationTypeDiv").hide();
    var nav = resolveTransformationNav(country);

    Claim.ajax.getClaimForm(null, nav, Claim.prefix.transformation);
}

//PARALLEL APPLICATION
$('#parallelApplicationCheckbox').live("click", function(){
    toggleClaimWrapperDiv(Claim.prefix.parallelApplication);
});


$('#parallelApplicationTrigger').live("click", function(){
    $("#parallelApplicationCurrentNumber").html(Claim.getClaimCount("#parallelApplicationTable") + 1);
    $("#parallelApplicationTypeDiv").show();
    $("#parallelApplicationType").val("");
    toggleClaimAdd(Claim.prefix.parallelApplication);
});

$('#parallelApplicationType').live("change", function(){
    if($(this).val() == ""){
        return;
    }
    Claim.ajax.chooseType($(this).val(), Claim.nav.choose.parallelApplication, Claim.prefix.parallelApplication);
});

function resolveParallelApplicationNav(country){
    if(country == $("#wipo_office").val()){
        return Claim.nav.add.parallelApplicationWO;
    } else if(country == $("#euipo_office").val()){
        return Claim.nav.add.parallelApplicationEM;
    } else {
        return Claim.nav.add.parallelApplicationNational;
    }
}

$("#createManualParallelApplication").live("click", function (){
    $("#parallelApplicationTypeDiv").hide();
    var country = $("#parallelApplicationType").val();
    var nav = resolveParallelApplicationNav(country);

    Claim.ajax.getClaimForm(null, nav, Claim.prefix.parallelApplication);
});

$('.editParallelApplication').live("click", function(){
    showClaimTab(Claim.prefix.parallelApplication);
    Claim.ajax.getClaimForm($(this).attr("data-id"), resolveParallelApplicationNav($(this).attr("data-country")), Claim.prefix.parallelApplication);
});

$(".removeParallelApplication").live("click", function () {
    var id = $(this).attr("data-id");
    var imported = $(this).attr("data-imported");
    var onConfirmYes = partial(Claim.ajax.removeClaimForm, id, Claim.nav.remove.parallelApplication, Claim.prefix.parallelApplication, imported =='true');
    showConfirmModal($("#parallelApplication_remove").html(), onConfirmYes);
});

$('.addParallelApplicationButton').live("click", function(){
    var nav = resolveParallelApplicationNav($("#parallelApplicationCountry").val());
    Claim.ajax.addClaimPost(nav, Claim.prefix.parallelApplication);
});

$(".cancelButton.parallelApplication").live("click", function ()
{
    $("#parallelApplicationSection").html("");
    toggleClaimAdd(Claim.prefix.parallelApplication);
    scrollToClaimsTop(Claim.prefix.parallelApplication);
});

function callParallelApplicationFormImmediately(country){
    $("#parallelApplicationTypeDiv").hide();
    var nav = resolveParallelApplicationNav(country);

    Claim.ajax.getClaimForm(null, nav, Claim.prefix.parallelApplication);
}

//CLAIMS COMMON

$("table.claim-table th a.sorter").live("click", function()
{
    Claim.sortClaimsWithSorter($(this));
})

function toggleClaimWrapperDiv(claimPrefix){
    if($("#"+claimPrefix+"Checkbox").is(":checked")){
        $("#"+claimPrefix+"WrapperDiv").show();
    } else {
        $("#"+claimPrefix+"WrapperDiv").hide();
    }

    enableDisableCheckboxes();
}

function toggleClaimAdd(claimPrefix)
{
    if($("#tab"+claimPrefix).is(":hidden"))
    {
        showClaimTab(claimPrefix);
        return true;
    }
    else
    {
        hideClaimTab(claimPrefix);
        return false;
    }
}

function showClaimTab(claimPrefix){
    $("#tab"+claimPrefix).show();
    $("#"+claimPrefix+"Trigger").addClass("active");
}


function hideClaimTab(claimPrefix) {
    $("#tab"+claimPrefix).hide();
    $("#"+claimPrefix+"Trigger").removeClass("active");
    $("#"+claimPrefix+"Section").html("");
}


function refreshClaimCards(html, claimPrefix)
{
    $("#"+claimPrefix+"CardList").html(html);
    scrollToClaimsTop(claimPrefix);
    checkMaxClaims(claimPrefix);
    toggleDisableSelectCheckbox(claimPrefix);
}


function scrollToClaimsTop(claimPrefix){
    scrollToContainer("#"+claimPrefix+"CardList");
}

function checkSelectCheckbox(claimPrefix){
    var rowCount = $("#"+claimPrefix+"CardList"+" tr").length-1;
    if(rowCount>0){
        $("#"+claimPrefix+"Checkbox").attr('checked', 'checked');
    } else {
        $("#"+claimPrefix+"Checkbox").removeAttr("checked");
    }
    toggleClaimWrapperDiv(claimPrefix);
}

function toggleDisableSelectCheckbox(claimPrefix){
    var rowCount =$("#"+claimPrefix+"CardList"+" tr").length-1;
    if(rowCount>0){
        $("#"+claimPrefix+"Checkbox").attr('disabled', 'disabled');
    } else {
        $("#"+claimPrefix+"Checkbox").removeAttr("disabled");
    }

    enableDisableCheckboxes();

}

function checkMaxClaims(claimPrefix){
    var maxPriorities=$('#max'+claimPrefix).val();
    var rowCount = $('#'+claimPrefix+'CardList tr').length-1;

    if (rowCount >= maxPriorities) {
        $("#"+claimPrefix+"Trigger").hide();
    } else {
        $("#"+claimPrefix+"Trigger").show();
    }
}

function initializeClaimSection(claimPrefix) {
    checkMaxClaims(Claim.prefix[claimPrefix]);
    checkSelectCheckbox(Claim.prefix[claimPrefix]);
    toggleDisableSelectCheckbox(Claim.prefix[claimPrefix]);
}


function enableDisableCheckboxes(){
    var checked = $(".claim-checkbox.single-selectable:checked");
    var checkedId = $(checked[0]).attr("id");
    if(checked.length>0){
        $(".claim-checkbox.single-selectable").each(function (ind, el){
            var elId = $(el).attr("id");
            if(elId != checkedId){
               $(el).attr("disabled", "disabled");
            }
        });
    } else {
        $(".claim-checkbox.single-selectable").each(function (ind, el){
            $(el).removeAttr("disabled");
        });
    }
}