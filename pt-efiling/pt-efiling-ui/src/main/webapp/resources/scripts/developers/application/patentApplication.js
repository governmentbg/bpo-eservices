'use strict';

var pt = {};
pt.efiling = {};
pt.efiling.application = {};
pt.efiling.application.oneForm = {};

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
        },
        initApplicationKindChange: function () {
            $(document).on("change", "#applicationKindForm input[name='mainForm.applicationKind']", function () {
                submitMainForm("changeApplicationKind", true);
            });
        }
    };

    this.init = function(options) {
        fspLog('init patent application...');
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
        initializations.initApplicationKindChange();

        // Init languages.
        this.languages.init();

        // Init designs views.
        this.patent.views.init();

        // Init entitlemenet.
        this.entitlement.init();
        //Init declaration
        this.declaration.init();

        // In the future migrate the rest of js functionality.
        fspLog('...patent application initiated');
    };


    this.hasSelectedLanguageWithCallback = function(context, callback /*, 0..n args */) {
        if (pt.efiling.application.languages.hasSelectedLanguage()) {
            var params = Array.prototype.slice.call(arguments, 2);
            callback.apply(context, params);
        } else {
            showMessageModal($("#selectLanguageFirst").html());
        }
    };


}.call(pt.efiling.application);

$(document).on("change","#certificateRequestedIndicator",  function() {
    $.ajax({
        url : "changeCertificateRequestedIndicator.htm",
        data: "indicatorValue=" + $(this).is(":checked"),
        type : "POST",
        success : function(html) {
            updateFeesInformation();
        }
    });
});
