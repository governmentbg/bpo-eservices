<%@ tag language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<jsp:directive.attribute name="fileWrapper" required="true" type="eu.ohim.sp.common.ui.form.resources.FileWrapper" description="Path for the form:tag"/>
<jsp:directive.attribute name="fileWrapperPath" required="true" type="java.lang.String" description="the path where the fileWrapper class exists during validation"/>
<jsp:directive.attribute name="labelCode" required="false" type="java.lang.String" description="The label code that is used to get label for the field"/>
<jsp:directive.attribute name="labelClass" required="false" type="java.lang.String" description="Class for the label" />
<jsp:directive.attribute name="pathFilename" required="false" type="java.lang.String" description="The path that will be used on the file upload, used on the MultipartForm"/>
<jsp:directive.attribute name="helpMessageKey" required="false" type="java.lang.String" description="The help message key"/>
<jsp:directive.attribute name="helpUseFlowModeId" required="false" type="java.lang.String"
                         description="Indicates whether to append the flow mode id to the help message key"/>
<jsp:directive.attribute name="showPreLabelInputFileButton" required="false" type="java.lang.Boolean" description="Show the extra label before the input-file button"/>
<jsp:directive.attribute name="inputFileClass" required="false" type="java.lang.String" description="Extra classes for the span input file button"/>
<jsp:directive.attribute name="inputFileLabelCode" required="false" type="java.lang.String" description="Label code for the span input file button"/>

<c:set var="sectionId" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:sectionId}"/>

<c:if test="${empty labelClass}">
	<c:set var="labelClass" value=""/>
</c:if>

