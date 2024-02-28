<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="${configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}"/>	
</c:if>
<c:if test="${empty configurationServiceDelegator.getValue('personChange.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="true"/>
</c:if>

<c:set var="service_personChange_import" value="${configurationServiceDelegator.isServiceEnabled('PersonChange_Import')}"/>
<c:set var="service_personChange_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('PersonChange_Autocomplete')}"/>
<c:set var="service_personChange_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="service_personChange_manual" value="${configurationServiceDelegator.isServiceEnabled('PersonChange_Manual')}"/>

<td data-val="number">${param.rowId}</td>
<c:if test="${service_personChange_autocomplete}">
	<td data-val="id">
		<c:if test="${not empty param.personChangeId}">${param.personChangeId}</c:if>
		<c:if test="${empty param.personChangeId}">?</c:if>	
	</td>
</c:if>
<td data-val='type'>
    <spring:message code="personChange.changeType.${param.personChangeType}"/>
</td>
<td data-val='curname'>
    <c:set var="storename" value=""/>
    <c:if test="${not empty param.personChangePreName}">
        <spring:eval var="storename" expression="param.personChangePreName"/>
    </c:if>
    <c:choose>
        <c:when test="${storename.length()>10}">
            <span title="${storename}"><c:out value="${storename.substring(0,10)}..." /></span>
        </c:when>
        <c:otherwise>
            <c:out value="${storename}" />
        </c:otherwise>
    </c:choose>
</td>
<td data-val="updname">
    <c:set var="storename" value=""/>
    <c:if test="${not empty param.personChangeName}">
        <spring:eval var="storename" expression="param.personChangeName"/>
    </c:if>
    <c:choose>
        <c:when test="${storename.length()>10}">
            <span title="${storename}"><c:out value="${storename.substring(0,10)}..." /></span>
        </c:when>
        <c:otherwise>
            <c:out value="${storename}" />
        </c:otherwise>
    </c:choose>
</td>
<td data-val="updaddress">
    <c:set var="storeaddress" value=""/>
    <c:if test="${not empty param.personChangeAddress}">
        <spring:eval var="storeaddress" expression="param.personChangeAddress"/>
    </c:if>
    <c:choose>
        <c:when test="${storeaddress.length()>10}">
            <span title="${storeaddress}"><c:out value="${storeaddress.substring(0,10)}..." /></span>
        </c:when>
        <c:otherwise>
            <c:out value="${storeaddress}" />
        </c:otherwise>
    </c:choose>
</td>

<td data-val="options">
	
    <a class="edit-icon" id="editPersonChangeButton" data-val="${param.personChangeId}" data-rownum=${param.rowId}></a>
    
    <a class="remove-icon" id="deletePersonChangeButton" data-val="${param.personChangeId}"></a>
</td>