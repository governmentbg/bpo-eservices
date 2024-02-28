<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="service_fasttrack"
	   value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>

<div id="previousPriority" class="prevFilling">
	<div id="tabPriorityPrevious">
    	<p class="previousClaim"><spring:message code="claim.priority.previous"/></p>
    </div>	
	<div class="claim-row">
		<c:set var="sectionId" value="claim" scope="request"/>
	    <%-- Yes / No buttons --%>
	    <ul class="declared-claim-buttons">
	        <li>
	            <a id="openPriority" data-toggle="tab" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack? 'fasttrack-icon-after':''}"><spring:message code="claim.action.yes"/></a>
				<component:fastTrackMark sectionId="priority" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
	        </li>
	        
	        <c:choose>
	        	<c:when test="${empty flowBean.priorities and not flowBean.mainForm.priorityClaimLater}">
	        		<li class="active" id="claimPriorityNoItem">
	        	</c:when>
	        	<c:otherwise>
	        		<li id="claimPriorityNoItem">
	        	</c:otherwise>
	        </c:choose>	
	            <a id="noPriority" data-toggle="tab" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack? 'fasttrack-icon-after':''}"><spring:message code="claim.action.no"/></a>
				<component:fastTrackMark sectionId="priority" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
	        </li>
	    </ul>
	    
	    <%-- Claim later check --%>
	    <form:form class="claim-form mainForm formEf" modelAttribute="flowBean.mainForm">
			<component:checkbox path="priorityClaimLater" checkRender="true" id="checkPriorityClaimLater"
								labelTextCode="claim.button.later" labelClass="later"/>
		</form:form>
		
	</div>	
	
	<div class="previousClaim" id="tabPriority">
	</div>

</div>        