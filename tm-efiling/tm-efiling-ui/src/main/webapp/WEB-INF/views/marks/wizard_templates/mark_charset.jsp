<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:generic path="mainForm.charset" checkRender="true">

    <component:select items="${configurationServiceDelegator['firstLanguages']}"
                      labelTextCode="mark.characterSet"
                      path="mainForm.charset" itemLabel="value"
                      itemValue="code" checkRender="true"/>

    <p class="note"><spring:message code="mark.characterSet.note"/></p>

</component:generic>