package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ionitdi
 */
public interface Validator
{
    /**
     * This method validates the given MultipartFile according to the
     * validation parameters contained by the AllowedFileType parameter.
     * @param file
     * @param allowedFileType
     */
    void validate(MultipartFile file, AllowedFileType allowedFileType) throws ValidationException;
}
