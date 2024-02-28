<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div class="modal-dialog">
	<div class="modal-content">
		<header>
			<h1>
				<spring:message code="patent.form.view.title" />
			</h1>
			<a class="close-icon" data-dismiss="modal"></a>
		</header>

		<section class="modal-body modal-patent-view">

			<form:form id="addNewPatentViewForm" cssClass="fileUploadForm formEF form-patent-view" modelAttribute="patentViewForm">
				<form:errors path="view" cssClass="flMessageError"/>
				<c:set var="sectionId" value="patent_view" scope="request" />

				<form:hidden id="hiddenFormId" path="id" />

				<div id="patentViewfileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
					<component:file labelCode="patent.form.view.modal.field.view" pathFilename="view"
									fileWrapperPath="view" fileWrapper="${patentViewForm.view}" inputFileClass="views-upload-btn"
									inputFileLabelCode="patent.form.view.modal.field.view.add" showPreLabelInputFileButton="false"
									helpMessageKey="patent.form.views.validViewFormat"/>
				</div>
			</form:form>

		</section>

		<footer>
			<ul>
				<li><a data-dismiss="modal"><spring:message code="patent.form.view.modal.button.cancel" /></a></li>
				<li><button id="savePatentViewBtn"><spring:message code="patent.form.view.modal.button.save" /></button></li>
			</ul>
		</footer>
	</div>
</div>