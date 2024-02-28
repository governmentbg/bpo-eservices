<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<!-- COMMENTED TO HIDE ID (ONLY WORKS ON IMPORTED ASSIGNEES) 
<td data-val='id'><c:if test="${param.assigneeIsImported}">${param.assigneeId}</c:if></td>
-->
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
<td data-val='name'>
<spring:eval var="storename" expression="param.assigneeName"/>
									<c:choose>
										<c:when test="${storename.length()>10}">
											<c:out value="${storename.substring(0,10)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${storename}" />
										</c:otherwise>
									</c:choose>
</td>
<td data-val='country'>${param.assigneeCountry}</td>
<td data-val='options'>
        <a id="editAssigneeButton" class="edit-icon"
           data-val="${param.assigneeId}" data-rownum="${param.rowId}"></a>
        <a id="deleteAssigneeButton" class="remove-icon" data-val="${param.assigneeId}"></a>
</td>
