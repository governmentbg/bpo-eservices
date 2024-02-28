<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="designer">
    <header>
        <a id="designer" class="anchorLink">
        </a>

        <h2><spring:message code="designer.section.title"></spring:message></h2>
    </header>

    <jsp:include page="/WEB-INF/views/designers/designer_card_list.jsp"/>
    <sec:authorize access="hasRole('Designer_Add')" var="security_designer_add" />
    <c:if test="${security_designer_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.designer"/>
        </button>

        <div id="tabDesigner" class="addbox" style="display:none">
            <jsp:include page="/WEB-INF/views/designers/designer_import.jsp"/>
            <div id="designerSection">
            </div>
        </div>

        <div id="designerMatches">
            <jsp:include page="/WEB-INF/views/designers/designerMatches.jsp"/>
        </div>
    </c:if>
</section>