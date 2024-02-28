'use strict';

pt.efiling.application.patent = {};
pt.efiling.application.patent.views = {};

!function(commonAjax) {

    this.init = function() {
        fspLog('   init patent views...');
        initializations.initAddPatentView();
        initializations.initSavePatentView();
        initializations.initEditPatentView();
        initializations.initRemovePatentView();
        initializations.initViewPatentView();
        initializations.initViewImagePatentView();
        fspLog('   ...patent views initiated');
    };

    var initializations = {
        initAddPatentView: function() {
            $(document).on('click', '#addViewPatentBtn', function() {
                ajax.addPatentView();
            });
        },
        initSavePatentView: function() {
            $(document).on('click', '#savePatentViewBtn', function() {
                ajax.savePatentView();
            });
        },
        initEditPatentView: function() {
            $(document).on('click', 'a[id^="editPatentViewBtn"]', function() {
                var id = $(this).attr('data-val');
                ajax.getPatentView(id);
            });
        },
        initRemovePatentView: function() {
            $(document).on('click', 'a[id^="removePatentViewBtn"]', function() {
                var id = $(this).attr('data-val');
                var onConfirmYes = partial(ajax.removePatentView, id);
                showConfirmModal($('#patent_view_deleteConfirmation').html(), onConfirmYes);
            });
        },
        initViewPatentView: function() {
            $(document).on('click', 'a[id^="viewPatentViewBtn"]', function() {
                var id = $(this).attr('data-val');
                ajax.viewPatentView(id);
            });
        },
        initViewImagePatentView: function() {
            $(document).on('click', 'img[id^="viewImage"]', function() {
                showLoadingScreen();
                var documentId = $(this).attr('data-file-documentId');
                var name = $(this).attr('data-file-name');
                $('#modalViewImageTitle').html(name);
                $('#modalViewImageImg').attr("src", 'getDocument.htm?documentId='+ documentId);
                $('#viewImageModalDiv').modal('show');
                hideLoadingScreen();
            });
        }
    };

    var manipulation = {
        showPatentViewSection: function() {
            $('#patentViewSection').modal('show');
        },
        hidePatentViewSection: function() {
            $('#patentViewSection').modal('hide');
        },
        updatePatentViewSection: function(html) {
            $('#patentViewSection').html(html);
            updateFeesInformation();
        },
        updatePatentViewsList: function(html) {
            $('#patentViews').html(html);
            updateFeesInformation();
        }
    };

    var utils = {
        errorSectionsSelectors: ['#patentViewSection', '#patentViews']
    };

    var ajax = {
        viewPatentView: function(id) {
            commonAjax.commonCall({
                url: 'viePatentView.htm',
                data: 'id=' + id,
                errorMetadata: {selectorContainerErrorMessage: '#patentViewGenericErrorDiv', selectorContainerError: '#patentViews'},
                success: manipulation.updatePatentViewSection,
                complete: manipulation.showPatentViewSection,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        addPatentView: function() {
            commonAjax.commonCall({
                url: 'addPatentView.htm',
                errorMetadata: {selectorContainerErrorMessage: '#patentViewGenericErrorDiv', selectorContainerError: '#patentViewSection'},
                success: manipulation.updatePatentViewSection,
                complete: manipulation.showPatentViewSection,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        savePatentView: function() {
            commonAjax.commonCall({
                url: 'savePatentView.htm',
                type: 'POST',
                data: $('#addNewPatentViewForm').serialize(),
                errorMetadata: {selectorContainerErrorMessage: '#patentViewGenericErrorDiv', selectorContainerError: '#patentViewSection'},
                success: function(html) {
                    // We check if the controller has returned the same view, this will mean there are errors
                    if ($('#addNewPatentViewForm', $(html)).length != 0) {
                        // We come back to paint the same page
                        manipulation.updatePatentViewSection(html);
                    } else {
                        manipulation.updatePatentViewsList(html);
                        manipulation.hidePatentViewSection();
                    }
                },
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        getPatentView: function(id) {
            commonAjax.commonCall({
                url: 'getPatentView.htm',
                data: 'id=' + id,
                errorMetadata: {selectorContainerErrorMessage: '#patentViewGenericErrorDiv', selectorContainerError: '#patentViews'},
                success: manipulation.updatePatentViewSection,
                complete: manipulation.showPatentViewSection,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        removePatentView: function(id) {
            commonAjax.commonCall({
                url: 'removePatentView.htm',
                data: 'id=' + id,
                success: manipulation.updatePatentViewsList,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        }
    };

}.call(pt.efiling.application.patent.views, pt.efiling.application.commonAjax);

function checkMaxPatentViews(){
    var maxViews=$('#maxPatentViews').val();

    var rowCount = $('#patentViewsTable tr').length-1;

    if(rowCount>=maxViews){
        $("#addViewPatentBtn").hide();
    } else {
        $("#addViewPatentBtn").show();
    }

}