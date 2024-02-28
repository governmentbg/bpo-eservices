<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/assignee/assignee_choosetype.jsp"/>

<form:form cssClass="formEF" id="assigneeDetailsForm" modelAttribute="assigneeNaturalPersonSpecialForm">
    <c:set var="sectionId" value="assignee_naturalpersonspecial" scope="request"/>
    <c:set var="imported" value="${assigneeNaturalPersonSpecialForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedAssignee"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="apptypehidden" value="${assigneeNaturalPersonSpecialForm.type}"/>
    <component:input path="name" checkRender="true"
                    labelTextCode="assignee.naturalPersonSpecialCase.field.name"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isAssignee" checkRender="true"
                     labelTextCode="assignee.naturalPersonSpecialCase.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="assignee.naturalPersonSpecialCase.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="assignee.naturalPersonSpecialCase.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                    labelTextCode="assignee.naturalPersonSpecialCase.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="assignee.naturalPersonSpecialCase.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="assignee.naturalPersonSpecialCase.field.countryOfDomicile" itemLabel="value" itemValue="code"
                     checkRender="true"
                     itemFilter="isAssignee"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="assignee.naturalPersonSpecialCase.field.consentForPublishingPersonalInfo"/>
    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isAssignee"/>
    </jsp:include>
    <component:generic path="correspondenceAddresses" checkRender="true">
        <label class="correspondence-address" for="assigneeHasCorrespondenceAddress">
            <input id="assigneeHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <spring:message code="person.field.wantCorrespondenceAddress"/>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isAssignee"/>
            <jsp:param name="size" value="${assigneeNaturalPersonSpecialForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>
    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>


    <component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                       labelTextCode="assignee.naturalPersonSpecialCase.field.receiveCorrespondenceViaEmail"/>


    <ul class="controls">
        <li>
            <a class="cancelButton assignee"><spring:message
                    code="assignee.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addAssignee addAssNaturalPersonSpecial" type="button">
                <i class="add-icon"/>
                <spring:message code="assignee.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>