<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.10.2022 г.
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <span id="representativeCurrentNumber" class="number"></span>

    <h3><spring:message code="representative.form.title.intlp"/></h3>
</header>

<sec:authorize access="hasRole('Representative_Import')" var="security_representative_import" />
<sec:authorize access="hasRole('Representative_Search')" var="security_representative_search" />

<c:set var="service_representative_import" value="${configurationServiceDelegator.isServiceEnabled('Representative_Import')}"/>
<c:set var="service_representative_search" value="${configurationServiceDelegator.isServiceEnabled('Person_Search')}"/>
<c:set var="security_service_representative_import"
       value="${(security_representative_import || !configurationServiceDelegator.securityEnabled) && service_representative_import}"/>
<c:set var="security_service_representative_search"
       value="${(security_representative_search || !configurationServiceDelegator.securityEnabled) && service_representative_search}"/>


<c:if test="${security_service_representative_import && security_service_representative_search}">

    <c:set var="sectionId" value="representative_intellectual" scope="request"/>

    <select class="span1" id="representativeIntellectualSelect">
        <option id="representativeIntellectualSelectEmptyOpt" value="">-</option>
    </select>
    <button type="button"
            id="representativeIntellectualImportBtn" class="btn">
        <spring:message code="representative.form.action.import"/>
    </button>

    <hr>

    <script type="text/javascript">
        loadIntlPRepresentatives();
    </script>
</c:if>
<c:if test="${!(security_service_representative_import && security_service_representative_search)}">
    <div class="alert alert-danger">! Bad configuration for representative search and import. Intellectual property representative can only be searched and imported</div>
</c:if>
