<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="attachments">
    <header>
		<c:choose>
			<c:when test="${fn:endsWith(flowModeId, '-generic')}">
				<spring:message code="review.otherAttachments.title.generic"/>
			</c:when>
			<c:otherwise>
				<spring:message code="review.otherAttachments.title"/>
			</c:otherwise>
		</c:choose>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Attachments"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
        
    <c:if test="${not empty flowBean.comment}">		
			<table>
				<tbody>					
					 <tr>						
						<td>
						
						<spring:eval var="storecomment" expression="flowBean.comment"/>
									<c:choose>
										<c:when test="${storecomment.length()>60}">
											<c:out value="${storecomment.substring(0,60)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${storecomment}" />
										</c:otherwise>
									</c:choose>
						</td>
					</tr>
				</tbody>
			</table>		
		</c:if>
	<br>
     <c:if test="${not empty flowBean.otherAttachments}">		
			<table>
				<tbody>			
					<c:forEach var="index" items="${flowBean.otherAttachments.storedFiles}" varStatus="status">		
					 	<tr>
							<td><b><spring:message code="review.otherAttachments.file"/>: </b>
							${index.originalFilename}
							(<fmt:formatNumber value="${index.fileSize/(1024)}" maxFractionDigits="0"/> Kb)
<%-- 							<c:if test="${not empty index.numPages}"> --%>
<%-- 								${index.numPages} <spring:message code="review.otherAttachments.pages"/> --%>
<%-- 							</c:if> --%>
							</td>
							<c:if test="${not empty index.docType}">
								<td><b><spring:message code="review.otherAttachments.type"/>: </b><spring:message code="AttachmentDocumentType.${index.docType}"/></td>
							</c:if>
							<c:if test="${not empty index.description}">						
								<td><b><spring:message code="review.otherAttachments.description"/>: </b>${index.description}</td>							
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>		
		</c:if>
</div>