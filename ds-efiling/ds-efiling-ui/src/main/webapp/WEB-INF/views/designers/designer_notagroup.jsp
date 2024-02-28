<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="designerNotAGroup" cssClass="formEF" modelAttribute="designerForm">

	<jsp:include page="designer_sharedCodeTopButtons.jsp" />
	
	<jsp:include page="designer_sharedCodeTop.jsp" /> 

	<jsp:include page="designer_sharedCodeWaiverCheck.jsp" />

	<c:if test="${formImportReset}">
		<div class="alert alert-danger" style="margin-top: 10px">
			<spring:message code="person.imported.reset"/>
		</div>
	</c:if>
	
	<div id="designer_notWaive">
		<jsp:include page="designer_sharedCodeBelongsCheck.jsp" />
	
		<div id="designer_notAGroup">
			<c:set var="sectionId" value="designer_notAGroup" scope="request" />
			
			<component:input path="firstName" checkRender="true" labelTextCode="designer.field.firstName" />
			<component:input path="middleName" checkRender="true" labelTextCode="designer.field.middleName" />
			<div class="tip designerNaturalPersonTip" style="color:red; display:none"><spring:message code="person.naturalPerson.form.middleName.tip"/></div>
		    <component:input path="surname" checkRender="true" labelTextCode="designer.field.surname" />
			<component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
							  itemValue="code" itemFilter="isApplicant" checkRender="true"
							  labelTextCode="designer.field.nationality"/>
		    
		    <component:input path="contactPerson" checkRender="true" labelTextCode="designer.field.contactPerson"/>
		    
			
			<jsp:include page="/WEB-INF/views/partials/address.jsp">
        		<jsp:param name="path" value="address"/>
        		<jsp:param name="itemFilterValue" value=""/>
                <jsp:param name="streetFieldMessageCode" value="person.address.field.address"/>
    		</jsp:include>
    		
    		
    		<component:generic path="correspondenceAddresses" checkRender="true">
        		<label class="correspondence-address" for="designerHasCorrespondenceAddress">
            		<input id="designerHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            		<spring:message code="person.field.wantCorrespondenceAddress"/>
        		</label>
        		<jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            		<jsp:param name="path" value="correspondenceAddresses"/>
            		<jsp:param name="itemFilterValue" value=""/>
            		<jsp:param name="size" value="${designerForm.correspondenceAddresses.size()}"/>
        		</jsp:include>
    		</component:generic>


    		<jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

		</div>
	</div>
	
	<c:set var="sectionId" value="designers" scope="request"/>

	<div id="designerDesignsLinkedSection">
		<tiles:insertDefinition name="designs_associate_to">
			<tiles:putAttribute name="containsDesignsLinkForm" value="${designerForm}" />
			<tiles:putAttribute name="partialId" value="Designer" />
		</tiles:insertDefinition>
	</div>

	<jsp:include page="designer_sharedCodeBottom.jsp" />
		
</form:form>
