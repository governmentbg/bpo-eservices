<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <h3 class="boxTitle">
        <spring:message code="mark.names.threeDword"/>
    </h3>
</header>

<c:set var="sectionId" value="3dwordmark" scope="request"/>
<jsp:include page="../wizard_templates/mark_views_representation.jsp"/>

<form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">

    <jsp:include page="../wizard_templates/mark_representation.jsp">
        <jsp:param name="helpMessageKey" value="mark.imageFile.attachment.help"/>
        <jsp:param name="helpUseFlowModeId" value="false"/>
    </jsp:include>
    <jsp:include page="../wizard_templates/mark_wordelements.jsp"/>
    <jsp:include page="../wizard_templates/mark_description.jsp"/>
    <jsp:include page="../wizard_templates/mark_description_second.jsp"/>
    <jsp:include page="../wizard_templates/mark_disclaimer.jsp"/>
    <jsp:include page="../wizard_templates/mark_disclaimer_second.jsp"/>
    <jsp:include page="../wizard_templates/mark_transcription.jsp"/>
    <jsp:include page="../wizard_templates/mark_type.jsp"/>
    <jsp:include page="../wizard_templates/mark_series.jsp"/>
    <jsp:include page="../wizard_templates/mark_colormark.jsp"/>

    <form:hidden path="mainForm.markType" value="3dwordmark"/>
</form:form>
