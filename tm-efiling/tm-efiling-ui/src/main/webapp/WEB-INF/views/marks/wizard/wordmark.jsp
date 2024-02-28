<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form modelAttribute="flowBean" cssClass="mainForm" id="formEF" method="POST">
    <c:set var="sectionId" value="wordmark" scope="request"/>
    <header>

        <h3>
            <spring:message code="mark.names.word"/>
        </h3>
    </header>

    <jsp:include page="../wizard_templates/mark_charset.jsp"/>
    <jsp:include page="../wizard_templates/mark_wordelements.jsp"/>
    <jsp:include page="../wizard_templates/mark_description.jsp"/>
    <jsp:include page="../wizard_templates/mark_description_second.jsp"/>
    <jsp:include page="../wizard_templates/mark_disclaimer.jsp"/>
    <jsp:include page="../wizard_templates/mark_disclaimer_second.jsp"/>
    <jsp:include page="../wizard_templates/mark_transcription.jsp"/>
    <jsp:include page="../wizard_templates/mark_type.jsp"/>
    <jsp:include page="../wizard_templates/mark_series.jsp"/>

    <form:hidden path="mainForm.markType" value="wordmark"/>
</form:form>
