<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claimFields">
    <form:form id="exhibitionForm" cssClass="formEF" modelAttribute="exhibitionForm">
        <input type="hidden" id="formReturned" value="true"/>
        <c:set var="sectionId" value="exhibition" scope="request"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.exhibition"/>
            </h3>
        </header>
        <div class="tip">
            <spring:message code="claim.title.exhibition.hint"/>
        </div>
        <ul class="controls" id="topExhibitionButtons">
            <li>
                <a class="cancelButton exhibition"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addExhibitionButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedExhibition"/>

        <component:input path="exhibitionName" checkRender="true" id="exhibitionName"
                         labelTextCode="claim.exhibition.field.name" formTagClass="name-input"/>

        <component:input path="firstDate" checkRender="true" id="dateExhibition"
                         labelTextCode="claim.exhibition.field.date"
                         formTagClass="filing-date-input"/>

        <br/>
        <ul class="controls" id="bottomExhibitionButtons">
            <li>
                <a class="cancelButton exhibition"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addExhibitionButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>

