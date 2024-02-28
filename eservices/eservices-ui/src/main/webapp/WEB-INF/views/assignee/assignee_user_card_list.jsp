<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="userAssigneeCardList">
    <c:if test="${not empty flowBean.userAssigneesForm}">
    	<input type="hidden" id="importAssigneeUser"/>
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userAssigneesForm" class="table" style="width:98%">

            <tr>
            	<th class="class-check"></th>
                <th><spring:message code="person.table.header.number"/><a data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a  data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a data-val='country'/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="assignee" items="${flowBean.userAssigneesForm}" varStatus="assigneeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="assignee_id_${assignee.id}">
                    <jsp:include page="/WEB-INF/views/assignee/assignee_user_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${assignee.id}" name="assigneeId"/>
                        <jsp:param value="${assignee.name}" name="assigneeName"/>
                        <jsp:param value="${assignee.type}" name="assigneeType"/>
                        <jsp:param value="${assignee.address.country}" name="assigneeCountry"/>
                        <jsp:param value="${assignee.imported}" name="assigneeIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            
        </table>
    </c:if>
    
</div>