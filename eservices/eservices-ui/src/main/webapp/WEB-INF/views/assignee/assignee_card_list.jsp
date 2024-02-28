<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<div>
    <c:set var="userDataAssigneeSize" value="0"/>
    <c:set var="prevImportedNatural" value="false"/>
    <c:set var="prevImportedLegal" value="false"/>
    <c:if test="${not empty flowBean.userDataAssignees}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataAssignees" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="assignee" items="${flowBean.userDataAssignees}" varStatus="assigneeId">
            	
            	<c:choose>
				    <c:when test="${assignee.type eq 'NATURAL_PERSON' || assignee.type eq 'NATURAL_PERSON_SPECIAL'}">
				        <c:set var="prevImportedNatural" value="true"/>
				    </c:when>
				    <c:when test="${assignee.type eq 'LEGAL_ENTITY'}">
				        <c:set var="prevImportedLegal" value="true"/>
				    </c:when>
				</c:choose>
            
                <c:set var="i" value="${i+1}"/>
                <tr id="assignee_id_${assignee.id}">
                    <jsp:include page="/WEB-INF/views/assignee/assignee_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${assignee.id}" name="assigneeId"/>
                        <jsp:param value="${assignee.name}" name="assigneeName"/>
                        <jsp:param value="${assignee.type}" name="assigneeType"/>
                        <jsp:param value="${assignee.address.country}" name="assigneeCountry"/>
                        <jsp:param value="${assignee.imported}" name="assigneeIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="userDataAssigneeSize" value="${i}"/>
        </table>
    </c:if>
	 <c:if test="${not empty flowBean.addedAssignees}">
        <h4><spring:message code="person.table.title.assignee.${flowModeId}"/></h4>
        <table id="addedAssignees" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <!-- COMMENTED TO HIDE ID (ONLY WORKS ON IMPORTED ASSIGNEES)
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>
                -->
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataAssigneeSize}"/>
            <c:forEach var="assignee" items="${flowBean.addedAssignees}" varStatus="assigneeId">
            	<c:if test="${assignee.imported eq true}">
	            	<c:choose>
					    <c:when test="${assignee.type eq 'NATURAL_PERSON' || assignee.type eq 'NATURAL_PERSON_SPECIAL'}">
					        <c:set var="prevImportedNatural" value="true"/>
					    </c:when>
					    <c:when test="${assignee.type eq 'LEGAL_ENTITY'}">
					        <c:set var="prevImportedLegal" value="true"/>
					    </c:when>
					</c:choose>
				</c:if>
                <c:set var="i" value="${i+1}"/>
                <tr id="assignee_id_${assignee.id}">
                    <jsp:include page="/WEB-INF/views/assignee/assignee_card.jsp">
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

    <c:set var="sectionId" value="${functions:getPersonDataSetionId(flowModeId)}" scope="request"/>

    <c:if test="${configurationServiceDelegator.hasSelectPersonButton(flowModeId, sectionId)}">
        <input type="hidden" class="updatableParent" value="#assigneeCardList"/>
        <button style="margin-top:10px" type="button" class="btn fileinput-button personSelectButton" data-section="${sectionId}">
            <i class="list-icon"></i> <spring:message code="person.select.button"/>
        </button>
    </c:if>
    
</div>