<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/holder/holder_choosetype.jsp"/>

<form:form id="holderDetailsForm" cssClass="formEF" modelAttribute="holderLegalEntityForm">
    <c:set var="sectionId" value="holder_legalentity" scope="request"/>
    <c:set var="imported" value="${holderLegalEntityForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedHolder"/>
    <input type="hidden" id="formReturned" value="true"/>

    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="htypehidden" value="${holderLegalEntityForm.type}"/>
    
     <component:generic path="holderHasChanged" checkRender="true">
	    	<c:set var="errors">
	        	<form:errors path="holderHasChanged"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<p class="flMessageError" id="${path}ErrorMessageServer">${message}</p>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>	   

    <component:input path="name" checkRender="true" labelTextCode="holder.legalEntity.field.entityName"/>
    <component:input path="legalForm" checkRender="true" labelTextCode="holder.legalEntity.field.legalForm"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfRegistration" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="holder.legalEntity.field.nationality"/>

    <span>
    <component:select path="stateOfIncorporation" itemLabel="value"
                     itemValue="code" checkRender="true"
                     labelTextCode="holder.legalEntity.field.stateOfIncorporation"/>
    </span>
    <form:input path="stateOfIncorporation" id="selectedStateOfIncorporation" disabled="true" type="hidden"/>

    <component:input path="businessVatNumber" checkRender="true" labelTextCode="holder.legalEntity.field.businessVatNumber"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="holder.legalEntity.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true" labelTextCode="holder.legalEntity.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true" labelTextCode="holder.legalEntity.field.contactPerson"/>
    <component:input path="domicile" checkRender="true" labelTextCode="holder.legalEntity.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="holder.legalEntity.field.countryOfDomicile"
                     itemLabel="value" itemValue="code" checkRender="true" itemFilter="isApplicant"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="holder.legalEntity.field.consentForPublishingPersonalInfo"/>

    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isApplicant"/>
    </jsp:include>

    <component:generic path="correspondenceAddresses" checkRender="true">
        <label class="correspondence-address" for="holderHasCorrespondenceAddress">        
			<component:checkbox path="hasCorrespondenceAddress" checkRender="true"
                       labelTextCode="person.field.wantCorrespondenceAddress" id="holderHasCorrespondenceAddress"/>          
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isApplicant"/>
            <jsp:param name="size" value="${holderLegalEntityForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>
	<br>
    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

    <component:checkbox path="receiveCorrespondenceViaEmail" checkRender="true"
                       labelTextCode="holder.legalEntity.field.receiveCorrespondenceViaEmail"/>


    <ul class="controls">
        <li>
            <a class="cancelButton holder"><spring:message
                    code="holder.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addHolder addHolLegalEntity" type="button">
                <i class="add-icon"/>
                <spring:message code="holder.form.action.add.top"/>
            </button>
        </li>
    </ul>

</form:form>