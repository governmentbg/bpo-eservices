<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>

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
        
    	<div id="newFormDetails">
    		<div id="divRegistrationNo">
    			<label for="trademarkId"> <spring:message code="tm.import.field.referenceNo"/>
    			</label>
    		</div>
    		
            <component:import
            		importButtonId="importButton"
                    autocompleteUrl="autocompleteEarlierTrademark.htm?previousCTM=false"
                    autocompleteServiceEnabled="Priority_Autocomplete"
                    minimumCharAutocomplete="minimum.characters.autocomplete"
                    permissionAutocomplete="true"
                    id="trademarkIdEarlier"
                    importButtonTextCode="tm.import.field.import"
                    importButtonClass="btn importTMEarlier" component="general"
                    dataask="service.import.priority.extraImport"
                    textCodeWhenEmpty="tm.import.field.whenEmptyOpposition"
                    importTextfieldClass="claim-id-input"/>
            <input type="hidden" id="auto_trademarkId" value=""/>

            <tags:render flowModeId="${flowModeId}" sectionId="sec_${earlierEntitleMent}" field="relativeGrounds.earlierEntitlementRightInf.noManualTM" checkRender="true" var="noManualTM"/>

            <c:if test="${noManualTM == false }">
                <span class="separator"><spring:message code="claim.title.or"/></span>

                <button type="button" id="createEarlierManualDetails" class="create-manually-button">
                    <i class="create-icon"></i>
                    <spring:message code="tm.details.button.addManually"/>
                </button>
            </c:if>
            
            <div class="tip"><spring:message code="claim.priority.field.footerImport.earlierTM"/></div>
            
    	</div>
