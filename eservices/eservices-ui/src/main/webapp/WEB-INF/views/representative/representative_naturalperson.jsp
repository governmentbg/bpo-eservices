<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/representative/representative_choosetype.jsp"/>

<form:form id="representativeNaturalPersonForm" cssClass="fileUploadForm formEF" modelAttribute="representativeNaturalPersonForm">
    <c:set var="imported" value="${representativeNaturalPersonForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_naturalperson" scope="request"/>

    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>
    <input type="hidden" class="reptypehidden" value="${representativeNaturalPersonForm.type}"/>
    <component:input path="title" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.title" formTagClass="address-small"/>
    <component:input path="firstName" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.middlename"/>
    <component:input path="surname" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isRepresentative" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.taxIdNumber"/>
    <component:input path="businessVatNumber" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.businessVatNumber"/>
    <component:input path="reference" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.reference"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="representative.naturalPerson.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfDomicile"
                     labelTextCode="representative.naturalPerson.field.countryOfDomicile" itemLabel="value"
                     itemValue="code" checkRender="true" itemFilter="isRepresentative"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="representative.naturalPerson.field.consentForPublishingPersonalInfo"/>
    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isRepresentative"/>
    </jsp:include>
    
    <br>
    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>
    
    <component:checkbox path="representativeIsOwner" checkRender="true"
                  labelTextCode="representative.naturalPerson.field.representativeIsOwner"/> 
    
    <component:generic path="correspondenceAddresses" checkRender="false">

        <label class="correspondence-address" for="representativeHasCorrespondenceAddress">
        	<component:checkbox path="hasCorrespondenceAddress" checkRender="true"
                       labelTextCode="person.field.wantCorrespondenceAddress" id="representativeHasCorrespondenceAddress"/>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isRepresentative"/>
            <jsp:param name="size" value="${representativeNaturalPersonForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

 
    
    <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">            
        <component:file pathFilename="representativeAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.naturalPerson.field.attachment"
                        fileWrapper="${representativeNaturalPersonForm.representativeAttachment}"/>
	</div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />
	    
    <ul class="controls">
        <li>
            <a class="cancelButton representative"><spring:message
                    code="representative.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addRepresentative addRepresentativeNaturalPerson" type="button">
                <i class="add-icon"/>
                <spring:message code="representative.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>                 	
