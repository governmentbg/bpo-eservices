<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tr>
    <td>
        <c:if test="${param.alreadyAdded ==false}">
            <input class="personSelectItem" type="checkbox" data-id="${param.id}" data-field="${param.field}">
        </c:if>
        <c:if test="${param.alreadyAdded == true}">
            <spring:message code="person.select.alreadyAdded" var="alreadyAddedTitle"/>
            <i class="icon-check" title="${alreadyAddedTitle}"/>
        </c:if>
    </td>
    <td>
        <span><b><c:out value="${param.name}"/></b></span>
    </td>
    <td>
        <span style="font-style: italic"><c:out value="${param.addressCountry}, ${param.addressCity} ${param.addressPostal}, ${param.addressStreet}"/></span>
    </td>
    <td>
        <c:choose>
            <c:when test="${fn:containsIgnoreCase( param.fieldValue, 'assignee')}">
                <spring:message code="person.select.source.${param.fieldValue}.${flowModeId}"/>
            </c:when>
            <c:otherwise><spring:message code="person.select.source.${param.fieldValue}"/></c:otherwise>
        </c:choose>

    </td>
</tr>
