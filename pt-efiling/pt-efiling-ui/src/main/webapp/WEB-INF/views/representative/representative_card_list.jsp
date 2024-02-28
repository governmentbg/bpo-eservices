<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="representative" scope="request" />
<component:generic path="showRepresentativeIdInTable" checkRender="true">
	<c:set var="showRepresentativeIdInTable" value="true" />
</component:generic>

<div id="representativeCardList">
    <c:set var="userDataRepresentativeSize" value="0"/>
    <c:if test="${not empty flowBean.userDataRepresentatives}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataRepresentatives" class="table table-striped data-table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"/></th>
                <c:if test="${showRepresentativeIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"/></th>
                </c:if>
                <%--<th><spring:message code="person.table.header.organization"/><a class="sorter" data-val="organization"/></th>--%>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val="type"/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val="country"/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="representative" items="${flowBean.userDataRepresentatives}" varStatus="representativeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="representative_id_${representativeId.count}">
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
                        <jsp:param value="${showRepresentativeIdInTable}" name="showRepresentativeIdInTable"/>
                    </jsp:include>
                </tr>
                <c:set var="userDataRepresentativeSize" value="${i}"/>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${not empty flowBean.addedRepresentatives}">
        <h4><spring:message code="person.table.title.representative.${flowModeId}"/></h4>
        <table id="addedRepresentatives" class="table table-striped data-table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val="number"/></th>
                <c:if test="${showRepresentativeIdInTable}">
                	<th><spring:message code="person.table.header.id"/><a class="sorter" data-val="id"/></th>
                </c:if>	
                <%--<th><spring:message code="person.table.header.organization"/><a class="sorter" data-val="organization"/></th>--%>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val="type"/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val="name"/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val="country"/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataRepresentativeSize}"/>
            <c:forEach var="representative" items="${flowBean.addedRepresentatives}" varStatus="representativeId">
                <c:set var="i" value="${i+1}"/>
                <tr id="representative_id_${representativeId.count}">
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
                    <jsp:include page="/WEB-INF/views/representative/representative_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${representative.id}" name="representativeId"/>
                        <jsp:param value="${representative.name}" name="representativeName"/>
                        <jsp:param value="${representative.organization}" name="representativeOrganization"/>
                        <jsp:param value="${representative.type}" name="representativeType"/>
                        <jsp:param value="${representative.address.country}" name="representativeCountry"/>
                        <jsp:param value="${representative.imported}" name="representativeIsImported"/>
                        <jsp:param value="${isAssociation}" name="isAssociation"/>
                        <jsp:param value="${showRepresentativeIdInTable}" name="showRepresentativeIdInTable"/>
                    </jsp:include>
                </tr>

            </c:forEach>
        </table>
    </c:if>


    <div style="margin:10px 0px">
        <c:if test="${not empty flowBean.earlierAppRepresentatives and flowBean.earlierAppImported and flowModeId != 'sv-efiling'}">
            <button id="importRepresentativesFromEarlierApp" type="button" class="btn fileinput-button">
                <spring:message code="representative.button.import.earlierApp"/>
            </button>
        </c:if>
    </div>
</div>