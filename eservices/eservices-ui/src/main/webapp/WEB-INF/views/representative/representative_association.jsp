<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/representative/representative_choosetype.jsp"/>
	 <form:form id="representativeAssociationForm" cssClass="formEF"
           modelAttribute="representativeAssociationForm">

    <c:set var="imported" value="${representativeAssociationForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_form_association" scope="request"/>
    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>
    <input type="hidden" class="reptypehidden" value="${representativeAssociationForm.type}"/>

    <jsp:include page="/WEB-INF/views/partials/representative_legalStatus.jsp" />

    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isRepresentative" checkRender="true"
                     labelTextCode="representative.association.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                    labelTextCode="representative.professionalPractitioner.field.taxIdNumber"/>
    <component:input path="associationName" checkRender="true" fieldLength="200"
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
    <component:generic path="correspondenceAddresses" checkRender="false">
        <label class="correspondence-address" for="representativeHasCorrespondenceAddress">
            <component:checkbox path="hasCorrespondenceAddress" checkRender="true"
                                labelTextCode="person.field.wantCorrespondenceAddress" id="representativeHasCorrespondenceAddress"/>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isRepresentative"/>
            <jsp:param name="size" value="${representativeAssociationForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

         <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
             <component:file pathFilename="representativeAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.legalEntity.field.attachment"
                             fileWrapper="${representativeAssociationForm.representativeAttachment}"/>
         </div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />

	<ul class="controls">
			<li><a class="cancelButton representative"><spring:message
						code="representative.form.action.cancelAdd.top" /></a></li>
			<li>
				<button class="addRepresentative addRepresentativeAssociation" type="button">
                <i class="add-icon"/>
                <spring:message code="representative.form.action.add.top"/>
            </button>
			</li>
	</ul>
	

</form:form>