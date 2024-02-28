<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>


    <h3><spring:message code="personChange.form.bottom.title.${personChangeForm.changeType}"/></h3>
</header>

<sec:authorize access="hasRole('PersonChange_Import')" var="security_personChange_import" />
<sec:authorize access="hasRole('PersonChange_Search')" var="security_personChange_search" />

<c:set var="service_personChange_import" value="${configurationServiceDelegator.isServiceEnabled('Representative_Import')}"/>
<c:set var="service_personChange_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('Representative_Autocomplete')}"/>
<c:set var="service_personChange_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="service_personChange_manual" value="${configurationServiceDelegator.isServiceEnabled('PersonChange_Manual')}"/>

<c:set var="personChangeRol" value="${personChangeForm.personRol}" />
<div id="personChange_missingInputText" class="hidden">
	<spring:message code="general.messages.${personChangeRol}.emptyInput"/>
</div>

<span id="personChangeFormTypeText" class="hide"><spring:message code="personChange.form.type.${personChangeRol}"/></span>

<input type = "hidden" id="id_personChange_manual" value="${service_personChange_manual}" />

<c:if test="${service_personChange_import}">
    <c:set var="sectionId" value="personChange" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import importButtonId="personChangeImportButton"
                         id="personChangeImportTextfield"
                         component="eu.ohim.sp.core.person"
                         autocompleteUrl="autocompleteRepresentative.htm"
                         autocompleteServiceEnabled="Representative_Autocomplete"
                         permissionAutocomplete="${(service_personChange_search || !configurationServiceDelegator.securityEnabled)}"
                         minimumCharAutocomplete="service.representative.autocomplete.minchars"
                         importButtonClass="btn"
                         textCodeWhenEmpty="personChange.importBox.${personChangeRol}.whenEmpty"
                         importButtonTextCode="personChange.form.action.import"/>
        <c:if test="${service_personChange_manual}">
	        <span class="separator"><spring:message code="person.form.separator.or"/></span>
	        <button type="button" id="personChangeCreateNew" class="create-new-button">
	            <i class="create-icon"></i>
	            <spring:message code="personChange.form.action.createNew"/>
	        </button>
        </c:if>
        <div class="tip"><spring:message code="personChange.form.import"/></div>
        <hr>
    </component:generic>
</c:if>
<c:if test="${!service_personChange_import && service_personChange_autocomplete && !service_personChange_manual}">
	<jsp:useBean class="eu.ohim.sp.common.ui.form.person.ChangeRepresentativeNaturalPersonForm" id="representativeNaturalPersonForm" scope="request"/>
   		<form:form id ="personChangeForm" cssClass="formEF" modelAttribute="representativeNaturalPersonForm">
	   		<c:set var="sectionId" value="personChange" scope="request"/>
	   		<c:set var="imported" value="${true}" scope="request"/>
	        <component:import importButtonId=""
	                         id="personChangeImportTextfield"
	                         component="eu.ohim.sp.core.person"
	                         autocompleteUrl="autocompleteRepresentative.htm"
	                         autocompleteServiceEnabled="Representative_Autocomplete"
	                         permissionAutocomplete="${true}"
	                         minimumCharAutocomplete="service.representative.autocomplete.minchars"
	                         importButtonClass="btn"
	                         textCodeWhenEmpty="representative.importBox.${personChangeRol}.whenEmpty"
	                         importButtonTextCode="representative.form.action.import"/>
	        	<div class="tip"><spring:message code="representative.form.import"/></div>
    		<form:hidden path="firstName" id="firstName"/>
    		<form:hidden path="id" id="id" />
    		<input type = "hidden" id="afimi" name="personIdentifierForm.afimi"/>
    		<input type = "hidden" id="imported" value="${imported}"/>
		    <ul class="controls">
                <li>
                    <a class="cancelButton representative"><spring:message
                            code="representative.form.action.cancelAdd.top"/></a>
                </li>
                <li>
                    <button class="addRepresentative addRepresentativeNaturalPerson" type="button">
                        <i class="add-icon"> </i>
                        <spring:message code="representative.form.action.add.top"/>
                    </button>
                </li>
            </ul>
   		</form:form>
</c:if>