<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div class="modal-dialog">
<div class="modal-content">
<header>
	<h1>
		<spring:message code="mark.form.view.title" />
	</h1>
	<a class="close-icon" data-dismiss="modal"></a>
</header>

<section class="modal-body modal-mark-view">

 	<form:form id="addNewMarkViewForm" cssClass="formEF form-mark-view" modelAttribute="markViewForm">
		<c:set var="storedFile" value="${markViewForm.view.storedFiles[0]}" />
		
		<div>
			<label><spring:message code="mark.form.view.modal.field.view" /></label>
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
		
		<component:generic path="title" checkRender="true">
    		<label for="viewTitle"><spring:message code="mark.form.view.modal.field.title" /></label>
			<form:input path="title" id="viewTitle" disabled="true" />
    	</component:generic>

		<component:generic path="imageNumber" checkRender="true">
			<label for="viewImageNumber"><spring:message code="mark.form.view.modal.field.imageNumber" /></label>
			<form:input path="imageNumber" id="viewImageNumber" disabled="true" />
		</component:generic>

    </form:form>
    
</section>
    
<footer>
    <ul>
        <li><a data-dismiss="modal"><spring:message code="mark.form.view.modal.button.cancel" /></a></li>
    </ul>
</footer>
</div>
</div>