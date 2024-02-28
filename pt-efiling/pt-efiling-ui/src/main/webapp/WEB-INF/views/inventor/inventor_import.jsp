<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="inventorCurrentNumber" class="number"></span>
    <h3><spring:message code="inventor.form.title.${flowModeId}"/></h3>
</header>

<sec:authorize access="hasRole('Inventor_Import')" var="security_inventor_import" />
<sec:authorize access="hasRole('Inventor_Search')" var="security_inventor_search" />

<c:set var="service_inventor_import" value="${configurationServiceDelegator.isServiceEnabled('Inventor_Import')}"/>
<c:set var="service_inventor_search" value="${configurationServiceDelegator.isServiceEnabled('Inventor_Search')}"/>

<c:set var="security_service_inventor_import" value="${(security_inventor_import || !configurationServiceDelegator.securityEnabled) && service_inventor_import}"/>

<input type="hidden" id="inventorConfig_searchEnabled" value="${security_service_inventor_import}"/>

<c:if test="${security_service_inventor_import}">

    <c:set var="service_inventor_autocomplete_detailsUrlEnabled" value="${configurationServiceDelegator.getValueFromGeneralComponent('service.inventor.details.urlEnabled')}"/>
    
    <input type="hidden" id="inventorConfig_searchUrlEnabled"  value="${service_inventor_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_inventor_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="inventorConfig_searchUrl" value="${configurationServiceDelegator.getValueFromGeneralComponent('service.inventor.details.url')}"/>
        <span class="hidden" id="inventorConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
    </c:if>

    <c:set var="sectionId" value="inventor" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import
                id="inventorImportTextfield"
                component="eu.ohim.sp.core.person"
                importButtonId="inventorImportButton"
                importButtonClass="btn"
                autocompleteUrl="autocompleteInventor.htm"
                autocompleteServiceEnabled="Inventor_Autocomplete"
                permissionAutocomplete="${security_inventor_search || !configurationServiceDelegator.securityEnabled}"
                minimumCharAutocomplete="service.inventor.autocomplete.minchars"
                textCodeWhenEmpty="inventor.importBox.whenEmpty"
                importButtonTextCode="inventor.form.action.import"/>
        <span class="separator"><spring:message code="person.form.separator.or"/></span>
        <button id="inventorCreateNew" class="create-new-button" type="button">
            <i class="create-icon"></i>
            <spring:message code="inventor.form.action.createNew"/>
        </button>
        <div class="tip"><spring:message code="inventor.form.import"/></div>
        <hr />
    </component:generic>
</c:if>