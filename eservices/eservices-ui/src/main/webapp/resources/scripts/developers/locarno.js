var locarno = {};
		
$(document).ready(function() {
	locarno.init();
});

!function() {
	'use strict';
	
	// PUBLIC PART
	/**
	 * Registers all dom events.
	 */
	this.init = function() {
		// Add and save locarno class 
		$(document).on('click', 'a[href = "#section_locarno"][data-target = "addClass"]', function() {
        		ajax.loadAddClass();
		});
		$(document).on('click', '#buttonSaveAddClass', ajax.addClass);
		
		// Add, save and edit new list of products
		$(document).on('click', 'a[href = "#section_locarno"][data-target = "addNewProduct"]', function() {
        		ajax.loadAddNewProduct();
		});
		$(document).on('click', '#buttonSaveAddNewProduct', ajax.addNewProduct);
		$(document).on('click', '#locarnoList a[id ^= "iconEditNewProductAdded"]', function() {
			var locarnoId = $(this).attr("data-val");
        		ajax.getNewProductForEdit(locarnoId);
		});
		
		// Add new complex list of products
		$(document).on('click', 'a[href = "#section_locarno"][data-target = "addNewComplexProduct"]', function() {
        		ajax.loadAddNewComplexProduct();
		});
		$(document).on('click', '#buttonAddLocarnoComplexProductClass', manipulation.appendLocarnoComplexProductClass);
		$(document).on('click', '#buttonSaveAddNewComplexProduct', ajax.addNewComplexProduct);
		
		$(document).on('click', '#listAddedLocarnoComplexProductClasses a[class="remove-icon"]', function() {
			$(this).closest('li').remove();
		});
		
		// Remove added locarno classification
		$(document).on('click', '#locarnoList a[id ^= "iconRemoveLocarno"]', function() {
			var locarnoId = $(this).attr("data-val");
			var onConfirmYes = partial(ajax.remove, locarnoId);
			showConfirmModal($("#locarno_deleteConfirmation").html(), onConfirmYes);
		});

		// Load locarno subclasses when class is changed.
		$(document).on('change', '#selectLocarnoClass', function() {
			ajax.integration.loadLocarnoSubclasses($(this).val());
		});

		// Search locarno classifications
		$(document).on('click', '#buttonSearchLocarnoClassifications', function() {
			ajax.integration.searchLocarnoClassifications();
		});

		// Sorting locarno list
		$(document).on('click', '#tableAddedLocarnoClassifications th a.sorter', function() {
			Person.sortTable('locarno', $(this));
		});
	};
	
	// Only for sorting locarno list, required and invoked by person.js (Person.sortTable).
	this.sort = function(tableSelector, property, descending) {
		var list = manipulation.buildLocarnoListFromTable(tableSelector);
		list.sort(Common.Sort.predicate(property, descending));
		manipulation.buildTableFromLocarnoList(tableSelector, list);
	};
	
	// PRIVATE PART
	var nav = {
		displayAddNewClass: 'displayAddLocarnoClass.htm',
		addClass: 'addLocarnoClass.htm',
		displayAddNewProduct: 'displayAddLocarnoNewProduct.htm',
		addNewProduct: 'addLocarnoNewProduct.htm',
		displayAddNewComplexProduct: 'displayAddLocarnoNewComplexProduct.htm',
		addNewComplexProduct: 'addNewLocarnoComplexProduct.htm',
		getNewProductForEdit: 'getLocarnoNewProductForEdit.htm',
		remove: 'removeLocarno.htm',
		integration: {
			getSubclasses: 'getLocarnoSubclasses.htm',
			getClassifications: 'getLocarnoClassifications.htm'
		}
	};
 
	var utils = {
		error: function(selectorContainerError) {
			return function() {
				genericHandleError($('#locarnoGenericError').html(), selectorContainerError, true);
			};
		}
	};
	
	var manipulation = {
		updateSectionLocarno: function(html) {
			$('#section_locarno').html(html);
		},
		showSectionLocarno: function() {
			$('#section_locarno').modal('show');
		},
		hideSectionLocarno: function() {
			$('#section_locarno').modal('hide');
		},
		refreshLocarnoList: function(html) {
			$('#locarnoList').html(html);
		},
		refreshLocarnoClassificationsList: function(html) {
			$('#locarnoClassificationsList').html(html);
		},
		buildLocarnoListFromTable: function(tableSelector) {
	        var list = new Array();
	        $(tableSelector + " tr[id ^= 'locarno_id_']").each(function() {
	        	var obj = new Object();
	        	$(this).find("td").each(function() {
	                obj[$(this).attr("data-val")] = $(this).html().trim();
	            });
	            list.push(obj);
	        });
	        return list;
	    },
	    buildTableFromLocarnoList: function(tableSelector, list) {
	        // clear table by removing all rows except first, which is the header
	        $(tableSelector).find("tr:gt(0)").remove();
	        for(var i = 0; i < list.length; i++){
	            var row = "<tr id='locarno_id_" + list[i].id + "'>";
	            row += "<td data-val='classification'>" + list[i].classification + "</td>";
	            row += "<td data-val='indication'>" + list[i].indication + "</td>";
	            if (!!list[i].options) { // view mode
	            	row += "<td data-val='options'>" + list[i].options + "</td>";	
	            }
	            row += "</tr>";
	            $(tableSelector + " tbody:last").append(row);
	        }
	    },
	    emptyLocarnoSelectSubclasses: function() {
	    	$('#selectLocarnoSubclass').empty();
	    },
	    refreshLocarnoSelectSubclasses: function(subclasses) {
	    	var valueEmptyOption = $('#locarnoSelectText').html();
	    	$('#selectLocarnoSubclass').append('<option value="">' + valueEmptyOption + '</option>');
			for (var i=0; i<subclasses.length; i++) {
				$('#selectLocarnoSubclass').append('<option value="' + subclasses[i].subclass + '">' + subclasses[i].subclass  + '</option>');
			}
	    },
	    appendLocarnoComplexProductClass: function() {
	    	var classValue = $('#selectLocarnoClass').val();
	    	if (classValue != '') {
		    	var subclassValue = $('#selectLocarnoSubclass').val();
		    	if (subclassValue != '') {
			    	var classAndSubclass = classValue + "." + subclassValue;
			    	var input = "<input type='hidden' name='classes' value='" + classAndSubclass + "' />";
			    	var li = "<li>" + input + classAndSubclass + "<a class='remove-icon'></a></li>";
			    	$('#listAddedLocarnoComplexProductClasses:last').append(li);
		    	}
	    	}
	    },
	    removeLocarnoErrors: function() {
	    	removePreviousErrors('#section_locarno');
	    	removePreviousErrors('#locarnoList');
	    }
	};
	
	var ajax = {
		loadAddClass: function() {
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.displayAddNewClass,
				type: 'GET',
				success: function(html) {
					manipulation.updateSectionLocarno(html);
				},
				error: utils.error('#section_locarno'),
				complete: function() {
					manipulation.showSectionLocarno();
				}
			});
		}, 
		addClass: function() {
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.addClass,
				data: $('#section_locarno #formAddClass input:checkbox:checked'),
				type: 'POST',
				success: function(html) {
					manipulation.refreshLocarnoList(html);
					manipulation.hideSectionLocarno();
				},
				error: utils.error('#section_locarno')
			});
		},
		loadAddNewProduct: function() {
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.displayAddNewProduct,
				type: 'GET',
				success: function(html) {
					manipulation.updateSectionLocarno(html);
				},
				error: utils.error('#section_locarno'),
				complete: function() {
					manipulation.showSectionLocarno();
				}
			});
		},
		addNewProduct: function(e) {
			var dataToSend = $('#formAddNewProduct').serialize();
			dataToSend = dataToSend + "&id=" + $('#hiddenFormIdNP').val();
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.addNewProduct,
				type: 'POST',
				data: dataToSend,
				success: function(html) {
					// We check if the controller has returned the same view, this will mean there are errors
					if ($('#formAddNewProduct', $(html)).length != 0) {
						// We come back to paint the same page
						manipulation.updateSectionLocarno(html);
					} else {
						manipulation.refreshLocarnoList(html);
						manipulation.hideSectionLocarno();
					}
				},
				error: utils.error('#section_locarno')
			});
		},
		loadAddNewComplexProduct: function() {
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.displayAddNewComplexProduct,
				type: 'GET',
				success: function(html) {
					manipulation.updateSectionLocarno(html);
				},
				error: utils.error('#section_locarno'),
				complete: function() {
					manipulation.showSectionLocarno();
				}
			});
		}, 
		addNewComplexProduct: function() {
			var dataToSend = $('#formAddNewComplexProduct').serialize();
			dataToSend = dataToSend + "&id=" + $('#hiddenFormIdNC').val();
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.addNewComplexProduct,
				type: 'POST',
				data: dataToSend,
				success: function(html) {
					// We check if the controller has returned the same view, this will mean there are errors
					if ($('#formAddNewComplexProduct', $(html)).length != 0) {
						// We come back to paint the same page
						manipulation.updateSectionLocarno(html);
					} else {
						manipulation.refreshLocarnoList(html);
						manipulation.hideSectionLocarno();
					}
				},
				error: utils.error('#section_locarno')
			});
		},
		getNewProductForEdit: function(locarnoId) {
			manipulation.removeLocarnoErrors();
	        $.ajax({
	            url: nav.getNewProductForEdit,
	            type: 'GET',
	            data: 'id=' + locarnoId,
	            success: function(html) {
	            	manipulation.updateSectionLocarno(html);
	            	manipulation.showSectionLocarno();
	            },
	            error: utils.error('#locarnoList')
	        });
	    },
		remove: function(locarnoId) {
			manipulation.removeLocarnoErrors();
			$.ajax({
				url: nav.remove,
				data: 'id=' + locarnoId,
				type: 'GET',
	            success: function(html) {
	            	manipulation.refreshLocarnoList(html);
	            },
	            error: utils.error('#locarnoList')
			});
		},
		integration: {
			loadLocarnoSubclasses: function(clazz) {
				manipulation.removeLocarnoErrors();
				manipulation.emptyLocarnoSelectSubclasses();
				if (clazz != ''){
					$.ajax({
						url: nav.integration.getSubclasses,
						type: 'GET',
						data: 'clazz=' + clazz,
						success: function(subclasses) {
							manipulation.refreshLocarnoSelectSubclasses(subclasses);
						},
						error: utils.error('#section_locarno')
					});
				}
			},
			searchLocarnoClassifications: function() {
				manipulation.removeLocarnoErrors();
				$.ajax({
					url: nav.integration.getClassifications,
					type: 'POST',
					data: $('#formSearchClass').serialize(),
					success: function(html) {
						// We check if the controller has returned the same view, this will mean there are errors
						if ($('#formSearchClass', $(html)).length != 0) {
							// We come back to paint the same page
							manipulation.updateSectionLocarno(html);
						} else {
							manipulation.refreshLocarnoClassificationsList(html);	
						}
					},
					error: utils.error('#section_locarno')
				});
			}
		}	
	};
	
}.apply(locarno);
