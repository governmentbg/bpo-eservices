<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="class-check">
	<input name="applicantSelected" type="checkbox" value="${param.applicantId}" />
</td>
<td data-val='number'>${param.rowId}</td>
<td data-val='type'>
    <c:choose>
        <c:when test="${param.applicantType eq 'NATURAL_PERSON'}">
            <spring:message code="applicant.naturalPerson.type"/>
        </c:when>
        <c:when test="${param.applicantType eq 'LEGAL_ENTITY'}">
            <spring:message code="applicant.legalEntity.type"/>
        </c:when>
        <c:when test="${param.applicantType eq 'NATURAL_PERSON_SPECIAL'}">
            <spring:message code="applicant.naturalPersonSpecialCase.type"/>
        </c:when>
    </c:choose>
</td>
<td data-val='name'><div class="name">${param.applicantName}</div></td>
<td data-val='country'>${param.applicantCountry}</td>
