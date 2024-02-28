$(document).ready(function() {
	Designs.import.init();
});

Designs.import = {};

!function() {
	// Public part.
	this.init = function() {
		$(document).on('focus', '#inputIdRegisteredDesign', () => callbacks.autocomplete('#inputIdRegisteredDesign',true, "#previousDesign_viewMessage", "#previousDesign_viewUrl"));
        $(document).on('focus', '#inputIdDivisionalDesign', () => callbacks.autocomplete('#inputIdDivisionalDesign',true, "#previousDesign_viewMessage", "#previousDesign_viewUrl"));
		$('#buttonImportRegisteredDesign').click(callbacks.clickButtonImport);
        $('#buttonImportDivisionalDesign').live('click', callbacks.clickButtonDivisionalImport);
		$('#confirmImportRegisteredDesignModalOk').click(importDesign);
		$("#radioImportAll").click(displayAppsForDesignId);
	};
	
	// Private part.
	var callbacks = {
		clickButtonImport: function() {
			var designId = getDesignId();
			if (designId.trim() == '') {
				showWarningModal($("#previousRegisteredDesign_missingInputText").html());
				return;
			} else {
				$("#radioImportOnlyTheDesign").prop("checked", true);
				$('#confirmImportRegisteredDesignModal #loadAllApplicationSuggestedApps').html("");
				$('#confirmImportRegisteredDesignModal').modal('show');
			}
		},

        clickButtonDivisionalImport: function() {
            var designId = $('#inputIdDivisionalDesign').val();
            if (designId.trim() == '') {
                showWarningModal($("#previousRegisteredDesign_missingInputText").html());
                return;
            } else {
                ajax.importDivisionalDesign(designId);
            }
        },
		
		autocomplete: function(object, searchUrlEnabled, viewMessage, searchUrl) {
			initAutocomplete(object);
		    if ($(object).data("autocomplete") == null) { // autocomplete not enabled
		        return;
		    }
			$(object).autocomplete( "option", "delay", 500 );
		    $(object).data("autocomplete")._renderItem = function(ul, item) {
				var wrappedUrl = "";
				if (searchUrlEnabled == true)
				{
					var viewMessageVar = $(viewMessage).html();
					wrappedUrl = "<span class='navigation-col' data-url='" +
						getDesignNavigationUrl([
							["designId", item.ST13]
						], searchUrl) + "'>" +
						"<span class='fourth-col'>" + viewMessageVar + "</span></span>";
				}

				return $("<li></li>")
					.data("item.autocomplete", item)
					.append("<a><span class='four-col'><span class='selectable-col'><span class='first-col'>" + item.Name + "</span>" +
						"<span class='second-col'>" + item.ApplicationNumber + "</span><span class='third-col'><span style='padding-left: 10px'>" + item.RegistrationNumber+"</span></span></span></span></a>"+wrappedUrl)
					.appendTo(ul);

		    };
			$(object).data("autocomplete")._resizeMenu = function() {
				this.menu.element.outerWidth( 660 );
			}
		    $(object).bind("autocompleteselect", function(event, ui) {
		        setTimeout(function() {
		        	if(ui.item == null) {
		                return;
		            }
					var registrationNum = ui.item.RegistrationNumber.substr(0, ui.item.RegistrationNumber.indexOf("-"));
		            $("#inputIdRegisteredDesign").val(registrationNum);
		            return false;
		        }, 100);
		    });
		}
	};
	
	var ajax = {
		importDesign: function(designId) {
            removePreviousErrors('#importPreviousDesignErrorSection');
	        // Disable the page to not allow the user to click anything else while the process ends.
	        showLoadingScreen();
	        $.ajax({
	            url: 'importDesign.htm',
	            data: "id=" + designId,
	            type: "GET",
	            success: function(html) {
	            	if (Person.containsImportError(html)) {
	            		showWarningModal($(".importError", $(html)).html());
	            		hideLoadingScreen();
	            	} else {
		            	$('#designsListDiv').html(html);
		            	var designCount = $('#designsListDiv .designCountRow').length;
	                    $('#designCount').html(designCount);
	                    scrollToContainer("#designsListDiv");
	                    getFeesInformation();
	                    callGetFastTrackFails();
		                hideLoadingScreen();
	            	}
	            },
	            error: function(error) {
	                hideLoadingScreen();
	                genericHandleError($("#design_cannotImport").html(), "#importPreviousDesignErrorSection", true);
	            }
	        });
	    },

		getApplicationsByDesignId: function(designId){
			showLoadingScreen();
			$.ajax({
				url: 'getApplicationsForDesignId.htm',
				data: "id=" + designId,
				type: "GET",
				success: function(html) {
					$("#loadAllApplicationSuggestedApps").html(html);
					if($("#loadAllApplicationSuggestedApps input[name='selectedApplicationId']").length >0){
						$($("#loadAllApplicationSuggestedApps input[name='selectedApplicationId']")[0]).prop("checked", true);
					}
					if($("#loadAllApplicationSuggestedApps input[name='selectedApplicationId']").length < 2){
						$("#selectAppToImport").hide();
					}
					hideLoadingScreen();
				},
				error: function(error) {
					hideLoadingScreen();
				}
			});
		},

        importDivisionalDesign: function() {
            $.ajax({
                url: "importDivisionalApplication.htm",
                data: $('#divisionalApplicationTabForm form').serialize(),
                type: "POST",
                success: function(html) {
                    if (Person.containsImportError(html)) {
                        showWarningModal($(".importError", $(html)).html());
                    } else {
                        //Show result
                        $('#divisionalApplicationTab').html(html);
                    }
                },
                error: function(error) {
                    genericHandleError($("#design_cannotImport").html(), "#importDivisionalDesignErrorSection", true);
                },
                beforeSend: showLoadingScreen,
                complete: hideLoadingScreen
            });
        }

    };
	
	function importDesign() {
		var loadAllApplication = $("input[name=loadAllApplication]:radio:checked").val();
		var applicationId = $("input[name=selectedApplicationId]:radio:checked").val();
		var designId = getDesignId();
		if (loadAllApplication == "true") {
			// Flow transition.
			var $hiddenForm = $("#hiddenForm");
			$("#hiddenElement").val('importDesignApplication');
			$('<input>').attr({type: 'hidden', name: 'idDesign', value: designId}).appendTo($hiddenForm);
			$('<input>').attr({type: 'hidden', name: 'applicationId', value: applicationId}).appendTo($hiddenForm);
			$('#confirmImportRegisteredDesignModal').modal('hide');
			//Disable the page to not allow the user to click anything else while the process ends
	    	showLoadingScreen();
	        $($hiddenForm).submit();
		} else {
			ajax.importDesign(designId);
		}
	}
	
	function getDesignId() {
		// Checks if the user has selected a value using the autocomplete feature.
		var designId = $('#inputAutocompleteIdRegisteredDesign').val();
		if (designId == '') {
			// if not, make the call to the import service using the value in the inputIdRegisteredDesign text box
			designId = $('#inputIdRegisteredDesign').val();
		}
		return designId;
	}

	function displayAppsForDesignId(){
		var id = getDesignId();
		ajax.getApplicationsByDesignId(id);
	}
	
}.apply(Designs.import);

$("#searchForDesignBtn").live("click", function() {
	window.open($(this).attr("data-ask"));
});

function getDesignNavigationUrl(keyValuePairArray, searchUrl)
{
	return parseLinkUsingKeyValuePairs(keyValuePairArray, $(searchUrl).val());
}