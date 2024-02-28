<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="similar-marks" id="similarMarks">
    <header>
        <a id="similarMarks" class="anchorLink"></a>

        <h2><spring:message code="similarMarks.title"/></h2>
    </header>

    <c:set var="service_similarMarks_enabled" value="${configurationServiceDelegator.isServiceEnabled('SimilarTM_Service')}"/>
    <c:if test="${service_similarMarks_enabled}">

            <button type="button" id="similarMarkModalTrigger">
                <spring:message code="similarMarks.action.seeFullReport"/>
            </button>

    </c:if>
    <c:if test="${service_similarMarks_enabled}">
        <div id="similarMarksModal" class="modal fade modal-similar-marks">
        <div class="modal-dialog">
		<div class="modal-content">
            <header>
                <h1><spring:message code="similarMarks.risks"/></h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>

            <section class="modal-similar-marks-body">
            <span id="similarMarksBody">
                <c:set var="service_similarTM_details"
                       value="${configurationServiceDelegator.isServiceEnabled('SimilarTM_FindMoreDetails_Service')}"/>
                <c:if test="${service_similarTM_details}">
                    <span class="hidden similarTMDetailsUrlTemplate">
                        <c:out value="${configurationServiceDelegator.getValueFromGeneralComponent('service.similarTm.details.url')}"
                               escapeXml="false"/>
                    </span>
                </c:if>

                <jsp:include page="../similarmark_table.jsp"/>
            </span>
            </section>
            <footer>
                <ul>
                    <li>
                        <button type="button" data-dismiss="modal"><spring:message code="similarMarks.popup.action.close"/></button>
                    </li>
                </ul>
            </footer>

        </div>
        </div>
        </div>

    </c:if>
</section>