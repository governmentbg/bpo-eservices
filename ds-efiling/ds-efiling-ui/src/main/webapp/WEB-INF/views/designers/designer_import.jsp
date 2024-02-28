<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="designerCurrentNumber" class="number"></span>
    <h3><spring:message code="designer.form.title"/></h3>
</header>

<sec:authorize access="hasRole('Designer_Import')" var="security_designer_import" />
<sec:authorize access="hasRole('Designer_Search')" var="security_designer_search" />

<c:set var="service_designer_import" value="${configurationServiceDelegator.isServiceEnabled('Designer_Import')}"/>
<c:set var="service_designer_search" value="${configurationServiceDelegator.isServiceEnabled('Designer_Search')}"/>

<c:set var="security_service_designer_import" value="${(security_designer_import || !configurationServiceDelegator.securityEnabled) && service_designer_import}"/>

<input type="hidden" id="designerConfig_searchEnabled" value="${security_service_designer_import}"/>

<c:if test="${security_service_designer_import}">

    <c:set var="service_designer_autocomplete_detailsUrlEnabled" value="${configurationServiceDelegator.getValueFromGeneralComponent('service.designer.details.urlEnabled')}"/>
    
    <input type="hidden" id="designerConfig_searchUrlEnabled"  value="${service_designer_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_designer_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="designerConfig_searchUrl" value="${configurationServiceDelegator.getValueFromGeneralComponent('service.designer.details.url')}"/>
        <span class="hidden" id="designerConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
    </c:if>

    <c:set var="sectionId" value="designers" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import
                id="designerImportTextfield"
                component="eu.ohim.sp.core.person"
                importButtonId="designerImportButton"
                importButtonClass="btn"
                autocompleteUrl="autocompleteDesigner.htm"
                autocompleteServiceEnabled="Designer_Autocomplete"
                permissionAutocomplete="${security_designer_search || !configurationServiceDelegator.securityEnabled}"
                minimumCharAutocomplete="service.designer.autocomplete.minchars"
                textCodeWhenEmpty="designer.importBox.whenEmpty"
                importButtonTextCode="designer.form.action.import"/>
        <span class="separator"><spring:message code="person.form.separator.or"/></span>
        <button id="designerCreateNew" class="create-new-button" type="button">
            <i class="create-icon"></i>
            <spring:message code="designer.form.action.createNew"/>
        </button>
        <div class="tip"><spring:message code="designer.form.import"/></div>
        <hr />
    </component:generic>
</c:if>