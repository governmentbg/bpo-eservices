<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
    <c:when test="${fn:startsWith(flowModeId, 'ds-')}">
        <c:set var="dossierTypeI18nCode" value="ds"/>
    </c:when>
    <c:otherwise>
        <c:set var="dossierTypeI18nCode" value="tm"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${param.personRol eq 'Representative'}">
        <c:set var="listOfPersonsToImport" value="${flowBean.importedRepresentatives}"/>
        <span id="personChange_warning_last_person" class="hide">
            <spring:message code="personChange.warning.last.person.${dossierTypeI18nCode}.representative"/>
        </span>
        <input type="hidden" id="countOfRemainPersons" value="${flowBean.countOfRemainRepresentatives(param.previousPersonId)}" />
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>

<input type="hidden" id="formEdit" value="${formEdit}" />

<c:if test="${not empty listOfPersonsToImport and not formEdit}">
    <span style="font-weight: bold;">
        <spring:message code="personChange.form.importTmDs.${param.personRol}"/>
    </span>
    <select id="importPersonFromTmDs" class="${param.importPersonFromTmDsClasses}">
        <option value=""><spring:message code="selectBox.SELECT"/></option>
        <c:forEach var="personsToImport" items="${listOfPersonsToImport}">
            <c:forEach var="personToImport" items="${personsToImport.value}">
                <c:if test="${not empty personToImport.id and personToImport.id eq param.previousPersonId}">
                    <c:set var="selectedPerson" value="${selected='selected'}"/>
                </c:if>
                <option data-fulladdress="${personToImport.address.fullAddress}" value="${personToImport.id}" ${selectedPerson}>${personToImport.name}</option>
            </c:forEach>
        </c:forEach>
    </select>
</c:if>



