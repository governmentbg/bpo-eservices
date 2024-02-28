<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.12.2019 г.
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div class="trade-mark-details">
    <header>
        <spring:message code="pt.details.title.${formUtil.getMainType()}"/>
        <c:if test="${!flowBean.readOnly}">
            <a class="edit navigateBtn" data-val="Update_Patent"><spring:message code="review.edit"/></a>
        </c:if>
    </header>
    <c:if test="${not empty flowBean.patentsList}">
        <div class="patent">
            <table id="patentsList" class="table">

                <c:if test="${fn:endsWith(flowModeId, '-surrender') }">
                    <tr>
                        <th colspan="6">
                            <div class="alert alert-error">
                                <spring:message code="${flowModeId}.surrender.declaration"/>
                            </div>
                        </th>
                    </tr>
                </c:if>

                <c:if test="${fn:endsWith(flowModeId, '-withdrawal') }">
                    <tr>
                        <th colspan="6">
                            <div class="alert alert-error">
                                <spring:message code="${flowModeId}.withdrawal.declaration"/>
                            </div>
                        </th>
                    </tr>
                </c:if>

                <tr>
                    <th><spring:message code="pt.details.table.header.number"/></th>
                    <th><spring:message code="pt.details.table.header.applicationNumber"/></th>
                    <th><spring:message code="pt.details.table.header.title"/></th>
                    <th><spring:message code="pt.details.table.header.status"/></th>
                </tr>

                <c:set var="i" value="0"/>
                <c:forEach var="pt" items="${flowBean.patentsList}">
                    <c:set var="i" value="${i+1}"/>
                    <tr id="pt_id_${pt.id}">
                        <td>${i}</td>
                        <td><c:out value="${pt.applicationNumber}"/> </td>
                        <td><c:out value="${pt.patentTitle}"/></td>
                        <td><c:out value="${pt.patentCurrentStatus}"/> </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>
