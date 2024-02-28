<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<footer class="n-container notmdn-footer">
	<div class="footer-box fb1-notmdn">
		<h4><spring:message code="layout.footer.about.title"/></h4>
		<!-- <p><spring:message code="layout.footer.about.description"/></p> -->
		<div>
			<p>
				<a href="<spring:message code='layout.footer.about.more'/>"><spring:message code="layout.footer.about.description"/></a>&nbsp;
			</p>
			<%--  <a><spring:message code="layout.footer.about.procurements"/></a>--%>
			<%--<a><spring:message code="layout.footer.about.jobs"/></a>--%>

			<p>
				<a href="mailto:<spring:message code='layout.footer.contact.email.value'/>"><spring:message code="layout.footer.contact.write"/></a>
			</p>

		</div>
	</div>
	<div class="footer-box fb2-notmdn">
		<h4><spring:message code="layout.footer.contact.title"/></h4>
		<address>
			<p>
				<spring:message code="layout.footer.contact.address.line1"/>,<spring:message code="layout.footer.contact.address.line2"/>,<spring:message code="layout.footer.contact.address.line3"/><br>
				<spring:message code="layout.footer.contact.infoCenterNumber"/></p>
		</address>
	</div>
	<div class="footer-box fb3-notmdn">
		<h4><spring:message code="layout.footer.project.title"/></h4>
		<p><spring:message code="layout.footer.project.description"/></p>

		<img class="notmdn" src="<spring:message code='layout.footer.project.img1'/>" />
		<img class="notmdn" src="<spring:message code='layout.footer.project.img2'/>" />
	</div>
</footer>
	
