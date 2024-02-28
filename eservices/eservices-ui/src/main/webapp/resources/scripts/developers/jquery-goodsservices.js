jQuery.fn.exists = function(){return this.length>0;}

var terms = new Terms();

function Terms() {

	var selectedTerms = {}; 
	var selectedClasses = {}; 
	var dataSaved = false;
	var selectedNodeTaxonomy = {};
	
	this.addTerm=addTerm;
	this.removeTerm=removeTerm;
	this.getNumberOfClasses=getNumberOfClasses;
	this.getNumberOfTerms=getNumberOfTerms;
	this.generateAddedTerms = generateAddedTerms;
	this.isDataSaved = isDataSaved;
	this.containsTerm = containsTerm;
	this.removeSelectedTermOnNode = removeSelectedTermOnNode;
	this.addSelectedTermOnNode = addSelectedTermOnNode;
	this.getSelectedNodes = getSelectedNodes;
	
	clearData();

	function isDataSaved() {
		return dataSaved;
	}
	
	function getSelectedNodes(conceptId) {
		var data = selectedNodeTaxonomy[conceptId];
		
 		var count = 0;
		for (var prop in data) {
			// hasOwnProperty check is important because 
			// we don't want to count properties on the prototype chain
			// such as "get", "put", "size", or others.
			if(data[prop]!=null) {
				count++;
			}
		}
		return count;
	}
	
	function addSelectedTermOnNode(term, conceptId) {
		if (selectedNodeTaxonomy[conceptId]==null) {
			selectedNodeTaxonomy[conceptId] = {};
		}
		
		if (selectedNodeTaxonomy[conceptId][term]==null) {
			selectedNodeTaxonomy[conceptId][term] = {};
		}
		
		selectedNodeTaxonomy[conceptId][term] = true;
	
		var data = selectedNodeTaxonomy[conceptId];
	
 		var count = 0;
		for (var prop in data) {
			// hasOwnProperty check is important because 
			// we don't want to count properties on the prototype chain
			// such as "get", "put", "size", or others.
			if(data[prop]!=null) {
				count++;
			}
		}
		return count;
	}

	function removeSelectedTermOnNode(term, conceptId) {
		if (selectedNodeTaxonomy[conceptId]==null) {
			selectedNodeTaxonomy[conceptId] = {};
		}
		
		if (selectedNodeTaxonomy[conceptId][term]==null) {
			selectedNodeTaxonomy[conceptId][term] = {};
		}
		
		selectedNodeTaxonomy[conceptId][term] = null;
	
		var data = selectedNodeTaxonomy[conceptId];
	
 		var count = 0;
		for (var prop in data) {
			// hasOwnProperty check is important because 
			// we don't want to count properties on the prototype chain
			// such as "get", "put", "size", or others.
			if(data[prop]!=null) {
				count++;
			}
		}
		return count;
	}

	
	function addTerm(term) {
		selectedTerms[term.text] = term;
		if (selectedClasses[term.idClass]==null) {
			selectedClasses[term.idClass] = 1;
		} else {
			selectedClasses[term.idClass]++;
		}
		getNumberOfClasses();
		getNumberOfTerms();
	}

	function removeTerm(term) {
		if (selectedClasses[term.idClass]==1) {
			delete selectedClasses[term.idClass];
		} else {
			selectedClasses[term.idClass]--;
		}
		delete selectedTerms[term.text];
		getNumberOfClasses();
		getNumberOfTerms();
	}
	
	function getNumberOfClasses() {
		size = 0;
		for (var key in selectedClasses) {
			size++;
		}
		$(".taxonomy-count-classes").html(size);
		return size;
	}
	
	function getNumberOfTerms() {
		var size = 0;
		for (var key in selectedTerms) {
			size++;
		}
		$(".taxonomy-count-terms").html(size);
		return size;
	}	
	
	function clearData() {
		$(".last-level").html('');
		getNumberOfClasses();
		getNumberOfTerms();
	}
	
	function generateAddedTerms() {
		var data = "";
		var i = 0;
		for (var key in selectedTerms) {
			if(i>0) {
				data+="&";
			}
			data+="terms["+(i)+"].idClass="+selectedTerms[key].idClass+"&terms["+(i++)+"].description="+selectedTerms[key].text;
		}
		dataSaved = true;
		return data;
	}
	
	function containsTerm(term) {
		return (selectedTerms[term]!=null?true:false);
	}
}

