<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="reference">
    <header>
        <a id="security_measure" class="anchorLink"></a>
        <h2>
            <spring:message code="security.measure"/>
        </h2>
    </header>
    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
    	<c:set var="sectionId" value="security_measure" scope="request"/>
    	
        <component:checkbox path="securityMeasure.forbidUse" checkRender="true" id="forbidUse"
                        labelTextCode="${flowModeId}.security.measure.forbid.use" labelClass="use-reference"/>
                        
		<component:checkbox path="securityMeasure.forbidManage" checkRender="true" id="forbidManage"
                        labelTextCode="${flowModeId}.security.measure.forbid.manage" labelClass="use-reference"/>
		                        
    </form:form>
</section>