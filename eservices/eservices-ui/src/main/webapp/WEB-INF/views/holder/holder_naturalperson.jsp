<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:include page="/WEB-INF/views/holder/holder_choosetype.jsp"/>

<form:form cssClass="formEF" id="holderDetailsForm" modelAttribute="holderNaturalPersonForm">
    <c:set var="sectionId" value="holder_naturalperson" scope="request"/>
    <c:set var="imported" value="${holderNaturalPersonForm.imported}" scope="request"/>

    <form:hidden path="imported" id="importedHolder"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>

    <input type="hidden" class="htypehidden" value="${holderNaturalPersonForm.type}"/>
    
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
    <component:input path="title" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.title" formTagClass="address-small"/> 
    <component:input path="firstName" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                     labelTextCode="holder.naturalPerson.field.middleName"/>
    <div class="tip holderNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
    <component:input path="surname" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                     itemValue="code" itemFilter="isApplicant" checkRender="true"
                     labelTextCode="holder.naturalPerson.field.nationality"/>
    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"

                    labelTextCode="holder.naturalPerson.field.taxIdNumber"/>
    <component:input path="contactPerson" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.contactPerson"/>
    <component:input path="domicile" checkRender="true"
                    labelTextCode="holder.naturalPerson.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="domicileCountry"
                     labelTextCode="holder.naturalPerson.field.countryOfDomicile" formTagClass="span3"
                     itemLabel="value" itemValue="code" checkRender="true"
                     itemFilter="isApplicant"/>

    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                       labelTextCode="holder.naturalPerson.field.consentForPublishingPersonalInfo"/>

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
            <jsp:param name="size" value="${holderNaturalPersonForm.correspondenceAddresses.size()}"/>
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
            <button class="addHolder addHolNaturalPerson" type="button">
                <i class="add-icon"/>
                <spring:message code="holder.form.action.add.top"/>
            </button>
        </li>
    </ul>
</form:form>