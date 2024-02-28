<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="locarnoSearch">
    <div class="row-fluid">
        <div class="span12 modal-relative">
            <div class="form-inline input-append search-form">
                <select name="locarnoClasses" id="locarnoClasses" class="span2 lcoarnoSelect">
                    <option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllClasses"/></option>
                </select>
                <select name="locarnoSubclasses" id="locarnoSubclasses" class="span2 lcoarnoSelect">
                    <option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllSubclasses"/></option>
                </select>
                <input type="text" id="input-search" data-provide="typeahead"/><button class="btn" id="search-btn"><spring:message code="design.form.classes.locarno.modal.search"/></button>
                <a id="locarnoModalSearchHelp" href="<spring:message code="design.form.classes.locarno.modal.searchHelp.url"/>" target="_blank" class="help"><spring:message code="design.form.classes.locarno.modal.searchHelp"/></a>
                <span class="modal-separator"><spring:message code="person.form.separator.or"/></span>
               	<a id="navigateTaxonomy" href="javascript:void(0)" class="help"><spring:message code="design.form.classes.locarno.modal.navigateThroughPIs"/></a>
            </div>
            <em class="note"><spring:message code="design.form.classes.locarno.modal.remember"/>&nbsp<c:out value="${flowBean.firstLang}"/></em>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <p id="results-info"></p>
        </div>
    </div>
</div>
<div id="locarnoWrapNavigation">
</div>