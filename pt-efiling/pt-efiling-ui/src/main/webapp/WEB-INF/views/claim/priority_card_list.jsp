<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.04.2019 г.
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty flowBean.priorities}">
    <table class="table claim-table" id="priorityTable">
        <tr>
            <th><spring:message code="claim.table.header.country"/><a class="sorter" data-val="str"></a></th>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <c:if test="${!flowBean.earlierAppImported && !flowBean.patentTemplateImported}">
                <th><spring:message code="claim.table.header.options"/></th>
            </c:if>
        </tr>

        <c:forEach var="priority" items="${flowBean.priorities}">
            <tr class="priority claimRow" data-cols="4">
                <td data-val="str">${priority.country}</td>
                <td data-val="number"><c:out value="${priority.number}"/></td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${priority.date}"/>
                </td>
                <c:if test="${!flowBean.earlierAppImported && !flowBean.patentTemplateImported}">
                    <td data-val="options">
                        <a class="edit-icon editPriority" data-id="${priority.id}"></a>
                        <a class="remove-icon removePriority" data-id="${priority.id}"></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>