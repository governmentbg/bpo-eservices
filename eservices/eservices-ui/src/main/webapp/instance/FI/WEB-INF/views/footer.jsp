<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<footer>
    <p><spring:message code="layout.footer.about.description"/></p>
    <br/>

    <p><spring:message code="layout.footer.contact.title"/></p>
    <p><spring:message code="layout.footer.contact.address.line1"/></p>
    <p><spring:message code="layout.footer.contact.address.line2"/></p>
    <p><spring:message code="layout.footer.contact.address.line3"/></p>
    <p><spring:message code="layout.footer.contact.infoCenterNumber"/></p>
    <p><a href="<spring:message code="layout.footer.contact.email.value"/>" target="_blank"><spring:message
            code="layout.footer.contact.email.value"/></a></p>
</footer>