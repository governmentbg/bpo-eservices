<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="previousExhibition" class="prevFilling">
    <p><spring:message code="claim.exhibition.previous"/></p>

    <c:set var="service_fasttrack"
           value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
    <ul class="declared-claim-buttons">
        <li>
            <a id="openExhibition" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noExhibition" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack ? 'fasttrack-icon-after':''}" data-toggle="tab"><spring:message code="claim.action.no"/></a>
            <c:set var="sectionId" value="exhibition"/>
            <component:fastTrackMark sectionId="${sectionId}" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
        </li>
    </ul>

    <div class="previousClaim" id="tabExhibition">
    </div>
</div>

<script type="text/javascript">
    markClaimNoButtonActive(".exhpriority", "#noExhibition");
</script>