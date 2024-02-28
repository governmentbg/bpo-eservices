<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />
<div class="your-details">
	<header>
		<spring:message code="review.yourDetails" />
		<a class="edit navigateBtn" data-val="Update_Person"><spring:message
				code="review.update" /></a>
	</header>
	<div>

		<c:if test="${not empty flowBean.representatives}">
			<table>
				<caption>
					<spring:message code="review.person.representative.info.title" />
				</caption>
				<thead>
					<tr>
						<th><spring:message
								code="review.person.representative.table.column.number.title" /></th>
						<th><spring:message
								code="review.person.representative.table.column.id.title" /></th>
						<%--<th><spring:message--%>
								<%--code="review.person.representative.table.column.organization.title" /></th>--%>
						<th><spring:message
								code="review.person.representative.table.column.type.title" /></th>
						<th><spring:message
								code="review.person.representative.table.column.name.title" /></th>
						<th><spring:message
								code="review.person.representative.table.column.country.title" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="index" items="${flowBean.representatives}"
						varStatus="status">
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<td><c:out value="${index.id}"></c:out></td>
							<td>
								<c:choose>
									<c:when test="${index.type eq 'NATURAL_PERSON'}">
										<spring:message code="representative.naturalPerson.type"/>
									</c:when>
									<c:when test="${index.type eq 'ASSOCIATION'}">
										<spring:message code="representative.association.type"/>
									</c:when>
									<c:when test="${index.type eq 'LEGAL_PRACTITIONER'}">
										<spring:message code="representative.legalPractitioner.type"/>
									</c:when>
									<c:when test="${index.type eq 'LEGAL_ENTITY'}">
										<spring:message code="representative.legalEntity.type"/>
									</c:when>
									<c:when test="${index.type eq 'LAWYER_COMPANY'}">
										<spring:message code="representative.lawyercompany.type"/>
									</c:when>
									<c:when test="${index.type eq 'LAWYER_ASSOCIATION'}">
										<spring:message code="representative.lawyerassociation.type"/>
									</c:when>
									<c:when test="${index.type eq 'TEMPORARY_REPRESENTATIVE'}">
										<spring:message code="representative.temporary.type"/>
									</c:when>
								</c:choose>
							</td>
							<td><spring:eval var="storename" expression="index.name" />
								<c:choose>
									<c:when test="${storename.length()>40}">
										<c:out value="${storename.substring(0,40)}..."></c:out>
									</c:when>
									<c:otherwise>
										<c:out value="${index.name}"></c:out>
									</c:otherwise>
								</c:choose></td>
							<td><c:if test="${index.address!=null}">
									<c:out value="${index.address.country}"></c:out>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<c:if test="${not empty flowBean.applicants}">
			<table>
				<caption>
					<spring:message code="review.person.applicant.info.title" />
				</caption>
				<thead>
					<tr>
						<th><spring:message
								code="review.person.aplicant.table.column.number.title" /></th>
						<th><spring:message
								code="review.person.aplicant.table.column.id.title" /></th>
						<th><spring:message
								code="review.person.aplicant.table.column.type.title" /></th>
						<th><spring:message
								code="review.person.aplicant.table.column.name.title" /></th>
						<th><spring:message
								code="review.person.aplicant.table.column.country.title" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="index" items="${flowBean.applicants}"
						varStatus="status">
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<td><c:if test="${index.imported}"><c:out value="${index.id}"></c:out></c:if></td>
							<td><c:choose>
									<c:when test="${index.type eq 'NATURAL_PERSON'}">
										<spring:message code="applicant.naturalPerson.type" />
									</c:when>
									<c:when test="${index.type eq 'LEGAL_ENTITY'}">
										<spring:message code="applicant.legalEntity.type" />
									</c:when>
									<c:when test="${index.type eq 'NATURAL_PERSON_SPECIAL'}">
										<spring:message code="applicant.naturalPersonSpecialCase.type" />
									</c:when>
								</c:choose></td>
							<td><spring:eval var="storename" expression="index.name" />
								<c:choose>
									<c:when test="${storename.length()>10}">
										<c:out value="${storename.substring(0,10)}..."></c:out>
									</c:when>
									<c:otherwise>
										<c:out value="${index.name}"></c:out>
									</c:otherwise>
								</c:choose></td>
							<td><c:if test="${index.address!=null}">
									<c:out value="${index.address.country}"></c:out>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

		<c:if test="${not empty flowBean.correspondanceAddresses}">
			<table>
				<caption><spring:message code="review.applicationCA.info.title"/></caption>

					<tbody>
					<c:forEach items="${flowBean.correspondanceAddresses}" var="ca">
						<tr>
							<td><spring:message code="review.applicationCA.table.column.name.title"/></td><td><c:out value="${ca.correspondenceAddressForm.correspondenceName}"></c:out></td>
						</tr>
						<tr>
							<td><spring:message code="review.applicationCA.table.column.country.title"/></td><td><c:out value="${ca.correspondenceAddressForm.addressForm.country}"></c:out></td>
						</tr>
						<tr>
							<td><spring:message code="review.applicationCA.table.column.city.title"/></td><td><c:out value="${ca.correspondenceAddressForm.addressForm.city}"></c:out></td>
						</tr>
						<tr>
							<td><spring:message code="review.applicationCA.table.column.street.title"/></td><td><c:out value="${ca.correspondenceAddressForm.addressForm.street}"></c:out></td>
						</tr>
						<c:if test="${not empty ca.correspondenceAddressForm.electronicCorrespondence && ca.correspondenceAddressForm.electronicCorrespondence}">
							<tr>
								<td colspan="2"><spring:message code="person.address.field.electronicCorrespondence"/></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>
</div>