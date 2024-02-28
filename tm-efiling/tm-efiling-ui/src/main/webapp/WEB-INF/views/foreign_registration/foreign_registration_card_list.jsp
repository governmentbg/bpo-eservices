<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 5.5.2022 г.
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div id="foreignRegistrationCardList" class="applicant">
    <c:if test="${not empty flowBean.foreignRegistrations}">
        <h4><spring:message code="foreign.registrations.table.title"/></h4>
        <table id="foreignRegistrationsList" class="table">
            <tr>
                <th><spring:message code="foreign.registration.table.header.number"/></th>
                <th><spring:message code="foreign.registration.table.header.date"/></th>
                <th><spring:message code="foreign.registration.table.header.country"/></th>
                <th><spring:message code="foreign.registration.table.header.options"/></th>
            </tr>
            <c:forEach var="foreignReg" items="${flowBean.foreignRegistrations}" varStatus="foreignRegStat">
                <c:set var="registrationDate">
                    <fmt:formatDate type="date" pattern="${dateFormat}" value="${foreignReg.registrationDate}"/>
                </c:set>
                <tr id="foreignReg_id_${foreignReg.id}">
                    <jsp:include page="/WEB-INF/views/foreign_registration/foreign_registration_card_row.jsp">
                        <jsp:param value="${foreignReg.id}" name="foreignRegId"/>
                        <jsp:param value="${foreignReg.registrationNumber}" name="registrationNumber"/>
                        <jsp:param value="${registrationDate}" name="registrationDate"/>
                        <jsp:param value="${foreignReg.registrationCountry}" name="registrationCountry"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>