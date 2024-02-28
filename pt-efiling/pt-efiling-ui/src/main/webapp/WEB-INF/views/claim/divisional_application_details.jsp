<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claimFields">


    <form:form id="divisionalApplicationForm" cssClass="formEF" modelAttribute="divisionalApplicationForm">
        <input type="hidden" id="formReturned" value="true"/>
        <c:set var="sectionId" value="divisionalApplicationSection" scope="request"/>
        <c:set var="imported" value="${divisionalApplicationForm.imported}" scope="request"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.divisionalApplication.${flowModeId}"/>
            </h3>
        </header>
        <ul class="controls" id="topDivisionalApplicationButtons">
            <li>
                <a class="cancelButton divisionalApplication"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addDivisionalApplicationButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedDivisionalApplication"/>

        <component:input path="dateDivisionalApplication" checkRender="true" id="dateDivisionalApplication"
                         labelTextCode="claim.divisionalApplication.field.onWhatDate"
                         formTagClass="filing-date-input"/>
        <component:input path="numberDivisionalApplication" checkRender="true" id="numberDivisionalApplication"
                         labelTextCode="claim.divisionalApplication.field.filingNumber"
                         formTagClass="filing-number-input"/>
        <br>
        <span class="tip"><spring:message code="claim.divisionalApplication.import.hint.${flowModeId}"/></span>

        <br/>
        <ul class="controls" id="bottomDivisionalApplicationButtons">
            <li>
                <a class="cancelButton divisionalApplication"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addDivisionalApplicationButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>

