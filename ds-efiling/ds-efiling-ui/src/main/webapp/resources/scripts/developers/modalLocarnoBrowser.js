/*global Locarno, showMessageModal */
'use strict';
var ModalLocarnoBrowser = {
	locarnoToAdd: '',
    selectedClassNumbers: [],
    addedTerms: [],
    currentStatus: 'browsingStart',
    previousStatus: '',
    searchPage: 1,
    paginationAmount: 25,
    previousSearch: {
        selectedClass: -1,
        selectedSubclass: -1,
        searchInput: '',
        page: 1,
        pageSize: 25,
        sortBy : ''
    },
    jsonData: {},
    orderBy: '',
    sortBy: '',
    searchTerm: '',
    nav: {
        searchProductIndications: 'searchProductIndications.htm',
        translateProductIndication: 'getProductIndicationTranslation.htm',
        getTaxonomy: 'locarnoClassificationTaxonomy.htm'
    },
    pregQuote: function(str) {
        return (str + '').replace(/([\\\.\+\*\?\[\^\]\$\(\)\{\}\=\!\\<\>\|\:])/g, '\\$1');
    },

    highlightText: function(data, search) {
        return data.replace(new RegExp('(' + ModalLocarnoBrowser.pregQuote(search) + ')', 'gi'), '<strong>$1</strong>');
    },

    appendLocarnoModalResults: function(productIndications) {
        $('#locarnoResultsTable').show();
        $('#results-info').html('');
        var total = productIndications.totalResults;
        var pageSize = productIndications.pageSize;
        var amountPages = Math.ceil(total / pageSize);
        var topLimit = Number(ModalLocarnoBrowser.searchPage) + 2;
        var bottomLimit = Number(ModalLocarnoBrowser.searchPage) - 4;
        var paginationTemplate;
        if(ModalLocarnoBrowser.previousSearch.searchMode || !ModalLocarnoBrowser.previousSearch.searchMode && ModalLocarnoBrowser.previousSearch.selectedClass >= 0 && ModalLocarnoBrowser.previousSearch.selectedSubclass >= 0) {
        	paginationTemplate = ModalLocarnoBrowser.templates.pagination({
				total : total,
				currentPage : parseInt(ModalLocarnoBrowser.searchPage),
				topLimit : topLimit,
				bottomLimit : bottomLimit,
				pageSize : pageSize,
				amountPages : amountPages
			});
        }
        var showTerms = ModalLocarnoBrowser.previousSearch.selectedClass > -1 && ModalLocarnoBrowser.previousSearch.selectedSubclass > -1;
        var template = ModalLocarnoBrowser.previousSearch.searchMode || !ModalLocarnoBrowser.previousSearch.searchMode && showTerms ? ModalLocarnoBrowser.templates.results : ModalLocarnoBrowser.templates.taxonomy;
        var tableWrapper = $('#locarnoResultsTable>div');
        tableWrapper.html(template({
            data: _.sortBy(productIndications.results,function(el) { return !el.group; }),
            term: ModalLocarnoBrowser.searchTerm,
            orderBy: ModalLocarnoBrowser.orderBy,
            sortBy: ModalLocarnoBrowser.sortBy,
            alreadySelected: ModalLocarnoBrowser.addedTerms,
            pagination: paginationTemplate
        }));

        if(ModalLocarnoBrowser.previousSearch.searchMode) {
        	$('p', tableWrapper).each(function(event) {
	            var txt = $(this).html();
	            var appendTxt = ModalLocarnoBrowser.highlightText(txt, ModalLocarnoBrowser.searchTerm);
	            $(this).html(appendTxt);
	        });
        }
        return productIndications;
    },
    
    setClass: function() {
    	var data = $('#locarnoModalBrowser').data('flow');
		$('#locarnoModalBrowser').modal('show');
		$('#locarnoModalBrowser select#locarnoClasses').val(String(data.locarnoClass));
		$('#input-search').val('');
		$('#search-btn').click();
    },

    setSearchInfo: function(productIndications) {
        $('#results-info').html(ModalLocarnoBrowser.templates.resultsInfo({
            locarnoResultAmount: productIndications.totalResults
        }));
    },
    normalizeClass: function(classCode) {
    	if(classCode !== 0 && classCode !== '00' && !classCode) {
    		return -1;
    	}
    	var str = ''+classCode;
    	return ('00'+str).substring(str.length);
    },
    getLocarnoModalSearchParams: function(searchMode, element) {
    	var searchParams = {
            selectedClass: -1,
            selectedSubclass: -1,
            searchInput: '',
            page: 1,
            pageSize: ModalLocarnoBrowser.paginationAmount,
            searchMode: $('#isUsingTmDsView').val() === 'true' ? true : !!searchMode
        };
    	if(searchParams.searchMode) {
            searchParams.selectedClass = $('#locarnoModalBrowser select#locarnoClasses').val() || -1;
            searchParams.selectedSubclass = $('#locarnoModalBrowser select#locarnoSubclasses').val() || -1;
            searchParams.searchInput = $('#locarnoModalBrowser input#input-search').val();
            if(ModalLocarnoBrowser.locarnoToAdd.clickedClass){
            	searchParams.searchMode = false;
        	}
            ModalLocarnoBrowser.searchTerm = searchParams.searchInput;
    	}
    	else {
        	if(element) {
        		var $element = $(element);
        		searchParams.selectedClass = ModalLocarnoBrowser.normalizeClass($element.data('class'));
    	        searchParams.selectedSubclass = ModalLocarnoBrowser.normalizeClass($element.data('subclass'));
                searchParams.taxoConceptNodeId = $element.data('groupIdentifier');
        	}
    	}
        return searchParams;
    },
    searchProductIndications: function(searchMode, element) {
        var searchParams = ModalLocarnoBrowser.getLocarnoModalSearchParams(searchMode, element);
        ModalLocarnoBrowser.previousSearch = searchParams;
        ModalLocarnoBrowser.searchProductIndicationsCall(searchParams);
    },
    getTotalClassesAddedAmount: function() {
        var termsAmount = ModalLocarnoBrowser.addedTerms.length;
        var classesList = [];
        var i;
        for (i = 0; i < termsAmount; i++) {
            classesList.push(ModalLocarnoBrowser.addedTerms[i].classCode);
        }

        for (i = 0; i < ModalLocarnoBrowser.selectedClassNumbers.length; i++) {
            classesList.push(ModalLocarnoBrowser.selectedClassNumbers[i]);
        }

        var unifiedClasses = _.uniq(classesList);
        return unifiedClasses;
    },

    animateSelected: function(tr) {
        var scrollTop = $(window).scrollTop();
        var scrollLeft = $(window).scrollLeft();

        //duplicate tr and insert in a table with the same look and feel.
        $('#wrap-generated').remove();
        var replicatedTpl = _.template(document.getElementById('replicatedProductIndication').innerHTML);
        $('#locarnoModalBrowser .modal-body').append(replicatedTpl({
            data: tr.html()
        }));

        //position of tr
        var offset = tr.offset();
        $('#wrap-generated').css({
            'top': offset.top - scrollTop,
            'left': offset.left - scrollLeft,
            'width': tr.width(),
            'height': tr.height()
        });

        //move to the same position and size as the cart box
        var $cartInfo = $('#cart-info');
        var cartOffset = $cartInfo.offset();
        $('#wrap-generated').animate({
            'top': cartOffset.top - scrollTop,
            'left': cartOffset.left - scrollLeft,
            'width': $cartInfo.width(),
            'height': $cartInfo.height(),
            'opacity': 0
        }, 700, function() {
            $(this).remove();
        });
    },

    addTerm: function(checkbox) {
        var filter = {identifier: checkbox.closest('tr').attr('data-product-indication-identifier')};
        var termToAdd = _.findWhere(ModalLocarnoBrowser.jsonData.results, filter);
        ModalLocarnoBrowser.addedTerms.push(termToAdd);
    },
    cleanSelectTable: function(productIndicationIdentifier) {
        var row = $('#locarnoResultsTable tr[data-product-indication-identifier="' + productIndicationIdentifier + '"]');
        row.removeClass('active');
        row.attr('checked', false);
        row.find(':checkbox').attr('checked', false);
    },

    removeTerm: function(checkbox) {
        var productIndicationIdentifier = checkbox.closest('tr').attr('data-product-indication-identifier');
        ModalLocarnoBrowser.addedTerms = _.reject(ModalLocarnoBrowser.addedTerms, function(item) {
            return item.identifier === productIndicationIdentifier;
        });
        if (checkbox.closest('#locarnoSelected').size() > 0) {
            ModalLocarnoBrowser.cleanSelectTable(productIndicationIdentifier);
        }
    },

    getClassesAddedAmount: function() {
        var termsAmount = ModalLocarnoBrowser.addedTerms.length;
        var classesList = [];
        for (var i = 0; i < termsAmount; i++) {
            classesList.push(ModalLocarnoBrowser.addedTerms[i].classCode);
        }

        var unifiedClasses = _.uniq(classesList);
        return unifiedClasses;
    },

    updateTermsView: function() {
        var termsAmount = ModalLocarnoBrowser.addedTerms.length;
        if (termsAmount > 0) {
            $('#cart-info, #btn-done').removeClass('disabled');
        } else {
            $('#cart-info, #btn-done').addClass('disabled');
        }

        $('#terms-amount').html(termsAmount);
        var added = ModalLocarnoBrowser.getClassesAddedAmount();
        var addedClassesAmount = added.length;
        $('#classes-amount').html(addedClassesAmount);
    },

    showBrowserDisclaimers: function(checkbox, htmlMessageIdentifier) {
        var $checkbox = $(checkbox);
        var $tr = $checkbox.closest('tr');

        $checkbox.attr('checked', false);
        $tr.removeClass('active');
		$('body').addClass('modal-open');

        var span = $(checkbox).parent();

        span.on('click', function(e) {
            e.stopPropagation();
        });

        $('body').off('click', '.addItem');
        var secondClick = function(e) {
            var l = $(e.target);
            span.not(l).trigger('click');
            l.tooltip('hide').popover('toggle').toggleClass('active');
            return e.preventDefault();
        };
        span.popover({
            placement: 'top',
            container:'body',
            trigger: 'manual',
            html: 'true',
            template: '<div class="popover popover-add bottom"><div class="popover-inner"><div class="arrow"></div><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>',
            content: _.template(document.getElementById(htmlMessageIdentifier).innerHTML)
        }).on('click', secondClick).popover('show');
        var stopListeningEvents = function() {
            span.off('click');
            span.off('click', secondClick);
            $('body').off('click', '.cancelAdd', cancelAdd);
            $('body').off('click', closePopover);
            $('.popover:visible').remove();
        };
        var cancelAdd = function(e) {
            $('.popover:visible').remove();
            stopListeningEvents();
            return e.preventDefault();
        };
        $('body').on('click', '.cancelAdd', cancelAdd);
        var closePopover = function(e) {
            if ($(e.target).closest('.popover').size() === 0) {
                stopListeningEvents();
            }
        };
        $('body').on('click', closePopover);
        return;
    },
    showBrowserTermAlreadyAdded: function(checkbox) {
        return ModalLocarnoBrowser.showBrowserDisclaimers(checkbox, 'errorTermAlreadyAdded');
    },
    showBrowserTermsDisclaimer: function(checkbox) {
        return ModalLocarnoBrowser.showBrowserDisclaimers(checkbox, 'errorNoMoreTermsToBeAdded');
    },
    showBrowserClassDisclaimer: function(checkbox) {
        return ModalLocarnoBrowser.showBrowserDisclaimers(checkbox, 'errorClassNotAllowed');
    },
    evalAddingTerm: function(e) {
        var checkbox = $(e.target);
        var tr = checkbox.closest('tr');
        var checked = checkbox.attr('checked');

        e.stopPropagation();

        var filter = {identifier: checkbox.closest('tr').attr('data-product-indication-identifier')};
        var termToAdd = _.findWhere(ModalLocarnoBrowser.jsonData.results, filter);

        if(ModalLocarnoBrowser.isTermAlreadyAdded(termToAdd)){
            ModalLocarnoBrowser.showBrowserTermAlreadyAdded(checkbox);
            return;
        }

	 	if (checked && ModalLocarnoBrowser.locarnoToAdd && Locarno.isTermsMaxReached(ModalLocarnoBrowser.addedTerms.length)){
	 		ModalLocarnoBrowser.showBrowserTermsDisclaimer(checkbox);
	 		return;	 		
	 	}

        else if (checked && !ModalLocarnoBrowser.locarnoToAdd && Locarno.isTermsMaxReached(ModalLocarnoBrowser.addedTerms.length + 1)) {
            ModalLocarnoBrowser.showBrowserTermsDisclaimer(checkbox);
            return;
        }

        if (checked && !Locarno.isValidClass(termToAdd.classCode)) {
            ModalLocarnoBrowser.showBrowserClassDisclaimer(checkbox);
            return;
        }

        if (checked) {
            tr.addClass('active');
            ModalLocarnoBrowser.animateSelected(tr);
            ModalLocarnoBrowser.addTerm(checkbox);
        } else {
            tr.removeClass('active');
            ModalLocarnoBrowser.removeTerm(checkbox);
        }
        ModalLocarnoBrowser.updateTermsView();
    },
    isTermAlreadyAdded: function (termToValidate){
	    var alreadyAdded = false;
        var classCodeToValidate = termToValidate.classCode + "." + termToValidate.subclassCode;
        var textTermToValidate = termToValidate.text;
        $('#addedProductIndications tbody tr td span').each(function(index, elem){
            var classCodeItem = $($(this)).parent().parent().find('.locarno-classification').first().text();
            var termTextItem = $(this).text();
            if(textTermToValidate===termTextItem && classCodeToValidate===classCodeItem){
                alreadyAdded = true;
            }
        });
	    return alreadyAdded;
    },
    resetLocarnoBrowserVisualElements: function() {
        $('div#locarnoSelected').html('');
        $('#locarnoModalBrowser #input-search').val('');
        $('#locarnoModalBrowser #terms-amount').html('0');
        $('#locarnoModalBrowser #classes-amount').html('0');
        $('#locarnoModalBrowser #cart-info, #locarnoModalBrowser #btn-done').addClass('disabled');
    },

    resetModalLocarnoBrowser: function() {
        ModalLocarnoBrowser.resetLocarnoBrowserVisualElements();
        ModalLocarnoBrowser.selectedClassNumbers = [];
        ModalLocarnoBrowser.addedTerms = [];
        ModalLocarnoBrowser.previousStatus = '';
        ModalLocarnoBrowser.searchTerm = '';
        ModalLocarnoBrowser.searchPage = 1;
        ModalLocarnoBrowser.sortBy = '';
        ModalLocarnoBrowser.jsonData = {};
    },
    isSearchCriteriaEmpty: function(){
        var selectedClass = $('#locarnoModalBrowser select#locarnoClasses').val() || -1;
        var selectedSubclass = $('#locarnoModalBrowser select#locarnoSubclasses').val() || -1;
        var searchInput = $('#locarnoModalBrowser input#input-search').val();

        if(searchInput != "" || selectedClass != -1 || selectedSubclass != -1){
            return false;
        }
        return true;
    },
    enableDisableSearchButton: function(){
        if(ModalLocarnoBrowser.isSearchCriteriaEmpty()){
            $('#locarnoModalBrowser #search-btn').addClass('disabled');
        }
        else{
            $('#locarnoModalBrowser #search-btn').removeClass('disabled')
        }
    },
    hideSelected: function() {
        $('#locarnoResultsTable>div').show();
        $('#locarnoSelected').hide();
        ModalLocarnoBrowser.currentStatus = ModalLocarnoBrowser.previousStatus;
        ModalLocarnoBrowser.updateLocarnoModalHeader();
        $('#locarnoResultsTable,  #locarnoSearch').show();
        $('#locarnoSearchBack').hide();
        $('#view-terms').parent().removeClass('hide');
        $('#hide-terms').parent().addClass('hide');
        $('#btn-end').parent().addClass('hide');
        $('#btn-done').parent().removeClass('hide');
    },

    appendSelected: function() {
        function evalAdded(e) {
            var checkbox = $(e.target),
                tr = checkbox.closest('tr'),
                checked = checkbox.attr('checked'),
                pos = checkbox.data('term');
            if (!checked) {
                ModalLocarnoBrowser.removeTerm(checkbox);
                tr.remove();
                var input = $('#locarnoResultsTable input[data-term="' + pos + '"]');
                input.closest('tr').removeClass('active');
                input.attr('checked', false);
            }
            ModalLocarnoBrowser.updateTermsView();
            if (ModalLocarnoBrowser.addedTerms.length === 0) {
                ModalLocarnoBrowser.hideSelected();
            }
        }
        $('#locarnoSelected').html(ModalLocarnoBrowser.templates.selected({
            data: ModalLocarnoBrowser.addedTerms
        }));
        $($('#locarnoSelected'), ':checkbox').off('change');
        $($('#locarnoSelected'), ':checkbox').on('change', evalAdded);

    },

    viewSelected: function(e) {
        var wrapNav = $(e.target).closest('div');
        if (wrapNav.hasClass('disabled') || $(e.target).hasClass('disabled')) {
            return;
        }
        ModalLocarnoBrowser.previousStatus = ModalLocarnoBrowser.currentStatus;
        ModalLocarnoBrowser.currentStatus = 'basket';
        ModalLocarnoBrowser.updateLocarnoModalHeader();
        $('#locarnoResultsTable').hide();
        $('#locarnoSelected').show();
        ModalLocarnoBrowser.appendSelected();
        $('#view-terms').parent().addClass('hide');
        $('#hide-terms').parent().removeClass('hide');
        $('#btn-done').parent().addClass('hide');
        $('#btn-end').parent().removeClass('hide');
    },

    addSelectedProductIndicationModal: function() {
        var dataToBeSent = {
            'designIdentifier': ModalLocarnoBrowser.locarnoToAdd.id,
            'productIndications': ModalLocarnoBrowser.addedTerms
        };
        Locarno.ajax.addProductIndication(dataToBeSent, ModalLocarnoBrowser.resetId);
    },
    
    resetId: function(){
    	ModalLocarnoBrowser.locarnoToAdd = '';
    },

    postprocessLocarnoModal: function() {
        $('#locarnoModalBrowser select#locarnoClasses').val('-1');
        Locarno.ajax.loadLocarnoSubclassCodes('-1', '#locarnoModalBrowser select#locarnoSubclasses', function() {
            $('#locarnoModalBrowser select#locarnoSubclasses').val('-1');
            ModalLocarnoBrowser.updateLocarnoModalHeader();
            ModalLocarnoBrowser.currentStatus = 'browsingStart';
            ModalLocarnoBrowser.searchProductIndications(false);
        });
    },
    /**
     * Depending on the status show or hides parts of the header
     * @inner
     */
    updateLocarnoModalHeader: function() {
    	if (ModalLocarnoBrowser.currentStatus === 'browsingStart') {
            $('#locarnoWrapNavigation, #results-info, #locarnoSearchBack').hide();
            $('#locarnoSearch').show();
            $('#view-terms').parent().removeClass('hide');
            $('#hide-terms').parent().addClass('hide');
            $('#btn-end').parent().addClass('hide');
            $('#btn-done').parent().removeClass('hide');
        } else if (ModalLocarnoBrowser.currentStatus === 'searching') {
        	$('#locarnoSearch').show();
        	$('#results-info').toggle(ModalLocarnoBrowser.previousSearch.searchMode || ModalLocarnoBrowser.previousSearch.selectedSubclass !== -1);
        	$('#locarnoSearchBack #locarnoWrapNavigation, #gs-breadcrumb-container').hide();
        } else if (ModalLocarnoBrowser.currentStatus === 'basket') {
            $('#locarnoSearchBack').hide();
            $('#locarnoWrapNavigation, #locarnoSearch, #breadcrumb-container').hide();
        }
    },
    setLocarnoModalResults: function(data) {
        $('#wait-overlay, #preloader').hide();
        ModalLocarnoBrowser.updateLocarnoModalHeader();
        ModalLocarnoBrowser.jsonData = data;
        ModalLocarnoBrowser.appendLocarnoModalResults(data);
        ModalLocarnoBrowser.setSearchInfo(data);
        $('#input-search').focus();
    },
	searchProductIndicationsCall: function(searchParams) {
		var url = searchParams.searchMode ? ModalLocarnoBrowser.nav.searchProductIndications : ModalLocarnoBrowser.nav.getTaxonomy;		
        $.ajax({
            dataType: 'json',
            url:  url+ '?flowKey=' + $('input[name="execution"]').val(),
            type: 'GET',
            data: searchParams,
            beforeSend: function() {
                $('#wait-overlay, #preloader').show();
            },
            success: function(data){
                ModalLocarnoBrowser.searchPage = searchParams.page;
                ModalLocarnoBrowser.setLocarnoModalResults(data);
                //Auto-resize modal
                $($('.locarnoModal')).modal('layout');
            },
            error: function(data) {
                $('#wait-overlay, #preloader').hide();
                var res=data.responseText;
                if($(res).hasClass('flMessageError')){
					showMessageModal($(res).text());
                }else{
                	var text=$(res).find('.flMessageError').text(); 
                	showMessageModal(text);
                }
            }
        });
    },
    paginateSearch: function(e) {
        var a = $(e.target);
        if (a.parent().hasClass('disabled')) {
            e.preventDefault();
            return;
        }
        ModalLocarnoBrowser.searchPage = ModalLocarnoBrowser.previousSearch.page = $(e.target).data('pag');
        ModalLocarnoBrowser.searchProductIndicationsCall(ModalLocarnoBrowser.previousSearch);
        e.preventDefault();
    },
    sortSearch: function(e){
        var a = $(e.target);

        ModalLocarnoBrowser.sortBy = a.data("sort");
        ModalLocarnoBrowser.previousSearch.sortBy = a.data("sort");
        ModalLocarnoBrowser.searchProductIndicationsCall(ModalLocarnoBrowser.previousSearch);
        e.preventDefault();
    },
    showTranslation: function(e) {
        var row = $(e.currentTarget);
        var productIndicationIdentifier = row.attr('data-product-indication-identifier');
        var translationRow = row.closest('tr').next('.translationRow[data-product-indication-identifier="' + productIndicationIdentifier + '"]');
        if (translationRow.is(':visible')) {
            translationRow.hide();
            row.addClass('icon-chevron-down');
            row.removeClass('icon-chevron-up');
        } else {
            $.ajax({
                url: ModalLocarnoBrowser.nav.translateProductIndication+ '?flowKey=' + $('input[name="execution"]').val() + '&productIndicationIdentifier=' + productIndicationIdentifier,
                type: 'GET',
                beforeSend: function() {
                    $('#wait-overlay, #preloader').show();
                },
                success: function(htmlResponse) {
                	if($(htmlResponse).hasClass('flMessageError')){
                    	$('#wait-overlay, #preloader').hide();
						showMessageModal($(htmlResponse).text());
               		}else{
               			row.addClass('icon-chevron-up');
                    	row.removeClass('icon-chevron-down');
                   	 	translationRow.html(htmlResponse).show();
                    	$('#wait-overlay, #preloader').hide();
               		}
                },
                error: function() {
                    $('#wait-overlay, #preloader').hide();
                }
            });
        }
    }
};


