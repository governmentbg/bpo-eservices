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
			data += "terms[" + (i) + "].idClass=" + selectedTerms[key].idClass + "&terms[" + (i++) + "].description=" + encodeURIComponent(selectedTerms[key].text);
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


$("#modal-taxonomy").live("hide", function(event) {
	if (!terms.isDataSaved()) {
		if (terms.getNumberOfTerms()>0) {
			if (!confirm(getMessage("gs.warning.closeSearch"))) {
				event.preventDefault();
			}
		}
	}
});

function removeTerm(term, classId, langId, imported, deleteButton) {
	if($(deleteButton).closest("section").find("#tableGoodsAndServices").find(".term-list > li:visible").size() > 1) {
		var str = "term=" + encodeURIComponent(term) + "&classId=" + classId + "&langId=" + langId + "&imported=" + imported;
		$.ajax({
			url: "removeTerm.htm",
			type: "POST",
			data: str,
			cache: false,
			success: function (html) {
				$(deleteButton).closest("section").find("#tableGoodsAndServices").refreshGS({langId: langId, keepClassOpen: classId});
			}
		});
	} else {
		showWarningModal($("#goodsServices_lastTerm").html());
	}
}

$(".term-list:not(.notRemovableClass) .term-close").live('click', function (e) {
    var appLang = $("#appLang").val();
    var langId = $("#gs_Languages").val();
    if(appLang != null && appLang != ""){
        langId = langId + ","+appLang;
    }

    var classId = $(this).closest("tr").find(".class-number").html();
	var term = $(this).closest("li").find(".name").html();
	var imported = $("#importedId").val();

	removeTerm(term, classId, langId, imported, $(this));
});

function removeClass(classId, langId, imported, deleteButton) {
	if($(deleteButton).closest("section").find("#tableGoodsAndServices").find("ul.term-list:visible").size() > 1) {
		var str = "&classId=" + classId + "&langId=" + langId + "&imported=" + imported;
		$.ajax({
			url: "removeClass.htm",
			type: "POST",
			data: str,
			cache: false,
			success: function (html) {
				$(deleteButton).closest("section").find("#tableGoodsAndServices").refreshGS({langId: langId});
			}
		});
	} else {
		showWarningModal($("#goodsServices_lastClass").html());
	}
}

$(".term-options:not(.notRemovableClass) a.term-close").live('click', function (e) {
    var appLang = $("#appLang").val();
    var langId = $("#gs_Languages").val();
    if(appLang != null && appLang != ""){
        langId = langId + ","+appLang;
    }

    var classId = $(this).closest("tr").find(".class-number").html();
	var imported = $("#importedId").val();

	removeClass(classId, langId, imported, $(this));
});

function resetTerms(langId, resetButton){
	var str = "&langId=" + langId;
	$.ajax({
		url : "resetTerms.htm",
		type : "POST",
		data : str,
		cache : false,
		success : function(html) {
			$(resetButton).closest("section").find("#tableGoodsAndServices").refreshGS({langId: langId});
		}
	});
}

function cleanGS(langId){
	var str = "&langId=" + langId;
	$.ajax({
		url : "cleanGS.htm",
		type : "POST",
		data : str,
		cache : false,
		success : function(html) {
			$("#tableGoodsAndServices").refreshGS({langId: langId});
		}
	});
}

