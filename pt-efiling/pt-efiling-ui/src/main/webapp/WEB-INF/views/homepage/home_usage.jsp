<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
	<div class="span6">
		<div class="usage">
			<h2 class="blueHeader"><spring:message code="homepage.usage.header"/></h2>
			<p><spring:message code="homepage.usage.p1"/></p>
			<p><spring:message code="homepage.usage.p2.${flowModeId}"/></p>
			<c:if test="${flowModeId == 'pt-efiling' || flowModeId == 'um-efiling'}">
				<p style="color: red"><spring:message code="homepage.usage.p2.classified.${flowModeId}"/></p>
			</c:if>
			<c:if test="${flowModeId != 'spc-efiling' && flowModeId != 'sv-efiling'}">
				<p><spring:message code="homepage.usage.p3.${flowModeId}"/></p>
			</c:if>
			<p><spring:message code="homepage.usage.p4.${flowModeId}"/></p>
			<p><spring:message code="homepage.usage.p5"/></p>
			<p>
				<ul>
					<li><spring:message code="homepage.usage.bullet1"/></li>
					<li><spring:message code="homepage.usage.bullet2"/></li>
				</ul>
			</p>
			<p><spring:message code="homepage.usage.p6.${flowModeId}"/></p>
		</div>

	</div>
	<div class="span6">
		<ul class="wizardSteps">
		    <li class="step1"><spring:message code="homepage.option1"/> <spring:message code="homepage.option.manual"/> <a target="_blank" href="<spring:message code='homepage.manual.url.${flowModeId}'/>">
				<spring:message code="homepage.option.manual.here"/></a></li>
			<%--<li class="step2"><spring:message code="homepage.option2"/></li>--%>
		    <li class="step3"><spring:message code="homepage.option3"/></li>
		    <li class="step4"><spring:message code="homepage.option4"/></li>
		</ul>
	</div>
</div>
