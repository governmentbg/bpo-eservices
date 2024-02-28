<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="applicant" id="applicant">

    <header>
        <a id="applicant" class="anchorLink">
        </a>

        <h2><spring:message code="${flowModeId}.applicant.section.title"></spring:message></h2>
    </header>

    <c:if test="${fn:endsWith(flowModeId, '-transfer') || fn:endsWith(flowModeId, '-licence') || fn:endsWith(flowModeId, '-compulsorylicence') || fn:endsWith(flowModeId, '-surrender') || fn:endsWith(flowModeId, '-withdrawal') || fn:endsWith(flowModeId, '-renewal') || fn:endsWith(flowModeId, '-change') || fn:endsWith(flowModeId, '-docchanges') || fn:endsWith(flowModeId, '-appeal')
		 ||flowModeId eq 'ds-rem' ||flowModeId eq 'pt-rem' ||flowModeId eq 'um-rem' ||flowModeId eq 'ep-rem' ||flowModeId eq 'sv-rem' ||flowModeId eq 'spc-rem'}">
        <div class="alert alert-info">
            <spring:message code="applicant.canbe.${flowModeId}"/>
        </div>
    </c:if>

    <div id="applicantCardList">
        <jsp:include page="/WEB-INF/views/applicant/applicant_card_list.jsp"/>
    </div>

    <sec:authorize access="hasRole('Applicant_Add')" var="security_applicant_add" />
    
    <c:if test="${security_applicant_add || !configurationServiceDelegator.securityEnabled}">

        <button id="applicantTrigger" class="add-button" data-toggle="button" type="button">
            <i class="add-icon"></i>
            <spring:message code="${flowModeId}.person.button.applicant"/>
        </button>


        <div id="tabApplicant" class="addbox" style="display: none">
            <jsp:include page="/WEB-INF/views/applicant/applicant_import.jsp"/>
            <div id="applicantSection">           
            </div>
        </div>

        <div id="applicantMatches">
            <jsp:include page="/WEB-INF/views/applicant/applicantMatches.jsp"/>
        </div>
    </c:if>
    <div class="hidden" id="saveApplicantMsg"><spring:message code="applicant.form.action.save"/></div>
    
    <c:set var="maxApp" value="applicant.add.maxNumber"/>
    
	<input type="hidden" id="maxApplicants"
	value="${configurationServiceDelegator.getValue(maxApp, 'eu.ohim.sp.core.person')}">    
        <script type="text/javascript">
        	checkMaxApplicants();
        </script>
   
</section>
