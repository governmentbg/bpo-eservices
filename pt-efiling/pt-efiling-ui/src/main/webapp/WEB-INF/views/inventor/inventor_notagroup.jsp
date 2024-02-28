<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="inventorNotAGroup" cssClass="formEF" modelAttribute="inventorForm">

	<jsp:include page="inventor_sharedCodeTopButtons.jsp" />
	
	<jsp:include page="inventor_sharedCodeTop.jsp" />

	<jsp:include page="inventor_sharedCodeWaiverCheck.jsp" />

	<c:if test="${formImportReset}">
		<div class="alert alert-danger" style="margin-top: 10px">
			<spring:message code="person.imported.reset"/>
		</div>
	</c:if>
	
	<div id="inventor_notWaive">
		<jsp:include page="inventor_sharedCodeBelongsCheck.jsp" />
	
		<div id="inventor_notAGroup">
			<c:set var="sectionId" value="inventor_notAGroup" scope="request" />

			<form:hidden path="importedFromApplicant"/>

			<component:input path="firstName" checkRender="true" labelTextCode="inventor.field.firstName" />
			<component:input path="middleName" checkRender="true" labelTextCode="inventor.field.middleName" />
			<div class="tip inventorNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
		    <component:input path="surname" checkRender="true" labelTextCode="inventor.field.surname" />
			<component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
							  itemValue="code" itemFilter="isApplicant" checkRender="true"
							  labelTextCode="inventor.field.nationality"/>
		    
		    <component:input path="contactPerson" checkRender="true" labelTextCode="inventor.field.contactPerson"/>


			<jsp:include page="/WEB-INF/views/partials/address.jsp">
        		<jsp:param name="path" value="address"/>
        		<jsp:param name="itemFilterValue" value=""/>
                <jsp:param name="streetFieldMessageCode" value="person.address.field.address"/>
    		</jsp:include>
    		
    		
    		<component:generic path="correspondenceAddresses" checkRender="true">
        		<label class="correspondence-address" for="inventorHasCorrespondenceAddress">
            		<input id="inventorHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            		<spring:message code="person.field.wantCorrespondenceAddress"/>
        		</label>
        		<jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            		<jsp:param name="path" value="correspondenceAddresses"/>
            		<jsp:param name="itemFilterValue" value=""/>
            		<jsp:param name="size" value="${inventorForm.correspondenceAddresses.size()}"/>
        		</jsp:include>
    		</component:generic>


    		<jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

		</div>
	</div>
	
	<c:set var="sectionId" value="inventor" scope="request"/>

	<jsp:include page="inventor_sharedCodeBottom.jsp" />
		
</form:form>
