<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="userApplicantCardList">
    <c:if test="${not empty flowBean.userApplicantsForm}">
    	<input type="hidden" id="importApplicantUser"/>
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userApplicantsForm" class="table" style="width:98%">

            <tr>
            	<th class="class-check"></th>
                <th><spring:message code="person.table.header.number"/><a data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a  data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a data-val='country'/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="applicant" items="${flowBean.userApplicantsForm}" varStatus="applicantId">
                <c:set var="i" value="${i+1}"/>
                <tr id="applicant_id_${applicant.id}">
                    <jsp:include page="/WEB-INF/views/applicant/applicant_user_card.jsp">
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