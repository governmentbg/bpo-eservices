// add navigation functions to buttons
$(window).load(function ()
{
    $(".navigateBtn").click(function ()
    {
        submitMainForm($(this).attr("data-val"), true, $(this).attr("href"));
        return false;
    });
    
    $('.receipt-new-window').bind("click", function() {
    	getReceipt();
    });
    if(disableNoButtons) {
        disableNoButtons()
    };
    showHideNotes();
    nationalSearchReportInitialization();
    if($(".minorType.active").size() > 0)
    {
        $("button.see-more-button").trigger("click");
    }
    /*
     Clear autocompleted fields on trigger click
     */
    $('*').filter(function() {
        return this.id.match(/.*Trigger/);
    }).each(function() {
        $(this).live('click', function() {
            $('.autocompleted').each(function() { $(this).val(''); });
        })
    });

    $('[data-toggle="tooltip"]').tooltip();

    adjustMainContainerMinHeight();
});

function adjustMainContainerMinHeight() {
    var divmain = $('body > div.main');
    var aside = $('body > div.main > aside');
    if (divmain && aside) {
        divmain.css('min-height', aside.css('height'));
    }
}

$('#fee-item').live('shown.bs.collapse', function () {
    adjustMainContainerMinHeight();
});
$('#fee-item').live('hidden.bs.collapse', function () {
    adjustMainContainerMinHeight();
});

// added a global flag to blur and autocomplete.
// When I enter my autocomplete input field's blur handling function,
// I have a if(!autocompleteIEHack) {//do blur stuff here}.
// In autocomplete 'focus', I set autocompleteIEHack=true;
// In autocomplete 'close', I set autocompleteIEHack=false and explicitly call blur() on my autocomplete field
var autocompleteIEHack = false;


// Shows/hides notes depending of the type of form. If it's one form, notes are hidden, otherwise, shown.
function showHideNotes() {
	if($(".body").attr("id")=="normal") {
		$(".note visible").hide();
	} else {
		$(".note visible").show();
	}
	
}

function appendHiddens(param) {
    var hiddenfield = document.createElement("input");
    $(hiddenfield).attr("type", "hidden");
    $(hiddenfield).attr("name", param.name);
    $(hiddenfield).attr("id", param.name);
    if (param.type == 'checkbox')
    {
        if (param.checked)
            $(hiddenfield).val('true');
        else
            $(hiddenfield).val('false');
    } else if (param.type == 'radio')
    {
        if (param.checked)
            $(hiddenfield).val(param.value);
    } else
        $(hiddenfield).val(param.value);

    $("#hiddenForm").append(hiddenfield);
}

function overwriteHiddens(param) {
    if (param.type == 'checkbox')
    {
        if (param.checked)
            document.hiddenForm.elements[param.name].value = 'true';
        else
            document.hiddenForm.elements[param.name].value = 'false';
    } else if (param.type == 'radio')
    {
        if (param.checked)
            document.hiddenForm.elements[param.name].value = param.value;
    } else {
        document.hiddenForm.elements[param.name].value = param.value;
    }
}

//Submits the hidden form and collects all inputs that are having the class "mainForm"
function submitMainForm(value, showLoading, gotosection) {
    if(value!='Refresh' && anyFormsOpen())
    {
        showMessageModal(createFormsOpenText());
        return false;
    }


    $("#finalSubmitContainer :input").attr("disabled", "disabled");

    $("#hiddenElement").val(value);

    prepareHiddenForm();

    if(showLoading)
    {
        showLoadingScreen();
    }
    if (gotosection)
    {
        $("#hiddenForm").attr('action',$("#hiddenForm").attr('action')+gotosection);
    }
    $("#hiddenForm").submit();
}

