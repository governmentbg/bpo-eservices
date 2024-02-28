<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="gs-search">
    <div class="row-fluid">
        <div class="span8">
            <label>
                <input class="gs-search-source" type="checkbox" value="Nice" style="margin-right:5px"/>
                <spring:message code="gs.newModal.search.niceOnly"/>
            </label>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span8">
            <div class="form-inline input-append search-form">
                <input type="text" id="input-search" data-provide="typeahead" data-sphrase="<spring:message code="gs.modalSearch.selectYourGoods"/>"><button class="btn" id="search-btn"><spring:message code="gs.newModal.search"/></button>
                <a id="gsmodal-browse-classes" href="#" class="browse-classes"><spring:message code="gs.newModal.search.navigate"/></a>
            </div>
            <em class="note"><spring:message code="gs.newModal.search.remember"/>&nbsp<strong id="gsmodal-first-lang"></strong></em>
            <p id="search-volver"><a href="#" class="ico-arrow-left"><spring:message code="gs.newModal.search.goBackToSearchResults"/></a></p>
        </div>
        <div class="span3">
            <p id="results-info"></p>
        </div>
    </div>
</div>
<div id="gs-wrap-nav">
</div>
