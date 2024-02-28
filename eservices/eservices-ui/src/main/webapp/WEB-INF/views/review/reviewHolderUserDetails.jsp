<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
			<dl class="column">
				<dt><spring:message code="review.person.user.details.username.title"/></dt>
				<dd><sec:authentication property="principal.username"/></dd>
				<sec:authentication property="principal" var="userPrincipal"/>
				<c:set value="${userPrincipal.userPersonDetails.holders[0]}" var="holder"/>
				
				<dt><spring:message code="review.person.user.details.legal.name.title"/></dt>
				<dd>
				
				<c:if test="${holder!=null && holder.addressBook!=null && holder.addressBook.name!=null }">
					<c:choose>
						<c:when test="${holder.addressBook.name.organizationName!=null && holder.addressBook.name.organizationName!=''}">
							<c:out value="${holder.addressBook.name.organizationName}"></c:out>
						</c:when>
						<c:when test="${holder.addressBook.name.firstName!=null && holder.addressBook.name.firstName!='' &&
										holder.addressBook.name.lastName!=null && holder.addressBook.name.lastName!=''}">
							<c:out value="${holder.addressBook.name.firstName +' '+ holder.addressBook.name.lastName}"></c:out>
						</c:when>	
						<c:when test="${holder.addressBook.name.lastName!=null && holder.addressBook.name.lastName!=''}">
							<c:out value="${holder.addressBook.name.lastName}"></c:out>
						</c:when>	
						<c:when test="${holder.addressBook.name.firstName!=null && holder.addressBook.name.firstName!=''}">
							<c:out value="${holder.addressBook.name.firstName}"></c:out>
						</c:when>																			
					</c:choose>
					
				</c:if>
				</dd>
				
				<dt><spring:message code="review.person.user.details.country.title"/></dt>
				<dd><c:out value="${holder.addressBook.address.countryCode}"></c:out></dd>
				
				<dt><spring:message code="review.person.user.details.address.title"/></dt>
				<dd><c:out value="${holder.addressBook.address}"></c:out></dd>
			</dl>
		
			<dl class="column">
				<dt><spring:message code="review.person.user.details.mail.title"/></dt>
				<dd><c:out value="${holder.addressBook.contactInformationDetails.email}"></c:out></dd>
				
				<dt><spring:message code="review.person.user.details.legal.form.title"/></dt>
				<dd><c:out value="${holder.legalForm}"></c:out></dd>
				
				
				
				<dt><spring:message code="review.person.user.details.telephone.title"/></dt>
				<dd><c:out value="${holder.addressBook.contactInformationDetails.phone.number}"></c:out></dd>
			</dl>	
					
