<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="termsAndConditions" id="termsAndConditions">
	
	<c:set var="sectionId" value="termsAndConditions" scope="request" />
	
	<header>
		<a href="#termsAndConditions" class="anchorLink"></a>
		
	    <h2>
	        <spring:message code="termsAndConditions.title"/>
	    </h2>
	</header>

	<form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
		<component:checkbox path="termsAndConditions" checkRender="true" 
					labelTextCode="termsAndConditions.form.field.termsAndConditions" id="termsAndConditionsCheck"/>

		<a href="#termsAndConditionsModal" id="termsAndConditionsLink" data-toggle="modal">
    		<spring:message code="Show terms and conditions"/>
    	</a>
    		
	</form:form>

</section>

<jsp:include page="termsAndConditions_modal.jsp" />

