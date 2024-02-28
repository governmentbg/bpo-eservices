<%@ page import="org.apache.log4j.Logger" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:generic path="${param.path}.correspondenceName" checkRender="true">
	<component:input path="${param.path}.correspondenceName" checkRender="true"
                labelTextCode="person.address.field.correspondentName"/>
	<br />
</component:generic>

<c:set var ="defaultCountry" value=""/>

<spring:bind path="${param.path}.addressForm.country">
    <c:set var="wizard" value="${status.value}"/>
</spring:bind>
<c:if test="${empty formEdit && empty wizard}">
    <c:set var="defaultCountry" value="${configurationServiceDelegator.getOffice()}"/>
</c:if>

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
                 labelTextCode="person.address.field.country" defaultValue="${defaultCountry}"/>
<span>
    <component:select path="${param.path}.addressForm.stateprovince" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="person.address.field.stateProvince"/>
</span>

<component:input path="${param.path}.correspondenceEmail" checkRender="true"
                labelTextCode="person.address.field.correspondentEmail"/>


<component:input path="${param.path}.correspondencePhone" checkRender="true"
                labelTextCode="person.address.field.correspondentPhone"/>

<component:input path="${param.path}.correspondenceFax" checkRender="true"
                labelTextCode="person.address.field.correspondentFax"/>

<component:checkbox path="${param.path}.submittedJurisdictionIndicator" checkRender="true"
                    labelTextCode="person.address.field.submittedJurisdictionIndicator"/>


<component:generic path="${param.path}.electronicCorrespondence" checkRender="true">
    <c:if test="${not empty flowBean.currentUserName && not empty flowBean.currentUserEmail}">
        <div>
            <br>
            <span><b><spring:message code="current.user.logged.in"/></b> </span>
            <span><c:out value="${flowBean.currentUserName}, ${flowBean.currentUserEmail}"/> </span>
        </div>
    </c:if>

    <div style="margin-top:10px">
        <spring:message code="person.address.field.electronicCorrespondence.hint"/>
    </div>

    <form:hidden path="${param.path}.electronicCorrespondence" value="true"/>
</component:generic>


<form:input type="hidden" disabled="true" path="${param.path}.addressForm.stateprovince" id="${param.path}.addressForm.selectedstate"/>
