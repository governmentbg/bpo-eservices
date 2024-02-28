<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="holderCurrentNumber" class="number"></span>

    <h3><spring:message code="holder.form.title"/></h3>
</header>

<sec:authorize access="hasRole('Holder_Import')" var="security_holder_import" />
<sec:authorize access="hasRole('Holder_Search')" var="security_holder_search" />

<%--
<c:set var="service_holder_import" value="${configurationServiceDelegator.isServiceEnabled('Holder_Import')}"/>
<c:set var="service_holder_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="security_service_holder_import"
       value="${(security_holder_import || !configurationServiceDelegator.securityEnabled) && service_holder_import}"/>
 --%>
<input type="hidden" id="holderConfig_searchEnabled"
       value="${security_service_holder_import}"/>
<c:if test="${security_service_holder_import}">
    <c:set var="service_holder_autocomplete_detailsUrlEnabled"
           value="${configurationServiceDelegator.getValueFromGeneralComponent('service.holder.details.urlEnabled')}"/>
    <input type="hidden" id="holderConfig_searchUrlEnabled"
           value="${service_holder_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_holder_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="holderConfig_searchUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.holder.details.url")}"/>
        <span class="hidden" id="holderConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
    </c:if>

    <c:set var="sectionId" value="holder" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import
                id="holderImportTextfield"
                component="eu.ohim.sp.core.person"
                importButtonId="holderImportButton"
                importButtonClass="btn"
                autocompleteUrl="autocompleteHolder.htm"
                autocompleteServiceEnabled="Holder_Autocomplete"
                permissionAutocomplete="${security_holder_search || !configurationServiceDelegator.securityEnabled}"
                minimumCharAutocomplete="service.holder.autocomplete.minchars"
                textCodeWhenEmpty="holder.importBox.whenEmpty"
                importButtonTextCode="holder.form.action.import"/>
        <span class="separator"><spring:message code="person.form.separator.or"/></span>
        <button id="holderCreateNew" class="create-new-button" type="button">
            <i class="create-icon"></i>
            <spring:message code="holder.form.action.createNew"/>
        </button>
        <div class="tip"><spring:message code="holder.form.import"/></div>
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