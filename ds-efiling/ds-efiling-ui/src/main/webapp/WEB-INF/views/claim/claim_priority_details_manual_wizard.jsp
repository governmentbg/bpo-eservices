<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="sectionId" value="priority" scope="request"/>
<form:form modelAttribute="priorityForm" id="priorityManualForm" cssClass="fileUploadForm formEF">

	<c:choose>
    	<c:when test="${empty priorityForm.id}">
            <c:set var="bottomCancelButtonClass" value="cancelManualButton"/>
		</c:when>
        <c:otherwise>
            <c:set var="bottomCancelButtonClass" value="cancelPriorityButton"/>
        </c:otherwise>
	</c:choose>

	<component:input path="date" checkRender="true" id="wDate" labelTextCode="claim.priority.field.onWhatDate"
                formTagClass="filing-date-input" showDateHelp="true" />
    <div style="display: none;">
        <component:input  path="dateStringFormat" id="dateStringFormat"
                          formTagClass="filing-text-input" showDateHelp="false" />
    </div>
	<component:input path="number" checkRender="true" id="fNumber" labelTextCode="claim.priority.field.filingNumber"
                formTagClass="filing-number-input"/>
	<component:input path="applicantName" checkRender="true" id="applicantName" labelTextCode="claim.priority.field.applicantName" 
				formTagClass="filing-text-input" />

	<div id="fileWrapperCopy" class="fileuploadInfo collectiveSelectors">
    	<component:file labelCode="claim.priority.firstFiling.copy"
                    	pathFilename="filePriorityCopy" fileWrapperPath="fileWrapperCopy"
                    	fileWrapper="${priorityForm.fileWrapperCopy}"/>
	</div>

	<div id="fileWrapperTranslation" class="fileuploadInfo collectiveSelectors">
    	<component:file labelCode="claim.priority.firstFiling.translation"
        	            pathFilename="filePriorityTranslation"
            	        fileWrapperPath="fileWrapperTranslation"
                	    fileWrapper="${priorityForm.fileWrapperTranslation}"/>
	</div>

	<div id="filePriorityCertificate" class="fileuploadInfo collectiveSelectors">
    	<component:file labelCode="claim.priority.certificate"
        	            pathFilename="filePriorityCertificate"
            	        fileWrapperPath="filePriorityCertificate"
                	    fileWrapper="${priorityForm.filePriorityCertificate}"/>
	</div>

	<tiles:insertDefinition name="designs_associate_to">
		<tiles:putAttribute name="containsDesignsLinkForm" value="${priorityForm}"/>
		<tiles:putAttribute name="partialId" value="Priority" />		
	</tiles:insertDefinition>

	<br />
	
	<ul class="controls">
    	<li>
        	<a class="${bottomCancelButtonClass}">
	            <spring:message code="claim.action.cancel"/>
            </a>
    	</li>
       	<li>
           	<button type="button" class="addPriorityWizard">
           		<spring:message code="claim.action.save"/>
           	</button>
       	</li>
	</ul>
	
	<br />
	
</form:form>
        
      
	
