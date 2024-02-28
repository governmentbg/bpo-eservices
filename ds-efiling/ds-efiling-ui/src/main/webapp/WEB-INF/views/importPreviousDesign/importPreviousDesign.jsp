<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<sec:authorize access="hasRole('Import_Previous')" var="security_import_previous" />
<sec:authorize access="hasRole('RegisteredDesign_Search')" var="security_registeredDesign_search" />
<c:set var="service_previous_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Previous')}"/>
<c:if test="${(security_import_previous || !configurationServiceDelegator.securityEnabled) && service_previous_import}">

    <section class="import" id="importPreviousDesign">
    
    	<%--
    	Change property names if apply
        <c:set var="service_previousRegisteredDesign_autocomplete_detailsUrlEnabled"
               value="${configurationServiceDelegator.getValueFromGeneralComponent('service.design.details.urlEnabled')}"/>
               
        <input type="hidden" id="previousRegisteredDesign_searchUrlEnabled" value="${service_previousRegisteredDesign_autocomplete_detailsUrlEnabled}"/>

        <c:if test="${service_previousRegisteredDesign_autocomplete_detailsUrlEnabled}">
            <input type="hidden" id="previousRegisteredDesignConfig_searchUrl"
                   value="${configurationServiceDelegator.getValueFromGeneralComponent('service.design.details.url')}"/>
            <span class="hidden" id="previousRegisteredDesignConfig_viewMessage">
            	<spring:message code="person.autocomplete.action.viewDetails"/>
            </span>
        </c:if>
        --%>

        <header>
            <a href="#importPreviousDesign" class="anchorLink"></a>

            <h2>
            	<spring:message code="previousRegisteredDesign.title"/>
            </h2>
        </header>

        <form:form id="previousRegisteredDesignForm">
			<input type="hidden" id="previousDesign_viewUrl"
				   value="${configurationServiceDelegator.getValueFromGeneralComponent("service.design.details.url")}"/>
			<span class="hidden" id="previousDesign_viewMessage"><spring:message
					code="person.autocomplete.action.viewDetails"/></span>

            <c:set var="sectionId" value="importPreviousDesign" scope="request"/>
            <component:generic path="designApplicationId" checkRender="true">

                <component:import autocompleteUrl="autocompleteDesign.htm"
                                 permissionAutocomplete="${security_registeredDesign_search || !configurationServiceDelegator.securityEnabled}"
                                 autocompleteServiceEnabled="Import_Previous_Autocomplete"
                                 minimumCharAutocomplete="minimum.characters.autocomplete.design"
                                 id="inputIdRegisteredDesign"
                                 importButtonId="buttonImportRegisteredDesign"
                                 importButtonClass="import-button"
                                 importButtonTextCode="previousRegisteredDesign.action.import"
                                 component="general"
                                 textCodeWhenEmpty="previousRegisteredDesign.importBox.whenEmpty"/>
                <input type="hidden" id="inputAutocompleteIdRegisteredDesign" value=""/>

				<c:set var="registeredDesignSearchUrl"
					   value="${configurationServiceDelegator.getValueFromGeneralComponent('service.designview.search.url')}"/>
				<button type="button" class="import-button" id="searchForDesignBtn"
						data-ask="${registeredDesignSearchUrl}"><spring:message
						code="previousRegisteredDesign.action.search"/>
				</button>

				<br />
                
                <%--<span class="tip">--%>
                	<%--<spring:message code="previousRegisteredDesign.helpfulText"/>--%>
                <%--</span>--%>
                <component:help-iconandtext code="previousRegisteredDesign.helpfulText"/>
                 
                 <div id="importPreviousDesignErrorSection"></div>
            </component:generic>
            
            
    		<div id="confirmImportRegisteredDesignModal" class="modal fade messagePopup modal-standard">
    		<div class="modal-dialog">
			<div class="modal-content">
    			<header>
        			<h1>
			            <spring:message code="general.confirmModal.header.title"/>
        			</h1>
        			<a class="close-icon" data-dismiss="modal"></a>
    			</header>
    			<div class="modal-body">
        			<span id="confirmPlaceholder">
        				<spring:message code="general.messages.previousRegisteredDesign.importConfirmation"/>
        			</span>
					        			
        			<component:generic path="loadAllApplication" checkRender="true">
        				<div class="import-options">
							<ul>
							    <li>
							    	<label for="radioImportOnlyTheDesign">
							    		<input type="radio" name="loadAllApplication" value="false" id="radioImportOnlyTheDesign" checked="checked" />
							    		<spring:message code="previousRegisteredDesign.modal.form.option.design" />
							    	</label>
								</li>
							    <li>
							    	<label for="radioImportAll">
							    		<input type="radio" name="loadAllApplication" value="true" id="radioImportAll" />
        								<spring:message code="previousRegisteredDesign.modal.form.option.application" />
        							</label>
								</li>
							</ul>
	    				</div>
						<div id="loadAllApplicationSuggestedApps">

						</div>
	    			</component:generic>	
	    		</div>	
    			<footer>
        			<ul>
            			<li>
                			<button type="button" data-dismiss="modal">
                    			<spring:message code="general.confirmModal.footer.buttonCancel"/>
                			</button>
            			</li>
            			<li>
                			<button type="button" id="confirmImportRegisteredDesignModalOk" data-dismiss="modal">
                    			<spring:message code="general.confirmModal.footer.buttonOk"/>
                			</button>
            			</li>
        			</ul>
    			</footer>
			</div>
			</div>
			</div>
                
        </form:form>
        
    </section>
    
</c:if>
