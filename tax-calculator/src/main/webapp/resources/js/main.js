function callCalculator(applicationType, params) {
    var calculateFeesUrl = $('#calculateFeesUrl').data('url');
    $("#bpo-loader").dialog('open');
    $.ajax({
        url: calculateFeesUrl,
        type: "get",
        data: {
            applicationType: applicationType,
            params: params
        },
        success: function (result) {
            $('#error-text-main').text('');
            $('#fee-panel').html(result);
            document.getElementById("clear-fees-btn").style.display = 'block';
            $("#bpo-loader").dialog('close');
        },
        error: function (request, status, error) {
            var responseMap = JSON.parse(request.responseText);
            $('#error-text-main').text(responseMap.message);
            $("#bpo-loader").dialog('close');
        }
    });
}

$(document).on("click", "#clear-fees-btn", function (e) {
    document.getElementById("fee-data").style.display = 'none';
});

$(document).on("click", "#calc-btn", function (e) {

    var applicationType = $('#applicationTypes').attr("data-service");
    var parameterMap = new Object();
    $(".input-param").each(function () {
        parameterMap[$(this).attr("data-name")] = $(this).val();
    });
    var params = JSON.stringify(parameterMap);
    callCalculator(applicationType, params);
});

$(document).on("change", ".radio-btn", function (e) {
    var value = $(this).val();
    var relField = $(this).attr('data-rel-field');
    $('#' + relField).val(value);
});

$(document).on("change", "#totalDesignsCount", function (e) {
    var count = $(this).val();
    var deferredField = $('#deferredDesignsCount');
    deferredField.attr('max', count);
    if (deferredField.val() > count) {
        deferredField.val(count);
    }
});

$(document).on("change", ".examination-required", function (e) {
    var checked = $(this).val();

    if (checked === 'true') {
        document.getElementById("anticipation-of-publication-frag").style.display = 'block';
    } else if (checked === 'false') {
        $("#anticipationOfPublicationFalse").click();
        document.getElementById("anticipation-of-publication-frag").style.display = 'none';
    }
});

$(document).on("change", ".fees-pct", function (e) {
    var checked = $(this).val();

    if (checked === 'true') {
        $("#feesTransformationFalse").click();
    }
});

$(document).on("change", ".fees-transformation", function (e) {
    var checked = $(this).val();

    if (checked === 'true') {
        $("#feesPctFalse").click();
    }
});

$(document).on("click", ".tab-content", function (e) {
    $(".tab-content").removeClass("ui-tabs-selected");
    $(this).addClass("ui-tabs-selected");

    var relDiv = $(this).attr("data-relDiv");
    $('.ui-tabs-panel').css('display','none');
    $('#' + relDiv).css('display','block');
});

$(document).on("click", "#check-btn", function (e) {

    var objectType = $('#objectTypes-owed-taxes').attr("data-object");
    var applicationNumberKind = $('input[name="application-number-kind"]:checked').val();
    var applicationNumber = $('#application-number-field').val();

    var checkOwedTaxesUrl = $('#checkOwedTaxesUrl').data('url');
    $("#bpo-loader").dialog('open');
    $.ajax({
        url: checkOwedTaxesUrl,
        type: "get",
        data: {
            objectType: objectType,
            applicationNumberKind: applicationNumberKind,
            applicationNumber: applicationNumber

        },
        success: function (result) {
            $('#error-text-main').text('');
            $('#owed-taxes-panel').html(result);
            document.getElementById("clear-owed-taxes-btn").style.display = 'block';
            $("#bpo-loader").dialog('close');
        },
        error: function (request, status, error) {
            var responseMap = JSON.parse(request.responseText);
            document.getElementById("owed-taxes-data").style.display = 'none';
            $('#error-text-main').text(responseMap.message);
            $("#bpo-loader").dialog('close');
        }
    });
});

$(document).on("click", "#clear-owed-taxes-btn", function (e) {
    document.getElementById("owed-taxes-data").style.display = 'none';
});

$(document).on("change", ".translation-type", function (e) {
    var checked = $(this).val();

    if (checked === 'message') {
        document.getElementById("translation-pages-count-frag").style.display = 'none';
        $('#pages-count-field').val(1);
    } else if (checked === 'publication') {
        document.getElementById("translation-pages-count-frag").style.display = 'block';
    }
});

$(document).on("click", ".serviceDropdownOption", function (e) {
    var applicationType = $(this).attr("value");
    var text = $(this).text();
    $('#applicationTypes').val(text);
    $('#applicationTypes').attr("data-service", applicationType);

    var objectType = $('#objectTypes').attr("data-object");
    var appBoxUrl = $('#appBoxUrl').data('url');
    $("#bpo-loader").dialog('open');
    $.ajax({
        url: appBoxUrl,
        type: "get",
        data: {
            applicationType: applicationType,
            objectType: objectType
        },
        success: function (result) {
            $('#error-text-main').text('');
            document.getElementById("fee-data").style.display = 'none';
            $('#calculator-panel').html(result);
            document.getElementById("calc-btn").style.display = 'block';
            $("#bpo-loader").dialog('close');
        },
        error: function (request, status, error) {
            var responseMap = JSON.parse(request.responseText);
            $('#error-text-main').text(responseMap.message);
            $("#bpo-loader").dialog('close');
        }
    });
});

$(document).on("click", ".objectDropdownOption", function (e) {

    var objectType = $(this).attr("value");
    var text = $(this).text();

    $('#objectTypes').val(text);
    $('#objectTypes').attr("data-object", objectType);

    var serviceBarUrl = $('#serviceBarUrl').data('url');
    $("#bpo-loader").dialog('open');
    $.ajax({
        url: serviceBarUrl,
        type: "get",
        data: {
            objectType: objectType
        },
        success: function (result) {
            $('#error-text-main').text('');
            document.getElementById("fee-data").style.display = 'none';
            $('#applicationType-wheel').html(result);
            $('#calculator-panel').html('');
            document.getElementById("calc-btn").style.display = 'none';
            $("#bpo-loader").dialog('close');
        },
        error: function (request, status, error) {
            var responseMap = JSON.parse(request.responseText);
            $('#error-text-main').text(responseMap.message);
            $("#bpo-loader").dialog('close');
        }
    });
});

$(document).on("click", ".objectDropdownOption-owed-taxes", function (e) {
    var objectType = $(this).attr("value");
    var text = $(this).text();
    $('#objectTypes-owed-taxes').val(text);
    $('#objectTypes-owed-taxes').attr("data-object", objectType);
});