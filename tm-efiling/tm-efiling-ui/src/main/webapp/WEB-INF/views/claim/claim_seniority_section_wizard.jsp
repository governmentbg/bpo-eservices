<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="previousSeniority" class="prevFilling">
    <p><spring:message code="claim.seniority.previous"/></p>

    <c:set var="service_fasttrack"
           value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
    <ul class="declared-claim-buttons">
        <li>
            <a id="openSeniority" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noSeniority" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack ? 'fasttrack-icon-after':''}" data-toggle="tab"><spring:message code="claim.action.no"/></a>
            <c:set var="sectionId" value="seniority"/>
            <component:fastTrackMark sectionId="${sectionId}" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
        </li>
    </ul>
    <%--<p class="introtext"><spring:message code="claim.seniority.previous"/></p>--%>

    <%--<div class="toggleWrap">--%>
        <%--<p class="btn-group toggleNav" data-toggle="buttons-radio">--%>
            <%--<a class="btn" id="openSeniority"> <spring:message code="claim.action.yes"/></a>--%>
            <%--<a id="noSeniority" class="btn" class="active"> <spring:message code="claim.action.no"/></a>--%>
        <%--</p>--%>

        <div class="previousClaim" id="tabSeniority">
<%--             <jsp:include page="claim_seniority_details_wizard.jsp"/> --%>
        </div>
</div>
<script type="text/javascript">
    markClaimNoButtonActive(".seniority", "#noSeniority");
</script>

