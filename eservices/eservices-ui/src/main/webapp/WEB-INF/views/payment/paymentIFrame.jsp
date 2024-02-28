<%@page import="java.util.Map" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/jquery-migrate-1.4.1.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/developers/payment.js"></script>
<html>
<body>
<form method="post" id="paymentPopupForm">
	<c:if test="${not empty _csrf.parameterName}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</c:if>
</form>
<input type="hidden" id="usePopup" value="${configurationServiceDelegator.isServiceEnabled('Payment_Popup')}" />

</body>
<script type="text/javascript">
    //Get URL and data based on parameters
    var url = '<%= request.getAttribute("urlPayment") %>';
    if($("#usePopup").val() == "true") {
    	var submitForm = $("#paymentPopupForm");
    } else {
    	var submitForm = $('#paymentRedirectionForm', window.parent.document);
    }
    submitForm.removeData();
    submitForm.attr("action", url);

    //For each parameter in the list of parameters to be sent, add a hidden input into the form that will be posted
    <%
             Map<String, String> params = (Map<String, String>) request.getAttribute("paramsPayment");
             for(String key : params.keySet()) {
                 String value = params.get(key);
    %>
    			submitForm.append($($("<input/>").attr("type", "hidden").attr("name", "<%= key %>").val('<%= value %>')));
    <%
             }
    %>
    
    submitForm.submit();

</script>
</html>
