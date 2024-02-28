<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />

<div class="your-details">
	<header>
		<spring:message code="review.designersDetails"/>
		<a class="edit navigateBtn" data-val="Update_Designer"><spring:message code="review.update"/></a>
	</header>
	<div>
		<c:if test="${not empty flowBean.designers}">
		
			<c:set var="sectionId" value="designers" scope="request" />
			<component:generic path="showDesignerIdInTable" checkRender="true">
				<c:set var="showDesignerIdInTable" value="true" />
			</component:generic>

			<table>
				<caption><spring:message code="review.person.designer.info.title"/></caption>
				<thead>
					<tr>
						<th><spring:message code="review.person.designer.table.column.number.title"/></th>
						<c:if test="${showDesignerIdInTable}">
							<th><spring:message code="review.person.designer.table.column.id.title"/></th>
						</c:if>
						<th><spring:message code="review.person.designer.table.column.name.title"/></th>
						<th><spring:message code="review.person.designer.table.column.designNumbers.title"/></th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach var="index" items="${flowBean.designers}" varStatus="status">	
						<tr>
							<td><c:out value="${status.count}"></c:out></td>
							<c:if test="${showDesignerIdInTable}">
								<td>
									<c:out value="${index.imported ? index.id : '-'}"></c:out>
								</td>
							</c:if>	
							<td><c:out value="${index.name}"></c:out></td>
							<td><c:out value="${functions:getLinkedDesignsNumbers(index)}"></c:out></td>
						</tr>		
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>