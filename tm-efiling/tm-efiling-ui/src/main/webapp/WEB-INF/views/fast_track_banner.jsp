<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="service_fasttrack" value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>

<c:if test="${service_fasttrack}">
	<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>

	<div class="fast-track-banner">
		<div class="fasttrack-icon-left">
			<div id="fast-track-collapse-banner" class="minimized" style="display:none;">
				<div class="fast-track-banner-title">
					<spring:message code="fasttrack.banner.title.short"/>
					<span class="add-icon"></span>
				</div>
				<div class="fast-track-banner-content">
					<p><spring:message code="fasttrack.banner.content.short"/></p>
					<a target="_blank" href="${fasttrackConditionsUrl}" class="share-icon-after">
						<spring:message code="fasttrack.banner.read.conditions.short"/>
					</a>
				</div>
			</div>
			<div id="fast-track-expand-banner" class="expanded">
				<div class="fast-track-banner-title" >
					<spring:message code="fasttrack.banner.title.short"/>
					<span class="minus-icon"></span>
				</div>
				<div class="fast-track-banner-content">
					<spring:message code="fasttrack.banner.content"/>
					<a target="_blank" href="${fasttrackConditionsUrl}" class="share-icon-after">
						<spring:message code="fasttrack.banner.read.conditions"/>
					</a>
				</div>
			</div>
		</div>
	</div>
</c:if>
