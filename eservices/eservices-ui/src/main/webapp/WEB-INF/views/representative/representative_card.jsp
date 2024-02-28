<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="${configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}"/>	
</c:if>
<c:if test="${empty configurationServiceDelegator.getValue('representative.cardlist.shortformat', 'eu.ohim.sp.eservices.views')}">
	<c:set var="cardListShortFormat" value="true"/>
</c:if>

<c:set var="service_representative_import" value="${configurationServiceDelegator.isServiceEnabled('Representative_Import')}"/>
<c:set var="service_representative_autocomplete" value="${configurationServiceDelegator.isServiceEnabled('Representative_Autocomplete')}"/>
<c:set var="service_representative_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="service_representative_manual" value="${configurationServiceDelegator.isServiceEnabled('Representative_Manual')}"/>

<td data-val="number">${param.rowId}</td>

<c:if test="${service_representative_autocomplete && param.showIdInTable}">
	<td data-val="id">
		<c:if test="${param.representativeIsImported}">${param.representativeId}</c:if>
		<c:if test="${param.representativeIsImported == false}">-</c:if>
	</td>
</c:if>

<%--<c:if test="${not service_representative_autocomplete}">--%>
	<td data-val='type'>
	<c:choose>
	    <c:when test="${param.representativeType eq 'NATURAL_PERSON'}">
	        <spring:message code="representative.naturalPerson.type"/>
	    </c:when>
	    <c:when test="${param.representativeType eq 'ASSOCIATION'}">
	        <spring:message code="representative.association.type"/>
	    </c:when>
	    <c:when test="${param.representativeType eq 'LEGAL_PRACTITIONER'}">
	        <spring:message code="representative.legalPractitioner.type"/>
	    </c:when>
		<c:when test="${param.representativeType eq 'LEGAL_ENTITY'}">
			<spring:message code="representative.legalEntity.type"/>
		</c:when>
		<c:when test="${param.representativeType eq 'LAWYER_COMPANY'}">
			<spring:message code="representative.lawyercompany.type"/>
		</c:when>
		<c:when test="${param.representativeType eq 'LAWYER_ASSOCIATION'}">
			<spring:message code="representative.lawyerassociation.type"/>
		</c:when>
		<c:when test="${param.representativeType eq 'TEMPORARY_REPRESENTATIVE'}">
			<spring:message code="representative.temporary.type"/>
		</c:when>
		<c:when test="${param.representativeType eq 'INTELLECTUAL_PROPERTY_REPRESENTATIVE'}">
			<spring:message code="representative.intlprepresenetative.type"/>
		</c:when>
	</c:choose>
	</td>
<%--</c:if>--%>

<c:if test="${not cardListShortFormat}">
	<c:set var="storename" value="${param.representativeOrganization}" />
	<td title="${storename}" data-val="organization">
		<c:choose>
			<c:when test="${storename.length()>10}">
				<c:out value="${storename.substring(0,10)}..." />
			</c:when>
			<c:otherwise>
				<c:out value="${storename}" />
			</c:otherwise>
		</c:choose>
	</td>
</c:if>

<c:set var="storename" value="${param.representativeName}" />
<td title="${storename}" data-val="name">
	<c:choose>
		<c:when test="${storename.length()>20}">
			<c:out value="${storename.substring(0,20)}..." />
		</c:when>
		<c:otherwise>
			<c:out value="${storename}" />
		</c:otherwise>
	</c:choose>
</td>

<c:if test="${not service_representative_autocomplete}">
	<td data-val='country'>${param.representativeCountry}</td>
</c:if>

<td data-val="options">
    <a class="edit-icon" id="editRepresentativeButton" data-val="${param.representativeId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon" id="deleteRepresentativeButton" data-val="${param.representativeId}"></a>
</td>