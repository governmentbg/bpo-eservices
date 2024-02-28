<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">

	

	<c:set var="displayAb" value="display:none"/>
	<c:set var="displayRel" value="display:none"/>
	<c:set var="displayRev" value="display:none"/>
	
	<c:choose>
		
		<c:when test="${param.nCategoriesParam eq 'false'}">
			<c:choose>
				<c:when test="${param.countAbsoluteParam > 0 && param.countRelativeParam eq 0}">
					<c:set var="displayAb" value="display:block"/>
				</c:when>
		
				<c:when test="${param.countRelativeParam > 0 && param.countAbsoluteParam eq 0}">
					<c:set var="displayRel" value="display:block"/>
				</c:when>
				
				<c:when test="${param.countRelativeParam eq 0 && param.countAbsoluteParam eq 0 && param.countRevocationParam > 0}">
					<c:set var="displayRev" value="display:block"/>
				</c:when>
				
				<c:when test="${param.countRelativeParam eq 0 && param.countAbsoluteParam eq 0 && param.countRevocationParam eq 0}">
					<c:set var="displayNotLegalAct" value="display:block"/>
				</c:when>
			</c:choose>
		</c:when>
		<c:when test="${param.nCategoriesParam eq 'true'}">
			<c:set var="displayAb" value="display:none"/>
			<c:set var="displayRel" value="display:none"/>
			<c:set var="displayRev" value="display:none"/>
		</c:when>
	
	</c:choose>
	
	<c:if test="${empty param.legalActParam}">
	
		
		
		<div class="row" id="divLegalActVersionAbsoluteSelect" style="${displayAb}">
			<h4><spring:message code="opposition_basis.legalAct.tittle"/></h4>
	            <div class="span3">
	           	 	<component:generic path="legalActVersion.nameVersion" checkRender="true">
					    <label><spring:message code="opposition_basis.LegalAct.field.nameVersion" arguments="${fn:substringAfter(flowModeId, \"-\")}"/><span class="requiredTag">*</span></label>
					    <select name="legalactversionAbsolute-select" id="legalactversionAbsolute-select">
					        <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
							<c:forEach items="${configurationServiceDelegator.getLegalActVersions('ABSOLUTE_GROUNDS', flowBean.avaibleLegalActVersions)}" var="legalActAbVar">					       
								<option value="${legalActAbVar.code}"><spring:message code="${legalActAbVar.nameVersion}"/></option>
								<c:if test="${param.countAbsoluteParam eq 1}">
									<c:set var="varLegalActAbCode" value="${legalActAbVar.code}"/>
									<c:set var="varLegalActAbNameVersion" value="${legalActAbVar.nameVersion}"/>
								</c:if>	
					        </c:forEach>
					    </select>
					    <c:if test="${param.countAbsoluteParam eq 1}">
							<input id="inputLegalActAbCode" type="hidden" value="${varLegalActAbCode}"/>
							<input id="inputLegalActAbNameVersion" type="hidden" value="<spring:message code="${varLegalActAbNameVersion}"/>"/>
						</c:if>	
					</component:generic>
	
	    		</div>
	    </div>
	    
	    
	    
	    <div class="row" id="divLegalActVersionRelativeSelect" style="${displayRel}">
	    	<h4><spring:message code="opposition_basis.legalAct.tittle"/></h4>
	            <div class="span3">
	            	<component:generic path="legalActVersion.nameVersion" checkRender="true">
					    <label><spring:message code="opposition_basis.LegalAct.field.nameVersion" arguments="${fn:substringAfter(flowModeId, \"-\")}"/><span class="requiredTag">*</span></label>
					    <select name="legalactversionRelative-select" id="legalactversionRelative-select">
					        <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
							<c:forEach items="${configurationServiceDelegator.getLegalActVersions('RELATIVE_GROUNDS', flowBean.avaibleLegalActVersions)}" var="legalActReVar">					       
								<option value="${legalActReVar.code}"><spring:message code="${legalActReVar.nameVersion}"/></option>
								<c:if test="${param.countRelativeParam eq 1}">
									<c:set var="varLegalActReCode" value="${legalActReVar.code}"/>
									<c:set var="varLegalActReNameVersion" value="${legalActReVar.nameVersion}"/>
								</c:if>		
					        </c:forEach>
					    </select>
					    <c:if test="${param.countRelativeParam eq 1}">
							<input id="inputLegalActReCode" type="hidden" value="${varLegalActReCode}"/>
							<input id="inputLegalActReNameVersion" type="hidden" value="<spring:message code="${varLegalActReNameVersion}"/>"/>
						</c:if>	
					</component:generic>
	            </div>
	    </div>
	    
	    
	    <div class="row" id="divLegalActVersionRevocationSelect" style="${displayRev}">
	    	<h4><spring:message code="opposition_basis.legalAct.tittle"/></h4>
	            <div class="span3">
	           	 	<component:generic path="legalActVersion.nameVersion" checkRender="true">
					    <label><spring:message code="opposition_basis.LegalAct.field.nameVersion" arguments="${fn:substringAfter(flowModeId, \"-\")}"/><span class="requiredTag">*</span></label>
					    <select name="legalactversionRevocation-select" id="legalactversionRevocation-select">
					        <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
							<c:forEach items="${configurationServiceDelegator.getLegalActVersions('REVOCATION_GROUNDS', flowBean.avaibleLegalActVersions)}" var="legalActRevVar">					       
								<option value="${legalActRevVar.code}"><spring:message code="${legalActRevVar.nameVersion}"/></option>
								<c:if test="${param.countRevocationParam eq 1}">
									<c:set var="varLegalActRevCode" value="${legalActRevVar.code}"/>
									<c:set var="varLegalActRevNameVersion" value="${legalActRevVar.nameVersion}"/>
								</c:if>		
					        </c:forEach>
					    </select>
					    <c:if test="${param.countRevocationParam eq 1}">
							<input id="inputLegalActRevCode" type="hidden" value="${varLegalActRevCode}"/>
							<input id="inputLegalActRevNameVersion" type="hidden" value="<spring:message code="${varLegalActRevNameVersion}"/>"/>
						</c:if>	
					</component:generic>
	
	    		</div>
	    </div>
	    
	     <div id="confirmWarningLAV" style="display: none">
		    <div id="confirmSection">
			    <spring:message code="opposition_basis.legalAct.confirmWarning"/>
			     <component:generic path="legalActVersion.confirmLegalActVersion" checkRender="true">
			        <label class="correspondence-address" for="applicantConfirmLegalActVersion">
			            <input id="applicantConfirmLegalActVersion" name="applicantNConfirmLegalActVersion" type="checkbox">
			            <spring:message code="opposition_basis.LegalAct.field.confirmLegalActVersion"/>
			        </label>
			    </component:generic>
		    </div>
	    </div>
		
		<div id="legalActVersionSelected" style="display: none; width: 100%" contenteditable="false" >
			<label for="legalActVersionSelectedInput"><spring:message code="opposition_basis.legalAct.tittle"/></label>
			<component:input id="legalActVersionSelectedInput" path="legalActVersion.nameVersion" checkRender="true" formTagClass="long-fields"/>
			<div style="display:none">
			<input id="codeLegalActVersionSelected"/>
			</div>
		</div>
	</c:if>
	
    
   
	 
</section>