function prepareHiddenForm() {
    var params = $(".mainForm :input").filter(":not(.submittedAjax)");

    if (document.hiddenForm == undefined) {
        document.hiddenForm = document.getElementById("hiddenForm");
    }

    for (var i = 0; i < params.length; i++) {
        if (document.hiddenForm.elements[params[i].name] == null && params[i].name != "") {
            appendHiddens(params[i]);
        } else if (document.hiddenForm.elements[params[i].name] != null && params[i].name != "") {
            overwriteHiddens(params[i]);
        }
    }
}

function anyFormsOpen()
{
    return isFormOpen("#tabApplicant")
           || isFormOpen("#tabRepresentative")
           || isFormOpen(".claimFields")
}

function createFormsOpenText()
{
    var text = "";
    if(isFormOpen("#applicantSection"))
    {
        text += $("#openApplicantFormMessage").html();
    }
    if(isFormOpen("#representativeSection"))
    {
        text += $("#openRepresentativeFormMessage").html();
    }
    if(isFormOpen(".claimFields"))
    {
        text += $("#openClaimsFormMessage").html();
    }
    return text;
}

$(".messageScroll").live("click", function()
{
    scrollToContainer($(this).attr("data-scroll"));
})

function isFormOpen(sectionSelector)
{
    return $(sectionSelector).is(":visible");
}

function scrollToContainer(containerSelector)
{
    var scrollToContainer = $(containerSelector);
    $('html, body').animate({
        scrollTop:scrollToContainer.offset().top
    }, 'slow');
}

$(document).on('focus', 'input[class*="-date"]', function(){
	$(this).datepicker(
			{format: $("#dateFormat").html(),
                language: $('#language-selector > div > button').attr('value')
			}
	).on('changeDate', function() {
		var $this = $(this);
		$this.blur();
		$this.datepicker('hide');
	});
});

$('.autocompleted').live('blur', function(){
    if ($(this).autocomplete( "widget" ).find(".ui-state-hover").length==0
        && $(this).autocomplete( "widget" ).find(".navigation-col:hover").length==0) {
        $(this).autocomplete("destroy");
    }
});

$('.autocompleted').live("paste", function (){
    $(this).autocomplete("search", $(this).val());
});

function initAutocomplete(element, customBuiltUrl = undefined){
    if($(element).hasClass("autocompleted")) {
        var urlJson = customBuiltUrl ? customBuiltUrl : $(element).attr('data-url');
        var minChars = $(element).attr('data-minchars');
        if (!minChars) {
            minChars = 3;
        }
        $(element).autocomplete({
            minLength: minChars,
            source: function (request, response) {
                $.getJSON(urlJson, {
                    args: extractLast(request.term),
                }, function (data) {
                    if (data == null || data.length == 0) {
                        data = [{
                            NoResultsFound: true
                        }];
                    }
                    response(data);
                });
            },
            search: function () {
                $('#spinner').show();
            },
            response: function () {
                $('#spinner').hide();
            },
            // The following lines make the autocomplete work properly in IE9
            // Initial issue: when selecting value with mouse, autocompletechange event would appear not to trigger
            // Reason: possibly because blur function was called at a wrong time
            // Coupled with: 100ms delay on autocompleteselect desired functionality
            // Why this works: unknown
            blur: function () {
                if (!autocompleteIEHack) {
                    return;
                }
            },
            focus: function () {
                autocompleteIEHack = true;
            },
            close: function () {
                autocompleteIEHack = false;
            }
        });
    }
};

$(":input").live("blur", function (event)
{
    //Remove the error message if it exists
    var errorMessageId = event.target.id + 'ErrorMessageServer';
    errorMessageId = errorMessageId.replace(/\./g, '\\.');
    if ($('#' + errorMessageId).length > 0)
    {
        $('#' + errorMessageId).remove();
        //Remove the error class
        if (event.target.type == 'checkbox')
        {
            event.target.className = event.target.className.replace('errorValCheckbox', '');
        } else
        {
            event.target.className = event.target.className.replace('error', '');
        }
    }
});

