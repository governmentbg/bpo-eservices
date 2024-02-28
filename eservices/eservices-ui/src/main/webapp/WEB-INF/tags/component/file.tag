<%@ tag language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>
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
<c:set var="WORDMARK" value="<%=eu.ohim.sp.common.ui.form.trademark.TradeMarkType.WORD.toString()%>"/>

<c:if test="${empty labelClass}">
	<c:set var="labelClass" value=""/>
</c:if>

<!-- check if we're in trademark import -->
<c:choose>
	<c:when test="${!empty tmDetailsForm}">	
		<c:choose>
			<c:when test="${tmDetailsForm.imported}">
				<c:set var="tm_imported" value="true"/>
			</c:when>
			<c:otherwise>
				<c:set var="tm_imported" value="false"/>
			</c:otherwise>
		</c:choose>			
	</c:when>
	<c:otherwise>
			<c:set var="tm_imported" value="false"/>
	</c:otherwise>
</c:choose>	

<!-- check when opposition basis earlier trademark is imported -->
<c:choose>
	<c:when test="${!empty oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails}">	
		<c:choose>
			<c:when test="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported}">
				<c:set var="tm_opposed_imported" value="true"/>
			</c:when>
			<c:otherwise>
				<c:set var="tm_opposed_imported" value="false"/>
			</c:otherwise>
		</c:choose>			
	</c:when>
	<c:otherwise>
			<c:set var="tm_opposed_imported" value="false"/>
	</c:otherwise>
</c:choose>

<!-- check if we're in design import -->
<c:choose>
	<c:when test="${!empty eSDesignDetailsForm}">	
		<c:choose>
			<c:when test="${eSDesignDetailsForm.imported}">
				<c:set var="ds_imported" value="true"/>
			</c:when>
			<c:otherwise>
				<c:set var="ds_imported" value="false"/>
			</c:otherwise>
		</c:choose>			
	</c:when>
	<c:otherwise>
			<c:set var="ds_imported" value="false"/>
	</c:otherwise>
</c:choose>

<%--
<sec:accesscontrollist var="security_attachment_add" hasPermission="Attachment_Add" domainObject="${flowModeId}"/>
<c:if test="${security_attachment_add || true}">
--%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}" checkRender="true">
                 
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.description" checkRender="true" var="hasDescription" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.explanation" checkRender="true" var="hasExplanation" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.attachment" checkRender="true" var="hasToFollowOption" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.toFollowTextOnly" checkRender="true" var="hasToFollowText" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.preview" checkRender="true" var="hasPreview" />
	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.toFollowWarning" checkRender="true" var="hasToFollowWarning" />
    <tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.provided" checkRender="true" var="provided" />
    <tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}.docType" checkRender="true" var="needFiletypeCombo" />



<c:choose>
	<c:when test="${hasToFollowText }">
		<c:if test="${flowModeId != 'pt-invalidity' && flowModeId != 'spc-invalidity' && flowModeId != 'um-invalidity'}">
		<br>
		<div class="alert alert-info">
			<spring:message code="${flowModeId}.grounds.details.evidence.toFollowText.${sectionId }"></spring:message>
		</div>
		</c:if>
	</c:when>
<c:when test="${tm_imported}">
	<c:if test="${WORDMARK != tmDetailsForm.tradeMarkType}">
		<c:if test="${not empty tmDetailsForm.imageRepresentationURI}">
			<div class="mark-image">
				<p><img class="thumb" src="${tmDetailsForm.imageRepresentationURI}"/>
				</p>
			</div>
		</c:if>
		<c:if test="${not empty fileWrapper && not empty fileWrapper.storedFiles}">
			<c:set var="storedFileTmImg" value="${fileWrapper.storedFiles[0]}"></c:set>
			<input type="hidden" name="${fileWrapperPath}.storedFiles[0].originalFilename" value="${storedFileTmImg.originalFilename}"/>
			<input type="hidden" name="${fileWrapperPath}.storedFiles[0].filename" value="${storedFileTmImg.filename}"/>
			<input type="hidden" name="${fileWrapperPath}.storedFiles[0].documentId" value="${storedFileTmImg.documentId}"/>
			<input type="hidden" name="${fileWrapperPath}.storedFiles[0].fileSize" value="${storedFileTmImg.fileSize}"/>
			<input type="hidden" name="${fileWrapperPath}.storedFiles[0].contentType" value="${storedFileTmImg.contentType}"/>
		</c:if>
	</c:if>
</c:when>
<c:when test="${(fileWrapperPath=='relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.representationAttachment') && (tm_opposed_imported || (not empty oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imageRepresentationURI && !fileWrapper.attachment))}">
	<c:if test="${WORDMARK != oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkType}">
		<div class="mark-image">
			<p><img class="thumb" src="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imageRepresentationURI}"/>		    		   
			</p>
		</div>
	</c:if>
