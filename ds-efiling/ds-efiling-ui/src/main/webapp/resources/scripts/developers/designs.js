var Designs = {

    ui: {
        $designsListDiv: null,
        $addAnotherDesignBtn: null,
        $addDesignForm: null
    },

    init: function() {
      this.ui.$designsListDiv = $('#designsListDiv');
      this.ui.$addAnotherDesignBtn = $('#addAnotherDesignBtn');
      this.ui.$addDesignForm = $('#addDesignForm');
      this.initDeferredPublicationToggle();
      this.initDeferredPublicationDesignToggle();
      this.initViewDesignButton();
      this.initEditDesignButton();
      this.initDeleteDesignButton();
      this.initAddAnotherDesignButton();
      this.initAddDesignButton();
      this.initCancelDesignButton();
      this.initDuplicateDesignButton();
    },

    initDeferredPublicationToggle: function() {
       $defermentTillDateDiv = $('#defermentTillDateDiv');
       $defermentTillDate = $('#defermentTillDate');
       
       $(document).on('click', '#requestDeferredPublication', function(ev) {
            if ($(this).is(':checked')) {
                $defermentTillDateDiv.show();
            } else {
                $defermentTillDateDiv.hide();
                $defermentTillDate.val('');
            }
       });
    },
    
    initDeferredPublicationDesignToggle: function() {
        $(document).on('click', '#requestDeferredPublicationDesign', function(ev) {
             if ($(this).is(':checked')) {
            	 $('#defermentTillDateDesignDiv').show();
             } else {
            	 $('#defermentTillDateDesignDiv').hide();
            	 $('#defermentTillDateDesign').val('');
             }
        });
     },

    initEditDesignButton: function() {
        $(document).on('click', '#editDesignBtn', function(ev) {
        	var designId = $(this).attr("data-val");
    		Designs.ui.$designsListDiv.find('tr').removeClass('active');
            $('tr[class$="designRow' + designId + '"]').addClass('active');
            Designs.ui.$addAnotherDesignBtn.addClass('disabled');
            Designs.ajax.loadAddDesignForm(designId, $(this).attr("data-rownum"));
        });
    },

    initViewDesignButton: function() {
        $(document).on('click', '#viewDesignBtn', function(ev) {
        	var designId = $(this).attr("data-val");
            Designs.ui.$designsListDiv.find('tr').removeClass('active');
            $('tr[class$="designRow' + designId + '"]').addClass('active');
            Designs.ui.$addAnotherDesignBtn.addClass('disabled');
            Designs.ajax.viewDesignForm(designId, $(this).attr("data-rownum"));
        });
    },
    
    initDeleteDesignButton: function() {
        $(document).on('click', '#removeDesignBtn', function(ev) {
        	var designId = $(this).attr('data-val');
        	if (ds.efiling.application.oneForm.isDesignLinkedInSomeForm(designId)) {
        		showWarningModal($("#design_cannotDelete").html());
        	} else if (ds.efiling.application.isOneForm() && anyFormsOpen()) {
            	showMessageModal(createFormsOpenText());
        	} else {
        		var onConfirmYes = partial(Designs.ajax.removeDesign, designId);
        		showConfirmModal($('#design_deleteConfirmation').html(), onConfirmYes);
        	}
        });
    },
    
    initDuplicateDesignButton: function() {
    	$(document).on('click', '#duplicateDesignBtn', function(ev) {
    		if (ds.efiling.application.isOneForm() && anyFormsOpen()) {
        		showMessageModal(createFormsOpenText());
        	} else {
        		var designId = $(this).attr('data-val');
        		$('#designIdToDuplicate').val(designId);
        		$('#duplicateDesignForm').modal('show');
        	}
    	});
    	
    	$(document).on('click', '#duplicateDesignOkBtn', function() {
    		var designId = $('#designIdToDuplicate').val();
    		var times = $("#timesToDuplicate").val();
    		if ($.isNumeric(times) && times > 0) {
    			Designs.ajax.duplicateDesign(designId, times);
    		}
    	});
    },
    
    initAddAnotherDesignButton: function() {
        $(document).on('click', '#addAnotherDesignBtn:not(.disabled)', function(ev) {
        	if (ds.efiling.application.isOneForm() && anyFormsOpen()) {
        		showMessageModal(createFormsOpenText());
        	} else {
        		if (Designs.ui.$addDesignForm.is(':hidden')) {
                    Designs.ui.$addAnotherDesignBtn.addClass('active');
                    Designs.ui.$addDesignForm.html('');
                    Designs.ajax.loadAddDesignForm();
                } else {
                    Designs.ui.$addDesignForm.hide();
                    Designs.ui.$addAnotherDesignBtn.removeClass('active');
                }
        	}
        });
    },

    initAddDesignButton: function() {
        $(document).on('click', '#addDesignBtn', function(ev) {
       		if ( $('#distinctiveFeatures').length > 0 && $('#distinctiveFeatures').val().trim() != "") {
    			showMessageModal($("#design_pubDistinctFeatFee").html()); 
    		}   		
            showLoadingScreen();
            Designs.ajax.submitDesignForm();
        });
    },

    initCancelDesignButton: function() {
        $(document).on('click', '#cancelDesignBtn', function(ev) {
            Designs.ui.$addAnotherDesignBtn.removeClass('active disabled');
            Designs.ui.$designsListDiv.find('tr').removeClass('active');
            Designs.ui.$addDesignForm.hide();
            scrollToContainer("#designsListDiv");
        });
    },

    utils: {
    	removeDesignPreviousErrors: function() {
    		removePreviousErrors('#designsErrorDiv');
    	}
    },
    
    nav: {
        add: "addDesign.htm",
        edit: "addDesign.htm",
        remove: "removeDesign.htm",
        duplicate: "duplicateDesign.htm",
        view: "viewDesign.htm"
    },

    ajax: {
        removeDesign: function(designId) {
        	Designs.utils.removeDesignPreviousErrors();
            $.ajax({
                data:"id=" + designId,
                url:Designs.nav.remove,
                success:function (html) {
                    Designs.ui.$designsListDiv.html(html);
                    var designCount = ds.efiling.application.getNumberOfDesigns();
                    $('#designCount').html(designCount);
                    ds.efiling.application.oneForm.refreshTablesWithDesigns();
                    getFeesInformation();
                    callGetFastTrackFails();
                },
                error:function (error) {
                    genericHandleError($('#design_form_error_load').html(), "#designsErrorDiv", true);
                }
            });
        },

        duplicateDesign: function(designId, times) {
        	Designs.utils.removeDesignPreviousErrors();
            $.ajax({
                data:"id=" + designId + "&times=" + times,
                url:Designs.nav.duplicate,
                success: function (html) {
                    Designs.ui.$designsListDiv.html(html);
                    var designCount = ds.efiling.application.getNumberOfDesigns();
                    $('#designCount').html(designCount);
                    $("#timesToDuplicate").val('');
                    getFeesInformation();
                    callGetFastTrackFails();
                    ds.efiling.application.oneForm.refreshTablesWithDesigns();
                    if (html.indexOf('maximumEntitiesReached') != -1) {
                		genericHandleError($('#design_form_error_duplicate_reach_max').html(), "#designsErrorDiv", true);
                	}
                },
                error:function (error) {
                    genericHandleError($('#design_form_error_load').html(), "#designsErrorDiv", true);
                },
                complete: function() {
                	$('#duplicateDesignForm').modal('hide');
                }
            });
        },
        
        viewDesignForm: function(designId, rowNumber) {
        	Designs.utils.removeDesignPreviousErrors();
        	$.ajax({
                url: Designs.nav.view,
                data:"id=" + designId,
                success:function (html) {
                    Designs.ui.$addDesignForm.html(html);
                    $("#designCurrentNumber").html(rowNumber);
                    Designs.ui.$addDesignForm.show();
                },
                error:function (error) {
                    Designs.ui.$addAnotherDesignBtn.removeClass('active disabled');
                    Designs.ui.$designsListDiv.find('tr').removeClass('active');
                    Designs.ui.$addDesignForm.hide();
                    genericHandleError($('#design_form_error_load').html(), "#designsErrorDiv", true);
                }
            });
        },
        
        loadAddDesignForm: function(designId, rowNumber) {
            //DS Class Integration changes start
            setTimeout(function(){Locarno.ajax.getAllowedLocarnoClasses();},500);
            //DS Class Integration changes end
        	Designs.utils.removeDesignPreviousErrors();
            if (rowNumber === void 0) {
            	rowNumber = ds.efiling.application.getNumberOfDesigns() + 1;
            }
            $.ajax({
                url:Designs.nav.add,
                data:"id=" + ((designId === void 0) ? '' : designId),
                success:function (html) {
                    Designs.ui.$addDesignForm.html(html);
                    $("#designCurrentNumber").html(rowNumber);
                    Designs.ui.$addDesignForm.show();
                    //DS Class Integration changes start
                    $('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                    //DS Class Integration changes end
                },
                error:function (error) {
                    Designs.ui.$addAnotherDesignBtn.removeClass('active disabled');
                    Designs.ui.$designsListDiv.find('tr').removeClass('active');
                    Designs.ui.$addDesignForm.hide();
                    genericHandleError($('#design_form_error_load').html(), "#designsErrorDiv", true);
                }
            });
        },
        submitDesignForm: function() {
        	Designs.utils.removeDesignPreviousErrors();
            var dataToSend = $("#formAddDesign").serialize();
            fspLog('Mando: ' + dataToSend);
            $.ajax({
                url:Designs.nav.add,
                data: dataToSend,
                type: 'POST',
                success:function (html) {
                    // check if returned form is a form containing errors
                    if ($("input#formReturned", $(html)).val() == "true") {
                        // then display the errors
                        Designs.ui.$addDesignForm.html(html);
                        var rowNumber = ds.efiling.application.getNumberOfDesigns() + 1;
                        $("#designCurrentNumber").html(rowNumber);
                        return;
                    }
                    // otherwise, just refresh designs list
                    Designs.ui.$designsListDiv.html(html);
                    var designCount = ds.efiling.application.getNumberOfDesigns();
                    $('#designCount').html(designCount);
                    Designs.ui.$addDesignForm.hide();
                    Designs.ui.$addAnotherDesignBtn.removeClass('active disabled');
                    Designs.ui.$designsListDiv.find('tr').removeClass('active');
                    scrollToContainer("#designsListDiv");                    
                    getFeesInformation();
                    callGetFastTrackFails();

                    ds.efiling.application.oneForm.refreshTablesWithDesigns();
                },
                error:function (error) {
                    genericHandleError($('#design_form_error_submit').html(), "#designErrorDiv", true);
                },
                complete: function() {
                	hideLoadingScreen();	
                }
            });
        }
    }
};

$(document).ready(function() {
    Designs.init();
});


