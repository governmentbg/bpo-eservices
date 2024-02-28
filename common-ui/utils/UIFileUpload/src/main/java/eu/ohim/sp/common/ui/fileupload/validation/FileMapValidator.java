package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author ionitdi
 */
public interface FileMapValidator
{
    /**
     * Gets the list of validators used to validate the files in the given file map.
     *
     * @return
     */
    public List<Validator> getValidators();

    /**
     * Adds a validator to the list of validators used on the files in the given file map.
     *
     * @param validator the validator to add
     */
    public void addValidator(Validator validator);

    /**
     * Method that iterates through the given files and checks them against
     * their configuration.
     * The configuration contains information about the allowed file type
     * (specified as a mime type)and image validation parameters.
     * The method first checks that the file type is allowed and
     * afterwards proceeds to calling the given validators against the file,
     * according to given configuration.
     *
     * @param fileMap
     *              the files that need to be validated
     * @param fileUploadConfigurationMap
     *              the configuration according to which the files are validated
     */
    public void validateMultipartFiles(Map<String, MultipartFile> fileMap, Map<String, FileUploadType> fileUploadConfigurationMap)
            throws ValidationException;
}
