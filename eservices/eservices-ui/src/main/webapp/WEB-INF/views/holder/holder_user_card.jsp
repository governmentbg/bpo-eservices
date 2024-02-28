<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="class-check">
	<input name="holderSelected" type="checkbox" value="${param.holderId}" />
</td>
<td data-val='number'>${param.rowId}</td>
<td data-val='type'>
    <c:choose>
        <c:when test="${param.holderType eq 'NATURAL_PERSON'}">
            <spring:message code="holder.naturalPerson.type"/>
        </c:when>
        <c:when test="${param.holderType eq 'LEGAL_ENTITY'}">
            <spring:message code="holder.legalEntity.type"/>
        </c:when>
        <c:when test="${param.holderType eq 'NATURAL_PERSON_SPECIAL'}">
            <spring:message code="holder.naturalPersonSpecialCase.type"/>
        </c:when>
    </c:choose>
</td>
<td data-val='name'><div class="name">${param.holderName}</div></td>
<td data-val='country'>${param.holderCountry}</td>
