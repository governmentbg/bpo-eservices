var LanguageOptions =
{
    backupSecondLangHtml: "",
    createBackupSecondLang : function()
    {
        LanguageOptions.backupSecondLangHtml = $("#secLang").html();
    },
    restoreBackupSecondLang: function()
    {
        $("#secLang").html(LanguageOptions.backupSecondLangHtml);
    }
};

var originalLanguage;

function changeLanguage(target, language, first, collective) {
	if ($('#firstLang').val()!=""&&$('#firstLang').val()!=null
			&&$('#firstLang').val()==$('#secLang').val()) {
		showMessageModal($("#languages_prevent_same").html());
		$(target).val(originalLanguage);
		return;
	}
	var originalSelectedLanguage = originalLanguage;
	var onConfirmNo = partial(revertLanguage, target, originalSelectedLanguage);
	var onConfirmYes = partial(changeLanguageAjax, target, language, first, collective, originalSelectedLanguage);
	if (originalLanguage!=""  && originalLanguage!=null) {
	    showConfirmModal($("#languageMessage_changeClearsData").html(), onConfirmYes, onConfirmNo);
	} else {
		changeLanguageAjax(target, language, first, collective, originalSelectedLanguage);
	}
}

function serialize(form) {
	var str = "";
	$(form).find(".ajax-input").each(function(index, element) {
		str += $(this).attr("ajax-name")+"="+$(this).attr("ajax-value")+"&";
 	});
	str += $(form).serialize();
}

$(".ajax-submit").live("click", function() {
	$.ajax({
		url: $(this).closest(".ajax-form").attr("ajax-action"),
		cache: false,
		data: serialize($(this).closest(".ajax-form")),
		success: function() {

		}
	});
});

function changeLanguageAjax(target, language, first, collective, originalSelectedLanguage) {
	$.ajax({
		url : "changeLanguage.htm",
		cache : false,
		data : "language=" + language + "&first=" + first + "&collective=" + collective,
		success : function() {
			$("#tableGoodsAndServices").refreshGS({langId: getLanguageInfo(true).first, languageInfo : getLanguageInfo(true)});
			$.ajax({
	  			url: "clearMarkType.htm",
	  			type: "GET",
	  			cache: false,
	  			success: function(html){
	  				$(".mark-types-wizard li").each(function()
                      {
                          $(this).removeClass("active");
                      });
	  				$("#markType_content").html(html);
                    $(".types-of-marks .markMenu").each(function()
                    {
                        $(this).removeClass("active");
                    })
                    $("#markType").val("0");

                      // restore backup html for second language options

                      // this process was used to support "hiding" the selected second language in
                      // IE browsers, as they do not support the "hide" mechanism
                      var oldSelection = $("#secLang option:selected").val();
                    if(first == true)
                    {
                        LanguageOptions.restoreBackupSecondLang();
                        $("#secLang option[value='" + language + "']").remove();
                        $("#secLang option[value='" + oldSelection + "']").attr("selected", "selected");
                    }

	  			}
			});
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			showMessageModal($("#errors_language_ajax_update").html());
			$(target).val(originalSelectedLanguage);
		}
	});
}

function revertLanguage(target, originalSelectedLanguage) {
	$(target).val(originalSelectedLanguage);
}

$('#firstLang').live("change", function() {
	changeLanguage($(this), $('#firstLang option:selected').val(), true,
		$('#sendsecondlng').is(':checked'));
});

$('#secLang').live("click", function() {
	originalLanguage = $(this).val();
});

$('#firstLang').live("click", function() {
	originalLanguage = $(this).val();
});

$('#secLang').live("change", function() {
	changeLanguage($(this), $('#secLang option:selected').val(), false,
		$('#sendsecondlng').is(':checked'));
});

$('#secondLangChkbox').live("click", function() {
    var secondLangFieldsVisible = $(this).is(':checked');
	$.ajax({
		url : "changeLanguageTranslation.htm",
		cache : false,
		data : "secondLanguageTranslation=" + secondLangFieldsVisible,
		success : function(html) {
			$("#tableGoodsAndServices").refreshGS({langId: getLanguageInfo(true).first, languageInfo : getLanguageInfo(true)});
            if(secondLangFieldsVisible)
            {
                $("#markType_content #secondLanguageField").show();
            }
            else
            {
                $("#markType_content #secondLanguageField").hide();
                $("#markType_content #secondLanguageField textarea").val("");
            }
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$("#firstSecondLang").html(errorThrown);
		}
	});
});

$(document).ready(function()
{
    LanguageOptions.createBackupSecondLang();
    if($("#secLang").size() > 0)
    {
        var firstLangSelector;
        if($("select#firstLang").size() > 0)
        {
            firstLangSelector = "#firstLang option:selected";
        }
        else
        {
            firstLangSelector = "#storedFirstLang";
        }

        var firstLanguage = $(firstLangSelector).val();

        if(firstLanguage != null && firstLanguage != "")
        {
            $("#secLang option[value='" + firstLanguage + "']").remove();
        }
    }
});