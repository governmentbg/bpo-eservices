<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fieldset>
    <div class="controls" id="markType">
        <div class="col-400">
            <component:checkbox path="mainForm.collectiveMark"
                                checkRender="true" id="collectiveMark"
                                labelTextCode="mark.isCollective" formTagClass="checkerCollective fasttrackinput-ignore" helpMessageKey="BRXYZ.collective.mandatory" useLabelFor="false"/>
            <component:checkbox path="mainForm.certificateMark"
                                checkRender="true" id="certificateMark"
                                labelTextCode="mark.isCertificate" formTagClass="checkerCertificate fasttrackinput-ignore" helpMessageKey="BRXYZ.certificate.mandatory" useLabelFor="false"/>
        </div>

    </div>
    <div id="wdCollective1" class="collectiveSelectors" style="display : ${flowBean.mainForm.collectiveMark || flowBean.mainForm.certificateMark ? 'block' : 'none'}">
        <div class="fileuploadInfo" id="trademarkRegulationDocuments">
            <component:file labelCode="mark.collective.regulations" pathFilename="trademarkRegulationDocuments"
                            fileWrapperPath="mainForm.trademarkRegulationDocuments"
                            fileWrapper="${flowBean.mainForm.trademarkRegulationDocuments}"/>
        </div>
        <div class="fileuploadInfo" id="trademarkTranslationDocuments">
            <component:file labelCode="mark.collective.translation" pathFilename="trademarkTranslationDocuments"
                            fileWrapperPath="mainForm.trademarkTranslationDocuments"
                            fileWrapper="${flowBean.mainForm.trademarkTranslationDocuments}"/>
        </div>
        <div class="fileuploadInfo" id="trademarkApplicantDocuments">
            <component:file labelCode="mark.collective.markApplicants" pathFilename="trademarkApplicantDocuments"
                            fileWrapperPath="mainForm.trademarkApplicantDocuments"
                            fileWrapper="${flowBean.mainForm.trademarkApplicantDocuments}"/>
        </div>
    </div>
</fieldset>
