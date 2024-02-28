<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="main" role="main">
	<section id="basicPage">
		<div id="centerCtn" class="noCol welcomePage">
			<h2 class="flContentHeader">
				<spring:message code="homepage.mainTitle" />
			</h2>
			<form:form id="formEF" modelAttribute="flowBean" method="POST" cssClass="flForm formEF mainForm">
				<input type="hidden" name="_eventId" value="Next" id="hiddenElement" />
				<%--<tiles:insertAttribute name="video" />--%>
				<tiles:insertAttribute name="usage" />
				<tiles:insertAttribute name="lang_start_restore" />
			</form:form>
		</div>
	</section>
	<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
		/design/register-design/begin
	</div>
</div>