</c:when>
	<c:when test="${ds_imported}">
		<c:choose>
			<c:when test="${not empty fileWrapper && not empty fileWrapper.storedFiles && fn:length(fileWrapper.storedFiles) > 1}">
				<div id="carousel-container">
					<div id="dsCarousel" class="carousel slide">
						<div class="carousel-inner">
							<c:set value="active" var="activecarousel"/>
							<c:forEach items="${fileWrapper.storedFiles}" var="storedFile" varStatus="status">
								<div class="${activecarousel} item"><img  src="${storedFile.originalFilename}"/></div>
								<c:set value="" var="activecarousel"/>
							</c:forEach>
						</div>
						<a data-slide="prev" href="#dsCarousel" class="left carousel-control left-glyph"></a>
						<a data-slide="next" href="#dsCarousel" class="right carousel-control right-glyph"></a>
					</div>
				</div>
			</c:when>
			<c:when test="${not empty fileWrapper && not empty fileWrapper.storedFiles && fn:length(fileWrapper.storedFiles) == 1}">
				<c:set var="storedSingle" value="${fileWrapper.storedFiles[0]}"/>
				<div class="mark-image">
					<p>
						<c:if test="${not empty eSDesignDetailsForm.imageRepresentationURI}">
							<img class="thumb" src="${eSDesignDetailsForm.imageRepresentationURI}"/>
						</c:if>
						<c:if test="${empty eSDesignDetailsForm.imageRepresentationURI}">
							<img class="thumb" src="${storedSingle.originalFilename}"/>
						</c:if>
					</p>
				</div>
				<input type="hidden" name="${fileWrapperPath}.storedFiles[0].originalFilename" value="${storedSingle.originalFilename}"/>
				<input type="hidden" name="${fileWrapperPath}.storedFiles[0].filename" value="${storedSingle.filename}"/>
				<input type="hidden" name="${fileWrapperPath}.storedFiles[0].documentId" value="${storedSingle.documentId}"/>
				<input type="hidden" name="${fileWrapperPath}.storedFiles[0].fileSize" value="${storedSingle.fileSize}"/>
				<input type="hidden" name="${fileWrapperPath}.storedFiles[0].contentType" value="${storedSingle.contentType}"/>
			</c:when>
		</c:choose>
	</c:when>
<c:otherwise>			
	<c:set var="errorsAttachment">
		<form:errors path="${fileWrapperPath}.attachment"></form:errors>
	</c:set>
		
	<c:set var="errorsDescription">
		<c:if test="${not empty multipartFileForm}">
			<form:errors path="multipartFileForm.fileWrapper.description"></form:errors>
		</c:if>
	</c:set>

	<c:set var="checkRequired">
		<tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}" />
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
					<c:if test="${hasToFollowOption || provided || hasExplanation}"> <%--c:if test="${hasToFollowOption && selectBox}"--%>
						<c:set var="checkAttachOption" value="${hasExplanation and (empty fileWrapper.attachmentStatus or fileWrapper.attachmentStatus eq 'NOT_ATTACHED')}"/>
						<c:set var="checkProvidedOption" value="${provided and not empty fileWrapper.attachmentStatus and fileWrapper.attachmentStatus eq 'NOT_ATTACHED'}"/>

						<ul class="file-upload-radio">
							<li>
								<label for="copy-upload-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachmentStatus" value="ATTACHED" <c:if test="${checkAttachOption or (not empty fileWrapper.attachmentStatus and fileWrapper.attachmentStatus eq 'ATTACHED')}">checked</c:if> id="copy-upload-${fileWrapperPath}"><spring:message code="fileUpload.attachment.attached"/></label>
							</li>
							<c:if test="${hasExplanation && provided == false}">
								<li>
									<label for="copy-explanation-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachmentStatus" value="EXPLANATIONS" <c:if test="${not empty fileWrapper.attachmentStatus and fileWrapper.attachmentStatus eq 'EXPLANATIONS'}">checked</c:if> id="copy-explanation-${fileWrapperPath}"><spring:message code="fileUpload.attachment.explanation"/></label>
								</li>
							</c:if>

							<c:if test="${hasToFollowOption }">
								<li>
									<label for="copy-tofollow-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachmentStatus" value="TO_FOLLOW" <c:if test="${not empty fileWrapper.attachmentStatus and fileWrapper.attachmentStatus eq 'TO_FOLLOW'}">checked</c:if> id="copy-tofollow-${fileWrapperPath}"><spring:message code="fileUpload.attachment.follow"/></label>
									<c:if test="${not empty hasToFollowWarning && hasToFollowWarning}">

											<input type="hidden" id="copy-tofollow-${fileWrapperPath}-warning" value="<spring:message code="${flowModeId}.${fileWrapperPath}.warning"/>"></input>

									</c:if>
								</li>
							</c:if>
                            <c:if test="${provided}">
                                <li>
                                    <label for="copy-provided-${fileWrapperPath}"><input type="radio" name="${fileWrapperPath}.attachmentStatus" value="PROVIDED" <c:if test="${checkProvidedOption or (not empty fileWrapper.attachmentStatus and fileWrapper.attachmentStatus eq 'PROVIDED')}">checked</c:if> id="copy-provided-${fileWrapperPath}"><spring:message code="fileUpload.attachment.provided"/></label>
                                </li>
                            </c:if>
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
										
										<c:when test="${storedFile.contentType != 'audio/mp3' && storedFile.contentType != 'video/mp4' && storedFile.contentType != 'audio/mpeg' && storedFile.contentType != 'application/pdf'}">
											<div class="mark-image-holder">
												<br>
												<div class="mark-image">
													<img class="thumb" id="${storedFile.originalFilename}" src="getDocument.htm?documentId=${storedFile.documentId}"/>
													<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
														<div data-href="getDocument.htm?documentId=${storedFile.documentId}" class="mark-image-expand" data-gallery="gallery" title="${image_filename}"></div>
													</div>
												</div>
												
												<div class="mark-image-details">
