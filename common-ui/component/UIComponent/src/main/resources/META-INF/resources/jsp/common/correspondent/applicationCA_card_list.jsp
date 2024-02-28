<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="applicationCACardList">
    <c:if test="${not empty flowBean.correspondanceAddresses}">
        <h4><spring:message code="person.table.title.applicationCA"/></h4>
        <table id="addedApplicationCAs" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataApplicationCAtSize}"/>
            <c:forEach var="applicationCA" items="${flowBean.correspondanceAddresses}" varStatus="applicationCAId">
                <c:set var="i" value="${i+1}"/>
                <tr id="applicationCA_id_${applicationCA.id}">                
                    <jsp:include page="applicationCA_card.jsp">
                    	<jsp:param name="rowId" value="${i}" />
                    	<jsp:param name="applicationCAId" value="${applicationCA.id}"/> 
                        <jsp:param name="applicationCAName" value="${applicationCA.correspondenceAddressForm.correspondenceName}" />
                        <jsp:param name="applicationCACountry" value="${applicationCA.correspondenceAddressForm.addressForm.country}" />
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