$(".bgAdd").live("click", function (event)
{
    //Remove the error message if it exists
    var errorMessageId = event.target.id + 'ErrorMessageServer';
    if ($('#' + errorMessageId).length > 0)
    {
        $('#' + errorMessageId).remove();
        //Remove the error class
        if (event.target.type == 'checkbox')
        {
            event.target.className = event.target.className.replace('errorValCheckbox', '');
        } else
        {
            event.target.className = event.target.className.replace('error', '');
        }
    }
});

function genericHandleError(errorMessage, containerSelector, scrollToTopOfContainer)
{
    removePreviousErrors(containerSelector);
    $(containerSelector).prepend(wrapErrorNicely(errorMessage));
    if(scrollToTopOfContainer)
    {
        scrollToContainer(containerSelector);
    }

}

function wrapErrorNicely(errorMessage)
{
    return "<p class='flMessageError'><span>" + errorMessage + "</span></p>";
}
function removePreviousErrors(containerSelector)
{
    $(containerSelector + " p.flMessageError").remove();
}


function clearTabsDevelopers(button)
{
    var tab = $(button).closest('.tab-content').find('>div:visible');
    if (tab.length == 0)
    {
        tab = $(this).closest('.tab-content').find('>div:visible');
    }
    tab.removeClass('active');
}

function fspLog(message)
{
	if (window.console) {
        console.log(message);
    }
}

function showMessageModal(messageInnerHtml)
{
    $("#messageModal #messagePlaceholder").html(messageInnerHtml);
    $("#messageModal").modal("show");
}


$(".loginLink").live("click", function(){
    if ($("#hiddenForm")!=null && $("#hiddenForm").length>0) {
		submitMainForm("GlobalLogin");
		return false;
    } else {
    	$("#GlobalLoginButton").click();
    }
    return false;
});


$(".dropdown-menu li").live("click", function(){
	$("#language-selector .btn-primary:first").val($(this).find("a").attr("href"));
	var originalLanguageSet = $("#language-selector .btn-primary:first").html();
	$("#language-selector .btn-primary:first").html($(this).find("a").html());

    if ($("#hiddenForm")!=null && $("#hiddenForm").length>0) {
        $("#languageInput").val($(this).find("a").attr("href"));
        if (!submitMainForm("ChangeLanguage")) {
        	$("#language-selector .btn-primary:first").html(originalLanguageSet);
        }
    } else {
    	$("#headerLanguageInput").val($(this).find("a").attr("href"));
    	$("#ChangeLanguageButton").click();
    }

	return false;
});

function showWarningModal(warningInnerHtml)
{
    $("#warningModal #warningPlaceholder").html(warningInnerHtml);
    $("#warningModal").modal("show");
}

function showModal(warningInnerHtml, onClose)
{
    $("#warningModal #warningPlaceholder").html(warningInnerHtml);
    $("#warningModal").modal("show").on('hidden.bs.modal', onClose);
}

function ajaxMessage(code) {
	$.ajax({
		url: "message.htm",
		data:"code="+code,
		cache: true,
		success: function(html){
			showWarningModal(html);
		}
	});

}

function showConfirmModal(messageInnerHtml, onYes, onNo)
{
    $("#confirmModal #confirmPlaceholder").html(messageInnerHtml);
    $("#confirmModal").modal("show");

    $("#confirmModal #confirmModalOk").unbind("click");
    $("#confirmModal #confirmModalCancel").unbind("click");

    // When clicking on YES (or OK), make sure the function for the "hidden" event
    // is not called
    $("#confirmModal #confirmModalOk").bind("click", function()
    {
        $("#confirmModal").unbind("hidden");
        onYes();
    });

    // When clicking on NO (or Cancel), make sure the function for the "hidden" event
    // is not called
    $("#confirmModal #confirmModalCancel").bind("click", function()
    {
        $("#confirmModal").unbind("hidden");
        onNo();
    });

    // If YES or NO are not clicked and the modal still closes, execute the onNo function
    $("#confirmModal").unbind("hidden");
    $("#confirmModal").bind("hidden", onNo);
}

