<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div>
	<c:if test="${not empty flowBean.markViews}">

		<component:generic checkRender="true" path="showViewNameInTable">
			<c:set var="showViewNameInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewFormatInTable">
			<c:set var="showViewFormatInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewSizeInTable">
			<c:set var="showViewSizeInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showTitleInTable">
			<c:set var="showTitleInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showImageNumberInTable">
			<c:set var="showImageNumberInTable" value="true" />
		</component:generic>

		<div class="mark-views">
			<table id="markViewsTable">
				<thead>
				<tr>
					<th><spring:message code="mark.views.table.header.view" /></th>
					<c:if test="${showTitleInTable}">
						<th><spring:message code="mark.views.table.header.title" /></th>
					</c:if>
					<c:if test="${showImageNumberInTable}">
						<th><spring:message code="mark.views.table.header.imageNumber" /></th>
					</c:if>
					<c:if test="${showViewNameInTable}">
						<th><spring:message code="mark.views.table.header.name" /></th>
					</c:if>
					<c:if test="${showViewFormatInTable}">
						<th><spring:message code="mark.views.table.header.format" /></th>
					</c:if>
					<th><spring:message code="mark.views.table.header.options" /></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="markViewForm" items="${flowBean.markViews}" varStatus="statusMarkViews">
					<c:set var="storedFile" value="${markViewForm.view.storedFiles.size() > 0 ? markViewForm.view.storedFiles[0] : null}" />
					<tr>
						<td>
							<div class="view-image">
								<c:if test="${storedFile ne null}">
									<c:choose>
										<c:when test="${storedFile.thumbnail ne null && storedFile.thumbnail.documentId ne null && not empty storedFile.thumbnail.documentId}">
											<img class="thumb" src="getDocument.htm?documentId=<c:out value="${storedFile.thumbnail.documentId}" escapeXml="true" />" id="viewImage${statusMarkViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
										</c:when>
										<c:when test="${not empty storedFile.documentId}">
											<img class="thumb" src="getDocument.htm?documentId=<c:out value="${storedFile.documentId}" escapeXml="true" />" id="viewImage${statusMarkViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
										</c:when>
										<c:otherwise>
											<img class="thumb" src="resources/img/thumbnailNotAvailable.png" id="viewImage${statusMarkViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>

						<c:if test="${showTitleInTable}">
							<td>

								<spring:eval var="storeTitle" expression="markViewForm.title"/>
								<c:choose>
									<c:when test="${storeTitle.length()>10}">
										<span>${storeTitle.substring(0,10)}...</span>
									</c:when>
									<c:otherwise>
										<span>${storeTitle}</span>
									</c:otherwise>
								</c:choose>
							</td>
						</c:if>
						<c:if test="${showImageNumberInTable}">
							<td>
								<c:out value="${markViewForm.imageNumber}"/>
							</td>
						</c:if>

						<c:if test="${showViewNameInTable || showViewSizeInTable}">
							<td>
								<c:if test="${showViewNameInTable}">
									<spring:eval var="storeFileBaseName" expression="T(org.apache.commons.io.FilenameUtils).getBaseName(storedFile.originalFilename)"/>
									<spring:eval var="storeFileExtension" expression="T(org.apache.commons.io.FilenameUtils).getExtension(storedFile.originalFilename)"/>
									<c:choose>
										<c:when test="${storeFileBaseName.length()>10}">
											<span>${storeFileBaseName.substring(0,10)}...${storeFileExtension}</span>
										</c:when>
										<c:otherwise>
											<span>${storedFile.originalFilename}</span>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${showViewSizeInTable && not empty storedFile.canonicalFileSize}">
									<spring:eval var="storecanonicalFileSize" expression="storedFile.canonicalFileSize"/>
									<span>(${storecanonicalFileSize})</span>
								</c:if>
							</td>
						</c:if>
						<c:if test="${showViewFormatInTable}">
							<td>
								<span>${storedFile.contentType}</span>
							</td>
						</c:if>
						<td>
							<c:choose>
								<c:when test="${empty param.viewMode or not param.viewMode}">
									<a class="edit-icon" id="editMarkViewBtn${statusMarkViews.count}" data-sequence="${markViewForm.sequence}"></a>
									<a class="remove-icon" id="removeMarkViewBtn${statusMarkViews.count}" data-sequence="${markViewForm.sequence}"></a>
								</c:when>
								<c:otherwise>
									<a class="view-icon" id="viewMarkViewBtn${statusMarkViews.count}" data-sequence="${markViewForm.sequence}"></a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<br />
		<div class="genericHelp">
			<ul>
				<li><component:helpMessage textCode="mark.form.views.help.disclaimer" useFlowModeId="false"/></li>
			</ul>
		</div>
	</c:if>
</div>

<form:form modelAttribute="flowBean">
	<form:errors path="markViews" element="p" cssClass="flMessageError"/>
</form:form>

<jsp:include page="view_image.jsp" />	