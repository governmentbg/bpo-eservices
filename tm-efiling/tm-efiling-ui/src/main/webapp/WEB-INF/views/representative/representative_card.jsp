<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val="number">${param.rowId}</td>
<td data-val="id">
    <c:if test="${param.representativeIsImported}">${param.representativeId}</c:if>
    <c:if test="${param.representativeIsImported == false}">-</c:if>
</td>
<%--
<td data-val="organization">
<c:set var="storename" value="${param.representativeOrganization}" />
<c:choose>
    <c:when test="${storename.length()>10}">
        <c:out value="${storename.substring(0,10)}..." />
    </c:when>
    <c:otherwise>
        <c:out value="${storename}" />
    </c:otherwise>
</c:choose>
</td>
--%>
<td data-val='type'>
    <c:choose>
        <c:when test="${param.representativeType eq 'NATURAL_PERSON'}">
            <spring:message code="representative.naturalPerson.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'ASSOCIATION'}">
            <spring:message code="representative.association.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'LEGAL_PRACTITIONER'}">
            <spring:message code="representative.legalPractitioner.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'LEGAL_ENTITY'}">
            <spring:message code="representative.legalEntity.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'LAWYER_COMPANY'}">
            <spring:message code="representative.lawyercompany.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'LAWYER_ASSOCIATION'}">
            <spring:message code="representative.lawyerassociation.type"/>
        </c:when>
        <c:when test="${param.representativeType eq 'TEMPORARY_REPRESENTATIVE'}">
            <spring:message code="representative.temporary.type"/>
        </c:when>
    </c:choose>
</td>

<c:choose>
	<c:when test="${param.legalForm eq 'Natural Person' || param.representativeType eq 'EMPLOYEE_REPRESENTATIVE'}">
	<c:set var="storename" value="${param.representativeName}" />
	</c:when>
	<c:otherwise>
	<c:set var="storename" value="${param.representativeOrganization}" />
	</c:otherwise>
</c:choose>
<td title="${storename}" data-val="name">
    <c:out value="${storename}" />
</td>
<td data-val="country">${param.representativeCountry}</td>
<td data-val="options">
    <a class="edit-icon" id="editRepresentativeButton" data-val="${param.representativeId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon" id="deleteRepresentativeButton" data-val="${param.representativeId}"></a>
</td>