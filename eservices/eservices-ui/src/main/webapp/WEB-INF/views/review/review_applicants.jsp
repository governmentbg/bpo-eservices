<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />
<div class="your-details">
	<header>
		<spring:message code="${flowModeId}.applicant.section.title"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Person"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div>
		<c:if test="${authenticatedUser}">
			<sec:authentication property="principal" var="userPrincipal"/>			
			<c:if test="${userPrincipal!=null && userPrincipal.userPersonDetails!=null }">
				<c:choose>
					<c:when test="${userPrincipal.userPersonDetails.applicants!=null && not empty userPrincipal.userPersonDetails.applicants }">
						<jsp:include page="/WEB-INF/views/review/reviewApplicantUserDetails.jsp">
	   						<jsp:param value="${userPrincipal.userPersonDetails.applicants[0]}" name="applicant"/>
	    				</jsp:include>
					</c:when>					
				</c:choose>
			</c:if>				
		</c:if>
			
		<c:if test="${not empty flowBean.applicants}">
			<table>
				
				<thead>
					<tr>
						<th><spring:message code="review.person.aplicant.table.column.number.title"/></th>
						<%--<th><spring:message code="review.person.aplicant.table.column.id.title"/></th>--%>
						<th><spring:message code="review.person.aplicant.table.column.type.title"/></th>
						<th><spring:message code="review.person.aplicant.table.column.name.title"/></th>
						<th><spring:message code="review.person.aplicant.table.column.country.title"/></th>							
					</tr>
				</thead>
				<tbody>		
					<c:forEach var="index" items="${flowBean.applicants}" varStatus="status">	
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<%--<td><c:out value="${index.id}"></c:out></td>--%>
							<td>
								<c:choose>
								    <c:when test="${index.type eq 'NATURAL_PERSON'}">
								        <spring:message code="applicant.naturalPerson.type"/>
								    </c:when>
								    <c:when test="${index.type eq 'LEGAL_ENTITY'}">
								        <spring:message code="applicant.legalEntity.type"/>
								    </c:when>
								    <c:when test="${index.type eq 'NATURAL_PERSON_SPECIAL'}">
								        <spring:message code="applicant.naturalPersonSpecialCase.type"/>
								    </c:when>
								</c:choose>							
							</td>
							<td>
								<spring:eval var="storename" expression="index.name"/>
									<c:choose>
										<c:when test="${storename.length()>10}">
											<c:out value="${storename.substring(0,10)}..."></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${index.name}"></c:out>
										</c:otherwise>
									</c:choose>
							</td>
							<td><c:if test="${index.address!=null}"><c:out value="${index.address.country}"></c:out></c:if></td>
						</tr>		
					</c:forEach>
				</tbody>
			</table>
		</c:if>	
	
					
	</div>
</div>