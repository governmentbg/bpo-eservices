<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="service_address_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('Address_Autocomplete')}"/>
<c:set var="notEditableClassCorr" value="show"/>

<div class="lineheightzero">
    <div class="address-info">
        <component:input id="corrStreet" path="${param.path}.addressForm.street" checkRender="true" formTagClass="address-big"
                        labelTextCode="person.address.field.streetAddress" readOnly="${service_address_autocomplete && notEditableClassCorr != 'hide'}"/>
    </div>
    <div class="address-info">
        <component:input id="corrHouseNumber" path="${param.path}.addressForm.houseNumber" checkRender="true" formTagClass="address-small"
                        labelTextCode="person.address.field.houseNumber" readOnly="${service_address_autocomplete && notEditableClassCorr != 'hide'}"/>
    </div>
</div>

<div class="lineheightzero">
    <div class="address-info">
        <component:input id="corrCity" path="${param.path}.addressForm.city" checkRender="true" formTagClass="address-big"
                        labelTextCode="person.address.field.city" readOnly="${service_address_autocomplete && notEditableClassCorr != 'hide'}"/>
    </div>
    <div class="address-info">
        <component:input id="corrPostalCode" path="${param.path}.addressForm.postalCode" checkRender="true" formTagClass="address-small"
                        labelTextCode="person.address.field.postalCode" readOnly="${service_address_autocomplete && notEditableClassCorr != 'hide'}"/>
    </div>
</div>



<component:input path="${param.path}.correspondenceName" checkRender="true"
                labelTextCode="person.address.field.correspondenceName" formTagClass="long-fields"/>

<c:set var ="defaultValueFilter" value=""/>

<spring:bind path="${param.path}.addressForm.country">
	<c:set var="wizard" value="${status.value}"/>
</spring:bind>

<c:if test="${empty formEdit && empty wizard}">
	<c:set var="defaultValueFilter" value="${configurationServiceDelegator.getOffice()}"/>
</c:if>

<c:choose>
	<c:when test="${not empty wizard && configurationServiceDelegator.getOffice() != wizard}">
		<c:set var="notEditableClassCorr" value="hide"/>
	</c:when>
	<c:otherwise>
		<c:set var="notEditableClassCorr" value="show"/>
	</c:otherwise>
</c:choose>

<input type="hidden" id="office" value="${configurationServiceDelegator.getOffice()}" />

 <component:select items="${configurationServiceDelegator['countries']}" path="${param.path}.addressForm.country" id="correspondenceAddressCountry" itemLabel="value"
                 itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                 labelTextCode="person.address.field.country" defaultValue="${defaultValueFilter}" sortValues="true" />

<c:if test="${service_address_autocomplete}">
	<div id="autocompleteCorrAdddress" class="${notEditableClassCorr}">
		<component:import importButtonId=""
			              id="corrAddressAutocompleteTextfield"
			              component="eu.ohim.sp.core.person"
			              autocompleteUrl="autocompleteAddress.htm"
			              autocompleteServiceEnabled="Address_Autocomplete"
			              permissionAutocomplete="${true}"
			              minimumCharAutocomplete="service.representative.autocomplete.minchars"
			              importButtonClass="btn"
			              textCodeWhenEmpty="address.importBox.whenEmpty"
			              importButtonTextCode="representative.form.action.import"/>
		<div class="tip"><spring:message code="address.form.autocomplete"/></div>
	</div>
</c:if>


<span>
    <component:select id="corrStateProvince" path="${param.path}.addressForm.stateprovince" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="person.address.field.stateProvince"/>
</span>

<component:input path="${param.path}.correspondenceEmail" checkRender="true"
                labelTextCode="person.address.field.correspondenceEmail"/>

<component:input path="${param.path}.correspondencePhone" checkRender="true"
                labelTextCode="person.address.field.correspondencePhone"/>
                
<component:input path="${param.path}.correspondenceFax" checkRender="true"
                labelTextCode="person.address.field.correspondenceFax"/>               

<form:input type="hidden" disabled="true" path="${param.path}.addressForm.stateprovince" id="${param.path}.addressForm.selectedstate"/>
