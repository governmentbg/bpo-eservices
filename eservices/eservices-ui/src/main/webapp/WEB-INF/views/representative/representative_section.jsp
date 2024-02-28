<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="representative" id="representative">
    <header>
        <a id="representative" class="anchorLink">
        </a>

        <c:choose>
            <c:when test="${fn:endsWith(flowModeId, '-changerep')}">
                <h2><spring:message code="new.representative.section.title"></spring:message></h2>
            </c:when>
            <c:otherwise>
                <h2><spring:message code="representative.section.title"></spring:message></h2>
            </c:otherwise>
        </c:choose>

    </header>

    <c:if test="${fn:endsWith(flowModeId, '-withdrawal') || fn:endsWith(flowModeId, '-surrender') }">
        <div class="alert alert-info">
            <spring:message code="representative.pow.${flowModeId}"/>
        </div>
    </c:if>

    <c:if test="${fn:endsWith(flowModeId, '-changerep')}">
        <div class="alert alert-info">
            <spring:message code="personChange.warning.message.${flowModeId}"/>
        </div>
    </c:if>

    <div class="alert alert-info">
        <spring:message code="representative.pow.details.info"/>
    </div>

    <div id="representativeCardList">
        <jsp:include page="/WEB-INF/views/representative/representative_card_list.jsp"/>
    </div>
    
    <sec:authorize access="hasRole('Representative_Add')" var="security_representative_add" />
    
    <c:if test="${security_representative_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.representative"/>
        </button>

        <div id="tabRepresentative" class="addbox" style="display:none">
        	<div id="importAndCreateSection">
            <jsp:include page="/WEB-INF/views/representative/representative_import.jsp"/>
            </div>
            <div id="representativeSection">
            </div>
        </div>

        <div id="representativeMatches">
            <jsp:include page="/WEB-INF/views/representative/representativeMatches.jsp"/>
        </div>
    </c:if>
    
    <c:set var="maxRepr" value="representative.add.maxNumber.${flowModeId}"/>
    
	<input type="hidden" id="maxRepresentatives"
       value="${configurationServiceDelegator.getValue(maxRepr, 'eu.ohim.sp.core.person')}">    
        <script type="text/javascript">
        	checkMaxRepresentatives();
        </script>

    <div id="pis_partnershipRepresentativeMsg" style="display: none"><spring:message code="representative.association.shortName"/> </div>
    <div id="pisRepresentativeMsg" style="display: none"><spring:message code="representative.naturalPerson.shortName"/> </div>
    <div id="pis_companyRepresentativeMsg" style="display: none"><spring:message code="representative.legalentity.shortName"/> </div>
    <div id="lawyerRepresentativeMsg" style="display: none"><spring:message code="representative.lawyer.shortName"/> </div>
    <div id="lawyer_partnershipRepresentativeMsg" style="display: none"><spring:message code="representative.lawyerpartnership.shortName"/> </div>
    <div id="lawyer_companyRepresentativeMsg" style="display: none"><spring:message code="representative.lawyercompany.shortName"/> </div>
    <div id="temporaryRepresentativeMsg" style="display: none"><spring:message code="representative.temporary.shortName"/> </div>
</section>
