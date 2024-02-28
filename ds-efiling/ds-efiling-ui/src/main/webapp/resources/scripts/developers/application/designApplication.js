'use strict';

var ds = {};
ds.efiling = {};
ds.efiling.application = {};
ds.efiling.application.oneForm = {};

!function() {
	
	var self = this;
	
	var initializations = {
		initSessionTimeout: function() {
			$(document).ready(function(){
				$.removeCookie("sessionExpired");
			});

			$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
				if (jqXHR.status == 902) {
					alert($("#session_time_out").html());
					submitMainForm('Refresh');
				}
			});
			$(document).ajaxComplete(function( event, xhr, settings ) {
				if($.cookie("sessionExpired")!==undefined) {
					var dest = $.cookie("sessionExpired");
					showModal($("#sessionExpired").html(), function () {
						$.removeCookie("sessionExpired");
						window.location = dest;
					});
				}
			});
		},
		initExportApplicationToPC: function() {
			$(document).on("click", ".saveToPCButton", function() {
				submitMainForm('SaveLocally');
			});
		},
		initExportApplicationXml: function() {
			$(document).on("click", ".saveButton", function() {
				$.ajax({
					url : "saveApplication.htm",
					type : "POST",
					cache : false,
					success : function(html) {
						showMessageModal(html);
					}
				});
			});
		},
		initImportApplicationXml: function() {
            // $(document).on("click", "#loadApplicationXML_trigger", function () {
            //     showConfirmModal($("#importApplicationXml_willRedirectToHome").html(), function(){
            //         $("input[name='loadApplicationXML']").click();
            //     });
            // });
            $(document).on("mousedown", ".uploadLoadApplication", function (event) {
                event.preventDefault();
                $(this).trigger("click");
            });

            $(document).on("click", ".uploadLoadApplication", function () {
				$(this).fileupload({
				    dataType:"iframe",
				    fileInput:$(this),
				    done:function (e, data) {
			            $.each(data.result, function (index, file) {
							var text = file.body.innerHTML;
			            	if(text == null || text == "" ) {
								text = file.body.innerText;
			            	}

							if(text.indexOf(".htm?") != -1) {
								text = file.body.innerText;
							}
			            	
			            	if (text.indexOf("flMessageError") != -1) {
								if(text.toLowerCase().indexOf("sessionexpired") == -1) {
									showMessageModal(text);
									$("#messageModal .flMessageError").removeClass("flMessageError");
								}
			            	} else if (text.indexOf("Failed") != -1) {
			            		showMessageModal($("#importApplicationXml_contentNotValid").html());
			            	} else {
								document.location = text.replace(/&amp;/g, '&');
							}
			            });
				    }
				});
			});
		},
		initPrintApplication: function() {
			$(document).on("click", ".printButton", function() {
				self.hasSelectedLanguageWithCallback(this, submitMainForm, 'PrintDraftReceipt');
			});
		},
		initResetButton: function() {
			$(document).on("click", ".resetButton", function() {
			    var url = $(this).attr("data-url");
			    showConfirmModal($("#resetApplicationConfirmation").html(), function() {
			        location.href = url;
			    });
			});
		}
	};
	
	this.init = function(options) {
		fspLog('init design application...');
		options = options || {};
		
		// Is application in one form mode?
		this.isOneForm = function() {
			return options.oneForm || false;
		};

		// Init ajax timeout.
		initializations.initSessionTimeout();
		
		// Init right buttons.
		initializations.initExportApplicationToPC();
		initializations.initExportApplicationXml();
		initializations.initImportApplicationXml();
		initializations.initResetButton();
		initializations.initPrintApplication();
		
		// Init languages.
		this.languages.init();
		
		// Init designs views.
		this.designs.views.init();
		
		// Init divisional application.
		this.divisionalApplication.init();
		
		// Init entitlemenet.
		this.entitlement.init();
		
		// In the future migrate the rest of js functionality.
		fspLog('...design application initiated');
	};
	
	this.hasSelectedLanguageWithCallback = function(context, callback /*, 0..n args */) {
		if (ds.efiling.application.languages.hasSelectedLanguage()) {
			var params = Array.prototype.slice.call(arguments, 2);
			callback.apply(context, params);
		} else {
			showMessageModal($("#selectLanguageFirst").html());
		}
	};
	
	this.hasAtLeastOneDesignWithCallbacks = function(context, callbackOK, callbackKO) {
		if (this.hasDesigns()) {
			callbackOK.call(context);
		} else {
			showMessageModal($("#design_youNeedAtLeastOneDesign").html());
			if (!!callbackKO) {
				callbackKO.call(context);
			}
		}
	};
	
	this.hasDivisionalApplication = function() {
		return $('#divisionalApplicationTable tr').length > 1;
	};
	
	this.hasClaims = function() {
		return $("#claimTableContainer tr").length > 1;
	};
	
	this.hasDesigners = function() {
		return $("#userDataDesigners tr").length > 1 || $("#addedDesigners tr").length > 1;
	};
	
	this.getNumberOfDesigns = function() {
		return $('#designsListDiv .designCountRow').length;
	};
	
	this.hasDesigns = function() {
		return this.getNumberOfDesigns() > 0;
	};
	
	this.hasOnlyOneDesign = function() {
		return this.getNumberOfDesigns() == 1;
	};
	
	this.oneForm.isDesignLinkedInSomeForm = function(designId) {
 		return self.isOneForm() &&
				(self.hasDivisionalApplication() || self.hasClaims() || self.hasDesigners()) &&
					(self.hasOnlyOneDesign() ||
												$.ajax({
													url: 'isDesignLinked.htm',
													data: 'id=' + designId,
													type: 'GET',
													async: false
												}).responseText === "true");
	};
	
	this.oneForm.refreshTablesWithDesigns = function() {
		if (self.isOneForm()) {
			showLoadingScreen();
			
			// Refresh divisional application table
			if (self.hasDivisionalApplication()) {
				$.ajax({
					url: 'getDivisionalApplicationTable.htm',
					type: 'POST',
					async: false,
					success: function(html) {
						$("#divisionalApplicationTableContainer").html(html);
					}
				});
			}
			
			// Refresh claims table
			if (self.hasClaims()) {
				$.ajax({
					url: 'getClaimsTable.htm',
					type: 'POST',
					async: false,
					success: function(html) {
						$("#claimTableContainer").html(html);
					}
				});	
			}
			
			// Refresh designers table
			if (self.hasDesigners()) {
				$.ajax({
					url: 'getDesignersTable.htm',
					type: 'POST',
					async: false,
					success: function(html) {
						$("#designerCardList").html(html);
					}
				});
			}
			
			hideLoadingScreen();
		}
	};
	
	this.oneForm.refreshDesignersTableWithAssociatedApplicant = function() {
		if (self.isOneForm()) {
			showLoadingScreen();
			$.ajax({
				url: 'addDesignerFromApplicant.htm',
				type: 'POST',
				async: false,
				success: function(html) {
					$("#designerCardList").html(html);
				}
			});
			hideLoadingScreen();
		}
	};
	
	this.oneForm.refreshApplicantsTableWithAssociatedDesigner = function() {
		if (self.isOneForm()) {
			showLoadingScreen();
			$.ajax({
				url: 'updateApplicantFromDesigner.htm',
				type: 'POST',
				async: false,
				success: function(html) {
					$("#applicantCardList").html(html);
				}
			});
			hideLoadingScreen();
		}
	};
	
}.call(ds.efiling.application);
