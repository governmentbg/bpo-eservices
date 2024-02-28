<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- <div class="flBox noBG"> -->
<div class="addbox claimFields">
        <form:form modelAttribute="transformationForm" id="transformationForm" cssClass="fileUploadForm formEF">
            <c:set var="sectionId" value="transformation" scope="request"/>
            <c:set var="imported" value="${transformationForm.imported}" scope="request"/>
            <c:set var="service_transformation_manual" value="${configurationServiceDelegator.isServiceEnabled('Transformation_Manual')}" />
			<spring:eval var="wipo_office_code" expression="@propertyConfigurer.getProperty('tmefiling.foreign.office').trim()" />

			<header>
				<span class="number"><fmt:formatNumber
						value="${entityPosition}" minIntegerDigits="2" /></span>
				<h3>
					<spring:message code="claim.title.transformation" />
				</h3>
				<ul class="controls">
					<li><a class="cancelButton"><spring:message
								code="claim.action.cancel" /></a></li>
					<c:if test="${service_transformation_manual}">
						<li><button type="button" class="addTransformation">
								<i class="claim-button-add"></i>
								<c:choose>
									<c:when test="${empty transformationForm.id}">
										<spring:message code="claim.action.add" />
									</c:when>
									<c:otherwise>
										<spring:message code="claim.action.save" />
									</c:otherwise>
								</c:choose>
							</button></li>
					</c:if>
				</ul>
			</header>
            
            <fieldset>
                <form:hidden path="id"/>
                <form:hidden path="imported" id="importedTransformation"/>
				<form:hidden path="transformationCountryCode" name="transformationCountryCode" id="transformationCountryCode"/>

                <sec:authorize access="hasRole('Transformation_Import')" var="security_transformation_import" />
                <sec:authorize access="hasRole('Transformation_Search')" var="security_transformation_search" />

				<c:set var="service_transformation_import" value="${configurationServiceDelegator.isServiceEnabled('Transformation_Import')}" />
				
				<c:choose>
					<c:when test="${empty transformationForm.id}">
						<c:set var="newFormDetailsClass" value="" />
						<c:set var="manualDetailsClass" value="hide" />
						<c:set var="bottomCancelButtonClass" value="cancelManualButton" />
					</c:when>
					<c:otherwise>
						<c:set var="newFormDetailsClass" value="hide" />
						<c:set var="manualDetailsClass" value="" />
						<c:set var="bottomCancelButtonClass" value="cancelButton" />
					</c:otherwise>
				</c:choose>
				
                <div id="newFormDetails" class="${newFormDetailsClass}">
			        <c:if test="${service_transformation_import && (security_transformation_import || !configurationServiceDelegator.securityEnabled)}">
	                  	<c:set var="service_transformation_autocomplete_detailsUrlEnabled"
	           				value="${configurationServiceDelegator.getValueFromGeneralComponent('service.trademark.transformation.details.urlEnabled')}"/>
	                	<input type="hidden" id="transformationTradeMarkConfig_searchUrlEnabled"
	           				value="${service_transformation_autocomplete_detailsUrlEnabled}"/>
	           			<input type="hidden" id="transformationTradeMark_officeCode" value="${wipo_office_code}"/>
					    <c:if test="${service_transformation_autocomplete_detailsUrlEnabled}">
					        <input type="hidden" id="transformationTradeMarkConfig_searchUrl"
					               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.trademark.transformation.details.url")}"/>
					        <span class="hidden" id="transformationTradeMarkConfig_viewMessage"><spring:message code="person.autocomplete.action.viewDetails"/></span>        
					    </c:if>	
					    		 
                        <component:import autocompleteUrl="autocompleteTrademark.htm?previousCTM=false&office=${wipo_office_code}"
                                          autocompleteServiceEnabled="Transformation_Autocomplete"
										  permissionAutocomplete="${security_transformation_search || !configurationServiceDelegator.securityEnabled}"
                                          minimumCharAutocomplete="minimum.characters.autocomplete"
                                          id="lastTransformation"
                                          labelTextCode="claim.transformation.field.lastTransformation"
                                          importButtonTextCode="claim.action.import"
                                          importButtonClass="btn importTransformation"
					                      component="general"
                                          dataask="service.import.transformation.extraImport"
                                          textCodeWhenEmpty="claim.transformation.import.whenEmpty"
                                          importTextfieldClass="claim-id-input" />
                        <button id="" class="btn importTransformation" data-ask="true" type="button"><spring:message code="claim.action.import" /></button>
                        <input type="hidden" id="transformationTradeMark_importId" value=""/>
	                </c:if>
	                
	                <c:if test="${service_transformation_import && (security_transformation_import || !configurationServiceDelegator.securityEnabled) && service_transformation_manual}">
						<span class="separator"><spring:message code="claim.title.or" /></span>
					</c:if>
					
	                <c:if test="${service_transformation_manual}">
		                <button type="button" id="createManualDetails" class="create-manually-button">
							<i class="create-icon"></i>
							<spring:message code="claim.action.manually" />
						</button>
	                </c:if>
	                
	                <c:if test="${service_transformation_import && (security_transformation_import || !configurationServiceDelegator.securityEnabled)}">
						<div class="tip"><spring:message code="claim.transformation.field.footerImport"/></div>
					</c:if>
                </div>
                
                <hr>
                <div id="manualDetails" class="${manualDetailsClass}">
                	<component:input path="irNumber" checkRender="true" id="irNum"
   	                         labelTextCode="claim.transformation.field.irNumber" formTagClass=""/>
   	                <component:input path="irDate" checkRender="true" id="irDate" labelTextCode="claim.transformation.field.irDate"
                                     formTagClass="filing-date-input"/>
                    <component:input path="cancelationDate" checkRender="true" id="cancelDate"
                                     labelTextCode="claim.transformation.field.cancelDate" formTagClass="filing-date-input"/>
                    <component:input path="priosenioDate" checkRender="true" id="psDate"
                                     labelTextCode="claim.transformation.field.prioritySeniorityDate" formTagClass="filing-date-input"/>
                                     
                    <footer>
						<ul class="controls">
							<li><a class="${bottomCancelButtonClass}"><spring:message
										code="claim.action.cancel" /></a></li>
							<c:if test="${service_transformation_manual}">
								<li><button class="addTransformation" type="button">
										<i class="claim-button-add"></i>
										<c:choose>
											<c:when test="${empty transformationForm.id}">
												<spring:message code="claim.action.add" />
											</c:when>
											<c:otherwise>
												<spring:message code="claim.action.save" />
											</c:otherwise>
										</c:choose>
									</button></li>
							</c:if>
						</ul>
					</footer>	                
                </div>
            </fieldset>            
        </form:form>
</div>
