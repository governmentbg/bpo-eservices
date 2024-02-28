package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ionitdi
 */
public class FileMapValidatorImpl implements FileMapValidator
{
    private final Logger log = Logger.getLogger(FileMapValidatorImpl.class);

    private List<Validator> validators;

    public FileMapValidatorImpl(boolean initValidators)
    {
        validators = new ArrayList<Validator>();

        if(initValidators)
        {
            // Initializing validators
            addValidator(new ImageValidator());
            addValidator(new SoundValidator());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<Validator> getValidators()
    {
        return validators;
    }

    @Override
    public void addValidator(Validator validator)
    {
        validators.add(validator);
    }

    /**
     * Method that iterates through the given files and checks them against
     * their configuration.
     * The configuration contains information about the allowed file type
     * (specified as a mime type)and image validation parameters.
     * The method first checks that the file type is allowed and
     * afterwards proceeds to checking whether
     * it is an image and its format is according to given configuration.
     *
     * @param fileMap
     *              the files that need to be validated
     * @param fileUploadConfigurationMap
     *              the configuration according to which the files are validated
     */
    public void validateMultipartFiles(Map<String, MultipartFile> fileMap, Map<String, FileUploadType> fileUploadConfigurationMap)
        throws ValidationException
    {
        if (fileMap == null)
            return;
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet())
        {
            FileUploadType fileConfiguration = fileUploadConfigurationMap.get(entry.getKey());
            if (fileConfiguration == null || fileConfiguration.getFileUploadInfo() == null || fileConfiguration.getFileUploadInfo().getAllowedTypes() == null)
            {
                continue;
            }
            if (log.isInfoEnabled())
            {
                log.info("Validator : Found configuration for entry with key " + entry.getKey());
            }

            MultipartFile file = entry.getValue();

            if (log.isDebugEnabled())
            {
                log.debug("Validator : Iterating through allowed file types... ");
            }
            for (AllowedFileType allowedFileType : fileConfiguration.getFileUploadInfo().getAllowedTypes().getAllowedFileType())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Validator : Found allowed file type named " + allowedFileType.getValue() +
                                      ". Checking if equal to file's actual content type " + file.getContentType());
                }
                if (allowedFileType.getValue().equals(file.getContentType()))
                {
                    for (Validator v : validators)
                    {
                        v.validate(file, allowedFileType);
                    }
                }
            }
        }
    }
}
