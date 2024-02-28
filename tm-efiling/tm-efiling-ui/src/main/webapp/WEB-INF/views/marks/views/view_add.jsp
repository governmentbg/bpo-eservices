<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<c:if test="${empty errorList}">

	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<!-- Change to spring message but add the same class !-->
					<span class="deleteModal"><spring:message code="mark.form.view.modal.delete"/></span>
					<span class="editModal"><spring:message code="mark.form.view.modal.edit"/></span>
					<span class="viewViewsModal"><spring:message code="mark.form.view.modal.view"/></span>
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<section class="modal-body modal-mark-view">
				<div id="dropzone">
					<div class="dz-message">
						<spring:message code="mark.form.view.modal.dragdropfiles"/> <br>
						<small><spring:message code="mark.form.view.modal.or"/></small> <br>
						<small><spring:message code="mark.form.view.modal.browsefromcomputer"/></small>
					</div>
					<div id="markViewfileDocumentAttachment" class="fileuploadInfo collectiveSelectors hidden">
						<jsp:include page="view_dragdrop_list.jsp"/>
					</div>
					<div id="fallback" class="fallback" style="margin: 10px">
						<p class="alert alert-danger">
							<spring:message code="dragdrop.not.supported"/>
						</p>
					</div>
				</div>
				<div id="counter" class="viewModal"><spring:message code="mark.form.view.modal.updatedfiles"/><span class="result"></span></div>
				<div id="uploadFilesErrorContainer"></div>
				<div id="previewFiles" class="dropzone-previews"></div>
				<div id="tpl" class="hidden">
					<div class="dz-preview dz-file-preview">
						<div class="dz-error-server"></div>
						<div class="dz-error-message"  data-dz-errormessage></div>
						<div class="dz-details">
							<div class="change-image">
								<img class="edit-icon" src="resources/img/edit.svg">
							</div>
							<img data-dz-thumbnail />
							<div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress></span></div>
							<div class="dz-filename"><span data-dz-name></span></div>
							<div class="dz-type"></div>
							<div class="dz-size" data-dz-size></div>
						</div>
						<div class="form-container"></div>
					</div>
				</div>
				<div id="tpl-edit" class="hidden">
					<div class="dz-preview dz-image-preview dz-success">
						<div class="select-input"><input type="checkbox"></div>
						<div class="dz-details">
							<div class="change-image">
								<img class="edit-icon" src="resources/img/edit.svg">
							</div>
							<div class="dz-filename"><span></span></div>
							<div class="dz-type"></div>
							<div class="dz-size"></div>
						</div>
						<div class="form-container"></div>
					</div>
				</div>
				<div id="fileUploadTemplate" class="hide">
					<jsp:include page="view_add_form.jsp"/>
				</div>
			</section>
			<footer>
				<ul>
					<li><a data-dismiss="modal"><spring:message code="mark.form.view.modal.button.cancel" /></a></li>
					<li>
						<button id="saveMarkViewsBtn" class="viewModal editModal"><spring:message code="mark.form.view.modal.button.save" /></button>
						<button id="deleteMarkViewsBtn" class="deleteModal"><spring:message code="mark.form.view.modal.button.delete" /></button>
					</li>
				</ul>
			</footer>
		</div>
	</div>
	<div id="uploadDiv" class="hidden"></div>
	<input type="hidden" id="maximumEntities" value="${configurationServiceDelegator.getValue('markView.add.maxNumber', 'general')}"/>
	<input hidden="hidden" id="numUploadEntities" value="${empty flowBean.markViews ? 0: flowBean.markViews.size()}" />
	<span id="errorRepeatedImage" class="hidden">
		<spring:message code="fileupload.error.file.repeat" />
	</span>
</c:if>


<c:forEach items="${errorList}" var="error">
	<div class="flMessageError" data-file="${error.field}">
		<spring:message code="${error.code}" var="errorMsg"/>
		<c:choose>
			<c:when test="${empty error.code or errorMsg eq error.code}">
				<c:out value="${error.displayMessage}"/>
			</c:when>
			<c:otherwise>
				<c:out value="${errorMsg}"/>
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>
