<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>

<spring:eval var="office_code"
	expression="@propertyConfigurer.getProperty('sp.office')" />
<input type="hidden" id="previousCtmConfig_officeCode"
	value="${office_code}" />
<input type="hidden" id="previousDesign_viewUrl"
	   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.design.details.url")}"/>
<span class="hidden" id="previousDesign_viewMessage"><spring:message
		code="person.autocomplete.action.viewDetails"/></span>

<div id="newFormDetails">
	<component:import importButtonId="importButton"
		autocompleteUrl="autocompleteEarlierDesign.htm"
		autocompleteServiceEnabled="Design_Autocomplete"
		minimumCharAutocomplete="minimum.characters.autocomplete"
		permissionAutocomplete="true" id="designId"
		labelTextCode="ds.import.field.registrationNo"
		importButtonTextCode="ds.import.field.import"
		importButtonClass="btn importDSEarlier" component="general"
		dataask="service.import.priority.extraImport"
		textCodeWhenEmpty="ds.import.field.whenEmpty"
		importTextfieldClass="claim-id-input" />
	<input type="hidden" id="auto_designId" value="" /> <span
		class="separator"><spring:message code="claim.title.or" /></span>
	
	
	<button type="button" class="import-button" id="searchForDesignBtn"
		data-ask="${configurationServiceDelegator.getValueFromGeneralComponent("service.designview.search.url")}">
		<spring:message code="ds.details.button.dsView" />
	</button>


	<div class="tip">
		<spring:message code="design.field.footerImport" />
	</div>

</div>
