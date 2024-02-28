<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<jsp:include page="parallel_application_section_set.jsp"/>

<c:set var="importVisible" value="false"></c:set>
<component:generic path="importParallelApplication" checkRender="true">
    <c:set var="importVisible" value="true"/>

    <sec:authorize access="hasRole('ParallelApplication_Import')" var="security_parallelApplication_import" />
    <sec:authorize access="hasRole('ParallelApplication_Autocomplete')" var="security_parallelApplication_search" />
    <c:set var="service_parallelApplication_import" value="${configurationServiceDelegator.isServiceEnabled('ParallelApplication_Import')}"/>
    <c:set var="service_parallelApplication_search" value="${configurationServiceDelegator.isServiceEnabled('ParallelApplication_Autocomplete')}"/>
    <c:set var="service_parallelApplication_manual" value="${configurationServiceDelegator.isServiceEnabled('ParallelApplication_Manual')}" />

    <c:set var="security_service_parallelApplication_import"
           value="${(security_parallelApplication_import || !configurationServiceDelegator.securityEnabled) && service_parallelApplication_import}"/>

    <c:if test="${security_service_parallelApplication_import}">
        <input type="hidden" id="previousPatentParallel_viewUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
        <span class="hidden" id="previousPatentParallel_viewMessage"><spring:message
                code="person.autocomplete.action.viewDetails"/></span>

        <component:import autocompleteUrl="autocompletePatent.htm?office=${country}&flowModeId=pt-"
                          autocompleteServiceEnabled="ParallelApplication_Autocomplete"
                          permissionAutocomplete="${security_parallelApplication_search || !configurationServiceDelegator.securityEnabled}"
                          minimumCharAutocomplete="minimum.characters.autocomplete"
                          id="importParallelApplication"
                          importButtonTextCode="claim.form.action.import"
                          labelTextCode="claim.parallelApplication.field.import"
                          importButtonClass="btn importParallelApplication"
                          component="general"
                          textCodeWhenEmpty="claim.parallelApplication.import.whenEmpty"
                          importTextfieldClass="claim-id-input importPatentInput" />
        <input type="hidden" id="parallelApplication_importId" value=""/>
        <input type="hidden" id="parallelApplication_importCall" value="${importParallelCall}"/>
    </c:if>

    <c:if test="${service_parallelApplication_import && (security_parallelApplication_import || !configurationServiceDelegator.securityEnabled) && service_parallelApplication_manual}">
        <span class="separator"><spring:message code="claim.title.or" /></span>
    </c:if>

    <c:if test="${service_parallelApplication_manual}">
        <button type="button" id="createManualParallelApplication" class="create-manually-button">
            <i class="create-icon"></i>
            <spring:message code="claim.action.manually" />
        </button>
    </c:if>

    <c:if test="${security_service_parallelApplication_import}">
        <c:if test="${sectionId  == 'national_parallel_application'}">
            <br>
            <span class="tip"><spring:message code="claim.national_parallel_application.import.hint"/></span>
        </c:if>
        <c:if test="${sectionId  == 'european_parallel_application'}">
            <br>
            <span class="tip"><spring:message code="claim.european_parallel_application.import.hint"/></span>
        </c:if>
    </c:if>
</component:generic>

<c:if test="${importVisible == false}">
    <script type="text/javascript">
        callParallelApplicationFormImmediately('${country}');
    </script>
</c:if>