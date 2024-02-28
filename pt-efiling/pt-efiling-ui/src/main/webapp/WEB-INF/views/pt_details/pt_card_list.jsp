<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div id="patentsCardList">

    <c:if test="${flowBean.spcPatents != null && flowBean.spcPatents.size() > 0}">

        <table id="patentsList" class="table">
            <tr>
                <th><spring:message code="pt.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="pt.details.table.header.applicationNumber"/><a class="sorter" data-val='application'/></th>
                <th><spring:message code="pt.details.table.header.title"/><a class="sorter" data-val='title'/></th>
                <th><spring:message code="pt.details.table.header.status"/><a class="sorter" data-val='status'/></th>
                <th><spring:message code="pt.details.table.header.options"/></th>
            </tr>

            <c:set var="i" value="0"/>
            <c:forEach var="pt" items="${flowBean.spcPatents}">
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