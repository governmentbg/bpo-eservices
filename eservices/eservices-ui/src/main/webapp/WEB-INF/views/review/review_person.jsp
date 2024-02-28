<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />
<div class="your-details">
	<header>
		<spring:message code="review.yourDetails"/>
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
					<c:when test="${userPrincipal.userPersonDetails.representatives!=null && not empty userPrincipal.userPersonDetails.representatives }">
						<c:set value="${userPrincipal.userPersonDetails.representatives[0]}" var="representative"/>
						<jsp:include page="/WEB-INF/views/review/reviewRepresentativeUserDetails.jsp">
	   						<jsp:param value="${representative}" name="representative" />
	    				</jsp:include>	
					</c:when>
					<c:when test="${userPrincipal.userPersonDetails.holders!=null && not empty userPrincipal.userPersonDetails.holders }">
						<c:set value="${userPrincipal.userPersonDetails.holders[0]}" var="holder"/>
						<jsp:include page="/WEB-INF/views/review/reviewHolderUserDetails.jsp">
	   						<jsp:param value="${holder}" name="holder" />
	    				</jsp:include>	
					</c:when>
					<c:when test="${userPrincipal.userPersonDetails.holders!=null && not empty userPrincipal.userPersonDetails.assignees }">
						<c:set value="${userPrincipal.userPersonDetails.assignees[0]}" var="assignee"/>
						<jsp:include page="/WEB-INF/views/review/reviewAssigneeUserDetails.jsp">
	   						<jsp:param value="${assignee}" name="assignee" />
	    				</jsp:include>	
					</c:when>
					
				</c:choose>
			</c:if>				
		</c:if>
			
		<c:if test="${not empty flowBean.applicants}">
			<table>
				<caption><spring:message code="${flowModeId}.review.person.applicant.info.title"/></caption>
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
		<c:if test="${not empty flowBean.representatives}">
			<table>
				<caption><spring:message code="review.person.representative.info.title"/></caption>
				<thead>
					<tr>
						<th><spring:message code="review.person.representative.table.column.number.title"/></th>
						<th><spring:message code="review.person.representative.table.column.id.title"/></th>
						<%--<th><spring:message code="review.person.representative.table.column.organization.title"/></th>--%>
						<%--<th><spring:message code="review.person.representative.table.column.type.title"/></th> --%>
						<th><spring:message code="review.person.representative.table.column.name.title"/></th>
						<%--<th><spring:message code="review.person.representative.table.column.country.title"/></th> --%>														
					</tr>
				</thead>	
				<tbody>						
					<c:forEach var="index" items="${flowBean.representatives}" varStatus="status">		
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<td><c:out value="${index.id}"></c:out></td>
							<%--<td>	
								<c:choose>
									<c:when test="${index.type eq 'PROFESSIONAL_PRACTITIONER'}">
										<c:out value="${index.associationName}"></c:out>
									</c:when>
									<c:when test="${index.type eq 'LEGAL_PRACTITIONER'}">
										<c:out value="${index.associationName}"></c:out>
									</c:when>
									<c:when test="${index.type eq 'EMPLOYEE_REPRESENTATIVE'}">
										<c:out value="${index.nameOfEmployer}"></c:out>
									</c:when>
									<c:when test="${index.type eq 'NATURAL_PERSON'}">
										<c:out value=" "></c:out>
									</c:when>
									<c:when test="${index.type eq 'LEGAL_ENTITY'}">
										<c:out value=" "></c:out>
									</c:when>	
									<c:when test="${index.type eq 'ASSOCIATION'}">
										<c:out value="${index.associationName}"></c:out>
									</c:when>																																							
								</c:choose>							
							</td>
							<td>							
								<c:if test="${param.isAssociation eq 'true'}">
							        <spring:message code="representative.association.type"/>
							    </c:if>
							    <c:if test="${not param.isAssociation eq 'true'}">
							        <c:choose>
							            <c:when test="${param.representativeType eq 'LEGAL_PRACTITIONER'}">
							                <spring:message code="representative.legalPractitioner.type"/>
							            </c:when>
							            <c:when test="${param.representativeType eq 'PROFESSIONAL_PRACTITIONER'}">
							                <spring:message code="representative.professionalPractitioner.type"/>
							            </c:when>
							            <c:when test="${param.representativeType eq 'EMPLOYEE_REPRESENTATIVE'}">
							                <spring:message code="representative.employeeRepresentative.type"/>
							            </c:when>
							            <c:when test="${param.representativeType eq 'LEGAL_ENTITY'}">
							                <spring:message code="representative.legalEntity.type"/>
							            </c:when>
							            <c:when test="${param.representativeType eq 'NATURAL_PERSON'}">
							                <spring:message code="representative.naturalPerson.type"/>
							            </c:when>
							        </c:choose>
							    </c:if>
							</td>
							<td>
							  <c:choose>
							  	<c:when test="${index.type eq 'LEGAL_ENTITY'}">							  	
							        <spring:message code="representative.legalEntity.type"/>
							    </c:when>
							    <c:when test="${index.type eq 'NATURAL_PERSON'}">							    
							        <spring:message code="representative.naturalPerson.type"/>
							    </c:when>
							  </c:choose>							
							</td> --%>
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
							<%-- <td><c:if test="${index.address!=null}"><c:out value="${index.address.country}"></c:out></c:if></td>--%>
						</tr>
					</c:forEach>
				</tbody>					
			</table>
		</c:if>	
		
		<c:if test="${not empty flowBean.correspondanceAddresses}">
			<table>
				<caption><spring:message code="review.applicationCA.info.title"/></caption>
				<thead>
					<tr>
						<th><spring:message code="review.applicationCA.table.column.number.title"/></th>
						<th><spring:message code="review.applicationCA.table.column.name.title"/></th>
						<th><spring:message code="review.applicationCA.table.column.country.title"/></th>
					</tr>
				</thead>	
				<tbody>						
					<c:forEach var="ca" items="${flowBean.correspondanceAddresses}" varStatus="status">		
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<td><c:out value="${ca.correspondenceAddressForm.correspondenceName}" /></td>
							<td><c:out value="${ca.correspondenceAddressForm.addressForm.country}" /></td>
						</tr>
					</c:forEach>
				</tbody>					
			</table>
		</c:if>
		
		
		<c:if test="${not empty flowBean.holders}">
			<table>
				<caption><spring:message code="review.person.holder.info.title"/></caption>
				<thead>
					<tr>
						<th><spring:message code="review.person.holder.table.column.number.title"/></th>
						<th><spring:message code="review.person.holder.table.column.type.title"/></th>
						<th><spring:message code="review.person.holder.table.column.name.title"/></th>
						<th><spring:message code="review.person.holder.table.column.country.title"/></th>														
					</tr>
				</thead>	
				<tbody>						
					<c:forEach var="index" items="${flowBean.holders}" varStatus="status">		
						<tr>
							<td><c:out value="${status.count}"></c:out></td>							
							<td>
							  <c:choose>
							  	<c:when test="${index.type eq 'LEGAL_ENTITY'}">							  	
							        <spring:message code="holder.legalEntity.type"/>
							    </c:when>
							    <c:when test="${index.type eq 'NATURAL_PERSON'}">							    
							        <spring:message code="holder.naturalPerson.type"/>
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
		
		<c:if test="${not empty flowBean.assignees}">
			<table>
				<caption><spring:message code="${flowModeId}.review.person.assignee.info.title" text="Assignee info"/></caption>
				<thead>
					<tr>
						<th><spring:message code="review.person.assignee.table.column.number.title"/></th>
						<th><spring:message code="review.person.assignee.table.column.type.title"/></th>
						<th><spring:message code="review.person.assignee.table.column.name.title"/></th>
						<th><spring:message code="review.person.assignee.table.column.country.title"/></th>														
					</tr>
				</thead>	
				<tbody>						
					<c:forEach var="index" items="${flowBean.assignees}" varStatus="status">		
						<tr>
							<td><c:out value="${status.count}"></c:out></td>							
							<td>
							  <c:choose>
							  	<c:when test="${index.type eq 'LEGAL_ENTITY'}">							  	
							        <spring:message code="assignee.legalEntity.type"/>
							    </c:when>
							    <c:when test="${index.type eq 'NATURAL_PERSON'}">							    
							        <spring:message code="assignee.naturalPerson.type"/>
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