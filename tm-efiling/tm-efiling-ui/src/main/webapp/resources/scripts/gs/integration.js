$("#modal-gs").live("show", function(event)
{
    if(!checkLanguage())
    {
        ajaxMessage("general.messages.goodsServices.selectLanguageFirst");
        event.preventDefault();
    }
})

function submitAddedTerms(termList) {
    $.ajax({
        url: "addTerms.htm",
        type: "POST",
        data: adaptTermsForSubmission(termList),
        beforeSend: function() {
            $(".gsLoading").html("<div class='loading'></div>");
            if (showLoadingScreen) {
                showLoadingScreen();
            }
        },
        complete: function() {
            if (hideLoadingScreen) {
                hideLoadingScreen();
            }
        },
        success: function(data) {

            $("#tableGoodsAndServices").refreshGS({ langId: getLanguageInfo().first, languageInfo: getLanguageInfo() });
        }
    })
}
function replaceTerm(termCLass, termDescription, termList) {
    $.ajax({
        url: "replaceEditedTerm.htm",
        type: "POST",
        data: adaptTermsForReplacement(termCLass, termDescription, termList),
        beforeSend: function() {
            $(".gsLoading").html("<div class='loading'></div>");
            if (showLoadingScreen) {
                showLoadingScreen();
            }
        },
        complete: function() {
            if (hideLoadingScreen) {
                hideLoadingScreen();
            }
        },
        success: function(data) {
            $("#tableGoodsAndServices").refreshGS({ langId: getLanguageInfo().first, languageInfo: getLanguageInfo() });
        }
    })
}

function adaptTermsForSubmission(termList)
{
    var data = [];
    var i = 0;
    for (var key in termList) {
        var term = termList[key];
        data[data.length] = encodeURIComponent("terms["+i+"].idClass") + "=" + encodeURIComponent( term.cat );
        data[data.length] = encodeURIComponent("terms["+i+"].description") + "=" + encodeURIComponent( term.term );
        if(_.isArray(term.relatedTerms)) {
            for(var j = 0; j<term.relatedTerms.length; j++) {
                data[data.length] = encodeURIComponent("terms["+i+"].relatedTerms["+j+"]") + "=" + encodeURIComponent( term.relatedTerms[j].name );
            }
        }
        i++;
    }
    return data.join( "&" );
}

function adaptTermsForReplacement(termCLass, termDescription, termList) {
    var term = encodeURIComponent("termClass") + "=" + encodeURIComponent( termCLass ) + "&" +
        encodeURIComponent("termDescription") + "=" + encodeURIComponent( termDescription );
    return term + "&" + adaptTermsForSubmission(termList);
}

$("a[href=#modal-gs]").live("click", function(e){
    checkLanguageAsync(function(){
        var node = $(e.currentTarget);
        var languageData = getLanguageInfo();
        var lang = node.data('lang');
        var langLabel = languageData.first;
        if(typeof lang !== undefined && languageData.second === lang) {
            langLabel = languageData.second;
        }
        setLabelSelectedLanguage(langLabel);
        $('#modal-gs').modal('show');
    },
    function(){
        ajaxMessage("general.messages.goodsServices.selectLanguageFirst");
        e.preventDefault();
    });
})

function setLabelSelectedLanguage(langLabel) {
    $("#gsmodal-first-lang").html(langLabel);
}

function getFirstLangValue()
{
    var storedFirstLang = $("#storedFirstLang").val();
	var firstLang = $("#firstLang").val();
	
	if (firstLang !== undefined) {
		return firstLang;
	} else {
		return storedFirstLang;
	}
}

function getSelectedTabLangValue()
{

    return $("section.classes").find("ul.language-tabs").find("li.active a").attr("href") || getFirstLangValue() ;
}

function getFirstLanguageText()
{
    return $.ajax({
            url: "getFirstLanguageText.htm"
    });
}

function getSecondLanguageText()
{
    return $.ajax({
        url: "getSecondLanguageText.htm"
    });
}

function isProUser()
{
    if($("body#wizard").size() > 0)
    {
        return true;
    }
    return false;
}
