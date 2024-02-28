<%@ tag language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/compmock" prefix="compmock" %>

<jsp:directive.attribute name="fileWrapper" required="true" type="eu.ohim.sp.common.ui.form.FileWrapper" description="Path for the form:tag"/>
<jsp:directive.attribute name="fileWrapperPath" required="true" type="java.lang.String" description="the path where the fileWrapper class exists during validation"/>
<jsp:directive.attribute name="labelCode" required="false" type="java.lang.String" description="The label code that is used to get label for the field"/>
<jsp:directive.attribute name="pathFilename" required="false" type="java.lang.String" description="The path that will be used on the file upload, used on the MultipartForm"/>

<c:set var="sectionId" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:sectionId}"/>

<sec:accesscontrollist var="security_attachment_add" hasPermission="Attachment_Add" domainObject="${flowModeId}"/>
<c:if test="${security_attachment_add || true}">

	<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
		<c:if test="${storedFile.fileSize==null}">
			${storedFile.setFileSize(configurationServiceDelegator.getDocumentSize(flowBean.idreserventity.concat("/").concat(storedFile.originalFilename), ""))}
		</c:if>
	</c:forEach>

    <tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}"
                 checkRender="true">
                 
		<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.description"
						checkRender="true" var="hasDescription" />
		<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.attachment"
		                 checkRender="true" var="hasToFollowOption" />
		<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.preview"
		                 checkRender="true" var="hasPreview" />

		<c:set var="errorsAttachment">
			<form:errors path="${fileWrapperPath}.attachment"></form:errors>
		</c:set>
		<c:set var="errorsDescription">
			<c:if test="${not empty multipartFileForm}">
				<form:errors path="multipartFileForm.fileWrapper.description"></form:errors>
			</c:if>
		</c:set>

		<c:set var="checkRequired">
			<tags:required flowModeId="${flowModeId}" sectionId="${sectionId}"
				field="mainForm.${pathFilename}" />
		</c:set>

		<c:if test="${empty errorsDescription}">
			<c:if test="${not empty fileWrapper.labelCode || not empty labelCode}">
				<label>
					<spring:message code="${not empty labelCode?labelCode:fileWrapper.labelCode}"/>
					<c:if test="${checkRequired}">
						<em class="requiredTag">(*)</em>
					</c:if>
				</label>
			</c:if>
			<c:set value="true" var="selectBox"/>
			<span id="formUpload"> 
				<inputmock name="fileWrapperPath" value="${fileWrapperPath}"></inputmock>
				<inputmock name="fileWrapper.labelCode" value="${not empty labelCode?labelCode:fileWrapper.labelCode}"></inputmock>
				<inputmock name="fileWrapper.pathFilename" value="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}"></inputmock>
				<inputmock name="fileWrapper.attachment" value="${fileWrapper.attachment}"></inputmock> 
				<inputmock name="fileWrapper.sectionId" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:sectionId}"></inputmock>
				<inputmock name="fileWrapper.maximumFiles" value="${fileWrapper.maximumFiles}"></inputmock>
				<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
					<inputmock name="fileWrapper.storedFiles[${status.count-1}].filename"
						value="${storedFile.filename}"></inputmock>
					<inputmock name="fileWrapper.storedFiles[${status.count-1}].fileSize" value="${storedFile.fileSize}"></inputmock>
					<inputmock name="fileWrapper.storedFiles[${status.count-1}].originalFilename"
						value="${storedFile.originalFilename}"></inputmock>
					<inputmock name="fileWrapper.storedFiles[${status.count-1}].description"
						value="${storedFile.description}"></inputmock>
					<inputmock name="fileWrapper.storedFiles[${status.count-1}].contentType"
							   value="${storedFile.contentType}"></inputmock>
				</c:forEach>
			</span>
			
			<div class="row noPaddingBottom">
				<c:choose>
					<c:when test="${empty fileWrapper.storedFiles}">
						<c:if test="${hasToFollowOption && selectBox}">
							<ul class="file-upload-radio">
							    <li>
									<label for="copy-upload-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachment" value="true" <c:if test="${not empty fileWrapper.attachment && fileWrapper.attachment}">checked</c:if> id="copy-upload-${fileWrapperPath}"><spring:message code="fileUpload.attachment.attached"/></label>
								</li>
							    <li>
									<label for="copy-tofollow-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachment" value="false" <c:if test="${empty fileWrapper.attachment || !fileWrapper.attachment}">checked</c:if> id="copy-tofollow-${fileWrapperPath}"><spring:message code="fileUpload.attachment.follow"/></label>
								</li>
							</ul>
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="span7 noPaddingBottom">
								<c:choose>
									<c:when test="${hasPreview}">
										<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
											<c:choose>
												<c:when test="${storedFile.contentType!='audio/mp3' && storedFile.contentType!='audio/mpeg'}">
													<div class="mark-image-holder">
														<div class="mark-image">
															<img class="thumb" id="${storedFile.originalFilename}" src="getImage.htm?image=${storedFile.originalFilename}&flowKey=${empty param.execution?param.flowKey:param.execution}"/>
															<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
																<div data-href="getImage.htm?image=${storedFile.originalFilename}&flowKey=${empty param.execution?param.flowKey:param.execution}" class="mark-image-expand" data-gallery="gallery"></div>
															</div>
															<div id="modal-gallery" class="modal modal-gallery hide fade" tabindex="-1">
																<header>
																	<h1>${storedFile.filename}</h1>
																	<a class="close-icon" data-dismiss="modal"></a>
																</header>
																<div class="modal-body"><div class="modal-image"></div></div>
																<div class="modal-footer">
																	<a class="btn modal-download" target="_blank"><i class="icon-download"></i> <spring:message code="fileUpload.download"/></a>
																</div>
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
															     <compmock:helpMessage textCode="mark.file.help"
                                                                    useFlowModeId="true"/>
															</div>
															<p class="mark-file-delete"><a class="btn toBeRemovedFile"><i class="mark-file-delete-icon"></i><span id="${storedFile.originalFilename}"><spring:message code="fileUpload.action.delete"/></span></a></p>
														</div>
													</div>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename"
														   value="${storedFile.originalFilename}"/>
													<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename"
														   value="${storedFile.filename}"/>
												</c:when>
												<c:otherwise>
													<span>
														<!--[If IE]> 
															<a href="getDocument.htm?flowKey=${empty param.execution?param.flowKey:param.execution}&document=${storedFile.originalFilename}&uploadName=${storedFile.filename}"  target="_blank" id="${storedFile.originalFilename}"
																  class="tknClass">${storedFile.filename}</a>
														<![endif]-->
														<!--[if !IE]>-->
														<audio controls="controls">
														  <source src="getSoundFile.htm?file=${storedFile.originalFilename}&flowKey=${empty param.execution?param.flowKey:param.execution}" type="audio/mpeg" />
														  Your browser does not support the audio element.
														</audio>
														<!--<![endif]-->
														<span class="btn fileinput-button toBeRemovedFile">
															<i class="icon-trash icon"></i>
															<span id="${storedFile.originalFilename}"><spring:message code="fileUpload.action.delete"/> </span>
														</span>
													</span>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename"
														   value="${storedFile.originalFilename}"/>
													<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].filename"
														   value="${storedFile.filename}"/>
												
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
			    							<c:when test="${hasDescription}">
												<table class="genericTable tableFileupload">
													<thead>
														<tr>
															<th class="filename"><spring:message code="fileDescription.table.header.filename"/></th>
															<th><spring:message code="fileDescription.table.header.description"/></th>
															<th class="action"><spring:message code="fileDescription.table.header.actions"/></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
															<tr>
																<td>
																	<a title="${storedFile.filename}" href="getDocument.htm?flowKey=${empty param.execution?param.flowKey:param.execution}&document=${storedFile.originalFilename}&uploadName=${storedFile.filename}"  target="_blank" id="${storedFile.originalFilename}"
																		  class="tknClass">
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
																		(${storedFile.canonicalFileSize})
																	</a>
																	<input type="hidden" class="submittedAjax"
																		   name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename"
																		   value="${storedFile.originalFilename}"/>
																	<input type="hidden" class="submittedAjax"
																		   name="${fileWrapperPath}.storedFiles[${status.count-1}].filename"
																		   value="${storedFile.filename}"/>
																	<input type="hidden" class="submittedAjax"
																		   name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType"
																		   value="${storedFile.contentType}"/>
																	<input type="hidden" class="submittedAjax"
																		   name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize"
																		   value="${storedFile.fileSize}"/>
																	<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].description"
																		   value="${storedFile.description}"/>
																</td>
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
																		<span id="${storedFile.originalFilename}"></span>
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
														<a title="${storedFile.filename}" href="getDocument.htm?flowKey=${empty param.execution?param.flowKey:param.execution}&document=${storedFile.originalFilename}&uploadName=${storedFile.filename}"  target="_blank" id="${storedFile.originalFilename}"
															  class="tknClass">
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
															(${storedFile.canonicalFileSize})
														</a>
														<a class="file-delete-icon toBeRemovedFile">
															<span id="${storedFile.originalFilename}"></span>
														</a>
													</span>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].originalFilename"
														   value="${storedFile.originalFilename}"/>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].filename"
														   value="${storedFile.filename}"/>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].contentType"
														   value="${storedFile.contentType}"/>
													<input type="hidden" class="submittedAjax"
														   name="${fileWrapperPath}.storedFiles[${status.count-1}].fileSize"
														   value="${storedFile.fileSize}"/>
													<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.storedFiles[${status.count-1}].description"
														   value="${storedFile.description}"/>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.filename" value="${fileWrapper.filename}"/>
							<input type="hidden" class="submittedAjax" name="${fileWrapperPath}.attachment" value="true"
								   id="${fileWrapper.attachment}"/>
					   </div>
					</c:otherwise>
				</c:choose>
				<c:if test="${empty fileWrapper.maximumFiles || fn:length(fileWrapper.storedFiles)<fileWrapper.maximumFiles}">
					<div class="span7 noPaddingBottom fileAttachedButton navUploader" <c:if test="${hasToFollowOption && (empty fileWrapper.attachment || !fileWrapper.attachment)}">style="display:none;"</c:if>>
						<label><spring:message code="fileUpload.addAttachments"/><a rel="file-help-tooltip" class="attachment-disclaimer-icon"></a>
							<div data-tooltip="help" class="hidden">
								<a class="close-popover"></a>
								<compmock:helpMessage textCode="attachments.help" useFlowModeId="true"/>
							</div>
                        </label>
							<c:choose>
								<c:when test="${hasDescription}">
									<span class="btn fileinput-button" data-target="#modal-file-description" data-toggle="modal"> 
										<spring:message code="fileUpload.addFiles"/>
									</span>
									<div id="modal-file-description" class="modal fade modal-file-description">
										<header>
											<ul class="navModal">
												<li><a class="close-icon" data-dismiss="modal"></a></li>
											</ul>
											<h1><spring:message code="fileDescription.description"/></h1>
										</header>
										<section>
											<textarea id="attachDescription1" class="submittedAjax" name="fileWrapper.description"
												maxlength="500"
												class="submittedAjax"
												data-section="claim"></textarea>
											<span class="errorMessage"></span>
										</section>
										<footer>
											<span class="btn fileinput-button"> 
												<c:set var="allowedFileType">
													<spring:message code="fileupload.supported.filetypes" argumentSeparator=";" arguments="${fileUploadConfigurationFactory.getAvailableFormats(not empty pathFilename?pathFilename:fileWrapper.pathFilename)}" />
												</c:set>
												<spring:message code="fileUpload.addFiles"/>
												<input id="fileUpload" type="file" title="${allowedFileType}" name="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}" class="submittedAjax uploadInput"
													data-url="fileUpload.htm?fileWrapperPath=${fileWrapperPath}&flowKey=${empty param.execution?param.flowKey:param.execution}"/>
											</span>
										</footer>
									</div>
								</c:when>
								<c:otherwise>
									<span class="btn fileinput-button"> 
										<c:set var="allowedFileType">
											<spring:message code="fileupload.supported.filetypes" argumentSeparator=";" arguments="${fileUploadConfigurationFactory.getAvailableFormats(not empty pathFilename?pathFilename:fileWrapper.pathFilename)}" />
										</c:set>
										<spring:message code="fileUpload.addFiles"/>
										<input id="fileUpload" type="file" title="${allowedFileType}" name="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}" class="submittedAjax uploadInput"
											data-url="fileUpload.htm?fileWrapperPath=${fileWrapperPath}&flowKey=${empty param.execution?param.flowKey:param.execution}"/>
									</span>
								</c:otherwise>
							</c:choose>
						<div class="fileProgressBar" role="progressbar">
							<div class="bar" style="width:0%"></div>
						</div>
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
    </tags:render>
</c:if>
	