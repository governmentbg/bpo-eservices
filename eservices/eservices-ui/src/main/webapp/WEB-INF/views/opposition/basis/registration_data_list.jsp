<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="regCardList">
    <c:set var="regDetailsSize" value="0"/>
    <c:if test="${not empty param.regList}">
        <h4><spring:message code="earlierRight.details.field.eERDRegistration.table.tittle"/></h4>
        <table id="regsList" class="table">

            <tr>
                <th><spring:message code="opposition_basis.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="earlierRight.details.field.eERDRegistration.registrationNumber"/><a class="sorter" data-val='regNumber'/></th>
                <th><spring:message code="earlierRight.details.field.eERDRegistration.registrationDate"/><a class="sorter" data-val='regDate'/></th>
                <th><spring:message code="earlierRight.details.field.eERDRegistration.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="opposition_basis.details.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="reg" items="${param.regList}" varStatus="obId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="reg_id" value="ob_id_${reg.registrationNumber}_${reg.registrationDate}_${reg.country}"/>
                <tr id="reg_id_${reg.registrationNumber}_${reg.registrationDate}_${reg.country}">
                	
                    <jsp:include page="/WEB-INF/views/opposition/basis/registration_data_card_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${reg_id}" name="regId"/>
                        <jsp:param value="${reg.registrationNumber}" name="regRegNumber"/>
                        <jsp:param value="${reg.registrationDate}" name="regRegDate"/>
                        <jsp:param value="${reg.country}" name="regCountry"/>     
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="regListSize" value="${i}"/>
        </table>
    </c:if>
</div>