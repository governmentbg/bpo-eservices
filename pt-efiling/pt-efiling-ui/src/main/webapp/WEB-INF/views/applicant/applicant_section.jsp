<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="applicant" id="applicant">

    <header>
        <a href="#applicant" class="anchorLink">
        </a>

        <h2><spring:message code="applicant.section.title.${flowModeId}"></spring:message><span class="requiredTag">*</span></h2>
    </header>

    <jsp:include page="/WEB-INF/views/applicant/applicant_card_list.jsp"/>
    <sec:authorize access="hasRole('Applicant_Add')" var="security_applicant_add" />
    <c:if test="${security_applicant_add || !configurationServiceDelegator.securityEnabled}">

        <c:if test="${!flowBean.applicantsImportedFromTemplate}">
            <button id="applicantTrigger" class="add-button" data-toggle="button" type="button">
                <i class="add-icon"></i>
                <spring:message code="person.button.applicant"/>
            </button>
        </c:if>

        <c:if test="${flowBean.applicantsImportedFromTemplate}">
            <div class="alert alert-info"><spring:message code="applicantsImportedFromTemplate.imported.can.not.edit"/></div>
        </c:if>
        <div id="tabApplicant" class="addbox" style="display: none">
            <jsp:include page="/WEB-INF/views/applicant/applicant_import.jsp"/>
            <div id="applicantSection">
            </div>
            <br />
        </div>

        <div id="applicantMatches">
            <jsp:include page="/WEB-INF/views/applicant/applicantMatches.jsp"/>
        </div>
    </c:if>
</section>
<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/persons
</div>