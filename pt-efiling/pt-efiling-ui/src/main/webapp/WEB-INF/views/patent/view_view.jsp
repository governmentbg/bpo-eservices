<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<header>
	<h1>
		<spring:message code="patent.form.view.title" />
	</h1>
	<a class="close-icon" data-dismiss="modal"></a>
</header>

<section class="modal-body modal-patent-view">

 	<form:form id="addNewPatentViewForm" cssClass="formEF form-patent-view" modelAttribute="patentViewForm">
 		<c:set var="sectionId" value="patent_view" scope="request" />
		<c:set var="storedFile" value="${patentViewForm.view.storedFiles[0]}" />
		
		<div>
			<label><spring:message code="patent.form.view.modal.field.view" /></label>
			<div>
				<img class="thumb" src="getDocument.htm?documentId=${storedFile.documentId}">
			</div>
			<div>
				<p>
					<spring:eval var="storeFileBaseName" expression="T(org.apache.commons.io.FilenameUtils).getBaseName(storedFile.filename)"/>
					<spring:eval var="storeFileExtension" expression="T(org.apache.commons.io.FilenameUtils).getExtension(storedFile.filename)"/>
					<c:choose>
						<c:when test="${storeFileBaseName.length()>15}">
							${storeFileBaseName.substring(0,15)}...${storeFileExtension}
						</c:when>
						<c:otherwise>
							${storedFile.filename}
						</c:otherwise>
					</c:choose>
				</p>
				<p>
					<spring:message code="fileDescription.type"/>: ${storedFile.contentType}<br>
				</p>
				<p>(${storedFile.canonicalFileSize})</p>
			</div>
		</div>
		
		<component:generic path="description" checkRender="true">   
    		<label for="viewDescription"><spring:message code="patent.form.view.modal.field.description" /></label>
			<form:textarea path="description" id="viewDescription" cssClass="patent-view-textarea" disabled="true" />
    	</component:generic>

    	<component:generic path="publishInColour" checkRender="true">
    		<label for="publishInColour">
    			<form:checkbox path="publishInColour" id="publishInColour" disabled="true" />
    			<span><spring:message code="patent.form.view.modal.field.publishInColour" /></span>
    		</label>
    	</component:generic>
    	
    	<component:generic path="publishInBlackWhite" checkRender="true">
    		<label for="publishInBlackWhite">
    			<form:checkbox path="publishInBlackWhite" id="publishInBlackWhite" disabled="true" />
    			<span><spring:message code="patent.form.view.modal.field.publishInBlackAndWhite" /></span>
    		</label>
    	</component:generic>
		    	
		<component:generic path="publicationSize" checkRender="true">
			<label for="publicationSize"><spring:message code="patent.form.view.modal.field.publicationSize" /></label>
			<form:select id="publicationSize" path="publicationSize" disabled="true">
				<c:forEach items="${configurationServiceDelegator['designViewPublicationSizes']}" var="item">
				 	<form:option value="${item.code}"><spring:message code="${item.value}" /></form:option>
				</c:forEach>
			</form:select>
        </component:generic>
		
		<component:generic path="type" checkRender="true">
			<label for="type"><spring:message code="patent.form.view.modal.field.type" /></label>
			<form:select id="type" path="type" disabled="true">
				<c:forEach items="${configurationServiceDelegator['designViewTypes']}" var="item">
				 	<form:option value="${item.code}"><spring:message code="${item.value}" /></form:option>
				</c:forEach>
			</form:select>
		</component:generic>
		
    </form:form>
    
</section>
    
<footer>
    <ul>
        <li><a data-dismiss="modal"><spring:message code="patent.form.view.modal.button.cancel" /></a></li>
    </ul>
</footer>