<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row">
	<div class="span6">
		<img src="<%=request.getContextPath()%>/resources/img/fakevid.gif" alt="Video" class="flAlignLeft"/>
	</div>
	<div class="span6">
		<ul class="wizardSteps">
		    <li class="step1"><spring:message code="homepage.option1"/></li>
		    <li class="step2"><spring:message code="homepage.option2"/></li>
		    <li class="step3"><spring:message code="homepage.option3"/></li>
		    <li class="step4"><spring:message code="homepage.option4"/></li>
		</ul>
	</div>
</div>
