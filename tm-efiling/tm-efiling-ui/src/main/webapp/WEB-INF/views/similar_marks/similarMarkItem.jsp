<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tr>
    <td>${param.number}</td>
    <td>${param.name}</td>
    <td><spring:message code="similarMarks.table.mark.type.${fn:replace(param.type,' ','')}"/></td>
    <td>${param.office}</td>
    <td>${param.ownerName}</td>
    <td>
        <c:forEach var="classNumber" items="${param.inputTerms}">
            <c:set var="classNo" value="${fn:replace(classNumber, '[', '')}"/>
            <c:set var="classNo" value="${fn:replace(classNo, ']', '')}"/>
        <span class="badge badge-info">${classNo}</span>
        </c:forEach>

    <c:if test="${param.service_similarTM_details}">
        <td class="similarTM_DetailsUrl_AnchorContainer">
            <span class="hidden similarTM_DetailsUrl_ParamContainer">
                <input type="hidden" class="similarTM_urlParameter_trademarkId" value="${param.number}"/>
                <input type="hidden" class="similarTM_urlParameter_officeCode" value="${param.office}"/>
            </span>

            <a href="#"
               class="btn btn-mini similarTM_DetailsUrl_Anchor" target="_blank">
                <spring:message code="similarMarks.table.moreInfo"/>
            </a>
        </td>
    </c:if>
</tr>