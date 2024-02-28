<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">

		<table id="relativeGrounds" class="table">
				<c:set var="listOfGrounds" value=""/>
	            <c:forEach var="relativeGround" items="${configurationServiceDelegator['relativeGrounds']}" varStatus="relativeGroundId">
	                <c:if test="${relativeGround.legalActVersion eq param.legalActVersionParam && relativeGround.earlierEntitlementRightType eq param.earlierEntitleMent && relativeGround.applicationType eq flowModeId}">
	                <tr>
						<c:choose>
						<c:when test="${relativeGround.code eq 'plain_text'}">
							<td data-val='ground'>
								<spring:message code="${relativeGround.value}"/>
							</td>
						</c:when>
						<c:otherwise>
		                <td data-val='ground'>
		                	<component:oppositionCheckbox  path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.relativeGroundText" checkRender="true" id="${fn:replace(relativeGround.code, '.', '')}" value="${relativeGround.value}"
		                								   exclusivity="${relativeGround.isExclusivity()}" excludedLaw="${relativeGround.excludedLawArticleReference}" reputationClaim="${relativeGround.reputationClaim}" labelTextCode="${relativeGround.value}" onclick="updateGroundsSection();checkGrounds();"
														   formTagClass="relativeCheckbox"/>
		                <c:set var="listOfGrounds" value="${listOfGrounds}${fn:replace(relativeGround.code, '.', '')};"/>
		                </td>
						</c:otherwise>
						</c:choose>
	                   
	                </tr>
	                </c:if>
	            </c:forEach>
	            
	        </table>
	      
	      	<div style="display:none">
	        <input id="listExclLaw" value="${listOfGrounds}"/>
			</div>

	   	     
	   	     
			<jsp:include page="/WEB-INF/views/opposition/basis/relative_grounds_details.jsp"/>
	 
</section>