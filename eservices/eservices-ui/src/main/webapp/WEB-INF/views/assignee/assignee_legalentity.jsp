<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/assignee/assignee_choosetype.jsp"/>

<form:form id="assigneeDetailsForm" cssClass="formEF" modelAttribute="assigneeLegalEntityForm">
    <c:set var="sectionId" value="assignee_legalentity" scope="request"/>
    <c:set var="imported" value="${assigneeLegalEntityForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedAssignee"/>
    <input type="hidden" id="formReturned" value="true"/>
    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>

    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="apptypehidden" value="${assigneeLegalEntityForm.type}"/>

    <component:input path="name" checkRender="true" fieldLength="200" labelTextCode="assignee.legalEntity.field.entityName"/>
    <component:input path="legalForm" checkRender="true" labelTextCode="assignee.legalEntity.field.legalForm"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfRegistration" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="assignee.legalEntity.field.nationality"/>

    <span>
    <component:select path="stateOfIncorporation" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="assignee.legalEntity.field.stateOfIncorporation"/>
    </span>
    <form:input path="stateOfIncorporation" id="selectedStateOfIncorporation" disabled="true" type="hidden"/>

    <component:input path="businessVatNumber" checkRender="true" labelTextCode="assignee.legalEntity.field.businessVatNumber"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="assignee.legalEntity.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true" labelTextCode="assignee.legalEntity.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true" labelTextCode="assignee.legalEntity.field.contactPerson"/>
    <component:input path="domicile" checkRender="true" labelTextCode="assignee.legalEntity.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="assignee.legalEntity.field.countryOfDomicile"
                     itemLabel="value" itemValue="code" checkRender="true" itemFilter="isApplicant"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="assignee.legalEntity.field.consentForPublishingPersonalInfo"/>

    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isApplicant"/>
    </jsp:include>
   
	<br>
    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>
    
	<component:generic path="correspondenceAddresses" checkRender="true">
        <label class="correspondence-address" for="assigneeHasCorrespondenceAddress">
			<component:checkbox path="hasCorrespondenceAddress" checkRender="true"
                       labelTextCode="person.field.wantCorrespondenceAddress" id="assigneeHasCorrespondenceAddress"/>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isApplicant"/>
            <jsp:param name="size" value="${assigneeLegalEntityForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>    

    <component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                       labelTextCode="assignee.legalEntity.field.receiveCorrespondenceViaEmail"/>


    <ul class="controls">
        <li>
            <a class="cancelButton assignee"><spring:message
                    code="assignee.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addAssignee addAssLegalEntity" type="button">
                <i class="add-icon"/>
                <spring:message code="assignee.form.action.add.top"/>
            </button>
        </li>
    </ul>

</form:form>