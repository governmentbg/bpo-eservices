<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />
<div class="your-details">
	<header>
		<spring:message code="${flowModeId}.designer.section.title"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Person"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div>

		<c:if test="${not empty flowBean.designers}">
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
					<c:forEach var="index" items="${flowBean.designers}" varStatus="status">
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<%--<td><c:out value="${index.id}"></c:out></td>--%>
							<td>
								<spring:message code="designer.naturalPerson.type"/>
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