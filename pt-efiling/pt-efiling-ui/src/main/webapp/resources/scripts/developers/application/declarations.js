'use strict';

pt.efiling.application.declaration = {};

pt.efiling.application.declaration.nav = {
    declaration_smallCompany: "changeSmallCompanyDeclaration.htm",
    licenceAvailability: "changeLicenceAvailability.htm",
    declaration_examinationRequested: "changeExaminationRequested.htm",
    anticipationOfPublication: "changeAnticipationOfPublication.htm",
};

!function() {

    function DeclarationKind(idDiv, idDeselectOnHide) {
        this.idDiv = idDiv;
        this.idDeselectOnHide = idDeselectOnHide;
    }

    DeclarationKind.prototype.hideDiv = function() {
        if (!!this.idDiv) {
            $('#' + this.idDiv).hide();
        }
    };

    DeclarationKind.prototype.showDiv = function() {
        if (!!this.idDiv) {
            $('#' + this.idDiv).show();
        }
    };

    DeclarationKind.prototype.deselect = function(){
        if (!!this.idDeselectOnHide) {
            if($('#' + this.idDeselectOnHide).is(":checked")){
                $('#' + this.idDeselectOnHide).removeAttr("checked");
                $('#' + this.idDeselectOnHide).trigger("change");
            }
        }
    };

    var options = [];

    var manipulations = {
        clickInOption: function() {
            if ($(this).is(':checked')) {
               options[this.id].showDiv();
            } else {
                options[this.id].hideDiv();
                options[this.id].deselect();
            }
        }
    };

    var initializations = {
        initOptions: function() {
            options['declaration_inventorsAreReal'] = new DeclarationKind('inventorsAreReal_div', null);
            options['declaration_smallCompany'] = new DeclarationKind('smallCompany_div', null);
            options['declaration_examinationRequested'] = new DeclarationKind('examinationRequested_div', 'anticipationOfPublication');
            options['declaration_postponementOfPublication'] = new DeclarationKind('postponementOfPublication_div', null);
        },
        initCheckboxes: function() {
            $('input[type="checkbox"][id^="declaration_"]').click(manipulations.clickInOption);
        },
        initAjaxSubmissions: function () {
            $(".ajaxSubmitDeclarationOption").each(function(){
                var id = $(this).attr("id");
                $(document).on("change", "#"+id, function () {
                    $.ajax(
                        {
                            url: pt.efiling.application.declaration.nav[id],
                            method: "POST",
                            success: function(){
                                getFeesInformation();
                            },
                            error:function (error)
                            {
                                genericHandleError("An error occured while processing your request. Please try again later.",
                                    "#"+id, true);
                            }
                        }
                    )
                });
            });
        }
    };

    this.init = function() {
        fspLog('   init declarations...');
        initializations.initOptions();
        initializations.initCheckboxes();
        initializations.initAjaxSubmissions();
        fspLog('   ...declarations initialized');
    };

}.apply(pt.efiling.application.declaration);

