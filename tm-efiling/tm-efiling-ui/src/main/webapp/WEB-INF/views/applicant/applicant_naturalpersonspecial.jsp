<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/applicant/applicant_choosetype.jsp"/>

<form:form cssClass="formEF" id="applicantDetailsForm" modelAttribute="applicantNaturalPersonSpecialForm">
    <c:set var="sectionId" value="applicant_naturalpersonspecial" scope="request"/>
    <c:set var="imported" value="${applicantNaturalPersonSpecialForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedApplicant"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="apptypehidden" value="${applicantNaturalPersonSpecialForm.type}"/>
    <component:input path="name" checkRender="true" fieldLength="200"
                    labelTextCode="applicant.naturalPersonSpecialCase.field.name"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="applicant.naturalPersonSpecialCase.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="applicant.naturalPersonSpecialCase.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="applicant.naturalPersonSpecialCase.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                    labelTextCode="applicant.naturalPersonSpecialCase.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="applicant.naturalPersonSpecialCase.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="applicant.naturalPersonSpecialCase.field.countryOfDomicile" itemLabel="value" itemValue="code"
                     checkRender="true"
                     itemFilter="isApplicant"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="applicant.naturalPersonSpecialCase.field.consentForPublishingPersonalInfo"/>
    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isApplicant"/>
    </jsp:include>
    <component:generic path="correspondenceAddresses" checkRender="true">
        <label class="correspondence-address" for="applicantHasCorrespondenceAddress">
            <input id="applicantHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <spring:message code="person.field.wantCorrespondenceAddress"/>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isApplicant"/>
            <jsp:param name="size" value="${applicantNaturalPersonSpecialForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <br/>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>


    <component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                       labelTextCode="applicant.naturalPersonSpecialCase.field.receiveCorrespondenceViaEmail"/>


    <ul class="controls">
        <li>
            <a class="cancelButton applicant"><spring:message
                    code="applicant.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addApplicant addAppNaturalPersonSpecial" type="button">
                <i class="add-icon"/>
                <spring:message code="applicant.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>