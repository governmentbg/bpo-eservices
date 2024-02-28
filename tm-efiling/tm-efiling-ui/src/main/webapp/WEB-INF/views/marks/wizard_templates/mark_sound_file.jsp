<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/component" %>

<div id="figurativeUploader" class="span6 uploader">
    <div id="soundFile" class="fileuploadInfo row">
        <component:file labelCode="mark.representation.sound" pathFilename="soundFile"
                        fileWrapperPath="mainForm.soundFile"
                        fileWrapper="${flowBean.mainForm.soundFile}"
                        helpMessageKey="${param.helpMessageKey}"
                        helpUseFlowModeId="${param.helpUseFlowModeId}"
                        showPreLabelInputFileButton="false"/>
    </div>
</div>