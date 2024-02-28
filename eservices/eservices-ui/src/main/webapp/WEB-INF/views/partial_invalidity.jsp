<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="reference">

    <a id="partial_invalidity" class="anchorLink"></a>

    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
        <c:set var="sectionId" value="partial_invalidity" scope="request"/>
<%--        <component:checkbox path="partialInvalidity" checkRender="true" id="partialInvalidity"--%>
<%--                            labelTextCode="${flowModeId}.invalidity.kind" labelClass="use-reference"/>--%>

        <label><spring:message code="${flowModeId}.invalidity.kind"/></label>
        <component:radiobutton id ="partialInvalidity" path="partialInvalidity" checkRender="true" value="true" labelTextCode="review.partial.invalidity.${flowModeId}" />
        <component:radiobutton id ="fullInvalidity" path="partialInvalidity" checkRender="true" value="false" labelTextCode="review.full.invalidity.${flowModeId}"/>


    </form:form>
</section>