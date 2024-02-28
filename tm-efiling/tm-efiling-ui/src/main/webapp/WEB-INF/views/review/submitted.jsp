<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="main" role="main">
	<section class="done" id="basicPage">
		<div class="finished">
			<p class="success-message">
				<spring:message code="review.success.done" />
				<span class="reviewSeparator"><spring:message
						code="review.success.separator" /></span>
				<spring:message code="review.success.part1" />
				<strong><spring:message code="review.success.part2" /></strong>
				<spring:message code="review.success.part3" />
			</p>
			<div class="info-details">
				<a href="./"
				   class="download-receipt">
					<spring:message code="submitted.new.application" /></a>

				<spring:eval expression="@propertyConfigurer.getProperty('appmanagement.application.url')" var="appUrl"/>
				<a href="${appUrl}${submittedForm.provisionalId}"
				   class="download-receipt" target="_blank">
					<spring:message code="review.button.go.to.app" /></a>

				<a href="finalReceipt.htm?uuid=${param.uuid}" class="download-receipt">
					<i class="download-receipt-icon"></i>
					<spring:message code="review.button.download" />
				</a>
			</div>
		</div>

		<p id="printButton" class="print">
			<button type="button" class="print-receipt">
				<i class="print-receipt-icon"></i>
				<spring:message code="review.button.print" />
			</button>
		</p>
		<div class="info-submission">
              <p><spring:message code="info.submission.part1"/></p>
              <p><spring:message code="info.submission.part2"/></p>
              <ul>
                  <!--<li><spring:message code="info.submission.opt1"/></li>-->
                  <li><spring:message code="info.submission.opt2"/></li>
              </ul>
        </div>
	</section>

	<hr class="summary-rule">

	<section class="summary">
		<div class="summary-box">
			<h2>
				<spring:message code="review.application.details" />
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

		<%-- <c:if test="${not empty submittedForm.flowBean.feesForm.feeLineFormList}">
			<div class="summary-box">
				<h2>
					<spring:message code="review.payment.details" />
				</h2>
				<dl class="column">
					<dt>
						<spring:message code="review.payment.method" />
					</dt>
					<c:if test="${not empty submittedForm.paymentMethod}">
						<dd><spring:message code="review.payment.method.${submittedForm.paymentMethod}"/></dd>
					</c:if>
				</dl>
				<dl class="column">
					<dt>
						<spring:message code="review.payment.transaction" />
					</dt>
					<dd>${submittedForm.paymentId}</dd>
				</dl>
			</div>
		</c:if> --%>

	</section>
    <!--
	<hr class="summary-rule">

	<section class="follow-up">
		<h2>
			<i class="what-now-icon"></i>
			<spring:message code="review.what.details" />
		</h2>
		<img class="what-now-video" src="resources/img/what-now.png">
		<c:set var="communication_inbox"
			value="${configurationServiceDelegator.isServiceEnabled('Communication_Inbox')}" />
		<c:if test="${communication_inbox}">
			<a
				href="${configurationServiceDelegator.getValue('submitted.communication.url', 'tmefiling')}"
				target="_blank" class="check-inbox"><spring:message
					code="review.what.communication" /></a>
		</c:if>
	</section>
	-->

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
			<a
				href="<spring:message code='submitted.feedback.email.value'/>"
				target="_blank" class="give-feedback"><spring:message
					code="review.feedback.button" /></a>
		</section>
	</c:if>
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
				href="${configurationServiceDelegator.getValue('submitted.misleading.url', 'tmefiling')}"
				target="_blank" class="more-info"><spring:message
					code="review.misleading.moreinformation" /></a>
		</c:if>
	</section>
	<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
		/trademark/register-trademark/submitted
	</div>
</div>