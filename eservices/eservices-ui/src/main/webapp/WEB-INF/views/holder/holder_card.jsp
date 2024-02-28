<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<!-- <td data-val='id'><c:if test="${param.holderIsImported}">${param.holderId}</c:if></td>-->
<td data-val='type'>
<c:choose>
    <c:when test="${param.holderType eq 'NATURAL_PERSON'}">
        <spring:message code="holder.naturalPerson.type"/>
    </c:when>
    <c:when test="${param.holderType eq 'LEGAL_ENTITY'}">
        <spring:message code="holder.legalEntity.type"/>
    </c:when>
</c:choose>
</td>
<td data-val='name'>
    <c:set var="storeholderName" value="${param.holderName}" scope="request" />
    <c:choose>
        <c:when test="${storeholderName.length()>10}">
            <c:out value="${storeholderName.substring(0,10)}..." />
        </c:when>
        <c:otherwise>
            <c:out value="${storeholderName}" />
        </c:otherwise>
    </c:choose>
</td>
<td data-val='country'>${param.holderCountry}</td>
<td data-val='options'>
    <a id="editHolderButton" class="edit-icon"
       data-val="${param.holderId}" data-rownum="${param.rowId}"></a>
    <a id="deleteHolderButton" class="remove-icon" data-val="${param.holderId}" style="display: none;"></a>
</td>