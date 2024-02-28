<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var ="defaultValueFilter" value=""/>
<c:set var="notEditableClass" value="show"/>

<c:set var="service_address_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('Address_Autocomplete')}"/>
<input type="hidden" id="service_address_autocomplete" value="${service_address_autocomplete}" />

<input type="hidden" id="office" value="${configurationServiceDelegator.getOffice()}" />

<spring:bind path="${param.path}.country">
	<c:set var="wizard" value="${status.value}"/>
</spring:bind>

<c:if test="${empty formEdit && empty wizard}">
	<c:set var="defaultValueFilter" value="${configurationServiceDelegator.getOffice()}"/>
</c:if>

<c:choose>
	<c:when test="${not empty wizard && configurationServiceDelegator.getOffice() != wizard}">
		<c:set var="notEditableClass" value="hide"/>
	</c:when>
	<c:otherwise>
		<c:set var="notEditableClass" value="show"/>
	</c:otherwise>
</c:choose>
                              
	<c:if test="${service_address_autocomplete}">
			<div id="autocompleteAdddress" class="${notEditableClass}">
				<component:import importButtonId=""
					              id="addressAutocompleteTextfield"
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
	<div class="lineheightzero">
        <div class="address-info">
            <component:input path="${param.path}.street" id="street" checkRender="true" formTagClass="address-big" labelTextCode="person.address.field.streetAddress" readOnly="${service_address_autocomplete && notEditableClass != 'hide'}"/>
        </div>
        <div class="address-info">
            <component:input path="${param.path}.houseNumber" id="houseNumber" checkRender="true" formTagClass="address-small" labelTextCode="person.address.field.houseNumber" readOnly="${service_address_autocomplete && notEditableClass != 'hide'}"/>
        </div>
    </div>
    <div class="lineheightzero">
        <div class="address-info">
            <component:input path="${param.path}.city" id="city" checkRender="true" formTagClass="address-big" labelTextCode="person.address.field.city" readOnly="${service_address_autocomplete && notEditableClass != 'hide'}"/>
        </div>
        <div class="address-info">
            <component:input path="${param.path}.postalCode" id="postalCode" checkRender="true" formTagClass="address-small" labelTextCode="person.address.field.postalCode" readOnly="${service_address_autocomplete && notEditableClass != 'hide'}"/>
        </div>
    </div>
<component:select items="${configurationServiceDelegator['countries']}" path="${param.path}.country" id="addressCountry" itemLabel="value"
				  itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
				  labelTextCode="person.address.field.country" defaultValue="${defaultValueFilter}" sortValues="true" />
<!-- labelTextCode="person.address.field.country" defaultValue="${param.defaultCountry}"/>-->

<component:generic path="${param.path}.stateprovince" checkRender="true">
<span style="display: block;">
    <component:select path="${param.path}.stateprovince" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="person.address.field.stateProvince"/>
</span>
</component:generic>
<form:input type="hidden" disabled="true" path="${param.path}.stateprovince" id="${param.path}.selectedstate"/>