function Term(text, idClass) {
	this.text = text;
	this.idClass = idClass;
}


/**
// * Refreshs the section of Goods and Services providing the GS selected for the given
 * language
 */
jQuery.fn.refreshGS = function() {
	var args = arguments[0] || {}; // It's your object of arguments
	var reverseLanguage = args.reverse != null ? args.reverse : false;
	var invoker = $(this);
	var languageInfo = args.languageInfo;
	var scroll = args.scroll;
	
	if (scroll) {
		var position = $(this).offset().top-50;
		$('html, body').animate({scrollTop:position}, 'slow');
	}
	
	if ($(invoker).exists()) {
		$(invoker).siblings(".showHideAll").find(".showAllTerms").hide();
		$(invoker).siblings(".showHideAll").find(".hideAllTerms").hide();

		if ($(".language-tabs").exists() && languageInfo!=null) {
			$(".language-tabs>li:nth-child(1) a").attr("href", languageInfo.first);
			$(".language-tabs>li:nth-child(1) a").html(languageInfo.firstLabel);

			if (languageInfo.secondLanguageTranslation) {
				$(".language-tabs>li:nth-child(2) a").attr("href", languageInfo.second);
				$(".language-tabs>li:nth-child(2) a").html(languageInfo.secondLabel);
				$(".language-tabs>li:nth-child(2) a").show();
				$(".provide-translation-info").show();
			} else {
				$(".language-tabs>li:nth-child(2) a").hide();
				$(".provide-translation-info").hide();
			}
		}
		
		var initiator = $(invoker).find("tbody");
		$.ajax({
			url: "terms.htm",
			type: "POST",
			data: "langId="+args.langId,
			cache: false,
            beforeSend: function(){
                $(".gsLoading").html("<div class='loading'></div>");
            },
			success: function(nodes) {
				$(initiator).find("tr:not(.template)").remove();
				var classes = {};
				for (var i=0;i<nodes.length;i++) {
					if (classes[nodes[i].cat] == null) {
						classes[nodes[i].cat] = new Array();
						classes[nodes[i].cat][0] = nodes[i];
					} else {
						classes[nodes[i].cat][classes[nodes[i].cat].length] = nodes[i];
					}
				}
				if (nodes.length>0) {
					$(".termsDefinition,#tableGoodsAndServices,.language-tabs,.provide-translation-icon,.provide-translation-arrow,.provide-translation").show();
				} else {
					$(".termsDefinition,#tableGoodsAndServices,.language-tabs,.provide-translation-icon,.provide-translation-arrow,.provide-translation").hide();
				}
				for (key in classes) {
					$(initiator).find(".template").hide();
					var cloned = $(initiator).find(".template").clone();
					$(cloned).find(".class-number").html(key);

					if (!classes[key][0].secondLanguage) {
						$(cloned).find(".firstLanguageTermsInfo").remove();
					} else {
						var languageInfo = getLanguageInfo();
						$(cloned).find(".firstLanguageTermsInfo").find("strong").html(languageInfo.firstLabel);
						$('<span>'+classes[key][0].firstLanguageDescription+'</span>').appendTo($(cloned).find(".firstLanguageTermsInfo"));
					}
					
					$(cloned).find(".term-list").find("li").remove();
					var classHasHiddenTerms = false;
					for (var i=0;i<classes[key].length;i++) {
						if (classes[key][i].containsAllNiceClassHeading) {
							if (classes[key][i].disabledRemoval) {
								$(cloned).find(".term-list").addClass("notRemovableClass");
								$(cloned).find(".disableRemoval input").attr("checked", "checked");
							}
							$(cloned).find(".disableRemoval input").attr("data-language", classes[key][i].langId);
							$(cloned).find(".disableRemoval input").attr("data-classId", classes[key][i].cat);
						} else {
							$(cloned).find(".disableRemoval").remove();
						}

						if (classes[key][i].validationState!=null) {
							var createdTerm;
							if (classes[key][i].validationState=='valid') {
								createdTerm = $(initiator).find(".template").find("li.term-notEditable").clone();
							} else if (classes[key][i].validationState=='editable') {
								createdTerm = $(initiator).find(".template").find("li.term-notEditable").clone();
							} else if (classes[key][i].validationState=='notfound') {
								createdTerm = $(initiator).find(".template").find("li.term-notEditable").clone();
							}
							$(createdTerm).find(".name").html(classes[key][i].name);
							$(createdTerm).data({
								'classId': key,
								'originalDescription': classes[key][i].name});
							if(i >= 4){
								$(createdTerm).addClass("toShowHide");
								if(!args.keepClassOpen || args.keepClassOpen != key) {
									$(createdTerm).hide();
									classHasHiddenTerms = true;
								}
							}
							$(createdTerm).appendTo($(cloned).find(".term-list"));
						}
					}
					if(classes[key].length <= 4){
						$(cloned).find(".showClassTerms").remove();
						$(cloned).find(".hideClassTerms").remove();
					} else {
						if(classHasHiddenTerms == false){
							$(cloned).find(".hideClassTerms").show();
						} else {
							$(cloned).find(".showClassTerms").show();
						}
						$(invoker).siblings(".showHideAll").find(".showAllTerms").show();
					}
					
					if (!classes[key][0].secondLanguage) {
						$(cloned).find(".term-translation").remove();
					}
					
					$(cloned).removeClass("template");
					
					if (!reverseLanguage) {
						$(cloned).appendTo(initiator);
					} else {
						$(cloned).prependTo(initiator);
					}
					$(cloned).show();
				}
				
				updateFeesInformation();
                $(".gsLoading").html("");
				if ($(".language-tabs>li.active a").attr("href")!=args.langId) {
					$(invoker).closest("section").find(".language-tabs a[href='"+args.langId+"']").tab('show');
				}
				$(invoker).find('ul.term-list li .name.editable').editableTerm(args.langId);
			}
		});
	}
};

