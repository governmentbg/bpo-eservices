<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="designs_reference" id="designs_reference">
   <header>
        <a href="#designs_reference" class="anchorLink"></a>
        <h2>
            <spring:message code="design.reference"/>
        </h2>
    </header>
    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm formEf" >
        <c:set var="sectionId" value="designs_reference" scope="request"/>
        <div id="reference-container" class="reference-container">
	        <component:input path="mainForm.reference" checkRender="true" id="reference"
	                        labelTextCode="design.reference.yours" formTagClass=""/>

            <component:help-simpletext code="reference.number.help.wizard"/>
	        <%--<div class="reference-info">--%>
	            <%--<ul>--%>
	                <%--<li><component:helpMessage textCode="reference.number.help.wizard" useFlowModeId="false"/></li>--%>
	            <%--</ul>--%>
	        <%--</div>--%>
        </div>

    </form:form>
</section>