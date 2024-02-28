<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<br>
<div class="address-info">
    <component:input path="${param.path}.street" checkRender="true" formTagClass="address-big"
                    labelTextCode="person.address.field.streetAddress"/>
</div>
<div class="address-info">
    <component:input path="${param.path}.houseNumber" checkRender="true" formTagClass="address-small"
                    labelTextCode="person.address.field.houseNumber"/>
</div>
<br>
<div class="address-info">
    <component:input path="${param.path}.city" checkRender="true" formTagClass="address-big"
                    labelTextCode="person.address.field.city"/>
</div>
<div class="address-info">
    <component:input path="${param.path}.postalCode" checkRender="true" formTagClass="address-small"
                    labelTextCode="person.address.field.postalCode"/>
</div>

<component:select items="${configurationServiceDelegator['countries']}" path="${param.path}.country" itemLabel="value"
                 itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                 labelTextCode="person.address.field.country"/>

<component:generic path="${param.path}.stateprovince" checkRender="true">
<span>
    <component:select path="${param.path}.stateprovince" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="person.address.field.stateProvince"/>
</span>
</component:generic>	
<form:input type="hidden" disabled="true" path="${param.path}.stateprovince" id="${param.path}.selectedstate"/>