_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@([\s\S]+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};

ModalLocarnoBrowser.templates = {
    resultsInfo: _.template(document.getElementById('locarnoResultsInfo').innerHTML),
    results: _.template(document.getElementById('locarnoResults').innerHTML),
    selected: _.template(document.getElementById('locarnoSelectedTable').innerHTML),
    taxonomy: _.template(document.getElementById('locarnoNavTaxonomy').innerHTML),
    pagination: _.template(document.getElementById('locarnoPagination').innerHTML)
};


$('#locarnoModalBrowser').on('click', '#navigateTaxonomy', function() {
	ModalLocarnoBrowser.currentStatus = 'searching';
	ModalLocarnoBrowser.searchProductIndications(false);
});

$('#locarnoModalBrowser').on('click', '.locarnoTaxonomy a.browse-icon', function() {
	ModalLocarnoBrowser.currentStatus = 'searching';
	ModalLocarnoBrowser.searchProductIndications(false, this);
});


$('#locarnoModalBrowser').on('click', 'button#search-btn', function() {
    if(ModalLocarnoBrowser.isSearchCriteriaEmpty()){
        showMessageModal($('#locarno_searchCriteriaEmpty').html());
    }
    else{
        ModalLocarnoBrowser.currentStatus = 'searching';
        ModalLocarnoBrowser.searchProductIndications(true);
    }
});

