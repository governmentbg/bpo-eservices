<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="userDesignerCardList">
    <c:if test="${not empty flowBean.userDesignersForm}">
    	<input type="hidden" id="importDesignerUser"/>
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDesignersForm" class="table" style="width:98%">

            <tr>
            	<th class="class-check"></th>
                <th><spring:message code="person.table.header.number"/><a data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a  data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a data-val='country'/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="designer" items="${flowBean.userDesignersForm}" varStatus="designerId">
                <c:set var="i" value="${i+1}"/>
                <tr id="designer_id_${designer.id}">
                    <jsp:include page="/WEB-INF/views/designer/designer_user_card.jsp">
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
    
</div>