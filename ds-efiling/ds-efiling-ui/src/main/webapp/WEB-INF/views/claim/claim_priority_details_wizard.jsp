<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="addbox claimFields">
    <form:form modelAttribute="priorityForm" id="priorityForm" cssClass="fileUploadForm formEF">
        
        <c:set var="sectionId" value="priority" scope="request"/>
        <c:set var="service_priority_manual" value="${configurationServiceDelegator.isServiceEnabled('Priority_Manual')}"/>
        
        <header>
        	<span id="priorityCurrentNumber" class="number"></span>
            <h3><spring:message code="claim.title.priority"/></h3>
            <ul class="controls">
                <li>
                    <a class="cancelPriorityButton"><spring:message code="claim.action.cancel"/></a>
                </li>
                <li>
                    <button type="button" class="addPriorityWizard">
                    	<spring:message code="claim.action.save"/>
                    </button>
                </li>
            </ul>
        </header>
        
        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedPriority"/>

        <component:select items="${configurationServiceDelegator['priorityTypes']}"
                         labelTextCode="claim.priority.field.type"
                         path="type" itemLabel="value"
                         itemValue="code" checkRender="true"/>

        <component:select items="${configurationServiceDelegator['countries']}"
                         labelTextCode="claim.priority.field.countryOfFirstFiling"
                         path="country" itemLabel="value"
                         itemValue="code" itemFilter="isPriority" checkRender="true"/>
 
 		<sec:authorize access="hasRole('Priority_Import')" var="security_priority_import" />                        
 		<sec:authorize access="hasRole('Priority_Search')" var="security_priority_search" />
        <c:set var="service_priority_import" value="${configurationServiceDelegator.isServiceEnabled('Priority_Import')}"/>

        <c:choose>
            <c:when test="${empty priorityForm.id}">
                <c:set var="newFormDetailsClass" value=""/>
                <c:set var="manualDetailsClass" value="hide"/>
            </c:when>
            <c:otherwise>
                <c:set var="newFormDetailsClass" value="hide"/>
                <c:set var="manualDetailsClass" value=""/>
            </c:otherwise>
        </c:choose>

        <div id="newFormDetails" class="import ${newFormDetailsClass}">
            <c:if test="${service_priority_import && (security_priority_import || !configurationServiceDelegator.securityEnabled)}">
                <c:set var="service_priority_autocomplete_detailsUrlEnabled" value="${configurationServiceDelegator.getValueFromGeneralComponent('service.design.priority.details.urlEnabled')}"/>
                <input type="hidden" id="priorityTradeMarkConfig_searchUrlEnabled" value="${service_priority_autocomplete_detailsUrlEnabled}"/>
                <c:if test="${service_priority_autocomplete_detailsUrlEnabled}">
                    <input type="hidden" id="priorityTradeMarkConfig_searchUrl" value="${configurationServiceDelegator.getValueFromGeneralComponent("service.design.priority.details.url")}"/>
                    <span class="hidden" id="priorityTradeMarkConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>
                </c:if>

                <component:import
                				id="priorityImportTextField"
                				component="general"
                				labelTextCode="claim.priority.field.lastPriority"
                                importTextfieldClass="claim-id-input"
                                textCodeWhenEmpty="claim.priority.import.whenEmpty"
                                footerTextCode="claim.priority.field.footerImport"
                				importButtonId="priorityImportButton"
                				importButtonClass="btn importPriority"
                				importButtonTextCode="claim.action.import"
                				autocompleteUrl="autocompletePriority.htm"
                                autocompleteServiceEnabled="Priority_Autocomplete"
                                minimumCharAutocomplete="minimum.characters.autocomplete"
                                permissionAutocomplete="${security_priority_search || !configurationServiceDelegator.securityEnabled}"
                                />
            </c:if>
            <c:if test="${service_priority_import && (security_priority_import || !configurationServiceDelegator.securityEnabled) && service_priority_manual}">
                <span class="separator"><spring:message code="claim.title.or"/></span>
            </c:if>
            <c:if test="${service_priority_manual}">
                <button type="button" id="createManualDetails" class="create-manually-button">
                    <i class="create-icon"></i>
                    <spring:message code="claim.action.manually"/>
                </button>
            </c:if>
        </div>
	</form:form>

	<div id="manualDetailsImportError">
	</div>
	
    <div id="manualDetails" class="${manualDetailsClass}">
        
		<jsp:include page="claim_priority_details_manual_wizard.jsp" />

	</div>
</div>
	
