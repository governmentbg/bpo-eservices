<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/applicant/applicant_choosetype.jsp"/>

<form:form id="applicantDetailsForm" cssClass="formEF" modelAttribute="applicantLegalEntityForm">
    <c:set var="sectionId" value="applicant_legalentity" scope="request"/>
    <c:set var="imported" value="${applicantLegalEntityForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedApplicant"/>
    <input type="hidden" id="formReturned" value="true"/>
    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>

    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="apptypehidden" value="${applicantLegalEntityForm.type}"/>

    <component:input path="name" checkRender="true" fieldLength="200" labelTextCode="applicant.legalEntity.field.entityName"/>
    <component:input path="legalForm" checkRender="true" labelTextCode="applicant.legalEntity.field.legalForm"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfRegistration" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="applicant.legalEntity.field.nationality"/>

    <span>
    <component:select path="stateOfIncorporation" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="applicant.legalEntity.field.stateOfIncorporation"/>
    </span>
    <form:input path="stateOfIncorporation" id="selectedStateOfIncorporation" disabled="true" type="hidden"/>

    <component:input path="businessVatNumber" checkRender="true" labelTextCode="applicant.legalEntity.field.businessVatNumber"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="applicant.legalEntity.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true" labelTextCode="applicant.legalEntity.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true" labelTextCode="applicant.legalEntity.field.contactPerson"/>
    <component:input path="domicile" checkRender="true" labelTextCode="applicant.legalEntity.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="applicant.legalEntity.field.countryOfDomicile"
                     itemLabel="value" itemValue="code" checkRender="true" itemFilter="isApplicant"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="applicant.legalEntity.field.consentForPublishingPersonalInfo"/>

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
            <jsp:param name="size" value="${applicantLegalEntityForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <br/>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

    <component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                       labelTextCode="applicant.legalEntity.field.receiveCorrespondenceViaEmail"/>


    <ul class="controls">
        <li>
            <a class="cancelButton applicant"><spring:message
                    code="applicant.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addApplicant addAppLegalEntity" type="button">
                <i class="add-icon"/>
                <spring:message code="applicant.form.action.add.top"/>
            </button>
        </li>
    </ul>

</form:form>