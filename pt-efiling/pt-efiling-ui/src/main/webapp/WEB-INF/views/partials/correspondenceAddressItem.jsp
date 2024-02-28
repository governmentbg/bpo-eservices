<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:generic path="${param.path}.correspondenceName" checkRender="true">
	<component:input path="${param.path}.correspondenceName" checkRender="true"
                labelTextCode="person.address.field.correspondenceName"/>
	<br />
</component:generic>


<div class="address-info">
    <component:input path="${param.path}.addressForm.street" checkRender="true" formTagClass="address-big"
                    labelTextCode="person.address.field.streetAddress"/>
</div>
<div class="address-info">
    <component:input path="${param.path}.addressForm.houseNumber" checkRender="true" formTagClass="address-small"
                    labelTextCode="person.address.field.houseNumber"/>
</div>
<br>

<div class="address-info">
    <component:input path="${param.path}.addressForm.city" checkRender="true" formTagClass="address-big"
                    labelTextCode="person.address.field.city"/>
</div>
<div class="address-info">
    <component:input path="${param.path}.addressForm.postalCode" checkRender="true" formTagClass="address-small"
                    labelTextCode="person.address.field.postalCode"/>
</div>

<component:select items="${configurationServiceDelegator['countries']}" path="${param.path}.addressForm.country" itemLabel="value"
                 itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                 labelTextCode="person.address.field.country"/>
<span>
    <component:select path="${param.path}.addressForm.stateprovince" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="person.address.field.stateProvince"/>
</span>

<component:input path="${param.path}.correspondenceEmail" checkRender="true"
                labelTextCode="person.address.field.correspondenceEmail"/>


<component:input path="${param.path}.correspondencePhone" checkRender="true"
                labelTextCode="person.address.field.correspondencePhone"/>

<component:input path="${param.path}.correspondenceFax" checkRender="true"
                labelTextCode="person.address.field.correspondenceFax"/>

<component:checkbox path="${param.path}.submittedJurisdictionIndicator" checkRender="true"
                    labelTextCode="person.address.field.submittedJurisdictionIndicator"/>

<form:input type="hidden" disabled="true" path="${param.path}.addressForm.stateprovince" id="${param.path}.addressForm.selectedstate"/>
