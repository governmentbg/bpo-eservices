<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="representativeCurrentNumber" class="number"></span>

    <h3><spring:message code="representative.form.title"/></h3>
</header>

<sec:authorize access="hasRole('Representative_Import')" var="security_representative_import" />
<sec:authorize access="hasRole('Representative_Search')" var="security_representative_search" />

<c:set var="service_representative_import" value="${configurationServiceDelegator.isServiceEnabled('Representative_Import')}"/>
<c:set var="service_representative_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('Representative_Autocomplete')}"/>
<c:set var="service_representative_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="service_representative_manual" value="${configurationServiceDelegator.isServiceEnabled('Representative_Manual')}"/>

<input type = "hidden" id="id_representative_manual" value="${service_representative_manual}" />

<c:if test="${service_representative_import}">
    <c:set var="sectionId" value="representative" scope="request"/>
    <component:generic path="personIdNumber" checkRender="true">
        <component:import importButtonId="representativeImportButton"
                         id="representativeImportTextfield"
                         component="eu.ohim.sp.core.person"
                         autocompleteUrl="autocompleteRepresentative.htm"
                         autocompleteServiceEnabled="Representative_Autocomplete"
                         permissionAutocomplete="${(security_representative_search || !configurationServiceDelegator.securityEnabled)}"
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
<c:if test="${!service_representative_import && service_representative_autocomplete && !service_representative_manual}">
	<jsp:useBean class="eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm" id="representativeNaturalPersonForm" scope="request"/>
   		<form:form id ="representativeForm" cssClass="formEF" modelAttribute="representativeNaturalPersonForm">
	   		<c:set var="sectionId" value="representative" scope="request"/>
	   		<c:set var="imported" value="${true}" scope="request"/>
	        <component:import importButtonId=""
	                         id="representativeImportTextfield"
	                         component="eu.ohim.sp.core.person"
	                         autocompleteUrl="autocompleteRepresentative.htm"
	                         autocompleteServiceEnabled="Representative_Autocomplete"
	                         permissionAutocomplete="${true}"
	                         minimumCharAutocomplete="service.representative.autocomplete.minchars"
	                         importButtonClass="btn"
	                         textCodeWhenEmpty="representative.importBox.whenEmpty"
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