<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="showPowerOfAttorney" value="true"/>
<c:set var="showGeneralPowerOfAttorney" value="true"/>
<c:set var="showExplanationOfGrounds" value="false"/>
<c:set var="showProposalToDecide" value="false"/>
<c:set var="showEvidence" value="false"/>
<c:set var="showRepresentationOfATrademark" value="false"/>
<c:set var="showOtherDocument" value="false"/>
<c:set var="showReputationClaim" value="false"/>

<!--  TODO
<c:if test="${not empty configurationServiceDelegator.getRepresentativeTypes(flowModeId)}">
    <c:forEach var="section_item" items="${configurationServiceDelegator.getRepresentativeTypes(flowModeId)}">
        <c:choose>
            <c:when test="${section_item eq 'representative_employeerepresentative'}">
                <c:set var="showRepresentativeEmployeeRepresentative" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_legalpractitioner'}">
                <c:set var="showRepresentativeLegalPractitioner" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_professionalpractitioner'}">
                <c:set var="showRepresentativeProfessionalPractitioner" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_legalentity'}">
                <c:set var="showRepresentativeLegalEntity" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_naturalperson'}">
                <c:set var="showRepresentativeNaturalPerson" value="true"/>
            </c:when>
        </c:choose>
    </c:forEach>
</c:if>
 -->
 

<div id="repfiletypeholder">
    <label><spring:message code="representative.form.representativeDocumentType"/></label>
    <select name="fileWrapper.docType" id="attachmentType">
    	<!-- <option value=""><spring:message code="representative.form.action.select"/></option>  -->
        <c:if test="${showPowerOfAttorney}">
            <option value="POWER_OF_ATTORNEY">
                <spring:message code="representative.powerOfAttorney.type"/></option>
        </c:if>
        <c:if test="${showGeneralPowerOfAttorney}">
            <option value="GENERAL_POWER_OF_ATTORNEY">
                <spring:message code="representative.generalPowerOfAttorney.type"/></option>
        </c:if>
    </select>
   </div>