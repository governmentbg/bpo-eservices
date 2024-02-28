<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="userRepresentativeCardList">
    <c:if test="${not empty flowBean.userRepresentativesForm}">
    	<input type="hidden" id="importRepresentativeUser"/>
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userRepresentativesForm" class="table" style="width:98%">

            <tr>
            	<th class="class-check"></th>
                <th><spring:message code="person.table.header.number"/><a data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a  data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a data-val='country'/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="representative" items="${flowBean.userRepresentativesForm}" varStatus="representativeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="representative_id_${representative.id}">
                    <jsp:include page="/WEB-INF/views/representative/representative_user_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${representative.id}" name="representativeId"/>
                        <jsp:param value="${representative.name}" name="representativeName"/>
                        <jsp:param value="${representative.type}" name="representativeType"/>
                        <jsp:param value="${representative.address.country}" name="representativeCountry"/>
                        <jsp:param value="${representative.imported}" name="representativeIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            
        </table>
    </c:if>
    
</div>