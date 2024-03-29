<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
    <!--
    $('.cancelSeniorityButton').live("click", function ()
    {
        cancelSeniority();
    });

    function cancelSeniority()
    {
        $('#tabSeniority').hide();
        $('.seniority').removeClass('active');
        $('#openSeniority').parent().removeClass('active');
        markClaimNoButtonActive(".seniority", "#noSeniority");
    }
    //-->
</script>

<div class="addbox claimFields">
    <form:form modelAttribute="seniorityForm" id="seniorityForm" cssClass="fileUploadForm formEF">
        <c:set var="sectionId" value="seniority" scope="request"/>
        <c:set var="imported" value="${seniorityForm.imported}" scope="request"/>
        <c:set var="service_seniority_manual" value="${configurationServiceDelegator.isServiceEnabled('Seniority_Manual')}"/>

        <header>
            <h3><spring:message code="claim.title.seniority"/></h3>
            <ul class="controls">
                <li>
                    <a class="cancelSeniorityButton">
                        <spring:message code="claim.action.cancel"/>
                    </a>
                </li>
                <c:if test="${service_seniority_manual}">
                    <li>
                        <button type="button" class="addSeniorityWizard">
                            <c:choose>
                                <c:when test="${empty seniorityForm.id}">
                                    <spring:message code="claim.action.add"/>
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="claim.action.save"/>
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </li>
                </c:if>
            </ul>
        </header>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedSeniority"/>
        <component:select items="${configurationServiceDelegator['countries']}" labelTextCode="claim.seniority.field.memberState"
                         path="country" itemLabel="value"
                         itemValue="code" checkRender="true"/>

        <sec:authorize access="hasRole('Seniority_Import')" var="security_seniority_import" />
        <sec:authorize access="hasRole('Seniority_Search')" var="security_seniority_search" />

        <c:set var="service_seniority_import" value="${configurationServiceDelegator.isServiceEnabled('Seniority_Import')}"/>

        <c:choose>
            <c:when test="${empty seniorityForm.id}">
                <c:set var="newFormDetailsClass" value=""/>
                <c:set var="manualDetailsClass" value="hide"/>
                <c:set var="bottomCancelButtonClass" value="cancelManualButton"/>
            </c:when>
            <c:otherwise>
                <c:set var="newFormDetailsClass" value="hide"/>
                <c:set var="manualDetailsClass" value=""/>
                <c:set var="bottomCancelButtonClass" value="cancelSeniorityButton"/>
            </c:otherwise>
        </c:choose>

        <div id="newFormDetails" class="${newFormDetailsClass}">
            <c:if test="${service_seniority_import && (security_seniority_import || !configurationServiceDelegator.securityEnabled)}">
                <c:set var="service_seniority_autocomplete_detailsUrlEnabled"
                       value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.seniority.details.urlEnabled')}"/>
                <input type="hidden" id="seniorityTradeMarkConfig_searchUrlEnabled"
                       value="${service_seniority_autocomplete_detailsUrlEnabled}"/>
                <c:if test="${service_seniority_autocomplete_detailsUrlEnabled}">
                    <input type="hidden" id="seniorityTradeMarkConfig_searchUrl"
                           value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.seniority.details.url")}"/>
                    <span class="hidden" id="seniorityTradeMarkConfig_viewMessage"><spring:message
                            code="person.autocomplete.action.viewDetails"/></span>
                </c:if>
                <component:import autocompleteUrl="autocompleteTrademark.htm?previousCTM=false"
                                 autocompleteServiceEnabled="Seniority_Autocomplete"
                                 minimumCharAutocomplete="minimum.characters.autocomplete"
                                 permissionAutocomplete="${security_seniority_search || !configurationServiceDelegator.securityEnabled}"
                                 id="lastSeniority"
                                 importButtonTextCode="claim.action.import"
                                 labelTextCode="claim.seniority.field.lastSeniority"
                                 importButtonClass="btn importSeniority"
                                 component="general"
                                 importTextfieldClass="claim-id-input"
                                 dataask="service.import.seniority.extraImportWizard"
                                 textCodeWhenEmpty="claim.seniority.import.whenEmpty"
                                 footerTextCode="claim.seniority.field.footerImport"/>
                <!--button id="" class="importSeniority" data-ask="false" type="button"><spring:message code="claim.action.import" /></button-->
                <input type="hidden" id="seniorityTradeMark_importId" value=""/>
            </c:if>
            <c:if test="${service_seniority_import && (security_seniority_import || !configurationServiceDelegator.securityEnabled) && service_seniority_manual}">
                <span class="separator"><spring:message code="claim.title.or"/></span>
            </c:if>
            <c:if test="${service_seniority_manual}">
                <button type="button" id="createManualDetails" class="create-manually-button">
                    <i class="create-icon"></i>
                    <spring:message code="claim.action.manually"/>
                </button>
            </c:if>


        </div>

        <div id="manualDetails" class="${manualDetailsClass}">

            <component:select items="${configurationServiceDelegator.getSeniorityNaturesByModule('tmefiling')}"
                             labelTextCode="claim.seniority.field.nature" path="nature"
                             itemLabel="value"
                             itemValue="code" checkRender="true"/>


            <component:input path="filingDate" checkRender="true" id="fillingDate"
                            labelTextCode="claim.seniority.field.filingDate"
                            formTagClass="filing-date-input"/>

            <component:input path="filingNumber" checkRender="true" id="fillingNum"
                            labelTextCode="claim.seniority.field.filingNumber"
                            formTagClass="filing-number-input"/>


            <component:input path="registrationDate" checkRender="true" id="regDate"
                            labelTextCode="claim.seniority.field.registrationDate"
                            formTagClass="filing-date-input"/>

            <component:input path="registrationNumber" checkRender="true" id="regNum"
                            labelTextCode="claim.seniority.field.registrationNumber"
                            formTagClass="filing-number-input"/>


            <div id="fileWrapperCopy" class="fileuploadInfo collectiveSelectors">
                <component:file labelCode="claim.seniority.firstFiling.copy" pathFilename="fileSeniorityCopy"
                                fileWrapperPath="fileWrapperCopy" fileWrapper="${seniorityForm.fileWrapperCopy}"/>
            </div>

            <div id="fileSeniorityCertificate" class="fileuploadInfo collectiveSelectors">
                <component:file labelCode="claim.seniority.firstFiling.translation" pathFilename="fileSeniorityTranslation"
                                fileWrapperPath="fileSeniorityCertificate" fileWrapper="${seniorityForm.fileSeniorityCertificate}"/>
            </div>
            <ul class="controls">
                <li>
                    <a class="${bottomCancelButtonClass}">
                        <spring:message code="claim.action.cancel"/></a>
                </li>
                <c:if test="${service_seniority_manual}">
                    <li>
                        <button type="button" class="addSeniorityWizard">
                            <c:choose>
                                <c:when test="${empty seniorityForm.id}">
                                    <spring:message code="claim.action.add"/>
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="claim.action.save"/>
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </li>
                </c:if>
            </ul>
        </div>


        <br>
    </form:form>
</div>