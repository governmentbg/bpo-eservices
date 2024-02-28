<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="reference" id="markreference">
    <header>
        <a id="markreference" class="anchorLink"></a>
        <h2>
            <spring:message code="mark.reference"/>
        </h2>
    </header>
    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
        <c:set var="sectionId" value="markreference" scope="request"/>
        <component:checkbox path="useReference" checkRender="true" id="useReference"
                        labelTextCode="mark.reference.use" labelClass="use-reference"/>
        <component:generic checkRender="true" path="useReference">
            <c:if test="${!flowBean.useReference}">
                <c:set var="referenceContainerClass" value="hidden"/>
            </c:if> 
        </component:generic>
        <div id="reference-container" class="${referenceContainerClass}">
            <div class="reference-fields">
	        <component:input path="reference" checkRender="true" id="reference"
	                        labelTextCode="mark.reference.yours" formTagClass=""/>
            </div>
	        <div class="reference-info">
	            <ul>
	                <li><component:helpMessage textCode="reference.number.help"
	                       useFlowModeId="true"/></li>
	            </ul>
	        </div>
        </div>

    </form:form>
</section>
