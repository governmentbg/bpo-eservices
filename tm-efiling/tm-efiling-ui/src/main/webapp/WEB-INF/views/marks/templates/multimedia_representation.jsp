<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="label" value="mark.representation"/>

<c:if test="${flowBean.mainForm.markType == 'hologrammark'}">
	<c:set var="label" value="mark.representation.hologram"/>
</c:if>
<c:if test="${flowBean.mainForm.markType == 'motionmark'}">
	<c:set var="label" value="mark.representation.motion"/>
</c:if>
<c:if test="${flowBean.mainForm.markType == 'multimediamark'}">
	<c:set var="label" value="mark.representation.multimedia"/>
</c:if>

<div id="figurativeUploader" class="span6 uploader">
	<input type="hidden" id="helpMessageKey" value="${param.helpMessageKey}">
	<div class="fileuploadInfo row fileupload-buttonbar imagePreview" id="fileMultimedia">
	    <component:file labelCode="${label}" pathFilename="fileWrapperMultimedia"
                       fileWrapperPath="mainForm.fileWrapperMultimedia"
	                   fileWrapper="${flowBean.mainForm.fileWrapperMultimedia}"
                       helpMessageKey="${param.helpMessageKey}"
                       helpUseFlowModeId="${param.helpUseFlowModeId}"/>
	</div>
</div>