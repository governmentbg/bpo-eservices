<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form:form modelAttribute="flowBean" cssClass="mainForm" method="POST">
	<spring:nestedPath path="feesForm">
		<c:set var="general_currency_symbol"
			   value="${configurationServiceDelegator.getValueFromGeneralComponent('currency.symbol')}"/>
		<ul class="aside-fees">
			<li id="fee-items" class="fee-item-total collapsed" data-toggle="collapse" data-target=".fee-item" href="#fee-item">
				<span class="fee-description-total"><spring:message code="layout.cart.totalFees" /></span>
				<span class="fee-value fee-total"><fmt:formatNumber value="${flowBean.feesForm.totalAmount}" maxFractionDigits="2"/>&nbsp;${general_currency_symbol}</span>
				
				<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
	            	<li id="fee-item" class="fee-item collapse">
	            		<span class="fee-description"><spring:message code="layout.cart.${feeLine.nameKey}" /></span>
	                    <span class="fee-value"><fmt:formatNumber value="${feeLine.amount}" maxFractionDigits="2"/>&nbsp;${general_currency_symbol}</span>
	            	</li>
	            </c:forEach>
			</li>
		</ul>
	</spring:nestedPath>
</form:form>

	<script type="text/javascript">
	// Number of classes included in the basic/collective fee
	var feeClassesIncluded = [];

	$(function(){feesCollapse()});
	</script>