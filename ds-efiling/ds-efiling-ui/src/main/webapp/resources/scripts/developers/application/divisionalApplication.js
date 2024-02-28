'use strict';

ds.efiling.application.divisionalApplication = {};
	
!function(commonAjax) {

	this.init = function() {
		fspLog('   init divisional application...');
		initializations.yesAndNoButtons();
		initializations.cancelButtons();
		initializations.addButtons();
		initializations.editButton();
		initializations.removeButton();
		fspLog('   ...divisional application initiated');
	};
	
	var initializations = {
		yesAndNoButtons: function() {
			if ($('#divisionalApplicationTable').length > 0) {
				// Disable Yes and No buttons and remove click event
				$('#claimDivisionalApplicationYesBtn').addClass('disabled');
				$('#claimDivisionalApplicationNoBtn').addClass('disabled');
				$(document).off('click', '#claimDivisionalApplicationYesBtn');
				$(document).off('click', '#claimDivisionalApplicationNoBtn');
			} else {
				// By default No button must be selected
				$('#divisionalApplicationNoItem').addClass('active');
				$('#claimDivisionalApplicationNoBtn').removeClass('disabled');
				$('#divisionalApplicationYesItem').removeClass('active');
				$('#claimDivisionalApplicationYesBtn').removeClass('disabled');
				this.yesButton();
				this.noButton();
			}
		},
		yesButton: function() {
			var self = this;
			$(document).on('click', '#claimDivisionalApplicationYesBtn', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, ajax.show, initializations.yesAndNoButtons);
				} else {
					ajax.show();
				}
			});
		},
		noButton: function() {
			$(document).on('click', '#claimDivisionalApplicationNoBtn', function() {
				$('#divisionalApplicationTab').hide();
			});
		},
		cancelButtons: function() {
			$(document).on('click', '.cancelDivisionalApplicationButton', function() {
				$('#divisionalApplicationTab').hide();
				$('.divisionalApplicationRow').removeClass('active');
				initializations.yesAndNoButtons();
			});
		},
		addButtons: function() {
			$(document).on('click', '.addDivisionalApplication', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(this, ajax.add);
				} else {
					ajax.add();
				}
			});
		},
		editButton: function() {
			$(document).on('click', '.editDivisionalApplication', function() {
				var id = $(this).attr('data-id');
				ajax.edit(id, $(this).closest('tr'));
			});
		},
		removeButton: function() {
			$(document).on('click', '.removeDivisionalApplication', function() {
				var id = $(this).attr('data-id');
				var onConfirmYes = partial(ajax.remove, id);
				showConfirmModal($('#divisionalApplication_deleteConfirmation').html(), onConfirmYes);
			});
		}
	};
	
	var ajax = {
		show: function() {
			commonAjax.commonCall({
				url: 'addDivisionalApplication.htm',
				success: function(html) {
					$("#divisionalApplicationTab").html(html);
					$("#divisionalApplicationTab").show();
				},
				error: function (xmlHttpRequest, textStatus, errorThrown) {
					$("#divisionalApplicationTab").html(errorThrown);
					$("#divisionalApplicationTab").show();	
				}
			});
		},
		add: function() {
			DesignsLink.selectOptionsDivisionalApplicationLists();
			commonAjax.commonCall({
				url: 'saveDivisionalApplication.htm',
				type: 'POST',
				data: $('#formDivisionalApplication1').serialize() + '&' + $('#formDivisionalApplication2').serialize(),
				success: function(html) {
					// We check if the controller has returned the same view, this will mean there are errors
					if ($('#formDivisionalApplication1', $(html)).length != 0) {
						// We come back to paint the same page
						$("#divisionalApplicationTab").html(html);
					} else {
						// Paint the table
						$("#divisionalApplicationTableContainer").html(html);
						// Hide the form
						$("#divisionalApplicationTab").hide();
						// If edit divisional application remove row class 
						$('.divisionalApplicationRow').removeClass('active');
						// Configure the buttons
						initializations.yesAndNoButtons();
						callGetFastTrackFails();
					}
				},
				error: function (xmlHttpRequest, textStatus, errorThrown) {
					$("#divisionalApplicationTab").html(errorThrown);
					$("#divisionalApplicationTab").show();	
				}
			});
		},
		remove: function(id) {
			commonAjax.commonCall({
	    		url: 'removeDivisionalApplication.htm',
	    		data: 'id=' + id,
	    		success: function(html) {
					// Paint the empty table
					$("#divisionalApplicationTableContainer").html(html);
					// Hide form, for example, edit and then delete
					$('#divisionalApplicationTab').hide();
					// Configure the buttons
					initializations.yesAndNoButtons();
					callGetFastTrackFails();
	    		},
	    		error: function (xmlHttpRequest, textStatus, errorThrown) {
					$("#divisionalApplicationTab").html(errorThrown);
					$("#divisionalApplicationTab").show();	
				}
	    	});
	    },
		edit: function(id, row) {
			commonAjax.commonCall({
				url: 'getDivisionalApplication.htm',
				data: 'id=' + id,
				success: function(html) {
					$("#divisionalApplicationTab").html(html);
					$("#divisionalApplicationTab").show();
					row.addClass('active');
				},
				error: function(xmlHttpRequest, textStatus, errorThrown) {
					$("#divisionalApplicationTab").html(errorThrown);
					$("#divisionalApplicationTab").show();	
				}
			});
		}
	};
	
}.call(ds.efiling.application.divisionalApplication, ds.efiling.application.commonAjax);
