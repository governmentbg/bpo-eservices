<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 21.10.2019 г.
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<section id="application_kind" class="patent">
    <header>
        <a id="application_kind_section" class="anchorLink">
        </a>

        <h2><spring:message code="application.kind.title"></spring:message></h2>
    </header>

    <form:form id="applicationKindForm" class="mainForm formEF" modelAttribute="flowBean" method="POST">
        <c:set var="sectionId" value="application_kind" scope="request"/>

        <c:forEach var="kind" items="${configurationServiceDelegator.getPatentApplicationKinds(flowModeId)}">
            <component:radiobutton path="mainForm.applicationKind" value="${kind.name}" labelTextCode="${kind.key}"/>
        </c:forEach>
    </form:form>

</section>