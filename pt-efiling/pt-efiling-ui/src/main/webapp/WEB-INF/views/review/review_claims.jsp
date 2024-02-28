<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 4.1.2019 г.
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval scope="request" var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
<spring:eval scope="request" var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

<div class="your-details">
    <header>
        <spring:message code="review.claimsDetails.${flowModeId}"/>
        <a class="edit navigateBtn" data-val="Update_Claim"><spring:message code="review.update"/></a>
    </header>
    <div>
        <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

        <c:if test="${not empty flowBean.pcts}">
            <table>
                <caption><spring:message code="pct.section.checkbox"/></caption>
                <thead>
                <tr>
                    <th><spring:message code="claim.table.header.number"/></th>
                    <th><spring:message code="claim.table.header.date"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="pct" items="${flowBean.pcts}">
                    <tr>
                        <td data-val="number"><c:out value="${pct.applicationNumber}"/></td>
                        <td data-val="date">

                            <fmt:formatDate type="date" pattern="${dateFormat}" value="${pct.applicationDate}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty flowBean.divisionalApplications}">
            <table>
                <caption><spring:message code="divisionalApplication.section.checkbox"/></caption>
                <thead>
                    <tr>
                        <th><spring:message code="claim.table.header.number"/></th>
                        <th><spring:message code="claim.table.header.date"/></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="divisionalApplication" items="${flowBean.divisionalApplications}">
                    <tr>
                        <td data-val="number"><c:out value="${divisionalApplication.numberDivisionalApplication}"/></td>
                        <td data-val="date">
                            <fmt:formatDate type="date" pattern="${dateFormat}" value="${divisionalApplication.dateDivisionalApplication}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty flowBean.transformations}">
            <table>
                <caption><spring:message code="transformation.section.checkbox.${flowModeId}"/></caption>
                <thead>
                    <tr>
                        <th><spring:message code="claim.table.header.type"/><a class="sorter" data-val="str"></a></th>
                        <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
                        <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
                    </tr>
                </thead>

                <c:forEach var="transformation" items="${flowBean.transformations}">
                    <tr>
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
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${not empty flowBean.parallelApplications}">
            <table>
                <caption><spring:message code="parallelApplication.section.checkbox"/></caption>
                <thead>
                    <tr>
                        <th><spring:message code="claim.table.header.type"/></th>
                        <th><spring:message code="claim.table.header.number"/></th>
                        <th><spring:message code="claim.table.header.date"/></th>
                    </tr>
                </thead>

                <c:forEach var="parallelApplication" items="${flowBean.parallelApplications}">
                    <tr >
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
                            <fmt:formatDate type="date" pattern="${dateFormat}" value="${parallelApplication.applicationDate}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>


        <c:if test="${not empty flowBean.priorities}">
            <table>
                <caption><spring:message code="priority.section.checkbox"/></caption>
                <thead>
                <tr>
                    <th><spring:message code="claim.table.header.country"/></th>
                    <th><spring:message code="claim.table.header.number"/></th>
                    <th><spring:message code="claim.table.header.date"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="priority" items="${flowBean.priorities}">
                    <tr class="priority claimRow" data-cols="4">
                        <td data-val="str">${priority.country}</td>
                        <td data-val="number"><c:out value="${priority.number}"/></td>
                        <td data-val="date">
                            <fmt:formatDate type="date" pattern="${dateFormat}" value="${priority.date}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty flowBean.exhibitions}">
            <table>
                <caption><spring:message code="exhibition.section.checkbox"/></caption>
                <thead>
                <tr>
                    <th><spring:message code="claim.table.header.name"/></th>
                    <th><spring:message code="claim.table.header.date"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="exhibition" items="${flowBean.exhibitions}">
                    <tr class="exhibition claimRow" data-cols="3">
                        <td data-val="str">
                            <spring:eval var="storename" expression="exhibition.exhibitionName"/>
                            <c:choose>
                                <c:when test="${storename.length()>40}">
                                    <c:out value="${storename.substring(0,40)}..."></c:out>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${exhibition.exhibitionName}"></c:out>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td data-val="date">
                            <fmt:formatDate type="date" pattern="${dateFormat}" value="${exhibition.firstDate}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
