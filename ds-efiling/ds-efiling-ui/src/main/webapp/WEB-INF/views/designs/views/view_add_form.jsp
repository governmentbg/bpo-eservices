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

<c:set var="sectionId" value="designViews" scope="request" />

<form:form id="addDesignViewForm" commandName="designViewForm">
    <form:hidden path="id"/>
    <form:hidden path="imported"/>

    <component:textarea path="description" checkRender="false" labelTextCode="design.form.view.modal.field.description" formTagClass="design-view-textarea"
                        id="description" />

    <component:checkbox path="publishInColour" id="publishInColour" checkRender="true" labelTextCode="design.form.view.modal.field.publishInColour" />

    <component:checkbox path="publishInBlackWhite" id="publishInBlackWhite" checkRender="true" labelTextCode="design.form.view.modal.field.publishInBlackAndWhite" />

    <component:select items="${configurationServiceDelegator['designViewPublicationSizes']}" path="publicationSize" itemLabel="value"
                      itemValue="code" checkRender="true" labelTextCode="design.form.view.modal.field.publicationSize" id="publicationSize"/>

    <component:select items="${configurationServiceDelegator['designViewTypes']}" path="type" itemLabel="value"
                      itemValue="code" checkRender="true" labelTextCode="design.form.view.modal.field.type" id="type"/>
</form:form>