<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.10.2022 г.
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="representative" id="representative">

    <header>
        <a href="#representative" class="anchorLink">
        </a>

        <h2><spring:message code="representative.section.title.${flowModeId}"></spring:message></h2>
    </header>

    <jsp:include page="/WEB-INF/views/representative/representative_card_list.jsp"/>
    <sec:authorize access="hasRole('Representative_Add')" var="security_representative_add" />
    <c:if test="${security_representative_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.representative.${flowModeId}"/>
        </button>

        <div id="tabRepresentative" class="addbox" style="display:none">
            <div id="representativeIntellectualImport">
                <jsp:include page="/WEB-INF/views/representative/representative_intellectual_import.jsp"/>
            </div>
            <div id="representativeSection">
            </div>
            <br />
        </div>
    </c:if>
</section>
