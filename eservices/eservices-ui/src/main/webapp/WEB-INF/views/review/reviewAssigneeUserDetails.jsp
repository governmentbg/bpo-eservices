<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
			<dl class="column">
				<dt><spring:message code="review.person.user.details.username.title"/></dt>
				<dd><sec:authentication property="principal.username"/></dd>
				<sec:authentication property="principal" var="userPrincipal"/>
				<c:set value="${userPrincipal.userPersonDetails.assignees[0]}" var="assignee"/>
				
				<dt><spring:message code="review.person.user.details.legal.name.title"/></dt>
				<dd>
				
				<c:if test="${assignee!=null && assignee.addressBook!=null && assignee.addressBook.name!=null }">
					<c:choose>
						<c:when test="${assignee.addressBook.name.organizationName!=null && assignee.addressBook.name.organizationName!=''}">
							<c:out value="${assignee.addressBook.name.organizationName}"></c:out>
						</c:when>
						<c:when test="${assignee.addressBook.name.firstName!=null && assignee.addressBook.name.firstName!='' &&
										assignee.addressBook.name.lastName!=null && assignee.addressBook.name.lastName!=''}">
							<c:out value="${assignee.addressBook.name.firstName +' '+ assignee.addressBook.name.lastName}"></c:out>
						</c:when>	
						<c:when test="${assignee.addressBook.name.lastName!=null && assignee.addressBook.name.lastName!=''}">
							<c:out value="${assignee.addressBook.name.lastName}"></c:out>
						</c:when>	
						<c:when test="${assignee.addressBook.name.firstName!=null && assignee.addressBook.name.firstName!=''}">
							<c:out value="${assignee.addressBook.name.firstName}"></c:out>
						</c:when>																			
					</c:choose>
					
				</c:if>
				</dd>
				
				<dt><spring:message code="review.person.user.details.country.title"/></dt>
				<dd><c:out value="${assignee.addressBook.address.countryCode}"></c:out></dd>
				
				<dt><spring:message code="review.person.user.details.address.title"/></dt>
				<dd><c:out value="${assignee.addressBook.address}"></c:out></dd>
			</dl>
		
			<dl class="column">
				<dt><spring:message code="review.person.user.details.mail.title"/></dt>
				<dd><c:out value="${assignee.addressBook.contactInformationDetails.email}"></c:out></dd>
				
				<dt><spring:message code="review.person.user.details.legal.form.title"/></dt>
				<dd><c:out value="${assignee.legalForm}"></c:out></dd>
				
				<dt><spring:message code="review.person.user.details.type.title"/></dt>
				<dd><spring:message code="review.person.user.details.type.assignee.${assignee.assigneeKind.name()}"/></dd>
				
				<dt><spring:message code="review.person.user.details.telephone.title"/></dt>
				<dd><c:out value="${assignee.addressBook.contactInformationDetails.phone.number}"></c:out></dd>
			</dl>	
					
