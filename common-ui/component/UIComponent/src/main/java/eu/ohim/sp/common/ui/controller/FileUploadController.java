/*******************************************************************************
 * * $Id:: FileUploadController.java 55918 2013-01-31 21:29:37Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.FileUploadConfigurationFactory;
import eu.ohim.sp.common.ui.flow.section.FileUploadFlowBean;
import eu.ohim.sp.common.ui.form.resources.FileContent;
import eu.ohim.sp.common.ui.form.resources.MultipartFileForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

/**
 * Controller that manages the uploading of attachments in the application
 * 
 */
@Controller
public class FileUploadController extends CommonFileuploadController {

    private static Logger logger = Logger.getLogger(FileUploadController.class);

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, fieldPropertyEditor);
    }

    /**
     * Controller that uploads a file to efiling. It is generic and it is used
     * by all the sections. It is based on the MultipartFileForm that contains
     * all the MultipartFile used by the application and based on the name of
     * the input(html) that contains the file that is uploaded, it understands
     * on which section it refers to.
     * 
     * @param fileForm
     *            the MultipartFileForm which role is already described
     * @return the view and info related to the file that was uploaded
     */
    @PreAuthorize("hasRole('Attachment_Add')")
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public ModelAndView onFileupload(@ModelAttribute("multipartFileForm") MultipartFileForm fileForm,
            BindingResult result, @RequestParam("fileWrapperPath") String fileWrapperPath) {

        ModelAndView mav = new ModelAndView("fileupload/attachedFile");
        Map<String, FileUploadType> map = fileUploadConfigurationFactory.getFileuploadConfiguration();

        fileForm.getFileWrapper().setAttachment(true);
        FileUploadType fileUploadType = map.get(fileForm.getFileForm().getName());

        // Do not remove, required for passing security test plan.
        logger.info("Attempt to UPLOAD file: " + fileForm.getFileForm().getName());

        logger.debug("File : " + fileForm.getFileForm().getName());
        if (fileUploadType != null) {
            logger.debug("File upload type : " + fileUploadType);
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

        // Special treatment for flowbean because lazylist does not work properly in webflow
        // Puts the attachment information on ajax call
        if (fileWrapperPath.equals("mainForm.fileWrapperImage") || fileWrapperPath.equals("mainForm.soundFile")
                || fileWrapperPath.equals("mainForm.fileWrapperMultimedia")
                || fileWrapperPath.equals("mainForm.trademarkRegulationDocuments")
                || fileWrapperPath.equals("mainForm.trademarkTranslationDocuments")
                || fileWrapperPath.equals("mainForm.trademarkApplicantDocuments")
                || fileWrapperPath.equals("patent.patentDescriptions")
                || fileWrapperPath.equals("patent.patentClaims")
                || fileWrapperPath.equals("patent.patentDrawings")
                || fileWrapperPath.equals("patent.sequencesListing")
                || fileWrapperPath.equals("otherAttachments")
                || fileWrapperPath.equals("mainForm.divisionalApplication.fileDivisionalApplication")) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, fileWrapperPath, fileForm.getFileWrapper());
            } catch (NoSuchMethodException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (IllegalAccessException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (InvocationTargetException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }

        }

        if (fileWrapperPath.equals("designOfficiaryFiles") || fileWrapperPath.equals("designNotOfficiaryFiles") ||
            fileWrapperPath.equals("patentOfficiaryFiles") || fileWrapperPath.equals("patentNotOfficiaryFiles") ||  fileWrapperPath.equals("transferContractFiles")
        ) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, "mainForm.entitlement." + fileWrapperPath, fileForm.getFileWrapper());
            } catch (NoSuchMethodException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (IllegalAccessException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (InvocationTargetException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }
        }

        if (fileWrapperPath.equals("inventorsAreRealFiles") || fileWrapperPath.equals("smallCompanyFiles") || fileWrapperPath.equals("postponementOfPublicationFiles")
            || fileWrapperPath.equals("epoDecisionCopyFiles") || fileWrapperPath.equals("epoTransferChangeFormFiles")
        ) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, "mainForm." + fileWrapperPath, fileForm.getFileWrapper());
            } catch (NoSuchMethodException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (IllegalAccessException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (InvocationTargetException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }
        }
        if (fileWrapperPath.equals("payLaterAttachment")) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, "paymentForm." + fileWrapperPath, fileForm.getFileWrapper());
            } catch (NoSuchMethodException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (IllegalAccessException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            } catch (InvocationTargetException e) {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }
        }

        StoredFile storedFile = null;
        try {
            if (fileForm.getFileForm() != null && fileForm.getFileForm().getBytes() != null) {
            
            	if (!mimeTypesMatch(fileForm)) {
                    logger.info("Fileupload CANCELLED. MimeTypes are not matching.");
                    result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
                }
                if (containsMaliciousXSS(fileForm.getFileForm().getContentType()) ||  containsMaliciousXSS(fileForm.getFileForm().getOriginalFilename())) {
                    logger.error("Fileupload CANCELLED. Malicious data in the form.");
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

            } else {
                result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
            }
        } catch (IOException e) {
            result.rejectValue("fileWrapper.description", "fileupload.error.fatal");
        }

        if (result.getAllErrors().size() == 0 && fileWrapperPath.equals("otherAttachments")) {
            validator.validate(storedFile, result, flowScopeDetails);
        }

        if (result.getErrorCount() == 0) {
            fileForm.getFileWrapper().getStoredFiles().add(storedFile);
            fileForm.getFileWrapper().setAttachment(Boolean.TRUE);
        }

        mav.addObject("fileWrapper", fileForm.getFileWrapper());
        mav.addObject("fileWrapperPath", fileWrapperPath);

        return mav;
    }


    private boolean containsMaliciousXSS(String value) {
        boolean xssDetected = false;

        if(!value.isEmpty()){
            if (value.contains("<") || value.contains(">")) {
                logger.debug("Wrong value:" + value);
                xssDetected = true;
            }
        }

        return xssDetected;
    }

    @RequestMapping(value = "editCommentAttachment", method = RequestMethod.GET)
    public ModelAndView editComment(String image) {
        ModelAndView mav = new ModelAndView("fileupload/attachedFile");
        return mav;
    }

    /**
     * Controller that returns the byte array that contains the document which
     * is requested with the given filename
     * 
     * @param documentId
     *            the filename of the requested image
     * @return the byte[] that contains the requested image
     */
    @RequestMapping(value = "getDocument", method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam String documentId,
            HttpServletResponse response) throws Exception {
        try {
            // Do not remove, required for passing security test plan.
            logger.info("Attempt to DOWNLOAD file with documentId: " + documentId);

            FileContent content = fileService.getFileStream(documentId);

            byte[] b = content.getContent();
            
			response.setContentType(content.getContentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + content.getName());
            response.setContentLength(b.length);

            // Do not remove, required for passing security test plan.
            logger.info("DOWNLOADED file with documentId: " + documentId);

           // Write file to response.
            OutputStream output = response.getOutputStream();
            output.write(b);
            output.close();
 
        } catch (Exception e) {
            throw new SPException(e);
        }
        return null;
    }

    /**
     * Controller that removes files from the application
     * 
     * @param documentId Uploaded file id to remove.
     * @param form the MultipartFileForm that gives us information about the removed file.
     * @param fileWrapperPath the file wrapper path.
     * @return
     */
    @RequestMapping(value = "removeUploadedFile", method = RequestMethod.POST)
    public ModelAndView onRemoveUploadedFile(@RequestParam(required = true, value = "documentId") String documentId,
            @ModelAttribute("fileWrapper") MultipartFileForm form,
            @RequestParam("fileWrapperPath") String fileWrapperPath) {

        ModelAndView mav = new ModelAndView("fileupload/attachedFile");

        // Special treatment for flowbean because lazylist does not work properly in webflow
        // Puts the attachment information on ajax call
        if (fileWrapperPath.equals("mainForm.fileWrapperImage") || fileWrapperPath.equals("mainForm.soundFile")
                || fileWrapperPath.equals("mainForm.fileWrapperMultimedia")
                || fileWrapperPath.equals("mainForm.trademarkRegulationDocuments")
                || fileWrapperPath.equals("mainForm.trademarkTranslationDocuments")
                || fileWrapperPath.equals("mainForm.trademarkApplicantDocuments")
                || fileWrapperPath.equals("patent.patentDescriptions")
                || fileWrapperPath.equals("patent.patentClaims")
                || fileWrapperPath.equals("patent.patentDrawings")
                || fileWrapperPath.equals("patent.sequencesListing")
                || fileWrapperPath.equals("otherAttachments")) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();

            try {
                utilBeans.setProperty(flowBean, fileWrapperPath, form.getFileWrapper());
            } catch (NoSuchMethodException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (IllegalAccessException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (InvocationTargetException e) {
                throw new SPException("Problem uploading file ", e, "");
            }

        }

        if (fileWrapperPath.equals("designOfficiaryFiles") || fileWrapperPath.equals("designNotOfficiaryFiles") ||
            fileWrapperPath.equals("patentOfficiaryFiles") || fileWrapperPath.equals("patentNotOfficiaryFiles") ||  fileWrapperPath.equals("transferContractFiles")
        ) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, "mainForm.entitlement." + fileWrapperPath, form.getFileWrapper());
            } catch (NoSuchMethodException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (IllegalAccessException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (InvocationTargetException e) {
                throw new SPException("Problem uploading file ", e, "");
            }
        }

        if (fileWrapperPath.equals("inventorsAreRealFiles") || fileWrapperPath.equals("smallCompanyFiles") || fileWrapperPath.equals("postponementOfPublicationFiles")
            || fileWrapperPath.equals("epoDecisionCopyFiles") || fileWrapperPath.equals("epoTransferChangeFormFiles")
        ) {
            PropertyUtilsBean utilBeans = new PropertyUtilsBean();
            try {
                utilBeans.setProperty(flowBean, "mainForm." + fileWrapperPath, form.getFileWrapper());
            } catch (NoSuchMethodException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (IllegalAccessException e) {
                throw new SPException("Problem uploading file ", e, "");
            } catch (InvocationTargetException e) {
                throw new SPException("Problem uploading file ", e, "");
            }
        }

        Iterator<StoredFile> it = form.getFileWrapper().getStoredFiles().iterator();
        while (it.hasNext()) {
            StoredFile storedFile = it.next();
            if (storedFile.getDocumentId().equals(documentId)) {
                it.remove();
                logger.debug("To be removed File : " + storedFile.getOriginalFilename());
                break;
            }
        }

        if (form.getFileWrapper().getStoredFiles().size() == 0) {
            form.getFileWrapper().setAttachment(false);
        }

        if (fileWrapperPath.equals("mainForm.fileWrapperImage")){
            mav.addObject("helpMessageKey", "mark.imageFile.attachment.help");
        } else if (fileWrapperPath.equals("mainForm.soundFile")){
            mav.addObject("helpMessageKey", "mark.soundFile.attachment.help");
        } else if(fileWrapperPath.equals("view")){
            mav.addObject("helpMessageKey", "design.form.views.validViewFormat");
        }
        mav.addObject("fileWrapper", form.getFileWrapper());
        mav.addObject("fileWrapperPath", fileWrapperPath);

        return mav;
    }
}
