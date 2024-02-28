<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div id="obCardList">
    <c:set var="obDetailsSize" value="0"/>
    <c:if test="${not empty flowBean.obsList}">
    
    <c:set var="warningMessages"><form:errors path="oppositionBasisForm.formWarnings"/></c:set>
   	<c:if test="${not empty warningMessages}">
   		<c:set var="concatenated" value=""/>
    	<c:forEach items="${warningMessages}" var="message">
    		<c:set var="concatenated" value="${concatenated}${message}"/>
		</c:forEach>
		<div id="divErrorsWarnings" style="display:none">
			${concatenated}
		</div>
		<script type="text/javascript">
			showMessageModal($("#divErrorsWarnings").html());				
		</script>
   	</c:if>
    
        <!-- <h4><spring:message code="opposition_basis.table.title"/></h4> -->
        <table id="obsList" class="table">

            <tr>
                <th><spring:message code="opposition_basis.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <c:choose>
	                <c:when test="${flowModeId eq 'tm-opposition' }">
	                	<th><spring:message code="opposition_basis.details.table.header.earlierRight"/><a class="sorter" data-val='main'/></th>
	                </c:when>
	                <c:otherwise>
	                	<th><spring:message code="opposition_basis.details.table.header.grounds"/><a class="sorter" data-val='main'/></th>
	                </c:otherwise>
                </c:choose>
                
                <th><spring:message code="opposition_basis.details.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="ob" items="${flowBean.obsList}" varStatus="obId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="earlierRightType" value=""/>
                <c:if test="${not empty ob.relativeGrounds && not empty ob.relativeGrounds.earlierEntitlementRightTypeCode}">
					<c:choose>
						<c:when test="${fn:endsWith(flowModeId, 'invalidity')}">
							<spring:message var="earlierRightType" code="invalidity.earlierEntitlement.${ob.relativeGrounds.earlierEntitlementRightTypeCode}"/>
						</c:when>
						<c:otherwise>
							<spring:message var="earlierRightType" code="earlierEntitlement.${ob.relativeGrounds.earlierEntitlementRightTypeCode}"/>
						</c:otherwise>
					</c:choose>

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
                	
                
                	
                    <jsp:include page="/WEB-INF/views/opposition/opposition_basis_card_row.jsp">
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