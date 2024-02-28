<%@page import="java.util.List"%>
<%@page import="eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/jquery-migrate-1.4.1.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/developers/payment.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/common.js"></script>
<html>
	<input type="hidden" id="usePopup" value="${configurationServiceDelegator.isServiceEnabled('Payment_Popup')}" />	
	<input type="hidden" id="messageInput" value="<spring:message code='<%= (String) request.getAttribute("message") %>'/>" />
	<input type="hidden" id="paymentLabel" value="<spring:message code='general.errorBox.payment'/>" />
	<input type="hidden" id="errorTitle" value="<spring:message code="general.errorBox.header" />" />
	<input type="hidden" id="closeTitle" value="<spring:message code="general.errorBox.closeBox" />" />
	<input type="hidden" id="initialError" value="<%= (Boolean) request.getAttribute("initialError") %>" />
	
	<!-- FORM TO DO THE REDIRECTION IN THE SAME WINDOW -->
	<form:form id="sameWindowForm" action="${param.flowMode}.htm?execution=${param.flowKey}">
		<input type="hidden" name="_eventId" value='<%= request.getAttribute("resultStatus") %>' />
		<input type="hidden" name="paymentMessage" value='<%= (String) request.getAttribute("message") %>' />
	</form:form>
	
	<script type="text/javascript">
		if($("#usePopup").val() == "true" || $("#initialError").val() == "true" ) {
		    var resultStatus = '<%= request.getAttribute("resultStatus") %>';
		    if (resultStatus == '<%= PaymentStatus.PAYMENT_OK.name() %>')
		    {
		        window.parent.submitMainForm(resultStatus);
		    } else if (resultStatus == '<%= PaymentStatus.PAYMENT_CANCELLED.name() %>')
		    {
		        window.parent.removeMessageError();
		        window.parent.closePopup();
		    } else
		    {
		    	window.parent.setMessageError($("#messageInput").val(), $("#paymentLabel").val(), $("#errorTitle").val(), $("#closeTitle").val());	    		    	
		        window.parent.closePopup();
		    }
		} else {
			$("#sameWindowForm").submit();
		}
	</script>
</html>
