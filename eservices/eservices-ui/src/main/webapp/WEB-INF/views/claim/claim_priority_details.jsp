<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="addbox claimFields">
<form:form cssClass="fileUploadForm formEF"
           modelAttribute="priorityForm" id="priorityForm">
<c:set var="sectionId" value="priority" scope="request"/>
<c:set var="imported" value="${priorityForm.imported}" scope="request"/>
<c:set var="service_priority_manual"
       value="${configurationServiceDelegator.isServiceEnabled('Priority_Manual')}"/>
<header>
			<span class="number"><fmt:formatNumber
                    value="${entityPosition}" minIntegerDigits="2"/></span>

    <h3>
        <spring:message code="claim.title.priority"/>
    </h3>
    <ul class="controls">
        <li><a class="cancelButton" ><spring:message
                code="claim.action.cancel"/></a></li>
        <c:if test="${service_priority_manual}">
            <li>
                <button type="button" class="addPriority">
                    <i class="claim-button-add"></i>
                    <c:choose>
                        <c:when test="${empty priorityForm.id}">
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
<fieldset>
        <form:hidden path="id"/>
        <form:hidden path="imported" id="importedPriority"/>

        <component:select
                items="${configurationServiceDelegator['countries']}"
                labelTextCode="claim.priority.field.countryOfLastFiling"
                path="country" itemLabel="value" itemValue="code"
                itemFilter="isPriority" checkRender="true"/>

        <sec:accesscontrollist var="security_priority_import"
                               hasPermission="Priority_Import" domainObject="${flowModeId}"/>
    <!-- fix also import tag for the security check -->
        <sec:accesscontrollist var="security_priority_search"
                               hasPermission="Priority_Search" domainObject="${flowModeId}"/>
        <c:set var="service_priority_import"
               value="${configurationServiceDelegator.isServiceEnabled('Priority_Import')}"/>

    <c:choose>
    <c:when test="${empty priorityForm.id}">
        <c:set var="newFormDetailsClass" value=""/>
        <c:set var="manualDetailsClass" value="hide"/>
        <c:set var="bottomCancelButtonClass" value="cancelManualButton"/>
    </c:when>
    <c:otherwise>
        <c:set var="newFormDetailsClass" value="hide"/>
        <c:set var="manualDetailsClass" value=""/>
        <c:set var="bottomCancelButtonClass" value="cancelButton"/>
    </c:otherwise>
    </c:choose>

    <div id="newFormDetails" class="${newFormDetailsClass}">
        <c:if test="${service_priority_import && (security_priority_import || !configurationServiceDelegator.securityEnabled)}">
            <c:set var="service_priority_autocomplete_detailsUrlEnabled"
                   value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.priority.details.urlEnabled')}"/>
            <input type="hidden" id="priorityTradeMarkConfig_searchUrlEnabled"
                   value="${service_priority_autocomplete_detailsUrlEnabled}"/>
            <c:if test="${service_priority_autocomplete_detailsUrlEnabled}">
                <input type="hidden" id="priorityTradeMarkConfig_searchUrl"
                       value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.priority.details.url")}"/>
						<span class="hidden" id="priorityTradeMarkConfig_viewMessage"><spring:message
                                code="person.autocomplete.action.viewDetails"/></span>
            </c:if>

            <component:import
                    autocompleteUrl="autocompleteTrademark.htm?previousCTM=false"
                    autocompleteServiceEnabled="Priority_Autocomplete"
                    minimumCharAutocomplete="minimum.characters.autocomplete"
                    permissionAutocomplete="${security_priority_search || !configurationServiceDelegator.securityEnabled}"
                    id="lastPriority"
                    labelTextCode="claim.priority.field.lastPriority"
                    importButtonTextCode="claim.action.import"
                    importButtonClass="btn importPriority" component="general"
                    dataask="service.import.priority.extraImport"
                    textCodeWhenEmpty="claim.priority.import.whenEmpty"
                    importTextfieldClass="claim-id-input"/>
            <input type="hidden" id="priorityTradeMark_importId" value=""/>
        </c:if>

        <c:if
                test="${service_priority_import && (security_priority_import || !configurationServiceDelegator.securityEnabled) && service_priority_manual}">
					<span class="separator"><spring:message
                            code="claim.title.or"/></span>
        </c:if>
        <c:if test="${service_priority_manual}">
            <button type="button" id="createManualDetails" class="create-manually-button">
                <i class="create-icon"></i>
                <spring:message code="claim.action.manually"/>
            </button>
        </c:if>
        <c:if test="${service_priority_import && (security_priority_import || !configurationServiceDelegator.securityEnabled)}">
            <div class="tip"><spring:message code="claim.priority.field.footerImport"/></div>
        </c:if>
    </div>

    <hr>
    <div id="manualDetails" class="${manualDetailsClass}">

        <component:input path="date" checkRender="true" id="wDate"
                         labelTextCode="claim.priority.field.onWhatDate"
                         formTagClass="filing-date-input"/>
        <component:input path="number" checkRender="true" id="fNumber"
                         labelTextCode="claim.priority.field.filingNumber"
                         formTagClass="filing-number-input"/>

        <div id="fileWrapperCopy" class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.priority.firstFiling.copy"
                            pathFilename="filePriorityCopy" fileWrapperPath="fileWrapperCopy"
                            fileWrapper="${priorityForm.fileWrapperCopy}"/>
        </div>

        <div id="fileWrapperTranslation"
             class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.priority.firstFiling.translation"
                            pathFilename="filePriorityTranslation"
                            fileWrapperPath="fileWrapperTranslation"
                            fileWrapper="${priorityForm.fileWrapperTranslation}"/>
        </div>

        <div id="filePriorityCertificate"
             class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.priority.certificate"
                            pathFilename="filePriorityCertificate"
                            fileWrapperPath="filePriorityCertificate"
                            fileWrapper="${priorityForm.filePriorityCertificate}"/>
        </div>

        <!--                 PARTIAL PRIORITY CLAIM -->
        <component:generic path="goodsServices" checkRender="true">
        <p class="checkItem">
            <input type="checkbox" id="claimPriority1" class="togglePartial" name="partialPriority" rel="partialInfo">
            <label
                    for="claimPriority1">
                <spring:message code="claim.priority.partial.check"/>
            </label>
        </p>

        <div id="partialInfo">
            <p>
                <spring:message code="claim.priority.partial.select"/>
            </p>

            <div class="classNumber">
                <label><spring:message
                        code="claim.priority.partial.class"/><em class="requiredTag">(*)</em></label>
                <select class="span1">
                    <c:forEach begin="1"
                               end="${configurationServiceDelegator.getValueFromGeneralComponent('service.goods.classes.number')}"
                               var="classHeading">
                        <option value="${classHeading}">${classHeading}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="termsDescription">
                <label for="addClassDesc"><spring:message
                        code="claim.priority.partial.list"/><em class="requiredTag">(*)</em></label>
                <input type="text" class="addClassDesc" id="addClassDesc"/>
            </div>

            <button type="button" class="addClass" data-id="${classRow.classId}">
                <spring:message
                        code="claim.priority.partial.add"/>
            </button>
                <%--<p class="span1">--%>
                <%--<label>&nbsp;</label> <a class="btn addClass"--%>
                <%--data-id="${classRow.classId}"><spring:message--%>
                <%--code="claim.priority.partial.add"/></a>--%>
                <%--</p>--%>

            <!-- 							this container is refreshed after the ajax calls -->
            <div class="partialGoodServicesContainer">
                <jsp:include page="claim_priority_goodsservices.jsp"></jsp:include>
            </div>
        </div>
    </div>
    </component:generic>

    <footer>
        <ul class="controls">
            <li><a class="${bottomCancelButtonClass}" ><spring:message
                    code="claim.action.cancel"/></a></li>
            <c:if test="${service_priority_manual}">
                <li>
                    <button class="addPriority" type="button">
                        <i class="claim-button-add"></i>
                        <c:choose>
                            <c:when test="${empty priorityForm.id}">
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
    </footer>
</fieldset>
</form:form>
</div>

<c:if test="${not empty priorityForm.goodsServices}">
    <script type="text/javascript">
        $('.togglePartial').attr('checked', 'checked');
    </script>
</c:if>
<c:if test="${empty priorityForm.goodsServices}">
    <script type="text/javascript">
        $(".claimSections #partialInfo").hide();
    </script>
</c:if>
