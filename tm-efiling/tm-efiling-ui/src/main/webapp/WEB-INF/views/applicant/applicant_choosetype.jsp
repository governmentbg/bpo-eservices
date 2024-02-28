<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<ul class="controls">
    <li>
        <a class="cancelButton applicant"><spring:message
                code="applicant.form.action.cancelAdd.top"/></a>
    </li>
    <li>
        <button id="addApplicantTopButton" disabled="disabled" type="button">
            <i class="add-icon"/>
            <spring:message code="applicant.form.action.add.top"/>
        </button>
    </li>
</ul>

<c:set var="showApplicantLegalEntity" value="false"/>
<c:set var="showApplicantNaturalPerson" value="false"/>
<c:set var="showApplicantNaturalPersonSpecial" value="false"/>
<c:if test="${not empty configurationServiceDelegator.getApplicantTypes(flowModeId)}">
    <c:forEach var="section_item" items="${configurationServiceDelegator.getApplicantTypes(flowModeId)}">
        <c:if test="${section_item eq 'applicant_legalentity'}">
            <c:set var="showApplicantLegalEntity" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'applicant_naturalperson'}">
            <c:set var="showApplicantNaturalPerson" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'applicant_naturalpersonspecial'}">
            <c:set var="showApplicantNaturalPersonSpecial" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<span class="importErrorPlaceholder"></span>

<c:set var="sectionId" value="applicant" scope="request"/>
<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.applicant.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">
<input type="hidden" id="service_applicantMatches_enabled"
       value="${configurationServiceDelegator.isServiceEnabled('Applicant_Match')}"/>
<component:generic path="type" checkRender="true">
    <label><spring:message code="applicant.form.applicantType"/><span class="requiredTag">*</span></label>
    <select name="aptype" id="aptype">
        <option value=""><spring:message code="applicant.form.action.select"/></option>
        <c:if test="${showApplicantLegalEntity}">
            <option value="LEGAL_ENTITY"><spring:message code="applicant.legalEntity.type"/></option>
        </c:if>
        <c:if test="${showApplicantNaturalPerson}">
            <option value="NATURAL_PERSON"><spring:message code="applicant.naturalPerson.type"/></option>
        </c:if>
        <c:if test="${showApplicantNaturalPersonSpecial}">
            <option value="NATURAL_PERSON_SPECIAL"><spring:message code="applicant.naturalPersonSpecialCase.type"/></option>
        </c:if>
    </select>
</component:generic>