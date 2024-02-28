<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="previousExhibition" class="prevFilling">
	<p class="claim-question"><spring:message code="claim.exhibition.previous"/></p>
	<div class="claim-row">
	    <ul class="declared-claim-buttons">
	        <li>
	            <a id="openExhibition" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
	        </li>
	        <c:choose>
	        	<c:when test="${empty flowBean.exhpriorities and not flowBean.mainForm.exhPriorityClaimLater}">
	        		<li class="active" id="claimExhibitionNoItem">
	        	</c:when>
	        	<c:otherwise>
	        		<li id="claimExhibitionNoItem">
	        	</c:otherwise>
	        </c:choose>	
	            <a id="noExhibition" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.no"/></a>
	        </li>
	    </ul>

	    <c:set var="sectionId" value="claim" scope="request"/>
	    <form:form class="claim-form mainForm formEf" modelAttribute="flowBean.mainForm">
	    	<component:generic path="exhPriorityClaimLater" checkRender="true">
				<label class="later" for="checkExhPriorityClaimLater">
					<form:checkbox id="checkExhPriorityClaimLater" path="exhPriorityClaimLater" />
					<spring:message code="claim.button.later" />
				</label>
			</component:generic>
		</form:form>
	</div>	
	
    <div class="previousClaim" id="tabExhibition">
    </div>
</div>    