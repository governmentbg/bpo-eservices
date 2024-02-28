<%-- <script type="text/javascript"	src="<%=request.getContextPath()%>/resources/scripts/jquery-1.7.1.min.js"></script> --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<script type='text/javascript'
        src="<%=request.getContextPath()%>/resources/scripts/developers/payment.js"></script>

<c:if test="${configurationServiceDelegator.isServiceEnabled('Payment_Enabled')}">
<section class="reference payment">

	<c:set var="sectionId" value="paymentSection" scope="request"/>
	<component:generic path="title" checkRender="true">
		<header>
			<h2><spring:message code="payment.options"/></h2>
			<a id="paymentSection" class="anchorLink"></a>
		</header>
	</component:generic>

	<form:form id="paymentForm" class="mainForm formEf" modelAttribute="flowBean.paymentForm">
		<c:set var="payLaterEnabled"
				value="${configurationServiceDelegator.isServiceEnabled('Payment_Later')}" />
		<c:set var="payNowEnabled"
	 			value="${configurationServiceDelegator.isServiceEnabled('Payment_Now')}" />
				
		<c:choose>
	        <c:when test="${payLaterEnabled && !payNowEnabled}">
	            <c:set var="paymentLaterContainerClass" value=""/>
	            <c:set var="paymentNowContainerClass" value="hidden"/>
	            <input type="hidden" name="paymentForm.payLater" value="true" />
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.submit.${flowModeId}" />" />
	            <input type="hidden" id="buttonAction" value="doSubmit" />
	        </c:when>
	        <c:when test="${!payLaterEnabled && payNowEnabled}">
	            <c:set var="paymentLaterContainerClass" value="hidden"/>
	            <c:set var="paymentNowContainerClass" value=""/>
	            <input type="hidden" name="paymentForm.payLater" value="false" />
	            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.accessPayment" />" />
	            <input type="hidden" id="buttonAction" value="doPayment" />
	        </c:when>
	        <c:otherwise>
	        	<input type="hidden" id="payMessage" value="<spring:message code="layout.button.accessPayment" />" />
				<input type="hidden" id="submitMessage" value="<spring:message code="layout.button.submit.${flowModeId}" />" />
				<input type="hidden" id="payAction" value="doPayment" />
				<input type="hidden" id="submitAction" value="doSubmit" />
	            <c:set var="paymentLaterContainerClass" value="hidden"/>
	            <c:set var="paymentNowContainerClass" value=""/>
	            <label>
	            	<spring:message code="payment.when.select" />	            
	                <span class="requiredTag">*</span>
	            </label>
	            <form:select id="whenPayment" path="payLater" >
	            	<form:option value="false"><spring:message code="payment.when.now"/></form:option>
	            	<form:option value="true"><spring:message code="payment.when.later"/></form:option>	
	            </form:select>
	        </c:otherwise>
	    </c:choose>
	            
		<div id="paymentLater" class="${paymentLaterContainerClass}">
			<jsp:include page="payment_later.jsp"/>
		</div>
		
	    <div id="paymentOptions" class="${paymentNowContainerClass}">
	        <jsp:include page="payment_options.jsp"/>
	    </div>
    </form:form>
</section>
</c:if>
<!--paymentOptions-->