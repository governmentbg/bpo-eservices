<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="userHolderCardList">
    <c:if test="${not empty flowBean.userHoldersForm}">
    	<input type="hidden" id="importHolderUser"/>
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userHoldersForm" class="table" style="width:98%">

            <tr>
            	<th class="class-check"></th>
                <th><spring:message code="person.table.header.number"/><a data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a  data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a data-val='country'/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="holder" items="${flowBean.userHoldersForm}" varStatus="holderId">
                <c:set var="i" value="${i+1}"/>
                <tr id="holder_id_${holder.id}">
                    <jsp:include page="/WEB-INF/views/holder/holder_user_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${holder.id}" name="holderId"/>
                        <jsp:param value="${holder.name}" name="holderName"/>
                        <jsp:param value="${holder.type}" name="holderType"/>
                        <jsp:param value="${holder.address.country}" name="holderCountry"/>
                        <jsp:param value="${holder.imported}" name="holderIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            
        </table>
    </c:if>
    
</div>