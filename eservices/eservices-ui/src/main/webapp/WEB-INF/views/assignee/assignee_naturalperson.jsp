<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/assignee/assignee_choosetype.jsp"/>

<form:form cssClass="formEF" id="assigneeDetailsForm" modelAttribute="assigneeNaturalPersonForm">
    <c:set var="sectionId" value="assignee_naturalperson" scope="request"/>
    <c:set var="imported" value="${assigneeNaturalPersonForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedAssignee"/>
    <input type="hidden" id="formReturned" value="true"/>
    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>
    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="apptypehidden" value="${assigneeNaturalPersonForm.type}"/>
    <component:input path="title" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.title" formTagClass="address-small"/>
    <component:input path="firstName" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                     labelTextCode="assignee.naturalPerson.field.middleName"/>
    <div class="tip assigneeNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
    <component:input path="surname" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="assignee.naturalPerson.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="assignee.naturalPerson.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="assignee.naturalPerson.field.countryOfDomicile" formTagClass="span3"
                     itemLabel="value" itemValue="code" checkRender="true"
                     itemFilter="isApplicant"/>

    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="assignee.naturalPerson.field.consentForPublishingPersonalInfo"/>	
	
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
            <jsp:param name="size" value="${assigneeNaturalPersonForm.correspondenceAddresses.size()}"/>
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
            <button class="addAssignee addAssNaturalPerson" type="button">
                <i class="add-icon"/>
                <spring:message code="assignee.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>