$(document).ready(function() {
	Claims.init();
});

var Claims = {
	
	init: function() {
		this.common.table.init();
		this.common.buttons.init();
		this.priority.init();
		this.exhibition.init();
	},
	
	common: {
		buttons: {
			init: function() {
				$(document).on('click', '.cancelExhibitionButton', function() {
					Claims.exhibition.utils.cancelExhibitionButton();
				});
				
				$(document).on('click', '.cancelPriorityButton', function() {
					Claims.priority.utils.cancelPriorityButton();
				});
				
				this.setButtons();
			},
			
			setButtons: function(claim) {
				claim = claim || '';
				if ($("#claimTableContainer tr.priority").length > 0) {
					$('#tabPriorityPrevious').addClass('hidden');
			        $('a#noPriority').addClass('disabled');
			        this.setDisabledCheckClaimLater('#checkPriorityClaimLater', true);
			    } else {
					$('#tabPriorityPrevious').removeClass('hidden');			    	
			        $('a#noPriority').removeClass('disabled');
			        this.setDisabledCheckClaimLater('#checkPriorityClaimLater', false);
			    }
				
				if ($("#claimTableContainer tr.exhpriority").length > 0) {
					$('a#noExhibition').addClass('disabled');
					this.setDisabledCheckClaimLater('#checkExhPriorityClaimLater', true);
				} else {
					$('a#noExhibition').removeClass('disabled');
					this.setDisabledCheckClaimLater('#checkExhPriorityClaimLater', false);
				}
					
				this.changeClaimText("#openPriority", parseInt($("#prioritiesSizeValue").val()));
				this.changeClaimText("#openExhibition", parseInt($("#exhibitionsSizeValue").val()));
				
		        // If there isn't any priority, we have to check No button
				if (claim == 'priority' && $("#claimTableContainer tr.priority").length == 0 && 
						!$('#checkPriorityClaimLater').is(':checked') &&
							!$('#tabPriority').is(":visible")) {
		        	$('#claimPriorityNoItem').addClass('active');
		        }
				
		        // If there isn't any exhibition, we have to check No button
		        if (claim == 'exhibition' && $("#claimTableContainer tr.exhpriority").length == 0 && 
		        		!$('#checkExhPriorityClaimLater').is(':checked') &&
		        			!$('#tabExhibition').is(":visible")) {
		        	$('#claimExhibitionNoItem').addClass('active');
		        }
			},

			setDisabledCheckClaimLater: function(checkSelector, disabled) {
				$(checkSelector).attr('disabled', disabled);
			},
			
			changeClaimText: function(buttonSelector, numberOfClaims) {
			    if($(buttonSelector).size() == 0) {
			        return;
			    }
			    if(numberOfClaims == 0) {
			        $(buttonSelector).html($(buttonSelector + "_yesMessage").html());
			    } else {
			        $(buttonSelector).html($(buttonSelector + "_addAnotherMessage").html());
			    }
			}
		}, 
		table: {
			init: function() {
				var self = this;
				// sorting functionality trigger
				$(document).on('click', '#claimTableContainer table th a.sorter', function() {
					self.sortClaimsWithSorter($(this));
				});
			},
			
			createClaimListFromTable: function() {
			    var list = new Array();
			    $("#claimTableContainer").find("tr:gt(0)").each(function() {
			        var obj = new Object();
			        obj.className = $(this).attr("class");
			        $(this).find("td").each(function() {
			            obj[$(this).attr("data-val")] = $(this).html().trim();
			        });
	
			        list.push(obj);
			    });
			    return list;
			},
	
			createTableFromClaimList: function(list) {
			    // clear table by removing all rows except first, which is the header
			    $("#claimTableContainer").find("tr:gt(0)").remove();
	
			    for(var i = 0; i < list.length; i++) {
			        var row = "<tr class='" + list[i].className + "'>";
			        row += "<td class='number' data-val='number'>" + list[i].number + "</td>";
			        row += "<td data-val='type'>" + list[i].type + "</td>";
			        row += "<td data-val='country'>" + list[i].country + "</td>";
			        row += "<td data-val='date'>" + list[i].date + "</td>";
			        row += "<td data-val='id'>" + list[i].id + "</td>";
			        //row += "<td data-val='name'>" + list[i].name + "</td>";
			        row += "<td data-val='files'>" + list[i].files + "</td>";
			        if (!!list[i].designNumbers) { // multiple design application
			        	row += "<td data-val='designNumbers'>" + list[i].designNumbers + "</td>";
			        }
			        row += "<td data-val='options'>" + list[i].options + "</td>";
			        row += "</tr>";
			        $("#claimTableContainer tbody:last").append(row);
			    }
			},
	
			sortClaimsWithSorter: function(trigger) {
			    var sortDescending = $(trigger).hasClass("asc");
			    // remove all sort classes from other selectors
			    $(trigger).parents("table").find("a.sorter").each(function() {
			        $(this).removeClass("desc");
			        $(this).removeClass("asc");
			    });
	
			    this.sortClaims($(trigger).attr("data-val"), sortDescending);
			    if(sortDescending) {
			        $(trigger).addClass("desc");
			    } else {
			        $(trigger).addClass("asc");
			    }
			},
	
			sortClaims: function(property, descending) {
			    var list = this.createClaimListFromTable();
			    list.sort(Common.Sort.predicate(property, descending));
			    this.createTableFromClaimList(list);
			}
		}
	},
	
	priority: {
		init: function() {
			var self = this;
			
			$(document).on("focus", "#priorityImportTextField", function() {
				var officeCodeVar = $("#country").val();

				// change the autocomplete url (depends on the selected country)
				initAutocomplete(this, "autocompletePriority.htm?office=" + officeCodeVar);
				if($(this).data("autocomplete") == null || officeCodeVar == "") {
			        // autocomplete not enabled or not selected country.
			        return;
			    }
				$(this).data("autocomplete")._renderItem = function(ul, item) {
//			        var searchUrlEnabled = $("#priorityTradeMarkConfig_searchUrlEnabled").val();
			        var wrappedUrl = "";
//			        if (searchUrlEnabled == "true") {
//			            var viewMessageVar = $("#priorityTradeMarkConfig_viewMessage").html();
//			            wrappedUrl = "<span class='navigation-col' data-url='" +
//			            	parseLinkUsingKeyValuePairs([["id", item.di], ["office", officeCodeVar]], 'TODO') +
//			                // TODO getTradeMarkAutocompleteNavigationUrl([["trademarkId", item.designIdentifier], ["officeCode", officeCodeVar]], searchUrl) + 
//			                "'><span class='fourth-col'>" + viewMessageVar + "</span></span>";
//			        }
			        return $("<li></li>")
			            .data("item.autocomplete", item)
			            .append("<a><span class='three-col'><span class='selectable-col'><span class='first-col'>" + item.di + "</span>" +
			            "<span class='second-col'>" + item.dn + "</span></span></span></a>" + wrappedUrl)
			            .appendTo(ul);
				};
			    $(this).bind("autocompleteselect", function(event, ui) {
			        setTimeout(function() {
			        	if(ui.item == null) {
			                return;
			            }

			            $("#priorityImportTextField").val(ui.item.di);
			            return false;
			        }, 100);
			    });
			});
			
			$(document).on('click', '#priorityImportButton', function() {
			    var importId = $("#priorityImportTextField").val();
			    if (importId == null || importId.trim() == "") {
			        showWarningModal($("#previousRegisteredDesign_missingInputText").html());
			        return;
			    }
				
			    var country = $("#priorityForm #country").val();
			    self.ajax.import.importPriority(importId, country);
			});
			
			$(document).on('click', '.addPriorityWizard', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, self.utils.addPriority);
				} else {
					self.utils.addPriority();
				}
			});

			$(document).on('click', '.editPriorityCommon', function() {
				self.ajax.getPriorityDetails($(this).attr("data-id"), 'claim/claim_priority_details_wizard', $(this).closest('tr'), $(this).attr("data-priority-num"));
			});

			$(document).on('click', '.removePriorityCommon', function() {    
			    var removeFunc = partial(self.ajax.removePriority, $(this).attr("data-id"), 'claim/claim_table_common');
			    showConfirmModal($("#priority_remove").html(), removeFunc);
			});
			
			// Click in 'Yes' button.
			$(document).on('click', '#openPriority', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, self.utils.yesButton, self.utils.cancelPriorityButton);
				} else {
					self.utils.yesButton();
				}
			});

			// Click in 'No' button.
			$(document).on('click', '#noPriority', function () {
				$(".claimSections #priorityForm .cancelPriorityButton").trigger('click');
				$('#checkPriorityClaimLater').attr('checked', false);
				$('#checkPriorityClaimLater').trigger("change");
			});

			// Click in check 'Claim later'
			$('#checkPriorityClaimLater').click(function() {
				if ($(this).is(':checked')) {		
					$('#tabPriorityPrevious').hide(); // hide the tab, in case it would be open
					$('#tabPriority').hide(); // hide the tab, in case it would be open
					$('#openPriority').parent().removeClass('active'); // remove class button 'Yes', if checked
					$('#noPriority').parent().removeClass('active'); // remove class button 'No', if checked
				} else {
					Claims.common.buttons.setButtons('priority');
				}
			});
			
			$(document).on('click', '#createManualDetails', function() {
				$("#manualDetails").show();	
			});

			// Click in tab bottom cancel link.
			$(document).on('click', '.claimSections .cancelManualButton', function() {
				$(this).closest('#manualDetails').hide();
			});
			
			$(document).on('change', "#priorityForm #country", this.utils.enableDisablePriority);
		},
		
		utils: {
			enableDisablePriority: function() {
				if($("#priorityForm #country").val() == "") {
					$('#createManualDetails').bind('click', false);
					$('#createManualDetails').addClass('disabled');
					$('.claimSections #priorityForm .importPriority').bind('click', false);
					$('.claimSections #priorityForm .importPriority').addClass('disabled');
					$('#lastPriority').attr('disabled','true');
					$("#manualDetails").hide();
				} else {
					$('#createManualDetails').unbind('click', false);
					$('#createManualDetails').removeClass('disabled');
					$('.claimSections #priorityForm .importPriority').unbind('click', false);
					$('.claimSections #priorityForm .importPriority').removeClass('disabled');
					$('#lastPriority').removeAttr('disabled');
				}
			},
			yesButton: function() {
				removeHighlightRow($('.priority.active'));
				Claims.priority.ajax.newPriorityForm(this, 'claim/claim_priority_details_wizard');
				// Unchecked claim later, if checked
				$('#checkPriorityClaimLater').attr('checked', false);
				$('#checkPriorityClaimLater').trigger("change");
			},
			cancelPriorityButton: function() {
				$('#tabPriority').hide();
		        $('.priority').removeClass('active');
		        $('#openPriority').parent().removeClass('active');
		        Claims.common.buttons.setButtons('priority');
			},
			addPriority: function() {
				DesignsLink.selectOptionsPriorityLists();
				Claims.priority.ajax.addPriorityPost(true, this, 'claim/claim_priority_details_wizard', 'claim/claim_table_common');
			}
		},
		
		ajax: {
			
			import: {
				importPriority: function(importPriority, country) {
                    $.ajax({
			            url: 'importPriority.htm',
			            data: "id=" + importPriority + "&office=" + country,
			            type: "GET",
			            success: function(html) {
			            	if (Person.containsImportError(html)) {
			            		showWarningModal($(".importError", $(html)).html());
			            	} else {
			            		$("#manualDetails").html(html);
			            		$("#manualDetails").show();
                                // $("#wDate").val($("#dateStringFormat").val());
			            		Claims.priority.utils.enableDisablePriority();
			            	}
			            },
			            error: function(error) {
                            genericHandleError($("#design_cannotImport").html(), "#manualDetailsImportError", true);
			            },
                        beforeSend: function() {
                            removePreviousErrors('#manualDetailsImportError');
                            // Disable the page to not allow the user to click anything else while the process ends.
                            showLoadingScreen();
                        },
                        complete: hideLoadingScreen
			        });
				}
			},
			
			newPriorityForm: function(tabPaneObject, claimDetails) {
				$('.claimSections .cancelButton').trigger('click');
				$(".tabClaim .flMessageError").closest(".tabClaim").hide();
				$.ajax({
					url: "addPriority.htm",
					data: "detailsView=" + claimDetails,
					cache: false,
					success: function(html){
						$("#tabPriority").html(html);
						$('#priorityCurrentNumber').html($("#claimTableContainer tr.priority").length + 1);
						$("#tabPriority").show();
						Claims.priority.utils.enableDisablePriority();
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabPriority").html(errorThrown);
						$("#tabPriority").show();	
					}
				});
			},

			getPriorityDetails: function(id, claimDetails, row, number) {
				$.ajax({
					url: "addPriority.htm",
					data: "id=" + id + "&detailsView=" + claimDetails,
					cache: false,
					success: function(html){
						$("#tabPriority").html(html);
						$('#priorityCurrentNumber').html(number);
						$("#tabPriority").addClass('active'); //for oneform
						$("#tabPriority").show(); //for wizard
						$("#tabExhibition").removeClass('active'); //for oneform
						Claims.priority.utils.enableDisablePriority();
						//highlight the selected row
						highlightRow(row);
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabPriority").html(errorThrown);
						$("#tabPriority").show();	
					}
				});
			},

			removePriority: function(id, claimTable) {
				$.ajax({
					url: "removePriority.htm",
					data: "id=" + id + "&claimTable=" + claimTable,
					cache: false,
					success: function(html){
						//If post succeded we have to make two more actions:
						//Reload the common table
						$("#claimTableContainer").html(html);
						//If the removed one is currently being viewed in the edit form, hide it
						if($("#tabPriority").attr('class').indexOf("active") !=-1 && $("#tabPriority #id").val() == id) {
							$("#tabPriority").removeClass('active');
							Claims.priority.utils.enableDisablePriority();
							if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Priority tab cannot be displayed anymore in oneform
								$("#tabPriority").hide();
							}
						}
			            $("#openPriority").parent(".active").removeClass("active");
                        getFeesInformation();
			            // change text of buttons to reflect change in number of priorities
			            Claims.common.buttons.setButtons('priority');
                        // update fast track status
                        callGetFastTrackFails();
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#claimTableContainer").html(errorThrown);
					}
				});
			},

			addPriorityPost: function(closeDiv, tabPaneObject, claimDetails, claimTable) {
				$.ajax({
					url: "addPriority.htm",
					data: $("#priorityForm").serialize() + '&' + $('#priorityManualForm').serialize() + 
						"&detailsView=" + claimDetails + "&claimTable=" + claimTable,
					type: "POST",
					cache: false,
					success: function(html){
						if(html.indexOf("error") != -1) {
							//If post succeded with validation errors:
							$("#tabPriority").html(html);
							$('#priorityCurrentNumber').html($("#claimTableContainer tr.priority").length + 1);
							$("#manualDetails").show();
						} else {
							//Reload the common table
							$("#claimTableContainer").html(html);
							if(closeDiv) {
								clearTabsDevelopers(tabPaneObject);
								$('.cancelPriorityButton').trigger('click');
								$('.cancelButton').trigger('click');
							} else {
								$('#openPriority').trigger('click');
								$('#openPriority').trigger('mouseup');
							}
                            getFeesInformation();
							callGetFastTrackFails();
						}
			            // change text of buttons to reflect change in number of priorities
						Claims.common.buttons.setButtons('priority');
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabPriority").html(errorThrown);
						$("#tabPriority").show();	
					}
				});
			}
		}
	},
	
	exhibition: {
		init: function() {
			var self = this;
			
			// Click in button 'No'
			$(document).on('click', '#noExhibition', function() {
				// Unchecked claim later, if checked
				$('#checkExhPriorityClaimLater').attr('checked', false);
				$("#exhibitionForm .cancelExhibitionButton").trigger('click');
			});

			// Click in button 'Yes'
			$(document).on('click', '#openExhibition', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, self.utils.yesButton, self.utils.cancelExhibitionButton);
				} else {
					self.utils.yesButton();
				}
			});
			
			// Click in check 'Claim later'
			$('#checkExhPriorityClaimLater').click(function() {
				if ($(this).is(':checked')) {
					$('#tabExhibition').hide(); // hide the tab, in case it would be open
					$('#openExhibition').parent().removeClass('active'); // remove class button 'Yes', if checked
					$('#noExhibition').parent().removeClass('active'); // remove class button 'No', if checked
				} else {
					Claims.common.buttons.setButtons('exhibition');
				}
			});
			
			// Click in button 'Add' exhibition.
			$(document).on('click', '.addExhibitionWizard', function() {
				if (ds.efiling.application.isOneForm()) {
					ds.efiling.application.hasAtLeastOneDesignWithCallbacks(self, self.utils.addExhibition);
				} else {
					self.utils.addExhibition();
				}
			});

			// Click in edit icon from common priorities table.
			$(document).on('click', '.editExhibitionCommon', function() {
				self.ajax.getExhibitionDetails($(this).attr("data-id"), 'claim/claim_exhibition_details_wizard', $(this).closest('tr'), $(this).attr("data-exhibition-num"));
			});

			// Click in remove icon from priorities table.
			$(document).on('click', '.removeExhibitionCommon', function() {
			    var removeFunc = partial(self.ajax.removeExhibition, $(this).attr("data-id"), 'claim/claim_table_common');
			    showConfirmModal($("#exhibition_remove").html(), removeFunc); // function in common.js
			});
		},
	
		utils: {
			yesButton: function() {
				removeHighlightRow($('.exhpriority.active')); // in common.js
				// Unchecked claim later, if checked
				$('#checkExhPriorityClaimLater').attr('checked', false);
				Claims.exhibition.ajax.newExhPriorityForm(this, 'claim/claim_exhibition_details_wizard');
			},
			cancelExhibitionButton: function() {
				$('#tabExhibition').hide(); // hide the tab
		        $('.exhpriority').removeClass('active'); // remove class for selected tr in table (edit case)
		        $('#openExhibition').parent().removeClass('active'); // remove class button 'Yes'
		        Claims.common.buttons.setButtons('exhibition'); // Disable no button and set button Yes text.
			},
			addExhibition: function() {
				DesignsLink.selectOptionsExhPriorityLists();
				Claims.exhibition.ajax.addExhibitionPost(true, this, 'claim/claim_exhibition_details_wizard', 'claim/claim_table_common');
			}
		},
		
		ajax: {
			newExhPriorityForm: function(tabPaneObject, claimDetails) {
				$(".tabClaim .flMessageError").closest(".tabClaim").hide();
				$.ajax({
					url: "addExhPriority.htm",
					data: "detailsView=" + claimDetails,
					cache: false,
					success: function(html){
						$("#tabExhibition").html(html);
						$('#exhibitionCurrentNumber').html($("#claimTableContainer tr.exhpriority").length + 1);
						$("#tabExhibition").show();								
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabExhibition").html(errorThrown);
						$("#tabExhibition").show();	
					}
				});				
			},

			getExhibitionDetails: function(id, claimDetails, row, number) {
				$.ajax({
					url: "addExhPriority.htm",
					data: "id=" + id + "&detailsView=" + claimDetails,
					cache: false,
					success: function(html){
						$("#tabExhibition").html(html);
						$('#exhibitionCurrentNumber').html(number);
						$("#tabExhibition").addClass('active');
						$("#tabExhibition").show(); //for wizard
						$("#tabPriority").removeClass('active');

						// highlight the selected row
						highlightRow(row); // function in common.js
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabExhibition").html(errorThrown);
						$("#tabExhibition").show();	
					}
				});
			},

			removeExhibition: function(id, claimTable) {  
				$.ajax({
					url: "removeExhPriority.htm",
					data: "id=" + id + "&claimTable=" + claimTable,
					cache: false,
					success: function(html){
						// If post succeded we have to make two more actions: 
						// Reload the common table
						$("#claimTableContainer").html(html);
						// If the removed one is currently being viewed in the card, hide it
						if($("#tabExhibition").attr('class').indexOf("active") !=-1 && $("#tabExhibition #id").val() == id) {
							$("#tabExhibition").removeClass('active');
							if($('body').attr('id') == 'wizard') { //do only in wizard mode. otherwise Priority tab cannot be displayed anymore in oneform
								$("#tabExhibition").hide();
							}
						}
			            // change text of buttons to reflect change in number of exhibitions
						Claims.common.buttons.setButtons('exhibition');
                        // update fast track status
                        callGetFastTrackFails();
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#claimTableContainer").html(errorThrown);
					}
				});
			},

			addExhibitionPost: function(closeDiv, tabPaneObject, claimDetails, claimTable) {
				$.ajax({
					url: "addExhPriority.htm",
					data: $("#exhibitionForm").serialize() + "&detailsView=" + claimDetails + "&claimTable=" + claimTable,
					type: "POST",
					cache: false,
					success: function(html) {
						if(html.indexOf("error") != -1) {
							//If post succeded with validation errors:
							$("#tabExhibition").html(html);
							$('#exhibitionCurrentNumber').html($("#claimTableContainer tr.exhpriority").length + 1);
						} else {
							//Reload the common table
							$("#claimTableContainer").html(html);
							if(closeDiv) {
								clearTabsDevelopers(tabPaneObject);
								$('.cancelExhibitionButton').trigger('click');
								// $('.cancelButton').trigger('click');
							} else {
								$('#openExhibition').trigger('click');
								$('#openExhibition').trigger('mouseup');
							}
							callGetFastTrackFails();
						}
			            // change text of buttons to reflect change in number of exhibitions
						Claims.common.buttons.setButtons('exhibition');
					},
					error: function (xmlHttpRequest, textStatus, errorThrown) {
						$("#tabExhibition").html(errorThrown);
						$("#tabExhibition").show();	
					}
				});
			}
		}
	}
};

