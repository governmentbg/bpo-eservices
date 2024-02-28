<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 19.8.2019 г.
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${not empty flowBean.markViews}">
    <c:set var="storedFile" value="${flowBean.markViews[0].view.storedFiles.size() > 0 ? flowBean.markViews[0].view.storedFiles[0] : null}" />
    <div id="viewThumbnailList" class="modal fade messagePopup modal-mark-view-image" style="display:none">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="header">
                    <h1><spring:message code="review.mark.thumbnail.title" /></h1>
                    <a class="close-icon" data-dismiss="modal"></a>
                </div>

                <div class="section">
                    <ul>
                        <c:forEach var="viewItem" items="${flowBean.markViews}" varStatus="viewStatus">
                            <li>
                                <c:set var="viewItemFile" value="${viewItem.view.storedFiles.size() > 0 ? viewItem.view.storedFiles[0] : null}" />

                                <div class="view-image">
                                    <c:if test="${viewItemFile ne null}">
                                        <c:if test="${empty viewItemFile.documentId}">
                                            <c:set var="documentSrc" value="${viewItemFile.originalFilename}"></c:set>
                                        </c:if>
                                        <c:if test="${not empty viewItemFile.documentId}">
                                            <c:set var="documentSrc" value="getDocument.htm?documentId=${viewItemFile.documentId}&flowKey=${empty param.execution?param.flowKey:param.execution}"></c:set>
                                        </c:if>
                                        <a href="${documentSrc}">
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
                                        </a>
                                    </c:if>
                                </div>

                                <br/>
                                <span>${viewItemFile.originalFilename}
                                (<c:out value="${not empty viewItem.title ? viewItem.title : viewItem.imageNumber}"/>)
                                </span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="footer">
                    <ul>
                        <li>
                            <a data-dismiss="modal">
                                <spring:message code="mark.view.image.button.close" />
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="view-image">
        <div style="margin: 10px"><spring:message code="review.mark.first.image"/> </div>
        <c:if test="${storedFile ne null}">
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

            <button style="margin:20px;" class="btn btn-primary" id="thumbnail-list"><spring:message code="review.mark.show.views"/> </button>
        </c:if>
    </div>
</c:if>