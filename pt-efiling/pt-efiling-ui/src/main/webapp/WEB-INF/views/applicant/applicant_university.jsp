<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/applicant/applicant_choosetype.jsp"/>

<form:form id="applicantDetailsForm" cssClass="formEF" modelAttribute="applicantUniversityForm">
    <c:set var="sectionId" value="applicant_university" scope="request"/>
    <c:set var="imported" value="${applicantUniversityForm.imported}" scope="request"/>

	
    <form:hidden path="imported" id="importedApplicant"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>
 
    <input type="hidden" class="apptypehidden" value="${applicantUniversityForm.type}"/>

	<component:validateImport path="validateImported" />
	
    <component:input path="name" checkRender="true" fieldLength="200" labelTextCode="applicant.university.field.entityName"/>

	<br>

	<component:generic path="personIdentifierForm.afimi" checkRender="true">
	<div class="identify-info">
		<component:input path="personIdentifierForm.afimi" checkRender="true" formTagClass="identify-big" labelTextCode="person.field.afimi"/>						
	</div>
	</component:generic>

	<component:generic path="personIdentifierForm.doy" checkRender="true">
	<div class="identify-info">
		<component:input path="personIdentifierForm.doy" checkRender="true" formTagClass="identify-big" labelTextCode="person.field.doy"/>
	</div>
	</component:generic>
	   
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
    
    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp">
    	 <jsp:param name="websiteFieldMessageCode" value="person.contactDetails.field.universitySite"/>
    </jsp:include>
    
    <ul class="controls">
        <li>
            <a class="cancelButton applicant"><spring:message
                    code="applicant.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addApplicant addAppUniversity" type="button">
            	<spring:message code="applicant.form.action.save" />
            	<%--
                <i class="add-icon"/>
                <spring:message code="applicant.form.action.add.top"/>
                --%>
            </button>
        </li>
    </ul>

</form:form>