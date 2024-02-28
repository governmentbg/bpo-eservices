<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<section class="trademark">
	<form:form id="oppositionBasisForm" cssClass="formEF" modelAttribute="oppositionBasisForm">
			
		<c:set var="sectionId" value="opposition_basis_details_section" scope="request"/>
		<input type="hidden" id="formReturned" value="true"/>
		
 		<form:hidden id="hiddenFormId" path="id"/>
 		
 		<component:generic path="formMessages" checkRender="true">
	    	<c:set var="errors">
	        	<form:errors path="formMessages"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>	 
	    
	    <component:generic path="formWarnings" checkRender="false">
	    	<c:set var="errors">
	        	<form:errors path="formWarnings"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<div id="customWarningMessage" class="hidden">${message}</div>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>
	   
	   	<c:set var="x" value ="${flowBean.getAbsoluteGroundsCount()}"/>
		<c:set var="z" value ="${flowBean.getRelativeGroundsCount()}"/>
		<c:set var="r" value ="${flowBean.getRevocationGroundsCount()}"/>
		<c:set var="nCategories" value="false"/>
		<input type="hidden" id ="countAbsolute" value="${x}"/>
		<input type="hidden" id ="countRelative" value="${z}"/>
		<input type="hidden" id ="countRevocation" value="${r}"/>
		
		<div id="divOppositionBasisData">

		<c:if test="${empty legalAct}">
			<c:if test="${x>0 && z>0}">
				<c:set var="nCategories" value="true"/>
				<div id="divGroundCategory">
				    <component:generic path="groundCategory" checkRender="true">
					    <label><spring:message code="opposition_basis.details.field.groundCategory"/><span class="requiredTag">*</span></label>
					    <select name="gcategory" id="gcategory">
					        <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
					       	<option value="ABSOLUTE_GROUNDS"><spring:message code="opposition_basis.details.groundCategory.absoluteGrounds"/></option>
					        <option value="RELATIVE_GROUNDS"><spring:message code="opposition_basis.details.groundCategory.relativeGrounds"/></option>
					        
					    </select>
					</component:generic>
	    		</div>
	    	</c:if>
		</c:if>
	   
	   
	   <jsp:include page="/WEB-INF/views/opposition/basis/legal_act_version.jsp">
	   		<jsp:param value="${legalAct}" name="legalActVersionParam"/>
	   		<jsp:param value="${x}" name="countAbsoluteParam"/>
	   		<jsp:param value="${z}" name="countRelativeParam"/>
	   		<jsp:param value="${r}" name="countRevocationParam"/>
	   		<jsp:param value="${nCategories}" name="nCategoriesParam"/>
	   </jsp:include>

		
		
		
		
		<div style="display:none">
			<component:input id="groundCategoryMain"  path="groundCategory" checkRender="true" labelTextCode=""/>
			<component:input id="lavHidden"  path="legalActVersion.nameVersion" checkRender="true" labelTextCode=""/>
			<component:input id="lavCodeHidden"  path="legalActVersion.code" checkRender="true" labelTextCode=""/>
			<component:input id="eeHidden" path="relativeGrounds.earlierEntitlementRightType" checkRender="true" labelTextCode="opposition_basis.details.relative.field.earlierEntitlementRightType"/>
		</div>
		
	
		
		<c:choose>
			
			<c:when test="${not empty groundCategory && not empty legalAct}">
				<c:choose>
				    <c:when test="${groundCategory eq 'ABSOLUTE_GROUNDS'}">
				        <div id="absoluteGroundsDiv">		
			  				<jsp:include page="/WEB-INF/views/opposition/basis/absolute_ground.jsp">
			  					<jsp:param value="${legalAct}" name="legalActVersionParam"/>
			  				</jsp:include>
			   			</div>
				    </c:when>
				    <c:when test="${groundCategory  eq 'RELATIVE_GROUNDS'}">
				        <div id="relativeGroundsDiv">
				   			<jsp:include page="/WEB-INF/views/opposition/basis/relative_ground.jsp">
				   				<jsp:param value="${legalAct}" name="legalActVersionParam"/>
				   			</jsp:include>
				   		</div>
				    </c:when>
				    <c:when test="${groundCategory eq 'REVOCATION_GROUNDS'}">
				        <div id="absoluteGroundsDiv">		
			  				<jsp:include page="/WEB-INF/views/opposition/basis/absolute_ground.jsp">
			  					<jsp:param value="${legalAct}" name="legalActVersionParam"/>
			  				</jsp:include>
			   			</div>
				    </c:when>
				 </c:choose>
			</c:when>
			
			<c:when test="${x>0 && z==0 && not empty legalAct}">
				
				<div id="absoluteGroundsDiv">		
		  				<jsp:include page="/WEB-INF/views/opposition/basis/absolute_ground.jsp">
		  					<jsp:param value="${legalAct}" name="legalActVersionParam"/>
		  				</jsp:include>
			   	</div>
			</c:when>
			
			<c:when test="${x==0 && z>0 && not empty legalAct}">
				
			   	<div id="relativeGroundsDiv">
			   			<jsp:include page="/WEB-INF/views/opposition/basis/relative_ground.jsp">
			   				<jsp:param value="${legalAct}" name="legalActVersionParam"/>
			   			</jsp:include>
			   	</div>
			</c:when>
			
			<c:when test="${x==0 && z==0 && r>0 && not empty legalAct}">
				<div id="absoluteGroundsDiv">		
		  				<jsp:include page="/WEB-INF/views/opposition/basis/absolute_ground.jsp">
		  					<jsp:param value="${legalAct}" name="legalActVersionParam"/>
		  				</jsp:include>
			   	</div>
			</c:when>
			
			<c:when test="${x==0 && z==0 && r==0 && not empty legalAct}">
				<h2><spring:message code="opposition_basis.messages.notGrounds"/></h2>
			</c:when>
			
		</c:choose>
		
	   		
		<br>
		</div>

		<c:set var="sectionId" value="opposition_basis_details_section" scope="request"/>
		<component:generic path="formMessages" checkRender="true">
			<c:set var="errors">
				<form:errors path="formMessages"></form:errors>
			</c:set>
			<c:if test="${!empty errors}">
				<c:forEach items="${errors}" var="message">
					<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
				</c:forEach>
			</c:if>
		</component:generic>

		<div id="divNotLegalActVersion" style = "display:none">
			<h2><spring:message code="opposition_basis.messages.notGrounds"/></h2>
		</div>
	   
	    <ul class="controls">
	        <li>
	            <a class="cancelButton oppositionBasis"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	        	<div id="buttonOpposition" style="display:none">
	            <button class="addOppositionBasis addOB" type="button">
	                <i class="add-icon"/>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	            </div>
	        </li>
	    </ul>
    </form:form>                
</section>