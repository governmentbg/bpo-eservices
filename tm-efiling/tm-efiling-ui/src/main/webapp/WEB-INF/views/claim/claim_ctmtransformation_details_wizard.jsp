<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
    <!--
    $('.cancelCtmTransformationButton').live("click", function ()
    {
        cancelCtmTransformation();
    });

    function cancelCtmTransformation()
    {
        $('#tabCtmTransformation').html('');
        $('#openCtmTransformationLi').removeClass('active');
        $('.ctmtransformation').removeClass('active');
        markClaimNoButtonActive(".conversion", "#noCtmTransformation");
    }
    //-->
</script>

<div class="addbox claimFields">
    <form:form modelAttribute="transformationForm" id="ctmTransformationForm" cssClass="fileUploadForm formEF">
        <c:set var="sectionId" value="ctmTransformation" scope="request"/>
        <c:set var="imported" value="${transformationForm.imported}" scope="request"/>
        <c:set var="service_ctm_transformation_manual" value="${configurationServiceDelegator.isServiceEnabled('CTM_Transformation_Manual')}"/>
        <spring:eval var="ohim_office_code" expression="@propertyConfigurer.getProperty('tmefiling.ohim.office')"/>

        <header>
            <h3><spring:message code="claim.title.ctm.transformation"/></h3>
            <ul class="controls">
                <li>
                    <a class="cancelCtmTransformationButton">
                        <spring:message code="claim.action.cancel"/>
                    </a>
                </li>
            </ul>
        </header>

        <form:hidden path="id"/>
        <form:hidden path="imported" id="ctmImportedTransformation"/>
        <form:hidden path="transformationCountryCode" name="transformationCountryCode" id="ctmTransformationCountryCode"/>
        <input type="hidden" id="maxTransformations" value="${configurationServiceDelegator.getValueFromGeneralComponent('claim.transformation.add.maxNumber')}"/>

        <sec:authorize access="hasRole('CTM_Transformation_Import')" var="security_ctm_transformation_import" />
        <sec:authorize access="hasRole('CTM_Transformation_Search')" var="security_ctm_transformation_search" />

        <c:set var="service_ctm_transformation_import"
               value="${configurationServiceDelegator.isServiceEnabled('CTM_Transformation_Import')}"/>

        <c:choose>
            <c:when test="${empty transformationForm.id}">
                <c:set var="newFormDetailsClass" value=""/>
                <c:set var="manualDetailsClass" value="hide"/>
                <c:set var="bottomCancelButtonClass" value="cancelManualButton"/>
            </c:when>
            <c:otherwise>
                <c:set var="newFormDetailsClass" value="hide"/>
                <c:set var="manualDetailsClass" value=""/>
                <c:set var="bottomCancelButtonClass" value="cancelCtmTransformationButton"/>
            </c:otherwise>
        </c:choose>

        <div id="ctmNewFormDetails" class="${newFormDetailsClass}">
            <c:if test="${service_ctm_transformation_import && (security_ctm_transformation_import || !configurationServiceDelegator.securityEnabled)}">
                <c:set var="service_ctm_transformation_autocomplete_detailsUrlEnabled"
                       value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.ctm.transformation.details.urlEnabled')}"/>
                <input type="hidden" id="ctmTransformationTradeMarkConfig_searchUrlEnabled"
                       value="${service_ctm_transformation_autocomplete_detailsUrlEnabled}"/>
                <input type="hidden" id="ctmTransformationTradeMark_officeCode" value="${ohim_office_code}"/>
                <c:if test="${service_ctm_transformation_autocomplete_detailsUrlEnabled}">
                    <input type="hidden" id="ctmTransformationTradeMarkConfig_searchUrl"
                           value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.ctm.transformation.details.url")}"/>
                    <span class="hidden" id="ctmTransformationTradeMarkConfig_viewMessage"><spring:message
                            code="person.autocomplete.action.viewDetails"/></span>
                </c:if>

                <component:import autocompleteUrl="autocompleteTrademark.htm?previousCTM=false&office=${ohim_office_code}"
                                 autocompleteServiceEnabled="CTM_Transformation_Autocomplete"
                                 minimumCharAutocomplete="${trademark_autocomplete_minimumChar}"
                                 permissionAutocomplete="${security_ctm_transformation_search || !configurationServiceDelegator.securityEnabled}"
                                 id="lastCtmTransformation"
                                 importButtonTextCode="claim.action.import"
                                 labelTextCode="claim.ctm.transformation.field.lastTransformation"
                                 importButtonClass="btn importCtmTransformation"
                                 component="general"
                                 dataask="service.import.ctm.transformation.extraImportWizard"
                                 importTextfieldClass="claim-id-input"
                                 textCodeWhenEmpty="claim.ctm.transformation.import.whenEmpty"
                                 footerTextCode="claim.ctm.transformation.field.footerImport"/>
                <input type="hidden" id="ctmTransformationTradeMark_importId" value=""/>
				
            </c:if>

            <c:if test="${service_ctm_transformation_import && (security_ctm_transformation_import || !configurationServiceDelegator.securityEnabled) && service_ctm_transformation_manual}">
                <span class="separator"><spring:message code="claim.title.or"/></span>
            </c:if>

            <c:if test="${service_ctm_transformation_manual}">
                <button type="button" id="ctmCreateManualDetails" class="create-manually-button">
                    <i class="create-icon"></i>
                    <spring:message code="claim.action.manually"/>
                </button>
            </c:if>
            <c:if test="${service_ctm_transformation_import && (security_ctm_transformation_import || !configurationServiceDelegator.securityEnabled)}">
            	<br>
            	<span class="tip"><spring:message code="claim.ctm.transformation.helpfulText"/></span>
            </c:if>
        </div>

        <div id="manualDetails" class="${manualDetailsClass}">
            <component:input path="irNumber" checkRender="true" id="ctmIrNum" labelTextCode="claim.ctm.transformation.field.irNumber"
                        formTagClass="irNumber"/>
            <component:input path="cancelationDate" checkRender="true" id="ctmCancelDate"
                            labelTextCode="claim.ctm.transformation.field.cancelDate" formTagClass="filing-date-input"/>

            <component:input path="irDate" checkRender="true" id="ctmIrDate" labelTextCode="claim.ctm.transformation.field.irDate"
                            formTagClass="filing-date-input"/>

            <component:input path="priosenioDate" checkRender="true" id="ctmPsDate"
                            labelTextCode="claim.ctm.transformation.field.prioritySeniorityDate" formTagClass="filing-date-input"/>
            
            <ul class="controls">
                <li>
                    <a class="${bottomCancelButtonClass}"
                       title="labels.confirmCancelForm">
                        <spring:message code="claim.action.cancel"/></a>
                </li>
                <c:if test="${service_ctm_transformation_manual}">
                    <li>
                        <button type="button" class="addCtmTransformationWizard">
                            <c:choose>
                                <c:when test="${empty transformationForm.id}">
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
            <br>
        </div>
    </form:form>
    <%--<c:if test="${service_transformation_manual}">--%>
    <%--<div class="row">--%>
    <%--<p class="flBox data-url" data-rel="tab1_irtransformation">--%>
    <%--<a class="addCtn addAnotherTransformationWizard"><span--%>
    <%--><spring:message code="claim.transformation.action.addAnother"/></span></a>--%>
    <%--</p>--%>
    <%--</div>--%>
    <%--</c:if>--%>
</div>