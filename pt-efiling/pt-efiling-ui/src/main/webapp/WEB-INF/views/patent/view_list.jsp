<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<div id="patentViews">

	<a class="btn add-button" href="#patentViewSection" id="addViewPatentBtn">
		<i class="add-icon"></i>
		<spring:message code="patent.form.views.button.add"/>
	</a>

	<c:if test="${not empty flowBean.patent.patentViews}">

		<c:set var="sectionId" value="patent_view" scope="request" />

		<c:set var="thereIsThumbnail" value="true" />

		<div class="patent-views">
			<table id="patentViewsTable" >
				<thead>
				<tr>
					<th><spring:message code="patent.views.table.header.view" /></th>
					<th><spring:message code="patent.views.table.header.options" /></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="patentViewForm" items="${flowBean.patent.patentViews}" varStatus="statusPatentViews">
					<c:set var="storedFile" value="${patentViewForm.view.storedFiles.size() > 0 ? patentViewForm.view.storedFiles[0] : null}" />
					<tr>
						<td>
							<div class="view-image">
								<c:if test="${storedFile ne null}">
									<c:choose>
										<c:when test="${thereIsThumbnail and storedFile.thumbnail ne null}">
											<c:if test="${storedFile.thumbnail.documentId ne null && not empty storedFile.thumbnail.documentId}">
												<img class="thumb" src="getDocument.htm?documentId=${storedFile.thumbnail.documentId}" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
											</c:if>
											<c:if test="${storedFile.thumbnail.documentId eq null || empty storedFile.thumbnail.documentId}">
												<img class="thumb" src="resources/img/thumbnailNotAvailable.png" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
											</c:if>
										</c:when>
										<c:otherwise>
											<img class="thumb" src="getDocument.htm?documentId=${storedFile.documentId}" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>

						<td>
							<a class="edit-icon" id="editPatentViewBtn${statusPatentViews.count}" data-val="${patentViewForm.id}"></a>
							<a class="remove-icon" id="removePatentViewBtn${statusPatentViews.count}" data-val="${patentViewForm.id}"></a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

		<br />
	</c:if>

</div>

<jsp:include page="view_image.jsp" />

<input type="hidden" id="maxPatentViews"
	   value="${configurationServiceDelegator.getValue('patentView.add.maxNumber', 'general')}">

<script type="text/javascript">
	checkMaxPatentViews();
</script>