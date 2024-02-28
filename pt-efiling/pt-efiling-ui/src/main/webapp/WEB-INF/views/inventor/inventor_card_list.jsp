<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="inventor" scope="request" />
<component:generic path="showInventorIdInTable" checkRender="true">
	<c:set var="showInventorIdInTable" value="true" />
</component:generic>
	
<div id="inventorCardList">
	
    <c:set var="userDataInventorSize" value="0"/>
    
    <c:if test="${not empty flowBean.userDataInventors}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataInventors" class="table table-striped data-table">
            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"></a></th>
                <c:if test="${showInventorIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"></a></th>
                </c:if>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"></a></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="inventor" items="${flowBean.userDataInventors}" varStatus="inventorId">
                <c:set var="i" value="${i+1}"/>
                <tr id="inventor_id_${inventor.id}">
					<jsp:include page="/WEB-INF/views/inventor/inventor_card.jsp">
						<jsp:param value="${i}" name="rowId"/>
						<jsp:param value="${inventor.id}" name="inventorId"/>
						<jsp:param value="${inventor.name}" name="inventorName"/>
						<jsp:param value="${inventor.imported}" name="inventorIsImported"/>
						<jsp:param value="${showInventorIdInTable}" name="showInventorIdInTable"/>
					</jsp:include>
                </tr>
                <c:set var="userDataInventorSize" value="${i}"/>
            </c:forEach>
        </table>
    </c:if>
    
    <c:if test="${not empty flowBean.addedInventors}">
        <c:if test="${flowModeId eq 'sv-efiling'}">
            <h4><spring:message code="person.table.title.author"/></h4>
        </c:if>
        <c:if test="${!(flowModeId eq 'sv-efiling')}">
            <h4><spring:message code="person.table.title.inventor"/></h4>
        </c:if>
        <table id="addedInventors" class="table table-striped data-table">
            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"></a></th>
                <c:if test="${showInventorIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"></a></th>
                </c:if>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"></a></th>
                <c:if test="${!flowBean.earlierAppImported && !flowBean.patentTemplateImported}">
                    <th><spring:message code="person.table.header.options"/></th>
                </c:if>
            </tr>
            <c:set var="i" value="${userDataInventorSize}"/>
            <c:forEach var="inventor" items="${flowBean.addedInventors}" varStatus="inventorId">
                <c:set var="i" value="${i+1}"/>
                <tr id="inventor_id_${inventor.id}">
					<jsp:include page="/WEB-INF/views/inventor/inventor_card.jsp">
						<jsp:param value="${i}" name="rowId"/>
						<jsp:param value="${inventor.id}" name="inventorId"/>
						<jsp:param value="${inventor.name}" name="inventorName"/>
						<jsp:param value="${inventor.imported}" name="inventorIsImported"/>
						<jsp:param value="${showInventorIdInTable}" name="showInventorIdInTable"/>
                        <jsp:param value="${flowBean.earlierAppImported || flowBean.patentTemplateImported}" name="hideOptions" />
					</jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

