<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<c:if test="${param.showApplicantIdInTable}">
	<td data-val='id'>
		<c:choose>
			<c:when test="${param.applicantIsImported}">${param.applicantId}</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
	</td>
</c:if>
<td data-val='type'>
    <c:choose>
        <c:when test="${param.applicantType eq 'NATURAL_PERSON'}">
            <spring:message code="applicant.naturalPerson.type"/>
        </c:when>
        <c:when test="${param.applicantType eq 'LEGAL_ENTITY'}">
            <spring:message code="applicant.legalEntity.type"/>
        </c:when>
       <c:when test="${param.applicantType eq 'UNIVERSITY'}">
            <spring:message code="applicant.university.type"/>
        </c:when>        
        <c:when test="${param.applicantType eq 'NATURAL_PERSON_SPECIAL'}">
            <spring:message code="applicant.naturalPersonSpecialCase.type"/>
        </c:when>
    </c:choose>
</td>
<td data-val='name'><div class="name">${param.applicantName}</div></td>
<td data-val='country'>${param.applicantCountry}</td>
<c:if test="${empty param.hideOptions or !param.hideOptions}">
    <td data-val='options'>
        <a id="editApplicantButton" class="edit-icon" data-val="${param.applicantId}" data-rownum="${param.rowId}"></a>
        <a id="deleteApplicantButton" class="remove-icon" data-val="${param.applicantId}"></a>
    </td>
</c:if>