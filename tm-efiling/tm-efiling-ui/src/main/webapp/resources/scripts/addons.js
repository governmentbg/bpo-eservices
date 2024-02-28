// --- Functions start --- //

function activateBreadcrumbSteps() {
    // navigation --> add class for previous steps
   // $(".breadcrumb-steps").find(".active").prevAll().addClass("done");

// uncomment the following code to make the breadcrumb clickable!!!
/*
    // create a "link" to the previous clicked step
    $(".breadcrumb-steps li.done").on("click", function(){
        var loc = $(this).find("span").text();
        var url = "wizard.htm?execution=e2s" + loc;
        $(location).attr('href', url);
    });
*/
}

function setToolTip(selector, tt, dataId) {
    var toolTipSecLangWizard = '';

    toolTipSecLangWizard += '<a rel="classes-help-tooltip" class="tooltip-icon" data-original-title=""></a>';
    toolTipSecLangWizard += '<div data-tooltip="help" class="hidden efToolTip">';
    toolTipSecLangWizard += $(tt).html();
    toolTipSecLangWizard += '</div>';

    // if for wizard only then add the class "wizard" to the element
    // if for oneform only then add the class "oneForm" to the element
    // if for both then add the class "oneFormWizard" to the element
    // IMPORTANT: DO NOT ADD ANY CSS TO THOSE CLASSES!!!
    $(selector).append(toolTipSecLangWizard);
}

function formatTypeOfMarkButtons() {
    var $selector = $(".types-of-marks li a");
    var t = 0;
    var t_elem;
    var maxHeight;
    setTimeout(function() {
        $selector.each(function() {
            $this = $(this);
            if ($this.height() > t) {
                t_elem = this;
                t = $this.height();
            }
            maxHeight = t;
        });

        $selector.css("height", t);

    }, 300);
}

function feesCollapse() {
    // collapsing script for the fees box
    $("span.fee-description-total").on("click", function(){
        var itemClass = $(".fee-item-total");
        if (itemClass.hasClass("collapsed")) {
            itemClass.removeClass("collapsed");
        } else {
            itemClass.addClass("collapsed");
        }
    });
}

// --- Functions end --- //


// --- fire 'em when DOM is ready--- //

$(function() {
    activateBreadcrumbSteps();
    formatTypeOfMarkButtons();

    // /views/marks/wizard/mark_language.jsp
    //setToolTip("section.language>p", "#tt01", "sendsecondlng1");
    // /views/marks/wizard/mark_language.jsp
    setToolTip("#reference-container div label.oneFormWizard", "#tt02", "reference");

    // /views/claim/claim_priority_manual_details.jsp
    //setToolTip(".file-upload-radio", "#tt03", "reference");

});
