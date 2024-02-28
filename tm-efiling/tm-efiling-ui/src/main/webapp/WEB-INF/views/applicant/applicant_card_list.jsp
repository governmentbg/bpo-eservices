<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="applicantCardList">
    <c:set var="userDataApplicantSize" value="0"/>
    <c:if test="${not empty flowBean.userDataApplicants}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataApplicants" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="applicant" items="${flowBean.userDataApplicants}" varStatus="applicantId">
                <c:set var="i" value="${i+1}"/>
                <tr id="applicant_id_${applicant.id}">
                    <jsp:include page="/WEB-INF/views/applicant/applicant_card_userData.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${applicant.id}" name="applicantId"/>
                        <jsp:param value="${applicant.name}" name="applicantName"/>
                        <jsp:param value="${applicant.type}" name="applicantType"/>
                        <jsp:param value="${applicant.address.country}" name="applicantCountry"/>
                        <jsp:param value="${applicant.imported}" name="applicantIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="userDataApplicantSize" value="${i}"/>
        </table>
    </c:if>
    <c:if test="${not empty flowBean.addedApplicants}">
        <h4><spring:message code="person.table.title.applicant"/></h4>
        <table id="addedApplicants" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataApplicantSize}"/>
            <c:forEach var="applicant" items="${flowBean.addedApplicants}" varStatus="applicantId">
                <c:set var="i" value="${i+1}"/>
                <tr id="applicant_id_${applicant.id}">
                    <jsp:include page="/WEB-INF/views/applicant/applicant_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${applicant.id}" name="applicantId"/>
                        <jsp:param value="${applicant.name}" name="applicantName"/>
                        <jsp:param value="${applicant.type}" name="applicantType"/>
                        <jsp:param value="${applicant.address.country}" name="applicantCountry"/>
                        <jsp:param value="${applicant.imported}" name="applicantIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>