<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="security_measure">
	<header>
		<spring:message code="review.market.permission"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_MarketPermission"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<c:if test="${not empty flowBean.marketPermission}">
		<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
		<table>
			<tbody>
				<c:if test="${flowBean.marketPermission.firstPermissionBGNumber != null && flowBean.marketPermission.firstPermissionBGDate != null}">
					 <tr>
						<td><spring:message code="market.permission.firstPermissionBGLabel"/></td>
					</tr>
					<tr>
						<td>
							<div style="width: 50%; display: inline-block"><spring:message code="market.permission.firstPermissionBGNumber"/>: ${flowBean.marketPermission.firstPermissionBGNumber}</div>
							<div style="width: 49%; display: inline-block"><spring:message code="market.permission.firstPermissionBGDate"/>: <fmt:formatDate type="date" pattern="${dateFormat}" value="${flowBean.marketPermission.firstPermissionBGDate}"/></div>
						</td>
					</tr>
				</c:if>
				<c:if test="${flowBean.marketPermission.firstPermissionEUNumber != null && flowBean.marketPermission.firstPermissionEUDate != null}">
					<tr>
						<td><spring:message code="market.permission.firstPermissionEULabel"/></td>
					</tr>
					<tr>
						<td>
							<div style="width: 50%; display: inline-block"><spring:message code="market.permission.firstPermissionEUNumber"/>: ${flowBean.marketPermission.firstPermissionEUNumber}</div>
							<div style="width: 49%; display: inline-block"><spring:message code="market.permission.firstPermissionEUDate"/>: <fmt:formatDate type="date" pattern="${dateFormat}" value="${flowBean.marketPermission.firstPermissionEUDate}"/></div>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
</div>