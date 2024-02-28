<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="certificateRequested" id="certificateRequested">
    <c:set var="sectionId" value="certificate_requested" scope="request" />
    <header>
        <a href="#certificateRequested" class="anchorLink">
        </a>

        <h2>
            <spring:message code="registrationCertificate.title"/>
        </h2>
    </header>

    <form:form class="mainForm formEf" modelAttribute="flowBean">

        <component:checkbox path="certificateRequestedIndicator" checkRender="true" id="certificateRequestedIndicator"
                            labelTextCode="registrationCertificate.field.certificateRequestedIndicator"/>
        <div class="tip">
            <spring:message code="registrationCertificate.field.certificateRequestedIndicator.tip"/>
        </div>

    </form:form>
</section>

