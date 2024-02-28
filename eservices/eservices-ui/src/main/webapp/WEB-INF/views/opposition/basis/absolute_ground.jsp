<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">


<div class="row" id="absoluteGroundsDivTable">
	<div id="absoluteGroundsDivTable-warning" class="hidden"><spring:message code="BR111.tm-opposition.articles.Empty.warning" /></div>
		<spring:message code="basis.name.${fn:substringAfter(flowModeId, '-')}" var="basisName"/>
		<c:choose>
			<c:when test="${flowModeId eq 'tm-revocation'}">
				<table id="revocationGrounds" class="table">
				<tr>
	                <th><spring:message code="absoluteGrounds.table.header.ground" arguments="${basisName}"/></th>

	            </tr>
				<c:set var="listOfGrounds" value=""/>
	            <c:forEach var="revocationGround" items="${configurationServiceDelegator['revocationGrounds']}" varStatus="revocationGroundId">
	                <c:if test="${revocationGround.legalActVersion eq param.legalActVersionParam && revocationGround.applicationType eq flowModeId}">
	                <tr>
						<c:choose>
						<c:when test="${revocationGround.code eq 'plain_text'}">
							<td data-val='ground'>
								<spring:message code="${revocationGround.value}"/>
							</td>
						</c:when>
						<c:otherwise>
		                		<td data-val='ground'>
				                	<component:oppositionCheckbox  path="revocationGrounds.revocationGroundText" checkRender="true" id="${fn:replace(revocationGround.code,'.','')}" value="${revocationGround.value}"
				                								   exclusivity="false" reputationClaim="false" labelTextCode="${revocationGround.value}" onclick="updateGroundsSection"/> 
				                <c:set var="listOfGrounds" value="${listOfGrounds}${fn:replace(revocationGround.code,'.','')};"/>
				                <c:if test="${revocationGround.isNonUse()}">
		                			<c:if test="${not empty revocationGround.nonUsePeriod && revocationGround.nonUsePeriod != ''}">
		                				<c:set var="nonUsePeriod" value="${revocationGround.nonUsePeriod}" />
		                				<c:if test="${flowBean.isNonUsePeriod(nonUsePeriod)}">
		                					<div id="nonUse${fn:replace(revocationGround.code,'.','')}" style="display:none">
		                						<p class="eServiceMessageWarning"><spring:message code="nonUse.message"></spring:message> </p>
		                					</div>
		                				</c:if>
		                				
		                			</c:if>
		                		</c:if>
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
			</c:when>
		
			<c:otherwise>
				<table id="absoluteGrounds" class="table">
				<tr>
					<c:choose>
						<c:when test="${flowModeId eq 'pt-invalidity'}">
							<th><spring:message code="absoluteGrounds.table.header.ground.pt-invalidity"/>
						</c:when>
						<c:when test="${flowModeId eq 'spc-invalidity'}">
							<th><spring:message code="absoluteGrounds.table.header.ground.spc-invalidity"/>
						</c:when>
						<c:when test="${flowModeId eq 'um-invalidity'}">
							<th><spring:message code="absoluteGrounds.table.header.ground.um-invalidity"/>
						</c:when>
						<c:otherwise>
							<th><spring:message code="absoluteGrounds.table.header.ground" arguments="${basisName}"/></th>
						</c:otherwise>
					</c:choose>
                
            	</tr>
				<c:set var="listOfGrounds" value=""/>
	            <c:forEach var="absoluteGround" items="${configurationServiceDelegator['absoluteGrounds']}" varStatus="absoluteGroundId">
	                <c:if test="${absoluteGround.legalActVersion eq param.legalActVersionParam && absoluteGround.applicationType eq flowModeId}">
	                <tr>
						<c:choose>
						<c:when test="${absoluteGround.code eq 'plain_text'}">
							<td data-val='ground'>
								<spring:message code="${absoluteGround.value}"/>
							</td>
						</c:when>
						<c:otherwise>
		                <td data-val='ground'>
		                	<component:oppositionCheckbox  path="absoluteGrounds.absoluteGroundText" checkRender="true" id="${fn:replace(absoluteGround.code,'.','')}" value="${absoluteGround.value}"
		                								   exclusivity="false" reputationClaim="false" labelTextCode="${absoluteGround.value}" onclick="updateGroundsSection"/> 
		                <c:set var="listOfGrounds" value="${listOfGrounds}${fn:replace(absoluteGround.code,'.','')};"/>
		                </td>
						</c:otherwise>
						</c:choose>

					</tr>
	                </c:if>
	            </c:forEach>
	            
	        	</table>
			</c:otherwise>
	
		
 		
		
		</c:choose>
</div>	

		<jsp:include page="/WEB-INF/views/opposition/basis/absolute_grounds_details.jsp"/>

	 
</section>