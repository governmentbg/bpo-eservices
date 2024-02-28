<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/developers/payment.js"></script>

<div class="modal paymentModal fade" id="paymentContainer" data-backdrop="static">
<div class="modal-dialog">
<div class="modal-content">
	<div class="modal-header">
        <button id="closePaymentPopup" type="button" class="close" >x</button>        
    </div>
    
    <c:set var="payNowEnabled" value="${configurationServiceDelegator.isServiceEnabled('Payment_Now')}" />
	 			
    <c:choose>
        <c:when test="${flowBean.paymentForm.payLater || !payNowEnabled}">            
            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.submit" />" />
            <input type="hidden" id="buttonAction" value="doSubmit" />
        </c:when>
        <c:otherwise>            
            <input type="hidden" id="buttonMessage" value="<spring:message code="layout.button.accessPayment" />" />
            <input type="hidden" id="buttonAction" value="doPayment" />
        </c:otherwise>
    </c:choose>
	        
    <input type="hidden" id="whenPaymentSelected" value="${flowBean.paymentForm.payLater}" />

	<input type="hidden" id="flowKey" value="flowKey=${empty param.execution?param.flowKey:param.execution}" />
	<input type="hidden" id="flowMode" value="flowMode=${flowModeId}" />
	<input type="hidden" id="doPayment" value="${doPayment}" />
	<input type="hidden" id="paymentMessage" value="${paymentMessage}" />
	<input type="hidden" id="paymentLabel" value="<spring:message code='general.errorBox.payment'/>" />
	<input type="hidden" id="errorTitle" value="<spring:message code="general.errorBox.header" />" />
	<input type="hidden" id="closeTitle" value="<spring:message code="general.errorBox.closeBox" />" />
	
    <iframe id="paymentIFrame" height="570" width="1025"></iframe>    
    <form method="post" id="paymentRedirectionForm">
        <c:if test="${not empty _csrf.parameterName}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </c:if>
	</form>
</div>
</div>
</div>
