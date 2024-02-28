<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="representativeCurrentNumber" class="number"></span>

    <h3><spring:message code="representative.form.title.${flowModeId}"/></h3>
</header>

<sec:authorize access="hasRole('Representative_Import')" var="security_representative_import" />
<sec:authorize access="hasRole('Representative_Search')" var="security_representative_search" />

<c:set var="service_representative_import" value="${configurationServiceDelegator.isServiceEnabled('Representative_Import')}"/>
<c:set var="service_representative_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="security_service_representative_import"
       value="${(security_representative_import || !configurationServiceDelegator.securityEnabled) && service_representative_import}"/>
<c:set var="service_representative_manual" value="${configurationServiceDelegator.isServiceEnabled('Representative_Manual')}"/>

<input type = "hidden" id="id_representative_manual" value="${service_representative_manual}" />

<input type="hidden" id="representativeConfig_searchEnabled"
       value="${security_service_representative_import}"/>
<c:if test="${security_service_representative_import}">
    <c:set var="service_representative_autocomplete_detailsUrlEnabled"
           value="${configurationServiceDelegator.getValueFromGeneralComponent('service.representative.details.urlEnabled')}"/>
    <input type="hidden" id="representativeConfig_searchUrlEnabled"
           value="${service_representative_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_representative_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="representativeConfig_searchUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.representative.details.url")}"/>
        <span class="hidden" id="representativeConfig_viewMessage"><spring:message
                code="person.autocomplete.action.viewDetails"/></span>
    </c:if>
    <c:set var="sectionId" value="representative" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import importButtonId="representativeImportButton"
                         id="representativeImportTextfield"
                         component="eu.ohim.sp.core.person"
                         autocompleteUrl="autocompleteRepresentative.htm"
                         autocompleteServiceEnabled="Representative_Autocomplete"
                         permissionAutocomplete="${security_representative_search || !configurationServiceDelegator.securityEnabled}"
                         minimumCharAutocomplete="service.representative.autocomplete.minchars"
                         importButtonClass="btn"
                         textCodeWhenEmpty="representative.importBox.whenEmpty"
                         importButtonTextCode="representative.form.action.import"/>
        <c:if test="${service_representative_manual}">
            <span class="separator"><spring:message code="person.form.separator.or"/></span>
            <button type="button" id="representativeCreateNew" class="create-new-button">
                <i class="create-icon"></i>
                <spring:message code="representative.form.action.createNew"/>
            </button>
        </c:if>
        <div class="tip"><spring:message code="representative.form.import"/></div>
        <hr>

    </component:generic>
</c:if>