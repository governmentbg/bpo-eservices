var terms = new Terms();

function Terms() {

	var selectedTerms = {}; 
	var selectedClasses = {}; 
	var dataSaved = false;
	
	this.addTerm=addTerm;
	this.removeTerm=removeTerm;
	this.getNumberOfClasses=getNumberOfClasses;
	this.getNumberOfTerms=getNumberOfTerms;
	this.generateAddedTerms = generateAddedTerms;
	this.isDataSaved = isDataSaved;
	this.containsTerm = containsTerm;
	
	clearData();

	function isDataSaved() {
		return dataSaved;
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
		
		var currentListCount = $(".taxonomy-tree .active .taxonomy-selected-count").first();
		currentListCount.html(parseInt(currentListCount.html())+1);
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
		
		var currentListCount = $(".taxonomy-tree .active .taxonomy-selected-count").first();
		currentListCount.html(parseInt(currentListCount.html())-1);
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
				data+="&"
			}
			data+="terms["+(i)+"].idClass="+selectedTerms[key].idClass+"&terms["+(i++)+"].description="+encodeURIComponent(selectedTerms[key].text);
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

function removeTerm(term, classId, langId,$this) {
	var str = "term=" + encodeURIComponent(term) + "&classId=" + classId + "&langId=" + langId;
	$this.parent().addClass('loading-element-before');
	$.ajax({
		url : "removeTerm.htm",
		type : "POST",
		data : str,
		cache : false,
		success : function(html) {
			$("#tableGoodsAndServices").refreshGS({langId: langId});
		},
		error: function(){
			$this.parent().removeClass('loading-element-before');
		}
	});
}

$(".term-list:not(.notRemovableClass) .term-close").live('click', function (e) {
	var langId = $(this).closest("section").find("ul.language-tabs").find("li.active").find("a").attr("href");
	var classId = $(this).closest("tr").find(".class-number").html();
	var term = $(this).closest("li").find(".fullname").html();

	removeTerm(term, classId, langId, $(this))
});


$(".importedNiceClassHeading").live('click', function(e) {
	var langId = $(this).attr("data-language");
	var str = "langId=" + langId + "&removal=" + $(this).is(":checked") + "&classId=" + $(this).attr("data-classId");
	$.ajax({
		url : "updateDisabledRemoval.htm",
		type : "POST",
		data : str,
		cache : false,
		success : function(html) {
			$("#tableGoodsAndServices").refreshGS({langId: langId});
		}
	});
});


$('#goodsandservices a[data-toggle="tab"]').live('click', function (e) {
	$("#tableGoodsAndServices").refreshGS({langId: $(this).attr("href")});
});

function getLanguageInfo(notcached) {
	var languageInfo = new Object();
	if ((notcached==null
		|| notcached==false)
		&& $("body").data("languageDataRetrieved")) {
		languageInfo.first = $("body").data("firstLanguage");
		languageInfo.firstLabel = $("body").data("firstLabel");
		languageInfo.second = $("body").data("secondLanguage");
		languageInfo.secondLabel = $("body").data("secondLabel");
		languageInfo.secondLanguageTranslation = $("body").data("secondLanguageTranslation");
	} else {
		$.ajax({
			url: "getLanguageInfo.htm",
			async: false,
			cache: false,
			data: "",
			success: function(response) {
				$("body").data("firstLanguage", response.first);
				$("body").data("firstLabel", response.firstLabel);
				$("body").data("secondLanguage", response.second);
				$("body").data("secondLabel", response.secondLabel);
				$("body").data("secondLanguageTranslation", response.secondLanguageTranslation);
				$("body").data("languageDataRetrieved", true);
				languageInfo = response;
			}
		});
	}
	return languageInfo;
}


$(".add-terms-button").live("click", function(event) {
	$.ajax({
		url: "addTerms.htm",
		type: "POST",
		data: terms.generateAddedTerms(),
		cache: false,
		success: function(nodes) {
			var languageInfo = getLanguageInfo();
			
			$("#tableGoodsAndServices").refreshGS({langId: languageInfo.first, scroll: true});
		}
	});

	terms = new Terms();
	$("#modal-taxonomy").modal("hide");
});


//have to dingify specific table
$("#tableGoodsAndServices .sorter").live("click", function() {
	$(this).toggleClass("asc");
	$(this).toggleClass("desc");

	var langId = $(this).closest("section").find("ul.language-tabs").find("li.active").find("a").attr("href");

	if ($(this).hasClass("asc")) {
		$("#tableGoodsAndServices").refreshGS({langId: langId, reverse: false});
	} else {
		$("#tableGoodsAndServices").refreshGS({langId: langId, reverse: true});
	}
	
});



//To be checked again when service works properly
$('.notloadedNodes').live('show', function(){
	var collapsedItem = $(this);
	var languageInfo = getLanguageInfo();
	
	data='langId='+languageInfo.first+'&taxoConceptNodeId='+$(this).attr('id');
	$.ajax({
		url: "conceptNodes.htm",
		type: "GET",
		data: data,
		cache: false,
		success: function(nodes) {
			for (var i=0;i<nodes.length;i++) {
				if (nodes[i].id!=null){
					if (nodes[i].leaf) {
						$(getLeaf(nodes[i])).appendTo(collapsedItem);
					} else {
						$(getNode(nodes[i])).appendTo(collapsedItem);
					}
					$(collapsedItem).removeClass('notloadedNodes');
					$(collapsedItem).find('.notloadedNodes').collapse('hide')
				}
			}
		}
	});
});

$('.taxonomy-list').find(':checkbox').live('click', function(){
	var term = new Term($(this).closest("li").find("span").html(), $(this).closest("li").find("span").attr("data-class"));
	if ($(this).filter(":checked").length==1) {
		terms.addTerm(term);
	} else {
		terms.removeTerm(term);
	}
});

$('.last-level .sorterSelection .sorter').live("click", function() {
	var languageInfo = getLanguageInfo();
	var tableResults = $('.taxonomy-list').find('.last-level');
	$('.taxonomy-list').attr('data-loaded-page', '1');

	var searchData = 'term='+$('.taxonomy-list').attr('data-term');
	searchData += '&langId='+languageInfo.first + ($(".taxonomy-tree li.active span").attr("data-id")!=null?'&taxoConceptNodeId='+$(".taxonomy-tree li.active span").attr("data-id"):'');
	
	$(tableResults).searchTerm({			
		filter:$('#classDistribution').val(), 
		searchData:searchData, appendResults:false, 
		async:false, 
		orderBy: ($(this).hasClass("asc")?'DESC':'ASC'), 
		sortBy: $(this).attr("data-sortby")
	});
});

$('#classDistribution').live("change", function(){
	var tableResults = $('.taxonomy-list').find('.last-level');
	var languageInfo = getLanguageInfo();
	$('.taxonomy-list').attr('data-loaded-page', '1');

	data='langId='+languageInfo.first+'&term='+$('.taxonomy-list').attr('data-term');

	var anySort = ($('.last-level .sorter.asc')!= null ? $('.last-level .sorter.asc') : $('.last-level .sorter.desc'));
	$(tableResults).searchTerm({searchData:data, filter:$(this).val(), 
				orderBy: ($(anySort).length>0?($(anySort).hasClass("asc")?'ASC':'DESC'):null), 
				sortBy:($(anySort).length>0?($(anySort).attr("data-sortby")):null)
				});
});

$('.taxonomy-list,.last-level').scroll(function(){
	var tableResults = $('.taxonomy-list').find('.last-level');

	var position = (!$('.modal-no-taxonomy-body').exists()?$('.taxonomy-list').scrollTop():$('.last-level').scrollTop());
	var windowsSize = (!$('.modal-no-taxonomy-body').exists()?$('.taxonomy-list').height():$('.last-level').height());
	var size = (!$('.modal-no-taxonomy-body').exists()?$('.last-level').height():$('.results').height());
	var offset = 50;
	var languageInfo = getLanguageInfo();

	if (size-position-windowsSize<50 
			&& $(this).find(".loading").length==0) {

		var page = parseInt($('.taxonomy-list').attr('data-loaded-page'));
		page +=1;
		
		var anySort = ($('.last-level .sorter.asc')!= null ? $('.last-level .sorter.asc') : $('.last-level .sorter.desc'));

		$('.taxonomy-list').attr('data-loaded-page', page);

		data='langId='+languageInfo.first+'&term='+$('.taxonomy-list').attr('data-term')+'&page='+page+($(".taxonomy-tree li.active span").attr("data-id")!=null?'&taxoConceptNodeId='+$(".taxonomy-tree li.active span").attr("data-id"):'');
		
		$(tableResults).searchTerm({searchData:data, 
			filter:$('#classDistribution').val(), 
			appendResults:true, 
			orderBy: ($(anySort).length>0?($(anySort).hasClass("asc")?'ASC':'DESC'):null), 
			sortBy:($(anySort).length>0?($(anySort).attr("data-sortby")):null)
		});
	}
});

//Called when a node is selected 
$('.taxonomy-description').live('click', function(){
	var tableResults = $('.taxonomy-list').find('.last-level');
	$('.taxonomy-list').attr('data-loaded-page', '1');
	
	var languageInfo = getLanguageInfo();
	var taxoConceptNodeId = $(this).parent("span").attr("data-id");
	var data='langId='+languageInfo.first+'&term='+$('.taxonomy-search-input').val()+"&taxoConceptNodeId="+taxoConceptNodeId;
	
	$('.first-level li').removeClass("active");
	$(this).closest("li").addClass("active");
	
	$(tableResults).searchTerm({searchData:data});
});



/**
 *  Import Nice Class Heading functionality
 */
var importHeadings = function(ui_obj, closeModal) {
	var modalPopup = $(ui_obj).closest("#modal-gands-importHeading");
	var languageInfo = getLanguageInfo();
	$(ui_obj).closest("#modal-gands-importHeading").importClassHeading({langId:languageInfo.first,
		classId:$(modalPopup).find(".nice-classes .active").html(), closeModal: closeModal});
};

$('#modal-gands-importHeading .Save').live('click', function () {
	importHeadings(this, 0);
});

$('#modal-gands-importHeading .SaveAndClose').live('click', function () {
	importHeadings(this, 1);
});

$("#modal-gands-importHeading button").live('click', function (e) {
	var languageInfo = getLanguageInfo();
	$(this).closest("#modal-gands-importHeading").loadImportedHeading({langId:languageInfo.first, classId:$(this).html()});
});

$("#modal-gands-importHeading").on("show.bs.modal", function (e) {
	$(this).clear();
});

$("#modal-gands-importHeading [data-toggle='buttons-radio'] button").live('click', function (e) {
	$("#modal-gands-importHeading").find(".nice-classes-headings").clear();
	$(this).siblings().removeClass("active");
});
/**
 *  Import Nice Class Heading functionality - END
 */

/**
 *	Provide my list functionality
 **/
$("#modal-gands-mylist").on("show.bs.modal", function (e) {
	$(this).clear();
});

$("#modal-gands-mylist button:not('.SaveAndClose')").live('click', function (e) {
	var languageInfo = getLanguageInfo();
	var clsNumber = $(this).html()
	var str = "classId=" + clsNumber + "&langId=" + languageInfo.first;
	showLoadingScreen();
	$.ajax({
		url: "loadNiceClassHeading.htm",
		type: "GET",
		data: str,
		success: function (termsHeading) {
			var termsText = '';
			for(var i=0; i< termsHeading.termForms.length; i++){
				termsText = termsText+ termsHeading.termForms[i].description+"; ";
			}
			$("#modal-gands-mylist .classDescHolder").html(termsText);
		},
		error: function (){
			$("#modal-gands-mylist .classDescHolder").html(
				"<span style='color: red'>"+$("#generic_errors_headings_fetch").html()+"</span>");
		},
		complete: function (){
			$("#modal-gands-mylist .classDescHolder").show();
			$(".modal-gands-mylist").find("h3").show();
			$(".modal-gands-mylist").find("h3").find(".class-number").html(clsNumber);
			$(".modal-gands-mylist").find("textarea").css('display', 'block');
			hideLoadingScreen();
		}
	});
});

$("#modal-gands-mylist [data-toggle='buttons-radio'] button").live('click', function (e) {
	$(this).siblings().removeClass("active");
});

$('#modal-gands-mylist .Save').live('click', function() {
	var modalPopup = $(this).closest(".modal-gands-mylist");
	var languageInfo = getLanguageInfo();
	$(modalPopup).provideTerms({langId:languageInfo.first, classId:$(modalPopup).find(".nice-classes .active").html(), term:$(modalPopup).find("textarea").val()});
});

$('#modal-gands-mylist .SaveAndClose').live('click', function() {
	var modalPopup = $(this).closest(".modal-gands-mylist");
	var languageInfo = getLanguageInfo();
	if ($(modalPopup).find(".nice-classes .active")!=null) {
        var errors = $(modalPopup).provideTerms({langId:languageInfo.first, classId:$(modalPopup).find(".nice-classes .active").html(), term:$(modalPopup).find("textarea").val()});
        if (!errors) {
            modalPopup.modal('hide');
        }
    } else {
        modalPopup.modal('hide');
    }
});
/**
 *	Provide my list functionality - END
 **/

/**
 *	Provide translation functionality
 **/
$(".modal-translation button").live('click', function(e) {
	var modalPopup = $(this).closest(".modal-translation");
	var languageInfo = getLanguageInfo();
	$(modalPopup).provideTerms({langId:languageInfo.second, classId:$(modalPopup).find(".class-number").html(), term:$(modalPopup).find("textarea").val()});
});

$(".term-translation a").live("click", function(event) {
	var classId = $(this).closest("tr").find(".class-number").html();

	var languageInfo = getLanguageInfo();
	$("#modal-translation").find("h2 .class-number").html(classId);
	$("#modal-translation").find("h2 strong").html(languageInfo.secondLabel);
});

/**
 *	Provide translation functionality - END
 **/
 
/**
 * Check Language before loading the modal popup
 */
//Checks if a language is available, so continues to the onTrue, otherwise it prints a warning
function checkLanguage() {
	$(".gsSearch").find(".errorMessage").html("");
	var result = false;
	$.ajax({
		url : "hasLanguage.htm",
		type : "GET",
		async : false,
		cache : false,
		success : function(html) {
			if (html.indexOf("true")>-1) {
				result = true;
			} else {
				result = false;
			}
		}
	});
	return result;
}

function checkLanguageAsync(callbackOk, callbackError) {
    $(".gsSearch").find(".errorMessage").html("");
    $.ajax({
        url : "hasLanguage.htm",
        type : "GET",
        async : true,
        cache : false,
        success : function(html) {
            if (html.indexOf("true")>-1) {
                callbackOk();
            } else {
                callbackError();
            }
        }
    });
}

$("#modal-taxonomy,#modal-gands-mylist,#modal-gands-importHeading").on("show.bs.modal", function(event) {
	if (!checkLanguage()) {
		ajaxMessage("general.messages.goodsServices.selectLanguageFirst");
		event.preventDefault();
	} else {
		if ($(this).find(".taxonomy-tree").length>0 
				&& $(this).find(".taxonomy-tree").find(".first-level li").length == 0) {
			var languageInfo = getLanguageInfo();
			if (languageInfo.first!=null) {
				$("ul.first-level").generateTreeView({term : '', langId : languageInfo.first});
			}
		}		
	}
});

/**
 * Show taxonomy
 */
$("#gsbuilderShowPathButton").live('click', function(e) {
	e.preventDefault();
	if($(this).hasClass("active")){
		$(this).removeClass('active');
		$(this).closest('#tableGoodsAndServices').removeClass('pathVisible');
	} else {
		var langId = $(this).closest("section").find("ul.language-tabs").find("li.active").find("a").attr("href");
		$("#tableGoodsAndServices").refreshGS({langId: langId, generatePath: true});
		$(this).addClass('active');
	}
});
$("#gsbuilderShowPathButton").popover({
	placement: 'top',
	trigger: 'hover',
	content: $(this).data('content')
});
/**
 * Check Language before loading the modal popups - END
 */

/**
 * Avoid page reloading after item selection 
 */
$(".typeahead.dropdown-menu a.dropdown-item").live('click', function(e) {
	e.stopPropagation();
});

$(".class-options a.term-close").live("click", function (e){
	e.preventDefault();
	var langId = $(this).closest("section").find("ul.language-tabs").find("li.active").find("a").attr("href");
	var classId = $(this).parent("td").siblings(".class-number").html();
	$.ajax({
		url: "removeClass.htm",
		type: "POST",
		data: "langId="+langId+"&classId="+classId,
		success: function () {
			$("#tableGoodsAndServices").refreshGS({langId: langId});
		},
		error: function () {
			showWarningModal($("#generic_errors_service_fail"));
		}
	});
});

$("#modal-gands-editAsText").live("hide.bs.modal", function (){
	$("#modal-gands-editAsText").clear();
});

$(".class-options:not(.notRemovableClass) a.term-edit").live("click", function (e){
	e.preventDefault();
	var langId = $(this).closest("section").find("ul.language-tabs").find("li.active").find("a").attr("href");
	var classId = $(this).parent("td").siblings(".class-number").html();
	$.ajax({
		url: "getClassForEdit.htm",
		type: "GET",
		data: "langId="+langId+"&classId="+classId,
		cache: false,
		success: function (gsForm) {
			$("#modal-gands-editAsText .class-number").html(gsForm.classId);
			$("#modal-gands-editAsText .termsEditClass").val(gsForm.classId);
			$("#modal-gands-editAsText .termsEditText").val(gsForm.desc);
			$("#modal-gands-editAsText").modal("show");
		},
		error: function () {
			showWarningModal($("#generic_errors_service_fail"));
		}
	});
});

$('#modal-gands-editAsText .SaveAndClose').live('click', function() {
	var modalPopup = $("#modal-gands-editAsText");
	var languageInfo = getLanguageInfo();
	var classId = $(modalPopup).find(".termsEditClass").val();
	var termText = $(modalPopup).find(".termsEditText").val();

	var errors = $(modalPopup).provideTerms({langId:languageInfo.first, classId:classId, term:termText, replaceClass: true});
	if (!errors) {
		modalPopup.modal('hide');
	}
});