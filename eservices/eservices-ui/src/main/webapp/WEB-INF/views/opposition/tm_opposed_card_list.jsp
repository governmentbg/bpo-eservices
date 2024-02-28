<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="tmoCardList">
    <c:set var="tmoDetailsSize" value="0"/>
    <c:if test="${not empty flowBean.tmosList}">
        <h4><spring:message code="tm_opposed.table.tittle"/></h4>
        <table id="tmosList" class="table">

            <tr>
                <th><spring:message code="tm_opposed.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="tm_opposed.details.table.header.applicationNumber"/><a class="sorter" data-val='id'/></th>
                <th><spring:message code="tm_opposed.details.table.header.applicationDate"/><a class="sorter" data-val='date'/></th>
                <th><spring:message code="tm_opposed.details.table.header.tradeMarkType"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="tm_opposed.details.table.header.tradeMarkName"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="tm_opposed.details.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="tmo" items="${flowBean.tmosList}" varStatus="tmoId">
                <c:set var="i" value="${i+1}"/>
                <tr id="tmo_id_${tmo.id}">
                    <jsp:include page="/WEB-INF/views/opposition/tm_opposed_card_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${tmo.id}" name="tmoId"/>
                        <jsp:param value="${tmo.getApplicationNumber()}" name="tmoApplication"/>
                        <jsp:param value="${tmo.getApplicationDate()}" name="tmoDate"/>
                        <jsp:param value="${tmo.getTradeMarkType()}" name="tmoType"/>
                        <jsp:param value="${tmo.getTradeMarkName()}" name="tmoName"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="tmoListSize" value="${i}"/>
        </table>
    </c:if>
</div>