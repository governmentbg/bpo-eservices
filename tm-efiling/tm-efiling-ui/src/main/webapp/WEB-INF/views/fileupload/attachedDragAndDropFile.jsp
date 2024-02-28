<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tags/component/fsp-tags.tld" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:if test="${empty sectionId}">
    <c:set var="sectionId" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:param.sectionId}"/>
</c:if>

<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fileWrapperPath}" checkRender="true">

    <form:errors path="${fileWrapperPath}.description" cssClass="flMessageError"></form:errors>

    <span id="formDragAndDropUpload" class="hide">
		<inputmock name="fileWrapperPath" value="${fileWrapperPath}" class="submittedAjax"></inputmock>
		<inputmock name="fileWrapper.labelCode" class="submittedAjax" value="${not empty labelCode?labelCode:fileWrapper.labelCode}"></inputmock>
		<inputmock name="fileWrapper.showPreLabelInputFileButton" class="submittedAjax" value="${not empty showPreLabelInputFileButton?showPreLabelInputFileButton:fileWrapper.showPreLabelInputFileButton}"></inputmock>
		<inputmock name="fileWrapper.inputFileClass" class="submittedAjax" value="${not empty inputFileClass?inputFileClass:fileWrapper.inputFileClass}"></inputmock>
		<inputmock name="fileWrapper.inputFileLabelCode" class="submittedAjax" value="${not empty inputFileLabelCode?inputFileLabelCode:fileWrapper.inputFileLabelCode}"></inputmock>
		<inputmock name="fileWrapper.pathFilename" class="submittedAjax" value="${not empty pathFilename?pathFilename:fileWrapper.pathFilename}"></inputmock>
		<inputmock name="fileWrapper.attachment" class="submittedAjax" value="${fileWrapper.attachment}"></inputmock>
		<inputmock name="fileWrapper.sectionId" class="submittedAjax" value="${not empty fileWrapper.sectionId?fileWrapper.sectionId:sectionId}"></inputmock>
		<inputmock name="fileWrapper.maximumFiles" class="submittedAjax" value="${fileWrapper.maximumFiles}"></inputmock>
		<inputmock name="fileWrapper.thumbnail" class="submittedAjax" value="true"></inputmock>
		<c:forEach items="${fileWrapper.storedFiles}" var="storedFile">
            <inputmock class="submittedAjax file" id="filename" value="${storedFile.filename}"></inputmock>
            <inputmock class="submittedAjax file" id="fileSize" value="${storedFile.fileSize}"></inputmock>
            <inputmock class="submittedAjax file" id="formattedFileSize" value="${storedFile.canonicalFileSize}"></inputmock>
            <inputmock class="submittedAjax file" id="originalFilename" value="<c:out value="${storedFile.originalFilename}" escapeXml="true" />"></inputmock>
            <inputmock class="submittedAjax file" id="description" value="<c:out value="${storedFile.description}" escapeXml="true" />"></inputmock>
            <inputmock class="submittedAjax file" id="contentType" value="<c:out value="${storedFile.contentType}" escapeXml="true" />"></inputmock>
            <inputmock class="submittedAjax file" id="documentId" value="<c:out value="${storedFile.documentId}" escapeXml="true" />"></inputmock>
            <c:if test="${not empty storedFile.thumbnail}">
                <inputmock class="submittedAjax thumbnail" name="filename" value="${storedFile.thumbnail.filename}"></inputmock>
                <inputmock class="submittedAjax thumbnail" name="fileSize" value="${storedFile.thumbnail.fileSize}"></inputmock>
                <inputmock class="submittedAjax thumbnail" name="originalFilename" value="<c:out value="${storedFile.thumbnail.originalFilename}" escapeXml="true" />"></inputmock>
                <inputmock class="submittedAjax thumbnail" name="description" value="<c:out value="${storedFile.thumbnail.description}" escapeXml="true" />" ></inputmock>
                <inputmock class="submittedAjax thumbnail" name="contentType" value="<c:out value="${storedFile.thumbnail.contentType}" escapeXml="true" />"></inputmock>
                <inputmock class="submittedAjax thumbnail " name="documentId" value="<c:out value="${storedFile.thumbnail.documentId}" escapeXml="true" />" ></inputmock>
            </c:if>
        </c:forEach>
	</span>
</tags:render>