/**
 * Clears the errors on the given section
 *
 */
jQuery.fn.clear = function() {
	$(".modal-gands-body .nice-classes button").each(function(){
        $(this).removeClass("active");
    })
	$(this).find(".hideOnClear").hide();
	$(this).find("input,textarea").val('');
	$(this).find(".flMessageError").html("");
	$("span.class-number").html('');
};

/**
 * Clears the errors on the given section
 *
 */
jQuery.fn.clearErrors = function() {
	$(this).find(".flMessageError").html("");
};


/**
 * Submits a list of GS to the current list of GS
 * and refreshs the table of GS
 */
jQuery.fn.provideTerms = function() {
	var args = arguments[0] || {};
	var langId = args.langId;
	var classId = args.classId;
	var term = args.term;
	var replaceClass = args.replaceClass || false;

	var modalPopup = $(this);
	$(modalPopup).clearErrors();
	var err = false;
	
	if (classId!=null 
			&& langId!=null) {
		var replaceClassData = replaceClass? '&replaceClass=true':'';
		var str = "classId="+classId+"&langId="+langId+replaceClassData+"&desc="+encodeURIComponent(term);
		$.ajax({
			url : "provideListOnMyOwnJSON.htm",
			type : "POST",
			async: false,
			data : str,
			cache : false,
			success : function(errors) {
				if (errors.length>0) {
					err = true;
					for (var i=0;i<errors.length;i++) {
						var errorCodes = errors[i].codes;
						for (var j=0;j<errorCodes.length;j++) {
							$(modalPopup).find(".flMessageError").getMessage(errorCodes[j]);
						}
					}
				} else {
					$("#tableGoodsAndServices").refreshGS({langId: langId, keepClassOpen: classId});
					$(modalPopup).clear();
				}
			}
		});
	}
	return err;
};

jQuery.fn.importClassHeading = function() {
	var args = arguments[0] || {};
	var langId = args.langId;
	var classId = args.classId;

	var modalPopup = $(this);

	if (classId!=null 
			&& langId!=null) {
		var str = "classId=" + classId + "&langId=" + langId;
		$.ajax({
			url : "importNiceClassHeading.htm",
			type : "POST",
			data : str,
			cache : false,
			success : function() {
				$(modalPopup).clear();
				$("#tableGoodsAndServices").refreshGS({langId: langId});
			}
		});
	}
};


