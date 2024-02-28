<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="security_measure">
	<header>
		<spring:message code="review.security.measure"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Measure"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<c:if test="${not empty flowBean.securityMeasure}">		
		<table>
			<tbody>	
				<c:if test="${flowBean.securityMeasure.forbidUse }">				
				 <tr>						
					<td><spring:message code="${flowModeId}.security.measure.forbid.use"/></td>
				</tr>
				</c:if>
				<c:if test="${flowBean.securityMeasure.forbidManage }">	
				<tr>						
					<td><spring:message code="${flowModeId}.security.measure.forbid.manage"/></td>
				</tr>
				</c:if>
			</tbody>
		</table>		
	</c:if>
</div>