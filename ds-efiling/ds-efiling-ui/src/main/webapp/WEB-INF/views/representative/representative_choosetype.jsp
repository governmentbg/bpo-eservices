<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="controls">
    <li>
        <a class="cancelButton representative"><spring:message
                code="representative.form.action.cancelAdd.top"/></a>
    </li>
    <li>
        <button id="addRepresentativeTopButton" disabled="disabled" type="button">
        	<spring:message code="representative.form.action.save" />
        	<%--
            <i class="add-icon"/>
            <spring:message code="representative.form.action.add.top"/>
            --%>
        </button>
    </li>
</ul>

<%--<ul class="flAlignRight flInlineList">--%>
<%--<li><a id="addRepresentativeTopButton" disabled="disabled" class="btn bgAdd addRepresentative"><spring:message code="representative.form.action.add.top"/></a></li>--%>
<%--<li><a class="cancelButton representative" ><spring:message code="representative.form.action.cancelAdd.top"/></a>--%>
<%--</li>--%>
<%--</ul>--%>

<c:set var="showRepresentativeEmployeeRepresentative" value="false"/>
<c:set var="showRepresentativeLegalPractitioner" value="false"/>
<c:set var="showRepresentativeProfessionalPractitioner" value="false"/>
<c:set var="showRepresentativeLegalEntity" value="false"/>
<c:set var="showRepresentativeNaturalPerson" value="false"/>
<c:set var="showRepresentativeAssociation" value="false"/>

<c:if test="${not empty configurationServiceDelegator.getRepresentativeTypes(flowModeId)}">
    <c:forEach var="section_item" items="${configurationServiceDelegator.getRepresentativeTypes(flowModeId)}">
    
        <c:choose>
			<%--        	
            <c:when test="${section_item eq 'representative_naturalperson'}">
                <c:set var="showRepresentativeNaturalPerson" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_legalentity'}">
                <c:set var="showRepresentativeLegalEntity" value="true"/>
            </c:when>
            --%>
            <c:when test="${section_item eq 'representative_naturalperson'}">
                <c:set var="showRepresentativeNaturalPerson" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_legalentity'}">
                <c:set var="showRepresentativeLegalEntity" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_employeerepresentative'}">
                <c:set var="showRepresentativeEmployeeRepresentative" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_legalpractitioner'}">
                <c:set var="showRepresentativeLegalPractitioner" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_professionalpractitioner'}">
                <c:set var="showRepresentativeProfessionalPractitioner" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_association'}">
                <c:set var="showRepresentativeAssociation" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_lawyercompany'}">
                <c:set var="showRepresentativeLawyerCompany" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_lawyerassociation'}">
                <c:set var="showRepresentativeLawyerAssociation" value="true"/>
            </c:when>
            <c:when test="${section_item eq 'representative_temporary'}">
                <c:set var="showRepresentativeTemporary" value="true"/>
            </c:when>
        </c:choose>
    </c:forEach>
</c:if>

<span class="importErrorPlaceholder"></span>


<c:set var="sectionId" value="representative" scope="request"/>
<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.representative.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">
<input type="hidden" id="service_representativeMatches_enabled"
       value="${configurationServiceDelegator.isServiceEnabled('Representative_Match')}"/>
<component:generic path="type" checkRender="true">
    <label><spring:message code="representative.form.representativeType"/><span class="requiredTag">*</span></label>
    <select name="reptype" id="reptype">
        <option value=""><spring:message code="representative.form.action.select"/></option>
        <c:if test="${showRepresentativeNaturalPerson}">
            <option value="NATURAL_PERSON">
                <spring:message code="representative.naturalPerson.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeLegalEntity}">
            <option value="LEGAL_ENTITY">
                <spring:message code="representative.legalEntity.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeEmployeeRepresentative}">
            <option value="EMPLOYEE_REPRESENTATIVE">
                <spring:message code="representative.employeeRepresentative.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeLegalPractitioner}">
            <option value="LEGAL_PRACTITIONER">
                <spring:message code="representative.legalPractitioner.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeProfessionalPractitioner}">
            <option value="PROFESSIONAL_PRACTITIONER">
                <spring:message code="representative.professionalPractitioner.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeAssociation}">
            <option value="ASSOCIATION">
                <spring:message code="representative.association.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeLawyerCompany}">
            <option value="LAWYER_COMPANY">
                <spring:message code="representative.lawyercompany.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeLawyerAssociation}">
            <option value="LAWYER_ASSOCIATION">
                <spring:message code="representative.lawyerassociation.type"/></option>
        </c:if>
        <c:if test="${showRepresentativeTemporary}">
            <option value="TEMPORARY_REPRESENTATIVE">
                <spring:message code="representative.temporary.type"/></option>
        </c:if>
    </select>
</component:generic>