<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="applicantCurrentNumber" class="number"></span>

    <h3><spring:message code="applicant.form.title"/></h3>
</header>

<sec:authorize access="hasRole('Applicant_Import')" var="security_applicant_import" />
<sec:authorize access="hasRole('Applicant_Search')" var="security_applicant_search" />

<c:set var="service_applicant_import" value="${configurationServiceDelegator.isServiceEnabled('Applicant_Import')}"/>
<c:set var="service_applicant_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="security_service_applicant_import"
       value="${(security_applicant_import || !configurationServiceDelegator.securityEnabled) && service_applicant_import}"/>

<input type="hidden" id="applicantConfig_searchEnabled"
       value="${security_service_applicant_import}"/>
<c:if test="${security_service_applicant_import}">
    <c:set var="service_applicant_autocomplete_detailsUrlEnabled"
           value="${configurationServiceDelegator.getValueFromGeneralComponent('service.applicant.details.urlEnabled')}"/>
    <input type="hidden" id="applicantConfig_searchUrlEnabled"
           value="${service_applicant_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_applicant_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="applicantConfig_searchUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.applicant.details.url")}"/>
        <span class="hidden" id="applicantConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
    </c:if>

    <c:set var="sectionId" value="applicant" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import
                id="applicantImportTextfield"
                component="eu.ohim.sp.core.person"
                importButtonId="applicantImportButton"
                importButtonClass="btn"
                autocompleteUrl="autocompleteApplicant.htm"
                autocompleteServiceEnabled="Applicant_Autocomplete"
                permissionAutocomplete="${security_applicant_search || !configurationServiceDelegator.securityEnabled}"
                minimumCharAutocomplete="service.applicant.autocomplete.minchars"
                textCodeWhenEmpty="applicant.importBox.whenEmpty"
                importButtonTextCode="applicant.form.action.import"/>
        <span class="separator"><spring:message code="person.form.separator.or"/></span>
        <button id="applicantCreateNew" class="create-new-button" type="button">
            <i class="create-icon"></i>
            <spring:message code="applicant.form.action.createNew"/>
        </button>
        <div class="tip"><spring:message code="applicant.form.import"/></div>
        <hr>
    </component:generic>


    <%-----%>
    <%--TODO: Search in ESearch button ?--%>
    <%-----%>
    <%--<c:if test="${(security_applicant_search || !configurationServiceDelegator.securityEnabled) && service_applicant_search}">--%>
        <%--<c:set var="applicantSearchUrl"--%>
               <%--value="${configurationServiceDelegator.getValueFromGeneralComponent('service.applicant.search.url')}"/>--%>
        <%--<a class="btn" href="${applicantSearchUrl}" target="_blank"><spring:message code="applicant.form.action.search"/></a>--%>
    <%--</c:if>--%>

    <%--<span class="separator"><spring:message code="person.form.separator.or"/></span>--%>
    <%--<button id="applicantCreateNew" class="create-new-button" type="button">--%>
        <%--<i class="create-icon"></i>--%>
        <%--<spring:message code="applicant.form.action.createNew"/>--%>
    <%--</button>--%>
</c:if>