<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

    <c:set var="sectionId" value="personChange" scope="request"/>

    <input type="hidden" id="formReturned" value="true"/>

    <form:hidden id="hiddenFormPreviousPersonId" path="previousPersonId"/>
<%--
    <c:choose>
        <c:when test="${not empty param.id}">
            <form:hidden id="hiddenFormPreviousPersonId" path="previousPersonId" value="${param.id}"/>
        </c:when>
        <c:otherwise>
            <form:hidden id="hiddenFormPreviousPersonId" path="previousPersonId"/>
        </c:otherwise>
    </c:choose>
--%>

    <component:input path="previousPersonName" checkRender="true"
                        labelTextCode="personChange.person.field.previousPersonName"/>
    <component:input path="previousPersonAddress" checkRender="true"
                        labelTextCode="personChange.person.field.previousPersonAddress"/>