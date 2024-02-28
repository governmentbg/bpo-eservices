<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty flowBean.divisionalApplications}">

    <table class="table claim-table" id="divisionalApplicationTable">
        <tr>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <th><spring:message code="claim.table.header.options"/></th>
        </tr>

        <c:forEach var="divisionalApplication" items="${flowBean.divisionalApplications}">
            <tr class="divisionalApplication claimRow" data-cols="3">
                <td data-val="number"><c:out value="${divisionalApplication.numberDivisionalApplication}"/></td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${divisionalApplication.dateDivisionalApplication}"/>
                </td>
                <td data-val="options">
                    <a class="edit-icon editDivisionalApplication" data-id="${divisionalApplication.id}"></a>
                    <a class="remove-icon removeDivisionalApplication" data-id="${divisionalApplication.id}" data-imported="${divisionalApplication.imported}"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>