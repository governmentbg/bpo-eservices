package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.mime.utils.MimeTypeUtil;
import eu.ohim.sp.common.ui.fileupload.ImageMetadataUtils;
import eu.ohim.sp.common.ui.fileupload.exception.ImageValidationException;
import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.validation.ImageValidator;
import eu.ohim.sp.common.ui.form.resources.MultipartFileForm;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by Raya
 * 01.08.2019
 */
public class CommonFileuploadController {

    @Autowired
    private ImageValidator imageValidator;

    private static Logger logger = Logger.getLogger(CommonFileuploadController.class);

    protected boolean mimeTypesMatch(MultipartFileForm fileForm) {
        boolean result = false;

        String mimeTypeGuessed = null;

        try {
            mimeTypeGuessed = MimeTypeUtil.getMimeType(fileForm.getFileForm().getBytes(), null);
            String mimeTypeDeclared = fileForm.getFileForm().getContentType();
            logger.info("Guessed mimeType from reviewing file content:" + mimeTypeGuessed);
            logger.info("Declared mimeType in file extension:" + mimeTypeDeclared);

            if (MimeTypeUtil.areEquivalent(mimeTypeGuessed,mimeTypeDeclared)) {
                result = true;
            }
        } catch (IOException e) {
            throw new SPException(e);
        }

        return result;
    }

    protected byte[] processMetadataAndValidate(FileUploadType fileUploadType, MultipartFileForm fileForm, BindingResult result) throws IOException{
        List<AllowedFileType> allowedFileTypes = fileUploadType.getFileUploadInfo().getAllowedTypes().getAllowedFileType();
        AllowedFileType allowedFileType = null;
        for(AllowedFileType type: allowedFileTypes){
            if(type.getValue().equalsIgnoreCase(fileForm.getFileForm().getContentType())){
                allowedFileType = type;
                break;
            }
        }

        byte[] clearedMDImage = null;
        if(allowedFileType != null && allowedFileType.getImageValidationParameters() != null && allowedFileType.getImageValidationParameters().getExifInfo() != null ){
            clearedMDImage = ImageMetadataUtils.removeMetadataAndFixResolution(fileForm.getFileForm().getBytes(),
                allowedFileType.getImageValidationParameters().getExifInfo().getMinXDensity(),
                allowedFileType.getImageValidationParameters().getExifInfo().getMinYDensity());
        } else {
            clearedMDImage = ImageMetadataUtils.removeMetadataAndFixResolution(fileForm.getFileForm().getBytes(),
                null,
                null);
        }

        try {
            if(allowedFileType != null) {
                imageValidator.validateResolutionAndSizeCm(allowedFileType.getImageValidationParameters(), clearedMDImage);
            }
        } catch (ImageValidationException e) {
            result.rejectValue("fileWrapper.description", e.getMessage());
        } catch (ValidationException e){
            result.rejectValue("fileWrapper.description", e.getMessage());
        }

        return clearedMDImage;
    }

}
