<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='id'><c:if test="${param.applicantIsImported}">${param.applicantId}</c:if></td>
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
<td data-val='name'><div class="name">
<spring:eval var="storename" expression="param.applicantName"/>
									<c:choose>
										<c:when test="${storename.length()>10}">
											<c:out value="${storename.substring(0,10)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${storename}" />
										</c:otherwise>
									</c:choose>
</div></td>
<td data-val='country'>${param.applicantCountry}</td>
<td data-val='options'>
    <a id="editApplicantButton" class="edit-icon"
       data-val="${param.applicantId}" data-rownum="${param.rowId}">
        <%--<c:if test="${param.applicantIsImported}">--%>
        <%--<spring:message code="person.table.action.view"/>--%>
        <%--</c:if>--%>
        <%--<c:if test="${not param.applicantIsImported}">--%>
        <%--<spring:message code="person.table.action.edit"/>--%>
        <%--</c:if>--%>
    </a>
    <a id="deleteApplicantButton" class="remove-icon" data-val="${param.applicantId}">
        <%--<spring:message code="person.table.action.remove"/>--%>
    </a>
</td>