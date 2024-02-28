/*
Underscore templates. Some underscore methods used:
- http://underscorejs.org/
    sortBy -> text, niceClass
    orderBy -> ASC,DESC
    1º) set lang cuando el usuario clicka, y no cargar el json de las clases hasta entonces
*/
$(function() {
    $('#btn-end').on('click', GS.resetGSContinue);
});

(function($, G) {
    //add functions here to expose them and call them from outside
     G.GS = G.GS || {resetGS: resetGS, resetGSContinue: resetGSContinue, removeExternal: removeExternal, initGs: initGs};
    _.templateSettings = {
        interpolate: /\<\@\=(.+?)\@\>/gim,
        evaluate: /\<\@([\s\S]+?)\@\>/gim,
        escape: /\<\@\-(.+?)\@\>/gim
    };

    var urls = {
        'classScope' : 'getClassScopes.htm',
        'search' : 'searchTerm.htm',
        'results' : 'searchTerm.htm',
        'autocomplete' : 'autocomplete.htm',
        'selectedClassCount' : 'selectedClassNumbers.htm'
    }

    var templates = {
        nav : _.template(document.getElementById('gsNav').innerHTML),
        results : _.template(document.getElementById('gsResults').innerHTML),
        classes : _.template(document.getElementById('gsClasses').innerHTML),
        selected : _.template(document.getElementById('gsSelected').innerHTML),
        info: _.template(document.getElementById('gsSearchInfo').innerHTML),
        browse : _.template(document.getElementById('gsTerms').innerHTML),
        breadcrumb : _.template(document.getElementById('gsBreadcrumb').innerHTML)
    }

    var tableWrap = $('#gs-results-table>div'),
        breadcrumb  = $('#breadcrumb-container'),
        footerOptions = $('#footer-options'),
        selectedWrap = $('#gs-selected'),
        paginationAmount = 25,
        addedTerms = [],
        selectedClassNumbers = [];

    var searchTerm,
        jsonData,
        previousStatus,
        currentStatus,
        pro,
        classesData,
        termsData,
        searching,
        autoCompleteReq,
        timerAutocomplete,
        autoCompleteTimer,
        searchPage,
        browsePage,
        sortBy,
        orderBy,
        activeClass,
        searchLanguage,
        addTermsMethod,//Used to store the data that comes from generate terms
        sources;

    var ajaxGSSearch;

//CLASS SCOPE -- Load at the beginning, append classes nav.

    function resetGS(){
        currentStatus = 'browsingStart';
        appendClassesTable();
        updateHeader();
    }

      function resetGSContinue(){
        currentStatus = 'browsingStart';
        appendClassesTable();
        $('#input-search').val('');
        $(".gs-search-source").removeAttr("checked");
        $('#cart-info').addClass('disabled');
        $('#terms-amount, #classes-amount').text('0');

        $('#btn-end').parent().addClass('hide');
        $('#btn-done').parent().removeClass('hide');
        $('#view-terms').parent().removeClass('hide');
        $('#hide-terms').parent().addClass('hide');
        addedTerms = [];
        updateHeader();
    }

    function loadClasses(callback, callbackOnloadClasses){
        var langId = searchLanguage || getFirstLangValue();
        $.ajax({
            dataType: "json",
            url: urls.classScope ,
            data: {'langId': langId, flowKey: $('#executionKey').val() },
            beforeSend:function(){
                $('.gsLoaderImg').show();
                showLoadingScreen();
            },
            success: setClassesData,
            complete:function() {
                $('.gsLoaderImg').hide();
                $('#wait-overlay, #preloader').hide();
                hideLoadingScreen();
            }
        });
        function setClassesData (data) {
            //Error: show modal error and clean class scopes
            if (data.value != null) {
                hideLoadingScreen();
                showWarningModal($("#" + data.value).html());
                classesData = [];
            } else {
                classesData = data;
            }

            if(currentStatus === 'browsingStart') appendClassesTable();
            appendNavClasses();
            if(!_.isUndefined(callbackOnloadClasses) && _.isFunction(callbackOnloadClasses)){
                callbackOnloadClasses();
            }
            
        }
        if(!_.isUndefined(callback) && _.isFunction(callback)){
            callback();
        }
    }

    //F- appends navigation at the top
    function appendNavClasses(){
        var goodsList = classesData.slice(0,33);
        var servicesList = classesData.slice(34,classesData.length);
        $('#gs-wrap-nav').html(templates.nav({
            goods:  classesData
            //services:  servicesList
        }));

        $('#nav-gs p a').on('click', collapseNav);
        $('#nav-gs li a').popover({
            placement: 'top',
            template:'<div class="popover popover-large bottom"><div class="popover-inner"><div class="arrow"></div><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>',
            trigger: 'hover'
        }).on("hover", function(){
            $('.popover').addClass('large-popover');
        });
        $('#nav-gs li a').on('click', loadTerm);

        function collapseNav(e){
            var link = $(e.target);
            var container = link.closest('div');
            var active = $('#nav-gs .active');
            $('#nav-gs').addClass('showing');
            $('#nav-gs .active').removeClass('active');
            container.toggleClass('active');
        }
    }


//SEARCH -- Load and display results.
    function searchResults(){
        searchTerm = $('#input-search').val();
        sources = '';
        var sourcesList = $("#gs-search .gs-search-source:checked");
        sourcesList.each(function(i, el) {
            sources += ($(el).val());
            if(i < sourcesList.length-1){
                sources+=',';
            }
        });
        searchPage = 1;
        loadResults();
        if(searchTerm || sources.length >0){
            $('#modal-gs').addClass('full-modal');
            $('#modal-gs').removeClass('start-modal');
        } else {
            showMessageModal(getMessage("general.messages.goodsServices.writeTerm"));
        }
    }
    function submitTerms(e){
        var flow = $('#modal-gs').data('flow');
        if(/popover/i.test(flow)) {
            window.TERMEDIT.addSearchToPopOverSuggested(addedTerms, flow);
        } else if(/assistant/i.test(flow)){
            ClassAssistantModal.setSearchTermData(addedTerms);
        } else if(addTermsMethod !== null){
            addTermsMethod(addedTerms);
        }else if(flow && flow.cat){
            replaceTerm(flow.cat,flow.name,addedTerms);
        }else{
            submitAddedTerms(addedTerms);
        }
    }

    function paginatedSearch(e){
        var a = $(e.target);
        if(a.parent().hasClass('disabled')){
            e.preventDefault();
            return;
        }
        searchPage = $(e.target).attr('data-pag');
        loadResults(e);
        e.preventDefault();
    }

    function paginatedBrowse(e){
        var a = $(e.target);
        if(a.parent().hasClass('disabled')){
            e.preventDefault();
            return;
        }
        browsePage = $(e.target).attr('data-pag');
        loadTerm(e);
        e.preventDefault();
    }

    function loadResults(e){
        var langId = searchLanguage || getFirstLangValue();
        $('.typeahead').hide();
        if(autoCompleteReq) autoCompleteReq.abort();
        clearTimeout(timerAutocomplete);
        $('#wait-overlay, #preloader').show();
        var searchParams = {
            term: searchTerm,
            pageSize: paginationAmount,
            page: searchPage,
            orderBy: null,
            sortBy: null,
            langId: langId,
            flowKey: $('#executionKey').val(),
            sources: sources
        }
        if (orderBy) searchParams.orderBy = orderBy;
        if (sortBy) searchParams.sortBy = sortBy;
        //

        if(e){
        	if ($(e.target).hasClass('sorting')) {
	            searchParams.sortBy = $(e.target).attr('data-sort');
	            var previousSortBy = sortBy;
	            sortBy = searchParams.sortBy;
	            if(orderBy == undefined || orderBy == 'DESC' || $(e.target).attr('data-sort') != previousSortBy) {
	            searchParams.orderBy = 'ASC';
	                orderBy = 'ASC';
	            }
	            else if( orderBy == 'ASC') {
	                searchParams.orderBy = 'DESC';
	                orderBy = 'DESC';
	            }
        	}
        } else {
            delete searchParams.sortBy;
            delete searchParams.orderBy;
            sortBy = '';
            orderBy = '';
        }

        if(ajaxGSSearch && ajaxGSSearch.readyState != 4){
            ajaxGSSearch.abort();
        }

        ajaxGSSearch = $.ajax({
            dataType: "json",
            url: urls.search,
            data: searchParams,
            success: setResults,
            error: function(jqXHR, status, error){
                $('#wait-overlay, #preloader').hide();
                alert("Error, please try again later. "+error);
            },
            timeout: 60000,
        });

        function setResults(data){
            $('#wait-overlay, #preloader').hide();
            currentStatus = 'searching';
            updateHeader();
            jsonData = data;
            appendResults();
            setSearchInfo();
        }
    }


    function setSearchInfo(){

        $("#results-info").html(templates.info({
            termsAmount: jsonData.totalResults,
            searchTerm: searchTerm,
            sources: sources
        }));
        $("#results-info").show();
    }

    function appendResults(){
        $('#gs-results-table').show();
        $('#results-info').html('');
        var total = jsonData.totalResults;
        var pageSize = jsonData.pageSize;
        var amountPages = Math.ceil(total/pageSize);
        var topLimit = Number(searchPage) + 2;
        var bottomLimit = Number(searchPage) - 4;

        tableWrap.html(templates.results({
            data: jsonData.results,
            total: total,
            currentPage: parseInt(searchPage),
            topLimit: topLimit,
            bottomLimit: bottomLimit,
            pageSize: pageSize,
            orderBy: orderBy,
            sortBy: sortBy,
            term: searchTerm,
            alreadySelected: addedTerms,
            amountPages: amountPages,
            pro: pro
        }));
        setBrowseEvents();
        $('#gs-results-table>div p').each(function(event) {
            var txt = $(this).html();
            var appendTxt = highlightText(txt, searchTerm);
            $(this).html(appendTxt);
        });
    }

    function preg_quote(str) {
        return (str+'').replace(/([\\\.\+\*\?\[\^\]\$\(\)\{\}\=\!\\<\>\|\:])/g, "\\$1");
    }

    function highlightText(data, search) {
        return data.replace( new RegExp( "(" + preg_quote( search ) + ")" , 'gi' ), "<strong>$1</strong>" );
    }



    function setBrowseEvents(){
        $('.gs-select .disabled').popover({
            placement: 'top',
            trigger: 'hover'
        });
    }

    function loadTerm(e){
        var item = $(e.currentTarget);
        var itemId = item.attr('data-id');
        var pageAttr = item.attr('data-pag');
        var dataClass = item.attr('data-class');
        var langId = searchLanguage || getFirstLangValue();
        if(!browsePage || !pageAttr) browsePage = 1;
        if(searchTerm || (sources && sources.length > 0)) {
            currentStatus = 'searchingWithin';
        } else {
            currentStatus = 'browsingWithin';
        }
        updateHeader();
        $('#wait-overlay, #preloader').show();
      $.ajax({
            dataType: "json",
            url: urls.results,
            data: {taxoConceptNodeId: itemId, langId: langId, page: browsePage, pageSize: paginationAmount, niceClassList: dataClass, flowKey: $('#executionKey').val()},
            success: setResults,
            error: function(jqXHR, status, error){
                $('#wait-overlay, #preloader').hide();
                alert("Error, please try again later. "+error);
            },
            timeout: 60000,
        });
        function setResults(data){
            jsonData = data;
            appendTerm(itemId);
            appendBreadCrumb();
        }

    }
    function loadTermData(options, callback){
        var itemId = options.identifier;
        var langId = searchLanguage || getFirstLangValue();
        var browsePage = options.pag;
        var niceClass = options.niceClass;
        if(!browsePage || !pageAttr) browsePage = 1;
        currentStatus = 'browsingWithin';
        updateHeader();
        $('#wait-overlay, #preloader').show();
         $.ajax({
            dataType: "json",
            url: urls.results,
            data: {taxoConceptNodeId: itemId, langId: langId, page: browsePage, pageSize: paginationAmount, niceClassList: niceClass, flowKey: $('#executionKey').val()},
            success: function(data){
                jsonData = data;
                appendTerm(itemId);
                appendBreadCrumb();
            }
        });
         if(!_.isUndefined(callback) && _.isFunction(callback)) {
            callback();
         }
    }

    function appendTerm(itemId){
        var total = jsonData.totalResults;
        var pageSize = jsonData.pageSize;
        var amountPages = Math.ceil(total/pageSize);
        var topLimit = Number(browsePage) + 2;
        var bottomLimit = Number(browsePage) - 4;
        $('#wait-overlay, #preloader').hide();
        //sort the array to show first the browsable results
        var withIdSorted = _.reject(_.sortBy(jsonData.results, 'term'), function(item){ return item.id === null});
        var withOutIdSorted = _.reject(_.sortBy(jsonData.results, 'term'), function(item){ return item.id});
        jsonData.results = withIdSorted.concat(withOutIdSorted);
        tableWrap.html(templates.browse({
            data: jsonData.results,
            total: total,
            currentId: itemId,
            currentPage: parseInt(browsePage),
            topLimit: topLimit,
            bottomLimit: bottomLimit,
            pageSize: pageSize,
            amountPages: amountPages,
            dataClass: jsonData.results[0].cat,
            alreadySelected: addedTerms
        }));
        setBrowseEvents();
    }

    function controlTopNav (){
        $('#nav-gs li a').removeClass('selected');
        $('#nav-gs li:eq(' + (activeClass -1) + ') a').addClass('selected');

        $('#nav-gs>div').removeClass('active');
        $('#nav-gs>div>p>a').removeClass('active');

        if(activeClass <= 34) {
            $('#nav-gs>div:eq(0)').addClass('active');
            $('#nav-gs>div:eq(0)>p>a').addClass('active');
        } else{
            $('#nav-gs>div:eq(1)').addClass('active');
            $('#nav-gs>div:eq(1)>p>a').addClass('active');
        }
    }

    function appendBreadCrumb(){
        activeClass = jsonData.results[0].cat;
        var definition = classesData[activeClass -1].term;
        checkBreadCrumb();
        controlTopNav();
        breadcrumb.html(templates.breadcrumb({
            classDescription: definition,
            term: jsonData.term,
            dataClass: jsonData.results[0].cat,
            parents: jsonData.parents
        }));
        breadcrumb.show();
        checkBreadCrumb();
        $('#gs-breadcrumb').on('click', '.browse-term', loadTerm);
        $('#breadcrumb-container').off('click', '.read-more');
        $('#breadcrumb-container').on('click', '.read-more', toggleView);

        function toggleView(e){
            var link = $(e.target);
            var tr = link.closest('div');
            link.toggleClass('active');
            tr.find('.full-term').slideToggle();
            tr.find('.short-term').slideToggle();
            e.preventDefault();
        }
    }
    function checkBreadCrumb(){
        var constrainWidth = 6;
        var breadCrumbContainer = $('#breadcrumb-container');
        var breadCrumbSize = getBreadCrumbSize(breadCrumbContainer);
        var termListWidth = breadCrumbSize.breadCrumbWidth - (breadCrumbSize.classListWidth - breadCrumbSize.classListTermWith);
        if(breadCrumbSize.classListWidth > breadCrumbSize.breadCrumbWidth){
            var itemWidth = Math.round(termListWidth / breadCrumbSize.totalTerms);
            breadCrumbContainer.find('li').addClass('ellipsisBreadCrumb').css('max-width', itemWidth - constrainWidth + 'px');
            initBreadCrumbPopOver(breadCrumbContainer);
        }
    }
    function initBreadCrumbPopOver(breadCrumbContainer) {

        var termList = breadCrumbContainer.find('li a:not(:first)');
        termList.each(function(index, element){
            $(element).attr('data-content', $(element).text());
        });
        termList.popover({
            placement: 'top',
            template:'<div class="popover popover-large bottom"><div class="popover-inner"><div class="arrow"></div><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>',
            trigger: 'hover'
        }).on("hover", function(){
            $('.popover').addClass('large-popover');
        });
    }

    function getBreadCrumbSize(breadCrumbContainer){
        var breadCrumbWidth = breadCrumbContainer.width();
        var classList =  breadCrumbContainer.find('#gs-breadcrumb li');
        var classListWidth = 0;
        classList.each(function(index, element){
             classListWidth = classListWidth + $(element).width();
        });
        return {
            classListWidth:classListWidth,
            classListTermWith: classListWidth - $(classList[0]).width(),
            breadCrumbWidth:breadCrumbWidth,
            totalTerms: classList.length - 1
        }
    }

    function appendClassesTable(){
        $('#gs-results-table').show();
        $('#gs-selected').hide();
        $('#modal-gs').addClass('full-modal');
        if(classesData){
            tableWrap.html(templates.classes({
                goods:  classesData
            }));
        }
        setBrowseEvents();
        $('.gs-result').on('click', '.read-more', toggleView);

        function toggleView(e){
            var link = $(e.target);
            var tr = link.closest('tr');
            link.toggleClass('active');
            tr.find('.full-term').slideToggle();
            tr.find('.short-term').slideToggle();
            e.preventDefault();
        }
    }

    function appendSelected(){
        selectedWrap.html(templates.selected({
            data: addedTerms
        }));
        $(selectedWrap, ':checkbox').off('change');
        $(selectedWrap, ':checkbox').on('change', evalAdded);

        function evalAdded(e){
            var checkbox = $(e.target),
                tr = checkbox.closest('tr'),
                checked = checkbox.attr('checked'),
                pos = checkbox.attr('data-term');
            if (!checked) {
                removeTerm(checkbox);
                tr.remove();
                var input =  $("#gs-results-table input[data-term='" + pos +"']");
                input.closest('tr').removeClass('active');
                input.attr('checked',false);
            }
            updateTermsView();
            if(addedTerms.length === 0) hideSelected();
        }
    }


// MISC FUNCTIONS

    function evalAddingTerm(e){
        var alreadyAdded = getTotalClassesAddedAmount();
        var addedClassesAmount = alreadyAdded.length;

        var checkbox = $(e.target),
            tr = checkbox.closest('tr'),
            trIndex = tr.index();
            checked = checkbox.attr('checked');

            e.stopPropagation();

        var position = checkbox.attr('data-rel');
        var classBeingAdded = jsonData.results[position].cat;
        var isClassAdded = _.indexOf(alreadyAdded, classBeingAdded);
        if(addedClassesAmount === feeClassesIncluded && isClassAdded < 0 && checked) {
            showClassDisclaimer(checkbox);
            return;
        }
        if (checked) {
            tr.addClass('active');
            animateSelected(tr);
            addTerm(checkbox);
        }
        else {
            tr.removeClass('active');
            removeTerm(checkbox);
        }
        updateTermsView();
    }


    function showClassDisclaimer(checkbox){
            $checkbox = $(checkbox);
            $tr = $checkbox.closest('tr');

            $checkbox.attr('checked', false);
            $tr.removeClass('active');

            var span = $(checkbox).parent();

            span.on('click', function(e){
                e.stopPropagation();
            });

            $('body').off('click', '.addItem');
            span.popover({
                placement: 'top',
                trigger: 'manual',
                template:'<div class="popover popover-add bottom"><div class="popover-inner"><div class="arrow"></div><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>',
                content: _.template(document.getElementById('warningFeeExtraClass').innerHTML + document.getElementById('gsAddClass').innerHTML)
            }).on('click', function(e) {
                var l = $(e.target);
                span.not(l).trigger('click');
                l.tooltip('hide').popover('toggle').toggleClass('active');
                return e.preventDefault();
            }).popover('show');

            $('body').on('click', '.cancelAdd', function(e) {
                $('.popover:visible').remove();
                span.off('click');
                return e.preventDefault();
            });
            $('body').on('click', '.addItem', function(e) {
                $checkbox.attr('checked', true);
                $tr.addClass('active');
                animateSelected($tr);
                addTerm($checkbox);
                updateTermsView();
                span.off('click');
                return e.preventDefault();
            });
            //This should be run just once, to don't mess with other popoovers such as the translation one.
            $('body').one('click', function(e) {
                span.off('click');
                $('.popover-add:visible').remove();
            });
            return;
        }

    function updateTermsView(){
        var termsAmount = addedTerms.length;
        if(termsAmount > 0) {
            $('#cart-info, #btn-done').removeClass('disabled');
        }
        else {
            $('#cart-info, #btn-done').addClass('disabled');
        }

        $('#terms-amount').html(termsAmount);
        var added = getClassesAddedAmount();
        var addedClassesAmount = added.length;
        $('#classes-amount').html(addedClassesAmount);
    }

    function getClassesAddedAmount(){
        var termsAmount = addedTerms.length;
        var classesList = [];
        for(var i=0; i < termsAmount; i++){
            classesList.push(addedTerms[i].cat);
        }

        var unifiedClasses = _.uniq(classesList);
        return unifiedClasses;
    }

    function getTotalClassesAddedAmount(){
        var termsAmount = addedTerms.length;
        var classesList = [];
        for(var i=0; i < termsAmount; i++){
            classesList.push(addedTerms[i].cat);
        }

        for(var i=0; i < selectedClassNumbers.length; i++) {
            classesList.push(selectedClassNumbers[i]);
        }

        var unifiedClasses = _.uniq(classesList);
        return unifiedClasses;
    }

    function getIfInArray() {
        var termsAmount = addedTerms.length;
        var classesList = [];
        for(var i=0; i < termsAmount; i++){
            classesList.push(addedTerms[i].cat);
        }
        var unifiedClasses = _.uniq(classesList);
        return isInarray;
    }

    function viewSelected(e) {
        var wrapNav = $(e.target).closest('div');
        if (wrapNav.hasClass('disabled') || $(e.target).hasClass('disabled')) return;
        previousStatus = currentStatus;
        currentStatus = 'basket';
        updateHeader();
        $('#gs-results-table').hide();
        $('#gs-selected').show();
        appendSelected();
        $('#view-terms').parent().addClass('hide');
        $('#hide-terms').parent().removeClass('hide');
        $('#btn-done').parent().addClass('hide');
        $('#btn-end').parent().removeClass('hide');
    }

    function hideSelected(e) {
        $('#gs-results-table>div').show();
        $('#gs-selected').hide();
        currentStatus = previousStatus;
        updateHeader();
        $("#gs-results-table").show();
        $('#results-info, #search-volver').hide();
        $('#view-terms').parent().removeClass('hide');
        $('#hide-terms').parent().addClass('hide');
        $('#btn-end').parent().addClass('hide');
        $('#btn-done').parent().removeClass('hide');
    }

    function removeExternal(term) {
        addedTerms = _.reject(addedTerms, function(item){ return item.term === term})
        // Escape possible single quotes in the term in order to avoid the following jquery selectors to be malformed
        term = term.replace(/'/g, "\\'");
        var input =  $("#gs-results-table input[data-term='" + term +"']");
        input.closest('tr').removeClass('active');
        input.attr('checked',false);
        $("#gs-selected input[data-term='" + term +"']").closest('tr').remove();
        updateTermsView();
    }


    function removeTerm(checkbox){
        var stringTerm = checkbox.attr('data-term');
        addedTerms = _.reject(addedTerms, function(item){ return item.term === stringTerm})
    }

    function addTerm (checkbox){
        //reference of the position in the array
        var position = checkbox.attr('data-rel');
        addedTerms.push(jsonData.results[position]);
        _.extend(addedTerms[addedTerms.length -1],{pos: position});
    }

    function animateSelected(tr){
        var scrollTop = $(window).scrollTop();
        var scrollLeft = $(window).scrollLeft();

        //duplicate tr and insert in a table with the same look and feel.
        $('#wrap-generated').remove();
        var replicatedTpl = _.template(document.getElementById('gsReplicated').innerHTML);
        var item = $('#modal-gs .modal-body').append(replicatedTpl({
            data: tr.html()
        }));

        //position of tr
        var offset = tr.offset();
        $('#wrap-generated').css({
            'top' : offset.top - scrollTop,
            'left' : offset.left - scrollLeft,
            'width' : tr.width(),
            'height' : tr.height()
        });

        //move to the same position and size as the cart box
        var $cartInfo = $('#cart-info');
        var cartOffset = $cartInfo.offset();
        $('#wrap-generated').animate({
            'top' : cartOffset.top - scrollTop,
            'left' : cartOffset.left - scrollLeft,
            'width' : $cartInfo.width(),
            'height' : $cartInfo.height(),
            'opacity' : 0
        },700, function(){
            $(this).remove();
        });
    }

    function updateHeader(){
        breadcrumb.hide();
        switch (currentStatus) {
            case 'browsingStart':
                $('#gs-wrap-nav, #results-info, #gs-search, #search-volver').hide();
                $('#gs-search, #gs-title').show();
            break;
            case 'browsingWithin':
                breadcrumb.show();
                $('#gs-wrap-nav, #gs-search, #gs-title, #gs-results-table').show();
                $('#results-info, #search-volver').hide();
            break;
            case 'searching':
                $('#gs-search').show();
                $('#search-volver').hide();
                $('#gs-wrap-nav, #gs-breadcrumb-container, #gs-title').hide();
            break;
            case 'searchingWithin':
                breadcrumb.show();
                $('#search-volver').show();
                $('#gs-wrap-nav, #gs-search, #gs-title').show();
            break;
            case 'basket':
                $('#search-volver').hide();
                $('#gs-wrap-nav, #gs-search, #breadcrumb-container').hide();
            break;
        }
    }

    function updateSelectedClassNumbers() {
        $.ajax({
            url: "selectedClassNumbers.htm",
            type: "GET",
            async: false
        })
        .success(function(data) {
            selectedClassNumbers = data;
        });

    }

    function launchBrowsing(e){
        currentStatus = 'browsingStart';
        searchTerm = '';
        sources = '';
        $('#input-search').val('');
        $(".gs-search-source").removeAttr("checked");
        updateHeader();
        appendClassesTable();
        e.preventDefault();
    }

    function setUpAutocomplete(){
        var _self = this;
        var skeys = [40, 38, 9, 13, 27];
        var req = null;
        var autoCompleteField = $('input#input-search');
        var selectGoodsMsg = autoCompleteField.data('sphrase');
        var isWizardForm = $('body').is('#wizard');
        autoCompleteField.on('keyup', function(e) {
            if($.inArray(e.keyCode, skeys)==-1) {
                e.stopImmediatePropagation();
                var input = $(this);
                clearTimeout(timerAutocomplete);                
                if(input.val().length < 2) return;
                timerAutocomplete = setTimeout(
                    function(){
                    requestSuggested();
                }, 500);
                function requestSuggested(){
                    var url = urls.autocomplete + '?langId=' + getFirstLangValue() + '&term=' + encodeURIComponent(input.val());
                    autoCompleteReq = $.get(url,
                        function(data) {
                            var listOfterms = data;
                            if(!_.isArray(data) && !_.isUndefined(data.terms) ) {
                                listOfterms = data.terms;
                            }
                            if (input.data('typeahead')){
                                input.data('typeahead').source = listOfterms;
                                input.data('typeahead').matcher = function(item) {
                                    return true
                                };
                                input.data('typeahead').render = function(items){
                                    var that = this;
                                    var isStandarPhrase = !_.isUndefined(data.standardPhrase) && !_.isEmpty(data.standardPhrase);
                                  items = $(items).map(function (i, item) {
                                    i = $(that.options.item).attr('data-value', item)
                                    i.find('a').html(that.highlighter(item))
                                    return i[0]
                                  });
                                  this.$menu.html(items);
                                  this.show();
                                  if(isStandarPhrase && isWizardForm) {
                                    var standardPhraseItem = '<li class="goodsSelector"><a href="#"><span class="standardPhrase"><strong>'+ data.standardPhrase.capitalize() +' > </strong><i>'+selectGoodsMsg+'</i></span></a></li>';
                                    this.$menu.prepend(standardPhraseItem);
                                  }
                                  return this
                                };
                                input.data('typeahead').select = function(items){
                                    var activeItem = this.$menu.find('.active');
                                    var val = activeItem.attr('data-value');
                                    if(activeItem.hasClass("goodsSelector")) {
                                        showClassAssistant();
                                    } else {
                                      this.$element
                                        .val(this.updater(val))
                                        .change()
                                    }
                                    return this.hide();
                                }
                            };
                            input.data('typeahead').render(listOfterms);
                            input.data('typeahead').keyup(e);
                        }
                    );
                }
            }
        });
    }

    // INIT FUNCTIONS & EVENTS
    function initGs(options, callbackOnloadClasses){
        if (options && typeof(options.addTermsMethod) === 'function') {
            addTermsMethod = options.addTermsMethod;
        } else {
            addTermsMethod = null;
        }
        resetGSContinue();

        setUpAutocomplete();
		
        currentStatus = 'browsingStart';

        loadClasses(null, callbackOnloadClasses);

        updateSelectedClassNumbers();

    }
    function showClassAssistant() {
        ClassAssistantModal.setupClassAssistantModal();
    }

    function searchTermAction(options){
        $('#modal-gs').find('#input-search').val(options.term);
        $('#modal-gs').find('button#search-btn').click();
    }

    function beforeCloseGSModalListener(e){
        if ($("#gs-table .active, #gs-terms .active").length!=0) {
            $('#confirm-close-modal-gands').modal('show');
            e.preventDefault();
        } else {
            afterCloseGSModalListener(e);
        }
    }

    function closeGSModalListener(e) {
    	$("#gs-table .active, #gs-terms .active").each(function() {
            $(this).removeClass("active");
        });
        $('#modal-gs').modal('hide');

        afterCloseGSModalListener(e);
    }

    function afterCloseGSModalListener(){
        searchLanguage = null;
        var flowType = $(this).data('flow');
        if(/assistant/i.test(flowType)) {
            $('.classAssistantModal').show();
        }
        $(this).data('flow', null);
    }

    function setGSSearchLanguage(lang) {
        searchLanguage = lang || getFirstLangValue();
    }

    events : {
        $('.choose-terms-button').on('click', initGs);
        $('#search-btn').on('click', searchResults); // clear the sort and order parameters, to start a new search
        $('#btn-end').on('click', submitTerms);
        $('#gs-results-table').on('click','.browse-term', loadTerm);
        $('#gs-results-table').on('click','.sorting', loadResults); // establish the sort and order parameters
        $('#gs-results-table').on('click','.search-page', paginatedSearch); // nothing (keep the current sort and order parameters)
        $('#gs-results-table').on('click','.browse-page', paginatedBrowse);
        tableWrap.on('click',':checkbox', evalAddingTerm);
        $('#cart-info').on('click', ' #view-terms', viewSelected);
        $('#btn-done').on('click', viewSelected);
        $('#hide-terms').on('click', hideSelected);
        $('#input-search').keypress(function(e) {
            if(e.which === 13) {
                $(this).blur();
                $('#search-btn').focus().click();
            }
        });
        $('#modal-gs').on('click', '.browse-classes', launchBrowsing);
        var observer = new MutationObserver(function(){
            if(targetNode.style.display == 'none' ){
                if(ajaxGSSearch && ajaxGSSearch.readyState != 4){
                    ajaxGSSearch.abort();
                }
            }
        });
        var targetNode = document.getElementById('modal-gs');
        observer.observe(targetNode,  { attributes: true });
        $('#search-volver').on('click', 'a', function(e){
            var term = $(this).attr('data-term');
            currentStatus = 'browsingStart';
            searchResults(term);
            e.preventDefault();
        });
        $(document).on('updateTerms', function(e, options){
            setGSSearchLanguage(options.langId);
            loadClasses(function(){
                loadTermData(options, options.callback);
            });
        });
        $(document).on('searchTerm', function(e, options){
            setGSSearchLanguage(options.langId);
            loadClasses(function(){
                searchTermAction(options);
            });
        });
        $('#modal-gs').on('hide.bs.modal', beforeCloseGSModalListener);
        $('#confirm-close-modal-gands .confirm-action').on('click', closeGSModalListener);
    }

})(jQuery, window);