function showConfirmModalYesNo(messageInnerHtml, onYes, onNo)
{
    $("#confirmModalYesNo #confirmYesNoPlaceholder").html(messageInnerHtml);
    $("#confirmModalYesNo").modal("show");

    $("#confirmModalYesNo #confirmModalYes").unbind("click");
    $("#confirmModalYesNo #confirmModalNo").unbind("click");

    // When clicking on YES (or OK), make sure the function for the "hidden" event
    // is not called
    $("#confirmModalYesNo #confirmModalYes").bind("click", function()
    {
        $("#confirmModalYesNo").unbind("hidden");
        onYes();
    });

    // When clicking on NO (or Cancel), make sure the function for the "hidden" event
    // is not called
    $("#confirmModalYesNo #confirmModalNo").bind("click", function()
    {
        $("#confirmModalYesNo").unbind("hidden");
        onNo();
    });

    // If YES or NO are not clicked and the modal still closes, execute the onNo function
    $("#confirmModalYesNo").unbind("hidden");
    $("#confirmModalYesNo").bind("hidden", onNo);
}

function getReceipt() {
	$.ajax({
		url: "receipt.htm",		
		cache: false,
		success: function(html){
			var win = window.open();
			var doc = win.document.open("application/pdf");
            doc.write(html);
		},
		error: function () {
			//to be determined.
		}
});				
}


function removeAllHighlightRow() {
	$('.priority').removeClass('active');
	$('.seniority').removeClass('active');
	$('.exhpriority').removeClass('active');
	$('.transformation').removeClass('active');
	$('.conversion').removeClass('active');
}

function removeHighlightRow(row) {
	if(row.length > 0) {
		$('.' + row.attr('class').replace(' ','.')).removeClass('active');
	}
}

function highlightRow(row) {
	if($('body').attr('id') == 'normal') {
		removeAllHighlightRow();
	} else {
		removeHighlightRow(row);	
	}		
	row.addClass('active');
}


function partial(func /*, 0..n args */) {
    var args = Array.prototype.slice.call(arguments, 1);
    return function() {
        var allArguments = args.concat(Array.prototype.slice.call(arguments));
        return func.apply(this, allArguments);
    };
}

$(".autocompletedMultiple").live('keyup.autocomplete', function ()
{
    var urlJson = $(this).attr('data-url');
    var minChars = $(this).attr('data-minchars')
    if(!minChars) {
        minChars = 3;
    }
    $(this).autocomplete({
        minLength:minChars,
        source:function (request, response)
        {
            $.getJSON(urlJson, {
                root:extractLast(request.term)
            }, response)
        },
        focus:function ()
        {
            // prevent value inserted on focus
            return false;
        },
        select:function (event, ui)
        {
            var terms = split(this.value);
            // remove the current input
            terms.pop();
            // add the selected item
            terms.push(ui.item.value);
            // add placeholder to get the comma-and-space at the end
            terms.push("");
            this.value = terms.join("; ");
            return false;
        }

    })
})

/**
 * Makes each element with the class navigation-col open a page
 * with the url specified in its data-url attribute
 */
$(".navigation-col").live("click", function()
{
    var url = $(this).attr("data-url");
    window.open(url, "_blank");
    $(".ui-autocomplete-input").focus();
})

$(".resetButton").live("click", function()
{
    var url = $(this).attr("data-url");
    showConfirmModal($("#resetApplicationConfirmation").html(), function ()
    {
        location.href = url;
    });
})

function parseLinkUsingKeyValuePairs(keyValueArray, link)
{
    if(link == null) {
        return;
    }
    for(var i = 0; i < keyValueArray.length; i++)
    {
        if(!Object.prototype.toString.call(keyValueArray[i]) === "[object Array]")
        {
        	fspLog("invalid input parameter: keyValueArray");
            return link;
        }
        var parameter = keyValueArray[i][0];
        var value = keyValueArray[i][1];
        link = link.replace("{" + parameter + "}", value);
    }
    return link;
}

