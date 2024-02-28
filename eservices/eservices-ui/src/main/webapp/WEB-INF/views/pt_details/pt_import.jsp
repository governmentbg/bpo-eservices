<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div id="newPTFormDetails">
    <span class="hidden" id="previousPatent_viewMessage"><spring:message
        code="person.autocomplete.action.viewDetails"/></span>

    <c:choose>
        <c:when test="${fn:startsWith(flowModeId, 'ep-')}">
            <input type="hidden" id="previousPatent_viewUrl"
                   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.epopatent.details.url")}"/>
        </c:when>
        <c:otherwise>
            <input type="hidden" id="previousPatent_viewUrl"
                   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
        </c:otherwise>
    </c:choose>

    <component:import
            importButtonId="importPTButton"
            autocompleteUrl="autocompletePatent.htm"
            autocompleteServiceEnabled="Patent_Autocomplete"
            minimumCharAutocomplete="minimum.characters.autocomplete"
            permissionAutocomplete="true"
            id="patentId"
            labelTextCode="pt.import.field.application.number"
            importButtonTextCode="pt.import.field.import"
            importButtonClass="btn importPT" component="general"
            dataask="service.import.priority.extraImport"
            textCodeWhenEmpty="pt.import.field.whenEmpty"
            importTextfieldClass="claim-id-input"/>
    <input type="hidden" id="auto_patentId" value=""/>

    <span class="separator"><spring:message code="claim.title.or"/></span>

    <button type="button" class="import-button" id="searchForPatentBtn" data-ask="${configurationServiceDelegator.getPatentSearchExternalUrl(flowModeId)}">
        <spring:message code="patent.search.button.external"/>
    </button>


    <div class="tip"><spring:message code="patent.field.footerImport.${formUtil.getMainType()}"/></div>

</div>
