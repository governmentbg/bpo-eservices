<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<sec:authorize access="hasRole('Import_Divisional')" var="security_import_divisional" />
<sec:authorize access="hasRole('RegisteredDesign_Search')" var="security_registeredDesign_search" />

<div class="addbox" id="divisionalApplicationTabForm">
	<form:form id="formDivisionalApplication1" cssClass="fileUploadForm formEF import" modelAttribute="divisionalApplicationForm">
		<c:set var="sectionId" value="divisionalApplicationSection" scope="request" />
		
		<form:hidden id="hiddenFormId" path="id" />
		
	 	<header>
	        <h3><spring:message code="divisionalApplication.field.claim"/></h3>
	        <ul class="controls">
				<li>
	            	<a class="cancelDivisionalApplicationButton">
	                	<spring:message code="claim.action.cancel"/>
					</a>
				</li>
	
	            <li>
	            	<button type="button" class="addDivisionalApplication">
	            		<spring:message code="claim.action.save"/>
	            		<%--
	                	<c:choose>
	                    	<c:when test="${empty divisionalApplicationForm.id}">
	                        	<spring:message code="claim.action.add"/>
	                        </c:when>
	                        <c:otherwise>
	                        	<spring:message code="claim.action.save"/>
							</c:otherwise>
						</c:choose>
						--%>
					</button>
				</li>
			</ul>
	    </header>
	        
		<component:textarea path="infoDivisionalApplication" checkRender="true" labelTextCode="divisionalApplication.field.info" formTagClass="divApp-textarea" />


		<c:set var="service_divisional_import" value="${configurationServiceDelegator.isServiceEnabled('Import_Divisional')}"/>
		<c:choose>
			<c:when test="${(security_import_divisional || !configurationServiceDelegator.securityEnabled) && service_divisional_import}">
				<component:generic path="numberDivisionalApplication" checkRender="true">

					<component:import autocompleteUrl="autocompleteDesign.htm"
                                      permissionAutocomplete="${security_registeredDesign_search || !configurationServiceDelegator.securityEnabled}"
									  autocompleteServiceEnabled="Import_Divisional_Autocomplete"
									  minimumCharAutocomplete="minimum.characters.autocomplete.design"
									  id="inputIdDivisionalDesign"
									  importButtonId="buttonImportDivisionalDesign"
									  importButtonClass="import-button"
									  importButtonTextCode="previousRegisteredDesign.action.import"
									  component="general"
									  textCodeWhenEmpty="previousRegisteredDesign.importBox.whenEmpty"
                                      inputTextName="numberDivisionalApplication"/>

                    <br />

					<component:help-iconandtext code="previousRegisteredDesign.helpfulText"/>

					<div id="importDivisionalDesignErrorSection"></div>

				</component:generic>
			</c:when>
			<c:otherwise>
				<component:input path="numberDivisionalApplication" checkRender="true" id="divisionalNumber1"
								 labelTextCode="divisionalApplication.field.number" formTagClass="divApp-number"/>
			</c:otherwise>
		</c:choose>

		<component:input path="dateDivisionalApplication" checkRender="true" id="divisionalDate1"
					labelTextCode="divisionalApplication.field.dateOfFiling" formTagClass="standard-date-input" showDateHelp="true" />
		
		<div id="fileDocumentAttachmentDivisionalApplication" class="fileuploadInfo collectiveSelectors">
			<component:file pathFilename="fileDivisionalApplication" fileWrapperPath="fileDivisionalApplication"
		    	fileWrapper="${divisionalApplicationForm.fileDivisionalApplication}" />
		</div>
	</form:form>
	
    <component:generic path="locarnoEnabled" checkRender="true">
		<label><spring:message code="design.form.productIndication" /></label>
		<jsp:include page="../locarno/locarno_section_list.jsp"/>
		<jsp:include page="../locarno/locarno_buttons.jsp"/>
	</component:generic>
	
	<form:form id="formDivisionalApplication2" class="formEF" modelAttribute="divisionalApplicationForm">
		<tiles:insertDefinition name="designs_associate_to">
			<tiles:putAttribute name="containsDesignsLinkForm" value="${divisionalApplicationForm}"/>
			<tiles:putAttribute name="partialId" value="DivisionalApplication" />		
		</tiles:insertDefinition>
	</form:form>	
	
    <%--        	
	<form:form id="formDivisionalApplication" class="mainForm formEf" modelAttribute="flowBean.mainForm.divisionalApplication">
	
		<component:textarea path="infoDivisionalApplication" checkRender="true" labelTextCode="divisionalApplication.field.info" formTagClass="divApp-textarea" />
		  
		<component:input path="numberDivisionalApplication" checkRender="true" id="divisionalNumber1"
	    		labelTextCode="divisionalApplication.field.number" formTagClass="divApp-number"/>
		
		<component:input path="dateDivisionalApplication" checkRender="true" id="divisionalDate1"
				labelTextCode="divisionalApplication.field.dateOfFiling" formTagClass="standard-date-input" showDateHelp="true" />
	</form:form>
	
	<form:form id="formDivisionalApplication" class="fileUploadForm formEf" modelAttribute="flowBean">
		<div id="fileDocumentAttachmentDivisionalApplication" class="fileuploadInfo collectiveSelectors">
	    	<component:file pathFilename="fileDivisionalApplication" fileWrapperPath="mainForm.divisionalApplication.fileDivisionalApplication"
	                        fileWrapper="${flowBean.mainForm.divisionalApplication.fileDivisionalApplication}" />
		</div>
	</form:form>
	
	<component:generic path="locarnoEnabled" checkRender="true">
		<label><spring:message code="design.form.productIndication" /></label>
		<jsp:include page="../locarno/locarno_section_list.jsp"/>
		<jsp:include page="../locarno/locarno_buttons.jsp"/>
	</component:generic>
	
	<form:form id="formLinksDesignsToDivisionalApplication" class="mainForm formEf" modelAttribute="flowBean.mainForm.divisionalApplication">
		<tiles:insertDefinition name="designs_associate_to">
			<tiles:putAttribute name="containsDesignsLinkForm" value="${flowBean.mainForm.divisionalApplication}"/>
			<tiles:putAttribute name="partialId" value="DivisionalApplication" />		
		</tiles:insertDefinition>
	</form:form>
	 --%>
	<ul class="controls">
   		<li>
       		<a class="cancelDivisionalApplicationButton">
            	<spring:message code="claim.action.cancel"/>
           	</a>
   		</li>
       	<li>
           	<button type="button" class="addDivisionalApplication">
           		<spring:message code="claim.action.save"/>
           		<%--
               	<c:choose>
                   	<c:when test="${empty divisionalApplicationForm.id}">
                       	<spring:message code="claim.action.add"/>
                   	</c:when>
                   	<c:otherwise>
                       	<spring:message code="claim.action.save"/>
                   	</c:otherwise>
               	</c:choose>
               	--%>
           	</button>
       	</li>
	</ul>
	<br />
</div>

<jsp:include page="../locarno/locarno_section.jsp" />