<!-- 													<p class="mark-file-name"> -->
<!-- 														<i class="mark-file-ok-icon"></i> -->
<%-- 														<spring:eval var="storeFileBaseName" expression="T(org.apache.commons.io.FilenameUtils).getBaseName(storedFile.originalFilename)"/> --%>
<%-- 														<spring:eval var="storeFileExtension" expression="T(org.apache.commons.io.FilenameUtils).getExtension(storedFile.originalFilename)"/> --%>
<%-- 														<c:choose> --%>
<%-- 															<c:when test="${storeFileBaseName.length()>15}"> --%>
<%-- 																${storeFileBaseName.substring(0,15)}...${storeFileExtension} --%>
<%-- 															</c:when> --%>
<%-- 															<c:otherwise> --%>
<%-- 																${storedFile.originalFilename} --%>
<%-- 															</c:otherwise> --%>
<%-- 														</c:choose> --%>
<!-- 													</p> -->
<!-- 													<p class="mark-file-size"> -->
<%-- 														<spring:message code="fileDescription.type"/>: ${storedFile.contentType}<br> --%>
<!-- 													</p> -->
<!-- 													<p class="mark-file-size"> -->
<%-- 														(${storedFile.canonicalFileSize}) --%>
<!-- 													</p> -->
<!-- 													<div class="mark-file-disclaimer"> -->
<!-- 														<i class="mark-file-disclaimer-icon"></i> -->
<%-- 														<component:helpMessage textCode="design.view.file.help" useFlowModeId="true"/> --%>
<!-- 													</div> -->
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
												<div class="mark-image">
													<div><img class="thumb" id="${storedFile.originalFilename}" src="resources/img/pdf.png"/> <c:out value="${storedFile.originalFilename}">?</c:out></div>
													<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
														<div data-href="getDocument.htm?documentId=${storedFile.documentId}" class="mark-image-expand" data-gallery="gallery"></div>
													</div>
												</div>												
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
												<div class="mark-image">
													<div><img class="thumb" id="${storedFile.originalFilename}" src="resources/img/multimedia.png"/> <c:out value="${storedFile.originalFilename}">?</c:out></div>
													<div id="gallery" data-selector="div.mark-image-expand" data-toggle="modal-gallery" data-target="#modal-gallery">
														<div data-href="getDocument.htm?documentId=${storedFile.documentId}" class="mark-image-expand" data-gallery="gallery"></div>
													</div>
												</div>												
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
				<div class="span7 noPaddingBottom fileAttachedButton navUploader" <c:if test="${(hasToFollowOption || provided) && (empty fileWrapper.attachment || !fileWrapper.attachment)}">style="display:none;"</c:if>>
					<c:if test="${not empty fileWrapper.showPreLabelInputFileButton?fileWrapper.showPreLabelInputFileButton:(not empty showPreLabelInputFileButton ? showPreLabelInputFileButton : true)}">
						<label>
							<spring:message code="fileUpload.addAttachments"/>
				            <c:if test="${empty helpUseFlowModeId}">
				                <c:set var="helpUseFlowModeId" value="true"/>
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
							<span class="btn btn-default fileinput-button" data-toggle="show-next"> 
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
										<c:set var="documentTypes" value="${configurationServiceDelegator.getAttachmentTypeForFlow(flowScopeDetails.getFlowModeId(), null, flowBean.changeType)}" scope="request"/>
										<select name="fileWrapper.docType" class="submittedAjax" id="attachDocType1" data-section="claim">
											<c:forEach items="${documentTypes}" var="item">
												<c:set var="mandatory" value="${configurationServiceDelegator.attachmentTypeMandatory(flowScopeDetails.getFlowModeId(), null, flowBean.changeType, item)}" scope="request" />
												<c:set var="labelStr" value='${item}'/>
												<option value="${item}">
													<span><spring:message code="AttachmentDocumentType.${labelStr}"/></span>
													<c:if test="${mandatory}">
														<span style="margin-left: 5px">*</span>
													</c:if>
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
</c:otherwise>
</c:choose>

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
	
