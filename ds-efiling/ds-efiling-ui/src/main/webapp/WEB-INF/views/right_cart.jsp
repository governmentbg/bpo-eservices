<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form:form modelAttribute="flowBean" cssClass="mainForm" method="POST">
	<c:forEach var="warning" items="${flowBean.warningsMessage}">
		<c:if test="${warning eq 'NonBlockingMessage.Split.Application'}">
			<script>showMessageModal($("#showDesignWarning").html()); </script>
		</c:if>
	</c:forEach>

	<spring:nestedPath path="feesForm">
		<c:set var="general_currency_symbol"
			   value="${configurationServiceDelegator.getValueFromGeneralComponent('currency.symbol')}"/>
		<ul class="aside-fees">
		
			<c:forEach var="dsDetails" items="${flowBean.designs}">
				
				<c:set var="showDesign" value="false"/>
				<c:set var="actualDsNumber" value="${dsDetails.number}"/>				
				
				<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
					<c:if test="${actualDsNumber eq feeLine.status}">
						<c:set var="showDesign" value="true"/>		
					</c:if>
				</c:forEach>
				
				<c:if test="${showDesign eq 'true'}">
					<li id="fee-item" class="fee-item collapse" style="background: #F0F0F0;">
						<span class="fee-description" style="font-weight: bold"><spring:message code="fees.design"></spring:message> ${actualDsNumber}</span>
					</li>
					<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
						<c:if test="${actualDsNumber eq feeLine.status}">
							<li class="fee-item collapse">
								<span class="fee-description"><spring:message code="layout.cart.${feeLine.nameKey}" /></span>
								<span class="fee-value"><fmt:formatNumber value="${feeLine.amount}" maxFractionDigits="2"/>&nbsp;${general_currency_symbol}</span>
							</li>
						</c:if>
					</c:forEach>										
				</c:if>		

			</c:forEach>
			
			<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
					<c:if test="${feeLine.status eq '' || empty feeLine.status}">
						<c:set var="showOthers" value="true"/>
					</c:if>
			</c:forEach>
			<c:if test="${showOthers eq 'true'}">
				<li id="fee-item" class="fee-item collapse" style="background: #F0F0F0;">
					<span class="fee-description" style="font-weight: bold"><spring:message code="fees.others"></spring:message> </span>
				</li>
			
				<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
						<c:if test="${feeLine.status eq '' || empty feeLine.status}">
							<li class="fee-item collapse">
								<span class="fee-description"><spring:message code="layout.cart.${feeLine.nameKey}" /></span>
								<span class="fee-value"><fmt:formatNumber value="${feeLine.amount}" maxFractionDigits="2"/>&nbsp;${general_currency_symbol}</span>
							</li>
						</c:if>
				</c:forEach>
			</c:if>
			
			<li id="fee-items" class="fee-item-total collapsed" data-toggle="collapse" data-target=".fee-item" href="#fee-item">
				<span class="fee-description-total"><spring:message code="layout.cart.totalFees" /></span>
				<span class="fee-value fee-total"><fmt:formatNumber value="${flowBean.feesForm.totalAmount}" maxFractionDigits="2"/>&nbsp;${general_currency_symbol}</span>
			</li>	
			
		</ul>
	</spring:nestedPath>
	
</form:form>