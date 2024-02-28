<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="${configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}"/>	
</c:if>
<c:if test="${empty configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="false"/>
</c:if>

<td data-val="number">${param.rowId}</td>
<%--<td data-val="type">--%>
    <%--<c:if test="${param.isAssociation eq 'true'}">--%>
        <%--<spring:message code="representative.association.type"/>--%>
    <%--</c:if>--%>
    <%--<c:if test="${not param.isAssociation eq 'true'}">--%>
        <%--<c:choose>--%>
            <%--<c:when test="${param.representativeType eq 'LEGAL_PRACTITIONER'}">--%>
                <%--<spring:message code="representative.legalPractitioner.type"/>--%>
            <%--</c:when>--%>
            <%--<c:when test="${param.representativeType eq 'PROFESSIONAL_PRACTITIONER'}">--%>
                <%--<spring:message code="representative.professionalPractitioner.type"/>--%>
            <%--</c:when>--%>
            <%--<c:when test="${param.representativeType eq 'EMPLOYEE_REPRESENTATIVE'}">--%>
                <%--<spring:message code="representative.employeeRepresentative.type"/>--%>
            <%--</c:when>--%>
            <%--<c:when test="${param.representativeType eq 'LEGAL_ENTITY'}">--%>
                <%--<spring:message code="representative.legalEntity.type"/>--%>
            <%--</c:when>--%>
            <%--<c:when test="${param.representativeType eq 'NATURAL_PERSON'}">--%>
                <%--<spring:message code="representative.naturalPerson.type"/>--%>
            <%--</c:when>--%>
        <%--</c:choose>--%>
    <%--</c:if>--%>
<%--</td>--%>
<td data-val="name">
    <c:set var="storename" value=""/>
    <c:if test="${not empty param.representativeName}">
        <spring:eval var="storename" expression="param.representativeName"/>
    </c:if>
    <c:choose>
        <c:when test="${storename.length()>20}">
            <c:out value="${storename.substring(0,20)}..." />
        </c:when>
        <c:otherwise>
            <c:out value="${storename}" />
        </c:otherwise>
    </c:choose>
</td>
<td data-val="country">${param.representativeCountry}</td>
<td data-val="options">
    <a class="edit-icon" id="editRepresentativeButton" data-val="${param.representativeId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon" id="deleteRepresentativeButton" data-val="${param.representativeId}"></a>
</td>