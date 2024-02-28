<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<div id="newPTFormDetails">
    <div>
        <label><spring:message code="pt.form.type.select"/><span class="requiredTag">*</span></label>
        <select id="ptType">
            <option value=""><spring:message code="pt.form.action.select"/></option>
            <option value="national"><spring:message code="pt.type.national"/></option>
            <option value="epo"><spring:message code="pt.type.epo"/></option>
        </select>
    </div>

    <span class="hidden" id="previousPatent_viewMessage"><spring:message
            code="person.autocomplete.action.viewDetails"/></span>
    <spring:eval scope="page" var="office" expression="@propertyConfigurer.getProperty('sp.office')"/>
    <div id="patentNationalImport" style="display:none;">
        <input type="hidden" id="previousPatentNational_viewUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
        <component:import
                importButtonId="importPTNationalButton"
                autocompleteUrl="autocompletePatent.htm?office=${office}&flowModeId=pt-"
                autocompleteServiceEnabled="Patent_Autocomplete"
                minimumCharAutocomplete="minimum.characters.autocomplete"
                permissionAutocomplete="true"
                id="patentIdNational"
                labelTextCode="pt.import.field.application.number"
                importButtonTextCode="pt.import.field.import"
                importButtonClass="btn importPTNational" component="general"
                dataask="service.import.priority.extraImport"
                textCodeWhenEmpty="pt.import.field.whenEmpty"
                importTextfieldClass="claim-id-input importPatentInput"/>
        <input type="hidden" id="auto_patentId_national" value=""/>

        <span class="separator"><spring:message code="claim.title.or"/></span>

        <button type="button" class="import-button" id="searchForNationalPatentBtn" data-ask="${configurationServiceDelegator.getPatentSearchExternalUrl('pt-')}">
            <spring:message code="patent.search.button.external"/>
        </button>
        <div class="tip"><spring:message code="patent.field.footerImport.national"/></div>
    </div>
    <div id="patentEPOImport" style="display:none;">
        <input type="hidden" id="previousPatentEPO_viewUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.epopatent.details.url")}"/>
        <component:import
                importButtonId="importPTEPOButton"
                autocompleteUrl="autocompletePatent.htm?office=${office}&flowModeId=ep-"
                autocompleteServiceEnabled="Patent_Autocomplete"
                minimumCharAutocomplete="minimum.characters.autocomplete"
                permissionAutocomplete="true"
                id="patentIdEPO"
                labelTextCode="pt.import.field.application.number"
                importButtonTextCode="pt.import.field.import"
                importButtonClass="btn importPTEPO" component="general"
                dataask="service.import.priority.extraImport"
                textCodeWhenEmpty="pt.import.field.whenEmpty"
                importTextfieldClass="claim-id-input importPatentInput"/>
        <input type="hidden" id="auto_patentId_epo" value=""/>

        <span class="separator"><spring:message code="claim.title.or"/></span>

        <button type="button" class="import-button" id="searchForEPOPatentBtn" data-ask="${configurationServiceDelegator.getPatentSearchExternalUrl('ep-')}">
            <spring:message code="patent.search.button.external"/>
        </button>
        <div class="tip"><spring:message code="patent.field.footerImport.epo"/></div>
    </div>
</div>
