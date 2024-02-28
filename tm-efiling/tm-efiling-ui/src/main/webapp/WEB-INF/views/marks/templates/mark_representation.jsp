<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="figurativeUploader" class="span6 uploader">
	<div class="fileuploadInfo row fileupload-buttonbar imagePreview" id="fileImage">
	    <component:file labelCode="mark.representation" pathFilename="fileWrapperImage"
                       fileWrapperPath="mainForm.fileWrapperImage"
	                   fileWrapper="${flowBean.mainForm.fileWrapperImage}"
                       helpMessageKey="${param.helpMessageKey}"
                       helpUseFlowModeId="${param.helpUseFlowModeId}"/>
	</div>
</div>