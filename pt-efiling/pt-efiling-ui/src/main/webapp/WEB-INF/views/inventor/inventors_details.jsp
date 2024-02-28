<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="inventor">
    <header>
        <a id="inventor" class="anchorLink">
        </a>

        <h2><spring:message code="inventor.section.title"></spring:message></h2>
    </header>

    <jsp:include page="/WEB-INF/views/inventor/inventor_card_list.jsp"/>
    <sec:authorize access="hasRole('Inventor_Add')" var="security_inventor_add" />
    <c:if test="${security_inventor_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.inventor"/>
        </button>

        <div id="tabInventor" class="addbox" style="display:none">
            <jsp:include page="/WEB-INF/views/inventor/inventor_import.jsp"/>
            <div id="inventorSection">
            </div>
        </div>

        <div id="inventorMatches">
            <jsp:include page="/WEB-INF/views/inventor/inventorMatches.jsp"/>
        </div>
    </c:if>
</section>