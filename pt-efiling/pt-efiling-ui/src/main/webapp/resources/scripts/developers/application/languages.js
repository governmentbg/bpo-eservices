'use strict';

pt.efiling.application.languages = {};

!function() {
	
	var originalLanguage = "";
	
	var LanguageOptions = {
		backupSecondLangHtml: "",
		createBackupSecondLang: function() {
			this.backupSecondLangHtml = $("#secLang").html();
		},
		restoreBackupSecondLang: function() {
			$("#secLang").html(this.backupSecondLangHtml);
		}
	};

	var manipulations = {
		removeFirstLenguageInSelectSecondLanguage: function() {
			if ($("#secLang").size() > 0) {
				var firstLangSelector;
		        if ($("select#firstLang").size() > 0) {
		            firstLangSelector = "#firstLang option:selected";
		        } else {
		            firstLangSelector = "#storedFirstLang";
		        }
		        var firstLanguage = $(firstLangSelector).val();
		        if (firstLanguage != null && firstLanguage != "") {
		        	$("#secLang option[value='" + firstLanguage + "']").remove();
		        }
		    }
		}
	};
	
	var utils = {
		changeFirstOrSecondLanguage: function(target, language, isFirstLang, sendCorrespondenceInSecondLang) {
			if ($('#firstLang').val() != "" && $('#firstLang').val() != null && $('#firstLang').val() == $('#secLang').val()) {
				showMessageModal($("#languages_prevent_same").html());
				$(target).val(originalLanguage);
				return;
			}
			var originalSelectedLanguage = originalLanguage;
			var onConfirmYes = partial(ajax.setLanguage, target, language, isFirstLang, sendCorrespondenceInSecondLang, originalSelectedLanguage);
			var onConfirmNo = partial(this.revertLanguage, target, originalSelectedLanguage);
			if (originalLanguage != ""  && originalLanguage != null) {
			    showConfirmModal($("#languageMessage_changeClearsData").html(), onConfirmYes, onConfirmNo);
			} else {
				ajax.setLanguage(target, language, isFirstLang, sendCorrespondenceInSecondLang, originalSelectedLanguage);
			}
		},
		revertLanguage: function(target, originalSelectedLanguage) {
			$(target).val(originalSelectedLanguage);
		}
	};
	
	var initializations = {
		initLanguageSelects: function() {
			LanguageOptions.createBackupSecondLang();
			manipulations.removeFirstLenguageInSelectSecondLanguage();
		},
		initFirstLanguage: function() {
			$('#firstLang').click(function() {
				originalLanguage = $(this).val();
			});
			
			$('#firstLang').change(function() {
				utils.changeFirstOrSecondLanguage($(this), $('#firstLang option:selected').val(), true, $('#sendsecondlng').is(':checked'));
			});
		},
		initSecondLanguage: function() {
			$('#secLang').click(function() {
				originalLanguage = $(this).val();
			});

			$('#secLang').change(function() {
				utils.changeFirstOrSecondLanguage($(this), $('#secLang option:selected').val(), false, $('#sendsecondlng').is(':checked'));
			});
		},
		initSecondLanguageTranslation: function() {
			$('#secondLangChkbox').click(function() {
				var isChecked = $(this).is(':checked');
				ajax.setSecondLenguageTranslation(isChecked);
			});
		}
	};
	
	var ajax = {
		setLanguage: function(target, language, first, sendCorrespondenceInSecondLang, originalSelectedLanguage) {
			$.ajax({
				url: "changeLanguage.htm",
				cache: false,
				data: "language=" + language + "&first=" + first + "&sendCorrespondenceInSecondLang=" + sendCorrespondenceInSecondLang,
				success: function() {
					// restore backup html for second language options
					// this process was used to support "hiding" the selected second language in
					// IE browsers, as they do not support the "hide" mechanism
			        var oldSelection = $("#secLang option:selected").val();
			        if (first == true) {
			        	LanguageOptions.restoreBackupSecondLang();
			            $("#secLang option[value='" + language + "']").remove();
			            $("#secLang option[value='" + oldSelection + "']").attr("selected", "selected");
			        }
				},
				error: function(xmlHttpRequest, textStatus, errorThrown) {
					showMessageModal($("#errors_language_ajax_update").html());
					$(target).val(originalSelectedLanguage);
				}
			});
		},
		setSecondLenguageTranslation: function(isChecked) {
			$.ajax({
				url: "changeProvideSecondLanguageTranslation.htm",
				cache: false,
				data: "secondLanguageTranslation=" + isChecked,
				success: function(html) {
					// 
				},
				error: function(xmlHttpRequest, textStatus, errorThrown) {
					$("#firstSecondLang").html(errorThrown);
				}
			});
		},
		hasLanguage: function() {
			var language = $.ajax({
				url: "hasLanguage.htm",
				type: "GET",
				cache: false,
				async: false,
			}).responseText;
			return language === "true";
		}
	};
	
	this.hasSelectedLanguage = function() {
		return ajax.hasLanguage();
	};
	
	this.init = function() {
		fspLog('   init languages...');
		initializations.initLanguageSelects();
		initializations.initFirstLanguage();
		initializations.initSecondLanguage();
		initializations.initSecondLanguageTranslation();
		fspLog('   ...languages initiated');
	};

}.apply(pt.efiling.application.languages);

