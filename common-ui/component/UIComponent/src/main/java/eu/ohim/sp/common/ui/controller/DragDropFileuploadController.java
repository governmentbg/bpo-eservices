package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.FileUploadConfigurationFactory;
import eu.ohim.sp.common.ui.flow.section.FileUploadFlowBean;
import eu.ohim.sp.common.ui.form.resources.MultipartFileForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Raya
 * 01.08.2019
 */
@Controller
public class DragDropFileuploadController extends CommonFileuploadController{

    private static Logger logger = Logger.getLogger(DragDropFileuploadController.class);

    @Autowired
    private FileServiceInterface fileService;

    @Autowired
    private FileUploadConfigurationFactory fileUploadConfigurationFactory;

    @Autowired
    private FormValidator validator;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private FileUploadFlowBean flowBean;

    @Autowired
    private FieldPropertyEditor fieldPropertyEditor;

    @PreAuthorize("hasRole('Attachment_Add')")
    @RequestMapping(value = "fileDragAndDropUpload", method = RequestMethod.POST)
    public ModelAndView onFileuploadDragAndDrop(@ModelAttribute("multipartFileForm") MultipartFileForm fileForm,
                                                BindingResult result, @RequestParam("fileWrapperPath") String fileWrapperPath) {

        ModelAndView mav = new ModelAndView("fileupload/attachedDragAndDropFile");
        Map<String, FileUploadType> map = fileUploadConfigurationFactory.getFileuploadConfiguration();

        fileForm.getFileWrapper().setAttachment(true);
        FileUploadType fileUploadType = map.get(fileForm.getFileForm().getName());

        // Do not remove, required for passing security test plan.
        logger.info("Attempt to Drag and Drop UPLOAD file: " + fileForm.getFileForm().getName());

        logger.debug("File : " + fileForm.getFileForm().getName());
        if (fileUploadType != null) {
            logger.debug("File drag and drop upload type : " + fileUploadType);
            if (fileUploadType.getFileUploadInfo().getMaximumFiles() != null) {
                logger.debug("Maximum files : " + fileUploadType.getFileUploadInfo().getMaximumFiles());
                fileForm.getFileWrapper().setMaximumFiles(
                    fileUploadType.getFileUploadInfo().getMaximumFiles().intValue());
            }
        }

        if (logger.isDebugEnabled()) {
            for (StoredFile file : fileForm.getFileWrapper().getStoredFiles()) {
                logger.debug(file.getFilename() + " , " + file.getOriginalFilename());
                logger.debug(fileForm.getFileWrapper().getAttachment() + " , " + file.getContentType());
            }
        }

        StoredFile storedFile = null;
        try {
            if (fileForm.getFileForm() != null && fileForm.getFileForm().getBytes() != null) {
                if (!mimeTypesMatch(fileForm)) {
                    logger.error("Drag And Drop Fileupload CANCELLED. MimeTypes are not matching.");
                    result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
                }

                if(fileWrapperPath.equals("mainForm.fileWrapperImage") || fileWrapperPath.equals("view")) {

                    byte[] clearedMDImage = processMetadataAndValidate(fileUploadType, fileForm, result);
                    if (result.getErrorCount() == 0) {
                        storedFile = fileService.addFile(flowBean.getIdreserventity(), fileForm.getFileForm().getOriginalFilename(),
                            fileForm.getFileForm().getContentType(), (long) clearedMDImage.length, clearedMDImage, fileForm.getFileWrapper());
                    }

                } else {
                    if (result.getErrorCount() == 0) {
                        storedFile = fileService.addFile(flowBean.getIdreserventity(), fileForm.getFileForm(),
                            fileForm.getFileWrapper());
                    }
                }

                // Do not remove, required for passing security test plan.
                logger.info("UPLOADED FILE: " + fileForm.getFileForm().getName());
            }
            else {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }
        }
        catch (IOException e) {
            result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
        }

        if (!result.hasErrors()&& fileWrapperPath.equals("otherAttachments")) {
            validator.validate(storedFile, result, flowScopeDetails);
        }

        if (result.getErrorCount() == 0) {
            fileForm.getFileWrapper().getStoredFiles().add(storedFile);
            fileForm.getFileWrapper().setAttachment(Boolean.TRUE);
        }

        mav.addObject("fileWrapper", fileForm.getFileWrapper());
        mav.addObject("fileWrapperPath", StringEscapeUtils.escapeJavaScript(fileWrapperPath));
        return mav;
    }
}
