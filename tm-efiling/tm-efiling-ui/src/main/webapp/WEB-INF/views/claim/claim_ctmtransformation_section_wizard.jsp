<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="previousCtmTransformation">
    <p><spring:message code="claim.ctm.transformation.previous"/></p>

    <c:set var="service_fasttrack"
           value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
    <ul class="declared-claim-buttons">
        <li id="openCtmTransformationLi">
            <a id="openCtmTransformation" class="yesNoBtn" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li id="noCtmTransformationLi">
            <a id="noCtmTransformation" rel="file-help-tooltip" class="yesNoBtn ${service_fasttrack ? 'fasttrack-icon-after':''}" data-toggle="tab"><spring:message code="claim.action.no"/></a>
            <c:set var="sectionId" value="ctmTransformation"/>
            <component:fastTrackMark sectionId="${sectionId}" isButton="true" helpMessageKey="fasttrack.help.stayOnFastrack" />
        </li>
    </ul>

    <div class="ctmPreviousClaim" id="tabCtmTransformation">

    </div>
</div>

<script type="text/javascript">
    markClaimNoButtonActive(".conversion", "#noCtmTransformation");
</script>