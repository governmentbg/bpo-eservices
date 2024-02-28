<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val="number">${param.rowId}</td>
<c:if test="${param.showRepresentativeIdInTable}">
	<td data-val='id'>
		<c:choose>
			<c:when test="${param.representativeIsImported}">${param.representativeId}</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
	</td>
</c:if>
<%--<td data-val="organization">${param.representativeOrganization}</td>--%>
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

<c:set var="storename" value="${param.representativeName}" />
<td title="${storename}" data-val="name">
    <c:out value="${storename}" />
</td>

<td data-val="country">${param.representativeCountry}</td>
<td data-val="options">
    <a class="edit-icon" id="editRepresentativeButton" data-val="${param.representativeId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon" id="deleteRepresentativeButton" data-val="${param.representativeId}"></a>
</td>