<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty flowBean.exhibitions}">

    <table class="table claim-table" id="exhibitionsTable">
        <tr>
            <th><spring:message code="claim.table.header.name"/><a class="sorter" data-val="str"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <c:if test="${!flowBean.earlierAppImported}">
                <th><spring:message code="claim.table.header.options"/></th>
            </c:if>
        </tr>

        <c:forEach var="exhibition" items="${flowBean.exhibitions}">
            <tr class="exhibition claimRow" data-cols="3">
                <td data-val="str">
                    <spring:eval var="storename" expression="exhibition.exhibitionName"/>
                    <c:choose>
                        <c:when test="${storename.length()>10}">
                            <c:out value="${storename.substring(0,10)}..."></c:out>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${exhibition.exhibitionName}"></c:out>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${exhibition.firstDate}"/>
                </td>
                <c:if test="${!flowBean.earlierAppImported}">
                    <td data-val="options">
                        <a class="edit-icon editExhibition" data-id="${exhibition.id}"></a>
                        <a class="remove-icon removeExhibition" data-id="${exhibition.id}"></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>