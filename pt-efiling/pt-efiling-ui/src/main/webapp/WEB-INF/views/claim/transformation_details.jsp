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
    <form:form id="transformationForm" cssClass="formEF" modelAttribute="transformationForm">
        <input type="hidden" id="formReturned" value="true"/>

        <jsp:include page="transformation_section_set.jsp"/>

        <header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

            <h3>
                <spring:message code="claim.title.${sectionId}"/>
            </h3>
        </header>
        <ul class="controls" id="topTransformationButtons">
            <li>
                <a class="cancelButton transformation"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addTransformationButton" type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedTransformation"/>
        <form:hidden path="countryCode" value="${not empty country ? country : transformationForm.countryCode }" id="transformationCountry"/>
        <c:set var="imported" value="${transformationForm.imported}" scope="request"/>

        <component:input path="applicationNumber" checkRender="true" id="transformationApplicationNumber"
                         labelTextCode="claim.${sectionId}.field.applicationNumber"
                         formTagClass="filing-number-input"/>

        <c:if test="${sectionId  == 'national_transformation'}">
            <br>
            <span class="tip"><spring:message code="claim.national_transformation.import.hint"/></span>
        </c:if>
        <c:if test="${sectionId  == 'conversion'}">
            <br>
            <span class="tip"><spring:message code="claim.conversion.import.hint"/></span>
        </c:if>


        <component:input path="applicationDate" checkRender="true" id="transformationApplicationDate"
                         labelTextCode="claim.${sectionId}.field.applicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="publicationNumber" checkRender="true" id="transformationPublicationNumber"
                         labelTextCode="claim.${sectionId}.field.publicationNumber"
                         formTagClass="filing-number-input"/>

        <component:input path="publicationDate" checkRender="true" id="transformationublicationDate"
                         labelTextCode="claim.${sectionId}.field.publicationDate"
                         formTagClass="filing-date-input"/>

        <component:input path="holderName" checkRender="true" id="transformationHolderName"
                         labelTextCode="claim.${sectionId}.field.holderName"/>

        <component:checkbox path="payedFees" checkRender="true" id="payedFees"
                            labelTextCode="claim.${sectionId}.field.payedFees"/>

        <br/>
        <ul class="controls" id="bottomTransformationButtons">
            <li>
                <a class="cancelButton transformation"><spring:message code="claim.form.action.cancel"/></a>
            </li>
            <li>
                <button class="addTransformationButton"  type="button">
                    <spring:message code="claim.form.action.save" />
                </button>
            </li>
        </ul>
        <br>

    </form:form>
</div>