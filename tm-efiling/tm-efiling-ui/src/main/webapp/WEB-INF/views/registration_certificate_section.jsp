<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 20.5.2020 г.
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="attachments" id="registrationCertificateSection">
    <header>
        <h2><spring:message code="registrationCertificate.title"/></h2>
    </header>
    <c:set var="sectionId" value="registration_certificate" scope="request"/>

    <form:form class="mainForm formEf" modelAttribute="flowBean">
        <component:checkbox path="certificateRequestedIndicator" checkRender="true" id="certificateRequestedIndicator"
                            labelTextCode="registrationCertificate.field.certificateRequestedIndicator"/>
        <div class="tip">
            <spring:message code="registrationCertificate.field.certificateRequestedIndicator.tip"/>
        </div>
    </form:form>
</section>