jQuery.fn.loadImportedHeading = function() {
	var args = arguments[0] || {};
	var langId = args.langId;
	var classId = args.classId;

	var modalPopup = $(this);

	var str = "classId=" + classId + "&langId=" + langId;
	$.ajax({
		url : "loadNiceClassHeading.htm",
		type : "POST",
		data : str,
		cache : false,
		success : function(termsHeading) {
			
			$(modalPopup).find(".nice-classes-headings").clear();
			var template = $(modalPopup).find(".term-list-template");
		
			cloned = $(template).clone();
			$(cloned).removeClass("term-list-template").addClass("term-list");
			$(cloned).html("");
			$(modalPopup).find(".nice-classes-headings").find(".classHeading").html(classId);
			
			for (var i=0;i<termsHeading.termForms.length;i++) {
				var clonedTerm;
				if (termsHeading.termForms[i].error==null) {
					clonedTerm = $(template).find(".term-valid").clone();
				} else if (termsHeading.termForms[i].error.verificationAssessment=="Not found") {
					clonedTerm = $(template).find(".term-not-found").clone().appendTo(cloned);
				} else if (termsHeading.termForms[i].error.verificationAssessment=="Rejection term") {
					clonedTerm = $(template).find(".term-invalid").clone().appendTo(cloned);
				} else {
					clonedTerm = $(template).find(".term-modifiable").clone().appendTo(cloned);
				} 
				$(clonedTerm).find(".name").html(termsHeading.termForms[i].description);
				$(clonedTerm).appendTo(cloned);
			}

			$(cloned).appendTo($(modalPopup).find(".nice-classes-headings"));
			$(cloned).show();
		}
	});
};

/**
 * 
 *
 */
jQuery.fn.getMessage = function(code) {
	var errorNode = $(this);
	message=getMessage(code);
	if (code != message.trim()) {
		$(errorNode).append(message);
	}
}

function getMessage(code) {
	var message = null;
	$.ajax({
		url : "message.htm",
		type : "GET",
		async: false,
		data : "code="+code,
		cache : true,
		success : function(messageLabel) {
			message = messageLabel;
		}
	});
	return message;
}

/**
 * Searches for the given term with given language.
 *
 */
jQuery.fn.searchTerm = function() {
	var args = arguments[0] || {};
	var searchData = args.searchData;
	var appendResults = args.appendResults;
	var sortBy = args.sortBy;
	var orderBy = args.orderBy;
	var filter = args.filter;
	
	var async = args.async;
	
	var initiator = $(this);
	
	var data = encodeURI(searchData);
	data += (orderBy!=null?'&orderBy='+orderBy:'')+(sortBy!=null?'&sortBy='+sortBy:'')+(filter!=null?'&filter='+filter:'');
	
	if ($(initiator).data("loadedSync")==null
			&& ((appendResults==null || !appendResults) 
					|| ($(initiator).data("last-page-returnedResults")==null 
					|| $(initiator).data("last-page-returnedResults")>0))) {
		$("#ajaxBlock").show();
		if (appendResults==null || !appendResults) {
			$(initiator).html('');
		}
 
		$('<li class="loading">'
		   +'</li>').appendTo(initiator);
		$(initiator).data("loadedSync", "true");
	
		$.ajax({
			url: "search.htm",
			type: "POST",
			data: data,
			async: (async!=null?async:true),
			cache: false,
			success: function(nodes) {
				$(initiator).data("last-page-returnedResults", nodes.length)
				$("#ajaxBlock").hide();
				if ($('.modal-no-taxonomy-body').length>0) {
					var tbody;
					if ($('.modal-no-taxonomy-body table').length==0) {
						var table = $('<table class="results"></table>');
						var theader = $('<tr><th class="sorterSelection"><span class="classLabel"></span><a data-sortby="niceClass" class="sorter"></a></th> <th class="sorterSelection"><span class="classTerm"></span><a data-sortby="text" class="sorter"></a></th> </tr>');
						tbody = $('<tbody class="data"></tbody>');
						
						$(table).appendTo(initiator);
						$(theader).appendTo(table);
						$(theader).find(".classLabel").getMessage("gs.class");
						$(theader).find(".classTerm").getMessage("gs.inputTerm");
						$(tbody).appendTo(table);
	
						$.ajax({
							url: "getDistribution.htm",
							type: "POST",
							data: data,
							async: false,
							cache: false,
							success: function(classDistribution) {
								if (classDistribution.length>0) { 
									var classHeader = $(table).find("th [data-sortby='niceClass']").closest("th");
									var selectBox = $('<select id="classDistribution"></select>');
									
									var option = $('<option value=""></option>');
									$(option).appendTo(selectBox);
									for(i=0;i<classDistribution.length;i++) {
										option = $('<option value="'+classDistribution[i]+'">'+classDistribution[i]+'</option>');
										if (classDistribution[i]==filter) {
											$(option).attr('selected', 'selected');
										}
										$(option).appendTo(selectBox);
									}
									$(selectBox).appendTo(classHeader);
								}
							}
						});
					} else {
						tbody = $(initiator).find('table tbody.data');
					}
					
					for (var i=0;i<nodes.length;i++) {
						if (nodes[i].description!=null) {
							$('<tr><td class="class-number">'+nodes[i].idClass+'</td><td class="term-text"><ul><li> <label for="term-'+nodes[i].description+'">'
									+ (nodes[i].scopeAvailabilty?'<input type="checkbox" '+(terms.containsTerm(nodes[i].description)?'checked ':'') + 'id="term-'+nodes[i].description+'">':'') + '<span data-class="'+nodes[i].idClass+'">'+nodes[i].description+'</span></label></li></ul></td></tr>').appendTo(tbody);
						}
					}
	
					$('.last-level .sorter').removeClass("asc");
					$('.last-level .sorter').removeClass("desc");
					if (sortBy!=null) {
						$('.last-level .sorter[data-sortby="'+sortBy+'"]').addClass(orderBy==null||orderBy=="ASC"?"asc":"desc");
					}
					
				} else {
					for (var i=0;i<nodes.length;i++) {
						if (nodes[i].description!=null) {
							var  nodeData = '<li><label for="term-'+nodes[i].description+'" ';
							nodeData += '>';
							nodeData += (nodes[i].scopeAvailabilty?'<input type="checkbox" '+(terms.containsTerm(nodes[i].description)?'checked ':'') + 'id="term-'+nodes[i].description+'">':'') + '<span data-class="'+nodes[i].idClass+'" ';

							if (nodes[i].parentIds!=null && nodes[i].parentIds.length>0) {
								nodeData += ' data-parents="';
								for (var j=0;j<nodes[i].parentIds.length;j++) {
									nodeData+=nodes[i].parentIds[j]+",";
								}
								nodeData += '"';
							}
							
							nodeData += '>'+nodes[i].description+'</span></label></li>';
							$(nodeData).appendTo(initiator);
						}
					}
				}
				$(initiator).data("loadedSync", null);
				$(initiator).find(".loading").remove();
			}
		});
	}
};

