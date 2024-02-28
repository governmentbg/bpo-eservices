<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<ul class="controls">
    <li>
        <a class="cancelButton assignee"><spring:message
                code="assignee.form.action.cancelAdd.top"/></a>
    </li>
    <li>
        <button id="addAssigneeTopButton" disabled="disabled" type="button">
            <i class="add-icon"/>
            <spring:message code="assignee.form.action.add.top"/>
        </button>
    </li>
</ul>

<c:set var="showAssigneeLegalEntity" value="false"/>
<c:set var="showAssigneeNaturalPerson" value="false"/>
<c:set var="showAssigneeNaturalPersonSpecial" value="false"/>

<c:set var="types" value="${functions:getPersonDataTypesBySectionId(configurationServiceDelegator, flowModeId)}" scope="request"/>

<c:if test="${not empty types}">
    <c:forEach var="section_item" items="${types}">
        <c:if test="${section_item eq 'assignee_legalentity'}">
            <c:set var="showAssigneeLegalEntity" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'assignee_naturalperson'}">
            <c:set var="showAssigneeNaturalPerson" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'assignee_naturalpersonspecial'}">
            <c:set var="showAssigneeNaturalPersonSpecial" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<span class="importErrorPlaceholder"></span>

<c:set var="sectionId" value="${functions:getPersonDataSetionId(flowModeId)}" scope="request"/>

<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.assignee.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">
<input type="hidden" id="service_assigneeMatches_enabled"
       value="${configurationServiceDelegator.isServiceEnabled('Assignee_Match')}"/>
<component:generic path="type" checkRender="true">
    <label><spring:message code="assignee.form.assigneeType.${flowModeId}"/><span class="requiredTag">*</span></label>
    <select name="astype" id="astype">
        <option value=""><spring:message code="assignee.form.action.select"/></option>
        <c:if test="${showAssigneeLegalEntity}">
            <option value="LEGAL_ENTITY"><spring:message code="assignee.legalEntity.type"/></option>
        </c:if>
        <c:if test="${showAssigneeNaturalPerson}">
            <option value="NATURAL_PERSON"><spring:message code="assignee.naturalPerson.type"/></option>
        </c:if>
        <c:if test="${showAssigneeNaturalPersonSpecial}">
            <option value="NATURAL_PERSON_SPECIAL"><spring:message code="assignee.naturalPersonSpecialCase.type"/></option>
        </c:if>
    </select>
</component:generic>