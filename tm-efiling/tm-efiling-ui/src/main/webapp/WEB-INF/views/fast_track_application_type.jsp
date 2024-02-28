<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="service_fasttrack" value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>

<c:if test="${service_fasttrack}">
	<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>

	<div class="aside-fast-track-applicationtype">
		<div class="fast-track-aside-title">
			<span class="fastTrackTitle"><spring:message code="fasttrack.applicationtype.title"/></span>
			<span class="fastTrackTitle fastTrackTitleYes" style="display: none"><spring:message code="fasttrack.applicationtype.title.yes"/></span>
			<span class="fastTrackTitle fastTrackTitleNo" style="display: none"><spring:message code="fasttrack.applicationtype.title.no"/></span>
			<span class="fasttrack-icon-after"></span>
		</div>
		<div class="fast-track-aside-content">
			<p><spring:message code="fasttrack.applicationtype.content"/></p>
			<a target="_blank" href="${fasttrackConditionsUrl}">
				<spring:message code="fasttrack.applicationtype.find.more"/>
			</a>
		</div>

		<div id="fees_content">
			<jsp:include page="right_cart.jsp"></jsp:include>
		</div>
	</div>
	<script id="aside-fast-track-applicationtype-ok-template" type="text/template">
		<p><spring:message code="fasttrack.applicationtype.content.ok"/></p>
	</script>
	<script id="aside-fast-track-applicationtype-fail-template" type="text/template">
		<p><spring:message code="fasttrack.applicationtype.content.fail"/></p>
		<@ for (var i = 0; i < fails.length; i++){ @>
		<p>
			<a href="<@=fails[i].sectionAnchor@>" data-val="<@=fails[i].eventId@>"
			   class="exclamation-sign-icon-dark-before aside-fast-track-applicationtype-fail-entry-action">
				<@=getI18nMessageWithArgs(fails[i].message, fails[i].args, fails[i].translateArgs)@>
			</a>
		</p>
		<@ } @>
		<a target="_blank" href="${fasttrackConditionsUrl}">
			<spring:message code="fasttrack.applicationtype.find.more"/>
		</a>
	</script>

	<script id="aside-fast-track-applicationtype-is-started" type="text/template">${flowBean.fastTrackStarted}</script>

	<script type="text/javascript">
		$(window).load(function(){
			getFeesInformation();
		});
	</script>

</c:if>
<c:if test="${!service_fasttrack}">
	<div id="fees_content" style="border: 1px solid #D2D5D9; margin: 0 0 32px 0;">
		<jsp:include page="right_cart.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
		$(window).load(function(){
			getFeesInformation();
		});
	</script>
</c:if>
