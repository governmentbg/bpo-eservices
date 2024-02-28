<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<header>
    <span id="divisionalCurrentNumber" class="number"></span>

    <h3><spring:message code="claim.title.divisionalApplication.${flowModeId}"/></h3>
</header>

<sec:authorize access="hasRole('Import_Divisional')" var="security_divisional_import" />
<sec:authorize access="hasRole('Import_Divisional_Autocomplete')" var="security_divisional_search" />
<c:set var="service_divisional_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Divisional')}"/>
<c:set var="service_divisional_search" value="${configurationServiceDelegator.isServiceEnabled('Import_Divisional_Autocomplete')}"/>
<c:set var="service_divisional_manual" value="${configurationServiceDelegator.isServiceEnabled('Divisional_Manual')}" />

<c:set var="security_service_divisional_import"
       value="${(security_divisional_import || !configurationServiceDelegator.securityEnabled) && service_divisional_import}"/>

<c:if test="${flowModeId == 'pt-efiling'}">
    <c:set var="importDivisionalCall" value="importPatentDivisional"/>
</c:if>
<c:if test="${flowModeId == 'um-efiling'}">
    <c:set var="importDivisionalCall" value="importUMDivisional"/>
</c:if>
<c:if test="${flowModeId == 'sv-efiling'}">
    <c:set var="importDivisionalCall" value="importSVDivisional"/>
</c:if>

<c:if test="${security_service_divisional_import}">
    <spring:eval scope="page" var="office" expression="@propertyConfigurer.getProperty('sp.office')"/>
    <input type="hidden" id="previousPatentDivisional_viewUrl"
           value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
    <span class="hidden" id="previousPatentDivisional_viewMessage"><spring:message
            code="person.autocomplete.action.viewDetails"/></span>


    <component:import autocompleteUrl="autocompletePatent.htm?office=${office}&flowModeId=${flowModeId}"
                      autocompleteServiceEnabled="Import_Divisional_Autocomplete"
                      permissionAutocomplete="${security_divisional_search || !configurationServiceDelegator.securityEnabled}"
                      minimumCharAutocomplete="minimum.characters.autocomplete"
                      id="importDivisional"
                      importButtonTextCode="claim.form.action.import"
                      labelTextCode="claim.divisionalApplication.field.import"
                      importButtonClass="btn importDivisional"
                      component="general"
                      textCodeWhenEmpty="claim.divisionalApplication.import.whenEmpty"
                      importTextfieldClass="claim-id-input importPatentInput" />
    <input type="hidden" id="divisionalApplication_importId" value=""/>
    <input type="hidden" id="divisionalApplication_importCall" value="${importDivisionalCall}"/>
</c:if>

<c:if test="${service_divisional_import && (security_divisional_import || !configurationServiceDelegator.securityEnabled) && service_divisional_manual}">
    <span class="separator"><spring:message code="claim.title.or" /></span>
</c:if>

<c:if test="${service_divisional_manual}">
    <button type="button" id="createManualDivisional" class="create-manually-button">
        <i class="create-icon"></i>
        <spring:message code="claim.action.manually" />
    </button>
</c:if>

<c:if test="${security_service_divisional_import}">
    <br>
    <span class="tip"><spring:message code="claim.divisionalApplication.import.hint.${flowModeId}"/></span>
</c:if>