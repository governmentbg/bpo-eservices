<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="your-details">
        <header>
            <spring:message code="review.design.details"/>
            <a class="edit navigateBtn" data-val="Update_Design"><spring:message code="review.update"/></a>
        </header>
        
        <div>                
	        <c:if test="${not empty flowBean.designs}">
				<table class="table">
					<caption><spring:message code="review.design.info.title"/></caption>
					<thead>
						<tr>
							<th><spring:message code="review.design.table.column.number.title"/></th>
							<th><spring:message code="review.design.table.column.thumbnail.title"/></th>
							<th><spring:message code="review.design.table.column.views.title"/></th>
							<th><spring:message code="review.design.table.column.locarnoClassification.title"/></th>
							<th><spring:message code="review.design.table.column.productIndication.title"/></th>
							<th><spring:message code="review.design.table.column.deferred.title"/></th>
							<th><spring:message code="review.design.table.column.actions.title"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="design" items="${flowBean.designs}" varStatus="status">	
							<tr>
								<td><c:out value="${status.count}"></c:out></td>
								<td>
									<c:set var="storedFile" value="${design.views[0].view.storedFiles.size() > 0 ? design.views[0].view.storedFiles[0] : null}" />
									<div id="viewThumbnailList${status.count}" class="modal fade messagePopup modal-design-view-image" style="display:none">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="header">
													<h1><spring:message code="review.design.thumbnail.title" /> ${status.count}</h1>
													<a class="close-icon" data-dismiss="modal"></a>
												</div>

												<div class="section">
													<ul>
														<c:forEach var="viewItem" items="${design.views}" varStatus="viewStatus">
															<li>
																<c:set var="viewItemFile" value="${viewItem.view.storedFiles.size() > 0 ? viewItem.view.storedFiles[0] : null}" />

																<div class="view-image">
																	<c:if test="${viewItemFile ne null}">
																		<c:choose>
																			<c:when test="${viewItemFile.thumbnail ne null}">
																				<c:if test="${viewItemFile.thumbnail.documentId ne null && not empty viewItemFile.thumbnail.documentId}">
																					<img src="getDocument.htm?documentId=<c:out escapeXml="true" value="${viewItemFile.documentId}" />"  id="viewImage${viewStatus.count}" data-file-documentId="<c:out escapeXml="true" value="${viewItemFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${viewItemFile.originalFilename}" />" >
																				</c:if>
																				<c:if test="${viewItemFile.thumbnail.documentId eq null || empty viewItemFile.thumbnail.documentId}">
																					<img src="resources/img/thumbnailNotAvailable.png" data-file-documentId="<c:out escapeXml="true" value="${viewItemFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${viewItemFile.originalFilename}" />">
																				</c:if>
																			</c:when>
																			<c:otherwise>
																				<img src="getDocument.htm?documentId=<c:out escapeXml="true" value="${viewItemFile.documentId}" />" data-file-documentId="<c:out escapeXml="true" value="${viewItemFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${viewItemFile.originalFilename}" />">
																			</c:otherwise>
																		</c:choose>
																	</c:if>
																</div>

																<br/>
																<span>${viewStatus.count} ${viewItemFile.originalFilename}</span>
															</li>
														</c:forEach>
													</ul>
												</div>

												<div class="footer">
													<ul>
														<li>
															<a data-dismiss="modal">
																<spring:message code="design.view.image.button.close" />
															</a>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>

									<div class="view-image">
										<c:if test="${storedFile ne null}">
											<c:choose>
												<c:when test="${storedFile.thumbnail ne null}">
													<c:if test="${storedFile.thumbnail.documentId ne null && not empty storedFile.thumbnail.documentId}">
														<img class="thumb" src="getDocument.htm?documentId=${storedFile.thumbnail.documentId}" id="viewImage${statusDesignViews.count}" data-file-documentId="<c:out escapeXml="true" value="${storedFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${storedFile.originalFilename}"/>">
													</c:if>
													<c:if test="${storedFile.thumbnail.documentId eq null || empty storedFile.thumbnail.documentId}">
														<img class="thumb" src="resources/img/thumbnailNotAvailable.png" id="viewImage${status.count}" data-file-documentId="<c:out escapeXml="true" value="${storedFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${storedFile.originalFilename}"/>">
													</c:if>
												</c:when>
												<c:otherwise>
													<img class="thumb" src="getDocument.htm?documentId=${storedFile.documentId}" id="viewImage${status.count}" data-file-documentId="<c:out escapeXml="true" value="${storedFile.documentId}" />" data-file-name="<c:out escapeXml="true" value="${storedFile.originalFilename}"/>">
												</c:otherwise>
											</c:choose>
										</c:if>
									</div>
								</td>

								<td><c:out value="${design.views.size()}"></c:out></td>
								<td><c:out value="${design.locarnoClassification}" /></td>
								<td><c:out value="${design.productIndication}" /></td>
								<td>
									<c:choose>
	        							<c:when test="${design.requestDeferredPublication or flowBean.mainForm.requestDeferredPublication}">
	        								<spring:message code="review.design.table.column.deferred.yes" />
	        							</c:when>
	        							<c:otherwise>
	        								<spring:message code="review.design.table.column.deferred.no" />
	        							</c:otherwise>
	        						</c:choose>
	        					</td>
								<td><a class="search-icon" id="thumbnail-list${status.count}" data-val="${design.id}" data-rownum="${status.count}" ></a></td>
							</tr>		
						</c:forEach>
					</tbody>
				</table>
			</c:if>
        
		</div>
	
</div>
<jsp:include page="../designs/views/view_image.jsp" />
