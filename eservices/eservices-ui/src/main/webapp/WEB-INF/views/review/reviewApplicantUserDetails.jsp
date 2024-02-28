<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<c:set var="applicant" value="${userPersonDetails.applicants[0]}" />

<dl class="column">
	<dt><spring:message code="review.person.user.details.username.title"/></dt>
	<dd><sec:authentication property="principal.username"/></dd>
	
	<dt><spring:message code="review.person.user.details.legal.name.title"/></dt>
	<dd>
		<c:if test="${applicant ne null and applicant.name ne null}">
			<c:choose>
				<c:when test="${not empty applicant.name.organizationName}">
					<c:out value="${applicant.name.organizationName}" />
				</c:when>
				<c:when test="${not empty applicant.name.firstName}">
					<c:out value="${applicant.name.firstName}" />
					<c:if test="${not empty applicant.name.middleName}">
						<c:out value="${' ' + applicant.name.middleName}" />
					</c:if>
					<c:if test="${not empty applicant.name.lastName}">
						<c:out value="${' ' + applicant.name.lastName}" />
					</c:if>
				</c:when>	
			</c:choose>
		</c:if>
	</dd>
	
	<dt><spring:message code="review.person.user.details.country.title"/></dt>
	<dd>
		<c:if test="${not empty applicant.addresses}">
			<c:out value="${applicant.addresses[0].address.country}" />	
		</c:if>
	</dd>
	
	<dt><spring:message code="review.person.user.details.address.title"/></dt>
	<dd>
		<c:if test="${not empty applicant.addresses}">
			<c:out value="${applicant.addresses[0].address.street}" />
		</c:if>
	</dd>
</dl>

<dl class="column">
	<dt><spring:message code="review.person.user.details.mail.title"/></dt>
	<dd>
		<c:if test="${not empty applicant.emails}">
			<c:out value="${applicant.emails[0].emailAddress}"></c:out>
		</c:if>
	</dd>
	
	<dt><spring:message code="review.person.user.details.legal.form.title"/></dt>
	<dd><c:out value="${applicant.legalForm}" /></dd>
	
	<dt><spring:message code="review.person.user.details.type.title"/></dt>
	<dd><spring:message code="review.person.user.details.type.applicant.${applicant.kind.name()}"/></dd>
	
	<dt><spring:message code="review.person.user.details.telephone.title"/></dt>
	<dd>
		<c:if test="${not empty applicant.phones}">
			<c:out value="${applicant.phones[0].number}"></c:out>
		</c:if>	
	</dd>
</dl>	
		

