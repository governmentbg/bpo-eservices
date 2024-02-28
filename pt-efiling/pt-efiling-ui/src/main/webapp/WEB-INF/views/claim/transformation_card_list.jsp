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

<c:if test="${not empty flowBean.transformations}">

    <spring:eval var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
    <spring:eval var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

    <table class="table claim-table" id="transformationTable">
        <tr>
            <th><spring:message code="claim.table.header.type"/><a class="sorter" data-val="str"></a></th>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <th><spring:message code="claim.table.header.options"/></th>
        </tr>

        <c:forEach var="transformation" items="${flowBean.transformations}">
            <tr class="transformation claimRow" data-cols="4">
                <td data-val="str">
                    <c:choose>
                        <c:when test="${transformation.countryCode == wipo_office}">
                            <spring:message code="transformation.type.wo"/>
                        </c:when>
                        <c:when test="${transformation.countryCode == euipo_office}">
                            <spring:message code="transformation.type.em"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="transformation.type.national"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td data-val="number"><c:out value="${transformation.applicationNumber}"/></td>
                <td data-val="date">
                    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${transformation.applicationDate}"/>
                </td>
                <td data-val="options">
                    <a class="edit-icon editTransformation" data-id="${transformation.id}" data-country="${transformation.countryCode}"></a>
                    <a class="remove-icon removeTransformation" data-id="${transformation.id}" data-imported="${transformation.imported}"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>