/**
 * Generates the taxonomy view of GS
 *
 */
jQuery.fn.generateTreeView = function(term) {
	var args = arguments[0] || {};
	var collapsedItem = $(this);
	
	var data='langId='+args.langId;
	if (term!=null) {
		data+="&term="+args.term;
	}
	if ($(collapsedItem).exists()) {
		$.ajax({
			url: "conceptNodes.htm",
			type: "POST",
			data: data,
			cache: false,
			success: function(nodes) {
				$(collapsedItem).html('');
				printNode(nodes, $(collapsedItem));
				$('[rel="term-tooltip"]').tooltip({
					placement: 'top',
					trigger: 'hover'
				});
				$(".taxonomy-info-icon").tooltip({
					placement: 'top',
					trigger: 'hover'
				});
			}
		});
	}
}

jQuery.fn.editableTerm = function(langId) {
	var args = arguments[0] || {};
	var $editables = $(this);

	$editables.on('click', function () {
		var $this = $(this);
		if($this.hasClass('editing')) return false;
		$this.attr('contenteditable','true').addClass('editing');
		$this.html($this.parent().data('originalDescription'));
		$this.focus();
		$this.unbind('blur');

		$this.blur(fireEvent);
		$this.keypress(function(e){
			if(e.keyCode === 13){
				e.preventDefault();
				$this.trigger('blur');
				return false;
			}
			return true;
		})

		function fireEvent(){
			var currentName = $this.text().trim();
			var data = {
				classId: $this.parent().data('classId'),
				description: currentName,
				old: $this.parent().data('originalDescription'),
				langId: langId
			}
			$this.attr('contenteditable','false');
			$this.removeClass('editing');
			var tmpname = currentName;
			if(tmpname && tmpname.length > 60){
				tmpname = tmpname.substring(0,60)+'...';
			}
			$this.html(tmpname);
			$this.parent().addClass('loading-element-before');

			if(currentName !== $this.parent().data('originalDescription')){
				renameTerm(data,function(){
					$("#tableGoodsAndServices").refreshGS({langId: langId, keepClassOpen: data.classId, callback: function(){
							$this.parent().removeClass('loading-element-before');
						}});
				})
			}else{
				$this.parent().removeClass('loading-element-before');
			}

		}
	});


	function renameTerm(data, onsuccess) {
		$.ajax({
			url: "editTerm.htm",
			type: "POST",
			data: data,
			cache: false,
			async: true,
			success: function(result) {
				onsuccess(result);
			}
		});
	}
}

