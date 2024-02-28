<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.5.2019 г.
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claimFields">
    <form:form id="pctForm" cssClass="formEF" modelAttribute="pctForm">
        <input type="hidden" id="formReturned" value="true"/>
        <c:set var="sectionId" value="pct" scope="request"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.pct"/>
            </h3>
        </header>
        <ul class="controls" id="topPctButtons">
            <li>
                <a class="cancelButton pct"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addPctButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedPct"/>

        <component:input path="applicationNumber" checkRender="true" id="pctApplicationNumber"
                         labelTextCode="claim.pct.field.applicationNumber"
                         formTagClass="filing-number-input"/>

        <component:input path="applicationDate" checkRender="true" id="pctApplicationDate"
                         labelTextCode="claim.pct.field.applicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="publicationNumber" checkRender="true" id="pctPublicationNumber"
                         labelTextCode="claim.pct.field.publicationNumber"
                         formTagClass="filing-number-input"/>

        <component:input path="publicationDate" checkRender="true" id="pctPublicationDate"
                         labelTextCode="claim.pct.field.publicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="holderName" checkRender="true" id="pctHolderName"
                         labelTextCode="claim.pct.field.holderName"/>

        <component:checkbox path="payedFees" checkRender="true" id="payedFees"
                            labelTextCode="claim.pct.field.payedFees"/>

        <br/>
        <ul class="controls" id="bottomPctButtons">
            <li>
                <a class="cancelButton pct"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addPctButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>