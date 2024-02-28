'use strict';
var tm ={};
tm.application = {};
tm.application.commonAjax = {};

!function() {
	
	function removeSectionErrors(errorSectionsSelectors) {
		errorSectionsSelectors = [].concat(errorSectionsSelectors);
		for (var i = 0; i < errorSectionsSelectors.length; i++) {
			removePreviousErrors(errorSectionsSelectors[i]);
		}
	}

	function handleError(errorMetadata) {
		genericHandleError($(errorMetadata.selectorContainerErrorMessage).html(), errorMetadata.selectorContainerError, true);
	}

	this.commonCall = function(wrapper) {
		if (!!wrapper.errorSectionsSelectors) {
			removeSectionErrors(wrapper.errorSectionsSelectors);
		}
		showLoadingScreen();
		$.ajax({
			url: wrapper.url,
			type: wrapper.type || 'GET',
			data: wrapper.data || '',
			success: function(data, textStatus, jqXHR) {
				hideLoadingScreen();
				if (!!wrapper.success) {
					wrapper.success(data, textStatus, jqXHR);	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				hideLoadingScreen();
				if (!!wrapper.error) {
					wrapper.error(jqXHR, textStatus, errorThrown);
				} else if (!!wrapper.errorMetadata) {
					handleError(wrapper.errorMetadata, jqXHR, textStatus, errorThrown);
				}
			},
			complete: function(jqXHR, textStatus) {
				hideLoadingScreen();
				if (!!wrapper.complete) {
					wrapper.complete(jqXHR, textStatus);
				}
			}
		});
	};

}.apply(tm.application.commonAjax);

