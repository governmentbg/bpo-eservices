<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<div id="designViews">
	<c:if test="${not empty flowBean.designViews}">
		<c:set var="sectionId" value="designViews" scope="request" />
		<component:generic checkRender="true" path="showViewSequenceInTable">
			<c:set var="showViewSequenceInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="view.thumbnail">
			<c:set var="thereIsThumbnail" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewInTable">
			<c:set var="showViewInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewNameInTable">
			<c:set var="showViewNameInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewFormatInTable">
			<c:set var="showViewFormatInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showViewSizeInTable">
			<c:set var="showViewSizeInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showDescriptionInTable">
			<c:set var="showDescriptionInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showPublishInColourInTable">
			<c:set var="showPublishInColourInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showPublishInBlackWhiteInTable">
			<c:set var="showPublishInBlackWhiteInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showPublicationSizeInTable">
			<c:set var="showPublicationSizeInTable" value="true" />
		</component:generic>
		<component:generic checkRender="true" path="showTypeInTable">
			<c:set var="showTypeInTable" value="true" />
		</component:generic>
		<div class="design-views">
			<table id="designViewsTable" >
				<thead>
				<tr>
					<c:if test="${showViewSequenceInTable}">
						<th><spring:message code="design.views.table.header.number" /></th>
					</c:if>
					<c:if test="${showViewInTable}">
						<th><spring:message code="design.views.table.header.view" /></th>
					</c:if>
					<c:if test="${showViewNameInTable}">
						<th><spring:message code="design.views.table.header.name" /></th>
					</c:if>
					<c:if test="${showViewFormatInTable}">
						<th><spring:message code="design.views.table.header.format" /></th>
					</c:if>
					<c:if test="${showViewSizeInTable}">
						<th><spring:message code="design.views.table.header.size" /></th>
					</c:if>
					<c:if test="${showDescriptionInTable}">
						<th><spring:message code="design.views.table.header.description" /></th>
					</c:if>
					<c:if test="${showPublishInColourInTable}">
						<th><spring:message code="design.views.table.header.colour" /></th>
					</c:if>
					<c:if test="${showPublishInBlackWhiteInTable}">
						<th><spring:message code="design.views.table.header.blackAndWhite" /></th>
					</c:if>
					<c:if test="${showPublicationSizeInTable}">
						<th><spring:message code="design.views.table.header.pubSize" /></th>
					</c:if>
					<c:if test="${showTypeInTable}">
						<th><spring:message code="design.views.table.header.type" /></th>
					</c:if>
					<th><spring:message code="design.views.table.header.options" /></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="designViewForm" items="${flowBean.designViews}" varStatus="statusDesignViews">
					<c:set var="storedFile" value="${designViewForm.view.storedFiles.size() > 0 ? designViewForm.view.storedFiles[0] : null}" />
					<tr>
						<c:if test="${showViewSequenceInTable}">
							<td>
								<span>${designViewForm.sequence}</span>
							</td>
						</c:if>
						<c:if test="${showViewInTable}">
							<td>
								<div class="view-image">
									<c:if test="${storedFile ne null}">
										<c:choose>
											<c:when test="${storedFile.thumbnail ne null && storedFile.thumbnail.documentId ne null && not empty storedFile.thumbnail.documentId}">
												<img class="thumb" src="getDocument.htm?documentId=<c:out value="${storedFile.thumbnail.documentId}" escapeXml="true" />" id="viewImage${statusDesignViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
											</c:when>
											<c:when test="${not empty storedFile.documentId}">
												<img class="thumb" src="getDocument.htm?documentId=<c:out value="${storedFile.documentId}" escapeXml="true" />" id="viewImage${statusDesignViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
											</c:when>
											<c:otherwise>
												<img class="thumb" src="resources/img/thumbnailNotAvailable.png" id="viewImage${statusDesignViews.count}" data-file-documentId="<c:out value="${storedFile.documentId}" escapeXml="true" />" data-file-name="<c:out value="${storedFile.originalFilename}" escapeXml="true" />">
											</c:otherwise>
										</c:choose>
									</c:if>
								</div>
							</td>
						</c:if>
						<c:if test="${showViewNameInTable}">
							<td>
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
							</td>
						</c:if>
						<c:if test="${showViewFormatInTable}">
							<td>
								<span>${storedFile.contentType}</span>
							</td>
						</c:if>
						<c:if test="${showViewSizeInTable}">
							<td>
								<spring:eval var="storecanonicalFileSize" expression="storedFile.canonicalFileSize"/>
								<span>${storecanonicalFileSize}</span>
							</td>
						</c:if>
						<c:if test="${showDescriptionInTable}">
							<td>

								<spring:eval var="storeDescription" expression="designViewForm.description"/>
								<c:choose>
									<c:when test="${storeDescription.length()>10}">
										<span>${storeDescription.substring(0,10)}...</span>
									</c:when>
									<c:otherwise>
										<span>${storeDescription}</span>
									</c:otherwise>
								</c:choose>
							</td>
						</c:if>
						<c:if test="${showPublishInColourInTable}">
							<td>
								<c:choose>
									<c:when test="${designViewForm.publishInColour}">
										<spring:message code="design.views.table.colour.yes" />
									</c:when>
									<c:otherwise>
										<spring:message code="design.views.table.colour.no" />
									</c:otherwise>
								</c:choose>
							</td>
						</c:if>
						<c:if test="${showPublishInBlackWhiteInTable}">
							<td>
								<c:choose>
									<c:when test="${designViewForm.publishInBlackWhite}">
										<spring:message code="design.views.table.blackAndWhite.yes" />
									</c:when>
									<c:otherwise>
										<spring:message code="design.views.table.blackAndWhite.no" />
									</c:otherwise>
								</c:choose>
							</td>
						</c:if>
						<c:if test="${showPublicationSizeInTable}">
							<td>
								<span><c:out value="${designViewForm.publicationSize}" escapeXml="true" /></span>
							</td>
						</c:if>
						<c:if test="${showTypeInTable}">
							<td>
								<spring:message code="${functions:getLabelDesignViewType(designViewForm, configurationServiceDelegator['designViewTypes'])}" />
							</td>
						</c:if>
						<td>
							<c:choose>
								<c:when test="${empty param.viewMode or not param.viewMode}">
									<a class="edit-icon" id="editDesignViewBtn${statusDesignViews.count}" data-sequence="${designViewForm.sequence}"></a>
									<a class="remove-icon" id="removeDesignViewBtn${statusDesignViews.count}" data-sequence="${designViewForm.sequence}"></a>
								</c:when>
								<c:otherwise>
									<a class="view-icon" id="viewDesignViewBtn${statusDesignViews.count}" data-sequence="${designViewForm.sequence}"></a>
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
				<li><component:helpMessage textCode="design.form.views.help" useFlowModeId="false"/></li>
			</ul>
		</div>
	</c:if>
</div>
<jsp:include page="view_image.jsp" />	