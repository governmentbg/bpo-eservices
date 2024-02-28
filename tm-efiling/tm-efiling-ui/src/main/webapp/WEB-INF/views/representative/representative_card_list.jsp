<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="representativeCardList">
    <c:set var="userDataRepresentativeSize" value="0"/>
    <c:if test="${not empty flowBean.userDataRepresentatives}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataRepresentatives" class="table table-striped data-table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"/></th>
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val="type"/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val="country"/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="representative" items="${flowBean.userDataRepresentatives}" varStatus="representativeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="representative_id_${representative.id}">
                    <c:set var="isAssociation" value=""/>
                    <c:if test="${representative.type eq 'ASSOCIATION'}">
                        <c:set var="isAssociation" value="true"/>
                    </c:if>
                    <c:if test="${representative.type eq 'LEGAL_PRACTITIONER'}">
                        <c:if test="${representative.legalPractitionerType eq 'ASSOCIATION'}">
                            <c:set var="isAssociation" value="true"/>
                        </c:if>
                    </c:if>
                    <c:if test="${representative.type eq 'PROFESSIONAL_PRACTITIONER'}">
                        <c:if test="${representative.professionalPractitionerType eq 'ASSOCIATION'}">
                            <c:set var="isAssociation" value="true"/>
                        </c:if>
                    </c:if>
                    <jsp:include page="/WEB-INF/views/representative/representative_card_userData.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${representative.id}" name="representativeId"/>
                  		<jsp:param value="${representative.name}" name="representativeName"/>
                        <jsp:param value="${representative.type}" name="representativeType"/>
                        <jsp:param value="${representative.address.country}" name="representativeCountry"/>
                        <jsp:param value="${representative.imported}" name="representativeIsImported"/>
                        <jsp:param value="${isAssociation}" name="isAssociation"/>
                    </jsp:include>
                </tr>
                <c:set var="userDataRepresentativeSize" value="${i}"/>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${not empty flowBean.addedRepresentatives}">
        <h4><spring:message code="person.table.title.representative"/></h4>
        <table id="addedRepresentatives" class="table table-striped data-table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"/></th>
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"/></th>
                <%--<th><spring:message code="person.table.header.organization"/><a class="sorter" data-val="organization"/></th>--%>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val="type"/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val="country"/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataRepresentativeSize}"/>
            <c:forEach var="representative" items="${flowBean.addedRepresentatives}" varStatus="representativeId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="name" value="${representative.name}"/>
                <c:choose>
                    <c:when test="${representative.type eq 'EMPLOYEE_REPRESENTATIVE'}">
                        <c:set var="legalName" value="${representative.nameOfLegalEntity}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="legalName" value="${representative.name}"/>
                    </c:otherwise>
                </c:choose>
                <tr id="representative_id_${representative.id}">
                    <c:set var="isAssociation" value=""/>
                    <c:if test="${representative.type eq 'ASSOCIATION'}">
                        <c:set var="isAssociation" value="true"/>
                    </c:if>
                    <c:if test="${representative.type eq 'LEGAL_PRACTITIONER'}">
                        <c:if test="${representative.legalPractitionerType eq 'ASSOCIATION'}">
                            <c:set var="isAssociation" value="true"/>
                        </c:if>
                    </c:if>
                    <c:if test="${representative.type eq 'PROFESSIONAL_PRACTITIONER'}">
                        <c:if test="${representative.professionalPractitionerType eq 'ASSOCIATION'}">
                            <c:set var="isAssociation" value="true"/>
                        </c:if>
                    </c:if>
                    <c:set var="representativeAssociation" value=""/>
                     <c:if test="${representative.type eq 'ASSOCIATION'}">
                     		<c:set var="representativeAssociation" value="${representative.associationName}"/>
                        </c:if>
                    <jsp:include page="/WEB-INF/views/representative/representative_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${representative.id}" name="representativeId"/>
                        <jsp:param value="${name}" name="representativeName"/>
                        <jsp:param value="${legalName}" name="representativeOrganization"/>
                       	<jsp:param value="${representativeAssociation}" name="representativeAssociation"/>
                        <jsp:param value="${representative.type}" name="representativeType"/>
                        <jsp:param value="${representative.address.country}" name="representativeCountry"/>
                        <jsp:param value="${representative.imported}" name="representativeIsImported"/>
                        <jsp:param value="${isAssociation}" name="isAssociation"/>
                        <jsp:param value="${representative.legalForm}" name="legalForm"/>
                    </jsp:include>
                </tr>

            </c:forEach>
        </table>
    </c:if>
</div>