<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
$(document).ready(function(){
	 checkBrowserVersionWizard();
});
</script>

<div class="flBox clear">
    <div class="flFormRow alignCenter"  style="display: none;" id="wizard_correct_browser_version">

	    <sec:authorize access="hasRole('Save_Load_Locally')" var="security_save_load_locally" />
		<c:set var="service_save_load" value="${configurationServiceDelegator.isServiceEnabled('Save_Load_Service')}"
		/><span class="flField w17m">
			<component:mobileSelect sectionId="languages" items="${configurationServiceDelegator['firstLanguages']}" labelTextCode="homepage.chooseLanguage" path="firstLang" itemLabel="value" itemValue="code" formTagClass="w15m" />
		</span>
		<ul class="horizontalList slim-m">
            <li><a class="darkButton" rel="next" href="javascript:$('#formEF').submit();"><spring:message code="homepage.startApplication"/></a>
            </li>
            <c:if test="${service_save_load && (security_save_load_locally || !configurationServiceDelegator.securityEnabled)}"
				><li>
            	<spring:message code="homepage.or"/> 
            	<a class="btn fileinput-button underline darkFont"><spring:message code="homepage.restoreSaved"/>
					<input type="file" name="loadApplicationXML" class="submittedAjax uploadLoadApplication" data-url="loadApplication.htm?fileWrapperPath=${fileWrapperPath}&_csrf=${_csrf.token}&flowKey=${empty param.execution?param.flowKey:param.execution}" />
				</a>
            </li></c:if
        ></ul>
	</div>
	<div class="flFormRow alignCenter" style="display: none;" id="wizard_message_error_browser">
	  <p style="margin-top: 15px">
            <b><spring:message code="error.message.wizard.browser.version"/> </b>
       </p>
</div>