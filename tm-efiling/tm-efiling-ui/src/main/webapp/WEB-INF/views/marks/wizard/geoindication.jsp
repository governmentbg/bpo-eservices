<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 4.5.2022 г.
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form modelAttribute="flowBean" cssClass="mainForm" id="formEF" method="POST">
    <c:set var="sectionId" value="geoindication" scope="request"/>
    <header>

        <h3>
            <spring:message code="mark.names.geoindication"/>
        </h3>
    </header>
    <jsp:include page="../wizard_templates/mark_wordelements.jsp"/>
    <jsp:include page="../wizard_templates/mark_description.jsp"/>
    <form:hidden path="mainForm.markType" value="geoindication"/>
</form:form>
