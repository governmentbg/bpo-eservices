<html>
	<body style="background-color: white">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
	<script type='text/javascript' src="<%=request.getContextPath()%>/resources/scripts/jquery-migrate-1.4.1.js"></script>
	
		<form action="" id="formId" method="post">
			<img src="<%=request.getContextPath()%>/resources/images/copato_mock_environment.png" />
			<input type="button" value="TEST CANCEL PAYMENT" onclick="javascript:cancelPayment()">
			<input type="button" value="TEST SUBMIT OK PAYMENT" onclick="javascript:okPayment()">
			<input type="button" value="TEST SUBMIT ERROR PAYMENT" onclick="javascript:errorPayment()">
			<c:if test="${not empty _csrf.parameterName}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</c:if>
		</form>
	</body>
	
	<script type="text/javascript">
		function cancelPayment() {
			callback('<%=request.getContextPath()%>/paymentCallback.htm', 'cancelled', 'THE PAYMENT WAS CANCELLED');
		}
		
		function okPayment() {
			callback('<%=request.getContextPath()%>/paymentCallback.htm', 'ok', 'THE PAYMENT WAS OK');
		}
		
		function errorPayment() {
			callback('<%=request.getContextPath()%>/paymentCallback.htm', 'error', 'THERE WAS AN ERROR');
		}
		
		function callback(url, resultOK, message) {
			$("#formId").removeData();
			$("#formId").attr("action", url);
			
			var resultStatusParam = $("<input/>").attr("type", "hidden").attr("name", "resultStatus").val(resultOK);
			$('#formId').append($(resultStatusParam));
			
			var messageParam = $("<input/>").attr("type", "hidden").attr("name", "message").val(message);
			$('#formId').append($(messageParam));
			
			$("#formId").submit();
		}
	</script>
</html>
