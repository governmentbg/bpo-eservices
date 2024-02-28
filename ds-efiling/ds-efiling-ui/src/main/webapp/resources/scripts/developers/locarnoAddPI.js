/*global Common, ajaxMessage, showConfirmModal, showLoadingScreen, hideLoadingScreen, DesignInformation, showWarningModal, genericHandleError*/
'use strict';
var Locarno = {
    /**
     * The list of class codes (integers) that can be added
     * @type {Array}
     */
    allowedClasses: [],
    setResponseInTable: function(responseHtml) {
        $('#productIndicationTableWrapper').html(responseHtml);
    },
    cleanImportInput: function() {
        $('#locarnoImportId').attr('classcode', '');
        $('#locarnoImportId').attr('subclasscode', '');
        $('#locarnoImportId').val('');
        $('#locarnoImportTextfield').val('');
    },
    /**
     * In order to disable the input
     * @inner
     */
    disableButtonBar: function() {
        $('button#locarnoImportButton').attr('disabled', true);
        $('a.oneformLocarnoBrowser').bind('click', false);
        $('a.oneformLocarnoBrowser').addClass("disabled");
        $('a.oneformLocarnoEnterMyTerms').bind('click', false);
        $('a.oneformLocarnoEnterMyTerms').addClass("disabled");
    },
    /**
     * In order to enable the input
     * @inner
     */
    enableButtonBar: function() {
        $('button#locarnoImportButton').attr('disabled', false);
        $('a.oneformLocarnoBrowser').unbind('click', false);
        $('a.oneformLocarnoBrowser').removeClass("disabled");
        $('a.oneformLocarnoEnterMyTerms').unbind('click', false);
        $('a.oneformLocarnoEnterMyTerms').removeClass("disabled");
    },
    cleanMessageErrors: function() {
        $('.locarnoValidationError .flMessageError').hide();
    },
    showMaxTermsErrorMessage: function() {
        $('p.locarnoMaxTermsImportError').show();
    },
    showNoClassAllowedErrorMessage: function() {
        $('p.locarnoClassNotAllowed').show();
    },
    showNoImputFilledErrorMessage: function() {
        $('p.locarnoImportNotFilled').show();
    },
    //General functions for the modals
    createSelectFromServer: function(optionsList, htmlIdentifier) {
        var selectOptionsHtml = '';
        _.forEach(optionsList, function(option) {
            selectOptionsHtml += '<option value="' + option.key + '">' + option.text + '</option>';
        });
        $(htmlIdentifier).html(selectOptionsHtml);
    },
    isTermsMaxReached: function(numberOfItems) {
        var canAddTermsResponse = true;
        if (_.isUndefined(numberOfItems)) {
            numberOfItems = 1;
        }
        if (numberOfItems <= parseInt($('#maxLocarnoItems').val()) - $('#addedProductIndications tbody tr').size()) {
            canAddTermsResponse = false;
        }
        return canAddTermsResponse;
    },
    isValidClass: function(classIdentifier) {
        return _.contains(Locarno.allowedClasses, classIdentifier);
    },
    checkForServerError: function(htmlResponse) {
        if ($(htmlResponse).hasClass('flMessageError')) {
            genericHandleError($(htmlResponse).html(), '#genericErrorLocarno');
            return true;
        }
        $('#genericErrorLocarno').html('');
        return false;
    },
    transformClassToString: function(classIdentifier) {
        if (_.size(classIdentifier) === 1) {
            classIdentifier = '0' + classIdentifier;
        }
        return classIdentifier;
    },
    validateImport: function() {
        var response = true;
        if ($('#locarnoImportId').val() === '') {
            Locarno.showNoImputFilledErrorMessage();
            response = false;
        } else {

            if (Locarno.isTermsMaxReached()) {
                Locarno.showMaxTermsErrorMessage();
                response = false;
            }
            if (!Locarno.isValidClass(Locarno.transformClassToString($('#locarnoImportId').attr('classcode')))) {
                Locarno.showNoClassAllowedErrorMessage();
                response = false;
            }
        }
        return response;
    },
    enableDisableAddsButtons: function () {
        if(Locarno.isTermsMaxReached()){//Disable buttons
            Locarno.disableButtonBar();
        }
        else{ //Enable buttons
            Locarno.enableButtonBar();
        }
    },
    nav: {
    	addProductIndication: 'addProductIndication.htm',
    	remove: 'removeLocarno.htm',
        loadClassCodes: 'getLocarnoClassesFromDSClass.htm',
        loadSubclassCodes: 'getLocarnoSubclassesFromDSClass.htm',
        getAllowedLocarnoClasses: 'getAllowedLocarnoClasses.htm',
        addNewProduct: 'addLocarnoNewProduct.htm',
        addNewComplexProduct: 'addNewLocarnoComplexProduct.htm',
        switchProductType: 'switchProductType.htm',
    },
    ajax: {
    	
    	remove: function(locarnoId) {
			$.ajax({
				url: Locarno.nav.remove,
				data: 'id=' + locarnoId,
				type: 'GET',
	            success: function(html) {
	            	Locarno.cleanMessageErrors();
	            	Locarno.setResponseInTable(html);
	            	$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                    Locarno.enableDisableAddsButtons();
	            }
			});
		},
		
    	addNewProduct: function(succesCallback) {
			$.ajax({
				url: Locarno.nav.addNewProduct,
				type: 'POST',
				data: $('#formNewLocarno').serialize(),
				beforeSend: function() {
	                    showLoadingScreen();
	            },
				success: function(html) {
					if (!Locarno.checkForServerError(html)) {
						Locarno.cleanMessageErrors();					
						if ($('#formNewLocarno', $(html)).length !== 0) {
							Locarno.setResponseInTable(html);
						}
						if (_.isFunction(succesCallback)) {
                            succesCallback(html);
                        }
					}
					Locarno.enableDisableAddsButtons();
					hideLoadingScreen();
				},
				error: function(data) {
                    hideLoadingScreen();
                }
			});
		},
        addNewComplexProduct: function(succesCallback) {
            $.ajax({
                url: Locarno.nav.addNewComplexProduct,
                type: 'POST',
                data: $('#formNewComplexLocarno').serialize(),
                beforeSend: function() {
                    showLoadingScreen();
                },
                success: function(html) {
                    if (!Locarno.checkForServerError(html)) {
                        Locarno.cleanMessageErrors();
                        if ($('#formNewComplexLocarno', $(html)).length !== 0) {
                            Locarno.setResponseInTable(html);
                        }
                        if (_.isFunction(succesCallback)) {
                            succesCallback(html);
                        }
                    }
                    Locarno.enableDisableAddsButtons();
                    hideLoadingScreen();
                },
                error: function(data) {
                    hideLoadingScreen();
                }
            });
        },

        switchProductType: function(id){
            $.ajax({
                url: Locarno.nav.switchProductType,
                type: 'POST',
                data: "id="+id,
                beforeSend: function() {
                    showLoadingScreen();
                },
                success: function(html) {
                    if (!Locarno.checkForServerError(html)) {
                        Locarno.cleanMessageErrors();
                        Locarno.setResponseInTable(html);
                    }
                    hideLoadingScreen();
                    $('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                },
                error: function(data) {
                    hideLoadingScreen();
                }
            });
        },
        /**
         * In order to get the classes allowed for a design
         * @param  {Function} successCallback Function to be run as success
         * @param {Boolean} editMode If true the allowed classes is call because of a design edition
         */
        getAllowedLocarnoClasses: function(successCallback, editMode) {
            var url = Locarno.nav.getAllowedLocarnoClasses;
            if (editMode === true) {
                url += '?editMode=true';
            }
            $.ajax({
                url: url,
                type: 'GET',
                beforeSend: function() {
                    showLoadingScreen();
                },
                success: function(data) {
                    if (!Locarno.checkForServerError(data)) {
                        if (!_.isEmpty(data)) {
                            Locarno.allowedClasses = data;
                            if (successCallback) {
                                successCallback();
                            }
                        }
                    }
                    hideLoadingScreen();
                },
                error: function() {
                    hideLoadingScreen();
                }
            });
        },
        /**
         * Call to add one or several product indications
         * @param {Objec} dataToBeSent   The elements to be added
         * @param {Function} succesCallback A function for the success
         * @param {Boolean} manageResponse If true the callback will manage the response
         */
        addProductIndication: function(dataToBeSent, succesCallback, manageResponse) {
            $.ajax({
                url: Locarno.nav.addProductIndication + '?flowKey=' + $('input[name="execution"]').val(),
                data: JSON.stringify(dataToBeSent),
                type: 'POST',
                contentType: 'application/json',
                beforeSend: function() {
                    showLoadingScreen();
                },
                success: function(responseHtml) {
                    if (!Locarno.checkForServerError(responseHtml)) {
                        Locarno.cleanMessageErrors();
                        if (manageResponse !== true) {
                            Locarno.setResponseInTable(responseHtml);
                        }
                        if (_.isFunction(succesCallback)) {
                            succesCallback(responseHtml);
                        }
                    }
                    Locarno.enableDisableAddsButtons();
                    hideLoadingScreen();
            		$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                },
                error: function(data) {
                    hideLoadingScreen();
                }
            });
        },
        
        /**
         * Get the existing locarno classes
         * @param  {String} htmlIdentifier Identifier to set the selector
         * @param  {Function} succesCallback Function to be performed after the success
         */
        loadLocarnoClassCodes: function(htmlIdentifier, succesCallback) {
            $.ajax({
                url: Locarno.nav.loadClassCodes,
                type: 'GET',
                dataType: 'json',
                beforeSend: function() {
                	showLoadingScreen();
            	},
                success: function(response) {
                    Locarno.createSelectFromServer(response, htmlIdentifier);
                    if (_.isFunction(succesCallback)) {
                        succesCallback();
                    }
                    hideLoadingScreen();
                },
                error: function(data) {
                	hideLoadingScreen();
                    var res=data.responseText;
                    if($(res).hasClass('flMessageError')){
						showMessageModal($(res).text());
               		}else{
                		var text=$(res).find('.flMessageError').text(); 
                		showMessageModal(text);
                	}
                }
            });
        },
        /**
         * Get the existing locarno subclasses
         * @param  {String} htmlIdentifier Identifier to set the selector
         * @param  {Function} succesCallback Function to be performed after the success
         */
        loadLocarnoSubclassCodes: function(value, htmlIdentifier, succesCallback) {
            $.ajax({
                url: Locarno.nav.loadSubclassCodes,
                type: 'GET',
                data: 'selectedClass=' + value,
                dataType: 'json',
                beforeSend: function() {
                	showLoadingScreen();
            	},
                success: function(response) {                	
                    Locarno.createSelectFromServer(response, htmlIdentifier);
                    if (_.isFunction(succesCallback)) {
                        succesCallback();
                    }
                    hideLoadingScreen();
                },
                error: function(data) {
                	hideLoadingScreen();
                	$('#locarnoModalEnterMyTerms select#locarnoSubclasses').children('option:not(:first)').remove();
                    var res=data.responseText;
                    if($(res).hasClass('flMessageError')){
						showMessageModal($(res).text());
               		}else{
                		var text=$(res).find('.flMessageError').text(); 
                		showMessageModal(text);
                	}
                }
            });
        },
        /**
         * In order to get the language that is going to be used in the browser modal
         * @inner
         */
        getFirstLanguageText: function() {
            return $.ajax({
                url: 'getFirstLanguageText.htm'
            });
        }
    },
    /**
     * Manage the autocomplete visualization
     * @param  {Object} object      The context from the caller
     * @inner
     */
    locarnoAutocomplete: function(object) {
        if (_.isUndefined($(object).data('autocomplete')) || _.isNull($(object).data('autocomplete'))) {
            return;
        }
        $(object).data('autocomplete')._renderItem = function(ul, item) {
            return $('<li></li>')
                .data('item.autocomplete', item)
                .append('<a><span class="two-col"><span class="selectable-col">' +
                    '<span class="first-col">' + item.text + '</span>' +
                    '<span class="second-col">' + Locarno.transformClassToString(item.classCode) +
                    '.' + Locarno.transformClassToString(item.subclassCode) + '</span>' +
                    '</span></span></a>')
                .appendTo(ul);
        };

    }
};

$(document).on('click', '#productIndicationTableWrapper a[id ^= "iconRemoveLocarno"]', function() {
	var locarnoId = $(this).attr('data-val');
	var onConfirmYes = partial(Locarno.ajax.remove, locarnoId);
	showConfirmModal($('#locarno_deleteConfirmation').html(), onConfirmYes);
});

$(document).on('click', 'a[href=#locarnoModalBrowser]', function() {
    var promiseFirstLang = Locarno.ajax.getFirstLanguageText();
    promiseFirstLang.success(function(result) {
        $('#locarnoModalFirstLang').html(result);
    });
});

$(document).on('focus', '#locarnoImportTextfield', function() {
    initAutocomplete(this);
    Locarno.locarnoAutocomplete(this);
});

$(document).on('autocompleteselect', '#locarnoImportTextfield', function(event, ui) {
    setTimeout(function() {
        if (_.isUndefined(ui.item) || _.isNull(ui.item)) {
            return;
        }
        $('#locarnoImportTextfield').val(ui.item.text);
        $('#locarnoImportId').val(ui.item.identifier);
        $('#locarnoImportId').attr('classcode', ui.item.classCode);
        $('#locarnoImportId').attr('subclasscode', ui.item.subclassCode);
        return false;
    }, 10);
});
$(document).on('show', '#locarnoModalBrowser', function(event) {
    if (!DesignInformation.checkLanguage()) {
        ajaxMessage('general.messages.productIndication.selectLanguageFirst');
        event.preventDefault();
    }
});

$(document).on('click', '#locarnoImportButton', function() {
    Locarno.cleanMessageErrors();
    if (Locarno.validateImport()) {
        var identifier = $('#locarnoImportId').val().trim();
        if (identifier === '') {
            showConfirmModal($('#locarnoImportEmpty').html());
        }
        else {
	        var dataToBeSent = {
	            'designIdentifier': $('#designIdentifierHidden').val(),
	            'productIndications': [{
	                'classCode': $('#locarnoImportId').attr('classcode'),
	                'subclassCode': $('#locarnoImportId').attr('subclasscode'),
	                'identifier': identifier,
	                'text': $('#locarnoImportTextfield').val().trim()
	            }]
	        };
	        Locarno.ajax.addProductIndication(dataToBeSent);
	        Locarno.cleanImportInput();
            Locarno.enableDisableAddsButtons();
        }
    }
});