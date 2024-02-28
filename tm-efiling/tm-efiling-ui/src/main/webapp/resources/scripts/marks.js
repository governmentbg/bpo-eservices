function showMarkTypeForm(id, mode, typemark) {
    $.ajax({
		url : "changeMarkType.htm",
		cache : false,
		data : "id=" + id + "&mode=" + mode,
		success : function(html) {
			if(html){
				$("#markType_content").html(html);
				$('#markType_content :checkbox').trigger('change');
				checkSecondLanguages();
				$("#markType").val(id);
				resetActiveTab(typemark);
                callGetFastTrackFails();

				$("#hiddenForm").children("input[name='mainForm.wordRepresentation']").each(function(){
				    $(this).val("");
                });
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$("#markType_content").html(errorThrown);
		}
	});
}

function changeMarkType(target, mode, typemark) {
	var markType = $(target).attr('id');
	if (markType!=$("#markType").val()) {
		$.ajax({
			url : "hasLanguage.htm",
			type : "GET",
			cache : false,
			success : function(html) {
				if (html.indexOf("true")>-1) {
					if ($("#markType").val()!='0') {
						var onConfirmYes = partial(showMarkTypeForm, markType, mode);
						showConfirmModalTypeMark($("#marks_changeType").html(), onConfirmYes, resetActiveTab, typemark);
					} else {
						showMarkTypeForm(markType, mode, typemark);
					}
				} else {
					showWarningModal($("#goodsServices_selectLanguageFirst").html());
				}
			}
		});
	}
}

$('#secondLangChkbox').live("click", function() {
	checkSecondLanguages();
});

function checkSecondLanguages() {
	if ($('#secondLangChkbox').is(':checked'))
		$('div[id=secondLanguageField]').removeClass('secondLanguage');
	else
		$('div[id=secondLanguageField]').addClass('secondLanguage');
}

// Event Listeners Implementation
$('.markMenuWiz').live("click", function() {
	changeMarkType(this, "wizard", $(this).attr('id'));
});

function resetActiveTab(typemark) {
	if(typemark != null){
		// For the oneform
		$('li').filter('.markMenu').removeClass("active");
		$('li').filter('#'+typemark).addClass('active');
		// For the wizard
		$('div').filter('.markMenuWiz').parent().removeClass("active");
		$('div').filter('#'+typemark).parent().addClass('active');
	}
}

$('li.markMenu').live("click", function() {
	changeMarkType(this, "forms", $(this).attr('id'));
});

$('#otherMarkTypeButton').live("click", function() {
	$('#moreMarks').hide();
	$('#moreMarks').removeClass('hidden');
	$('#moreMarks').slideDown();
	$('#otherMarkButton').hide();
});

$('.see-more-button').live("click", function(){
    $(".minorType").each(function()
    {
        $(this).show();
    })
    $(this).hide();
})

$('#collectiveMark').live("click", function() {
    $.ajax({
        url : "changeCollective.htm",
        type : "GET",
        cache : false,
        success : function(html) {
            if ($('#collectiveMark').is(':checked')){
                showMessageModal(html);
            }
            updateFeesInformation();
            callGetFastTrackFails();
        }
    });
});

$('#certificateMark').live("click", function() {
    $.ajax({
        url : "changeCertificate.htm",
        type : "GET",
        cache : false,
        success : function(html) {
            if ($('#certificateMark').is(':checked')){
                showMessageModal(html);
            }
            updateFeesInformation();
            callGetFastTrackFails();
        }
    });
});

$("input").live("keypress", function (evt) {
	var charCode = evt.charCode || evt.keyCode;
	if (charCode  == 13) {
		return false;
	}
});

function showConfirmModalTypeMark(messageInnerHtml, onYes, onNo, typemark)
{
    $("#confirmModal #confirmPlaceholder").html(messageInnerHtml);
    $("#confirmModal").modal("show");

    $("#confirmModal #confirmModalOk").unbind("click");
    $("#confirmModal #confirmModalCancel").unbind("click");

    // When clicking on YES (or OK), make sure the function for the "hidden" event
    // is not called
    $("#confirmModal #confirmModalOk").bind("click", function()
    {
        $("#confirmModal").unbind("hidden");
        resetActiveTab(typemark);
        onYes();
    });

    // When clicking on NO (or Cancel), make sure the function for the "hidden" event
    // is not called
    $("#confirmModal #confirmModalCancel").bind("click", function()
    {
        $("#confirmModal").unbind("hidden");
    });

    // If YES or NO are not clicked and the modal still closes, execute the onNo function
    $("#confirmModal").unbind("hidden");
}