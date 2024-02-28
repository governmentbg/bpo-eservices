<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="class-check">
	<input name="assigneeSelected" type="checkbox" value="${param.assigneeId}" />
</td>
<td data-val='number'>${param.rowId}</td>
<td data-val='type'>
    <c:choose>
        <c:when test="${param.assigneeType eq 'NATURAL_PERSON'}">
            <spring:message code="assignee.naturalPerson.type"/>
        </c:when>
        <c:when test="${param.assigneeType eq 'LEGAL_ENTITY'}">
            <spring:message code="assignee.legalEntity.type"/>
        </c:when>
        <c:when test="${param.assigneeType eq 'NATURAL_PERSON_SPECIAL'}">
            <spring:message code="assignee.naturalPersonSpecialCase.type"/>
        </c:when>
    </c:choose>
</td>
<td data-val='name'><div class="name">${param.assigneeName}</div></td>
<td data-val='country'>${param.assigneeCountry}</td>