function htmlEncode(value){
    return $('<div/>').text(value).html();
}

function htmlDecode(value){
    return $('<div/>').html(value).text();
}

function showLoadingScreen()
{
    $("#ajaxBlock").show();
}

function hideLoadingScreen()
{
    $("#ajaxBlock").hide();
}

var Common = {}

Common.Sort =
{
    predicate: function (property, descending)
    {
        var multiplier = 1;
        if(descending)
        {
            multiplier = -1;
        }
        return function(a,b)
        {
            if( a[property] > b[property])
            {
                return 1 * multiplier;
            }
            else if(a[property] < b[property] )
            {
                return -1 * multiplier;
            }
            return 0;
        }
    }
}

// IE8 trim fix (see http://stackoverflow.com/questions/2308134/trim-in-javascript-not-working-in-ie )
if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, '');
    }
}

// IE8 indexOf fix (see http://stackoverflow.com/questions/3629183/why-doesnt-indexof-work-on-an-array-ie8 )
if (!Array.prototype.indexOf)
{
    Array.prototype.indexOf = function(elt /*, from*/)
    {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++)
        {
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}



function isOldVersion(){
    if (navigator.appName=="Microsoft Internet Explorer"){
 	   if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)){ //test for MSIE x.x;
 		   var ieversion=new Number(RegExp.$1) // capture x.x portion and store as a number
 		   if (ieversion<8){
 	    	 return true;
 			}
 	   }
	 }  
    else  if (navigator.appName=="Netscape" && /Firefox\/(\d+\.\d+);/.test(navigator.userAgent)){ //test for Mozilla x.x;
        var version=new Number(RegExp.$1)
        if (version<3.6){
            return true;
        }
    }
    return false;
}

function checkBrowserVersionWizard(){
	var isOld=isOldVersion()
	
	if(isOld){
		$('#wizard_correct_browser_version').remove();
		$('#wizard_message_error_browser').show();

		
	}
	else{
		$('#wizard_message_error_browser').remove();
		$('#wizard_correct_browser_version').show();
	}
}

function getI18nMessage(message) {
    if (message) {
        return i18nApplicationMessages[message];
    } else {
        return '';
    }
}

function getI18nMessageWithArgs(message, args, translateArgs) {
    if (args && args.length > 0) {
        var i18nMessage = getI18nMessage(message);
        for (var i=0; i<args.length; i++) {
            var argsi = args[i];
            if (translateArgs) {
                argsi = getI18nMessage(argsi);
            }
            i18nMessage = i18nMessage.replace('{'+i+'}', argsi);
        }
        return i18nMessage;
    } else {
        return getI18nMessage(message);
    }
}

$(document).on("click", ".logoutLink", function(e) {
    e.preventDefault();
    var url = $(this).attr("href");
    showConfirmModal($("#logoutApplicationConfirmation").html(), function() {
        location.href = url;
    }, function() {});
});


(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);

$("textarea").live("focusout", function(){
    if($(this).attr("maxlength") == undefined || $(this).attr("maxlength") == ""){
        return;
    } else {
        var len = parseInt($(this).attr("maxlength"));
        if($(this).val().length > len){
            showMessageModal($("#maxLengthExceeded"));
            var newVal = $(this).val().substring(0, len);
            $(this).val(newVal);
        }
    }
});

$("textarea, input:not(.uploadInput,.uploadLoadApplication)").live("focusout", function(){
    var match = XRegExp.test($(this).val(), XRegExp("^[\\p{Symbol}\\p{Punctuation}\\p{Letter}\\p{Number}();,'\\.\\-\\/+\=@_ \\[\\]\\r\\n\\t'\"\\[\\]$%&\#!¡\:]*$"));
    if(!match) {
        showMessageModal($("#badCharacters_possibleLoss"));
    }
});