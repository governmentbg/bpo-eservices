<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<spring:eval var="office_code" expression="@propertyConfigurer.getProperty('sp.office')"/>
<spring:eval var="office_code_wo" expression="@propertyConfigurer.getProperty('tmefiling.foreign.office')"/>
<spring:eval var="office_code_em" expression="@propertyConfigurer.getProperty('tmefiling.european.office')"/>
<spring:eval var="gs_Languages" expression="@propertyConfigurer.getProperty('gs.languages')"/>
<input type="hidden" id="previousCtmConfig_officeCodesp" value="${office_code}"/>
<input type="hidden" id="previousCtmConfig_officeCodewo" value="${office_code_wo}"/>
<input type="hidden" id="previousCtmConfig_officeCodeem" value="${office_code_em}"/>
<input type="hidden" id="previousCtmConfig_officeCode" value=""/>
<input type="hidden" id="gs_Languages" value="${gs_Languages}"/>
<c:set var="service_previousCtm_autocomplete_detailsUrlEnabled"
       value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.details.urlEnabled')}"/>
<input type="hidden" id="previousCtmConfig_searchUrlEnabled"
       value="${service_previousCtm_autocomplete_detailsUrlEnabled}"/>
<c:if test="${service_previousCtm_autocomplete_detailsUrlEnabled}">
    <input type="hidden" id="previousCtmConfig_searchUrl"
           value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.details.url")}"/>
    <span class="hidden" id="previousCtmConfig_viewMessage"><spring:message
            code="person.autocomplete.action.viewDetails"/></span>
</c:if>


<div id="divCategoryInsert">

    <component:generic path="categoryTradeMark" checkRender="true">
        <c:set var="errors"><form:errors path="categoryTradeMark"></form:errors></c:set>
        <label for="categoryTradeMark-select"><spring:message code="earlierRight.details.field.categoryTradeMark"></spring:message>
            <span class="requiredTag">*</span>
        </label>

        <select id="categoryTradeMark-select">
            <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
            <c:set var="categorySection" value="${flowModeId }_tm_section"/>
            <c:forEach items="${configurationServiceDelegator['categoriesTradeMark']}" var="categoryTM">
                <c:if test="${(not empty categoryTM.code) && (categoryTM.applicationType eq categorySection) && categoryTM.enabled eq true}">
                    <option value="${categoryTM.code}"><spring:message code="${categoryTM.value}"/> </option>
                </c:if>
            </c:forEach>
        </select>
        <c:if test="${!empty errors}">
            <c:forEach items="${errors}" var="message">
                <p class="flMessageError" id="categoryTradeMark-selectErrorMessageServer">${message}</p>
            </c:forEach>
        </c:if>
    </component:generic>

</div>

<div id="newFormDetails">
    <component:import
            importButtonId="importButton"
            autocompleteUrl="autocompleteTrademark.htm?previousCTM=false"
            autocompleteServiceEnabled="Priority_Autocomplete"
            minimumCharAutocomplete="minimum.characters.autocomplete"
            permissionAutocomplete="true"
            id="trademarkId"
            labelTextCode="tm.import.field.registrationNo"
            importButtonTextCode="tm.import.field.import"
            importButtonClass="btn importTM" component="general"
            dataask="service.import.priority.extraImport"
            textCodeWhenEmpty="tm.import.field.whenEmpty"
            importTextfieldClass="claim-id-input"/>
    <input type="hidden" id="auto_trademarkId" value=""/>

    <span class="separator"><spring:message code="claim.title.or"/></span>

    <c:if test="${configurationServiceDelegator.isServiceEnabled('Priority_Manual') eq true}">
        <button type="button" id="createManualDetails" class="create-manually-button">
            <i class="create-icon"></i>
            <spring:message code="tm.details.button.addManually"/>
        </button>
    </c:if>
    <button type="button" class="import-button" id="searchForTrademarkBtn" data-ask="${configurationServiceDelegator.getValueFromGeneralComponent("service.previousCtm.search.url")}">
        <spring:message code="tm.details.button.tmView"/>
    </button>


    <div class="tip"><spring:message code="claim.priority.field.footerImport.${flowModeId }"/></div>

</div>
