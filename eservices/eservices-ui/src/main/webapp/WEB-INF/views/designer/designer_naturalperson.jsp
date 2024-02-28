<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form cssClass="formEF" id="designerDetailsForm" modelAttribute="designerForm">
    <c:set var="sectionId" value="designer_notAGroup" scope="request"/>
    <c:set var="imported" value="${designerForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedDesigner"/>
    <input type="hidden" id="formReturned" value="true"/>

    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>

    <form:hidden id="hiddenFormId" path="id"/>
    <component:generic path="currentUserIndicator" checkRender="true">
        <form:hidden id="currentUserIndicatorId" path="currentUserIndicator"/>
    </component:generic>
    <component:input path="title" checkRender="true"
                     labelTextCode="designer.field.title" formTagClass="address-small"/>
    <component:input path="firstName" checkRender="true"
                     labelTextCode="designer.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                     labelTextCode="designer.field.middlename"/>
    <div class="tip designerNaturalPersonTip" style="color:red; display:none"><spring:message
            code="person.naturalPerson.form.middleName.tip"/></div>
    <component:input path="surname" checkRender="true"
                     labelTextCode="designer.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                      itemValue="code" checkRender="true"
                      labelTextCode="designer.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                     labelTextCode="designer.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                     labelTextCode="designer.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                     labelTextCode="designer.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                     labelTextCode="designer.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                      labelTextCode="designer.field.countryOfDomicile" formTagClass="span3"
                      itemLabel="value" itemValue="code" checkRender="true" itemFilter="isDesigner"/>

    <jsp:include page="/WEB-INF/views/partials/address.jsp">
    <jsp:param name="path" value="address"/>
    <jsp:param name="itemFilterValue" value="isDesigner"/>
</jsp:include>


<br>
<jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

<component:generic path="correspondenceAddresses" checkRender="true">
    <label class="correspondence-address" for="designerHasCorrespondenceAddress">
        <component:checkbox path="hasCorrespondenceAddress" checkRender="true"
                            labelTextCode="person.field.wantCorrespondenceAddress"
                            id="designerHasCorrespondenceAddress"/>
        <!-- <input id="designerHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <spring:message code="person.field.wantCorrespondenceAddress"/>-->
    </label>
    <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
        <jsp:param name="path" value="correspondenceAddresses"/>
        <jsp:param name="itemFilterValue" value="isDesigner"/>
        <jsp:param name="size" value="${designerForm.correspondenceAddresses.size()}"/>
    </jsp:include>
</component:generic>


<component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                    labelTextCode="designer.legalEntity.field.receiveCorrespondenceViaEmail"/>

<ul class="controls">
    <li>
        <a class="cancelButton designer"><spring:message
                code="designer.form.action.cancelAdd.top"/></a>
    </li>
    <li>
        <button class="addDesigner addDesignerBtn" type="button">
            <i class="add-icon"/>
            <spring:message code="designer.form.action.add.top"/>
        </button>
    </li>
</ul>
</form:form>