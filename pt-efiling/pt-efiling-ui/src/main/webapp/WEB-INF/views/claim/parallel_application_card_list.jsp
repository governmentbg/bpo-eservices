<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty flowBean.parallelApplications}">

    <spring:eval var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
    <spring:eval var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

    <table class="table claim-table" id="parallelApplicationTable">
        <tr>
            <th><spring:message code="claim.table.header.type"/><a class="sorter" data-val="str"></a></th>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <th><spring:message code="claim.table.header.options"/></th>
        </tr>

        <c:forEach var="parallelApplication" items="${flowBean.parallelApplications}">
            <tr class="parallelApplication claimRow" data-cols="4">
                <td data-val="str">
                    <c:choose>
                        <c:when test="${parallelApplication.countryCode == wipo_office}">
                            <spring:message code="parallelApplication.type.wo"/>
                        </c:when>
                        <c:when test="${parallelApplication.countryCode == euipo_office}">
                            <spring:message code="parallelApplication.type.em"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="parallelApplication.type.national"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td data-val="number"><c:out value="${parallelApplication.applicationNumber}"/></td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${parallelApplication.applicationDate}"/>
                </td>
                <td data-val="options">
                    <a class="edit-icon editParallelApplication" data-id="${parallelApplication.id}" data-country="${parallelApplication.countryCode}"></a>
                    <a class="remove-icon removeParallelApplication" data-id="${parallelApplication.id}" data-imported="${parallelApplication.imported}"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>