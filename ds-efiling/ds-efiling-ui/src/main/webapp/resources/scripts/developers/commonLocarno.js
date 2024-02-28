/* global extractLast, split, Common, disableNoButtons, nationalSearchReportInitialization, Header, Footer, getMessage*/
'use strict';

function showCreateNewUserLink(show) {
    if (show) {
        $('a[href$="hideifappexactmatch"]').show();
    } else {
        $('a[href$="hideifappexactmatch"]').hide();
    }
}

function openDuplicatesModal(duplicateHashKey, userType, id, continueWithoutImportFn) {

    if ($('#duplicatesExactMatchAllowContinue').val() === 'true') {
        //configured through application properties to always show the option to continue even when an exact match
        showCreateNewUserLink(true);
    } else {
        if (duplicateHashKey.match('^E')) {
            //we found exact match - hide the link that allows the user to continue
            showCreateNewUserLink(false);
        } else {
            //we found similar match - show the link that allows the user to continue
            showCreateNewUserLink(true);
        }
    }

    //always show dialog if exact matches found
    if (duplicateHashKey.match('^E') || duplicateHashKey.match('^S')) {

        var hrefurl = window.location.href;
        var url = '';
        if (hrefurl.indexOf('wizard') !== -1) {
            url = hrefurl.replace('wizard', 'importDuplicate');
        } else {
            url = hrefurl.replace('oneform', 'importDuplicate');
        }

        var n = url.search('htm');
        var exec = url.search('execution');
        var a = url.slice(0, n + 3);
        var test = a + '?' + url.slice(exec, exec + 14);
        if (id !== '') {
            // delete this fake id if the user is imported
            test += '&idformusertoreplace=' + id;
        }

        var encodedCallerUrl = encodeURIComponent(test);
        var duplicatesUrl = '';
        if (userType === 'OWNER') {
            // applicant
            duplicatesUrl = $('#urlApp').val();
        } else {
            // representative
            duplicatesUrl = $('#urlRep').val();
        }

        var appLang = $('#appLanguage').val();

        $('#duplicatesIFrame').attr('src', duplicatesUrl + duplicateHashKey + '?locale='+appLang+'&callerApplicationURL=' + encodedCallerUrl);
        $('#modal-duplicates #modalDuplicateUserType').val(userType);
        $('#modal-duplicates #duplicateFormId').val(id);
        $('#modal-duplicates').modal('show');
        $('#modal-duplicates a[href$="hideifappexactmatch"]').off('click');
        $('#modal-duplicates a[href$="hideifappexactmatch"]').on('click', continueWithoutImportFn);
    }
}

function setToNoDuplicates() {
    if ($('#modal-duplicates #duplicateFormId').val() !== '') {
        if ($('#modal-duplicates #modalDuplicateUserType').val() === 'OWNER') {
            Applicant.ajax.verifyApplicant($('#modal-duplicates #duplicateFormId').val());
        } else {
            Representative.ajax.verifyRepresentative($('#modal-duplicates #duplicateFormId').val());
        }
        $('#modal-duplicates #duplicateFormId').val('');
        $('#modal-duplicates #modalDuplicateUserType').val('');
    }
}

function notImportApplicant() {
    $('#checkForDuplicates').val('false');
    $('.addApplicant:visible').trigger('click', ['true']);
}

function notImportRepresentative() {
    $('#checkForDuplicates').val('false');
    $('.addRepresentative:visible').trigger('click', ['true']);
}

