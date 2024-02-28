<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="representative" id="representative">
    <header>
        <a id="representative" class="anchorLink">
        </a>

        <h2><spring:message code="representative.section.title"></spring:message></h2>
    </header>

    <div class="alert alert-info">
        <spring:message code="representative.pow.details.info"/>
    </div>

    <jsp:include page="/WEB-INF/views/representative/representative_card_list.jsp"/>

    <sec:authorize access="hasRole('Representative_Add')" var="security_representative_add" />

    <c:if test="${security_representative_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="representativeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.representative"/>
        </button>

        <div id="tabRepresentative" class="addbox" style="display:none">
            <jsp:include page="/WEB-INF/views/representative/representative_import.jsp"/>
            <div id="representativeSection">
            </div>
        </div>

        <div id="representativeMatches">
            <jsp:include page="/WEB-INF/views/representative/representativeMatches.jsp"/>
        </div>
    </c:if>

    <div id="pis_partnershipRepresentativeMsg" style="display: none"><spring:message code="representative.association.shortName"/> </div>
    <div id="pisRepresentativeMsg" style="display: none"><spring:message code="representative.naturalPerson.shortName"/> </div>
    <div id="pis_companyRepresentativeMsg" style="display: none"><spring:message code="representative.legalentity.shortName"/> </div>
    <div id="lawyerRepresentativeMsg" style="display: none"><spring:message code="representative.lawyer.shortName"/> </div>
    <div id="lawyer_partnershipRepresentativeMsg" style="display: none"><spring:message code="representative.lawyerpartnership.shortName"/> </div>
    <div id="lawyer_companyRepresentativeMsg" style="display: none"><spring:message code="representative.lawyercompany.shortName"/> </div>
    <div id="temporaryRepresentativeMsg" style="display: none"><spring:message code="representative.temporary.shortName"/> </div>
</section>
