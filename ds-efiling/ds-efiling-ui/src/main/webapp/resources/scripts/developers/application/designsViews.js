'use strict';

ds.efiling.application.designs = {};
ds.efiling.application.designs.views = {};

!function(commonAjax) {

	this.init = function() {
		fspLog('   init designs views...');
		initializations.initAddDesignView();
		initializations.initSaveDesignViews();
		initializations.initEditDesignView();
		initializations.initRemoveDesignView();
		initializations.initViewDesignView();
		initializations.initViewImageDesignView();
		fspLog('   ...designs views initiated');
	};
	
	var initializations = {
		initAddDesignView: function() {
	        $(document).on('click', '#addViewDesignBtn', function() {
	        	ajax.addDesignView();
	        });
		},
		initSaveDesignViews: function() {
			$(document).on('click', '#saveDesignViewsBtn', function() {
				ajax.saveDesignView();
			});
		},
		initEditDesignView: function() {
			$(document).on('click', 'a[id^="editDesignViewBtn"]', function() {
				var sequence = $(this).attr('data-sequence');
				ajax.getDesignView(sequence);
			});
		},
		initRemoveDesignView: function() {
			$(document).on('click', 'a[id^="removeDesignViewBtn"]', function() {
				var sequence = $(this).attr('data-sequence');
				ajax.getDesignView(sequence, true);
			});
			$(document).on('click', '#deleteDesignViewsBtn', function() {
				showConfirmModal($('#design_view_deleteConfirmation').html(), ajax.removeDesignView);
			});
		},
		initViewDesignView: function() {
			$(document).on('click', 'a[id^="viewDesignViewBtn"]', function() {
				var sequence = $(this).attr('data-sequence');
				ajax.getDesignView(sequence, false, true);
			});
		},
		initViewImageDesignView: function() {
			$(document).on('click', 'img[id^="viewImage"]', function() {
				showLoadingScreen();
				var documentId = $(this).attr('data-file-documentId');
				var name = $(this).attr('data-file-name');
				$('#modalViewImageTitle').html(name);
				$('#modalViewImageImg').attr("src", 'getDocument.htm?documentId='+ documentId);
				$('#viewImageModalDiv').modal('show');
				hideLoadingScreen();
			});
		},
		initFileInfoPopover : function (show, initialize) {
			if(!show){
				$('[rel="file-info-tooltip"]').hide();
			} else if(initialize){
				$('[rel="file-info-tooltip"]').popover();
			}
		}
	};
	
	var manipulation = {
		showDesignViewSection: function() {
			$('#designViewSection').modal('show');
			uploadViews.addViewController.init();
		},
		showDesignViewEditDeleteSection: function(sequence,isDelete, isView) {
			$('#designViewSection').modal('show');
			$('#designViewSection').on('shown.bs.modal', function(){
				uploadViews.editViewController.scrollSelected();
			});
			uploadViews.editViewController.init(sequence, isDelete, isView);

		},
		hideDesignViewSection: function() {
			$('#designViewSection').modal('hide');
		},
		updateDesignViewSection: function(html) {
			$('#designViewSection').html(html);
		},
		updateDesignViewsList: function(html) {
			$('#designViews').html(html);
		}
	};
	
	var utils = {
		errorSectionsSelectors: ['#designViewSection', '#designViews']
	};
	
	var ajax = {
		viewDesignView: function(id) {
			commonAjax.commonCall({
				url: 'viewDesignView.htm',
				data: 'id=' + id,
				errorMetadata: {selectorContainerErrorMessage: '#designViewGenericErrorDiv', selectorContainerError: '#designViews'},
				success: manipulation.updateDesignViewSection,
				complete: manipulation.showDesignViewSection,
				errorSectionsSelectors: utils.errorSectionsSelectors
			});
	    },
		addDesignView: function() {
			commonAjax.commonCall({
				url: 'addDesignView.htm',
				errorMetadata: {selectorContainerErrorMessage: '#designViewGenericErrorDiv', selectorContainerError: '#designViewSection'},
				success: function(html) {
					// We check if the controller has returned the same view, this will mean there are errors
					var error = $('<div>'+html+'<div>').find('.flMessageError');
					if(error.length){
						// We come back to paint the same page
						$('#warningModal').modal('show').find('#warningPlaceholder').html(error.html());
					} else {
						manipulation.updateDesignViewSection(html)
						manipulation.showDesignViewSection();
						initializations.initFileInfoPopover(true, true);
					}
				},
				errorSectionsSelectors: utils.errorSectionsSelectors
			});
		},
		saveDesignView: function() {
			var data = uploadViews.utils.getFilesData();
			$.ajax({
				url: 'saveDesignView.htm?flowKey='+$('[name="flowKey"]').val(),
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
						manipulation.updateDesignViewsList(html);
						manipulation.hideDesignViewSection();
					}
				},
				errorSectionsSelectors: utils.errorSectionsSelectors
			});
		},
		getDesignView: function(sequence, isDelete, isView) {
			commonAjax.commonCall({
				url: 'getDesignView.htm',
				errorMetadata: {selectorContainerErrorMessage: '#designViewGenericErrorDiv', selectorContainerError: '#designViews'},
				success: manipulation.updateDesignViewSection,
				complete: function(){
					isDelete = isDelete || false;
					isView = isView || false;
					manipulation.showDesignViewEditDeleteSection(sequence, isDelete, isView);
					initializations.initFileInfoPopover(!isDelete, !isView);
				} ,
				errorSectionsSelectors: utils.errorSectionsSelectors
			});
	    },
	    removeDesignView: function(id) {
	    	commonAjax.commonCall({
	    		url: 'removeDesignView.htm',
	    		data: 'sequences=' + uploadViews.editViewController.getSelectedViews(),
	    		success: function(html){
					//Error
					if($('<div>'+html+'<div>').find('.flMessageError').length){
						var $container = $('#designViewSection')
						var error = $('<div>'+html+'<div>').find('.flMessageError').wrap('<p/>').parent().html();
                    	$container.prepend(error);
					}else{
						manipulation.updateDesignViewsList(html);
						manipulation.hideDesignViewSection();
					}

				} ,
	    		errorSectionsSelectors: utils.errorSectionsSelectors
	    	});
	    }
	};

}.call(ds.efiling.application.designs.views, ds.efiling.application.commonAjax);