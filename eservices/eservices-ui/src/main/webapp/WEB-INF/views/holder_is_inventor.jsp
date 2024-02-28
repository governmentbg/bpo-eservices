<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="reference">

    <a id="holder_is_inventor" class="anchorLink"></a>

    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
        <c:set var="sectionId" value="holder_is_inventor" scope="request"/>
        <component:checkbox path="holderIsInventor" checkRender="true" id="holderIsInventor"
                            labelTextCode="${flowModeId}.holder.is.inventor" labelClass="use-reference"/>

    </form:form>
</section>