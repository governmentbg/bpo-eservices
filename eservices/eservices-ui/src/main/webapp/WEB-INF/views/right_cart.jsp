<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form:form modelAttribute="flowBean" cssClass="mainForm" method="POST">
	<spring:nestedPath path="feesForm">
		<c:set var="general_currency_symbol"
			   value="${configurationServiceDelegator.getValueFromGeneralComponent('currency.symbol')}"/>
		<c:set var="showOthers" value=""/>
		<ul class="aside-fees">
			
			<c:forEach var="details" items="${formUtil.getMainType() == 'TM'? flowBean.tmsList : flowBean.dssList}">
				<c:set var="showMark" value="false"/>
				<c:if test="${formUtil.getMainType() == 'TM'}">
					<c:set var="actualAppNumber" value="${details.applicationNumber}"/>
					<c:if test="${actualAppNumber eq '' || empty actualAppNumber}">
						<c:set var="actualAppNumber" value="${details.registrationNumber}"/>	
					</c:if>
				</c:if>
				<c:if test="${formUtil.getMainType() == 'DS'}">
					<c:set var="actualAppNumber" value="${details.designIdentifier}"/>
					<c:if test="${actualAppNumber eq '' || empty actualAppNumber}">
						<c:set var="actualAppNumber" value="${details.registrationNumber}"/>	
					</c:if>
				</c:if>
				
				
				<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
					<c:if test="${actualAppNumber eq feeLine.status}">
						<c:set var="showMark" value="true"/>		
					</c:if>
				</c:forEach>
				<c:if test="${showMark eq 'true'}">
					<li id="fee-item" class="fee-item collapse" style="background: #F0F0F0;">
						<span class="fee-description" style="font-weight: bold">${actualAppNumber}</span>
					</li>
					<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
						<c:if test="${actualAppNumber eq feeLine.status}">
							<li id="fee-item" class="fee-item collapse">
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
				<%--<li id="fee-item" class="fee-item collapse" style="background: #F0F0F0;">--%>
					<%--<span class="fee-description" style="font-weight: bold"><spring:message code="fees.others"></spring:message> </span>--%>
				<%--</li>--%>
			
			<c:forEach var="feeLine" items="${flowBean.feesForm.feeLineFormList}">
					<c:if test="${feeLine.status eq '' || empty feeLine.status}">
						<li id="fee-item" class="fee-item collapse" style="background: #F0F0F0;">
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
