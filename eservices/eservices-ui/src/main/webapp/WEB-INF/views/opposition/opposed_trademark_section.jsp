<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">

    
    
	<form:form id="opposedTradeMarkForm" cssClass="formEF" modelAttribute="opposedTradeMarkForm">	

		<c:set var="sectionId" value="opposed_trademark_section" scope="request"/>
		
		<form:hidden id="hiddenFormId" path="id"/>
		<component:input path="applicationNumber" checkRender="true" labelTextCode="tm_opposed.details.field.applicationNumber"/>
		<component:input path="applicationRepresentation" checkRender="true" labelTextCode="tm_opposed.details.applicationRepresentation.representation"/>
	    <component:input path="applicationDate" checkRender="true" labelTextCode="tm_opposed.details.field.applicationDate" formTagClass="field-date"/>
	    <component:input path="publicationDate" checkRender="true" labelTextCode="tm_opposed.details.field.publicationDate" formTagClass="field-date"/>
	    <component:input path="applicationStatus" checkRender="true" labelTextCode="tm_opposed.details.field.applicationStatus"/>
	    <component:input path="tradeMarkType" checkRender="true" labelTextCode="tm_opposed.details.field.tradeMarkType"/>
	    <component:input path="tradeMarkName" checkRender="true" labelTextCode="tm_opposed.details.field.tradeMarkName"/>
	    <component:input path="markDisclaimer" checkRender="true" labelTextCode="tm_opposed.details.field.markDisclaimer"/>
	  
	    
		<fieldset class="opposedCss">
	    <legend><spring:message code="tm_opposed.details.opposedTradeMarkApplicant.field.applicantLegend"/></legend>
	   
	    <component:input path="opposedTradeMarkApplicant.name" checkRender="true" labelTextCode="tm_opposed.details.opposedTradeMarkApplicant.field.applicantName"/>
	    <jsp:include page="/WEB-INF/views/partials/address.jsp">
	        <jsp:param name="path" value="opposedTradeMarkApplicant.address"/>
	        <jsp:param name="itemFilterValue" value="isApplicant"/>
    	</jsp:include>
    	</fieldset>
    	
    	<fieldset class="opposedCss">
    	<legend><spring:message code="tm_opposed.details.opposedTradeMarkRepresentative.field.representativeLegend"/></legend>
	    <component:input path="opposedTradeMarkRepresentative.name" checkRender="true" labelTextCode="tm_opposed.details.opposedTradeMarkRepresentative.field.representativeName"/>
	    <jsp:include page="/WEB-INF/views/partials/address.jsp">
	        <jsp:param name="path" value="opposedTradeMarkRepresentative.address"/>
	        <jsp:param name="itemFilterValue" value="isRepresentative"/>
    	</jsp:include>
	    </fieldset>
	    
	    
	    <ul class="controls">
	        <li>
	            <a class="cancelButton opposedTradeMark"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	            <button class="addOpposedTradeMark addOpposedTM" type="button">
	                <i class="add-icon"/>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	        </li>
	    </ul>
    </form:form>                
</section>