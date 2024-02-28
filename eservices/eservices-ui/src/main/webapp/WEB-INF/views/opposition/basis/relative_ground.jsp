<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">

	<c:if test="${not empty param.legalActVersionParam}">
		
		<div id="divEarlierEntitlementRightType">
		    <component:generic path="relativeGrounds.earlierEntitlementRightType" checkRender="true"> 
			    <label><spring:message code="opposition_basis.details.relative.field.earlierEntitlementRightType"/><span class="requiredTag">*</span></label>
			    <select name="eEntitlementRightType" id="eEntitlementRightType" class="long-fields">
			    	<c:forEach var="eERightType" items="${configurationServiceDelegator['earlierEntitlementRightTypes']}" varStatus="eERightTypeId">
						<c:if test="${eERightType.legalActVersion eq param.legalActVersionParam && eERightType.applicationType eq flowModeId && eERightType.enabled == true}">
							<option value=${eERightType.code}><spring:message code="${eERightType.value}"/></option>
						</c:if>
			       	</c:forEach>
			    </select>
			</component:generic>
   		</div>
	    		
	    		
	</c:if>
	<div id="divEarlierEntitlementRightTypeHidden" style="display:none; width: 100%" contenteditable="false">
		<component:input id="eEntitlementRightTypeInput" path="relativeGrounds.earlierEntitlementRightType" checkRender="true" labelTextCode="opposition_basis.details.relative.field.earlierEntitlementRightType"/>
		<div style="display:none">
			<component:input id="eEntitlementRightTypeCodeInput" path="relativeGrounds.earlierEntitlementRightTypeCode" checkRender="true" labelTextCode="opposition_basis.details.relative.field.earlierEntitlementRightType"/>
		</div>
	</div>
	
	<div id="divEarlierRight">
		<c:if test="${not empty formErrors}">
			<jsp:include page="/WEB-INF/views/opposition/basis/opposition_basis_earlier_right.jsp">
			   	<jsp:param value="${legalAct}" name="legalActVersionParam"/>
			</jsp:include>
		</c:if>
	</div>
	 
</section>