$(document).ready(function() {
	$(document).on('show.bs.modal','#locarnoModalBrowser', function() {
		var data = $('#locarnoModalBrowser').data('flow');
		if(!data || data===''){
			Locarno.ajax.loadLocarnoClassCodes('#locarnoModalBrowser select#locarnoClasses', ModalLocarnoBrowser.postprocessLocarnoModal);
		}
		$('#locarnoModalBrowser').data('flow','');
		data='';
        ModalLocarnoBrowser.enableDisableSearchButton();
	});
});

$(document).on('hide.bs.modal','#locarnoModalBrowser', function() {
     ModalLocarnoBrowser.currentStatus = 'browsingStart';
	 ModalLocarnoBrowser.resetModalLocarnoBrowser();
	 ModalLocarnoBrowser.updateLocarnoModalHeader();
	 ModalLocarnoBrowser.resetId();
});

$('#locarnoModalBrowser').on('click', ':checkbox', ModalLocarnoBrowser.evalAddingTerm);

$('#locarnoModalBrowser #cart-info').on('click', ' #view-terms', ModalLocarnoBrowser.viewSelected);
$('#locarnoModalBrowser #btn-done').on('click', ModalLocarnoBrowser.viewSelected);

$('#locarnoModalBrowser').on('click', '#hide-terms', ModalLocarnoBrowser.hideSelected);

$('#locarnoModalBrowser').on('change', 'select#locarnoClasses', function(e) {
    ModalLocarnoBrowser.enableDisableSearchButton();
    Locarno.ajax.loadLocarnoSubclassCodes(e.target.value, '#locarnoModalBrowser select#locarnoSubclasses');
});

$('#locarnoModalBrowser').on('change', '#input-search', function(e) {
    ModalLocarnoBrowser.enableDisableSearchButton();
});

$('#locarnoModalBrowser').on('click', 'a#btn-end', ModalLocarnoBrowser.addSelectedProductIndicationModal);

$('#locarnoModalBrowser').on('click', '.search-page', ModalLocarnoBrowser.paginateSearch);

$('#locarnoModalBrowser').on('click', '.sorting', ModalLocarnoBrowser.sortSearch);

$('#locarnoModalBrowser').on('click', '.translateProductIndication', ModalLocarnoBrowser.showTranslation);

$('#locarnoModalBrowser #input-search').keypress(function(e) {
    ModalLocarnoBrowser.enableDisableSearchButton();
    if(e.which === 13) {
        $(this).blur();
        $('#locarnoModalBrowser #search-btn').focus().click();
    }
});