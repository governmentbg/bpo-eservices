<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="showRepresentative" value="false"/>
<c:set var="showApplicant" value="false"/>
<c:if test="${not empty configurationServiceDelegator.getPersonTypes(flowModeId)}">
    <c:forEach var="section_item" items="${configurationServiceDelegator.getPersonTypes(flowModeId)}">
        <c:if test="${fn:startsWith(section_item, 'applicant') eq true}">
            <c:set var="showApplicant" value="true"/>
        </c:if>
        <c:if test="${fn:startsWith(section_item, 'representative') eq true}">
            <c:set var="showRepresentative" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<a id="person" class="anchorLink">
</a>

<h3><spring:message code="person.title"></spring:message></h3>
<p class="declaration"><spring:message code="person.declaration"/></p>
<span id="selectText" class="hidden"><spring:message code="selectBox.SELECT"/></span>

<c:if test="${showRepresentative}">
    <jsp:include page="/WEB-INF/views/representative/representative_card_list.jsp"/>
</c:if>

<c:if test="${showApplicant}">
    <jsp:include page="/WEB-INF/views/applicant/applicant_card_list.jsp"/>
</c:if>

<div class="tabsWrap">
    <p data-tabs="tabs" class="btn-group" data-toggle="buttons-radio">
    <sec:authorize access="hasRole('Applicant_Add')" var="security_applicant_add" />    
    <sec:authorize access="hasRole('Representative_Add')" var="security_representative_add" />    
        <c:if test="${security_applicant_add || !configurationServiceDelegator.securityEnabled}">
            <c:if test="${showApplicant}">
                <a href="#tabApplicant" class="btn" data-toggle="tab">
                    <spring:message code="person.button.applicant"/>
                </a>
            </c:if>
        </c:if>
        <c:if test="${security_representative_add || !configurationServiceDelegator.securityEnabled}">
            <c:if test="${showRepresentative}">
                <a href="#tabRepresentative" class="btn" data-toggle="tab">
                    <spring:message code="person.button.representative"/>
                </a>
            </c:if>
        </c:if>
    </p>

    <div class="tab-content">
        <div id="tabApplicant" class="flBox data-url tab-pane" data-ap="applicant">
            <jsp:include page="/WEB-INF/views/applicant/applicant_import.jsp"/>
            <div id="applicantSection">
                <jsp:include page="/WEB-INF/views/applicant/applicant_choosetype.jsp"/>
            </div>
        </div>
        <div id="tabRepresentative" class="flBox data-url tab-pane" data-ap="representative">
            <jsp:include page="/WEB-INF/views/representative/representative_import.jsp"/>
            <div id="representativeSection">
                <jsp:include page="/WEB-INF/views/representative/representative_choosetype.jsp"/>
            </div>
        </div>
    </div>
</div>
<div id="applicantMatches">
    <jsp:include page="/WEB-INF/views/applicant/applicantMatches.jsp"/>
</div>
<div id="representativeMatches">
    <jsp:include page="/WEB-INF/views/representative/representativeMatches.jsp"/>
</div>