<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="main" role="main">
	<section class="done" id="basicPage">
		<div class="finished">
			<c:choose>
				<c:when test="${submittedForm.flowModeId eq 'tm-opposition'}">
					<p class="success-message">
						<spring:message code="review.success.done" />
						<span class="reviewSeparator"><spring:message
								code="review.success.separator" /></span>
						<spring:message code="review.success.part1.tm-opposition" />
						<strong><spring:message code="review.success.part2.tm-opposition" /></strong>
						<spring:message code="review.success.part3.tm-opposition" />
					</p>
				</c:when>
				<c:when test="${submittedForm.flowModeId eq 'tm-objection'}">
					<p class="success-message">
						<spring:message code="review.success.done" />
						<span class="reviewSeparator"><spring:message
								code="review.success.separator" /></span>
						<spring:message code="review.success.part1" />
						<strong><spring:message code="review.success.part2.tm-objection" /></strong>
						<spring:message code="review.success.part3" />
					</p>
				</c:when>
				<c:otherwise>
					<p class="success-message">
						<spring:message code="review.success.done" />
						<span class="reviewSeparator"><spring:message
								code="review.success.separator" /></span>
						<spring:message code="review.success.part1" />
						<strong><spring:message code="review.success.part2" /></strong>
						<spring:message code="review.success.part3" />
					</p>

				</c:otherwise>
			</c:choose>
			<div class="info-details">
				<a href="<%=request.getContextPath()%>/${submittedForm.flowModeId}.htm"
				   class="download-receipt" target="_blank">
					<spring:message code="review.button.newApp.${submittedForm.flowModeId }" /></a>

				<spring:eval expression="@propertyConfigurer.getProperty('portal.main.page.eservices')" var="portalUrl"/>
				<a href="${portalUrl}"
				   class="download-receipt" target="_blank">
					<spring:message code="review.button.newApp.from.list" /></a>


				<spring:eval expression="@propertyConfigurer.getProperty('appmanagement.application.url')" var="appUrl"/>
				<a href="${appUrl}${submittedForm.provisionalId}"
				   class="download-receipt" target="_blank">
					<spring:message code="review.button.go.to.app" /></a>
				<%--<c:if test="${submittedForm.flowBean.feesForm.totalAmount  >0}">
	                <a
	                        href="javascript:void(0)"
	                        class="download-receipt" onclick="$('#paymentform').submit(); return false;">
	                    <spring:message code="payment.pay" /></a>

				</c:if>--%>
				<a href="<%=request.getContextPath()%>/finalReceipt.htm?uuid=${param.uuid}"
				   class="download-receipt download-receiptButton" ><i class="download-receipt-icon"></i>
					<spring:message code="review.button.download" /></a>
			</div>
		</div>


		<%--<p id="printButton" class="print">
			<button type="button" class="print-receipt">
				<i class="print-receipt-icon"></i>
				<spring:message code="review.button.print" />
			</button>
		</p> --%>

		<div class="info-submission">

			<c:if test="${submittedForm.flowModeId == 'ds-invalidity' }">
				<div class="alert alert-error">
					<spring:message code="ds-invalidity.composition.set.fee.warning"/>
				</div>
			</c:if>

			<c:if test="${submittedForm.flowModeId == 'ds-appeal' }">
				<div class="alert alert-error">
					<spring:message code="ds-appeal.composition.set.fee.warning"/>
				</div>
			</c:if>

			<p><spring:message code="info.submission.part1.${submittedForm.flowModeId }"/></p>
			<p><spring:message code="info.submission.part2.${submittedForm.flowModeId }"/></p>
			<ul>
				<%--<li><spring:message code="info.submission.opt1"/></li>--%>
				<li><spring:message code="info.submission.opt5"/></li>
				<li><spring:message code="info.submission.opt2"/></li>
			</ul>
			<p><spring:message code="info.submission.part3"/></p>
			<ul>
				<%-- <li><spring:message code="info.submission.opt3"/></li>--%>
				<li><spring:message code="info.submission.opt4.${submittedForm.flowModeId }"/></li>
			</ul>
		</div>

	</section>

	<hr class="summary-rule">

	<section class="summary">
		<div class="summary-box">
			<h2>
				<c:choose>
					<c:when test="${submittedForm.flowModeId eq 'tm-opposition'}">
						<spring:message code="review.application.details.tm-opposition" />
					</c:when>
					<c:when test="${submittedForm.flowModeId eq 'tm-objection'}">
						<spring:message code="review.application.details.tm-objection" />
					</c:when>
					<c:otherwise>
						<spring:message code="review.application.details" />

					</c:otherwise>
				</c:choose>

			</h2>
			<dl>
				<dt>
					<spring:message code="review.application.processnumber" />
				</dt>
				<dd>${submittedForm.provisionalId}</dd>
			</dl>
			<dl class="column">
				<dt>
					<spring:message code="review.application.date" />
				</dt>
				<dd>
					<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
					<fmt:formatDate type="date" pattern="${dateFormat}"
									value="${flowBean.mainForm.dateOfSigning}" />
				</dd>
			</dl>
			<dl class="column">
				<dt>
					<spring:message code="review.application.time" />
				</dt>
				<dd>
					<fmt:formatDate type="date" pattern="HH:mm"
									value="${flowBean.mainForm.dateOfSigning}" />
					<span class="gmt-leyend"><fmt:formatDate type="date"
															 pattern="z" value="${flowBean.mainForm.dateOfSigning}" /></span>
				</dd>
			</dl>
		</div>



	</section>

	<hr class="summary-rule">

	<c:set var="service_feedback"
		   value="${configurationServiceDelegator.isServiceEnabled('FeedBack')}" />
	<c:if test="${service_feedback}">
		<section class="feedback">
			<h2>
				<i class="feedback-icon"></i>
				<spring:message code="review.feedback.details" />
			</h2>
			<p>
				<spring:message code="review.feedback.info" />
			</p>
			<c:choose>
				<c:when test="${submittedForm.flowModeId == 'tm-renewal' || submittedForm.flowModeId == 'ds-renewal' ||
								submittedForm.flowModeId == 'tm-transfer' || submittedForm.flowModeId == 'ds-transfer' ||
								submittedForm.flowModeId == 'pt-withdrawal' || submittedForm.flowModeId == 'um-withdrawal' || submittedForm.flowModeId == 'ep-withdrawal' ||
								fn:endsWith(submittedForm.flowModeId, '-change') ||
								fn:endsWith(submittedForm.flowModeId, '-licence') ||
								fn:endsWith(submittedForm.flowModeId, '-compulsorylicence') ||
								fn:endsWith(submittedForm.flowModeId, '-rem') ||
								fn:endsWith(submittedForm.flowModeId, '-security') ||
								fn:endsWith(submittedForm.flowModeId, '-surrender') ||
								fn:endsWith(submittedForm.flowModeId, '-bankruptcy') }">
					<a
							href="<spring:message code='submitted.feedback.email.value.new'/>" target="_blank" class="give-feedback"><spring:message
							code="review.feedback.button" /></a>
				</c:when>
				<c:otherwise>
					<a
							href="<spring:message code='submitted.feedback.email.value'/>" target="_blank" class="give-feedback"><spring:message
							code="review.feedback.button" /></a>
				</c:otherwise>
			</c:choose>
		</section>
	</c:if>


	<!--
	<section class="warning">
		<h2>
			<i class="misleading-icon"></i>
			<spring:message code="review.misleading.details" />
		</h2>
		<p>
			<spring:message code="review.misleading.intro1" />
		</p>
		<p>
			<strong><spring:message code="review.misleading.intro2" /></strong>
		</p>
		<c:set var="misleading_information"
			value="${configurationServiceDelegator.isServiceEnabled('Misleading_Information')}" />
		<c:if test="${misleading_information}">
			<a
				href="${configurationServiceDelegator.getValueFromGeneralComponent('submitted.misleading.url')}"
				target="_blank" class="more-info"><spring:message
					code="review.misleading.moreinformation" /></a>
		</c:if>
	</section>
	  -->
</div>