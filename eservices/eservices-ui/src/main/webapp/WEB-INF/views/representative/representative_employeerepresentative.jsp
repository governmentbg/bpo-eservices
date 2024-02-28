<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/representative/representative_choosetype.jsp"/>

<form:form id="representativeEmployeeRepresentativeForm" cssClass="formEF"
           modelAttribute="representativeEmployeeRepresentativeForm">
    <c:set var="imported" value="${representativeEmployeeRepresentativeForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_employeerepresentative" scope="request"/>
    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>

    <form:hidden id="hiddenFormId" path="id"/>
    <input type="hidden" class="reptypehidden" value="${representativeEmployeeRepresentativeForm.type}"/>

    <jsp:include page="/WEB-INF/views/partials/representative_legalStatus.jsp" />

    <component:input path="firstName" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.firstName"/>
    <component:input path="surname" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isRepresentative" checkRender="true"
                     labelTextCode="representative.employeeRepresentative.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.taxIdNumber"/>
    <component:checkbox path="economicConnections" checkRender="true" labelClass="economic-connections"
                       labelTextCode="representative.employeeRepresentative.field.economicConnections"/>
    <component:generic path="economicConnections" checkRender="true">
        <span>
            <label class="economic-connections" for="withoutEconomicConnections">
                <input id="withoutEconomicConnections" type="checkbox"/>
                    <span>
                        <spring:message code="representative.employeeRepresentative.field.withoutEconomicConnections"/>
                    </span>
            </label>
        </span>
    </component:generic>
    <span id="economicConnectionsFields">
        <component:input path="natureOfEconomicConnections" checkRender="true"
                        labelTextCode="representative.employeeRepresentative.field.natureOfEconomicConnections"/>
    </span>
    <component:input path="nameOfEmployer" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.nameOfEmployer"/>
    <component:input path="reference" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.reference"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="representative.employeeRepresentative.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfDomicile"
                     labelTextCode="representative.employeeRepresentative.field.countryOfDomicile"
                     itemLabel="value" itemValue="code" checkRender="true"
                     itemFilter="isRepresentative"/>

    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="representative.employeeRepresentative.field.consentForPublishingPersonalInfo"/>

    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isRepresentative"/>
    </jsp:include>

    <component:generic path="correspondenceAddresses" checkRender="false">

        <label class="correspondence-address" for="representativeHasCorrespondenceAddress">
            <input id="representativeHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <span><spring:message code="person.field.wantCorrespondenceAddress"/></span>
        </label>


        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isRepresentative"/>
            <jsp:param name="size" value="${representativeEmployeeRepresentativeForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

    <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
        <component:file pathFilename="representativeAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.legalEntity.field.attachment"
                        fileWrapper="${representativeEmployeeRepresentativeForm.representativeAttachment}"/>
    </div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />

    <ul class="controls">
        <li>
            <a class="cancelButton representative"><spring:message
                    code="representative.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addRepresentative addRepresentativeEmployeeRepresentative" type="button">
                <i class="add-icon"/>
                <spring:message code="representative.form.action.add.top"/>
            </button>
        </li>
    </ul>
    


</form:form>