<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}" checkRender="true">

	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.description" checkRender="true" var="hasDescription" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.attachment" checkRender="true" var="hasToFollowOption" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.preview" checkRender="true" var="hasPreview" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.toFollowWarning" checkRender="true" var="hasToFollowWarning" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.docType" checkRender="true" var="needFiletypeCombo" />

	<tags:isFastTrack var="fieldIsFastTrack" flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}"/>

	<c:if test="${fieldIsFastTrack and hasToFollowOption and empty fileWrapper.storedFiles}">
		<c:set var="showRadioFastTrackHelp" value="${true}"/>
	</c:if>
	<c:if test="${fieldIsFastTrack and (!hasToFollowOption or not empty fileWrapper.storedFiles)}">
		<c:set var="showLabelFastTrackHelp" value="${true}"/>
	</c:if>

	<c:set var="errorsAttachment">
		<form:errors path="${fileWrapperPath}.attachment"></form:errors>
	</c:set>
		
	<c:set var="errorsDescription">
		<c:if test="${not empty multipartFileForm}">
			<form:errors path="multipartFileForm.fileWrapper.description"></form:errors>
		</c:if>
	</c:set>

	<c:set var="checkRequired">
		<tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${pathFilename}" />
	</c:set>
	
	<c:set var="checkRequiredDescription">
        <tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.description"/>
    </c:set>

	<c:if test="${empty errorsDescription}">
		<c:if test="${not empty fileWrapper.labelCode || not empty labelCode}">
			<label class="${labelClass}">
				<spring:message code="${not empty labelCode ? labelCode : fileWrapper.labelCode}" />
				<c:if test="${checkRequired}">
					<span class="requiredTag">*</span>
				</c:if>
				<c:if test="${showLabelFastTrackHelp}">
					<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>
					<a rel="file-help-tooltip" class="fasttrack-icon-after"></a>
					<div data-tooltip="help" class="hidden">
						<div class="fire-icon-before">
							<a class="close-popover"></a>
							<spring:message code="fasttrack.help.stayOnFastrack"/><br/>
							<a target="_blank" href="${fasttrackConditionsUrl}">
								<spring:message code="fasttrack.help.learnmore"/>
							</a>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty fileWrapper.showPreLabelInputFileButton?fileWrapper.showPreLabelInputFileButton == false:(not empty showPreLabelInputFileButton ? showPreLabelInputFileButton == false : false)}">
					<c:if test="${empty helpUseFlowModeId}">
						<c:set var="helpUseFlowModeId" value="false"/>
					</c:if>
					<c:if test="${not empty helpMessageKey}">
						<a rel="file-help-tooltip" class="attachment-disclaimer-icon"></a>
						<div data-tooltip="help" class="hidden">
							<a class="close-popover"></a>
							<component:helpMessage textCode="${helpMessageKey}" useFlowModeId="${helpUseFlowModeId}"/>
						</div>
					</c:if>
				</c:if>
			</label>
		</c:if>
			
		<span id="formUpload">
			<inputmock name="fileWrapperPath" value="${fileWrapperPath}" class="submittedAjax"></inputmock>
			<inputmock name="fileWrapper.labelCode" class="submittedAjax" value="${not empty labelCode?labelCode:fileWrapper.labelCode}"></inputmock>
			<inputmock name="fileWrapper.showPreLabelInputFileButton" class="submittedAjax" value="${not empty showPreLabelInputFileButton?showPreLabelInputFileButton:fileWrapper.showPreLabelInputFileButton}"></inputmock>
			<inputmock name="fileWrapper.inputFileClass" class="submittedAjax" value="${not empty inputFileClass?inputFileClass:fileWrapper.inputFileClass}"></inputmock>
			<inputmock name="fileWrapper.inputFileLabelCode" class="submittedAjax" value="${not empty inputFileLabelCode?inputFileLabelCode:fileWrapper.inputFileLabelCode}"></inputmock>
			<inputmock name="fileWrapper.pathFilename" class="submittedAjax" value="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}"></inputmock>
			<inputmock name="fileWrapper.attachment" class="submittedAjax" value="${fileWrapper.attachment}"></inputmock>
			<inputmock name="fileWrapper.sectionId" class="submittedAjax" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:sectionId}"></inputmock>
			<inputmock name="fileWrapper.maximumFiles" class="submittedAjax" value="${fileWrapper.maximumFiles}"></inputmock>
			<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].description" value="${storedFile.description}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"></inputmock>
				<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].docType" value="${storedFile.docType}"></inputmock>
				<c:if test="${storedFile.docType!=null}">
					<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].docType" value="${storedFile.docType}"/>
				</c:if>
				<c:if test="${storedFile.docType==null}">
					<inputmock class="submittedAjax" name="fileWrapper.storedFiles[${status.count-1}].docType" value=""/>
				</c:if>
			</c:forEach>
		</span>
			
		<div class="row noPaddingBottom">

			<c:choose>
		
				<c:when test="${empty fileWrapper.storedFiles}">
					
					<%-- EMPTY STORED FILES AND TO FOLLOW OPTION --%>
					<c:if test="${hasToFollowOption}"> <%--c:if test="${hasToFollowOption && selectBox}"--%>
						<ul class="file-upload-radio">
							<li>
								<label for="copy-upload-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachment" value="true" <c:if test="${not empty fileWrapper.attachment && fileWrapper.attachment}">checked</c:if> id="copy-upload-${fileWrapperPath}"><spring:message code="fileUpload.attachment.attached"/>
									<c:if test="${showRadioFastTrackHelp}">
										<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>
										<a rel="file-help-tooltip" class="fasttrack-icon-after"></a>
										<div data-tooltip="help" class="hidden">
											<div class="fire-icon-before">
												<a class="close-popover"></a>
												<spring:message code="fasttrack.help.stayOnFastrack"/><br/>
												<a target="_blank" href="${fasttrackConditionsUrl}">
													<spring:message code="fasttrack.help.learnmore"/>
												</a>
											</div>
										</div>
									</c:if>
								</label>
							</li>
						    <li>
								<label for="copy-tofollow-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachment" value="false" <c:if test="${empty fileWrapper.attachment || !fileWrapper.attachment}">checked</c:if> id="copy-tofollow-${fileWrapperPath}"><spring:message code="fileUpload.attachment.follow"/></label>
								<c:if test="${not empty hasToFollowWarning && hasToFollowWarning}">
									
										<input type="hidden" id="copy-tofollow-${fileWrapperPath}-warning" value="<spring:message code="${flowModeId}.${fileWrapperPath}.warning"/>"></input>
									
								</c:if>
							</li>
						</ul>
						
						
					</c:if>
					
				</c:when>

				<c:otherwise>
					<div class="span7 noPaddingBottom">
						<c:choose>
							
							<%-- WITH PREVIEW --%>
							<c:when test="${hasPreview}">
								<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
									<c:choose>
										<c:when test="${storedFile.contentType == 'video/mp4'}" >
											<div class="mark-image-holder">
												<br>
												<video width="320" height="240" controls="controls">
													<source src="getDocument.htm?documentId=${storedFile.documentId}" type="video/mp4" />
													Your browser does not support the video element.
												</video>
												<div class="mark-image-details">
													<p class="mark-file-delete">
														<a class="btn btn-default toBeRemovedFile">
															<i class="mark-file-delete-icon"></i>
															<span id="${storedFile.documentId}">
																<spring:message code="fileUpload.action.delete"/>
															</span>
														</a>
													</p>
												</div>
											</div>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
										</c:when>
										<c:when test="${storedFile.contentType == 'audio/mp3' || storedFile.contentType == 'audio/mpeg'}" >
											<div class="mark-image-holder">
												<br>
												<audio controls="controls">
														<%--
                                                      <source src="getSoundFile.htm?file=${storedFile.originalFilename}&flowKey=${empty param.execution?param.flowKey:param.execution}" type="audio/mpeg" />
                                                       --%>
													<source src="getDocument.htm?documentId=${storedFile.documentId}" type="audio/mpeg" />
													Your browser does not support the audio element.
												</audio>
												<div class="mark-image-details">
													<p class="mark-file-delete">
														<a class="btn btn-default toBeRemovedFile">
															<i class="mark-file-delete-icon"></i>
															<span id="${storedFile.documentId}">
																<spring:message code="fileUpload.action.delete"/>
															</span>
														</a>
													</p>
												</div>
											</div>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
										</c:when>
										<c:when test="${storedFile.contentType != 'audio/mp3' && storedFile.contentType != 'audio/mpeg' && storedFile.contentType != 'application/pdf'}">
											<div class="mark-image-holder">
												<br>
												<div class="mark-image">
													<c:choose>
														<c:when test="${not empty storedFile.documentId}">
															<c:set var="image_filename" value="${storedFile.originalFilename}"/>	
															<c:set var="image_url" value="getDocument.htm?documentId=${storedFile.documentId}"/>
														</c:when>
														<c:otherwise>
															<%-- imported image --%>
															<c:set var="image_filename" value="${storedFile.filename}"/>	
															<c:set var="image_url" value="${storedFile.originalFilename}"/>
														</c:otherwise>
													</c:choose>
													<img class="thumb" id="${image_filename}" src="${image_url}"/>
													<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
														<div data-href="${image_url}" class="mark-image-expand" data-gallery="gallery" title="${image_filename}"></div>
													</div>
												</div>
												
												
												<div class="mark-image-details">
                                                    <p class="mark-file-name"><i class="mark-file-ok-icon"></i>
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
                                                    <p class="mark-file-size">
                                                        <spring:message code="fileDescription.type"/>: ${storedFile.contentType}<br>
                                                    </p>
                                                    <p class="mark-file-size">(${storedFile.canonicalFileSize})</p>

                                                    <div class="mark-file-disclaimer"><i class="mark-file-disclaimer-icon"></i>
                                                         <component:helpMessage textCode="mark.file.help"
                                                            useFlowModeId="true"/>
                                                    </div>
													<p class="mark-file-delete">
														<a class="btn btn-default toBeRemovedFile">
															<i class="mark-file-delete-icon"></i>
															<span id="${storedFile.documentId}">
																<spring:message code="fileUpload.action.delete"/>
															</span>
														</a>
													</p>
												</div>
											</div>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
										</c:when>
										<c:when test="${storedFile.contentType == 'application/pdf'}">
											<div class="mark-image-holder">
												<br>
												<c:if test="${empty storedFile.documentId}">
													<div class="mark-image">
														<p><img class="thumb" src="${storedFile.originalFilename}"/>		    		   
														</p>
													</div>
												</c:if>
												<c:if test="${not empty storedFile.documentId}">
													<div class="mark-image">
														<div><img class="thumb" id="${storedFile.originalFilename}" src="resources/img/pdf.png"/> <c:out value="${storedFile.originalFilename}">?</c:out></div>
														<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
															<div data-href="getDocument.htm?documentId=${storedFile.documentId}" class="mark-image-expand" data-gallery="gallery"></div>
														</div>														
													</div>
												</c:if>												
												<div class="mark-image-details">
													<p class="mark-file-delete">
														<a class="btn btn-default toBeRemovedFile">
															<i class="mark-file-delete-icon"></i>
															<span id="${storedFile.documentId}">
																<spring:message code="fileUpload.action.delete"/>
															</span>
														</a>
													</p>
												</div>
											</div>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
										</c:when>										
										<c:otherwise>
											<div class="mark-image-holder">
												<br>
												<c:if test="${empty storedFile.documentId}">
													<div class="mark-image">
														<p><img class="thumb" src="${storedFile.originalFilename}"/>
														</p>
													</div>
												</c:if>
												<c:if test="${empty storedFile.documentId}">
													<div class="mark-image">
													<div><img class="thumb" id="${storedFile.originalFilename}" src="resources/img/multimedia.png"/> <c:out value="${storedFile.originalFilename}">?</c:out></div>
													<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
														<div data-href="getDocument.htm?documentId=${storedFile.documentId}" class="mark-image-expand" data-gallery="gallery"></div>
													</div>
												</div>
												</c:if>
												<div class="mark-image-details">
													<p class="mark-file-delete">
														<a class="btn btn-default toBeRemovedFile">
															<i class="mark-file-delete-icon"></i>
															<span id="${storedFile.documentId}">
																<spring:message code="fileUpload.action.delete"/>
															</span>
														</a>
													</p>
												</div>
											</div>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							
							<%-- OTHER CASES --%>
							<c:otherwise>
								<c:choose>
									
									<c:when test="${hasDescription}">
										<table class="genericTable tableFileupload">
											<thead>
												<tr>
													<th class="filename"><spring:message code="fileDescription.table.header.filename"/></th>
													<c:if test="${needFiletypeCombo}">
														<c:choose>
															<c:when test="${empty labelCode}">
																<th><spring:message code="fileDescription.type"/></th>
															</c:when>
															<c:otherwise>
																<th><spring:message code="${labelCode}"/></th>
															</c:otherwise>
														</c:choose>
														<th><spring:message code="fileDescription.table.header.description"/></th>
													</c:if>
													<c:if test="${not needFiletypeCombo}">
														<th><spring:message code="fileDescription.table.header.description"/></th>
													</c:if>
													<th class="action"><spring:message code="fileDescription.table.header.actions"/></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
													<tr>
														<td>
															<a title="${storedFile.originalFilename}" href="getDocument.htm?documentId=${storedFile.documentId}"  target="_blank" id="${storedFile.originalFilename}" class="tknClass">
																<spring:eval var="storeFileBaseName" expression="T(org.apache.commons.io.FilenameUtils).getBaseName(storedFile.originalFilename)"/>
																<spring:eval var="storeFileExtension" expression="T(org.apache.commons.io.FilenameUtils).getExtension(storedFile.originalFilename)"/>
																<c:choose>
																	<c:when test="${storeFileBaseName.length()>15}">
																		${storeFileBaseName.substring(0,15)}...${storeFileExtension}
																	</c:when>
																	<c:otherwise>
																		${storedFile.originalFilename}
																	</c:otherwise>
																</c:choose>
																(${storedFile.canonicalFileSize})
															</a>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].description" value="${storedFile.description}"/>
															<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
															<c:if test="${storedFile.docType!=null}">
																<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].docType" value="${storedFile.docType}"/>
															</c:if>
															<c:if test="${storedFile.docType==null}">
																<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].docType" value=""/>
															</c:if>
														</td>
														<c:if test="${needFiletypeCombo}">
															<td class="breakword" title="${storedFile.docType}">

																<spring:message code="AttachmentDocumentType.${storedFile.docType}"/>

															</td>
														</c:if>
														<td class="breakword" title="${storedFile.description}">
															<c:choose>
																<c:when test="${storedFile.description.length()>=200}">
																	${storedFile.description.substring(0,200)}
																</c:when>
																<c:otherwise>
																	${storedFile.description}
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															<a class="remove-icon toBeRemovedFile">
																<span id="${storedFile.documentId}"></span>
															</a>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									
									<c:otherwise>
										<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
											<span class="file-button">
												<i class="file-icon"></i>
												<a title="${storedFile.originalFilename}" href="getDocument.htm?documentId=${storedFile.documentId}"  target="_blank" id="${storedFile.originalFilename}" class="tknClass">
													<spring:eval var="storeFileBaseName" expression="T(org.apache.commons.io.FilenameUtils).getBaseName(storedFile.originalFilename)"/>
													<spring:eval var="storeFileExtension" expression="T(org.apache.commons.io.FilenameUtils).getExtension(storedFile.originalFilename)"/>
													<c:choose>
														<c:when test="${storeFileBaseName.length()>15}">
															${storeFileBaseName.substring(0,15)}...${storeFileExtension}
														</c:when>
														<c:otherwise>
															${storedFile.originalFilename}
														</c:otherwise>
													</c:choose>
													(${storedFile.canonicalFileSize})
												</a>
												<a class="file-delete-icon toBeRemovedFile">
													<span id="${storedFile.documentId}"></span>
												</a>
											</span>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename" value="${storedFile.originalFilename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename" value="${storedFile.filename}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType" value="${storedFile.contentType}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].description" value="${storedFile.description}"/>
											<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].documentId" value="${storedFile.documentId}"/>
											<c:if test="${storedFile.docType!=null}">
												<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].docType" value="${storedFile.docType}"/>
											</c:if>
											<c:if test="${storedFile.docType==null}">
												<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].docType" value=""/>
											</c:if>
										</c:forEach>
									</c:otherwise>
									
								</c:choose>
							</c:otherwise>
								
						</c:choose>
						<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.filename" value="${fileWrapper.filename}"/>
						<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.attachment" value="true" id="${fileWrapper.attachment}"/>
					</div>
				</c:otherwise>
			</c:choose>
			
			<c:if test="${empty fileWrapper.maximumFiles || fn:length(fileWrapper.storedFiles) < fileWrapper.maximumFiles}">
				<c:choose>
                	<c:when test="${empty inputFileLabelCode && empty fileWrapper.inputFileLabelCode}">
                    	<c:set var="inputFileLabel"><spring:message code="fileUpload.addFiles"/></c:set>
                    </c:when>
                    <c:otherwise>
                    	<c:set var="inputFileLabel"><spring:message code="${not empty fileWrapper.inputFileLabelCode?fileWrapper.inputFileLabelCode:inputFileLabelCode}"/></c:set>
                    </c:otherwise>
				</c:choose>
                <c:set var="allowedFileType">
                	<spring:message code="fileupload.supported.filetypes" argumentSeparator=";" arguments="${fileUploadConfigurationFactory.getAvailableFormats(not empty pathFilename?pathFilename:fileWrapper.pathFilename)}" />
                </c:set>
				<div class="span7 noPaddingBottom fileAttachedButton navUploader" <c:if test="${hasToFollowOption && (empty fileWrapper.attachment || !fileWrapper.attachment)}">style="display:none;"</c:if>>
					<c:if test="${not empty fileWrapper.showPreLabelInputFileButton?fileWrapper.showPreLabelInputFileButton:(not empty showPreLabelInputFileButton ? showPreLabelInputFileButton : true)}">
						<label class="${labelClass}">
							<spring:message code="fileUpload.addAttachments"/>
				            <c:if test="${empty helpUseFlowModeId}">
				                <c:set var="helpUseFlowModeId" value="false"/>
				            </c:if>
				            <c:if test="${not empty helpMessageKey}">
				                <a rel="file-help-tooltip" class="attachment-disclaimer-icon"></a>
				                <div data-tooltip="help" class="hidden">
				                    <a class="close-popover"></a>
				                    <component:helpMessage textCode="${helpMessageKey}" useFlowModeId="${helpUseFlowModeId}"/>
				                </div>
				            </c:if>
                        </label>
                    </c:if>
					
					<c:choose>
						<c:when test="${hasDescription}">
							<span class="btn btn-default fileinput-button" data-target="#modal-file-description" data-toggle="modal">  
								<spring:message code="fileUpload.addFiles"/>
							</span>
							<div id="modal-file-description" class="modal fade modal-file-description">
							<div class="modal-dialog">
							<div class="modal-content">
								<header>
									<ul class="navModal">
										<li><a class="close-icon" data-dismiss="modal"></a></li>
									</ul>
									<h1><spring:message code="fileUpload.addAttachments"/></h1>
								</header>
								<section>
									<c:if test="${needFiletypeCombo}">

										<label> <spring:message code="fileDescription.document.type" /></label>
										<c:set var="documentTypes" value="${configurationServiceDelegator.getAttachmentTypeForFlow(flowScopeDetails.getFlowModeId(), null, null)}" scope="request"/>
										<select name="fileWrapper.docType" class="submittedAjax" id="attachDocType1" data-section="claim" style="max-width: 98%">
											<c:forEach items="${documentTypes}" var="item">
												<c:set var="labelStr" value='${item}'/>
												<option value="${item}">
													<spring:message code="AttachmentDocumentType.${labelStr}"/>
												</option>
											</c:forEach>
										</select>

										<div id="typeOtherWarning" style="display: none; margin-top:10px" class="alert alert-danger">
											<spring:message code="document.type.other.warning" htmlEscape="false"/>
										</div>

										<label> <spring:message code="fileDescription.table.header.description" />
											<c:if test="${checkRequiredDescription}">
												<span class="requiredTag">*</span>
											</c:if>
										</label>

										<textarea id="attachDescription1" class="submittedAjax" name="fileWrapper.description"
												  maxlength="500"
												  rows="4"
												  cols="60"
												  class="submittedAjax"
												  data-section="claim"></textarea>

									</c:if>
									<c:if test="${not needFiletypeCombo}">
										<label>
											<spring:message code="fileDescription.table.header.description"/>
											<c:if test="${checkRequiredDescription}">
												<span class="requiredTag">*</span>
											</c:if>
										</label>
										<textarea id="attachDescription1" class="submittedAjax" name="fileWrapper.description"
												  maxlength="500"
												  rows="4"
												  cols="60"
												  class="submittedAjax"
												  data-section="claim"></textarea>
									</c:if>
										
									<div class="fileProgressBar" role="progressbar">
										<div class="bar" style="width:0%"></div>
									</div>
									<span class="errorMessage"></span>
								</section>
								<footer>
									<span class="btn btn-default fileinput-button ${not empty fileWrapper.inputFileClass?fileWrapper.inputFileClass:inputFileClass}">
										${inputFileLabel}
										<input id="fileUpload" type="file" title="${allowedFileType}" name="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}" class="submittedAjax uploadInput"
											data-url="fileUpload.htm?fileWrapperPath=${fileWrapperPath}&flowKey=${empty param.execution?param.flowKey:param.execution}${empty _csrf.parameterName ? '' : '&'.concat(_csrf.parameterName).concat('=').concat(_csrf.token)}"/>
									</span>
								</footer>
							</div>
							</div>
							</div>
						</c:when>
						<c:otherwise>
							<span class="btn btn-default fileinput-button ${not empty fileWrapper.inputFileClass?fileWrapper.inputFileClass:inputFileClass}">
   								${inputFileLabel}
								<input id="fileUpload" type="file" title="${allowedFileType}" name="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}" class="submittedAjax uploadInput"
									data-url="fileUpload.htm?fileWrapperPath=${fileWrapperPath}&flowKey=${empty param.execution?param.flowKey:param.execution}${empty _csrf.parameterName ? '' : '&'.concat(_csrf.parameterName).concat('=').concat(_csrf.token)}"/>
							</span>
							<div class="fileProgressBar" role="progressbar">
								<div class="bar" style="width:0%"></div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
		</div>
	</c:if>

	<c:if test="${!hasDescription}">
		<span class="errorMessage"></span>
	</c:if>
	
	<c:if test="${!empty errorsAttachment}">
		<c:forEach items="${errorsAttachment}" var="message">
			<p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
		</c:forEach>
	</c:if>
	
	<c:if test="${!empty errorsDescription}">
		<c:forEach items="${errorsDescription}" var="message">
			<p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
		</c:forEach>
	</c:if>

<!-- Bootstrap Image Gallery Popup Light Box -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls">
    <!-- Modal Box Container -->
    <div class="slides"></div>
    <!-- Lightbox Controls -->
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="download" href="${image_url}" title="<spring:message code="fileUpload.download"/>"><span class="sp-download-icon"></span></a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>

</tags:render>
<%--    
</c:if>
 --%>
	
