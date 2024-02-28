<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty priorityForm.goodsServices}">
    <table class="table table-striped data-table">
        <tr>
            <th><spring:message code="claim.priority.partial.class"/></th>
            <th><spring:message code="claim.priority.partial.list"/></th>
            <th><spring:message code="claim.priority.partial.actions"/></th>
        </tr>

        <c:forEach var="classRow" items="${priorityForm.goodsServices}" varStatus="rowCounter">
            <tr class="partialGSClass">
                <input type="hidden" value="${classRow.langId}" name="goodsServices[${rowCounter.count-1}].langId"/>
                <input type="hidden" value="${classRow.classId}" name="goodsServices[${rowCounter.count-1}].classId" class="classIdText"/>
                <input type="hidden" value="${classRow.desc}" name="goodsServices[${rowCounter.count-1}].desc" class="descText"/>
                <td class="">
                    <div>${classRow.classId}</div>
                    <div>${classRow.langId}</div>
                </td>
                <td class="">
                        ${classRow.desc}
                </td>
                <td class="">
                    <a class="remove-icon removeClass" data-id="${classRow.classId}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>