<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />
<div class="your-details">
	<header>
		<c:choose>
			<c:when test="${fn:endsWith(flowModeId, '-changerep')}">
				<spring:message code="new.representative.section.title"/>
			</c:when>
			<c:otherwise>
				<spring:message code="representative.section.title"/>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Person"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div>
		<c:if test="${authenticatedUser}">
			<sec:authentication property="principal" var="userPrincipal"/>			
			<c:if test="${userPrincipal!=null && userPrincipal.userPersonDetails!=null }">
				<c:choose>
				
					<c:when test="${userPrincipal.userPersonDetails.representatives!=null && not empty userPrincipal.userPersonDetails.representatives }">
						<c:set value="${userPrincipal.userPersonDetails.representatives[0]}" var="representative"/>
						<jsp:include page="/WEB-INF/views/review/reviewRepresentativeUserDetails.jsp">
	   						<jsp:param value="${representative}" name="representative" />
	    				</jsp:include>	
					</c:when>
					
					
				</c:choose>
			</c:if>				
		</c:if>
			
		
		<c:if test="${not empty flowBean.representatives}">
			<table>
				
				<thead>
					<tr>
						<th><spring:message code="review.person.representative.table.column.number.title"/></th>
						<th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
						<th><spring:message code="review.person.representative.table.column.name.title"/></th>
												
					</tr>
				</thead>	
				<tbody>						
					<c:forEach var="index" items="${flowBean.representatives}" varStatus="status">		
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
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
									<c:when test="${index.type eq 'INTELLECTUAL_PROPERTY_REPRESENTATIVE'}">
										<spring:message code="representative.intlprepresenetative.type"/>
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:out value="${index.name}"></c:out>
							</td>						
						</tr>
					</c:forEach>
				</tbody>					
			</table>
		</c:if>	
					
	</div>
</div>