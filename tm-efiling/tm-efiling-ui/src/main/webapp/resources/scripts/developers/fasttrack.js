/*******************************************************************************
 * $Id:: fasttrack.js 2020/06/30 18:02 jmunoze
 *
 *        . * .
 *      * RRRR  *   Copyright (c) 2012-2020 EUIPO: European Intelectual
 *     .  RR  R  .  Property Organization (trademarks and designs).
 *     *  RRR    *
 *      . RR RR .   ALL RIGHTS RESERVED
 *       *. _ .*
 *
 *  The use and distribution of this software is under the restrictions exposed in 'license.txt'
 *
 ******************************************************************************/

var FastTrack = {};

FastTrack.nav =
{
    getFastTrackFails: "getFastTrackFails.htm",
    setFastTrackStart: "setFastTrackStart.htm"
};

$(document).ready(function($) {
    var failTemplate = $('#aside-fast-track-applicationtype-fail-template').html();
    var okTemplate = $('#aside-fast-track-applicationtype-ok-template').html();
    FastTrack.templates =
        {
            fails: _.template(failTemplate?failTemplate:''),
            ok: _.template(okTemplate?okTemplate:'')
        };

    var isFastTrackStarted = $('#aside-fast-track-applicationtype-is-started').html();
    var markType = $('#markType').val();
    if (isFastTrackStarted == 'true' || markType!='0' ) {
        callGetFastTrackFails();
    }
    if (isFastTrackStarted == 'false') {
        callIsFastTrackStarted();
    }

    // Change event on inputs
    $(":input.fasttrackinput:not(.fasttrackinput-ignore)").live("change", function() {
        updateMainForm(callGetFastTrackFails);
    });
});

function updateMainForm(success) {
    prepareHiddenForm();
    $.ajax({
        url: "updateMainForm.htm",
        type : "POST",
        data: $("#hiddenForm").serialize(),
        cache : false,
        async: false,
        success: success,
        error: function (error) {
            genericHandleError("An error occured while processing your request. Please try again later.", "#aside-fast-track-applicationtype-fails");
        }
    });
}

function callGetFastTrackFails() {
    if ($('.aside-fast-track-applicationtype').exists()) { // if FastTrack service is active
        showFastTrackLoadingScreen();
        $.ajax({
            url: FastTrack.nav.getFastTrackFails,
            success: function (fails) {
                var applicationtypeSection = $(".aside-fast-track-applicationtype");
                var applicationtypeTitleLayer = $(".aside-fast-track-applicationtype .fast-track-aside-title");
                var applicationtypeContentLayer = $(".aside-fast-track-applicationtype .fast-track-aside-content");
                if (fails && fails.length > 0) {
                    applicationtypeTitleLayer.find('.fasttrack-icon-after').hide();
                    applicationtypeTitleLayer.find('.fastTrackTitle').hide();
                    applicationtypeTitleLayer.find('.fastTrackTitleNo').show();
                    applicationtypeSection.removeClass("ok");
                    applicationtypeSection.addClass("fail");
                    applicationtypeContentLayer.html(FastTrack.templates.fails({
                        fails: fails
                    }));
                } else {
                    applicationtypeTitleLayer.find('.fasttrack-icon-after').show();
                    applicationtypeTitleLayer.find('.fastTrackTitle').hide();
                    applicationtypeTitleLayer.find('.fastTrackTitleYes').show();
                    applicationtypeSection.removeClass("fail");
                    applicationtypeSection.addClass("ok");
                    applicationtypeContentLayer.html(FastTrack.templates.ok());
                }
                adjustMainContainerMinHeight();
            },
            error: function (error) {
                genericHandleError("An error occured while processing your request. Please try again later.", "#aside-fast-track-applicationtype-fails");
            }
        });
    }
}

function callIsFastTrackStarted() {
    $.ajax({
        url: FastTrack.nav.setFastTrackStart,
        async: false,
        error: function (error) {
            genericHandleError("An error occured while processing your request. Please try again later.", "#aside-fast-track-applicationtype-fails");
        }
    });
}

function showFastTrackLoadingScreen() {
    var loading = $('<div></div>').css('padding-top', '12px').css('text-align', 'center');
    loading.append($('<img></img>').attr('src', 'resources\\img\\ajax-loader.gif'));
    $(".aside-fast-track-applicationtype .fast-track-aside-content").html(loading);
}

$('.aside-fast-track-applicationtype-fail-entry-action').live("click", function() {
    if (!$($(this).attr("href")).exists()) {
        submitMainForm($(this).attr("data-val"), true, $(this).attr("href"));
        return false;
    }
});

$('.fast-track-banner .fast-track-banner-title .add-icon').live("click", function() {
    $(".fast-track-banner #fast-track-collapse-banner").fadeToggle("fast", function(){
        $(".fast-track-banner").animate({width:'98%',height:'auto'},200,function(){
            $(".fast-track-banner #fast-track-expand-banner").fadeToggle("fast");
        });
    });
});

$('.fast-track-banner .fast-track-banner-title .minus-icon').live("click", function() {
    $(".fast-track-banner #fast-track-expand-banner").fadeToggle("fast", function(){
        $(".fast-track-banner").animate({width:'43%',height:'auto'},200,function(){
            $(".fast-track-banner #fast-track-collapse-banner").fadeToggle("fast");
        });
    });
});
