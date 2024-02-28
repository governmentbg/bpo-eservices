<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 1.8.2019 г.
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="${sectionId}" scope="request" />

<form:form id="addMarkViewForm" commandName="markViewForm">
    <form:hidden path="id"/>
    <form:hidden path="imported"/>

    <component:input path="title" checkRender="true" labelTextCode="mark.form.view.modal.field.title"
                        id="markTitle" formTagClass="mark-view-input-large"/>

    <component:input path="imageNumber" id="markImageNumber" checkRender="true" labelTextCode="mark.form.view.modal.field.imageNumber"
                     formTagClass="mark-view-input-small"/>

</form:form>