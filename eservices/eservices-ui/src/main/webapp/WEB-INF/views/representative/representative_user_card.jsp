<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="class-check">
	<input name="representativeSelected" type="checkbox" value="${param.representativeId}" />
</td>
<td data-val='number'>${param.rowId}</td>
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
<td data-val='name'><div class="name">${param.representativeName}</div></td>
<td data-val='country'>${param.representativeCountry}</td>
