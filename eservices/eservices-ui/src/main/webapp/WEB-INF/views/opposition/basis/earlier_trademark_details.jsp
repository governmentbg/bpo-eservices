<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">
	<c:set var="sectionId" value="sec_${earlierEntitleMent}_earlier_trademark_details" scope="request"/>

	<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails" checkRender="true">
		<c:set var="earlierEntitlementRight" value="${earlierEntitleMent}"/>
		<c:if test="${!empty param.earlierEntitleMent}">
			<c:set var="sectionId" value="sec_${param.earlierEntitleMent}_earlier_trademark_details" scope="request"/>
			<c:set var="earlierEntitlementRight" value="${param.earlierEntitleMent}"/>
		</c:if>

		<c:set var="imported" value="false"/>
		<c:choose>
			<c:when test="${formEdit}">
				<spring:nestedPath path="oppositionBasisForm">
					<c:set var="imported" value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported}" scope="request"/>
					<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details_fields.jsp">
						<jsp:param value="${earlierEntitlementRight}" name="earlierEntitlementRight"/>
					</jsp:include>
				</spring:nestedPath>
			</c:when>
			<c:when test="${empty param.notImportEarlier}">
				<spring:nestedPath path="oppositionBasisForm">
					<c:set var="imported" value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported}" scope="request"/>
					<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details_fields.jsp">
						<jsp:param value="${earlierEntitlementRight}" name="earlierEntitlementRight"/>
					</jsp:include>
				</spring:nestedPath>
			</c:when>
			<c:when test="${param.notImportEarlier eq 'true'}">
				<c:set var="imported" value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported}" scope="request"/>
				<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details_fields.jsp">
					<jsp:param value="${earlierEntitlementRight}" name="earlierEntitlementRight"/>
				</jsp:include>
			</c:when>
			<c:when test="${param.notImportEarlier eq 'false'}">
				<c:set var="imported" value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported}" scope="request"/>
				<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details_fields.jsp">
					<jsp:param value="${earlierEntitlementRight}" name="earlierEntitlementRight"/>
				</jsp:include>
			</c:when>

		</c:choose>

		<c:set var="sectionId" value="sec_${earlierEntitleMent}" scope="request"/>
		<c:if test="${!empty param.earlierEntitleMent}">
			<c:set var="sectionId" value="sec_${param.earlierEntitleMent}" scope="request"/>
		</c:if>
	</component:generic>
</section>