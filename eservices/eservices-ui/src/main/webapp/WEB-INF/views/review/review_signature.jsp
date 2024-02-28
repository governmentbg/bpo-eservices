<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="signature">
	<header>
		<spring:message code="review.signature"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Signature"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div>
		<c:choose>
			<c:when test="${empty flowBean.signatures}">
				<table>
					<tbody>
					<c:set value="${flowBean.mainForm.signatoryForm}" var="signature"/>
					<tr>
						<td><b><spring:message code="review.signature.name"/>:</b></td>
						<td>
							<spring:eval var="storename1" expression="flowBean.mainForm.signatoryForm.fullName"/>
							<c:choose>
								<c:when test="${storename1.length()>10}">
									<c:out value="${storename1.substring(0,10)}..."></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="${storename1}"></c:out>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><b><spring:message code="review.signature.position"/>:</b></td>
						<td>${flowBean.mainForm.signatoryForm.position}</td>
					</tr>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<table>
					<thead>
					<tr>
						<th><spring:message code="signature.details.table.header.number"/></th>
						<th><spring:message code="signature.details.table.header.signatoryName"/></th>
						<th><spring:message code="signature.details.table.header.personCapacity"/></th>
						<th><spring:message code="signature.details.table.header.associatedText"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="signature" items="${flowBean.signatures}" varStatus="status">
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<td>${signature.fullName }</td>
							<td><spring:message code="${signature.personCapacity.toString() }"/></td>
							<c:if test="${not empty signature.signatureAssociatedText}">
							<td>${signature.signatureAssociatedText.length() > 10 ? signature.signatureAssociatedText.substring(0,10) : signature.signatureAssociatedText}${signature.signatureAssociatedText.length() > 10 ? '...' : ''}</td>
							</c:if>
							<c:if test="${empty signature.signatureAssociatedText}">
								<td>-</td>
							</c:if>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>