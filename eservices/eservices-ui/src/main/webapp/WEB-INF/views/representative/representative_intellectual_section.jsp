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

        <c:choose>
            <c:when test="${fn:endsWith(flowModeId, '-changerep')}">
                <h2><spring:message code="new.representative.section.title.intlp"></spring:message></h2>
            </c:when>
            <c:otherwise>
                <h2><spring:message code="representative.section.title.intlp"></spring:message></h2>
            </c:otherwise>
        </c:choose>

    </header>

    <c:if test="${fn:endsWith(flowModeId, '-changerep')}">
        <div class="alert alert-info">
            <spring:message code="personChange.warning.message.${flowModeId}"/>
        </div>
    </c:if>

    <div id="representativeCardList">
        <jsp:include page="/WEB-INF/views/representative/representative_card_list.jsp"/>
    </div>
    <sec:authorize access="hasRole('Representative_Add')" var="security_representative_add" />
    <c:if test="${security_representative_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.representative.intlp"/>
        </button>

        <div id="tabRepresentative" class="addbox" style="display:none">
            <div id="importAndCreateSection">
                <jsp:include page="/WEB-INF/views/representative/representative_intellectual_import.jsp"/>
            </div>
            <div id="representativeSection">
            </div>
            <br />
        </div>
    </c:if>

    <c:set var="maxRepr" value="representative.add.maxNumber.${flowModeId}"/>

    <input type="hidden" id="maxRepresentatives"
           value="${configurationServiceDelegator.getValue(maxRepr, 'eu.ohim.sp.core.person')}">
    <script type="text/javascript">
        checkMaxRepresentatives();
    </script>
</section>
