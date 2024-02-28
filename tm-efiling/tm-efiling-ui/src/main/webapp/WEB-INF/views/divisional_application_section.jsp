<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<sec:authorize access="hasRole('Import_Divisional')" var="security_import_divisional" />
<sec:authorize access="hasRole('CTM_Search')" var="security_ctm_search" />

<section id="divisionalApplicationDivSection" class="reference">

    <c:set var="sectionId" value="divisionalApplicationSection" scope="request"/>
    <header>
        <a id="divisionalApplicationSection" class="anchorLink"></a>

        <h2><spring:message code="divisionalApplication.title"/></h2>
    </header>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
        <div>
            <component:checkbox path="claimDivisionalApplication" checkRender="true"
                               id="partialCheck" labelTextCode="divisionalApplication.field.claim" formTagClass=""/>

            <div style="display:none">
                <component:input id="divisionalApplicationImported" path="divisionalApplicationImported"  />
                <c:set var="imported" value="${flowBean.mainForm.divisionalApplicationImported}" scope="request"/>
            </div>

			<c:if test="${!flowBean.mainForm.claimDivisionalApplication}">
                <c:set var="divisionalApplicationContainerClass" value="hide"/>
            </c:if> 
            <div class="fields import ${divisionalApplicationContainerClass}">

                <c:set var="service_divisional_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Divisional')}"/>
                <c:choose>
                    <c:when test="${(security_import_divisional || !configurationServiceDelegator.securityEnabled) && service_divisional_import}">
                        <c:set var="service_divisionalCtm_autocomplete_detailsUrlEnabled"
                               value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.details.urlEnabled')}"/>
                        <input type="hidden" id="divisionalCtmConfig_searchUrlEnabled"
                               value="${service_divisionalCtm_autocomplete_detailsUrlEnabled}"/>

                        <spring:eval var="office_code" expression="@propertyConfigurer.getProperty('tmefiling.office')"/>
                        <input type="hidden" id="divisionalCtmConfig_officeCode" value="${office_code}"/>
                        <c:if test="${service_divisionalCtm_autocomplete_detailsUrlEnabled}">
                            <input type="hidden" id="divisionalCtmConfig_searchUrl"
                                   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.details.url")}"/>
                            <span class="hidden" id="divisionalCtmConfig_viewMessage"><spring:message
                                    code="person.autocomplete.action.viewDetails"/></span>
                        </c:if>

                        <c:choose>
                            <c:when test="${not empty imported && imported}">
                                <component:input path="numberDivisionalApplication" checkRender="true" id="divisionalNumber1"
                                                 labelTextCode="divisionalApplication.field.number" formTagClass="span4"/>
                            </c:when>
                            <c:otherwise>
                                <component:import autocompleteUrl="autocompleteTrademark.htm?previousCTM=false&office=${office_code}"
                                                  permissionAutocomplete="${security_ctm_search || !configurationServiceDelegator.securityEnabled}"
                                                  autocompleteServiceEnabled="Import_Divisional_Autocomplete"
                                                  minimumCharAutocomplete="minimum.characters.autocomplete"
                                                  id="IDdivisionalCTM"
                                                  importButtonClass="import-button importDivisionalCTM"
                                                  importButtonTextCode="previousCtm.action.import"
                                                  component="general"
                                                  textCodeWhenEmpty="previousCtm.importBox.whenEmpty"
                                                  labelTextCode="divisionalApplication.field.number"
                                                  inputTextName="numberDivisionalApplication"/>


                                <br>
                                <span class="tip"><spring:message code="previousCtm.helpfulText"/></span>

                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <component:input path="numberDivisionalApplication" checkRender="true" id="divisionalNumber1"
                                        labelTextCode="divisionalApplication.field.number" formTagClass="span4"/>
                    </c:otherwise>
                </c:choose>

                <component:input path="dateDivisionalApplication" checkRender="true" id="divisionalDate1"
                                labelTextCode="divisionalApplication.field.dateOfFiling" formTagClass="standard-date-input"/>

            </div>
        </div>
    </form:form>

</section>
