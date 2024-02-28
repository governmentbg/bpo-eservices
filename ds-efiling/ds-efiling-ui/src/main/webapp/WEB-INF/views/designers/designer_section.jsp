<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<section class="designer" id="designers">
    <header>
        <a href="#designers" class="anchorLink">
        </a>

        <h2><spring:message code="designer.section.title" /></h2>
    </header>

    <jsp:include page="designer_card_list.jsp"/>
    
    <sec:authorize access="hasRole('Designer_Add')" var="security_designer_add" />
    
    <c:if test="${security_designer_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="designerTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.designer"/>
        </button>

        <div id="tabDesigner" class="addbox" style="display:none">
            <jsp:include page="designer_import.jsp"/>
            <div id="designerSection">
            </div>
            <br />
        </div>

        <div id="designerMatches">
            <jsp:include page="designerMatches.jsp"/>
        </div>
    </c:if>
</section>

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /design/register-design/designers
</div>