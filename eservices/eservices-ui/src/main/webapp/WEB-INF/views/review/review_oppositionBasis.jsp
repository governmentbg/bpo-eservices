<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="trade-mark-details">
    <header>
    	<spring:message code="review.${flowModeId}.details"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_OppositionBasis"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    <div class="trademark">
    <c:set var="obSize" value="0"/>
    <c:if test="${not empty flowBean.obsList}">
       
       <table id="obsList" class="table">
			<tr>
            <th><spring:message code="opposition_basis.details.table.header.number"/></th>
             <c:choose>
              <c:when test="${flowModeId eq 'tm-opposition' }">
              	<th><spring:message code="opposition_basis.details.table.header.earlierRight"/></th>
              </c:when>
              <c:otherwise>
              	<th><spring:message code="opposition_basis.details.table.header.grounds"/></th>
              </c:otherwise>
             </c:choose>
             </tr>   
            <c:set var="i" value="0"/>
            <c:forEach var="ob" items="${flowBean.obsList}" varStatus="obId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="earlierRightType" value=""/>
                <c:if test="${not empty ob.relativeGrounds && not empty ob.relativeGrounds.earlierEntitlementRightTypeCode}">
                	<spring:message var="earlierRightType" code="earlierEntitlement.${ob.relativeGrounds.earlierEntitlementRightTypeCode}"/>               	
				</c:if> 
				
				 
				<c:set var="legalGrounds" value=""/>
				<c:if test="${not empty ob.revocationGrounds}">
					<c:forEach items="${ob.revocationGrounds.revocationGroundText }" var="revo">
						<spring:message var="springGround" code="${revo }.short"/> 
						<c:set var="legalGrounds" value="${  legalGrounds eq '' ? legalGrounds.concat(springGround) : legalGrounds.concat('; ').concat(springGround)}"/>
					</c:forEach>
				</c:if> 
				
				<c:if test="${not empty ob.absoluteGrounds}">
					<c:forEach items="${ob.absoluteGrounds.absoluteGroundText }" var="abs">
						<spring:message var="springGround" code="${abs }.short"/> 
						<c:set var="legalGrounds" value="${ legalGrounds eq '' ? legalGrounds.concat(springGround) : legalGrounds.concat('; ').concat(springGround) }"/>
					</c:forEach>
				</c:if>
				
                <tr id="ob_id_${ob.id}">
                	
                	
                    <jsp:include page="/WEB-INF/views/review/review_oppositionBasis_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${ob.id}" name="obId"/>                        
                        <jsp:param value="${earlierRightType}" name="earlierRightType"/>
                        <jsp:param value="${legalGrounds}" name="legalGrounds"/> 
                        	
                        
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="obListSize" value="${i}"/>
        </table>
    </c:if>
    </div>
</div>