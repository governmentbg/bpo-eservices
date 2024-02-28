<%@ page import="eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerType" %>
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
<form:form id="representativeProfessionalPractitionerForm" cssClass="formEF"
           modelAttribute="representativeProfessionalPractitionerForm">
    <c:set var="imported" value="${representativeProfessionalPractitionerForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_professionalpractitioner" scope="request"/>
    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>

    <form:hidden id="hiddenFormId" path="id"/>
    
    <component:validateImport path="validateImported" />
    
    <input type="hidden" class="reptypehidden" value="${representativeProfessionalPractitionerForm.type}"/>

    <jsp:include page="/WEB-INF/views/partials/representative_legalStatus.jsp" />

    <component:generic path="professionalPractitionerType" checkRender="true">
        <c:set var="enumValues" value="<%=Arrays.asList(ProfessionalPractitionerType.values())%>"/>
        <component:select path="professionalPractitionerType"
                         items="${enumValues}"
                         checkRender="true"
                         labelTextCode="representative.professionalPractitioner.field.type"
                         itemValue="code" itemLabel="description"/>
    </component:generic>
    <component:input path="firstName" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.firstName"/>
    <component:input path="surname" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isRepresentative" checkRender="true"
                     labelTextCode="representative.professionalPractitioner.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.taxIdNumber"/>
    <span>
    <component:input path="associationId" checkRender="true" formTagClass="${association_class}"
                    autocompleteUrl="autocompleteRepresentative.htm"
                    minimumCharAutocomplete="service.representative.association.autocomplete.minchars"
                    labelTextCode="representative.professionalPractitioner.field.associationId"/>
    </span>
    <component:input path="associationName" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.associationName"/>
    <component:input path="reference" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.reference"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfDomicile"
                     labelTextCode="representative.professionalPractitioner.field.countryOfDomicile"
                     itemLabel="value" itemValue="code" checkRender="true"
                     itemFilter="isRepresentative"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="representative.professionalPractitioner.field.consentForPublishingPersonalInfo"/>
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
            <jsp:param name="size" value="${representativeProfessionalPractitionerForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

    <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
        <component:file pathFilename="fileDocumentAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.legalEntity.field.attachment"
                        fileWrapper="${representativeProfessionalPractitionerForm.representativeAttachment}"/>
    </div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />
	
    <ul class="controls">
        <li>
            <a class="cancelButton representative"><spring:message
                    code="representative.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addRepresentative addRepresentativeProfessionalPractitioner" type="button">
            	<spring:message code="representative.form.action.save" />
            	<%--
                <i class="add-icon"/>
                <spring:message code="representative.form.action.add.top"/>
                --%>
            </button>
        </li>
    </ul>
</form:form>