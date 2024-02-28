<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty flowBean.reference || flowBean.reference eq ''}">
	<div class="reference">
		<header>
			<spring:message code="review.reference.title"/>
			<c:if test="${!flowBean.readOnly}">
				<a class="edit navigateBtn" data-val="Update_Reference"><spring:message code="review.edit"/></a>
			</c:if>
		</header>
		<div>
			<table>
				<tbody>
				 	<tr>
						<td>
							<b><spring:message code="review.reference.title"/>:</b></td>
						<td>${flowBean.reference}</td>
					</tr>					
				</tbody>
			</table>
		</div>
	</div>
</c:if>