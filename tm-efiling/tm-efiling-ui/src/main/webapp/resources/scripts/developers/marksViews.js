'use strict';

tm.application.marks = {};
tm.application.marks.views = {};

!function(commonAjax) {

    this.init = function() {
        fspLog('   init marks views...');
        initializations.initAddMarkView();
        initializations.initSaveMarkViews();
        initializations.initEditMarkView();
        initializations.initRemoveMarkView();
        initializations.initViewMarkView();
        initializations.initViewImageMarkView();
        fspLog('   ...marks views initiated');
    };

    var initializations = {
        initAddMarkView: function() {
            $(document).on('click', '#addViewMarkBtn', function() {
                ajax.addMarkView();
            });
        },
        initSaveMarkViews: function() {
            $(document).on('click', '#saveMarkViewsBtn', function(e) {
                ajax.saveMarkView();
            });
        },
        initEditMarkView: function() {
            $(document).on('click', 'a[id^="editMarkViewBtn"]', function() {
                var sequence = $(this).attr('data-sequence');
                ajax.getMarkView(sequence);
            });
        },
        initRemoveMarkView: function() {
            $(document).on('click', 'a[id^="removeMarkViewBtn"]', function() {
                var sequence = $(this).attr('data-sequence');
                ajax.getMarkView(sequence, true);
            });
            $(document).on('click', '#deleteMarkViewsBtn', function() {
                showConfirmModal($('#mark_view_deleteConfirmation').html(), ajax.removeMarkView);
            });
        },
        initViewMarkView: function() {
            $(document).on('click', 'a[id^="viewMarkViewBtn"]', function() {
                var sequence = $(this).attr('data-sequence');
                ajax.getMarkView(sequence, false, true);
            });
        },
        initViewImageMarkView: function() {
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
        showMarkViewSection: function() {
            $('#markViewSection').modal('show');
            uploadViews.addViewController.init();
        },
        showMarkViewEditDeleteSection: function(sequence,isDelete, isView) {
            $('#markViewSection').modal('show');
            $('#markViewSection').on('shown.bs.modal', function(){
                uploadViews.editViewController.scrollSelected();
            });
            uploadViews.editViewController.init(sequence, isDelete, isView);

        },
        hideMarkViewSection: function() {
            $('#markViewSection').modal('hide');
        },
        updateMarkViewSection: function(html) {
            $('#markViewSection').html(html);
        },
        updateMarkViewsList: function(html) {
            $('#markViews').html(html);
        }
    };

    var utils = {
        errorSectionsSelectors: ['#markViewSection', '#markViews']
    };

    var ajax = {
        viewMarkView: function(id) {
            commonAjax.commonCall({
                url: 'viewMarkView.htm',
                data: 'id=' + id,
                errorMetadata: {selectorContainerErrorMessage: '#markViewGenericErrorDiv', selectorContainerError: '#markViews'},
                success: manipulation.updateMarkViewSection,
                complete: manipulation.showMarkViewSection,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        addMarkView: function() {
            commonAjax.commonCall({
                url: 'addMarkView.htm',
                errorMetadata: {selectorContainerErrorMessage: '#markViewGenericErrorDiv', selectorContainerError: '#markViewSection'},
                success: function(html) {
                    // We check if the controller has returned the same view, this will mean there are errors
                    var error = $('<div>'+html+'<div>').find('.flMessageError');
                    if(error.length){
                        // We come back to paint the same page
                        $('#warningModal').modal('show').find('#warningPlaceholder').html(error.html());
                    } else {
                        manipulation.updateMarkViewSection(html)
                        manipulation.showMarkViewSection();
                    }
                },
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        saveMarkView: function() {
            var data = uploadViews.utils.getFilesData();
            $.ajax({
                url: 'saveMarkView.htm?flowKey='+$('[name="flowKey"]').val(),
                type:"POST",
                contentType: "application/json; charset=utf-8",
                data: data, //Stringified Json Object
                async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
                cache: false,    //This will force requested pages not to be cached by the browser
                processData:false, //To avoid making query String instead of JSON
                success: function(html) {
                    // We check if the controller has returned the same view, this will mean there are errors
                    if($('<div>'+html+'<div>').find('.flMessageError').length){
                        // We come back to paint the same page
                        uploadViews.utils.showErrorsForm(html);
                    } else {
                        manipulation.updateMarkViewsList(html);
                        manipulation.hideMarkViewSection();
                    }
                },
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        getMarkView: function(sequence, isDelete, isView) {
            commonAjax.commonCall({
                url: 'getMarkView.htm',
                errorMetadata: {selectorContainerErrorMessage: '#markViewGenericErrorDiv', selectorContainerError: '#markViews'},
                success: manipulation.updateMarkViewSection,
                complete: function(){
                    isDelete = isDelete || false;
                    isView = isView || false;
                    manipulation.showMarkViewEditDeleteSection(sequence, isDelete, isView);
                } ,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        },
        removeMarkView: function(id) {
            commonAjax.commonCall({
                url: 'removeMarkView.htm',
                data: 'sequences=' + uploadViews.editViewController.getSelectedViews(),
                success: function(html){
                    //Error
                    if($('<div>'+html+'<div>').find('.flMessageError').length){
                        var $container = $('#markViewSection')
                        var error = $('<div>'+html+'<div>').find('.flMessageError').wrap('<p/>').parent().html();
                        $container.prepend(error);
                    }else{
                        manipulation.updateMarkViewsList(html);
                        manipulation.hideMarkViewSection();
                    }

                } ,
                errorSectionsSelectors: utils.errorSectionsSelectors
            });
        }
    };

}.call(tm.application.marks.views, tm.application.commonAjax);