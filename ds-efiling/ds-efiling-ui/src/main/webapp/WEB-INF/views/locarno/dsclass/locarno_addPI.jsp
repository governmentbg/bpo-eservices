<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component/dsclass" prefix="component" %>

<sec:authorize access="hasRole('Locarno_Search')" var="security_locarno_search" />

<div class="classes" id="locarno">
    <c:set var="maxNumber" value="design.locarno.add.maxNumber"/>
    <input type="hidden" id="maxLocarnoItems" value="<spring:message code="${maxNumber}"/>">
            <component:import importButtonId="locarnoImportButton"
                             id="locarnoImportTextfield"
                             component="locarno"
                             autocompleteUrl="autocompleteProductIndication.htm?language=${flowBean.firstLang}"
                             autocompleteServiceEnabled="Locarno_Autocomplete"
                             permissionAutocomplete="${security_locarno_search || !configurationServiceDelegator.securityEnabled}"
                             minimumCharAutocomplete="service.locarno.autocomplete.minchars"
                             importButtonClass="btn"
                             textCodeWhenEmpty="design.form.classes.importBox.whenEmpty"
                             importButtonTextCode="design.form.classes.action.import"/>
        <input type="hidden" id="locarnoImportId" value=""/>
            <span class="separator"><spring:message code="person.form.separator.or"/></span>
            <a class="oneformLocarnoBrowser" href="#locarnoModalBrowser" data-toggle="modal" ><spring:message code="design.form.classes.action.browse"/></a>
                <span class="separator"><spring:message code="person.form.separator.or"/></span>
            <a class="oneformLocarnoEnterMyTerms" href="#locarnoModalEnterMyComplexTerms" data-toggle="modal" ><spring:message code="design.form.classes.action.enterMyTerms"/></a>
    <div class="clearfix"></div>
    <div class="locarnoValidationError">
        <p class="flMessageError locarnoMaxTermsImportError" style="display:none"><span><spring:message code="design.form.classes.action.termsExceed" arguments="${configurationServiceDelegator.getValue('design.locarno.add.maxNumber', 'design')}"/></span></p>
        <p class="flMessageError locarnoClassNotAllowed" style="display:none"><span><spring:message code="design.form.classes.action.classNotAllowed"/></span></p>
        <p class="flMessageError locarnoImportNotFilled" style="display:none"><span><spring:message code="design.form.classes.action.locarnoNotFilled"/></span></p>
        <div class="businessRulesError"></div>
        <div id="indicationProducts"/>
    </div>
	<div class="tip">
			<i class="disclaimer-icon"></i>
	        <div data-tooltip="help" class="hidden">
				<component:helpMessage textCode="classification.dsclass.help"
				useFlowModeId="true" />
			</div>
			<a href="http://euipo.europa.eu/designclass/" target="_blank"><spring:message code="design.form.classes.action.import.tip.designClass"/></a>
    </div>
    <div id="productIndicationTableWrapper">
        <jsp:include page="/WEB-INF/views/locarno/dsclass/locarno_Table.jsp"/>
    </div>
</div>