function printNode(nodes, collapsedItem) {
	for (var i=0;i<nodes.length;i++) {
	
		if (nodes[i].id!=null){
			if ($(collapsedItem).attr('class')=='first-level') {
				$(getFirstLevel(nodes[i])).appendTo(collapsedItem);

				if (nodes[i].children.length>0) {
					$(collapsedItem).find(".notloadedNodes").removeClass('notloadedNodes');

					var node = $(collapsedItem).find(".mid-level").filter("#"+generateNodeId(nodes[i]));
					node.html("");
					printNode(nodes[i].children, node);
				} else if (nodes[i].leaf) {
					$(collapsedItem).find(".notloadedNodes").removeClass('notloadedNodes');
					var node = $(collapsedItem).find(".mid-level").filter("#"+generateNodeId(nodes[i]));
					node.html("");
				}
			} else if (nodes[i].leaf || nodes[i].children.length==0) {
				$(getLeaf(nodes[i])).appendTo(collapsedItem);
			} else {
				$(getNode(nodes[i])).appendTo(collapsedItem);

				if (nodes[i].children.length>0) {
					$(collapsedItem).find(".notloadedNodes").removeClass('notloadedNodes');

					var node = $(collapsedItem).find(".mid-level").filter("#"+generateNodeId(nodes[i]));
					node.html("");
					printNode(nodes[i].children, node);
				} else if (nodes[i].leaf) {
					$(collapsedItem).find(".notloadedNodes").removeClass('notloadedNodes');
					var node = $(collapsedItem).find(".mid-level").filter("#"+generateNodeId(nodes[i]));
					node.html("");
				}
			}
		}
	}
}

function getLeaf(node) {
	return '<li>'
				+'<span data-target="#'+generateNodeId(node)+'" data-id="'+node.id+'"'+(node.nonTaxonomyParent!=null && node.nonTaxonomyParent?' '+'data-showNonTaxoTermsOnly="'+node.nonTaxonomyParent+'"' +' '+'data-niceClass="'+node.niceClass+'"':'')+'>'
					+'<span class="taxonomy-description">'+node.text+'</span>'
					+'<span class="taxonomy-selected-count">'+terms.getSelectedNodes(node.id)+'</span>'
					+'<span class="taxonomy-count">'+node.numTermsSatisfyingCriteria+'</span>'
				+'</span>'
			+'</li>';
}

function getNode(node) {
	return '<li>'
				+'<span data-toggle="collapse" data-target="#'+generateNodeId(node)+'"  data-id="'+node.id+'"'+(node.nonTaxonomyParent!=null && node.nonTaxonomyParent?' '+'data-showNonTaxoTermsOnly="'+node.nonTaxonomyParent+'"' +' '+'data-niceClass="'+node.niceClass+'"':'')+'>'
					+'<span class="taxonomy-description">'+node.text+'</span>'
					+'<span class="taxonomy-selected-count">'+terms.getSelectedNodes(node.id)+'</span>'
					+'<span class="taxonomy-count">'+node.numTermsSatisfyingCriteria+'</span>'
					+ (node.classScope ? '<a rel="taxonomy-popover" class="taxonomy-info-icon" data-title="'+node.classScopeDescription+'" data-target="#'+generateNodeId(node)+'-info"></a>' : '')
					+'<a class="taxonomy-open-icon"></a>'
				+'</span>'
				+'<ul class="mid-level in collapse notloadedNodes" id="'+generateNodeId(node)+'">'
                    +'<li>'
                        +'<div style="width:52px" class="loading"/>'
                    +'</li>'
				+'</ul>'
			+'</li>';

}

function getFirstLevel(node) {
	return '<li><span data-toggle="collapse" data-target="#'+generateNodeId(node)+'">'
					+node.text
					+(node.classScope ? '<a rel="taxonomy-popover" class="taxonomy-info-icon" data-title="'+node.classScopeDescription+'" data-target="#'+generateNodeId(node)+'-info"></a>' : '')
					+'<a class="taxonomy-open-icon"></a>'
				+'</span>'
				+'<ul class="mid-level in collapse notloadedNodes" id="'+generateNodeId(node)+'">'
                    +'<li>'
                        +'<div style="width:52px" class="loading"/>'
                    +'</li>'
				+'</ul>'
			+'</li>';
}

function generateNodeId(node) {
	return "taxonomy-"+node.id+"-"+node.numTermsSatisfyingCriteria;
}
