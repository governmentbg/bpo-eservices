<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="designers" scope="request" />
<component:generic path="showDesignerIdInTable" checkRender="true">
	<c:set var="showDesignerIdInTable" value="true" />
</component:generic>

<c:if test="${isThere and functions:isMultipleDesignApplication(flowBean)}">
	<div class="alert alert-info">
		<spring:message code="designer.section.warning" />
	</div>
</c:if>	
	
<div id="designerCardList">
	
    <c:set var="userDataDesignerSize" value="0"/>
    
    <c:if test="${not empty flowBean.userDataDesigners}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataDesigners" class="table table-striped data-table">
            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"></a></th>
                <c:if test="${showDesignerIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"></a></th>
                </c:if>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"></a></th>
                <c:if test="${functions:isMultipleDesignApplication(flowBean)}">
                	<th><spring:message code="person.table.header.designNumbers"/><a class="sorter" data-val="designNumbers"></a></th>
                </c:if>	
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="designer" items="${flowBean.userDataDesigners}" varStatus="designerId">
                <c:set var="i" value="${i+1}"/>
                <tr id="designer_id_${designer.id}">
					<jsp:include page="/WEB-INF/views/designers/designer_card.jsp">
						<jsp:param value="${i}" name="rowId"/>
						<jsp:param value="${designer.id}" name="designerId"/>
						<jsp:param value="${designer.name}" name="designerName"/>
						<jsp:param value="${designer.imported}" name="designerIsImported"/>
						<jsp:param value="${showDesignerIdInTable}" name="showDesignerIdInTable"/>
                        <jsp:param value="${functions:getLinkedDesignsNumbers(designer)}" name="designerDesignNumbers" />
					</jsp:include>
                </tr>
                <c:set var="userDataDesignerSize" value="${i}"/>
            </c:forEach>
        </table>
    </c:if>
    
    <c:if test="${not empty flowBean.addedDesigners}">
        <h4><spring:message code="person.table.title.designer"/></h4>
        <table id="addedDesigners" class="table table-striped data-table">
            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"></a></th>
                <c:if test="${showDesignerIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"></a></th>
                </c:if>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"></a></th>
                <c:if test="${functions:isMultipleDesignApplication(flowBean)}">
                	<th><spring:message code="person.table.header.designNumbers"/><a class="sorter" data-val="designNumbers"></a></th>
                </c:if>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataDesignerSize}"/>
            <c:forEach var="designer" items="${flowBean.addedDesigners}" varStatus="designerId">
                <c:set var="i" value="${i+1}"/>
                <tr id="designer_id_${designer.id}">
					<jsp:include page="/WEB-INF/views/designers/designer_card.jsp">
						<jsp:param value="${i}" name="rowId"/>
						<jsp:param value="${designer.id}" name="designerId"/>
						<jsp:param value="${designer.name}" name="designerName"/>
						<jsp:param value="${designer.imported}" name="designerIsImported"/>
						<jsp:param value="${showDesignerIdInTable}" name="showDesignerIdInTable"/>
                        <jsp:param value="${functions:getLinkedDesignsNumbers(designer)}" name="designerDesignNumbers" />
					</jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

