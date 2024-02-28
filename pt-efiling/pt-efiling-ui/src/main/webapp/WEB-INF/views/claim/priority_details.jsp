
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claimFields">
    <form:form id="priorityForm" cssClass="formEF" modelAttribute="priorityForm">
        <input type="hidden" id="formReturned" value="true"/>
        <c:set var="sectionId" value="priority" scope="request"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.priority"/>
            </h3>
        </header>
        <ul class="controls" id="topPriorityButtons">
            <li>
                <a class="cancelButton priority"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addPriorityButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedPriority"/>

        <component:select
                items="${configurationServiceDelegator['countries']}"
                labelTextCode="claim.priority.field.countryOfLastFiling"
                path="country" itemLabel="value" itemValue="code"
                itemFilter="isPriority" checkRender="true"/>

        <component:input path="date" checkRender="true" id="wDate"
                         labelTextCode="claim.priority.field.onWhatDate"
                         formTagClass="filing-date-input"/>
        <component:input path="number" checkRender="true" id="fNumber"
                         labelTextCode="claim.priority.field.filingNumber"
                         formTagClass="filing-number-input"/>

        <div id="filePriorityCertificate"
             class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.priority.certificate"
                            pathFilename="filePriorityCertificate"
                            fileWrapperPath="filePriorityCertificate"
                            fileWrapper="${priorityForm.filePriorityCertificate}"
            helpMessageKey="claim.priority.certificate.hint"/>
        </div>

        <br/>
        <ul class="controls" id="bottomPriorityButtons">
            <li>
                <a class="cancelButton priority"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addPriorityButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>