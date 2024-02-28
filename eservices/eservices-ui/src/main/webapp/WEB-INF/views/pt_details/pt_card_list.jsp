<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 12.12.2019 г.
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div id="patentsCardList">

    <c:if test="${fn:endsWith(flowModeId, '-surrender') }">
        <div class="alert alert-danger">
            <spring:message code="${flowModeId}.surrender.declaration"/>
        </div>
    </c:if>

    <c:if test="${fn:endsWith(flowModeId, '-withdrawal') }">
        <div class="alert alert-danger">
            <spring:message code="${flowModeId}.withdrawal.declaration"/>
        </div>
    </c:if>

    <c:if test="${not empty flowBean.patentsList}">

        <table id="patentsList" class="table">
            <tr>
                <th><spring:message code="pt.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="pt.details.table.header.applicationNumber"/><a class="sorter" data-val='application'/></th>
                <th><spring:message code="pt.details.table.header.title"/><a class="sorter" data-val='title'/></th>
                <th><spring:message code="pt.details.table.header.status"/><a class="sorter" data-val='status'/></th>
                <th><spring:message code="pt.details.table.header.options"/></th>
            </tr>

            <c:set var="i" value="0"/>
            <c:forEach var="pt" items="${flowBean.patentsList}">
                <c:set var="i" value="${i+1}"/>

                <tr id="pt_id_${pt.id}">
                    <jsp:include page="/WEB-INF/views/pt_details/pt_card_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${pt.id}" name="id"/>
                        <jsp:param value="${pt.applicationNumber}" name="applicationNumber"/>
                        <jsp:param value="${pt.patentTitle}" name="title"/>
                        <jsp:param value="${pt.patentCurrentStatus}" name="status"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>