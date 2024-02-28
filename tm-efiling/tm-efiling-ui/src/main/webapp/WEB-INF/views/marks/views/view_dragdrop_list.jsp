<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 1.8.2019 г.
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tags/component/fsp-tags.tld" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="view" checkRender="true">

    <form:errors path="view.description" cssClass="flMessageError"></form:errors>

    <input id="dragAndDropUpload" type="hidden" data-url="fileDragAndDropUpload.htm?fileWrapperPath=view&flowKey=${empty param.execution?param.flowKey:param.execution}"/>

    <span id="editMultiupload" class="hide">
        <c:if test="${not empty flowBean.markViews}">
            <c:forEach items="${flowBean.markViews}" var="form">
                <c:set var="sequence" value="${form.sequence}" scope="request"/>
                <inputmock data-sequence="${sequence}" name="sequence" value="${form.sequence}"></inputmock>
                <inputmock data-sequence="${sequence}" class="form" name="title" value="<c:out value="${form.title}" escapeXml="true" />"></inputmock>
                <inputmock data-sequence="${sequence}" class="form" name="imageNumber"  value="<c:out value="${form.imageNumber}" escapeXml="true" />"></inputmock>
                <inputmock data-sequence="${sequence}" class="form" name="imported"  value="<c:out value="${form.imported}" escapeXml="true" />"></inputmock>
                <inputmock data-sequence="${sequence}" class="form" name="id"  value="<c:out value="${form.id}" escapeXml="true" />"></inputmock>
                <c:if test="${not empty form.view}">
                    <inputmock data-sequence="${sequence}" name="maximumFiles" value="${form.view.maximumFiles}"></inputmock>
                    <c:forEach items="${form.view.storedFiles}" var="storedFile">
                        <inputmock data-sequence="${sequence}" name="filename" class="file" value="${storedFile.filename}"></inputmock>
                        <inputmock data-sequence="${sequence}" name="originalFilename" class="file" value="<c:out value="${storedFile.originalFilename}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="description" class="file" value="<c:out value="${storedFile.description}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="fileSize" class="file" value="${storedFile.fileSize}"></inputmock>
                        <inputmock data-sequence="${sequence}" name="formattedFileSize" value="${storedFile.canonicalFileSize}"></inputmock>
                        <inputmock data-sequence="${sequence}" name="contentType" class="file" value="<c:out value="${storedFile.contentType}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="documentId" class="file" value="<c:out value="${storedFile.documentId}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="filename" class="thumbnail" value="${storedFile.thumbnail.filename}"></inputmock>
                        <inputmock data-sequence="${sequence}" name="originalFilename" class="thumbnail" value="<c:out value="${storedFile.thumbnail.originalFilename}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="description" class="thumbnail" value="<c:out value="${storedFile.thumbnail.description}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="fileSize" class="thumbnail" value="${storedFile.thumbnail.fileSize}"></inputmock>
                        <inputmock data-sequence="${sequence}" name="contentType" class="thumbnail" value="<c:out value="${storedFile.thumbnail.contentType}" escapeXml="true" />"></inputmock>
                        <inputmock data-sequence="${sequence}" name="documentId" class="thumbnail" value="<c:out value="${storedFile.thumbnail.documentId}" escapeXml="true" />"></inputmock>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </c:if>
    </span>

    <c:set var="fileWrapperPath" value="view" scope="request"/>
    <jsp:include page="/WEB-INF/views/fileupload/attachedDragAndDropFile.jsp"/>

</tags:render>