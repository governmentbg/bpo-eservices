<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.5.2019 г.
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty flowBean.pcts}">

    <table class="table claim-table" id="pctTable">
        <tr>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <c:if test="${!flowBean.patentTemplateImported}">
                <th><spring:message code="claim.table.header.options"/></th>
            </c:if>
        </tr>

        <c:forEach var="pct" items="${flowBean.pcts}">
            <tr class="pct claimRow" data-cols="3">
                <td data-val="number"><c:out value="${pct.applicationNumber}"/></td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${pct.applicationDate}"/>
                </td>
                <c:if test="${!flowBean.patentTemplateImported}">
                    <td data-val="options">
                        <a class="edit-icon editPct" data-id="${pct.id}"></a>
                        <a class="remove-icon removePct" data-id="${pct.id}"></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>