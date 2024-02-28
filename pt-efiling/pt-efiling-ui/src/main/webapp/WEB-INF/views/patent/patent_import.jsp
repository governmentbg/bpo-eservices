<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 21.10.2019 г.
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('Import_Patent_Template')" var="security_import" />
<sec:authorize access="hasRole('Import_Patent_Template_Autocomplete')" var="security_search" />
<c:set var="service_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Patent_Template')}"/>
<c:set var="service_search" value="${configurationServiceDelegator.isServiceEnabled('Import_Patent_Template_Autocomplete')}"/>

<c:set var="security_service_import"
       value="${(security_import || !configurationServiceDelegator.securityEnabled) && service_import}"/>

<section id="patent_import" class="patent">
    <header>
        <a id="patent_import_section" class="anchorLink">
        </a>

        <h2><spring:message code="patent.import.title"></spring:message></h2>
    </header>

    <c:if test="${security_service_import}">
        <form:form id="patentImportForm" >
            <c:set var="sectionId" value="patent_import" scope="request"/>

            <c:set var="identifierKindDisplayed" value="false"/>
            <component:generic path="identifierKind" checkRender="true">
                <c:set var="identifierKindDisplayed" value="true"/>

                <input type="radio" value="application" name="identifierKind" checked="checked" /> <span style="margin-right:10px"><spring:message code="patent.import.applicationNumber"/></span>
                <c:if test="${not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION'}">
                    <input type="radio" value="registration" name="identifierKind"/> <span style="margin-right:10px"><spring:message code="patent.import.registrationNumber"/></span>
                </c:if>
            </component:generic>

            <c:if test="${identifierKindDisplayed == false}">
                <input type="radio" value="application" name="identifierKind" checked="checked" style="display: none"/>
            </c:if>

            <div class="import">
                <spring:eval scope="page" var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
                <input type="hidden" id="previousPatent_viewUrl"
                       value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
                <span class="hidden" id="previousPatent_viewMessage"><spring:message
                        code="person.autocomplete.action.viewDetails"/></span>

                <component:import autocompleteUrl="autocompletePatent.htm?office=${euipo_office}&flowModeId=${flowModeId}"
                                  autocompleteServiceEnabled="Import_Patent_Template_Autocomplete"
                                  permissionAutocomplete="${security_search || !configurationServiceDelegator.securityEnabled}"
                                  importButtonClass="import-button importTemplatePatent"
                                  minimumCharAutocomplete="minimum.characters.autocomplete"
                                  id="importTemplatePatent"
                                  importButtonTextCode="patent.import.action.import"
                                  labelTextCode="patent.import.field"
                                  component="general"
                                  textCodeWhenEmpty="patent.import.field.whenEmpty"
                                  importTextfieldClass="patent-id-input importPatentInput" />

                <input type="hidden" id="templatePatent_importId" value=""/>

                <br>
                <span class="tip"><spring:message code="patent.import.field.helpfulText"/></span>
            </div>
        </form:form>
    </c:if>

</section>