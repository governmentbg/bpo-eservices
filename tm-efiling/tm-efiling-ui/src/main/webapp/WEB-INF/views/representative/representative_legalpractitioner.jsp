<%@ page import="eu.ohim.sp.common.ui.form.person.LegalPractitionerType" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="service_representative_association_autocomplete"
       value="${configurationServiceDelegator.isServiceEnabled('Representative_Association_Autocomplete')}"/>

<c:set var="association_class" value=""/>
<c:if test="${service_representative_association_autocomplete}">
    <c:set var="association_class" value="autocompleted"/>
</c:if>

<jsp:include page="/WEB-INF/views/representative/representative_choosetype.jsp"/>
<form:form id="representativeLegalPractitionerForm" cssClass="formEF"
           modelAttribute="representativeLegalPractitionerForm">
    <c:set var="imported" value="${representativeLegalPractitionerForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_legalpractitioner" scope="request"/>

    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>
    <c:if test="${formImportReset}">
        <div class="alert alert-danger" style="margin-top: 10px">
            <spring:message code="person.imported.reset"/>
        </div>
    </c:if>

    <form:hidden id="hiddenFormId" path="id"/>
    <input type="hidden" class="reptypehidden" value="${representativeLegalPractitionerForm.type}"/>
    <component:generic path="legalPractitionerType" checkRender="true">
        <c:set var="enumValues" value="<%=Arrays.asList(LegalPractitionerType.values())%>"/>
        <component:select path="legalPractitionerType"
                         items="${enumValues}"
                         checkRender="true"
                         labelTextCode="representative.legalPractitioner.field.type"
                         itemValue="code" itemLabel="description"/>
    </component:generic>

    <jsp:include page="/WEB-INF/views/partials/representative_legalStatus.jsp" />

    <span id="legalPractitionerPersonFields">
        <component:input path="firstName" checkRender="true"
                        labelTextCode="representative.legalPractitioner.field.firstName"/>
        <component:input path="middleName" checkRender="true"
                         labelTextCode="representative.legalPractitioner.field.middlename"/>
        <div class="tip representativeNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
        <component:input path="surname" checkRender="true"
                        labelTextCode="representative.legalPractitioner.field.surname"/>
        
        
        <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                         itemValue="code" itemFilter="isRepresentative" checkRender="true"
                         labelTextCode="representative.legalPractitioner.field.nationality"/>
    </span>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="representative.legalPractitioner.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="representative.legalPractitioner.field.taxIdNumber"/>
    <span>
    <component:input path="associationId" checkRender="true" formTagClass="${association_class}"
                    autocompleteUrl="autocompleteRepresentative.htm"
                    minimumCharAutocomplete="service.representative.association.autocomplete.minchars"
                    labelTextCode="representative.legalPractitioner.field.associationId"/>
    </span>
    <component:input path="associationName" checkRender="true"
                    labelTextCode="representative.legalPractitioner.field.associationName"/>
    <component:input path="reference" checkRender="true"
                    labelTextCode="representative.legalPractitioner.field.reference"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="representative.legalPractitioner.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfDomicile"
                     labelTextCode="representative.legalPractitioner.field.countryOfDomicile" itemLabel="value"
                     itemValue="code" checkRender="true"
                     itemFilter="isRepresentative"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="representative.legalPractitioner.field.consentForPublishingPersonalInfo"/>

    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isRepresentative"/>
    </jsp:include>

    <component:generic path="correspondenceAddresses" checkRender="true">
        <label class="correspondence-address" for="representativeHasCorrespondenceAddress">
            <input id="representativeHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <span><spring:message code="person.field.wantCorrespondenceAddress"/></span>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isRepresentative"/>
            <jsp:param name="size" value="${representativeLegalPractitionerForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>
    <br>

    <div>
        <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>
    </div>

    <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
        <component:file pathFilename="fileDocumentAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.legalEntity.field.attachment"
                        fileWrapper="${representativeLegalPractitionerForm.representativeAttachment}"/>
    </div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />

    <ul class="controls">
        <li>
            <a class="cancelButton representative"><spring:message
                    code="representative.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addRepresentative addRepresentativeLegalPractitioner" type="button">
                <i class="add-icon"/>
                <spring:message code="representative.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>