function checkUserDuplicates(userType) {

    if ($('#checkForDuplicates').val() === 'false') {
        $('#checkForDuplicates').val('true');
        return false;
    }

    var foundDuplicates = true;

    var parentElement = $();
    var continueWithoutImportFn = null;
    if (userType === 'OWNER') {
        // applicant
        parentElement = $('#applicantSection');
        continueWithoutImportFn = notImportApplicant;
    } else {
        // representative
        parentElement = $('#representativeSection');
        continueWithoutImportFn = notImportRepresentative;
    }

    var data = {
        cityAddress: parentElement.find('input[name="address.city"]:visible').val(),
        firstName: parentElement.find('input[name="firstName"]:visible').val(),
        lastName: parentElement.find('input[name="surname"]:visible').val(),
        nationality: parentElement.find('select[name="nationality"]:visible').val(),
        firstStreetAddress: parentElement.find('textarea[name="address.street"]:visible').val(),
        countryAddress: parentElement.find('select[name="address.country"]:visible').val(),
        stateAddress: parentElement.find('select[name="address.stateprovince"]:visible').val(),
        telephoneNumber: parentElement.find('input[name="phone"]:visible').val(),
        faxNumber: parentElement.find('input[name="fax"]:visible').val(),
        email: parentElement.find('input[name="email"]:visible').val(),
        userType: userType
    };

    if (_.isUndefined(data.lastName)) {
        if (userType === 'OWNER') {
            data.lastName = parentElement.find('input[name="name"]:visible').val();
        } else {
            data.lastName = parentElement.find('input[name="associationOwnName"]:visible').val();
        }
        data.nationality = parentElement.find('select[name="countryOfRegistration"]:visible').val();
    }

    var id = parentElement.find('input[name="id"]').val();

    $.ajax({
        url: 'checkUserHasDuplicates.htm',
        type: 'GET',
        async: false,
        data: data,
        success: function(data) {
            if (!_.isUndefined(data.obj) && data.obj !== 'NOT_FOUND') {
                openDuplicatesModal(data.obj, userType, id, continueWithoutImportFn);
            } else {
                foundDuplicates = false;
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            $('#modal-loading').hide();
            $('#messageModal #messagePlaceholder').html('Error duplicates');
            $('#messageModal').modal('show');
            foundDuplicates = false;
        }
    });

    return foundDuplicates;
}

function getUserDuplicates(id, userType) {

    var foundDuplicates = true;

    var data = {
        id: id,
        userType: userType
    };

    $.ajax({
        url: 'getUserDuplicates.htm',
        type: 'GET',
        async: false,
        data: data,
        success: function(data) {
            if (!_.isUndefined(data.obj) && data.obj !== 'NOT_FOUND') {
                openDuplicatesModal(data.obj, userType, id, setToNoDuplicates);
            } else {
                foundDuplicates = false;
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            $('#modal-loading').hide();
            $('#messageModal #messagePlaceholder').html('Error duplicates');
            $('#messageModal').modal('show');
        }
    });

    return foundDuplicates;
}

function showMessageModal(messageInnerHtml) {
    $('#messageModal #messagePlaceholder').html(messageInnerHtml);
    $('#messageModal').modal('show');
}

function resetSavedExternallyStatus() {
    var savedValue = $('#wasSavedExternally').val();
    if (savedValue !== undefined && savedValue !== null && savedValue !== '') {
        if (savedValue === 'success') {
            showMessageModal(getMessage('general.messages.application.save.success'));
        }
        if (savedValue === 'failure') {
            showMessageModal(getMessage('general.messages.application.save.failure'));
        }
        $('#wasSavedExternally').val('');
    }
}

// Shows/hides notes depending of the type of form. If it's one form, notes are hidden, otherwise, shown.
function showHideNotes() {
    if ($('.body').attr('id') === 'normal') {
        $('.note visible').hide();
    } else {
        $('.note visible').show();
    }

}

function setFormParams() {
    var hiddenManagement = function(param) {
        var hiddenfield = document.createElement('input');
        $(hiddenfield).attr('type', 'hidden');
        $(hiddenfield).attr('name', param.name);
        $(hiddenfield).attr('id', param.name);
        if (param.type === 'checkbox') {
            if (param.checked) {
                $(hiddenfield).val('true');
            } else {
                $(hiddenfield).val('false');
            }
        } else if (param.type === 'radio') {
            if (param.checked) {
                $(hiddenfield).val(param.value);
            }
        } else {
            $(hiddenfield).val(param.value);
        }

        $('#hiddenForm').append(hiddenfield);
    };
    var visibleManagement = function(param) {
        if (param.type === 'checkbox') {
            if (param.checked) {
                document.hiddenForm.elements[param.name].value = 'true';
            } else {
                document.hiddenForm.elements[param.name].value = 'false';
            }
        } else if (param.type === 'radio') {
            if (param.checked) {
                document.hiddenForm.elements[param.name].value = param.value;
            }
        } else {
            document.hiddenForm.elements[param.name].value = param.value;
        }
    };
    var params = $('.mainForm :input').filter(':not(.submittedAjax)');

    $('#finalSubmitContainer').find(':input').attr('disabled', 'disabled');
    if (document.hiddenForm === undefined) {
        document.hiddenForm = document.getElementById('hiddenForm');
    }

    _.each(params, function(param) {
        if ((_.isUndefined(document.hiddenForm.elements[param.name]) || _.isNull(document.hiddenForm.elements[param.name])) && param.name !== '') {
            hiddenManagement(param);
        } else if (!_.isUndefined(document.hiddenForm.elements[param.name]) && !_.isNull(document.hiddenForm.elements[param.name]) && param.name !== '') {
            visibleManagement(param);
        }
    });

}

function isFormOpen(sectionSelector) {
    return $(sectionSelector).is(':visible');
}

function anyFormsOpen() {
    return isFormOpen('#tabApplicant') || isFormOpen('#tabRepresentative') || isFormOpen('#tabDesignInformation');
}

function formsOpenAction() {
    $('#errorBoxForm').html($('#errorBoxHolder').html());
    $('#errorBoxForm #errorBox_dummy').attr('id', 'errorBox');
    $('#errorBoxForm #errorBox').removeClass('hidden');
    /*sections*/
    var openApplicant = isFormOpen('#applicantSection');
    var openRepresentative = isFormOpen('#representativeSection');
    var openDesignInformation = isFormOpen('#tabDesignInformation');
    /*subsections*/
    var openPriority = isFormOpen('#priorityForm');
    var openExhibition = isFormOpen('#exhibitionForm');
    var openDesigner = isFormOpen('#tabDesigners');

    $('#errorBoxForm #error_openform_priority').toggle(openPriority);
    $('#errorBoxForm #error_openform_exhibition').toggle(openExhibition);
    $('#errorBoxForm #error_openform_designer').toggle(openDesigner);

    $('#errorBoxForm #error_openform_applicant').toggle(openApplicant);
    $('#errorBoxForm #error_openform_representative').toggle(openRepresentative);
    $('#errorBoxForm #error_openform_designinformation').toggle(openDesignInformation);

    window.location = window.location.href.split('#')[0] + '#pageTop';
}

function addErrorCommon(mainContainer, containerError, mesageError) {
    if ($('#' + containerError).length > 0) {
        // clear container if exist
        $('#' + containerError).html('');
    } else {
        // create new container
        $('<span/>', {
            id: containerError,
            class: 'errorMessage'
        }).appendTo('#' + mainContainer);
    }

    // Add message to container
    $('<p/>', {
        class: 'flMessageError',
        text: mesageError
    }).appendTo('#' + containerError);

    // Scroll to container to view message error
    scrollToContainer('#' + containerError);
}

function scrollToContainer(containerSelector) {
    $('html, body').animate({
        scrollTop: $(containerSelector).offset().top
    }, 'slow');
}

//Submits the hidden form and collects all inputs that are having the class "mainForm"
function submitMainForm(value, showLoading, importCTM) {

    if ($('#valueone').val()) {
        $('#colourSection').before('<p class="flMessageError" style="position: static;"><span>' + getMessage('click.on.add.button') + '</span></p>');

        if ($('.collectiveOptions').length) {
            scrollToContainer($('.collectiveOptions'));
        }

    } else {
        if (value !== 'Refresh' && anyFormsOpen() && !importCTM) {
            formsOpenAction();
            return false;
        }
        $('#hiddenElement').val(value);
        setFormParams();
        if (showLoading) {
            showLoadingScreen();
        }
        $('#hiddenForm').submit();
    }


}

// add navigation functions to buttons
$(document).ready(function() {

    $('.navigateBtn').click(function() {
        if (!$(this).hasClass('disabled')) {
            submitMainForm($(this).attr('data-val'), true);
        }
    });

    $('.receipt-new-window').bind('click', function() {
        getReceipt();
    });
    if (typeof disableNoButtons !== 'undefined') {
        disableNoButtons();
    }
    showHideNotes();
    if (typeof nationalSearchReportInitialization !== 'undefined') {
        nationalSearchReportInitialization();
    }
    if ($('.minorType.active').size() > 0) {
        $('button.see-more-button').trigger('click');
    }
    if (typeof W !== 'undefined') {
        W.setMainNav();
    }
    var appLang = $('#appLanguage').val();
    if (typeof Header !== 'undefined') {
        Header.getFromExternal(appLang);
    }
    if (typeof Footer !== 'undefined') {
        Footer.getFromExternal(appLang);
    }
    resetSavedExternallyStatus();
});



// added a global flag to blur and autocomplete.
// When I enter my autocomplete input field's blur handling function,
// I have a if(!autocompleteIEHack) {//do blur stuff here}.
// In autocomplete 'focus', I set autocompleteIEHack=true;
// In autocomplete 'close', I set autocompleteIEHack=false and explicitly call blur() on my autocomplete field
var autocompleteIEHack = false;



function createFormsOpenText() {
    var text = '';
    var openApplicant = isFormOpen('#applicantSection');
    var openRepresentative = isFormOpen('#representativeSection');
    var openDesignInformation = isFormOpen('#designInformation');

    if (openApplicant) {
        text += $('#openApplicantFormMessage').html();
    }
    if (openRepresentative) {
        text += $('#openRepresentativeFormMessage').html();
    }
    if (openDesignInformation) {
        text += $('#openDesignFormMessage').html();
    }
    return text;
}


$(document).on('click', '.messageScroll', function() {
    scrollToContainer($(this).attr('data-scroll'));
});



function scrollToContainerBottom(containerSelector) {
    var scrollToContainer = $(containerSelector);
    var ref = $('<div>');
    scrollToContainer.after(ref);
    var scrollTop = ref.offset().top - $(window).height();
    ref.remove();
    $('html, body').animate({
        scrollTop: scrollTop
    }, 'slow');
}

$(document).on('focus', 'input[class*="-date"]', function() {
    var _self = this;
    $(this).off('changeDate');
    $(this).datepicker({
        format: $('#datepickerFormat').html()
    }).on('changeDate', function(ev) {
        $(_self).blur();
        $(_self).datepicker('hide');
    });
});
$(document).on('change', 'input[class*="-date"]', function(ev) {
    setTimeout(function() {
        if (!Date.isValid($(ev.currentTarget).val())) {
            $(ev.currentTarget).addClass('error');
        } else {
            $(ev.currentTarget).removeClass('error');
        }
    }, 50); //The timeout is set to avoid problems of jquery validation validating the format afterwards.
});

$(document).on('blur', '.autocompleted', function(event) {
    if (!_.isEmpty($(this).attr('autocomplete')) &&
        $(this).autocomplete('widget').find('.ui-state-hover').length === 0 &&
        $(this).autocomplete('widget').find('.navigation-col:hover').length === 0) {
        $(this).autocomplete('destroy');
    }
});

$(document).on('keyup.autocomplete', '.autocompleted', function() {
    var urlJson = $(this).attr('data-url');
    var minChars = $(this).attr('data-minchars');
    var thisInput = $(this).parent();
    if (!minChars) {
        minChars = 3;
    }
    var inputAutocomplete = $(this).autocomplete({
        delay: 700,
        minLength: minChars,
        source: function(request, response) {
            $.getJSON(urlJson, {
                args: extractLast(request.term),
                beforeSend: function() {
                    $(thisInput).find('.loading-small').removeClass('hidden');
                },
                complete: function() {
                    $(thisInput).find('.loading-small').addClass('hidden');
                }
            }, response);
        },
        // The following lines make the autocomplete work properly in IE9
        // Initial issue: when selecting value with mouse, autocompletechange event would appear not to trigger
        // Reason: possibly because blur function was called at a wrong time
        // Coupled with: 100ms delay on autocompleteselect desired functionality
        // Why this works: unknown
        blur: function() {},
        focus: function() {
            autocompleteIEHack = true;
        },
        close: function() {
            autocompleteIEHack = false;
        },
        select: function(event, ui) {
            Common.selected = true;
            if ($(event.originalEvent.originalEvent.target).parent().hasClass('navigation-col')) {
                Common.selected = false;
                Common.previousAutocompleteValue = $(event.target).val();
            }
        }
    });
    (function() {
        var originalCloseMethod = inputAutocomplete.data('ui-autocomplete').close;
        inputAutocomplete.data('ui-autocomplete').close = function(event) {
            //Prevent from closing if the use click in the view element.
            if (Common.toView && $(event.originalEvent.target).parent().hasClass('autocomplete-input-holder')) {
                Common.toView = false;
                return;
            }
            Common.toView = false;
            if (!$(event.originalEvent.target).parent().hasClass('navigation-col')) {
                originalCloseMethod.apply(this, arguments);
            } else {
                var url = $(event.originalEvent.target).parent().attr('data-url');
                event.stopPropagation();
                Common.toView = true;
                window.open(url, '_blank');
            }
        };
    })();
});



$(document).on('blur', ':input', function(e) {
    //Remove the error message if it exists
    Common.removeErrorMessage(e);
});

$(document).on('click', '.bgAdd', function(e) {
    //Remove the error message if it exists
    Common.removeErrorMessage(e);
});

function wrapErrorNicely(errorMessage) {
    return '<p class="flMessageError"><span>' + errorMessage + '</span></p>';
}

function removePreviousErrors(containerSelector) {
    $(containerSelector + ' p.flMessageError').remove();
}

function genericHandleError(errorMessage, containerSelector, scrollToTopOfContainer, scrollToBottomOfContainer) {
    removePreviousErrors(containerSelector);
    if (scrollToBottomOfContainer) {
        $(containerSelector).append($(wrapErrorNicely(errorMessage)).css('position', 'static'));
        scrollToContainerBottom(containerSelector);
    } else {
        $(containerSelector).prepend(wrapErrorNicely(errorMessage));
        if (scrollToTopOfContainer) {
            scrollToContainer(containerSelector);
        }
    }
}



function clearTabsDevelopers(button) {
    var tab = $(button).closest('.tab-content').find('>div:visible');
    if (tab.length === 0) {
        tab = $(this).closest('.tab-content').find('>div:visible');
    }
    tab.removeClass('active');
}

function fspLog(message) {
    if (window.console) {
        console.log(message);
    }
}


$(document).on('click', '.loginLink', function() {
    if (!_.isUndefined($('#hiddenForm')) && !_.isNull($('#hiddenForm')) && $('#hiddenForm').length > 0) {
        submitMainForm('GlobalLogin');
        return false;
    } else {
        $('#GlobalLoginButton').click();
    }
    return false;
});


$(document).on('click', '.dropdown-languages li', function() {
    var destinationLanguage = $(this).find('a').attr('lang');

    $('#languageInput').val(destinationLanguage);

    if (!_.isUndefined($('#hiddenForm')) && !_.isNull($('#hiddenForm')) && $('#hiddenForm').length > 0) {
        var inputUserLanguage = $('<input type="hidden" name="userLanguage">');
        inputUserLanguage.val(destinationLanguage);
        $('#hiddenForm').append(inputUserLanguage);
        submitMainForm('ChangeLanguage');
    } else {
        $('#headerLanguageInput').val($(this).find('a').attr('href'));
        $('#ChangeLanguageButton').click();
    }
    return false;

});

function showWarningModal(warningInnerHtml) {
    $('#warningModal #warningPlaceholder').html(warningInnerHtml);
    $('#warningModal').modal('show');
}

function ajaxMessage(code) {
    $.ajax({
        url: 'message.htm',
        data: 'code=' + code,
        cache: true,
        dataType: 'text',
        type: 'POST',
        success: function(html) {
            showWarningModal(html);
        },
        error: function() {
            showWarningModal(code);
        }
    });

}

function showConfirmModal(messageInnerHtml, onYes, onNo) {
    $('#confirmModal #confirmPlaceholder').html(messageInnerHtml);
    $('#confirmModal').modal('show');

    $('#confirmModal #confirmModalOk').unbind('click');
    $('#confirmModal #confirmModalCancel').unbind('click');

    // When clicking on YES (or OK), make sure the function for the 'hidden' event
    // is not called
    $('#confirmModal #confirmModalOk').bind('click', function() {
        $('#confirmModal').unbind('hidden');
        if (!_.isUndefined(onYes)) {
            onYes();
        }
    });

    // When clicking on NO (or Cancel), make sure the function for the "hidden" event
    // is not called
    $('#confirmModal #confirmModalCancel').bind('click', function() {
        $('#confirmModal').unbind('hidden');
        if (!_.isUndefined(onNo)) {
            onNo();
        }
    });

    // If YES or NO are not clicked and the modal still closes, execute the onNo function
    $('#confirmModal').unbind('hidden');
    $('#confirmModal').bind('hidden', onNo);
}

function showConfirmModalYesNo(messageInnerHtml, onYes, onNo) {
    $('#confirmModalYesNo #confirmYesNoPlaceholder').html(messageInnerHtml);
    $('#confirmModalYesNo').modal('show');

    $('#confirmModalYesNo #confirmModalYes').unbind('click');
    $('#confirmModalYesNo #confirmModalNo').unbind('click');

    // When clicking on YES (or OK), make sure the function for the 'hidden' event
    // is not called
    $('#confirmModalYesNo #confirmModalYes').bind('click', function() {
        $('#confirmModalYesNo').unbind('hidden');
        onYes();
    });



    // When clicking on NO (or Cancel), make sure the function for the 'hidden' event
    // is not called
    if (!_.isUndefined(onNo)) {
        $('#confirmModalYesNo #confirmModalNo').bind('click', function() {
            $('#confirmModalYesNo').unbind('hidden');
            onNo();
        });
    }

    // If YES or NO are not clicked and the modal still closes, execute the onNo function
    $('#confirmModalYesNo').unbind('hidden');
    if (!_.isUndefined(onNo)) {
        $('#confirmModalYesNo').bind('hidden', onNo);
    }
}

function getReceipt() {
    $.ajax({
        url: 'receipt.htm',
        cache: false,
        type: 'POST',
        success: function(html) {
            var win = window.open();
            var doc = win.document.open('application/pdf');
            doc.write(html);
        },
        error: function(xmlHttpRequest, textStatus, errorThrown) {

        }
    });
}


function removeAllHighlightRow() {
    $('.priority').removeClass('active');
    $('.seniority').removeClass('active');
    $('.exhpriority').removeClass('active');
    $('.transformation').removeClass('active');
}

function removeHighlightRow(row) {
    if (row.length > 0) {
        $('.' + row.attr('class').replace(' ', '.')).removeClass('active');
    }
}

function highlightRow(row) {
    if ($('body').attr('id') === 'normal') {
        removeAllHighlightRow();
    } else {
        removeHighlightRow(row);
    }
    row.addClass('active');
}


function partial(func /*, 0..n args */ ) {
    var args = Array.prototype.slice.call(arguments, 1);
    return function() {
        var allArguments = args.concat(Array.prototype.slice.call(arguments));
        return func.apply(this, allArguments);
    };
}

$(document).on('keyup.autocomplete', '.autocompletedMultiple', function() {
    var urlJson = $(this).attr('data-url');
    var minChars = $(this).attr('data-minchars');
    if (!minChars) {
        minChars = 3;
    }
    $(this).autocomplete({
        delay: 700,
        minLength: minChars,
        source: function(request, response) {
            $.getJSON(urlJson, {
                root: extractLast(request.term)
            }, response);
        },
        beforeSend: function() {
            $(this).parent().find('.loading-small').removeClass('hidden');
        },
        success: function() {
            $(this).parent().find('.loading-small').addClass('hidden');
        },
        focus: function() {
            // prevent value inserted on focus
            return false;
        },
        select: function(event, ui) {
            var terms = split(this.value);
            // remove the current input
            terms.pop();
            // add the selected item
            terms.push(ui.item.value);
            // add placeholder to get the comma-and-space at the end
            terms.push('');
            this.value = terms.join('; ');
            return false;
        }

    });
});

$(document).on('click', '.resetButton', function() {
    var url = $(this).attr('data-url');
    showConfirmModal($('#resetApplicationConfirmation').html(), function() {
        location.href = url;
    });
});

function parseLinkUsingKeyValuePairs(keyValueArray, link) {
    if (_.isUndefined(link) || _.isNull(link)) {
        return;
    }
    for (var i = 0; i < keyValueArray.length; i++) {
        if (Object.prototype.toString.call(keyValueArray[i]) !== '[object Array]') {
            fspLog('invalid input parameter: keyValueArray');
            return link;
        }
        var parameter = keyValueArray[i][0];
        var value = keyValueArray[i][1];
        link = link.replace('{' + parameter + '}', value);
    }
    return link;
}

function htmlEncode(value) {
    return $('<div/>').text(value).html();
}

function htmlDecode(value) {
    return $('<div/>').html(value).text();
}

function showLoadingScreen() {
    $('#ajaxBlock').show();
}

function hideLoadingScreen() {
    $('#ajaxBlock').hide();
}

window.Common = {
    selected: null,
    previourAutocompleteValue: null,
    isPreviousValueAutocomplete: function() {
        return Common.selected === false && !_.isEmpty(Common.previousAutocompleteValue);
    },
    showGenericError: function(selector) {
        genericHandleError('An error occured while processing your request. Please try again later.', selector, true);
    },
    removeErrorMessage: function(event) {
        var errorMessageId = event.target.id + 'ErrorMessageServer';
        if ($('#' + errorMessageId).length > 0) {
            $('#' + errorMessageId).remove();
            //Remove the error class
            if (event.target.type === 'checkbox') {
                event.target.className = event.target.className.replace('errorValCheckbox', '');
            } else {
                event.target.className = event.target.className.replace('error', '');
            }
        }
    },
    cleanActiveRows: function(htmlSelector) {
        $(htmlSelector).find('tr').each(function() {
            $(this).removeClass('active');
        });
    },
    /**
     * In order to avoid submits when key enter in an inport inside a form
     * @param  {Event} event
     * @return {Boolena}       false if the key is enter
     */
    avoidSubmitOnEnter: function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
    },
    /**
     * From http://stackoverflow.com/a/23698434
     */
    getIEVersion: function() {
        var match = navigator.userAgent.match(/(?:MSIE |Trident\/.*; rv:)(\d+)/);
        return match ? parseInt(match[1]) : undefined;
    }
};

Common.Sort = {
    predicate: function(property, descending) {
        var multiplier = 1;
        if (descending) {
            multiplier = -1;
        }
        return function(a, b) {
            if (a[property] > b[property]) {
                return 1 * multiplier;
            } else if (a[property] < b[property]) {
                return -1 * multiplier;
            }
            return 0;
        };
    }
};


$(window).on('beforeunload', function() {
    if ($('.paymentModal').is(':visible')) {
        return 'Are you sure you want to navigate away from this page?';
    }
});

function resetFormFields(parent) {
    $(parent).find('input, select, textarea').each(function(index, value) {
        $(value).val('');
    });
    // hide validation errors
    $(parent).find('.flMessageError').each(function(index, value) {
        $(value).hide();
    });
}

var countryConfigurationList;

/**
 * Check if the country belongs to the EU. Used in additional-methods.js.
 * @param country the country iso code
 * @returns true if the country is in the EU, false otherwise
 */
function isCountryEU(country) {

    if (!countryConfigurationList) {
        $.ajax({
            url: 'getCountriesConfig.htm',
            type: 'GET',
            async: false,
            data: 'country=' + country,
            success: function(data) {
                countryConfigurationList = data;
                return countryConfigurationList;
            },
            error: function(data) {
                genericHandleError('An error occured while processing your request. Please try again later.', '#applicantCardList', true);
            }
        });
    }

    if (_.some(countryConfigurationList, function(countryConfiguration) {
            return countryConfiguration.code === country && countryConfiguration.isEU;
        })) {
        return true;
    }


    return false;
}

/**
 * Check if the country belongs to the EEA
 * @param country the country iso code
 * @returns true if the country is in the EEA, false otherwise 
 */
function isCountryEEA(country){
	
    if (!countryConfigurationList) {
        $.ajax({
            url: 'getCountriesConfig.htm',
            type: 'GET',
            async: false,
            data: 'country=' + country,
            success: function(data) {
                countryConfigurationList = data;
                return countryConfigurationList;
            },
            error: function(data) {
                genericHandleError('An error occured while processing your request. Please try again later.', '#applicantCardList', true);
            }
        });
    }

    if (_.some(countryConfigurationList, function(countryConfiguration) {
            return countryConfiguration.code === country && countryConfiguration.partOfEEA;
        })) {
        return true;
    }


    return false;
}

/**
 * To perform actions when the tab is visible
 */
(function() {
    var hidden = 'hidden';

    // Standards:
    if (hidden in document) {
        document.addEventListener('visibilitychange', onchange);
    } else if ((hidden = 'mozHidden') in document) {
        document.addEventListener('mozvisibilitychange', onchange);
    } else if ((hidden = 'webkitHidden') in document) {
        document.addEventListener('webkitvisibilitychange', onchange);
    } else if ((hidden = 'msHidden') in document) {
        document.addEventListener('msvisibilitychange', onchange);
    }
    // All others:
    else {
        window.onpageshow = window.onfocus = onchange;
    }

    function focusAutocompleteInput() {
        if (!_.isEmpty($('#representativeImportTextfield').attr('autocomplete')) &&
            !$('#representativeImportTextfield').is(':focus')) {
            $('#representativeImportTextfield').focus();
        } else if (!_.isEmpty($('#applicantImportTextfield').attr('autocomplete')) &&
            !$('#applicantImportTextfield').is(':focus')) {
            $('#applicantImportTextfield').focus();
        } else if (!_.isEmpty($('#lastPriority').attr('autocomplete')) &&
            !$('#lastPriority').is(':focus')) {
            $('#lastPriority').focus();
        }
    }

    function onchange(evt) {
        var v = 'visible',
            h = 'hidden',
            evtMap = {
                focus: v,
                focusin: v,
                pageshow: v,
                blur: h,
                focusout: h,
                pagehide: h
            },
            visibilityState;
        evt = evt || window.event;
        if (document.body) {
            if (evt.type in evtMap) {
                visibilityState = evtMap[evt.type];
            } else {
                visibilityState = this[hidden] ? 'hidden' : 'visible';
            }
            if (visibilityState === 'visible') {
                setTimeout(focusAutocompleteInput, 50);
            }
        }
    }

    // set the initial state (but only if browser supports the Page Visibility API)
    if (document[hidden] !== undefined) {
        onchange({
            type: document[hidden] ? 'blur' : 'focus'
        });
    }
})();