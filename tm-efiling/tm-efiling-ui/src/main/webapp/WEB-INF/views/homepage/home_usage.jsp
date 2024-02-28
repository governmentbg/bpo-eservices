<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
	<div class="span6">
		<div class="usage">
			<h2 class="blueHeader"><spring:message code="homepage.usage.header"/></h2>
			<p><spring:message code="homepage.usage.p1.${flowModeId}"/></p>
			<c:if test="${flowModeId == 'wizard'}">
				<p><spring:message code="homepage.usage.p2.${flowModeId}"/></p>
			</c:if>
			<p><spring:message code="homepage.usage.p3.${flowModeId}"/></p>
			<p><spring:message code="homepage.usage.p4.${flowModeId}"/></p>
			<p><spring:message code="homepage.usage.p5.${flowModeId}"/></p>
			<p>
				<ul>
					<li><spring:message code="homepage.usage.bullet1"/></li>
					<li><spring:message code="homepage.usage.bullet2"/></li>
				</ul>
			</p>
			<p><spring:message code="homepage.usage.p6.${flowModeId}"/></p>
		</div>
		<%-- <img src="<%=request.getContextPath()%>/resources/images/fakevid.gif" alt="Video" class="flAlignLeft"/>--%>

	</div>
	<div class="span6">
		<ul class="wizardSteps">
		    <li class="step1"><spring:message code="homepage.option1"/> <spring:message code="homepage.option.manual"/> <a target="_blank" href="<spring:message code='homepage.manual.url'/>">
				<spring:message code="homepage.option.manual.here"/></a></li>
			<c:if test="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}">
				<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>
				<li class="step5"><spring:message code="homepage.option5" arguments="${fasttrackConditionsUrl}"/></li>
			</c:if>
		    <li class="step2"><spring:message code="homepage.option2"/></li>
		    <li class="step3"><spring:message code="homepage.option3"/></li>
		    <li class="step4"><spring:message code="homepage.option4"/></li>
		</ul>
	</div>
</div>
