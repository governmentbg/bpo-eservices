<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="streetCode" value="person.address.field.streetAddress"/>
<c:if test="${not empty param.streetFieldMessageCode}">
	<c:set var="streetCode" value="${param.streetFieldMessageCode}"/>
</c:if>

<br>
<component:generic path="${param.path}.street" checkRender="true">
	<div class="address-info">
		<component:input path="${param.path}.street" checkRender="true" formTagClass="address-big"
						 labelTextCode="${streetCode}"/>
	</div>
</component:generic>

<component:generic path="${param.path}.houseNumber" checkRender="true">
	<div class="address-info">
		<component:input path="${param.path}.houseNumber" checkRender="true" formTagClass="address-small"
						 labelTextCode="person.address.field.houseNumber"/>
	</div>
</component:generic>

<br>

<component:generic path="${param.path}.city" checkRender="true">
	<div class="address-info">
		<component:input path="${param.path}.city" checkRender="true" formTagClass="address-big"
						 labelTextCode="person.address.field.city"/>
	</div>
</component:generic>

<component:generic path="${param.path}.postalCode" checkRender="true">
	<div class="address-info">
		<component:input path="${param.path}.postalCode" checkRender="true" formTagClass="address-small"
						 labelTextCode="person.address.field.postalCode"/>
	</div>
</component:generic>

<component:select items="${configurationServiceDelegator['countries']}" path="${param.path}.country" itemLabel="value"
				  itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
				  labelTextCode="person.address.field.country"/>

<form:input type="hidden" disabled="true" path="${param.path}.stateprovince" id="${param.path}.selectedstate"/>
<component:generic path="${param.path}.stateprovince" checkRender="true">
	<span>
    	<component:select path="${param.path}.stateprovince" itemLabel="value"
						  itemValue="code" checkRender="true"
						  labelTextCode="person.address.field.stateProvince"/>
	</span>
</component:generic>
<form:input type="hidden" disabled="true" path="${param.path}.stateprovince" id="${param.path}.selectedstate"/>