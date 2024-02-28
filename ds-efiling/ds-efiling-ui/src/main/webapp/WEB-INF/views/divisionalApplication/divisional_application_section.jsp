<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="service_fasttrack"
	   value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>

<section id="divisionalApplicationSection" class="claim divisionalApplication">

    <c:set var="sectionId" value="divisionalApplicationSection" scope="request"/>

    <header>
        <a href="#divisionalApplicationSection" class="anchorLink"></a>

        <h2><spring:message code="divisionalApplication.title"/></h2>
    </header>
 	
 	<div id="divisionalApplicationTableContainer">
        <jsp:include page="divisional_application_table.jsp"/>
    </div>
 	
 	<p class="claim-question">
 		<spring:message code="divisionalApplication.question"/>
 	</p>
 	<div class="claim-row">
	    <ul class="declared-claim-buttons">
	        <li id="divisionalApplicationYesItem">
	            <a id="claimDivisionalApplicationYesBtn" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
	        </li>
	        <li id="divisionalApplicationNoItem">
	            <a id="claimDivisionalApplicationNoBtn" data-toggle="tab" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack? 'fasttrack-icon-after':''}"><spring:message code="claim.action.no"/></a>
				<component:fastTrackMark sectionId="${sectionId}" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
	        </li>
	    </ul>
	</div>	
 	
 	<div id="divisionalApplicationTab" class="divisionalApplicationForm">
	</div>
    
</section>