$(".terms-header:not(.notRemovableClass) .terms-refresh").live('click', function (e) {
	if($("#manuallyGS").is(":hidden")){
        var appLang = $("#appLang").val();
        var langId = $("#gs_Languages").val();
        if(appLang != null && appLang != ""){
            langId = langId + ","+appLang;
        }

        resetTerms(langId, $(this));
	}
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

/**
 * Did you mean functionality. Calls asynchronously the did you mean service and loads the response
 */
function didYouMean(termInput, languageId, process) {
	$('.taxonomy-search-input').removeAttr("timeout");
	$.ajax({
		url: "didYouMean.htm",
		cache: true,
		data: "term="+$(termInput).val()+"&langId="+languageId,
		success: function(response) {
			process(response);
		}
	});
}

$('.taxonomy-search-input').typeahead({
	source: function (query, process) {
		var searchInput=$('.taxonomy-search-input');
		if (searchInput.val()!="" && searchInput.attr("timeout")!="triggered") {
			searchInput.attr("timeout", "triggered");
			setTimeout(function(){didYouMean(searchInput, getLanguageInfo().first, process)},700);
		}
        return [""];
    },
	minLength:3,
	matcher: function (item) {
      return true;
    }
});
/**
 * Did you mean functionality END
 */


//To be checked again when service works properly
$('.notloadedNodes').live('show', function(){
	var collapsedItem = $(this);
	var languageInfo = getLanguageInfo();
	
	var data='langId='+languageInfo.first+'&taxoConceptNodeId='+$(this).attr('id');
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

$('.taxonomy-search-button').live('click', function(){
	var tableResults = $('.taxonomy-list').find('.last-level');
	
	$('.taxonomy-list').attr('data-term', $('.taxonomy-search-input').val());
	$('.taxonomy-list').attr('data-loaded-page', '1');

	var languageInfo = getLanguageInfo();
	var data='langId='+languageInfo.first+'&term='+$('.taxonomy-search-input').val();
	$("ul.first-level").generateTreeView({term : $('.taxonomy-search-input').val(), langId : languageInfo.first});
	$(tableResults).searchTerm({searchData:data});
});


/**
 *  Import Nice Class Heading functionality
 */
$('#modal-gands-importHeading .Save').live('click', function() {
	var modalPopup = $(this).closest("#modal-gands-importHeading");
	var languageInfo = getLanguageInfo();
	$(this).closest("#modal-gands-importHeading").importClassHeading({langId:languageInfo.first, classId:$(modalPopup).find(".nice-classes .active").html()});
});

$('#modal-gands-importHeading .SaveAndClose').live('click', function() {
	var modalPopup = $(this).closest("#modal-gands-importHeading");
	var languageInfo = getLanguageInfo();
	$(this).closest("#modal-gands-importHeading").importClassHeading({langId:languageInfo.first, classId:$(modalPopup).find(".nice-classes .active").html()});
});

$("#modal-gands-importHeading button").live('click', function (e) {
	var languageInfo = getLanguageInfo();
	$(this).closest("#modal-gands-importHeading").loadImportedHeading({langId:languageInfo.first, classId:$(this).html()});
});

$("#modal-gands-importHeading").on("show.bs.modal", function (e) {
	$(this).clear();
});
/**
 *  Import Nice Class Heading functionality - END
 */

/**
 *	Provide my list functionality
 **/
$("#modal-gands-mylist").live("show.bs.modal", function (e) {
	$(this).clear();
});

$("#modal-gands-mylist button").live('click', function (e) {
	$(".modal-gands-mylist").find("h3").show();
	$(".modal-gands-mylist").find("h3").find(".class-number").html($(this).html());
	$(".modal-gands-mylist").find("textarea").css('display', 'block');
});

$("#modal-gands-mylist .unknown-class").live('click', function (e) {
	$(".modal-gands-mylist .hiddenButton").click();
	$(".modal-gands-mylist .hiddenButton").addClass("active");
	$(".modal-gands-mylist .btn").removeClass("active");
});

$("#modal-gands-mylist [data-toggle='buttons-radio'] button").live('click', function (e) {
	$(this).siblings().removeClass("active");
});

$('#modal-gands-mylist .Save').live('click', function() {
	var modalPopup = $(this).closest(".modal-gands-mylist");
	var languageInfo = getLanguageInfo();
	if($(modalPopup).find("textarea").val() == ""){
        $(modalPopup).find("textarea").addClass("error");
        return;
	} else {
        $(modalPopup).provideTerms({
            langId: languageInfo.first,
            classId: $(modalPopup).find(".nice-classes .active").html(),
            term: $(modalPopup).find("textarea").val()
        });
        $(modalPopup).find("textarea").removeClass("error");
    }
});

$('.modal-gands-mylist .SaveAndClose').live('click', function() {
	var modalPopup = $(this).closest(".modal-gands-mylist");
	var languageInfo = getLanguageInfo();
	if ($(modalPopup).find(".nice-classes .active")!=null) {
        if($(modalPopup).find("textarea").val() == ""){
            return;
        } else {
            var errors = $(modalPopup).provideTerms({
                langId: languageInfo.first,
                classId: $(modalPopup).find(".nice-classes .active").html(),
                term: $(modalPopup).find("textarea").val()
            });
			if (!errors) {
				modalPopup.modal('hide');
			}
        }
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
 * Check Language before loading the modal popups - END
 */

/**
 * Avoid page reloading after item selection 
 */
$(".typeahead.dropdown-menu a.dropdown-item").live('click', function(e) {
	e.stopPropagation();
});

$(".showAllTerms").live("click", function (e){
	e.preventDefault();
	$(".term-list .toShowHide").show();
	$(this).hide();
	$(this).siblings(".hideAllTerms").show();
	$("tr:not(.template) .showClassTerms").hide();
	$("tr:not(.template) .hideClassTerms").show();
});

$(".hideAllTerms").live("click", function (e){
	e.preventDefault();
	$(".term-list .toShowHide").hide();
	$(this).hide();
	$(this).siblings(".showAllTerms").show();
	$("tr:not(.template) .showClassTerms").show();
	$("tr:not(.template) .hideClassTerms").hide();
});

$(".showClassTerms").live("click", function (e){
	e.preventDefault();
	$(this).parent().siblings(".term-list").find(".toShowHide").show();
	$(this).hide();
	$(this).siblings(".hideClassTerms").show();
});

$(".hideClassTerms").live("click", function (e){
	e.preventDefault();
	$(this).parent().siblings(".term-list").find(".toShowHide").hide();
	$(this).hide();
	$(this).siblings(".showClassTerms").show();
});

$("#modal-gands-editAsText").live("hide.bs.modal", function (){
	$("#modal-gands-editAsText").clear();
});

$(".term-options:not(.notRemovableClass) a.term-edit").live("click", function (e){
	e.preventDefault();
	var languageInfo = getLanguageInfo();
	var langId = languageInfo.first;
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