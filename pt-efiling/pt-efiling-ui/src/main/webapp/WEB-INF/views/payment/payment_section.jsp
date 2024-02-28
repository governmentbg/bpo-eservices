<%@ page import="eu.ohim.sp.common.ui.form.payment.PayerKindForm" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/developers/payment.js"></script>

<section class="reference payment" id="paymentSection" >
	
	<header>
		<a href="#paymentSection" class="anchorLink"></a>
		<h2><spring:message code="payment.options"/></h2>
	</header>
	
	<form:form id="paymentForm" class="mainForm formEf fileUploadForm" modelAttribute="flowBean.paymentForm">
        
        <c:set var="sectionId" value="paymentSection" scope="request"/>

		<c:set var="payLaterEnabled" value="${configurationServiceDelegator.isServiceEnabled('Payment_Later')}" />
		<c:set var="payNowEnabled" value="${configurationServiceDelegator.isServiceEnabled('Payment_Now')}" />
		<c:set var="submitEnabled" value="${configurationServiceDelegator.isServiceEnabled('Submit')}" />		
		
		<c:choose>
	        <c:when test="${payLaterEnabled && !payNowEnabled}">
	            <input type="hidden" name="payLater" value="true" />
	            <input type="hidden" name="submitApplication" value="false" />
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.submit" />" />
	            <input type="hidden" id="buttonAction" value="doSubmit" />
				<div id="paymentLater">
					<jsp:include page="payment_later.jsp"/>
				</div>          
	        </c:when>
	        <c:when test="${!payLaterEnabled && payNowEnabled}">
	            <input type="hidden" name="payLater" value="false" />
	            <input type="hidden" name="submitApplication" value="false" />
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.accessPayment" />" />
	            <input type="hidden" id="buttonAction" value="doPayment" />
				
			    <div id="paymentOptions">
			        <jsp:include page="payment_options.jsp"/>
			    </div>	            
	        </c:when>
	        <c:when test="${payLaterEnabled && payNowEnabled}">
	            <input type="hidden" name="payLater" value="false" />	  
	            <input type="hidden" name="submitApplication" value="false" />      
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.accessPayment" />" />
	            <input type="hidden" id="buttonAction" value="doPayment" />
	            <input type="hidden" id="submitMessage" value="<spring:message code="layout.button.submit" />" />
	            <input type="hidden" id="payMessage" value="<spring:message code="layout.button.accessPayment" />" />
	           	<input type="hidden" id="payAction" value="doPayment" />
	           	<input type="hidden" id="submitAction" value="doSubmit" />
				<label>
	            	<spring:message code="payment.when.select" />	            
					<span class="requiredTag">*</span>
	            </label>
	            <form:select id="whenPayment" path="payLater" >
	            	<form:option value="false"><spring:message code="payment.when.now"/></form:option>
	            	<form:option value="true"><spring:message code="payment.when.later"/></form:option>	
	            </form:select>
				<div id="paymentLater" class="hidden">
					<jsp:include page="payment_later.jsp"/>
				</div>
			    <div id="paymentOptions">
			        <jsp:include page="payment_options.jsp"/>
			    </div>	            
	        </c:when>
	        <c:when test="${submitEnabled}">
	            <input type="hidden" name="submitApplication" value="true" />
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.submit" />" />
	            <input type="hidden" id="buttonAction" value="doSubmit" />
	        </c:when>		        	        
	    </c:choose>

    </form:form>
</section>