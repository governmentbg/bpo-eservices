<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/applicant/applicant_choosetype.jsp"/>

<form:form cssClass="formEF" id="applicantDetailsForm" modelAttribute="applicantNaturalPersonForm">
    <c:set var="sectionId" value="applicant_naturalperson" scope="request"/>
    <c:set var="imported" value="${applicantNaturalPersonForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedApplicant"/>
    <input type="hidden" id="formReturned" value="true"/>
    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>
    <form:hidden id="hiddenFormId" path="id"/>
    
	<component:validateImport path="validateImported" />    

    <input type="hidden" class="apptypehidden" value="${applicantNaturalPersonForm.type}"/>
    <component:input path="firstName" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.secondName"/>
    <div class="tip applicantNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
    <component:input path="surname" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="applicant.naturalPerson.field.nationality"/>

    <component:generic path="nationalId" checkRender="true">
        <component:generic path="nationalIdType" checkRender="true">
            <div style="margin: 10px 0px">
                <form:radiobutton cssClass="editableImport" path="nationalIdType" value="BG_NATIONAL_ID" /> <span style="margin-right: 10px"><spring:message code="person.naturalPerson.form.nationalIdType.BG_NATIONAL_ID"/></span>
                <form:radiobutton cssClass="editableImport" path="nationalIdType" value="BG_FOREIGNER_ID"/> <spring:message code="person.naturalPerson.form.nationalIdType.BG_FOREIGNER_ID"/>
            </div>
            <component:input formTagClass="editableImport" id="applicantNationalId" path="nationalId" checkRender="false"/>
            <div class="tip applicantNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.nationalId.tip"/></div>
        </component:generic>
    </component:generic>

    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"

                    labelTextCode="applicant.naturalPerson.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="applicant.naturalPerson.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="applicant.naturalPerson.field.countryOfDomicile" formTagClass="span3"
                     itemLabel="value" itemValue="code" checkRender="true"
                     itemFilter="isApplicant"/>

    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="applicant.naturalPerson.field.consentForPublishingPersonalInfo"/>

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
            <jsp:param name="size" value="${applicantNaturalPersonForm.correspondenceAddresses.size()}"/>
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
            <button class="addApplicant addAppNaturalPerson" type="button">
                <i class="add-icon"/>
                <spring:message code="applicant.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>