<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claimFields">
    <form:form id="parallelApplicationForm" cssClass="formEF" modelAttribute="parallelApplicationForm">
        <input type="hidden" id="formReturned" value="true"/>

        <jsp:include page="parallel_application_section_set.jsp"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.${sectionId}"/>
            </h3>
        </header>
        <ul class="controls" id="topParallelApplicationButtons">
            <li>
                <a class="cancelButton parallelApplication"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addParallelApplicationButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedParallelApplication"/>
        <form:hidden path="countryCode" value="${not empty country ? country : parallelApplicationForm.countryCode }" id="parallelApplicationCountry"/>
        <c:set var="imported" value="${parallelApplicationForm.imported}" scope="request"/>

        <component:input path="applicationNumber" checkRender="true" id="parallelApplicationApplicationNumber"
                         labelTextCode="claim.${sectionId}.field.applicationNumber"
                         formTagClass="filing-number-input"/>

        <c:if test="${sectionId  == 'national_parallel_application'}">
            <br>
            <span class="tip"><spring:message code="claim.national_parallel_application.import.hint"/></span>
        </c:if>
        <c:if test="${sectionId  == 'european_parallel_application'}">
            <br>
            <span class="tip"><spring:message code="claim.european_parallel_application.import.hint"/></span>
        </c:if>

        <component:input path="applicationDate" checkRender="true" id="parallelApplicationApplicationDate"
                         labelTextCode="claim.${sectionId}.field.applicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="publicationNumber" checkRender="true" id="parallelApplicationPublicationNumber"
                         labelTextCode="claim.${sectionId}.field.publicationNumber"
                         formTagClass="filing-number-input"/>

        <component:input path="publicationDate" checkRender="true" id="parallelApplicationPublicationDate"
                         labelTextCode="claim.${sectionId}.field.publicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="holderName" checkRender="true" id="parallelApplicationHolderName"
                         labelTextCode="claim.${sectionId}.field.holderName"/>

        <br/>
        <ul class="controls" id="bottomParallelApplicationButtons">
            <li>
                <a class="cancelButton parallelApplication"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addParallelApplicationButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>