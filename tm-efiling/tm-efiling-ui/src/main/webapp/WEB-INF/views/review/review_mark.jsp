<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="trade-mark-details">
        <header>
            <spring:message code="review.mark.details"/>
            <a class="edit navigateBtn" data-val="Update_Mark"><spring:message code="review.update"/></a>
        </header>
        
        <div>
			<div>
				<c:if test="${not empty flowBean.mainForm and not empty flowBean.mainForm.fileWrapperImage and not empty flowBean.mainForm.fileWrapperImage.storedFiles}">
					<c:forEach items="${flowBean.mainForm.fileWrapperImage.storedFiles}" var="storedFile" varStatus="status">
						<c:if test="${empty storedFile.documentId}">
							<c:set var="documentSrc" value="${storedFile.originalFilename}"></c:set>
						</c:if>
						<c:if test="${not empty storedFile.documentId}">
							<c:set var="documentSrc" value="getDocument.htm?documentId=${storedFile.documentId}&flowKey=${empty param.execution?param.flowKey:param.execution}"></c:set>
						</c:if>
						<a href="${documentSrc}"
						   title="${storedFile.filename}">
							<img id="${storedFile.originalFilename}"
								 src="${documentSrc}"/>
						</a>
					</c:forEach>
				</c:if>
			</div>
			<div>
				<jsp:include page="review_mark_views.jsp"/>
			</div>
			<div>
				<c:if test="${not empty flowBean.mainForm and not empty flowBean.mainForm.soundFile and not empty flowBean.mainForm.soundFile.storedFiles}">
					<c:forEach items="${flowBean.mainForm.soundFile.storedFiles}" var="storedFile" varStatus="status">
						<audio controls="controls">
							<source src="getDocument.htm?documentId=${storedFile.documentId}&flowKey=${empty param.execution?param.flowKey:param.execution}"
									type="audio/mp3"/>
							Your browser does not support the audio element.
						</audio>
					</c:forEach>
				</c:if>
			</div>
			<div>
				<c:if test="${not empty flowBean.mainForm and not empty flowBean.mainForm.fileWrapperMultimedia and not empty flowBean.mainForm.fileWrapperMultimedia.storedFiles}">
					<c:forEach items="${flowBean.mainForm.fileWrapperMultimedia.storedFiles}" var="storedFile" varStatus="status">
						<video width="320" height="240" controls="controls">
							<source src="getDocument.htm?documentId=${storedFile.documentId}" type="video/mp4" />
							Your browser does not support the video element.
						</video>
					</c:forEach>
				</c:if>
			</div>
       	 	<div class="details-list">
		        <c:choose>
		            <c:when test="${flowBean.mainForm.markType=='wordmark'}">
		            	<i class="word-mark"></i><span><spring:message code="mark.names.word"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='figurative'}">
		            	<i class="figurative-mark"></i><span><spring:message code="mark.names.figurative"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='figwordmark'}">
		            	<i class="figurative-with-words-mark"></i><span><spring:message code="mark.names.figurativeContainingWords"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='3dmark'}">
		            	<i class="threed-mark"></i><span><spring:message code="mark.names.threeD"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='colourmark'}">
		            	<i class="colour-mark"></i><span><spring:message code="mark.names.colour"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='soundmark'}">
		            	<i class="sound-mark"></i><span><spring:message code="mark.names.sound"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='other'}">
		            	<i class="other-mark"></i><span><spring:message code="mark.names.other"/></span>
		            </c:when>
		            <c:when test="${flowBean.mainForm.markType=='3dwordmark'}">
		            	<i class="threed-with-words-mark"></i><span><spring:message code="mark.names.threeDword"/></span>
		            </c:when>
					<c:when test="${flowBean.mainForm.markType=='multimediamark'}">
						<i class="multimedia-mark"></i><span class="mark-details-type"><spring:message code="mark.names.multimedia"/></span>
					</c:when>
					<c:when test="${flowBean.mainForm.markType=='positionmark'}">
						<i class="position-mark"></i><span class="mark-details-type"><spring:message code="mark.names.position"/></span>
					</c:when>
					<c:when test="${flowBean.mainForm.markType=='motionmark'}">
						<i class="motion-mark"></i><span class="mark-details-type"><spring:message code="mark.names.motion"/></span>
					</c:when>
					<c:when test="${flowBean.mainForm.markType=='patternmark'}">
						<i class="pattern-mark"></i><span class="mark-details-type"><spring:message code="mark.names.pattern"/></span>
					</c:when>
					<c:when test="${flowBean.mainForm.markType=='hologrammark'}">
						<i class="hologram-mark"></i><span class="mark-details-type"><spring:message code="mark.names.hologram"/></span>
					</c:when>
		            <c:when test="${empty flowBean.mainForm.markType}">&nbsp;</c:when>
		        </c:choose>
	        
	        
	
	            <dl>
					<c:if test="${flowBean.mainForm.certificateMark or flowBean.mainForm.collectiveMark}">
						<dt><spring:message code="review.mark.type"/></dt>
						<dd>
							<c:choose>
								<c:when test="${flowBean.mainForm.certificateMark }">
									<spring:message code="review.mark.type.certificate"/>
								</c:when>
								<c:when test="${flowBean.mainForm.collectiveMark }">
									<spring:message code="review.mark.type.collective"/>
								</c:when>
							</c:choose>
						</dd>
					</c:if>
	                <c:if test="${not empty flowBean.reference}">
				        <dt><spring:message code="review.mark.reference"/></dt>
				        <dd>${flowBean.reference}</dd>
				    </c:if>
				    <c:if test="${not empty flowBean.mainForm.wordRepresentation}">
	                	<dt><spring:message code="review.mark.wordElements"/></dt>
				    	<dd>
				    	 <spring:eval var="storewordRepresentation" expression="flowBean.mainForm.wordRepresentation"/>
									<c:choose>
										<c:when test="${storewordRepresentation.length()>50}">
											${storewordRepresentation.substring(0,50)}...
										</c:when>
										<c:otherwise>
											${storewordRepresentation}
										</c:otherwise>
									</c:choose>
				    	<dd>
				    </c:if>
					<c:if test="${flowBean.mainForm.markType=='other' && not empty flowBean.mainForm.markDescription}">
						<dt><spring:message code="review.mark.description"/></dt>
						<dd>
							<spring:eval var="storewordRepresentationDescr" expression="flowBean.mainForm.markDescription"/>
							<c:choose>
								<c:when test="${storewordRepresentationDescr.length()>100}">
									${storewordRepresentationDescr.substring(0,100)}...
								</c:when>
								<c:otherwise>
									${storewordRepresentationDescr}
								</c:otherwise>
							</c:choose>
						<dd>
					</c:if>
	                <%--<c:set var="firstLanguage"--%>
				           <%--value="${configurationServiceDelegator.resolveFirstLanguageCode(flowBean.firstLang)}"/>--%>
				    <%--<c:if test="${firstLanguage.value!='selectBox.SELECT'}">--%>
				        <%--<dt><spring:message code="review.mark.firstLanguage"/></dt>--%>
				        <%--<dd><c:if test="${not empty firstLanguage.value}"><spring:message code="${firstLanguage.value}"/></c:if>--%>
				            <%--<c:if test="${empty firstLanguage.value}">&nbsp;</c:if></dd>--%>
				    <%--</c:if>--%>
				<%----%>
				    <%--<c:set var="secondLanguage" value="${configurationServiceDelegator.resolveFirstLanguageCode(flowBean.secLang)}"/>--%>
				    <%--<c:if test="${secondLanguage.value!='selectBox.SELECT'}">--%>
				        <%--<dt><spring:message code="review.mark.secondLanguage"/></dt>--%>
				        <%--<dd><c:if test="${not empty secondLanguage.value}"><spring:message code="${secondLanguage.value}"/></c:if>--%>
				            <%--<c:if test="${empty secondLanguage.value}">&nbsp;</c:if></dd>--%>
				    <%--</c:if>--%>


	            </dl>
            </div>
        </div>
</div>