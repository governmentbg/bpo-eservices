<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<header>
    <span id="assigneeCurrentNumber" class="number"></span>

    <h3><spring:message code="assignee.form.title.${flowModeId}"/></h3>
</header>

    <sec:authorize access="hasRole('Assignee_Import')" var="security_assignee_import" />
    <sec:authorize access="hasRole('Assignee_Search')" var="security_assignee_search" />    

<c:set var="service_assignee_import" value="${configurationServiceDelegator.isServiceEnabled('Assignee_Import')}"/>
<c:set var="service_assignee_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="security_service_assignee_import"
       value="${(security_assignee_import || !configurationServiceDelegator.securityEnabled) && service_assignee_import}"/>
<input type="hidden" id="assigneeConfig_searchEnabled"
       value="${security_service_assignee_import}"/>
<c:if test="${security_service_assignee_import}">
    <c:set var="service_assignee_autocomplete_detailsUrlEnabled"
           value="${configurationServiceDelegator.getValueFromGeneralComponent('service.assignee.details.urlEnabled')}"/>
    <input type="hidden" id="assigneeConfig_searchUrlEnabled"
           value="${service_assignee_autocomplete_detailsUrlEnabled}"/>

    <c:if test="${service_assignee_autocomplete_detailsUrlEnabled}">
        <input type="hidden" id="assigneeConfig_searchUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.assignee.details.url")}"/>
        <span class="hidden" id="assigneeConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
    </c:if>

    <c:set var="sectionId" value="${functions:getPersonDataSetionId(flowModeId)}" scope="request"/>

    <component:generic path="personIdNumber" checkRender="true">
        <component:import
                id="assigneeImportTextfield"
                component="eu.ohim.sp.core.person"
                importButtonId="assigneeImportButton"
                importButtonClass="btn"
                autocompleteUrl="autocompleteAssignee.htm"
                autocompleteServiceEnabled="Assignee_Autocomplete"
                permissionAutocomplete="${security_assignee_search || !configurationServiceDelegator.securityEnabled}"
                minimumCharAutocomplete="service.assignee.autocomplete.minchars"
                textCodeWhenEmpty="assignee.importBox.whenEmpty"
                importButtonTextCode="assignee.form.action.import"/>
        <span class="separator"><spring:message code="person.form.separator.or"/></span>
        <button id="assigneeCreateNew" class="create-new-button" type="button">
            <i class="create-icon"></i>
            <spring:message code="assignee.form.action.createNew"/>
        </button>
        <div class="tip"><spring:message code="assignee.form.import"/></div>
        <hr>
    </component:generic>


    <%-----%>
    <%--TODO: Search in ESearch button ?--%>
    <%-----%>
    <%--<c:if test="${(security_assignee_search || !configurationServiceDelegator.securityEnabled) && service_assignee_search}">--%>
        <%--<c:set var="assigneeSearchUrl"--%>
               <%--value="${configurationServiceDelegator.getValueFromGeneralComponent('service.assignee.search.url')}"/>--%>
        <%--<a class="btn" href="${assigneeSearchUrl}" target="_blank"><spring:message code="assignee.form.action.search"/></a>--%>
    <%--</c:if>--%>

    <%--<span class="separator"><spring:message code="person.form.separator.or"/></span>--%>
    <%--<button id="assigneeCreateNew" class="create-new-button" type="button">--%>
        <%--<i class="create-icon"></i>--%>
        <%--<spring:message code="assignee.form.action.createNew"/>--%>
    <%--</button>--%>
</c:if>