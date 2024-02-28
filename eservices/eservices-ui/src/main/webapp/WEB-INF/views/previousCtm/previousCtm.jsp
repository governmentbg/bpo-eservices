<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<sec:authorize access="hasRole('Import_Previous')" var="security_import_previous" />
<sec:authorize access="hasRole('CTM_Search')" var="security_ctm_search" />

<c:set var="service_previous_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Previous')}"/>
<c:if test="${(security_import_previous || !configurationServiceDelegator.securityEnabled) && service_previous_import}">
    <section class="import" id="previousCTM">
        <c:set var="service_previousCtm_autocomplete_detailsUrlEnabled"
               value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.details.urlEnabled')}"/>
        <input type="hidden" id="previousCtmConfig_searchUrlEnabled"
               value="${service_previousCtm_autocomplete_detailsUrlEnabled}"/>

        <spring:eval var="office_code" expression="@propertyConfigurer.getProperty('sp.office')"/>
        <input type="hidden" id="previousCtmConfig_officeCode" value="${office_code}"/>
        <c:if test="${service_previousCtm_autocomplete_detailsUrlEnabled}">
            <input type="hidden" id="previousCtmConfig_searchUrl"
                   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.details.url")}"/>
            <span class="hidden" id="previousCtmConfig_viewMessage"><spring:message
                    code="person.autocomplete.action.viewDetails"/></span>
        </c:if>

        <header>
            <a id="previousCTM" class="anchorLink"></a>

            <h2><spring:message code="previousCtm.title"/></h2>
        </header>

        <form:form id="previousCtmForm">
            <c:set var="sectionId" value="previousCTM" scope="request"/>
            <%--<div class="previousCtm formSection">--%>
            <component:generic path="applicationId" checkRender="true">
                <%--<p class="form-inline">--%>
                <%--<span class="input-append">				--%>
                <%--<label class="offset" for="IDpreviousCTM"><spring:message code="previousCtm.idCtm"/></label>--%>

                <component:import autocompleteUrl="autocompleteTrademark.htm?previousCTM=true&office=${office_code}"
                                 permissionAutocomplete="${security_ctm_search || !configurationServiceDelegator.securityEnabled}"
                                 autocompleteServiceEnabled="Import_Previous_Autocomplete"
                                 minimumCharAutocomplete="minimum.characters.autocomplete"
                                 id="IDpreviousCTM"
                                 importButtonClass="import-button importPreviousCTM"
                                 importButtonTextCode="previousCtm.action.import"
                                 component="general"
                                 textCodeWhenEmpty="previousCtm.importBox.whenEmpty"/>
                <input type="hidden" id="previousCTM_importId" value=""/>
                <%--<span class="hidden" id="previousCTM_textWhenEmpty">--%>
                <%--<spring:message code="previousCtm.importBox.whenEmpty"/>--%>
                <%--</span>--%>
                <%--</span>--%>
                <c:if test="${security_ctm_search || !configurationServiceDelegator.securityEnabled}">
                    <c:set var="previousCtmSearchUrl"
                           value="${configurationServiceDelegator.getValueFromGeneralComponent('service.previousCtm.search.url')}"/>
                    <button type="button" class="import-button" id="searchForTrademarkBtn"
                            data-ask="${previousCtmSearchUrl}"><spring:message
                            code="previousCtm.action.search"/>
                        <%--<a href="${previousCtmSearchUrl}" target="_blank"></a>--%>
                    </button>
                </c:if>
                <%--</p><!--form-inline -->--%>
                <br>
                <span class="tip"><spring:message code="previousCtm.helpfulText"/></span>
            </component:generic>
            <%--</div>--%>
            <!--previousCtm-->
        </form:form>
    </section>
</c:if>
