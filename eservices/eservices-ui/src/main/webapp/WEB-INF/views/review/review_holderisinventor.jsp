<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="attachments">
	<c:if test="${not empty flowBean.holderIsInventor}">
		<header>
			<spring:message code="review.holder.is.inventor.title"/>
		</header>
		<table>
			<tbody>
			<tr>
				<td>

					<spring:eval var="holderIsInventor" expression="flowBean.holderIsInventor"/>
					<c:choose>
						<c:when test="${holderIsInventor == true && (flowModeId eq 'pt-change' || flowModeId eq 'um-change' || flowModeId eq 'ep-change')}">
							<spring:message code="review.holder.is.inventor"/>
						</c:when>
						<c:when test="${holderIsInventor == true && (flowModeId eq 'ds-change' || flowModeId eq 'sv-change')}">
							<spring:message code="review.holder.is.author"/>
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
