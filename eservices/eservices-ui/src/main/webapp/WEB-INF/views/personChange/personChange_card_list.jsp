<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:if test="${not empty configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="${configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}"/>	
</c:if>
<c:if test="${empty configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="true"/>
</c:if>

<c:set var="service_personChange_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('PersonChange_Autocomplete')}"/>

<div id="personChangeCardList">
    <c:set var="userDataPersonChangeSize" value="0"/>
    <c:if test="${not empty flowBean.addedPersonChanges}">
        <h4><spring:message code="person.table.title.person"/></h4>
        <table id="addedPersonChanges" class="table table-striped data-table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"/></th>
                <c:if test="${service_personChange_autocomplete}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"/></th>
                </c:if>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.currrentName"/><a class="sorter" data-val="curname"/></th>
                <th><spring:message code="person.table.header.updatedName"/><a class="sorter" data-val="updname"/></th>
                <th><spring:message code="person.table.header.updatedAddress"/><a class="sorter" data-val="updaddress"/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataPersonChangeSize}"/>
            <c:forEach var="personChange" items="${flowBean.addedPersonChanges}" varStatus="personChangeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="personChange_id_${personChange.id}">
                    <c:choose>
                        <c:when test="${personChange.changeType eq 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS'}">
                            <c:if test="${not empty personChange.correspondenceAddresses}">
                                <c:set var="personChangeNewAddress" value="${personChange.correspondenceAddresses[0].addressForm.fullAddress}" />
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="personChangeNewAddress" value="${personChange.address.fullAddress}" />
                        </c:otherwise>
                    </c:choose>
                    <jsp:include page="/WEB-INF/views/personChange/personChange_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${personChange.id}" name="personChangeId"/>
                        <jsp:param value="${personChange.changeType}" name="personChangeType"/>
                        <jsp:param value="${personChange.name}" name="personChangeName"/>
                        <jsp:param value="${personChange.previousPersonName}" name="personChangePreName"/>
                        <jsp:param value="${personChangeNewAddress}" name="personChangeAddress"/>
                    </jsp:include>
                </tr>

            </c:forEach>
        </table>
    </c:if>
</div>