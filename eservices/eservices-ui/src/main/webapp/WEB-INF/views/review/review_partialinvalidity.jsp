<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="attachments">
	<c:if test="${not empty flowBean.partialInvalidity}">
		<header>
			<spring:message code="review.partial.invalidity.title.${flowModeId}"/>
		</header>
		<table>
			<tbody>
			<tr>
				<td>

					<spring:eval var="partialInvalidity" expression="flowBean.partialInvalidity"/>
					<c:choose>
						<c:when test="${partialInvalidity == true}">
							<spring:message code="review.partial.invalidity.${flowModeId}"/>
						</c:when>
						<c:when test="${partialInvalidity == false}">
							<spring:message code="review.full.invalidity.${flowModeId}"/>
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</tbody>
		</table>
	</c:if>
</div>
