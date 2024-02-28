<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div>
    <c:set var="userDataDesignerSize" value="0"/>
    <c:set var="prevImportedNatural" value="false"/>
    <c:set var="prevImportedLegal" value="false"/>

    <c:if test="${not empty flowBean.userDataDesigners}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataDesigners" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            
            <c:set var="i" value="0"/>
            <c:forEach var="designer" items="${flowBean.userDataDesigners}" varStatus="designerId">
                <c:set var="prevImportedNatural" value="true"/>
                <c:set var="i" value="${i+1}"/>
                <tr id="designer_id_${designer.id}">
                    <jsp:include page="/WEB-INF/views/designer/designer_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${designer.id}" name="designerId"/>
                        <jsp:param value="${designer.name}" name="designerName"/>
                        <jsp:param value="${designer.address.country}" name="designerCountry"/>
                        <jsp:param value="${designer.imported}" name="designerIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="userDataDesignerSize" value="${i}"/>
        </table>
    </c:if>
    <c:if test="${not empty flowBean.addedDesigners}">
        <h4><spring:message code="person.table.title.designer"/></h4>
        <table id="addedDesigners" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <!-- COMMENTED TO HIDE ID (ONLY WORKS ON IMPORTED APPLICANTS)
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>
                -->
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataDesignerSize}"/>
            <c:forEach var="designer" items="${flowBean.addedDesigners}" varStatus="designerId">
            	<c:if test="${designer.imported eq true}">
                    <c:set var="prevImportedNatural" value="true"/>
				</c:if>
                <c:set var="i" value="${i+1}"/>
                <tr id="designer_id_${designer.id}">
                    <jsp:include page="/WEB-INF/views/designer/designer_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${designer.id}" name="designerId"/>
                        <jsp:param value="${designer.name}" name="designerName"/>
                        <jsp:param value="${designer.address.country}" name="designerCountry"/>
                        <jsp:param value="${designer.imported}" name="designerIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <c:set var="sectionId" value="designers" scope="request"/>

    <c:if test="${configurationServiceDelegator.hasSelectPersonButton(flowModeId, sectionId)}">
        <input type="hidden" class="updatableParent" value="#designerCardList"/>
        <button style="margin-top:10px" type="button" class="btn fileinput-button personSelectButton" data-section="${sectionId}">
            <i class="list-icon"></i> <spring:message code="person.select.button"/>
        </button>
    </c:if>
</div>