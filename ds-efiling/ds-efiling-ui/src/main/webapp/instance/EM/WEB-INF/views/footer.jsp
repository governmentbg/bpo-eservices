<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<footer class="n-container">
		<div class="footer-box fb1">
			<h4><spring:message code="layout.footer.about.title"/></h4>
			<p><spring:message code="layout.footer.about.description"/></p>
			<div class="row-fluid">
				<a href="about-ohim"><spring:message code="layout.footer.about.more"/></a>&nbsp;
				<a><spring:message code="layout.footer.about.procurements"/></a>
				<a><spring:message code="layout.footer.about.jobs"/></a>
			</div>
		</div>
		<div class="footer-box fb2">
			<h4><spring:message code="layout.footer.contact.title"/></h4>
			<address>
				<p>
					<spring:message code="layout.footer.contact.address.line1"/>,<spring:message code="layout.footer.contact.address.line2"/>,<spring:message code="layout.footer.contact.address.line3"/><br>
					<spring:message code="layout.footer.contact.infoCenterNumber"/></p>
				<a href="mailto:<spring:message code="layout.footer.contact.email.value"/>"><spring:message code="layout.footer.contact.email"/></a></address>
		</div>
		<div class="footer-box fb3">
			<h4><spring:message code="layout.footer.cooperationFund.title" /></h4>
			<p><spring:message code="layout.footer.cooperationFund.description" /></p>
			<img src="resources/img/cf-logo.png">	
		</div>
	</footer>
	
	
